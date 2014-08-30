package de.bistrosoft.palaver.menueplanverwaltung;

import java.sql.SQLException;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.gui.view.MenueAnlegen;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.palaver.Application;
import de.palaver.management.emploee.Employee;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Menu;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;
import fi.jasoft.dragdroplayouts.DDGridLayout;

/**
 * 
 * @author Eike Becher, Melanie Gross, Christine Hartkorn
 * 
 */

@SuppressWarnings("serial")
public class WinSelectMenue extends Window {

	Window subwindow = this;

	Menu menu;
	private FilterTable ftMenueList = new FilterTable();
	private FilterTable ftFehler = new FilterTable();

	private TextField tfId = new TextField("Id");
	private TextField tfAngezName = new TextField("Angezeigter Name");
	private TextField tfName = new TextField("Name");
	private TextField tfKoch = new TextField("Koch");
	private TextField tfFussnote = new TextField("Fußnote");
	private TextField tfMenueart = new TextField("Menueart");
	private TextField tfGeschmack = new TextField("Geschmack");
	private TextField tfPortion = new TextField("Portion in %");
	private CheckBox chbAufwand = new CheckBox("Aufwand");
	private CheckBox chbFavorit = new CheckBox("Favorit");
	private Button btNewMenue = new Button("Neues Menü");
	private Button btFilterLeeren;
	private Button btSelect = new Button(IConstants.BUTTON_SELECT);
	private VerticalLayout flEditorLayout = new VerticalLayout();

	List<Regel> regeln = null;

	@SuppressWarnings("unused")
	private Integer curCol;
	@SuppressWarnings("unused")
	private Integer curRow;

	private Component destComp;
	private int destRow;
	private int destCol;
	private MenueplanGridLayout menueplan;
	DDGridLayout menueGrid;

	BeanItemContainer<Menu> menueContainer;
	BeanItemContainer<Regel> ctRegeln;

	VerticalLayout vlLeftLayout = new VerticalLayout();

	public WinSelectMenue(MenueplanGridLayout menueplan, Component destComp,
			int destRow, int destCol) {
		setCaption("Menü einfügen");
		setResizable(false);
		this.menueplan = menueplan;
		this.menueGrid = menueplan.layout;
		this.destComp = destComp;
		this.destCol = destCol;
		this.destRow = destRow;

		regeln = Regelverwaltung.getInstance().getAllAktivRegeln();

		initLayout();
		initMenueList();
		initEditor();
		initOKButton();
		setRowCcol();
	}

	private void setRowCcol() {
		final int COLUMNS = menueGrid.getColumns();
		final int ROWS = menueGrid.getRows();

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (destComp.equals(menueGrid.getComponent(col, row))) {
					curCol = col;
					curRow = row;
				}
			}
		}

	}

	private void initLayout() {
		// SplitPanel erstellen
		// HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		HorizontalLayout hlBox = new HorizontalLayout();
		HorizontalLayout hlControl = new HorizontalLayout();
		hlControl.setMargin(new MarginInfo(false, true, true, false));		
		hlBox.setMargin(new MarginInfo(true, true, true, true));

		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);
		btFilterLeeren.setIcon(new ThemeResource("img/cross.ico"));

		// splitPanel.setWidth("800px");
		// Splitpanel die Layouts zufügen
		hlBox.addComponent(vlLeftLayout);
		vlLeftLayout.addComponent(hlControl);
		hlBox.setWidth("100%");

		hlControl.addComponent(btNewMenue);
		hlControl.addComponent(btFilterLeeren);

		vlLeftLayout.addComponent(ftMenueList);

		hlBox.addComponent(flEditorLayout);
		// Der linken Seite Tabelle hinzufügen und Layout für Suchfeld und
		// "Neu" Button

		btNewMenue.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addNewMenue();
			}

		});

		btFilterLeeren.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ftMenueList.resetFilters();
			}
		});

		// Linke Seite Höhe und Breite auf 100% setzen
		vlLeftLayout.setHeight("100%");
		flEditorLayout.setHeight("100%");
		flEditorLayout.setWidth("100%");
		flEditorLayout.setMargin(true);
		flEditorLayout.setVisible(false);
		setContent(hlBox);
	}

	private void initEditor() {

		HorizontalLayout hlEditorChB = new HorizontalLayout();
		hlEditorChB.addComponents(chbFavorit, chbAufwand);
		tfAngezName.setWidth("100%");
		tfName.setWidth("100%");
		tfKoch.setWidth("100%");
		tfMenueart.setWidth("100%");
		tfGeschmack.setWidth("100%");
		tfPortion.setWidth("100%");
		tfFussnote.setWidth("100%");
		flEditorLayout.addComponents(tfAngezName, tfName, tfKoch, tfMenueart,
				tfGeschmack, tfPortion, hlEditorChB, tfFussnote);
//TODO:
		flEditorLayout.addComponent(ftFehler);
		ftFehler.setVisible(false);

		flEditorLayout.addComponent(btSelect);
		flEditorLayout.setSpacing(true);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void initOKButton() {
		btSelect.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (tfPortion.getValue().isEmpty()
						|| !isInteger(tfPortion.getValue())) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_MENU_HINZUFUEGEN);
				} else if (ftMenueList.getValue() == null) {
					((Application) UI.getCurrent().getData())
							.showDialog("Bitte ein Menü auswählen!");
				} else {
					// aktuelle Column und Row ermitteln
					Integer sourceRow = -1;
					Integer sourceColumn = -1;

					final int COLUMNS = menueGrid.getColumns();
					final int ROWS = menueGrid.getRows();

					for (int row = 0; row < ROWS; row++) {
						for (int col = 0; col < COLUMNS; col++) {
							if (destComp.equals(menueGrid
									.getComponent(col, row))) {
								sourceColumn = col;
								sourceRow = row;
							}
						}
					}
					menueplan.removeMenue(destComp);

					// Menübezeichnung des ausgewählten Menüs
					try {
						Menu menu = (Menu) ftMenueList.getValue();
						// Neue Menükomponente aus ausgewähltem Menü erstellen
						// und hinzufügen
						Integer iPortion = Integer.parseInt(tfPortion
								.getValue());
						MenueComponent menueComp = new MenueComponent(menu,
								tfAngezName.getValue(), menueplan, menueGrid,
								sourceRow, sourceColumn, true, iPortion, tfFussnote.getValue());
						menueplan.addMenue(menueComp, sourceColumn, sourceRow);
						menueGrid.setComponentAlignment(menueComp,
								Alignment.MIDDLE_CENTER);

					} catch (Exception e) {

						e.printStackTrace();
					}

					// Window schließen
					subwindow.close();
				}
			}
		});
	}

	private void initMenueList() {

		ladeMenues();
		ftMenueList.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					menu = (Menu) event.getProperty().getValue();
					tfId.setValue(menu.getId().toString());
					tfId.setVisible(false);
					tfAngezName.setValue(menu.getName());
					tfName.setValue(menu.getName());
					tfName.setEnabled(false);
					tfKoch.setValue(menu.getEmployee().getName());
					tfKoch.setEnabled(false);
					if(menu.getFussnotenList() != null) {
						String a = "";
						int b = 0;
						for (Fussnote fn : menu.getFussnotenList()) {
							if(menu.getFussnotenList().get(menu.getFussnotenList().size()-1) == menu.getFussnotenList().get(b)){
								a = fn.getAbkuerzung();
							} else {  a = a + fn.getAbkuerzung() + ", "; }
						}
						tfFussnote.setValue(a);
						tfFussnote.setEnabled(true);
					}
					if (menu.getMenutype() != null) {
						tfMenueart.setValue(menu.getMenutype()
								.getName());
					}
					tfMenueart.setEnabled(false);
					if (menu.getGeschmack() != null) {
						tfGeschmack.setValue(menu.getGeschmack()
								.getName());
					}
					tfGeschmack.setEnabled(false);
					chbFavorit.setValue(menu.getFavorit());
					chbAufwand.setValue(menu.getAufwand());
					chbFavorit.setEnabled(false);
					chbAufwand.setEnabled(false);
					tfPortion.setValue("100");
					flEditorLayout.setVisible(true);
					

				}

			}
		});
		if (destComp instanceof MenueComponent) {
			Employee tmpMa = ((MenueComponent) destComp).getMenue()
					.getEmployee();
			if (((Application) UI.getCurrent().getData()).getUser().equals(
					tmpMa)) {

				ftMenueList.setFilterFieldValue("kochname", ((Application) UI
						.getCurrent().getData()).getUser().getVorname());
			}

		} else {
			ftMenueList.setFilterFieldValue("kochname", ((Application) UI
					.getCurrent().getData()).getUser().getVorname());

		}

		ftMenueList.setColumnWidth("name", 200);
		ftMenueList.setColumnWidth("kochname", 80);
		ftMenueList.setColumnWidth("geschmack", 80);
		ftMenueList.setColumnWidth("menueart", 80);

		// Spaltenbezeichnung angeben
		ftMenueList.setSelectable(true);
		ftMenueList.setFilterBarVisible(true);
		ftMenueList.setImmediate(true);

		// bei Ändern Komponente aus Menüplan selektieren
		if (menueGrid.getComponent(destCol, destRow) instanceof MenueComponent) {
			MenueComponent mc = (MenueComponent) menueGrid.getComponent(
					destCol, destRow);
			ftMenueList.select(mc.getMenue());
			tfAngezName.setValue(mc.getAngezeigterName());
			tfPortion.setValue(mc.getPortion().toString());
			tfFussnote.setValue(mc.getFussnoten());
		}

	}

	private void ladeMenues() {

		// Container für Menüliste festlegen
		try {
			menueContainer = new BeanItemContainer<Menu>(Menu.class,
					Menueverwaltung.getInstance().getAllMenuesFast());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ftMenueList.setContainerDataSource(menueContainer);

		// Spalten festlegen
		ftMenueList.setVisibleColumns(new Object[] { "name", "employee",
				"geschmack", "menutype" });
		ftMenueList.sort(new Object[] { "name" }, new boolean[] { true });
	}

	private void addNewMenue() {
		final Window win = new Window("Neues Menü");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("1100px");
		win.setHeight("850px");

		MenueAnlegen me = new MenueAnlegen();
		win.setContent(me);
		// addComponent(me);

		win.setContent(me);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				ladeMenues();

			}
		});
	}
}

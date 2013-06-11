package de.bistrosoft.palaver.menueplanverwaltung;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class WinSelectMenue extends Window {

	Window subwindow = this;

	Menue menue;
	private FilterTable ftMenueList = new FilterTable();
	// private TextField searchField = new TextField();
	private TextField tfId = new TextField("Id");
	private TextField tfAngezName = new TextField("Angezeigter Name");
	private TextField tfName = new TextField("Name");
	private TextField tfKoch = new TextField("Koch");
	private TextField tfMenueart = new TextField("Menueart");
	private TextField tfGeschmack = new TextField("Geschmack");
	private TextField tfPortion = new TextField("Portion in %");
	private CheckBox chbAufwand = new CheckBox("Aufwand");
	private CheckBox chbFavorit = new CheckBox("Favorit");
	private Button btNewMenue = new Button("Neu");
	private Button btSelect = new Button(IConstants.BUTTON_SELECT);
	private FormLayout flEditorLayout = new FormLayout();

	private static final String MENU = "name";

	Component destComp;
	int destRow;
	int destCol;
	MenueplanGridLayout menueplan;
	DDGridLayout menueGrid;

	BeanItemContainer<Menue> menueContainer;

	// Horizontales und vertikales Layout anlegen
	// HorizontalLayout bottomLeftLayout = new HorizontalLayout();
	VerticalLayout leftLayout = new VerticalLayout();

	// Konstruktor
	public WinSelectMenue(MenueplanGridLayout nMenuePlan, Component nDestComp,
			int nDestRow, int nDestCol) {
		setCaption("Men� einf�gen");
		menueplan = nMenuePlan;
		menueGrid = menueplan.layout;
		destComp = nDestComp;
		destCol = nDestCol;
		destRow = nDestRow;

		initLayout();
		initMenueList();
		initEditor();
		initSearch();
		initOKButton();
	}

	// Layout festlegen
	private void initLayout() {
		// SplitPanel erstellen
		// HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		HorizontalLayout splitPanel = new HorizontalLayout();

		// splitPanel.setWidth("800px");
		// Splitpanel die Layouts zuf�gen
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(flEditorLayout);
		// splitPanel.setExpandRatio(leftLayout, 2);
		// splitPanel.setExpandRatio(flEditorLayout, 1);
		// Der linken Seite Tabelle hinzuf�gen und Layout f�r Suchfeld und
		// "Neu" Button
		leftLayout.addComponent(ftMenueList);
		// leftLayout.addComponent(bottomLeftLayout);

		// Suchfeld und "Neu" Button hinzuf�gen
		// bottomLeftLayout.addComponent(searchField);
		leftLayout.addComponent(btNewMenue);

		// Linke Seite H�he und Breite auf 100% setzen
		leftLayout.setHeight("100%");
		// leftLayout.setWidth("500px");
		flEditorLayout.setHeight("100%");
		// flEditorLayout.setWidth("300px");
		// Kontaktliste soll gesamten Platz verwenden
		// leftLayout.setExpandRatio(ftMenueList, 2);
		// ftMenueList.setSizeFull();

		// "bottomleftlayout" soll gesamte Breite verwenden
		// bottomLeftLayout.setWidth("100%");

		// Suchfeld soll verf�gbare Breite verwenden
		// searchField.setWidth("100%");
		// bottomLeftLayout.setExpandRatio(searchField, 1);

		flEditorLayout.setMargin(true);
		flEditorLayout.setVisible(false);
		setContent(splitPanel);
	}

	// Rechte Seite
	private void initEditor() {

		HorizontalLayout v = new HorizontalLayout();
		v.addComponents(chbFavorit, chbAufwand);
		flEditorLayout.addComponents(tfAngezName, tfName, tfKoch, tfMenueart,
				tfGeschmack, tfPortion, v);

		flEditorLayout.addComponent(btSelect);
	}

	// Suchfeld
	private void initSearch() {

		// Info im Suchfeld setzen
		// searchField.setInputPrompt("Men� suchen");

		// TextChangeEvent wird ausgel�st, wenn bei der Eingabe eine Pause ist
		// searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		// ChangeListener hinzuf�gen
		// searchField.addTextChangeListener(new TextChangeListener() {
		// public void textChange(final TextChangeEvent event) {
		//
		// // Alle Filter entfernen
		// menueContainer.removeAllContainerFilters();
		// // Nur den eingegeben Filter hinzufügen
		// menueContainer.addContainerFilter(new MenueFilter(event
		// .getText()));
		// }
		// });
	}

	// Klasse f�r MenueFilter
	private class MenueFilter implements Filter {
		private String needle;

		// Konstruktor f�r Menuefilter
		public MenueFilter(String needle) {
			// string in Kleinbuchstaben
			this.needle = needle.toLowerCase();
		}

		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(MENU).getValue())
					.toLowerCase();
			return haystack.contains(needle);
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}

	// Clicklistener f�r Auswahl Button
	private void initOKButton() {
		btSelect.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				// aktuelle Column und Row ermitteln
				Component sourceComp = destComp;
				Integer sourceRow = -1;
				Integer sourceColumn = -1;

				final int COLUMNS = menueGrid.getColumns();
				final int ROWS = menueGrid.getRows();

				for (int row = 0; row < ROWS; row++) {
					for (int col = 0; col < COLUMNS; col++) {
						if (sourceComp.equals(menueGrid.getComponent(col, row))) {
							sourceColumn = col;
							sourceRow = row;
						}
					}
				}

				menueplan.removeMenue(destComp);

				// Men�bezeichnung des ausgew�hlten Men�s
				try {
					Menue menue = (Menue) ftMenueList.getValue();
//					Menue menue = Menueverwaltung.getInstance().getMenueByName(tfName.getValue());
					// Neue Men�komponente aus ausgew�hltem Men� erstellen
					// und hinzuf�gen
					Integer iPortion=Integer.parseInt(tfPortion.getValue());
					MenueComponent menueComp = new MenueComponent(menue,
							tfAngezName.getValue(), menueplan, menueGrid,
							sourceRow, sourceColumn, true, iPortion);
					menueplan.addMenue(menueComp, sourceColumn, sourceRow);
					menueGrid.setComponentAlignment(menueComp,
							Alignment.MIDDLE_CENTER);

				} catch (Exception e) {

					e.printStackTrace();
				}

				// Window schlie�en
				subwindow.close();
			}
		});
	}

	private void initMenueList() {

		// Container f�r Men�liste festlegen
		menueContainer = new BeanItemContainer<Menue>(Menue.class,
				Menueverwaltung.getInstance().getAllMenuesTabelle());

		ftMenueList.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					menue = (Menue) event.getProperty().getValue();
					tfId.setValue(menue.getId().toString());
					tfId.setVisible(false);
					tfAngezName.setValue(menue.getName());
					tfName.setValue(menue.getName());
					tfName.setEnabled(false);
					tfKoch.setValue(menue.getKochname());
					tfKoch.setEnabled(false);
					if (menue.getMenueart() != null) {
						tfMenueart.setValue(menue.getMenueart()
								.getBezeichnung());
					}
					tfMenueart.setEnabled(false);
					if (menue.getGeschmack() != null) {
						tfGeschmack.setValue(menue.getGeschmack()
								.getBezeichnung());
					}
					tfGeschmack.setEnabled(false);
					chbFavorit.setValue(menue.getFavorit());
					chbAufwand.setValue(menue.getAufwand());
					chbFavorit.setEnabled(false);
					chbAufwand.setEnabled(false);
					tfPortion.setValue("100");
					flEditorLayout.setVisible(true);

				}

			}
		});
		ftMenueList.setContainerDataSource(menueContainer);

		// Spalten festlegen
		ftMenueList.setVisibleColumns(new Object[] { "name", "kochname",
				"geschmack", "menueart" });
		ftMenueList.sort(new Object[] { "name" }, new boolean[] { true });

		ftMenueList.setFilterFieldValue("kochname", ((Application) UI
				.getCurrent().getData()).getUser().getVorname());
		
		ftMenueList.setColumnWidth("name", 200);
		ftMenueList.setColumnWidth("kochname", 80);
		ftMenueList.setColumnWidth("geschmack", 80);
		ftMenueList.setColumnWidth("menueart", 80);

		// Spaltenbezeichnung angeben
		ftMenueList.setSelectable(true);
		ftMenueList.setFilterBarVisible(true);
		ftMenueList.setImmediate(true);

		// bei �ndern Komponente aus Men�plan selektieren
		if (menueGrid.getComponent(destCol, destRow) instanceof MenueComponent) {
			MenueComponent mc = (MenueComponent) menueGrid.getComponent(destCol, destRow);
			ftMenueList.select(mc.getMenue());
			tfAngezName.setValue(mc.getAngezeigterName());
			tfPortion.setValue(mc.getPortion().toString());
		}

	}

}

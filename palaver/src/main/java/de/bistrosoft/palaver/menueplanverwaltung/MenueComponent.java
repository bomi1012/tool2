package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.palaver.Application;
import fi.jasoft.dragdroplayouts.DDGridLayout;

/**
 * 
 * @author Eike Becher, Christine Hartkorn, Melanie Gross
 * 
 */

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent {

	// private Component comp;
	public int row;
	public int col;
	public MenueplanGridLayout menueplan;
	private DDGridLayout menueGrid;
	private Button btn = new Button();
	private Button btDelete = new Button();
	private Button btChange = new Button();
	private Menue menue;
	private Boolean isChanged;
	private Button btFehler;
	private Integer portion;
	private CheckBox ogMarkiere = new CheckBox();

	private List<String> fehlermeldungen;
	private List<Regel> FehlerRegeln;

	private String angezeigterName;

	List<Fussnote> fns;
	String fn = null;

	String descNotification;

	public String getAngezeigterName() {
		return angezeigterName;
	}

	public void setAngezeigterName(String anzezeigterName) {
		this.angezeigterName = anzezeigterName;
	}

	public String getFussnoten() {
		return fn;
	}
	
	public void setFussnote(String fn) {
		this.fn = fn;
	}
	
	public List<Regel> getFehlerRegeln() {
		return FehlerRegeln;
	}

	public void setFehlerRegeln(List<Regel> fehlerRegeln) {
		FehlerRegeln = fehlerRegeln;
	}

	public List<String> getFehlermeldungen() {
		return fehlermeldungen;
	}

	public void setFehlermeldungen(List<String> fehlermeldungen) {
		btFehler.setVisible(true);
		this.fehlermeldungen = fehlermeldungen;
		if (fehlermeldungen != null && fehlermeldungen.size() > 0) {
			btFehler.setVisible(true);
			String desc = "";
			descNotification = "";
			for (String s : fehlermeldungen) {
				desc += s;
				descNotification += s;
			}
			btFehler.setDescription(desc);

			btFehler.addClickListener(new ClickListener() {

				// Click-Listener Fehler-Buttons
				@Override
				public void buttonClick(ClickEvent event) {
					Notification notification = new Notification(
							descNotification);
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				}
			});
		} else
			btFehler.setVisible(false);

	}

	public MenueplanGridLayout getMenueplan() {
		return menueplan;
	}

	public void setMenueplan(MenueplanGridLayout menueplan) {
		this.menueplan = menueplan;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Menue getMenue() {
		return menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	public DDGridLayout getMenueGrid() {
		return menueGrid;
	}

	/**
	 * @return the portion
	 */
	public Integer getPortion() {
		return portion;
	}

	/**
	 * @param portion
	 *            the portion to set
	 */
	public void setPortion(Integer portion) {
		this.portion = portion;
	}

	public void setMenueGrid(DDGridLayout menueGrid) {
		this.menueGrid = menueGrid;
	}

	public Boolean isChanged() {
		return isChanged;
	}

	public void isChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}

	public void addFehlerRegel(Regel regel) {
		if (regel == null) {
			return;
		}

		if (FehlerRegeln == null) {
			FehlerRegeln = new ArrayList<Regel>();
		}

		if (FehlerRegeln.indexOf(regel) >= 0) {
			btFehler.setVisible(true);
			return;
		}

		FehlerRegeln.add(regel);

		btFehler.setVisible(true);

		String desc = "<html>";
		descNotification = "";

		for (Regel r : FehlerRegeln) {
			desc += r.getFehlermeldung() + "<br>";
			descNotification += r.getFehlermeldung() + "\n\r";
		}

		desc += "</html>";

		btFehler.setDescription(desc);

		btFehler.addClickListener(new ClickListener() {

			// Click-Listener Fehler-Buttons
			@Override
			public void buttonClick(ClickEvent event) {
				Notification notification = new Notification(descNotification);
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		});
	}

	// Konstruktor für Menükomponente
	public MenueComponent(Menue menue, String angezName,
			MenueplanGridLayout nMenueplan, DDGridLayout nMenueGrid, int nRow,
			int nCol, Boolean isChanged, Integer portion, String fussnote) {
		this.isChanged = isChanged;
		this.col = nCol;
		this.row = nRow;
		this.menueplan = nMenueplan;
		this.menueGrid = nMenueGrid;
		// this.comp = this;
		this.setMenue(menue);
		this.angezeigterName = angezName;
		this.portion = portion;
		this.fn = fussnote;

		// Vertikales Layout erstellen
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);

		if(fn == "") {			
			// Menübezeichnung des ausgewählten Menüs der Menükomponente hinzufügen
			try {
				fns = Fussnotenverwaltung.getInstance().getFussnoteByMenue(
						menue.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			fn = "";
			for (Fussnote f : fns) {
				fn = fn + " " + f.getAbkuerzung().toString() + ",";
			}
			if(!fn.equals("")){
				fn = " (" + fn.substring(1, fn.length() - 1) + ")";
			}
		}
		Button btText = new Button(angezName + fn );
		btText.setPrimaryStyleName(BaseTheme.BUTTON_LINK);
		btText.setWidth("100%");
		btText.setHeight("80px");
		btText.addStyleName("center-text");

		vl.addComponent(btText);

		// Clicklistener für den ADD Button
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(new ThemeResource("img/Menue.png"));
		btn.addStyleName("menueplan-add");
		btn.addClickListener(new ClickListener() {

			// Click-Listener für ADD_Buttons
			@Override
			public void buttonClick(ClickEvent event) {
				WinSelectMenue window = new WinSelectMenue(menueplan, btn, row,
						col);
				UI.getCurrent().addWindow(window);
				window.setModal(true);
				window.setWidth("1050px");
				window.setHeight("510px");
			}
		});
		btn.setHeight("90px");
		btn.setWidth("149px");

		// ClickListener für den Ändernbutton
		btChange.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {

				//TODO:1
				((Application) UI.getCurrent().getData()).setChange(true);
				
				// finde position
				// Component sourceComp = comp;
				final int COLUMNS = menueGrid.getColumns();
				final int ROWS = menueGrid.getRows();
				for (int row = 0; row < ROWS; row++) {
					for (int col = 0; col < COLUMNS; col++) {
						if (MenueComponent.this.equals(menueGrid.getComponent(
								col, row))) {
						}
					}
				}
				// Add
				// TODO: Regeln übergeben
				WinSelectMenue window = new WinSelectMenue(menueplan,
						MenueComponent.this, row, col);
				UI.getCurrent().addWindow(window);
				window.setModal(true);
				window.setWidth("1050px");
				window.setHeight("510px");
			}
		});

		// ClickListener für den Löschbutton
		btDelete.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {

				//TODO:2
				((Application) UI.getCurrent().getData()).setChange(true);
				
				// Window erstellen welches abfragt, ob man das Menü wirklich
				// aus dem Menüplan löschen will
				ConfirmDialog.show(UI.getCurrent(), "Menü aus Plan löschen:",
						"Wollen Sie das Menü wirklich aus dem Plan löschen?",
						"Ja", "Nein", new ConfirmDialog.Listener() {

							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									MenueComponent.this.remove();
								}
							}
						});
			}
		});

		// /////////////
		btText.addListener(new Listener() {

			@Override
			public void componentEvent(Event event) {
				if (ogMarkiere.isVisible()) {
					if (ogMarkiere.getValue()) {
						ogMarkiere.setValue(false);
					} else {
						ogMarkiere.setValue(true);
					}
				} else {
					btChange.click();
				}
			}
		});
		// //////////

		btFehler = new Button();
		btFehler.setPrimaryStyleName(BaseTheme.BUTTON_LINK);
		btFehler.setIcon(new ThemeResource("img/false.ico"));
		// btFehler.setIcon(new ThemeResource("img/warning.bmp"));
		btFehler.addStyleName("menueplan-regel");
		btFehler.setWidth("20px");
		vl.addComponent(btFehler);
		btFehler.setVisible(false);
		vl.setComponentAlignment(btFehler, Alignment.TOP_RIGHT);

		HorizontalLayout hl = new HorizontalLayout();
		// btChange.addStyleName(Reindeer.BUTTON_SMALL);
		// btDelete.setStyleName(Reindeer.BUTTON_SMALL);
		btChange.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		btDelete.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		btChange.setIcon(new ThemeResource("img/edit.ico"));
		btDelete.setIcon(new ThemeResource("img/Delete.ico"));
		// btChange.addStyleName(Reindeer.BUTTON_DEFAULT);
		hl.addComponent(btChange);
		hl.addComponent(btDelete);

		ogMarkiere.setVisible(false);
		hl.addComponent(ogMarkiere);

		vl.addComponent(hl);
		vl.setComponentAlignment(btText, Alignment.MIDDLE_CENTER);
		// vl.setComponentAlignment(hlProp, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		// vl.setComponentAlignment(btDelete, Alignment.BOTTOM_RIGHT);
		vl.setHeight("90px");

	}

	public void remove() {
		// Speicher Position
		Integer col = MenueComponent.this.getCol();
		Integer row = MenueComponent.this.getRow();
		// aktuelle Menükomponente löschen
		menueplan.removeMenue(MenueComponent.this);
		// ADD Button hinzufügen
		menueGrid.addComponent(btn, col, row);
		menueGrid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
		if (this.isMarkiert()) {
			btn.setEnabled(false);
		}
	}

	public void pruefeRegeln(MenueplanGridLayout mp) {
		btFehler.setVisible(false);
		if (FehlerRegeln != null) {
			List<Regel> tmpRegeln = FehlerRegeln;
			FehlerRegeln = null;
			for (Regel r : tmpRegeln) {
				r.check(this, mp);
			}
		}

	}

	public void setCbDelete() {
		if (ogMarkiere.isVisible()) {
			ogMarkiere.setVisible(false);
			btDelete.setVisible(true);
			btChange.setVisible(true);
			ogMarkiere.setValue(false);
		} else {
			ogMarkiere.setVisible(true);
			btDelete.setVisible(false);
			btChange.setVisible(false);
		}
	}

	public boolean isMarkiert() {
		return ogMarkiere.getValue();
	}
}

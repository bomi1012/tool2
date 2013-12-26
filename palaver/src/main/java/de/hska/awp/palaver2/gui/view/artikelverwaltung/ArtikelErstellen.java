/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.domain.Lagerort;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver.artikelverwaltung.service.Lagerortverwaltung;
import de.hska.awp.palaver.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.view.lieferantenverwaltung.LieferantErstellen;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian Walz Diese Klasse ist eine Eingabeform fuer das Erstellen
 *         oder Ã„ndern eines Artikels. Wenn die Klasse ohne Parameter
 *         aufgerufen wird, dient sie zum Erstellen, werden Parameter
 *         uebergeben, werde die Daten automatisch in die Felder geschrieben und
 *         anstatt einen neuen Artikel anzulegen wird er geaendert.
 */
@SuppressWarnings({ "serial" })
public class ArtikelErstellen extends ArtikelAbstract implements View,
		ValueChangeListener {

	private static final Logger log = LoggerFactory
			.getLogger(ArtikelErstellen.class.getName());

	private Window win;
	private HorizontalLayout newKomponent;

	/** TextFields */
	private TextField artikelPreis;
	private TextField artikelNummer;
	private TextField gebinde;
	private TextField notiz;

	/** NativeSelects */
	private NativeSelect lieferantSelect;
	private NativeSelect mengeneinheitSelect;
	private NativeSelect kategorieSelect;
	private NativeSelect lagerortSelect;
	
	/** IntSteppers */
	private IntStepper durchschnittLT1;
	private IntStepper durchschnittLT2;

	/** Buttons */
	private Button addLieferant;
	private Button addMengeneinheit;
	private Button addKategorie;
	private Button addLagerort;
	private Button update = new Button(IConstants.BUTTON_SAVE);

	private CheckBox standard = new CheckBox("Standard");
	private CheckBox grundbedarf = new CheckBox("Grundbedarf");
	private CheckBox fuerRezepte = new CheckBox("Für Rezepte geeignet");

	private Artikel artikel = new Artikel();
	List<Mengeneinheit> mengen;
	List<Kategorie> kategorien;
	List<Lagerort> lagerorts;
	List<Lieferant> lieferanten;

	/**
	 * Der Konstruktor wird automatisch von dem ViewHandler aufgerufen. Er
	 * erzeugt das Layout, befuellt die Comboboxen und stellt die Funktionen
	 * bereit.
	 */
	public ArtikelErstellen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		/** TextFields */
		nameField = textFieldSettingAE(nameField, ArtikelAbstract.ARTIKEL_NAME,
				ArtikelAbstract.FULL, true, ArtikelAbstract.ARTIKEL_NAME, this);
		artikelPreis = textFieldSettingAE(artikelPreis,
				ArtikelAbstract.ARTIKEL_PREIS, ArtikelAbstract.FULL, false,
				ArtikelAbstract.ARTIKEL_PREIS, this);
		artikelNummer = textFieldSettingAE(artikelNummer,
				ArtikelAbstract.ARTIKEL_NUMMER, ArtikelAbstract.FULL, false,
				ArtikelAbstract.ARTIKEL_NUMMER, this);
		gebinde = textFieldSettingAE(gebinde, ArtikelAbstract.ARTIKEL_GEBINDE,
				ArtikelAbstract.FULL, true, ArtikelAbstract.ARTIKEL_GEBINDE,
				this);
		notiz = textFieldSettingAE(notiz, ArtikelAbstract.ARTIKEL_NOTIZ,
				ArtikelAbstract.FULL, false, ArtikelAbstract.ARTIKEL_NOTIZ,
				this);

		/** NativeSelects */
		lieferantSelect = nativeSelectSetting(lieferantSelect, "Lieferant",
				ArtikelAbstract.FULL, true, "Lieferant", this);
		mengeneinheitSelect = nativeSelectSetting(mengeneinheitSelect, "Mengeneinheit",
				ArtikelAbstract.FULL, true, "Mengeneinheit", this);
		kategorieSelect = nativeSelectSetting(kategorieSelect, "Kategorie",
				ArtikelAbstract.FULL, true, "Kategorie", this);
		lagerortSelect = nativeSelectSetting(lagerortSelect, "Lagerort", ArtikelAbstract.FULL,
				true, "Lagerort", this);

		/** Buttons */
		addLieferant = buttonSetting(addLieferant, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON);
		addMengeneinheit = buttonSetting(addMengeneinheit, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON);
		addKategorie = buttonSetting(addKategorie, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON);
		addLagerort = buttonSetting(addLagerort, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON);
		speichernButton = buttonSetting(speichernButton, IConstants.BUTTON_SAVE,
				IConstants.BUTTON_SAVE_ICON);
		verwerfenButton = buttonSetting(verwerfenButton, IConstants.BUTTON_DISCARD,
				IConstants.BUTTON_DISCARD_ICON);
		deaktivierenButton = buttonSetting(deaktivierenButton, IConstants.BUTTON_DEAKTIVIEREN,
				IConstants.BUTTON_DELETE_ICON);
		deaktivierenButton.setVisible(false);

		/** IntSteppers */
		durchschnittLT1 = intStepperSetting(durchschnittLT1, "Gebindeanzahl für den Termin 1",
				"98%", 0, 70, "Gebindeanzahl1");
		durchschnittLT2 = intStepperSetting(durchschnittLT2, "Gebindeanzahl für den Termin 2",
				"98%", 0, 70, "Gebindeanzahl2");
		
		durchschnittHL = new HorizontalLayout();
		durchschnittHL.setWidth(FULL);
		durchschnittHL.addComponent(durchschnittLT1);
		durchschnittHL.addComponent(durchschnittLT2);
		durchschnittHL.setExpandRatio(durchschnittLT1, 1);
		durchschnittHL.setExpandRatio(durchschnittLT2, 1);
		durchschnittHL.setComponentAlignment(durchschnittLT2, Alignment.BOTTOM_RIGHT);
		
		
		

		headlineLabel = new Label(ArtikelAbstract.NEW_ARTIKEL);
		headlineLabel.setStyleName("ViewHeadline");
		
		

		/** ControlPanel */
		controlHL = new HorizontalLayout();
		controlHL.setSpacing(true);
		controlHL.addComponent(verwerfenButton);
		controlHL.addComponent(speichernButton);
		controlHL.addComponent(deaktivierenButton);

		/** Layout */
		boxVL = boxLayout(boxVL, "450");
		this.addComponent(boxVL);
		this.setComponentAlignment(boxVL, Alignment.MIDDLE_CENTER);
		
		/** ValueChangeListene */
		grundbedarf.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				durchschnittLT1.setVisible(!durchschnittLT1.isVisible());
				durchschnittLT2.setVisible(!durchschnittLT2.isVisible());
			}
		});
		
		speichernButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					addArtickelToDataBase(0);
				}
			}
		});

		verwerfenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (ArtikelErstellen.this.getParent() instanceof Window) {
					Window win = (Window) ArtikelErstellen.this.getParent();
					win.close();
				} else {
					ViewHandler.getInstance().switchView(ArtikelAnzeigen.class,
							new ViewDataObject<Artikel>(artikel));
				}
			}
		});
		
		deaktivierenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Artikelverwaltung.getInstance().deaktivireArtikel(artikel);
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_DEAKTIVIEREN);
					ViewHandler.getInstance().switchView(ArtikelAnzeigen.class,
							new ViewDataObject<Artikel>(artikel));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/** Adds */		
		addLieferant.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addLieferant();
			}
		});
		addMengeneinheit.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addMengeneinheit();
			}
		});
		addKategorie.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addKategorie();
			}
		});
		addLagerort.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addLagerort();

			}
		});
		load();
	}
	
	@Override
	public void getViewParam(ViewData data) {
		artikel = (Artikel) ((ViewDataObject<?>) data).getData();
		deaktivierenButton.setVisible(true);
		controlHL.replaceComponent(speichernButton, update);
		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addArtickelToDataBase(1);
			}
		});

		headlineLabel.setValue("Artikel bearbeiten");
		nameField.setValue(artikel.getName());
		artikelNummer.setValue(artikel.getArtikelnr());
		artikelPreis.setValue(artikel.getPreis() + "");
		lieferantSelect.select(artikel.getLieferant());
		kategorieSelect.select(artikel.getKategorie());
		lagerortSelect.select(artikel.getLagerort());
		fuerRezepte.setValue(artikel.isFuerRezept());
		gebinde.setValue(artikel.getBestellgroesse() + "");
		standard.setValue(artikel.isStandard());
		grundbedarf.setValue(artikel.isGrundbedarf());
		durchschnittLT1.setValue(artikel.getDurchschnittLT1());
		durchschnittLT2.setValue(artikel.getDurchschnittLT2());
		notiz.setValue(artikel.getNotiz());
		mengeneinheitSelect.select(artikel.getMengeneinheit());
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
	}

	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //

	private Boolean validiereEingabe() {
		/** preis */
		if (!artikelPreis.getValue().isEmpty()) {
			try {
				Float.parseFloat(artikelPreis.getValue());
			} catch (NumberFormatException e) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_PREIS);
				return false;
			}
		}
		
		try {
			Double.parseDouble(gebinde.getValue());
		} catch (NumberFormatException e) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
			return false;
		}

		if (nameField.getValue() == "") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_NAME);
			return false;
		}
		
		if (mengeneinheitSelect.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT_B);
			return false;
		}
		
		if (kategorieSelect.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIE);
			return false;
		}
		
		if (!fuerRezepte.getValue()) {
			if (gebinde.getValue() == null
					|| Double.parseDouble(gebinde.getValue().toString()) < 0.1) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
				return false;
			}
		}
		return true;

	}
	private void addArtickelToDataBase(int action) {
		artikel.setName(nameField.getValue());// name
		artikel.setArtikelnr(artikelNummer.getValue()); // nr
		artikel.setPreis((artikelPreis.getValue() == "") ? 0F : 
				Float.parseFloat(artikelPreis.getValue().replace(',', '.')));// price
		artikel.setNotiz(notiz.getValue());// Notiz
		artikel.setDurchschnittLT1(durchschnittLT1.getValue()); // GebindeAnzahl
		artikel.setDurchschnittLT2(durchschnittLT2.getValue()); // GebindeAnzahl
		artikel.setKategorie((Kategorie) kategorieSelect.getValue()); // kategorie
		artikel.setLieferant((Lieferant) lieferantSelect.getValue()); // Lieferant
		artikel.setLagerort((Lagerort) lagerortSelect.getValue());
		artikel.setMengeneinheit((Mengeneinheit) mengeneinheitSelect
				.getValue()); 
		artikel.setBestellgroesse(Double.parseDouble(gebinde.getValue()));
		artikel.setGrundbedarf(grundbedarf.getValue()); // grundbedarf
		artikel.setStandard(standard.getValue());
		artikel.setFuerRezept(fuerRezepte.getValue());
		try {
			String message = null;
			if (action == 0) {
				Artikelverwaltung.getInstance().createArtikel(artikel);
				message = "Artikel Wurde gespeichert";
			} else if (action == 1) {
				Artikelverwaltung.getInstance().updateArtikel(artikel);
				message = "Artikel Wurde geändert";
			}
			Notification notification = new Notification(message);
			notification.setDelayMsec(2000);
			notification.show(Page.getCurrent());
			ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}
	
	private void allKategories(List<Kategorie> kategorien) {
		kategorieSelect.removeAllItems();
		for (Kategorie e : kategorien) {
			kategorieSelect.addItem(e);
		}
	}
	private void allLieferanten(List<Lieferant> lieferanten) {
		lieferantSelect.removeAllItems();
		for (Lieferant e : lieferanten) {
			lieferantSelect.addItem(e);
		}
	}
	private void allMengeneinheiten(List<Mengeneinheit> mengeneinheiten) {
		mengeneinheitSelect.removeAllItems();
		for (Mengeneinheit e : mengeneinheiten) {
			mengeneinheitSelect.addItem(e);
		}
	}
	private void allLagerorte(List<Lagerort> lagerorte) {
		lagerortSelect.removeAllItems();
		for (Lagerort e : lagerorte) {
			lagerortSelect.addItem(e);
		}
	}
	
	private void load() {
		try {			
			allLieferanten(Lieferantenverwaltung.getInstance().getAllLieferanten());
			allKategories(Kategorienverwaltung.getInstance().getAllKategories());
			allMengeneinheiten(Mengeneinheitverwaltung.getInstance().getAllMengeneinheiten());
			allLagerorte(Lagerortverwaltung.getInstance().getAllLagerorts());
		} catch (Exception e) {
			log.error(e.toString());
		}
	}
	private void addMengeneinheit() {		
		win = windowUI(win, "Neuer Mengeneinheit", "500", "350");
		MengeneinheitErstellen me = new MengeneinheitErstellen();
		addComponent(me);
		win.setContent(me);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				try {
					allMengeneinheiten(Mengeneinheitverwaltung.getInstance().getAllMengeneinheiten());
				} catch (ConnectException e1) {
					e1.printStackTrace();
				} catch (DAOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
	}
	private void addLieferant() {
		win = windowUI(win, "Neuer Lieferant", "950", "500");
		LieferantErstellen le = new LieferantErstellen();
		addComponent(le);
		win.setContent(le);
		UI.getCurrent().addWindow(win);
	}
	private void addKategorie() {
		win = windowUI(win, "Neuer Kategorie", "500", "250");
		KategorieErstellen ke = new KategorieErstellen();
		addComponent(ke);
		win.setContent(ke);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				try {
					allKategories(Kategorienverwaltung.getInstance().getAllKategories());
				} catch (ConnectException e1) {
					e1.printStackTrace();
				} catch (DAOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	private void addLagerort() {
		win = windowUI(win, "Neuer Lagerort", "500", "250");
		LagerortErstellen loe = new LagerortErstellen();
		addComponent(loe);
		win.setContent(loe);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				try {
					allLagerorte(Lagerortverwaltung.getInstance().getAllLagerorts());
				} catch (ConnectException e1) {
					e1.printStackTrace();
				} catch (DAOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	private VerticalLayout boxLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);

		box.addComponent(headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(nameField);
		box.addComponent(artikelNummer);
		box.addComponent(artikelPreis);
		box.addComponent(notiz);
		box.addComponent(newKomponent(newKomponent, lieferantSelect,
				addLieferant, ArtikelAbstract.FULL));
		box.addComponent(newKomponent(newKomponent, kategorieSelect,
				addKategorie, ArtikelAbstract.FULL));
		box.addComponent(newKomponent(newKomponent, lagerortSelect, addLagerort,
				ArtikelAbstract.FULL));
		box.addComponent(standard);
		box.addComponent(fuerRezepte);
		box.addComponent(gebinde);
		box.addComponent(newKomponent(newKomponent, mengeneinheitSelect,
				addMengeneinheit, ArtikelAbstract.FULL));
		box.addComponent(grundbedarf);
		box.addComponent(durchschnittHL);
		box.addComponent(new Hr());

		box.addComponent(controlHL);
		box.setComponentAlignment(controlHL, Alignment.MIDDLE_RIGHT);

		return box;
	}
}

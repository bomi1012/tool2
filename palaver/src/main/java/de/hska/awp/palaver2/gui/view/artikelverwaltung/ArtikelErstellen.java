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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
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
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian Walz Diese Klasse ist eine Eingabeform fuer das Erstellen
 *         oder Ã„ndern eines Artikels. Wenn die Klasse ohne Parameter
 *         aufgerufen wird, dient sie zum Erstellen, werden Parameter
 *         uebergeben, werde die Daten automatisch in die Felder geschrieben und
 *         anstatt einen neuen Artikel anzulegen wird er geaendert.
 */
@SuppressWarnings({ "serial" })
public class ArtikelErstellen extends OverErstellen implements View,
		ValueChangeListener {
	private HorizontalLayout m_durchschnittHL;
	private static final Logger LOG = LoggerFactory.getLogger(ArtikelErstellen.class.getName());

	/** TextFields */
	private TextField m_preisField;
	private TextField m_nummerField;
	private TextField m_gebindeField;
	private TextField m_notizField;

	/** NativeSelects */
	private NativeSelect m_lieferantSelect;
	private NativeSelect m_mengeneinheitSelect;
	private NativeSelect m_kategorieSelect;
	private NativeSelect m_lagerortSelect;
	
	/** IntSteppers */
	private IntStepper m_durchschnittLT1;
	private IntStepper m_durchschnittLT2;

	/** Buttons */
	private Button m_addLieferantButton;
	private Button m_addMengeneinheitButton;
	private Button m_addKategorieButton;
	private Button m_addLagerortButton;

	/** Checkbox */
	private CheckBox m_standardCheckbox = new CheckBox("Standard");
	private CheckBox m_grundbedarfCheckbox = new CheckBox("Grundbedarf");
	private CheckBox m_fuerRezepteCheckbox = new CheckBox("Für Rezepte geeignet");

	public ArtikelErstellen() {
		super();
		m_artikel = new Artikel();
		m_create = true;
		layout(NEW_ARTIKEL);
		listener();		
		load();
	}
	public ArtikelErstellen(Artikel artikel) {
		super();
		m_artikel = artikel;
		m_create = false;
		layout(EDIT_ARTIKEL);
		m_deaktivierenButton.setVisible(true);
		listener();		
		load();
		
		m_nameField.setValue(artikel.getName());
		m_nummerField.setValue(artikel.getArtikelnr());
		m_preisField.setValue(String.valueOf(artikel.getPreis()));
		m_lieferantSelect.select(artikel.getLieferant());
		m_kategorieSelect.select(artikel.getKategorie());
		m_lagerortSelect.select(artikel.getLagerort());
		m_fuerRezepteCheckbox.setValue(artikel.isFuerRezept());
		m_gebindeField.setValue(String.valueOf(artikel.getBestellgroesse()));
		m_standardCheckbox.setValue(artikel.isStandard());
		m_grundbedarfCheckbox.setValue(artikel.isGrundbedarf());
		m_durchschnittLT1.setValue(artikel.getDurchschnittLT1());
		m_durchschnittLT2.setValue(artikel.getDurchschnittLT2());
		m_notizField.setValue(artikel.getNotiz());
		m_mengeneinheitSelect.select(artikel.getMengeneinheit());
	}
	
	private void layout(String text) {
		this.setSizeFull();
		this.setMargin(true);
		/** TextFields */
		m_nameField = textFieldSettingAE(m_textField, OverArtikelverwaltungView.ARTIKEL_NAME,
				OverArtikelverwaltungView.FULL, true, OverArtikelverwaltungView.ARTIKEL_NAME, this);
		m_preisField = textFieldSettingAE(m_textField,
				OverArtikelverwaltungView.ARTIKEL_PREIS, OverArtikelverwaltungView.FULL, false,
				OverArtikelverwaltungView.ARTIKEL_PREIS, this);
		m_nummerField = textFieldSettingAE(m_textField,
				OverArtikelverwaltungView.ARTIKEL_NUMMER, OverArtikelverwaltungView.FULL, false,
				OverArtikelverwaltungView.ARTIKEL_NUMMER, this);
		m_gebindeField = textFieldSettingAE(m_textField, OverArtikelverwaltungView.ARTIKEL_GEBINDE,
				OverArtikelverwaltungView.FULL, true, OverArtikelverwaltungView.ARTIKEL_GEBINDE,
				this);
		m_notizField = textFieldSettingAE(m_textField, OverArtikelverwaltungView.ARTIKEL_NOTIZ,
				OverArtikelverwaltungView.FULL, false, OverArtikelverwaltungView.ARTIKEL_NOTIZ,
				this);

		/** NativeSelects */
		m_lieferantSelect = nativeSelectSetting(m_nativeSelect, "Lieferant",
				OverArtikelverwaltungView.FULL, true, "Lieferant", this);
		m_mengeneinheitSelect = nativeSelectSetting(m_nativeSelect, MENGENEINHEIT,
				OverArtikelverwaltungView.FULL, true, MENGENEINHEIT, this);
		m_kategorieSelect = nativeSelectSetting(m_nativeSelect, KATEGORIE,
				OverArtikelverwaltungView.FULL, true, KATEGORIE, this);
		m_lagerortSelect = nativeSelectSetting(m_nativeSelect, "Lagerort", OverArtikelverwaltungView.FULL,
				true, "Lagerort", this);

		/** Buttons */
		m_addLieferantButton = buttonSetting(m_button, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON, true, true);
		m_addMengeneinheitButton = buttonSetting(m_button, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON, true, true);
		m_addKategorieButton = buttonSetting(m_button, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON, true, true);
		m_addLagerortButton = buttonSetting(m_button, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON, true, true);
		m_speichernButton = buttonSetting(m_button, IConstants.BUTTON_SAVE,
				IConstants.BUTTON_SAVE_ICON, true, true);
		m_verwerfenButton = buttonSetting(m_button, IConstants.BUTTON_DISCARD,
				IConstants.BUTTON_DISCARD_ICON, true, true);
		m_deaktivierenButton = buttonSetting(m_button, IConstants.BUTTON_DEAKTIVIEREN,
				IConstants.BUTTON_DELETE_ICON, false, true);

		/** IntSteppers */
		m_durchschnittLT1 = intStepperSetting(m_intStepper, "Gebindeanzahl für den Termin 1",
				"98%", 0, 70, "Gebindeanzahl1");
		m_durchschnittLT2 = intStepperSetting(m_intStepper, "Gebindeanzahl für den Termin 2",
				"98%", 0, 70, "Gebindeanzahl2");
		
		
		m_durchschnittHL = new HorizontalLayout();
		m_durchschnittHL.setWidth(FULL);
		m_durchschnittHL.addComponent(m_durchschnittLT1);
		m_durchschnittHL.addComponent(m_durchschnittLT2);
		m_durchschnittHL.setExpandRatio(m_durchschnittLT1, 1);
		m_durchschnittHL.setExpandRatio(m_durchschnittLT2, 1);
		m_durchschnittHL.setComponentAlignment(m_durchschnittLT2, Alignment.BOTTOM_RIGHT);
		
		m_headlineLabel = headLine(m_headlineLabel, text, STYLE_HEADLINE);

		/** ControlPanel */
		m_control = controlErstellenPanel();

		/** Layout */
		vertikalLayout = vLayout(vertikalLayout, "450");
		this.addComponent(vertikalLayout);
		this.setComponentAlignment(vertikalLayout, Alignment.MIDDLE_CENTER);
	}
	
	private void listener() {
		/** ValueChangeListene */
		m_grundbedarfCheckbox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				m_durchschnittLT1.setVisible(!m_durchschnittLT1.isVisible());
				m_durchschnittLT2.setVisible(!m_durchschnittLT2.isVisible());
			}
		});
		
		m_speichernButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					sqlStatement(0);
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							ARTIKEL));		
					close();					
				}
			}
		});

		m_verwerfenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		m_deaktivierenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
					sqlStatement(1);
					m_okRemove = true;
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_ARTIKEL_DEAKTIVIEREN);
					close();
			}
		});

		/** Adds */		
		m_addLieferantButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addLieferant();
			}
		});
		m_addMengeneinheitButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addMengeneinheit();
			}
		});
		m_addKategorieButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addKategorie();
			}
		});
		m_addLagerortButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addLagerort();
			}
		});		
	}
	
	private void close() {
		if (ArtikelErstellen.this.getParent() instanceof Window) {					
			Window win = (Window) ArtikelErstellen.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
		}
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
	}

	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //

	private Boolean validiereEingabe() {
		/** preis */
		if (!m_preisField.getValue().isEmpty()) {
			try {
				Float.parseFloat(m_preisField.getValue());
			} catch (NumberFormatException e) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_PREIS);
				return false;
			}
		}
		
		try {
			Double.parseDouble(m_gebindeField.getValue());
		} catch (NumberFormatException e) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
			return false;
		}

		if (m_nameField.getValue() == "") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_NAME);
			return false;
		}
		
		if (m_mengeneinheitSelect.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT_B);
			return false;
		}
		
		if (m_kategorieSelect.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIE);
			return false;
		}
		
		if (!m_fuerRezepteCheckbox.getValue()) {
			if (m_gebindeField.getValue() == null
					|| Double.parseDouble(m_gebindeField.getValue().toString()) < 0.1) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
				return false;
			}
		}
		return true;

	}
	private void sqlStatement(int i) {
		try {
			if(i == 0) {
				m_artikel.setName(m_nameField.getValue());// name
				m_artikel.setArtikelnr(m_nummerField.getValue()); // nr
				m_artikel.setPreis((m_preisField.getValue() == "") ? 0F : 
						Float.parseFloat(m_preisField.getValue().replace(',', '.')));// price
				m_artikel.setNotiz(m_notizField.getValue());// Notiz
				m_artikel.setDurchschnittLT1(m_durchschnittLT1.getValue()); // GebindeAnzahl
				m_artikel.setDurchschnittLT2(m_durchschnittLT2.getValue()); // GebindeAnzahl
				m_artikel.setKategorie((Kategorie) m_kategorieSelect.getValue()); // kategorie
				m_artikel.setLieferant((Lieferant) m_lieferantSelect.getValue()); // Lieferant
				m_artikel.setLagerort((Lagerort) m_lagerortSelect.getValue());
				m_artikel.setMengeneinheit((Mengeneinheit) m_mengeneinheitSelect
						.getValue()); 
				m_artikel.setBestellgroesse(Double.parseDouble(m_gebindeField.getValue()));
				m_artikel.setGrundbedarf(m_grundbedarfCheckbox.getValue()); // grundbedarf
				m_artikel.setStandard(m_standardCheckbox.getValue());
				m_artikel.setFuerRezept(m_fuerRezepteCheckbox.getValue());				
					if (m_create) {
						Artikelverwaltung.getInstance().createArtikel(m_artikel);
					} else {
						Artikelverwaltung.getInstance().updateArtikel(m_artikel);
					}	
			} else {
					Artikelverwaltung.getInstance().deaktivireArtikel(m_artikel);	
			}
		}catch (Exception e) {
			LOG.error(e.toString());
		}
	}
	
	private void allKategories(List<Kategorie> kategorien) {
		m_kategorieSelect.removeAllItems();
		for (Kategorie e : kategorien) {
			m_kategorieSelect.addItem(e);
		}
	}
	private void allLieferanten(List<Lieferant> lieferanten) {
		m_lieferantSelect.removeAllItems();
		for (Lieferant e : lieferanten) {
			m_lieferantSelect.addItem(e);
		}
	}
	private void allMengeneinheiten(List<Mengeneinheit> mengeneinheiten) {
		m_mengeneinheitSelect.removeAllItems();
		for (Mengeneinheit e : mengeneinheiten) {
			m_mengeneinheitSelect.addItem(e);
		}
	}
	private void allLagerorte(List<Lagerort> lagerorte) {
		m_lagerortSelect.removeAllItems();
		for (Lagerort e : lagerorte) {
			m_lagerortSelect.addItem(e);
		}
	}
	
	private void load() {
		try {			
			allLieferanten(Lieferantenverwaltung.getInstance().getAllLieferanten());
			allKategories(Kategorienverwaltung.getInstance().getAllKategories());
			allMengeneinheiten(Mengeneinheitverwaltung.getInstance().getAllMengeneinheiten());
			allLagerorte(Lagerortverwaltung.getInstance().getAllLagerorts());
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}
	private void addMengeneinheit() {		
		win = windowUI(win, NEW_MENGENEINHEIT, "500", "350");
		MengeneinheitErstellen me = new MengeneinheitErstellen();
		addComponent(me);
		win.setContent(me);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				try {
					allMengeneinheiten(Mengeneinheitverwaltung.getInstance().getAllMengeneinheiten());
				} catch (Exception e1) {
					LOG.error(e.toString());
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
		win = windowUI(win, NEW_KATEGORIE, "500", "250");
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
		win = windowUI(win, NEW_LAGERORT, "500", "250");
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
	
	private VerticalLayout vLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);

		box.addComponent(m_headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(m_nameField);
		box.addComponent(m_nummerField);
		box.addComponent(m_preisField);
		box.addComponent(m_notizField);
		box.addComponent(newKomponent(m_horizontalLayout, m_lieferantSelect,
				m_addLieferantButton, OverArtikelverwaltungView.FULL));
		box.addComponent(newKomponent(m_horizontalLayout, m_kategorieSelect,
				m_addKategorieButton, OverArtikelverwaltungView.FULL));
		box.addComponent(newKomponent(m_horizontalLayout, m_lagerortSelect, m_addLagerortButton,
				OverArtikelverwaltungView.FULL));
		box.addComponent(m_standardCheckbox);
		box.addComponent(m_fuerRezepteCheckbox);
		box.addComponent(m_gebindeField);
		box.addComponent(newKomponent(m_horizontalLayout, m_mengeneinheitSelect,
				m_addMengeneinheitButton, OverArtikelverwaltungView.FULL));
		box.addComponent(m_grundbedarfCheckbox);
		box.addComponent(m_durchschnittHL);
		box.addComponent(new Hr());

		box.addComponent(m_control);
		box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);

		return box;
	}
}

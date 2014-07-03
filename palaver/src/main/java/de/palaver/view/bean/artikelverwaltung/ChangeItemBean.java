package de.palaver.view.bean.artikelverwaltung;

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
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.management.artikel.service.KategorieService;
import de.palaver.management.artikel.service.LagerortService;
import de.palaver.management.artikel.service.MengeneinheitService;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.service.SupplierService;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.lieferantenverwaltung.ChangeSupplierBean;

public class ChangeItemBean extends TemplateBuilder implements View, ValueChangeListener {
	private static final long serialVersionUID = 8763219450376751801L;
	private static final Logger LOG = LoggerFactory.getLogger(ChangeItemBean.class.getName());

	private static final String TITLE_NEW_ARTIKEL = "Neuer Artikel erstellen";
	private static final String TEXT_FIELD_ARTIKEL_NAME = "Artikelname";
	private static final String TEXT_FIELD_ARTIKEL_PREIS = "Preis";
	private static final String TEXT_FIELD_ARTIKEL_NUMMER = "Artikelnummer";
	private static final String TEXT_FIELD_ARTIKEL_GEBINDE = "Gebinde";
	private static final String TEXT_FIELD_ARTIKEL_NOTIZ = "Notiz";
	
	private Artikel m_artikel;
	private boolean m_toCreate;
	
	/** TextFields */
	private TextField m_nameField;
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
		
	private HorizontalLayout 		m_durchschnittHL;
	private HorizontalLayout 		m_control;
	
	public ChangeItemBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listener();
		loadContent();
	}
	
	public void init() {
		m_toCreate = true;
		m_artikel = new Artikel();
	} 	
	
	private void componetsManager() {
		m_headLine = title(TITLE_NEW_ARTIKEL, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_ARTIKEL_NAME, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_NAME, this);
		m_preisField = textField(TEXT_FIELD_ARTIKEL_PREIS, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_PREIS, this);
		m_nummerField = textField(TEXT_FIELD_ARTIKEL_NUMMER, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_NUMMER, this);
		m_gebindeField = textField(TEXT_FIELD_ARTIKEL_GEBINDE, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_GEBINDE, this);
		m_notizField = textField(TEXT_FIELD_ARTIKEL_NOTIZ, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_NOTIZ, this);
		
		m_lieferantSelect = nativeSelect("Lieferant", WIDTH_FULL, true, "Lieferant", this);
		m_mengeneinheitSelect = nativeSelect("Mengeneinheit", WIDTH_FULL, true, "Mengeneinheit", this);
		m_kategorieSelect = nativeSelect("Kategorie", WIDTH_FULL, true, "Kategorie", this);
		m_lagerortSelect = nativeSelect("Lagerort", WIDTH_FULL, true, "Lagerort", this);
		
		m_addLieferantButton = button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true); 
		m_addMengeneinheitButton= button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true); 
		m_addKategorieButton= button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);
		m_addLagerortButton= button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);

		/** IntSteppers */
		m_durchschnittLT1 = intStepper("Gebindeanzahl für den Termin 1",
				"98%", 0, 70, "Gebindeanzahl1");
		m_durchschnittLT2 = intStepper("Gebindeanzahl für den Termin 2",
				"98%", 0, 70, "Gebindeanzahl2");

		m_durchschnittHL = twoStepperPanel(m_durchschnittLT1, m_durchschnittLT2);
		m_control = controlPanel(this);	
	}

	private void templateBuilder() {
		setMargin(true);
		
		VerticalLayout innerBox = new VerticalLayout();		
		innerBox.setWidth("65%");
		innerBox.setSpacing(true);

		innerBox.addComponent(m_headLine);
		innerBox.addComponent(new Hr());
		innerBox.addComponent(m_nameField);
		innerBox.addComponent(m_nummerField);
		innerBox.addComponent(m_preisField);
		innerBox.addComponent(m_notizField);
		
		innerBox.addComponent(selectAndAddButtonPanel(m_lieferantSelect, m_addLieferantButton, WIDTH_FULL));
		innerBox.addComponent(selectAndAddButtonPanel(m_kategorieSelect, m_addKategorieButton, WIDTH_FULL));
		innerBox.addComponent(selectAndAddButtonPanel(m_lagerortSelect, m_addLagerortButton, WIDTH_FULL));

		innerBox.addComponent(m_standardCheckbox);
		innerBox.addComponent(m_fuerRezepteCheckbox);
		innerBox.addComponent(m_gebindeField);
		
		innerBox.addComponent(selectAndAddButtonPanel(m_mengeneinheitSelect, m_addMengeneinheitButton, WIDTH_FULL));

		innerBox.addComponent(m_grundbedarfCheckbox);
		innerBox.addComponent(m_durchschnittHL);
		innerBox.addComponent(new Hr());

		innerBox.addComponent(m_control);		
		innerBox.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(innerBox);
		setComponentAlignment(innerBox, Alignment.MIDDLE_CENTER);
	}
	
	@SuppressWarnings("serial")
	private void listener() {
		/** ValueChangeListene */
		m_grundbedarfCheckbox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				m_durchschnittLT1.setVisible(!m_durchschnittLT1.isVisible());
				m_durchschnittLT2.setVisible(!m_durchschnittLT2.isVisible());
			}
		});
		
		m_buttonSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Artikel"));		
					ViewHandler.getInstance().switchView(ShowItemsBean.class);
				}
			}

			private boolean validiereEingabe() {
				boolean isNotValid = false;
				/** preis */
				if (!m_preisField.getValue().isEmpty()) {
					try {
						Float.parseFloat(m_preisField.getValue());
					} catch (NumberFormatException e) {
						((Application) UI.getCurrent().getData())
								.showDialog(IConstants.INFO_ARTIKEL_PREIS);
						return isNotValid;
					}
				}
				
				try {
					Double.parseDouble(m_gebindeField.getValue());
				} catch (NumberFormatException e) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
					return isNotValid;
				}

				if (m_nameField.getValue() == "") {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_NAME);
					return isNotValid;
				}
				
				if (m_mengeneinheitSelect.getValue() == null) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT_B);
					return isNotValid;
				}
				
				if (m_kategorieSelect.getValue() == null) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_KATEGORIE);
					return isNotValid;
				}
				
				if (!m_fuerRezepteCheckbox.getValue()) {
					if (m_gebindeField.getValue() == null
							|| Double.parseDouble(m_gebindeField.getValue().toString()) < 0.1) {
						((Application) UI.getCurrent().getData())
								.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
						return isNotValid;
					}
				}
				return true;
			}
			
			private void saveItem() {
				m_artikel.setName(m_nameField.getValue());// name
				m_artikel.setArtikelnr(m_nummerField.getValue()); // nr
				m_artikel.setPreis((m_preisField.getValue() == "") ? 0F : 
						Float.parseFloat(m_preisField.getValue().replace(',', '.')));// price
				m_artikel.setNotiz(m_notizField.getValue());// Notiz
				m_artikel.setDurchschnittLT1(m_durchschnittLT1.getValue()); // GebindeAnzahl
				m_artikel.setDurchschnittLT2(m_durchschnittLT2.getValue()); // GebindeAnzahl
				m_artikel.setKategorie((Kategorie) m_kategorieSelect.getValue()); // kategorie
				m_artikel.setLieferant((Supplier) m_lieferantSelect.getValue()); // Lieferant
				m_artikel.setLagerort((Lagerort) m_lagerortSelect.getValue());
				m_artikel.setMengeneinheit((Mengeneinheit) m_mengeneinheitSelect
						.getValue()); 
				m_artikel.setBestellgroesse(Double.parseDouble(m_gebindeField.getValue()));
				m_artikel.setGrundbedarf(m_grundbedarfCheckbox.getValue()); // grundbedarf
				m_artikel.setStandard(m_standardCheckbox.getValue());
				m_artikel.setFuerRezept(m_fuerRezepteCheckbox.getValue());				
				
				try {
					if (m_toCreate) {
						ArtikelService.getInstance().createArtikel(m_artikel);
					} else {
						ArtikelService.getInstance().updateArtikel(m_artikel);
					}
				} catch (Exception e) {
					LOG.error(e.toString());
				}
			}
		});

		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ShowItemsBean.class);
			}
		});
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: visible = false --> implementieren!
				//sehe m_control
			}
		});

		/** Adds */		
		m_addLieferantButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeSupplierBean());
			}
		});
		m_addMengeneinheitButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeQuantityUnitBean());
			}
		});
		m_addKategorieButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeKategoryBean());
			}
		});
		m_addLagerortButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeWarehouseBean());
			}
		});			
	}
		
	private void loadContent() {
		Object[] objects = {new ChangeSupplierBean(), new ChangeQuantityUnitBean(), 
				new ChangeWarehouseBean(), new ChangeKategoryBean()};
		
		for (Object object : objects) {
			loadAllForSelectFactory(object);
		}
	} 
	
	private void getWindowFactory(final Object object) {	
		if(object instanceof ChangeSupplierBean) {
			addComponent((ChangeSupplierBean) object);
			m_window = windowUI(CREATE_NEW_LIEFERANT, true, false, "950", "500");
			m_window.setContent((ChangeSupplierBean) object);			
		} 
		if(object instanceof ChangeQuantityUnitBean) {
			addComponent((ChangeQuantityUnitBean) object);
			m_window = windowUI(CREATE_NEW_MENGENEINHEIT, true, false, "500", "350");
			m_window.setContent((ChangeQuantityUnitBean) object);			
		} 
		if(object instanceof ChangeKategoryBean) {
			addComponent((ChangeKategoryBean) object);
			m_window = windowUI(CREATE_NEW_KATEGORIE, true, false, "500", "250");
			m_window.setContent((ChangeKategoryBean) object);
		}
		if(object instanceof ChangeWarehouseBean) {
			addComponent((ChangeWarehouseBean) object);
			m_window = windowUI(CREATE_NEW_LAGERORT, true, false, "500", "250");
			m_window.setContent((ChangeWarehouseBean) object);
		}
		
		UI.getCurrent().addWindow(m_window);
		m_window.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				loadAllForSelectFactory(object);
			}
		});
	}
	
	private void loadAllForSelectFactory(Object object) {
		try {
			if(object instanceof ChangeSupplierBean) { 
				m_lieferantSelect.removeAllItems();			
				for (Supplier supplier : SupplierService.getInstance().getAllLieferanten()) {
					m_lieferantSelect.addItem(supplier);
				}
				 
			}
			if(object instanceof ChangeQuantityUnitBean) { 
				m_mengeneinheitSelect.removeAllItems();			
				for (Mengeneinheit mengeneinheit : MengeneinheitService.getInstance().getAllMengeneinheiten()) {
					m_mengeneinheitSelect.addItem(mengeneinheit);
				}
				 
			}
			if(object instanceof ChangeKategoryBean) { 
				m_kategorieSelect.removeAllItems();
				for (Kategorie kategorie : KategorieService.getInstance().getAllKategories()) {
					m_kategorieSelect.addItem(kategorie);
				}
			}
			if(object instanceof ChangeWarehouseBean) { 
				m_lagerortSelect.removeAllItems();
				for (Lagerort lagerort : LagerortService.getInstance().getAllLagerorts()) {
					m_lagerortSelect.addItem(lagerort);
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	

	@Override
	public void getViewParam(ViewData data) {		
		if(((ViewDataObject<?>) data).getData() instanceof Artikel) {
			m_artikel = (Artikel)((ViewDataObject<?>) data).getData();
			
			m_nameField.setValue(m_artikel.getName());
			m_nummerField.setValue(m_artikel.getArtikelnr());
			m_preisField.setValue(String.valueOf(m_artikel.getPreis()));
			m_lieferantSelect.select(m_artikel.getLieferant());
			m_kategorieSelect.select(m_artikel.getKategorie());
			m_lagerortSelect.select(m_artikel.getLagerort());
			m_fuerRezepteCheckbox.setValue(m_artikel.isFuerRezept());
			m_gebindeField.setValue(String.valueOf(m_artikel.getBestellgroesse()));
			m_standardCheckbox.setValue(m_artikel.isStandard());
			m_grundbedarfCheckbox.setValue(m_artikel.isGrundbedarf());
			m_durchschnittLT1.setValue(m_artikel.getDurchschnittLT1());
			m_durchschnittLT2.setValue(m_artikel.getDurchschnittLT2());
			m_notizField.setValue(m_artikel.getNotiz());
			m_mengeneinheitSelect.select(m_artikel.getMengeneinheit());
			
			m_toCreate = false;
		} 
	}


	@Override
	public void valueChange(ValueChangeEvent event) {
			
	}
}

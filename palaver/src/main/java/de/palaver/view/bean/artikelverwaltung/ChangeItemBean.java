package de.palaver.view.bean.artikelverwaltung;

import org.apache.commons.lang.StringUtils;
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

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
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

	private static final String TITLE_NEW_ARTIKEL = "Neuer Artikel erstellen";
	private static final String TEXT_FIELD_ARTIKEL_NAME = "Artikelname";
	private static final String TEXT_FIELD_ARTIKEL_PREIS = "Preis";
	private static final String TEXT_FIELD_ARTIKEL_NUMMER = "Artikelnummer";
	private static final String TEXT_FIELD_ARTIKEL_GEBINDE = "Gebinde";
	private static final String TEXT_FIELD_ARTIKEL_NOTIZ = "Notiz";
	private static final String TEXT_FIELD_ARTIKEL_MENGENEINHEIT = "Mengeneinheit";
	private static final String TEXT_FIELD_ARTIKEL_LIEFERANT = "Lieferant";
	private static final String TEXT_FIELD_ARTIKEL_KATEGORIE = "Kategorie";
	private static final String TEXT_FIELD_ARTIKEL_LAGERORT = "Lagerort";
	
	private Artikel m_artikel;
	public Artikel getArtikel() { return m_artikel; }
	private boolean m_toCreate;
	
	/** TextFields */
	private TextField m_nameField;
	private TextField m_preisField;
	private TextField m_numberField;
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
		clickListener();
		changeListner();
		loadContentNativeSelect();
	}

	public void init() {
		m_toCreate = true;
		m_artikel = new Artikel();
	} 	
	
	private void componetsManager() {
		m_headLine = title(TITLE_NEW_ARTIKEL, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_ARTIKEL_NAME, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_NAME, 45);
		m_preisField = textField(TEXT_FIELD_ARTIKEL_PREIS, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_PREIS, 10);
		m_numberField = textField(TEXT_FIELD_ARTIKEL_NUMMER, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_NUMMER, 45);
		m_gebindeField = textField(TEXT_FIELD_ARTIKEL_GEBINDE, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_GEBINDE, 17);
		m_notizField = textField(TEXT_FIELD_ARTIKEL_NOTIZ, WIDTH_FULL, false, TEXT_FIELD_ARTIKEL_NOTIZ, 90);

		
		m_lieferantSelect = nativeSelect(TEXT_FIELD_ARTIKEL_LIEFERANT, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_LIEFERANT, this);
		m_mengeneinheitSelect = nativeSelect(TEXT_FIELD_ARTIKEL_MENGENEINHEIT, WIDTH_FULL, true, 
				TEXT_FIELD_ARTIKEL_MENGENEINHEIT, this);
		m_kategorieSelect = nativeSelect(TEXT_FIELD_ARTIKEL_KATEGORIE, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_KATEGORIE, this);
		m_lagerortSelect = nativeSelect(TEXT_FIELD_ARTIKEL_LAGERORT, WIDTH_FULL, true, TEXT_FIELD_ARTIKEL_LAGERORT, this);
		
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
		innerBox.addComponent(m_numberField);
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
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					close();
				}
			}

			private boolean validiereEingabe() {
				boolean isValid = true;
				if (StringUtils.isBlank(m_nameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_NAME);
					isValid = false;
				} else if (m_mengeneinheitSelect.getValue() == null) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_MENGENEINHEIT);
					isValid = false;
				} else if (m_kategorieSelect.getValue() == null) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_KATEGORIE);
					isValid = false;
				} else if (m_lieferantSelect.getValue() == null) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_LIEFERANT);
					isValid = false;
				} else if (m_lagerortSelect.getValue() == null) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_LAGERORT);
					isValid = false;
				} else if (!m_fuerRezepteCheckbox.getValue()) {
					if (StringUtils.isBlank(m_gebindeField.getValue())) {
						message(MESSAGE_LEER_ARG_1, TEXT_FIELD_ARTIKEL_GEBINDE);
						isValid = false;
					}
				}
				return isValid;
			}
			
			private void saveItem() {
				try {
					if (m_toCreate) {
						ArtikelService.getInstance().createArtikel(m_artikel);		
						close();					
					} else {
						ArtikelService.getInstance().updateArtikel(m_artikel);
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Artikel");	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_artikel = null;
				close();
			}
		});
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModalYesNoRemove(m_artikel);	
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
	
	@SuppressWarnings("serial")
	private void changeListner() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setName((String) event.getProperty().getValue());
			}
		});
		m_numberField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setArtikelnr((String) event.getProperty().getValue());
			}
		});
		m_preisField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					m_artikel.setPreis(Float.parseFloat(((String) event.getProperty().getValue()).replace(',', '.')));
				} catch (NumberFormatException nfe) {
			    	message(MESSAGE_NUMBER_FORMAT, "0-9.");
			    	if (m_artikel.getPreis() != null) {
			    		m_preisField.setValue(String.valueOf(m_artikel.getPreis())); 
			    	} else {
			    		m_preisField.setValue(StringUtils.EMPTY);
			    	}
			    }	
			}
		});
		m_notizField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setNotiz((String) event.getProperty().getValue());
			}
		});		
		m_durchschnittLT1.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setDurchschnittLT1((Integer) event.getProperty().getValue());
			}
		});
		m_durchschnittLT2.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setDurchschnittLT2((Integer) event.getProperty().getValue());
			}
		});
		m_kategorieSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setKategorie((Kategorie) event.getProperty().getValue());
			}
		});
		m_mengeneinheitSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setMengeneinheit((Mengeneinheit) event.getProperty().getValue());
			}
		});
		m_lagerortSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setLagerort((Lagerort) event.getProperty().getValue());
			}
		});
		m_lieferantSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setLieferant((Supplier) event.getProperty().getValue());
			}
		});		
		m_gebindeField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					m_artikel.setBestellgroesse(Double.parseDouble(((String) event.getProperty().getValue()).replace(',', '.')));
				} catch (NumberFormatException nfe) {
			    	message(MESSAGE_NUMBER_FORMAT, "0-9.");
			    	if (m_artikel.getBestellgroesse() != null) {
			    		m_gebindeField.setValue(String.valueOf(m_artikel.getBestellgroesse())); 
			    	} else {
			    		m_gebindeField.setValue(StringUtils.EMPTY);
			    	}
			    }	
			}
		});
		m_grundbedarfCheckbox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				m_artikel.setGrundbedarf((Boolean) event.getProperty().getValue());
				m_durchschnittLT1.setVisible(!m_durchschnittLT1.isVisible());
				m_durchschnittLT2.setVisible(!m_durchschnittLT2.isVisible());
			}
		});
		m_standardCheckbox.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setStandard((Boolean) event.getProperty().getValue());
			}
		});
		
		m_fuerRezepteCheckbox.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_artikel.setFuerRezept((Boolean) event.getProperty().getValue());
			}
		});
	}
	
	private void close() {
		if (ChangeItemBean.this.getParent() instanceof Window) {	
			((Window) ChangeItemBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowItemsBean.class);
		}
	}	
			
	private void loadContentNativeSelect() {
		Object[] objects = {new ChangeSupplierBean(), new ChangeQuantityUnitBean(), 
				new ChangeWarehouseBean(), new ChangeKategoryBean()};
		
		for (Object object : objects) {
			loadAllForSelectFactory(object);
		}
	} 
	
	@SuppressWarnings("serial")
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
			e.printStackTrace();
		}
	}

	@Override
	public void getViewParam(ViewData data) {		
		if(((ViewDataObject<?>) data).getData() instanceof Artikel) {
			m_artikel = (Artikel)((ViewDataObject<?>) data).getData();
			setNewInfo();			
			setValueToComponent(getData());	
		} 
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_artikel.getName());
		getData().put(m_numberField, m_artikel.getArtikelnr());
		getData().put(m_preisField, m_artikel.getPreis());
		getData().put(m_mengeneinheitSelect, m_artikel.getMengeneinheit());
		getData().put(m_lieferantSelect, m_artikel.getLieferant());
		getData().put(m_kategorieSelect, m_artikel.getKategorie());
		getData().put(m_lagerortSelect, m_artikel.getLagerort());
		getData().put(m_fuerRezepteCheckbox, m_artikel.isFuerRezept());
		getData().put(m_gebindeField, m_artikel.getBestellgroesse());
		getData().put(m_standardCheckbox, m_artikel.isStandard());
		getData().put(m_grundbedarfCheckbox, m_artikel.isGrundbedarf());
		getData().put(m_durchschnittLT1, m_artikel.getDurchschnittLT1());
		getData().put(m_durchschnittLT2, m_artikel.getDurchschnittLT2());
		getData().put(m_notizField, m_artikel.getNotiz());

		m_toCreate = false;		
		m_headLine.setValue("Artikel bearbeiten");
		m_buttonDeaktiviren.setVisible(true);		
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }
}

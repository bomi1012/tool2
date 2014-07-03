package de.palaver.view.bean.lieferantenverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.info.person.Adresse;
import de.palaver.management.info.person.Kontakte;
import de.palaver.management.supplier.Ansprechpartner;
import de.palaver.management.supplier.Supplier;
import de.palaver.view.bean.helpers.HTMLComponents;

public class ChangeContactPersonBean extends ChangeFieldsAbstract implements View, ValueChangeListener {
	private static final long serialVersionUID = 6321945037675101L;
	private boolean m_toCreate;	
	private VerticalLayout m_innerBoxContactPerson;
		
	public ChangeContactPersonBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listeners();
	}
	
	
	private void init() {
		m_toCreate = true;
		m_contactPerson = new Ansprechpartner();
		m_supplier = new Supplier();
	}


	private void componetsManager() {
		m_headLine = title("Neuen Ansprechpartner anlegen", STYLE_HEADLINE_STANDART);
		
		m_subHeadInformation = title("Information", STYLE_HEADLINE_SUB);
		m_nameField = textField("Ansprechpartnername", WIDTH_FULL, true, "Ansprechpartnername", this);
		m_descriptionField = textField("Bezeichnung", WIDTH_FULL, false, "Bezeichnung", this);
		
		getContactDataDefinition();
		getAddressDataDefinition();	
		
		m_leftVLayout = vertikalLayoutBuilder(3, "90%");		
		m_centerVLayout = vertikalLayoutBuilder(1, "90%");
		m_rightVLayout = vertikalLayoutBuilder(2, "90%");
		
		m_table = HTMLComponents.table(true, true, "100%", null);
		m_control = controlPanel(this);	
	}


	private void templateBuilder() {
		m_innerBoxContactPerson = new VerticalLayout();		
		m_innerBoxContactPerson.setWidth(WIDTH_FULL);
		m_innerBoxContactPerson.setSpacing(true);
		m_innerBoxContactPerson.setMargin(true);		
		
		m_innerBoxContactPerson.addComponent(m_headLine);
		m_innerBoxContactPerson.addComponent(new Hr());
		m_innerBoxContactPerson.addComponent(horizontalLayoutBuilder());	
						
		addComponent(m_innerBoxContactPerson);
		addComponent(m_control);
		setComponentAlignment(m_innerBoxContactPerson, Alignment.MIDDLE_CENTER);		
		setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
	}


	@SuppressWarnings("serial")
	private void listeners() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -835805357073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();
					ViewHandler.getInstance()
						.switchView(ChangeSupplierBean.class, new ViewDataObject<Supplier>(m_supplier));
				}
			}
			private void saveItem() {
				if (m_toCreate) {
					addToDB(new Kontakte());
					addToDB(new Adresse());
					addToDB(new Ansprechpartner());
				} else {
					if (m_contactPerson.getKontakte() != null) {
						if (!checkFields(m_contactPerson.getKontakte())) {
							removeFromDB(m_contactPerson.getKontakte());	
							m_contactPerson.setKontakte(null);
						} else {
							updateInDB(m_contactPerson.getKontakte());							
						}
					} else {
						addToDB(new Kontakte());
						m_contactPerson.setKontakte(m_kontakte);
					}
					
					if (m_contactPerson.getAdresse() != null) {
						if (!checkFields(m_contactPerson.getAdresse())) {
							removeFromDB(m_contactPerson.getAdresse());	
							m_contactPerson.setAdresse(null);
						} else {
							updateInDB(m_contactPerson.getAdresse());							
						}
					} else {
						addToDB(new Adresse());
						m_contactPerson.setAdresse(m_adresse);
					}					
					updateInDB(m_contactPerson);
				}
			}
			private boolean validiereEingabe() {
				boolean isValid = true;
				if (m_nameField.getValue().equals("")) {
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_LEER_ARG_1, "Name"));
					isValid = false;
				}
				return isValid;
			}
		});
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance()
				.switchView(ChangeSupplierBean.class, new ViewDataObject<Supplier>(m_supplier));
			}
		});	
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: visible = false --> implementieren!
				//sehe m_control
			}
		});		
	}


	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Supplier) { 
			m_supplier = (Supplier)((ViewDataObject<?>) data).getData();
		} 
		
		if(((ViewDataObject<?>) data).getData() instanceof Ansprechpartner) { 
			m_contactPerson = (Ansprechpartner)((ViewDataObject<?>) data).getData();
			
			if (((ViewDataObject<?>) data).getObject() != null &&
					((ViewDataObject<?>) data).getObject() instanceof Supplier) {
				m_supplier = (Supplier) ((ViewDataObject<?>) data).getObject();
			}
			
			m_nameField.setValue(m_contactPerson.getName());
			m_descriptionField.setValue(m_contactPerson.getBezeichnung());
			
			if(m_contactPerson.getKontakte() != null) {
				m_telephonField.setValue(m_contactPerson.getKontakte().getTelefon());
				m_handyField.setValue(m_contactPerson.getKontakte().getHandy());
				m_faxField.setValue(m_contactPerson.getKontakte().getFax());
				m_emailField.setValue(m_contactPerson.getKontakte().getEmail());
				m_webField.setValue(m_contactPerson.getKontakte().getWww());
			}
			
			if(m_contactPerson.getAdresse() != null) {
				m_streetField.setValue(m_contactPerson.getAdresse().getStrasse());
				m_housenumberField.setValue(m_contactPerson.getAdresse().getHausnummer());
				m_cityField.setValue(m_contactPerson.getAdresse().getStadt());
				m_plzField.setValue(m_contactPerson.getAdresse().getPlz());
				m_countryField.setValue(m_contactPerson.getAdresse().getLand());
			}
			m_toCreate = false;
		}
	}
}

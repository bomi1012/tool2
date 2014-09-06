package de.palaver.view.bean.lieferantenverwaltung;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.management.info.person.Adresse;
import de.palaver.management.info.person.Kontakte;
import de.palaver.management.supplier.Ansprechpartner;
import de.palaver.management.supplier.Supplier;
import de.palaver.view.bean.util.ChangeFieldsPersonAbstract;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.interfaces.IChangeViewPage;

public class ChangeContactPersonBean extends ChangeFieldsPersonAbstract implements View, ValueChangeListener, IChangeViewPage {
	private static final long serialVersionUID = 6321945037675101L;
	private boolean m_toCreate;	
	private VerticalLayout m_innerBoxContactPerson;
	private static final String TEXT_FIELD_AP_NAME = "Ansprechpartnername";
	public ChangeContactPersonBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListner();
	}
	
	
	private void init() {
		resetMarkAsChange();
		m_toCreate = true;
		m_contactPerson = new Ansprechpartner(new Adresse(), new Kontakte(), m_supplier);
	}

	private void componetsManager() {
		m_headLine = title("Neuen Ansprechpartner anlegen", STYLE_HEADLINE_STANDART);
		
		m_subHeadInformation = title("Information", STYLE_HEADLINE_SUB);
		m_nameField = textField("Ansprechpartnername", WIDTH_FULL, true, "Ansprechpartnername", 45);
		m_descriptionField = textField("Bezeichnung", WIDTH_FULL, false, "Bezeichnung", 100);
		
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
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -835805357073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();
				}
			}		
			
			private boolean validiereEingabe() {
				if (StringUtils.isBlank(m_nameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_AP_NAME);
					return false;
				}
				return true;
			}
			
			private void saveItem() {
				if (m_toCreate) {
					addToDB(m_contactPerson.getAdresse(), 1);
					addToDB(m_contactPerson.getKontakt(), 1);
					addToDB(m_contactPerson, 1);
				} else {
					if (m_contactPerson.getKontakt().getId() != null) {
						if (!checkFields(m_contactPerson.getKontakt())) {
							removeFromDB(m_contactPerson.getKontakt());	
							m_contactPerson.setKontakt(new Kontakte());
						} else {
							updateInDB(m_contactPerson.getKontakt());							
						}
					} else {
						addToDB(m_contactPerson.getKontakt(), 1);
					}
					
					if (m_contactPerson.getAdresse().getId() != null) {
						if (!checkFields(m_contactPerson.getAdresse())) {
							removeFromDB(m_contactPerson.getAdresse());	
							m_contactPerson.setAdresse(new Adresse());
						} else {
							updateInDB(m_contactPerson.getAdresse());							
						}
					} else {
						addToDB(m_contactPerson.getAdresse(), 1);
					}					
					updateInDB(m_contactPerson);
				}
				close();
			}
		});
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});	
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModalYesNoRemove(m_contactPerson);	
			}
		});		
	}
	
	protected void close() {
		if (ChangeContactPersonBean.this.getParent() instanceof Window) {
			((Window) ChangeContactPersonBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ChangeSupplierBean.class, new ViewDataObject<Supplier>(m_supplier));
		}		
	}


	@SuppressWarnings("serial")
	private void changeListner() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.setName((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_descriptionField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.setBezeichnung((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_telephonField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getKontakt().setTelefon((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_handyField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getKontakt().setHandy((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_faxField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getKontakt().setFax((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_emailField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getKontakt().setEmail((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_webField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getKontakt().setWww((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_streetField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getAdresse().setStrasse((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_housenumberField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getAdresse().setHausnummer((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_cityField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getAdresse().setStadt((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_plzField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getAdresse().setPlz((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_countryField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_contactPerson.getAdresse().setLand((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
	}	

	@Override
	public void valueChange(ValueChangeEvent event) { }
	
	@Override
	public void getViewParam(IViewData data) {
		resetMarkAsChange();
		if(((ViewDataObject<?>) data).getData() instanceof Supplier) { 
			m_supplier = (Supplier)((ViewDataObject<?>) data).getData();
			init();
		} 
		
		if(((ViewDataObject<?>) data).getData() instanceof Ansprechpartner) { 			
			m_contactPerson = (Ansprechpartner)((ViewDataObject<?>) data).getData();
			
			if (((ViewDataObject<?>) data).getObject() != null &&
					((ViewDataObject<?>) data).getObject() instanceof Supplier) {
				m_supplier = (Supplier) ((ViewDataObject<?>) data).getObject();
				m_contactPerson.setLieferant(m_supplier);
			}
			
			setNewInfo();			
			setValueToComponent(getData());
		}
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_contactPerson.getName());
		getData().put(m_descriptionField, m_contactPerson.getBezeichnung());
		
		if (m_contactPerson.getKontakt() != null) {
			getData().put(m_telephonField, m_contactPerson.getKontakt().getTelefon());
			getData().put(m_handyField, m_contactPerson.getKontakt().getHandy());
			getData().put(m_faxField, m_contactPerson.getKontakt().getFax());
			getData().put(m_emailField, m_contactPerson.getKontakt().getEmail());
			getData().put(m_webField, m_contactPerson.getKontakt().getWww());
		} else {
			m_contactPerson.setKontakt(new Kontakte());
		}
		
		if (m_contactPerson.getAdresse() != null) {
			getData().put(m_streetField, m_contactPerson.getAdresse().getStrasse());
			getData().put(m_housenumberField, m_contactPerson.getAdresse().getHausnummer());
			getData().put(m_cityField, m_contactPerson.getAdresse().getStadt());
			getData().put(m_plzField, m_contactPerson.getAdresse().getPlz());
			getData().put(m_countryField, m_contactPerson.getAdresse().getLand());
		} else {
			m_contactPerson.setAdresse(new Adresse());
		}		
		
		m_toCreate = false;		
		m_headLine.setValue("Ansprechpartner bearbeiten");
		m_buttonDeaktiviren.setVisible(true);
	}
}

package de.palaver.view.bean.helpers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.palaver.management.info.person.Adresse;
import de.palaver.management.info.person.Kontakte;
import de.palaver.management.info.person.service.AdresseService;
import de.palaver.management.info.person.service.KontakteService;
import de.palaver.management.supplier.Ansprechpartner;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.service.AnsprechpartnerService;
import de.palaver.management.supplier.service.SupplierService;

@SuppressWarnings("serial")
abstract public class ChangeFieldsPersonAbstract extends TemplateBuilder{

	protected static final Logger LOG = LoggerFactory.getLogger(ChangeFieldsPersonAbstract.class.getName());
	
	protected Kontakte m_kontakte;
	protected Adresse m_adresse;
	protected Supplier m_supplier;
	protected Ansprechpartner m_contactPerson;
	
	protected VerticalLayout m_leftVLayout;
	protected VerticalLayout m_centerVLayout;
	protected VerticalLayout m_rightVLayout;
	
	protected Label m_subHeadInformation;
	protected Label m_subHeadPersonDaten;
	protected TextField m_nameField;
	protected TextField m_numberField;
	protected TextField m_descriptionField;
	protected TextArea m_commentField;
	protected Label m_subHeadKontaktDaten;
	protected TextField m_telephonField;
	protected TextField m_handyField;
	protected TextField m_faxField;
	protected TextField m_emailField;
	protected TextField m_webField;
	protected Label m_subHeadAdressDaten;
	protected TextField m_streetField;
	protected TextField m_housenumberField;
	protected TextField m_cityField;
	protected TextField m_plzField;
	protected TextField m_countryField;
	protected Button m_changePassword;
	protected TextField m_vornameField;
	protected TextField m_usernameField;
	protected PasswordField m_passwordField;
	protected TextField m_eintrittsdatumField;
	protected TextField m_austrittsdatumField;
	
	protected CheckBox m_mehrerLieferterminCheckbox = new CheckBox("mehrere Liefertermine");
	protected TwinColSelect m_rollenColSelect = new TwinColSelect();

	protected Component m_subHeadNewDaten;
	
	protected void getContactDataDefinition() {
		m_subHeadKontaktDaten = title("Kontaktdaten", STYLE_HEADLINE_SUB);
		m_telephonField = textField("Telefonnummer", WIDTH_FULL, false, "Telefonnummer", 0);
		m_handyField = textField("Handynummer", WIDTH_FULL, false, "Handynummer", 0);		
		m_faxField = textField("Fax", WIDTH_FULL, false, "Fax", 0);
		m_emailField = textField("E-Mail", WIDTH_FULL, false, "E-Mail", 0);
		m_webField = textField("Web", WIDTH_FULL, false, "Web", 0);
	}
	
	protected void getAddressDataDefinition() {		
		m_subHeadAdressDaten = title("Adresse", STYLE_HEADLINE_SUB);
		m_streetField = textField("Straﬂe", WIDTH_FULL, false, "Straﬂe", 0);
		m_housenumberField = textField("Housenummer", WIDTH_FULL, false, "Housenummer", 0);
		m_cityField = textField("Stadt", WIDTH_FULL, false, "Stadt", 0);
		m_plzField = textField("PLZ", WIDTH_FULL, false, "PLZ", 0);
		m_countryField = textField("Land", WIDTH_FULL, false, "Land", 0);
	}
	
	protected VerticalLayout vertikalLayoutBuilder(int order, String width) {
		VerticalLayout vl = new VerticalLayout();
		vl.setWidth(width);
		vl.setSpacing(true);
		switch (order) {
		case 0:
			vl.addComponent(m_subHeadPersonDaten);
			vl.addComponent(new Hr());
			vl.addComponent(m_nameField);
			vl.addComponent(m_numberField);
			vl.addComponent(m_descriptionField);
			vl.addComponent(m_commentField);
			vl.addComponent(m_mehrerLieferterminCheckbox);
			break;
		case 1:
			vl.addComponent(m_subHeadKontaktDaten);
			vl.addComponent(new Hr());
			vl.addComponent(m_telephonField);
			vl.addComponent(m_handyField);
			vl.addComponent(m_faxField);
			vl.addComponent(m_emailField);
			vl.addComponent(m_webField);
			break;
		case 2:
			vl.addComponent(m_subHeadAdressDaten);
			vl.addComponent(new Hr());
			vl.addComponent(m_streetField);
			vl.addComponent(m_housenumberField);
			vl.addComponent(m_cityField);
			vl.addComponent(m_plzField);
			vl.addComponent(m_countryField);
			break;
		case 3:
			vl.addComponent(m_subHeadInformation);
			vl.addComponent(new Hr());
			vl.addComponent(m_nameField);
			vl.addComponent(m_descriptionField);
			break;
		case 4:
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			horizontalLayout.setWidth(WIDTH_FULL);
			horizontalLayout.addComponent(m_passwordField);
			horizontalLayout.addComponent(m_changePassword);
			horizontalLayout.setExpandRatio(m_passwordField, 2);
			horizontalLayout.setExpandRatio(m_changePassword, 2);
			
			vl.addComponent(m_subHeadPersonDaten);
			vl.addComponent(new Hr());
			vl.addComponent(m_nameField);
			vl.addComponent(m_vornameField);
			vl.addComponent(m_usernameField);
			vl.addComponent(horizontalLayout);
			break;
		case 5:
			vl.addComponent(m_subHeadNewDaten);
			vl.addComponent(new Hr());
			vl.addComponent(m_eintrittsdatumField);
			vl.addComponent(m_austrittsdatumField);
			vl.addComponent(new Label());
			vl.addComponent(m_rollenColSelect);
		}
		return vl;		
	}
	
	protected HorizontalLayout horizontalLayoutBuilder() {
		HorizontalLayout windowHLayout = new HorizontalLayout();
		windowHLayout.setSizeFull();
		windowHLayout.setMargin(true);
		
		windowHLayout.addComponent(m_leftVLayout);
		windowHLayout.addComponent(m_centerVLayout);
		windowHLayout.addComponent(m_rightVLayout);

		windowHLayout.setExpandRatio(m_leftVLayout, 1);
		windowHLayout.setExpandRatio(m_centerVLayout, 1);
		windowHLayout.setExpandRatio(m_rightVLayout, 1);

		windowHLayout.setComponentAlignment(m_leftVLayout, Alignment.TOP_LEFT);
		windowHLayout.setComponentAlignment(m_centerVLayout, Alignment.TOP_CENTER);
		windowHLayout.setComponentAlignment(m_rightVLayout, Alignment.TOP_RIGHT);		
		
		return windowHLayout;
	}
	
	protected void addToDB(Object obj) {
		if (obj instanceof Kontakte) {
			m_kontakte = null;
			if (checkFields(obj)) {
				m_kontakte = new Kontakte(
						m_emailField.getValue(), m_handyField.getValue(), m_telephonField.getValue(),
						m_faxField.getValue(), m_webField.getValue());
				try {
					m_kontakte.setId(KontakteService.getInstance().createKontakte(m_kontakte));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 

		if (obj instanceof Adresse) {
			m_adresse = null;
			if (checkFields(obj)) {
				m_adresse = new Adresse(
						m_streetField.getValue(), m_housenumberField.getValue(), 
						m_cityField.getValue(), m_plzField.getValue(), m_countryField.getValue());
				try {
					m_adresse.setId(AdresseService.getInstance().createAdresse(m_adresse));
				} catch (Exception e) {
					LOG.error("Adresse_ERROR_Create: " + e.toString());
				}
			}
		}	
		
		if (obj instanceof Supplier) {
			m_supplier = new Supplier( 
					m_nameField.getValue(), m_numberField.getValue(),
					m_descriptionField.getValue(), m_mehrerLieferterminCheckbox.getValue(), 
					m_commentField.getValue(), m_adresse, m_kontakte);
			try {
				m_supplier.setId(SupplierService.getInstance().createLieferant(m_supplier));
			} catch (Exception e) {
				LOG.error("Lieferant_ERROR_Create: " + e.toString());
			}
		}
		
		if (obj instanceof Ansprechpartner) {
			m_contactPerson = new Ansprechpartner(
					m_nameField.getValue(), m_supplier, 
					m_descriptionField.getValue(), m_adresse, m_kontakte);
			try {
				m_contactPerson.setId(AnsprechpartnerService.getInstance().createAnsprechpartner(m_contactPerson));
			} catch (Exception e) {
				LOG.error("Ansprechpartner_ERROR_Create: " + e.toString());
			}
		}
	}
	
	protected void removeFromDB(Object obj) {
		if (obj instanceof Kontakte) { 
			try {
				KontakteService.getInstance().deleteKontakte(((Kontakte) obj).getId());
			} catch (Exception e) {
				LOG.error("Kontakt_ERROR_Remove: " + e.toString());
			}
		}
		if (obj instanceof Adresse) { 
			try {
				AdresseService.getInstance().deleteAdresse(((Adresse) obj).getId());
			} catch (Exception e) {
				LOG.error("Adresse_ERROR_Remove: " + e.toString());
			}
		}
	}
	
	protected void updateInDB(Object obj) {
		if (obj instanceof Kontakte) { 
			((Kontakte) obj).setTelefon(m_telephonField.getValue());
			((Kontakte) obj).setHandy(m_handyField.getValue()); 
			((Kontakte) obj).setFax(m_faxField.getValue());
			((Kontakte) obj).setEmail(m_emailField.getValue());
			((Kontakte) obj).setWww(m_webField.getValue());
			try {
				KontakteService.getInstance().updatekontakte((Kontakte) obj); }
			catch (Exception e) {
				LOG.error("Kontakt_ERROR_Update: " + e.toString());
			}
		}
		if (obj instanceof Adresse) { 
			((Adresse) obj).setStrasse(m_streetField.getValue());
			((Adresse) obj).setHausnummer(m_housenumberField.getValue()); 
			((Adresse) obj).setStadt(m_cityField.getValue());
			((Adresse) obj).setPlz(m_plzField.getValue());
			((Adresse) obj).setLand(m_countryField.getValue());
			try {
				AdresseService.getInstance().updateAdresse((Adresse) obj);}
			catch (Exception e) {
				LOG.error("Adresse_ERROR_Update: " + e.toString());
			}
		}		
		if(obj instanceof Supplier) {
			try {
				((Supplier) obj).setName(m_nameField.getValue());
				((Supplier) obj).setBezeichnung(m_descriptionField.getValue());
				((Supplier) obj).setLieferantnummer(m_numberField.getValue());
				((Supplier) obj).setMehrereliefertermine(m_mehrerLieferterminCheckbox.getValue());
				((Supplier) obj).setNotiz(m_commentField.getValue());
				SupplierService.getInstance().updateLieferant((Supplier) obj);
			} catch (Exception e) {
				LOG.error("Lieferant_ERROR_Update: " + e.toString());
			}
		}		
		if(obj instanceof Ansprechpartner) {
			try {
				((Ansprechpartner) obj).setName(m_nameField.getValue());
				((Ansprechpartner) obj).setBezeichnung(m_descriptionField.getValue());
				AnsprechpartnerService.getInstance().updateAnsprechpartner((Ansprechpartner) obj);
			} catch (Exception e) {
				LOG.error("Ansprechpartner_ERROR_Update: " + e.toString());
			}
		} 
	}
	
	protected boolean checkFields(Object obj) {
		if (obj instanceof Kontakte) {
			if (	StringUtils.isNotBlank(m_telephonField.getValue()) || 
					StringUtils.isNotBlank(m_handyField.getValue()) || 
					StringUtils.isNotBlank(m_faxField.getValue()) || 
					StringUtils.isNotBlank(m_emailField.getValue()) || 
					StringUtils.isNotBlank(m_webField.getValue()))  {
				return true;
			}
		} 

		if (obj instanceof Adresse) {
			if (	StringUtils.isNotBlank(m_streetField.getValue()) || 
					StringUtils.isNotBlank(m_housenumberField.getValue()) || 
					StringUtils.isNotBlank(m_cityField.getValue()) || 
					StringUtils.isNotBlank(m_plzField.getValue()) || 
					StringUtils.isNotBlank(m_countryField.getValue())) {
				return true;
			}
		}		
		return false;
	}
}

package de.palaver.view.bean.util;

import org.apache.commons.lang.StringUtils;

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

import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
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

	protected Supplier m_supplier;
	protected Ansprechpartner m_contactPerson;	
	protected Employee m_employeeView;
	
	private boolean m_markKontakteAsChange = false;
	protected boolean isMarkKontakteAsChange() { return m_markKontakteAsChange; }
	protected void markKontakteAsChange() { 
		m_markKontakteAsChange = true; 
		markAsChange();
	}
	
	private boolean m_markAdresseAsChange = false;
	protected boolean isMarkAdresseAsChange() { return m_markAdresseAsChange; }
	protected void markAdresseAsChange() { 
		m_markAdresseAsChange = true; 
		markAsChange();
	}
	
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
		m_telephonField = textField("Telefonnummer", WIDTH_FULL, false, "Telefonnummer", 100);
		m_handyField = textField("Handynummer", WIDTH_FULL, false, "Handynummer", 100);		
		m_faxField = textField("Fax", WIDTH_FULL, false, "Fax", 100);
		m_emailField = textField("E-Mail", WIDTH_FULL, false, "E-Mail", 100);
		m_webField = textField("Web", WIDTH_FULL, false, "Web", 100);
	}
	
	protected void getAddressDataDefinition() {		
		m_subHeadAdressDaten = title("Adresse", STYLE_HEADLINE_SUB);
		m_streetField = textField("Straﬂe", WIDTH_FULL, false, "Straﬂe", 100);
		m_housenumberField = textField("Housenummer", WIDTH_FULL, false, "Housenummer", 100);
		m_cityField = textField("Stadt", WIDTH_FULL, false, "Stadt", 100);
		m_plzField = textField("PLZ", WIDTH_FULL, false, "PLZ", 10);
		m_countryField = textField("Land", WIDTH_FULL, false, "Land", 100);
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
	
	/** 
	 * @param obj 
	 * @param switcher 0=Supplier.get... 1=ContactPerson.get...
	 */
	protected void addToDB(Object obj, int switcher) {
		if (obj instanceof Kontakte) {
			if (isMarkKontakteAsChange()) {
				try {
					switch (switcher) {
					case 0:
						m_supplier.getKontakte().setId(KontakteService.getInstance().createKontakte((Kontakte) obj));
						break;
					case 1:
						m_contactPerson.getKontakt().setId(KontakteService.getInstance().createKontakte((Kontakte) obj));
						break;
					case 2:
						m_employeeView.getKontakt().setId(KontakteService.getInstance().createKontakte((Kontakte) obj));
						break;
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 

		if (obj instanceof Adresse) {
			if (isMarkAdresseAsChange()) {
				try {
					switch (switcher) {
					case 0:
						m_supplier.getAdresse().setId(AdresseService.getInstance().createAdresse((Adresse) obj));
						break;
					case 1:
						m_contactPerson.getAdresse().setId(AdresseService.getInstance().createAdresse((Adresse) obj));
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}	
		
		if (obj instanceof Supplier) {
			try {
				m_supplier.setId(SupplierService.getInstance().createLieferant(m_supplier));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (obj instanceof Ansprechpartner) {
			try {
				m_contactPerson.setId(AnsprechpartnerService.getInstance().createAnsprechpartner(m_contactPerson));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (obj instanceof Employee) {
			try {
				m_employeeView.setId(EmployeeService.getInstance().create(m_employeeView));	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void removeFromDB(Object obj) {
		if (obj instanceof Kontakte) { 
			try {
				KontakteService.getInstance().deleteKontakte(((Kontakte) obj).getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (obj instanceof Adresse) { 
			try {
				AdresseService.getInstance().deleteAdresse(((Adresse) obj).getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void updateInDB(Object obj) {
		if (obj instanceof Kontakte) { 
			if (isMarkKontakteAsChange()) {
				try {
					KontakteService.getInstance().updatekontakte((Kontakte) obj); }
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (obj instanceof Adresse) { 
			if (isMarkAdresseAsChange()) {
				try {
					AdresseService.getInstance().updateAdresse((Adresse) obj);}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
		if(obj instanceof Supplier) {
			if (isMarkAsChange()) {
				try {
					SupplierService.getInstance().updateLieferant((Supplier) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
		if(obj instanceof Ansprechpartner) {
			if (isMarkAsChange()) {
				try {
					AnsprechpartnerService.getInstance().updateAnsprechpartner((Ansprechpartner) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 
		if(obj instanceof Employee) {
			if (isMarkAsChange()) {
				try {
					EmployeeService.getInstance().update((Employee) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
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

	@Override
	protected void resetMarkAsChange() {
		m_markAdresseAsChange = false;
		m_markKontakteAsChange = false;
		super.resetMarkAsChange();	
	}
}

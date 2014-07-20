package de.palaver.view.bean.mitarbeiterverwaltung;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.employee.service.RolleService;
import de.palaver.management.info.person.Kontakte;
import de.palaver.view.bean.helpers.ChangeFieldsPersonAbstract;

public class ChangeEmployeeBean extends ChangeFieldsPersonAbstract implements View, ValueChangeListener {
	private static final long serialVersionUID = -7019286728687412031L;
	private boolean m_toCreate;
	private Employee m_empl;
	private VerticalLayout m_innerBox;
	

	public ChangeEmployeeBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listener();
		setDataToTwinColSelect();
	}

	private void init() {
		m_toCreate = true;
		m_empl = new Employee();
	}

	private void componetsManager() {
		m_headLine = title("Mitarbeiter ändern", STYLE_HEADLINE_STANDART);		
		m_subHeadPersonDaten = title("Persönliche Daten", STYLE_HEADLINE_SUB);
		m_nameField = textField("Nachname", WIDTH_FULL, true, "Nachname", this);
		m_vornameField = textField("Vorname", WIDTH_FULL, true, "Vorname", this);
		m_usernameField = textField("Benutzername", WIDTH_FULL, true, "Benutzername", this);
		m_passwordField = passwordField("Passwort", WIDTH_FULL, "Bitte gültigen Passwort eingeben", true, 6, 45, true, true);
		m_changePassword = buttonAsIcon(" Passwort ändern", BaseTheme.BUTTON_LINK, 
				"cursor-hand margin-key", "icons/report_key.png", false);
		m_eintrittsdatumField = textField("Eintrittsdatum", WIDTH_FULL, false, "Eintrittsdatum", this);
		m_austrittsdatumField= textField("Austrittsdatum", WIDTH_FULL, false, "Austrittsdatum", this);		
		m_subHeadNewDaten = title("Weitere Informationen", STYLE_HEADLINE_SUB);		
		getContactDataDefinition();		
		m_rollen = twinColSelect(null, "Verfügbare Rollen", "Ausgewählte Rollen", 8, 0, visible(), enabled());		
		m_leftVLayout = vertikalLayoutBuilder(4, "90%");	
		m_centerVLayout = vertikalLayoutBuilder(1, "90%");
		m_rightVLayout = vertikalLayoutBuilder(5, "90%"); 		

		
		m_control = controlPanel(this);	
	}

	private void templateBuilder() {
		m_innerBox = new VerticalLayout();		
		m_innerBox.setWidth(WIDTH_FULL);
		m_innerBox.setSpacing(true);
		m_innerBox.setMargin(true);		
		
		m_innerBox.addComponent(m_headLine);
		m_innerBox.addComponent(new Hr());
		m_innerBox.addComponent(horizontalLayoutBuilder());	
		
		m_innerBox.addComponent(m_control);		
		m_innerBox.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(m_innerBox);		
		setComponentAlignment(m_innerBox, Alignment.MIDDLE_CENTER);	
	}

	@SuppressWarnings("serial")
	private void listener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();		
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Mitarbeiter"));
					ViewHandler.getInstance().switchView(ShowEmployeeBean.class);
				}							
			}

			private boolean validiereEingabe() {
				boolean bool = true;
				if (StringUtils.isBlank(m_nameField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MITARBEITER_NAME);
					bool =  false;
				} else if (StringUtils.isBlank(m_vornameField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MITARBEITER_VORNAME);
					bool =  false;
				} else if (StringUtils.isBlank(m_usernameField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MITARBEITER_BENUTZERNAME);
					bool =  false;
				} else if (m_passwordField.isRequired() && StringUtils.isBlank(m_passwordField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MITARBEITER_PASSWORT);
					bool =  false;
				}				
				return bool;
			}
			
			private void saveItem() {
				if (m_toCreate) {
					try {
						addToDB(new Kontakte()); 
						updateEmployee();						
						EmployeeService.getInstance().create(m_empl);						
					} catch (Exception e) {
						e.printStackTrace();
					} 					
				} else {
					try {
						if (m_empl.getKontakt() != null) {
							if (!checkFields(m_empl.getKontakt())) {
								removeFromDB(m_empl.getKontakt());	
								m_kontakte = null;
							} else {
								m_kontakte = m_empl.getKontakt();
								updateInDB(m_kontakte);	
								
							}
						} else {
							addToDB(new Kontakte());
						}										
						updateEmployee();
						EmployeeService.getInstance().update(m_empl);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			
			private void updateEmployee() {				
				try {
					m_empl.setName(m_nameField.getValue());
					m_empl.setVorname(m_vornameField.getValue());
					m_empl.setBenutzername(m_usernameField.getValue());	
					if (m_passwordField.isRequired()) {
						m_empl.setPasswort(Util.encryptPassword(m_passwordField.getValue()));
					}
					m_empl.setEintrittsdatum(m_eintrittsdatumField.getValue());
					m_empl.setAustrittsdatum(m_austrittsdatumField.getValue());
					m_empl.setRollen(setRolles());
					m_empl.setKontakt(m_kontakte);	
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}

			private List<Rolle> setRolles() {
				List<Rolle> rolleList = new ArrayList<Rolle>();
				for (Object rolleId : m_rollen.getItemIds()) {					
					if (m_rollen.isSelected((Long) rolleId))	{				
						rolleList.add(new Rolle((Long) rolleId, null));
					} 							
				}
				return rolleList;
			}
		});	
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ShowEmployeeBean.class);
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
	
	public void changePasswortListener() {
		m_changePassword.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangePasswordBean(m_empl));
			}
		});
	}
	
	protected void getWindowFactory(ChangePasswordBean object) {
		if(object instanceof ChangePasswordBean) {
			addComponent((ChangePasswordBean) object);
			m_window = windowUI(CHANGE_OLD_PASSWORD, true, false, "500", "250");
			m_window.setContent((ChangePasswordBean) object);
		}
		
		UI.getCurrent().addWindow(m_window);
		m_window.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				System.out.print("hallo");
			}
		});
	}

	private void setDataToTwinColSelect() {
		try {
			for (Rolle rolle : RolleService.getInstance().getAllRolles()) {
				m_rollen.addItem(rolle.getId());
				m_rollen.setItemCaption(rolle.getId(), rolle.getName());
			}
			if (!m_toCreate && m_empl.getRollen() != null) {
				for (Rolle rolle : m_empl.getRollen()) {
					m_rollen.select(rolle.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean visible() {
		boolean isVisible = false;		
		if (((Application) UI.getCurrent().getData()).userHasPersmission(Rolle.ADMINISTRATOR)) {		
			isVisible = true;
		} 
		return isVisible;
	}
	
	private boolean enabled() {
		boolean isEnabled = false;		
		if (((Application) UI.getCurrent().getData()).userHasPersmission(Rolle.ADMINISTRATOR)) {		
			isEnabled = true;
		} 
		return isEnabled;
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Employee) {
			m_toCreate = false;
			m_empl = (Employee)((ViewDataObject<?>) data).getData(); 
			
			m_nameField.setValue(m_empl.getName());
			m_vornameField.setValue(m_empl.getVorname());
			m_usernameField.setValue(m_empl.getBenutzername());
			m_eintrittsdatumField.setValue(m_empl.getEintrittsdatum());
			m_austrittsdatumField.setValue(m_empl.getAustrittsdatum());
			m_passwordField.setEnabled(false);
			m_passwordField.setRequired(false);
			m_passwordField.removeAllValidators();
			m_changePassword.setVisible(true);
			
			if(m_empl.getKontakt() != null) {
			
				m_emailField.setValue(m_empl.getKontakt().getEmail());
				m_faxField.setValue(m_empl.getKontakt().getFax());
				m_handyField.setValue(m_empl.getKontakt().getHandy());
				m_telephonField.setValue(m_empl.getKontakt().getTelefon());
				m_webField.setValue(m_empl.getKontakt().getWww());
				
			}
			
			if (m_empl.getRollen() != null && m_empl.getRollen().size() > 0) {
				for (Rolle r : m_empl.getRollen()) {
					m_rollen.select(r.getId());
				}
			}
			
			changePasswortListener();
		}		
	}
}

package de.palaver.view.bean.mitarbeiterverwaltung;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.employee.service.RolleService;
import de.palaver.management.info.person.Kontakte;
import de.palaver.view.bean.helpers.ChangeFieldsPersonAbstract;

public class ChangeEmployeeBean extends ChangeFieldsPersonAbstract implements View, ValueChangeListener {
	private static final long serialVersionUID = -7019286728687412031L;
	private boolean m_toCreate;
	private VerticalLayout m_innerBox;
	

	public ChangeEmployeeBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListner();
		setDataToTwinColSelect();
	}

	private void init() {
		resetMarkAsChange();
		m_toCreate = true;
		m_employeeView = new Employee(new Kontakte());
	}

	private void componetsManager() {
		m_headLine = title("Mitarbeiter ändern", STYLE_HEADLINE_STANDART);		
		m_subHeadPersonDaten = title("Persönliche Daten", STYLE_HEADLINE_SUB);
		m_nameField = textField("Nachname", WIDTH_FULL, true, "Nachname", 45);
		m_vornameField = textField("Vorname", WIDTH_FULL, true, "Vorname", 45);
		m_usernameField = textField("Benutzername", WIDTH_FULL, true, "Benutzername", 30);
		m_passwordField = passwordField("Passwort", WIDTH_FULL, "Bitte gültigen Passwort eingeben", true, 6, 45, true, true);
		m_changePassword = buttonAsIcon(" Passwort ändern", BaseTheme.BUTTON_LINK, 
				"cursor-hand margin-key", "icons/report_key.png", false);
		m_eintrittsdatumField = textField("Eintrittsdatum", WIDTH_FULL, false, "Eintrittsdatum", 45);
		m_austrittsdatumField= textField("Austrittsdatum", WIDTH_FULL, false, "Austrittsdatum", 45);		
		m_subHeadNewDaten = title("Weitere Informationen", STYLE_HEADLINE_SUB);	
		m_rollenColSelect = twinColSelect(null, "Verfügbare Rollen", "Ausgewählte Rollen", 8, 0, visible(), enabled());	
		
		getContactDataDefinition();				
	
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
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (isMarkAsChange() && validiereEingabe()) {
					saveItem();	
					close();
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Mitarbeiter"));
					ViewHandler.getInstance().switchView(ShowEmployeesBean.class);
				}							
			}

			private boolean validiereEingabe() {
				boolean bool = true;
				if (StringUtils.isBlank(m_nameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, "Nachname");
					bool = false;
				} else if (StringUtils.isBlank(m_vornameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, "Vorname");
					bool = false;
				} else if (StringUtils.isBlank(m_usernameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, "Benutzername");
					bool = false;
				} else if (m_passwordField.isRequired() && StringUtils.isBlank(m_passwordField.getValue())) {
					message(MESSAGE_LEER_ARG_1, "Passwort");
					bool = false;
				}				
				return bool;
			}
			
			private void saveItem() {
				try {
					if (m_toCreate) {
						addToDB(new Kontakte(), 2); 
						m_employeeView.setRollen(setRolles());
						addToDB(m_employeeView, 2);								
					} else {
						if (m_employeeView.getKontakt().getId() != null) {
							if (!checkFields(m_employeeView.getKontakt())) {
								removeFromDB(m_employeeView.getKontakt());	
								m_employeeView.setKontakt(new Kontakte());
							} else {
								updateInDB(m_employeeView.getKontakt());
							}
						} else {
							addToDB(m_employeeView.getKontakt(), 2);
						}	
						m_employeeView.setRollen(setRolles());
						updateInDB(m_employeeView);
						message(MESSAGE_SUSSEFULL_ARG_1, "Mitarbeiter");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			
			private List<Rolle> setRolles() {
				List<Rolle> rolleList = new ArrayList<Rolle>();
				for (Object rolleId : m_rollenColSelect.getItemIds()) {					
					if (m_rollenColSelect.isSelected((Long) rolleId))	{				
						rolleList.add(new Rolle((Long) rolleId, null));
					} 							
				}
				return rolleList;
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
				windowModalYesNoRemove(m_employeeView);	
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void changeListner() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.setName((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_vornameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.setVorname((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_usernameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.setBenutzername((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_passwordField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (m_passwordField.isRequired()) {
					try {
						m_employeeView.setPasswort(Util.encryptPassword((String) event.getProperty().getValue()));
						markAsChange();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		m_rollenColSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				markAsChange();
			}
		});
		
		m_eintrittsdatumField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.setEintrittsdatum((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_austrittsdatumField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.setAustrittsdatum((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_telephonField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.getKontakt().setTelefon((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_handyField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.getKontakt().setHandy((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_faxField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.getKontakt().setFax((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_emailField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.getKontakt().setEmail((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_webField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_employeeView.getKontakt().setWww((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
	}
	
	public void changePasswortListener() {
		m_changePassword.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangePasswordBean(m_employeeView));
			}
		});
	}
	
	private void close() {
		if (ChangeEmployeeBean.this.getParent() instanceof Window) {
			((Window) ChangeEmployeeBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowEmployeesBean.class);
		}
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
				System.out.print("hallo"); //TODO WAS IST DAS!!!!
			}
		});
	}

	private void setDataToTwinColSelect() {
		try {
			for (Rolle rolle : RolleService.getInstance().getAllRolles()) {
				m_rollenColSelect.addItem(rolle.getId());
				m_rollenColSelect.setItemCaption(rolle.getId(), rolle.getName());
			}
			if (!m_toCreate && m_employeeView.getRollen() != null) {
				for (Rolle rolle : m_employeeView.getRollen()) {
					m_rollenColSelect.select(rolle.getId());
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
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Employee) {
			m_employeeView = (Employee)((ViewDataObject<?>) data).getData();
			setNewInfo();			
			setValueToComponent(getData());
			changePasswortListener();
		}	
		resetMarkAsChange();
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_employeeView.getName());
		getData().put(m_vornameField, m_employeeView.getVorname());
		getData().put(m_usernameField, m_employeeView.getBenutzername());
		getData().put(m_eintrittsdatumField, m_employeeView.getEintrittsdatum());
		getData().put(m_austrittsdatumField, m_employeeView.getAustrittsdatum());
	
		if (m_employeeView.getKontakt() != null) {
			getData().put(m_telephonField, m_employeeView.getKontakt().getTelefon());
			getData().put(m_handyField, m_employeeView.getKontakt().getHandy());
			getData().put(m_faxField, m_employeeView.getKontakt().getFax());
			getData().put(m_emailField, m_employeeView.getKontakt().getEmail());
			getData().put(m_webField, m_employeeView.getKontakt().getWww());
		} else {
			m_employeeView.setKontakt(new Kontakte());			
		}
		if (m_employeeView.getRollen() != null && m_employeeView.getRollen().size() > 0) {
			for (Rolle r : m_employeeView.getRollen()) {
				m_rollenColSelect.select(r.getId());
			}
		}
		
		m_passwordField.setEnabled(false);
		m_passwordField.setRequired(false);
		m_passwordField.removeAllValidators();
		m_changePassword.setVisible(true);
		
		m_toCreate = false;		
		m_headLine.setValue("Mitarbeiter bearbeiten");
		m_buttonDeaktiviren.setVisible(true);	
	}
}

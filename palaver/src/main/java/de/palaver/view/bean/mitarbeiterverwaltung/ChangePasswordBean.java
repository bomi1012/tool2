package de.palaver.view.bean.mitarbeiterverwaltung;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.view.bean.helpers.TemplateBuilder;

public class ChangePasswordBean extends TemplateBuilder implements View, ValueChangeListener {
	private static final long serialVersionUID = -34841015627292738L;
	private static final String TITLE = "Passwort ändern";
	private static final String TEXT_FIELD_PASSWORD = "Passwort";
	

	private Employee m_employee;
	private TextField m_passwordField;

	public ChangePasswordBean() {
		super();
		componetsManager();
		templateBuilder();
		listener();
	}

	public ChangePasswordBean(Employee empl) {
		super();
		m_employee = empl;
		componetsManager();
		templateBuilder();
		listener();
	}

	private void componetsManager() {
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_passwordField = textField(TEXT_FIELD_PASSWORD, WIDTH_FULL, true, TEXT_FIELD_PASSWORD, 0);
		m_control = controlPanel(this);
	}
	
	private void templateBuilder() {
		setMargin(true);
		VerticalLayout innerBox = new VerticalLayout();		
		innerBox.setWidth("65%");
		innerBox.setSpacing(true);

		innerBox.addComponent(m_headLine);
		innerBox.addComponent(new Hr());
		innerBox.addComponent(m_passwordField);
		innerBox.addComponent(new Hr());

		innerBox.addComponent(m_control);		
		innerBox.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(innerBox);
		setComponentAlignment(innerBox, Alignment.MIDDLE_CENTER);
	}
	
	@SuppressWarnings("serial")
	private void listener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8358053287073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					try {
						saveItem();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Passwort"));		
					close();					
				}
			}
			private void saveItem() throws NoSuchAlgorithmException, UnsupportedEncodingException {
				m_employee.setPasswort(Util.encryptPassword(m_passwordField.getValue()));
				try {
					EmployeeService.getInstance().changePassword(m_employee);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private boolean validiereEingabe() {
				boolean isValid = true;
				if (StringUtils.isBlank(m_passwordField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_LEER_ARG_1, "Passwort"));
					isValid = false;
				}
				return isValid;
			}
		});
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});	
	}

	private void close() {
		if (ChangePasswordBean.this.getParent() instanceof Window) {					
			Window win = (Window) ChangePasswordBean.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(ShowEmployeesBean.class);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Employee) {
			m_employee = (Employee)((ViewDataObject<?>) data).getData(); 
		}
	}
}

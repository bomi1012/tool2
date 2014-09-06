package de.palaver.view.bean.artikelverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.artikel.service.MengeneinheitService;
import de.palaver.management.util.dao.DAOException;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IChangeViewPage;

public class ChangeQuantityUnitBean extends TemplateBuilder implements View, ValueChangeListener, IChangeViewPage {
	private static final long serialVersionUID = -3484101562729271738L;
	private static final String TITLE = "Mengeneinheit erstellen";
	private static final String TEXT_FIELD_MENGENEINHEIT_NAME = "Mengeneinheitname";
	private static final String TEXT_FIELD_MENGENEINHEIT_SHORT = "Kürzel";
	
	private Mengeneinheit m_mengeneinheit;
	private boolean m_toCreate;
	private TextField m_nameField;
	private TextField m_shortField;

	public ChangeQuantityUnitBean() {
		super();
		init();
		componetsManager();		
		templateBuilder();
		clickListener();
		changeListener();
	}

	private void init() {
		m_toCreate = true;
		m_mengeneinheit = new Mengeneinheit();
	}

	private void componetsManager() {
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_MENGENEINHEIT_NAME, WIDTH_FULL, true, TEXT_FIELD_MENGENEINHEIT_NAME, 45);
		m_shortField = textField(TEXT_FIELD_MENGENEINHEIT_SHORT, WIDTH_FULL, true, TEXT_FIELD_MENGENEINHEIT_SHORT, 5);
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
		innerBox.addComponent(m_shortField);
		innerBox.addComponent(new Hr());

		innerBox.addComponent(m_control);		
		innerBox.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(innerBox);
		setComponentAlignment(innerBox, Alignment.MIDDLE_CENTER);
	}

	
	@SuppressWarnings("serial")
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8358053287073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					close();					
				}
			}
			private void saveItem() {
				try {
					if (m_toCreate) {
						MengeneinheitService.getInstance().createMengeneinheit(m_mengeneinheit);
					} else {
						MengeneinheitService.getInstance().updateMengeneinheit(m_mengeneinheit);
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Mengeneinheit");	
				} catch (Exception e) {
					if (e instanceof DAOException) {
						message(MESSAGE_EXISTS_ARG_1, m_nameField.getValue());	
					}
				}
			}
			private boolean validiereEingabe() {
				boolean isValid = true;
				if (m_nameField.getValue().equals("")) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_MENGENEINHEIT_NAME);	
					isValid = false;
				} else if (m_shortField.getValue().equals("")) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_MENGENEINHEIT_SHORT);	
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
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModalYesNoRemove(m_mengeneinheit);
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void changeListener() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {		
		@Override
		public void valueChange(ValueChangeEvent event) {
			m_mengeneinheit.setName(String.valueOf(event.getProperty().getValue()));				
		}
		});
		m_shortField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_mengeneinheit.setKurz(String.valueOf(event.getProperty().getValue()));
			}
		});
	}

	private void close() {
		if (ChangeQuantityUnitBean.this.getParent() instanceof Window) {					
			((Window) ChangeQuantityUnitBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowQuantitiesUnitBean.class);
		}
	}

	@Override
	public void valueChange(final ValueChangeEvent event) { }

	@Override
	public void getViewParam(IViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Mengeneinheit) {
			m_mengeneinheit = (Mengeneinheit)((ViewDataObject<?>) data).getData();			
			setNewInfo();			
			setValueToComponent(getData());	
		}		
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_mengeneinheit.getName());
		getData().put(m_shortField, m_mengeneinheit.getKurz());
		
		m_toCreate = false;		
		m_headLine.setValue("Mengeneinheit bearbeiten");
		m_buttonDeaktiviren.setVisible(true);
	}
}

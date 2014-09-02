package de.palaver.view.bean.artikelverwaltung;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.service.LagerortService;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IChangeViewPage;

public class ChangeWarehouseBean extends TemplateBuilder implements View, ValueChangeListener, IChangeViewPage {
	private static final long serialVersionUID = -3484101562729271738L;
	private static final String TITLE = "Neuer Lagerort erstellen";
	private static final String TEXT_FIELD_LAGERORT_NAME = "Lagerortname";
	
	private Lagerort m_warehouse;
	private boolean m_toCreate;
	private TextField m_nameField;

	public ChangeWarehouseBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListener();
	}

	private void init() {
		m_toCreate = true;
		m_warehouse = new Lagerort();
	}

	private void componetsManager() {
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_LAGERORT_NAME, WIDTH_FULL, true, TEXT_FIELD_LAGERORT_NAME, 45);
		m_control = controlPanel(this);
	}
	
	private void templateBuilder() {
		m_nameField.addValueChangeListener(this);
		
		setMargin(true);
		VerticalLayout innerBox = new VerticalLayout();		
		innerBox.setWidth("65%");
		innerBox.setSpacing(true);

		innerBox.addComponent(m_headLine);
		innerBox.addComponent(new Hr());
		innerBox.addComponent(m_nameField);
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
			private void saveItem() {
				try {
					if (m_toCreate) {
						LagerortService.getInstance().createLagerort(m_warehouse);
					} else {
						LagerortService.getInstance().updateLagerort(m_warehouse);
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Lagerort");	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private boolean validiereEingabe() {
				boolean isValid = true;
				if (StringUtils.isBlank(m_nameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, "Name");
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
				windowModalYesNoRemove(m_warehouse);	
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void changeListener() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_warehouse.setName(String.valueOf(event.getProperty().getValue()));				
			}
		});
	}

	private void close() {
		if (ChangeWarehouseBean.this.getParent() instanceof Window) {					
			((Window) ChangeWarehouseBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowWarehousesBean.class);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Lagerort) {
			m_warehouse = (Lagerort)((ViewDataObject<?>) data).getData();	
			setNewInfo();
			
			setValueToComponent(getData());	
		}		
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_warehouse.getName());
		
		m_toCreate = false;		
		m_headLine.setValue("Lager bearbeiten");
		m_buttonDeaktiviren.setVisible(true);
	}
}

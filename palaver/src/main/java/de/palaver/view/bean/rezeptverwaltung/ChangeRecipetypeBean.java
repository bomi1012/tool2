package de.palaver.view.bean.rezeptverwaltung;

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
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.service.RecipeService;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.helpers.interfaces.IChangeViewPage;

public class ChangeRecipetypeBean extends TemplateBuilder implements View, ValueChangeListener, IChangeViewPage{
	private static final long serialVersionUID = -34841015629271738L;
	private static final String TITLE = "Neues Rezeptart erstellen";
	private static final String TEXT_FIELD_NAME = "Name des Rezeptarts";
	
	private Recipetype m_recipetype;
	public Recipetype getRecipetype() { return m_recipetype; }
	private boolean m_toCreate;
	private TextField m_nameField;

	public ChangeRecipetypeBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListener();
	}

	private void init() {
		m_toCreate = true;
		m_recipetype = new Recipetype();
	}

	private void componetsManager() {
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_NAME, WIDTH_FULL, true, TEXT_FIELD_NAME, 45);
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
		innerBox.addComponent(new Hr());

		innerBox.addComponent(m_control);		
		innerBox.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(innerBox);
		setComponentAlignment(innerBox, Alignment.MIDDLE_CENTER);
	}

	
	@SuppressWarnings("serial")
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -835805357073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					close();					
				}
			}
			private void saveItem() {
				m_recipetype.setName(m_nameField.getValue());
				try {
					if (m_toCreate) {
						RecipeService.getInstance().createRecipetype(m_recipetype);
					} else {
						RecipeService.getInstance().updateRecipetype(m_recipetype);
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Rezeptart");	
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
				//windowModalYesNoRemove(m_recipetype);	
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void changeListener() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_recipetype.setName(String.valueOf(event.getProperty().getValue()));				
			}
		});
	}

	private void close() {
		if (ChangeRecipetypeBean.this.getParent() instanceof Window) {					
			((Window) ChangeRecipetypeBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowRecipetypesBean.class);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Recipetype) {
			m_recipetype = (Recipetype)((ViewDataObject<?>) data).getData();		
			setNewInfo();			
			setValueToComponent(getData());	
		}		
	}
	
	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_recipetype.getName());
		
		m_toCreate = false;		
		m_headLine.setValue("Rezeptart bearbeiten");
	}
}

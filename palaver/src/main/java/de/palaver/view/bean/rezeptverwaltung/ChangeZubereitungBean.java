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
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.service.RecipeService;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IChangeViewPage;

public class ChangeZubereitungBean extends TemplateBuilder implements View, ValueChangeListener, IChangeViewPage{
	private static final long serialVersionUID = -3484101562729271738L;
	private static final String TITLE = "Neue Zubereitung erstellen";
	private static final String TEXT_FIELD_NAME = "Zubereitungsname";
	
	private Zubereitung m_zubereitung;
	public Zubereitung getZubereitung() { return m_zubereitung; }
	private boolean m_toCreate;
	private TextField m_nameField;

	public ChangeZubereitungBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListener();
	}

	private void init() {
		m_toCreate = true;
		m_zubereitung = new Zubereitung();
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
				m_zubereitung.setName(m_nameField.getValue());
				try {
					if (m_toCreate) {
						RecipeService.getInstance().createZubereitung(m_zubereitung);
					} else {
						RecipeService.getInstance().updateZubereitung(m_zubereitung);
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Zubereitung");	
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
				//windowModalYesNoRemove(m_zubereitung);	
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void changeListener() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_zubereitung.setName(String.valueOf(event.getProperty().getValue()));				
			}
		});
	}

	private void close() {
		if (ChangeZubereitungBean.this.getParent() instanceof Window) {					
			((Window) ChangeZubereitungBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowZubereitungenBean.class);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Zubereitung) {
			m_zubereitung = (Zubereitung)((ViewDataObject<?>) data).getData();		
			setNewInfo();			
			setValueToComponent(getData());	
		}		
	}
	
	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_zubereitung.getName());
		
		m_toCreate = false;		
		m_headLine.setValue("Zubereitung bearbeiten");
	}
}

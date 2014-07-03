package de.palaver.view.artikelverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.service.KategorieService;
import de.palaver.view.manager.TemplateBuilder;

public class ChangeKategoryBean extends TemplateBuilder implements View, ValueChangeListener {
	private static final long serialVersionUID = -3484101562729271738L;
	private static final Logger LOG = LoggerFactory.getLogger(ChangeKategoryBean.class.getName());
	private static final String TITLE = "Neue Kategorie erstellen";
	private static final String TEXT_FIELD_KATEGORIE_NAME = "Kategoriename";
	

	
	private Kategorie m_kategorie;
	private boolean m_toCreate;
	private TextField m_nameField;

	public ChangeKategoryBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listener();
	}

	private void init() {
		m_toCreate = true;
		m_kategorie = new Kategorie();
	}

	private void componetsManager() {
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_nameField = textField(TEXT_FIELD_KATEGORIE_NAME, WIDTH_FULL, true, TEXT_FIELD_KATEGORIE_NAME, this);
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
	private void listener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -835805357073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Kategorie"));		
					close();					
				}
			}
			private void saveItem() {
				m_kategorie.setName(m_nameField.getValue());
				try {
					if (m_toCreate) {
						KategorieService.getInstance().createKategorie(m_kategorie);
					} else {
						KategorieService.getInstance().updateKategorie(m_kategorie);
					}
				} catch (Exception e) {
					LOG.error(e.toString());
					if (e instanceof DAOException) {
						((Application) UI.getCurrent().getData())
						.showDialog(String.format(MESSAGE_EXISTS_ARG_1, m_nameField.getValue()));
					}
				}
			}
			private boolean validiereEingabe() {
				boolean isValid = true;
				if (m_nameField.getValue().equals("")) {
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_LEER_ARG_1, "Name"));
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
				//TODO: visible = false --> implementieren!
				//sehe m_control
			}
		});
	}

	private void close() {
		if (ChangeKategoryBean.this.getParent() instanceof Window) {					
			Window win = (Window) ChangeKategoryBean.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(ShowKategoriesBean.class);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Kategorie) {
			m_kategorie = (Kategorie)((ViewDataObject<?>) data).getData();			
			m_nameField.setValue(m_kategorie.getName());			
			m_toCreate = false;
		}		
	}
}

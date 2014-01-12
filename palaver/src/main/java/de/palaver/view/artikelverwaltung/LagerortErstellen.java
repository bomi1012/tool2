package de.palaver.view.artikelverwaltung;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.artikelverwaltung.Kategorie;
import de.palaver.domain.artikelverwaltung.Lagerort;
import de.palaver.service.artikelverwaltung.LagerorService;
import de.palaver.view.IErstellen;

public class LagerortErstellen extends OverErstellen implements View,
ValueChangeListener, IErstellen  {
	private static final long serialVersionUID = 115073498815246131L;
	private static final Logger LOG = LoggerFactory.getLogger(LagerortErstellen.class.getName());
	public LagerortErstellen() {
		super();
		m_kategorie = new Kategorie(); 
		m_create = true;	
		layout(NEW_LAGERORT);
	}
	public LagerortErstellen(Lagerort lagerort){
		super();
		m_create = false;				
		m_lagerort = lagerort; 
		layout(EDIT_LAGERORT);
		m_deaktivierenButton.setVisible(true);
	}
		
	private void layout(String text) {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, text, STYLE_HEADLINE);		
		/** Fields */
		m_nameField = textFieldSettingLE(m_textField, LAGERORT, FULL, true, LAGERORT, this);
		if(!m_create) {
			m_nameField.setValue(m_lagerort.getName());
		}	
		m_control = controlErstellenPanel();
		m_vertikalLayout = addToLayout(m_vertikalLayout, "450");
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);
		listener();
	}
	
	private void listener() {
		m_speichernButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8358053287073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					try {
						sqlStatement(0);
						close();
						((Application) UI.getCurrent().getData()).showDialog(String.format(OverArtikelverwaltungView.MESSAGE_SUSSEFULL_ARG_1, 
								LAGERORT));
					} catch (ConnectException e) {
						LOG.error(e.toString());
					} catch (DAOException e) {
						((Application) UI.getCurrent().getData())
							.showDialog(String.format(MESSAGE_EXISTS_ARG_1, m_nameField.getValue()));
						LOG.error(e.toString());
					} catch (SQLException e) {
						LOG.error(e.toString());
					}
				}
			}
		});
		
		m_verwerfenButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2701157762823717701L;
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		m_deaktivierenButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2701157762823717701L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {					
					sqlStatement(1);
					m_okRemove = true;
					close();
					((Application) UI.getCurrent().getData())
	             		.showDialog("Lagerort <" + m_lagerort.getName() + "> wurde gelöscht");
				} catch (ConnectException e) {
					LOG.error(e.toString());
				} catch (DAOException e) {
					((Application) UI.getCurrent().getData())
					.showDialog("Lagerort <" + m_lagerort.getName() + "> darf nicht gelöscht werden, " +
                  	"weil sie an Artikeln hängt.");  
					m_okRemove = false;
					close();
					LOG.error(e.toString());
				} catch (SQLException e) {
					LOG.error(e.toString());
				}
			}
		});
	}

	private void close() {
		if (LagerortErstellen.this.getParent() instanceof Window) {					
			Window win = (Window) LagerortErstellen.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(LagerorteAnzeigen.class);
		}
	}
	
	
	@Override
	public void valueChange(ValueChangeEvent event) { 
	}
	@Override
	public void getViewParam(ViewData data) {
	}
	
	@Override
	public void sqlStatement(int i) throws ConnectException, DAOException, SQLException {
		if(i == 0) {
			if(!m_create) {
				m_lagerort.setName(m_nameField.getValue());
				LagerorService.getInstance().updateLagerort(m_lagerort);
			} else {
				m_lagerort = new Lagerort(m_nameField.getValue());
				LagerorService.getInstance().createLagerort(m_lagerort);
			}
		} else if(i == 1) {
			LagerorService.getInstance().deleteLagerort(m_lagerort.getId());
		} 
	}
	
	@Override
	public boolean validiereEingabe() {
		if (m_nameField.getValue().equals("")) {
			((Application) UI.getCurrent().getData()).showDialog(String.format(OverArtikelverwaltungView.MESSAGE_LEER_ARG_1, "Name"));
			return false;
		}
		return true;
	}
	
	@Override
	public VerticalLayout addToLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);		
		box.addComponent(m_headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(m_nameField);	
		box.addComponent(m_control);
		box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		return box;
	}
}

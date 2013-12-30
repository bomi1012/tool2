package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.view.IErstellen;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

public class MengeneinheitErstellen extends OverErstellen implements View,
ValueChangeListener, IErstellen  {
	private static final long serialVersionUID = 115073498815246131L;
	private TextField kurzField;
	public MengeneinheitErstellen() {
		super();
		m_mengeneinheit = new Mengeneinheit(); 
		m_create = true;		
		layout(NEW_MENGENEINHEIT);
	}
	
	public MengeneinheitErstellen(Mengeneinheit mengeneinheit) {
		super();
		m_create = false;		
		m_mengeneinheit = mengeneinheit;
		layout(EDIT_MENGENEINHEIT);	
		m_deaktivierenButton.setVisible(true);
	}
	
	
	private void layout(String text) {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, text, STYLE_HEADLINE);		
		/** Fields */
		m_nameField = textFieldSettingME(m_textField, MENGENEINHEIT,
				ArtikelverwaltungView.FULL, true, MENGENEINHEIT, this);
		kurzField = textFieldSettingME(m_textField, MENGENEINHEIT_ABKUERZUNG,
				ArtikelverwaltungView.FULL, true, MENGENEINHEIT_ABKUERZUNG, this);
		if(!m_create) {
			m_nameField.setValue(m_mengeneinheit.getName());
			kurzField.setValue(m_mengeneinheit.getKurz());
		}	
		m_control = controlErstellenPanel();
		vertikalLayout = addToLayout(vertikalLayout, "450");
		this.addComponent(vertikalLayout);
		this.setComponentAlignment(vertikalLayout, Alignment.MIDDLE_CENTER);
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
						((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_SUSSEFULL_ARG_1, 
								MENGENEINHEIT));	
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						((Application) UI.getCurrent().getData())
							.showDialog(String.format(MESSAGE_EXISTS_ARG_1, m_nameField.getValue()));
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
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
	             		.showDialog("Mengeneinheit <" + m_mengeneinheit.getName() + "> wurde gelöscht");
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					((Application) UI.getCurrent().getData())
					.showDialog("Die Mengeneinheit <" + m_mengeneinheit.getName() + "> darf nicht gelöscht werden, " +
                  	"weil sie an Artikeln hängt.");  
					m_okRemove = false;
					close();
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	} 
	
	private void close() {
		if (MengeneinheitErstellen.this.getParent() instanceof Window) {					
			Window win = (Window) MengeneinheitErstellen.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
		}
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) { }
	@Override
	public void getViewParam(ViewData data) { }
	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //
	
	
	@Override
	public void sqlStatement(int i) throws ConnectException, DAOException, SQLException {
		if(i == 0) {
			if(!m_create) {
				m_mengeneinheit.setName(m_nameField.getValue());
				m_mengeneinheit.setKurz(kurzField.getValue());
				Mengeneinheitverwaltung.getInstance().updateMengeneinheit(m_mengeneinheit);
			} else {
				m_mengeneinheit = new Mengeneinheit(m_nameField.getValue(), kurzField.getValue());
				Mengeneinheitverwaltung.getInstance().createMengeneinheit(m_mengeneinheit);
			}
		} else if(i == 1) {
			Mengeneinheitverwaltung.getInstance().deleteMengeneinheit(m_mengeneinheit.getId());
		} 
		
	}

	@Override
	public boolean validiereEingabe() {
		boolean isTrue = true;
		if (m_nameField.getValue().equals("")) {
			((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_LEER_ARG_1, "Name"));
			isTrue = false;
		}
		if (kurzField.getValue().equals("")) {
			kurzField.setValidationVisible(true);
			((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_LEER_ARG_1, "Abkürzung"));
			isTrue = false;
		}
		return isTrue;
	}
	
	@Override
	public VerticalLayout addToLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);
		
		box.addComponent(m_headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(m_nameField);
		box.addComponent(kurzField);
	
		box.addComponent(m_horizontalLayout);
		box.setComponentAlignment(m_horizontalLayout, Alignment.MIDDLE_RIGHT);
		return box;
	}	
}

package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

public class MengeneinheitErstellen extends ArtikelverwaltungView implements View,
ValueChangeListener, IErstellen  {
	private static final long serialVersionUID = 115073498815246131L;
	private TextField kurzField;
	public MengeneinheitErstellen() {
		super();
		m_headlineLabel = headLine(m_headlineLabel, ArtikelverwaltungView.NEW_MENGENEINHEIT, "ViewHeadline");
		layout();
	}
	
	public MengeneinheitErstellen(Mengeneinheit mengeneinheit) {
		super();
		create = false;
		m_headlineLabel = headLine(m_headlineLabel, "Mengeneinheit ändern", "ViewHeadline");		
		this.m_mengeneinheit = mengeneinheit;
		layout();
		m_deaktivierenButton.setVisible(true);
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) { }
	@Override
	public void getViewParam(ViewData data) { }
	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //
	
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);			
		nameField = textFieldSettingME(nameField, "Mengeneinheit",
				ArtikelverwaltungView.FULL, true, "Mengeneinheit", this);
		kurzField = textFieldSettingME(kurzField, "Abkürzung",
				ArtikelverwaltungView.FULL, true, "Abkürzung", this);
		m_speichernButton = buttonSetting(m_speichernButton, IConstants.BUTTON_SAVE,
				IConstants.BUTTON_SAVE_ICON, true);
		m_verwerfenButton = buttonSetting(m_verwerfenButton, IConstants.BUTTON_DISCARD,
				IConstants.BUTTON_DISCARD_ICON, true);
		m_deaktivierenButton = buttonSetting(m_deaktivierenButton, IConstants.BUTTON_DELETE,
				IConstants.BUTTON_DELETE_ICON, false);
		
		m_headlineLabel = new Label(ArtikelverwaltungView.NEW_MENGENEINHEIT);
		m_headlineLabel.setStyleName("ViewHeadline");
		
		/** ControlPanel */
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_deaktivierenButton);
		m_horizontalLayout.addComponent(m_verwerfenButton);
		m_horizontalLayout.addComponent(m_speichernButton);
		
		vertikalLayout = boxLayout(vertikalLayout, "450");
		this.addComponent(vertikalLayout);
		this.setComponentAlignment(vertikalLayout, Alignment.MIDDLE_CENTER);	
		
		if(!create) {
			nameField.setValue(m_mengeneinheit.getName());
			kurzField.setValue(m_mengeneinheit.getKurz());
		}
		
		m_speichernButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8358053287073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					try {
						sqlStatement(0);
						win = (Window) MengeneinheitErstellen.this.getParent();
						win.close();	
						((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_SUSSEFULL_ARG_1, 
								"Die Mengeneinhet"));	
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						((Application) UI.getCurrent().getData())
							.showDialog(String.format(ArtikelverwaltungView.MESSAGE_EXISTS_ARG_1, nameField.getValue()));
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
				win = (Window) MengeneinheitErstellen.this.getParent();
				win.close();
			}
		});	
		
		m_deaktivierenButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2701157762823717701L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {					
					sqlStatement(1);
					((Application) UI.getCurrent().getData())
	             		.showDialog("Mengeneinheit <" + m_mengeneinheit.getName() + "> wurde gelöscht");
					win = (Window) MengeneinheitErstellen.this.getParent();
					win.close();
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					((Application) UI.getCurrent().getData())
					.showDialog("Die Mengeneinheit <" + m_mengeneinheit.getName() + "> darf nicht gelöscht werden, " +
                  	"weil sie an Artikeln hängt.");   
					win = (Window) MengeneinheitErstellen.this.getParent();
					win.close();
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void sqlStatement(int i) throws ConnectException, DAOException, SQLException {
		if(i == 0) {
			if(!create) {
				m_mengeneinheit.setName(nameField.getValue());
				m_mengeneinheit.setKurz(kurzField.getValue());
				Mengeneinheitverwaltung.getInstance().updateMengeneinheit(m_mengeneinheit);
			} else {
				m_mengeneinheit = new Mengeneinheit(nameField.getValue(), kurzField.getValue());
				Mengeneinheitverwaltung.getInstance().createMengeneinheit(m_mengeneinheit);
			}
		} else if(i == 1) {
			Mengeneinheitverwaltung.getInstance().deleteMengeneinheit(m_mengeneinheit.getId());
		} 
		
	}

	@Override
	public boolean validiereEingabe() {
		boolean isTrue = true;
		if (nameField.getValue().equals("")) {
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
	public VerticalLayout boxLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);
		
		box.addComponent(m_headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(nameField);
		box.addComponent(kurzField);
	
		box.addComponent(m_horizontalLayout);
		box.setComponentAlignment(m_horizontalLayout, Alignment.MIDDLE_RIGHT);
		return box;
	}	
}

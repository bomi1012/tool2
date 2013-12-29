package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.view.IErstellen;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

public class KategorieErstellen extends OverErstellen implements View,
ValueChangeListener, IErstellen  {
	private static final long serialVersionUID = 115073498815246131L;
	
	public KategorieErstellen() {
		super();
		layout(NEW_KATEGORIE);
	}
	public KategorieErstellen(Kategorie kategorie){
		super();
		m_create = false;				
		m_kategorie = kategorie; 
		layout(EDIT_KATEGORIE);
		m_deaktivierenButton.setVisible(true);
	}
		
	private void layout(String text) {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, text, STYLE_HEADLINE);		
		/** Fields */
		nameField = textFieldSettingKE(m_textField, KATEGORIE,
				ArtikelverwaltungView.FULL, true, KATEGORIE, this);
		if(!m_create) {
			nameField.setValue(m_kategorie.getName());
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
						win = (Window) KategorieErstellen.this.getParent();
						win.close();	
						((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_SUSSEFULL_ARG_1, 
								KATEGORIE));
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
				win = (Window) KategorieErstellen.this.getParent();
				win.close();
			}
		});
		
		m_deaktivierenButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2701157762823717701L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {					
					sqlStatement(1);
					m_okRemove = true;
					((Application) UI.getCurrent().getData())
	             		.showDialog("Kategorie <" + m_kategorie.getName() + "> wurde gelöscht");
					win = (Window) KategorieErstellen.this.getParent();
					win.close();
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					((Application) UI.getCurrent().getData())
					.showDialog("Die Kategorie <" + m_kategorie.getName() + "> darf nicht gelöscht werden, " +
                  	"weil sie an Artikeln hängt.");  
					m_okRemove = false;
					win = (Window) KategorieErstellen.this.getParent();
					win.close();
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
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
				m_kategorie.setName(nameField.getValue());
				Kategorienverwaltung.getInstance().updateKategorie(m_kategorie);
			} else {
				m_kategorie = new Kategorie(nameField.getValue());
				Kategorienverwaltung.getInstance().createKategorie(m_kategorie);
			}
		} else if(i == 1) {
			Kategorienverwaltung.getInstance().deleteKategorie(m_kategorie.getId());
		} 
	}
	
	@Override
	public boolean validiereEingabe() {
		if (nameField.getValue().equals("")) {
			((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelverwaltungView.MESSAGE_LEER_ARG_1, "Name"));
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
		box.addComponent(nameField);	
		box.addComponent(m_control);
		box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		return box;
	}
}

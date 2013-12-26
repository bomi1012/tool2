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
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

public class MengeneinheitErstellen extends ArtikelAbstract implements View,
ValueChangeListener  {
	private static final long serialVersionUID = 115073498815246131L;
	private TextField kurzField;
	private Mengeneinheit mengeneinheit;
	public MengeneinheitErstellen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		nameField = textFieldSettingME(nameField, "Mengeneinheit",
				ArtikelAbstract.FULL, true, "Mengeneinheit", this);
		kurzField = textFieldSettingME(kurzField, "Abkürzung",
				ArtikelAbstract.FULL, true, "Abkürzung", this);
		speichernButton = buttonSetting(speichernButton, IConstants.BUTTON_SAVE,
				IConstants.BUTTON_SAVE_ICON);
		verwerfenButton = buttonSetting(verwerfenButton, IConstants.BUTTON_DISCARD,
				IConstants.BUTTON_DISCARD_ICON);
		
		headlineLabel = new Label(ArtikelAbstract.NEW_MENGENEINHEIT);
		headlineLabel.setStyleName("ViewHeadline");
		
		/** ControlPanel */
		controlHL = new HorizontalLayout();
		controlHL.setSpacing(true);
		controlHL.addComponent(verwerfenButton);
		controlHL.addComponent(speichernButton);
		
		boxVL = boxLayout(boxVL, "450");
		this.addComponent(boxVL);
		this.setComponentAlignment(boxVL, Alignment.MIDDLE_CENTER);		
		
		speichernButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8358053287073859472L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					try {
						addToDataBase();
						Window win = (Window) MengeneinheitErstellen.this.getParent();
						win.close();	
						((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelAbstract.MESSAGE_SUSSEFULL_ARG_1, 
								"Die Mengeneinhet"));	
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						((Application) UI.getCurrent().getData())
							.showDialog(String.format(ArtikelAbstract.MESSAGE_EXISTS_ARG_1, nameField.getValue()));
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		verwerfenButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2701157762823717701L;
			@Override
			public void buttonClick(ClickEvent event) {
				Window win = (Window) MengeneinheitErstellen.this.getParent();
				win.close();
			}
		});		
	}
	@Override
	public void valueChange(ValueChangeEvent event) { }
	@Override
	public void getViewParam(ViewData data) { }
	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //
	
	private void addToDataBase() throws ConnectException, DAOException, SQLException {
		mengeneinheit = new Mengeneinheit(nameField.getValue(), kurzField.getValue());
		Mengeneinheitverwaltung.getInstance().createMengeneinheit(mengeneinheit);
	}

	private boolean validiereEingabe() {
		boolean isTrue = true;
		if (nameField.getValue().equals("")) {
			((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelAbstract.MESSAGE_LEER_ARG_1, "Name"));
			isTrue = false;
		}
		if (kurzField.getValue().equals("")) {
			kurzField.setValidationVisible(true);
			((Application) UI.getCurrent().getData()).showDialog(String.format(ArtikelAbstract.MESSAGE_LEER_ARG_1, "Abkürzung"));
			isTrue = false;
		}
		return isTrue;
	}
	private VerticalLayout boxLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);
		
		box.addComponent(headlineLabel);
		box.addComponent(new Hr());
		box.addComponent(nameField);
		box.addComponent(kurzField);
	
		box.addComponent(controlHL);
		box.setComponentAlignment(controlHL, Alignment.MIDDLE_RIGHT);
		return box;
	}	
}

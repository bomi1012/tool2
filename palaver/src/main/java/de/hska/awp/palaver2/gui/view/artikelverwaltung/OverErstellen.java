package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class OverErstellen extends ArtikelverwaltungView implements View,
ValueChangeListener {

	protected Button m_speichernButton;
	protected Button m_verwerfenButton;
	protected Button m_deaktivierenButton;
	protected HorizontalLayout m_control;
	protected TextField nameField;
		
	protected HorizontalLayout controlErstellenPanel() {	
		m_deaktivierenButton = buttonSetting(m_button, IConstants.BUTTON_DELETE,
				IConstants.ICON_PAGE_DELETE, false, true);
		m_verwerfenButton = buttonSetting(m_button, IConstants.BUTTON_DISCARD,
				IConstants.ICON_PAGE_REFRESH, true, true);
		m_speichernButton = buttonSetting(m_button, IConstants.BUTTON_SAVE,
				IConstants.ICON_PAGE_SAVE, true, true);
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_verwerfenButton);
		m_horizontalLayout.addComponent(m_deaktivierenButton);
		m_horizontalLayout.addComponent(m_speichernButton);
		return m_horizontalLayout;
	}
	
	protected TextField textFieldSettingKE(TextField field, String name,
			String width, boolean required, String descript,
			KategorieErstellen kErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(kErstellen);
		return field;
	}
	protected TextField textFieldSettingAE(TextField field, String name,
			String width, boolean required, String descript,
			ArtikelErstellen aErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(aErstellen);
		return field;
	}
	protected TextField textFieldSettingME(TextField field, String name,
			String width, boolean required, String descript,
			MengeneinheitErstellen mErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(mErstellen);
		return field;
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) { }
}

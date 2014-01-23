package de.palaver.view.artikelverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class OverErstellen extends OverArtikelverwaltungView implements View,
ValueChangeListener {

	public HorizontalLayout m_control;
	public TextField m_nameField;
			
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

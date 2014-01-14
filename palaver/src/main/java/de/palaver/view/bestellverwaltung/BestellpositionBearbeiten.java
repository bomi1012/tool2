package de.palaver.view.bestellverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.bestellverwaltung.Bestellposition;

@SuppressWarnings("serial")
public class BestellpositionBearbeiten extends OverBestellverwaltungView implements View,
ValueChangeListener {
	
	private TextField m_liefermengeField_1;
	private TextField m_liefermengeField_2;

	public BestellpositionBearbeiten() {
		
	}
	
	public BestellpositionBearbeiten(Bestellposition bestellposition) {
		m_bestellposition = bestellposition;
		layout();
		setData();
	}
	
	private void setData() {
		m_liefermengeField_1.setValue(m_bestellposition.getLiefermenge1().toString());
		m_liefermengeField_2.setValue(m_bestellposition.getLiefermenge2().toString());
		
	}

	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, m_bestellposition.getArtikel().getName() + " ändern", STYLE_HEADLINE);
		
		m_liefermengeField_1 = textFieldSettingLM1(m_textField, "Liefermenge 1", FULL, false, "Liefermenge 1", this);
		m_liefermengeField_2 = textFieldSettingLM1(m_textField, "Liefermenge 2", FULL, false, "Liefermenge 2", this);
		
		m_vertikalLayout = addToLayout(m_vertikalLayout, "450");
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);
	}

	private VerticalLayout addToLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);		
		box.addComponent(m_headlineLabel);
		box.addComponent(m_liefermengeField_1);	
		box.addComponent(m_liefermengeField_2);
		//box.addComponent(m_control);
		//box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		return box;
	}

	private TextField textFieldSettingLM1(TextField field, String name,
			String width, boolean required, String descript,
			BestellpositionBearbeiten bestellpositionBearbeiten) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(bestellpositionBearbeiten);
		return field;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		
	}

	@Override
	public void getViewParam(ViewData data) {
		
	}
}

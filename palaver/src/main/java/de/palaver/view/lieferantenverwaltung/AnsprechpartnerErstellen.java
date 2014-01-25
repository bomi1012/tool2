package de.palaver.view.lieferantenverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;

@SuppressWarnings("serial")
public class AnsprechpartnerErstellen extends OverLieferantverwaltungView implements View,
ValueChangeListener {

	private TextField m_nameField;
	private TextField m_bezeichnungField;
	private TextField m_telefonField;
	private TextField m_handyField;
	private TextField m_faxField;
	private TextField m_emailField;
	private TextField m_webField;
	private TextField m_strasseField;
	private TextField m_housenummerField;
	private TextField m_stadtField;
	private TextField m_plzField;
	private TextField m_landField;
	private VerticalLayout m_leftVLayout;
	private VerticalLayout m_centerKVLayout;
	private VerticalLayout m_centerAVLayout;
	private HorizontalLayout m_windowHLayout;

	public AnsprechpartnerErstellen() {
		super();
		layout();
	}
	
	public AnsprechpartnerErstellen(Lieferant lieferant) {
		super();
	}
	
	public AnsprechpartnerErstellen(Ansprechpartner ansprechpartner) {
		super();
		layout();
	}
	
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		
		m_nameField = textFieldSetting(m_textField, "Ansprechpartnername",
				FULL, true, "Ansprechpartnername", this);
		m_bezeichnungField = textFieldSetting(m_textField, "Bezeichnung",
				FULL, false, "Bezeichnung", this);
		
		m_telefonField = textFieldSetting(m_textField, "Telefonnummer",
				FULL, false, "Telefonnummer", this);
		m_handyField = textFieldSetting(m_textField, "Handynummer",
				FULL, false, "Handynummer", this);
		m_faxField = textFieldSetting(m_textField, "Fax",
				FULL, false, "Fax", this);
		m_emailField = textFieldSetting(m_textField, "E-Mail",
				FULL, false, "E-Mail", this);
		m_webField = textFieldSetting(m_textField, "Web-Seite",
				FULL, false, "Web-Seite", this);
		
		
		m_strasseField = textFieldSetting(m_textField, "Strasse",
				FULL, false, "Strasse", this);
		m_housenummerField = textFieldSetting(m_textField, "Hausnummer",
				FULL, false, "Hausnummer", this);
		m_stadtField = textFieldSetting(m_textField, "Stadt",
				FULL, false, "Stadt", this);
		m_plzField = textFieldSetting(m_textField, "PLZ",
				FULL, false, "PLZ", this);
		m_landField = textFieldSetting(m_textField, "Land",
				FULL, false, "Land", this);
		
		m_control = controlErstellenPanel();
		
		
		m_leftVLayout = new VerticalLayout();
		m_leftVLayout.setWidth("90%");
		m_leftVLayout.addComponent(headLine(m_headlineLabel, "Information", "subHeadline"));
		m_leftVLayout.addComponent(new Hr());
		m_leftVLayout.addComponent(m_nameField);
		m_leftVLayout.addComponent(m_bezeichnungField);
		m_leftVLayout.setSpacing(true);
		
		m_centerKVLayout = new VerticalLayout();
		m_centerKVLayout.setWidth("90%");
		m_centerKVLayout.addComponent(headLine(m_headlineLabel, "Kontakte", "subHeadline"));
		m_centerKVLayout.addComponent(new Hr());
		m_centerKVLayout.addComponent(m_telefonField);
		m_centerKVLayout.addComponent(m_handyField);
		m_centerKVLayout.addComponent(m_faxField);
		m_centerKVLayout.addComponent(m_emailField);
		m_centerKVLayout.addComponent(m_webField);
		m_centerKVLayout.setSpacing(true);
		
		m_centerAVLayout = new VerticalLayout();
		m_centerAVLayout.setWidth("90%");
		m_centerAVLayout.addComponent(headLine(m_headlineLabel, "Adresse", "subHeadline"));
		m_centerAVLayout.addComponent(new Hr());
		m_centerAVLayout.addComponent(m_strasseField);
		m_centerAVLayout.addComponent(m_housenummerField);
		m_centerAVLayout.addComponent(m_stadtField);
		m_centerAVLayout.addComponent(m_plzField);
		m_centerAVLayout.addComponent(m_landField);
		m_centerAVLayout.setSpacing(true);
		
		m_windowHLayout = new HorizontalLayout();
		m_windowHLayout.setWidth(FULL);
		m_windowHLayout.setHeight(FULL);
		m_windowHLayout.addComponent(headLine(m_headlineLabel, "Neuer Lieferant", STYLE_HEADLINE));
		m_windowHLayout.addComponent(m_leftVLayout);
		m_windowHLayout.addComponent(m_centerKVLayout);
		m_windowHLayout.addComponent(m_centerAVLayout);
		m_windowHLayout.setExpandRatio(m_leftVLayout, 1);
		m_windowHLayout.setExpandRatio(m_centerKVLayout, 1);
		m_windowHLayout.setExpandRatio(m_centerAVLayout, 1);
		m_windowHLayout.setComponentAlignment(m_leftVLayout, Alignment.TOP_LEFT);
		m_windowHLayout.setComponentAlignment(m_centerKVLayout, Alignment.TOP_CENTER);
		m_windowHLayout.setComponentAlignment(m_centerAVLayout, Alignment.TOP_RIGHT);
		
		m_vertikalLayout = new VerticalLayout();
		m_vertikalLayout.setWidth(FULL);
		m_vertikalLayout.addComponent(m_windowHLayout);
		m_vertikalLayout.addComponent(m_control);
		m_vertikalLayout.setComponentAlignment(m_windowHLayout, Alignment.TOP_CENTER);
		m_vertikalLayout.setComponentAlignment(m_control, Alignment.BOTTOM_RIGHT);
		m_vertikalLayout.setSpacing(true);
		
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) { }

	private TextField textFieldSetting(TextField field, String name,
			String width, boolean required, String descript,
			AnsprechpartnerErstellen ansprechpartnerErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(ansprechpartnerErstellen);
		return field;
	}
	
}

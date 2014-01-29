package de.palaver.view.lieferantenverwaltung.popup;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.Adresse;
import de.palaver.domain.person.Kontakte;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.person.AdresseService;
import de.palaver.service.person.KontakteService;
import de.palaver.service.person.lieferantenverwaltung.AnsprechpartnerService;
import de.palaver.view.lieferantenverwaltung.OverLieferantverwaltungView;

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
	}
	
	public AnsprechpartnerErstellen(Lieferant lieferant) {		
		super();
		m_lieferant = lieferant;
		m_ansprechpartner = null;
		m_create = true;
		layout();
		listeners();
	}
	
	public AnsprechpartnerErstellen(Ansprechpartner ansprechpartner) {
		super();
		m_ansprechpartner = ansprechpartner;
		m_create = false;
		layout();
		listeners();
		m_deaktivierenButton.setVisible(true);
		m_nameField.setValue(ansprechpartner.getName());
		m_bezeichnungField.setValue(ansprechpartner.getBezeichnung());
		
		if(ansprechpartner.getKontakte() != null) {
			m_telefonField.setValue(ansprechpartner.getKontakte().getTelefon());
			m_handyField.setValue(ansprechpartner.getKontakte().getHandy());
			m_faxField.setValue(ansprechpartner.getKontakte().getFax());
			m_emailField.setValue(ansprechpartner.getKontakte().getEmail());
			m_webField.setValue(ansprechpartner.getKontakte().getWww());
		}
		
		if(ansprechpartner.getAdresse() != null) {
			m_strasseField.setValue(ansprechpartner.getAdresse().getStrasse());
			m_housenummerField.setValue(ansprechpartner.getAdresse().getHausnummer());
			m_stadtField.setValue(ansprechpartner.getAdresse().getStadt());
			m_plzField.setValue(ansprechpartner.getAdresse().getPlz());
			m_landField.setValue(ansprechpartner.getAdresse().getLand());
		}
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
	
	private void listeners() {
		m_speichernButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if(validiereEingabe()) {
						sqlStatement(0);
					}
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}								
			}
		});	
		
		m_deaktivierenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					sqlStatement(1);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}
	
	protected void sqlStatement(int i) throws ConnectException, DAOException {
		if(i == 0) {
			if(m_create) {
				if (m_telefonField.getValue() != "" || m_handyField.getValue() != "" || m_faxField.getValue() != "" 
						|| m_emailField.getValue() != "" || m_webField.getValue() != "") {
					m_kontakte = new Kontakte(m_emailField.getValue(), m_handyField.getValue(), m_telefonField.getValue(),
							m_faxField.getValue(), m_webField.getValue());
					m_kontakte.setId(KontakteService.getInstance().createKontakte(m_kontakte));
				}
				if (m_strasseField.getValue() != "" || m_housenummerField.getValue() != "" 
						|| m_stadtField.getValue() != "" || m_plzField.getValue() != ""
						|| m_landField.getValue() != "") {
					m_adresse = new Adresse(m_strasseField.getValue(), m_housenummerField.getValue(), 
							m_stadtField.getValue(), m_plzField.getValue(), m_landField.getValue());
					m_adresse.setId(AdresseService.getInstance().createAdresse(m_adresse));
				}
				m_ansprechpartner = new Ansprechpartner(m_nameField.getValue(), m_lieferant,
						m_bezeichnungField.getValue(), m_adresse, m_kontakte);
				m_ansprechpartner.setId(AnsprechpartnerService.getInstance().createAnsprechpartner(m_ansprechpartner));
			}
			else {				
				m_ansprechpartner.setName(m_nameField.getValue());
				m_ansprechpartner.setBezeichnung(m_bezeichnungField.getValue());
				
				if (m_ansprechpartner.getAdresse() != null) {
					if (m_strasseField.getValue() == "" && m_housenummerField.getValue() == "" 
							&& m_stadtField.getValue() == "" && m_plzField.getValue() == ""
							&& m_landField.getValue() == "") { //remove adresse
						AdresseService.getInstance().deleteAdresse(m_ansprechpartner.getAdresse().getId());
						m_ansprechpartner.setAdresse(null);
					} else {
						m_ansprechpartner.getAdresse().setStrasse(m_strasseField.getValue());
						m_ansprechpartner.getAdresse().setHausnummer(m_housenummerField.getValue()); 
						m_ansprechpartner.getAdresse().setStadt(m_stadtField.getValue());
						m_ansprechpartner.getAdresse().setPlz(m_plzField.getValue());
						m_ansprechpartner.getAdresse().setLand(m_landField.getValue());
						AdresseService.getInstance().updateAdresse(m_ansprechpartner.getAdresse());
					}
				} else {
					if (m_strasseField.getValue() != "" || m_housenummerField.getValue() != "" 
							|| m_stadtField.getValue() != "" || m_plzField.getValue() != ""
							|| m_landField.getValue() != "") {
						m_adresse = new Adresse(m_strasseField.getValue(), m_housenummerField.getValue(), 
								m_stadtField.getValue(), m_plzField.getValue(), m_landField.getValue());
						m_adresse.setId(AdresseService.getInstance().createAdresse(m_adresse));
						m_ansprechpartner.setAdresse(m_adresse);
					}
				}
				
				if (m_ansprechpartner.getKontakte() != null) {
					if (m_telefonField.getValue() == "" && m_handyField.getValue() == "" && m_faxField.getValue() == "" 
							&& m_emailField.getValue() == "" && m_webField.getValue() == "") { //remove 
						KontakteService.getInstance().deleteKontakte(m_ansprechpartner.getKontakte().getId());
						m_ansprechpartner.setKontakte(null);
					} else {
						m_ansprechpartner.getKontakte().setTelefon(m_telefonField.getValue());
						m_ansprechpartner.getKontakte().setHandy(m_handyField.getValue()); 
						m_ansprechpartner.getKontakte().setFax(m_faxField.getValue());
						m_ansprechpartner.getKontakte().setEmail(m_emailField.getValue());
						m_ansprechpartner.getKontakte().setWww(m_webField.getValue());
						KontakteService.getInstance().updatekontakte(m_ansprechpartner.getKontakte());
					}
				} else {
					if (m_telefonField.getValue() != "" || m_handyField.getValue() != "" || m_faxField.getValue() != "" 
							|| m_emailField.getValue() != "" || m_webField.getValue() != "") {
						m_kontakte = new Kontakte(m_emailField.getValue(), m_handyField.getValue(), m_telefonField.getValue(),
								m_faxField.getValue(), m_webField.getValue());
						m_kontakte.setId(KontakteService.getInstance().createKontakte(m_kontakte));
						m_ansprechpartner.setKontakte(m_kontakte);
					}
				}
				AnsprechpartnerService.getInstance().updateAnsprechpartner(m_ansprechpartner);
			}
		}	else {
			AnsprechpartnerService.getInstance().deleteAnsprechpartner(m_ansprechpartner.getId());
		}		
	}

	protected boolean validiereEingabe() {
		if (m_nameField.getValue() == "") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_LIEFERANT_NAME);
			return false;
		}
		return true;
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

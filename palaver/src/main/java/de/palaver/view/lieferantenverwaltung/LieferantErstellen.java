package de.palaver.view.lieferantenverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.view.layout.popup.YesNoPopup;

@SuppressWarnings("serial")
public class LieferantErstellen extends OverLieferantverwaltungView implements View,
ValueChangeListener {
	private static final Logger LOG = LoggerFactory.getLogger(LieferantErstellen.class.getName());
	private static final String TEXT = "Neuer Lieferant wurde erstellt. <br> " +
			"Möchten Sie den Ansprechpartner hinzufügen?";
	
	private HorizontalLayout m_windowHLayout;
	private VerticalLayout m_leftVLayout; //Info
	private VerticalLayout m_centerKVLayout; //Kontakte
	private VerticalLayout m_centerAVLayout; //Adresse
	private VerticalLayout m_rightVLayout; //Ansprechpartner
	private TextField m_nameField;
	private TextField m_nummerField;
	private TextArea m_notizField;
	private TextField m_bezeichnungField;
	private CheckBox m_mehrerLieferterminCheckbox = new CheckBox("mehrere Liefertermine");
	private TextField m_telefonField;
	private TextField m_handyField;
	private TextField m_faxField;
	private TextField m_emailField;
	private TextField m_webField;
	private TextField m_landField;
	private TextField m_strasseField;
	private TextField m_housenummerField;
	private TextField m_stadtField;
	private TextField m_plzField;
	private Button btFehler;
	private YesNoPopup m_yesNoPopup;
	private AnsprechpartnerErstellen m_ansprechpartnerErstellen;
	
	
	public LieferantErstellen(Lieferant lieferant) {
		super();
		m_lieferant = lieferant;
		m_create = false;
		layout(lieferant);
		listeners();
	}
	
	public LieferantErstellen() {
		super();
		m_lieferant = new Lieferant();
		m_kontakte = null;
		m_adresse = null;
		m_create = true;
		layout(null);
		listeners();
	}
	
	private void layout(Lieferant lieferant) {
		this.setSizeFull();
		this.setMargin(true);		
		m_nameField = textFieldSetting(m_textField, "Lieferantname",
				FULL, true, "Lieferantname", this);
		m_nummerField = textFieldSetting(m_textField, "Lieferantnummer",
				FULL, false, "Lieferantnummer", this);
		m_bezeichnungField = textFieldSetting(m_textField, "Bezeichnung",
				FULL, false, "Bezeichnung", this);
		m_notizField = new TextArea("Kommentar");
		m_notizField.setWidth(FULL);
		m_notizField.setHeight("60");		
		
		
		
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
		
//		btFehler = new Button(" hallo!");
//		btFehler.setPrimaryStyleName(BaseTheme.BUTTON_LINK);
//		btFehler.setStyleName("cursor-hand");
//		btFehler.setStyleName("lieferant");
//		btFehler.setIcon(new ThemeResource("icons/user_add.png"));
		
		m_leftVLayout = new VerticalLayout();
		m_leftVLayout.setWidth("90%");
		m_leftVLayout.addComponent(headLine(m_headlineLabel, "Persönliche Daten", "subHeadline"));
		m_leftVLayout.addComponent(new Hr());
		m_leftVLayout.addComponent(m_nameField);
		m_leftVLayout.addComponent(m_nummerField);
		m_leftVLayout.addComponent(m_bezeichnungField);
		m_leftVLayout.addComponent(m_notizField);
		m_leftVLayout.addComponent(m_mehrerLieferterminCheckbox);
		m_leftVLayout.setSpacing(true);
		
		m_centerKVLayout = new VerticalLayout();
		m_centerKVLayout.setWidth("90%");
		m_centerKVLayout.addComponent(headLine(m_headlineLabel, "Kontaktdaten", "subHeadline"));
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
		
//		m_rightVLayout = new VerticalLayout();
//		m_rightVLayout.setWidth("90%");
//		m_rightVLayout.addComponent(headLine(m_headlineLabel, "Ansprechpartner", "subHeadline"));
//		m_rightVLayout.addComponent(new Hr());
//		m_rightVLayout.addComponent(btFehler);
//		m_rightVLayout.setSpacing(true);
		
		
		m_windowHLayout = new HorizontalLayout();
		m_windowHLayout.setWidth(FULL);
		m_windowHLayout.setHeight(FULL);
		m_windowHLayout.addComponent(headLine(m_headlineLabel, "Neuer Lieferant", STYLE_HEADLINE));
		m_windowHLayout.addComponent(m_leftVLayout);
		m_windowHLayout.addComponent(m_centerKVLayout);
		m_windowHLayout.addComponent(m_centerAVLayout);
//		m_windowHLayout.addComponent(m_rightVLayout);
		m_windowHLayout.setExpandRatio(m_leftVLayout, 1);
		m_windowHLayout.setExpandRatio(m_centerKVLayout, 1);
		m_windowHLayout.setExpandRatio(m_centerAVLayout, 1);
//		m_windowHLayout.setExpandRatio(m_rightVLayout, 1);
		m_windowHLayout.setComponentAlignment(m_leftVLayout, Alignment.TOP_LEFT);
		m_windowHLayout.setComponentAlignment(m_centerKVLayout, Alignment.TOP_CENTER);
		m_windowHLayout.setComponentAlignment(m_centerAVLayout, Alignment.TOP_RIGHT);
//		m_windowHLayout.setComponentAlignment(m_rightVLayout, Alignment.TOP_LEFT);
		
		m_vertikalLayout = new VerticalLayout();
		m_vertikalLayout.setWidth(FULL);
		m_vertikalLayout.addComponent(headLine(m_headlineLabel, "Neuer Lieferant", STYLE_HEADLINE));
		m_vertikalLayout.addComponent(m_windowHLayout);
		m_vertikalLayout.addComponent(m_control);
		m_vertikalLayout.setComponentAlignment(m_windowHLayout, Alignment.TOP_CENTER);
		m_vertikalLayout.setComponentAlignment(m_control, Alignment.BOTTOM_RIGHT);
		
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
						windowModal();
					}
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}								
			}
		});	
		
		m_verwerfenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();	
			}
		});
	}

	private void close() {
		if (LieferantErstellen.this.getParent() instanceof Window) {					
			Window win = (Window) LieferantErstellen.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(LieferantAnzeigen.class);
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

	protected void sqlStatement(int i) throws ConnectException, DAOException {
//		if(i == 0) {
//			if(m_create) {
//				if (m_telefonField.getValue() != "" || m_handyField.getValue() != "" || m_faxField.getValue() != "" 
//						|| m_emailField.getValue() != "" || m_webField.getValue() != "") {
//					m_kontakte = new Kontakte(m_emailField.getValue(), m_handyField.getValue(), m_telefonField.getValue(),
//							m_faxField.getValue(), m_webField.getValue());
//					m_kontakte.setId(KontakteService.getInstance().createKontakte(m_kontakte));
//				}
//				if (m_strasseField.getValue() != "" || m_housenummerField.getValue() != "" 
//						|| m_stadtField.getValue() != "" || m_plzField.getValue() != ""
//						|| m_landField.getValue() != "") {
//					m_adresse = new Adresse(m_strasseField.getValue(), m_housenummerField.getValue(), 
//							m_stadtField.getValue(), m_plzField.getValue(), m_landField.getValue());
//					m_adresse.setId(AdresseService.getInstance().createAdresse(m_adresse));
//				}
//				m_lieferant = new Lieferant(m_nameField.getValue(), m_nummerField.getValue(),
//						m_bezeichnungField.getValue(), m_mehrerLieferterminCheckbox.getValue(), m_notizField.getValue(),
//						m_adresse, m_kontakte);
//				m_lieferant.setId(LieferantenService.getInstance().createLieferant(m_lieferant));
//			}
//		}		
	}

	protected void windowModal() {
		m_window = windowUI(m_window, "", "450", "180");		
		m_yesNoPopup = new YesNoPopup("32x32/user.png", TEXT);
		addComponent(m_yesNoPopup);
		m_window.setContent(m_yesNoPopup);
		m_window.setModal(true);
		UI.getCurrent().addWindow(m_window);
		m_yesNoPopup.m_yesButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				m_window.close();
				windowModalAnspechpartner();
			}
		});
		m_yesNoPopup.m_noButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_window.close();
				close();
			}
		});
	}

	protected void windowModalAnspechpartner() {
		m_window = windowUI(m_window, "", "90%", "90%");		
		m_ansprechpartnerErstellen = new AnsprechpartnerErstellen(m_lieferant);
		addComponent(m_ansprechpartnerErstellen);
		m_window.setContent(m_ansprechpartnerErstellen);
		m_window.setModal(true);
		UI.getCurrent().addWindow(m_window);
		
		m_ansprechpartnerErstellen.m_verwerfenButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				m_window.close();				
			}
		});
	}
	
	
	private TextField textFieldSetting(TextField field, String name,
			String width, boolean required, String descript,
			LieferantErstellen lieferantErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(lieferantErstellen);
		return field;
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) { }

}

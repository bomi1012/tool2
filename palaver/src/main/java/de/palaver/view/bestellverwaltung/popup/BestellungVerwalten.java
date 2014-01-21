package de.palaver.view.bestellverwaltung.popup;

import java.io.File;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellungService;
import de.palaver.service.emailversand.MailService;
import de.palaver.view.bestellverwaltung.OverBestellverwaltungView;

@SuppressWarnings("serial")
public class BestellungVerwalten extends OverBestellverwaltungView implements View,
ValueChangeListener {

	private VerticalLayout m_left;
	private VerticalLayout m_center;
	private TextField m_empafaengerField;
	private VerticalLayout m_ttt;
	private TextField m_betreffField;
	private TextArea m_nachrichtArea;
	private Button m_sendenButton;
	private VerticalLayout m_right;
	private Image m_imageExcel;
	private Button m_downloadButton;
	private String m_anhangPath;

	public BestellungVerwalten() {
		super();
	}
	
	public BestellungVerwalten(Bestellung bestellung) {
		super();
		m_bestellung = bestellung;
		layout();
		listeners();
	}
	
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		
		/** menü */
		m_left = new VerticalLayout();	
		m_left.setWidth("95%");
		m_left.setHeight("100%");
		m_ttt = controlVerwaltung();
		m_left.addComponent(m_ttt);
		
		
		/** content */
		m_sendenButton = buttonSetting(m_button, "Senden",
				IConstants.ICON_EMAIL_GO, true, true);
		m_downloadButton = buttonSetting(m_button, "Download",
				IConstants.ICON_EMAIL_ATTACH, true, true);
		
		m_center = new VerticalLayout();
		m_center.setWidth("95%");
		m_center.setHeight("95%");
		/** ... */
		
		/**m_right*/
		m_right = new VerticalLayout();
		m_right.setWidth("95%");
		
		setLayout();
		
	}
	
	private void setLayout() {
		if(m_horizontalLayout != null) {
			this.removeComponent(m_horizontalLayout);
		}
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setWidth("95%");
		m_horizontalLayout.setHeight("95%");
		m_horizontalLayout.addComponent(m_left);
		m_horizontalLayout.addComponent(m_center);
		m_horizontalLayout.addComponent(m_right);
		m_horizontalLayout.setExpandRatio(m_left, 1);
		m_horizontalLayout.setExpandRatio(m_center, 4);
		m_horizontalLayout.setExpandRatio(m_right, 1);
		m_horizontalLayout.setComponentAlignment(m_left, Alignment.TOP_LEFT);
		m_horizontalLayout.setComponentAlignment(m_center, Alignment.TOP_RIGHT);
		m_horizontalLayout.setComponentAlignment(m_right, Alignment.TOP_RIGHT);
		
		
		this.setSpacing(true);				
		this.addComponent(m_horizontalLayout);		
	}

	private void listeners() {
		m_emailBestellenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				designEmail();
				excelGenerieren(m_bestellung);
			}
		});	
		
		m_sendenButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				boolean result = MailService.getInstance().EmailVersand(m_empafaengerField.getValue(),
						m_betreffField.getValue(), m_nachrichtArea.getValue(), m_anhangPath);
				
			}
		});
		
	}

	private void designEmail() {
		m_headlineLabel = headLine(m_headlineLabel, "Bestellug per E-Mail an " + 
							m_bestellung.getLieferant().getName() + " senden", STYLE_HEADLINE);
		m_center = new VerticalLayout();
		m_center.setWidth("95%");
		m_empafaengerField = textFieldSettingE(m_textField, "Empfänger", "45%", true, "Empfänger", this);
		m_betreffField = textFieldSettingE(m_textField, "Betreff", "45%", false, "Betreff", this);
		m_nachrichtArea = new TextArea("Nachricht");
		m_nachrichtArea.setWidth("100%");
		m_nachrichtArea.setHeight("85%");
		
		m_control = new HorizontalLayout();
		m_control.addComponent(m_downloadButton);
		m_control.addComponent(m_sendenButton);
			
		m_imageExcel = new Image();
		m_imageExcel.setSource(new ThemeResource(IConstants.IMAGE_32_ANHANG));
		m_imageExcel.addStyleName("cursor-hand");
		m_imageExcel.setDescription("Bestellung als excel-Datei herunterladen");
		
		m_center.addComponent(m_headlineLabel);
		m_center.addComponent(m_empafaengerField);
		m_center.addComponent(m_betreffField);
		m_center.addComponent(m_nachrichtArea);
		m_center.addComponent(m_control);
		m_center.addComponent(new Hr());
		
		m_center.setComponentAlignment(m_control, Alignment.TOP_RIGHT);
		m_center.setSpacing(true);
	
		
		////////////////////
		setLayout();
		////////////////////
		if(m_bestellung.getLieferant().getKontakte().getEmail() != null) {
			m_empafaengerField.setValue(m_bestellung.getLieferant().getKontakte().getEmail());
		}
		m_betreffField.setValue("Bestellung - Muster");
	}
	
	private TextField textFieldSettingE(TextField field, String name,
			String width, boolean required, String descript,
			BestellungVerwalten bestellungVerwalten) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(bestellungVerwalten);
		return field;
	}
	
	private void excelGenerieren(Bestellung bestellung) {
		m_anhangPath = BestellungService.getInstance().createExcel(bestellung);
		Resource resource = new FileResource(new File(m_anhangPath));
		FileDownloader fileDownloader = new FileDownloader(resource);
		fileDownloader.extend(m_downloadButton);
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

package de.palaver.view.bestellverwaltung.popup;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellpositionService;
import de.palaver.service.bestellverwaltung.BestellungService;
import de.palaver.service.emailversand.MailService;
import de.palaver.view.bestellverwaltung.OverBestellverwaltungView;
import de.palaver.view.models.WarenannahmeModel;

@SuppressWarnings("serial")
public class BestellungVerwalten extends OverBestellverwaltungView implements View,
ValueChangeListener {
	private static final Logger LOG = LoggerFactory.getLogger(BestellungVerwalten.class.getName());

	private VerticalLayout m_left;
	private VerticalLayout m_center;
	private TextField m_empafaengerField;
	private VerticalLayout m_vLT;
	private TextField m_betreffField;
	private TextArea m_nachrichtArea;
	private Button m_sendenButton;
	private VerticalLayout m_right;
	private Image m_imageExcel;
	private Button m_downloadButton;
	private String m_anhangPath;
	public CheckBox m_bestelltCheckBox;
	private List<WarenannahmeModel>  m_warenannahmes;
	private BeanItemContainer<WarenannahmeModel> m_container;

	public BestellungVerwalten() {
		super();
	}
	
	public BestellungVerwalten(Bestellung bestellung) {
		super();
		m_bestellung = bestellung;
		m_bestelltCheckBox.setValue(bestellung.getStatus());
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
		m_vLT = controlVerwaltung();
		m_left.addComponent(m_vLT);
		
		
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
		
		if(!m_bestellung.getStatus()) {
			bestellen();
		}
	}
	
	private void bestellen() {
		besstelenLayout();
		excelGenerieren(m_bestellung);
	}
	private void annahme() throws ConnectException, DAOException, SQLException {
		annahmeLayout();
		beans(m_bestellung);
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
		m_bestellenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bestellen();
			}
		});	
		
		m_annahmeButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					annahme();
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		/** ******************************** */
		m_sendenButton.addClickListener(new ClickListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void buttonClick(ClickEvent event) {
				boolean result = MailService.getInstance().EmailVersand(m_empafaengerField.getValue(),
						m_betreffField.getValue(), m_nachrichtArea.getValue(), m_anhangPath);
				if(result == true) {
					m_bestellung.setStatus(true);
					m_bestellungService.getInstance().updateBestellung(m_bestellung); //TODO
				}				
			}
		});		
	}

	private void besstelenLayout() {
		m_center = new VerticalLayout();
		m_center.setWidth("95%");
		m_empafaengerField = textFieldSettingE(m_textField, "Empfänger", "45%", true, "Empfänger", this);
		m_betreffField = textFieldSettingE(m_textField, "Betreff", "45%", false, "Betreff", this);
		m_nachrichtArea = new TextArea("Nachricht");
		m_nachrichtArea.setWidth("100%");
		m_nachrichtArea.setHeight("85%");
		m_bestelltCheckBox = new CheckBox(" bestellt");
		String message = "";
		boolean kontakt = false;
		if(m_bestellung.getLieferant().getKontakte() != null) {
			if(m_bestellung.getLieferant().getKontakte().getTelefon() != null 
					&& m_bestellung.getLieferant().getKontakte().getTelefon().length() > 1) {
				message += "<b>Telefonnummer:</b> " + m_bestellung.getLieferant().getKontakte().getTelefon() + "<br>";
				kontakt = true;
			}
			if(m_bestellung.getLieferant().getKontakte().getHandy() != null 
					&& m_bestellung.getLieferant().getKontakte().getHandy().length() > 1) {
				message += "<b>Handynummer:</b> " + m_bestellung.getLieferant().getKontakte().getHandy() + "<br>";
				kontakt = true;
			}
			if(m_bestellung.getLieferant().getKontakte().getFax() != null 
					&& m_bestellung.getLieferant().getKontakte().getFax().length() > 1) {
				message += "<b>Fax:</b> " + m_bestellung.getLieferant().getKontakte().getFax() + "<br>";
				kontakt = true;
			}
		} else {
			message = "<br>Keine Kontaktinformation verfügbar";
		}
		if(!kontakt) {
			message = "<br>Keine Kontaktinformation verfügbar";
		}
		
		m_control = new HorizontalLayout();
		m_control.addComponent(m_downloadButton);
		m_control.addComponent(m_sendenButton);
			
		m_imageExcel = new Image();
		m_imageExcel.setSource(new ThemeResource(IConstants.IMAGE_32_ANHANG));
		m_imageExcel.addStyleName("cursor-hand");
		m_imageExcel.setDescription("Bestellung als excel-Datei herunterladen");
		
		m_center.addComponent( headLine(m_headlineLabel, "Bestellug per E-Mail an " + 
				m_bestellung.getLieferant().getName() + " senden", STYLE_HEADLINE));
		m_center.addComponent(m_empafaengerField);
		m_center.addComponent(m_betreffField);
		m_center.addComponent(m_nachrichtArea);
		m_center.addComponent(m_control);
		m_center.addComponent(new Hr());
		m_center.addComponent( headLine(m_headlineLabel, "Bestellung per Telefon an " + 
				m_bestellung.getLieferant().getName(), STYLE_HEADLINE));
		m_center.addComponent(new Label(message, ContentMode.HTML));
		m_center.addComponent(m_bestelltCheckBox);
		
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
	
	private void annahmeLayout() {
		m_center = new VerticalLayout();
		m_center.setWidth("95%");
		
		m_filterTable = new FilterTable();
		m_filterTable.setWidth("95%");
		m_filterTable.setSelectable(true);
		m_filterTable.setFilterBarVisible(true);
		m_filterTable.setFilterGenerator(new customFilter());
		m_filterTable.setFilterDecorator(new customFilterDecorator());
		m_filterTable.setSelectable(true);
		
		m_center.addComponent(m_filterTable);
		m_center.setComponentAlignment(m_filterTable, Alignment.TOP_CENTER);
		m_center.setSpacing(true);
		
		
		////////////////////
		setLayout();
		////////////////////
	}
	
	private void beans(Bestellung bestellung) throws ConnectException, DAOException, SQLException {
		m_warenannahmes = new ArrayList<WarenannahmeModel>();
		m_bestellpositions = BestellpositionService.getInstance().getBestellpositionenByBestellungId(bestellung.getId());
		
		for (Bestellposition bestellposition : m_bestellpositions) {
			WarenannahmeModel wm = new WarenannahmeModel(bestellposition);
			m_warenannahmes.add(wm);
		}
		
		try {
			m_container = new BeanItemContainer<WarenannahmeModel>(WarenannahmeModel.class, m_warenannahmes);
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}	
	}
	
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		if(m_bestellung.getLieferant().isMehrereliefertermine()) {
		m_filterTable.setVisibleColumns(new Object[] { FIELD_ARTIKEL_NAME, FIELD_BESTELLGROESSE_LT1, "geliefertLT1",
				FIELD_BESTELLGROESSE_LT2, "geliefertLT2"});
		} else {
			m_filterTable.setVisibleColumns(new Object[] { FIELD_ARTIKEL_NAME, FIELD_BESTELLGROESSE_LT1, "geliefertLT1"});
		}
		m_filterTable.sort(new Object[] { FIELD_ARTIKEL_NAME }, new boolean[] { true });
		m_filterTable.setColumnWidth("geliefertLT1", 55);
		m_filterTable.setColumnHeader("geliefertLT1", "OK");	
		m_filterTable.setColumnWidth(FIELD_BESTELLGROESSE_LT1, 70);
		m_filterTable.setColumnHeader(FIELD_BESTELLGROESSE_LT1, "menge");	
		
		if(m_bestellung.getLieferant().isMehrereliefertermine()) {
			m_filterTable.setColumnWidth("geliefertLT2", 55);
			m_filterTable.setColumnHeader("geliefertLT2", "geliefert");	
			m_filterTable.setColumnWidth(FIELD_BESTELLGROESSE_LT2, 70);
			m_filterTable.setColumnHeader(FIELD_BESTELLGROESSE_LT2, "menge");
		}
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

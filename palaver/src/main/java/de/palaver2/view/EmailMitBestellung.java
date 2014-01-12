package de.palaver2.view;
//package de.hska.awp.palaver2.gui.view;
//
//import java.io.File;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.server.FileDownloader;
//import com.vaadin.server.FileResource;
//import com.vaadin.server.Resource;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.TextArea;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import de.hska.awp.palaver.Application;
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
//import de.hska.awp.palaver2.emailversand.Mail;
//import de.hska.awp.palaver2.excel.CreateExcelFile;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewDataObject;
//import de.hska.awp.palaver2.util.ViewHandler;
//
///**
// * 
// * @author Mihail Boehm
// * 
// */
//@SuppressWarnings("serial")
//public class EmailMitBestellung extends VerticalLayout implements View {
//	// Upload.Receiver,
//	
//	private static final Logger	log	= LoggerFactory.getLogger(EmailMitBestellung.class.getName());
//
//	private VerticalLayout fenster = new VerticalLayout();
//
//	private Label headline;
//
//	private TextField empfaenger = new TextField("Empfänger");
//	private TextField betreff = new TextField("Betreff");
//	private TextArea nachricht = new TextArea("Nachricht");
//
//	private String empfaengerInput;
//	private String betreffInput;
//	private String nachrichtInput;
//	private String anhang = null;
//
//	private Bestellung bestellung;
//
//	private Button senden = new Button(IConstants.BUTTON_SENDEN);
//	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
//	private Button download = new Button(IConstants.BUTTON_DOWNLOAD);
//
//	public EmailMitBestellung() {
//		super();
//		this.setSizeFull();
//
//		this.setMargin(true);
//
//		headline = new Label("Bestellung senden");
//		headline.setStyleName("ViewHeadline");
//		
//		empfaenger.setWidth("45%");
//		betreff.setWidth("45%");
//		nachricht.setWidth("100%");
//		nachricht.setHeight("250px");
//
//		download.setVisible(false);
//		download.setIcon(new ThemeResource(IConstants.BUTTON_EXCEL_ICON));
//		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
//		senden.setIcon(new ThemeResource(IConstants.BUTTON_EMAILVERSAND_ICON));
//		
//		fenster.setWidth("550px");
//		fenster.setSpacing(true);
//		fenster.addComponent(headline);
//		fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
//
//		fenster.addComponent(empfaenger);
//		fenster.addComponent(betreff);
//		fenster.addComponent(nachricht);
//
//		HorizontalLayout control = new HorizontalLayout();
//		control.setWidth("80%");
//		control.setSpacing(true);
//
//		control.addComponent(verwerfen);
//		control.addComponent(senden);
//		control.addComponent(download);
//		fenster.addComponent(control);
//
//		this.addComponent(fenster);
//		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
//
//		empfaenger.addValueChangeListener(new ValueChangeListener() {
//
//			public void valueChange(final ValueChangeEvent event) {
//				final String valueString = String.valueOf(event.getProperty()
//						.getValue());
//				empfaengerInput = valueString;
//			}
//		});
//
//		betreff.addValueChangeListener(new ValueChangeListener() {
//
//			public void valueChange(final ValueChangeEvent event) {
//				final String valueString = String.valueOf(event.getProperty()
//						.getValue());
//				betreffInput = valueString;
//			}
//		});
//
//		nachricht.addValueChangeListener(new ValueChangeListener() {
//
//			public void valueChange(final ValueChangeEvent event) {
//				final String valueString = String.valueOf(event.getProperty()
//						.getValue());
//				nachrichtInput = valueString;
//			}
//		});
//
//		senden.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				Mail mail = Mail.getInstance();
//				boolean result = mail.EmailVersand(empfaengerInput,
//						betreffInput, nachrichtInput, anhang);
//				if (result == true) {
//					try {
////						bestellung.setBestellt(true);
////						BestellungService.getInstance().updateBestellungOhneBP(
////								bestellung);
//					} 
//					catch (Exception e) {
//						log.error(e.toString());
//					}
//					((Application) UI.getCurrent().getData()).showDialog("Email wurde gesendet.");
//				}
//				
//			}
//		});
//
//		download.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//
//			}
//		});
//
//		verwerfen.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				ViewHandler.getInstance().switchView(
//						BestellungBearbeitenAuswaehlen.class);
//			}
//		});
//	}
//
//	@Override
//	public void getViewParam(ViewData data) {
//		bestellung = (Bestellung) ((ViewDataObject<?>) data).getData();
//		empfaengerInput = bestellung.getLieferant().getEmail();
//		empfaenger.setValue(empfaengerInput);
//		// Create excel
//		CreateExcelFile cef = CreateExcelFile.getInstance();
//		this.anhang = cef.Create(bestellung);
//		if (this.anhang != null) {
//			download.setVisible(true);
//			Resource res = new FileResource(new File(anhang));
//			FileDownloader fd = new FileDownloader(res);
//			fd.extend(download);
//		}
//	}
//
//	/*
//	 * Callback method to begin receiving the upload.
//	 * 
//	 * @Override public OutputStream receiveUpload(String filename, String
//	 * MIMEType) { // Generiren EXCEL
//	 * 
//	 * // Speichern
//	 * 
//	 * // Attach
//	 * 
//	 * FileOutputStream fos = null; // Output stream to write to file = new
//	 * File(filename); this.anhang = file.getAbsolutePath(); try { // Open the
//	 * file for writing. fos = new FileOutputStream(file); } catch (final
//	 * java.io.FileNotFoundException e) { // Error while opening the file. Not
//	 * reported here. e.printStackTrace(); return null; }
//	 * 
//	 * return fos; // Return the output stream to write to }
//	 */
//}

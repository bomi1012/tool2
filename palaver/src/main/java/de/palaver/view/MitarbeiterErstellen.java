//package de.palaver.view;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.data.validator.EmailValidator;
//import com.vaadin.data.validator.StringLengthValidator;
//import com.vaadin.server.Page;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.PasswordField;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.TwinColSelect;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
//import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Rollenverwaltung;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.Util;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewHandler;
//import de.palaver.Application;
//import de.palaver.management.emploee.Employee;
//import de.palaver.management.emploee.Rollen;
//
///**
// * View 
// * @author Christian Barth
// *
// */
//@SuppressWarnings("serial")
//public class MitarbeiterErstellen extends VerticalLayout implements View {
//
//	private static final Logger log = LoggerFactory.getLogger(MitarbeiterErstellen.class.getName());
//
//	private HorizontalLayout box = new HorizontalLayout();
//	private VerticalLayout fenster = new VerticalLayout();
//
//	private Label headline;
//
//	private TextField name = new TextField("Name");
//	private TextField vorname = new TextField("Vorname");
//	private TextField email = new TextField("E-Mail");
//	private PasswordField passwort = new PasswordField("Passwort");
//	private TextField eintrittsdatum = new TextField("Eintrittsdatum");
//	private TextField austrittsdatum = new TextField("Austrittsdatum");
//	private TextField benutzername = new TextField("Benutzername");
//	private TwinColSelect rollen = new TwinColSelect();
//
//	private Employee employee = new Employee();
//	private List<Rollen> rollenlist = new ArrayList<Rollen>();
//	public String valueString = null;
//
//	private Button speichern = new Button(IConstants.BUTTON_SAVE);
//	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
//
//	public MitarbeiterErstellen() {
//		super();
//		this.setSizeFull();
//		this.setMargin(true);
//
//		headline = new Label("Neuer Mitarbeiter");
//		headline.setStyleName("ViewHeadline");
//
//		name.setWidth("100%");
//		vorname.setWidth("100%");
//		email.setWidth("100%");
//		passwort.setWidth("100%");
//		eintrittsdatum.setWidth("100%");
//		austrittsdatum.setWidth("100%");
//		benutzername.setWidth("100%");
//
//		name.setEnabled(false);
//		vorname.setEnabled(false);
//		email.setEnabled(false);
//		passwort.setEnabled(false);
//		eintrittsdatum.setEnabled(false);
//		austrittsdatum.setEnabled(false);
//		benutzername.setEnabled(false);
//
//		fenster.setWidth("900px");
//		fenster.setSpacing(true);
//
//		VerticalLayout links = new VerticalLayout();
//		links.setWidth("300px");
//		links.setSpacing(true);
//
//		VerticalLayout rechts = new VerticalLayout();
//		rechts.setWidth("300px");
//		rechts.setSpacing(true);
//
//		box.setWidth("900px");
//		box.setSpacing(true);
//		box.addComponent(links);
//
//		links.addComponent(name);
//		links.addComponent(vorname);
//		links.addComponent(benutzername);
//		links.addComponent(passwort);
//		links.addComponent(email);
//
//		rechts.addComponent(eintrittsdatum);
//		rechts.addComponent(austrittsdatum);
//
//		rollen.setWidth("300px");
//		rollen.setRows(5);
//		rollen.setNullSelectionAllowed(true);
//		rollen.setMultiSelect(true);
//		rollen.setImmediate(true);
//		rollen.setLeftColumnCaption("Verf�gbare Rollen");
//		rollen.setRightColumnCaption("Ausgew�hlte Rollen");
//		rollen.setEnabled(false);
//
//		rollen.addValueChangeListener(new ValueChangeListener() {
//			@Override
//			public void valueChange(final ValueChangeEvent event) {
//				valueString = String.valueOf(event.getProperty().getValue());
//			}
//		});
//
//		try {
//			List<Rollen> l = Rollenverwaltung.getInstance().getAllRollen();
//			for (int i = 0; i < l.size(); i++) {
//				rollen.addItem(l.get(i).getId());
//				rollen.setItemCaption(l.get(i).getId(), l.get(i).getName());
//			}
//		} catch (Exception e) {
//
//			log.error(e.toString());
//		}
//
//		rechts.addComponent(rollen);
//
//		box.addComponent(rechts);
//
//		HorizontalLayout control = new HorizontalLayout();
//		control.setWidth("100%");
//		control.setSpacing(true);
//
//		control.addComponent(verwerfen);
//		control.addComponent(speichern);
//
//		rechts.addComponent(control);
//		rechts.setComponentAlignment(control, Alignment.TOP_CENTER);
//
//		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
//		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
//
//		fenster.addComponent(headline);
//		fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
//		fenster.addComponent(box);
//		fenster.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
//
//		this.addComponent(fenster);
//		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
//
//		name.setImmediate(true);
//		name.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3, 45, false));
//		name.setMaxLength(45);
//
//		vorname.setImmediate(true);
//		vorname.addValidator(new StringLengthValidator("Bitte g�ltigen Vornamen eingeben", 3, 45, false));
//		vorname.setMaxLength(45);
//
//		email.setImmediate(true);
//		email.addValidator(new EmailValidator("Bitte g�ltige E-Mailadresse angeben"));
//		email.setMaxLength(45);
//
//		passwort.setImmediate(true);
//		passwort.addValidator(new StringLengthValidator("Bitte g�ltigen Passwort eingeben", 6, 45, false));
//		passwort.setMaxLength(45);
//
//		eintrittsdatum.setImmediate(true);
//		eintrittsdatum.setMaxLength(45);
//
//		austrittsdatum.setImmediate(true);
//		austrittsdatum.setMaxLength(300);
//
//		benutzername.setImmediate(true);
//		benutzername.addValidator(new StringLengthValidator("Bitte g�ltigen Benutzernamen eingeben", 3, 45, false));
//		benutzername.setMaxLength(45);
//
//		verwerfen.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				ViewHandler.getInstance().returnToDefault();
//
//			}
//		});
//
//		speichern.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//
//				if (validiereEingabe()) {
//			
//					employee.setName(name.getValue());
//					employee.setVorname(vorname.getValue());
//					employee.setEmail(email.getValue());
//					try {
//						employee.setPasswort(Util.encryptPassword(passwort.getValue()).toString());
//					} catch (UnsupportedEncodingException e1) {
//						log.error(e1.toString());
//					} catch (NoSuchAlgorithmException e1) {
//						log.error(e1.toString());
//					}
//					employee.setEintrittsdatum(eintrittsdatum.getValue());
//					employee.setAustrittsdatum(austrittsdatum.getValue());
//	
//					employee.setBenutzername(benutzername.getValue());
//	
//					// Listbuilder: ValueChangeListener gibt einen String der IDs
//					// zur�ck z.B. [1, 3]
//					// String auseinander nehmen und die Objekte anhand der ID
//					// suchen und der Liste hinzuf�gen
//					List<String> rollenId = null;
//					if (rollen.getValue().toString() != "[]") {
//						rollenId = Arrays.asList(valueString.substring(1, valueString.length() - 1).split("\\s*,\\s*"));
//	
//						for (String sId : rollenId) {
//							Long id = null;
//							try {
//								id = Long.parseLong(sId.trim());
//	
//							} catch (NumberFormatException nfe) {
//								log.error(nfe.toString());
//							}
//	
//							Rollen rollen = null;
//							try {
//								rollen = Rollenverwaltung.getInstance().getRollenById(id);
//								rollenlist.add(rollen);
//							} catch (Exception e) {
//								log.error(e.toString());
//							}
//	
//						}
//					}
//	
//					employee.setRollen(rollenlist);
//	
//					try {
//						boolean vorhanden = false;
//						List<Employee> ml = Mitarbeiterverwaltung.getInstance().getAllMitarbeiter();
//						for (int i = 0; i < ml.size(); i++) {
//							if (benutzername.getValue().equals(ml.get(i).getBenutzername())) {
//								vorhanden = true;
//							}
//						}
//						if (vorhanden == false) {
//	
//							Mitarbeiterverwaltung.getInstance().createMitarbeiter(employee);
//							ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);
//						} else {
//							Notification notification = new Notification("Der Benutzername ist bereits vorhanden!");
//							notification.setDelayMsec(500);
//							notification.show(Page.getCurrent());
//						}
//	
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//				}
//			}
//		});
//
//		// Berechtigung: Nur Administrator/Chef darf Mitarbeiter anlegen
//		Employee m = ((Application) UI.getCurrent().getData()).getUser();
//		if (m.getRollen() != null) {
//			for (int i = 0; i < m.getRollen().size(); i++) {
//				if (m.getRollen().get(i).getName().equals(Rollen.ADMINISTRATOR)) {
//					name.setEnabled(true);
//					vorname.setEnabled(true);
//					email.setEnabled(true);
//					passwort.setEnabled(true);
//					eintrittsdatum.setEnabled(true);
//					austrittsdatum.setEnabled(true);
//					benutzername.setEnabled(true);
//					rollen.setEnabled(true);
//				}
//
//			}
//		}
//	}
//	
//	@Override
//	public void getViewParam(ViewData data) {
//
//	}
//	private Boolean validiereEingabe() {
//		if (name.isValid() == false) {
//			((Application) UI.getCurrent().getData())
//					.showDialog(IConstants.INFO_MITARBEITER_NAME);
//			return false;
//		}
//		if (vorname.isValid() == false) {
//			((Application) UI.getCurrent().getData())
//					.showDialog(IConstants.INFO_MITARBEITER_VORNAME);
//			return false;
//		}
//		if (benutzername.isValid() == false) {
//			((Application) UI.getCurrent().getData())
//					.showDialog(IConstants.INFO_MITARBEITER_BENUTZERNAME);
//			return false;
//		}
//		if (passwort.isValid() == false) {
//			((Application) UI.getCurrent().getData())
//					.showDialog(IConstants.INFO_MITARBEITER_PASSWORT);
//			return false;
//		}
//		if (!email.getValue().isEmpty() && !email.getValue().contains("@")) {
//			((Application) UI.getCurrent().getData())
//			.showDialog(IConstants.INFO_LIFERANT_EMAIL);
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
//}

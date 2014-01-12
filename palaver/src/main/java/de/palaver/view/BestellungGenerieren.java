package de.palaver.view;
//package de.hska.awp.palaver2.gui.view;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.vaadin.server.Page;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.PopupDateField;
//import com.vaadin.ui.VerticalLayout;
//
//import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
//import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
//import de.bistrosoft.palaver.util.Week;
//import de.hska.awp.palaver.bestellverwaltung.service.BestellungService;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewHandler;
//
///**
// * Die View ermÃ¶glicht die Auswahl eines Kalendertags und die Generierung und
// * Speicherung der Bestellungen zu der entsprechenden Kalenderwoche.
// * 
// * @author Christian Barth
// * 
// */
//@SuppressWarnings("serial")
//public class BestellungGenerieren extends VerticalLayout implements View {
//	
//	private static final Logger	log	= LoggerFactory.getLogger(BestellungLieferantAuswaehlen.class.getName());
//
//	private VerticalLayout form = new VerticalLayout();
//
////	private Button bg = new Button("Bestellungen aus dem Menüplan generieren");
//
//	public BestellungGenerieren() {
//		super();
//
//		this.setSizeFull();
//		this.setMargin(true);
//
//		form.setSizeFull();
//		form.setHeight("200px");
//		form.setSpacing(true);
//
//		final PopupDateField date = new PopupDateField("Kalenderwoche des Menüplans auswählen:");
//		date.setWidth("150px");
//		date.setDateFormat("dd.MM.yyyy");
//		date.setLenient(true);
//		date.setShowISOWeekNumbers(true);
//		form.addComponent(date);
//		form.setComponentAlignment(date, Alignment.MIDDLE_CENTER);
//
//		form.addComponent(bg);
//		form.setComponentAlignment(bg, Alignment.TOP_CENTER);
//		this.addComponent(form);
//
//		bg.addClickListener(new ClickListener() {
//
//			public void buttonClick(ClickEvent event) {
//				SimpleDateFormat sdf;
//				Calendar cal;
//				Date datum = null;
//				int week;
//				String sample = null;
//
//				if (date.getValue() == null) {
//					Notification notification = new Notification("Es wurde kein Datum ausgewählt!");
//					notification.setDelayMsec(500);
//					notification.show(Page.getCurrent());
//				} else {
//					sample = date.getValue().toString();
//
//					sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
//					try {
//						datum = sdf.parse(sample);
//					} catch (ParseException e) {
//						log.error(e.toString());
//					}
//					cal = Calendar.getInstance();
//					cal.setTime(datum);
//					week = cal.get(Calendar.WEEK_OF_YEAR);
//					@SuppressWarnings("deprecation")
//					int year = date.getValue().getYear() + 1900;
//					
//					//aktuell
//					Menueplan menuplanAktuelleWoche = null;
//					List<RezeptHasArtikel> kuchenplanAktuelleWoche = null;
//					
//					//vorwoche
//					Menueplan menuplanvorWoche = null;
//					List<RezeptHasArtikel> kuchenplanVorWoche = null;
//					
//					//WOCHE
//					Week weekvorwoche = null;
//					if(week - 1 == 0){
//						if(year == 2016 || year == 2021 || year == 2027){
//							weekvorwoche = new Week(53 ,year -1);
//						} else {
//							weekvorwoche = new Week(52 ,year -1);
//						}
//						
//					} else {
//						weekvorwoche = new Week(week-1, year);
//					}
//						
//					System.out.println("Start: Laden der Mengen");
//					
//					//1. Vorwoche
//					System.out.println("VORWOCHE");
//					//Bedarf aus der Woche davor für Freitag-Sonntag holen
//					try {
//						kuchenplanVorWoche = Kuchenplanverwaltung.getInstance().getKuchenartikelByWeek(weekvorwoche, '>', 4);						
//					} catch (Exception e) {
//						System.out.println("Laden des Kuchenbedarfs der Vorwoche fehltgeschlagen"+ weekvorwoche.getWeek());
//						log.error(e.toString());
//					}
//					try {
//						menuplanvorWoche = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(weekvorwoche);
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//					
//					
//					//2. Aktuell
//					System.out.println("AKTUELLEWOCHE");
//					//Bedarf aus der Woche (Mo-Do) holen für die Kuchen und die Menues
//					try {
//						kuchenplanAktuelleWoche = Kuchenplanverwaltung.getInstance().getKuchenartikelByWeek(new Week(week, year), '<', 5);
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//					try {
//						menuplanAktuelleWoche = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new Week(week, year));
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//					
//					
//					System.out.println("Stop: Laden der Mengen");
//					System.out.println("Start: Überprüfung fängt an");
//					if (menuplanAktuelleWoche == null || menuplanAktuelleWoche.getFreigegeben() == null || menuplanAktuelleWoche.getFreigegeben() == false) {
//						Notification notification = new Notification("Kein Menüplan vorhanden oder noch nicht freigegeben!");
//						notification.setDelayMsec(500);
//						notification.show(Page.getCurrent());
//					} else {
//						if (kuchenplanAktuelleWoche.isEmpty() == true){	
//							Notification notification = new Notification("Kein Kuchenbedarf vorhanden!");
//							notification.setDelayMsec(500);
//							notification.show(Page.getCurrent());
//						} else {
//							try {
//								System.out.println("Start Bestellung generieren----------------------------");
//								BestellungService.getInstance().generateAllBestellungenByMenueplanAndGrundbedarf(menuplanAktuelleWoche, kuchenplanAktuelleWoche, 
//										new Week(week, year), menuplanvorWoche, kuchenplanVorWoche);
////								System.out.println("View Bestellung Generieren:" + m + a + new Week(week, year)+ mv + av);
//								
//							} catch (Exception e) {
//								log.error(e.toString());
//								Notification notification = new Notification("Bestellungen konnte nicht erzeugt werden!");
//								notification.setDelayMsec(500);
//								notification.show(Page.getCurrent());
//							}
//
//							ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
//						}
//					}
//				}
//			}
//		});
//	}
//
//	@Override
//	public void getViewParam(ViewData data) {
//
//	}
//
//}

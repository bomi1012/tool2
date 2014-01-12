package de.bistrosoft.palaver.gui.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.KuchenplanLayout;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.Application;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenplanHistorie extends VerticalLayout implements View {


	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();
	KuchenplanLayout kuchenplan = null;
	Label lbKW = null;
	Label lbPlatzhalter1 = new Label();
	Label lbPlatzhalter2 = new Label();
	
	int exception = 0;
	
	// Fuﬂünoten
	Label lbFussnoten = new Label(IConstants.FUSSNOTEN_KUCHENPLAN, ContentMode.HTML);
	Label lbPlatzhalter = new Label();

	public KuchenplanHistorie() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		// Kalender zur Datums-Auswahl
		final PopupDateField date = new PopupDateField("Datum w‰hlen:") {
			@Override
			protected Date handleUnparsableDateString(String dateString)
					throws ConversionException {
				// bei Fehleingabe
				((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_KUCHENPLAN_HISTORIE_DATUMSFORMAT);
				exception = 1;
				throw new ConversionException("Falsches Datumsformat");
			}
		};
		date.setWidth("150px");
		date.setDateFormat("dd.MM.yyyy");
		date.setLenient(true);
		date.setShowISOWeekNumbers(true);
		box.addComponent(date);
		box.setComponentAlignment(date, Alignment.TOP_CENTER);
		final Button btDatumsauswahl = new Button("Kuchenplan anzeigen");
		btDatumsauswahl.addClickListener(new ClickListener() {
			// Click-Listener zur Datumsauswahl
			@Override
			public void buttonClick(ClickEvent event) {
				if (exception == 0) {
					// Datum in Woche und Jahr aufteilen
					SimpleDateFormat sdf;
					Calendar cal;
					Date datum = null;
					int week;
					String sample = null;
					if (date.getValue() == null) {
						((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_KUCHENPLAN_HISTORIE_KEINDATUM);
					} else {
						sample = date.getValue().toString();
						sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy",
								Locale.ENGLISH);
						try {
							datum = sdf.parse(sample);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						cal = Calendar.getInstance();
						cal.setTime(datum);
						week = cal.get(Calendar.WEEK_OF_YEAR);
						@SuppressWarnings("deprecation")
						int year = date.getValue().getYear() + 1900;
						// alte Anzeigen l√∂schen
						if (kuchenplan != null) {
							box.removeComponent(kuchenplan);
							box.removeComponent(lbKW);
							box.removeComponent(lbPlatzhalter1);
							box.removeComponent(lbPlatzhalter2);
						}
						// nur alte Pl‰ne
						Week curWeek = CalendarWeek.getCurrentWeek();
						final int woche = curWeek.getWeek();
						if (week < (woche + 1)) {
							// nur gespeicherte Pl‰ne
							if (Kuchenplanverwaltung.getInstance()
									.getKuchenplanByWeekWithItems(
											new Week(week, year)) == null) {
								((Application) UI.getCurrent().getData())
										.showDialog(IConstants.INFO_KUCHENPLAN_HISTORIE_KEINPLAN);
							} else {
								// Anzeige
								kuchenplan = new KuchenplanLayout(week, year);
								kuchenplan.kuchenTable
										.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.NONE);
								kuchenplan.itemMoTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemDiTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemMiTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemDoTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemFrTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemSaTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								kuchenplan.itemSoTable
										.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
								String strKW = new String("Kalenderwoche: "
										+ week + "/" + year);

								Label lbKW2 = new Label(strKW);
								lbKW2.setStyleName("ViewHeadline");
								lbKW = lbKW2;
								lbPlatzhalter1.setHeight("30px");
								box.addComponent(lbPlatzhalter1);
								box.setComponentAlignment(lbPlatzhalter1,
										Alignment.TOP_CENTER);
								box.addComponent(lbKW);
								box.setComponentAlignment(lbKW,
										Alignment.TOP_CENTER);
								lbPlatzhalter2.setHeight("30px");
								box.addComponent(lbPlatzhalter2);
								box.setComponentAlignment(lbPlatzhalter2,
										Alignment.TOP_CENTER);
								box.addComponent(kuchenplan);
								box.setComponentAlignment(kuchenplan,
										Alignment.MIDDLE_CENTER);
								box.addComponent(lbFussnoten);
								box.setComponentAlignment(lbFussnoten,
										Alignment.BOTTOM_CENTER);
							}
						} else {
							((Application) UI.getCurrent().getData())
									.showDialog(IConstants.INFO_KUCHENPLAN_HISTORIE_ALT);
						}
					}
				}
				exception = 0;
			}
		});
				
		lbPlatzhalter.setHeight("30px");
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(lbPlatzhalter, Alignment.TOP_CENTER);
		box.addComponent(btDatumsauswahl);
		box.setComponentAlignment(btDatumsauswahl, Alignment.TOP_CENTER);
		
	}
		
	@Override
	public void getViewParam(ViewData data) {

	}
}
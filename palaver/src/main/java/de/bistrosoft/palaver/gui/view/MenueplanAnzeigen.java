package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

import de.bistrosoft.palaver.menueplanverwaltung.KoecheComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

/**
 * 
 * @author Eike Becher, Melanie Gross, Christine Hartkorn
 * 
 */

@SuppressWarnings("serial")
public class MenueplanAnzeigen extends VerticalLayout implements View {

	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();

	Week curWeek = CalendarWeek.getCurrentWeek();
	final int week = curWeek.getWeek();
	final int year = curWeek.getYear();

	List<Mitarbeiter> mitarbeiter;
	List<Regel> regeln;

	MenueplanGridLayout ersteMenueplan;
	MenueplanGridLayout zweiteMenueplan;
	MenueplanGridLayout dritteMenueplan;
	MenueplanGridLayout vierteMenueplan;

	HorizontalLayout hlChangeWeek = new HorizontalLayout();
	private Button btForeWeek = new Button();
	private Button btNextWeek = new Button();
	private Button platzhalter1 = new Button();
	private Button platzhalter2 = new Button();
	private String strKW = new String("Kalenderwoche: " + (week + 1) + "/"
			+ year);

	HorizontalLayout hlControl = new HorizontalLayout();

	Button btEnableDelete = new Button("Elemente löschen");
	Button btSubmitDelete = new Button("Löschen bestätigen");
	Button btEnableDragging = new Button("Verschieben inaktiv");
	Button btSpeichern = new Button("Speichern");
	Button btFreigeben = new Button("Freigeben");
	Button btImport = new Button("Plan importieren");
	Button btPrint = new Button("Druckansicht");
	Button btWetter = new Button("Wetter");
	private Button btDeletePlan = new Button("Gesamten Plan löschen");

	private Label lbKW = new Label(
			"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
					+ strKW + "</pre>", ContentMode.HTML);
	MenueplanGridLayout shownMenueplan;

	// /////////////////
	Label lbMplWeek;

	public MenueplanAnzeigen() {
		super();
		ladeDaten();
		this.setSizeFull();
		this.setMargin(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);

		platzhalter1.setStyleName(BaseTheme.BUTTON_LINK);
		platzhalter1.setIcon(new ThemeResource("img/platzhalter.png"));
		platzhalter1.addStyleName("menueplan-lastweek");
		platzhalter2.setStyleName(BaseTheme.BUTTON_LINK);
		platzhalter2.setIcon(new ThemeResource("img/platzhalter.png"));
		platzhalter2.addStyleName("menueplan-nextweek");

		// Pfeil zum Wechseln zur vorherigen KW und Anzeige der Wochen-Nr
		final HorizontalLayout left = new HorizontalLayout();
		final HorizontalLayout right = new HorizontalLayout();

		btForeWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btForeWeek.setIcon(new ThemeResource("img/woche_vorherklein.png"));
		btForeWeek.addStyleName("menueplan-lastweek");
		btForeWeek.addClickListener(new ClickListener() {

			// Click-Listener für eine Woche vorher
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan == zweiteMenueplan) {
					box.replaceComponent(shownMenueplan, ersteMenueplan);
					shownMenueplan = ersteMenueplan;
					strKW = "Kalenderwoche: " + (week) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(btForeWeek, platzhalter1);

					checkRollen();
				} else if (shownMenueplan == dritteMenueplan) {
					box.replaceComponent(shownMenueplan, zweiteMenueplan);
					shownMenueplan = zweiteMenueplan;
					strKW = "Kalenderwoche: " + (week + 1) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(platzhalter1, btForeWeek);
					right.replaceComponent(platzhalter2, btNextWeek);

					checkRollen();

				} else if (shownMenueplan == vierteMenueplan) {
					box.replaceComponent(shownMenueplan, dritteMenueplan);
					shownMenueplan = dritteMenueplan;
					strKW = "Kalenderwoche: " + (week + 2) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(platzhalter1, btForeWeek);
					right.replaceComponent(platzhalter2, btNextWeek);

					checkRollen();
				}

				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				} else {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
				}

			}

		});

		// Pfeil zum Wechseln zur nächsten KW und Anzeige der Wochen-Nr
		btNextWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btNextWeek.setIcon(new ThemeResource("img/woche_spaterklein.png"));
		btNextWeek.addStyleName("menueplan-nextweek");
		btNextWeek.addClickListener(new ClickListener() {

			// Click-Listener für eine Woche später
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan == ersteMenueplan) {
					box.replaceComponent(shownMenueplan, zweiteMenueplan);
					shownMenueplan = zweiteMenueplan;
					strKW = "Kalenderwoche: " + (week + 1) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(platzhalter2, btNextWeek);
					left.replaceComponent(platzhalter1, btForeWeek);

					checkRollen();
				} else if (shownMenueplan == zweiteMenueplan) {
					box.replaceComponent(shownMenueplan, dritteMenueplan);
					shownMenueplan = dritteMenueplan;
					strKW = "Kalenderwoche: " + (week + 2) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(platzhalter2, btNextWeek);
					left.replaceComponent(platzhalter1, btForeWeek);

					checkRollen();
				} else if (shownMenueplan == dritteMenueplan) {
					box.replaceComponent(shownMenueplan, vierteMenueplan);
					shownMenueplan = vierteMenueplan;
					strKW = "Kalenderwoche: " + (week + 3) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(btNextWeek, platzhalter2);

					checkRollen();
				}

				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				} else {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
				}
			}

		});

		// Hinzufügen und Anordnen der Komponenten
		left.addComponent(btForeWeek);
		left.setComponentAlignment(btForeWeek, Alignment.TOP_LEFT);
		right.addComponent(btNextWeek);
		right.setComponentAlignment(btNextWeek, Alignment.TOP_RIGHT);

		hlChangeWeek.addComponents(left, lbKW, right);
		hlChangeWeek.setComponentAlignment(left, Alignment.TOP_LEFT);
		hlChangeWeek.setComponentAlignment(lbKW, Alignment.TOP_CENTER);
		hlChangeWeek.setComponentAlignment(right, Alignment.TOP_RIGHT);
		box.addComponent(hlChangeWeek);
		box.setComponentAlignment(hlChangeWeek, Alignment.TOP_CENTER);

		if (shownMenueplan.getMenueplan().getFreigegeben()) {
			btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
		} else {
			btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
		}

		btSubmitDelete.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan.layout.getDragMode() == LayoutDragMode.CLONE) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
				}

				Integer rows = shownMenueplan.layout.getRows();
				Integer cols = shownMenueplan.layout.getColumns();
				for (int x = 0; x < cols; ++x) {
					for (int y = 0; y < rows; ++y) {
						if (shownMenueplan.layout.getComponent(x, y) instanceof MenueComponent) {
							MenueComponent mc = (MenueComponent) shownMenueplan.layout
									.getComponent(x, y);
							if (mc.isMarkiert()) {
								mc.remove();
							}
						}
					}
				}
				MenueplanAnzeigen.this.btEnableDelete.click();
			}
		});

		btEnableDragging.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan.layout.getDragMode() == LayoutDragMode.CLONE) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
					btEnableDragging.setCaption("Verschieben aktiv");
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
					btEnableDragging.setCaption("Verschieben inaktiv");
				}

			}
		});

		btEnableDelete.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (btSubmitDelete.isVisible()) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
					btSubmitDelete.setVisible(false);
					btDeletePlan.setVisible(false);
					btSpeichern.setVisible(true);
					// btFreigeben.setVisible(true);
					btEnableDragging.setVisible(true);
					btEnableDelete.setCaption("Elemente lÃ¶schen");
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
					btSubmitDelete.setVisible(true);
					btDeletePlan.setVisible(true);
					btSpeichern.setVisible(false);
					// btFreigeben.setVisible(false);
					btEnableDragging.setVisible(false);
					btEnableDelete.setCaption("ZurÃ¼ck");
				}

				Integer rows = shownMenueplan.layout.getRows();
				Integer cols = shownMenueplan.layout.getColumns();
				for (int x = 0; x < cols; ++x) {
					for (int y = 0; y < rows; ++y) {
						if (shownMenueplan.layout.getComponent(x, y) instanceof MenueComponent) {
							MenueComponent mc = (MenueComponent) shownMenueplan.layout
									.getComponent(x, y);
							mc.setCbDelete();
						}
						if (shownMenueplan.layout.getComponent(x, y) instanceof Button) {
							Button bt = (Button) shownMenueplan.layout
									.getComponent(x, y);
							if (bt.isEnabled()) {
								bt.setEnabled(false);
							} else {
								bt.setEnabled(true);
							}
						}
					}
				}
			}
		});

		btImport.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				Window fvj = new Window();
				VerticalLayout vlWinBox = new VerticalLayout();
				final PopupDateField date = new PopupDateField("Datum wÃ¤hlen:") {
					@Override
					protected Date handleUnparsableDateString(String dateString)
							throws ConversionException {
						// Notification bei Fehleingabe
						Notification notification = new Notification(
								"Falsches Datumsformat.");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
						throw new ConversionException("Falsches Datumsformat");
					}
				};

				date.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						Date datum = date.getValue();
						Week selectedWeek = CalendarWeek.getWeekFromDate(datum);
						lbMplWeek.setCaption(lbMplWeek.getCaption()
								+ " ersetzen mit " + selectedWeek.getWeek()
								+ "/" + selectedWeek.getYear());
					}
				});
				date.setWidth("150px");
				date.setDateFormat("dd.MM.yyyy");
				date.setLenient(true);
				date.setShowISOWeekNumbers(true);
				vlWinBox.addComponent(date);
				vlWinBox.setComponentAlignment(date, Alignment.TOP_CENTER);
				Week mplWeek = shownMenueplan.getMenueplan().getWeek();
				lbMplWeek = new Label("MenÃ¼plan " + mplWeek.getWeek() + "/"
						+ mplWeek.getYear());
				vlWinBox.addComponent(lbMplWeek);

				fvj.setContent(vlWinBox);
				UI.getCurrent().addWindow(fvj);
				// Menueplan imp =
				// Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new
				// Week(27,2013));
				// Week newWeek = new Week(28, 2013);
				// imp.setWeek(newWeek);
				// imp.setId(null);
				// MenueplanGridLayout mgl = new MenueplanGridLayout(imp);
				// mgl.speichern();
				// ViewHandler.getInstance().switchView(MenueplanAnzeigen.class);
				// nextMenueplan = new MenueplanGridLayout(imp);
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// alle Felder durchgehen, prüfen ob menuecomponent vorhanden
				// ist und wenn ja speichern
				shownMenueplan.speichern();
				int week = shownMenueplan.getMenueplan().getWeek().getWeek();
				int year = shownMenueplan.getMenueplan().getWeek().getYear();
				Notification notification = new Notification(
						"Menüplan für Kalenderwoche " + week + "/" + year
								+ " wurde gespeichert");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				((Application)UI.getCurrent().getData()).setChange(false);
			}
		});

		btDeletePlan.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				Menueplanverwaltung.getInstance().delete(
						shownMenueplan.getMenueplan());

				// Plan leeren
				Integer rows = shownMenueplan.layout.getRows();
				Integer cols = shownMenueplan.layout.getColumns();
				for (int x = 0; x < cols; ++x) {
					for (int y = 0; y < rows; ++y) {
						if (shownMenueplan.layout.getComponent(x, y) instanceof MenueComponent) {
							MenueComponent mc = (MenueComponent) shownMenueplan.layout
									.getComponent(x, y);
							mc.remove();
							shownMenueplan.layout.getComponent(x, y)
									.setEnabled(false);
						} else if (shownMenueplan.layout.getComponent(x, y) instanceof KoecheComponent) {
							KoecheComponent kc = (KoecheComponent) shownMenueplan.layout
									.getComponent(x, y);
							kc.setKoch1(null);
							kc.setKoch2(null);
						}
					}
				}
				btEnableDelete.click();
			}
		});

		// TODO: FREIGABE
		btFreigeben.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					shownMenueplan.freigeben(false);
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
					shownMenueplan.speichern();
				} else {
					ConfirmDialog.show(
							UI.getCurrent(),
							"Menüplan freigeben",
							"Wenn Sie diesen Menüplan freigeben, können die Köche ihn nicht mehr bearbeiten. Wollen Sie diesen Plan wirklich freigeben?",
							"Ja", "Nein", new ConfirmDialog.Listener() {
								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										shownMenueplan.freigeben(true);
										shownMenueplan.speichern();
										btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
									}
								}
							});
				}

				/*
				 * ConfirmDialog.show(UI.getCurrent(), "Menüplan freigeben",
				 * "Wenn Sie diesen Menüplan freigeben, können die Köche ihn nicht mehr bearbeiten. Wollen Sie diesen Plan wirklich freigeben?"
				 * , "Ja", "Nein", new ConfirmDialog.Listener() {
				 * 
				 * public void onClose(ConfirmDialog dialog) { if
				 * (dialog.isConfirmed()) { shownMenueplan.speichern(); //TODO
				 * ÄNDERN shownMenueplan.freigeben(false); if
				 * (shownMenueplan.getMenueplan().getFreigegeben()) {
				 * btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				 * } else { btFreigeben.setIcon(new
				 * ThemeResource(IConstants.ICON_NO)); } } } });
				 */
			}
		});
		String url = "http://www.wetter.com/wetter_aktuell/wettervorhersage/7_tagesvorhersage/?id=DE0005309";
		final BrowserWindowOpener opener = new BrowserWindowOpener(url);
		opener.setFeatures("height=800,width=1200,resizable");
		opener.extend(btWetter);

		btPrint.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// opener.extend(btPrint);
				print();

			}
		});

		// Fußnoten
		Label lbFussnoten = new Label(IConstants.FUSSNOTEN_MENUEPLAN,
				ContentMode.HTML);

		// Hinzufügen und Anordnen weiterer Komponenten
		Label lbPlatzhalter = new Label(" ");
		lbPlatzhalter.setHeight("60px");
		btFreigeben.setVisible(false);
		hlControl.addComponent(btSpeichern);
		hlControl.addComponent(btFreigeben);
		hlControl.addComponent(btEnableDragging);
		hlControl.addComponent(btEnableDelete);
		hlControl.addComponent(btSubmitDelete);
		hlControl.addComponent(btPrint);
		hlControl.addComponent(btWetter);
		hlControl.setSpacing(true);
		btSubmitDelete.setVisible(false);
		hlControl.addComponent(btDeletePlan);
		btDeletePlan.setVisible(false);
		// hlControl.addComponent(btImport);
		box.setSpacing(true);
		box.addComponent(hlControl);
		box.setComponentAlignment(hlControl, Alignment.MIDDLE_CENTER);
		box.addComponent(zweiteMenueplan);
		box.addComponent(lbFussnoten);
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(zweiteMenueplan, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(lbFussnoten, Alignment.BOTTOM_CENTER);
		box.setComponentAlignment(lbPlatzhalter, Alignment.BOTTOM_CENTER);

		checkRollen();
		getHtmlTable();
	}

	private void ladeDaten() {
		try {
			mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		regeln = Regelverwaltung.getInstance().getAllAktivRegeln();

		ersteMenueplan = new MenueplanGridLayout(week, year, mitarbeiter,
				regeln);
		zweiteMenueplan = new MenueplanGridLayout(week + 1, year, mitarbeiter,
				regeln);
		dritteMenueplan = new MenueplanGridLayout(week + 2, year, mitarbeiter,
				regeln);
		vierteMenueplan = new MenueplanGridLayout(week + 3, year, mitarbeiter,
				regeln);

		shownMenueplan = zweiteMenueplan;
	}

	public void checkRollen() {
		if (((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			btFreigeben.setVisible(true);
		}
		if (!((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			if (shownMenueplan.getMenueplan().getFreigegeben() == null
					|| shownMenueplan.getMenueplan().getFreigegeben() == false) {
				btSpeichern.setVisible(true);
			} else if (shownMenueplan.getMenueplan().getFreigegeben() == true) {
				btSpeichern.setVisible(false);
			}
		}

	}

	public String getHtmlTable() {
		String h = "<table border=\"1\" height=\"600\">";
		Integer col = shownMenueplan.layout.getColumns();
		Integer row = shownMenueplan.layout.getRows();

		for (int y = 0; y < row; ++y) {
			if (y != 1) {
				h += "<tr>";
				for (int x = 0; x < col; ++x) {
					if (x != 0) {
						h += "<th align=\"center\">";
						h += getLabelText(x, y);
						h += "</th>";
					}
				}
				h += "</tr>";
			}
		}
		h += "</table>";
		h += IConstants.FUSSNOTEN_MENUEPLAN;
		return h;
	}

	private String getLabelText(Integer x, Integer y) {
		Component comp = shownMenueplan.layout.getComponent(x, y);
		if (comp instanceof MenueComponent) {
			MenueComponent mc = (MenueComponent) comp;
			return mc.getAngezeigterName();
			//+ mc.getFussnoten();
		} else if (comp instanceof Label) {
			Label lb = (Label) comp;
			String sLb = lb.getValue();
			return getZeilenLabel(sLb);
		}
		return "";
	}

	private String getZeilenLabel(String sLb) {
		if (sLb.contains(IConstants.INFO_REGEL_ZEILE_1)) {
			return IConstants.INFO_REGEL_ZEILE_1;
		}
		if (sLb.contains(IConstants.INFO_REGEL_ZEILE_2)) {
			return IConstants.INFO_REGEL_ZEILE_2;
		}
		if (sLb.contains(IConstants.INFO_REGEL_ZEILE_3)) {
			return IConstants.INFO_REGEL_ZEILE_3;
		}
		if (sLb.contains(IConstants.INFO_REGEL_ZEILE_4)) {
			return IConstants.INFO_REGEL_ZEILE_4;
		}
		if (sLb.contains(IConstants.INFO_REGEL_ZEILE_5)) {
			return IConstants.INFO_REGEL_ZEILE_5;
		}
		if (sLb.contains("Datum")) {
			return "Datum";
		}
		if (sLb.contains("Köche")) {
			return "Köche";
		}
		return sLb;

	}

	public static void switchMenueplan() {

	}

	@Override
	public void getViewParam(ViewData data) {

	}

	public void print() {
		final Window win = new Window();
		win.setModal(true);
		win.setResizable(false);
		win.setSizeFull();

		VerticalLayout vlBox = new VerticalLayout();
		HorizontalLayout hlControl = new HorizontalLayout();
		VerticalLayout vlMenueplan = new VerticalLayout();

		Label lbH = new Label(getHtmlTable(), ContentMode.HTML);
		// Button btSchliessen = new Button("SchließŸen");

		addComponent(vlBox);
		vlBox.addComponent(vlMenueplan);
		vlMenueplan.addComponent(lbH);
		vlMenueplan.setComponentAlignment(lbH, Alignment.MIDDLE_CENTER);

		vlBox.addComponent(hlControl);
		// hlControl.addComponent(btSchliessen);
		// hlControl.setComponentAlignment(btSchliessen,
		// Alignment.MIDDLE_RIGHT);
		//
		// btSchliessen.addClickListener(new ClickListener() {
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// win.close();
		// }
		// });

		// addComponent(lbH);
		// lbH.setSizeFull();
		win.setContent(vlBox);
		UI.getCurrent().addWindow(win);
	}
}

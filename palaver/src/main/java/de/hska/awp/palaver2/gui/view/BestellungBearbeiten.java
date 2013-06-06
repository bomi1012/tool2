package de.hska.awp.palaver2.gui.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.gui.layout.LoginForm;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class BestellungBearbeiten extends VerticalLayout implements View {
	
	private static final Logger	log	= LoggerFactory.getLogger(LoginForm.class.getName());

	private Table bestellungTable;

	private FilterTable artikelTable;

	private VerticalLayout fenster;

	private HorizontalLayout form;

	private HorizontalLayout control;

	private Bestellung bestellung;

	private PopupDateField datetime = new PopupDateField();
	private PopupDateField datetime2 = new PopupDateField();
	private CheckBox bestellt = new CheckBox("Bestellung wurde bestellt");

	private List<Bestellposition> bestellpositionen;
	private List<BestellungData> bestellData = new ArrayList<BestellungData>();;

	private Button speichern;
	private Button verwerfen;
	private Button bestellenperemail;

	private BeanItemContainer<BestellungData> containerBestellung;
	private BeanItemContainer<Artikel> containerArtikel;

	Label l = new Label();

	public BestellungBearbeiten() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		datetime.setVisible(false);
		datetime.setImmediate(true);
		datetime.setResolution(Resolution.DAY);
		datetime.setTextFieldEnabled(false);
		datetime.setShowISOWeekNumbers(true);

		datetime.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				java.util.Date date2 = new java.util.Date();
				if (date2.before(datetime.getValue()) == false || datetime.getValue() == null) {
					speichern.setEnabled(false);
				} else {
					speichern.setEnabled(true);
				}

			}
		});

		datetime2.setVisible(false);
		datetime2.setImmediate(true);
		datetime2.setResolution(Resolution.DAY);
		datetime2.setTextFieldEnabled(false);
		datetime2.setShowISOWeekNumbers(true);

		datetime2.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				java.util.Date date2 = new java.util.Date();
				Date d = new Date(date2.getTime());
				if (datetime.getValue() == null || d.before(datetime.getValue()) == false || d.before(datetime2.getValue()) == false
						|| datetime2.getValue() == null) {
					speichern.setEnabled(false);
				} else {
					speichern.setEnabled(true);
				}
			}
		});

		fenster = new VerticalLayout();
		fenster.setSizeFull();

		form = new HorizontalLayout();
		form.setSizeFull();

		control = new HorizontalLayout();
		control.setSpacing(true);
		control.setSizeFull();

		this.addComponent(fenster);

		speichern = new Button(IConstants.BUTTON_SAVE);
		verwerfen = new Button(IConstants.BUTTON_DISCARD);
		bestellenperemail = new Button(IConstants.BUTTON_EMAILVERSAND);
		speichern.setEnabled(false);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		control.addComponent(l);
		control.setComponentAlignment(l, Alignment.TOP_LEFT);
		control.addComponent(bestellenperemail);
		control.setComponentAlignment(bestellenperemail, Alignment.TOP_RIGHT);
		control.addComponent(verwerfen);
		control.setComponentAlignment(verwerfen, Alignment.TOP_RIGHT);
		control.addComponent(speichern);
		control.setComponentAlignment(speichern, Alignment.TOP_RIGHT);
		control.setExpandRatio(l, 7);
		control.setExpandRatio(bestellenperemail, (float) 1.5);
		control.setExpandRatio(verwerfen, 1);
		control.setExpandRatio(speichern, 1);

		bestellungTable = new Table();
		bestellungTable.setSizeFull();
		bestellungTable.setStyleName("palaverTable");
		bestellungTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		/**
		 * Darg n Drop
		 */
		artikelTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Bestellposition loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				BestellungData selected = (BestellungData) t.getData("itemId");
				containerBestellung.removeItem(selected);
				containerArtikel.addItem(selected.getBestellungArtikel());
				artikelTable.markAsDirty();
				bestellungTable.markAsDirty();
			}
		});

		bestellungTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		/**
		 * Drag n Drop
		 */
		bestellungTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Verschiebt einen Artikel in die Bestellliste.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				Artikel selected = (Artikel) t.getData("itemId");
				containerArtikel.removeItem(selected);
				containerBestellung.addItem(new BestellungData(selected));
				artikelTable.markAsDirty();
				bestellungTable.markAsDirty();
			}
		});

		form.addComponent(bestellungTable);
		form.addComponent(artikelTable);

		form.setExpandRatio(bestellungTable, 2);
		form.setExpandRatio(artikelTable, 1);
		form.setSpacing(true);

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setWidth("100%");
		l.setWidth("100%");
		datetime.setCaption("Montag");
		datetime2.setCaption("Freitag");
		hl.addComponent(datetime);
		hl.setComponentAlignment(datetime, Alignment.TOP_LEFT);
		hl.addComponent(datetime2);
		hl.setComponentAlignment(datetime2, Alignment.TOP_LEFT);

		bestellt.setDescription("<h2><img src=\"VAADIN/themes/runo/icons/32/note.png\"/>Information</h2>" + "<ul>"
				+ "<li>Nach der erfolgreichen telefonischen Bestellung, bitte den Kasten anklicken und anschließend die Bestellung abspeichern.</li>"
				+ "<li>Nach dem Abspeichern ist die Bearbeitung der Bestellung nicht mehr möglich!</li></ul>");

		hl.addComponent(bestellt);
		hl.setComponentAlignment(bestellt, Alignment.BOTTOM_LEFT);
		hl.setExpandRatio(datetime, 1);
		hl.setExpandRatio(datetime2, 1);
		hl.setExpandRatio(bestellt, 7);

		fenster.addComponent(hl);
		fenster.addComponent(form);
		fenster.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		fenster.addComponent(control);
		fenster.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		fenster.setSpacing(true);

		fenster.setExpandRatio(form, 8);
		fenster.setExpandRatio(control, 1);

		verwerfen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
			}
		});

		speichern.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				bestellData = containerBestellung.getItemIds();
				bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionenMitId(bestellData);

				for (int i = 0; i < (bestellpositionen.size()); i++) {

					if (bestellpositionen.get(i).getGesamt() == 0) {
						bestellpositionen.remove(bestellpositionen.get(i));
						i = i - 1;
					}
				}

				java.util.Date dateutil = new java.util.Date();
				Date date = new Date(dateutil.getTime());
				bestellung.setDatum(date);
				dateutil = datetime.getValue();
				Date datesql = new Date(dateutil.getTime());
				bestellung.setLieferdatum(datesql);
				if (bestellung.getLieferant().getMehrereliefertermine() == true) {
					java.util.Date date1 = datetime2.getValue();
					Date datesql1 = new Date(date1.getTime());
					bestellung.setLieferdatum2(datesql1);
				} else {
					bestellung.setLieferdatum2(datesql);
				}

				bestellung.setBestellpositionen(bestellpositionen);
				bestellung.setBestellt(bestellt.getValue());

				try {
					bestellung.setBestellt(bestellt.getValue());
					Bestellverwaltung.getInstance().updateBestellung(bestellung);
				} catch (Exception e) {
					log.error(e.toString());
				}

				ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
			}
		});

		bestellenperemail.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				bestellData = containerBestellung.getItemIds();
				bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionenMitId(bestellData);

				for (int i = 0; i < (bestellpositionen.size()); i++) {

					if (bestellpositionen.get(i).getGesamt() == 0) {
						bestellpositionen.remove(bestellpositionen.get(i));
						i = i - 1;
					}
				}

				java.util.Date dateutil = new java.util.Date();
				Date date = new Date(dateutil.getTime());
				bestellung.setDatum(date);
				dateutil = datetime.getValue();
				Date datesql = new Date(dateutil.getTime());
				bestellung.setLieferdatum(datesql);
				if (bestellung.getLieferant().getMehrereliefertermine() == true) {
					java.util.Date date1 = datetime2.getValue();
					Date datesql1 = new Date(date1.getTime());
					bestellung.setLieferdatum2(datesql1);
				} else {
					bestellung.setLieferdatum2(datesql);
				}

				bestellung.setBestellpositionen(bestellpositionen);
				try {
					Bestellverwaltung.getInstance().updateBestellung(bestellung);
				} catch (Exception e) {
					log.error(e.toString());
				}

				ViewHandler.getInstance().switchView(EmailMitBestellung.class, new ViewDataObject<Bestellung>(bestellung));
			}
		});

	}

	/**
	 * Uebergibt den Lieferanten und fuellt die Tabellen
	 */
	@Override
	public void getViewParam(ViewData data) {
		bestellung = (Bestellung) ((ViewDataObject<?>) data).getData();

		bestellungTable.setCaption("Bestellung " + bestellung.getLieferant().getName());
		artikelTable.setCaption("Artikel");

		List<BestellungData> list = new ArrayList<BestellungData>();
		List<Artikel> artikel = new ArrayList<Artikel>();
		try {
			artikel = Artikelverwaltung.getInstance().getAllArtikelByLieferantId(bestellung.getLieferant().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}

		containerBestellung = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		try {

			for (Bestellposition bp : Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId())) {

				containerBestellung.addItem(new BestellungData(bp));
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		if (bestellung.getLieferant().getMehrereliefertermine() == false) {

			datetime.setValue(bestellung.getLieferdatum());
			datetime2.setValue(bestellung.getLieferdatum());

		} else {
			datetime.setValue(bestellung.getLieferdatum());
			datetime2.setValue(bestellung.getLieferdatum2());
			datetime.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(final ValueChangeEvent event) {
					java.util.Date date2 = new java.util.Date();
					Date d = new Date(date2.getTime());
					if (date2.before(datetime.getValue()) == false || datetime.getValue() == null || d.before(datetime2.getValue()) == false) {
						speichern.setEnabled(false);
					} else {
						speichern.setEnabled(true);
					}

				}
			});

		}

		bestellungTable.setContainerDataSource(containerBestellung);

		if (bestellung.getLieferant().getMehrereliefertermine() == true) {
			bestellungTable
					.setVisibleColumns(new Object[] { "name", "gebinde","notiz", "kategorie", "durchschnitt", "kantine", "gesamt", "montag", "freitag" });
			datetime.setVisible(true);
			datetime.setRequired(true);
			datetime2.setVisible(true);
			datetime2.setRequired(true);
		} else {
			bestellungTable.setVisibleColumns(new Object[] { "name", "gebinde","notiz", "kategorie", "durchschnitt", "kantine", "gesamt" });
			datetime.setCaption("Lieferdatum");
			datetime.setVisible(true);
			datetime.setRequired(true);
			datetime2.setVisible(false);
		}

		containerArtikel = new BeanItemContainer<Artikel>(Artikel.class, artikel);
		artikelTable.setContainerDataSource(containerArtikel);
		artikelTable.setVisibleColumns(new Object[] { "name", "grundbedarf", "standard", "lebensmittel" });
		artikelTable.setColumnCollapsed("grundbedarf", true);
		artikelTable.setColumnCollapsed("standard", true);
		artikelTable.setColumnCollapsed("lebensmittel", true);

		List<Ansprechpartner> alist = Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(bestellung.getLieferant());
		String text = "";
		if (bestellung.getLieferant().getTelefon() != null) {

			if (alist != null) {
				for (int i = 0; i < alist.size(); i++) {
					text = text + alist.get(i).getName() + " ";
					if (alist.get(i).getTelefon().length() > 4) {
						text = text + "Tel.: " + alist.get(i).getTelefon() + " ";
					}
					if (alist.get(i).getHandy().length() > 6) {
						text = text + "Handy: " + alist.get(i).getHandy() + " ";
					}
				}
			}
			l.setValue(bestellung.getLieferant().getName() + " Tel: " + bestellung.getLieferant().getTelefon() + " " + text);
		} else if (alist != null) {
			l.setValue("Ansprechpartner: " + text);
		}

	}
}
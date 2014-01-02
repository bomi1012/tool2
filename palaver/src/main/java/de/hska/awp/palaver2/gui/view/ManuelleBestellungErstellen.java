/**
 * Created by Sebastian Walz
 * 06.05.2013 12:17:45
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

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
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class ManuelleBestellungErstellen extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory.getLogger(ManuelleBestellungErstellen.class.getName());

	private FilterTable bestellungTable;

	private FilterTable artikelTable;

	private VerticalLayout fenster;

	private HorizontalLayout form;

	private HorizontalLayout control;

	private Bestellung bestellung;

	private Lieferant lieferant;

	private PopupDateField datetime = new PopupDateField();
	private PopupDateField datetime2 = new PopupDateField();

	private List<Bestellposition> bestellpositionen;
	private List<BestellungData> bestellData = new ArrayList<BestellungData>();;

	private Button speichern;
	private Button verwerfen;

	private BeanItemContainer<BestellungData> containerBestellung;
	private BeanItemContainer<Artikel> containerArtikel;

	public ManuelleBestellungErstellen() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		datetime.setVisible(false);
		datetime.setImmediate(true);
		datetime.setResolution(Resolution.DAY);
		datetime.setTextFieldEnabled(false);
		datetime.setShowISOWeekNumbers(true);

		datetime2.setVisible(false);
		datetime2.setImmediate(true);
		datetime2.setResolution(Resolution.DAY);
		datetime2.setTextFieldEnabled(false);
		datetime2.setShowISOWeekNumbers(true);

		fenster = new VerticalLayout();
		fenster.setSizeFull();

		form = new HorizontalLayout();
		form.setSizeFull();

		control = new HorizontalLayout();
		control.setSpacing(true);

		this.addComponent(fenster);

		speichern = new Button(IConstants.BUTTON_SAVE);
		verwerfen = new Button(IConstants.BUTTON_DISCARD);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		control.addComponent(verwerfen);
		control.setComponentAlignment(verwerfen, Alignment.TOP_RIGHT);
		control.addComponent(speichern);
		control.setComponentAlignment(speichern, Alignment.TOP_RIGHT);

		bestellungTable = new FilterTable();
		bestellungTable.setSizeFull();
		bestellungTable.setStyleName("palaverTable");
		bestellungTable.setImmediate(true);
		bestellungTable.setFilterBarVisible(true);
		bestellungTable.setFilterGenerator(new customFilter());
		bestellungTable.setFilterDecorator(new customFilterDecorator());
		bestellungTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setColumnCollapsingAllowed(true);
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setFilterGenerator(new customFilter());
		artikelTable.setFilterDecorator(new customFilterDecorator());
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
		
		artikelTable.setCellStyleGenerator(new CellStyleGenerator()
		{
			
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId)
			{
				Artikel artikel = (Artikel) itemId;
				if ("standard".equals(propertyId))
				{
					return artikel.isStandard() ? "check" : "cross";
				}
				if ("grundbedarf".equals(propertyId))
				{
					return artikel.isGrundbedarf() ? "check" : "cross";
				}
				return "";
			}
		});

		bestellungTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
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
		datetime.setCaption("Termin 1");
		datetime2.setCaption("Termin 2");
		hl.addComponent(datetime);
		hl.addComponent(datetime2);
		hl.setSpacing(true);
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
				ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);
			}
		});

		speichern.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					
				bestellData = containerBestellung.getItemIds();
				bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionen(bestellData);

				for (int i = 0; i < (bestellpositionen.size()); i++) {

					if (bestellpositionen.get(i).getGesamt() == 0) {
						bestellpositionen.remove(i);
					}
				}

				java.util.Date date2 = new java.util.Date();
				Date date = new Date(date2.getTime());
				bestellung = new Bestellung();
				bestellung.setLieferant(lieferant);
				bestellung.setDatum(date);
				bestellung.setBestellpositionen(bestellpositionen);
				if (lieferant.getMehrereliefertermine() == true) {
					if (datetime2.isValid() == true) {
						java.util.Date date3 = datetime.getValue();
						Date datesql = new Date(date3.getTime());
						java.util.Date date1 = datetime2.getValue();
						Date datesql1 = new Date(date1.getTime());
						bestellung.setLieferdatum(datesql);
						bestellung.setLieferdatum2(datesql1);
					} else {
						java.util.Date date3 = datetime.getValue();
						Date datesql = new Date(date3.getTime());
						bestellung.setLieferdatum2(datesql);
						bestellung.setLieferdatum(datesql);
					}
				} else {
					java.util.Date date3 = datetime.getValue();
					Date datesql = new Date(date3.getTime());
					bestellung.setLieferdatum(datesql);
					bestellung.setLieferdatum2(datesql);
				}

				try {
					Bestellverwaltung.getInstance().createBestellung(bestellung);
				} catch (Exception e) {
					log.error(e.toString());
				}
				ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);
			}
			}	
		});

	}

	/**
	 * Uebergibt den Lieferanten und fuellt die Tabellen
	 */
	@Override
	public void getViewParam(ViewData data) {
		lieferant = (Lieferant) ((ViewDataObject<?>) data).getData();

		bestellungTable.setCaption("Bestellung " + lieferant.getName());
		artikelTable.setCaption("Artikel");

		List<BestellungData> list = new ArrayList<BestellungData>();
		List<Artikel> artikel = new ArrayList<Artikel>();
		List<Artikel> artikelListe = null;
		try {
			artikelListe = Artikelverwaltung.getInstance().getActiveArtikelByLieferantId(lieferant.getId());
		} catch (Exception e) {
			log.error(e.toString());
		}

		for (Artikel e : artikelListe) {
			artikel.add(e);
		}

		containerBestellung = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		bestellungTable.setContainerDataSource(containerBestellung);

		if (lieferant.getMehrereliefertermine() == true) {
			bestellungTable.setVisibleColumns(new Object[] { "name", "kategorie", "gebinde", "notiz",  "durchschnitt", "kantine", "gesamt", "freitag",
					"montag" });
			bestellungTable.setColumnHeader("montag", "Termin 2");
			bestellungTable.setColumnHeader("freitag", "Termin 1");
			bestellungTable.setColumnHeader("durchschnitt", "Menge");
			datetime.setVisible(true);
			datetime.setRequired(true);
			datetime2.setVisible(true);
			datetime2.setRequired(true);
		} else {
			bestellungTable.setVisibleColumns(new Object[] { "name", "kategorie", "gebinde", "notiz", "durchschnitt", "kantine", "gesamt" });
			datetime.setCaption("Lieferdatum");
			bestellungTable.setColumnHeader("durchschnitt", "Menge");
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
		artikelTable.setColumnCollapsible("name", false);		
		artikelTable.setColumnWidth("grundbedarf", 50);
		artikelTable.setColumnHeader("grundbedarf", "grundb.");
		artikelTable.setColumnWidth("standard", 50);
		artikelTable.setColumnWidth("lebensmittel", 50);
	}
	private Boolean validiereEingabe() {
		
		java.util.Date date2 = new java.util.Date();
		Date d = new Date(date2.getTime());
		
		if (lieferant.getMehrereliefertermine() == true) {	

			if (datetime.isValid() == false || d.before(datetime.getValue()) == false) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN1);
				return false;
			}
			if (datetime2.isValid() == false || d.before(datetime2.getValue()) == false) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN2);
				return false;
			}
			bestellData = containerBestellung.getItemIds();
			if (bestellData.isEmpty() == true) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_ARTIKEL);
				return false;
			}
			else {
				return true;
			}
		} 
		else {

			
			if (datetime.isValid() == false || d.before(datetime.getValue()) == false) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN1);
				return false;
			}
			bestellData = containerBestellung.getItemIds();
			if (bestellData.isEmpty() == true ) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_ARTIKEL);
				return false;
			}
			else {
				return true;
			}
		}
	}
}

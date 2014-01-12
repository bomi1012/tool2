package de.palaver.view;
//package de.hska.awp.palaver2.gui.view;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.tepi.filtertable.FilterTable;
//
//
//import com.vaadin.client.metadata.Property;
//import com.vaadin.data.Item;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.data.util.PropertysetItem;
//import com.vaadin.event.ItemClickEvent;
//import com.vaadin.event.Transferable;
//
//import com.vaadin.event.ItemClickEvent.ItemClickListener;
//
//import com.vaadin.event.dd.DragAndDropEvent;
//import com.vaadin.event.dd.DropHandler;
//import com.vaadin.event.dd.acceptcriteria.AcceptAll;
//import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.shared.ui.datefield.Resolution;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.CheckBox;
//import com.vaadin.ui.CustomTable;
//import com.vaadin.ui.CustomTable.CellStyleGenerator;
//import com.vaadin.ui.Field;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.PopupDateField;
//import com.vaadin.ui.Table;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import de.hska.awp.palaver.Application;
//import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
//import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
//import de.hska.awp.palaver.artikelverwaltung.service.ArtikelService;
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
//import de.hska.awp.palaver.bestellverwaltung.service.Bestellpositionverwaltung;
//import de.hska.awp.palaver.bestellverwaltung.service.BestellungService;
//import de.hska.awp.palaver2.gui.layout.LoginForm;
//import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
//import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
//import de.hska.awp.palaver2.util.BestellungData;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewDataObject;
//import de.hska.awp.palaver2.util.ViewHandler;
//import de.hska.awp.palaver2.util.customFilter;
//import de.hska.awp.palaver2.util.customFilterDecorator;
//
//@SuppressWarnings("serial")
//public class BestellungBearbeiten extends VerticalLayout implements View {
//	
//	private static final Logger	log	= LoggerFactory.getLogger(LoginForm.class.getName());
//
//	private FilterTable bestellungTable;
//	private List<Artikel> artikel;
//	List<BestellungData> list;
//	private FilterTable artikelTable;
//
//	private VerticalLayout fenster;
//
//	private HorizontalLayout form;
//
//	private HorizontalLayout control;
//
//	private Bestellung bestellung;
//
//	private PopupDateField datetime = new PopupDateField();
//	private PopupDateField datetime2 = new PopupDateField();
//	private CheckBox bestellt = new CheckBox("Bestellung wurde bestellt");
//
//	private List<Bestellposition> bestellpositionen;
//	private List<BestellungData> bestellData = new ArrayList<BestellungData>();;
//
//	private Button speichern;
//	private Button verwerfen;
//	private Button bestellenperemail;
//
//	private BeanItemContainer<BestellungData> containerBestellung;
//	private BeanItemContainer<Artikel> containerArtikel;
//
//	Label l = new Label();
//
//	public BestellungBearbeiten() {
//		super();
//
//		this.setSizeFull();
//		this.setMargin(true);
//
//		datetime.setVisible(false);
//		datetime.setImmediate(true);
//		datetime.setResolution(Resolution.DAY);
//		datetime.setTextFieldEnabled(false);
//		datetime.setShowISOWeekNumbers(true);
//
//		datetime2.setVisible(false);
//		datetime2.setImmediate(true);
//		datetime2.setResolution(Resolution.DAY);
//		datetime2.setTextFieldEnabled(false);
//		datetime2.setShowISOWeekNumbers(true);
//
//		fenster = new VerticalLayout();
//		fenster.setSizeFull();
//
//		form = new HorizontalLayout();
//		form.setSizeFull();
//
//		control = new HorizontalLayout();
//		control.setSpacing(true);
//		control.setSizeFull();
//		control.setMargin(true);
//
//		this.addComponent(fenster);
//
//		speichern = new Button(IConstants.BUTTON_SAVE);
//		verwerfen = new Button(IConstants.BUTTON_DISCARD);
//		bestellenperemail = new Button(IConstants.BUTTON_EMAILVERSAND);
//		bestellenperemail.setIcon(new ThemeResource(IConstants.BUTTON_EMAILVERSAND_ICON));
//
//		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
//		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
//
//		control.addComponent(l);
//		control.setComponentAlignment(l, Alignment.TOP_LEFT);
//		control.addComponent(bestellenperemail);
//		control.setComponentAlignment(bestellenperemail, Alignment.TOP_RIGHT);
//		control.addComponent(verwerfen);
//		control.setComponentAlignment(verwerfen, Alignment.TOP_RIGHT);
//		control.addComponent(speichern);
//		control.setComponentAlignment(speichern, Alignment.TOP_RIGHT);
//		control.setExpandRatio(l, 7);
//		control.setExpandRatio(bestellenperemail, (float) 1.5);
//		control.setExpandRatio(verwerfen, (float) 1.5);
//		control.setExpandRatio(speichern, (float) 1.4);
//
//		bestellungTable = new FilterTable();
//		bestellungTable.setSizeFull();
//		bestellungTable.setColumnCollapsingAllowed(true);
//		bestellungTable.setStyleName("palaverTable");
//		bestellungTable.setImmediate(true);
//		bestellungTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
//		bestellungTable.setFilterBarVisible(true);
//		bestellungTable.setFilterGenerator(new customFilter());
//		bestellungTable.setFilterDecorator(new customFilterDecorator());
//
//		artikelTable = new FilterTable();
//		artikelTable.setSizeFull();
//		artikelTable.setColumnCollapsingAllowed(true);
//		artikelTable.setStyleName("palaverTable");
//		artikelTable.setFilterBarVisible(true);
//		artikelTable.setFilterGenerator(new customFilter());
//		artikelTable.setFilterDecorator(new customFilterDecorator());
//		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
//		
//		/**
//		 * Darg n Drop
//		 */
//		artikelTable.setDropHandler(new DropHandler() {
//			/**
//			 * Prueft, ob das Element verschoben werden darf.
//			 */
//			@Override
//			public AcceptCriterion getAcceptCriterion() {
//				return AcceptAll.get();
//			}
//
//			/**
//			 * Bestellposition loeschen und Artikel wieder in Liste setzen.
//			 */
//			@Override
//			public void drop(DragAndDropEvent event) {
//				Transferable t = event.getTransferable();
//				BestellungData selected = (BestellungData) t.getData("itemId");
//				containerBestellung.removeItem(selected);
//				containerArtikel.addItem(selected.getBestellungArtikel());
//				artikelTable.markAsDirty();
//				bestellungTable.markAsDirty();
//			}
//		});
//
//		bestellungTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
//		/**
//		 * Drag n Drop
//		 */
//		bestellungTable.setDropHandler(new DropHandler() {
//			/**
//			 * Prueft, ob das Element verschoben werden darf.
//			 */
//			@Override
//			public AcceptCriterion getAcceptCriterion() {
//				return AcceptAll.get();
//			}
//
//			/**
//			 * Verschiebt einen Artikel in die Bestellliste.
//			 */
//			@Override
//			public void drop(DragAndDropEvent event) {
//				Transferable t = event.getTransferable();
//				Artikel selected = (Artikel) t.getData("itemId");
//				containerArtikel.removeItem(selected);
//				containerBestellung.addItem(new BestellungData(selected));
//				artikelTable.markAsDirty();
//				bestellungTable.markAsDirty();
//			}
//		});
//		
//		artikelTable.setCellStyleGenerator(new CellStyleGenerator()
//		{
//			
//			@Override
//			public String getStyle(CustomTable source, Object itemId, Object propertyId)
//			{
//				Artikel artikel = (Artikel) itemId;
//				if ("standard".equals(propertyId))
//				{
//					return artikel.isStandard() ? "check" : "cross";
//				}
//				if ("grundbedarf".equals(propertyId))
//				{
//					return artikel.isGrundbedarf() ? "check" : "cross";
//				}
//				return "";
//			}
//		});
//
//		form.addComponent(bestellungTable);
//		form.addComponent(artikelTable);
//
//		form.setExpandRatio(bestellungTable, 3);
//		form.setExpandRatio(artikelTable, 1);
//		form.setSpacing(true);
//
//		HorizontalLayout hl = new HorizontalLayout();
//		hl.setSpacing(true);
//		hl.setWidth("100%");
//		l.setWidth("100%");
//		datetime.setCaption("Termin 1");
//		datetime2.setCaption("Termin 2");
//		hl.addComponent(datetime);
//		hl.setComponentAlignment(datetime, Alignment.TOP_LEFT);
//		hl.addComponent(datetime2);
//		hl.setComponentAlignment(datetime2, Alignment.TOP_LEFT);
//
//		bestellt.setDescription("<h2><img src=\"VAADIN/themes/runo/icons/32/note.png\"/>Information</h2>" + "<ul>"
//				+ "<li>Nach der erfolgreichen telefonischen Bestellung, bitte den Kasten anklicken und anschließend die Bestellung abspeichern.</li>"
//				+ "<li>Nach dem Abspeichern ist die Bearbeitung der Bestellung nicht mehr möglich!</li></ul>");
//
//		hl.addComponent(bestellt);
//		hl.setComponentAlignment(bestellt, Alignment.BOTTOM_LEFT);
//		hl.setExpandRatio(datetime, 1);
//		hl.setExpandRatio(datetime2, 1);
//		hl.setExpandRatio(bestellt, 7);
//
//		fenster.addComponent(hl);
//		fenster.addComponent(form);
//		fenster.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
//		fenster.addComponent(control);
//		fenster.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
//		fenster.setSpacing(true);
//
//		fenster.setExpandRatio(form, 8);
//		fenster.setExpandRatio(control, 1);
//
//		verwerfen.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
//			}
//		});
//
//		speichern.addClickListener(new ClickListener() {
//
//			public void buttonClick(ClickEvent event) {
//				if (validiereBestellung()) {
//				
//					bestellData = containerBestellung.getItemIds();
//					bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionenMitId(bestellData);
//	
//					for (int i = 0; i < (bestellpositionen.size()); i++) {
//	
//						if (bestellpositionen.get(i).getGesamt() == 0) {
//							bestellpositionen.remove(bestellpositionen.get(i));
//							i = i - 1;
//						}
//					}
//	
//					java.util.Date dateutil = new java.util.Date();
//					Date date = new Date(dateutil.getTime());
//					bestellung.setDatum(date);
//					dateutil = datetime.getValue();
//					Date datesql = new Date(dateutil.getTime());
//					bestellung.setLieferdatum(datesql);
//					if (bestellung.getLieferant().getMehrereliefertermine() == true) {
//						java.util.Date date1 = datetime2.getValue();
//						Date datesql1 = new Date(date1.getTime());
//						bestellung.setLieferdatum2(datesql1);
//					} else {
//						bestellung.setLieferdatum2(datesql);
//					}
//	
//					bestellung.setBestellpositionen(bestellpositionen);
//					bestellung.setBestellt(bestellt.getValue());
//	
//					try {
//						bestellung.setBestellt(bestellt.getValue());
//						BestellungService.getInstance().updateBestellung(bestellung);
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//	
//					ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
//				}
//			}
//		});
//
//		bestellenperemail.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				if (validiereBestellung()) {
//				
//					bestellData = containerBestellung.getItemIds();
//					bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionenMitId(bestellData);
//	
//					for (int i = 0; i < (bestellpositionen.size()); i++) {
//	
//						if (bestellpositionen.get(i).getGesamt() == 0) {
//							bestellpositionen.remove(bestellpositionen.get(i));
//							i = i - 1;
//						}
//					}
//	
//					java.util.Date dateutil = new java.util.Date();
//					Date date = new Date(dateutil.getTime());
//					bestellung.setDatum(date);
//					dateutil = datetime.getValue();
//					Date datesql = new Date(dateutil.getTime());
//					bestellung.setLieferdatum(datesql);
//					if (bestellung.getLieferant().getMehrereliefertermine() == true) {
//						java.util.Date date1 = datetime2.getValue();
//						Date datesql1 = new Date(date1.getTime());
//						bestellung.setLieferdatum2(datesql1);
//					} else {
//						bestellung.setLieferdatum2(datesql);
//					}
//	
//					bestellung.setBestellpositionen(bestellpositionen);
//					try {
//						BestellungService.getInstance().updateBestellung(bestellung);
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//	
//					ViewHandler.getInstance().switchView(EmailMitBestellung.class, new ViewDataObject<Bestellung>(bestellung));
//				}
//			}
//		});
//
//	}
//
//	protected boolean validiereBestellung() {
//		
//		java.util.Date date2 = new java.util.Date();
//		Date d = new Date(date2.getTime());
//		
//		if (bestellung.getLieferant().getMehrereliefertermine() == true) {	
//
//			if (datetime.isValid() == false || d.before(datetime.getValue()) == false) {
//				((Application) UI.getCurrent().getData())
//						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN1);
//				return false;
//			}
//			if (datetime2.isValid() == false || d.before(datetime2.getValue()) == false) {
//				((Application) UI.getCurrent().getData())
//						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN2);
//				return false;
//			}
//			bestellData = containerBestellung.getItemIds();
//			if (bestellData.isEmpty() == true) {
//				((Application) UI.getCurrent().getData())
//						.showDialog(IConstants.INFO_BESTELLUNG_ARTIKEL);
//				return false;
//			}
//			if ( bestellData.isEmpty() == false) {
//				for (int i = 0; i < bestellData.size(); i++) {
//					if (bestellData.get(i).getDurchschnitt().isValid() == false || 
//							bestellData.get(i).getKantine().isValid() == false ) {
//						return false;
//					}
//				}
//			}
//			else {
//				return true;
//			}
//		} 
//		else {
//
//			
//			if (datetime.isValid() == false || d.before(datetime.getValue()) == false) {
//				((Application) UI.getCurrent().getData())
//						.showDialog(IConstants.INFO_BESTELLUNG_TERMIN1);
//				return false;
//			}
//			bestellData = containerBestellung.getItemIds();
//			if (bestellData.isEmpty() == true ) {
//				((Application) UI.getCurrent().getData())
//						.showDialog(IConstants.INFO_BESTELLUNG_ARTIKEL);
//				return false;
//			}
//			else {
//				return true;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * Uebergibt den Lieferanten und fuellt die Tabellen
//	 */
//	@Override
//	public void getViewParam(ViewData data) {
//		bestellung = (Bestellung) ((ViewDataObject<?>) data).getData();
//
//		bestellungTable.setCaption("Bestellung " + bestellung.getLieferant().getName());
//		artikelTable.setCaption("Artikel");
//
//		list = new ArrayList<BestellungData>();
//		artikel = new ArrayList<Artikel>();
//		try {
//			artikel = ArtikelService.getInstance().getActiveArtikelByLieferantId(bestellung.getLieferant().getId());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error(e.toString());
//		}
//
//		containerBestellung = new BeanItemContainer<BestellungData>(BestellungData.class, list);
//		try {
//
//			for (Bestellposition bp : Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId())) {
//
//				containerBestellung.addItem(new BestellungData(bp));
//			}
//		} catch (Exception e) {
//			log.error(e.toString());
//		}
//		if (bestellung.getLieferant().getMehrereliefertermine() == false) {
//
//			datetime.setValue(bestellung.getLieferdatum());
//			datetime2.setValue(bestellung.getLieferdatum());
//
//		} else {
//			datetime.setValue(bestellung.getLieferdatum());
//			datetime2.setValue(bestellung.getLieferdatum2());
//		}
//
//		bestellungTable.setContainerDataSource(containerBestellung);
//
//		if (bestellung.getLieferant().getMehrereliefertermine() == true) {
//		
//			bestellungTable
//					.setVisibleColumns(new Object[] { "name", "kategorie", "gebinde", "summe", "notiz", "durchschnitt", "kantine", "gesamt", "freitag", "montag" });
//			datetime.setVisible(true);
//			bestellungTable.setColumnHeader("durchschnitt", "Menge");
//			bestellungTable.setColumnHeader("montag", "Termin 2");
//			bestellungTable.setColumnHeader("freitag", "Termin 1");
//			bestellungTable.setColumnWidth("kantine", 60);
//			bestellungTable.setColumnWidth("montag", 60);
//			bestellungTable.setColumnWidth("freitag", 60);
//			bestellungTable.setColumnWidth("gesamt", 60);
//			bestellungTable.setColumnWidth("durchschnitt", 60);
//			bestellungTable.setColumnWidth("gebinde", 60);
//			
//			datetime.setRequired(true);
//			datetime2.setVisible(true);
//			datetime2.setRequired(true);
//		} else {
//			bestellungTable.setVisibleColumns(new Object[] { "summe",  "name", "kategorie", "gebinde" , "summe", "notiz", "durchschnitt", "kantine", "gesamt" });
//			datetime.setCaption("Lieferdatum");
//			datetime.setVisible(true);
//			bestellungTable.setColumnHeader("durchschnitt", "Menge");
//			bestellungTable.setColumnHeader("montag", "Termin 2");
//			bestellungTable.setColumnHeader("freitag", "Termin 1");
//			bestellungTable.setColumnWidth("kantine", 60);
//			bestellungTable.setColumnWidth("montag", 60);
//			bestellungTable.setColumnWidth("freitag", 60);
//			bestellungTable.setColumnWidth("gesamt", 60);
//			bestellungTable.setColumnWidth("durchschnitt", 60);
//			bestellungTable.setColumnWidth("gebinde", 60);
//			
//			datetime.setRequired(true);
//			datetime2.setVisible(false);
//		}
//
//		containerArtikel = new BeanItemContainer<Artikel>(Artikel.class, artikel);
//		artikelTable.setContainerDataSource(containerArtikel);
//		artikelTable.setVisibleColumns(new Object[] { "name", "grundbedarf", "standard", "lebensmittel" });
//		artikelTable.setColumnCollapsed("grundbedarf", true);
//		artikelTable.setColumnCollapsed("standard", true);
//		artikelTable.setColumnCollapsed("lebensmittel", true);
//		artikelTable.setColumnCollapsible("name", false);		
//		artikelTable.setColumnWidth("grundbedarf", 50);
//		artikelTable.setColumnHeader("grundbedarf", "grundb.");
//		artikelTable.setColumnWidth("standard", 50);
//		artikelTable.setColumnWidth("lebensmittel", 50);
//		
//		List<Ansprechpartner> alist = Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(bestellung.getLieferant());
//		String text = "";
//		if (bestellung.getLieferant().getTelefon() != null) {
//
//			if (alist.isEmpty()==false) {
//				for (int i = 0; i < alist.size(); i++) {
//					text = text + alist.get(i).getName() + " ";
//					if (alist.get(i).getTelefon().length() > 4) {
//						text = text + "Tel.: " + alist.get(i).getTelefon() + " ";
//					}
//					if (alist.get(i).getHandy().length() > 6) {
//						text = text + "Handy: " + alist.get(i).getHandy() + " ";
//					}
//				}
//			}
//			l.setValue(bestellung.getLieferant().getName() + " Tel: " + bestellung.getLieferant().getTelefon() + " " + text);
//		} else if (alist != null) {
//			l.setValue("Ansprechpartner: " + text);
//		}
//
//	}
//}

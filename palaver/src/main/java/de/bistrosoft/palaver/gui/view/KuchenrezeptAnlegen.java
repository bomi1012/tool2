package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import de.bistrosoft.palaver.data.FussnoteKuchenDAO;
import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasFussnote;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Fussnotekuchenverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.ArtikelErstellen;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Christine Hartkorn, Jasmin Baumgartner
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	// Layouts
	private VerticalLayout vlBox = new VerticalLayout();
	private VerticalLayout vlDetailsLinks = new VerticalLayout();
	private VerticalLayout vlDetailsRechts = new VerticalLayout();
	private HorizontalLayout hlDetails = new HorizontalLayout();
	private HorizontalLayout hlZutaten = new HorizontalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	// Tabellen
	private Table zutatenTable;
	private FilterTable artikelTable;

	// BeanContainer
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<KuchenrezeptHasArtikel> containerKuchenrezeptHasArtikel;

	// ï¿½berschriften
	private Label ueberschrift = new Label("Kuchenrezept anlegen");
	private Label ueberschrift2 = new Label("Kuchenrezept bearbeiten");

	// Textfeld
	private TextField name = new TextField("Bezeichnung");

	// Textarea
	private TextArea kommentar = new TextArea("Kommentar");

	// Buttons
	private Button btSpeichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button update = new Button(IConstants.BUTTON_SAVE);
	private Button btArtikel = new Button(
			IConstants.BUTTON_REZEPT_ARTIKEL_ANLEGEN);

	// Strings
	private String nameInput;
	private String kommentarInput;
	public String valueString = new String();

	// Kuchenrezepte
	Kuchenrezept kuchenrezept;
	Kuchenrezept kuchenrezeptNeu;

	// TwinCol
	private TwinColTouch fussnoten = new TwinColTouch("Fussnoten");

	// Listen
	List<KuchenrezeptHasArtikel> tmpZutaten = new ArrayList<KuchenrezeptHasArtikel>();
	List<KuchenrezeptHasArtikel> ausgArtikel = new ArrayList<KuchenrezeptHasArtikel>();
	List<KuchenrezeptHasArtikel> artikel = new ArrayList<KuchenrezeptHasArtikel>();

	// Konstruktor
	public KuchenrezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		// Komponenten
		
		ueberschrift.setStyleName("ViewHeadline");
		ueberschrift2.setStyleName("ViewHeadline");

		name.setWidth("100%");
		name.setImmediate(true);
		name.setMaxLength(200);
		name.setInputPrompt(nameInput);
		name.setSizeFull();
		name.addValidator(new StringLengthValidator(
				IConstants.INFO_NAME_VALID, 3, 200, false));

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);
		kommentar.setMaxLength(5000);

		vlBox.setWidth("1000px");
		vlBox.setSpacing(true);

		fussnoten.setWidth("100%");
		fussnoten.setImmediate(true);

		btArtikel.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));

		this.addComponent(vlBox);
		this.setComponentAlignment(vlBox, Alignment.MIDDLE_CENTER);
		vlBox.addComponent(ueberschrift);
		ueberschrift.setWidth("300px");

		vlBox.addComponent(hlDetails);
		vlBox.addComponent(hlZutaten);
		vlBox.addComponent(hlControl);

		hlDetails.addComponent(vlDetailsLinks);
		hlDetails.addComponent(vlDetailsRechts);
		hlDetails.setWidth("1000px");
		hlDetails.setHeight("230px");

		vlBox.setComponentAlignment(ueberschrift, Alignment.MIDDLE_LEFT);

		vlDetailsLinks.addComponent(name);
		vlDetailsLinks.addComponent(kommentar);
		vlDetailsLinks.setWidth("450px");

		vlDetailsRechts.addComponent(fussnoten);
		vlDetailsRechts.setWidth("500px");

		hlZutaten.setWidth("1000px");
		hlZutaten.setHeight("393px");

		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btSpeichern.setEnabled(true);

		hlControl.addComponent(btArtikel);
		hlControl.addComponent(verwerfen);
		hlControl.addComponent(btSpeichern);

		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		// ValueChangeListener Name
		name.addValueChangeListener(this);

		// ValueChangeListener Kommentar
		kommentar.addValueChangeListener(this);

		// ClickListener Verwerfen
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});

		// ClickListener Speichern
		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
				}
			}
		});

		// ClickListener Neuer Artikel
		btArtikel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addArtikel();
			}
		});

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		zutatenTable.setPageLength(16);
		zutatenTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerKuchenrezeptHasArtikel = new BeanItemContainer<KuchenrezeptHasArtikel>(
				KuchenrezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerKuchenrezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit" });
		zutatenTable.setEditable(true);

		hlZutaten.addComponent(zutatenTable);
		hlZutaten.addComponent(artikelTable);

		// Aufteilung im Layout
		hlZutaten.setExpandRatio(zutatenTable, 7);
		hlZutaten.setExpandRatio(artikelTable, 3);

		vlBox.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);

		// Überschriften
		artikelTable.setCaption("Artikel");
		zutatenTable.setCaption("Zutatenliste");

		// Drag&Drop
		artikelTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Zutat loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				KuchenrezeptHasArtikel selected = (KuchenrezeptHasArtikel) t
						.getData("itemId");
				containerKuchenrezeptHasArtikel.removeItem(selected);
				tmpZutaten.remove(selected);
				containerArtikel.addItem(selected.getArtikel());
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		zutatenTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);

		// Drag&Drop
		zutatenTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Verschiebt einen Artikel in die Zutatenliste.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Artikel) {
					Artikel selected = (Artikel) t.getData("itemId");
					containerArtikel.removeItem(selected);
					KuchenrezeptHasArtikel tmp = new KuchenrezeptHasArtikel(
							selected);
					tmpZutaten.add(tmp);
					containerKuchenrezeptHasArtikel.addItem(tmp);
				}

				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		ladeArtikel();

		load();
	}

	// Methode zum Laden der Artikel
	private void ladeArtikel() {
		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getActiveArtikeln());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable.setVisibleColumns(new Object[] { "name" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Methode zum Laden der Fußnoten
	public void load() {
		fussnoten.removeAllItems();

		try {
			List<FussnoteKuchen> fk = Fussnotekuchenverwaltung.getInstance()
					.getAllFussnoteKuchen();
			for (FussnoteKuchen f : fk) {
				fussnoten.addItem(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Methode zum Laden der Daten zum Ansehen und Ändern
	@Override
	public void getViewParam(ViewData data) {

		kuchenrezept = (Kuchenrezept) ((ViewDataObject<?>) data).getData();
		try {
			kuchenrezept = Kuchenrezeptverwaltung.getInstance()
					.getKuchenrezeptById(kuchenrezept.getId(), true);
		} catch (ConnectException e2) {
			e2.printStackTrace();
		} catch (DAOException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		if (kuchenrezept.getArtikel() != null) {
			tmpZutaten = kuchenrezept.getArtikel();
		} else {
			tmpZutaten = new ArrayList<KuchenrezeptHasArtikel>();
		}

		hlControl.replaceComponent(btSpeichern, update);
		vlBox.replaceComponent(ueberschrift, ueberschrift2);
		ueberschrift2.setWidth("300px");

		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
				}
			}
		});

		/**
		 * Daten in Felder schreiben
		 */

		name.setValue(kuchenrezept.getName());

		for (int i = 0; i < kuchenrezept.getFussnoteKuchen().size(); i++) {
			fussnoten.select(kuchenrezept.getFussnoteKuchen().get(i));
		}
		BeanItemContainer<KuchenrezeptHasArtikel> artikelcontainer;
		List<KuchenrezeptHasArtikel> list = new ArrayList<KuchenrezeptHasArtikel>();

		try {
			list = Kuchenrezeptverwaltung.getInstance()
					.getAllArtikelByKuchenrezeptId1(kuchenrezept);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<KuchenrezeptHasArtikel>(
					KuchenrezeptHasArtikel.class);

			zutatenTable = null;
			zutatenTable = new Table();
			zutatenTable.setSizeFull();
			zutatenTable.setStyleName("palaverTable2");
			zutatenTable.setImmediate(true);

			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname",
					"menge", "einheit" });

			tmpZutaten = list;

			for (KuchenrezeptHasArtikel kha : list) {
				containerArtikel.removeItem(kha.getArtikel());
				containerKuchenrezeptHasArtikel.addItem(kha);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		kuchenrezept.setArtikel(list);

		kommentar.setValue(kuchenrezept.getKommentar());
	}

	// Methode, die Änderungen der Textfelder übernimmt
	@Override
	public void valueChange(ValueChangeEvent event) {
		nameInput = name.getValue();
		kommentarInput = kommentar.getValue();
	}

	// Funktion zum Speichern
	private void speichern() {
		KuchenrezeptSpeichern();
	}

	// Funktion zum Speichern eines Kuchenrezeptes
	private void KuchenrezeptSpeichern() {
		Kuchenrezept kuchenrezept = new Kuchenrezept();

		kuchenrezept.setName(nameInput);

		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		kuchenrezept.setErstellt(date2);

		kuchenrezept.setKommentar(kommentarInput);

		String aus = "1";
		try {
			if (aus == "2") {
				aus = "1";
				return;
			} else {
				Kuchenrezeptverwaltung.getInstance().createKuchenrezept(
						kuchenrezept);
			}

			Kuchenrezept kuchenrezeptNeu = null;

			try {
				kuchenrezeptNeu = Kuchenrezeptverwaltung.getInstance()
						.getKuchenrezeptByName1(nameInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			@SuppressWarnings("unchecked")
			BeanItemContainer<KuchenrezeptHasArtikel> bicArtikel = (BeanItemContainer<KuchenrezeptHasArtikel>) zutatenTable
					.getContainerDataSource();
			ausgArtikel = bicArtikel.getItemIds();
			kuchenrezeptNeu.setArtikel(ausgArtikel);

			if (ausgArtikel.isEmpty()) {
				((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_REZEPT_ZUTATEN);
				aus = "2";
				return;
			}

			try {
				Kuchenrezeptverwaltung.getInstance().saveArtikel(
						kuchenrezeptNeu);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kuchenrezeptNeu = Kuchenrezeptverwaltung.getInstance()
					.getKuchenrezeptByName1(nameInput);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Liste der Zubereitungen
		if (fussnoten.getValue().toString() != "[]") {
			List<String> FussnoteKuchenId = Arrays.asList(fussnoten.getValue()
					.toString()
					.substring(1, fussnoten.getValue().toString().length() - 1)
					.split("\\s*,\\s*"));

			List<KuchenrezeptHasFussnote> fussnotelist = new ArrayList<KuchenrezeptHasFussnote>();
			for (String sId : FussnoteKuchenId) {
				FussnoteKuchen fussnotekuchen = new FussnoteKuchen();
				try {
					fussnotekuchen = FussnoteKuchenDAO.getInstance()
							.getFussnoteKuchenByName(sId);
					KuchenrezeptHasFussnote a = new KuchenrezeptHasFussnote(
							fussnotekuchen, kuchenrezeptNeu);
					fussnotelist.add(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (KuchenrezeptHasFussnote i : fussnotelist) {
				try {
					KuchenrezeptDAO.getInstance().FussnoteKuchenAdd(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		((Application) UI.getCurrent().getData())
		.showDialog(IConstants.INFO_REZEPT_SAVE);
		
		ViewHandler.getInstance().switchView(KuchenrezeptAnzeigen.class);
	}

	// Methode zum Aendern eines Rezepts
	private void update() {

		// setzt Rezeptname
		kuchenrezept.setName(nameInput);

		// setzt ï¿½nderungsdatum
		java.util.Date date = new java.util.Date();
		Date date3 = new Date(date.getTime());
		kuchenrezept.setErstellt(date3);

		// setzt Kommentar
		kuchenrezept.setKommentar(kommentarInput);

		// setzt Artikel
		kuchenrezept.setArtikel(tmpZutaten);

		try {
			Kuchenrezeptverwaltung.getInstance().updateKuchenrezept(
					kuchenrezept);
			Kuchenrezeptverwaltung.getInstance().deleteZutatenZuKuchenrezept(
					kuchenrezept);
			KuchenrezeptDAO.getInstance().FussnoteKuchenDelete(kuchenrezept);
			Kuchenrezeptverwaltung.getInstance().saveArtikel(kuchenrezept);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (fussnoten.getValue().toString() != "[]") {
			List<String> FussnoteKuchenId = Arrays.asList(fussnoten.getValue()
					.toString()
					.substring(1, fussnoten.getValue().toString().length() - 1)
					.split("\\s*,\\s*"));

			List<KuchenrezeptHasFussnote> fussnotelist = new ArrayList<KuchenrezeptHasFussnote>();
			for (String sId : FussnoteKuchenId) {
				FussnoteKuchen fussnotekuchen = new FussnoteKuchen();
				try {
					fussnotekuchen = FussnoteKuchenDAO.getInstance()
							.getFussnoteKuchenByName(sId);
					KuchenrezeptHasFussnote a = new KuchenrezeptHasFussnote(
							fussnotekuchen, kuchenrezept);
					fussnotelist.add(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (KuchenrezeptHasFussnote i : fussnotelist) {
				try {
					KuchenrezeptDAO.getInstance().FussnoteKuchenAdd(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		((Application) UI.getCurrent().getData())
		.showDialog(IConstants.INFO_KUCHENREZEPT_UPDATE);
		ViewHandler.getInstance().switchView(KuchenrezeptAnzeigen.class);
	}

	// Funktion zur Validierung
	private Boolean validiereEingabe() {
		if (name.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_KUCHENREZEPT_NAME);
			return false;
		}
		if (tmpZutaten != null || tmpZutaten.size() != 0) {
			for (KuchenrezeptHasArtikel kha : tmpZutaten) {
				if (kha.getMenge() >= 100000.0) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REZEPT_MENGE);
					return false;
				}
			}
			if (tmpZutaten == null || tmpZutaten.size() == 0) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REZEPT_ZUTATEN);
				return false;
			}
		}

		return true;
	}

	// Funktion zum Hinzufügen neuer Artikel
	private void addArtikel() {
		final Window win = new Window("Neuer Artikel");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("400px");
		win.setHeight("600px");

		ArtikelErstellen ae = new ArtikelErstellen();
		addComponent(ae);

		win.setContent(ae);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				ladeArtikel();

			}
		});
	}

}
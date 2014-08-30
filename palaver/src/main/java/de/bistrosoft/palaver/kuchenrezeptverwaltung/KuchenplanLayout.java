package de.bistrosoft.palaver.kuchenrezeptverwaltung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.util.IConstants;
import de.palaver.Application;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * 
 * @author Christine Hartkorn
 * 
 */

@SuppressWarnings("serial")
public class KuchenplanLayout extends CustomComponent {

	// Konstanten
	KuchenplanLayout instance = this;
	private static final int ROWS = 8;
	private static final int COLUMNS = 2;
	public GridLayout layout = null;
	private Kuchenplan kuchenplan = null;

	// Labels
	private Label lbHeadlinePlatzhalter = new Label();
	private Label lbHeadline = new Label(
			"<div><B>Kuchenname&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Anzahl&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fußnoten</B></div>",
			ContentMode.HTML);

	// Tabellen
	public Table itemMoTable;
	public Table itemDiTable;
	public Table itemMiTable;
	public Table itemDoTable;
	public Table itemFrTable;
	public Table itemSaTable;
	public Table itemSoTable;
	public FilterTable kuchenTable;

	// Listen
	List<KuchenplanHasKuchenrezept> tmpItems = new ArrayList<KuchenplanHasKuchenrezept>();

	// BeanContainer
	private BeanItemContainer<Kuchenrezept> containerKuchen;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptMo;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptDi;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptMi;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptDo;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptFr;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptSa;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptSo;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptAlle = new BeanItemContainer<KuchenplanHasKuchenrezept>(
			KuchenplanHasKuchenrezept.class);

	public Kuchenplan getKuchenplan() {
		return kuchenplan;
	}

	public void setKuchenplan(Kuchenplan kuchenplan) {
		this.kuchenplan = kuchenplan;
	}

	// Seitenlayout erstellen
	@SuppressWarnings("deprecation")
	public KuchenplanLayout(int week, int year) {
		kuchenplan = null;
		kuchenplan = Kuchenplanverwaltung.getInstance()
				.getKuchenplanByWeekWithItems(new Week(week, year));
		if (kuchenplan == null) {
			kuchenplan = new Kuchenplan(new Week(week, year));
		}

		setSizeFull();

		// vertikales Layout anlegen
		HorizontalLayout outer = new HorizontalLayout();
		outer.setWidth("1000px");
		setCompositionRoot(outer);

		// GridLayout erstellen
		layout = new GridLayout(COLUMNS, ROWS);
		layout.setWidth("400px");
		layout.setStyleName("menueplan-grid");

		// GridLayout zu Seitenlayout hinzufügen
		outer.addComponent(layout);
		outer.setComponentAlignment(layout, Alignment.MIDDLE_LEFT);

		// Überschriften-Zeile
		lbHeadlinePlatzhalter.setWidth("149px");
		layout.addComponent(lbHeadlinePlatzhalter, 0, 0);
		layout.addComponent(lbHeadline, 1, 0);

		// Fülle Datumsspalte mit Wochentag und Datum
		ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(week,
				year);
		for (int row = 1; row < ROWS; row++) {
			GregorianCalendar date = dates.get(row - 1);
			String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2,
					Locale.GERMANY);

			String strDate = date.get(Calendar.DAY_OF_MONTH) + "."
					+ (date.get(Calendar.MONTH) + 1) + "."
					+ date.get(Calendar.YEAR);

			Label lbTmp = new Label("<div align=center><B>" + "\r\n" + strDay
					+ "\r\n" + strDate + "</B></div>", ContentMode.HTML);
			lbTmp.setHeight("102px");
			lbTmp.setWidth("149px");
			layout.addComponent(lbTmp, 0, row);
			layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
		}

		// Füge Listen für KuchenplanItems ein
		itemMoTable = new Table();
		itemMoTable.setWidth("430px");
		itemMoTable.setHeight("100px");
		itemMoTable.setStyleName("palaverTable2");
		itemMoTable.setImmediate(true);
		itemMoTable.setColumnWidth("kuchenname", 210);
		itemMoTable.setColumnWidth("anzahl", 70);
		itemMoTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptMo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemMoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptMo);
		itemMoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemMoTable.setEditable(true);
		itemMoTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemDiTable = new Table();
		itemDiTable.setWidth("430px");
		itemDiTable.setHeight("100px");
		itemDiTable.setStyleName("palaverTable2");
		itemDiTable.setPageLength(16);
		itemDiTable.setImmediate(true);
		itemDiTable.setColumnWidth("kuchenname", 210);
		itemDiTable.setColumnWidth("anzahl", 70);
		itemDiTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptDi = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemDiTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptDi);
		itemDiTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemDiTable.setEditable(true);
		itemDiTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemMiTable = new Table();
		itemMiTable.setWidth("430px");
		itemMiTable.setHeight("100px");
		itemMiTable.setStyleName("palaverTable2");
		itemMiTable.setPageLength(16);
		itemMiTable.setImmediate(true);
		itemMiTable.setColumnWidth("kuchenname", 210);
		itemMiTable.setColumnWidth("anzahl", 70);
		itemMiTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptMi = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemMiTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptMi);
		itemMiTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemMiTable.setEditable(true);
		itemMiTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemDoTable = new Table();
		itemDoTable.setWidth("430px");
		itemDoTable.setHeight("100px");
		itemDoTable.setStyleName("palaverTable2");
		itemDoTable.setPageLength(16);
		itemDoTable.setImmediate(true);
		itemDoTable.setColumnWidth("kuchenname", 210);
		itemDoTable.setColumnWidth("anzahl", 70);
		itemDoTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptDo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemDoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptDo);
		itemDoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemDoTable.setEditable(true);
		itemDoTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemFrTable = new Table();
		itemFrTable.setWidth("430px");
		itemFrTable.setHeight("100px");
		itemFrTable.setStyleName("palaverTable2");
		itemFrTable.setPageLength(16);
		itemFrTable.setImmediate(true);
		itemFrTable.setColumnWidth("kuchenname", 210);
		itemFrTable.setColumnWidth("anzahl", 70);
		itemFrTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptFr = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemFrTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptFr);
		itemFrTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemFrTable.setEditable(true);
		itemFrTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemSaTable = new Table();
		itemSaTable.setWidth("430px");
		itemSaTable.setHeight("100px");
		itemSaTable.setStyleName("palaverTable2");
		itemSaTable.setPageLength(16);
		itemSaTable.setImmediate(true);
		itemSaTable.setColumnWidth("kuchenname", 210);
		itemSaTable.setColumnWidth("anzahl", 70);
		itemSaTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptSa = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemSaTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptSa);
		itemSaTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemSaTable.setEditable(true);
		itemSaTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		itemSoTable = new Table();
		itemSoTable.setWidth("430px");
		itemSoTable.setHeight("100px");
		itemSoTable.setStyleName("palaverTable2");
		itemSoTable.setPageLength(16);
		itemSoTable.setImmediate(true);
		itemSoTable.setColumnWidth("kuchenname", 210);
		itemSoTable.setColumnWidth("anzahl", 70);
		itemSoTable.setColumnWidth("fussnoten", 100);
		containerKuchenplanHasKuchenrezeptSo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemSoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptSo);
		itemSoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl",
				"fussnoten" });
		itemSoTable.setEditable(true);
		itemSoTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		layout.addComponent(itemMoTable, 1, 1);
		layout.addComponent(itemDiTable, 1, 2);
		layout.addComponent(itemMiTable, 1, 3);
		layout.addComponent(itemDoTable, 1, 4);
		layout.addComponent(itemFrTable, 1, 5);
		layout.addComponent(itemSaTable, 1, 6);
		layout.addComponent(itemSoTable, 1, 7);

		// Füge Liste mit allen Kuchen ein
		kuchenTable = new FilterTable();
		kuchenTable.setSizeFull();
		kuchenTable.setWidth("350px");
		kuchenTable.setHeight("350px");
		kuchenTable.setColumnWidth("name", 210);
		kuchenTable.setColumnWidth("fussnoten", 100);
		kuchenTable.setStyleName("palaverTable2");
		kuchenTable.setFilterBarVisible(true);
		kuchenTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		outer.addComponent(kuchenTable);
		outer.setComponentAlignment(kuchenTable, Alignment.MIDDLE_RIGHT);

		// Füge Kuchenrezepte ein
		if (kuchenplan != null) {
			if (kuchenplan.getKuchenrezepte() != null) {
				containerKuchenplanHasKuchenrezeptAlle.removeAllItems();
				containerKuchenplanHasKuchenrezeptMo.removeAllItems();
				containerKuchenplanHasKuchenrezeptDi.removeAllItems();
				containerKuchenplanHasKuchenrezeptMi.removeAllItems();
				containerKuchenplanHasKuchenrezeptDo.removeAllItems();
				containerKuchenplanHasKuchenrezeptFr.removeAllItems();
				containerKuchenplanHasKuchenrezeptSa.removeAllItems();
				containerKuchenplanHasKuchenrezeptSo.removeAllItems();
				for (KuchenplanHasKuchenrezept kc : kuchenplan
						.getKuchenrezepte()) {
					// in Container
					containerKuchenplanHasKuchenrezeptAlle.addItem(kc);
					switch (kc.getTag()) {
					case 1:
						containerKuchenplanHasKuchenrezeptMo.addItem(kc);
						break;
					case 2:
						containerKuchenplanHasKuchenrezeptDi.addItem(kc);
						break;
					case 3:
						containerKuchenplanHasKuchenrezeptMi.addItem(kc);
						break;
					case 4:
						containerKuchenplanHasKuchenrezeptDo.addItem(kc);
						break;
					case 5:
						containerKuchenplanHasKuchenrezeptFr.addItem(kc);
						break;
					case 6:
						containerKuchenplanHasKuchenrezeptSa.addItem(kc);
						break;
					case 7:
						containerKuchenplanHasKuchenrezeptSo.addItem(kc);
						break;
					}
					// Tabellen aktualisieren
					itemMoTable.markAsDirty();
					itemDiTable.markAsDirty();
					itemMiTable.markAsDirty();
					itemDoTable.markAsDirty();
					itemFrTable.markAsDirty();
					itemSaTable.markAsDirty();
					itemSoTable.markAsDirty();

				}
			}
		}

		// Drag&Drop Kuchenliste
		kuchenTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen löschen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				KuchenplanHasKuchenrezept selected = (KuchenplanHasKuchenrezept) t
						.getData("itemId");
				Table tSource = (Table) t.getSourceComponent();
				@SuppressWarnings("unchecked")
				BeanItemContainer<KuchenplanHasKuchenrezept> bicSource = (BeanItemContainer<KuchenplanHasKuchenrezept>) tSource
						.getContainerDataSource();
				// Listen werden erst in speichern bzw. update-Methode erstellt
				bicSource.removeItem(selected);
				containerKuchenplanHasKuchenrezeptAlle.removeItem(selected);
				tSource.markAsDirty();
			}
		});

		// Drag&Drop Montag
		itemMoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemMoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 1, fn);
					containerKuchenplanHasKuchenrezeptMo.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemMoTable.markAsDirty();
			}
		});

		// Drag&Drop Dienstag
		itemDiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemDiTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 2, fn);
					containerKuchenplanHasKuchenrezeptDi.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemDiTable.markAsDirty();
			}
		});

		// Drag&Drop Mittwoch
		itemMiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemMiTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 3, fn);
					containerKuchenplanHasKuchenrezeptMi.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemMiTable.markAsDirty();
			}
		});

		// Drag&Drop Donnerstag
		itemDoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemDoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 4, fn);
					containerKuchenplanHasKuchenrezeptDo.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemDoTable.markAsDirty();
			}
		});

		// Drag&Drop Freitag
		itemFrTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemFrTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 5, fn);
					containerKuchenplanHasKuchenrezeptFr.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemFrTable.markAsDirty();
			}
		});

		// Drag&Drop Samstag
		itemSaTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemSaTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 6, fn);
					containerKuchenplanHasKuchenrezeptSa.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemSaTable.markAsDirty();
			}
		});

		// Drag&Drop Sonntag
		itemSoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemSoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					String fn = ((Kuchenrezept) t.getData("itemId"))
							.getFussnoten();
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 7, fn);
					containerKuchenplanHasKuchenrezeptSo.addItem(tmp);
					containerKuchenplanHasKuchenrezeptAlle.addItem(tmp);
				}
				itemSoTable.markAsDirty();
			}
		});

		try {
			containerKuchen = new BeanItemContainer<Kuchenrezept>(
					Kuchenrezept.class, Kuchenrezeptverwaltung.getInstance()
							.getAllKuchenrezepte());
			kuchenTable.setContainerDataSource(containerKuchen);
			kuchenTable.setVisibleColumns(new Object[] { "name", "fussnoten" });
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

	// Funktion zum Validieren und Speichern
	public void speichern(int week, int year) {
		tmpItems = containerKuchenplanHasKuchenrezeptAlle.getItemIds();
		kuchenplan.setKuchenrezepte(tmpItems);

		for (KuchenplanHasKuchenrezept khk : tmpItems) {
			if (khk.getAnzahl() >= 100000.0) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_KUCHENPLAN_MENGE);
				khk.setAnzahl(1);
				return;
			}
		}

		kuchenplan.setKuchenrezepte(tmpItems);
		Kuchenplanverwaltung.getInstance().persist(kuchenplan);
		((Application) UI.getCurrent().getData())
				.showDialog("Kuchenplan für Kalenderwoche " + week + "/" + year
						+ " wurde gespeichert");
	}
}

/**
 * 
 * Jan Lauinger
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.google.gwt.user.client.ui.RadioButton;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.IConstants;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	private HorizontalLayout box = new HorizontalLayout();
	private HorizontalLayout hol = new HorizontalLayout();
	private VerticalLayout eins = new VerticalLayout();
	private VerticalLayout zwei = new VerticalLayout();
	private VerticalLayout dummy = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	private HorizontalLayout hlRezeptdetails = new HorizontalLayout();
	private VerticalLayout vlRezeptdetailsLinks = new VerticalLayout();
	private VerticalLayout vlRezeptdetailsRechts = new VerticalLayout();
	private HorizontalLayout hlRezeptZutaten = new HorizontalLayout();

	private Table zutatenTable;
	private FilterTable artikelTable;

	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<RezeptHasArtikel> containerRezeptHasArtikel;

	Rezept rezept;

	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept bearbeiten</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label d1 = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	private TextField name = new TextField("Bezeichnung");
	private TextField portion = new TextField("%");

	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	private ComboBox mitarbeiterCb = new ComboBox("Koch");
	private ComboBox rezeptartCb = new ComboBox("Rezeptart");

	// private RadioButton menueRb = new RadioButton("Men�");
	// private RadioButton hauptgerichtRb = new RadioButton("Rezeptart");
	// private RadioButton beilageRb = new RadioButton("Beilage");

	private CheckBox menueCbx = new CheckBox("Rezept als Men� speichern");
	private OptionGroup rezeptartOg = new OptionGroup();

	// public static Table tblArtikel = new Table("Zutaten");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	// private Button zutatneu = new Button("Zutaten hinzufuegen");
	private Button update = new Button(IConstants.BUTTON_SAVE);

	private String nameInput;
	private String portionInput;
	private String kommentarInput;
	private String geschmackInput;
	private String rezeptartInput;
	private String mitarbeiterInput;
	private String menueInput;

	public String valueString = new String();

	private List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();
	List<Zubereitung> listzubereitung = new ArrayList<Zubereitung>();
	List<RezeptHasArtikel> artikel = new ArrayList<RezeptHasArtikel>();

	// private Button btAdd = new Button("Add");

	@SuppressWarnings("deprecation")
	public RezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		rezeptartOg.addItem("Hauptgericht");
		rezeptartOg.addItem("Beilage");

		rezeptartOg.select(2);
		rezeptartOg.setNullSelectionAllowed(false);
		rezeptartOg.setHtmlContentAllowed(true);
		rezeptartOg.setImmediate(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.setInputPrompt(nameInput);

		portion.setWidth("100%");
		portion.setImmediate(true);
		portion.setInputPrompt(portionInput);

		zubereitung.setWidth("100%");
		zubereitung.setImmediate(true);

		mitarbeiterCb.setWidth("100%");
		mitarbeiterCb.setImmediate(true);
		mitarbeiterCb.setInputPrompt(mitarbeiterInput);
		mitarbeiterCb.setNullSelectionAllowed(false);

		rezeptartCb.setWidth("100%");
		rezeptartCb.setImmediate(true);
		rezeptartCb.setInputPrompt(rezeptartInput);
		rezeptartCb.setNullSelectionAllowed(false);

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);

		box.setWidth("800px");
		box.setSpacing(true);

		this.addComponent(box);
		// this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(vlRezeptdetailsLinks);
		box.addComponent(vlRezeptdetailsRechts);
		this.addComponent(hlRezeptZutaten);
		vlRezeptdetailsLinks.addComponent(ueberschrift);
		vlRezeptdetailsLinks.addComponent(name);
		vlRezeptdetailsLinks.addComponent(portion);
		vlRezeptdetailsLinks.addComponent(mitarbeiterCb);
		vlRezeptdetailsLinks.addComponent(rezeptartOg);

		vlRezeptdetailsRechts.addComponent(zubereitung);
		// vlRezeptdetailsRechts.addComponent(hol);
		// vlRezeptdetailsRechts.setComponentAlignment(hol,
		// Alignment.MIDDLE_CENTER);
		// vlRezeptdetailsRechts.addComponent(eins);
		// vlRezeptdetailsRechts.addComponent(zwei);
		dummy.addComponent(d1);
		// box.addComponent(tblArtikel);
		// box.addComponent(zutatneu);
		vlRezeptdetailsRechts.addComponent(kommentar);
		box.addComponent(menueCbx);

		control.setSpacing(true);
		this.addComponent(control);
		this.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btSpeichern.setEnabled(false);

		control.addComponent(btVerwerfen);
		control.addComponent(btSpeichern);

		// tblArtikel.setSizeUndefined();
		// tblArtikel.setSelectable(true);
		// tblArtikel.setMultiSelect(true);
		// tblArtikel.setImmediate(true);
		// tblArtikel.setEditable(true);
		// tblArtikel.setVisible(false);

		// zutatneu.addClickListener(new ClickListener() {
		// @Override
		// public void buttonClick(ClickEvent event) {
		// ViewHandler.getInstance().switchView(
		// RezeptAnlegenZutatenliste.class);
		// }
		// // @Override
		// // public void buttonClick(final ClickEvent event) {
		// // WinSelectArtikel window = new WinSelectArtikel(tblArtikel,
		// // ausgArtikel);
		// // UI.getCurrent().addWindow(window);
		// // window.setModal(true);
		// // window.setWidth("50%");
		// // window.setHeight("50%");
		// // tblArtikel.setVisible(true);
		// // }
		// });

		name.addValueChangeListener(this);
		portion.addValueChangeListener(this);
		zubereitung.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
			}
		});
		rezeptartOg.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				rezeptartInput = valueString;
			}
		});
		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		menueCbx.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				menueInput = valueString;
			}
		});
		kommentar.addValueChangeListener(this);

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				speichern();
			}
		});

		// ////////////
		// form = new HorizontalLayout();
		hlRezeptZutaten.setSizeFull();

		// control = new HorizontalLayout();
		// control.setSpacing(true);

		// this.addComponent(fenster);

		// speichern = new Button(IConstants.BUTTON_SAVE);
		// verwerfen = new Button(IConstants.BUTTON_DISCARD);
		// speichern.setEnabled(false);
		//
		// speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		// verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		//
		// control.addComponent(verwerfen);
		// control.setComponentAlignment(verwerfen, Alignment.TOP_RIGHT);
		// control.addComponent(speichern);
		// control.setComponentAlignment(speichern, Alignment.TOP_RIGHT);

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		zutatenTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");

		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikel>(
				RezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerRezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit" });
		zutatenTable.setEditable(true);

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
				RezeptHasArtikel selected = (RezeptHasArtikel) t
						.getData("itemId");
				containerRezeptHasArtikel.removeItem(selected);
				containerArtikel.addItem(selected.getArtikel());
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		zutatenTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		/**
		 * Drag n Drop
		 */
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

					containerRezeptHasArtikel.addItem(new RezeptHasArtikel(
							selected));
				}

				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		hlRezeptZutaten.addComponent(zutatenTable);
		hlRezeptZutaten.addComponent(artikelTable);

		hlRezeptZutaten.setExpandRatio(zutatenTable, 2);
		hlRezeptZutaten.setExpandRatio(artikelTable, 1);
		hlRezeptZutaten.setSpacing(true);

		// fenster.addComponent(form);
		// fenster.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		// fenster.addComponent(control);
		// fenster.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		// fenster.setSpacing(true);
		//
		// fenster.setExpandRatio(form, 8);
		// fenster.setExpandRatio(control, 1);

		// verwerfen.addClickListener(new ClickListener() {
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// ViewHandler.getInstance()
		// .switchView(BestellungAuswaehlen.class);
		// }
		// });

		// speichern.addClickListener(new ClickListener()
		// {
		// @SuppressWarnings("unchecked")
		// public void buttonClick(ClickEvent event)
		// {
		// bestellData = containerBestellung.getItemIds();
		// bestellpositionen =
		// Bestellpositionverwaltung.getInstance().getBestellpositionen(bestellData);
		//
		// java.util.Date date2 = new java.util.Date();
		// Date date = new Date(date2.getTime());
		// bestellung = new Bestellung();
		// bestellung.setLieferant(lieferant);
		// bestellung.setDatum(date);
		// bestellung.setBestellpositionen(bestellpositionen);
		//
		// if(lieferant.getMehrereliefertermine() == true)
		// {
		// java.util.Date date3 = datetime.getValue();
		// Date datesql = new Date(date3.getTime());
		// java.util.Date date1 = datetime2.getValue();
		// Date datesql1 = new Date(date1.getTime());
		// bestellung.setLieferdatum(datesql);
		// bestellung.setLieferdatum2(datesql1);
		// }
		// else
		// {
		// java.util.Date date3 = datetime.getValue();
		// Date datesql = new Date(date3.getTime());
		// bestellung.setLieferdatum(datesql);
		// }
		//
		// try {
		// Bestellverwaltung.getInstance().createBestellung(bestellung);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// ViewHandler.getInstance().switchView(BestellungAuswaehlen.class);
		// }
		// });

		artikelTable.setCaption("Artikel");
		zutatenTable.setCaption("Zutatenliste");

		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikel());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable
					.setVisibleColumns(new Object[] { "name", "artikelnr" });
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }
		// ////////////

		load();
	}

	public void load() {

		mitarbeiterCb.removeAllItems();
		rezeptartCb.removeAllItems();
		zubereitung.removeAllItems();

		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterCb.addItem(e.getId());
				mitarbeiterCb.setItemCaption(e.getId(), e.getVorname());
			}

			List<Rezeptart> rezeptart = Rezeptartverwaltung.getInstance()
					.getAllRezeptart();
			for (Rezeptart e : rezeptart) {
				rezeptartCb.addItem(e.getId());
				rezeptartCb.setItemCaption(e.getId(), e.getName());
			}

			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			for (Zubereitung z : zb) {
				zubereitung.addItem(z.getId());
				zubereitung.setItemCaption(z.getId(), z.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		rezept = (Rezept) ((ViewDataObject<?>) data).getData();

		control.replaceComponent(btSpeichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);

		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				rezept.setName(name.getValue());

				// try {
				// rezept.setRezeptart(RezeptartDAO.getInstance()
				// .getRezeptartById(
				// Long.parseLong(rezeptartInput.toString())));
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }

				try {
					rezept.setMitarbeiter(MitarbeiterDAO
							.getInstance()
							.getMitarbeiterById(
									Long.parseLong(mitarbeiterInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// rezept.setKommentar(kommentar.getValue());

				// Änderungsdatum erfassen
				java.util.Date date = new java.util.Date();
				Date date3 = new Date(date.getTime());
				rezept.setErstellt(date3);

				String notification = "Rezept gespeichert";

				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");

				try {
					Rezeptverwaltung.getInstance().updateRezept(rezept);
				} catch (Exception e) {
					e.printStackTrace();
					notification = e.toString();
				}

				try {
					Rezeptverwaltung.getInstance().ZubereitungenDelete(rezept);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (zubereitung.getValue().toString() != "[]") {
					List<String> ZubereitungId = Arrays.asList(valueString
							.substring(1, valueString.length() - 1).split(
									"\\s*,\\s*"));

					List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();

					for (String sId : ZubereitungId) {
						Long id = null;
						try {
							id = Long.parseLong(sId.trim());

						} catch (NumberFormatException nfe) {

						}

						Zubereitung zubereitung1 = null;
						try {

							zubereitung1 = Zubereitungverwaltung.getInstance()
									.getZubereitungById(id);
							//
							RezeptHasZubereitung a = new RezeptHasZubereitung(
									zubereitung1, rezept);
							zubereitunglist.add(a);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					System.out.println(zubereitunglist);
					for (RezeptHasZubereitung i : zubereitunglist) {

						try {
							Rezeptverwaltung.getInstance().ZubereitungAdd(i);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}
				} else {
					System.out.println("zubereitungsliste ist leer");
				}

				Notification notification1 = new Notification(
						"Rezept wurde geändert!");
				notification1.setDelayMsec(500);
				notification1.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(
						RezeptAnzeigenTabelle.class);
			}

		});

		/**
		 * Daten in Felder schreiben
		 */

		// tblArtikel.setVisible(true);

		try {
			name.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId())
					.getName().toString());
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {
			listzubereitung = ZubereitungDAO.getInstance()
					.getZubereitungByRezept(rezept.getId());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (listzubereitung != null) {

			for (Zubereitung zb : listzubereitung) {

				zubereitung.select(zb.getId());
			}

		} else {

		}

		try {
			mitarbeiterCb.setValue(MitarbeiterDAO.getInstance()
					.getMitarbeiterByRezept(rezept.getId()).getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//
		// try {
		// rezeptartCb.setValue(RezeptartDAO.getInstance()
		// .getRezeptartByRezept(rezept.getId()).getId());
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }

		BeanItemContainer<RezeptHasArtikel> artikelcontainer;
		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();

		try {
			list = Rezeptverwaltung.getInstance().getAllArtikelByRezeptId1(
					rezept.getId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<RezeptHasArtikel>(
					RezeptHasArtikel.class, list);
			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname",
					"menge", "einheit" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}

		// ausgArtikel = list;

		rezept.setArtikel(list);

		portion.setValue("100");
		// kommentar.setValue(rezept.getKommentar());

		try {
			kommentar.setValue(RezeptDAO.getInstance()
					.getRezeptById(rezept.getId()).getKommentar().toString());
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		// name.addValueChangeListener(this);
		// portion.addValueChangeListener(this);
		// zubereitung.addValueChangeListener(this);
		// geschmackCb.addValueChangeListener(this);
		// rezeptartCb.addValueChangeListener(this);
		// mitarbeiterCb.addValueChangeListener(this);
		// kommentar.addValueChangeListener(this);
		nameInput = name.getValue();
		portionInput = portion.getValue();
		kommentarInput = kommentar.getValue();

		if (name.getValue() == "" || name.getValue() == null
				&& portion.getValue() == null || portion.getValue() == ""
				&& rezeptartCb.getValue() == ""
				|| rezeptartCb.getValue() == null
				&& mitarbeiterCb.getValue() == ""
				|| mitarbeiterCb.getValue() == null) {
			btSpeichern.setEnabled(false);
		} else {
			btSpeichern.setEnabled(true);
		}

	}

	public void rezeptSpeichern() {
		Rezept rezept = new Rezept();

		rezept.setName(nameInput);

		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		rezept.setErstellt(date2);
		rezept.setKommentar(kommentarInput);
		rezept.setPortion(Integer.parseInt(portionInput.toString()));
		try {
			rezept.setMitarbeiter(MitarbeiterDAO.getInstance()
					.getMitarbeiterById(
							Long.parseLong(mitarbeiterInput.toString())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Rezeptverwaltung.getInstance().createRezept(rezept);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// / Liste der Zubereitungen
		Rezept rez = null;
		try {
			System.out.println(nameInput);
			rez = Rezeptverwaltung.getInstance().getRezeptByName1(nameInput);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(rez);
		if (zubereitung.getValue().toString() != "[]") {
			List<String> ZubereitungId = Arrays.asList(valueString.substring(1,
					valueString.length() - 1).split("\\s*,\\s*"));

			List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();

			for (String sId : ZubereitungId) {
				Long id = null;
				try {
					id = Long.parseLong(sId.trim());

				} catch (NumberFormatException nfe) {

				}

				Zubereitung zubereitung1 = null;
				try {

					zubereitung1 = Zubereitungverwaltung.getInstance()
							.getZubereitungById(id);
					//
					RezeptHasZubereitung a = new RezeptHasZubereitung(
							zubereitung1, rez);
					zubereitunglist.add(a);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			System.out.println(zubereitunglist);
			for (RezeptHasZubereitung i : zubereitunglist) {

				try {
					Rezeptverwaltung.getInstance().ZubereitungAdd(i);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}

		BeanItemContainer<RezeptHasArtikel> bicArtikel = (BeanItemContainer<RezeptHasArtikel>) zutatenTable
				.getContainerDataSource();
		ausgArtikel = bicArtikel.getItemIds();
		rez.setArtikel(ausgArtikel);

		try {
			Rezeptverwaltung.getInstance().saveArtikel(rez);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Notification notification = new Notification(
				"Rezept wurde gespeichert!");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
		ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
	}

	private void speichern() {
		if (menueInput == "true") {
			rezeptSpeichern();
			rezeptAlsMenuSpeichern();
			rezeptAlsHauptgerichtSpeichern();
		} else {
			rezeptSpeichern();
		}
	}

	private void rezeptAlsHauptgerichtSpeichern() {
		Menue menue = new Menue();

		menue.setName(nameInput);
		try {
			menue.setKoch(MitarbeiterDAO.getInstance().getMitarbeiterById(
					Long.parseLong(mitarbeiterInput.toString())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Menueverwaltung.getInstance().createRezeptAlsMenue(menue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rezeptAlsMenuSpeichern() {
		MenueHasRezept mhr = new MenueHasRezept();
			try {
				mhr.setMenue(MenueDAO.getInstance().getMenueIdByName(nameInput));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				mhr.setRezept(RezeptDAO.getInstance().getRezeptByName1(nameInput));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				Menueverwaltung.getInstance().RezepteAdd(mhr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mhr.setHauptgericht(true);		
			Notification notification = new Notification(
					"Rezept wurde als Men� gespeichert!");
			notification.setDelayMsec(500);
			notification.show(Page.getCurrent());
	}

}

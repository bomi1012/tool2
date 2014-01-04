//package de.hska.awp.palaver2.gui.view;
//
//import java.sql.SQLException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.tepi.filtertable.FilterTable;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.event.ItemClickEvent;
//import com.vaadin.event.ItemClickEvent.ItemClickListener;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.CustomTable;
//import com.vaadin.ui.CustomTable.CellStyleGenerator;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import de.hska.awp.palaver.Application;
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
//import de.hska.awp.palaver.bestellverwaltung.service.Bestellpositionverwaltung;
//import de.hska.awp.palaver.bestellverwaltung.service.BestellungService;
//import de.hska.awp.palaver.dao.ConnectException;
//import de.hska.awp.palaver.dao.DAOException;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewHandler;
//import de.hska.awp.palaver2.util.customFilter;
//import de.hska.awp.palaver2.util.customFilterDecorator;
//
///**
// * 
// * @author PhilippT
// * 
// */
//@SuppressWarnings("serial")
//public class BestellungAnzeigen extends VerticalLayout implements View {
//
//	private static final Logger log = LoggerFactory.getLogger(BestellungAnzeigen.class.getName());
//
//	private VerticalLayout form = new VerticalLayout();
//	private HorizontalLayout fenster = new HorizontalLayout();
//	private HorizontalLayout control = new HorizontalLayout();
//
//	private FilterTable bestellungen = new FilterTable("Bestellungen");
//	private FilterTable bpositionen = new FilterTable("Bestellpositionen");
//	private Bestellung bestellung;
//	private Bestellposition bestellposition;
//
//	private BeanItemContainer<Bestellposition> bpcontainer;
//
//	private Button allBestellungen = new Button(IConstants.BUTTON_ALL_ORDERS);
//	private Button zurueck = new Button(IConstants.BUTTON_BACK);
//	private Button auswaehlen = new Button(IConstants.BUTTON_SELECT);
//
//	public BestellungAnzeigen() {
//		super();
//
//		this.setSizeFull();
//		this.setMargin(true);
//
//		form.setSizeFull();
//		form.setSpacing(true);
//
//		zurueck.setVisible(false);
//
//		fenster.setSizeFull();
//		fenster.setSpacing(true);
//		fenster.addComponentAsFirst(bestellungen);
//		fenster.addComponent(bpositionen);
//
//		bestellungen.setSizeFull();
//		bestellungen.setStyleName("palaverTable");
//		bpositionen.setSizeFull();
//		bpositionen.setStyleName("palaverTable");
//
//		fenster.setExpandRatio(bestellungen, 1);
//		fenster.setExpandRatio(bpositionen, 2);
//
//		bestellungen.setSelectable(true);
//		bestellungen.setFilterBarVisible(true);
//		bestellungen.setFilterGenerator(new customFilter());
//		bestellungen.setFilterDecorator(new customFilterDecorator());
//
//		bpositionen.setImmediate(true);
//		bpositionen.setFilterBarVisible(true);
//		bpositionen.setVisible(false);
//		bpositionen.setFilterGenerator(new customFilter());
//		bpositionen.setFilterDecorator(new customFilterDecorator());
//		bpositionen.setSelectable(true);
//
//		BeanItemContainer<Bestellung> container;
//		try {
//			container = new BeanItemContainer<Bestellung>(Bestellung.class, BestellungService.getInstance().getBestellungenLTWeeks());
//			bestellungen.setContainerDataSource(container);
//			bestellungen.setVisibleColumns(new Object[] { "bestellt", "lieferant", "datumS", "lieferdatumS", "lieferdatum2S" });
//			bestellungen.setColumnHeader("datumS", "Datum");
//			bestellungen.setColumnHeader("lieferdatumS", "lieferdatum - 1");
//			bestellungen.setColumnHeader("lieferdatum2S", "lieferdatum - 2");
//			bestellungen.sort(new Object[] { "id" }, new boolean[] { true });
//			bestellungen.setCellStyleGenerator(new CellStyleGenerator() {
//
//				@Override
//				public String getStyle(CustomTable source, Object itemId, Object propertyId) {
//					Bestellung b = (Bestellung) itemId;
//					if ("bestellt".equals(propertyId)) {
//						return b.isBestellt() ? "check" : "cross";
//					}
//
//					return "";
//				}
//			});
//			bestellungen.setColumnWidth("bestellt", 50);
//		} catch (Exception e) {
//			log.error(e.toString());
//		}
//
//		bestellungen.addItemClickListener(new ItemClickListener() {
//
//			@Override
//			public void itemClick(ItemClickEvent event) {
//				if (event.isDoubleClick()) {
//					auswaehlen.click();
//				}
//			}
//		});
//
//		bestellungen.addValueChangeListener(new ValueChangeListener() {
//
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				if (event.getProperty().getValue() != null) {
//					bestellung = (Bestellung) event.getProperty().getValue();
//				}
//			}
//
//		});
//
//		control.addComponent(auswaehlen);
//		control.addComponent(zurueck);
//		control.addComponent(allBestellungen);
//		form.addComponent(fenster);
//		form.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
//		form.addComponent(control);
//		form.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
//		form.setExpandRatio(fenster, 9);
//		form.setExpandRatio(control, 1);
//
//		this.addComponent(form);
//		this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
//
//		zurueck.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				allBestellungen.setVisible(true);
//				zurueck.setVisible(false);
//				ViewHandler.getInstance().switchView(BestellungAnzeigen.class);
//
//			}
//		});
//
//		auswaehlen.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//
//				if (validiereAuswaehlen() == true) {
//					try {
//						if (bestellung.getLieferant().getMehrereliefertermine() == false) {
//							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance()
//									.getBestellpositionenByBestellungId(bestellung.getId()));
//							bpcontainer.sort(new Object[] { "id" }, new boolean[] { true });
//							bpositionen.setContainerDataSource(bpcontainer);
//							bpositionen.setVisibleColumns(new Object[] { "artikelName", "bestellgroesse", "durchschnitt", "kantine", "gesamt" });
//							bpositionen.setColumnHeader("bestellgroesse", IConstants.GEBINDE);
//							bpositionen.setColumnWidth("kantine", 60);
//							bpositionen.setColumnWidth("gesamt", 60);
//							bpositionen.setColumnWidth("durchschnitt", 60);
//							bpositionen.setColumnHeader("durchschnitt", "MENGE");
//							bpositionen.sort(new Object[] { "id" }, new boolean[] { true });
//							bpositionen.setCellStyleGenerator(new CellStyleGenerator() {
//
//								@Override
//								public String getStyle(CustomTable source, Object itemId, Object propertyId) {
//									Bestellposition bp = (Bestellposition) itemId;
//									return bp.isGeliefert() ? "status-1" : "status-none";
//								}
//							});
//						} else {
//							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance()
//									.getBestellpositionenByBestellungId(bestellung.getId()));
//							bpcontainer.sort(new Object[] { "id" }, new boolean[] { true });
//							bpositionen.setContainerDataSource(bpcontainer);
//							bpositionen.setVisibleColumns(new Object[] { "artikelName", "bestellgroesse", "durchschnitt", "kantine", "gesamt",
//									"freitag", "montag" });
//							bpositionen.setColumnHeader("bestellgroesse", IConstants.GEBINDE);
//							bpositionen.setColumnWidth("kantine", 60);
//							bpositionen.setColumnWidth("gesamt", 60);
//							bpositionen.setColumnWidth("durchschnitt", 60);
//							bpositionen.setColumnWidth("freitag", 60);
//							bpositionen.setColumnWidth("montag", 60);
//							bpositionen.setColumnHeader("durchschnitt", "MENGE");
//							bpositionen.setColumnHeader("freitag", "Termin 1");
//							bpositionen.setColumnHeader("montag", "Termin 2");
//							bpositionen.sort(new Object[] { "id" }, new boolean[] { true });
//							bpositionen.setCellStyleGenerator(new CellStyleGenerator() {
//
//								@Override
//								public String getStyle(CustomTable source, Object itemId, Object propertyId) {
//									Bestellposition bp = (Bestellposition) itemId;
//									return bp.isGeliefert() ? "status-1" : "status-none";
//								}
//							});
//						}
//						bpositionen.setVisible(true);
//						bpositionen.addValueChangeListener(new ValueChangeListener() {
//							@Override
//							public void valueChange(ValueChangeEvent event) {
//								if (event.getProperty().getValue() != null) {
//									bestellposition = (Bestellposition) event.getProperty().getValue();
//									bestellposition.setGeliefert(!bestellposition.isGeliefert());
//									bpcontainer.removeItem(bestellposition);
//									bpcontainer.addBean(bestellposition);
//									bpcontainer.sort(new Object[] { "id" }, new boolean[] { true });
//									try {
//										Bestellpositionverwaltung.getInstance().updateBestellposition(bestellposition);
//									} catch (ConnectException e) {
//										e.printStackTrace();
//									} catch (DAOException e) {
//										e.printStackTrace();
//									} catch (SQLException e) {
//										e.printStackTrace();
//									}
//									bpositionen.markAsDirtyRecursive();
//								}
//							}
//						});
//						bpositionen.addItemClickListener(new ItemClickListener() {
//							@Override
//							public void itemClick(ItemClickEvent event) {
//								bpositionen.markAsDirty();
//							}
//						});
//					} catch (Exception e) {
//						log.error(e.toString());
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//
//		allBestellungen.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//
//				allBestellungen.setVisible(false);
//				zurueck.setVisible(true);
//				BeanItemContainer<Bestellung> ncontainer;
//				try {
//					ncontainer = new BeanItemContainer<Bestellung>(Bestellung.class, BestellungService.getInstance().getAllBestellungen());
//					bestellungen.setContainerDataSource(ncontainer);
//					bestellungen.setVisibleColumns(new Object[] { "bestellt", "lieferant", "datumS", "lieferdatumS", "lieferdatum2S" });
//					bestellungen.setColumnHeader("datumS", "Datum");
//					bestellungen.setColumnHeader("lieferdatumS", "lieferdatum - 1");
//					bestellungen.setColumnHeader("lieferdatum2S", "lieferdatum - 2");
//					bestellungen.sort(new Object[] { "id" }, new boolean[] { true });
//					bestellungen.setCellStyleGenerator(new CellStyleGenerator() {
//
//						@Override
//						public String getStyle(CustomTable source, Object itemId, Object propertyId) {
//							Bestellung b = (Bestellung) itemId;
//							if ("bestellt".equals(propertyId)) {
//								return b.isBestellt() ? "check" : "cross";
//							}
//
//							return "";
//						}
//					});
//					bestellungen.setColumnWidth("bestellt", 50);
//				} catch (Exception e) {
//					log.error(e.toString());
//				}
//
//			}
//		});
//	}
//
//	public boolean validiereAuswaehlen() {
//
//		if (bestellungen.isValid() == false || bestellungen.getValue() == null) {
//			((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_BESTELLUNG_AUSWAEHLEN);
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	@Override
//	public void getViewParam(ViewData data) {
//		// TODO Auto-generated method stub
//
//	}
//
//}

package de.palaver2.view;
//package de.hska.awp.palaver2.gui.view;
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
//import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
//import de.hska.awp.palaver.bestellverwaltung.service.BestellungService;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewDataObject;
//import de.hska.awp.palaver2.util.ViewHandler;
//
//@SuppressWarnings("serial")
//public class BestellungBearbeitenAuswaehlen extends VerticalLayout implements View {
//
//	private static final Logger log = LoggerFactory.getLogger(BestellungLieferantAuswaehlen.class.getName());
//
//	private VerticalLayout fenster = new VerticalLayout();
//	private HorizontalLayout hl = new HorizontalLayout();
//
//	private Button auswaehlen = new Button(IConstants.BUTTON_SELECT);
//	private Button loeschen = new Button(IConstants.BUTTON_DELETE);
//
//	private FilterTable bestellungen = new FilterTable("Bestellung");
//	private Bestellung bestellung;
//
//	public BestellungBearbeitenAuswaehlen() {
//		super();
//
//		this.setSizeFull();
//		this.setMargin(true);
//
//		fenster.setSizeFull();
//		fenster.setSpacing(true);
//		fenster.addComponentAsFirst(bestellungen);
//
//		hl.setSpacing(true);
//		hl.setSizeFull();
//		hl.addComponent(loeschen);
//		hl.addComponent(auswaehlen);
//		hl.setComponentAlignment(auswaehlen, Alignment.TOP_RIGHT);
//		hl.setComponentAlignment(loeschen, Alignment.TOP_RIGHT);
//		hl.setExpandRatio(loeschen, 1);
//
//		// fenster.addComponent(auswaehlen);
//		// fenster.addComponent(loeschen);
//		fenster.addComponent(hl);
//		fenster.setExpandRatio(hl, 1);
//		// fenster.setComponentAlignment(auswaehlen, Alignment.TOP_RIGHT);
//		fenster.setComponentAlignment(hl, Alignment.TOP_RIGHT);
//
//		auswaehlen.setEnabled(true);
//		auswaehlen.addClickListener(new ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				if (validiereAuswaehlen()) {
//					ViewHandler.getInstance().switchView(BestellungBearbeiten.class, new ViewDataObject<Bestellung>(bestellung));
//				}
//			}
//		});
//
//		loeschen.setEnabled(true);
//		loeschen.addClickListener(new ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				if (validiereAuswaehlen()) {
//					try {
//						BestellungService.getInstance().deleteBestellung(bestellung);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
//				}
//			}
//		});
//
//		bestellungen.setSizeFull();
//
//		fenster.setExpandRatio(bestellungen, 9);
//
//		bestellungen.setSelectable(true);
//		bestellungen.setFilterBarVisible(true);
//
//		BeanItemContainer<Bestellung> container;
//		try {
//			container = new BeanItemContainer<Bestellung>(Bestellung.class, BestellungService.getInstance().getAllBestellungenNotOrdered());
//			bestellungen.setContainerDataSource(container);
//			bestellungen.setVisibleColumns(new Object[] { "bestellt", "lieferant", "datumS", "lieferdatumS" });
//			bestellungen.setColumnHeader("datumS", "datum");
//			bestellungen.setColumnHeader("lieferdatumS", "lieferdatum");
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
//		bestellungen.addItemClickListener(new ItemClickListener() {
//
//			@Override
//			public void itemClick(ItemClickEvent event) {
//				if (event.isDoubleClick()) {
//					auswaehlen.click();
//
//				}
//			}
//		});
//
//		this.addComponent(fenster);
//		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
//
//	}
//
//	public boolean validiereAuswaehlen() {
//		if (bestellung == null) {
//			((Application) UI.getCurrent().getData())
//				.showDialog(IConstants.INFO_BESTELLUNG_AUSWAEHLEN);
//			return false;
//		}
//		else {
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

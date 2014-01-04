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
//import com.vaadin.server.ThemeResource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import de.hska.awp.palaver.Application;
//import de.hska.awp.palaver.bestellverwaltung.service.BestellungService;
//import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
//import de.hska.awp.palaver2.util.IConstants;
//import de.hska.awp.palaver2.util.View;
//import de.hska.awp.palaver2.util.ViewData;
//import de.hska.awp.palaver2.util.ViewDataObject;
//import de.hska.awp.palaver2.util.ViewHandler;
//import de.hska.awp.palaver2.util.customFilter;
//import de.hska.awp.palaver2.util.customFilterDecorator;
//
//@SuppressWarnings("serial")
//public class BestellungLieferantAuswaehlen extends VerticalLayout implements View {
//	
//	private static final Logger	log	= LoggerFactory.getLogger(BestellungLieferantAuswaehlen.class.getName());
//
//	private FilterTable table;
//	private HorizontalLayout box = new HorizontalLayout();
//	private VerticalLayout mitte = new VerticalLayout();
//	private Button showFilter;
//	private Button bestellen = new Button("Bestellung anlegen");
//	private Lieferant lieferant;
//	private Label headline = new Label("Alle Lieferanten mit Artikeln");
//
//	public BestellungLieferantAuswaehlen() {
//
//		super();
//
//		this.setSizeFull();
//		this.setMargin(true);
//		this.addComponent(mitte);
//		this.setComponentAlignment(mitte, Alignment.MIDDLE_CENTER);
//
//		box.setWidth("100%");
//
//		mitte.setWidth("400px");
//		mitte.setSpacing(true);
//		box.setSpacing(true);
//
//		headline.setStyleName("ViewHeadline");
//
//		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
//		showFilter.setIcon(new ThemeResource("img/filter.ico"));
//
//		box.addComponent(headline);
//		box.setComponentAlignment(headline, Alignment.BOTTOM_LEFT);
//		box.addComponent(showFilter);
//		box.setComponentAlignment(showFilter, Alignment.BOTTOM_RIGHT);
//		box.setExpandRatio(headline, 1);
//
//		table = new FilterTable();
//		table.setStyleName("palaverTable");
//		table.setSizeFull();
//		table.setFilterBarVisible(false);
//		table.setFilterGenerator(new customFilter());
//		table.setFilterDecorator(new customFilterDecorator());
//		table.setSelectable(true);
//
//		table.addValueChangeListener(new ValueChangeListener() {
//
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				if (event.getProperty().getValue() != null) {
//					lieferant = (Lieferant) event.getProperty().getValue();
//				}
//			}
//		});
//
//		table.addItemClickListener(new ItemClickListener() {
//			@Override
//			public void itemClick(ItemClickEvent event) {
//				if (event.isDoubleClick()) {
//					bestellen.click();
//				}
//
//			}
//		});
//
//		BeanItemContainer<Lieferant> container;
//		try {
//			container = new BeanItemContainer<Lieferant>(Lieferant.class, BestellungService.getInstance().getLieferantenWithArtikel());
//			table.setContainerDataSource(container);
//			table.setVisibleColumns(new Object[] { "name" });
//			table.sort(new Object[] { "name" }, new boolean[] { true });
//		} catch (Exception e) {
//			log.error(e.toString());
//		}
//
//		mitte.addComponent(box);
//		mitte.addComponent(table);
//		mitte.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
//		mitte.setExpandRatio(table, 1);
//		mitte.addComponent(bestellen);
//		mitte.setComponentAlignment(bestellen, Alignment.BOTTOM_RIGHT);
//
//		bestellen.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				if (validiereLieferant()) {
//					ViewHandler.getInstance().switchView(ManuelleBestellungErstellen.class, new ViewDataObject<Lieferant>(lieferant));
//				}
//			}
//		});
//
//		showFilter.addClickListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				if (table.isFilterBarVisible()) {
//					table.setFilterBarVisible(false);
//					table.resetFilters();
//					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
//					showFilter.setIcon(new ThemeResource("img/filter.ico"));
//				} else {
//					table.setFilterBarVisible(true);
//					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
//					showFilter.setIcon(new ThemeResource("img/disable_filter.ico"));
//				}
//			}
//		});
//	}
//
//	protected boolean validiereLieferant() {
//		
//		if (lieferant == null ) {
//			((Application) UI.getCurrent().getData())
//					.showDialog(IConstants.INFO_LIEFERANT_AUSWAEHLEN);
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
//
//	@Override
//	public void getViewParam(ViewData data) {
//	}
//
//}

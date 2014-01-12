package de.palaver.view.lieferantenverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class LieferantAnzeigen extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory.getLogger(LieferantAnzeigen.class.getName());

	private FilterTable table;

	private Button showFilter;
	private Button auswaehlen;
	private Label headline;

	private HorizontalLayout head;
	private Lieferant lieferant;

	public LieferantAnzeigen() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		showFilter = new Button(IConstants.BUTTON_CLEAR_FILTER);
		showFilter.setIcon(new ThemeResource("img/disable_filter.ico"));

		auswaehlen = new Button(IConstants.BUTTON_SELECT);
		auswaehlen.setHeight("50px");

		headline = new Label("Alle Lieferanten");
		headline.setStyleName("ViewHeadline");

		head = new HorizontalLayout();
		head.setWidth("100%");

		head.addComponent(headline);
		head.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);
		head.addComponent(showFilter);
		head.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		head.setExpandRatio(headline, 1);

		table = new FilterTable();
		table.setStyleName("palaverTable");
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					lieferant = (Lieferant) event.getProperty().getValue();
					auswaehlen.setEnabled(true);
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					auswaehlen.click();
				}

			}
		});

		auswaehlen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (lieferant != null) {
					ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
				}
				else {
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_LIEFERANT_AUSWAEHLEN);
				}
			}
		});

		BeanItemContainer<Lieferant> container;
		try {
			container = new BeanItemContainer<Lieferant>(Lieferant.class, Lieferantenverwaltung.getInstance().getAllLieferanten());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "kundennummer", "bezeichnung", "strasse", "plz", "ort", "email", "telefon", "fax",
					"notiz" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		this.addComponent(head);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(auswaehlen);
		this.setComponentAlignment(auswaehlen, Alignment.BOTTOM_RIGHT);

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
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
				table.resetFilters();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util
	 * .ViewData)
	 */
	@Override
	public void getViewParam(ViewData data) {
	}
}
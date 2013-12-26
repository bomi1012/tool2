/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.hska.awp.palaver2.gui.view.artikelverwaltung;

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
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Sebastian Walz Diese Klasse gibt eine Tabelle aus, in der alle
 *         Artikel angezeigt werden. Klick man doppelt auf einen, kommt man
 *         direkt zur UpdateForm.
 */
@SuppressWarnings("serial")
public class ArtikelAnzeigen extends ArtikelAbstract implements View {
	private static final Logger log = LoggerFactory.getLogger(ArtikelAnzeigen.class.getName());

	private FilterTable table;
	
	public ArtikelAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		filterButton = new Button(IConstants.BUTTON_CLEAR_FILTER);
		filterButton.setIcon(new ThemeResource("img/disable_filter.ico"));

		auswaehlenButton = new Button(IConstants.BUTTON_SELECT);
		auswaehlenButton.setHeight("50px");

		headlineLabel = new Label("Alle Artikel");
		headlineLabel.setStyleName("ViewHeadline");

		controlHL = new HorizontalLayout();
		controlHL.setWidth("100%");
		controlHL.addComponent(headlineLabel);
		controlHL.setComponentAlignment(headlineLabel, Alignment.MIDDLE_LEFT);
		controlHL.addComponent(filterButton);
		controlHL.setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);
		controlHL.setExpandRatio(headlineLabel, 1);

		table = new FilterTable();
		table.setStyleName("palaverTable");
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);
		
		this.addComponent(controlHL);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(auswaehlenButton);
		this.setComponentAlignment(auswaehlenButton, Alignment.BOTTOM_RIGHT);

		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					artikel = (Artikel) event.getProperty().getValue();
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					auswaehlenButton.click();
				}

			}
		});
		
		auswaehlenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (artikel != null) {
					ViewHandler.getInstance().switchView(ArtikelErstellen.class, new ViewDataObject<Artikel>(artikel));
				}
				else {
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_AUSWAEHLEN);
				}
			}
		});

		BeanItemContainer<Artikel> container;
		try {
			container = new BeanItemContainer<Artikel>(Artikel.class, Artikelverwaltung.getInstance().getActiveArtikeln());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "artikelnr", "lieferant", "kategorie", 
					"lagerort", "preis", "standard", "grundbedarf", "bestellgroesse", "notiz" });
			
			table.sort(new Object[] { "name" }, new boolean[] { true });			
			table.setColumnWidth("kategorie", 70);
			table.setColumnWidth("artikelnr", 60);
			table.setColumnHeader("artikelnummer", "nummer");
			table.setColumnWidth("preis", 50);
			table.setColumnWidth("bestellgroesse", 50);
			table.setColumnHeader("bestellgroesse", "gebinde");

			table.setCellStyleGenerator(new CellStyleGenerator() {
				@Override
				public String getStyle(CustomTable source, Object itemId, Object propertyId) {
					Artikel artikel = (Artikel) itemId;
					if ("standard".equals(propertyId)) {
						return artikel.isStandard() ? "check" : "cross";
					}
					if ("grundbedarf".equals(propertyId)) {
						return artikel.isGrundbedarf() ? "check" : "cross";
					}
					return "";
				}
			});
			table.setColumnWidth("standard", 60);
			table.setColumnWidth("grundbedarf", 80);
		} catch (Exception e) {
			log.error(e.toString());
		}

		filterButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {	
	}
}

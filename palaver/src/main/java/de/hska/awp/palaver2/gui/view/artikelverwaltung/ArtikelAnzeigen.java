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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
public class ArtikelAnzeigen extends ArtikelverwaltungView implements View {
	private static final Logger log = LoggerFactory.getLogger(ArtikelAnzeigen.class.getName());

	private FilterTable table;
	
	public ArtikelAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		filterButton = new Button(IConstants.BUTTON_CLEAR_FILTER);
		filterButton.setIcon(new ThemeResource("img/disable_filter.ico"));

		m_auswaehlenButton = new Button(IConstants.BUTTON_SELECT);
		m_auswaehlenButton.setHeight("50px");

		m_headlineLabel = headLine(m_headlineLabel, "Alle Artikel", "ViewHeadline");

		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setWidth("100%");
		m_horizontalLayout.addComponent(m_headlineLabel);
		m_horizontalLayout.setComponentAlignment(m_headlineLabel, Alignment.MIDDLE_LEFT);
		m_horizontalLayout.addComponent(filterButton);
		m_horizontalLayout.setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);
		m_horizontalLayout.setExpandRatio(m_headlineLabel, 1);

		table = new FilterTable();
		table.setStyleName("palaverTable");
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);
		
		this.addComponent(m_horizontalLayout);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(m_auswaehlenButton);
		this.setComponentAlignment(m_auswaehlenButton, Alignment.BOTTOM_RIGHT);

		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_artikel = (Artikel) event.getProperty().getValue();
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_auswaehlenButton.click();
				}

			}
		});
		
		m_auswaehlenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_artikel != null) {
					ViewHandler.getInstance().switchView(ArtikelErstellen.class, new ViewDataObject<Artikel>(m_artikel));
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
	
	private VerticalLayout vLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);

		box.addComponent(m_headlineLabel);
		box.addComponent(new Hr());
	

		return box;
	}
}

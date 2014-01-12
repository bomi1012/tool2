/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.palaver2.view.artikelverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.artikelverwaltung.Artikel;
import de.palaver.service.artikelverwaltung.ArtikelService;

/**
 * @author Sebastian Walz Diese Klasse gibt eine Tabelle aus, in der alle
 *         Artikel angezeigt werden. Klick man doppelt auf einen, kommt man
 *         direkt zur UpdateForm.
 */
@SuppressWarnings("serial")
public class ArtikelAnzeigen extends OverAnzeigen implements View {
	private static final Logger LOG = LoggerFactory.getLogger(ArtikelAnzeigen.class.getName());
	private static final String ARTIKELN_ALL = "Alle Artikeln";
	private HorizontalLayout m_filterControl;
	private BeanItemContainer<Artikel> m_container = null;
	
	public ArtikelAnzeigen() {
		super();
		template();
		listeners();
		beans();
	}

	private void template() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, ARTIKELN_ALL, STYLE_HEADLINE);
		m_filterControl = filterLayout(m_headlineLabel);		
		m_filterTable = filterTable();
		m_control = controlPanelEditAndNew();
		
		this.setSpacing(true);				
		this.addComponent(m_filterControl);		
		this.addComponent(m_filterTable);
		this.setExpandRatio(m_filterTable, 1);
		this.addComponent(m_control);
		this.setComponentAlignment(m_control, Alignment.BOTTOM_RIGHT);
		
	}

	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_editButton.setEnabled(true);
					m_artikel = (Artikel) event.getProperty().getValue();
				}
			}
		});

		m_filterTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_editButton.click();
				}
			}
		});
		
		m_editButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_artikel != null) {
					windowModal(m_artikel);
					//ViewHandler.getInstance().switchView(ArtikelErstellen.class, new ViewDataObject<Artikel>(m_artikel));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_ARTIKEL_AUSWAEHLEN);
				}
			}
		});
		m_createButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModal(null);
			}
		});	
		
		m_filterButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_filterTable.resetFilters();
			}
		});
	}

	private void windowModal(Artikel artikel) {
		win = windowUI(win, ARTIKEL, "90%", "95%");		
		if(artikel != null) {
			m_artikelErstellen = new ArtikelErstellen(artikel);
		} else {
			m_artikelErstellen = new ArtikelErstellen();
	}
		addComponent(m_artikelErstellen);
		win.setContent(m_artikelErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		m_artikelErstellen.m_speichernButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				m_container.addItem(m_artikelErstellen.m_artikel);
				setTable();
			}
		});
		m_artikelErstellen.m_deaktivierenButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				if(m_artikelErstellen.m_okRemove) {
					m_container.removeItem(m_artikel);
					setTable();
					m_okRemove = false;
				}
			}
		});	
	}
	
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { FIELD_NAME, FIELD_ARTIKEL_NR, FIELD_LIEFERANT, FIELD_KATEGORIE, 
				FIELD_LAGERORT, FIELD_PREIS, FIELD_STANDARD, FIELD_GRUNDBEDARF, FIELD_BESTELLGROESSE, FIELD_NOTIZ });			
		m_filterTable.sort(new Object[] { FIELD_NAME }, new boolean[] { true });			
		m_filterTable.setColumnWidth(FIELD_KATEGORIE, 70);
		m_filterTable.setColumnWidth(FIELD_ARTIKEL_NR, 60);
		m_filterTable.setColumnHeader(FIELD_ARTIKEL_NR, "nummer");
		m_filterTable.setColumnWidth(FIELD_PREIS, 50);
		m_filterTable.setColumnWidth(FIELD_BESTELLGROESSE, 50);
		m_filterTable.setColumnHeader(FIELD_BESTELLGROESSE, "gebinde");

		m_filterTable.setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId) {
				Artikel artikel = (Artikel) itemId;
				if (FIELD_STANDARD.equals(propertyId)) {
					return artikel.isStandard() ? "check" : "cross";
				}
				if (FIELD_GRUNDBEDARF.equals(propertyId)) {
					return artikel.isGrundbedarf() ? "check" : "cross";
				}
				return "";				}
		});
		m_filterTable.setColumnWidth(FIELD_STANDARD, 60);
		m_filterTable.setColumnWidth(FIELD_GRUNDBEDARF, 80);
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Artikel>(Artikel.class, ArtikelService.getInstance().getActiveArtikeln());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	@Override
	public void getViewParam(ViewData data) {	 }
}

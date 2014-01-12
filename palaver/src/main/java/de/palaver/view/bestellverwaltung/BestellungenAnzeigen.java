package de.palaver.view.bestellverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellungService;

@SuppressWarnings("serial")
public class BestellungenAnzeigen extends OverBestellverwaltungView implements View,
ValueChangeListener {

	private static final Logger LOG = LoggerFactory.getLogger(BestellungenAnzeigen.class.getName());
	private BeanItemContainer<Bestellung> m_container;
	
	public BestellungenAnzeigen() {
		super();
		template();
		listeners();
		beans();
	}
	
	private void template() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, "Alle Bestellungen", STYLE_HEADLINE);
		m_filterTable = filterTable();
		
		this.setSpacing(true);				
		this.addComponent(m_filterTable);
	}
	
	private void listeners() {
				
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Bestellung>(Bestellung.class, BestellungService.getInstance().getAllBestellungen());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}

	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "lieferant", "kategorie", 
				"lieferdatum1", "lieferdatum2", "datum", "mitarbeiter", "status"});			
		m_filterTable.sort(new Object[] { "lieferant" }, new boolean[] { true });			
		m_filterTable.setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId) {
				Bestellung bestellung = (Bestellung) itemId;
				if ("status".equals(propertyId)) {
					return bestellung.getStatus() ? "check" : "cross";
				}
				return "";				}
		});
		
		m_filterTable.setColumnHeader("lieferdatum1", "liefertermin1");
		m_filterTable.setColumnHeader("lieferdatum2", "liefertermin2");
		m_filterTable.setColumnHeader("datum", "erstellt");
		m_filterTable.setColumnHeader("status", "bestellt");
		m_filterTable.setColumnHeader("mitarbeiter", "erstellt von...");
		m_filterTable.setColumnWidth("status", 50);
		m_filterTable.setColumnWidth("mitarbeiter", 180);
		m_filterTable.setColumnWidth("datum", 140);
		m_filterTable.setColumnWidth("lieferdatum1", 140);
		m_filterTable.setColumnWidth("lieferdatum2", 140);
		m_filterTable.setColumnAlignment("status", m_filterTable.ALIGN_CENTER);
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		
	}
}

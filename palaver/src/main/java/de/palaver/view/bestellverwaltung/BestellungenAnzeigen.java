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
	private static final String BESTELLUNG_ALL = "Alle Bestellungen";	
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
		m_headlineLabel = headLine(m_headlineLabel, BESTELLUNG_ALL, STYLE_HEADLINE);
		m_filterTable = filterTable();
		
		this.setSpacing(true);				
		this.addComponent(m_filterTable);
	}
	
	private void listeners() {
				//TODO:
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Bestellung>(Bestellung.class, BestellungService.getInstance().getAllBestellungen());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { FIELD_LIEFERANT, FIELD_KATEGORIE, 
				FIELD_LIEFERDATUM_1, FIELD_LIEFERDATUM_2, FIELD_DATUM, FIELD_MITARBEITER, FIELD_STATUS});			
		m_filterTable.sort(new Object[] { FIELD_LIEFERANT }, new boolean[] { true });			
		m_filterTable.setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId) {
				Bestellung bestellung = (Bestellung) itemId;
				if (FIELD_STATUS.equals(propertyId)) {
					return bestellung.getStatus() ? "check" : "cross";
				}
				return "";				
			}
		});
		
		m_filterTable.setColumnHeader(FIELD_LIEFERDATUM_1, "liefertermin1");
		m_filterTable.setColumnHeader(FIELD_LIEFERDATUM_2, "liefertermin2");
		m_filterTable.setColumnHeader(FIELD_DATUM, "erstellt");
		m_filterTable.setColumnHeader(FIELD_STATUS, "bestellt");
		m_filterTable.setColumnHeader(FIELD_MITARBEITER, "erstellt von...");
		m_filterTable.setColumnWidth(FIELD_STATUS, 50);
		m_filterTable.setColumnWidth(FIELD_MITARBEITER, 180);
		m_filterTable.setColumnWidth(FIELD_DATUM, 140);
		m_filterTable.setColumnWidth(FIELD_LIEFERDATUM_1, 140);
		m_filterTable.setColumnWidth(FIELD_LIEFERDATUM_2, 140);
		m_filterTable.setColumnAlignment(FIELD_STATUS, m_filterTable.ALIGN_CENTER);		
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {
		
	}
}

package de.palaver.view.bestellverwaltung;

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
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellungService;
import de.palaver.view.bestellverwaltung.popup.BestellpositionenVorschau;

@SuppressWarnings("serial")
public class BestellungenAnzeigenTable extends OverBestellverwaltungView implements View,
ValueChangeListener {

	private static final Logger LOG = LoggerFactory.getLogger(BestellungenAnzeigenTable.class.getName());
	private static final String BESTELLUNG_ALL = "Alle Bestellungen";	
	private BeanItemContainer<Bestellung> m_container;
	
	public BestellungenAnzeigenTable() {
		super();
		layout();
		listeners();
		beans();
	}
	
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, BESTELLUNG_ALL, STYLE_HEADLINE);
		m_control = controlAlleBestellungenPanel();
		m_filterTable = filterTable();
		
		this.setSpacing(true);				
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
					m_bestellung = (Bestellung) event.getProperty().getValue();
					m_control.setEnabled(true);
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
				if (m_bestellung != null) {
					windowModal(m_bestellung);
				}
				else {
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_BESTELLUNG_AUSWAEHLEN);
				}
			}
		});
		
		m_deleteButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					BestellungService.getInstance().deleteBestellung(m_bestellung.getId());
					m_container.removeItem(m_bestellung);
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		m_perEmailAttachButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				
			}

		});
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
		m_filterTable.setFilterFieldValue(FIELD_STATUS, false);
	}

	private void windowModal(Bestellung bestellung) {
		win = windowUI(win, "Bestellpositionen", "95%", "95%");		
		m_bestellpositionVorschau = new BestellpositionenVorschau(bestellung);
		addComponent(m_bestellpositionVorschau);
		win.setContent(m_bestellpositionVorschau);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		
	}
	

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) {		
		if(((ViewDataObject<?>) data).getData() instanceof Bestellung) {
			m_bestellung = (Bestellung) ((ViewDataObject<?>) data).getData();
			
			m_filterTable.setFilterFieldValue(FIELD_LIEFERANT, m_bestellung.getLieferant().getName());
			m_filterTable.setFilterFieldValue(FIELD_MITARBEITER, m_bestellung.getMitarbeiter().getVorname());
			m_filterTable.setFilterFieldValue(FIELD_KATEGORIE, m_bestellung.getKategorie());
			
		} 
	}
}

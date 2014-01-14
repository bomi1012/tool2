package de.palaver.view.bestellverwaltung.popup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellpositionService;
import de.palaver.view.bestellverwaltung.OverBestellverwaltungView;

@SuppressWarnings("serial")
public class BestellpositionenVorschau extends OverBestellverwaltungView implements View,
ValueChangeListener{
	private static final Logger LOG = LoggerFactory.getLogger(BestellpositionenVorschau.class.getName());
	private BeanItemContainer<Bestellposition> m_container;

	public BestellpositionenVorschau() {
		
	}
	
	public BestellpositionenVorschau(Bestellung bestellung) {
		m_bestellung = bestellung;
		layout();
		listeners();
		beans();
	}

	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, "Alle Bestellpositionen für die Bestellung von " 
				+ m_bestellung.getDatum(), STYLE_HEADLINE);
		m_control = controlBestellpositionenVorschau();
		m_filterTable = filterTable();
		
		this.setSpacing(true);				
		this.addComponent(m_filterTable);
		this.setExpandRatio(m_filterTable, 1);
		this.addComponent(m_control);
		this.setComponentAlignment(m_control, Alignment.BOTTOM_RIGHT);
	}
	
	private void listeners() {
		//close
		m_closeButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				Window win = (Window) BestellpositionenVorschau.this.getParent();
				win.close();				
			}
		});
		
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_editButton.setEnabled(true);
					m_bestellposition = (Bestellposition) event.getProperty().getValue();
				}
			}
		});

		m_editButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_bestellposition != null) {
					windowModal(m_bestellposition);
				}
			}
		});	
	}

	private void windowModal(Bestellposition bestellposition) {
		win = windowUI(win, "Bestellpositionen bearbeiten", "500", "250");		
		m_bestellpositionBearbeiten = new BestellpositionBearbeiten(bestellposition);
		addComponent(m_bestellpositionBearbeiten);
		win.setContent(m_bestellpositionBearbeiten);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		
	}
	private void beans() {
		try {
			m_container = new BeanItemContainer<Bestellposition>(Bestellposition.class, 
					BestellpositionService.getInstance().getBestellpositionenByBestellungId(m_bestellung.getId()));
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}	
		
	}
	
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "artikel", "liefermenge1", 
				"liefermenge2", FIELD_STATUS});			
		m_filterTable.sort(new Object[] { FIELD_LIEFERANT }, new boolean[] { true });			
		m_filterTable.setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId) {
				Bestellposition bestellposition = (Bestellposition) itemId;
				if (FIELD_STATUS.equals(propertyId)) {
					return bestellposition.isStatus() ? "check" : "cross";
				}
				return "";				
			}
		});
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(ViewData data) { }
}

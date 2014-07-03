package de.palaver.view.bean.artikelverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;

@SuppressWarnings("serial")
public class ShowItemsBean extends TemplateBuilder implements View {
	private static final Logger LOG = LoggerFactory.getLogger(ShowItemsBean.class.getName());	
	private static final long serialVersionUID = -2340836709411564164L;
	private static final String TITLE = "Alle Artikeln";
	private BeanItemContainer<Artikel> m_container;

	private Artikel m_artikel;
	
	public ShowItemsBean() {
		super();
		m_container = null;
		componetsManager();
		templateBuilder();
		listeners();
		beans();
	}
	
	private void componetsManager() {		
		m_filterControlPanel = filterHorisontalLayoutWithHeadTitle(TITLE, STYLE_HEADLINE_STANDART, WIDTH_FULL);
		m_filterTable = HTMLComponents.filterTable(true, true);
		m_control = controlPanel(this);		
	}

	private void templateBuilder() {
		defaultShowPageFilterTable(m_filterControlPanel, m_filterTable, m_control);
	}

	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_buttonEdit.setEnabled(true);
					m_artikel = (Artikel) event.getProperty().getValue();
				}
			}
		});

		m_filterTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_buttonEdit.click();
				}
			}
		});
		
		m_buttonEdit.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_artikel != null) {
					ViewHandler.getInstance().switchView(ChangeItemBean.class, new ViewDataObject<Artikel>(m_artikel));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_ARTIKEL_AUSWAEHLEN);
				}
			}
		});
		
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeItemBean.class);
			}
		});	
		
		m_buttonFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_filterTable.resetFilters();
			}
		});
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Artikel>(Artikel.class, ArtikelService.getInstance().getActiveArtikeln());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
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
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

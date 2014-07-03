package de.palaver.view.bean.lieferantenverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.service.SupplierService;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;

@SuppressWarnings("serial")
public class ShowSupplierBean extends TemplateBuilder implements View {
	private static final Logger LOG = LoggerFactory.getLogger(ShowSupplierBean.class.getName());	
	private static final long serialVersionUID = -2340836709414164L;
	private static final String TITLE = "Lieferanten Artikeln";
	private BeanItemContainer<Supplier> m_container;

	private Supplier m_supplier;
	
	public ShowSupplierBean() {
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
					m_supplier = (Supplier) event.getProperty().getValue();
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
				if (m_supplier != null) {
					ViewHandler.getInstance().switchView(ChangeSupplierBean.class, new ViewDataObject<Supplier>(m_supplier));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_LIEFERANT_AUSWAEHLEN);
				}
			}
		});
		
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeSupplierBean.class);
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
			m_container = new BeanItemContainer<Supplier>(Supplier.class, SupplierService.getInstance().getAllLieferanten());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "name", "lieferantnummer", "adresse", "kontakte", "bezeichnung", "notiz" });
		m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });	
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub		
	}
}

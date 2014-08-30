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
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.service.LagerortService;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.helpers.interfaces.IShowSingleTable;

@SuppressWarnings("serial")
public class ShowWarehousesBean extends TemplateBuilder implements View, IShowSingleTable {
	private static final Logger LOG = LoggerFactory.getLogger(ShowWarehousesBean.class.getName());	
	private static final long serialVersionUID = -23408367394167164L;
	private static final String TITLE = "Alle Lagerorte";
	private BeanItemContainer<Lagerort> m_container;

	private Lagerort m_warehouse;
	
	public ShowWarehousesBean() {
		super();
		m_container = null;
		componetsManager();
		templateBuilder();
		listeners();
		beans();
	}
	
	private void componetsManager() {		
		m_headLine = title(TITLE, STYLE_HEADLINE_STANDART);
		m_table = HTMLComponents.table(true, true, WIDTH_FULL, WIDTH_FULL);
		m_control = controlPanel(this);		
	}

	private void templateBuilder() {
		defaultShowPageTable(m_headLine, m_table, m_control, "60%");
	}

	private void listeners() {
		m_table.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_warehouse = (Lagerort) event.getProperty().getValue();
					m_buttonEdit.setEnabled(true);
				}
			}
		});
		
		m_table.addItemClickListener(new ItemClickListener() {
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
				if (m_warehouse != null) {
					ViewHandler.getInstance().switchView(ChangeWarehouseBean.class, new ViewDataObject<Lagerort>(m_warehouse));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_LAGERORT_AUSWAEHLEN);
				}
			}
		});
			
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeWarehouseBean.class);
			}
		});		
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Lagerort>(Lagerort.class, LagerortService.getInstance().getAllLagerorts());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { FIELD_NAME });
		m_table.sort(new Object[] { FIELD_NAME }, new boolean[] { true });
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

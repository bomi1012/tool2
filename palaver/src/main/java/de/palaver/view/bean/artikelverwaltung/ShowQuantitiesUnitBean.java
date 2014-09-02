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
import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.artikel.service.MengeneinheitService;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IShowSingleTable;

public class ShowQuantitiesUnitBean extends TemplateBuilder implements View, IShowSingleTable {
	private static final Logger LOG = LoggerFactory.getLogger(ShowQuantitiesUnitBean.class.getName());
	private static final long serialVersionUID = -1227087597370434248L;
	private static final String TITLE = "Alle Mengeneinheiten anzeigen";

	private BeanItemContainer<Mengeneinheit> m_container;
	private Mengeneinheit m_mengeneinheit;
	
	
	public ShowQuantitiesUnitBean() {
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

	@SuppressWarnings("serial")
	private void listeners() {
		m_table.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_mengeneinheit = (Mengeneinheit) event.getProperty().getValue();
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
				if (m_mengeneinheit != null) {
					ViewHandler.getInstance().switchView(ChangeQuantityUnitBean.class, new ViewDataObject<Mengeneinheit>(m_mengeneinheit));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENGENEINHEIT_AUSWAEHLEN);
				}
			}
		});
			
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeQuantityUnitBean.class);
			}
		});			
	}

	private void beans() {
		try {
			m_container = new BeanItemContainer<Mengeneinheit>(Mengeneinheit.class,
					MengeneinheitService.getInstance().getAllMengeneinheiten());
			setTable();			
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}
	
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { FIELD_NAME, FIELD_KURZ });
		m_table.sort(new Object[] { FIELD_NAME }, new boolean[] { true });
		m_table.setColumnWidth(FIELD_KURZ, 90);
		m_table.setColumnHeader(FIELD_KURZ, "abkürzung");
		m_table.setColumnAlignment(FIELD_KURZ, m_table.ALIGN_CENTER);
	}
		
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub		
	}
}

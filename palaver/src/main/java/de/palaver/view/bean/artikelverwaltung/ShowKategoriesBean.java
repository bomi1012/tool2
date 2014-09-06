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
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.service.KategorieService;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IShowSingleTable;

@SuppressWarnings("serial")
public class ShowKategoriesBean extends TemplateBuilder implements View, IShowSingleTable {
	private static final Logger LOG = LoggerFactory.getLogger(ShowKategoriesBean.class.getName());	
	private static final long serialVersionUID = -2340836739411567164L;
	private static final String TITLE = "Alle Kategorien";
	private BeanItemContainer<Kategorie> m_container;

	private Kategorie m_kategorie;
	
	public ShowKategoriesBean() {
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
					m_kategorie = (Kategorie) event.getProperty().getValue();
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
				if (m_kategorie != null) {
					ViewHandler.getInstance().switchView(ChangeKategoryBean.class, new ViewDataObject<Kategorie>(m_kategorie));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_KATEGORIE_AUSWAEHLEN);
				}
			}
		});
			
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeKategoryBean.class);
			}
		});		
	}
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Kategorie>(Kategorie.class, KategorieService.getInstance().getAllKategories());
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
	public void getViewParam(IViewData data) {
		// TODO Auto-generated method stub
		
	}
}

package de.palaver.view.bean.menuverwaltung;

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
import de.palaver.management.menu.Menu;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.interfaces.IShowSingleTable;
import de.palaver.view.bean.util.wrappers.MenuWrapper;

public class ShowMenusBean  extends TemplateBuilder implements View, IShowSingleTable {
	private static final long serialVersionUID = 6460191672926321401L;

	private static final String TITLE = "Alle Menüs anzeigen";
	
	private Menu m_menu;
	private BeanItemContainer<MenuWrapper> m_container;
	
	public ShowMenusBean() {
		super();
		m_container = null;
		componetsManager();
		templateBuilder();
		listeners();
		beans();
	}

	private void componetsManager() {
		m_filterControlPanel = filterHorisontalLayoutWithHeadTitle(TITLE, STYLE_HEADLINE_STANDART, WIDTH_FULL);
		m_filterTable = HTMLComponents.filterTable(true, true, WIDTH_FULL, WIDTH_FULL);
		m_control = controlPanel(this);			
	}

	private void templateBuilder() {
		defaultShowPageFilterTable(m_filterControlPanel, m_filterTable, m_control);
	}

	@SuppressWarnings("serial")
	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_buttonEdit.setEnabled(true);
					m_menu = ((MenuWrapper) event.getProperty().getValue()).getMenu();
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
				if (m_menu != null) {
					ViewHandler.getInstance().switchView(ChangeMenuBean.class, new ViewDataObject<Menu>(m_menu));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENU_SELECT);
				}
			}
		});
		
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeMenuBean.class);
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
			m_container = new BeanItemContainer<MenuWrapper>(MenuWrapper.class, MenuWrapper.getMenuWrappers());
			setTable();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "name",  "menutype", "geschmack", "employee"});
		m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });
		
		m_filterTable.setColumnHeader("employee", "Mitarbeiter");
		m_filterTable.setColumnHeader("menutype", "Menuart");
		
		m_filterTable.setFilterFieldValue("employee", ((Application) UI.getCurrent().getData()).getUser().getBenutzername());
	}

	@Override
	public void getViewParam(IViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Menu) {
			m_menu = (Menu)((ViewDataObject<?>) data).getData(); 
			m_filterTable.resetFilters();
			m_filterTable.setFilterFieldValue("name", ((Menu)((ViewDataObject<?>) data).getData()).getName());
		}
	}
}

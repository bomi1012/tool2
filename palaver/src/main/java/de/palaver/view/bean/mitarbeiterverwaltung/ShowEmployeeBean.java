package de.palaver.view.bean.mitarbeiterverwaltung;

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
import de.palaver.management.emploee.Employee;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.helpers.wrappers.EmployeeWrapper;

public class ShowEmployeeBean extends TemplateBuilder implements View {
	private static final Logger LOG = LoggerFactory.getLogger(ShowEmployeeBean.class.getName());	
	private static final long serialVersionUID = -40836709414164L;
	private static final String TITLE = "Alle Mitarbeiter";
	protected Employee m_employee;
	private BeanItemContainer<EmployeeWrapper> m_container;
	public ShowEmployeeBean() {
		super();
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
	
	@SuppressWarnings("serial")
	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					if (m_buttonEdit.isVisible()) {
						m_buttonEdit.setEnabled(true); 
					}
					m_employee = ((EmployeeWrapper) event.getProperty().getValue()).getEmployee();
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
				if (m_employee != null) {
					ViewHandler.getInstance().switchView(ChangeEmployeeBean.class, new ViewDataObject<Employee>(m_employee));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MITARBEITER_AUSWAEHLEN);
				}
			}
		});
		
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeEmployeeBean.class);
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
			m_container = new BeanItemContainer<EmployeeWrapper>(EmployeeWrapper.class, EmployeeWrapper.getEmployeeWrappers());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}

	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "username",  "fullname", "email", "rolles"});
		m_filterTable.sort(new Object[] { "username" }, new boolean[] { true });
		
		m_filterTable.setColumnHeader("username", "Benutzername");
		m_filterTable.setColumnHeader("fullname", "Vor- und Nachname");
		m_filterTable.setColumnHeader("rolles", "Rolle");
	}


	@Override
	public void getViewParam(ViewData data) {
		
	}
}

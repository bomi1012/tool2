package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import org.tepi.filtertable.FilterTable;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class OverAnzeigen extends ArtikelverwaltungView implements View  {

	protected Button m_createButton;
	protected Button m_editButton;
	protected Button m_filterButton;
	protected HorizontalLayout m_control;

	
	public OverAnzeigen() {
		super();
	}
	
	/**
	 * create + edit
	 */
	protected HorizontalLayout controlPanelEditAndNew() {
		m_createButton = buttonSetting(m_button, IConstants.BUTTON_NEW,
				IConstants.ICON_PAGE_ADD, true, true);
		m_editButton = buttonSetting(m_button, IConstants.BUTTON_EDIT,
				IConstants.ICON_PAGE_EDIT, true, false);

		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_createButton);
		m_horizontalLayout.addComponent(m_editButton);
		return m_horizontalLayout;
	}
	
	protected Table table() {
		m_table = new Table();
		m_table.setSizeFull();
		m_table.setSelectable(true);
		return m_table;
	}
	protected FilterTable filterTable() {
		m_filterTable = new FilterTable();
		m_filterTable.setSizeFull();
		m_filterTable.setFilterBarVisible(true);
		m_filterTable.setFilterGenerator(new customFilter());
		m_filterTable.setFilterDecorator(new customFilterDecorator());
		m_filterTable.setSelectable(true);
		return m_filterTable;
	}
	

	protected HorizontalLayout filterLayout(Label headlineLabel) {			
		m_filterButton = new Button(IConstants.BUTTON_CLEAR_FILTER);
		m_filterButton.setIcon(new ThemeResource("img/disable_filter.ico"));
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setWidth("100%");
		m_horizontalLayout.addComponent(headlineLabel);
		m_horizontalLayout.setComponentAlignment(headlineLabel, Alignment.MIDDLE_LEFT);
		m_horizontalLayout.addComponent(m_filterButton);
		m_horizontalLayout.setComponentAlignment(m_filterButton, Alignment.MIDDLE_RIGHT);
		m_horizontalLayout.setExpandRatio(headlineLabel, 1);
		
		return m_horizontalLayout;
	}
	
	protected VerticalLayout addToLayoutTableAndControl(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);
		box.setMargin(true);

		box.addComponent(m_headlineLabel);
		box.setComponentAlignment(m_headlineLabel, Alignment.MIDDLE_LEFT);

		box.addComponent(m_table);
		box.setComponentAlignment(m_table, Alignment.MIDDLE_CENTER);
		box.addComponent(m_control);
		box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		return box;
	}
	
	@Override
	public void getViewParam(ViewData data) { }

}

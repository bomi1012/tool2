package de.palaver.view.bean.helpers;

import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterTable;
import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.palaver.Application;
import de.palaver.management.emploee.Rolle;
import de.palaver.view.bean.artikelverwaltung.ChangeItemBean;
import de.palaver.view.bean.artikelverwaltung.ChangeKategoryBean;
import de.palaver.view.bean.artikelverwaltung.ChangeQuantityUnitBean;
import de.palaver.view.bean.artikelverwaltung.ChangeWarehouseBean;
import de.palaver.view.bean.artikelverwaltung.ShowItemsBean;
import de.palaver.view.bean.artikelverwaltung.ShowKategoriesBean;
import de.palaver.view.bean.artikelverwaltung.ShowQuantitiesUnitBean;
import de.palaver.view.bean.artikelverwaltung.ShowWarehousesBean;
import de.palaver.view.bean.lieferantenverwaltung.ChangeContactPersonBean;
import de.palaver.view.bean.lieferantenverwaltung.ChangeSupplierBean;
import de.palaver.view.bean.lieferantenverwaltung.ShowSupplierBean;
import de.palaver.view.bean.mitarbeiterverwaltung.ChangeEmployeeBean;
import de.palaver.view.bean.mitarbeiterverwaltung.ChangePasswordBean;
import de.palaver.view.bean.mitarbeiterverwaltung.ShowEmployeeBean;

public class TemplateBuilder extends AbstractView {
	
	private static final long serialVersionUID = 1367259725869115554L;
	
	private static final String 		BUTTON_CLEAR_FILTER =  "Filter leeren";	
	protected static final String 		BUTTON_TEXT_CREATE = "Anlegen";
	protected static final String 		BUTTON_TEXT_SAVE = "Speichern";
	protected static final String 		BUTTON_TEXT_VERWERFEN = "Verwerfen";
	protected static final String 		BUTTON_TEXT_DEAKTIVIEREN = "Deaktivieren";
	private static final String 		BUTTON_TEXT_EDIT = "Bearbeiten";

	private static final String			BUTTON_ICON_EDIT = "icons/page_edit.png";
	protected static final String		BUTTON_ICON_CREATE = "icons/page_add.png";
	protected static final String		BUTTON_ICON_SAVE = "icons/page_save.png";
	protected static final String		BUTTON_ICON_VERWERFEN = "icons/page_refresh.png";
	protected static final String		BUTTON_ICON_DEAKTIVIEREN = "img/Delete.ico";
	private static final String 		BUTTON_FILTER_ICON_PATH = "img/disable_filter.ico";
	
	protected Button m_buttonEdit;		
	protected Button m_buttonCreate;
	protected Button m_buttonFilter;
	protected Button m_buttonSpeichern;
	protected Button m_buttonVerwerfen;
	protected Button m_buttonDeaktiviren;
	
	protected Label 				m_headLine;
	protected Window 				m_window;
	protected HorizontalLayout 		m_filterControlPanel;
	protected FilterTable 			m_filterTable; 
	protected HorizontalLayout 		m_control;
	protected Table 				m_table;
	
	public TemplateBuilder() {
		super();
	}
	
	protected HorizontalLayout filterHorisontalLayoutWithHeadTitle(String title, String style, String width) {
		Label headLineLable = title(title, style);	
		m_buttonFilter = HTMLComponents.filterButton(BUTTON_CLEAR_FILTER, BUTTON_FILTER_ICON_PATH);
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth(width);
		horizontalLayout.addComponent(headLineLable);
		horizontalLayout.setComponentAlignment(headLineLable, Alignment.MIDDLE_LEFT);
		horizontalLayout.addComponent(m_buttonFilter);
		horizontalLayout.setComponentAlignment(m_buttonFilter, Alignment.MIDDLE_RIGHT);
		horizontalLayout.setExpandRatio(headLineLable, 1);
		
		return horizontalLayout;
	}
	
	protected HorizontalLayout selectAndAddButtonPanel(NativeSelect select, Button button, String width) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth(width);
		horizontalLayout.addComponent(select);
		horizontalLayout.addComponent(button);
		horizontalLayout.setExpandRatio(select, 1);
		horizontalLayout.setComponentAlignment(button, Alignment.BOTTOM_RIGHT);
		return horizontalLayout;
	}
	
	protected HorizontalLayout controlPanel(Object object) {
		List<Button> buttons = new ArrayList<Button>();
		if(object instanceof ShowItemsBean || object instanceof ShowQuantitiesUnitBean ||
				object instanceof ShowKategoriesBean || object instanceof ShowWarehousesBean ||
				object instanceof ShowSupplierBean) { 
			m_buttonCreate = button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);
			m_buttonEdit = button(BUTTON_TEXT_EDIT, BUTTON_ICON_EDIT, true, false);
			
			buttons.add(m_buttonCreate);
			buttons.add(m_buttonEdit);
		} else if (object instanceof ChangeItemBean || object instanceof ChangeQuantityUnitBean ||
				object instanceof ChangeKategoryBean || object instanceof ChangeWarehouseBean ||
				object instanceof ChangeSupplierBean || object instanceof ChangeContactPersonBean ||
				object instanceof ChangeEmployeeBean) {
			m_buttonSpeichern = button(BUTTON_TEXT_SAVE, BUTTON_ICON_SAVE, true, true);
			m_buttonVerwerfen = button(BUTTON_TEXT_VERWERFEN, BUTTON_ICON_VERWERFEN, true, true);
			m_buttonDeaktiviren = button(BUTTON_TEXT_DEAKTIVIEREN,BUTTON_ICON_DEAKTIVIEREN, false, true);
			
			buttons.add(m_buttonVerwerfen);
			buttons.add(m_buttonSpeichern);
			buttons.add(m_buttonDeaktiviren);
		} else if (object instanceof ShowEmployeeBean) {
			if (((Application) UI.getCurrent().getData()).userHasPersmission(Rolle.ADMINISTRATOR)) {		
				m_buttonCreate = button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);
				m_buttonEdit = button(BUTTON_TEXT_EDIT, BUTTON_ICON_EDIT, true, false);
			} else {
				m_buttonCreate = button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, false, false);
				m_buttonEdit = button(BUTTON_TEXT_EDIT, BUTTON_ICON_EDIT, false, false);
			}
			buttons.add(m_buttonCreate);
			buttons.add(m_buttonEdit);
		} else if (object instanceof ChangePasswordBean) {
			m_buttonSpeichern = button(BUTTON_TEXT_SAVE, BUTTON_ICON_SAVE, true, true);
			m_buttonVerwerfen = button(BUTTON_TEXT_VERWERFEN, BUTTON_ICON_VERWERFEN, true, true);
			buttons.add(m_buttonSpeichern);
			buttons.add(m_buttonVerwerfen);
		}

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		for (Button button : buttons) {
			horizontalLayout.addComponent(button);
		}
		return horizontalLayout;
	}
	
	protected HorizontalLayout twoStepperPanel(IntStepper intStepper1, IntStepper intStepper2) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth(WIDTH_FULL);
		horizontalLayout.addComponent(intStepper1);
		horizontalLayout.addComponent(intStepper2);
		horizontalLayout.setExpandRatio(intStepper1, 1);
		horizontalLayout.setExpandRatio(intStepper2, 1);
		horizontalLayout.setComponentAlignment(intStepper2, Alignment.BOTTOM_RIGHT);
		
		return horizontalLayout;
	}
	
	protected void defaultShowPageTable(Label headLineLabel, Table table, HorizontalLayout control, String width) {
		setMargin(true);
		VerticalLayout innerBox = new VerticalLayout();	
		
		innerBox.setWidth("65%");
		innerBox.setSpacing(true);
		
		innerBox.addComponent(headLineLabel);
		innerBox.setComponentAlignment(headLineLabel, Alignment.MIDDLE_LEFT);

		innerBox.addComponent(table);
		innerBox.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		innerBox.addComponent(control);
		innerBox.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		addComponent(innerBox);
		setComponentAlignment(innerBox, Alignment.MIDDLE_CENTER);
	}
	
	protected void defaultShowPageFilterTable(HorizontalLayout filterControlPanel, FilterTable filterTable, HorizontalLayout control) {
		setSizeFull();
		setMargin(true);
		setSpacing(true);	
		
		addComponent(filterControlPanel);		
		addComponent(filterTable);
		addComponent(control);
		
		setExpandRatio(filterTable, 1);		
		setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
	}
	
	
	
	protected Button button(String text, String icon, boolean visible, boolean enable) {
		return HTMLComponents.buttonConfiguration(text, icon, visible, enable);
	}
	
	protected Button buttonAsIcon(String text, String baseTheme, String StyleName, String icon, boolean isVisible) {
		return HTMLComponents.buttonAsIconConfiguration(text, baseTheme, StyleName, icon, isVisible);
	}
	
	protected Label title(String title, String style) {
		return HTMLComponents.headLine(title, style);	
	}
	
	protected TextField textField(String name, String width, boolean required, String descript, Object object) {
		return HTMLComponents.textFieldConfiguration(name, width, required, descript, object);
	}
	
	protected PasswordField passwordField(String title, String width, String errorMessage, boolean required, 
			int minLength, int maxLength, boolean isVisible, boolean isEnabled) {
		return HTMLComponents.passwordField(title, width, errorMessage, required, minLength, maxLength,
				isVisible, isEnabled);		
	}
	
	protected TextArea textArea(String name, String width, String height, boolean required, String descript, Object object) {
		return HTMLComponents.textAREAConfiguration(name, width, height, required, descript, object);
	}
	
	protected FilterTable filterTable(boolean filterBarVisible, boolean selectable) {
		return HTMLComponents.filterTable(filterBarVisible, selectable);
	}
	
	protected NativeSelect nativeSelect(String name, String width, boolean required, String description, Object object) {
		return HTMLComponents.nativeSelectConfiguration(name, width, required, description, object);
	}
	
	protected IntStepper intStepper(String name, String width, int min, int max, String desc)  {
		return HTMLComponents.intStepperConfiguration(name, width, min, max, desc);
	}
	
	protected TwinColSelect twinColSelect(String title, String source, String target, 
			int rows, int standard , boolean visible, boolean enabled) {
		return HTMLComponents.twinColSelect(title, source, target, rows, standard, visible, enabled);
	}
}

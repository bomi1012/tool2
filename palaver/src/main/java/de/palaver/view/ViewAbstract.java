package de.palaver.view;

import org.tepi.filtertable.FilterTable;
import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class ViewAbstract extends VerticalLayout {
	
	public static final String FIELD_KURZ = "kurz";
	public static final String FIELD_ARTIKEL_NR = "artikelnr";
	public static final String FIELD_ARTIKEL_NAME = "artikelName";
	public static final String FIELD_LIEFERANT = "lieferant";
	public static final String FIELD_KATEGORIE = "kategorie";
	public static final String FIELD_LAGERORT = "lagerort";
	public static final String FIELD_MITARBEITER = "mitarbeiter";
	public static final String FIELD_MENGENEINHEIT = "mengeneinheit";
	public static final String FIELD_PREIS = "preis";
	public static final String FIELD_STANDARD = "standard";
	public static final String FIELD_GRUNDBEDARF = "grundbedarf";
	public static final String FIELD_BESTELLGROESSE = "bestellgroesse";
	public static final String FIELD_LIEFERDATUM_1 = "lieferdatum1";
	public static final String FIELD_LIEFERDATUM_2 = "lieferdatum2";
	public static final String FIELD_DATUM = "datum";
	public static final String FIELD_NOTIZ = "notiz";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_SUMME_1 = "summe1";
	public static final String FIELD_SUMME_2 = "summe2";
	public static final String FIELD_IGNORE = "ignore";
	
	protected Button m_createButton;
	protected Button m_editButton;
	
	protected static final String FULL = "100%";
	protected static final String STYLE_HEADLINE = "ViewHeadline";
	protected static final String FIELD_NAME = "name";
	protected Label m_headlineLabel;
	protected VerticalLayout m_vertikalLayout;
	protected HorizontalLayout m_horizontalLayout;
	protected Button m_button;
	protected Window m_window;
	protected Table m_table;
	protected FilterTable m_filterTable; 
	protected TextField m_textField; 
	protected NativeSelect m_nativeSelect;
	protected IntStepper m_intStepper;
	protected DateField m_date;
	
	public Button m_speichernButton;
	public Button m_verwerfenButton;
	public Button m_deaktivierenButton;

	public boolean m_create;
	public boolean m_okRemove = false;
	public Button m_yesButton;
	public Button m_noButton;
	
	
	protected static final String MESSAGE_LEER_ARG_1 = "Tragen Sie bitte im Feld %s den Wert ein";
	protected static final String MESSAGE_EXISTS_ARG_1 = "Der Name %s ist bereits im System vorhanden";	
	protected static final String MESSAGE_SUSSEFULL_ARG_1 = "%s wurde erfolgreich gespeichert";
	
	protected TextField textFieldSetting(TextField field, String name,
			String width, boolean required, String descript) {		
		field = new TextField(name);
		field.setWidth(width);
		if(required) {
			field.addValidator(new StringLengthValidator(IConstants.INFO_NAME_VALID, 1, 100, true));
		}
		field.setImmediate(true);
		field.setDescription(descript);
		return field;
	}
	
	public ViewAbstract() {
		super();
	}
	
	public ViewAbstract(Component... children) {
		super(children);
	}
	
	protected Button filterButton(boolean v, boolean e) {
		return buttonSetting(m_button, IConstants.BUTTON_CLEAR_FILTER,
				"icons/textfield_delete.png", v, e);
	}
	protected Button editButton(boolean v, boolean e) {
		return buttonSetting(m_button, IConstants.BUTTON_EDIT,
				IConstants.ICON_PAGE_EDIT, v, e);
	}
	protected Button deleteButton(boolean v, boolean e) {
		return buttonSetting(m_button, IConstants.BUTTON_DELETE,
				IConstants.ICON_PAGE_DELETE, v, e);
	}
	
	protected Button buttonSetting(Button button, String title, String icon, boolean isVisible, boolean isEnable) {
		button = new Button(title);
		if(icon != null) 
			button.setIcon(new ThemeResource(icon));
		button.setVisible(isVisible);
		button.setEnabled(isEnable);
		return button;
	}
	
	protected HorizontalLayout newKomponent(HorizontalLayout hl,
			NativeSelect select, Button button, String width) {
		hl = new HorizontalLayout();
		hl.setWidth(width);
		hl.addComponent(select);
		hl.addComponent(button);
		hl.setExpandRatio(select, 1);
		hl.setComponentAlignment(button, Alignment.BOTTOM_RIGHT);
		return hl;
	}
	protected Window windowUI(Window win, String title, String w, String h) {
		win = new Window(title);
		win.setModal(true);
		win.setResizable(false);
		win.setWidth(w);
		win.setHeight(h);
		
		return win;
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

	protected Label headLine(Label lable, String title, String style) {		
		lable = new Label(title);
		lable.setStyleName(style);
		return lable;
	}

	public class Hr extends Label {
		@SuppressWarnings("deprecation")
		public Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}
	
	protected HorizontalLayout controlErstellenPanel() {	
		m_deaktivierenButton = buttonSetting(m_button, IConstants.BUTTON_DELETE,
				IConstants.ICON_PAGE_DELETE, false, true);
		m_verwerfenButton = buttonSetting(m_button, IConstants.BUTTON_DISCARD,
				IConstants.ICON_PAGE_REFRESH, true, true);
		m_speichernButton = buttonSetting(m_button, IConstants.BUTTON_SAVE,
				IConstants.ICON_PAGE_SAVE, true, true);
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_verwerfenButton);
		m_horizontalLayout.addComponent(m_deaktivierenButton);
		m_horizontalLayout.addComponent(m_speichernButton);
		return m_horizontalLayout;
	}
	
	
	protected HorizontalLayout controlYesNo() {	
		m_yesButton = buttonSetting(m_button, "ja",
				"icons/accept.png", true, true);
		m_noButton = buttonSetting(m_button, "nein",
				"icons/cancel.png", true, true);
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_yesButton);
		m_horizontalLayout.addComponent(m_noButton);
		return m_horizontalLayout;
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

}

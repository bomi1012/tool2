package de.hska.awp.palaver2.gui.view;

import org.tepi.filtertable.FilterTable;
import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.IConstants;

@SuppressWarnings("serial")
public class ViewAbstract extends VerticalLayout {
	protected static final String FULL = "100%";
	protected static final String STYLE_HEADLINE = "ViewHeadline";
	protected static final String FIELD_NAME = "name";
	protected Label m_headlineLabel;
	protected VerticalLayout vertikalLayout;
	protected HorizontalLayout m_horizontalLayout;
	protected Button m_button;
	protected Window win;
	protected Table m_table;
	protected FilterTable m_filterTable; 
	protected TextField m_textField; 
	protected NativeSelect m_nativeSelect;
	protected IntStepper m_intStepper;

	public boolean m_create;
	public boolean m_okRemove = false;
	
	
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

}

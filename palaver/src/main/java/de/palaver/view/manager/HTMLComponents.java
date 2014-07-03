package de.palaver.view.manager;

import org.tepi.filtertable.FilterTable;
import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.view.artikelverwaltung.ChangeItemBean;

public class HTMLComponents {
	
	protected static Label headLine(String title, String style) {		
		Label lable = new Label(title);
		lable.setStyleName(style);
		return lable;
	}
	
	protected static Button filterButton(String text, String icon) {
		Button filterButton = new Button(text);
		filterButton.setIcon(new ThemeResource(icon));
		return filterButton;
	}
	
	public static FilterTable filterTable(boolean filterBarVisible, boolean selectable) {
		FilterTable filterTable = new FilterTable();
		filterTable.setSizeFull();
		filterTable.setFilterBarVisible(filterBarVisible);
		filterTable.setFilterGenerator(new customFilter());
		filterTable.setFilterDecorator(new customFilterDecorator());
		filterTable.setSelectable(selectable);
		return filterTable;
	}	
	
	public static Table table(boolean selectable, boolean immediate, String width, String height) {
		Table table = new Table();
		if ((width != null && width.equals("100%")) && (height != null && height.equals("100%"))) {
			table.setSizeFull();
		} else {
			if (height != null) {
				table.setHeight(height);
			}
			if (width != null) {
				table.setWidth(width);
			}
		}
		table.setSelectable(selectable);
		table.setImmediate(immediate);
		return table;
	}
	
	protected static Button buttonConfiguration(String title, String icon, boolean isVisible, boolean isEnable) {
		Button button = new Button(title);
		if(icon != null) {
			button.setIcon(new ThemeResource(icon));
		}
		button.setVisible(isVisible);
		button.setEnabled(isEnable);
		return button;
	}
	
	protected static TextField textFieldConfiguration(String name, String width, boolean required, String descript, Object object) {
		TextField textField = new TextField(name);
		textField.setWidth(width);
		if(required) {
			textField.addValidator(new StringLengthValidator(IConstants.INFO_NAME_VALID, 1, 100, true));
		}
		textField.setImmediate(true);
		textField.setDescription(descript);
		
		if (object instanceof ChangeItemBean) {
			textField.addValueChangeListener((ChangeItemBean) object); 
		}
		return textField;
	}
	
	protected static TextArea textAREAConfiguration(String name, String width, String height, boolean required, String descript, Object object) {
		TextArea textArea = new TextArea(name);
		textArea.setWidth(width);
		textArea.setHeight(height);
		if(required) {
			textArea.addValidator(new StringLengthValidator(IConstants.INFO_NAME_VALID, 1, 100, true));
		}
		textArea.setImmediate(true);
		textArea.setDescription(descript);
		
		if (object instanceof ChangeItemBean) {
			textArea.addValueChangeListener((ChangeItemBean) object); 
		}
		return textArea;
	}
	
	@SuppressWarnings("serial")
	protected static NativeSelect nativeSelectConfiguration(String name, String width, boolean required, String description,
			Object object) {
		NativeSelect nativeSelect = new NativeSelect(name);
		nativeSelect.setWidth(width);
		if(required) {
			nativeSelect.addValidator(new Validator() {
				@Override
				public void validate(Object value) throws InvalidValueException {
					if (value == null) {
						throw new InvalidValueException("Wählen Sie bitte den Name aus der Auswahl");
					}
				}
			});
		}
		nativeSelect.setImmediate(true);
		nativeSelect.setDescription(description);
		if(object instanceof ChangeItemBean) {
			nativeSelect.addValueChangeListener((ChangeItemBean) object);
		}	
		return nativeSelect;
	}
	
	protected static IntStepper intStepperConfiguration(String name, String width, int min, int max, String desc) {
		IntStepper intStepper = new IntStepper(name);
		intStepper.setMaxValue(max);
		intStepper.setMinValue(min);
		intStepper.setStyleName("stepper-palaver");
		intStepper.setWidth(width);
		intStepper.setDescription(desc);
		intStepper.setVisible(false);
		return intStepper;
	}

	protected static Button buttonAsIconConfiguration(String text,
			String baseTheme, String styleName, String icon) {
		Button button = new Button(text);
		button.setPrimaryStyleName(baseTheme);
		button.setStyleName(styleName);
		button.setIcon(new ThemeResource(icon));
		return button;
	}
}

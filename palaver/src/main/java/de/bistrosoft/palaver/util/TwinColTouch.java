package de.bistrosoft.palaver.util;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TwinColTouch extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723143101691071320L;

	// Komponenten
	private VerticalLayout vlMain;

	// Komponenten für NonTouch
	private TwinColSelect tcs;

	// Komponenten für Touch
	private ListSelect ls;

	private Boolean isTouch;

	public TwinColTouch() {
		layout();
	}

	public TwinColTouch(String caption) {
		layout();

		if (isTouch) {
			ls.setCaption(caption);
		} else {
			tcs.setCaption(caption);
		}
	}

	@SuppressWarnings("deprecation")
	private void layout() {
		vlMain = new VerticalLayout();

		setCompositionRoot(vlMain);
		vlMain.setSizeFull();

		isTouch = UI.getCurrent().getSession().getBrowser().isTouchDevice();

		if (isTouch == null) {
			isTouch = false;
		}

		if (isTouch) {
			layoutTouch();
		} else {
			layoutNonTouch();
		}
	}

	private void layoutNonTouch() {
		tcs = new TwinColSelect();
		tcs.setImmediate(true);
		vlMain.addComponent(tcs);
	}

	private void layoutTouch() {
		ls = new ListSelect();
		ls.setMultiSelect(true);
		ls.setImmediate(true);
		vlMain.addComponent(ls);
	}
	
	@Override
	public void setWidth(String width) {
		if (isTouch) {
			ls.setWidth(width);
		} else {
			tcs.setWidth(width);
		}
	}
	
	public void addValidator(Validator validator) {
		if (isTouch) {
			ls.addValidator(validator);
		} else {
			tcs.addValidator(validator);
		}
	}
	
	public void addValueChangeListener(ValueChangeListener valuechangelistener) {
		if (isTouch) {
			ls.addValueChangeListener(valuechangelistener);
		} else {
			tcs.addValueChangeListener(valuechangelistener);
		}
	}

	public void setImmediate(Boolean immediate) {
		if (isTouch) {
			ls.setImmediate(immediate);
		} else {
			tcs.setImmediate(immediate);
		}
	}
	
	public void removeAllItems() {
		if (isTouch) {
			ls.removeAllItems();
		} else {
			tcs.removeAllItems();
		}

	}

	public void addItem(Object item) {
		if (isTouch) {
			ls.addItem(item);
		} else {
			tcs.addItem(item);
		}
	}

	public void select(Object item) {
		if (isTouch) {
			ls.select(item);
		} else {
			tcs.select(item);
		}

	}

	public Object getValue() {
		if (isTouch) {
			return ls.getValue();
		} else {
			return tcs.getValue();
		}

	}
}

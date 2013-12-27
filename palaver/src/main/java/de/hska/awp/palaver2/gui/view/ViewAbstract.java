package de.hska.awp.palaver2.gui.view;

import com.vaadin.data.Validator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.gui.view.artikelverwaltung.ArtikelErstellen;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.ArtikelverwaltungView;

public class ViewAbstract extends VerticalLayout {
	private static final long serialVersionUID = -7534579781394440179L;
	protected static final String FULL = "100%";
	
	protected static final String MESSAGE_LEER_ARG_1 = "Tragen Sie bitte im Feld %s den Wert ein";
	protected static final String MESSAGE_EXISTS_ARG_1 = "Der Name %s ist bereits im System vorhanden";	
	protected static final String MESSAGE_SUSSEFULL_ARG_1 = "%s wurde erfolgreich gespeichert";
	
	protected VerticalLayout vertikalLayout;
	protected HorizontalLayout m_horizontalLayout;
	protected Label m_headlineLabel;
	protected Button m_speichernButton;
	protected Button m_verwerfenButton;
	protected Button m_deaktivierenButton;
	protected Button filterButton;
	protected Button m_auswaehlenButton;
	
	
	protected Window win;
	protected Table m_table;

	
	protected ViewAbstract() {
		super();
	}
	
	protected ViewAbstract(Component... children) {
		super(children);
	}
	
	protected Button buttonSetting(Button button, String title, String icon, boolean isVisible) {
		button = new Button(title);
		button.setIcon(new ThemeResource(icon));
		button.setVisible(isVisible);
		return button;
	}
	@SuppressWarnings("unused")
	protected NativeSelect nativeSelectSetting(NativeSelect select,
			final String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
		Object value = select.getValue();
		select.setWidth(ArtikelverwaltungView.FULL);
		if(required) {
			select.addValidator(new Validator() {
				private static final long serialVersionUID = 1972800127752278750L;
				@Override
				public void validate(Object value) throws InvalidValueException {
					if (value == null) {
						throw new InvalidValueException("Wählen Sie bitte den Name aus der Auswahl");
					}
				}
			});
		}
		select.setImmediate(true);
		select.addValueChangeListener((ArtikelErstellen) object);
		select.setDescription(descript);
		return select;
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
		public Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}

}

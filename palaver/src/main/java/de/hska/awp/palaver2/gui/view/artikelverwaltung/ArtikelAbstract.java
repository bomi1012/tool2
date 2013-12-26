package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;

public class ArtikelAbstract extends VerticalLayout {
	private static final long serialVersionUID = 682496000359523955L;
	public static final String ARTIKEL_NAME = "Artikelname";
	public static final String ARTIKEL_PREIS = "Preis";
	public static final String ARTIKEL_NUMMER = "Artikelnummer";
	public static final String ARTIKEL_GEBINDE = "Gebinde";
	public static final String ARTIKEL_NOTIZ = "Notiz";
	public static final String ARTIKEL_STANDARD = "Standard";
	public static final String ARTIKEL_GRUNDBEDARF = "Grundbedarf";
	public static final String FULL = "100%";
	public static final String NEW_ARTIKEL = "Neuer Artikel";
	public static final String NEW_MENGENEINHEIT = "Neue Mengeeinheit";
	public static final String NEW_KATEGORIE = "Neue Kategorie";
	public static final String NEW_LAGERORT = "Neuer Lagerort";
	
	public static final String MESSAGE_LEER_ARG_1 = "Tragen Sie bitte im Feld %s den Wert ein";
	public static final String MESSAGE_EXISTS_ARG_1 = "Der Name %s ist bereits im System vorhanden";
	
	public static final String MESSAGE_SUSSEFULL_ARG_1 = "%s wurde erfolgreich gespeichert";
	
	protected VerticalLayout boxVL;
	protected HorizontalLayout durchschnittHL;
	protected HorizontalLayout controlHL;
	protected Label headlineLabel;
	
	protected TextField nameField;
	
	protected Button speichernButton;
	protected Button verwerfenButton;
	protected Button deaktivierenButton;
	protected Button filterButton;
	protected Button auswaehlenButton;
	
	protected Artikel artikel;
	
	public ArtikelAbstract() {
		super();
	}

	public ArtikelAbstract(Component... children) {
		super(children);
	}

	protected IntStepper intStepperSetting(IntStepper intStepper, String name,
			String width, int min, int max, String desc) {
		intStepper = new IntStepper(name);
		intStepper.setMaxValue(max);
		intStepper.setMinValue(min);
		intStepper.setStyleName("stepper-palaver");
		intStepper.setWidth(width);
		intStepper.setDescription(desc);
		intStepper.setVisible(false);
		return intStepper;

	}

	protected Button buttonSetting(Button button, String title, String icon) {
		button = new Button(title);
		button.setIcon(new ThemeResource(icon));
		return button;
	}

	private TextField textFieldSetting(TextField field, String name,
			String width, boolean required, String descript) {		
		field = new TextField(name);
		field.setWidth(width);
		field.setRequired(required);
		field.setImmediate(true);
		field.setDescription(descript);
		return field;
	}
	
	protected TextField textFieldSettingAE(TextField field, String name,
			String width, boolean required, String descript,
			ArtikelErstellen aErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(aErstellen);
		return field;
	}

	protected TextField textFieldSettingME(TextField field, String name,
			String width, boolean required, String descript,
			MengeneinheitErstellen mErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(mErstellen);
		return field;
	}
	
	protected TextField textFieldSettingKE(TextField field, String name,
			String width, boolean required, String descript,
			KategorieErstellen kErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(kErstellen);
		return field;
	}
	protected TextField textFieldSettingLE(TextField field, String name,
			String width, boolean required, String descript,
			LagerortErstellen lErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(lErstellen);
		return field;
	}

	protected NativeSelect nativeSelectSetting(NativeSelect select,
			String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
		select.setWidth(ArtikelAbstract.FULL);
		select.setRequired(required);
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

	class Hr extends Label {
		Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}
}

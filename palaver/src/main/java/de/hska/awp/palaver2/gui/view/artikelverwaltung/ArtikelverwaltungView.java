package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.gui.view.ViewAbstract;
import de.hska.awp.palaver2.util.IConstants;

public class ArtikelverwaltungView extends ViewAbstract {
	private static final long serialVersionUID = 682496000359523955L;
	protected static final String ARTIKEL_NAME = "Artikelname";
	protected static final String ARTIKEL_PREIS = "Preis";
	protected static final String ARTIKEL_NUMMER = "Artikelnummer";
	protected static final String ARTIKEL_GEBINDE = "Gebinde";
	protected static final String ARTIKEL_NOTIZ = "Notiz";
	protected static final String ARTIKEL_STANDARD = "Standard";
	protected static final String ARTIKEL_GRUNDBEDARF = "Grundbedarf";

	protected static final String NEW_ARTIKEL = "Neuer Artikel";
	protected static final String NEW_MENGENEINHEIT = "Neue Mengeeinheit";
	protected static final String NEW_KATEGORIE = "Neue Kategorie";
	protected static final String NEW_LAGERORT = "Neuer Lagerort";
	
	protected HorizontalLayout durchschnittHL;
	protected TextField nameField;
	protected boolean create = true;
	
	protected Button m_createNewButton;
	
	protected Artikel m_artikel;
	protected Kategorie m_kategorie;
	protected Mengeneinheit m_mengeneinheit;
	
	protected MengeneinheitErstellen m_mengeneinheitErstellen;
	protected KategorieErstellen m_kategorieErstellen;
	
	protected ArtikelverwaltungView() {
		super();
	}

	protected ArtikelverwaltungView(Component... children) {
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
	private TextField textFieldSetting(TextField field, String name,
			String width, boolean required, String descript) {		
		field = new TextField(name);
		field.setWidth(width);
		if(required) {
			field.addValidator(new StringLengthValidator(IConstants.INFO_REZEPT_NAME_VALID, 1, 100, true));
		}
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

}

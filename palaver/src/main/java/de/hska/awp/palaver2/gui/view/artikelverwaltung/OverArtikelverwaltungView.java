package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.util.List;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.domain.Lagerort;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.gui.view.ViewAbstract;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

@SuppressWarnings("serial")
public class OverArtikelverwaltungView extends ViewAbstract {
	public static final String ARTIKEL = "Artikel";	
	public static final String MENGENEINHEIT = "Mengeneinheit";
	public static final String KATEGORIE = "Kategorie";
	public static final String LAGERORT = "Lagerort";
	
	
	public static final String FIELD_KURZ = "kurz";
	public static final String FIELD_ARTIKEL_NR = "artikelnr";
	public static final String FIELD_LIEFERANT = "lieferant";
	public static final String FIELD_KATEGORIE = "kategorie";
	public static final String FIELD_LAGERORT = "lagerort";
	public static final String FIELD_PREIS = "preis";
	public static final String FIELD_STANDARD = "standard";
	public static final String FIELD_GRUNDBEDARF = "grundbedarf";
	public static final String FIELD_BESTELLGROESSE = "bestellgroesse";
	public static final String FIELD_NOTIZ = "notiz";
	

	public static final String ARTIKEL_NAME = "Artikelname";
	public static final String ARTIKEL_PREIS = "Preis";
	public static final String ARTIKEL_NUMMER = "Artikelnummer";
	public static final String ARTIKEL_GEBINDE = "Gebinde";
	public static final String ARTIKEL_NOTIZ = "Notiz";
	public static final String ARTIKEL_STANDARD = "Standard";
	public static final String ARTIKEL_GRUNDBEDARF = "Grundbedarf";
	
	public static final String MENGENEINHEIT_ABKUERZUNG = "Abkürzung";

	public static final String EDIT_ARTIKEL = "Artikel bearbeiten";
	public static final String EDIT_KATEGORIE = "Kategorie bearbeiten";
	public static final String EDIT_MENGENEINHEIT = "Mengeneinheit bearbeiten";
	public static final String EDIT_LAGERORT = "Lagerort bearbeiten";
	
	public static final String NEW_ARTIKEL = "Neuer Artikel";
	public static final String NEW_MENGENEINHEIT = "Neue Mengeeinheit";
	public static final String NEW_KATEGORIE = "Neue Kategorie";
	public static final String NEW_LAGERORT = "Neuer Lagerort";
	
	
	public Mengeneinheit m_mengeneinheit;
	public Kategorie m_kategorie;
	public Artikel m_artikel;
	public Lagerort m_lagerort;
	public MengeneinheitErstellen m_mengeneinheitErstellen;
	public ArtikelErstellen m_artikelErstellen;
	public KategorieErstellen m_kategorieErstellen;
	public LagerortErstellen m_lagerortErstellen;
	
	public List<Artikel> m_artikeln;
	public List<Mengeneinheit> m_mengen;
	public List<Kategorie> m_kategorien;
	public List<Lagerort> m_lagerorts;
	public List<Lieferant> m_lieferanten;



	

	////////////////////////////////////
	
	
	
	
	
	
	
	
	public OverArtikelverwaltungView() {
		super();
	}

	protected OverArtikelverwaltungView(Component... children) {
		super(children);
	}
	
	protected NativeSelect nativeSelectSetting(NativeSelect select,
			final String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
		select.setWidth(OverArtikelverwaltungView.FULL);
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

	protected TextField textFieldSettingLE(TextField field, String name,
			String width, boolean required, String descript,
			LagerortErstellen lErstellen) {
		field =  textFieldSetting(field, name, width, required, descript);
		field.addValueChangeListener(lErstellen);
		return field;
	}

}

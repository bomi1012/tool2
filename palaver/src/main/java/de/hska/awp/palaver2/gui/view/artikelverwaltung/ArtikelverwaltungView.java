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
public class ArtikelverwaltungView extends ViewAbstract {
	protected static final String ARTIKEL = "Artikel";	
	protected static final String MENGENEINHEIT = "Mengeneinheit";
	protected static final String KATEGORIE = "Kategorie";
	protected static final String LAGERORT = "Lagerort";
	
	
	protected static final String FIELD_KURZ = "kurz";
	protected static final String FIELD_ARTIKEL_NR = "artikelnr";
	protected static final String FIELD_LIEFERANT = "lieferant";
	protected static final String FIELD_KATEGORIE = "kategorie";
	protected static final String FIELD_LAGERORT = "lagerort";
	protected static final String FIELD_PREIS = "preis";
	protected static final String FIELD_STANDARD = "standard";
	protected static final String FIELD_GRUNDBEDARF = "grundbedarf";
	protected static final String FIELD_BESTELLGROESSE = "bestellgroesse";
	protected static final String FIELD_NOTIZ = "notiz";
	

	protected static final String ARTIKEL_NAME = "Artikelname";
	protected static final String ARTIKEL_PREIS = "Preis";
	protected static final String ARTIKEL_NUMMER = "Artikelnummer";
	protected static final String ARTIKEL_GEBINDE = "Gebinde";
	protected static final String ARTIKEL_NOTIZ = "Notiz";
	protected static final String ARTIKEL_STANDARD = "Standard";
	protected static final String ARTIKEL_GRUNDBEDARF = "Grundbedarf";
	
	protected static final String MENGENEINHEIT_ABKUERZUNG = "Abkürzung";

	protected static final String EDIT_ARTIKEL = "Artikel bearbeiten";
	protected static final String EDIT_KATEGORIE = "Kategorie bearbeiten";
	protected static final String EDIT_MENGENEINHEIT = "Mengeneinheit bearbeiten";
	protected static final String EDIT_LAGERORT = "Lagerort bearbeiten";
	
	protected static final String NEW_ARTIKEL = "Neuer Artikel";
	protected static final String NEW_MENGENEINHEIT = "Neue Mengeeinheit";
	protected static final String NEW_KATEGORIE = "Neue Kategorie";
	protected static final String NEW_LAGERORT = "Neuer Lagerort";
	
	
	public Mengeneinheit m_mengeneinheit;
	public Kategorie m_kategorie;
	public Artikel m_artikel;
	public Lagerort m_lagerort;
	public MengeneinheitErstellen m_mengeneinheitErstellen;
	public ArtikelErstellen m_artikelErstellen;
	public KategorieErstellen m_kategorieErstellen;
	public LagerortErstellen m_lagerortErstellen;
	
	public List<Mengeneinheit> m_mengen;
	public List<Kategorie> m_kategorien;
	public List<Lagerort> m_lagerorts;
	public List<Lieferant> m_lieferanten;



	

	////////////////////////////////////
	
	
	
	
	
	
	
	
	protected ArtikelverwaltungView() {
		super();
	}

	protected ArtikelverwaltungView(Component... children) {
		super(children);
	}
	
	protected NativeSelect nativeSelectSetting(NativeSelect select,
			final String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
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

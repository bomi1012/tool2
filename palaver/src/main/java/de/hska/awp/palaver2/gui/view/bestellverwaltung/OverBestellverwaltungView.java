package de.hska.awp.palaver2.gui.view.bestellverwaltung;

import java.util.Date;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;

import de.hska.awp.palaver2.gui.components.Grundbedarf;
import de.hska.awp.palaver2.gui.view.ViewAbstract;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.service.bestellverwaltung.BestellpositionService;
import de.palaver.service.bestellverwaltung.BestellungService;

public class OverBestellverwaltungView extends ViewAbstract {
	private static final long serialVersionUID = 2853996004535479919L;
	public List<Lieferant> m_lieferanten;
	public List<Grundbedarf> m_grundbedarfe;
	protected HorizontalLayout m_control;
	
	public Bestellposition m_bestellposition;
	public List<Bestellposition> m_bestellpositions;
	public BestellpositionService m_bestellpositionService;
	
	public List<Bestellung> m_bestellungs;
	public Bestellung m_bestellung;
	public BestellungService m_bestellungService;
	
	protected OverBestellverwaltungView() {
		super();
	}

	protected OverBestellverwaltungView(Component... children) {
		super(children);
	}
	
	protected DateField dateField(String name, String format, String width,
			Date date, boolean week) {
		m_date = new DateField(name);
		m_date.setWidth(width);
		m_date.setDateFormat(format);
		m_date.setValue(date);
		m_date.setShowISOWeekNumbers(week);
		return m_date;
	}
	
	protected NativeSelect nativeSelectSetting(NativeSelect select,
			final String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
		select.setWidth(FULL);
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
		select.addValueChangeListener((GrundbedarfGenerierenAnsicht) object);
		select.setDescription(descript);
		return select;
	}
}

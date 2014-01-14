package de.palaver.view.bestellverwaltung;

import java.util.Date;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;

import de.hska.awp.palaver2.util.IConstants;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.bestellverwaltung.BestellpositionService;
import de.palaver.service.bestellverwaltung.BestellungService;
import de.palaver.view.ViewAbstract;
import de.palaver.view.models.GrundbedarfModel;

public class OverBestellverwaltungView extends ViewAbstract {
	private static final long serialVersionUID = 2853996004535479919L;
	
	public List<Lieferant> m_lieferanten;
	public List<GrundbedarfModel> m_grundbedarfe;
	protected HorizontalLayout m_control;
	
	public Bestellposition m_bestellposition;
	public List<Bestellposition> m_bestellpositions;
	public BestellpositionService m_bestellpositionService;
	
	public List<Bestellung> m_bestellungs;
	public Bestellung m_bestellung;
	public BestellungService m_bestellungService;

	protected Button m_deleteButton;
	protected Button m_editButton;
	protected Button m_vorschauButton;
	
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
	
	protected HorizontalLayout controlAlleBestellungenPanel() {	
		m_deleteButton = deleteButton(true, true);
		m_editButton = editButton(true, true);
		m_vorschauButton = buttonSetting(m_button, "Vorschau",
				IConstants.ICON_PAGE_WHITE_LUPE, true, true);
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_vorschauButton);
		m_horizontalLayout.addComponent(m_editButton);
		m_horizontalLayout.addComponent(m_deleteButton);
		m_horizontalLayout.setEnabled(false);
		return m_horizontalLayout;
	}
	
}

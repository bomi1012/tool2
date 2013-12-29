package de.hska.awp.palaver2.gui.view.bestellverwaltung;

import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;

import de.hska.awp.palaver2.gui.view.ViewAbstract;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

public class BestellverwaltungView extends ViewAbstract {
	private static final long serialVersionUID = 2853996004535479919L;
	protected List<Lieferant> m_lieferanten;
	protected HorizontalLayout m_control;
	protected BestellverwaltungView() {
		super();
	}

	protected BestellverwaltungView(Component... children) {
		super(children);
	}
	
	
	protected NativeSelect nativeSelectSetting(NativeSelect select,
			final String name, String width, boolean required, String descript,
			Object object) {
		select = new NativeSelect(name);
		Object value = select.getValue();
		select.setWidth(FULL);
		if(required) {
			select.addValidator(new Validator() {
				private static final long serialVersionUID = 1972800127752278750L;
				@Override
				public void validate(Object value) throws InvalidValueException {
					if (value == null) {
						throw new InvalidValueException("W�hlen Sie bitte den Name aus der Auswahl");
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

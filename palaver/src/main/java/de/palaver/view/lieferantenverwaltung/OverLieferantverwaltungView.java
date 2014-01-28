package de.palaver.view.lieferantenverwaltung;

import com.vaadin.ui.HorizontalLayout;

import de.palaver.domain.person.Adresse;
import de.palaver.domain.person.Kontakte;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.view.ViewAbstract;

@SuppressWarnings("serial")
public class OverLieferantverwaltungView extends ViewAbstract {

	protected HorizontalLayout m_control;


	protected Lieferant m_lieferant;
	protected Ansprechpartner m_ansprechpartner;
	protected Adresse m_adresse;
	protected Kontakte m_kontakte;
	
	protected OverLieferantverwaltungView() {
		super();
	}


}

package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import de.bistrosoft.palaver.data.KuchenplanDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;
import de.bistrosoft.palaver.util.Week;

/**
 * 
 * @author Christine Hartkorn
 * 
 */

public class Kuchenplanverwaltung extends KuchenplanDAO {

	private static Kuchenplanverwaltung instance = null;

	// Konstruktor
	private Kuchenplanverwaltung() {
		super();
	}

	// Methode, die eine Instanz der Kuchenplanverwaltung zur�ckliefert
	public static Kuchenplanverwaltung getInstance() {
		if (instance == null) {
			instance = new Kuchenplanverwaltung();
		}
		return instance;
	}

	// Methode, die Kuchenplan mit Inhalten zu einer Woche zur�ckliefert
	public Kuchenplan getKuchenplanByWeekWithItems(Week week) {
		Kuchenplan mpl = null;
		try {
			mpl = super.getKuchenplanByWeekWithItems(week);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mpl;
	}

	// Methode, die Kuchenplan speichert
	public void persist(Kuchenplan kuchenplan) {
		try {
			if (kuchenplan.getId() == null) {
				super.createKuchenplan(kuchenplan);
				kuchenplan.setId(super.getKuchenplanByWeekWithItems(
						kuchenplan.getWeek()).getId());
			}
			super.deleteItemsByKuchenplan(kuchenplan);
			for (KuchenplanHasKuchenrezept kc : kuchenplan.getKuchenrezepte()) {

				super.createKuchenrezepteForKuchenplan(kuchenplan,
						kc.getKuchenrezept(), kc.getKuchenname(), kc.getTag(),
						kc.getAnzahl());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

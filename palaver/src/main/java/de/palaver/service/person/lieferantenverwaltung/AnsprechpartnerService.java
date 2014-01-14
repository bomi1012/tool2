package de.palaver.service.person.lieferantenverwaltung;

import de.palaver.dao.person.lieferantenverwaltung.AnsprechpartnerDAO;

public class AnsprechpartnerService extends AnsprechpartnerDAO {

	private static AnsprechpartnerService instance = null;

	private AnsprechpartnerService() {
		super();
	}

	public static AnsprechpartnerService getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerService();
		}
		return instance;
	}
	
	
}

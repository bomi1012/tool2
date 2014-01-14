package de.palaver.dao.person.lieferantenverwaltung;

import de.palaver.dao.AbstractDAO;


public class AnsprechpartnerDAO extends AbstractDAO {

	private static AnsprechpartnerDAO instance = null;

	public AnsprechpartnerDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static AnsprechpartnerDAO getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerDAO();
		}
		return instance;
	}

}

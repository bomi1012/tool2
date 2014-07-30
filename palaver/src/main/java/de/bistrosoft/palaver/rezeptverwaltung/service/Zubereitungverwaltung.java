package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.DAO.ZubereitungDAO;

/**
 * @author Michael Marschall
 * 
 */
public class Zubereitungverwaltung extends ZubereitungDAO {

	private static Zubereitungverwaltung instance = null;

	private Zubereitungverwaltung() {
		super();
	}

	public static Zubereitungverwaltung getInstance() {
		if (instance == null) {
			instance = new Zubereitungverwaltung();
		}
		return instance;
	}

	public List<Zubereitung> getAllZubereitungs() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> result = null;
		result = super.getAllZubereitungs();
		return result;
	}

	public Zubereitung getZubereitungByName(String name)
			throws ConnectException, DAOException, SQLException {
		// List<Zubereitung> result = null;
		// result = super.getZubereitungByName(name);
		return super.getZubereitungByName(name);
	}

	public void createZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		super.createZubereitung(zubereitung);
	}

	public void updateZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		super.updateZubereitung(zubereitung);
	}
}

package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.FussnoteDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Fussnote;

/**
 * @author Michael Marschall
 * 
 */

public class Fussnotenverwaltung extends FussnoteDAO {

	private static Fussnotenverwaltung instance = null;

	private Fussnotenverwaltung() {
		super();
	}

	public static Fussnotenverwaltung getInstance() {
		if (instance == null) {
			instance = new Fussnotenverwaltung();
		}
		return instance;
	}

	public List<Fussnote> getAllFussnote() throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> result = null;
		result = super.getAllFussnote();
		return result;
	}

	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		Fussnote result = null;
		result = super.getFussnoteById(id);
		return result;
	}

	public Fussnote getFussnoteByName(String name) throws ConnectException,
			DAOException, SQLException {
		Fussnote result = null;
		result = super.getFussnoteByName(name);
		return result;
	}

	public void createFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		super.createFussnote(fussnote);
	}

	public void updateFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		super.updateFussnote(fussnote);
	}
}

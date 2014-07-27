package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.GeschmackDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Geschmack;

/**
 * @author Michael Marschall
 * 
 */
public class Geschmackverwaltung extends GeschmackDAO {

	private static Geschmackverwaltung instance = null;

	private Geschmackverwaltung() {
		super();
	}

	public static Geschmackverwaltung getInstance() {
		if (instance == null) {
			instance = new Geschmackverwaltung();
		}
		return instance;
	}

	public List<Geschmack> getAllGeschmack() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> result = null;
		result = super.getAllGeschmack();
		return result;
	}

	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		Geschmack result = null;
		result = super.getGeschmackById(id);
		return result;
	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> result = null;
		result = super.getGeschmackByName(name);
		return result;
	}

	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		super.createGeschmack(geschmack);
	}

	public void updateGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		super.updateGeschmack(geschmack);
	}

}

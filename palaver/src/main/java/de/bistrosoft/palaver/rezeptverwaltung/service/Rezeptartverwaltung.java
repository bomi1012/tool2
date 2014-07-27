package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Recipetype;
import de.bistrosoft.palaver.data.RezeptartDAO;

/**
 * @author Michael Marschall
 * 
 */
public class Rezeptartverwaltung extends RezeptartDAO {

	private static Rezeptartverwaltung instance = null;

	private Rezeptartverwaltung() {
		super();
	}

	public static Rezeptartverwaltung getInstance() {
		if (instance == null) {
			instance = new Rezeptartverwaltung();
		}
		return instance;
	}

	public List<Recipetype> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {
		List<Recipetype> result = null;
		result = super.getAllRezeptart();
		return result;
	}

	public Recipetype getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Recipetype recipetype = null;
		recipetype = super.getRezeptartById(id);
		return recipetype;
	}

	public void createRezeptart(Recipetype recipetype) throws ConnectException,
			DAOException, SQLException {
		super.createRezeptart(recipetype);
	}

	public void updateRezeptart(Recipetype recipetype) throws ConnectException,
			DAOException, SQLException {
		super.updateRezeptart(recipetype);
	}
}
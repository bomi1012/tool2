package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;

import de.bistrosoft.palaver.data.RezeptartDAO;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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

	public Recipetype getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Recipetype recipetype = null;
		recipetype = super.getRezeptartById(id);
		return recipetype;
	}
}
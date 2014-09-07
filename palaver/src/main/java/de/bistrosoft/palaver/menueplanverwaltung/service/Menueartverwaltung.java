package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.DAO.MenutypeDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * @author Jasmin Baumgartner
 * 
 */
public class Menueartverwaltung extends MenutypeDAO {

	private static Menueartverwaltung instance = null;

	private Menueartverwaltung() {
		super();
	}

	public static Menueartverwaltung getInstance() {
		if (instance == null) {
			instance = new Menueartverwaltung();
		}
		return instance;
	}

	public List<Menutype> getAllMenutypes() throws ConnectException,
			DAOException, SQLException {
		List<Menutype> result = null;
		result = super.getAllMenutypes();
		return result;
	}


	public Menutype getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Menutype menutype = null;
		menutype = super.getMenueartById(id);
		return menutype;
	}


}
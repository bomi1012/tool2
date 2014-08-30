package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.DAO.MenueartDAO;

/**
 * @author Jasmin Baumgartner
 * 
 */
public class Menueartverwaltung extends MenueartDAO {

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

	public List<Menutype> getAllMenueart() throws ConnectException,
			DAOException, SQLException {
		List<Menutype> result = null;
		result = super.getAllMenueart();
		return result;
	}

	public List<Menutype> getMenueartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Menutype> result = null;
		result = super.getMenueartByName(name);
		return result;
	}

	public Menutype getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Menutype menutype = null;
		menutype = super.getMenueartById(id);
		return menutype;
	}

	public void createMenueart(Menutype menutype) throws ConnectException,
			DAOException, SQLException {
		super.createMenueart(menutype);
	}

	public void updateMenuetart(Menutype menutype) throws ConnectException,
			DAOException, SQLException {
		super.updateMenueart(menutype);
	}
}
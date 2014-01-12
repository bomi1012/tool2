package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.MenueartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

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

	public List<Menueart> getAllMenueart() throws ConnectException,
			DAOException, SQLException {
		List<Menueart> result = null;
		result = super.getAllMenueart();
		return result;
	}

	public List<Menueart> getMenueartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Menueart> result = null;
		result = super.getMenueartByName(name);
		return result;
	}

	public Menueart getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Menueart menueart = null;
		menueart = super.getMenueartById(id);
		return menueart;
	}

	public void createMenueart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		super.createMenueart(menueart);
	}

	public void updateMenuetart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		super.updateMenueart(menueart);
	}
}
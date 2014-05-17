package de.palaver.management.artikel.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.DAO.LagerortDAO;

public class LagerorService {
	private static LagerorService instance = null;
	
	private LagerorService() {
		super();
	}

	public static LagerorService getInstance() {
		if (instance == null) {
			instance = new LagerorService();
		}
		return instance;
	}

	public List<Lagerort> getAllLagerorts() throws ConnectException,
			DAOException, SQLException {
		return LagerortDAO.getInstance().getAllLagerorts();
	}

	public void createLagerort(Lagerort lagerort) throws ConnectException, DAOException {
		LagerortDAO.getInstance().createLagerort(lagerort);		
	}

	public void updateLagerort(Lagerort lagerort) throws ConnectException, DAOException, SQLException {
		LagerortDAO.getInstance().updateLagerort(lagerort);
	}

	public void deleteLagerort(Long id) throws ConnectException, DAOException {
		LagerortDAO.getInstance().deleteLagerort(id);
	}
}

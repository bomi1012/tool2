package de.palaver.management.artikel.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.DAO.LagerortDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class LagerortService {
	private static LagerortService instance = null;
	
	private LagerortService() {
		super();
	}

	public static LagerortService getInstance() {
		if (instance == null) {
			instance = new LagerortService();
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

	public void removeLagerort(Long id) throws ConnectException, DAOException {
		LagerortDAO.getInstance().removeLagerort(id);
	}
}

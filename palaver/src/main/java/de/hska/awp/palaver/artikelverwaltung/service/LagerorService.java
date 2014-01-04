package de.hska.awp.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.LagerortDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Lagerort;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class LagerorService extends LagerortDAO {
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
		return super.getInstance().getAllLagerorts();
	}

	public void createLagerort(Lagerort lagerort) throws ConnectException, DAOException {
		super.getInstance().createLagerort(lagerort);		
	}
}

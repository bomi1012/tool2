package de.palaver.service.artikelverwaltung;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.artikelverwaltung.LagerortDAO;
import de.palaver.domain.artikelverwaltung.Lagerort;

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

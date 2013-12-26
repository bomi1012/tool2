package de.hska.awp.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.LagerortDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Lagerort;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class Lagerortverwaltung extends LagerortDAO {
	private static Lagerortverwaltung instance = null;
	
	private Lagerortverwaltung() {
		super();
	}

	public static Lagerortverwaltung getInstance() {
		if (instance == null) {
			instance = new Lagerortverwaltung();
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

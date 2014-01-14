package de.palaver.service.person;

import java.sql.SQLException;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.person.KontakteDAO;
import de.palaver.domain.person.Kontakte;

public class KontakteService {
	private static KontakteService instance = null;
	public KontakteService() {
		super();
	}

	public static KontakteService getInstance() {
		if (instance == null) {
			instance = new KontakteService();
		}
		return instance;
	}

	public Kontakte getKontakteById(long id) throws ConnectException, DAOException, SQLException {
		if (id != 0) {
			return KontakteDAO.getInstance().getKontakteById(id);
		}
		return null;
	}
}

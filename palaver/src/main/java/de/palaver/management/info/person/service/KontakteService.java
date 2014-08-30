package de.palaver.management.info.person.service;

import java.sql.SQLException;

import de.palaver.management.info.person.Kontakte;
import de.palaver.management.info.person.DAO.KontakteDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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

	public long createKontakte(Kontakte kontakte) throws ConnectException, DAOException {
		return KontakteDAO.getInstance().createKontakte(kontakte);
	}

	public void deleteKontakte(Long id) throws ConnectException, DAOException {
		KontakteDAO.getInstance().deleteKontakte(id);	
	}

	public void updatekontakte(Kontakte kontakte) throws ConnectException, DAOException {
		KontakteDAO.getInstance().updatekontakte(kontakte);	
	}
}

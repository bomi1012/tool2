package de.palaver.service.person;

import java.sql.SQLException;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.person.AdresseDAO;
import de.palaver.domain.person.Adresse;

public class AdresseService {

	private static AdresseService instance = null;
	public AdresseService() {
		super();
	}

	public static AdresseService getInstance() {
		if (instance == null) {
			instance = new AdresseService();
		}
		return instance;
	}

	public Adresse getAdresseById(long id) throws ConnectException, DAOException, SQLException {
		if (id != 0) {
			return AdresseDAO.getInstance().getAdresseById(id);
		}
		return null;
	}
}

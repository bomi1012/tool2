package de.palaver.management.info.person.service;

import java.sql.SQLException;

import de.palaver.management.info.person.Adresse;
import de.palaver.management.info.person.DAO.AdresseDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class AdresseService {

	private static AdresseService m_instance = null;
	public AdresseService() {
		super();
	}

	public static AdresseService getInstance() {
		if (m_instance == null) {
			m_instance = new AdresseService();
		}
		return m_instance;
	}

	public Adresse getAdresseById(long id) throws ConnectException, DAOException, SQLException {
		if (id != 0) {
			return AdresseDAO.getInstance().getAdresseById(id);
		}
		return null;
	}

	public Long createAdresse(Adresse adresse) throws ConnectException, DAOException {
		return AdresseDAO.getInstance().createAdresse(adresse);
	}

	public void deleteAdresse(Long id) throws ConnectException, DAOException {
		AdresseDAO.getInstance().deleteAdresse(id);
	}

	public void updateAdresse(Adresse adresse) throws ConnectException, DAOException {
		AdresseDAO.getInstance().updateAdresse(adresse);		
	}
}

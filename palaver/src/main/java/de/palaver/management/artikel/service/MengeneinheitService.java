package de.palaver.management.artikel.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.artikel.DAO.MengeneinheitDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class MengeneinheitService {

	private static MengeneinheitService instance = null;

	private MengeneinheitService() {
		super();
	}

	public static MengeneinheitService getInstance() {
		if (instance == null) {
			instance = new MengeneinheitService();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Mengeneinheiten zurück.
	 */
	public List<Mengeneinheit> getAllMengeneinheiten() throws ConnectException, DAOException, SQLException {
		return MengeneinheitDAO.getInstance().getAllMengeneinheiten();
	}

	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter id zurück.
	 */
	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException {
		return MengeneinheitDAO.getInstance().getMengeneinheitById(id);
	}

	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter name zurück.
	 */
	public List<Mengeneinheit> getMengeneinheitByName(String name) throws ConnectException, DAOException, SQLException {
		return MengeneinheitDAO.getInstance().getMengeneinheitByName(name);
	}

	public void createMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException {
		MengeneinheitDAO.getInstance().createMengeneinheit(mengeneinheit);
	}
	public void updateMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException {
		MengeneinheitDAO.getInstance().updateMengeneinheit(mengeneinheit);
	}
	public void removeMengeneinheit(Long id) throws ConnectException, DAOException {
		MengeneinheitDAO.getInstance().removeMengeneinheit(id);
		
	}
}

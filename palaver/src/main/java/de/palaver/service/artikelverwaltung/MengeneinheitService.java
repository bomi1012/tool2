package de.palaver.service.artikelverwaltung;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.palaver.dao.artikelverwaltung.MengeneinheitDAO;
import de.palaver.domain.artikelverwaltung.Mengeneinheit;

public class MengeneinheitService extends MengeneinheitDAO {

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
		return super.getAllMengeneinheiten();
	}

	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter id zurück.
	 */
	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException {
		return super.getMengeneinheitById(id);
	}

	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter name zurück.
	 */
	public List<Mengeneinheit> getMengeneinheitByName(String name) throws ConnectException, DAOException, SQLException {
		return super.getMengeneinheitByName(name);
	}

	public void createMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException {
		super.createMengeneinheit(mengeneinheit);
	}
	public void updateMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException {
		super.updateMengeneinheit(mengeneinheit);
	}
	public void deleteMengeneinheit(Long id) throws ConnectException, DAOException {
		super.deleteMengeneinheit(id);
		
	}
}

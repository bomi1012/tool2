package de.hska.awp.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.MengeneinheitDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class Mengeneinheitverwaltung extends MengeneinheitDAO {

	private static Mengeneinheitverwaltung instance = null;

	private Mengeneinheitverwaltung() {
		super();
	}

	public static Mengeneinheitverwaltung getInstance() {
		if (instance == null) {
			instance = new Mengeneinheitverwaltung();
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

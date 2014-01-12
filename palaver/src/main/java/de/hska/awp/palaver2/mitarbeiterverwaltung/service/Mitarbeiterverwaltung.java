package de.hska.awp.palaver2.mitarbeiterverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * Die Klasse ermöglicht die Verwaltung der Mitarbeiter.
 * 
 * @author Christian Barth
 * 
 */
public class Mitarbeiterverwaltung extends MitarbeiterDAO {

	private static Mitarbeiterverwaltung instance = null;

	private Mitarbeiterverwaltung() {
		super();
	}

	public static Mitarbeiterverwaltung getInstance() {
		if (instance == null) {
			instance = new Mitarbeiterverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Mitarbeiter zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> result = null;

		result = super.getAllMitarbeiter();

		return result;
	}

	/**
	 * Die Methode liefert mehrere Mitarbeiter anhand ihres Namen zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Mitarbeiter> getMitarbeiterByName(String name) throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> result = null;

		result = super.getMitarbeiterByName(name);

		return result;
	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand der Id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return Mitarbeiter
	 */
	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException, DAOException, SQLException {
		Mitarbeiter mitarbeiter = super.getMitarbeiterById(id);
		return mitarbeiter;
	}

	/**
	 * Die Methode liefert alle Mitarbeiter zur einer RollenId zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Mitarbeiter> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> result = null;

		result = super.getMitarbeiterByRollenId(id);

		return result;
	}

	/**
	 * Die Methode erzeugt einen Mitarbeiter.
	 */
	public void createMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {

		super.createMitarbeiter(mitarbeiter);
	}

	/**
	 * Die Methode aktualisiert einen Mitarbeiter.
	 */
	public void updateMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {

		super.updateMitarbeiter(mitarbeiter);
	}

}

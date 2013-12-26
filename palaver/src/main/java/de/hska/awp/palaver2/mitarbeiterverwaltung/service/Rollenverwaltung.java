/**
 * Created by Christian Barth
 * 18.05.2013 - 10:14:28
 */
package de.hska.awp.palaver2.mitarbeiterverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * Die Klasse ermöglicht die Verwaltung der Rollen.
 * 
 * @author Christian Barth
 * 
 */
public class Rollenverwaltung extends RollenDAO {

	private static Rollenverwaltung instance = null;

	private Rollenverwaltung() {
		super();
	}

	public static Rollenverwaltung getInstance() {
		if (instance == null) {
			instance = new Rollenverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Rollen zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Rollen>
	 */
	public List<Rollen> getAllRollen() throws ConnectException, DAOException, SQLException {

		List<Rollen> result = null;

		result = super.getAllRollen();

		return result;
	}

	/**
	 * Die Methode liefert eine Rollen anhand der Id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return Rollen
	 */
	public Rollen getRollenById(Long id) throws ConnectException, DAOException, SQLException {
		Rollen rolle = super.getRollenById(id);
		return rolle;
	}

	/**
	 * Die Methode liefert alle Rollen zu einer MitarbeiterId zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Rollen>
	 */
	public List<Rollen> getRollenByMitarbeiterId(Long id) throws ConnectException, DAOException, SQLException {

		List<Rollen> result = null;

		result = super.getRollenByMitarbeiterId(id);

		return result;
	}

	/**
	 * Die Methode erzeugt eine Rollen.
	 */
	public void createRollen(Rollen rolle) throws ConnectException, DAOException, SQLException {

		super.createRollen(rolle);
	}

	/**
	 * Die Methode aktualisiert eine Rollen.
	 */
	public void updateRollen(Rollen rolle) throws ConnectException, DAOException, SQLException {

		super.updateRollen(rolle);
	}

}

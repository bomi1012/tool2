/**
 * Created by Christian Barth
 * 26.04.2013 - 09:32:35
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.data.AnsprechpartnerDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Die Klasse ermöglicht die Verwaltung der Ansprechpartner und stellt für die
 * GUI Methoden bereit.
 * 
 * @author Christian Barth
 * 
 */
public class Ansprechpartnerverwaltung extends AnsprechpartnerDAO {

	private static Ansprechpartnerverwaltung instance = null;

	private Ansprechpartnerverwaltung() {
		super();
	}

	public static Ansprechpartnerverwaltung getInstance() {
		if (instance == null) {
			instance = new Ansprechpartnerverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Ansprechpartner zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public List<Ansprechpartner> getAllAnsprechpartner() throws ConnectException, DAOException, SQLException {

		List<Ansprechpartner> result = null;

		result = super.getAllAnsprechpartner();

		return result;
	}

	/**
	 * Die Methode liefert einen Ansprechpartner anhand des Parameter id zurück.
	 * 
	 * @param id
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public Ansprechpartner getAnsprechpartnerById(Long id) throws ConnectException, DAOException, SQLException {
		Ansprechpartner result = null;

		result = super.getAnsprechpartnerById(id);

		return result;
	}

	/**
	 * Die Methode liefert einen oder mehrere Ansprechpartner anhand des
	 * Parameter name zurück.
	 * 
	 * @param name
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public List<Ansprechpartner> getAnsprechpartnerByName(String name) throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> result = null;

		result = super.getAnsprechpartnerByName(name);

		return result;
	}

	/**
	 * Die Methode erzeugt einen Ansprechpartner.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException, SQLException {
		super.createAnsprechpartner(ansprechpartner);
	}

	/**
	 * Die Methode aktualisiert einen Ansprechpartner.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException, SQLException {
		super.updateAnsprechpartner(ansprechpartner);
	}

	/**
	 * Die Methode löscht einen Ansprechpartner.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void deleteAnsprechpartner(Long id) throws ConnectException, DAOException, SQLException {

		super.deleteAnsprechpartner(id);
	}

	/**
	 * Die Methode gibt alle Ansprechpartner von einem Lieferanten zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public List<Ansprechpartner> getAnsprechpartnerByLieferant(Lieferant lieferant) {
		List<Ansprechpartner> result = null;

		try {
			result = super.getAnsprechpartnerByLieferant(lieferant);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}

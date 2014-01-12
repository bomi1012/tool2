/**
 * Created by Christian Barth
 * 26.04.2013 - 08:32:35
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * Klasse AnsprechpartnerDAO. Die Klasse stellt für den Ansprechpartner alle
 * notwendigen Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Christian Barth
 */
public class AnsprechpartnerDAO extends AbstractDAO {

	private static AnsprechpartnerDAO instance = null;

	private final static String TABLE = "ansprechpartner";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TELEFON = "telefon";
	private final static String HANDY = "handy";
	private final static String FAX = "fax";
	private final static String EMAIL = "email";
	private final static String BEZEICHNUNG = "bezeichnung";
	private final static String LIEFERANT_FK = "lieferant_fk";
	private final static String GET_ALL_ANSPRECHPARTNER = "SELECT * FROM "
			+ TABLE + " ORDER BY ansprechpartner.name";
	private static final String GET_ANSPRECHPARTNER_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "= {0}";
	private static final String GET_ANSPRECHPARTNER_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_ANSPRECHPARTNER = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private static final String GET_ANSPRECHPARTNER_BY_LIEFERANT = "SELECT * FROM "
			+ TABLE + " WHERE " + LIEFERANT_FK + "=";

	public AnsprechpartnerDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static AnsprechpartnerDAO getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllAnsprechpartner liefert alle in der Datenbank
	 * befindlichen Ansprechpartner zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Ansprechpartner> getAllAnsprechpartner()
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();
		ResultSet set = getManaged(GET_ALL_ANSPRECHPARTNER);
		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID),
					set.getString("name"), set.getString("telefon"), set
							.getString("handy"), set.getString("fax"), set
							.getString("email"), LieferantDAO.getInstance()
							.getLieferantById(set.getLong("lieferant_fk")), set
							.getString(BEZEICHNUNG)));
		}
		return list;
	}

	/**
	 * Die Methode getAnsprechpartnerByName liefert eins bis mehrere Ergebnisse
	 * zurück bei der Suche nach einem Ansprechpartner in der Datenbank.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Ansprechpartner> getAnsprechpartnerByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();

		ResultSet set = getManaged(GET_ANSPRECHPARTNER_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), set.getString(NAME),
					set.getString(TELEFON), set.getString(HANDY), set
							.getString(FAX), set.getString(EMAIL), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong(LIEFERANT_FK)), set
							.getString(BEZEICHNUNG)));
		}

		return list;
	}

	/**
	 * Die Methode getAnsprechpartnerById liefert eins Ergebnisse zurück bei der
	 * Suche nach einem Ansprechpartner in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Ansprechpartner getAnsprechpartnerById(Long id)
			throws ConnectException, DAOException, SQLException {

		Ansprechpartner ansprechpartner = null;
		ResultSet set = getManaged(MessageFormat.format(
				GET_ANSPRECHPARTNER_BY_ID, id));

		while (set.next()) {
			ansprechpartner = new Ansprechpartner(set.getLong(ID),
					set.getString(NAME), set.getString(TELEFON),
					set.getString(HANDY), set.getString(FAX),
					set.getString(EMAIL), LieferantDAO.getInstance()
							.getLieferantById(set.getLong(LIEFERANT_FK)),
					set.getString(BEZEICHNUNG));
		}

		return ansprechpartner;
	}

	/**
	 * Die Methode erzeugt einen Ansprechpartner in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createAnsprechpartner(Ansprechpartner ansprechpartner)
			throws ConnectException, DAOException, SQLException {
		String INSERTQUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ TELEFON + "," + HANDY + "," + FAX + "," + EMAIL + ","
				+ LIEFERANT_FK + "," + BEZEICHNUNG + ")" + "VALUES" + "('"
				+ ansprechpartner.getName() + "','"
				+ ansprechpartner.getTelefon() + "','"
				+ ansprechpartner.getHandy() + "','" + ansprechpartner.getFax()
				+ "','" + ansprechpartner.getEmail() + "','"
				+ ansprechpartner.getLieferant().getId() + "','"
				+ ansprechpartner.getBezeichnung() + "')";
		this.putManaged(INSERTQUERY);
	}

	/**
	 * Die Methode aktualisiert einen Ansprechpartner in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateAnsprechpartner(Ansprechpartner ansprechpartner)
			throws ConnectException, DAOException, SQLException {
		String UPDATEQUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ ansprechpartner.getName() + "'," + TELEFON + "='"
				+ ansprechpartner.getTelefon() + "'," + HANDY + "='"
				+ ansprechpartner.getHandy() + "'," + FAX + "='"
				+ ansprechpartner.getFax() + "'," + EMAIL + "='"
				+ ansprechpartner.getEmail() + "'," + LIEFERANT_FK + "='"
				+ ansprechpartner.getLieferant().getId() + "',"
				+ BEZEICHNUNG + "='" + ansprechpartner.getBezeichnung() + "'"
				+ " WHERE " + ID + "='" + ansprechpartner.getId() + "'";
		this.putManaged(UPDATEQUERY);
	}

	/**
	 * Die Methode löscht einen Ansprechpartner in der Datenbank.
	 * 
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteAnsprechpartner(Long id) throws ConnectException,
			DAOException, SQLException {

		if (id == null) {
			throw new NullPointerException("kein Ansprechpartner übergeben");
		}
		putManaged(MessageFormat.format(DELETE_ANSPRECHPARTNER, id));
	}

	/**
	 * Die Methode gibt alle Ansprechpartner für einen Lieferanten zurück.
	 * 
	 * @param lieferant
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Ansprechpartner> getAnsprechpartnerByLieferant(
			Lieferant lieferant) throws ConnectException, DAOException,
			SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();

		ResultSet set = getManaged(GET_ANSPRECHPARTNER_BY_LIEFERANT
				+ lieferant.getId());

		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), set.getString(NAME),
					set.getString(TELEFON), set.getString(HANDY), set
							.getString(FAX), set.getString(EMAIL), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong(LIEFERANT_FK)), set
							.getString(BEZEICHNUNG)));
		}

		return list;
	}
}

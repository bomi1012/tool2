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

import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

/**
 * Klasse LieferantDAO. Die Klasse stellt für den Lieferant alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Christian Barth
 * 
 */
public class LieferantDAO extends AbstractDAO {

	private static LieferantDAO instance = null;

	private static final String TABLE = "lieferant";
	private static final String TABLE_ARTIKEL = "artikel";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String KUNDENNUMMER = "kundennummer";
	private static final String BEZEICHNUNG = "bezeichnung";
	private static final String STRASSE = "strasse";
	private static final String PLZ = "plz";
	private static final String ORT = "ort";
	private static final String EMAIL = "email";
	private static final String TELEFON = "telefon";
	private static final String FAX = "fax";
	private static final String NOTIZ = "notiz";
	private static final String MEHRERELIEFERTERMINE = "mehrereliefertermine";

	private static final String GET_ALL_LIEFERANTEN = "SELECT * FROM " + TABLE + " WHERE deaktivieren = 0 ORDER BY name";
	private static final String GET_ALL_LIEFERANTEN_FOR_SHOW = "SELECT * FROM " + TABLE  + " WHERE deaktivieren = 0";
	private static final String GET_LIEFERANT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "= {0}";
	private static final String GET_LIEFERANT_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + " LIKE" + " '%";

	private static final String GET_LIEFERANTEN_BY_ARTIKEL_ID = "SELECT * FROM " + TABLE + " join " + TABLE_ARTIKEL + " on " + TABLE + "." + ID
			+ " = " + TABLE_ARTIKEL + ".lieferant_fk" + " where " + TABLE_ARTIKEL + "." + ID + " = {0} AND " + TABLE + ".deaktivieren = 0";

	private static final String GET_ALL_LIEFERANTEN_WITH_ARTIKEL = "SELECT Distinct lieferant.id, "
			+ "lieferant.name, lieferant.kundennummer, lieferant.bezeichnung, lieferant.strasse, lieferant.plz, lieferant.ort, "
			+ "lieferant.email, lieferant.telefon, lieferant.fax, lieferant.notiz, lieferant.mehrereliefertermine FROM lieferant "
			+ "join artikel on lieferant.id = artikel.lieferant_fk";

	public LieferantDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static LieferantDAO getInstance() {
		if (instance == null) {
			instance = new LieferantDAO();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle in der Datenbank befindlichen Lieferanten
	 * sortiert nach dem Namen zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getAllLieferanten() throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_ALL_LIEFERANTEN);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

	/**
	 * Die Methode liefert alle in der Datenbank befindlichen Lieferanten
	 * zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getAllLieferantenForShow() throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_ALL_LIEFERANTEN_FOR_SHOW);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

	/**
	 * Die Methode liefert eins bis mehrere Ergebnisse zurück bei der Suche nach
	 * einem Lieferanten in der Datenbank.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getLieferantenByName(String name) throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_LIEFERANT_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

	/**
	 * Die Methode liefert ein Ergebnisse zurück bei der Suche nach einem
	 * Lieferant in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Lieferant getLieferantById(Long id) throws ConnectException, DAOException, SQLException {

		Lieferant lieferant = null;
		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANT_BY_ID, id));

		while (set.next()) {
			lieferant = new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
					set.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON),
					set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
		}

		return lieferant;
	}

	/**
	 * Die Methode liefert ein Lieferant zu einer ArtikelId zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Lieferant getLieferantByArtikelId(long id) throws ConnectException, DAOException, SQLException {
		Lieferant lieferant = null;
		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANTEN_BY_ARTIKEL_ID, id));
		while (set.next()) {
			lieferant = new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
					set.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON),
					set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
		}
		return lieferant;
	}

	/**
	 * Die Methode erzeugt einen Lieferant in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		String insertquery = "INSERT INTO " + TABLE + "(" + NAME + "," + KUNDENNUMMER + "," + BEZEICHNUNG + "," + STRASSE + "," + PLZ + "," + ORT
				+ "," + EMAIL + "," + TELEFON + "," + FAX + "," + NOTIZ + "," + MEHRERELIEFERTERMINE + ")" + "VALUES" + "('" + lieferant.getName()
				+ "','" + lieferant.getKundennummer() + "','" + lieferant.getBezeichnung() + "','" + lieferant.getStrasse() + "','"
				+ lieferant.getPlz() + "','" + lieferant.getOrt() + "','" + lieferant.getEmail() + "','" + lieferant.getTelefon() + "','"
				+ lieferant.getFax() + "','" + lieferant.getNotiz() + "','" + Util.convertBoolean(lieferant.getMehrereliefertermine()) + "')";
		this.putManaged(insertquery);
	}

	/**
	 * Die Methode aktualisiert einen Lieferant in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		String updatequery = "UPDATE " + TABLE + " SET " + NAME + "='" + lieferant.getName() + "'," + KUNDENNUMMER + "='"
				+ lieferant.getKundennummer() + "'," + BEZEICHNUNG + "='" + lieferant.getBezeichnung() + "'," + STRASSE + "='"
				+ lieferant.getStrasse() + "'," + PLZ + "='" + lieferant.getPlz() + "'," + ORT + "='" + lieferant.getOrt() + "'," + EMAIL + "='"
				+ lieferant.getEmail() + "'," + TELEFON + "='" + lieferant.getTelefon() + "'," + FAX + "='" + lieferant.getFax() + "'," + NOTIZ
				+ "='" + lieferant.getNotiz() + "'," + MEHRERELIEFERTERMINE + "='" + Util.convertBoolean(lieferant.getMehrereliefertermine())
				+ "' WHERE " + ID + "='" + lieferant.getId() + "'";
		this.putManaged(updatequery);
	}
	
	
	public void deaktivierung(Long id, int zahl)  throws ConnectException, DAOException, SQLException{
		String updatequery = "UPDATE " + TABLE + " SET deaktivieren" + " = " +  zahl + " WHERE " + ID + "=" + id;
		this.putManaged(updatequery);
	}

	/**
	 * Die Methode liefert alle Lieferanten die mindestens einen Artikel haben
	 * zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getLieferantenWithArtikel() throws ConnectException, DAOException, SQLException {

		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_ALL_LIEFERANTEN_WITH_ARTIKEL);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

}

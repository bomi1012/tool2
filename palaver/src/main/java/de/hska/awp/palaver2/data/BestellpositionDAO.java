/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.ArtikelDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

/**
 * Klasse BestellungpositionDAO. Die Klasse stellt für die Bestellung alle
 * notwendigen Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */
public class BestellpositionDAO extends AbstractDAO {

	private static BestellpositionDAO instance = null;

	private final static String TABLE = "bestellposition";
	private final static String ID = "id";
	private final static String ARTIKEL_FK = "artikel_fk";
	private final static String BESTELLUNG_FK = "bestellung_fk";
	private final static String DURCHSCHNITT = "durchschnitt";
	private final static String KANTINE = "kantine";
	private final static String GESAMT = "gesamt";
	private final static String FREITAG = "freitag";
	private final static String MONTAG = "montag";
	private final static String GELIEFERT = "geliefert";
	private final static String SUMME = "summe";

	private static final String GET_BESTELLPOSITION_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "= {0}";

	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE + " WHERE " + BESTELLUNG_FK + "= {0}";
	private static final String DELETE_BESTELLPOSITION = "DELETE FROM " + TABLE + " WHERE id = {0}";

	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
	private final static String GET_KATEGORIE_BY_ID = "SELECT * FROM kategorie WHERE id = {0}";
	private final static String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM mengeneinheit WHERE id = {0}";

	private final static String LIEFERANT_FK = "lieferant_fk";
	private final static String DATUM = "datum";
	private final static String LIEFERDATUM = "lieferdatum";
	private final static String LIEFERDATUM2 = "lieferdatum2";
	private final static String BESTELLT = "bestellt";

	private final static String GET_BESTELLUNG_BY_ID = "SELECT * FROM bestellung WHERE " + ID + "= {0}";

	public BestellpositionDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static BestellpositionDAO getInstance() {
		if (instance == null) {
			instance = new BestellpositionDAO();
		}
		return instance;
	}

	/**
	 * Die Methode liefert eine Bestellposition zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Bestellposition getBestellpositionById(Long id) throws ConnectException, DAOException, SQLException {

		Bestellposition bp = null;
		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITION_BY_ID, id));

		while (set.next()) {
			bp = new Bestellposition(set.getLong(ID), ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)), BestellungDAO.getInstance()
					.getBestellungById(set.getLong(BESTELLUNG_FK)), set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT),
					set.getInt(FREITAG), set.getInt(MONTAG), set.getBoolean(GELIEFERT), set.getDouble(SUMME));
		}

		return bp;
	}

	/**
	 * Die Methode liefert alle Bestellpositionen zur einer Bestellung zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellposition> getBestellpositionenByBestellungId(Long id) throws ConnectException, DAOException, SQLException {
		List<Bestellposition> list = new ArrayList<Bestellposition>();

		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));
		openConnection();
		while (set.next()) {
			list.add(new Bestellposition(set.getLong(ID), getArtikelById(set.getLong(ARTIKEL_FK)), getBestellungByIdWithoutBP(set
					.getLong(BESTELLUNG_FK)), set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT), set.getInt(FREITAG), set
					.getInt(MONTAG), set.getBoolean(GELIEFERT), set.getDouble(SUMME)));
		}
		closeConnection();
		return list;
	}

	/**
	 * Die Methode erzeugt eine Bestellposition in der Datenbank.
	 * 
	 * @param bestellposition
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException, SQLException, ParseException {
		String INSERTQUERY = "INSERT INTO " + TABLE + "(" + ARTIKEL_FK + "," + BESTELLUNG_FK + "," + DURCHSCHNITT + "," + KANTINE + "," + GESAMT
				+ "," + FREITAG + "," + MONTAG + "," + GELIEFERT + ")" + "VALUES" + "('" + bestellposition.getArtikel().getId() + "','"
				+ bestellposition.getBestellung().getId() + "','" + bestellposition.getDurchschnitt() + "','" + bestellposition.getKantine() + "','"
				+ bestellposition.getGesamt() + "','" + bestellposition.getFreitag() + "','" + bestellposition.getMontag() + "','"
				+ Util.convertBoolean(bestellposition.isGeliefert()) + "')";
		this.putManaged(INSERTQUERY);
	}

	/**
	 * Die Methode aktualisiert eine Bestellposition in der Datenbank.
	 * 
	 * @param bestellposition
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException, SQLException {
		String UPDATEQUERY = "UPDATE " + TABLE + " SET " + ARTIKEL_FK + "='" + bestellposition.getArtikel().getId() + "'," + BESTELLUNG_FK + "='"
				+ bestellposition.getBestellung().getId() + "'," + DURCHSCHNITT + "='" + bestellposition.getDurchschnitt() + "'," + KANTINE + "='"
				+ bestellposition.getKantine() + "'," + GESAMT + "='" + bestellposition.getGesamt() + "'," + FREITAG + "='"
				+ bestellposition.getFreitag() + "'," + MONTAG + "='" + bestellposition.getMontag() + "'," + GELIEFERT + "='"
				+ Util.convertBoolean(bestellposition.isGeliefert()) + "' WHERE " + ID + "='" + bestellposition.getId() + "'";
		this.putManaged(UPDATEQUERY);
	}

	/**
	 * Die Methode löscht eine Bestellposition in der Datenbank.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteBestellposition(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			throw new NullPointerException("kein ID übergeben");
		}
		putManaged(MessageFormat.format(DELETE_BESTELLPOSITION, id));

	}

	/**
	 * Die Methode liefert einen Artikel zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException {
		Artikel result = null;

		ResultSet set = getMany(MessageFormat.format(GET_ARTIKEL_BY_ID, id));

		while (set.next()) {
//			result = new Artikel(set.getLong("id"), getMengeneinheitById(set.getLong("mengeneinheit_fk")),
//					getKategorieById(set.getLong("kategorie_fk")), getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"),
//					set.getString("name"), set.getDouble("bestellgroesse"), set.getFloat("preis"), 
//					set.getBoolean("standard"), set.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"),
//					set.getString("notiz"));
		}
		return result;
	}

	/**
	 * Die Methode liefert einen Lieferant zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Lieferant getLieferantById(Long id) throws SQLException {
		Lieferant lieferant = null;
		ResultSet set = get(MessageFormat.format(GET_LIEFERANT_BY_ID, id));

		while (set.next()) {
			lieferant = new Lieferant(set.getLong("id"), set.getString("name"), set.getString("kundennummer"), set.getString("bezeichnung"),
					set.getString("strasse"), set.getString("plz"), set.getString("ort"), set.getString("email"), set.getString("telefon"),
					set.getString("fax"), set.getString("notiz"), set.getBoolean("mehrereliefertermine"));
		}

		return lieferant;
	}

	/**
	 * Die Methode liefert eine Kategorie zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Kategorie getKategorieById(Long id) throws ConnectException, DAOException, SQLException {
		Kategorie kategorie = null;
		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
		while (set.next()) {
			kategorie = new Kategorie(set.getLong("id"), set.getString("name"));
		}
		return kategorie;
	}

	/**
	 * Die Methode liefert eine Mengeneinheit zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException {

		Mengeneinheit me = null;
		ResultSet set = get(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));

		while (set.next()) {
			me = new Mengeneinheit(set.getLong("id"), set.getString("name"), set.getString("kurz"));
		}

		return me;
	}

	/**
	 * Die Methode liefert eine Bestellung ohne Bestellpositionen zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Bestellung getBestellungByIdWithoutBP(Long id) throws ConnectException, DAOException, SQLException {

		Bestellung bestellung = null;
		ResultSet set = getMany(MessageFormat.format(GET_BESTELLUNG_BY_ID, id));

		while (set.next()) {
			bestellung = new Bestellung(set.getLong(ID), getLieferantById(set.getLong(LIEFERANT_FK)), set.getDate(DATUM), set.getDate(LIEFERDATUM),
					set.getDate(LIEFERDATUM2), set.getBoolean(BESTELLT));
		}

		return bestellung;
	}
}

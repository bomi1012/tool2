/**
 * Created by Elena W
 */
package de.palaver.alt.dao.bestellverwaltung;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.util.Util;
import de.palaver.alt.domain.bestellverwaltung.Bestellposition;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;


/**
 * Klasse BestellungpositionDAO. Die Klasse stellt f�r die Bestellung alle
 * notwendigen Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */
public class BestellpositionDAO extends AbstractBestellverwaltungDAO {

	private static BestellpositionDAO instance = null;

	private final static String GET_BESTELLPOSITIONEN_BY_BESTELLUNG_ID = "SELECT * FROM " + TABLE_BP + " WHERE " +
			FIELD_BESTELLUNG_FK + " = {0}";
	
	private final static String INSERT_QUERY = "INSERT INTO " + TABLE_BP + "(" 
			+ "`" + FIELD_ARTIKEL_FK + "`, " + "`" + FIELD_BESTELLUNG_FK + "`, " + "`" + FIELD_LIEFERMENGE1 + "`, " 
			+ "`" + FIELD_LIEFERMENGE2 + "`, " + "`" + FIELD_STATUS_LT_1 + "`, " + "`" + FIELD_STATUS_LT_2 + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5})";
	
	private final static String DELETE_BY_BESTELLUNG_ID = "DELETE FROM " + TABLE_BP + " WHERE " + 
			FIELD_BESTELLUNG_FK + " = {0}";

	
	
//	private static final String GET_BESTELLPOSITION_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "= {0}";
//
//	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE + " WHERE " + BESTELLUNG_FK + "= {0}";
//	private static final String DELETE_BESTELLPOSITION = "DELETE FROM " + TABLE + " WHERE id = {0}";
//
//	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
//	private final static String GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
//	private final static String GET_KATEGORIE_BY_ID = "SELECT * FROM kategorie WHERE id = {0}";
//	private final static String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM mengeneinheit WHERE id = {0}";



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

	public List<Bestellposition> getBestellpositionenByBestellungId(Long id) throws ConnectException, DAOException, SQLException {
		m_listBestellposition = new ArrayList<Bestellposition>();
		m_set = getManaged(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNG_ID, id));
		while (m_set.next()) {
			m_listBestellposition.add(setBestellposition(m_set));
		}
		return m_listBestellposition;
	}

	public void deleteBestellpositionenByBestellungId(long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_BY_BESTELLUNG_ID, id));			
	}
	
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(				
				INSERT_QUERY, 
				bestellposition.getArtikel().getId(),
				bestellposition.getBestellung().getId(),
				bestellposition.getLiefermenge1(),
				bestellposition.getLiefermenge2(),
				Util.convertBoolean(bestellposition.isStatusLT1()),
				Util.convertBoolean(bestellposition.isStatusLT2())));	
		
	}

	public void updateStatus(Long id, String fieldStatus, boolean value) throws ConnectException, DAOException {
		putManaged("UPDATE " + TABLE_BP + " SET " + fieldStatus + " = " + value + " WHERE " + 
				FIELD_ID + " = " + id);	
		
	}
	
//	/**
//	 * Die Methode liefert eine Bestellposition zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
////	public Bestellposition getBestellpositionById(Long id) throws ConnectException, DAOException, SQLException {
////
////		Bestellposition bp = null;
////		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITION_BY_ID, id));
////
////		while (set.next()) {
////			bp = new Bestellposition(set.getLong(ID), ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)), BestellungDAO.getInstance()
////					.getBestellungById(set.getLong(BESTELLUNG_FK)), set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT),
////					set.getInt(FREITAG), set.getInt(MONTAG), set.getBoolean(GELIEFERT), set.getDouble(SUMME));
////		}
////
////		return bp;
////	}
//
//	/**
//	 * Die Methode liefert alle Bestellpositionen zur einer Bestellung zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public List<Bestellposition> getBestellpositionenByBestellungId(Long id) throws ConnectException, DAOException, SQLException {
//		List<Bestellposition> list = new ArrayList<Bestellposition>();
//
//		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));
//		openConnection();
//		while (set.next()) {
//			list.add(new Bestellposition(set.getLong(ID), getArtikelById(set.getLong(ARTIKEL_FK)), null, set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT), set.getInt(FREITAG), set
//					.getInt(MONTAG), set.getBoolean(GELIEFERT), set.getDouble(SUMME)));
//		}
//		closeConnection();
//		return list;
//	}
//
//	/**
//	 * Die Methode erzeugt eine Bestellposition in der Datenbank.
//	 * 
//	 * @param bestellposition
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	public void createBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException, SQLException, ParseException {
//		String INSERTQUERY = "INSERT INTO " + TABLE + "(" + ARTIKEL_FK + "," + BESTELLUNG_FK + "," + DURCHSCHNITT + "," + KANTINE + "," + GESAMT
//				+ "," + FREITAG + "," + MONTAG + "," + GELIEFERT + ")" + "VALUES" + "('" + bestellposition.getArtikel().getId() + "','"
//				+ bestellposition.getBestellung().getId() + "','" + bestellposition.getDurchschnitt() + "','" + bestellposition.getKantine() + "','"
//				+ bestellposition.getGesamt() + "','" + bestellposition.getFreitag() + "','" + bestellposition.getMontag() + "','"
//				+ Util.convertBoolean(bestellposition.isGeliefert()) + "')";
//		this.putManaged(INSERTQUERY);
//	}
//
//	/**
//	 * Die Methode aktualisiert eine Bestellposition in der Datenbank.
//	 * 
//	 * @param bestellposition
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException, SQLException {
//		String UPDATEQUERY = "UPDATE " + TABLE + " SET " + ARTIKEL_FK + "='" + bestellposition.getArtikel().getId() + "'," + BESTELLUNG_FK + "='"
//				+ bestellposition.getBestellung().getId() + "'," + DURCHSCHNITT + "='" + bestellposition.getDurchschnitt() + "'," + KANTINE + "='"
//				+ bestellposition.getKantine() + "'," + GESAMT + "='" + bestellposition.getGesamt() + "'," + FREITAG + "='"
//				+ bestellposition.getFreitag() + "'," + MONTAG + "='" + bestellposition.getMontag() + "'," + GELIEFERT + "='"
//				+ Util.convertBoolean(bestellposition.isGeliefert()) + "' WHERE " + ID + "='" + bestellposition.getId() + "'";
//		this.putManaged(UPDATEQUERY);
//	}
//
//	/**
//	 * Die Methode l�scht eine Bestellposition in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public void deleteBestellposition(Long id) throws ConnectException, DAOException, SQLException {
//
//		if (id == null) {
//			throw new NullPointerException("kein ID �bergeben");
//		}
//		putManaged(MessageFormat.format(DELETE_BESTELLPOSITION, id));
//
//	}
//
//	/**
//	 * Die Methode liefert einen Artikel zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	private Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException {
//		Artikel result = null;
//
//		ResultSet set = getMany(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
//
//		while (set.next()) {
////			result = new Artikel(set.getLong("id"), getMengeneinheitById(set.getLong("mengeneinheit_fk")),
////					getKategorieById(set.getLong("kategorie_fk")), getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"),
////					set.getString("name"), set.getDouble("bestellgroesse"), set.getFloat("preis"), 
////					set.getBoolean("standard"), set.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"),
////					set.getString("notiz"));
//		}
//		return result;
//	}
//
//	/**
//	 * Die Methode liefert einen Lieferant zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	private Lieferant getLieferantById(Long id) throws SQLException {
//		Lieferant lieferant = null;
//		ResultSet set = get(MessageFormat.format(GET_LIEFERANT_BY_ID, id));
//
//		while (set.next()) {
//			lieferant = new Lieferant(set.getLong("id"), set.getString("name"), set.getString("kundennummer"), set.getString("bezeichnung"),
//					set.getString("strasse"), set.getString("plz"), set.getString("ort"), set.getString("email"), set.getString("telefon"),
//					set.getString("fax"), set.getString("notiz"), set.getBoolean("mehrereliefertermine"));
//		}
//
//		return lieferant;
//	}
//
//	/**
//	 * Die Methode liefert eine Kategorie zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	private Kategorie getKategorieById(Long id) throws ConnectException, DAOException, SQLException {
//		Kategorie kategorie = null;
//		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
//		while (set.next()) {
//			kategorie = new Kategorie(set.getLong("id"), set.getString("name"));
//		}
//		return kategorie;
//	}
//
//	/**
//	 * Die Methode liefert eine Mengeneinheit zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	private Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException {
//
//		Mengeneinheit me = null;
//		ResultSet set = get(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));
//
//		while (set.next()) {
//			me = new Mengeneinheit(set.getLong("id"), set.getString("name"), set.getString("kurz"));
//		}
//
//		return me;
//	}
//
//	/**
//	 * Die Methode liefert eine Bestellung ohne Bestellpositionen zur�ck.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
////	private Bestellung getBestellungByIdWithoutBP(Long id) throws ConnectException, DAOException, SQLException {
////
////		Bestellung bestellung = null;
////		ResultSet set = getMany(MessageFormat.format(GET_BESTELLUNG_BY_ID, id));
////
////		while (set.next()) {
////			bestellung = new Bestellung(set.getLong(ID), getLieferantById(set.getLong(LIEFERANT_FK)), set.getDate(DATUM), set.getDate(LIEFERDATUM),
////					set.getDate(LIEFERDATUM2), set.getBoolean(BESTELLT));
////		}
////
////		return bestellung;
////	}
}

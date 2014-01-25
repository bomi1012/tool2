package de.palaver.dao.person.lieferantenverwaltung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.util.Util;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.person.AdresseService;
import de.palaver.service.person.KontakteService;

public class LieferantDAO extends AbstractDAO {

	private static LieferantDAO instance = null;

	private static final String TABLE = "lieferant";
	private static final String TABLE_ARTIKEL = "artikel";
	private static final String FIELD_LIEFERANTNUMMER = "lieferantnummer";
	private static final String FIELD_BEZEICHNUNG = "bezeichnung";
	private static final String FIELD_NOTIZ = "notiz";
	private static final String FIELD_MEHRERELIEFERTERMINE = "mehrereliefertermine";
	private final static String FIELD_ADRESSE_FK = "adresse_fk";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";
	private final static String ACTIVE = "status = 0";
	

	private static final String GET_LIEFERANT_BY_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_ID + " = {0} AND " + ACTIVE;
	private static final String GET_ALL_ACTIVE_LIEFERANTEN = "SELECT * FROM " + TABLE 
			+ " WHERE " + ACTIVE;
	private static final String GET_LIEFERANTEN_GRUNDBEDARF = "SELECT * FROM " + TABLE + " JOIN  " + TABLE_ARTIKEL 
            + " ON " + TABLE + "." + FIELD_ID + " = " + TABLE_ARTIKEL + ".lieferant_fk"
            + " WHERE " + TABLE_ARTIKEL + ".grundbedarf = {0} AND " + TABLE_ARTIKEL + "." + ACTIVE;

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_LIEFERANTNUMMER + "`, " + "`" + FIELD_BEZEICHNUNG + "`, " 
			+ "`" + FIELD_NOTIZ + "`, " + "`" + FIELD_MEHRERELIEFERTERMINE + "`, " 
			+ "`" + FIELD_ADRESSE_FK + "`, " + "`" + FIELD_KONTAKTE_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5},{6})";

	private Lieferant m_lieferant;
	private ArrayList<Lieferant> m_list;

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

	public Lieferant getActiveLieferantById(long id) throws ConnectException, DAOException, SQLException {
		m_lieferant = null;
		m_set = getManaged(MessageFormat.format(GET_LIEFERANT_BY_ID, id));
		while (m_set.next()) {
			m_lieferant = setLieferant(m_set);
		}
		return m_lieferant;
	}

	public List<Lieferant> getActiveLieferanten() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Lieferant>();
		m_set = getManaged(GET_ALL_ACTIVE_LIEFERANTEN);
		while (m_set.next()) {
			m_list.add(setLieferant(m_set));
		}		
		return m_list;
	}

	public List<Lieferant> getLieferantenByGrundbedarf(boolean hatGrundbedarf) throws SQLException, ConnectException, DAOException {
	m_list = new ArrayList<Lieferant>();
	m_set = getManaged(MessageFormat.format(GET_LIEFERANTEN_GRUNDBEDARF, hatGrundbedarf));
	while (m_set.next()) {
		m_list.add(setLieferant(m_set));
	}
	return m_list;
}
	
	
	
	private Lieferant setLieferant(ResultSet set) throws SQLException, ConnectException, DAOException {
		return 	new Lieferant(set.getLong(FIELD_ID), 
				set.getString(FIELD_NAME), 
				set.getString(FIELD_LIEFERANTNUMMER),
				set.getString(FIELD_BEZEICHNUNG),
				set.getBoolean(FIELD_MEHRERELIEFERTERMINE),
				set.getString(FIELD_NOTIZ),
				AdresseService.getInstance().getAdresseById(set.getLong(FIELD_ADRESSE_FK)),
				KontakteService.getInstance().getKontakteById(set.getLong(FIELD_KONTAKTE_FK)));	
	}

	public Long createLieferant(Lieferant lieferant) throws ConnectException, DAOException {
		String kontakt = null;
		String adresse = null;
		if (lieferant.getKontakte() != null) {
			kontakt = String.valueOf(lieferant.getKontakte().getId());
		}
		if (lieferant.getAdresse() != null) {
			adresse = String.valueOf(lieferant.getAdresse().getId());
		}
		
		return insert(MessageFormat.format(INSERT_QUERY, 
				"'" + lieferant.getName() + "'",
				"'" + lieferant.getLieferantnummer() + "'",
				"'" + lieferant.getBezeichnung() + "'",
				"'" + lieferant.getNotiz() + "'",
				Util.convertBoolean(lieferant.isMehrereliefertermine()),
				adresse, kontakt));
	}



	


//
//	/**
//	 * Die Methode liefert alle in der Datenbank befindlichen Lieferanten
//	 * zurück.
//	 * 
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public List<Lieferant> getAllLieferantenForShow() throws ConnectException, DAOException, SQLException {
//		List<Lieferant> list = new ArrayList<Lieferant>();
//
//		ResultSet set = getManaged(GET_ALL_LIEFERANTEN_FOR_SHOW);
//
//		while (set.next()) {
//			list.add(new Lieferant(set.getLong(FIELD_ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
//					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
//					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
//		}
//
//		return list;
//	}
//
//	/**
//	 * Die Methode liefert eins bis mehrere Ergebnisse zurück bei der Suche nach
//	 * einem Lieferanten in der Datenbank.
//	 * 
//	 * @param name
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public List<Lieferant> getLieferantenByName(String name) throws ConnectException, DAOException, SQLException {
//		List<Lieferant> list = new ArrayList<Lieferant>();
//
//		ResultSet set = getManaged(GET_LIEFERANT_BY_NAME + name + "%'");
//
//		while (set.next()) {
//			list.add(new Lieferant(set.getLong(FIELD_ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
//					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
//					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
//		}
//
//		return list;
//	}
//
//	/**
//	 * Die Methode liefert ein Ergebnisse zurück bei der Suche nach einem
//	 * Lieferant in der Datenbank.
//	 * 
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public Lieferant getLieferantById(Long id) throws ConnectException, DAOException, SQLException {
//
//		Lieferant lieferant = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANT_BY_ID, id));
//
//		while (set.next()) {
//			lieferant = new Lieferant(set.getLong(FIELD_ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
//					set.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON),
//					set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
//		}
//
//		return lieferant;
//	}
//
//	/**
//	 * Die Methode liefert ein Lieferant zu einer ArtikelId zurück.
//	 * 
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public Lieferant getLieferantByArtikelId(long id) throws ConnectException, DAOException, SQLException {
//		Lieferant lieferant = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANTEN_BY_ARTIKEL_ID, id));
//		while (set.next()) {
//			lieferant = new Lieferant(set.getLong(FIELD_ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
//					set.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON),
//					set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
//		}
//		return lieferant;
//	}
//
//	/**
//	 * Die Methode erzeugt einen Lieferant in der Datenbank.
//	 * 
//	 * @param ansprechpartner
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public void createLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
//		String insertquery = "INSERT INTO " + TABLE + "(" + NAME + "," + KUNDENNUMMER + "," + BEZEICHNUNG + "," + STRASSE + "," + PLZ + "," + ORT
//				+ "," + EMAIL + "," + TELEFON + "," + FAX + "," + NOTIZ + "," + MEHRERELIEFERTERMINE + ")" + "VALUES" + "('" + lieferant.getName()
//				+ "','" + lieferant.getKundennummer() + "','" + lieferant.getBezeichnung() + "','" + lieferant.getStrasse() + "','"
//				+ lieferant.getPlz() + "','" + lieferant.getOrt() + "','" + lieferant.getEmail() + "','" + lieferant.getTelefon() + "','"
//				+ lieferant.getFax() + "','" + lieferant.getNotiz() + "','" + Util.convertBoolean(lieferant.getMehrereliefertermine()) + "')";
//		this.putManaged(insertquery);
//	}
//
//	/**
//	 * Die Methode aktualisiert einen Lieferant in der Datenbank.
//	 * 
//	 * @param ansprechpartner
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public void updateLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
//		String updatequery = "UPDATE " + TABLE + " SET " + NAME + "='" + lieferant.getName() + "'," + KUNDENNUMMER + "='"
//				+ lieferant.getKundennummer() + "'," + BEZEICHNUNG + "='" + lieferant.getBezeichnung() + "'," + STRASSE + "='"
//				+ lieferant.getStrasse() + "'," + PLZ + "='" + lieferant.getPlz() + "'," + ORT + "='" + lieferant.getOrt() + "'," + EMAIL + "='"
//				+ lieferant.getEmail() + "'," + TELEFON + "='" + lieferant.getTelefon() + "'," + FAX + "='" + lieferant.getFax() + "'," + NOTIZ
//				+ "='" + lieferant.getNotiz() + "'," + MEHRERELIEFERTERMINE + "='" + Util.convertBoolean(lieferant.getMehrereliefertermine())
//				+ "' WHERE " + FIELD_ID + "='" + lieferant.getId() + "'";
//		this.putManaged(updatequery);
//	}
//	
//	
//	public void deaktivierung(Long id, int zahl)  throws ConnectException, DAOException, SQLException{
//		String updatequery = "UPDATE " + TABLE + " SET deaktivieren" + " = " +  zahl + " WHERE " + FIELD_ID + "=" + id;
//		this.putManaged(updatequery);
//	}
//
//	/**
//	 * Die Methode liefert alle Lieferanten die mindestens einen Artikel haben
//	 * zurück.
//	 * 
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public List<Lieferant> getLieferantenWithArtikel() throws ConnectException, DAOException, SQLException {
//
//		List<Lieferant> list = new ArrayList<Lieferant>();
//
//		ResultSet set = getManaged(GET_ALL_LIEFERANTEN_WITH_ARTIKEL);
//
//		while (set.next()) {
//			list.add(new Lieferant(set.getLong(FIELD_ID), set.getString(NAME), set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
//					.getString(STRASSE), set.getString(PLZ), set.getString(ORT), set.getString(EMAIL), set.getString(TELEFON), set.getString(FAX),
//					set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
//		}
//
//		return list;
//	}
//


}

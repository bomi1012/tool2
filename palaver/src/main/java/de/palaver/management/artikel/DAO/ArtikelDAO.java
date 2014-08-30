/**
 * 	Sebastian Walz
 */
package de.palaver.management.artikel.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.util.Util;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.DAO.SupplierDAO;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * Klasse ArtikelDAO. Die Klasse stellt für den Artikel alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 */
public class ArtikelDAO extends AbstractDAO {
	private static ArtikelDAO instance = null;
	private final static String TABLE = "artikel";
	private final static String FIELD_MENGENEINHEIT_FK = "mengeneinheit_fk";
	private final static String FIELD_KATEGORIE_FK = "kategorie_fk";
	private final static String FIELD_LAGERORT_FK = "lagerort_fk";
	private final static String FIELD_LIEFERANT_FK = "lieferant_fk";
	private final static String FIELD_ARTIKELNUMMER = "artikelnr";
	private final static String FIELD_BESTELLGROESSE = "bestellgroesse";
	private final static String FIELD_PREIS = "preis";
	private final static String FIELD_DURCHSCHNITT_LT_1 = "durchschnittLT1";
	private final static String FIELD_DURCHSCHNITT_LT_2 = "durchschnittLT2";
	private final static String FIELD_NOTIZ = "notiz";
	private final static String FIELD_STANDARD = "standard";
	private final static String FIELD_GRUNDBEDARF = "grundbedarf";
	private final static String FIELD_FUER_REZEPT = "fuerRezepte";
	private final static String ACTIVE_AND = " status = 0";	
	
	private String number = null;
	private String notiz = null;
	
	private final static String TABLE_WITH_RELATIONS = "SELECT " +
			"a.id, a.name, a.artikelnr, a.bestellgroesse, a.preis, " +
			"a.standard, a.grundbedarf, a.fuerRezepte, a.durchschnittLT1," +
			"a.durchschnittLT2, a.notiz ,k.id, k.name, lo.id, lo.name, li.id, li.name, " +
			"me.id, me.name " +
			"FROM " + TABLE + " a, kategorie k, lagerort lo, lieferant li, mengeneinheit me " +
			"WHERE a.mengeneinheit_fk = me.id " +
			"AND a.kategorie_fk = k.id " +
			"AND a.lagerort_fk = lo.id " +
			"AND a.lieferant_fk = li.id " +
			"AND a.status = 0";
	
	
	private final static String GET_ALL_ACTIVE_ARTIKLES = TABLE_WITH_RELATIONS;
	
	///OLD
	private final static String GET_ACTIVE_ARTIKLES_BY_LIEFERANT_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} AND " + ACTIVE_AND + " ORDER BY " + FIELD_NAME;
	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_ID + " = {0}";
	private final static String GET_ACTIVE_ARTIKELS_BY_NAME = "SELECT * FROM" + TABLE 
			+ " WHERE " + FIELD_NAME + " = {0}" + ACTIVE_AND;
	private final static String GET_ACTIVE_ARTIKELS_BY_GRUNDBEDARF = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_GRUNDBEDARF + " = 1 AND " + ACTIVE_AND;
	private final static String GET_ACTIVE_ARTIKELS_BY_STANDARD = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_STANDARD + " = 1" + ACTIVE_AND;
	private final static String GET_ACTIVE_ARTIKEL_BY_KATEGORIE = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_KATEGORIE_FK + " = {0} AND " + ACTIVE_AND;
	private final static String GET_ACTIVE_GRUNDBEDARF_BY_LIEFERANT_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} AND " + ACTIVE_AND
			+ " AND " + FIELD_GRUNDBEDARF + " = 1";
	
	
	private final static String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_ARTIKELNUMMER + "`, " + "`" + FIELD_NAME + "`, " + "`" + FIELD_BESTELLGROESSE + "`, " 
			+ "`" + FIELD_MENGENEINHEIT_FK + "`, " + "`" + FIELD_PREIS + "`, " + "`" + FIELD_LIEFERANT_FK + "`, "
			+ "`" + FIELD_KATEGORIE_FK + "`, " + "`" + FIELD_STANDARD + "`, " + "`" + FIELD_GRUNDBEDARF + "`, " 
			+ "`" + FIELD_DURCHSCHNITT_LT_1 + "`, " + "`" + FIELD_DURCHSCHNITT_LT_2 + "`, " 
			+ "`" + FIELD_NOTIZ + "`, " + "`" + FIELD_FUER_REZEPT + "`, " + "`" + FIELD_LAGERORT_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13})";
	
	private List<Artikel> m_list;
	private Artikel m_artikel;
	
	public ArtikelDAO() {
		super();
	}
	
	/**
	 * @return instance
	 */
	public static ArtikelDAO getInstance() {
		if (instance == null) {
			instance = new ArtikelDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllArtikel liefert aktive Artikeln zurück.
	 */
	public List<Artikel> getActiveArtikeln() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ALL_ACTIVE_ARTIKLES);
		while (m_set.next()) {
			m_list.add(setArtikelRelations(m_set));
		}		
		return m_list;
	}

	/**
	 * Die Methode getAllArtikelByLieferantId liefert ein Ergebniss zurück bei
	 * der Suche nach einem Artikel anhang der LieferantId in der Datenbank.
	 */
	public List<Artikel> getActiveArtikelByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_ARTIKLES_BY_LIEFERANT_ID, id));
		while (m_set.next()) {
			m_list.add(setArtikel(m_set));
		}
		return m_list;
	}
	
	public List<Artikel> getActiveArtikelnByKategorieId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_ARTIKEL_BY_KATEGORIE, id));
		while (m_set.next()) {
			m_list.add(setArtikel(m_set));
		}
		return m_list;
	}

	/**
	 * Die Methode getArtikelById liefert eins Ergebniss zurück bei der Suche
	 * nach einem Artikel in der Datenbank.
	 */
	public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException {
		m_artikel = null;
		m_set = getManaged(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
		while (m_set.next()) {
			m_artikel = setArtikel(m_set);
		}
		return m_artikel;
	}

	/**
	 * Die Methode getArtikelByName liefert eins bis mehrere Ergebnisse zurück
	 * bei der Suche nach einem Artikel in der Datenbank.
	 */
	public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_ARTIKELS_BY_NAME, name));
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		return list;
	}

	/**
	 * Die Methode liefert alle Artikel zurück die Grundbedarf sind. Als
	 * Grundbedarf gilt z.B. Salami für die Belegte Brötchen auf der Menükarte.
	 */
	public List<Artikel> getArtikelByGrundbedarf() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ACTIVE_ARTIKELS_BY_GRUNDBEDARF);
		while (m_set.next()) {
			m_list.add(setArtikel(m_set));
		}
		return m_list;
	}

	/**
	 * Die Methode liefert alle Artikel zurück die Standardbedarf sind. Als
	 * Standardbedarf gilt z.B. Pfeffer oder Salz.
	 */
	public List<Artikel> getArtikelByStandard() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ACTIVE_ARTIKELS_BY_STANDARD);
		while (m_set.next()) {
			m_list.add(setArtikel(m_set));
		}
		return m_list;
	}
	
	public void createArtikel(Artikel artikel) throws ConnectException, DAOException {
		if (artikel.getArtikelnr() != null) { number = "'" + artikel.getArtikelnr() + "'"; }
		if (artikel.getNotiz() != null) { notiz = "'" + artikel.getNotiz() + "'"; }
		
				putManaged(MessageFormat.format(				
				INSERT_QUERY, 
				number, "'" +artikel.getName() + "'", 
				artikel.getBestellgroesse(),
				artikel.getMengeneinheit().getId(),
				artikel.getPreis() ,
				artikel.getLieferant().getId(),
				artikel.getKategorie().getId(),
				Util.convertBoolean(artikel.isStandard()),
				Util.convertBoolean(artikel.isGrundbedarf()) ,
				artikel.getDurchschnittLT1(),
				artikel.getDurchschnittLT2(), notiz,
				Util.convertBoolean(artikel.isFuerRezept()),
				artikel.getLagerort().getId()));				
	}
	
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException {
		if (artikel.getArtikelnr() != null) { number = "'" + artikel.getArtikelnr() + "'"; }
		if (artikel.getNotiz() != null) { notiz = "'" + artikel.getNotiz() + "'"; }
		
		putManaged("UPDATE artikel SET " +
				"`artikelnr` = " + number + "," 
				+ "`name` = '" + artikel.getName() + "',"
				+ "`bestellgroesse` = " + artikel.getBestellgroesse() + "," 
				+ "`mengeneinheit_fk` = " + artikel.getMengeneinheit().getId() + ","
				+ "`preis` = " + artikel.getPreis() + "," 
				+ "`lieferant_fk` = " + artikel.getLieferant().getId() + "," 	
				+ "`kategorie_fk` = " + artikel.getKategorie().getId() + "," 
				+ "`standard` = " + Util.convertBoolean(artikel.isStandard()) + "," 
				+ "`grundbedarf` = " + Util.convertBoolean(artikel.isGrundbedarf()) + ","
				+ "`durchschnittLT1` = " + artikel.getDurchschnittLT1() + "," 
				+ "`durchschnittLT2` = " + artikel.getDurchschnittLT2() + "," 
				+ "`FuerRezepte` = " + Util.convertBoolean(artikel.isFuerRezept()) + ","
				+ "`notiz` = " + notiz  + ","
				+ "`lagerort_fk` = " + artikel.getLagerort().getId()
				+ " WHERE id = " + artikel.getId());
	}
		
	/**
	 * a.id,
	 * a.name, 
	 * a.artikelnr,
	 * a.bestellgroesse, 
	 * a.preis, 
	 * a.standard, 
	 * a.grundbedarf, 
	 * a.fuerRezepte, 
	 * a.durchschnittLT1,
	 * a.durchschnittLT2, 
	 * a.notiz ,
	 * 
	 * k.id, 
	 * k.name, 
	 * 
	 * lo.id,  
	 * lo.name, 
	 * 
	 * li.id, 
	 * li.name, 
	 * 
	 * me.id, 
	 * me.name 

	 * @param set
	 * @return
	 * @throws DAOException 
	 * @throws ConnectException 
	 * @throws SQLException 
	 */
	
	private Artikel setArtikelRelations(ResultSet set) throws SQLException, ConnectException, DAOException {
		return 	new Artikel(set.getLong(1), 
				set.getString(2), 
				set.getString(3),
				set.getDouble(4),
				set.getFloat(5),
				set.getBoolean(6),
				set.getBoolean(7),
				set.getBoolean(8),
				set.getInt(9), 
				set.getInt(10), 
				set.getString(11), 
				new Kategorie(set.getLong(12), set.getString(13)),
				new Lagerort(set.getLong(14), set.getString(15)), 
				new Supplier(set.getLong(16), set.getString(17)),
				new Mengeneinheit(set.getLong(18), set.getString(19)));	
	}
	
	private Artikel setArtikel(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Artikel(set.getLong(FIELD_ID), 
				set.getString(FIELD_NAME), 
				MengeneinheitDAO.getInstance().getMengeneinheitById(set.getLong(FIELD_MENGENEINHEIT_FK)), //TODO
				KategorieDAO.getInstance().getKategorieById(set.getLong(FIELD_KATEGORIE_FK)),  //TODO
				SupplierDAO.getInstance().getActiveLieferantById(set.getLong(FIELD_LIEFERANT_FK)),  //TODO
				LagerortDAO.getInstance().getLagerortById(set.getLong(FIELD_LAGERORT_FK)),  //TODO
				set.getString(FIELD_ARTIKELNUMMER), 
				set.getDouble(FIELD_BESTELLGROESSE), 
				set.getFloat(FIELD_PREIS), 
				set.getInt(FIELD_DURCHSCHNITT_LT_1), 
				set.getInt(FIELD_DURCHSCHNITT_LT_2), 
				set.getString(FIELD_NOTIZ), 
				set.getBoolean(FIELD_STANDARD), 
				set.getBoolean(FIELD_GRUNDBEDARF), 
				set.getBoolean(FIELD_FUER_REZEPT));
	}

	public List<Artikel> getGrundbedarfByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_GRUNDBEDARF_BY_LIEFERANT_ID, id));
		openConnection();
		while (m_set.next()) {
			m_list.add(setArtikel(m_set));
		}
		closeConnection();
		return m_list;
	}

	public void removeArtikel(Long artikelId) throws ConnectException, DAOException {
		putManaged("DELETE FROM artikel WHERE id = " + artikelId);
	}	
}

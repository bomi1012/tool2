/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver.artikelverwaltung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.util.Util;

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
	private final static String ACTIVE = "status = 0";
		
	private final static String GET_ALL_ACTIVE_ARTIKLES = "SELECT * FROM " + TABLE 
			+ " WHERE " + ACTIVE;
	private final static String GET_ACTIVE_ARTIKLES_BY_LIEFERANT_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} AND " + ACTIVE + " ORDER BY " + FIELD_NAME;
	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_ID + " = {0}";
	private final static String GET_ACTIVE_ARTIKELS_BY_NAME = "SELECT * FROM" + TABLE 
			+ " WHERE " + FIELD_NAME + " = {0}" + ACTIVE;
	private final static String GET_ACTIVE_ARTIKELS_BY_GRUNDBEDARF = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_GRUNDBEDARF + " = 1 AND " + ACTIVE;
	private final static String GET_ACTIVE_ARTIKELS_BY_STANDARD = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_STANDARD + " = 1" + ACTIVE;
	private final static String GET_ACTIVE_ARTIKEL_BY_KATEGORIE = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_KATEGORIE_FK + " = {0} AND " + ACTIVE;
	private final static String GET_ACTIVE_GRUNDBEDARF_BY_LIEFERANT_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} AND " + ACTIVE
			+ " AND " + FIELD_GRUNDBEDARF + " = 1";
	
	
	private final static String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_ARTIKELNUMMER + "`, " + "`" + FIELD_NAME + "`, " + "`" + FIELD_BESTELLGROESSE + "`, " 
			+ "`" + FIELD_MENGENEINHEIT_FK + "`, " + "`" + FIELD_PREIS + "`, " + "`" + FIELD_LIEFERANT_FK + "`, "
			+ "`" + FIELD_KATEGORIE_FK + "`, " + "`" + FIELD_STANDARD + "`, " + "`" + FIELD_GRUNDBEDARF + "`, " 
			+ "`" + FIELD_DURCHSCHNITT_LT_1 + "`, " + "`" + FIELD_DURCHSCHNITT_LT_2 + "`, " 
			+ "`" + FIELD_NOTIZ + "`, " + "`" + FIELD_FUER_REZEPT + "`, " + "`" + FIELD_LAGERORT_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13})";
	
	private List<Artikel> list;
	private Artikel artikel;
	
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
		list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ALL_ACTIVE_ARTIKLES);
		openConnection();
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}		
		closeConnection();
		return list;
	}

	/**
	 * Die Methode getAllArtikelByLieferantId liefert ein Ergebniss zurück bei
	 * der Suche nach einem Artikel anhang der LieferantId in der Datenbank.
	 */
	public List<Artikel> getActiveArtikelByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_ARTIKLES_BY_LIEFERANT_ID, id));
		openConnection();
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		closeConnection();
		return list;
	}
	
	public List<Artikel> getActiveArtikelnByKategorieId(Long id) throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_ARTIKEL_BY_KATEGORIE, id));
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		return list;
	}

	/**
	 * Die Methode getArtikelById liefert eins Ergebniss zurück bei der Suche
	 * nach einem Artikel in der Datenbank.
	 */
	public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException {
		artikel = null;
		m_set = getManaged(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
		openConnection();
		while (m_set.next()) {
			artikel = setArtikel(m_set);
		}
		closeConnection();
		return artikel;
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
		list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ACTIVE_ARTIKELS_BY_GRUNDBEDARF);
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		return list;
	}

	/**
	 * Die Methode liefert alle Artikel zurück die Standardbedarf sind. Als
	 * Standardbedarf gilt z.B. Pfeffer oder Salz.
	 */
	public List<Artikel> getArtikelByStandard() throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Artikel>();
		m_set = getManaged(GET_ACTIVE_ARTIKELS_BY_STANDARD);
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		return list;
	}
	
	public void createArtikel(Artikel artikel) throws ConnectException, DAOException {
				putManaged(MessageFormat.format(				
				INSERT_QUERY, 
				"'" + artikel.getArtikelnr() + "'",
				"'" +artikel.getName() + "'",
				artikel.getBestellgroesse(),
				artikel.getMengeneinheit().getId(),
				artikel.getPreis() ,
				artikel.getLieferant().getId(),
				artikel.getKategorie().getId(),
				Util.convertBoolean(artikel.isStandard()),
				Util.convertBoolean(artikel.isGrundbedarf()) ,
				artikel.getDurchschnittLT1(),
				artikel.getDurchschnittLT2(),
				"'" +artikel.getNotiz() + "'",
				Util.convertBoolean(artikel.isFuerRezept()),
				artikel.getLagerort().getId()));				
	}
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException {
		putManaged("UPDATE artikel SET " +
				"`artikelnr` = '" + artikel.getArtikelnr() + "'," 
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
				+ "`notiz` = '" + artikel.getNotiz()  + "',"
				+ "`lagerort_fk` = " + artikel.getLagerort().getId()
				+ " WHERE id = " + artikel.getId());
	}
		
	public void deaktivirenArtikel(Long id) throws ConnectException, DAOException {
		putManaged("UPDATE artikel SET `status` = " + 1 + " WHERE id = " + id);
	}

	private Artikel setArtikel(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Artikel(set.getLong(FIELD_ID), 
				MengeneinheitDAO.getInstance().getMengeneinheitById(set.getLong(FIELD_MENGENEINHEIT_FK)),
				KategorieDAO.getInstance().getKategorieById(set.getLong(FIELD_KATEGORIE_FK)), 
				LieferantDAO.getInstance().getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), 
				set.getString(FIELD_ARTIKELNUMMER), 
				set.getString(FIELD_NAME), 
				set.getDouble(FIELD_BESTELLGROESSE), 
				set.getFloat(FIELD_PREIS), 
				set.getInt(FIELD_DURCHSCHNITT_LT_1), 
				set.getInt(FIELD_DURCHSCHNITT_LT_2), 
				set.getString(FIELD_NOTIZ), 
				set.getBoolean(FIELD_STANDARD), 
				set.getBoolean(FIELD_GRUNDBEDARF), 
				set.getBoolean(FIELD_FUER_REZEPT),  
				LagerortDAO.getInstance().getLagerortById(set.getLong(FIELD_LAGERORT_FK)));	
	}

	public List<Artikel> getGrundbedarfByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Artikel>();
		m_set = getManaged(MessageFormat.format(GET_ACTIVE_GRUNDBEDARF_BY_LIEFERANT_ID, id));
		openConnection();
		while (m_set.next()) {
			list.add(setArtikel(m_set));
		}
		closeConnection();
		return list;
	}	
}

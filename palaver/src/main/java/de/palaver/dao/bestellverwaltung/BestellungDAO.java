package de.palaver.dao.bestellverwaltung;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.util.Util;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.bestellverwaltung.Bestellung;

/**
 * Klasse BestellungDAO. Die Klasse stellt für die Bestellung alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */
public class BestellungDAO extends AbstractBestellverwaltungDAO {

	private static BestellungDAO instance = null;	
	
	private static final String GET_ALL_BESTELLUNGEN = "SELECT * FROM " + TABLE_B;
	private final static String INSERT_QUERY = "INSERT INTO " + TABLE_B + "(" 
			+ "`" + FIELD_LIEFERANT_FK + "`, " + "`" + FIELD_MITARBEITER_FK + "`, " + "`" + FIELD_LIEFERDATUM1 + "`, " 
			+ "`" + FIELD_LIEFERDATUM2 + "`, " + "`" + FIELD_STATUS + "`, " + "`" + FIELD_KATEGORIE + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5})";
	
	
	public BestellungDAO() {
		super();
	}

	public static BestellungDAO getInstance() {
		if (instance == null) {
			instance = new BestellungDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllBestellungen liefert alle in der Datenbank befindlichen
	 * Bestellungen zurück ohne Bestellpositionen.
	 */
	public List<Bestellung> getAllBestellungen() throws ConnectException, DAOException, SQLException {
		listBestellung = new ArrayList<Bestellung>();
		m_set = getManaged(GET_ALL_BESTELLUNGEN);
		while (m_set.next()) {
			listBestellung.add(setBestellung(m_set));
		}
		return listBestellung;
	}

	public Long createBestellung(Bestellung bestellung) throws ConnectException, DAOException {
		String lieferdatum2;
		if(!bestellung.getLieferant().isMehrereliefertermine()) {
			lieferdatum2 = null;
		} else {
			lieferdatum2 = "'" + bestellung.getLieferdatum2() + "'";
		}
		return insert(MessageFormat.format(				
				INSERT_QUERY, 
				bestellung.getLieferant().getId(),
				bestellung.getMitarbeiter().getId(),
				"'" + bestellung.getLieferdatum1() + "'",
				lieferdatum2,
				Util.convertBoolean(bestellung.getStatus()),
				"'" + bestellung.getKategorie() + "'"));

	}

//	/**
//	 * Die Methode liefert alle nicht bestellten Bestellungen zurück
//	 * 
//	 * @author Christian Barth
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public List<Bestellung> getAllBestellungenNotOrdered() throws ConnectException, DAOException, SQLException {
//		List<Bestellung> list = new ArrayList<Bestellung>();
//		ResultSet set = getManaged(GET_ALL_BESTELLUNGEN_NOT_ORDERED);
//		while (set.next()) {
//			list.add(new Bestellung(set.getLong(FIELD_ID), LieferantDAO.getInstance().getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), set.getDate(FIELD_DATUM), set
//					.getDate(FIELD_LIEFERDATUM1), set.getDate(FIELD_LIEFERDATUM2), set.getBoolean(FIELD_KATEGORIE)));
//		}
//		return list;
//	}
//
//	public List<Bestellung> getBestellungenLTWeeks() throws ConnectException, DAOException, SQLException {
//
//		List<Bestellung> list = new ArrayList<Bestellung>();
//
//		java.util.Date date2 = new java.util.Date();
//		Date date = new Date(date2.getTime());
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.DAY_OF_MONTH, TAGEZURUECK);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		df.format(cal.getTime());
//
//		String GET_BESTELLUNGENLTWEEKS = "select * from bestellung where datum > '" + df.format(cal.getTime()) + "'";
//		System.out.println(GET_BESTELLUNGENLTWEEKS);
//		ResultSet set = getManaged(GET_BESTELLUNGENLTWEEKS);
//		while (set.next()) {
//			list.add(new Bestellung(set.getLong(FIELD_ID), LieferantDAO.getInstance().getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), set.getDate(FIELD_DATUM), set
//					.getDate(FIELD_LIEFERDATUM1), set.getDate(FIELD_LIEFERDATUM2), set.getBoolean(FIELD_KATEGORIE)));
//		}
//		return list;
//	}
//
//	/**
//	 * Die Methode getBestellungById liefert ein Ergebnisse zurück bei der Suche
//	 * nach einer Bestellung in der Datenbank inklusive Bestellpositionen.
//	 * 
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public Bestellung getBestellungById(Long id) throws ConnectException, DAOException, SQLException {
//
//		Bestellung bestellung = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLUNG_BY_ID, id));
//
//		openConnection();
//
//		while (set.next()) {
//			bestellung = new Bestellung(set.getLong(FIELD_ID), getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), set.getDate(FIELD_DATUM), set.getDate(FIELD_LIEFERDATUM1),
//					set.getDate(FIELD_LIEFERDATUM2), getBestellpositionen(set.getLong(FIELD_ID)), set.getBoolean(FIELD_KATEGORIE));
//		}
//		closeConnection();
//
//		return bestellung;
//	}
//
//	/**
//	 * Die Methode getBestellungById liefert ein Ergebnisse zurück bei der Suche
//	 * nach einer Bestellung in der Datenbank ohne Bestellpositionen.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public Bestellung getBestellungByIdWithoutBP(Long id) throws ConnectException, DAOException, SQLException {
//
//		Bestellung bestellung = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLUNG_BY_ID, id));
//
//		openConnection();
//
//		while (set.next()) {
//			bestellung = new Bestellung(set.getLong(FIELD_ID), getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), set.getDate(FIELD_DATUM), set.getDate(FIELD_LIEFERDATUM1),
//					set.getDate(FIELD_LIEFERDATUM2), set.getBoolean(FIELD_KATEGORIE));
//		}
//
//		closeConnection();
//
//		return bestellung;
//	}
//
//	/**
//	 * Die Methode erzeugt eine Bestellung in der Datenbank inklusive den
//	 * Bestellpositionen.
//	 * 
//	 * @author Christian Barth
//	 * 
//	 * @param bestellung
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	public void createBestellung(Bestellung bestellung) throws ConnectException, DAOException, SQLException, ParseException {
//
//		if (bestellung.getBestellpositionen().size() == 0) {
//			return;
//		}
//
//		String insertquery = "INSERT INTO " + TABLE + "(" + FIELD_LIEFERANT_FK + "," + FIELD_DATUM + "," + FIELD_LIEFERDATUM1 + "," + FIELD_LIEFERDATUM2 + "," + FIELD_KATEGORIE
//				+ ")" + "VALUES" + "('" + bestellung.getLieferant().getId() + "','" + bestellung.getDatum() + "','" + bestellung.getLieferdatum()
//				+ "','" + bestellung.getLieferdatum2() + "','" + Util.convertBoolean(bestellung.isBestellt()) + "')";
//		this.putManaged(insertquery);
//
//		List<Bestellung> bestellungen = getAllBestellungen();
//
//		Bestellung bestell = getBestellungById(bestellungen.get(bestellungen.size() - 1).getId());
//		openConnection();
//		for (int i = 0; i < bestellung.getBestellpositionen().size(); i++) {
//
//			bestellung.getBestellpositionen().get(i).setBestellung(bestell);
//			createBestellposition(bestellung.getBestellpositionen().get(i));
//		}
//		closeConnection();
//	}
//
//	/**
//	 * Die Methode erzeugt eine Bestellposition in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param bestellposition
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	private void createBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException, SQLException, ParseException {
//		String insertquery = "INSERT INTO bestellposition(" + ARTIKEL_FK + "," + BESTELLUNG_FK + "," + DURCHSCHNITT + "," + KANTINE + "," + GESAMT
//				+ "," + FREITAG + "," + MONTAG + "," + GELIEFERT + "," + SUMME + ")" + "VALUES" + "('" + bestellposition.getArtikel().getId() + "','"
//				+ bestellposition.getBestellung().getId() + "','" + bestellposition.getDurchschnitt() + "','" + bestellposition.getKantine() + "','"
//				+ bestellposition.getGesamt() + "','" + bestellposition.getFreitag() + "','" + bestellposition.getMontag() + "','"
//				+ Util.convertBoolean(bestellposition.isGeliefert()) + "','"
//				+ bestellposition.getSumme() + "')";
//		this.putMany(insertquery);
//	}
//
//	/**
//	 * Die Methode aktualisiert eine Bestellung in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param bestellung
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	public void updateBestellung(Bestellung bestellung) throws ConnectException, DAOException, SQLException, ParseException {
//		String updatequery = "UPDATE " + TABLE + " SET " + FIELD_LIEFERANT_FK + "='" + bestellung.getLieferant().getId() + "'," + FIELD_DATUM + "='"
//				+ bestellung.getDatum() + "'," + FIELD_LIEFERDATUM1 + "='" + bestellung.getLieferdatum() + "'," + FIELD_LIEFERDATUM2 + "='"
//				+ bestellung.getLieferdatum2() + "'," + FIELD_KATEGORIE + "='" + Util.convertBoolean(bestellung.isBestellt()) + "' WHERE " + FIELD_ID + "='"
//				+ bestellung.getId() + "'";
//		this.putManaged(updatequery);
//
//		List<Bestellposition> bplist = BestellpositionDAO.getInstance().getBestellpositionenByBestellungId(bestellung.getId());
//		List<Bestellposition> bebplist = null;
//		bebplist = bestellung.getBestellpositionen();
//
//		for (int i = 0; i < bplist.size(); i++) {
//			boolean vorhanden = false;
//			for (int z = 0; z < bebplist.size(); z++) {
//				if (bplist.get(i).getArtikel().getId().equals(bebplist.get(z).getArtikel().getId())) {
//					vorhanden = true;
//					bebplist.get(z).setBestellung(bestellung);
//					BestellpositionDAO.getInstance().updateBestellposition(bebplist.get(z));
//					bebplist.remove(bebplist.get(z));
//				}
//
//			}
//			if (vorhanden == false) {
//				BestellpositionDAO.getInstance().deleteBestellposition(bplist.get(i).getId());
//			}
//
//		}
//
//		if (bebplist != null) {
//			for (Bestellposition bp : bebplist) {
//				bp.setBestellung(bestellung);
//				BestellpositionDAO.getInstance().createBestellposition(bp);
//
//			}
//
//		}
//
//	}
//
//	/**
//	 * Die Methode aktualisiert eine Bestellung in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param bestellung
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public void updateBestellungOhneBP(Bestellung bestellung) throws ConnectException, DAOException, SQLException {
//		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_LIEFERANT_FK + "='" + bestellung.getLieferant().getId() + "'," + FIELD_DATUM + "='"
//				+ bestellung.getDatum() + "'," + FIELD_LIEFERDATUM1 + "='" + bestellung.getLieferdatum() + "'," + FIELD_LIEFERDATUM2 + "='"
//				+ bestellung.getLieferdatum2() + "'," + FIELD_KATEGORIE + "='" + Util.convertBoolean(bestellung.isBestellt()) + "' WHERE " + FIELD_ID + "='"
//				+ bestellung.getId() + "'";
//		this.putManaged(UPDATE_QUERY);
//
//	}
//
//	/**
//	 * Die Methode löscht eine Bestellung in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param bestellung
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	public void deleteBestellung(Bestellung bestellung) throws ConnectException, DAOException, SQLException {
//
//		if (bestellung.getId() == null) {
//			throw new NullPointerException("Keine BestellungsId übergeben!");
//		}
//		Bestellung b = null;
//		try {
//			b = BestellungService.getInstance().getBestellungById(bestellung.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		openConnection();
//		if (b.getBestellpositionen().isEmpty() == false) {
//
//			putMany("DELETE FROM bestellposition where bestellung_fk = " + bestellung.getId());
//			putMany(MessageFormat.format(DELETE_BESTELLUNG, b.getId()));
//
//		} else {
//			putMany(MessageFormat.format(DELETE_BESTELLUNG, b.getId()));
//		}
//		closeConnection();
//
//	}
//
//	/**
//	 * Die Methode löscht eine Bestellposition in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	private void deleteBestellposition(Long id) throws ConnectException, DAOException, SQLException {
//
//		if (id == null) {
//			throw new NullPointerException("kein ID übergeben");
//		}
//		putMany(MessageFormat.format(DELETE_BESTELLPOSITION, id));
//
//	}
//
//	/**
//	 * Die Methode liefert einen Lieferanten zurück.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
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
//	 * Die Methode liefert alle Bestellpositionen zur einer Bestellung zurück.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 * @throws ParseException
//	 */
//	private List<Bestellposition> getBestellpositionen(Long id) throws SQLException, ConnectException, DAOException {
//		List<Bestellposition> list = new ArrayList<Bestellposition>();
//
//		ResultSet set = getMany(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));
//
//		while (set.next()) {
//			list.add(new Bestellposition(set.getLong(FIELD_ID), getArtikelById(set.getLong(ARTIKEL_FK)), set.getInt(DURCHSCHNITT), set.getInt(KANTINE),
//					set.getInt(GESAMT), set.getInt(FREITAG), set.getInt(MONTAG), set.getBoolean(GELIEFERT), set.getDouble(SUMME)));
//		}
//		return list;
//	}
//
//	/**
//	 * Die Methode getArtikelById liefert eins Ergebnis zurück bei der Suche
//	 * nach einem Artikel in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
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
//	 * Die Methode getKategorieById liefert eins Ergebnis zurück bei der Suche
//	 * nach einer Kategorie in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	private Kategorie getKategorieById(Long id) throws ConnectException, DAOException, SQLException {
//		Kategorie kategorie = null;
//
//		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
//
//		while (set.next()) {
//			kategorie = new Kategorie(set.getLong("id"), set.getString("name"));
//		}
//		return kategorie;
//	}
//
//	/**
//	 * Die Methode getMengeneinheitById liefert eins Ergebniss zurück bei der
//	 * Suche nach einer Mengeneinheit in der Datenbank.
//	 * 
//	 * @author Christian Barth
//	 * @param id
//	 * @return
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
}

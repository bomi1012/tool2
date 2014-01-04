package de.hska.awp.palaver.bestellverwaltung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.data.MitarbeiterDAO;

public class AbstractBestellungDAO extends AbstractDAO {
	protected static final String TABLE_B = "bestellung";
	protected static final String FIELD_LIEFERANT_FK = "lieferant_fk";
	protected static final String FIELD_MITARBEITER_FK = "mitarbeiter_fk";
	protected static final String FIELD_DATUM = "datum";
	protected static final String FIELD_LIEFERDATUM1 = "lieferdatum1";
	protected static final String FIELD_LIEFERDATUM2 = "lieferdatum2";
	protected static final String FIELD_STATUS = "status";
	protected static final String FIELD_KATEGORIE = "kategorie";
//	private static final String TABLE2 = "bestellposition";
//	private static final String ARTIKEL_FK = "artikel_fk";
//	private static final String BESTELLUNG_FK = "bestellung_fk";
//	private static final String DURCHSCHNITT = "durchschnitt";
//	private static final String KANTINE = "kantine";
//	private static final String GESAMT = "gesamt";
//	private static final String FREITAG = "freitag";
//	private static final String MONTAG = "montag";
//	private static final String GELIEFERT = "geliefert";

//	private static final String GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
//	private static final String GET_KATEGORIE_BY_ID = "SELECT * FROM kategorie WHERE id = {0}";
//	private static final String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM mengeneinheit WHERE id = {0}";
//	private static final String GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
//	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE2 + " WHERE " + BESTELLUNG_FK + "= {0}";

	
//	private static final String GET_BESTELLUNG_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + FIELD_ID + "= {0}";
//	private static final String GET_ALL_BESTELLUNGEN_NOT_ORDERED = "SELECT * FROM " + TABLE + " WHERE " + FIELD_KATEGORIE + "= '0'";
////
//	private static final String DELETE_BESTELLPOSITION = "DELETE FROM " + TABLE2 + " WHERE id = {0}";
//	private static final String DELETE_BESTELLUNG = "DELETE FROM " + TABLE + " WHERE id = {0}";

//	private static final int TAGEZURUECK = -22;
//	private static final String SUMME = "summe";
	protected List<Bestellung> listBestellung;
	
	protected AbstractBestellungDAO() {
		super();
	}
	
	protected Bestellung setBestellung(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Bestellung(
				set.getLong(FIELD_ID), 
				LieferantDAO.getInstance().getLieferantById(set.getLong(FIELD_LIEFERANT_FK)), 
				MitarbeiterDAO.getInstance().getMitarbeiterById(set.getLong(FIELD_MITARBEITER_FK)), 
				set.getDate(FIELD_DATUM), 
				set.getDate(FIELD_LIEFERDATUM1), 
				set.getDate(FIELD_LIEFERDATUM2), 
				BestellpositionDAO.getInstance().getBestellpositionenByBestellungId(set.getLong(FIELD_ID)), 
				set.getBoolean(FIELD_STATUS),
				set.getInt(FIELD_KATEGORIE));	
	}
}

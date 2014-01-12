package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

public class RegelDAO extends AbstractDAO {

	private static RegelDAO instance = null;

	private final static String TABLE = "regel";
	private final static String ID = "id";
	private final static String ZEILEN = "zeile";
	private final static String SPALTEN = "spalte";
	private final static String REGELTYP = "regeltyp";
	private final static String OPERATOR = "operator";
	private final static String KRITERIEN = "kriterien";
	private final static String FEHLERMELDUNG = "fehlermeldung";
	private final static String AKTIV = "aktiv";
	private final static String IGNORIERBAR = "ignorierbar";

	private final String CREATE_REGEL = "INSERT INTO "
			+ TABLE
			+ "(zeile, spalte, regeltyp, operator, kriterien, fehlermeldung, aktiv, ignorierbar) "
			+ "VALUES({0},{1},{2},{3},{4},{5},{6},{7})";
	private static final String GET_ALL_REGELN = "SELECT * FROM " + TABLE;
	private static final String GET_ALL_AKTIV_REGELN = "SELECT * FROM " + TABLE
			+ " WHERE aktiv = 1";
	private static final String GET_REGEL_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE id = {0}";
	private static final String GET_KRITERIEN_BY_REGELID = "SELECT "
			+ KRITERIEN + " FROM " + TABLE + " WHERE id = {0}";
	private static final String DELETE_REGEL = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";

	public static RegelDAO getInstance() {
		if (instance == null) {
			instance = new RegelDAO();
		}
		return instance;
	}

	public List<Regel> getAllRegeln() throws ConnectException, DAOException,
			SQLException {
		List<Regel> list = new ArrayList<Regel>();

		ResultSet set = getManaged(GET_ALL_REGELN);

		while (set.next()) {
			list.add(new Regel(set.getLong("id"), set.getString("regeltyp"),
					set.getString("zeile"), set.getString("spalte"), set
							.getString("operator"), set.getString("kriterien"),
					set.getString("fehlermeldung"), set.getBoolean("aktiv"), set.getBoolean("ignorierbar")));
		}

		return list;
	}

	public List<String> getKriterienByRegelId(Long id) throws ConnectException,
			DAOException, SQLException {
		List<String> list = new ArrayList<String>();

		ResultSet set = getManaged(MessageFormat.format(
				GET_KRITERIEN_BY_REGELID, id));

		while (set.next()) {
			list.add(set.getString("kriterien"));
		}

		return list;

	}

	public List<Regel> getAllAktivRegeln() {

		List<Regel> list = new ArrayList<Regel>();

		ResultSet set;
		try {
			set = getManaged(GET_ALL_AKTIV_REGELN);
			while (set.next()) {
				list.add(new Regel(set.getLong("id"),
						set.getString("regeltyp"), set.getString("zeile"), set
								.getString("spalte"),
						set.getString("operator"), set.getString("kriterien"),
						set.getString("fehlermeldung"), set.getBoolean("aktiv"), set.getBoolean("ignorierbar")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Regel getRegelById(Long id) throws ConnectException, DAOException,
			SQLException {

		Regel regel = null;
		ResultSet set = getManaged(MessageFormat.format(GET_REGEL_BY_ID, id));

		while (set.next()) {
			regel = new Regel(set.getLong(ID), set.getString(ZEILEN),
					set.getString(SPALTEN), set.getString(REGELTYP),
					set.getString(OPERATOR), set.getString(KRITERIEN),
					set.getString(FEHLERMELDUNG), set.getBoolean(AKTIV), set.getBoolean("ignorierbar"));
		}

		return regel;
	}

	public void createRegel(Regel regel) throws ConnectException, DAOException,
			SQLException {
		putManaged(MessageFormat.format(CREATE_REGEL, "'" + regel.getZeile()
				+ "'", "'" + regel.getSpalte() + "'", "'" + regel.getRegeltyp()
				+ "'", "'" + regel.getOperator() + "'",
				"'" + regel.getKriterien() + "'",
				"'" + regel.getFehlermeldung() + "'", regel.getAktiv(), regel.getIgnorierbar()));

	}

	public void deleteRegel(Regel regel) throws ConnectException, DAOException,
			SQLException {

		if (regel.getId() == null) {
			throw new NullPointerException("Die Regel wurde nicht gefunden");
		}
		putManaged(MessageFormat.format(DELETE_REGEL, regel.getId()));
	}

	public void updateRegel(Regel regel) throws ConnectException, DAOException,
			SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + ZEILEN + "='"
				+ regel.getZeile() + "'," + SPALTEN + "='" + regel.getSpalte()
				+ "'," + REGELTYP + "='" + regel.getRegeltyp() + "'," + OPERATOR + "='"
				+ regel.getOperator() + "'," + KRITERIEN + "='"
				+ regel.getKriterien() + "'," + FEHLERMELDUNG + "='"
				+ regel.getFehlermeldung() + "'," + AKTIV + "="
				+ regel.getAktiv() + "," + IGNORIERBAR + "="
						+ regel.getIgnorierbar() +" WHERE " + ID + "=" + regel.getId();
		this.putManaged(UPDATE_QUERY);
	}

}

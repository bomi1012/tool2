package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;

import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * 
 * @author Jasmin Baumgartner
 * 
 */

public class FussnoteKuchenDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String ABKUERZUNG = "abkuerzung";
	private final static String TABLE = "fussnotekuchen";

	private static FussnoteKuchenDAO instance = null;

	FussnoteKuchen fussnotekuchen;

	// SQL-Statements
	private final static String GET_ALL_FUSSNOTEKUCHEN = "SELECT * FROM "
			+ TABLE;
	private final static String GET_FUSSNOTEKUCHEN_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "={0}";
	private final static String GET_FUSSNOTEKUCHEN_BY_NAME = "SELECT * FROM fussnotekuchen WHERE name = {0}";
	private final static String GET_FUSSNOTEKUCHEN_BY_KUCHEN = "Select fussnotekuchen.id, fussnotekuchen.name, fussnotekuchen.abkuerzung from fussnotekuchen JOIN kuchenrezept_has_fussnote On kuchenrezept_has_fussnote.fussnotekuchen_fk = fussnotekuchen.id WHERE kuchenrezept_has_fussnote.kuchenrezept_fk = {0}";

	// Konstruktor
	public FussnoteKuchenDAO() {
		super();
	}

	// Instanz erzeugen
	public static FussnoteKuchenDAO getInstance() {
		if (instance == null) {
			instance = new FussnoteKuchenDAO();
		}

		return instance;
	}

	// Methode, die alle Fussnoten in einer Liste zurückliefert
	public List<FussnoteKuchen> getAllFussnoteKuchen() throws ConnectException,
			DAOException, SQLException {
		List<FussnoteKuchen> list = new ArrayList<FussnoteKuchen>();
		ResultSet set = getManaged(GET_ALL_FUSSNOTEKUCHEN);
		while (set.next()) {
			list.add(new FussnoteKuchen(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG)));
		}
		return list;
	}

	// Methode, die alle Fussnoten zu einem Kuchen über die ID zurückliefert
	public List<FussnoteKuchen> getFussnoteKuchenByKuchen(Long id)
			throws ConnectException, DAOException, SQLException {
		List<FussnoteKuchen> list = new ArrayList<FussnoteKuchen>();
		ResultSet set = getManaged(MessageFormat.format(
				GET_FUSSNOTEKUCHEN_BY_KUCHEN, id));
		while (set.next()) {
			list.add(new FussnoteKuchen(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG)));
		}
		return list;
	}

	// Methode, die eine Fussnote über die ID zurückliefert
	public FussnoteKuchen getFussnoteKuchenById(Long id)
			throws ConnectException, DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(
				GET_FUSSNOTEKUCHEN_BY_ID, id));
		while (set.next()) {
			fussnotekuchen = new FussnoteKuchen(set.getLong(ID),
					set.getString(NAME), set.getString(ABKUERZUNG));
		}
		return fussnotekuchen;
	}

	// Methode, die eine Fussnote über den Namen zurückliefert
	public FussnoteKuchen getFussnoteKuchenByName(String fn)
			throws ConnectException, DAOException, SQLException {
		FussnoteKuchen result = null;
		fn = "'" + fn + "'";

		ResultSet set = getManaged(MessageFormat.format(
				GET_FUSSNOTEKUCHEN_BY_NAME, fn));
		while (set.next()) {
			result = new FussnoteKuchen(set.getLong("id"),
					set.getString("name"), set.getString("abkuerzung"));
		}

		return result;
	}

	// Methode, die eine Fussnote erstellt
	public void createFussnoteKuchen(FussnoteKuchen fussnotekuchen)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name, abkuerzung) VALUES('"
				+ fussnotekuchen.getBezeichnung() + "','"
				+ fussnotekuchen.getAbkuerzung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Fussnote ändert
	public void updateFussnoteKuchen(FussnoteKuchen fussnotekuchen)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ fussnotekuchen.getBezeichnung() + "'," + ABKUERZUNG + "='"
				+ fussnotekuchen.getAbkuerzung() + "'" + " WHERE " + ID + "='"
				+ fussnotekuchen.getId() + "'";
		this.putManaged(UPDATE_QUERY);

	}

}

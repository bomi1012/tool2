package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * @author Michael Marschall
 * 
 */

public class ZubereitungDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "zubereitung";

	private static ZubereitungDAO instance = null;

	Zubereitung zubereitung;

	// SQL-Statements
	private final static String GET_ALL_ZUBEREITUNG = "SELECT * FROM " + TABLE;
	private final static String GET_ZUBEREITUNG_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "={0}";
	private final static String GET_ZUBEREITUNG_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String GET_ZUBEREITUNG_BY_REZEPT = "Select zubereitung.id, zubereitung.name From zubereitung Join rezept_has_zubereitung On zubereitung.id = rezept_has_zubereitung.zubereitung_fk WHERE rezept_has_zubereitung.rezept_fk = {0}";

	// Konstruktor
	public ZubereitungDAO() {
		super();
	}

	// Instanz erzeugen
	public static ZubereitungDAO getInstance() {
		if (instance == null) {
			instance = new ZubereitungDAO();
		}

		return instance;
	}

	// Methode, die alle Zubereitung in einer Liste zurückliefert
	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();
		ResultSet set = getManaged(GET_ALL_ZUBEREITUNG);
		while (set.next()) {
			list.add(new Zubereitung(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Zubereitung eines Rezepts über die ID zurückliefert
	public List<Zubereitung> getZubereitungByRezept(Long id)
			throws ConnectException, DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();
		ResultSet set = getManaged(MessageFormat.format(
				GET_ZUBEREITUNG_BY_REZEPT, id));
		while (set.next()) {
			list.add(new Zubereitung(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Zubereitung über die ID zurückliefert
	public Zubereitung getZubereitungById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_ZUBEREITUNG_BY_ID,
				id));
		while (set.next()) {
			zubereitung = new Zubereitung(set.getLong(ID), set.getString(NAME));
		}
		return zubereitung;
	}

	// Methode, die eine Zubereitung über den Name zurückliefert
	public Zubereitung getZubereitungByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();

		ResultSet set = getManaged(GET_ZUBEREITUNG_BY_NAME + name + "%'");
		while (set.next()) {
			list.add(new Zubereitung(set.getLong(ID), set.getString(NAME)));
		}
		return list.get(0);
	}

	// Methode, die eine Zubereitung erstellt
	public void createZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ zubereitung.getBezeichnung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Zubereitung Ã¤ndert
	public void updateZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ zubereitung.getBezeichnung() + "'" + " WHERE " + ID + "='"
				+ zubereitung.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

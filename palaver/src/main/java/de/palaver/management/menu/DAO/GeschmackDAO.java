package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.menu.Geschmack;

/**
 * 
 * @author Michael Marschall
 * 
 */

public class GeschmackDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "geschmack";
	private final static String INAKTIV = "inaktiv";

	private static GeschmackDAO instance = null;

	Geschmack geschmack;

	// SQL-Statements
	private final static String GET_ALL_GESCHMACK = "SELECT * FROM " + TABLE;
	private final static String GET_GESCHMACK_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private final static String GET_GESCHMACK_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE name = '{0}'";
	private static final String GET_GESCHMACK_BY_REZEPT = "Select geschmack.id, geschmack.name, geschmack.inaktiv from geschmack Join rezept On rezept.geschmack_fk = geschmack.id WHERE rezept.id = {0}";
	private static final String GET_GESCHMACK_BY_MENUE = "Select geschmack.id, geschmack.name, geschmack.inaktiv from geschmack Join menue On menue.geschmack_fk = geschmack.id WHERE menue.id = {0}";

	// Konstruktor
	public GeschmackDAO() {
		super();
	}

	// Instanz erzeugen
	public static GeschmackDAO getInstance() {
		if (instance == null) {
			instance = new GeschmackDAO();
		}

		return instance;
	}

	// Methode, die alle GeschmÃ¤cker in einer Liste zurückliefert
	public List<Geschmack> getAllGeschmack() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_ALL_GESCHMACK);
		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die einen Geschmack zu einem Menü über die ID zurückliefert
	public Geschmack getGeschmackByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_MENUE,
				id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME));
		}
		return geschmack;
	}

	// Methode, die einen Geschmack zu einem Rezept über die ID zurückliefert
	public Geschmack getGeschmackByRezept(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(
				GET_GESCHMACK_BY_REZEPT, id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME));
		}
		return geschmack;
	}

	// Methode, die einen Geschmack über die ID zurückliefert
	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat
				.format(GET_GESCHMACK_BY_ID, id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME));
		}
		return geschmack;
	}

	public Geschmack getGeschmackByName1(String name) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_NAME,
				NAME));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong("id"), null);
		}
		return geschmack;

	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_GESCHMACK_BY_NAME + name + "%'");
		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die einen Geschmack erstellt
	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name, inaktiv) VALUES('" + geschmack.getName()
				+ "' " + ", false)";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die einen Geschmack Ã¤ndert
	public void updateGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "= '"
				+ geschmack.getName() + "' WHERE " + ID + " = "
				+ geschmack.getId() + ";";
		this.putManaged(UPDATE_QUERY);

	}

}
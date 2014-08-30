package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.menu.Fussnote;

/**
 * @author Michael Marschall
 * 
 */
public class FussnoteDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String ABKUERZUNG = "abkuerzung";
	private final static String TABLE = "fussnote";

	private static FussnoteDAO instance = null;

	Fussnote fussnote;

	// SQL-Statements
	private final static String GET_ALL_FUSSNOTE = "SELECT * FROM " + TABLE;
	private final static String GET_FUSSNOTE_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private final static String GET_FUSSNOTE_BY_NAME = "SELECT * FROM " + TABLE
			+ " WHERE name = {0}";
	private final static String GET_FUSSNOTE_BY_MENUE = "Select fussnote.id, fussnote.name, fussnote.abkuerzung from fussnote JOIN menue_has_fussnote On menue_has_fussnote.fussnote_fk = fussnote.id WHERE menue_has_fussnote.menue_fk = {0}";

	// Konstruktor
	public FussnoteDAO() {
		super();
	}

	// Instanz erzeugen
	public static FussnoteDAO getInstance() {
		if (instance == null) {
			instance = new FussnoteDAO();
		}

		return instance;
	}

	// Methode, die alle Fussnoten in einer Liste zurückliefert
	public List<Fussnote> getAllFussnote() throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> list = new ArrayList<Fussnote>();
		ResultSet set = getManaged(GET_ALL_FUSSNOTE);
		while (set.next()) {
			list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
					.getString(ABKUERZUNG)));
		}
		return list;
	}

	// Methode, die alle Fussnoten zu einem Menü über die ID zurückliefert
	public List<Fussnote> getFussnoteByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> list = new ArrayList<Fussnote>();
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_MENUE,
				id));
		while (set.next()) {
			list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
					.getString(ABKUERZUNG)));
		}
		return list;
	}

	// Methode, die eine Fussnote über die ID zurückliefert
	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_ID, id));
		while (set.next()) {
			fussnote = new Fussnote(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG));
		}
		return fussnote;
	}

	// Methode, die eine Fussnote über den Namen zurückliefert
	public Fussnote getFussnoteByName(String fn) throws ConnectException,
			DAOException, SQLException {
		Fussnote result = null;
		fn = "'" + fn + "'";

		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_NAME,
				fn));
		while (set.next()) {
			result = new Fussnote(set.getLong("id"), set.getString("name"),
					set.getString("abkuerzung"));
		}

		return result;
	}

	// Methode, die eine Fussnote erstellt
	public void createFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ ABKUERZUNG + ") VALUES('" + fussnote.getName() + "','"
				+ fussnote.getAbkuerzung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Fussnote Ändert
	public void updateFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ fussnote.getName() + "'," + ABKUERZUNG + "='"
				+ fussnote.getAbkuerzung() + "'" + " WHERE " + ID + "='"
				+ fussnote.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

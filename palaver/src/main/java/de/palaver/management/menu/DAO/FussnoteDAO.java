package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.menu.Fussnote;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class FussnoteDAO extends AbstractDAO {
	
	private static final String FIELD_ABKUERZUNG = "abkuerzung";
	private final static String TABLE = "fussnote";
	private final static String TABLE_MENU_FUSSNOTE = "menue_has_fussnote";

	private static FussnoteDAO instance = null;

	private Fussnote m_fussnote;
	private ArrayList<Fussnote> m_list;

	
	
	
	
	private final static String GET_FUSSNOTE_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + FIELD_ID + "={0}";
	private final static String GET_FUSSNOTE_BY_NAME = "SELECT * FROM " + TABLE
			+ " WHERE name = {0}";
	private final static String GET_FUSSNOTE_BY_MENUE = "SELECT f.* FROM " 
			+ TABLE + " f JOIN " + TABLE_MENU_FUSSNOTE + " mf" 
			+ " ON mf.fussnote_fk = f.id WHERE mf.menue_fk = {0}";

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
	public List<Fussnote> getAllFussnote() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Fussnote>();
		m_set = getManaged("SELECT * FROM " + TABLE);
		while (m_set.next()) {
			m_list.add(setFussnone(m_set));
		}
		return m_list;
	}
	
	public List<Fussnote> getAllFussnotenByMenuId(Long menuId) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Fussnote>();
		m_set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_MENUE, menuId));
		while (m_set.next()) {
			m_list.add(setFussnone(m_set));
		}
		return m_list;
	}
	
	private Fussnote setFussnone(ResultSet set) throws SQLException {
		return new Fussnote(set.getLong(FIELD_ID), set.getString(FIELD_NAME), set.getString(FIELD_ABKUERZUNG));
	}
	

	
	
	
	
	
	/////////////////////////////////////////////////
	
	



	// Methode, die alle Fussnoten zu einem Menü über die ID zurückliefert
	public List<Fussnote> getFussnoteByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> list = new ArrayList<Fussnote>();
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_MENUE,
				id));
		while (set.next()) {
			list.add(new Fussnote(set.getLong(FIELD_ID), set.getString(FIELD_NAME), set
					.getString(FIELD_ABKUERZUNG)));
		}
		return list;
	}

	// Methode, die eine Fussnote über die ID zurückliefert
	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_ID, id));
		while (set.next()) {
			m_fussnote = new Fussnote(set.getLong(FIELD_ID), set.getString(FIELD_NAME),
					set.getString(FIELD_ABKUERZUNG));
		}
		return m_fussnote;
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
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_NAME + ","
				+ FIELD_ABKUERZUNG + ") VALUES('" + fussnote.getName() + "','"
				+ fussnote.getAbkuerzung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Fussnote Ändert
	public void updateFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + "='"
				+ fussnote.getName() + "'," + FIELD_ABKUERZUNG + "='"
				+ fussnote.getAbkuerzung() + "'" + " WHERE " + FIELD_ID + "='"
				+ fussnote.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}


}

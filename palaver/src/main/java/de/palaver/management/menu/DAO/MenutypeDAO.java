package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.menu.Menutype;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class MenutypeDAO extends AbstractDAO {

	private final static String TABLE = "menueart";

	private static final String GET_MENUEART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + FIELD_ID + "={0}";
	private static final String GET_MENUEART_BY_NAME = "SELECT * FROM " + TABLE
			+ " WHERE " + FIELD_NAME + " LIKE" + " '%";
	private static final String GET_MENUEART_BY_MENUE = "Select menueart.id, menueart.name from menueart Join menue On menue.menueart_fk = menueart.id WHERE menue.id = {0}";

	private static MenutypeDAO m_instance = null;

	private Menutype m_menutype;
	private ArrayList<Menutype> m_list;

	public MenutypeDAO() {
		super();
	}

	public static MenutypeDAO getInstance() {
		if (m_instance == null) {
			m_instance = new MenutypeDAO();
		}
		return m_instance;
	}

	public List<Menutype> getAllMenutypes() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Menutype>();
		m_set = getManaged("SELECT * FROM " + TABLE);
		while (m_set.next()) {
			m_list.add(setResult(m_set));
		}
		return m_list;
	}
	
	private Menutype setResult(ResultSet set) throws SQLException {
		return new Menutype(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	// Methode, die eine Menueart über die ID zurückliefert
	public Menutype getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_ID, id));
		while (set.next()) {
			m_menutype = new Menutype(set.getLong("id"), set.getString("name"));
		}
		return m_menutype;
	}

	// Methode, die eine Menueart eines Menue über die ID zurückliefert
	public Menutype getMenueartByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_MENUE,
				id));
		while (set.next()) {
			m_menutype = new Menutype(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
		}
		return m_menutype;
	}

	// Methode, die eine Menueart über den Namen zurückliefert
	public List<Menutype> getMenueartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Menutype> list = new ArrayList<Menutype>();

		ResultSet set = getManaged(GET_MENUEART_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Menutype(set.getLong(FIELD_ID), set.getString(FIELD_NAME)));
		}
		return list;
	}

	// Methode, die eine Menueart erstellt
	public void createMenueart(Menutype menutype) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ menutype.getName() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Menueart Ã¤ndert
	public void updateMenueart(Menutype menutype) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + "='"
				+ menutype.getName() + "'" + " WHERE " + FIELD_ID + "='"
				+ menutype.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

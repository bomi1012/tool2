package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * @author Jasmin Baumgartner
 * 
 */

public class MenueartDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "menueart";

	// SQL-Statements
	private static final String GET_ALL_MENUEART = "SELECT * FROM " + TABLE;
	private static final String GET_MENUEART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private static final String GET_MENUEART_BY_NAME = "SELECT * FROM " + TABLE
			+ " WHERE " + NAME + " LIKE" + " '%";
	private static final String GET_MENUEART_BY_MENUE = "Select menueart.id, menueart.name from menueart Join menue On menue.menueart_fk = menueart.id WHERE menue.id = {0}";

	private static MenueartDAO instance = null;

	Menueart menueart;

	// Konstruktor
	public MenueartDAO() {
		super();
	}

	// Instanz erzeugen
	public static MenueartDAO getInstance() {
		if (instance == null) {
			instance = new MenueartDAO();
		}

		return instance;
	}

	// Methode, die alle Menuearten in einer Liste zurückliefert
	public List<Menueart> getAllMenueart() throws ConnectException,
			DAOException, SQLException {
		List<Menueart> list = new ArrayList<Menueart>();
		ResultSet set = getManaged(GET_ALL_MENUEART);
		while (set.next()) {
			list.add(new Menueart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Menueart über die ID zurückliefert
	public Menueart getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_ID, id));
		while (set.next()) {
			menueart = new Menueart(set.getLong("id"), set.getString("name"));
		}
		return menueart;
	}

	// Methode, die eine Menueart eines Menue über die ID zurückliefert
	public Menueart getMenueartByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_MENUE,
				id));
		while (set.next()) {
			menueart = new Menueart(set.getLong(ID), set.getString(NAME));
		}
		return menueart;
	}

	// Methode, die eine Menueart über den Namen zurückliefert
	public List<Menueart> getMenueartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Menueart> list = new ArrayList<Menueart>();

		ResultSet set = getManaged(GET_MENUEART_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Menueart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Menueart erstellt
	public void createMenueart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ menueart.getBezeichnung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Menueart ändert
	public void updateMenueart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ menueart.getBezeichnung() + "'" + " WHERE " + ID + "='"
				+ menueart.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

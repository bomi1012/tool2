package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.menu.Geschmack;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class GeschmackDAO extends AbstractDAO {

	private final static String TABLE = "geschmack";

	private static GeschmackDAO m_instance = null;

	private Geschmack m_geschmack;
	private ArrayList<Geschmack> m_list;

	// SQL-Statements
	private final static String GET_GESCHMACK_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + FIELD_ID + "={0}";
	private final static String GET_GESCHMACK_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE name = '{0}'";
	private static final String GET_GESCHMACK_BY_REZEPT = "Select geschmack.id, geschmack.name, geschmack.inaktiv from geschmack Join rezept On rezept.geschmack_fk = geschmack.id WHERE rezept.id = {0}";
	private static final String GET_GESCHMACK_BY_MENUE = "Select geschmack.id, geschmack.name, geschmack.inaktiv from geschmack Join menue On menue.geschmack_fk = geschmack.id WHERE menue.id = {0}";

	public GeschmackDAO() {
		super();
	}

	public static GeschmackDAO getInstance() {
		if (m_instance == null) {
			m_instance = new GeschmackDAO();
		}

		return m_instance;
	}

	public List<Geschmack> getAllGeschmack() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Geschmack>();
		m_set = getManaged("SELECT * FROM " + TABLE);
		while (m_set.next()) {
			m_list.add(setResult(m_set));
		}
		return m_list;
	}
	
	private Geschmack setResult(ResultSet set) throws SQLException {
		return new Geschmack(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// Methode, die einen Geschmack zu einem Menü über die ID zurückliefert
	public Geschmack getGeschmackByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_MENUE,
				id));
		while (set.next()) {
			m_geschmack = new Geschmack(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
		}
		return m_geschmack;
	}

	// Methode, die einen Geschmack zu einem Rezept über die ID zurückliefert
	public Geschmack getGeschmackByRezept(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(
				GET_GESCHMACK_BY_REZEPT, id));
		while (set.next()) {
			m_geschmack = new Geschmack(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
		}
		return m_geschmack;
	}

	// Methode, die einen Geschmack über die ID zurückliefert
	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat
				.format(GET_GESCHMACK_BY_ID, id));
		while (set.next()) {
			m_geschmack = new Geschmack(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
		}
		return m_geschmack;
	}

	public Geschmack getGeschmackByName1(String name) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_NAME,
				FIELD_NAME));
		while (set.next()) {
			m_geschmack = new Geschmack(set.getLong("id"), null);
		}
		return m_geschmack;

	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_GESCHMACK_BY_NAME + name + "%'");
		while (set.next()) {
			list.add(new Geschmack(set.getLong(FIELD_ID), set.getString(FIELD_NAME)));
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
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + "= '"
				+ geschmack.getName() + "' WHERE " + FIELD_ID + " = "
				+ geschmack.getId() + ";";
		this.putManaged(UPDATE_QUERY);

	}

}
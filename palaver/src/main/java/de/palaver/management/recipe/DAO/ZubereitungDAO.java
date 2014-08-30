package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Zubereitung;

public class ZubereitungDAO extends AbstractDAO {

	private final static String TABLE = "zubereitung";
	private static ZubereitungDAO m_instance;
	private ArrayList<Zubereitung> m_list;

	private final static String GET_ALL_ZUBEREITUNGS = "SELECT * FROM " + TABLE;
	private static final String GET_ALL_ZUBEREITUNGS_BY_RECIPE_ID = "SELECT z.* FROM " + TABLE + " z, rezept_has_zubereitung rhz" +
			" WHERE z.id = rhz.zubereitung_fk AND rhz.rezept_fk = {0}";

	public ZubereitungDAO() {
		super();
	}

	public static ZubereitungDAO getInstance() {
		if (m_instance == null) {
			m_instance = new ZubereitungDAO();
		}
		return m_instance;
	}

	// Methode, die alle Zubereitung in einer Liste zurückliefert
	public List<Zubereitung> getAllZubereitungen() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Zubereitung>();
		m_set = getManaged(GET_ALL_ZUBEREITUNGS);
		while (m_set.next()) {
			m_list.add(setZubereitung(m_set));
		}
		return m_list;
	}
	
	public List<Zubereitung> getAllZubereitungsByRecipeId(Long recipeId) throws SQLException, ConnectException, DAOException {
		m_list = new ArrayList<Zubereitung>();
		m_set = getManaged(MessageFormat.format(GET_ALL_ZUBEREITUNGS_BY_RECIPE_ID, recipeId));
		while (m_set.next()) {
			m_list.add(setZubereitung(m_set));
		}
		return m_list;
	}
	
	private Zubereitung setZubereitung(ResultSet set) throws SQLException {		
		return new Zubereitung(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}

	public Long createZubereitung(Zubereitung zubereitung) throws ConnectException, DAOException, SQLException {
		return insert("INSERT INTO " + TABLE + "(name) VALUES('" + zubereitung.getName() + "');");
	}
	
	public void updateZubereitung(Zubereitung zubereitung) throws ConnectException, DAOException, SQLException {
		putManaged("UPDATE " + TABLE + " SET " + FIELD_NAME + " = '"
				+ zubereitung.getName() + "'" + " WHERE " + FIELD_ID + " = '"
				+ zubereitung.getId() + "'");
	}
	
	
	
	
	
	
	
	
	////////////////////////////////

	
	
	private final static String GET_ZUBEREITUNG_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + FIELD_NAME + " LIKE" + " '%";

	// Methode, die eine Zubereitung über den Name zurückliefert
	public Zubereitung getZubereitungByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();

		ResultSet set = getManaged(GET_ZUBEREITUNG_BY_NAME + name + "%'");
		while (set.next()) {
			list.add(new Zubereitung(set.getLong(FIELD_ID), set.getString(FIELD_NAME)));
		}
		return list.get(0);
	}
}

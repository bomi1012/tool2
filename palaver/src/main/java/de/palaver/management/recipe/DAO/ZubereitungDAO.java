package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;
import de.palaver.management.util.dao.SimplyCRUD;

public class ZubereitungDAO extends SimplyCRUD {

	private final static String TABLE = "zubereitung";
	private static ZubereitungDAO m_instance;
	private ArrayList<Zubereitung> m_list;

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

	public List<Zubereitung> getAllZubereitungen() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Zubereitung>();
		m_set = findAll(TABLE);
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

	public Long createZubereitung(Zubereitung zubereitung) throws ConnectException, DAOException, SQLException {
		return create(TABLE, zubereitung.getName());
	}
	
	public void updateZubereitung(Zubereitung zubereitung) throws ConnectException, DAOException, SQLException {
		update(TABLE, zubereitung.getName(), zubereitung.getId());
	}
	
	private Zubereitung setZubereitung(ResultSet set) throws SQLException {		
		return new Zubereitung(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}
}

package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Recipetype;

public class RecipetypeDAO extends AbstractDAO{
	private final static String TABLE = "rezeptart";
	private static final String GET_ALL_RECIPETYPES = "SELECT * FROM " + TABLE;
	private static RecipetypeDAO m_instance = null;
	private ArrayList<Recipetype> m_list;
	public RecipetypeDAO() {
		super();
	}
	
	public static RecipetypeDAO getInstance() {
		if (m_instance == null) {
			m_instance = new RecipetypeDAO();
		}
		return m_instance;
	}

	
	public List<Recipetype> getAllRecipetypes() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Recipetype>();
		m_set = getManaged(GET_ALL_RECIPETYPES);
		while (m_set.next()) {
			m_list.add(setRecipetype(m_set));
		}
		return m_list;
	}
	
	private Recipetype setRecipetype(ResultSet set) throws SQLException {
		return new Recipetype(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}

}

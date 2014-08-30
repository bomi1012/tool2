package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.recipe.Recipetype;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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
	
	public Long createRecipetype(Recipetype recipetype) throws ConnectException, DAOException {
		return insert("INSERT INTO " + TABLE + "(name) VALUES('" + recipetype.getName() + "');");
	}
	
	public void updateRecipetype(Recipetype recipetype) throws ConnectException, DAOException {
		putManaged("UPDATE " + TABLE + " SET " + FIELD_NAME + " = '"
				+ recipetype.getName() + "'" + " WHERE " + FIELD_ID + " = '"
				+ recipetype.getId() + "'");
	}
	
	private Recipetype setRecipetype(ResultSet set) throws SQLException {
		return new Recipetype(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}





}

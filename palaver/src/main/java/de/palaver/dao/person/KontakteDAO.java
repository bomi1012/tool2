package de.palaver.dao.person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.Kontakte;


public class KontakteDAO extends AbstractDAO{
	private static KontakteDAO instance = null;
	
	private final static String TABLE = "kontakte";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_TELEFON = "telefon";
	private static final String FIELD_HANDY = "handy";
	private static final String FIELD_FAX = "fax";
	private static final String FIELD_WWW = "www";

	private static final String GET_KONTAKTE_BY_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_ID + " = {0}";

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_EMAIL + "`, " + "`" + FIELD_TELEFON + "`, " + "`" + FIELD_HANDY + "`, " 
			+ "`" + FIELD_FAX + "`, " + "`" + FIELD_WWW + "`)" +
					" VALUES({0},{1},{2},{3},{4})";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + 
			FIELD_TELEFON + " = {0}, " + FIELD_HANDY + " = {1}," +
			FIELD_FAX + " = {2}, " + FIELD_EMAIL + " = {3}, " + 
			FIELD_WWW + " = {4} WHERE " + FIELD_ID + " = {5}";

	private Kontakte m_kontakte;
	
	public KontakteDAO() {
		super();
	}

	public static KontakteDAO getInstance() {
		if (instance == null) {
			instance = new KontakteDAO();
		}
		return instance;
	}


	public Kontakte getKontakteById(long id) throws ConnectException, DAOException, SQLException {
		m_kontakte = null;
		m_set = getManaged(MessageFormat.format(GET_KONTAKTE_BY_ID, id));
		while (m_set.next()) {
			m_kontakte = setKontakte(m_set);
		}
		return m_kontakte;
	}

	private Kontakte setKontakte(ResultSet set) throws SQLException {
		return 	new Kontakte(set.getLong(FIELD_ID), 
				set.getString(FIELD_EMAIL), 
				set.getString(FIELD_HANDY),
				set.getString(FIELD_TELEFON),
				set.getString(FIELD_FAX),
				set.getString(FIELD_WWW));	
	}

	public long createKontakte(Kontakte kontakte) throws ConnectException, DAOException {
		return insert(MessageFormat.format(INSERT_QUERY, 
				"'" + kontakte.getEmail() + "'",
				"'" + kontakte.getTelefon() + "'",
				"'" + kontakte.getHandy() + "'",
				"'" + kontakte.getFax() + "'",
				"'" + kontakte.getWww() + "'"));
	}

	public void deleteKontakte(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));		
	}

	public void updatekontakte(Kontakte kontakte) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(UPDATE_QUERY, 
				"'" + kontakte.getTelefon() + "'", "'" + kontakte.getHandy() + "'", 
				"'" + kontakte.getFax() + "'", "'" + kontakte.getEmail() + "'",
				"'" + kontakte.getWww() + "'", kontakte.getId()));
	}
}

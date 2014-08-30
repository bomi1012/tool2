package de.palaver.management.artikel.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * @author Mihail Boehm
 * @datum 19.04.2013
 * @version 1.0
 */
public class MengeneinheitDAO extends AbstractDAO {

	private final static String TABLE = "mengeneinheit";
	private static MengeneinheitDAO instance = null;
	private final static String FIELD_KURZ = "kurz";

	private final static String GET_ALL_MENGENEINHEITEN = "SELECT * FROM "
			+ TABLE + " ORDER BY " + FIELD_NAME;
	private final static String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + FIELD_ID + " = {0}";
	private final static String GET_MENGENEINHEIT_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + FIELD_NAME + " = {0}";

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "("
			+ FIELD_NAME + ", " + FIELD_KURZ + ") VALUES({0})";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET "
			+ FIELD_NAME + " = {0}," + FIELD_KURZ + " = {1} WHERE " + FIELD_ID
			+ " = {2}";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";

	private List<Mengeneinheit> list;
	private Mengeneinheit mengeneinheit;

	public MengeneinheitDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static MengeneinheitDAO getInstance() {
		if (instance == null) {
			instance = new MengeneinheitDAO();
		}
		return instance;
	}

	public List<Mengeneinheit> getAllMengeneinheiten() throws ConnectException,
			DAOException, SQLException {
		list = new ArrayList<Mengeneinheit>();
		m_set = getManaged(GET_ALL_MENGENEINHEITEN);
		while (m_set.next()) {
			list.add(setMengeneinheit(m_set));
		}
		return list;
	}

	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException,
			DAOException, SQLException {
		mengeneinheit = null;
		m_set = getManaged(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));
		while (m_set.next()) {
			mengeneinheit = setMengeneinheit(m_set);
		}
		
		return mengeneinheit;
	}

	public List<Mengeneinheit> getMengeneinheitByName(String name)
			throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Mengeneinheit>();
		m_set = getManaged(MessageFormat.format(GET_MENGENEINHEIT_BY_NAME, name));
		while (m_set.next()) {
			list.add(setMengeneinheit(m_set));
		}
		return list;
	}

	public void createMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {
		String content = "'" + mengeneinheit.getName() + "', '"
				+ mengeneinheit.getKurz() + "'";
		putManaged(MessageFormat.format(INSERT_QUERY, content));
	}

	public void updateMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {

		putManaged(MessageFormat.format(UPDATE_QUERY,
				"'" + mengeneinheit.getName() + "'",
				"'" + mengeneinheit.getKurz() + "'", mengeneinheit.getId()));
	}

	public void removeMengeneinheit(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));	
		
	}
	
	private Mengeneinheit setMengeneinheit(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Mengeneinheit(set.getLong(FIELD_ID),  set.getString(FIELD_NAME), set.getString(FIELD_KURZ));	
	}
}

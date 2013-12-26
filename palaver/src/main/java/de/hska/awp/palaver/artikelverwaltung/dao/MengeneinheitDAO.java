package de.hska.awp.palaver.artikelverwaltung.dao;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

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
		set = getManaged(GET_ALL_MENGENEINHEITEN);
		while (set.next()) {
			list.add(new Mengeneinheit(set.getLong(FIELD_ID), set
					.getString(FIELD_NAME), set.getString(FIELD_KURZ)));
		}
		return list;
	}

	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException,
			DAOException, SQLException {
		mengeneinheit = null;
		set = getManaged(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));
		while (set.next()) {
			mengeneinheit = new Mengeneinheit(set.getLong("id"),
					set.getString("name"), set.getString("kurz"));
		}
		return mengeneinheit;
	}

	public List<Mengeneinheit> getMengeneinheitByName(String name)
			throws ConnectException, DAOException, SQLException {
		list = new ArrayList<Mengeneinheit>();
		set = getManaged(MessageFormat.format(GET_MENGENEINHEIT_BY_NAME, name));
		while (set.next()) {
			list.add(new Mengeneinheit(set.getLong(FIELD_ID), set
					.getString(FIELD_NAME), set.getString(FIELD_KURZ)));
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
}
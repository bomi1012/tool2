package de.hska.awp.palaver.artikelverwaltung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.domain.Lagerort;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class LagerortDAO extends AbstractDAO {

	private static final String TABLE = "lagerort";
	private static final String GET_ALL_LAGERORTS = "SELECT * FROM " + TABLE;
	private static final String GET_LAGERORT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_NAME + ") VALUES({0})";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + " = {0} WHERE " + FIELD_ID + " = {1}";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";

	private List<Lagerort> list;
	private Lagerort lagerort;
	public LagerortDAO() {
		super();
	}

	private static LagerortDAO instance = null;

	public static LagerortDAO getInstance() {
		if (instance == null) {
			instance = new LagerortDAO();
		}
		return instance;
	}

	public List<Lagerort> getAllLagerorts() throws ConnectException,
			DAOException, SQLException {
		list = new ArrayList<Lagerort>();
		set = getManaged(GET_ALL_LAGERORTS);
		while (set.next()) {
			list.add(setLagerort(set));
		}
		return list;
	}
	
	public Lagerort getLagerortById(long id) throws ConnectException, DAOException, SQLException {
		set = getManaged(MessageFormat.format(GET_LAGERORT_BY_ID, id));
		while (set.next()) {
			lagerort = setLagerort(set);
		}
		return lagerort;
	}

	public void createLagerort(Lagerort lagerort) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(INSERT_QUERY, "'" + lagerort.getName() + "'" ));	
	}
	public void updateLagerort(Lagerort lagerort) throws ConnectException,
		DAOException, SQLException {
		putManaged(MessageFormat.format(UPDATE_QUERY, "'" + lagerort.getName() + "'", lagerort.getId()));
	}
	
	public void deleteLagerort(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));		
	}

	private Lagerort setLagerort(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Lagerort(set.getLong(FIELD_ID),  set.getString(FIELD_NAME));	
	}
}

package de.hska.awp.palaver.artikelverwaltung.dao;

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
			list.add(new Lagerort(set.getLong(FIELD_ID), set.getString(FIELD_NAME)));
		}
		return list;
	}

	public void createLagerort(Lagerort lagerort) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(INSERT_QUERY, "'" + lagerort.getName() + "'" ));	
	}

	public Lagerort getLagerortById(long id) throws ConnectException, DAOException, SQLException {
		set = getManaged(MessageFormat.format(GET_LAGERORT_BY_ID, id));
		while (set.next()) {
			lagerort = new Lagerort(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
		}
		return lagerort;
	}
}

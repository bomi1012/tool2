package de.palaver.dao.artikelverwaltung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.artikelverwaltung.Kategorie;

public class KategorieDAO extends AbstractDAO {

	private static KategorieDAO instance = null;
	private Kategorie kategorie;
	private List<Kategorie> kategories;	
	private static final String TABLE = "kategorie";
	private static final String GET_ALL_KATEGORIES = "SELECT * FROM " + TABLE;
	private static final String GET_KATEGORIE_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_NAME + ") VALUES({0})";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + " = {0} WHERE " + FIELD_ID + " = {1}";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	

	public KategorieDAO() {
		super();
	}

	public static KategorieDAO getInstance() {
		if (instance == null) {
			instance = new KategorieDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllKategories liefert alle in der Datenbank befindlichen
	 * Kategorien zurück.
	 */
	public List<Kategorie> getAllKategories() throws ConnectException, DAOException, SQLException {
		kategories = new ArrayList<Kategorie>();
		m_set = getManaged(GET_ALL_KATEGORIES);
		while (m_set.next()) {
			kategories.add(setKategorie(m_set));
		}
		return kategories;
	}

	/**
	 * Die Methode getKategorieById liefert ein Ergebniss zurÃ¼ck bei der Suche
	 * nach einer Kategorie in der Datenbank.
	 */
	public Kategorie getKategorieById(Long id) throws ConnectException,
			DAOException, SQLException {
		m_set = getManaged(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
		while (m_set.next()) {
			kategorie = setKategorie(m_set);
		}
		return kategorie;
	}

	public void createKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {		
		putManaged(MessageFormat.format(INSERT_QUERY, "'" + kategorie.getName() + "'" ));
	}

	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		putManaged(MessageFormat.format(UPDATE_QUERY, "'" + kategorie.getName() + "'", kategorie.getId()));
	}

	public void deleteKategorie(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));		
	}
	
	private Kategorie setKategorie(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Kategorie(set.getLong(FIELD_ID),  set.getString(FIELD_NAME));	
	}
}

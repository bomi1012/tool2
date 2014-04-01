package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * @author Michael Marschall
 * 
 */

public class RezeptartDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "rezeptart";

	// SQL-Statements
	private static final String GET_ALL_REZEPTART = "SELECT * FROM " + TABLE;
	private static final String GET_REZEPTART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private static final String GET_REZEPTART_BY_REZEPT = "Select rezeptart.id, rezeptart.name from rezeptart Join rezept On rezept.rezeptart_fk = rezeptart.id WHERE rezept.id = {0}";

	private static RezeptartDAO instance = null;

	Rezeptart rezeptart;

	// Konstruktor
	public RezeptartDAO() {
		super();
	}

	// Instanz erzeugen
	public static RezeptartDAO getInstance() {
		if (instance == null) {
			instance = new RezeptartDAO();
		}

		return instance;
	}

	// Methode, die alle Rezeptarten in einer Liste zurückliefert
	public List<Rezeptart> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {
		List<Rezeptart> list = new ArrayList<Rezeptart>();
		ResultSet set = getManaged(GET_ALL_REZEPTART);
		while (set.next()) {
			list.add(new Rezeptart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Rezeptart über die ID zurückliefert
	public Rezeptart getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat
				.format(GET_REZEPTART_BY_ID, id));
		while (set.next()) {
			rezeptart = new Rezeptart(set.getLong(ID), set.getString(NAME));
		}
		return rezeptart;
	}

	// Methode, die eine Rezeptart zu einem Rezept über die ID zurückliefert
	public Rezeptart getRezeptartByRezept(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(
				GET_REZEPTART_BY_REZEPT, id));
		while (set.next()) {
			rezeptart = new Rezeptart(set.getLong(ID), set.getString(NAME));
		}
		return rezeptart;
	}

	// Methode, die eine Rezeptart erstellt
	public void createRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ rezeptart.getName() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Rezeptart Ã¤ndert
	public void updateRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ rezeptart.getName() + "'" + " WHERE " + ID + "='"
				+ rezeptart.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

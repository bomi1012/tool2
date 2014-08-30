package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.recipe.Recipetype;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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

	Recipetype recipetype;

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
	public List<Recipetype> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {
		List<Recipetype> list = new ArrayList<Recipetype>();
		ResultSet set = getManaged(GET_ALL_REZEPTART);
		while (set.next()) {
			list.add(new Recipetype(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	// Methode, die eine Rezeptart über die ID zurückliefert
	public Recipetype getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat
				.format(GET_REZEPTART_BY_ID, id));
		while (set.next()) {
			recipetype = new Recipetype(set.getLong(ID), set.getString(NAME));
		}
		return recipetype;
	}

	// Methode, die eine Rezeptart zu einem Rezept über die ID zurückliefert
	public Recipetype getRezeptartByRezept(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(
				GET_REZEPTART_BY_REZEPT, id));
		while (set.next()) {
			recipetype = new Recipetype(set.getLong(ID), set.getString(NAME));
		}
		return recipetype;
	}

	// Methode, die eine Rezeptart erstellt
	public void createRezeptart(Recipetype recipetype) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ recipetype.getName() + "');";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die eine Rezeptart Ã¤ndert
	public void updateRezeptart(Recipetype recipetype) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ recipetype.getName() + "'" + " WHERE " + ID + "='"
				+ recipetype.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}

package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.palaver.management.recipe.Recipetype;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class RezeptartDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "rezeptart";

	private static final String GET_REZEPTART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";

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
}

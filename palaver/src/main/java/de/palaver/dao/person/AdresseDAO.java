package de.palaver.dao.person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.Adresse;



public class AdresseDAO extends AbstractDAO {
	private static AdresseDAO instance = null;
	
	private final static String TABLE = "adresse";
	private static final String FIELD_STRASSE = "strasse";
	private static final String FIELD_PLZ = "plz";
	private static final String FIELD_STADT = "stadt";
	private static final String FIELD_HAUSNUMMER = "hausnummer";
	private static final String FIELD_LAND = "land";

	private static final String GET_ADRESSE_BY_ID = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_ID + " = {0}";

	private Adresse m_adresse;
	
	public AdresseDAO() {
		super();
	}

	public static AdresseDAO getInstance() {
		if (instance == null) {
			instance = new AdresseDAO();
		}
		return instance;
	}

	public Adresse getAdresseById(long id) throws ConnectException, DAOException, SQLException {
		m_adresse = null;
		m_set = getManaged(MessageFormat.format(GET_ADRESSE_BY_ID, id));
		while (m_set.next()) {
			m_adresse = setAdresse(m_set);
		}
		return m_adresse;
	}

	private Adresse setAdresse(ResultSet set) throws SQLException {
		return 	new Adresse(set.getLong(FIELD_ID), 
				set.getString(FIELD_STRASSE), 
				set.getString(FIELD_HAUSNUMMER),
				set.getString(FIELD_STADT),
				set.getString(FIELD_PLZ),
				set.getString(FIELD_LAND));	
	}
}

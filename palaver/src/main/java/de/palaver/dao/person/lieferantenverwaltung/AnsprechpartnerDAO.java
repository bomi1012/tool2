package de.palaver.dao.person.lieferantenverwaltung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.person.AdresseDAO;
import de.palaver.dao.person.KontakteDAO;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;


public class AnsprechpartnerDAO extends AbstractDAO {
	private static final String TABLE = "ansprechpartner";
	private static final String FIELD_BEZEICHNUNG = "bezeichnung";
	private final static String FIELD_ADRESSE_FK = "adresse_fk";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";
	private final static String FIELD_LIEFERANT_FK = "lieferant_fk";
	
	private static AnsprechpartnerDAO instance = null;
	private ArrayList<Ansprechpartner> m_list;

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_LIEFERANT_FK + "`, " + "`" + FIELD_BEZEICHNUNG + "`, " 
			+ "`" + FIELD_ADRESSE_FK + "`, " + "`" + FIELD_KONTAKTE_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5},{6})";
	private static final String GET_ALL_ANSPRECHPARTNERS = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} ORDER BY " + FIELD_NAME;
	
	
	public AnsprechpartnerDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static AnsprechpartnerDAO getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerDAO();
		}
		return instance;
	}
	
	public List<Ansprechpartner> getAllAnsprechpartnersByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Ansprechpartner>();
		m_set = getManaged(MessageFormat.format(GET_ALL_ANSPRECHPARTNERS, id));
		while (m_set.next()) {
			m_list.add(setAnsprechpartner(m_set));
		}		
		return m_list;
	}

	public Long createAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException {
		String kontakt = null;
		String adresse = null;
		if (ansprechpartner.getKontakte() != null) {
			kontakt = String.valueOf(ansprechpartner.getKontakte().getId());
		}
		if (ansprechpartner.getAdresse() != null) {
			adresse = String.valueOf(ansprechpartner.getAdresse().getId());
		}
		
		return insert(MessageFormat.format(INSERT_QUERY, 
				"'" + ansprechpartner.getName() + "'",
				"'" + ansprechpartner.getLieferant().getId() + "'",
				"'" + ansprechpartner.getBezeichnung() + "'",
				adresse, kontakt));
	}
	
	private Ansprechpartner setAnsprechpartner(ResultSet set) throws SQLException, ConnectException, DAOException {
		return 	new Ansprechpartner(set.getLong(FIELD_ID), 
				set.getString(FIELD_NAME),
				LieferantDAO.getInstance().getActiveLieferantById(set.getLong(FIELD_LIEFERANT_FK)),
				set.getString(FIELD_BEZEICHNUNG),
				
				AdresseDAO.getInstance().getAdresseById(set.getLong(FIELD_ADRESSE_FK)),
				KontakteDAO.getInstance().getKontakteById(set.getLong(FIELD_KONTAKTE_FK)));	
	}
}

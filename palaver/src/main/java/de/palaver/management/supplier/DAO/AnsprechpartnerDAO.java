package de.palaver.management.supplier.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.info.person.DAO.AdresseDAO;
import de.palaver.management.info.person.DAO.KontakteDAO;
import de.palaver.management.supplier.Ansprechpartner;


public class AnsprechpartnerDAO extends AbstractDAO {
	private static final String TABLE = "ansprechpartner";
	private static final String FIELD_BEZEICHNUNG = "bezeichnung";
	private final static String FIELD_ADRESSE_FK = "adresse_fk";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";
	private final static String FIELD_LIEFERANT_FK = "lieferant_fk";
	
	private String kontakt;
	private String adresse;
	private String desc;
	
	private static AnsprechpartnerDAO instance = null;
	private ArrayList<Ansprechpartner> m_list;

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_LIEFERANT_FK + "`, " + "`" + FIELD_BEZEICHNUNG + "`, " 
			+ "`" + FIELD_ADRESSE_FK + "`, " + "`" + FIELD_KONTAKTE_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4})";
	private static final String GET_ALL_ANSPRECHPARTNERS = "SELECT * FROM " + TABLE 
			+ " WHERE " + FIELD_LIEFERANT_FK + " = {0} ORDER BY " + FIELD_NAME;
	private static final String DELETE_QUERY_BY_LIEFERANT_ID = "DELETE FROM " + TABLE + " WHERE " + FIELD_LIEFERANT_FK + " = {0}";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET "
			+ FIELD_NAME + " = {0}, " + FIELD_BEZEICHNUNG + " = {1}, "
			+ FIELD_ADRESSE_FK + " = {2}, " + FIELD_KONTAKTE_FK + " = {3} WHERE " 
			+ FIELD_ID + " = {4}";
	
	
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
		setFields(ansprechpartner);
		return insert(MessageFormat.format(INSERT_QUERY, "'" + ansprechpartner.getName() + "'",
				ansprechpartner.getLieferant().getId(), desc, adresse, kontakt));
	}
	
	public void deleteAnsprechpartnerByLieferantId(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY_BY_LIEFERANT_ID, id));		
	}

	public void updateAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException {
		setFields(ansprechpartner);
		putManaged(MessageFormat.format(UPDATE_QUERY, "'" + ansprechpartner.getName() + "'", 
				desc, adresse, kontakt, ansprechpartner.getId()));
		
	}

	public void removeAnsprechpartner(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));	
	}
	
	private Ansprechpartner setAnsprechpartner(ResultSet set) throws SQLException, ConnectException, DAOException {
		return 	new Ansprechpartner(set.getLong(FIELD_ID), 
				set.getString(FIELD_NAME),
				SupplierDAO.getInstance().getActiveLieferantById(set.getLong(FIELD_LIEFERANT_FK)),
				set.getString(FIELD_BEZEICHNUNG),
				
				AdresseDAO.getInstance().getAdresseById(set.getLong(FIELD_ADRESSE_FK)),
				KontakteDAO.getInstance().getKontakteById(set.getLong(FIELD_KONTAKTE_FK)));	
	}
	
	private void setFields(Ansprechpartner ansprechpartner) {
		setFieldsToNull();
		if (ansprechpartner.getKontakt() != null) {
			kontakt = String.valueOf(ansprechpartner.getKontakt().getId());
		}
		if (ansprechpartner.getAdresse() != null) {
			adresse = String.valueOf(ansprechpartner.getAdresse().getId());
		}
		if(ansprechpartner.getBezeichnung() != null) { desc = "'" + ansprechpartner.getBezeichnung() + "'"; }
	}

	private void setFieldsToNull() {		
		kontakt = null;
		adresse = null;
		desc = null;
	}
}

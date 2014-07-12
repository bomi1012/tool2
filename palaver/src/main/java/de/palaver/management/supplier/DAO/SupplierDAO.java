package de.palaver.management.supplier.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.util.Util;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.info.person.service.AdresseService;
import de.palaver.management.info.person.service.KontakteService;
import de.palaver.management.supplier.Supplier;

public class SupplierDAO extends AbstractDAO {

	private static SupplierDAO instance = null;

	private static final String TABLE = "lieferant";
	private static final String TABLE_ARTIKEL = "artikel";
	private static final String FIELD_LIEFERANTNUMMER = "lieferantnummer";
	private static final String FIELD_BEZEICHNUNG = "bezeichnung";
	private static final String FIELD_NOTIZ = "notiz";
	private static final String FIELD_MEHRERELIEFERTERMINE = "mehrereliefertermine";
	private final static String FIELD_ADRESSE_FK = "adresse_fk";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";
	private final static String ACTIVE = "status = 0";

	private static final String GET_LIEFERANT_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + FIELD_ID + " = {0} AND " + ACTIVE;
	private static final String GET_ALL_ACTIVE_LIEFERANTEN = "SELECT * FROM "
			+ TABLE + " WHERE " + ACTIVE;
	private static final String GET_LIEFERANTEN_GRUNDBEDARF = "SELECT * FROM "
			+ TABLE + " JOIN  " + TABLE_ARTIKEL + " ON " + TABLE + "."
			+ FIELD_ID + " = " + TABLE_ARTIKEL + ".lieferant_fk" + " WHERE "
			+ TABLE_ARTIKEL + ".grundbedarf = {0} AND " + TABLE_ARTIKEL + "."
			+ ACTIVE;
	
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "("
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_LIEFERANTNUMMER + "`, "
			+ "`" + FIELD_BEZEICHNUNG + "`, " + "`" + FIELD_NOTIZ + "`, " + "`"
			+ FIELD_MEHRERELIEFERTERMINE + "`, " + "`" + FIELD_ADRESSE_FK
			+ "`, " + "`" + FIELD_KONTAKTE_FK + "`)"
			+ " VALUES({0},{1},{2},{3},{4},{5},{6})";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_ID + " = {0}";
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET "
			+ FIELD_NAME + " = {0}, " + FIELD_LIEFERANTNUMMER + " = {1}, "
			+ FIELD_BEZEICHNUNG + " = {2}, " + FIELD_NOTIZ + " = {3}, "
			+ FIELD_MEHRERELIEFERTERMINE + " = {4}, " + FIELD_ADRESSE_FK
			+ " = {5}, " + FIELD_KONTAKTE_FK + " = {6} WHERE " + FIELD_ID
			+ " = {7}";


	private Supplier m_supplier;
	private ArrayList<Supplier> m_list;

	public SupplierDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static SupplierDAO getInstance() {
		if (instance == null) {
			instance = new SupplierDAO();
		}
		return instance;
	}

	public Supplier getActiveLieferantById(long id) throws ConnectException,
			DAOException, SQLException {
		m_supplier = null;
		m_set = getManaged(MessageFormat.format(GET_LIEFERANT_BY_ID, id));
		while (m_set.next()) {
			m_supplier = setLieferant(m_set);
		}
		return m_supplier;
	}

	public List<Supplier> getActiveLieferanten() throws ConnectException,
			DAOException, SQLException {
		m_list = new ArrayList<Supplier>();
		m_set = getManaged(GET_ALL_ACTIVE_LIEFERANTEN);
		while (m_set.next()) {
			m_list.add(setLieferant(m_set));
		}
		return m_list;
	}

	public List<Supplier> getLieferantenByGrundbedarf(boolean hatGrundbedarf)
			throws SQLException, ConnectException, DAOException {
		m_list = new ArrayList<Supplier>();
		m_set = getManaged(MessageFormat.format(GET_LIEFERANTEN_GRUNDBEDARF,
				hatGrundbedarf));
		while (m_set.next()) {
			m_list.add(setLieferant(m_set));
		}
		return m_list;
	}
	
	private Supplier setLieferant(ResultSet set) throws SQLException,
			ConnectException, DAOException {
		return new Supplier(set.getLong(FIELD_ID), set.getString(FIELD_NAME),
				set.getString(FIELD_LIEFERANTNUMMER),
				set.getString(FIELD_BEZEICHNUNG),
				set.getBoolean(FIELD_MEHRERELIEFERTERMINE),
				set.getString(FIELD_NOTIZ), 
				
				AdresseService.getInstance()
						.getAdresseById(set.getLong(FIELD_ADRESSE_FK)),
				KontakteService.getInstance().getKontakteById(
						set.getLong(FIELD_KONTAKTE_FK)));
	}

	public Long createLieferant(Supplier supplier) throws ConnectException,
			DAOException {
		String kontakt = null;
		String adresse = null;
		if (supplier.getKontakte() != null) {
			kontakt = String.valueOf(supplier.getKontakte().getId());
		}
		if (supplier.getAdresse() != null) {
			adresse = String.valueOf(supplier.getAdresse().getId());
		}

		return insert(MessageFormat.format(INSERT_QUERY,
				"'" + supplier.getName() + "'",
				"'" + supplier.getLieferantnummer() + "'",
				"'" + supplier.getBezeichnung() + "'",
				"'" + supplier.getNotiz() + "'",
				Util.convertBoolean(supplier.isMehrereliefertermine()),
				adresse, kontakt));
	}

	public void updateLieferant(Supplier supplier) throws ConnectException,
			DAOException {
		String kontakt = null;
		String adresse = null;
		if (supplier.getKontakte() != null) {
			kontakt = String.valueOf(supplier.getKontakte().getId());
		}
		if (supplier.getAdresse() != null) {
			adresse = String.valueOf(supplier.getAdresse().getId());
		}
		putManaged(MessageFormat.format(UPDATE_QUERY, "'" + supplier.getName()
				+ "'", "'" + supplier.getLieferantnummer() + "'", "'"
				+ supplier.getBezeichnung() + "'", "'" + supplier.getNotiz()
				+ "'", Util.convertBoolean(supplier.isMehrereliefertermine()),
				adresse, kontakt, supplier.getId()));

	}

	public void deleteLieferant(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));	
		
	}
}

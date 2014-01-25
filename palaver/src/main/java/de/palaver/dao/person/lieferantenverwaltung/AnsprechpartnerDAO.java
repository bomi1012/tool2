package de.palaver.dao.person.lieferantenverwaltung;

import java.text.MessageFormat;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;


public class AnsprechpartnerDAO extends AbstractDAO {
	private static final String TABLE = "ansprechpartner";
	private static final String FIELD_BEZEICHNUNG = "bezeichnung";
	private final static String FIELD_ADRESSE_FK = "adresse_fk";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";
	private final static String FIELD_LIEFERANT_FK = "lieferant_fk";
	
	private static AnsprechpartnerDAO instance = null;

	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_LIEFERANT_FK + "`, " + "`" + FIELD_BEZEICHNUNG + "`, " 
			+ "`" + FIELD_ADRESSE_FK + "`, " + "`" + FIELD_KONTAKTE_FK + "`)" +
					" VALUES({0},{1},{2},{3},{4},{5},{6})";
	
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

}

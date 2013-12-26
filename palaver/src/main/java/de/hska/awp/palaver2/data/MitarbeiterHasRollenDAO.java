package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.MitarbeiterHasRollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * Die Klasse stellt Methoden für den Datenbankzugriff für das Objekt
 * MitarbeiterHasRollen bereit.
 * 
 * @author Christian Barth
 * 
 */
public class MitarbeiterHasRollenDAO extends AbstractDAO {

	private static MitarbeiterHasRollenDAO instance = null;

	private static final String TABLE = "mitarbeiter_has_rollen";
	private static final String MITARBEITER_FK = "mitarbeiter_fk";
	private static final String ROLLEN_FK = "rollen_fk";
	private static final String GET_ALL_MITARBEITER_HAS_ROLLEN = "SELECT * FROM mitarbeiter_has_rollen";
	private static final String DELETE = "DELETE FROM mitarbeiter_has_rollen " + "WHERE mitarbeiter_has_rollen.mitarbeiter_fk = {0}";
	private static final String GET_MITARBEITER_HAS_ROLLEN_BY_MITARBEITER_AND_ROLLE = "SELECT * FROM mitarbeiter_has_rollen "
			+ "WHERE mitarbeiter_has_rollen.mitarbeiter_fk = {0} AND mitarbeiter_has_rollen.rollen_fk = {1}";

	public MitarbeiterHasRollenDAO() {
		super();
	}

	public static MitarbeiterHasRollenDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterHasRollenDAO();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle MitarbeiterHasRollen zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<MitarbeiterHasRollen> getAllMitarbeiterHasRollen() throws ConnectException, DAOException, SQLException {
		List<MitarbeiterHasRollen> list = new ArrayList<MitarbeiterHasRollen>();
		ResultSet set = getManaged(GET_ALL_MITARBEITER_HAS_ROLLEN);
		while (set.next()) {
			list.add(new MitarbeiterHasRollen(set.getLong("mitarbeiter_fk"), set.getLong("rollen_fk")));
		}
		return list;
	}

	/**
	 * Die Methode liefert alle MitarbeiterHasRollen anhand eines Mitarbeiter
	 * und einer Rolle zurück.
	 * 
	 * @param mitarbeiter
	 * @param rolle
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public MitarbeiterHasRollen getMitarbeiterHasRollenByMitarbeiterAndRolle(Mitarbeiter mitarbeiter, Rollen rolle) throws ConnectException,
			DAOException, SQLException {
		MitarbeiterHasRollen mitarbeiterhasrollen = new MitarbeiterHasRollen();
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_HAS_ROLLEN_BY_MITARBEITER_AND_ROLLE, mitarbeiter.getId(), rolle.getId()));
		while (set.next()) {
			mitarbeiterhasrollen = new MitarbeiterHasRollen(set.getLong("mitarbeiter_fk"), set.getLong("rollen_fk"));
		}
		return mitarbeiterhasrollen;
	}

	/**
	 * Die Methode erzeugt einen MitarbeiterHasRollen in der Datenbank.
	 * 
	 * @param mitarbeiter
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createMitarbeiterHasRollen(Mitarbeiter mitarbeiter, Rollen rolle) throws ConnectException, DAOException, SQLException {

		String inserq = "INSERT INTO " + TABLE + "(" + MITARBEITER_FK + "," + ROLLEN_FK + ")" + "VALUES" + "('" + mitarbeiter.getId() + "','"
				+ rolle.getId() + "')";
		this.putManaged(inserq);
	}

	/**
	 * Die Methode löscht einen MitarbeiterHasRollen in der Datenbank.
	 * 
	 * @param mitarbeiter
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteMitarbeiterHasRollen(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {

		if (mitarbeiter == null) {
			throw new NullPointerException("Es wurde kein Mitarbeiter übergeben");
		}
		putManaged(MessageFormat.format(DELETE, mitarbeiter.getId()));
	}

}

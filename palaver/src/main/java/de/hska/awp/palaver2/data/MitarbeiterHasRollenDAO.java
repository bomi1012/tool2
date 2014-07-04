package de.hska.awp.palaver2.data;

import java.sql.SQLException;
import java.text.MessageFormat;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rollen;

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
	 * Die Methode erzeugt einen MitarbeiterHasRollen in der Datenbank.
	 * 
	 * @param employee
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createMitarbeiterHasRollen(Employee employee, Rollen rolle) throws ConnectException, DAOException, SQLException {

		String inserq = "INSERT INTO " + TABLE + "(" + MITARBEITER_FK + "," + ROLLEN_FK + ")" + "VALUES" + "('" + employee.getId() + "','"
				+ rolle.getId() + "')";
		this.putManaged(inserq);
	}

	/**
	 * Die Methode löscht einen MitarbeiterHasRollen in der Datenbank.
	 * 
	 * @param employee
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteMitarbeiterHasRollen(Employee employee) throws ConnectException, DAOException, SQLException {

		if (employee == null) {
			throw new NullPointerException("Es wurde kein Mitarbeiter übergeben");
		}
		putManaged(MessageFormat.format(DELETE, employee.getId()));
	}

}

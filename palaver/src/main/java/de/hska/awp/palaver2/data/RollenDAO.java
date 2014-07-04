package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rollen;

/**
 * Die Klasse stellt Methoden für den Datenbankzugriff für das Objekt Rollen
 * bereit.
 * 
 * @author Christian Barth
 * 
 */
public class RollenDAO extends AbstractDAO {

	private static final String TABLE = "rollen";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String GET_ALL_ROLLEN = "SELECT * FROM rollen";
	private static final String GET_ROLLEN_BY_ID = "SELECT * FROM rollen WHERE id = {0}";
	private static final String GET_ROLLEN_BY_MITARBEITER_ID = "SELECT rollen.id, rollen.name FROM rollen join mitarbeiter_has_rollen on "
			+ "rollen.id = mitarbeiter_has_rollen.rollen_fk where mitarbeiter_fk = {0}";
	private static final String GET_NACHRICHT_BY_ROLLE_ID = "SELECT * FROM nachrichten WHERE empf_rolle_fk = {0}";
	private static final String GET_MITARBEITER_BY_ROLLEN_ID = "SELECT * FROM mitarbeiter "
			+ "JOIN mitarbeiter_has_rollen on mitarbeiter.id = mitarbeiter_has_rollen.mitarbeiter_fk "
			+ "join rollen on mitarbeiter_has_rollen.rollen_fk = rollen.id where rollen.id = {0}";
	private static final String GET_MITARBEITER_BY_ID = "SELECT * FROM mitarbeiter WHERE id = {0}";

	private static RollenDAO instance = null;

	public static RollenDAO getInstance() {
		if (instance == null) {
			instance = new RollenDAO();
		}
		return instance;
	}

	public RollenDAO() {
		super();
	}

	/**
	 * Die Methode liefert alle Rollen aus der Datenbank zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Rollen> getAllRollen() throws ConnectException, DAOException, SQLException {
		List<Rollen> list = new ArrayList<Rollen>();
		openConnection();
		ResultSet set = get(GET_ALL_ROLLEN);

		openConnection();
		while (set.next()) {
			list.add(new Rollen(set.getLong("id"), set.getString("name"), getMitarbeiterByRollenId(set.getLong("id")), getNachrichtByRolleId(set
					.getLong("id"))));
		}
		closeConnection();
		return list;
	}

	/**
	 * Die Methode liefert ein Rolle anhand ihrer ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Rollen getRollenById(Long id) throws ConnectException, DAOException, SQLException {

		Rollen rolle = null;
		ResultSet set = getManaged(MessageFormat.format(GET_ROLLEN_BY_ID, id));

		while (set.next()) {
			rolle = new Rollen(set.getLong("id"), set.getString("name"), MitarbeiterDAO.getInstance().getMitarbeiterByRollenId(set.getLong("id")),
					NachrichtDAO.getInstance().getNachrichtByRolleId(set.getLong("id")));
		}
		return rolle;

	}

	/**
	 * Die Methode liefert alle Rollen zur einer Mitarbeiter ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Rollen> getRollenByMitarbeiterId(Long id) throws ConnectException, DAOException, SQLException {

		List<Rollen> list = new ArrayList<Rollen>();

		ResultSet set = getManaged(MessageFormat.format(GET_ROLLEN_BY_MITARBEITER_ID, id));

		while (set.next()) {
			list.add(new Rollen(set.getLong("id"), set.getString("name"), getNachrichtByRolleId(set.getLong("id"))));

		}

		return list;

	}

	/**
	 * Die Methode liefert alle Nachrichten zur einer RollenId zurück.
	 * 
	 * @param rid
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Nachricht> getNachrichtByRolleId(Long rid) throws ConnectException, DAOException, SQLException {

		List<Nachricht> list = new ArrayList<Nachricht>();

		ResultSet set = getMany(MessageFormat.format(GET_NACHRICHT_BY_ROLLE_ID, rid));

		while (set.next()) {
			list.add(new Nachricht(set.getLong("id"), set.getString("nachricht"), getMitarbeiterByIdForNachricht(set.getLong("sender_fk")),
					new Rollen()));
		}

		return list;
	}

	/**
	 * Die Methode liefert eine Mitarbeiter ohne Rollen zurück anhand einer
	 * RollenId.
	 * 
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Employee> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Employee> list = new ArrayList<Employee>();

		ResultSet set = getMany(MessageFormat.format(GET_MITARBEITER_BY_ROLLEN_ID, id));

		while (set.next()) {
			list.add(new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername")));

		}

		return list;

	}

	/**
	 * Die Methode liefert eine Mitarbeiter in abgespeckter Form für die
	 * Nachrichten zurück.
	 * 
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Employee getMitarbeiterByIdForNachricht(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Employee employee = null;
		ResultSet set = getMany(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			employee = new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername"));
		}
		return employee;

	}

	/**
	 * Die Methode erzeugt eine Rolle in der Datenbank.
	 * 
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createRollen(Rollen rolle) throws ConnectException, DAOException, SQLException {
		String insertq = "INSERT INTO " + TABLE + "(" + NAME + ")" + "VALUES" + "('" + rolle.getName() + "')";
		this.putManaged(insertq);
	}

	/**
	 * Die Methode aktualisiert eine Rolle in der Datenbank.
	 * 
	 * @param rolle
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateRollen(Rollen rolle) throws ConnectException, DAOException, SQLException {
		String updateq = "UPDATE " + TABLE + " SET " + NAME + "='" + rolle.getName() + "' WHERE " + ID + "='" + rolle.getId() + "'";
		this.putManaged(updateq);
	}

}

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
 * Die Klasse stellt Methoden für den Datenbankzugriff für das Objekt
 * Mitarbeiter bereit.
 * 
 * @Author Christian Barth
 */
public class MitarbeiterDAO extends AbstractDAO {

	private static final String TABLE = "mitarbeiter";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String VORNAME = "vorname";
	private static final String EMAIL = "email";
	private static final String PASSWORT = "passwort";
	private static final String EINTRITTSDATUM = "eintrittsdatum";
	private static final String AUSTRITTSDATUM = "austrittsdatum";
	private static final String BENUTZERNAME = "benutzername";

	private static final String GET_ALL_MITARBEITER = "SELECT * FROM mitarbeiter";
	private static final String GET_MITARBEITER_BY_ID = "SELECT * FROM mitarbeiter WHERE id = {0}";
	private static final String GET_MITARBEITER_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String GET_MITARBEITER_BY_ROLLEN_ID = "SELECT * FROM mitarbeiter "
			+ "JOIN mitarbeiter_has_rollen on mitarbeiter.id = mitarbeiter_has_rollen.mitarbeiter_fk "
			+ "join rollen on mitarbeiter_has_rollen.rollen_fk = rollen.id where rollen.id = {0}";
	private static final String GET_MITARBEITER_BY_BENUTZERNAME = "SELECT * FROM " + TABLE + " WHERE " + BENUTZERNAME + " = '";

	private static final String GET_NACHRICHT_BY_ROLLE_ID = "SELECT * FROM nachrichten WHERE empf_rolle_fk = {0}";
	private static final String GET_ROLLEN_BY_MITARBEITER_ID = "SELECT rollen.id, rollen.name FROM rollen join mitarbeiter_has_rollen on "
			+ "rollen.id = mitarbeiter_has_rollen.rollen_fk where mitarbeiter_fk = {0}";

	private static MitarbeiterDAO instance = null;

	public static MitarbeiterDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterDAO();
		}
		return instance;
	}

	public MitarbeiterDAO() {
		super();
	}

	/**
	 * Die Methode liefert alle Mitarbeiter aus der Datenbank zurück. author
	 * Christian Barth
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Employee> getAllMitarbeiter() throws ConnectException, DAOException, SQLException {
		List<Employee> list = new ArrayList<Employee>();
		openConnection();
		ResultSet set = get(GET_ALL_MITARBEITER);
		openConnection();
		while (set.next()) {
			list.add(new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), getRollenByMitarbeiterId(set
					.getLong("id")), set.getString("benutzername")));
		}
		closeConnection();
		return list;
	}

	/**
	 * Die Methode liefert ein Mitarbeiter anhand seiner ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Employee getMitarbeiterById(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Employee employee = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			employee = new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"),
					getRollenByMitarbeiterId(set.getLong("id")), set.getString("benutzername"));
		}

		return employee;
	}

	/**
	 * Die Methode liefer alle Mitarbeiter anhand einer Rollen ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Employee> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Employee> list = new ArrayList<Employee>();

		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ROLLEN_ID, id));

		while (set.next()) {
			list.add(new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername")));

		}

		return list;

	}

	/**
	 * Die Methode liefer einen Mitarbeiter ohne Rollen anhand seiner Id zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Employee getMitarbeiterByIdForNachricht(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Employee employee = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			employee = new Employee(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername"));
		}
		return employee;

	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand seines Namen zurück.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Employee> getMitarbeiterByName(String name) throws ConnectException, DAOException, SQLException {

		List<Employee> list = new ArrayList<Employee>();

		ResultSet set = getManaged(GET_MITARBEITER_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Employee(set.getLong(ID), set.getString(NAME), set.getString(VORNAME), set.getString(EMAIL), set.getString(PASSWORT),
					set.getString(EINTRITTSDATUM), set.getString(AUSTRITTSDATUM), RollenDAO.getInstance().getRollenByMitarbeiterId(set.getLong(ID)),
					set.getString(BENUTZERNAME)));
		}

		return list;
	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand seines Benutzernamen zurück.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Employee getMitarbeiterByBenutzername(String name) throws ConnectException, DAOException, SQLException {

		Employee employee = new Employee();

		ResultSet set = getManaged(GET_MITARBEITER_BY_BENUTZERNAME + name + "'");

		while (set.next()) {
			employee = new Employee(set.getLong(ID), set.getString(NAME), set.getString(VORNAME), set.getString(EMAIL),
					set.getString(PASSWORT), set.getString(EINTRITTSDATUM), set.getString(AUSTRITTSDATUM),
					getRollenByMitarbeiterId(set.getLong(ID)), set.getString(BENUTZERNAME));
		}

		return employee;
	}

	/**
	 * Die Methode erzeugt einen Mitarbeiter in der Datenbank.
	 * 
	 * @param employee
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createMitarbeiter(Employee employee) throws ConnectException, DAOException, SQLException {
		String inserq = "INSERT INTO " + TABLE + "(" + NAME + "," + VORNAME + "," + EMAIL + "," + PASSWORT + "," + EINTRITTSDATUM + ","
				+ AUSTRITTSDATUM + "," + BENUTZERNAME + ")" + "VALUES" + "('" + employee.getName() + "','" + employee.getVorname() + "','"
				+ employee.getEmail() + "','" + employee.getPasswort() + "','" + employee.getEintrittsdatum() + "','"
				+ employee.getAustrittsdatum() + "','" + employee.getBenutzername() + "')";
		this.putManaged(inserq);

		List<Employee> mitarbeiterlist = getAllMitarbeiter();

		Employee mitarbeiterdb = getMitarbeiterById(mitarbeiterlist.get(mitarbeiterlist.size() - 1).getId());

		if (employee.getRollen() != null) {
			for (int i = 0; i < employee.getRollen().size(); i++) {

				MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(mitarbeiterdb, employee.getRollen().get(i));

			}
		}
	}

	/**
	 * Die Methode aktualisiert einen Mitarbeiter mitsamt seiner dazugehörigen
	 * Rollen in der Datenbank.
	 * 
	 * @param employee
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateMitarbeiter(Employee employee) throws ConnectException, DAOException, SQLException {
		String updateq = "UPDATE " + TABLE + " SET " + NAME + "='" + employee.getName() + "'," + VORNAME + "='" + employee.getVorname()
				+ "'," + EMAIL + "='" + employee.getEmail() + "'," + PASSWORT + "='" + employee.getPasswort() + "'," + EINTRITTSDATUM + "='"
				+ employee.getEintrittsdatum() + "'," + AUSTRITTSDATUM + "='" + employee.getAustrittsdatum() + "'," + BENUTZERNAME + "='"
				+ employee.getBenutzername() + "' WHERE " + ID + "='" + employee.getId() + "'";
		this.putManaged(updateq);
		if (employee.getRollen() != null) {
			for (int i = 0; i < employee.getRollen().size(); i++) {
				MitarbeiterHasRollenDAO.getInstance().deleteMitarbeiterHasRollen(employee);

			}
			for (int i = 0; i < employee.getRollen().size(); i++) {
				MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(employee, employee.getRollen().get(i));
			}
		}
	}

	/**
	 * Die Methode liefert die Rollen zur einer Mitarbeiter ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Rollen> getRollenByMitarbeiterId(Long id) throws ConnectException, DAOException, SQLException {

		List<Rollen> list = new ArrayList<Rollen>();

		ResultSet set = getManaged(MessageFormat.format(GET_ROLLEN_BY_MITARBEITER_ID, id));

		while (set.next()) {
			list.add(new Rollen(set.getLong("id"), set.getString("name"), getNachrichtByRolleId(set.getLong("id"))));

		}

		return list;

	}

	/**
	 * Methode um alle Nachrichten zu einer RolleId zu bekommen.
	 * 
	 * @param rolle
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Nachricht> getNachrichtByRolleId(Long rid) throws ConnectException, DAOException, SQLException {

		List<Nachricht> list = new ArrayList<Nachricht>();

		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_ROLLE_ID, rid));

		while (set.next()) {
			list.add(new Nachricht(set.getLong("id"), set.getString("nachricht"), getMitarbeiterByIdForNachricht(set.getLong("sender_fk")),
					new Rollen()));
		}

		return list;
	}
}

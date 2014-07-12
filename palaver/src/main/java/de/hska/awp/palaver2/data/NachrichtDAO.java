package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.InternMessage;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.employee.service.EmployeeService;

/**
 * Die Klasse stellt Methoden für den Datenbankzugriff für das Objekt Nachricht
 * bereit.
 * 
 * @author PhilippT
 * @author PhilippT
 */

public class NachrichtDAO extends AbstractDAO {

	private static final String GET_ALL_NACHRICHTEN = "SELECT * FROM nachrichten";
	private static final String GET_NACHRICHT_BY_ID = "SELECT * FROM nachrichten WHERE id = {0}";
	private static final String GET_NACHRICHT_BY_Rolle = "SELECT * FROM nachrichten WHERE empf_rolle_fk = {0}";
	private static final String GET_NACHRICHT_BY_ROLLE_ID = "SELECT * FROM nachrichten WHERE empf_rolle_fk = {0}";
	private static final String DELETE_NACHRICHT = "DELETE FROM nachrichten WHERE id = {0}";

	private static NachrichtDAO instance = null;

	public NachrichtDAO() {
		super();
	}

	public static NachrichtDAO getInstance() {
		if (instance == null) {
			instance = new NachrichtDAO();
		}
		return instance;
	}

	/**
	 * Methode um eine Nachricht anhand der ID zu suchen
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */

	public InternMessage getNachrichtById(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		InternMessage internMessage = null;
		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_ID, id));

		while (set.next()) {
			internMessage = new InternMessage(set.getLong("id"), set.getString("nachricht"), new Employee(), new Rolle());
		}
		return internMessage;

	}

	/**
	 * Methode um eine Nachricht zu gegebener Rolle zu suchen
	 * 
	 * @param rolle
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<InternMessage> getNachrichtByRolle(Rolle rolle) throws ConnectException, DAOException, SQLException {

		if (rolle == null) {
			return null;
		}
		List<InternMessage> list = new ArrayList<InternMessage>();

		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_Rolle, rolle.getId()));

		while (set.next()) {
			list.add(new InternMessage(set.getLong("id"), set.getString("nachricht"), new Employee(), new Rolle()));
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
	public List<InternMessage> getNachrichtByRolleId(Long rid) throws ConnectException, DAOException, SQLException {

		List<InternMessage> list = new ArrayList<InternMessage>();

		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_ROLLE_ID, rid));

		while (set.next()) {
			list.add(new InternMessage(set.getLong("id"), set.getString("nachricht"), EmployeeService.getInstance()
					.getEmployee(set.getLong("sender_fk")), new Rolle()));
		}

		return list;
	}

	/**
	 * Methode um alle Nachrichten zu suchen, ausgeben zu lassen
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */

	public List<InternMessage> getAllNachricht() throws ConnectException, DAOException, SQLException {
		List<InternMessage> list = new ArrayList<InternMessage>();
		ResultSet set = getManaged(GET_ALL_NACHRICHTEN);
		while (set.next()) {
			list.add(new InternMessage(set.getLong("id"), set.getString("nachricht"), new Employee(), new Rolle()));
		}
		return list;
	}

	/**
	 * Methode um eine neue Nachricht anzulegen. Nachricht wird mitgeliefert
	 * 
	 * @param internMessage
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */

	public void createNachricht(InternMessage internMessage) throws ConnectException, DAOException, SQLException {

		if (internMessage == null) {
			throw new NullPointerException("keine Nachricht übergeben");

		}
		String anlegen = "Insert into nachrichten (nachricht, sender_fk, empf_rolle_fk) values('" + internMessage.getNachricht() + "',"
				+ internMessage.getMitarbeiterSender().getId() + "," + internMessage.getEmpfaengerRolle().getId() + ")";
		putManaged(anlegen);

	}

	/**
	 * Methode um eine vorhandene Nachricht zu gegebener ID zu löschen
	 * 
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteNachricht(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		}
		putManaged(MessageFormat.format(DELETE_NACHRICHT, id));
	}
}

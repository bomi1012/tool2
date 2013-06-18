package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * Die Klasse stellt Methoden f�r den Datenbankzugriff f�r das Objekt Rollen
 * bereit.
 * 
 * @author Christian Barth
 * 
 */
public class RollenDAO extends AbstractDAO {

	private final static String TABLE = "rollen";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String GET_ALL_ROLLEN = "SELECT * FROM rollen";
	private final static String GET_ROLLEN_BY_ID = "SELECT * FROM rollen WHERE id = {0}";
	private final static String GET_ROLLEN_BY_MITARBEITER_ID = "SELECT rollen.id, rollen.name FROM rollen join mitarbeiter_has_rollen on "
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
	 * Die Methode liefert alle Rollen aus der Datenbank zur�ck.
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
	 * Die Methode liefert ein Rolle anhand ihrer ID zur�ck.
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
	 * Die Methode liefert alle Rollen zur einer Mitarbeiter ID zur�ck.
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
	 * Die Methode liefert alle Nachrichten zur einer RollenId zur�ck.
	 * 
	 * @param Long
	 *            id
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
	 * Die Methode liefert eine Mitarbeiter ohne Rollen zur�ck anhand einer
	 * RollenId.
	 * 
	 * @param Long
	 *            id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Mitarbeiter> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();

		ResultSet set = getMany(MessageFormat.format(GET_MITARBEITER_BY_ROLLEN_ID, id));

		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername")));

		}

		return list;

	}

	/**
	 * Die Methode liefert eine Mitarbeiter in abgespeckter Form f�r die
	 * Nachrichten zur�ck.
	 * 
	 * @param Long
	 *            id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private Mitarbeiter getMitarbeiterByIdForNachricht(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Mitarbeiter mitarbeiter = null;
		ResultSet set = getMany(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			mitarbeiter = new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername"));
		}
		return mitarbeiter;

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
		String INSERTQUERY = "INSERT INTO " + TABLE + "(" + NAME + ")" + "VALUES" + "('" + rolle.getName() + "')";
		this.putManaged(INSERTQUERY);
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
		String UPDATEQUERY = "UPDATE " + TABLE + " SET " + NAME + "='" + rolle.getName() + "' WHERE " + ID + "='" + rolle.getId() + "'";
		this.putManaged(UPDATEQUERY);
	}

}

package de.hska.awp.palaver2.mitarbeiterverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;

/**
 * Die Klasse ermöglicht die Verwaltung der Mitarbeiter.
 * 
 * @author Christian Barth
 * 
 */
public class Mitarbeiterverwaltung extends MitarbeiterDAO {

	private static Mitarbeiterverwaltung instance = null;

	private Mitarbeiterverwaltung() {
		super();
	}

	public static Mitarbeiterverwaltung getInstance() {
		if (instance == null) {
			instance = new Mitarbeiterverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Mitarbeiter zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Employee> getAllMitarbeiter() throws ConnectException, DAOException, SQLException {

		List<Employee> result = null;

		result = super.getAllMitarbeiter();

		return result;
	}

	/**
	 * Die Methode liefert mehrere Mitarbeiter anhand ihres Namen zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Employee> getMitarbeiterByName(String name) throws ConnectException, DAOException, SQLException {

		List<Employee> result = null;

		result = super.getMitarbeiterByName(name);

		return result;
	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand der Id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return Mitarbeiter
	 */
	public Employee getMitarbeiterById(Long id) throws ConnectException, DAOException, SQLException {
		Employee employee = super.getMitarbeiterById(id);
		return employee;
	}

	/**
	 * Die Methode liefert alle Mitarbeiter zur einer RollenId zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mitarbeiter>
	 */
	public List<Employee> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Employee> result = null;

		result = super.getMitarbeiterByRollenId(id);

		return result;
	}

	/**
	 * Die Methode erzeugt einen Mitarbeiter.
	 */
	public void createMitarbeiter(Employee employee) throws ConnectException, DAOException, SQLException {

		super.createMitarbeiter(employee);
	}

	/**
	 * Die Methode aktualisiert einen Mitarbeiter.
	 */
	public void updateMitarbeiter(Employee employee) throws ConnectException, DAOException, SQLException {

		super.updateMitarbeiter(employee);
	}

}

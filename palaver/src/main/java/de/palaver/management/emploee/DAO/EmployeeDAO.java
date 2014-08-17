package de.palaver.management.emploee.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.RolleService;
import de.palaver.management.info.person.service.KontakteService;

public class EmployeeDAO extends EmployeeHasRollenDAO {
	private static final long serialVersionUID = 189499847070124261L;
	private static final String VORNAME = "vorname";
	private static final String PASSWORT = "passwort";
	private static final String EINTRITTSDATUM = "eintrittsdatum";
	private static final String AUSTRITTSDATUM = "austrittsdatum";
	private static final String BENUTZERNAME = "benutzername";
	private final static String FIELD_KONTAKTE_FK = "kontakte_fk";

	private static final String GET_ALL_EMPLOYEES = "SELECT * FROM mitarbeiter";
	private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM mitarbeiter WHERE id = {0}";
	private static final String GET_EMPLOYEE_BY_USERNAME = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + 
			BENUTZERNAME + " = {0}";
	
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE_EMPLOYEE + 
			" (`name`, `vorname`, `passwort`, `eintrittsdatum`, `austrittsdatum`, `benutzername`, `kontakte_fk`)" +
			" VALUES({0},{1},{2},{3},{4},{5},{6})";
	
	private static final String UPDATE_QUERY = "UPDATE " + TABLE_EMPLOYEE + " SET " + 
			"`name` = {0}, `vorname` = {1}, `eintrittsdatum` = {2}, `austrittsdatum` = {3}, " +
			"`benutzername` = {4}, `kontakte_fk` = {5} WHERE " + FIELD_ID + " = {6}";
	
	private static final String UPDATE_PASSWORD_QUERY = "UPDATE " + TABLE_EMPLOYEE + " SET " + 
			"`passwort` = {0} WHERE " + FIELD_ID + " = {1}";
	
	private static EmployeeDAO instance = null;

	public static EmployeeDAO getInstance() {
		if (instance == null) {
			instance = new EmployeeDAO();
		}
		return instance;
	}

	private ArrayList<Employee> m_list;
	private Employee m_employee;
	private String ein;
	private String aus;
	private String kontakt;

	public EmployeeDAO() {
		super();
	}

	public List<Employee> getAllEmployees() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Employee>();
		m_set = getManaged(GET_ALL_EMPLOYEES);
		while (m_set.next()) {
			m_list.add(setEmployee(m_set));
		}		
		return m_list;
	}	
	
	public Employee getEmployeeById(Long id) throws ConnectException, DAOException, SQLException {
		m_set = getManaged(MessageFormat.format(GET_EMPLOYEE_BY_ID, id));
		while (m_set.next()) {
			m_employee = setEmployee(m_set);
		}
		return m_employee;
	}

	public Employee getEmployeeByUsername(String name) throws ConnectException, DAOException, SQLException {
		m_set = getManaged(MessageFormat.format(GET_EMPLOYEE_BY_USERNAME, "'" + name + "'"));
		while (m_set.next()) {
			m_employee = setEmployee(m_set);
		}
		return m_employee;
	}
	
	public Long create(Employee employee) throws ConnectException, DAOException {
		setFields(employee);
		return insert(MessageFormat.format(INSERT_QUERY,
				"'" + employee.getName() + "'",
				"'" + employee.getVorname() + "'",
				"'" + employee.getPasswort() + "'", ein, aus,
				"'" + employee.getBenutzername() + "'", kontakt));
	}
	
	public void update(Employee employee) throws ConnectException, DAOException {
		setFields(employee);		
		putManaged(MessageFormat.format(UPDATE_QUERY,
				"'" + employee.getName() + "'",
				"'" + employee.getVorname() + "'", ein, aus,
				"'" + employee.getBenutzername() + "'", kontakt, 
				employee.getId()));
		
	}
	
	public void removeEmployee(Long employeeId) throws ConnectException, DAOException {
		putManaged("DELETE FROM " + TABLE_EMPLOYEE + " WHERE " + FIELD_ID + " = " + employeeId);
	}
	
	public void changePassword(String passwort, Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(UPDATE_PASSWORD_QUERY,
				"'" + passwort + "'", id));
	}
	
	
	private Employee setEmployee(ResultSet set) throws SQLException, ConnectException, DAOException {
		return new Employee(
				set.getLong(FIELD_ID), set.getString(FIELD_NAME), 
				set.getString(VORNAME), set.getString(PASSWORT), 
				set.getString(EINTRITTSDATUM), set.getString(AUSTRITTSDATUM), 
				RolleService.getInstance().getRollesByLieferantId(set.getLong(FIELD_ID)),
				set.getString(BENUTZERNAME), 
				KontakteService.getInstance().getKontakteById(set.getLong(FIELD_KONTAKTE_FK)));
	}

	private void setFields(Employee employee) {
		setFieldsToNull();
		if (employee.getKontakt() != null) { kontakt = String.valueOf(employee.getKontakt().getId()); }
		if (employee.getEintrittsdatum() != null) { ein = "'" + employee.getEintrittsdatum() + "'"; }
		if (employee.getAustrittsdatum() != null) { aus = "'" + employee.getAustrittsdatum() + "'"; }
	}

	private void setFieldsToNull() {
		ein = null;
		aus = null;
		kontakt = null;
	}

}

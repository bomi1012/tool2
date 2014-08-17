package de.palaver.management.employee.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.emploee.DAO.EmployeeDAO;
import de.palaver.management.emploee.DAO.EmployeeHasRollenDAO;

public class EmployeeService {

	private static EmployeeService instance = null;

	private EmployeeService() {
		super();
	}

	public static EmployeeService getInstance() {
		if (instance == null) {
			instance = new EmployeeService();
		}
		return instance;
	}
	
	public List<Employee> getAllEmployees() throws ConnectException, DAOException, SQLException {
		return EmployeeDAO.getInstance().getAllEmployees();	
	}
	
	/**
	 * @param obj 
	 * 		 	{@link String} ==> 	getByBenutzerName oder 
	 * 			{@link Long} ==> 	getById
	 * @return {@link Employee}
	 */
	public Employee getEmployee(Object obj) throws ConnectException, DAOException, SQLException {
		if (obj instanceof Long) {
			return EmployeeDAO.getInstance().getEmployeeById((Long) obj);
		} else if (obj instanceof String) {
			return EmployeeDAO.getInstance().getEmployeeByUsername((String) obj);
		}
		return null;
	}

	public Long create(Employee employee) throws ConnectException, DAOException {
		Long employeeId = EmployeeDAO.getInstance().create(employee);			
		createRelation(employeeId, employee.getRollen());
		return employeeId;
	}
	
	public void update(Employee employee) throws ConnectException, DAOException {
		EmployeeDAO.getInstance().update(employee);
		removeRelation(employee.getId());
		createRelation(employee.getId(), employee.getRollen());
	}

	public void removeEmployee(Long employeeId) throws ConnectException, DAOException {
		removeRelation(employeeId);
		EmployeeDAO.getInstance().removeEmployee(employeeId);
	}


	private void createRelation(Long employeeId, List<Rolle> rollen) throws ConnectException, DAOException {
		if (rollen.size() > 0) {
			EmployeeHasRollenDAO.getInstance().createRelation(employeeId, rollen);
		}
	}	
	
	
	private void removeRelation(Long employeeId) throws ConnectException, DAOException {
		EmployeeHasRollenDAO.getInstance().deleteRelationsByEmployeeId(employeeId);		
	}

	public void changePassword(Employee employee) throws ConnectException, DAOException {
		EmployeeDAO.getInstance().changePassword(employee.getPasswort(), employee.getId());		
	}

}

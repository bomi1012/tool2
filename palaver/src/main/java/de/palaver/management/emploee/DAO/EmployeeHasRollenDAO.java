package de.palaver.management.emploee.DAO;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Rolle;

public class EmployeeHasRollenDAO extends AbstractDAO implements Serializable{

	private static final long serialVersionUID = -7510518269404275134L;
	protected static final String TABLE_EMPLOYEE = "mitarbeiter";
	protected static final String TABLE_ROLLE = "rollen";
	protected static final String TABLE_EMPLOYEE_ROLLE = "mitarbeiter_has_rollen";
	
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE_EMPLOYEE_ROLLE + 
			" (`mitarbeiter_fk`, `rollen_fk`) VALUES({0},{1})";
	
	private static final String DELETE_BY_EMPLOYEE_ID_QUERY = "DELETE FROM " + TABLE_EMPLOYEE_ROLLE + " WHERE " +
			"`mitarbeiter_fk` = {0}";
	
	private static EmployeeHasRollenDAO instance = null;

	public static  EmployeeHasRollenDAO getInstance() {
		if (instance == null) {
			instance = new  EmployeeHasRollenDAO();
		}
		return instance;
	}
	
	public EmployeeHasRollenDAO() {
		super();
	}

	public void createRelation(Long employeeId, List<Rolle> rollen) throws ConnectException, DAOException {
		for (Rolle rolle : rollen) {
			putManaged(MessageFormat.format(INSERT_QUERY, employeeId, rolle.getId()));
		}		
	}

	public void deleteRelationsByEmployeeId(Long employeeId) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_BY_EMPLOYEE_ID_QUERY, employeeId));		
	}
}

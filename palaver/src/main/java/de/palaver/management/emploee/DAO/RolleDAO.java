package de.palaver.management.emploee.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.emploee.Rolle;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class RolleDAO extends EmployeeHasRollenDAO {
	private static final long serialVersionUID = -8513973754601711817L;

	private static RolleDAO instance = null;

	private static final String GET_ALL_ROLLES = "SELECT * FROM " + TABLE_ROLLE;	
	private static final String GET_ROLLES_BY_EMPLOYEE_ID = "SELECT r.* FROM " + TABLE_ROLLE + " r " +
			"JOIN " + TABLE_EMPLOYEE_ROLLE + " er " +
			"ON r.id = er.rollen_fk " +
			"WHERE er.mitarbeiter_fk = {0}";
	
	private ArrayList<Rolle> m_list;
	
	public static RolleDAO getInstance() {
		if (instance == null) {
			instance = new RolleDAO();
		}
		return instance;
	}

	public RolleDAO() {
		super();
	}

	public List<Rolle> getRollesByEmployeeId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Rolle>();
		if (id != 0) {
			m_set = getManaged(MessageFormat.format(GET_ROLLES_BY_EMPLOYEE_ID, id));
			while (m_set.next()) {
				m_list.add(setRolleSimple(m_set));
			}	
		}
		return m_list;
	}
	
	public List<Rolle> getAllRolles() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Rolle>();
		m_set = getManaged(GET_ALL_ROLLES); 
		while (m_set.next()) {
			m_list.add(setRolleSimple(m_set)); //FIXME wenn Nachrichten!!
		}
		return m_list;
	}
	
	

	private Rolle setRolleSimple(ResultSet set) throws SQLException, ConnectException, DAOException {
		return new Rolle(set.getLong(FIELD_ID), set.getString(FIELD_NAME));
	}




}

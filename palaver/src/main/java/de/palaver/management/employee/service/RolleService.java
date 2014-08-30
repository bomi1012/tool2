package de.palaver.management.employee.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.emploee.Rolle;
import de.palaver.management.emploee.DAO.RolleDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class RolleService {

	private static RolleService instance = null;

	private RolleService() {
		super();
	}

	public static RolleService getInstance() {
		if (instance == null) {
			instance = new RolleService();
		}
		return instance;
	}
		
	public List<Rolle> getRollesByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		return RolleDAO.getInstance().getRollesByEmployeeId(id);
	}

	public List<Rolle> getAllRolles() throws ConnectException, DAOException, SQLException {
		return RolleDAO.getInstance().getAllRolles();
	}

	public void updateRolle(Rolle rollenupdate) {
		// TODO Auto-generated method stub
		
	}
}

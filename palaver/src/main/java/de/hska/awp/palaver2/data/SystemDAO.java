/**
 * Sebastian Walz
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

public class SystemDAO extends AbstractDAO {
	private static SystemDAO instance = null;

	private final static String TEST_CONNECTION = "SELECT 1";

	private SystemDAO() {
		super();
	}

	public static SystemDAO getInstance() {
		if (instance == null) {
			instance = new SystemDAO();
		}
		return instance;
	}

	public String testConnection() throws ConnectException, DAOException, SQLException {
		String result = "FAIL";

		ResultSet set = getManaged(TEST_CONNECTION);

		while (set.next()) {
			result = set.getString(1);
		}

		return result;
	}
}

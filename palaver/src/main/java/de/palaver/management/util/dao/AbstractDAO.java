package de.palaver.management.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

@SuppressWarnings("restriction")
public abstract class AbstractDAO {
	protected static final String FIELD_ID = "id";
	protected static final String FIELD_NAME = "name";
	protected Connector m_connector;
	private Statement m_statement;
	protected ResultSet m_set;
	protected long m_lastId;

	public AbstractDAO() {
		super();
		m_connector = new Connector();
	}

	@SuppressWarnings({ "resource" })
	protected synchronized ResultSet getManaged(String query)
			throws ConnectException, DAOException, SQLException {
		openConnection();
		ResultSet result = null;
		CachedRowSet cache = new CachedRowSetImpl();
		try {
			result = m_statement.executeQuery(query);
			cache.populate(result);
		} catch (Exception e) {
			throw new DAOException("Statement error: " + query
					+ " caused by: " + e.toString());
		} finally {
			closeConnection();
			if (result != null) {
				result.close();
			}
		}
		return cache.getOriginal();
	}

	protected synchronized Long insert(String query) throws ConnectException,
			DAOException {
		openConnection();
		try {
			m_statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			m_set = m_statement.getGeneratedKeys();
			m_set.next();
			m_lastId = m_set.getLong(1);
		} catch (Exception e) {
			throw new DAOException("Statement error: " + query);
		} finally {
			closeConnection();
		}
		return m_lastId;
	}

	protected synchronized void putManaged(String query)
			throws ConnectException, DAOException {
		openConnection();
		try {
			m_statement.executeUpdate(query);
		} catch (Exception e) {
			throw new DAOException("Statement error: " + query);
		} finally {
			closeConnection();
		}
	}

	@Deprecated
	protected ResultSet get(String query) throws SQLException {
		ResultSet result = null;
		result = m_statement.executeQuery(query);
		return result;
	}

	protected void openConnection() throws ConnectException {
		m_connector.connect();
		m_statement = m_connector.getStmt();
	}

	protected void closeConnection() throws ConnectException, DAOException {
		m_connector.disconnect();
		try {
			m_statement.close();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
	}
}
/*
 * DAO.java
 *
 * created at 28.11.2012 09:12:11  by Sebastian Walz
 *
 */
package de.hska.awp.palaver.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public abstract class AbstractDAO
{
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
	
	@SuppressWarnings({ "resource", "restriction" })
	protected synchronized ResultSet getManaged(String querry) throws ConnectException, DAOException, SQLException  {
		openConnection();

		ResultSet result = null;
		CachedRowSet cache = new CachedRowSetImpl();
		try  {
			result = m_statement.executeQuery(querry);
			cache.populate(result);	
		} 
		catch (Exception e) {
			throw new DAOException("Statement error: " + querry + " caused by: " + e.toString());
		}
		finally  {
			closeConnection();
			if (result != null)
			{
				result.close();
			}	
		}
		return cache.getOriginal();
	}
	
	@SuppressWarnings({ "resource", "restriction" })
	protected synchronized ResultSet getMany(String querry) throws ConnectException, DAOException, SQLException  {
		ResultSet result = null;
		CachedRowSet cache = new CachedRowSetImpl();
		try  {
			result = m_statement.executeQuery(querry);
			cache.populate(result);	
		} 
		catch (Exception e)  {
			throw new DAOException("Statement error: " + querry + " caused by: " + e.toString());
		}
		
		return cache.getOriginal();
	}
	
	protected synchronized Long putManaged(String querry) throws ConnectException, DAOException  {
		openConnection();
		try  {
			m_statement.executeUpdate(querry, Statement.RETURN_GENERATED_KEYS);
			m_set = m_statement.getGeneratedKeys();
			m_set.next();
			m_lastId = m_set.getLong(1);
		} 
		catch (Exception e)  {
			throw new DAOException("Statement error: " + querry);
		}
		finally {
			closeConnection();	
		}
		return m_lastId;
	}
	
	@Deprecated
	protected ResultSet get(String querry) throws SQLException {
		ResultSet result = null;
		result = m_statement.executeQuery(querry);
		
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
		}
		catch (SQLException e) {
			throw new DAOException(e.toString());
		}
	}
}
package de.hska.awp.palaver.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Driver;

import de.hska.awp.palaver2.util.IConstants;

public class Connector {
	private Statement stmt;

	private Connection connection;

	public Connector(Statement stmt, Connection connection) {
		super();
		this.stmt = stmt;
		this.connection = connection;
	}

	public Connector() {
		super();
	}

	public Statement getStmt() {
		return stmt;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) throws SQLException {
		this.connection = connection;
		this.stmt = connection.createStatement();
	}

	public void connect() throws ConnectException {
		try {
			// Create a connection to the database
			connection = new Driver().connect(IConstants.DB_CONNECTION_URL, new Properties());
			stmt = connection.createStatement();
			// InitialContext cxt = new InitialContext();
			//
			// DataSource ds = (DataSource) cxt.lookup(
			// "java:/comp/env/jdbc/palaverDB" );

			// stmt = ds.getConnection().createStatement();
		} catch (SQLException e) {
			throw new ConnectException("Connection failed.");
		}
		// catch (NamingException e)
		// {
		// throw new ConnectException("Connection failed at Java Naming.");
		// }
	}

	public void disconnect() throws ConnectException {
		try {
			stmt.close();
			connection.close();
		} catch (Exception e) {
			throw new ConnectException("Problem while closing connection.");
		}
	}
}
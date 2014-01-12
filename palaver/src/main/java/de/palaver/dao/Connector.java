package de.palaver.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Driver;

import de.hska.awp.palaver2.util.IConstants;

public class Connector {
	private Statement m_stmt;
	private Connection m_connection;

	public Connector(Statement stmt, Connection connection) {
		super();
		this.m_stmt = stmt;
		this.m_connection = connection;
	}

	public Connector() {
		super();
	}

	public Statement getStmt() {
		return m_stmt;
	}

	public Connection getConnection() {
		return m_connection;
	}

	public void setConnection(Connection connection) throws SQLException {
		this.m_connection = connection;
		this.m_stmt = connection.createStatement();
	}

	public void connect() throws ConnectException {
		try {
			m_connection = new Driver().connect(IConstants.DB_CONNECTION_URL, new Properties());
			m_stmt = m_connection.createStatement();
		} catch (SQLException e) {
			throw new ConnectException("Connection failed.");
		}
	}

	public void disconnect() throws ConnectException {
		try {
			m_stmt.close();
			m_connection.close();
		} catch (Exception e) {
			throw new ConnectException("Problem while closing connection.");
		}
	}
}
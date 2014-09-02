package de.palaver.management.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimplyCRUD extends AbstractDAO {

	protected ResultSet findAll(String table) throws ConnectException, DAOException, SQLException {
		return getManaged("SELECT * FROM " + table);		
	}
	
	protected Long create(String table, String name) throws ConnectException, DAOException {
		return insert("INSERT INTO " + table + "(" + FIELD_NAME + ") VALUES('" + name + "')");
	}
	
	protected void update(String table, String name, Long id) throws ConnectException, DAOException, SQLException {
		putManaged("UPDATE " + table + " SET " + FIELD_NAME + " = '"
				+ name + "'" + " WHERE " + FIELD_ID + " = '" + id + "'");
	}
}

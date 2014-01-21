package de.palaver.dao.emailversand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.emailversand.Mail;

public class MailDAO extends AbstractDAO{

	private static MailDAO instance = null;
	private final static String TABLE = "email";
	private final static String FIELD_PASSWORD = "passwort";
	private final static String FIELD_KEY = "schlussel";
	private final static String FIELD_LENGTH = "pw_length";
	private final static String FIELD_DESCRIPTION = "descript";
	
	
	private final static String INSERT_QUERY = "INSERT INTO " + TABLE + "(" 
			+ "`" + FIELD_PASSWORD + "`, " + "`" + FIELD_KEY + "`, " + "`" + FIELD_LENGTH + "`, "
			+ "`" + FIELD_DESCRIPTION + "`)" + "VALUES({0},{1},{2},{3},{4})";
	private final static String GET_EMAIL_BY_ENUM = "SELECT * FROM " + TABLE + " WHERE " + FIELD_DESCRIPTION + " = {0}";
	private Mail m_mail;
	public MailDAO() {
		super();
	}

	public static MailDAO getInstance() {
		if (instance == null) {
			instance = new MailDAO();
		}
		return instance;
	}
	
	public Mail getMailByEnum(String filter) throws ConnectException, DAOException, SQLException{
		m_mail = null;
		m_set = getManaged(MessageFormat.format(GET_EMAIL_BY_ENUM, filter));
		while (m_set.next()) {
			m_mail = setMail(m_set);
		}
		return m_mail;

	}

	public void insertMail(Mail mail) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(
				INSERT_QUERY,
				"'" + mail.getPassword() + "'",
				"'" + mail.getKey() + "'",
				"'" + mail.getLength() + "'",
				"'" + mail.getDescription() + "'"));
	}
	
	
	private Mail setMail(ResultSet set) throws ConnectException, DAOException, SQLException {
		return 	new Mail(set.getLong(FIELD_ID), 
				set.getString(FIELD_PASSWORD), 
				set.getString(FIELD_KEY), 
				set.getLong(FIELD_LENGTH), 
				set.getString(FIELD_DESCRIPTION));	
	}
}

package de.palaver.alt.domain.emailversand;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
public class MailAuthenticator extends Authenticator{
	private String m_user;
    private String m_password;
     
    public MailAuthenticator(String user, String password) {
    	m_user = user;
        m_password = password;
    }
     
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(m_user, m_password);
    }
}

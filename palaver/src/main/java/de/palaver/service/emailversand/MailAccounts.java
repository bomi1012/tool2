package de.palaver.service.emailversand;

import de.palaver.domain.emailversand.MailAuthenticator;

/**
 * @author Mihon
 */
public enum MailAccounts {
	 NACHRICHT("smtp.1und1.de", 25, "bestellung@cafepalaver.de", "bestellung@cafepalaver.de", "nachricht");
     
	    private String m_smtpHost;
	    private int m_port;
	    private String m_username;
	    private String m_password;
	    private String m_email;
	     
	    private MailAccounts(String smtpHost, int port, String username, String email, String filter)
	    {
	        try {
		        m_smtpHost = smtpHost;
		        m_port = port;
		        m_username = username;
				m_password = MailService.getInstance().Password(filter);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        this.m_email = email;
	    }
	     
	    public int getPort()
	    {
	        return m_port;
	    }
	     
	    public String getSmtpHost()
	    {
	        return m_smtpHost;
	    }
	     
	    public MailAuthenticator getPasswordAuthentication() {
	        return new MailAuthenticator(m_username, m_password);
	    }
	     
	    public String getEmail()
	    {
	        return m_email;
	    }
	}
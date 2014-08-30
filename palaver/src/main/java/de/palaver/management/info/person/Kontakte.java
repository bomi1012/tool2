package de.palaver.management.info.person;

import de.palaver.management.util.entity.EntityId;

public class Kontakte extends EntityId {
	private static final long serialVersionUID = -1596761912104967382L;
	private String m_email;
	private String m_handy;
	private String m_telefon;
	private String m_fax;
	private String m_www;
	
	public String getEmail() {
		return m_email;
	}
	public void setEmail(String email) {
		m_email = email;
	}
	public String getHandy() {
		return m_handy;
	}
	public void setHandy(String handy) {
		m_handy = handy;
	}
	public String getTelefon() {
		return m_telefon;
	}
	public void setTelefon(String telefon) {
		m_telefon = telefon;
	}
	public String getFax() {
		return m_fax;
	}
	public void setFax(String fax) {
		m_fax = fax;
	}
	public String getWww() {
		return m_www;
	}
	public void setWww(String www) {
		m_www = www;
	}
	public Kontakte() {
		super();
	}
	public Kontakte(long id, String email, String handy, String telefon, String fax,
			String www) {
		super(id);
		m_email = email;
		m_handy = handy;
		m_telefon = telefon;
		m_fax = fax;
		m_www = www;
	}
	
	public Kontakte(String email, String handy, String telefon, String fax,
			String www) {
		super();
		m_email = email;
		m_handy = handy;
		m_telefon = telefon;
		m_fax = fax;
		m_www = www;
	}
	@Override
	public String toString() {
		String kontakte = "";
		if(m_telefon != null && m_telefon != "") {
			kontakte = "Tel.: " + m_telefon;
		} else if(m_handy != null && m_handy != "") {
			kontakte = "Handy: " + m_handy;
		} else if(m_fax != null && m_fax != "") {
			kontakte = "Fax: " + m_fax;
		}
		return kontakte;
	}
	
	
}

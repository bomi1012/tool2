package de.palaver.management.emploee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.palaver.domain.EntityName;

public class Employee extends EntityName implements Serializable {
	private static final long serialVersionUID = -590239735735958622L;
	
	private String m_vorname;
	public String getVorname() { return m_vorname; }
	public void setVorname(String vorname) { m_vorname = vorname; }
	
	private String m_benutzername;
	public String getBenutzername() { return m_benutzername; }
	public void setBenutzername(String benutzername) { m_benutzername = benutzername; }
	
	private String m_email;
	public String getEmail() { return m_email; }
	public void setEmail(String email) { m_email = email; }
	
	private String m_passwort;
	public String getPasswort() { return m_passwort; }
	public void setPasswort(String passwort) { m_passwort = passwort; }
	
	
	private String m_eintrittsdatum;
	public String getEintrittsdatum() { return m_eintrittsdatum; }
	public void setEintrittsdatum(String eintrittsdatum) { m_eintrittsdatum = eintrittsdatum; }
	
	private String m_austrittsdatum;
	public String getAustrittsdatum() { return m_austrittsdatum; }
	public void setAustrittsdatum(String austrittsdatum) { m_austrittsdatum = austrittsdatum; }
	
	private List<Rollen> m_rollen = new ArrayList<Rollen>();
	public List<Rollen> getRollen() { return m_rollen; }
	public void setRollen(List<Rollen> rollen) { m_rollen = rollen; }

	public Employee() {
		this(null, null, null, null, null, null, null, null);
	}
	
	public Employee(Long id, String name, String vorname, String email, String passwort, String eintrittsdatum, String austrittsdatum,
			String benutzername) {
		this(id, name, vorname, email, passwort, eintrittsdatum, austrittsdatum, null, benutzername);
	}

	public Employee(Long id, String name, String vorname, String email, String passwort, String eintrittsdatum, String austrittsdatum,
			List<Rollen> rollen, String benutzername) {
		super(id, name);
		m_vorname = vorname;
		m_email = email;
		m_passwort = passwort;
		m_eintrittsdatum = eintrittsdatum;
		m_austrittsdatum = austrittsdatum;
		m_rollen = rollen;
		m_benutzername = benutzername;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_benutzername == null) ? 0 : m_benutzername.hashCode());
		result = prime * result + ((m_id == null) ? 0 : m_id.hashCode());
		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
		result = prime * result + ((m_vorname == null) ? 0 : m_vorname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (m_benutzername == null) {
			if (other.m_benutzername != null)
				return false;
		} else if (!m_benutzername.equals(other.m_benutzername))
			return false;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		if (m_vorname == null) {
			if (other.m_vorname != null)
				return false;
		} else if (!m_vorname.equals(other.m_vorname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return m_vorname;
	}

}

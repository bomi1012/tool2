package de.palaver.management.emploee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.palaver.domain.EntityName;

public class Rollen extends EntityName implements Serializable {

	private static final long serialVersionUID = -3366000000412110979L;
	
	private List<Employee> m_employeeList = new ArrayList<Employee>();
	public List<Employee> getMitarbeiters() { return m_employeeList; }
	public void setMitarbeiters(List<Employee> employees) { m_employeeList = employees; }	
	
	private List<Nachricht> m_nachrichtList = new ArrayList<Nachricht>();
	public List<Nachricht> getNachrichten() { return m_nachrichtList; }
	public void setNachrichten(List<Nachricht> nachrichten) { m_nachrichtList = nachrichten; }
	
	public static final String	ADMINISTRATOR = "Chef";
	public static final String	BESTELLER = "Besteller";
	public static final String	EINKAUF = "Einkauf";
	public static final String	KOCH = "Koch";
	public static final String	SPEISEPLAN = "Speiseplan freigeben";

	public Rollen() {
		this(null, null, null);
	}
	
	public Rollen(Long id, String name, List<Nachricht> nachrichten) {
		this(id, name, null, nachrichten);
	}

	public Rollen(Long id, String name, List<Employee> employees, List<Nachricht> nachrichten) {
		super(id, name);
		m_employeeList = employees;
		m_nachrichtList = nachrichten;
	}

	@Override
	public String toString() {
		return m_name;
	}

}

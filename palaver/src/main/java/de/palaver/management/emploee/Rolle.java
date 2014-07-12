package de.palaver.management.emploee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.palaver.domain.EntityName;

public class Rolle extends EntityName implements Serializable {

	private static final long serialVersionUID = -3366000000412110979L;
	
	private List<Employee> m_employeeList = new ArrayList<Employee>();
	public List<Employee> getMitarbeiters() { return m_employeeList; }
	public void setMitarbeiters(List<Employee> employees) { m_employeeList = employees; }	
	
	private List<InternMessage> m_nachrichtList = new ArrayList<InternMessage>();
	public List<InternMessage> getNachrichten() { return m_nachrichtList; }
	public void setNachrichten(List<InternMessage> nachrichten) { m_nachrichtList = nachrichten; }
	
	public static final String	ADMINISTRATOR = "Chef";
	public static final String	BESTELLER = "Besteller";
	public static final String	EINKAUF = "Einkauf";
	public static final String	KOCH = "Koch";
	public static final String	SPEISEPLAN = "Speiseplan freigeben";

	public Rolle() {
		super();
	}
	
	public Rolle(Long id, String name) {
		this(id, name, null, null);
	}

	public Rolle(Long id, String name, List<Employee> employees, List<InternMessage> nachrichten) {
		super(id, name);
		m_employeeList = employees;
		m_nachrichtList = nachrichten;
	}


	@Override
	public String toString() {
		return m_name;
	}
}

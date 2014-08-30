package de.palaver.domain;

import de.palaver.management.emploee.Employee;

public class EntityKoch extends EntityName{
	private static final long serialVersionUID = -4382072469347944932L;

	protected Employee m_employee;
	public Employee getEmployee() { return m_employee; }
	public void setEmployee(Employee employee) { m_employee = employee; }
	
	public EntityKoch() {
		super();
	}
	
	public EntityKoch(Long id, String name, Employee employee) {
		super(id, name);
		m_employee = employee;
	}
}

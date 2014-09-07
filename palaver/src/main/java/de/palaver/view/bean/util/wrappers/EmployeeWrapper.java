package de.palaver.view.bean.util.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Label;

import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.employee.service.EmployeeService;

public class EmployeeWrapper {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeWrapper.class.getName());
	
	private Employee m_employee;
	public Employee getEmployee() { return m_employee; }
	
	private Label m_username = new Label();
	public Label getUsername() { return m_username; }
	
	private Label m_fullname = new Label();
	public Label getFullname() { return m_fullname; }
	
	private Label m_email = new Label();
	public Label getEmail() { return m_email; }
	
	private Label m_rolles = new Label();
	public Label getRolles() { return m_rolles; }
	
	public EmployeeWrapper(Employee employee) {
		m_employee = employee;
		m_username.setValue(employee.getBenutzername());
		m_fullname.setValue(employee.getVorname() + " " + employee.getName());
		if (employee.getKontakt() != null && StringUtils.isNotEmpty(employee.getKontakt().getEmail())) {
			m_email.setValue(employee.getKontakt().getEmail());
		}
		if (employee.getRollen().size() > 0) {
			String rolles = "";
			for (Rolle r : employee.getRollen()) {
				rolles += " " + r.getName() + ",";
			}
			rolles = rolles.substring( 0, rolles.length() - 1 );
			m_rolles.setValue(rolles);
		}
	}
	
	
	public static List<EmployeeWrapper> getEmployeeWrappers() {
		List<EmployeeWrapper> wrappers = new ArrayList<EmployeeWrapper>();
		try {
			for (Employee emp : EmployeeService.getInstance().getAllEmployees()) {
				wrappers.add(new EmployeeWrapper(emp));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		return wrappers;
	}
}

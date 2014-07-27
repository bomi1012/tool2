package de.palaver.management.recipe;

import java.sql.Date;
import java.util.List;

import de.palaver.domain.EntityName;
import de.palaver.management.emploee.Employee;

public class Recipe extends EntityName {
	private static final long serialVersionUID = 7984117576450240771L;

	private Recipetype m_recipetype;
	public Recipetype getRezeptart() { return m_recipetype; }
	public void setRezeptart(Recipetype recipetype) { m_recipetype = recipetype; }
	
	private Employee m_employee;
	public Employee getEmployee() { return m_employee; }
	public void setEmployee(Employee employee) { m_employee = employee; }
	
	private String m_kommentar;
	public String getKommentar() { return m_kommentar; }
	public void setKommentar(String kommentar) { m_kommentar = kommentar; }
	
	private Date m_erstellt;
	public Date getErstellt() { return m_erstellt; }
	public void setErstellt(Date erstellt) { m_erstellt = erstellt; }
	
	private Boolean m_aktiv;
	public Boolean getAktiv() { return m_aktiv; }
	public void setAktiv(Boolean aktiv) { m_aktiv = aktiv; }
	
	private List<RezeptHasArtikel> m_rezeptHasArtikelList;
	public List<RezeptHasArtikel> getRezeptHasArtikelList() { return m_rezeptHasArtikelList; }
	public void setRezeptHasArtikelList(List<RezeptHasArtikel> artikel) { m_rezeptHasArtikelList = artikel; }
	
	private Boolean m_menue;
	public Boolean getMenue() { return m_menue; }
	public void setMenue(Boolean menue) { m_menue = menue; }	
	
	private List<Zubereitung> m_zubereitung;
	public List<Zubereitung> getZubereitung() { return m_zubereitung; }
	public void setZubereitung(List<Zubereitung> zubereitung) { m_zubereitung = zubereitung; }

	public Recipe() {
		super();
	}

	public Recipe(Recipetype recipetype, Employee employee, String name,
			String kommentar) {
		super(name);
		m_recipetype = recipetype;
		m_employee = employee;
		m_kommentar = kommentar;
	}

	public Recipe(Long id, Recipetype recipetype, Employee employee,
			String name, String kommentar) {
		super(id, name);
		this.m_recipetype = recipetype;
		this.m_employee = employee;
		this.m_kommentar = kommentar;
	}


}

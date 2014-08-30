package de.palaver.management.recipe;

import java.util.Date;
import java.util.List;

import de.palaver.management.emploee.Employee;
import de.palaver.management.util.entity.EntityKoch;

public class Recipe extends EntityKoch {
	private static final long serialVersionUID = 7984117576450240771L;

	private Recipetype m_recipetype;
	public Recipetype getRecipetype() { return m_recipetype; }
	public void setRecipetype(Recipetype recipetype) { m_recipetype = recipetype; }
	
	private String m_kommentar;
	public String getKommentar() { return m_kommentar; }
	public void setKommentar(String kommentar) { m_kommentar = kommentar; }
	
	private Date m_erstellt;
	public Date getErstellt() { return m_erstellt; }
	public void setErstellt(Date erstellt) { m_erstellt = erstellt; }
	
	//////////////////////////////////////////////////////
	
	private List<RezeptHasArtikel> m_rezeptHasArtikelList;
	public List<RezeptHasArtikel> getRezeptHasArtikelList() { return m_rezeptHasArtikelList; }
	public void setRezeptHasArtikelList(List<RezeptHasArtikel> artikel) { m_rezeptHasArtikelList = artikel; }

	private List<Zubereitung> m_zubereitungList;
	public List<Zubereitung> getZubereitungList() { return m_zubereitungList; }
	public void setZubereitungList(List<Zubereitung> zubereitung) { m_zubereitungList = zubereitung; }
	
	public Recipe() {
		super();
	}
	
	/**
	 * Entity - Table
	 * @param id
	 * @param name
	 * @param recipetype
	 * @param employee
	 * @param kommentar
	 */
	public Recipe(Long id, String name, Recipetype recipetype, Employee employee, String kommentar, Date erstellt ) {
		super(id, name, employee);
		m_recipetype = recipetype;
		m_kommentar = kommentar;
		m_erstellt = erstellt;
	}
	
	public Recipe(Long id, Recipetype recipetype, Employee employee, String name, String kommentar) {
		this(id, name, recipetype, employee, kommentar, null);
	}
}

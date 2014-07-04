/**
 * Created by Michael Marschall
 */
package de.bistrosoft.palaver.rezeptverwaltung.domain;

import java.sql.Date;
import java.util.List;

import de.palaver.management.emploee.Employee;

public class Rezept implements java.io.Serializable {

	private static final long serialVersionUID = 7984117576450240771L;

	// Variablen
	private Long id;
	private Rezeptart rezeptart;
	private Employee employee;
	private String name;
	private String kommentar;
	private Date erstellt;
	private Boolean aktiv;
	private List<RezeptHasArtikel> artikel;
	private Boolean menue;
	private List<Zubereitung> zubereitung;

	// Konstruktoren
	public Rezept() {
		super();
	}

	public Rezept(Rezeptart rezeptart, Employee employee, String name,
			String kommentar) {
		super();
		this.rezeptart = rezeptart;
		this.employee = employee;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Rezept(Long id, Rezeptart rezeptart, Employee employee,
			String name, String kommentar) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.employee = employee;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Rezept(Long id, Rezeptart rezeptart, Employee employee,
			String name, String kommentar, Date erstellt) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.employee = employee;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
	}
	
	public Rezept(Long id, Rezeptart rezeptart, Employee employee,
			String name, String kommentar, Date erstellt, Boolean aktiv) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.employee = employee;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
		this.aktiv=aktiv;
	}

	public Rezept(Long id) {
		super();
		this.id = id;
	}

	// Getter- und Setter
	public Boolean getMenue() {
		return menue;
	}

	public void setMenue(Boolean menue) {
		this.menue = menue;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Zubereitung> getZubereitung() {
		return zubereitung;
	}

	public void setZubereitung(List<Zubereitung> zubereitung) {
		this.zubereitung = zubereitung;
	}

	public Rezeptart getRezeptart() {
		return this.rezeptart;
	}

	public void setRezeptart(Rezeptart rezeptart) {
		this.rezeptart = rezeptart;
	}

	public Employee getMitarbeiter() {
		return this.employee;
	}

	public void setMitarbeiter(Employee employee) {
		this.employee = employee;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Date getErstellt() {
		return erstellt;
	}

	public void setErstellt(Date erstellt) {
		this.erstellt = erstellt;
	}
	
	public Boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}

	public List<RezeptHasArtikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<RezeptHasArtikel> artikel) {
		this.artikel = artikel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Rezept other = (Rezept) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + id + "";
	}
}

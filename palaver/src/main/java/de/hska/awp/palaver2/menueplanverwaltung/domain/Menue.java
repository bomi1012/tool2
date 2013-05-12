package de.hska.awp.palaver2.menueplanverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.rezeptverwaltung.domain.Rezept;

/**
 * Menue generated by hbm2java
 */

public class Menue implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -489234749011441338L;
	private Integer id;
	private Mitarbeiter mitarbeiter;
	private String name;
	private Set<Rezept> rezepts = new HashSet<Rezept>(0);
	private Set<Menueplan> menueplans = new HashSet<Menueplan>(0);

	public Menue() {
	}

	public Menue(Mitarbeiter mitarbeiter, String name) {
		this.mitarbeiter = mitarbeiter;
		this.name = name;
	}

	public Menue(Mitarbeiter mitarbeiter, String name, Set<Rezept> rezepts,
			Set<Menueplan> menueplans) {
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.rezepts = rezepts;
		this.menueplans = menueplans;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Mitarbeiter getMitarbeiter() {
		return this.mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Rezept> getRezepts() {
		return this.rezepts;
	}

	public void setRezepts(Set<Rezept> rezepts) {
		this.rezepts = rezepts;
	}

	public Set<Menueplan> getMenueplans() {
		return this.menueplans;
	}

	public void setMenueplans(Set<Menueplan> menueplans) {
		this.menueplans = menueplans;
	}

}
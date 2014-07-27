package de.palaver.management.recipe;

import java.util.HashSet;
import java.util.Set;

import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;

/**
 * @author Jan Lauinger
 */

public class Fussnote implements java.io.Serializable {

	private static final long serialVersionUID = 6494109377137286448L;

	// Variablen
	private Long id;
	private String name;
	private String abkuerzung;
	private Set<MenueHasFussnote> menueHasFussnotes = new HashSet<MenueHasFussnote>(
			0);

	// Konstruktoren
	public Fussnote() {
	}

	public Fussnote(String name) {
		this.name = name;
	}

	public Fussnote(Long id, String name, String abkuerzung) {
		this.id = id;
		this.name = name;
		this.abkuerzung = abkuerzung;
	}

	public Fussnote(String name, String abkuerzung) {
		this.name = name;
		this.abkuerzung = abkuerzung;
	}

	// Getter- und Setter
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return this.name;
	}

	public void setBezeichnung(String name) {
		this.name = name;
	}

	public String getAbkuerzung() {
		return this.abkuerzung;
	}

	public void setAbkuerzung(String abkuerzung) {
		this.abkuerzung = abkuerzung;
	}

	public Set<MenueHasFussnote> getMenueHasFussnotes() {
		return this.menueHasFussnotes;
	}

	public void setMenueHasFussnotes(Set<MenueHasFussnote> MenueHasFussnotes) {
		this.menueHasFussnotes = menueHasFussnotes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abkuerzung == null) ? 0 : abkuerzung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fussnote other = (Fussnote) obj;
		if (abkuerzung == null) {
			if (other.abkuerzung != null)
				return false;
		} else if (!abkuerzung.equals(other.abkuerzung))
			return false;
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
		return name;
	}

}

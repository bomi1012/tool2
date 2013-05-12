package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Fussnote generated by hbm2java
 */
public class Fussnote implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6494109377137286448L;
	private Long id;
	private String name;
	private String abkuerzung;
	private Set<RezeptHasFussnote> rezeptHasFussnotes = new HashSet<RezeptHasFussnote>(
			0);

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

	public Fussnote(String name, String abkuerzung,
			Set<RezeptHasFussnote> rezeptHasFussnotes) {
		this.name = name;
		this.abkuerzung = abkuerzung;
		this.rezeptHasFussnotes = rezeptHasFussnotes;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbkuerzung() {
		return this.abkuerzung;
	}

	public void setAbkuerzung(String abkuerzung) {
		this.abkuerzung = abkuerzung;
	}

	public Set<RezeptHasFussnote> getRezeptHasFussnotes() {
		return this.rezeptHasFussnotes;
	}

	public void setRezeptHasFussnotes(Set<RezeptHasFussnote> rezeptHasFussnotes) {
		this.rezeptHasFussnotes = rezeptHasFussnotes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abkuerzung == null) ? 0 : abkuerzung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((rezeptHasFussnotes == null) ? 0 : rezeptHasFussnotes
						.hashCode());
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
		if (rezeptHasFussnotes == null) {
			if (other.rezeptHasFussnotes != null)
				return false;
		} else if (!rezeptHasFussnotes.equals(other.rezeptHasFussnotes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
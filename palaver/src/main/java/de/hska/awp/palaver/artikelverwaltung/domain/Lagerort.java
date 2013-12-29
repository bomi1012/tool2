package de.hska.awp.palaver.artikelverwaltung.domain;

import de.hska.awp.palaver.EntityName;

public class Lagerort extends EntityName {
	
	public Lagerort() {
		super();
	}
	public Lagerort(String name) {
		super();
		this.name = name;
	}
	public Lagerort(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Lagerort other = (Lagerort) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

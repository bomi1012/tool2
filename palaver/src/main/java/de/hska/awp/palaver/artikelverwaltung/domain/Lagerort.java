package de.hska.awp.palaver.artikelverwaltung.domain;

import de.hska.awp.palaver.EntityName;

public class Lagerort extends EntityName {
	
	public Lagerort() {
		super();
	}
	public Lagerort(String name) {
		super(name);
	}
	public Lagerort(Long id, String name) {
		super(id, name);
	}

	@Override
	public String toString() {
		return m_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_id == null) ? 0 : m_id.hashCode());
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
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}

}

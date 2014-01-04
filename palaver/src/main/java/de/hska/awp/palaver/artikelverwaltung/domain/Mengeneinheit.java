package de.hska.awp.palaver.artikelverwaltung.domain;

import de.hska.awp.palaver.EntityName;

public class Mengeneinheit extends EntityName implements java.io.Serializable {

	private static final long serialVersionUID = 5210068506937506344L;
	private String kurz;

	public Mengeneinheit() {
		super();
	}
	public Mengeneinheit(Long id, String name, String kurz) {
		super(id, name);
		this.kurz = kurz;
	}
	public Mengeneinheit(String name, String kurz) {
		super(name);
		this.kurz = kurz;
	}

	public String getKurz() { return this.kurz; }
	public void setKurz(String kurz) { this.kurz = kurz; }

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
		Mengeneinheit other = (Mengeneinheit) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}
}

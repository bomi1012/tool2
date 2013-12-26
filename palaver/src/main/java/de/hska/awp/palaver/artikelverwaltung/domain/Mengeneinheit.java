package de.hska.awp.palaver.artikelverwaltung.domain;

import de.hska.awp.palaver.EntityName;

public class Mengeneinheit extends EntityName implements java.io.Serializable {

	private static final long serialVersionUID = 5210068506937506344L;
	private String kurz;

	public Mengeneinheit() {
		super();
	}
	public Mengeneinheit(Long id, String name, String kurz) {
		this.kurz = kurz;
		this.id = id;
		this.name = name;
	}
	public Mengeneinheit(String name, String kurz) {
		this.kurz = kurz;
		this.name = name;
	}

	public String getKurz() { return this.kurz; }
	public void setKurz(String kurz) { this.kurz = kurz; }

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kurz == null) ? 0 : kurz.hashCode());
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
		Mengeneinheit other = (Mengeneinheit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kurz == null) {
			if (other.kurz != null)
				return false;
		} else if (!kurz.equals(other.kurz))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

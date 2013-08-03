package de.bistrosoft.palaver.menueplanverwaltung.domain;

/**
 * 
 */
public class Menueart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3791004464836691661L;
	private Long id;
	private String name;

	public Menueart() {
	}

	public Menueart(Long id, String name) {
		this.id = id;
		this.name = name;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime * result + ((rezepts == null) ? 0 :
		// rezepts.hashCode());
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
		Menueart other = (Menueart) obj;
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
		// if (rezepts == null) {
		// if (other.rezepts != null)
		// return false;
		// } else if (!rezepts.equals(other.rezepts))
		// return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}

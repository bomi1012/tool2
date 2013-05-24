package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 24.04.2013 16:47:07 by Hibernate Tools 4.0.0


import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezeptId;

/**
 * RezeptHasZubereitung generated by hbm2java
 */
public class RezeptHasZubereitung implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8364474045416406094L;
	private Long rezeptFk;
	private Zubereitung zubereitung;
	private Rezept rezept;
	private RezeptHasZubereitungId id;

	public RezeptHasZubereitung() {
	}

	public RezeptHasZubereitung(Rezept rezept) {
		this.rezept = rezept;
	}
	public RezeptHasZubereitung(RezeptHasZubereitungId id, Rezept rezept) {
		this.id = id;
		this.rezept = rezept;
	}

	public RezeptHasZubereitung(Zubereitung zubereitung, Rezept rezept) {
		this.zubereitung = zubereitung;
		this.rezept = rezept;
	}
	public RezeptHasZubereitung(RezeptHasZubereitungId id, Zubereitung zubereitung, Rezept rezept) {
		this.id = id;
		this.zubereitung = zubereitung;
		this.rezept = rezept;
	}

	public RezeptHasZubereitungId getId() {
		return this.id;
	}

	public void setId(RezeptHasZubereitungId id) {
		this.id = id;
	}

	public Zubereitung getZubereitung() {
		return this.zubereitung;
	}

	public void setZubereitung(Zubereitung zubereitung) {
		this.zubereitung = zubereitung;
	}

	public Rezept getRezept() {
		return this.rezept;
	}

	public void setRezept(Rezept rezept) {
		this.rezept = rezept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rezept == null) ? 0 : rezept.hashCode());
		result = prime * result
				+ ((rezeptFk == null) ? 0 : rezeptFk.hashCode());
		result = prime * result
				+ ((zubereitung == null) ? 0 : zubereitung.hashCode());
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
		RezeptHasZubereitung other = (RezeptHasZubereitung) obj;
		if (rezept == null) {
			if (other.rezept != null)
				return false;
		} else if (!rezept.equals(other.rezept))
			return false;
		if (rezeptFk == null) {
			if (other.rezeptFk != null)
				return false;
		} else if (!rezeptFk.equals(other.rezeptFk))
			return false;
		if (zubereitung == null) {
			if (other.zubereitung != null)
				return false;
		} else if (!zubereitung.equals(other.zubereitung))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RezeptHasZubereitung [rezeptFk=" + rezeptFk + ", zubereitung="
				+ zubereitung + ", rezept=" + rezept + "]";
	}

}
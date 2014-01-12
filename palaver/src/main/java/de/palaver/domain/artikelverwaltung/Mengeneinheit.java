package de.palaver.domain.artikelverwaltung;

import de.palaver.domain.EntityName;

public class Mengeneinheit extends EntityName implements java.io.Serializable {
	private static final long serialVersionUID = 5210068506937506344L;
	private String m_kurz;

	public Mengeneinheit() {
		super();
	}
	public Mengeneinheit(Long id, String name, String kurz) {
		super(id, name);
		this.m_kurz = kurz;
	}
	public Mengeneinheit(String name, String kurz) {
		super(name);
		this.m_kurz = kurz;
	}

	public String getKurz() { return this.m_kurz; }
	public void setKurz(String kurz) { this.m_kurz = kurz; }

}

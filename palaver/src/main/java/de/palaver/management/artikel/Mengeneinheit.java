package de.palaver.management.artikel;

import de.palaver.management.util.entity.EntityName;

public class Mengeneinheit extends EntityName implements java.io.Serializable {
	private static final long serialVersionUID = 5210068506937506344L;
	private String m_kurz;
	public String getKurz() { return m_kurz; }
	public void setKurz(String kurz) { m_kurz = kurz; }

	public Mengeneinheit() {
		super();
	}
	public Mengeneinheit(Long id, String name, String kurz) {
		super(id, name);
		this.m_kurz = kurz;
	}
	public Mengeneinheit(String name, String kurz) {
		this(null, name, kurz);
	}

	public Mengeneinheit(long id, String name) {
		this(id, name, null);
	}


}

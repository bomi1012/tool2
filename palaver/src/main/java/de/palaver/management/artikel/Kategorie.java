package de.palaver.management.artikel;

import de.palaver.management.util.entity.EntityName;

public class Kategorie extends EntityName implements java.io.Serializable {
	private static final long serialVersionUID = -4647006694762094989L;

	public Kategorie() { 
		super(); 
	}
	
	public Kategorie(Long id, String name) {
		super(id, name);
	}

	public Kategorie(String name) {
		super(name);
	}
}

package de.palaver.domain.artikelverwaltung;

import de.palaver.domain.EntityName;

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
}

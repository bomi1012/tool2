package de.palaver.management.menu;

import de.palaver.domain.EntityName;

public class Menutype extends EntityName {
	private static final long serialVersionUID = 3791004464836691661L;

	public Menutype() {
		super();
	}

	public Menutype(Long id, String name) {
		super(id, name);
	}
	
	public Menutype(String name) {
		this(null, name);
	}
}

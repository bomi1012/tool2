package de.palaver.management.recipe;

import de.palaver.domain.EntityName;

public class Geschmack extends EntityName {

	private static final long serialVersionUID = 2070753066033963562L;
	
	private Boolean m_inaktiv;
	public Boolean getInaktiv() { return m_inaktiv; }
	public void setInaktiv(Boolean inaktiv) { m_inaktiv = inaktiv; }

	public Geschmack() {
		super();
	}

	public Geschmack(Long id, String name, Boolean inaktiv) {
		super(id, name);
		m_inaktiv = inaktiv;
	}	
}

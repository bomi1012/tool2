package de.palaver.management.menu;

import de.palaver.domain.EntityName;

public class Fussnote extends EntityName  {
	private static final long serialVersionUID = 5587891631592514791L;
	
	private String m_abkuerzung;
	public String getAbkuerzung() { return m_abkuerzung; }
	public void setAbkuerzung(String abkuerzung) { m_abkuerzung = abkuerzung; }

	public Fussnote() {
		super();
	}

	public Fussnote(Long id, String name, String abkuerzung) {
		super(id, name);
		m_abkuerzung = abkuerzung;
	}
}

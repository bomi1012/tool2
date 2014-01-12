package de.palaver.domain;

public class EntityName extends EntityId {
	
	protected String m_name;
	public String getName() { return m_name; }
	public void setName(String name) { m_name = name; }
	
	public EntityName() {
		super();
	}
	
	public EntityName(Long id, String name) {
		super(id);
		m_name = name;
	}

	public EntityName(String name) {
		m_name = name;
	}

	@Override
	public String toString() {
		return m_name;
	}	
}

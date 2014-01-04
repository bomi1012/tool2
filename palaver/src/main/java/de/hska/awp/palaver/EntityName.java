package de.hska.awp.palaver;

public class EntityName extends EntityId {
	protected String m_name;
	
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

	public String getName() { return m_name; }
	public void setName(String name) { m_name = name; }

}

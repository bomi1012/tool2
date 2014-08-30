package de.palaver.management.util.entity;


public class EntityName extends EntityId  {
	private static final long serialVersionUID = -3715073420630350421L;
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
		this(null, name);
	}

	@Override
	public String toString() {
		return m_name;
	}	
}

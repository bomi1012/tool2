package de.hska.awp.palaver;

public class EntityId {
	protected Long m_id;
	public EntityId() {
		super();
	}
	
	public EntityId(Long id) {
		super();
		m_id = id;
	}

	public Long getId() { return m_id; }
	public void setId(Long id) { m_id = id; }
	
}

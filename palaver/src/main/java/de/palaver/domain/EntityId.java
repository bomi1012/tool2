package de.palaver.domain;

public class EntityId {
	protected Long m_id;
	public Long getId() { return m_id; }
	public void setId(Long id) { m_id = id; }
	
	public EntityId() {
		this(null);
	}
	
	public EntityId(Long id) {
		m_id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_id == null) ? 0 : m_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityId other = (EntityId) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}
	
	
}

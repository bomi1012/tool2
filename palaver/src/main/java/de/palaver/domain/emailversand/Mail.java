package de.palaver.domain.emailversand;

import de.palaver.domain.EntityId;

public class Mail extends EntityId {
	
	private String m_password;
	private String m_key;
	private String m_description;
	private long m_length;
	
	public Mail(Long id, String password, String key, long length, String description){
		super(id);
		this.m_password = password;
		this.m_key = key;
		this.m_description = description;
		this.m_length = length;
	}

	public Mail() {
		super();
	}

	public String getPassword() {
		return m_password;
	}

	public void setPassword(String password) {
		m_password = password;
	}

	public String getKey() {
		return m_key;
	}

	public void setKey(String key) {
		m_key = key;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String description) {
		m_description = description;
	}

	public long getLength() {
		return m_length;
	}

	public void setLength(long length) {
		m_length = length;
	}
}


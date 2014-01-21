package de.palaver.dao.email;

import de.palaver.domain.EntityId;

public class MailModel extends EntityId {
	
	private String passwort;
	private String schlussel;
	private String descript;
	private long length;
	
	public MailModel(Long id, String passwort, String key, long length, String zweck){
		super(id);
		this.passwort = passwort;
		this.schlussel = key;
		this.descript = zweck;
		this.length = length;
	}

	@Override
	public String toString() {
		return "Mail";
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	public MailModel() {
		super();
	}

	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * @param passwort the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * @return the schlussel
	 */
	public String getSchlussel() {
		return schlussel;
	}

	/**
	 * @param schlussel the schlussel to set
	 */
	public void setSchlussel(String schlussel) {
		this.schlussel = schlussel;
	}	
	/**
	 * @return the zweck
	 */
	public String getDescript() {
		return descript;
	}

	/**
	 * @param zweck the zweck to set
	 */
	public void setZweck(String zweck) {
		this.descript = zweck;
	}
}


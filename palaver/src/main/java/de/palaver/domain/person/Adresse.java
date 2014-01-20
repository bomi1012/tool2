package de.palaver.domain.person;

import de.palaver.domain.EntityId;

public class Adresse extends EntityId {

	private String m_strasse;
	private String m_hausnummer;
	private String m_stadt;	
	private String m_plz;
	private String m_land;
	
	public String getStrasse() {
		return m_strasse;
	}
	public void setStrasse(String strasse) {
		m_strasse = strasse;
	}
	public String getHausnummer() {
		return m_hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		m_hausnummer = hausnummer;
	}
	public String getStadt() {
		return m_stadt;
	}
	public void setStadt(String stadt) {
		m_stadt = stadt;
	}
	public String getPlz() {
		return m_plz;
	}
	public void setPlz(String plz) {
		m_plz = plz;
	}
	public String getLand() {
		return m_land;
	}
	public void setLand(String land) {
		m_land = land;
	}
	public Adresse() {
		super();
	}
	public Adresse(long id, String strasse, String hausnummer, String stadt, String plz,
			String land) {
		super(id);
		m_strasse = strasse;
		m_hausnummer = hausnummer;
		m_stadt = stadt;
		m_plz = plz;
		m_land = land;
	}
	@Override
	public String toString() {
		String adresse = "";
		if (m_stadt != null) {
			adresse += m_stadt;
		} 
		if(m_strasse != null) {
			if (adresse != "") {
				adresse += ", " + m_strasse;
			} else {
				adresse += m_strasse;
			}
		}
		if(m_hausnummer != null && m_strasse != null) {
			adresse += " " + m_hausnummer;
		}		
		return adresse;
	}	
	
	
}

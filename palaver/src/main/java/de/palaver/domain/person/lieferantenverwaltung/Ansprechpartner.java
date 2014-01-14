package de.palaver.domain.person.lieferantenverwaltung;

import de.palaver.domain.EntityName;
import de.palaver.domain.person.Adresse;
import de.palaver.domain.person.Kontakte;

public class Ansprechpartner extends EntityName {

	private Lieferant m_lieferant;
	private String m_bezeichnung;
	private Adresse m_adresse;
	private Kontakte m_kontakte;
	
	public Lieferant getLieferant() {
		return m_lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		m_lieferant = lieferant;
	}

	public String getBezeichnung() {
		return m_bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		m_bezeichnung = bezeichnung;
	}

	public Adresse getAdresse() {
		return m_adresse;
	}

	public void setAdresse(Adresse adresse) {
		m_adresse = adresse;
	}

	public Kontakte getKontakte() {
		return m_kontakte;
	}

	public void setKontakte(Kontakte kontakte) {
		m_kontakte = kontakte;
	}

	public Ansprechpartner() {
		super();
	}

	public Ansprechpartner(long id, String name, Lieferant lieferant, String bezeichnung,
			Adresse adresse, Kontakte kontakte) {
		super(id, name);
		m_lieferant = lieferant;
		m_bezeichnung = bezeichnung;
		m_adresse = adresse;
		m_kontakte = kontakte;
	}
}

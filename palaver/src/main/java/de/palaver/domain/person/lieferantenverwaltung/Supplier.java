package de.palaver.domain.person.lieferantenverwaltung;

import de.palaver.domain.EntityName;
import de.palaver.domain.person.Adresse;
import de.palaver.domain.person.Kontakte;

public class Supplier extends EntityName {

	private String m_lieferantnummer;
	private String m_bezeichnung;
	private String m_notiz;
	private boolean m_mehrereliefertermine;
	private Adresse m_adresse;
	private Kontakte m_kontakte;

	public String getLieferantnummer() {
		return m_lieferantnummer;
	}

	public void setLieferantnummer(String lieferantnummer) {
		m_lieferantnummer = lieferantnummer;
	}

	public String getBezeichnung() {
		return m_bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		m_bezeichnung = bezeichnung;
	}

	public boolean isMehrereliefertermine() {
		return m_mehrereliefertermine;
	}

	public void setMehrereliefertermine(boolean mehrereliefertermine) {
		m_mehrereliefertermine = mehrereliefertermine;
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

	public String getNotiz() {
		return m_notiz;
	}

	public void setNotiz(String notiz) {
		m_notiz = notiz;
	}

	public Supplier() {
		super();
	}

	public Supplier(long id, String name, String lieferantnummer,
			String bezeichnung, boolean mehrereliefertermine, String notiz,
			Adresse adresse, Kontakte kontakte) {
		super(id, name);
		m_lieferantnummer = lieferantnummer;
		m_bezeichnung = bezeichnung;
		m_mehrereliefertermine = mehrereliefertermine;
		m_adresse = adresse;
		m_kontakte = kontakte;
		m_notiz = notiz;
	}

	public Supplier(String name, String lieferantnummer, String bezeichnung,
			boolean mehrereliefertermine, String notiz, Adresse adresse,
			Kontakte kontakte) {
		super(name);
		m_lieferantnummer = lieferantnummer;
		m_bezeichnung = bezeichnung;
		m_mehrereliefertermine = mehrereliefertermine;
		m_adresse = adresse;
		m_kontakte = kontakte;
		m_notiz = notiz;
	}

	public Supplier(long id, String name) {
		this(id, name, null, null, false, null, null, null);
	}
}

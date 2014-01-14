package de.palaver.domain.bestellverwaltung;

import java.sql.Date;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.palaver.domain.EntityId;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;

/**
 * Die Klasse Bestellung spiegelt den Bestellung aus der Datenbank wieder.
 */

public class Bestellung extends EntityId implements java.io.Serializable {
	private static final long serialVersionUID = -4115989551813492575L;

	private Lieferant m_lieferant;
	private Mitarbeiter m_mitarbeiter;
	private Date m_datum;
	private Date m_lieferdatum1;
	private Date m_lieferdatum2;
	private boolean m_status;  	
	private String m_kategorie; 
	
	private List<Bestellposition> m_bestellpositions;
	
	public Bestellung() {
		super();
	}
	
	public Bestellung(Long id, Lieferant lieferant, Mitarbeiter mitarbeiter,
			Date datum, Date lieferdatum1, Date lieferdatum2, boolean status, String kategorie) {
		super(id);
		m_lieferant = lieferant;
		m_mitarbeiter = mitarbeiter;
		m_datum = datum;
		m_lieferdatum1 = lieferdatum1;
		m_lieferdatum2 = lieferdatum2;
		m_status = status;
		m_kategorie = kategorie;
	}

	public Bestellung(Lieferant lieferant, Mitarbeiter mitarbeiter,
			Date lieferdatum1, Date lieferdatum2, boolean status, String kategorie) {
		super();
		m_lieferant = lieferant;
		m_mitarbeiter = mitarbeiter;
		m_lieferdatum1 = lieferdatum1;
		m_lieferdatum2 = lieferdatum2;
		m_status = status;
		m_kategorie = kategorie;
	}




	public Lieferant getLieferant() {
		return m_lieferant;
	}
	public void setLieferant(Lieferant lieferant) {
		m_lieferant = lieferant;
	}

	public Mitarbeiter getMitarbeiter() {
		return m_mitarbeiter;
	}
	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		m_mitarbeiter = mitarbeiter;
	}

	public Date getDatum() {
		return m_datum;
	}
	public void setDatum(Date datum) {
		m_datum = datum;
	}

	public Date getLieferdatum1() {
		return m_lieferdatum1;
	}
	public void setLieferdatum1(Date lieferdatum1) {
		m_lieferdatum1 = lieferdatum1;
	}

	public Date getLieferdatum2() {
		return m_lieferdatum2;
	}
	public void setLieferdatum2(Date lieferdatum2) {
		m_lieferdatum2 = lieferdatum2;
	}

	public boolean getStatus() {
		return m_status;
	}
	public void setStatus(boolean status) {
		m_status = status;
	}

	public String getKategorie() {
		return m_kategorie;
	}
	public void setKategorie(String kategorie) {
		m_kategorie = kategorie;
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
		Bestellung other = (Bestellung) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}
}
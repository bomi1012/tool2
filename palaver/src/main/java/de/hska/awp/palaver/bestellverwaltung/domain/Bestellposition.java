package de.hska.awp.palaver.bestellverwaltung.domain;

import de.hska.awp.palaver.EntityId;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;

/**
 * Die Klasse Bestellposition spiegelt den Bestellposition aus der Datenbank
 * wieder.
 */

public class Bestellposition extends EntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1291141881234373163L;

	private Artikel m_artikel;
	private Bestellung m_bestellung;
	private Double m_liefermenge1;
	private Double m_liefermenge2;
	private boolean m_status;
		

	public Artikel getArtikel() {
		return m_artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.m_artikel = artikel;
	}

	public Bestellung getBestellung() {
		return m_bestellung;
	}
	public void setBestellung(Bestellung bestellung) {
		this.m_bestellung = bestellung;
	}

	public Double getLiefermenge1() {
		return m_liefermenge1;
	}
	public void setLiefermenge1(Double liefermenge1) {
		this.m_liefermenge1 = liefermenge1;
	}

	public Double getLiefermenge2() {
		return m_liefermenge2;
	}
	public void setLiefermenge2(Double liefermenge2) {
		this.m_liefermenge2 = liefermenge2;
	}

	public boolean isStatus() {
		return m_status;
	}
	public void setStatus(boolean status) {
		this.m_status = status;
	}


	public Bestellposition() {
		super();
	}
	public Bestellposition(Long id, Artikel artikel, Bestellung bestellung,
			Double liefertermin1, Double liefertermin2, boolean status) {
		super(id);
		m_artikel = artikel;
		m_bestellung = bestellung;
		m_liefermenge1 = liefertermin1;
		m_liefermenge2 = liefertermin2;
		m_status = status;
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
		Bestellposition other = (Bestellposition) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}
}

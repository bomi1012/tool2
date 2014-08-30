package de.palaver.alt.domain.bestellverwaltung;

import de.palaver.management.artikel.Artikel;
import de.palaver.management.util.entity.EntityId;

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
	private boolean m_statusLT1;
	private boolean m_statusLT2;
		

	public Artikel getArtikel() {
		return m_artikel;
	}
	public void setArtikel(Artikel artikel) {
		m_artikel = artikel;
	}

	public Bestellung getBestellung() {
		return m_bestellung;
	}
	public void setBestellung(Bestellung bestellung) {
		m_bestellung = bestellung;
	}

	public Double getLiefermenge1() {
		return m_liefermenge1;
	}
	public void setLiefermenge1(Double liefermenge1) {
		m_liefermenge1 = liefermenge1;
	}

	public Double getLiefermenge2() {
		return m_liefermenge2;
	}
	public void setLiefermenge2(Double liefermenge2) {
		m_liefermenge2 = liefermenge2;
	}

	public boolean isStatusLT1() {
		return m_statusLT1;
	}
	public void setStatusLT1(boolean status) {
		m_statusLT1 = status;
	}
	
	public boolean isStatusLT2() {
		return m_statusLT2;
	}
	public void setStatusLT2(boolean status) {
		m_statusLT2 = status;
	}

	public Bestellposition() {
		super();
	}
	public Bestellposition(Long id, Artikel artikel, Bestellung bestellung,
			Double liefertermin1, Double liefertermin2, boolean statusLT1, boolean statusLT2) {
		super(id);
		m_artikel = artikel;
		m_bestellung = bestellung;
		m_liefermenge1 = liefertermin1;
		m_liefermenge2 = liefertermin2;
		m_statusLT1 = statusLT1;
		m_statusLT2 = statusLT2;
	}

	public Bestellposition(Artikel artikel, Bestellung bestellung,
			Double liefermenge1, Double liefermenge2, boolean status) {
		super();
		m_artikel = artikel;
		m_bestellung = bestellung;
		m_liefermenge1 = liefermenge1;
		m_liefermenge2 = liefermenge2;
		m_statusLT1 = status;
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

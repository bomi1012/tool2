package de.hska.awp.palaver.bestellverwaltung.domain;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Die Klasse Bestellung spiegelt den Bestellung aus der Datenbank wieder.
 * 
 * @author Elena W
 */

public class Bestellung implements java.io.Serializable {

	private static final long serialVersionUID = -4115989551813492575L;

	private Long m_id;
	private Lieferant m_lieferant;
	private Date m_datum;
	private Date m_lieferdatum;
	private Date m_lieferdatum2;
	private List<Bestellposition> m_bestellpositionen;
	private boolean m_status; 
	
	public Bestellung() {
		super();
	}

	public Bestellung(Long id, Lieferant lieferant, Date datum, Date lieferdatum, Date lieferdatum2, boolean bestellt) {
		super();
		this.m_id = id;
		this.m_lieferant = lieferant;
		this.m_datum = datum;
		this.m_lieferdatum = lieferdatum;
		this.m_status = bestellt;
		this.m_lieferdatum2 = lieferdatum2;
	}

	/**
	 * @author Christian Barth
	 * @param id
	 * @param lieferant
	 * @param datum
	 * @param lieferdatum
	 * @param lieferdatum2
	 * @param bestellpositionen
	 * @param bestellt
	 */
	public Bestellung(Long id, Lieferant lieferant, Date datum, Date lieferdatum, Date lieferdatum2, List<Bestellposition> bestellpositionen,
			boolean bestellt) {
		super();
		this.m_id = id;
		this.m_lieferant = lieferant;
		this.m_datum = datum;
		this.m_lieferdatum = lieferdatum;
		this.m_bestellpositionen = bestellpositionen;
		this.m_status = bestellt;
		this.m_lieferdatum2 = lieferdatum2;
	}

	public Long getId() {
		return this.m_id;
	}

	public Lieferant getLieferant() {
		return this.m_lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.m_lieferant = lieferant;
	}

	public Date getDatum() {
		return this.m_datum;
	}
	
	public String getDatumS() {
		DateFormat df=new SimpleDateFormat("dd.MM.yy");
		Date date = m_datum;
		String s=df.format(date); 
		return s;
	}

	public void setDatum(Date datum) {
		this.m_datum = datum;
	}

	public Date getLieferdatum() {
		return m_lieferdatum;
	}
	
	public String getLieferdatumS() {
		DateFormat df=new SimpleDateFormat("dd.MM.yy");
		Date date = m_lieferdatum;
		String s=df.format(date); 
		return s;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.m_lieferdatum = lieferdatum;
	}

	public List<Bestellposition> getBestellpositionen() {
		return m_bestellpositionen;
	}

	public void setBestellpositionen(List<Bestellposition> bestellpositionen) {
		this.m_bestellpositionen = bestellpositionen;
	}

	public Bestellung addBestellposition(Bestellposition bestellposition) {
		if (m_bestellpositionen == null) {
			m_bestellpositionen = new ArrayList<Bestellposition>();
		}
		m_bestellpositionen.add(bestellposition);
		return this;
	}

	public boolean isBestellt() {
		return m_status;
	}

	public void setBestellt(boolean bestellt) {
		this.m_status = bestellt;
	}

	public Date getLieferdatum2() {
		return m_lieferdatum2;
	}
	
	public String getLieferdatum2S() {
		DateFormat df=new SimpleDateFormat("dd.MM.yy");
		Date date = m_lieferdatum2;
		String s=df.format(date); 
		return s;
	}

	public void setLieferdatum2(Date lieferdatum2) {
		this.m_lieferdatum2 = lieferdatum2;
	}

	@Override
	public String toString() {
		return "Bestellung [id=" + m_id + ", lieferant=" + m_lieferant + ", datum=" + m_datum + ", lieferdatum=" + m_lieferdatum + ", lieferdatum2="
				+ m_lieferdatum2 + ", bestellt=" + m_status + "]";
	}

}

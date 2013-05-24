package de.hska.awp.palaver2.bestellverwaltung.domain;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;



/**
 * @author Elena W
 * Die Klasse Bestellposition spiegelt den Bestellposition aus der Datenbank wieder
 */
	
public class Bestellposition implements java.io.Serializable {

	private static final long serialVersionUID = 1291141881234373163L;

	private Long id;
	private Artikel artikel;
	private Bestellung bestellung;	
	private Integer durchschnitt;
	private Integer kantine;
	private Integer gesamt;
	private Integer freitag;
	private Integer montag;
	private boolean geliefert;

	public Bestellposition() {
		super();
	}

	/**
	 * @param id
	 * @param artikel
	 * @param bestellung
	 * @param menge
	 */
	public Bestellposition(Long id, Artikel artikel, Bestellung bestellung, 
			Integer durchschnitt, Integer kantine, Integer gesamt, Integer freitag, Integer montag, boolean geliefert) {
		super();
		this.id = id;
		this.artikel = artikel;
		this.bestellung = bestellung;
		this.durchschnitt = durchschnitt;
		this.kantine = kantine;
		this.gesamt = gesamt;
		this.freitag = freitag;
		this.montag = montag;
		this.geliefert = geliefert;
	}
	
	public Bestellposition(Long id, Artikel artikel, 
			Integer durchschnitt, Integer kantine, Integer gesamt, Integer freitag, Integer montag, boolean geliefert) {
		this.id = id;
		this.artikel = artikel;
		this.durchschnitt = durchschnitt;
		this.kantine = kantine;
		this.gesamt = gesamt;
		this.freitag = freitag;
		this.montag = montag;
		this.geliefert = geliefert;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}

	
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	
	public String getArtikelName() {
		return this.artikel.getName();
	}

	public Bestellung getBestellung() {
		return this.bestellung;
	}

	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}

	public Integer getDurchschnitt() {
		if(durchschnitt==null){
			durchschnitt = 0;
		}
		return durchschnitt;
	}

	public void setDurchschnitt(Integer durchschnitt) {
		this.durchschnitt = durchschnitt;
	}

	public Integer getKantine() {
		if(kantine==null){
			kantine = 0;
		}
		return kantine;
	}

	public void setKantine(Integer kantine) {
		this.kantine = kantine;
	}

	public Integer getGesamt() {
		if(gesamt==null){
			gesamt = 0;
		}
		return gesamt;
	}

	public void setGesamt(Integer gesamt) {
		this.gesamt = gesamt;
	}

	public Integer getFreitag() {
		if(freitag==null){
			freitag = 0;
		}
		return freitag;
	}

	public void setFreitag(Integer freitag) {
		this.freitag = freitag;
	}

	public Integer getMontag() {
		if(montag==null){
			montag = 0;
		}
		return montag;
	}

	public void setMontag(Integer montag) {
		this.montag = montag;
	}

	public boolean isGeliefert() {
		return geliefert;
	}

	public void setGeliefert(boolean geliefert) {
		this.geliefert = geliefert;
	}


}
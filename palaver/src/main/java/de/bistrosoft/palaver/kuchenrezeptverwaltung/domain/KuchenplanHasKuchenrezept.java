package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

public class KuchenplanHasKuchenrezept {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3949683156254221803L;

	private Kuchenrezept kuchenrezept;
	private int tag;
	private int anzahl;

	public KuchenplanHasKuchenrezept(Kuchenrezept a, int tag) {
		this.kuchenrezept = a;
		this.tag = tag;
		this.anzahl = 1;
	}

	public Kuchenrezept getKuchenrezept() {
		return kuchenrezept;
	}

	public void setKuchenrezept(Kuchenrezept kuchenrezept) {
		this.kuchenrezept = kuchenrezept;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public String getKuchenname() {
		return this.kuchenrezept.getName();
	}

	@Override
	public String toString() {
		return "kuchenrezept " + kuchenrezept.getId() + " "
				+ tag + " " + anzahl + " " + kuchenrezept.getName() ;
	}
}
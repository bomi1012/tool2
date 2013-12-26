package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;

/**
 * 
 * @author Christine Hartkorn
 * 
 */

public class KuchenrezeptHasArtikel {

	// Variablen
	private Artikel artikel;
	private double menge;
	private Mengeneinheit mengeneinheit;
	private Kuchenrezept kuchenrezept;

	// Konstruktoren
	public KuchenrezeptHasArtikel() {
		super();
	}

	public KuchenrezeptHasArtikel(Artikel artikel, Mengeneinheit mengeneinheit,
			double menge) {
		this.artikel = artikel;
		this.mengeneinheit = mengeneinheit;
		this.menge = menge;
	}

	public KuchenrezeptHasArtikel(Kuchenrezept kuchenrezept, Artikel artikel,
			Mengeneinheit mengeneinheit, double menge) {
		this.kuchenrezept = kuchenrezept;
		this.artikel = artikel;
		this.menge = menge;
		this.mengeneinheit = mengeneinheit;
	}

	public KuchenrezeptHasArtikel(Artikel a) {
		this.artikel = a;
		this.mengeneinheit = a.getMengeneinheit();
		this.menge = 1.0;
		this.kuchenrezept = null;
	}

	// Getter und Setter
	public Mengeneinheit getMengeneinheit() {
		return mengeneinheit;
	}

	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
		this.mengeneinheit = mengeneinheit;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public double getMenge() {
		return this.menge;
	}

	public void setMenge(double menge) {
		this.menge = menge;
	}

	public String getArtikelname() {
		return this.artikel.getName();
	}

	public String getEinheit() {
		return this.mengeneinheit.getName();
	}

	public Long getArtikelId() {
		return this.artikel.getId();
	}

	public void setArtike(Artikel artikel) {
		this.artikel = artikel;
	}

	public Kuchenrezept getKuchenrezept() {
		return this.kuchenrezept;
	}

	public void setKuchenrezept(Kuchenrezept kuchenrezept) {
		this.kuchenrezept = kuchenrezept;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}

	// toString
	@Override
	public String toString() {
		return "artikel " + artikel.getId() + menge + mengeneinheit + "   "
				+ artikel.getName();
	}
}

package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.Mengeneinheit;

public class RezeptHasArtikel {

	private Artikel artikel;
	private double menge;
	private Mengeneinheit mengeneinheit;
	private Rezept rezept;

	// private String artikelname;

	public RezeptHasArtikel() {
		super();
	}

	public Mengeneinheit getMengeneinheit() {
		return mengeneinheit;
	}

	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
		this.mengeneinheit = mengeneinheit;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public RezeptHasArtikel(Artikel artikel, Mengeneinheit mengeneinheit,
			double menge) {
		this.artikel = artikel;
		this.mengeneinheit = mengeneinheit;
		this.menge = menge;
	}

	public RezeptHasArtikel(Rezept rezept, Artikel artikel,
			Mengeneinheit mengeneinheit, double menge) {
		this.rezept = rezept;
		this.artikel = artikel;
		this.menge = menge;
		this.mengeneinheit = mengeneinheit;

	}

	public RezeptHasArtikel(Artikel a) {
		this.artikel = a;
		this.mengeneinheit = a.getMengeneinheit();
		this.menge = 1.0;
		this.rezept = null;
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

	public Rezept getRezept() {
		return this.rezept;
	}

	public void setRezept(Rezept rezept) {
		this.rezept = rezept;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}

	public String getNotiz() {
		return artikel.getNotiz();
	}

	@Override
	public String toString() {
		return "artikel " + artikel.getId() + menge + mengeneinheit + "   "
				+ artikel.getName();
	}

	// @Override
	// public int hashCode() {
	// return artikel.hashCode();
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// return artikel.equals(obj);
	// }

	// public void setRezeptart(Rezeptart rezeptart) {
	// this.rezeptart = rezeptart;
	// }

	//
	// public void setArtikel(Artikel artikel) {
	// this.artikel = artikel;
	// }
	//
	// public void setMengeneinheit(Mengeneinheit mengeneinheit) {
	// this.mengeneinheit = mengeneinheit;
	// }
	//
	//
	// public BigDecimal getMenge() {
	// return this.menge;
	// }
	//
	// public void setMenge(BigDecimal menge) {
	// this.menge = menge;
	// }
}

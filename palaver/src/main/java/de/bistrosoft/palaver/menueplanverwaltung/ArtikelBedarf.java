package de.bistrosoft.palaver.menueplanverwaltung;

import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.Mengeneinheit;

public class ArtikelBedarf {

	private Artikel artikel;
	private Double menge;
	private Mengeneinheit einheit;
	private Integer tag;

	public ArtikelBedarf() {

	}

	public ArtikelBedarf(Artikel artikel, Double menge, Mengeneinheit einheit,
			Integer tag) {
		this.artikel = artikel;
		this.menge = menge;
		this.einheit = einheit;
		this.tag = tag;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public Double getMenge() {
		return menge;
	}

	public void setMenge(Double menge) {
		this.menge = menge;
	}

	public Mengeneinheit getEinheit() {
		return einheit;
	}

	public void setEinheit(Mengeneinheit einheit) {
		this.einheit = einheit;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "ArtikelBedarf [artikel=" + artikel.getId() + ", menge=" + menge
				+ ", einheit=" + einheit + ", tag=" + tag + "]";
	}
}

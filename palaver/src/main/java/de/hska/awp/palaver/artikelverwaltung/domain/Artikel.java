/**
 * Created by Sebastian Walz
 */
package de.hska.awp.palaver.artikelverwaltung.domain;

import java.sql.SQLException;

import de.hska.awp.palaver.EntityName;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

public class Artikel extends EntityName implements java.io.Serializable {
	private static final long serialVersionUID = 6557876739298794189L;

	private Mengeneinheit mengeneinheit;
	private Kategorie kategorie;
	private Lagerort lagerort;
	private Lieferant lieferant;
	private String artikelnr;
	private Double bestellgroesse;
	private Float preis;
	private Integer durchschnittLT1;
	private Integer durchschnittLT2;
	private String notiz;
	private boolean standard;
	private boolean grundbedarf;
	private boolean fuerRezept;
	
	public Artikel() {
		super();
	}

	public Artikel(Long id, String name) {
		super();
		m_id = id;
		m_name = name;
	}

	/**
	 * ALL
	 * @param id
	 * @param mengeneinheit
	 * @param kategorie
	 * @param lieferant
	 * @param artikelnr
	 * @param name
	 * @param bestellgroesse
	 * @param preis
	 * @param durchschnitt
	 * @param notiz
	 * @param standard
	 * @param grundbedarf
	 * @param fuerRezept
	 * @param lagerort
	 * @param durchschnittLT2 
	 */
	public Artikel(Long id, Mengeneinheit mengeneinheit, Kategorie kategorie, Lieferant lieferant, String artikelnr,
			String name, Double bestellgroesse, Float preis,
			Integer durchschnittLT1, Integer durchschnittLT2, String notiz, boolean standard, boolean grundbedarf, 
			boolean fuerRezept,Lagerort lagerort) throws ConnectException, DAOException, SQLException {
		super(id, name);
		this.mengeneinheit = mengeneinheit;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.artikelnr = artikelnr;
		this.bestellgroesse = bestellgroesse;
		this.durchschnittLT1 = durchschnittLT1;
		this.durchschnittLT2 = durchschnittLT2;
		this.preis = preis;
		this.notiz = notiz;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.fuerRezept = fuerRezept;
		this.lagerort = lagerort;

	}

	public Mengeneinheit getMengeneinheit() { 
		return this.mengeneinheit; 
	}
	public void setMengeneinheit(Mengeneinheit mengeneinheit) { 
		this.mengeneinheit = mengeneinheit;
	}

	public String getArtikelnr() {
		return this.artikelnr;
	}

	public void setArtikelnr(String artikelnr) {
		this.artikelnr = artikelnr;
	}

	public Kategorie getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public Double getBestellgroesse() {
		return this.bestellgroesse;
	}

	public void setBestellgroesse(Double bestellgroesse) {
		this.bestellgroesse = bestellgroesse;
	}

	public Float getPreis() {
		return this.preis;
	}

	public void setPreis(Float preis) {
		this.preis = preis;
	}

	public boolean isStandard() {
		return this.standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	public boolean isGrundbedarf() {
		return this.grundbedarf;
	}

	public void setGrundbedarf(boolean grundbedarf) {
		this.grundbedarf = grundbedarf;
	}

	public Integer getDurchschnittLT1() {
		return this.durchschnittLT1;
	}

	public void setDurchschnittLT1(Integer durchschnittLT1) {
		this.durchschnittLT1 = durchschnittLT1;
	}
	
	public Integer getDurchschnittLT2() {
		return this.durchschnittLT2;
	}

	public void setDurchschnittLT2(Integer durchschnittLT2) {
		this.durchschnittLT2 = durchschnittLT2;
	}

	public String getNotiz() {
		return this.notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	public boolean isFuerRezept() {
		return fuerRezept;
	}

	public void setFuerRezept(boolean fuerRezept) {
		this.fuerRezept = fuerRezept;
	}

	public Lagerort getLagerort(){
		return lagerort;
	}

	public void setLagerort(Lagerort lagerort) {
		this.lagerort = lagerort;
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
		Artikel other = (Artikel) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return this.m_name;
	}
}
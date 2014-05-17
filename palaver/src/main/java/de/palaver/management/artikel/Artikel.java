package de.palaver.management.artikel;

import java.io.Serializable;

import de.palaver.domain.EntityName;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;

public class Artikel extends EntityName implements Serializable {
	private static final long serialVersionUID = 6557876739298794189L;

	private Mengeneinheit m_mengeneinheit;
	private Kategorie m_kategorie;
	private Lagerort m_lagerort;
	private Lieferant m_lieferant;
	private String m_artikelnr;
	private Double m_bestellgroesse;
	private Float m_preis;
	private Integer m_durchschnittLT1;
	private Integer m_durchschnittLT2;
	private String m_notiz;
	private boolean m_standard;
	private boolean m_grundbedarf;
	private boolean m_fuerRezept;
	
	public Artikel() {
		this(null, null);
	}

	public Artikel(Long id, String name) {
		super(id, name);
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
	public Artikel(Long id, String name, 
			Mengeneinheit mengeneinheit, Kategorie kategorie, 
			Lieferant lieferant, Lagerort lagerort, String artikelnr, 
			Double bestellgroesse, Float preis,
			Integer durchschnittLT1, Integer durchschnittLT2, 
			String notiz, boolean standard, 
			boolean grundbedarf, boolean fuerRezept)  {
		super(id, name);
		m_mengeneinheit = mengeneinheit;
		m_kategorie = kategorie;
		m_lieferant = lieferant;
		m_artikelnr = artikelnr;
		m_bestellgroesse = bestellgroesse;
		m_durchschnittLT1 = durchschnittLT1;
		m_durchschnittLT2 = durchschnittLT2;
		m_preis = preis;
		m_notiz = notiz;
		m_standard = standard;
		m_grundbedarf = grundbedarf;
		m_fuerRezept = fuerRezept;
		m_lagerort = lagerort;

	}
/**
 *  * a.id,
	 * a.name, 
	 * a.artikelnr,
	 * a.bestellgroesse, 
	 * a.preis, 
	 * a.standard, 
	 * a.grundbedarf, 
	 * a.fuerRezepte, 
	 * a.durchschnittLT1,
	 * a.durchschnittLT2, 
	 * a.notiz ,
	 * 
	 * k.id, 
	 * k.name, 
	 * 
	 * lo.id,  
	 * lo.name, 
	 * 
	 * li.id, 
	 * li.name, 
	 * 
	 * me.id, 
	 * me.name 
 */
	
	public Artikel(long id, String name, String  artikelnr, double bestellgroesse,
			float preis, boolean standard, boolean grundbedarf, boolean fuerRezepte,
			int durchschnittLT1, int durchschnittLT2, String notiz, Kategorie kategorie,
			Lagerort lagerort, Lieferant lieferant, Mengeneinheit mengeneinheit) {
		super(id, name);
		m_mengeneinheit = mengeneinheit;
		m_kategorie = kategorie;
		m_lieferant = lieferant;
		m_artikelnr = artikelnr;
		m_bestellgroesse = bestellgroesse;
		m_durchschnittLT1 = durchschnittLT1;
		m_durchschnittLT2 = durchschnittLT2;
		m_preis = preis;
		m_notiz = notiz;
		m_standard = standard;
		m_grundbedarf = grundbedarf;
		m_fuerRezept = fuerRezepte;
		m_lagerort = lagerort;
				
	}

	public Mengeneinheit getMengeneinheit() { 
		return m_mengeneinheit; 
	}
	public void setMengeneinheit(Mengeneinheit mengeneinheit) { 
		m_mengeneinheit = mengeneinheit;
	}

	public String getArtikelnr() {
		return m_artikelnr;
	}

	public void setArtikelnr(String artikelnr) {
		m_artikelnr = artikelnr;
	}

	public Kategorie getKategorie() {
		return m_kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		m_kategorie = kategorie;
	}

	public Lieferant getLieferant() {
		return m_lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		m_lieferant = lieferant;
	}

	public Double getBestellgroesse() {
		return m_bestellgroesse;
	}

	public void setBestellgroesse(Double bestellgroesse) {
		m_bestellgroesse = bestellgroesse;
	}

	public Float getPreis() {
		return m_preis;
	}

	public void setPreis(Float preis) {
		m_preis = preis;
	}

	public boolean isStandard() {
		return m_standard;
	}

	public void setStandard(boolean standard) {
		m_standard = standard;
	}

	public boolean isGrundbedarf() {
		return m_grundbedarf;
	}

	public void setGrundbedarf(boolean grundbedarf) {
		m_grundbedarf = grundbedarf;
	}

	public Integer getDurchschnittLT1() {
		return m_durchschnittLT1;
	}

	public void setDurchschnittLT1(Integer durchschnittLT1) {
		m_durchschnittLT1 = durchschnittLT1;
	}
	
	public Integer getDurchschnittLT2() {
		return m_durchschnittLT2;
	}

	public void setDurchschnittLT2(Integer durchschnittLT2) {
		m_durchschnittLT2 = durchschnittLT2;
	}

	public String getNotiz() {
		return m_notiz;
	}

	public void setNotiz(String notiz) {
		m_notiz = notiz;
	}
	
	public boolean isFuerRezept() {
		return m_fuerRezept;
	}

	public void setFuerRezept(boolean fuerRezept) {
		m_fuerRezept = fuerRezept;
	}

	public Lagerort getLagerort(){
		return m_lagerort;
	}

	public void setLagerort(Lagerort lagerort) {
		m_lagerort = lagerort;
	}
}
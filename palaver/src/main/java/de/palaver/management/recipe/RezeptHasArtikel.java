package de.palaver.management.recipe;

import java.io.Serializable;

import de.palaver.management.artikel.Artikel;

public class RezeptHasArtikel implements Serializable {
	private static final long serialVersionUID = 7063839431848079581L;

	private Artikel m_artikel;	
	public Artikel getArtikel() { return m_artikel; }
	public void setArtikel(Artikel artikel) { m_artikel = artikel; }
	
	private double m_menge;
	public double getMenge() { return m_menge; }
	public void setMenge(double menge) { m_menge = menge; }	
	
	private Recipe m_recipe;
	public Recipe getRecipe() { return m_recipe; }
	public void setRecipe(Recipe recipe) { m_recipe = recipe; }
	
	public RezeptHasArtikel() {
		super();
	}
	public RezeptHasArtikel(Artikel artikel, double menge, Recipe recipe) {
		super();
		m_artikel = artikel;
		m_menge = menge;
		m_recipe = recipe;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((m_artikel == null) ? 0 : m_artikel.hashCode());
		long temp;
		temp = Double.doubleToLongBits(m_menge);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((m_recipe == null) ? 0 : m_recipe.hashCode());
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
		RezeptHasArtikel other = (RezeptHasArtikel) obj;
		if (m_artikel == null) {
			if (other.m_artikel != null)
				return false;
		} else if (!m_artikel.equals(other.m_artikel))
			return false;
		if (Double.doubleToLongBits(m_menge) != Double
				.doubleToLongBits(other.m_menge))
			return false;
		if (m_recipe == null) {
			if (other.m_recipe != null)
				return false;
		} else if (!m_recipe.equals(other.m_recipe))
			return false;
		return true;
	}	
}

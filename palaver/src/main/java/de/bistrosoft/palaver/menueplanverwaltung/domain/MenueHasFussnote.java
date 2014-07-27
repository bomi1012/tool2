package de.bistrosoft.palaver.menueplanverwaltung.domain;


import de.palaver.management.recipe.Fussnote;

public class MenueHasFussnote implements java.io.Serializable {

	private MenueHasFussnoteId menueHasFussnoteId;
	private Fussnote fussnote;
	private Menue menue;
	private Long fussnoteid;

	public MenueHasFussnote() {
	}

	public MenueHasFussnote(MenueHasFussnoteId menueHasFussnoteId, Fussnote fussnote,
			Menue menue) {
		this.menueHasFussnoteId = menueHasFussnoteId;
		this.fussnote = fussnote;
		this.menue = menue;
	}
	
	public MenueHasFussnote( Fussnote fussnote,
			Menue menue) {
		
		this.fussnote = fussnote;
		this.menue = menue;
	}
	public MenueHasFussnote( Long fussnoteid,
			Menue menue) {
		
		this.fussnoteid = fussnoteid;
		this.menue = menue;
	}
	

	public MenueHasFussnoteId getId() {
		return this.menueHasFussnoteId;
	}
	public void setId(Long id) {
		this.menueHasFussnoteId = menueHasFussnoteId;
	}
	
	public Fussnote getFussnote() {
		return this.fussnote;
	}

	public void setFussnote(Fussnote fussnote) {
		this.fussnote = fussnote;
	}

	
	public Menue getMenue() {
		return this.menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fussnote == null) ? 0 : fussnote.hashCode());
		result = prime * result + ((menue == null) ? 0 : menue.hashCode());
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
		MenueHasFussnote other = (MenueHasFussnote) obj;
		if (fussnote == null) {
			if (other.fussnote != null)
				return false;
		} else if (!fussnote.equals(other.fussnote))
			return false;
		if (menue == null) {
			if (other.menue != null)
				return false;
		} else if (!menue.equals(other.menue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "menueHasFussnote [fussnote=" + fussnote + ", menue=" + menue
				+ "]";
	}

	public Long getFussnoteid() {
		return fussnoteid;
	}

	public void setFussnoteid(Long fussnoteid) {
		this.fussnoteid = fussnoteid;
	}

}

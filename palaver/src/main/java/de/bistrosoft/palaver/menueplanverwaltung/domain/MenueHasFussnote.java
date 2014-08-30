package de.bistrosoft.palaver.menueplanverwaltung.domain;


import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Menu;

public class MenueHasFussnote implements java.io.Serializable {

	private MenueHasFussnoteId menueHasFussnoteId;
	private Fussnote fussnote;
	private Menu menu;
	private Long fussnoteid;

	public MenueHasFussnote() {
	}

	public MenueHasFussnote(MenueHasFussnoteId menueHasFussnoteId, Fussnote fussnote,
			Menu menu) {
		this.menueHasFussnoteId = menueHasFussnoteId;
		this.fussnote = fussnote;
		this.menu = menu;
	}
	
	public MenueHasFussnote( Fussnote fussnote,
			Menu menu) {
		
		this.fussnote = fussnote;
		this.menu = menu;
	}
	public MenueHasFussnote( Long fussnoteid,
			Menu menu) {
		
		this.fussnoteid = fussnoteid;
		this.menu = menu;
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

	
	public Menu getMenue() {
		return this.menu;
	}

	public void setMenue(Menu menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fussnote == null) ? 0 : fussnote.hashCode());
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
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
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "menueHasFussnote [fussnote=" + fussnote + ", menue=" + menu
				+ "]";
	}

	public Long getFussnoteid() {
		return fussnoteid;
	}

	public void setFussnoteid(Long fussnoteid) {
		this.fussnoteid = fussnoteid;
	}

}

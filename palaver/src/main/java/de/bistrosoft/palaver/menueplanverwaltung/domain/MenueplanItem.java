package de.bistrosoft.palaver.menueplanverwaltung.domain;

import de.palaver.management.menu.Menu;

public class MenueplanItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3949683156254221803L;

	private Menu menu;
	private int zeile;
	private int spalte;

	public Menu getMenue() {
		return menu;
	}

	public void setMenue(Menu menu) {
		this.menu = menu;
	}

	public int getZeile() {
		return zeile;
	}

	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

	public int getSpalte() {
		return spalte;
	}

	public void setSpalte(int spalte) {
		this.spalte = spalte;
	}
}

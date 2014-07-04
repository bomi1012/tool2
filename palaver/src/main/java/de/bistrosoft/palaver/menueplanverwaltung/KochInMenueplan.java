package de.bistrosoft.palaver.menueplanverwaltung;

import de.palaver.management.emploee.Employee;

public class KochInMenueplan {
	private Employee koch;
	private Integer spalte;
	private Integer position;


	public KochInMenueplan(Employee koch, Integer col, Integer position) {
		this.koch = koch;
		this.spalte = col;
		this.position = position;
	}

	public KochInMenueplan() {
	}

	public Employee getKoch() {
		return koch;
	}

	public void setKoch(Employee koch) {
		this.koch = koch;
	}

	public Integer getSpalte() {
		return spalte;
	}

	public void setSpalte(Integer spalte) {
		this.spalte = spalte;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}

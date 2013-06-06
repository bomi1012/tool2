package de.bistrosoft.palaver.menueplanverwaltung.domain;

import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

public class Menue {
	private Long id;
	private String name;
	private List<Rezept> rezepte;
	private Mitarbeiter koch;
	private Boolean hauptgericht;
	private Geschmack geschmack;
	private boolean aufwand;
	private boolean favorit;
	private Menueart menueart;

	List<Fussnote> fussnoten;

	private String kochname;
	private String geschmackname;
	private String menueartname;

	public Menue(Long id, String name, String kochname, String geschmackname,
			String menueartname) {
		this.id = id;
		this.name = name;
		this.kochname = kochname;
		this.geschmackname = geschmackname;
		this.menueartname = menueartname;
	}

	public Menue(Long id, String name, String koch) {
		this.id = id;
		this.name = name;
		this.kochname = koch;
	}

	public Menue(Long id, String name, String koch, Geschmack geschmack,
			Menueart menueart, Boolean aufwand, Boolean favorit) {
		this.id = id;
		this.name = name;
		this.kochname = koch;
		this.geschmack = geschmack;
		this.menueart = menueart;
		this.aufwand = aufwand;
		this.favorit = favorit;
	}

	public Menue(Long id, String name, String koch, Menueart menueart,
			Geschmack geschmack) {
		this.id = id;
		this.name = name;
		this.kochname = koch;
		this.menueart = menueart;
		this.geschmack = geschmack;

	}

	public Menue(Long id, String name, Mitarbeiter koch) {
		this.id = id;
		this.name = name;
		this.koch = koch;
	}

	public Menue(Long id) {
		this.id = id;
	}

	public Menue(Long id, String name, Mitarbeiter koch, Geschmack geschmack,
			Menueart menueart, Boolean aufwand, Boolean favorit) {
		this.id = id;
		this.name = name;
		this.koch = koch;
		this.geschmack = geschmack;
		this.menueart = menueart;
		this.aufwand = aufwand;
		this.favorit = favorit;

	}

	public Menue(String name) {
		this.name = name;
	}

	public Menue(String name, Mitarbeiter koch) {
		this.name = name;
		this.koch = koch;
	}

	public Menue() {
	}

	public List<Fussnote> getFussnoten() {
		return fussnoten;
	}

	public void setFussnoten(List<Fussnote> fussnoten) {
		this.fussnoten = fussnoten;
	}

	public List<Rezept> getRezepte() {
		return rezepte;
	}

	public void setRezepte(List<Rezept> rezepte) {
		this.rezepte = rezepte;
	}

	public String getKochname() {
		return kochname;
	}

	public void setKochname(String kochname) {
		this.kochname = kochname;
	}

	public String getGeschmackname() {
		return geschmackname;
	}

	public void setGeschmackame(String geschmackname) {
		this.geschmackname = geschmackname;
	}

	public String getMenueartame() {
		return menueartname;
	}

	public void setMenueartname(String menueartname) {
		this.menueartname = menueartname;
	}

	public Boolean getHauptgericht() {
		return hauptgericht;
	}

	public void setHauptgericht(Boolean hauptgericht) {
		this.hauptgericht = hauptgericht;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Mitarbeiter getKoch() {
		return koch;
	}

	public void setKoch(Mitarbeiter koch) {
		this.koch = koch;
	}

	public Geschmack getGeschmack() {
		return this.geschmack;
	}

	public void setGeschmack(Geschmack geschmack) {
		this.geschmack = geschmack;
	}

	public Menueart getMenueart() {
		return this.menueart;
	}

	public void setMenueart(Menueart menueart) {
		this.menueart = menueart;
	}

	public boolean getAufwand() {
		return aufwand;
	}

	public void setAufwand(boolean aufwand) {
		this.aufwand = aufwand;
	}

	public boolean getFavorit() {
		return this.favorit;
	}

	public void setFavorit(boolean favorit) {
		this.favorit = favorit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aufwand ? 1231 : 1237);
		result = prime * result + (favorit ? 1231 : 1237);
		result = prime * result
				+ ((geschmack == null) ? 0 : geschmack.hashCode());
		result = prime * result
				+ ((hauptgericht == null) ? 0 : hauptgericht.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((koch == null) ? 0 : koch.hashCode());
		result = prime * result
				+ ((kochname == null) ? 0 : kochname.hashCode());
		result = prime * result
				+ ((menueart == null) ? 0 : menueart.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Menue other = (Menue) obj;
		if (aufwand != other.aufwand)
			return false;
		if (favorit != other.favorit)
			return false;
		if (geschmack == null) {
			if (other.geschmack != null)
				return false;
		} else if (!geschmack.equals(other.geschmack))
			return false;
		if (hauptgericht == null) {
			if (other.hauptgericht != null)
				return false;
		} else if (!hauptgericht.equals(other.hauptgericht))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (koch == null) {
			if (other.koch != null)
				return false;
		} else if (!koch.equals(other.koch))
			return false;
		if (kochname == null) {
			if (other.kochname != null)
				return false;
		} else if (!kochname.equals(other.kochname))
			return false;
		if (menueart == null) {
			if (other.menueart != null)
				return false;
		} else if (!menueart.equals(other.menueart))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + id + "";
	}

}
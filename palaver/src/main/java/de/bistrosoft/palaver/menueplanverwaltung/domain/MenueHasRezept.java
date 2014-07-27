package de.bistrosoft.palaver.menueplanverwaltung.domain;

import de.palaver.management.recipe.Recipe;

public class MenueHasRezept implements java.io.Serializable {

	private MenueHasRezeptId id;
	private Menue menue;
	private Recipe recipe;
	private Boolean hauptgericht;

	public MenueHasRezept() {
	}

	public MenueHasRezept(MenueHasRezeptId id, Menue menue, Recipe recipe) {
		this.id = id;
		this.menue = menue;
		this.recipe = recipe;
	}

	public MenueHasRezept(Menue menue, Recipe recipe) {

		this.menue = menue;
		this.recipe = recipe;
	}

	public MenueHasRezept(Menue menue, Recipe recipe, Boolean hauptgericht) {

		this.menue = menue;
		this.recipe = recipe;
		this.hauptgericht = hauptgericht;
	}

	public MenueHasRezeptId getId() {
		return this.id;
	}

	public void setId(MenueHasRezeptId id) {
		this.id = id;
	}

	public Recipe getRezept() {
		return recipe; // /stand bei beidem noch this.
	}

	public void setRezept(Recipe recipe) {
		this.recipe = recipe;
	}

	public Menue getMenue() {
		return menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	public Boolean getHauptgericht() {
		return hauptgericht;
	}

	public void setHauptgericht(Boolean hauptgericht) {
		this.hauptgericht = hauptgericht;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
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
		MenueHasRezept other = (MenueHasRezept) obj;
		if (recipe == null) {
			if (other.recipe != null)
				return false;
		} else if (!recipe.equals(other.recipe))
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
		return "menueHasFussnote [fussnote=" + recipe + ", menue=" + menue
				+ "]";
	}

}

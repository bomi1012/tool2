package de.bistrosoft.palaver.menueplanverwaltung.domain;

import de.palaver.management.menu.Menu;
import de.palaver.management.recipe.Recipe;

public class MenueHasRezept implements java.io.Serializable {

	private MenueHasRezeptId id;
	private Menu menu;
	private Recipe recipe;
	private Boolean hauptgericht;

	public MenueHasRezept() {
	}

	public MenueHasRezept(MenueHasRezeptId id, Menu menu, Recipe recipe) {
		this.id = id;
		this.menu = menu;
		this.recipe = recipe;
	}

	public MenueHasRezept(Menu menu, Recipe recipe) {

		this.menu = menu;
		this.recipe = recipe;
	}

	public MenueHasRezept(Menu menu, Recipe recipe, Boolean hauptgericht) {

		this.menu = menu;
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

	public Menu getMenue() {
		return menu;
	}

	public void setMenue(Menu menu) {
		this.menu = menu;
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
		MenueHasRezept other = (MenueHasRezept) obj;
		if (recipe == null) {
			if (other.recipe != null)
				return false;
		} else if (!recipe.equals(other.recipe))
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
		return "menueHasFussnote [fussnote=" + recipe + ", menue=" + menu
				+ "]";
	}

}

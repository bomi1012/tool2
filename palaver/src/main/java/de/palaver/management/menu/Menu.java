package de.palaver.management.menu;

import java.util.List;

import de.palaver.management.emploee.Employee;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.util.entity.EntityKoch;

public class Menu extends EntityKoch {
	private static final long serialVersionUID = 4540138544043368369L;
	
	private Geschmack m_geschmack;
	public Geschmack getGeschmack() { return m_geschmack; }
	public void setGeschmack(Geschmack geschmack) { m_geschmack = geschmack; }
	
	private Menutype m_menutype;
	public Menutype getMenutype() { return m_menutype; }
	public void setMenutype(Menutype menutype) { m_menutype = menutype; }
	
	private boolean m_aufwand;
	public boolean hasAufwand() { return m_aufwand; }
	public void setAufwand(boolean aufwand) { m_aufwand = aufwand; }

	private boolean m_favorit;
	public boolean isFavorit() { return m_favorit; }
	public void setFavorit(boolean favorit) { m_favorit = favorit; }
	
	///////////////////////////
	
	private List<Recipe> m_recipeList;
	public List<Recipe> getRecipeList() { return m_recipeList; }
	public void setRecipeList(List<Recipe> recipes) { m_recipeList = recipes; }

	private List<Fussnote> m_fussnotenList;
	public List<Fussnote> getFussnotenList() { return m_fussnotenList; }
	public void setFussnotenList(List<Fussnote> fussnoten) { m_fussnotenList = fussnoten; }

	public Menu() {
		super();
	}

	public Menu(Long id, String name, Employee employee) {
		super(id, name, employee);
	}
	
	/**
	 * NEW
	 * @param id
	 * @param name
	 * @param aufwand
	 * @param favorit
	 * @param employee
	 * @param geschmack
	 * @param menutype
	 */
	public Menu(Long id, String name, boolean aufwand, boolean favorit,
			Employee employee, Geschmack geschmack, Menutype menutype) {
		super(id, name, employee);
		m_geschmack = geschmack;
		m_menutype = menutype;
		m_aufwand = aufwand;
		m_favorit = favorit;
	}

	public Menu(Long id, String name, Employee employee, Geschmack geschmack,
			Menutype menutype, boolean aufwand, boolean favorit) {
		super(id, name, employee);
		m_geschmack = geschmack;
		m_menutype = menutype;
		m_aufwand = aufwand;
		m_favorit = favorit;
	}
}
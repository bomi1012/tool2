package de.palaver.view.bean.util.wrappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Label;

import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.service.RecipeService;

public class RecipeWrapper {

	private Recipe m_recipe;
	public Recipe getRecipe() { return m_recipe; }
	
	private Label m_name = new Label();
	public Label getName() { return m_name; }
	
	private Label m_recipetype = new Label();
	public Label getRecipetype() { return m_recipetype; }
	
	private Label m_employee = new Label();
	public Label getEmployee() { return m_employee; }
	
	private Label m_kommentar = new Label();
	public Label getKommentar() { return m_kommentar; }
	
	private Date m_erstellt;
	public Date getErstellt() { return m_erstellt; }
	
	public RecipeWrapper(Recipe recipe) {
		m_recipe = recipe;
		m_name.setValue(recipe.getName());
		m_recipetype.setValue(recipe.getRecipetype().getName());
		m_employee.setValue(recipe.getEmployee().getBenutzername());
		m_kommentar.setValue(recipe.getKommentar());
		m_erstellt = recipe.getErstellt();
	}
	
	public static List<RecipeWrapper> getRecipeWrappers() {
		List<RecipeWrapper> wrappers = new ArrayList<RecipeWrapper>();
		try {
			for (Recipe recipe : RecipeService.getInstance().getAllRecipes()) {
				wrappers.add(new RecipeWrapper(recipe));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return wrappers;
	}
}

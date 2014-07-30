package de.palaver.management.recipe.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.DAO.RecipeDAO;

public class RecipeService {

	private static RecipeService m_instance = null;
	
	public RecipeService() {
		super();
	}

	public static RecipeService getInstance() {
		if (m_instance == null) {
			m_instance = new RecipeService();
		}
		return m_instance;
	}
	
	public List<Recipe> getAllRecipes() throws ConnectException, DAOException, SQLException {
		return RecipeDAO.getInstance().getAllRecipes();
	}
}

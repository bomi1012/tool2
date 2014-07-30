package de.palaver.management.recipe.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.RezeptHasArtikel;
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.DAO.RecipeDAO;
import de.palaver.management.recipe.DAO.RecipeHasArtikelDAO;
import de.palaver.management.recipe.DAO.RecipetypeDAO;
import de.palaver.management.recipe.DAO.ZubereitungDAO;
import de.palaver.view.bean.helpers.wrappers.RezeptHasArtikelWrapper;

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
	public Long createRecipe(Recipe recipe) throws ConnectException, DAOException {
		return RecipeDAO.getInstance().createRecipe(recipe);
	}
	public void updateRecipe(Recipe recipe) throws ConnectException, DAOException {
		RecipeDAO.getInstance().updateRecipe(recipe);
	}
	
	
	
	public List<Zubereitung> getAllZubereitungs() throws ConnectException, DAOException, SQLException {
		return ZubereitungDAO.getInstance().getAllZubereitungs();
	}
	public List<Zubereitung> getAllZubereitungsByRecipeId(Long recipeId) throws SQLException, ConnectException, DAOException {
		return ZubereitungDAO.getInstance().getAllZubereitungsByRecipeId(recipeId);
	}
	public void createRelationZubereitung(Long recipeId, List<Zubereitung> zubereitungs) throws ConnectException, DAOException {
		for (Zubereitung zubereitung : zubereitungs) {
			RecipeDAO.getInstance().createRelationZubereitung(recipeId, zubereitung.getId());
		}		
	}
	public void updateRelationZubereitung(Long recipeId, List<Zubereitung> setZubereitungs) throws ConnectException, DAOException {
		RecipeDAO.getInstance().removeRelationZubereitungsByRecipeId(recipeId);
		createRelationZubereitung(recipeId, setZubereitungs);
	}
	


	public List<RezeptHasArtikel> getRecipeHasArtikelByRecipeID(Recipe recipe) throws ConnectException, DAOException, SQLException {
		return RecipeHasArtikelDAO.getInstance().getRecipeHasArtikelByRecipeID(recipe);
	}
	public void createRelationItem(Long recipeId, List<RezeptHasArtikelWrapper> rezeptHasArtikelWrapperList) throws ConnectException, DAOException {
		for (RezeptHasArtikelWrapper rezeptHasArtikelWrapper : rezeptHasArtikelWrapperList) {
			RecipeHasArtikelDAO.getInstance().createRelation(recipeId, 
					rezeptHasArtikelWrapper.getRezeptHasArtikel().getArtikel().getId(),
					Double.valueOf(rezeptHasArtikelWrapper.getMenge().getValue()));
		}
	}	
	public void updateRelationItem(Long recipeId, List<RezeptHasArtikelWrapper> rezeptHasArtikelWrapperList) throws ConnectException, DAOException {
		RecipeHasArtikelDAO.getInstance().removeRelationItemByRecipeId(recipeId);
		createRelationItem(recipeId, rezeptHasArtikelWrapperList);
	}
	



	public List<Recipetype> getAllRecipetypes() throws ConnectException, DAOException, SQLException {
		return RecipetypeDAO.getInstance().getAllRecipetypes();
	}
}

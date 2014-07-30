package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.DAO.ArtikelDAO;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.RezeptHasArtikel;

public class RecipeHasArtikelDAO extends AbstractDAO {
	private static RecipeHasArtikelDAO m_instance = null;
	private static final String TABLE_RECIPE_HAS_ARTIKEL = "rezept_has_artikel";
	
	private static final String GET_QUERY_ZUBEREITUNG = "SELECT * FROM " + TABLE_RECIPE_HAS_ARTIKEL  +
			" WHERE `rezept_fk` = {0}";
	
	private static final String INSERT_QUERY_RECIPE_HAS_ARTIKEL =
			"INSERT INTO " + TABLE_RECIPE_HAS_ARTIKEL  +
			" (`rezept_fk`, `artikel_fk`, `menge`) VALUES({0},{1}, {2})";
	private static final String DELETE_QUERY_ZUBEREITUNG = "DELETE FROM " + TABLE_RECIPE_HAS_ARTIKEL  +
			" WHERE `rezept_fk` = {0}";
	
	private ArrayList<RezeptHasArtikel> m_list;
	
	public RecipeHasArtikelDAO() {
		super();
	}
	
	public static RecipeHasArtikelDAO getInstance() {
		if (m_instance == null) {
			m_instance = new RecipeHasArtikelDAO();
		}
		return m_instance;
	}
	
	public List<RezeptHasArtikel> getRecipeHasArtikelByRecipeID(Recipe recipe) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<RezeptHasArtikel>();
		m_set = getManaged(MessageFormat.format(GET_QUERY_ZUBEREITUNG, recipe.getId()));
		while (m_set.next()) {
			m_list.add(setRezeptHasArtikel(m_set, recipe));
		}		
		return m_list;
	}

	public void createRelation(Long recipeId, Long artikelId, double menge) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(INSERT_QUERY_RECIPE_HAS_ARTIKEL, recipeId, artikelId, 
				String.valueOf(menge).replace(',', '.')));			
	}

	public void removeRelationItemByRecipeId(Long recipeId) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY_ZUBEREITUNG, recipeId));		
	}

	private RezeptHasArtikel setRezeptHasArtikel(ResultSet set, Recipe recipe) throws ConnectException, DAOException, SQLException {
		return new RezeptHasArtikel(ArtikelDAO.getInstance().getArtikelById(set.getLong("artikel_fk")), 
				set.getDouble("menge"), recipe);
	}

}

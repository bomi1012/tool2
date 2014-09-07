package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.menu.Menu;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.RezeptHasArtikel;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class RecipeDAO extends AbstractDAO {

	private final static String TABLE = "rezept";
	private final static String TABLE_RECIPE_ZUBEREITUNG = "rezept_has_zubereitung";
	private final static String TABLE_RECIPE_MENU = "menue_has_rezept";
	private final static String FIELD_REZEPTART = "rezeptart_fk";
	private final static String FIELD_KOMMENTAR = "kommentar";
	private final static String FIELD_MITARBEITER = "mitarbeiter_fk";
	private final static String FIELD_ERSTELLT = "erstellt";
	private final static String FIELD_REZEPT_FK = "rezept_fk";

	private static RecipeDAO m_instance = null;
	private static final String GET_ALL_RECIPES = "SELECT r.id, r.name, r.kommentar, r.erstellt, " +
			"ra.id, ra.name, " +
			"m.id, m.benutzername FROM " + TABLE + " r, " +
			"rezeptart ra, mitarbeiter m WHERE r.rezeptart_fk = ra.id AND r.mitarbeiter_fk = m.id";	
	
	
	private final static String GET_ALL_RECIPES_BY_MENUE = "SELECT r.id, r.name, r.kommentar, r.erstellt, " +
			"ra.id, ra.name, " +
			"m.id, m.benutzername FROM " + TABLE + " r, " +
			"rezeptart ra, mitarbeiter m , menue_has_rezept mr " +
			"WHERE r.rezeptart_fk = ra.id AND r.mitarbeiter_fk = m.id " +
			"AND mr.rezept_id = r.id AND mr.menue_id = {0}";	
	
	private static final String INSERT_QUERY = "INSERT INTO " + TABLE + "("
			+ "`" + FIELD_NAME + "`, " + "`" + FIELD_KOMMENTAR + "`, "
			+ "`" + FIELD_ERSTELLT + "`, " + "`" + FIELD_MITARBEITER + "`, `"
			+ FIELD_REZEPTART + "`)"
			+ " VALUES({0},{1},{2},{3},{4})";	
	private static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + 
			"`" + FIELD_NAME + "` = {0}, `" + FIELD_KOMMENTAR + "` = {1}, `" + FIELD_MITARBEITER + "` = {2}, " +
			"`" + FIELD_REZEPTART + "` = {3} WHERE " + FIELD_ID + " = {4}";
	private static final String DELETE_QUERY = "DELETE FROM " + TABLE  + " WHERE `" + FIELD_ID + "` = {0}";
	
	
	
	private static final String INSERT_QUERY_ZUBEREITUNG = "INSERT INTO " + TABLE_RECIPE_ZUBEREITUNG  +
			" (`rezept_fk`, `zubereitung_fk`) VALUES({0},{1})";
	private static final String DELETE_QUERY_ZUBEREITUNG = "DELETE FROM " + TABLE_RECIPE_ZUBEREITUNG  +
			" WHERE `" + FIELD_REZEPT_FK + "` = {0}";
	
	
	
	
	
	



	private ArrayList<Recipe> m_list;
	private String comment;

	public RecipeDAO() {
		super();
	}

	public static RecipeDAO getInstance() {
		if (m_instance == null) {
			m_instance = new RecipeDAO();
		}
		return m_instance;
	}

	public List<Recipe> getAllRecipes() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Recipe>();
		m_set = getManaged(GET_ALL_RECIPES);
		while (m_set.next()) {
			m_list.add(setRecipeRelations(m_set));
		}		
		return m_list;		
	}
	public List<Recipe> getAllRecipesByMenuId(Long id) throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Recipe>();
		m_set = getManaged(MessageFormat.format(GET_ALL_RECIPES_BY_MENUE, id));
		while (m_set.next()) {
			m_list.add(setRecipeRelations(m_set));
		}		
		return m_list;	
	}

	public Long createRecipe(Recipe recipe) throws ConnectException, DAOException {
		Calendar calendar = Calendar.getInstance();
		setFields(recipe);
		return insert(MessageFormat.format(INSERT_QUERY,
				"'" + recipe.getName() + "'", comment,
				"'" + new java.sql.Date(calendar.getTime().getTime()) + "'",
				recipe.getEmployee().getId(), recipe.getRecipetype().getId()));
	}
	
	public void updateRecipe(Recipe recipe) throws ConnectException, DAOException {
		setFields(recipe);
		putManaged(MessageFormat.format(UPDATE_QUERY,
				"'" + recipe.getName() + "'", comment,
				recipe.getEmployee().getId(), recipe.getRecipetype().getId(),
				recipe.getId()));
	}
	
	public void removeRecipe(Long id) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY, id));			
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void removeRelationZubereitungsByRecipeId(Long recipeId) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_QUERY_ZUBEREITUNG, recipeId));		
	}
	
	public void createRelationZubereitung(Long recipeId, Long zubereitungId) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(INSERT_QUERY_ZUBEREITUNG, recipeId, zubereitungId));
		
	}
	

	
	private void setFields(Recipe recipe) {
		comment = null;
		if(recipe.getKommentar() != null) { comment = "'" + recipe.getKommentar() + "'"; }
	}
	/**
	 * r.id
	 * r.name
	 * r.kommentar
	 * r.erstellt
	 * 
	 * ra.id
	 * ra.name
	 * 
	 * m.id
	 * m.name
	 * 
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	private Recipe setRecipeRelations(ResultSet set) throws SQLException {		
		return new Recipe(set.getLong(1), set.getString(2), 
				new Recipetype(set.getLong(5), set.getString(6)), 
				new Employee(set.getLong(7), set.getString(8)),
				set.getString(3), set.getDate(4));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE rezept.name = {0}";


	
	



	// Methode, die Rezepte zu einem Menue in einer Liste zurueckliefert
	public List<Recipe> getRezepteByMenue(Menu menu) throws ConnectException,
			DAOException, SQLException {
		List<Recipe> rezepte = new ArrayList<Recipe>();
		ResultSet set = getManaged("select rez.* from menue_has_rezept mhr, rezept rez where mhr.rezept_id=rez.id and mhr.menue_id = "
				+ menu.getId());
		while (set.next()) {
			Recipe rez = new Recipe();
			Long id = set.getLong("id");
			String name = set.getString("name");
			rez.setId(id);
			rez.setName(name);
			List<RezeptHasArtikel> artikel = ladeArtikelFuerRezept(rez);
			rez.setRezeptHasArtikelList(artikel);
			Recipetype rezArt = Rezeptartverwaltung.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk"));
			rez.setRecipetype(rezArt);
			Employee koch = EmployeeService.getInstance()
					.getEmployee(set.getLong("mitarbeiter_fk"));
			rez.setEmployee(koch);
			rezepte.add(rez);
		}
		return rezepte;
	}

	// Methode, welche die Artikel zu einem Rezept lädt
	public List<RezeptHasArtikel> ladeArtikelFuerRezept(Recipe rez)
			throws ConnectException, DAOException, SQLException {
		List<RezeptHasArtikel> rha = new ArrayList<RezeptHasArtikel>();

		ResultSet set = getManaged("select * from rezept_has_artikel where rezept_fk="
				+ rez.getId());

		while (set.next()) {
			RezeptHasArtikel a = new RezeptHasArtikel();
			a.setRecipe(rez);
			a.setMenge(set.getDouble("menge"));
			Artikel artikel = ArtikelService.getInstance().getArtikelById(
					set.getLong("artikel_fk"));
			a.setArtikel(artikel);
			rha.add(a);
		}
		return rha;
	}

}

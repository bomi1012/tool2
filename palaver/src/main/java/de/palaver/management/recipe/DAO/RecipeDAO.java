package de.palaver.management.recipe.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.palaver.dao.AbstractDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.RezeptHasArtikel;

public class RecipeDAO extends AbstractDAO {

	private final static String TABLE = "rezept";
	private final static String NAME = "name";
	private final static String REZEPTART = "rezeptart_fk";
	private final static String KOMMENTAR = "kommentar";
	private final static String MITARBEITER = "mitarbeiter_fk";
	private final static String ERSTELLT = "erstellt";
	private final static String REZEPTFK = "rezept_fk";
	private final static String AKTIV = "aktiv";

	private static RecipeDAO m_instance = null;
	private static final String GET_ALL_RECIPES = "SELECT r.id, r.name, r.kommentar, r.erstellt, " +
			"ra.id, ra.name, " +
			"m.id, m.benutzername FROM " + TABLE + " r, " +
			"rezeptart ra, mitarbeiter m WHERE r.rezeptart_fk = ra.id AND r.mitarbeiter_fk = m.id";
	
	
	

	final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE rezept.name = {0}";
	private static final String GET_ALL_REZEPT_MENUE = "select r.id rid, r.name rname, m.id mid,m.vorname, m.name , m.benutzername ,ra.id raid,ra.name raname "
															+"from rezept r,mitarbeiter m,rezeptart ra "
															+"where r.mitarbeiter_fk=m.id "
															+"AND r.rezeptart_fk=ra.id "
															+"AND r.deaktivieren = 0";

	private ArrayList<Recipe> m_list;

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
	
	
	
	
	
	
	
	
	


		// Methode, die alle Rezepte f�r Men� anlegen
		public List<Recipe> getAllRezepteForMenue() throws ConnectException,
				DAOException, SQLException {
			List<Recipe> list = new ArrayList<Recipe>();
			ResultSet set = getManaged(GET_ALL_REZEPT_MENUE);
			while (set.next()) {
				Recipe recipe = new Recipe();
				recipe.setId(set.getLong(1));
				recipe.setName(set.getString(2));
				Employee koch = new Employee();
				koch.setId(set.getLong(3));
				koch.setVorname(set.getString(4));
				koch.setName(set.getString(5));
				koch.setBenutzername(set.getString(6));
				recipe.setEmployee(koch);
				
				recipe.setRecipetype(new Recipetype(set.getLong(7), set.getString(8)));
				list.add(recipe);
			}
			return list;
		}

	// Methode, die ein Rezept �ber den Name zurueckliefert
	public Recipe getRezeptByName(String namerezept) throws ConnectException,
			DAOException, SQLException {
		Recipe result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_REZEPT_BY_NAME,
				NAME));

		while (set.next()) {
			result = new Recipe(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					EmployeeService.getInstance().getEmployee(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), null);
		}

		return result;
	}

	// Methode, die ein Rezept erstellt
	public void createRezept(Recipe recipe) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ REZEPTART + "," + KOMMENTAR + "," + MITARBEITER + ","
				+ ERSTELLT + "," + AKTIV + ")" + " VALUES" + "('"
				+ recipe.getName() + "'," + recipe.getRecipetype().getId()
				+ ",'" + recipe.getKommentar() + "',"
				+ recipe.getEmployee().getId() + ",'" + recipe.getErstellt()
				+ "',true)";
		this.putManaged(INSERT_QUERY);
	}

//	// Methode, die eine Zubereitung zu einem Rezept speichert
//	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
//			throws ConnectException, DAOException, SQLException {
//		String INSERT_QUERY = "INSERT INTO rezept_has_zubereitung (rezept_fk, zubereitung_fk) VALUES"
//				+ "("
//				+ rezeptHasZubereitung.getRezept()
//				+ ", "
//				+ rezeptHasZubereitung.getZubereitung().getId() + ")";
//		this.putManaged(INSERT_QUERY);
//	}

	// Methode, die ein Rezept bearbeitet
	public void updateRezept(Recipe recipe) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "UPDATE rezept SET name = '" + recipe.getName()
				+ "'," + "rezeptart_fk=" + recipe.getRecipetype().getId() + ","
				+ "kommentar='" + recipe.getKommentar() + "',"
				+ "mitarbeiter_fk = " + recipe.getEmployee().getId() + ","
				+ "erstellt='" + recipe.getErstellt() + "' WHERE id = "
				+ recipe.getId();
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die Rezepte zu einem Menue in einer Liste zurueckliefert
	public List<Recipe> getRezepteByMenue(Menue menue) throws ConnectException,
			DAOException, SQLException {
		List<Recipe> rezepte = new ArrayList<Recipe>();
		ResultSet set = getManaged("select rez.* from menue_has_rezept mhr, rezept rez where mhr.rezept_id=rez.id and mhr.menue_id = "
				+ menue.getId());
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

	// Methode, die die Zutaten zu einem Rezept loescht
	public void deleteZutatenZuRezept(Recipe rez2) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE FROM rezept_has_artikel WHERE "
				+ REZEPTFK + "=" + rez2.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}

	// Methode, die die Zubereitung zu einem Rezept loescht
	public void deleteZubereitungZuRezept(Recipe recipe)
			throws ConnectException, DAOException {
		String DELETE_QUERY = "DELETE FROM rezept_has_zubereitung WHERE "
				+ REZEPTFK + "=" + recipe.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}

	// Methode, die ein Rezept inaktiv setzt
	public void setRezeptDisabled(Recipe rezeptAusTb) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + AKTIV
				+ "=false, deaktivieren = 1 WHERE id=" + rezeptAusTb.getId();
		this.putManaged(UPDATE_QUERY);
	}
	
	public void deaktivierung(Long id, int zahl) throws ConnectException, DAOException{
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET deaktivieren "
				+ " = " + zahl + " WHERE id=" + id;
		this.putManaged(UPDATE_QUERY);
	}
}

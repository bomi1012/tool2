package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.Util;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class MenueDAO extends AbstractDAO {
	private static MenueDAO instance;
	private final static String TABLE = "menue";
	private static final String MENUEART = "menueart_fk";
	private final static String KOCH = "koch";
	private final static String AKTIV = "aktiv";
	
	//FIXME:!!!!!!!!!!
	private static final String GET_ALL_MENUES = "SELECT m.id, m.name, m.aufwand, m.favorit, " 
			+ " k.id, k.benutzername, g.id, g.name, ma.id, ma.name "
			+ " FROM menue m, mitarbeiter k, geschmack g, menueart ma "
			+ " WHERE m.geschmack_fk = g.id "
			+ " AND m.menueart_fk = ma.id "
			+ " AND m.koch = k.id ";
	
	
	
	private final String GET_HAUPTGERICHT = "Select * from rezept join menue_has_rezept ON rezept.id = menue_has_rezept.rezept_id WHERE (menue_has_rezept.hauptgericht = true) AND (menue_has_rezept.menue_id = {0})";
	private final String GET_Beilagen = "Select * from rezept join menue_has_rezept ON rezept.id = menue_has_rezept.rezept_id WHERE (menue_has_rezept.hauptgericht = false) AND (menue_has_rezept.menue_id = {0})";
	private final String GET_MENUE_BY_NAME = "SELECT * FROM menue WHERE menue.name = {0}";
	private ArrayList<Menu> m_list;
	private static final String GET_MENUE_BY_ID = "SELECT * FROM menue WHERE id = {0}";
	private static final String GET_REZEPTE_BY_MENUE = "SELECT * FROM rezept JOIN menue_has_rezept ON rezept.id = menue_has_rezept.rezept_fk WHERE menue_has_rezept.menue_fk = {0}";

	private static final String GET_ALL_MENUES_SCHNELL = "select m.id, m.name, m.aufwand,m.favorit, k.id, k.name, k.vorname, g.id,g.name, ma.id,ma.name, m.aktiv, k.benutzername "
															+"from menue m, mitarbeiter k, geschmack g, menueart ma "
															+"where m.geschmack_fk=g.id "
															+"AND m.menueart_fk=ma.id "
															+"AND m.koch=k.id " 
															+"AND aktiv = 1";
	
	public MenueDAO() {
		super();
	}

	public static MenueDAO getInstance() {
		if (instance == null) {
			instance = new MenueDAO();
		}
		return instance;
	}
	
	
	public List<Menu> getAllMenues() throws ConnectException, DAOException, SQLException {
		m_list = new ArrayList<Menu>();
		m_set = getManaged(GET_ALL_MENUES);
		while (m_set.next()) {
			m_list.add(setMenuRelations(m_set));
		}		
		return m_list;		
	}
	
	
	
	
	
	
	/**
	 * m.id, 
	 * m.name, 
	 * m.aufwand,
	 * m.favorit,
	 * 
	 * k.id, 
	 * k.benutzername, 
	 * 
	 * g.id, 
	 * g.name, 
	 * 
	 * ma.id, 
	 * ma.name
	 * 
	 * @param set
	 * @return
	 * @throws SQLException 
	 */
	private Menu setMenuRelations(ResultSet set) throws SQLException {
		return new Menu(set.getLong(1), set.getString(2), set.getBoolean(3), set.getBoolean(4),
				new Employee(set.getLong(5), set.getString(6)), 
				new Geschmack(set.getLong(7), set.getString(8)),
				new Menutype(set.getLong(9), set.getString(10)));
	}

	
	////OLD////////
	
	
	


	public List<Menu> getAllMenuesFast() throws ConnectException, DAOException,
	SQLException {
		List<Menu> list = new ArrayList<Menu>();
		ResultSet set = getManaged(GET_ALL_MENUES_SCHNELL);
		
		while (set.next()) {
			Menu m = new Menu();
			m.setId(set.getLong(1));
			m.setName(set.getString(2));
			m.setAufwand(set.getBoolean(3));
			m.setFavorit(set.getBoolean(4));
			Employee k = new Employee();
			k.setId(set.getLong(5));
			k.setName(set.getString(6));
			k.setVorname(set.getString(7));
			k.setBenutzername(set.getString(13));
			m.setEmployee(k);
			m.setGeschmack(new Geschmack(set.getLong(8), set.getString(9)));
			m.setMenutype(new Menutype(set.getLong(10), set.getString(11)));
			list.add(m);
		}
		
		return list;
	}

	public Menu getMenueById(Long id) throws ConnectException, DAOException,
			SQLException {
		Menu menu = new Menu();
		ResultSet set = getManaged(MessageFormat.format(GET_MENUE_BY_ID, id));

		while (set.next()) {
			menu = new Menu(set.getLong("id"), set.getString("name"),
					EmployeeService.getInstance().getEmployee(
							set.getLong("koch")), GeschmackDAO.getInstance()
							.getGeschmackById(set.getLong("geschmack_fk")),
					MenutypeDAO.getInstance().getMenueartById(
							set.getLong("menueart_fk")),
					set.getBoolean("aufwand"), set.getBoolean("favorit"));
		}
		menu.setFussnotenList(Fussnotenverwaltung.getInstance()
				.getFussnoteByMenue(id));
		menu.setRecipeList(Rezeptverwaltung.getInstance()
				.getRezepteByMenue(menu));
		return menu;
	}

	public List<Recipe> getRezepteByMenue() throws ConnectException,
			DAOException, SQLException {
		List<Recipe> list = new ArrayList<Recipe>();
		ResultSet set = getManaged(GET_REZEPTE_BY_MENUE);

		while (set.next()) {
			list.add(new Recipe(set.getLong("id"), null, null, null, null));
		}

		return list;
	}

	public String getHauptgerichtByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		Recipe list = null;
		ResultSet set = getManaged(MessageFormat.format(GET_HAUPTGERICHT, id));

		while (set.next()) {
			list = new Recipe(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					EmployeeService.getInstance().getEmployee(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), null);
		}

		return list.getName();
	}

	public Recipe getHauptgerichtMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		Recipe list = null;
		ResultSet set = getManaged(MessageFormat.format(GET_HAUPTGERICHT, id));

		while (set.next()) {
			list = new Recipe(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					EmployeeService.getInstance().getEmployee(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), null);
		}

		return list;
	}

	public List<Recipe> getBeilagenByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		List<Recipe> list = new ArrayList<Recipe>();
		ResultSet set = getManaged(MessageFormat.format(GET_Beilagen, id));

		while (set.next()) {
			list.add(new Recipe(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					EmployeeService.getInstance().getEmployee(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null));
		}

		return list;
	}

	public Menu getMenueByName(String namemenue) throws ConnectException,
			DAOException, SQLException {
		Menu result = null;
		String name = "'" + namemenue + "'";
		ResultSet set = getManaged(MessageFormat
				.format(GET_MENUE_BY_NAME, name));

		while (set.next()) {
			result = new Menu(set.getLong("id"), set.getString("name"),
					EmployeeService.getInstance().getEmployee(
							set.getLong("koch")), GeschmackDAO.getInstance()
							.getGeschmackById(set.getLong("geschmack_fk")),
					MenutypeDAO.getInstance().getMenueartById(
							set.getLong("menueart_fk")),
					set.getBoolean("aufwand"), set.getBoolean("favorit"));
			List<Fussnote> fussnoten = Fussnotenverwaltung.getInstance()
					.getFussnoteByMenue(set.getLong("id"));
			result.setFussnotenList(fussnoten);
		}
		List<Recipe> rezepte = Rezeptverwaltung.getInstance()
				.getRezepteByMenue(result);
		result.setRecipeList(rezepte);
		return result;
	}

	public void createMenue(Menu menu) throws ConnectException, DAOException,
			SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_NAME + "," + KOCH
				+ ", geschmack_fk, menueart_fk, aufwand, favorit,aktiv)"
				+ " VALUES" + "('" + menu.getName() + "',"
				+ menu.getEmployee().getId() + ", " + menu.getGeschmack().getId()
				+ ", " + menu.getMenutype().getId() + ", "
				+ Util.convertBoolean(menu.hasAufwand()) + ", "
				+ Util.convertBoolean(menu.isFavorit()) + ",true)";
		this.putManaged(INSERT_QUERY);
	}

	public void createRezeptAlsMenue(Menu menu) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_NAME + "," + KOCH
				+ "," + MENUEART + ")" + " VALUES" + "('" + menu.getName()
				+ "'," + menu.getEmployee().getId() + ",1)";
		this.putManaged(INSERT_QUERY);
	}

	public void updateMenue(Menu menu) throws ConnectException, DAOException,
			SQLException {
		String INSERT_QUERY = "UPDATE " + TABLE + " SET " + FIELD_NAME + " = '"
				+ menu.getName() + "' ," + KOCH + " = "
				+ menu.getEmployee().getId() + ", geschmack_fk = "
				+ menu.getGeschmack().getId() + ", menueart_fk = "
				+ menu.getMenutype().getId() + ", aufwand = "
				+ Util.convertBoolean(menu.hasAufwand()) + ", favorit = "
				+ Util.convertBoolean(menu.isFavorit())
				+ " WHERE menue.id = " + menu.getId() + ";";
		this.putManaged(INSERT_QUERY);

		Menueverwaltung.getInstance().FussnoteDelete(menu);
		for (Fussnote fs : menu.getFussnotenList()) {
			Menueverwaltung.getInstance().FussnoteAdd(
					new MenueHasFussnote(fs, menu));
		}

		Menueverwaltung.getInstance().RezepteDelete(menu);
		for (Recipe recipe : menu.getRecipeList()) {
			RezepteAdd(new MenueHasRezept(menu, recipe));
		}
	}

	public void FussnoteAdd(MenueHasFussnote menueHasFussnote)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_fussnote (menue_fk, fussnote_fk) VALUES"
				+ "("
				+ menueHasFussnote.getMenue().getId()
				+ ", "
				+ menueHasFussnote.getFussnote().getId() + ")";
		this.putManaged(INSERT_QUERY);
	}

	public void RezepteAdd(MenueHasRezept menueHasRezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_rezept (menue_id, rezept_id, hauptgericht) VALUES"
				+ "("
				+ menueHasRezept.getMenue()
				+ ", "
				+ menueHasRezept.getRezept()
				// + ", true" + ")";
				+ "," + menueHasRezept.getHauptgericht() + ")";
		this.putManaged(INSERT_QUERY);
	}

	public void FussnoteDelete(Menu menu) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from menue_has_fussnote WHERE menue_fk = "
				+ menu.getId() + ";";

		this.putManaged(DELETE_QUERY);
	}

	public void RezepteDelete(Menu menu) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from menue_has_rezept WHERE menue_id = "
				+ menu.getId();
		this.putManaged(DELETE_QUERY);
	}

	public void speicherMenue(Menu menu) throws ConnectException,
			DAOException, SQLException {
		createMenue(menu);
		menu.setId(getMenueByName(menu.getName()).getId());
		for (Fussnote fs : menu.getFussnotenList()) {
			FussnoteAdd(new MenueHasFussnote(fs, menu));
		}

		for (Recipe recipe : menu.getRecipeList()) {
			RezepteAdd(new MenueHasRezept(menu, recipe));
		}
	}

	public void setMenueDisabled(Menu menueAusTb) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + AKTIV
				+ "=false WHERE id=" + menueAusTb.getId();
		this.putManaged(UPDATE_QUERY);
	}
}

package de.palaver.management.menu.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.emploee.Employee;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.util.Helper;
import de.palaver.management.util.dao.AbstractDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class MenuDAO extends AbstractDAO {
	private static MenuDAO instance;
	private final static String TABLE = "menue";
	private final static String TABLE_MENU_FUSSNOTE = "menue_has_fussnote";
	private final static String TABLE_MENU_RECIPE = "menue_has_rezept";
	private static final String MENUEART = "menueart_fk";
	private final static String KOCH = "koch";
	private final static String AKTIV = "aktiv";
	
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
	
	public MenuDAO() {
		super();
	}

	public static MenuDAO getInstance() {
		if (instance == null) {
			instance = new MenuDAO();
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
	 * create new menu
	 * @param menu {@link Menu}
	 * @return menuId
	 */
	public Long createMenu(Menu menu) throws ConnectException, DAOException {		
		return insert ("INSERT INTO " + TABLE + "(" + FIELD_NAME + "," + KOCH
				+ ", geschmack_fk, menueart_fk, aufwand, favorit)"
				+ " VALUES" + "('" + menu.getName() + "'," + menu.getEmployee().getId() + ", " 
				+ menu.getGeschmack().getId() + ", " + menu.getMenutype().getId() + ", "
				+ Helper.convertBoolean(menu.hasAufwand()) + ", " + Helper.convertBoolean(menu.isFavorit()) + ")");
	}
	
	/**
	 * update menu
	 * @param menu {@link Menu}
	 */
	public void updateMenu(Menu menu) throws ConnectException, DAOException {
		putManaged("UPDATE " + TABLE + " SET " + FIELD_NAME + " = '"
				+ menu.getName() + "' ," + KOCH + " = "
				+ menu.getEmployee().getId() + ", geschmack_fk = "
				+ menu.getGeschmack().getId() + ", menueart_fk = "
				+ menu.getMenutype().getId() + ", aufwand = "
				+ Helper.convertBoolean(menu.hasAufwand()) + ", favorit = "
				+ Helper.convertBoolean(menu.isFavorit())
				+ " WHERE menue.id = " + menu.getId() + ";");		
	}
	
	
	public void createRelationFussnoten(Long menuId, Long fussnoteId) throws ConnectException, DAOException {
		putManaged("INSERT INTO " + TABLE_MENU_FUSSNOTE + " (menue_fk, fussnote_fk) VALUES "
				+ "(" + menuId + ", " + fussnoteId + ")");
	}
	public void removeRelationFussnotenByMenuId(Long menuId) throws ConnectException, DAOException {
		putManaged("DELETE FROM " + TABLE_MENU_FUSSNOTE + " WHERE menue_fk = " + menuId);
	}	
	
	public void createRelationRecipe(Long menuId, Long recipeId) throws ConnectException, DAOException {
		putManaged("INSERT INTO " + TABLE_MENU_RECIPE + " (menue_id, rezept_id) VALUES "
				+ "(" + menuId + ", " + recipeId + ")");
	}
	public void removeRelationRecipeByMenuId(Long menuId) throws ConnectException, DAOException {
		this.putManaged("DELETE FROM " + TABLE_MENU_RECIPE + " WHERE menue_id = " + menuId);
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
}

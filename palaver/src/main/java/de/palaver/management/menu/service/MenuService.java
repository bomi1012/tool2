package de.palaver.management.menu.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.DAO.FussnoteDAO;
import de.palaver.management.menu.DAO.GeschmackDAO;
import de.palaver.management.menu.DAO.MenuDAO;
import de.palaver.management.menu.DAO.MenutypeDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;
import de.palaver.view.bean.util.wrappers.RecipeWrapper;

public class MenuService {

	private static MenuService m_instance = null;
	
	public MenuService() {
		super();
	}

	public static MenuService getInstance() {
		if (m_instance == null) {
			m_instance = new MenuService();
		}
		return m_instance;
	}

	//// MENU ////
	public List<Menu> getAllMenus() throws ConnectException, DAOException, SQLException {
		return MenuDAO.getInstance().getAllMenues();
	}
	public Long createMenu(Menu menu) throws ConnectException, DAOException, SQLException {
		return MenuDAO.getInstance().createMenu(menu);
	}
	public void updateMenu(Menu menu) throws ConnectException, DAOException {
		MenuDAO.getInstance().updateMenu(menu);		
	}
	
	//// MENU_RELATIONS ////
	public void createRelationFussnoten(Long menuId, List<Fussnote> fussnoten) throws ConnectException, DAOException {
		for (Fussnote fussnote : fussnoten) {
			MenuDAO.getInstance().createRelationFussnoten(menuId, fussnote.getId());
		}		
	}
	public void updateRelationFussnoten(Long menuId, List<Fussnote> fussnoten) throws ConnectException, DAOException {
		MenuDAO.getInstance().removeRelationFussnotenByMenuId(menuId);
		createRelationFussnoten(menuId, fussnoten);
	}
	public void createRelationRecipe(Long menuId, List<RecipeWrapper> recipeWrapperList) throws ConnectException, DAOException {
		for (RecipeWrapper recipeWrapper : recipeWrapperList) {
			MenuDAO.getInstance().createRelationRecipe(menuId, recipeWrapper.getRecipe().getId());
		}
	}
	public void updateRelationItem(Long menuId, List<RecipeWrapper> RecipeWrappers) throws ConnectException, DAOException {
		MenuDAO.getInstance().removeRelationRecipeByMenuId(menuId);
		createRelationRecipe(menuId, RecipeWrappers);
	}
	
	//// FUSSNOTE ////
	public List<Fussnote> getAllFussnote() throws ConnectException, DAOException, SQLException {
		return FussnoteDAO.getInstance().getAllFussnote();
	}
	public List<Fussnote> getAllFussnotenByMenuId(Long menuId) throws ConnectException, DAOException, SQLException {
		return FussnoteDAO.getInstance().getAllFussnotenByMenuId(menuId);
	}

	//// MENUTYPES ////
	public List<Menutype> getAllMenutypes() throws ConnectException, DAOException, SQLException {
		return MenutypeDAO.getInstance().getAllMenutypes();
	}

	//// GESCHMACK ////
	public List<Geschmack> getAllGeschmacks() throws ConnectException, DAOException, SQLException {
		return GeschmackDAO.getInstance().getAllGeschmack();
	}





}

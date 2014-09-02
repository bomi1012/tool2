package de.palaver.management.menu.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.DAO.FussnoteDAO;
import de.palaver.management.menu.DAO.GeschmackDAO;
import de.palaver.management.menu.DAO.MenueDAO;
import de.palaver.management.menu.DAO.MenutypeDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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
		return MenueDAO.getInstance().getAllMenues();
	}
	
	//// FUSSNOTE ////
	public List<Fussnote> getAllFussnote() throws ConnectException, DAOException, SQLException {
		return FussnoteDAO.getInstance().getAllFussnote();
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

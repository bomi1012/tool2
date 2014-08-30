package de.palaver.management.menu.service;

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
}

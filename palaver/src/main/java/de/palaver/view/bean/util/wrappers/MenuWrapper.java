package de.palaver.view.bean.util.wrappers;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Label;

import de.palaver.management.menu.Menu;
import de.palaver.management.menu.service.MenuService;

public class MenuWrapper {

	private Menu m_menu;
	public Menu getMenu() { return m_menu; }
	
	private Label m_name = new Label();
	public Label getName() { return m_name; }
	
	private Label m_menutype = new Label();
	public Label getMenutype() { return m_menutype; }
	
	private Label m_employee = new Label();
	public Label getEmployee() { return m_employee; }
	
	private Label m_geschmack = new Label();
	public Label getGeschmack() { return m_geschmack; }
	
	public MenuWrapper(Menu menu) {
		m_menu = menu;
		m_name.setValue(menu.getName());
		m_menutype.setValue(menu.getMenutype().getName());
		m_employee.setValue(menu.getEmployee().getBenutzername());
		m_geschmack.setValue(menu.getGeschmack().getName());
	}
	
	public static List<MenuWrapper> getMenuWrappers() {
		List<MenuWrapper> wrappers = new ArrayList<MenuWrapper>();
		try {
			for (Menu menu : MenuService.getInstance().getAllMenus()) {
				wrappers.add(new MenuWrapper(menu));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return wrappers;
	}
}

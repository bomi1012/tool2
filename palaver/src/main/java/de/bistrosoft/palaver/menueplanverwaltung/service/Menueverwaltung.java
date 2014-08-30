package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;

import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.DAO.MenueDAO;

public class Menueverwaltung extends MenueDAO {

	private static Menueverwaltung instance = null;

	private Menueverwaltung() {
		super();
	}

	public static Menueverwaltung getInstance() {
		if (instance == null) {
			instance = new Menueverwaltung();
		}
		return instance;
	}

	public void createMenue(Menu menu) throws ConnectException, DAOException,
			SQLException {
		super.createMenue(menu);
	}

	public void FussnoteAdd(MenueHasFussnote menueHasFussnote)
			throws ConnectException, DAOException, SQLException {
		super.FussnoteAdd(menueHasFussnote);
	}

	public void setMenueDisabled(Menu menueAusTb) throws ConnectException,
			DAOException, SQLException {
		super.setMenueDisabled(menueAusTb);
	}

}

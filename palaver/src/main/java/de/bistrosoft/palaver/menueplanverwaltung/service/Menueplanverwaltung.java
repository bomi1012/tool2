package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.MenueplanDAO;
import de.bistrosoft.palaver.menueplanverwaltung.ArtikelBedarf;
import de.bistrosoft.palaver.menueplanverwaltung.KochInMenueplan;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.util.Week;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class Menueplanverwaltung extends MenueplanDAO {

	private static Menueplanverwaltung instance = null;

	private Menueplanverwaltung() {
		super();
	}

	public static Menueplanverwaltung getInstance() {
		if (instance == null) {
			instance = new Menueplanverwaltung();
		}
		return instance;
	}

	public Menueplan getMenueplanByWeekWithItems(Week week) {
		Menueplan mpl = null;
		try {
			mpl = super.getMenueplanByWeekWithItems(week);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mpl;
	}
	
	public List<ArtikelBedarf> getArtikelBedarfByWeek(Week week){
		try {
			return super.getArtikelBedarf(week);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void persist(Menueplan menueplan) {
		try {
			if (menueplan.getId() == null) {
				super.createMenueplan(menueplan);
				menueplan.setId(super.getMenueplanByWeekWithItems(
						menueplan.getWeek()).getId());
			}
			super.deleteItemsByMenueplan(menueplan);
			for (MenueComponent mc : menueplan.getMenues()) {

				super.createMenueForMenueplan(menueplan, mc.getMenue(),
						mc.getAngezeigterName(), mc.getCol(), mc.getRow(), mc.getPortion(), mc.getFussnoten());
			}

			super.deleteKoecheByMenueplan(menueplan);
			for (KochInMenueplan kim : menueplan.getKoeche()) {
				super.createKochForMenueplan(menueplan, kim);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void delete(Menueplan menueplan) {
		try {
			super.deleteItemsByMenueplan(menueplan);
			super.deleteKoecheByMenueplan(menueplan);
		} catch (ConnectException e) {
		} catch (DAOException e) {
		}
	}

	public void updateMenueplan(Menueplan menueplan) {
		
		try {
			super.updateMenueplan(menueplan);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
//	public List<KochInMenueplan> getKoecheByMenueplan(Menueplan menueplan){
//		try {
//			return super.getKoecheByMenueplan(menueplan);
//		} catch (ConnectException e) {
//			e.printStackTrace();
//		} catch (DAOException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	public void deleteItemsByMenueplan(Menueplan mpl) {
		try {
			super.deleteItemsByMenueplan(mpl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

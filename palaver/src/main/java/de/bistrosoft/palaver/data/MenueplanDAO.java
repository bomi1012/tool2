/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.ArtikelBedarf;
import de.bistrosoft.palaver.menueplanverwaltung.KochInMenueplan;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueplanItem;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;

/**
 * @author Eike
 * 
 */
public class MenueplanDAO extends AbstractDAO {
	private static MenueplanDAO instance;
	private final String TABLE = "menueplan";
	private final String ID = "id";

	private final String GET_MENUEPLAN_BY_WEEK = "SELECT * FROM " + TABLE
			+ " WHERE week = {0} AND year = {1,number,#}";
	private final String GET_MENUES_BY_MENUEPLAN = "SELECT men.*,mhm.angezName, mhm.spalte, mhm.zeile, mhm.portion "
			+ "FROM menue men, menueplan_has_menues mhm "
			+ "WHERE men.id = mhm.menue AND mhm.menueplan = {0}";
	private final String CREATE_MENUE_FOR_MENUEPLAN = "INSERT INTO menueplan_has_menues (menueplan, menue,angezName, spalte, zeile, portion) "
			+ "VALUES ({0},{1},{2},{3},{4},{5})";
	private final String CREATE_MENUEPLAN = "INSERT INTO menueplan (week,year)  VALUES ({0},{1,number,#})";
	private final String DELETE_MENUPLANITEMS_BY_MENUEPLAN = "DELETE FROM menueplan_has_menues WHERE menueplan = {0}";
	private final String GET_ARTIKEL_BEDARF = "select rha.artikel_fk, rha.menge, rha.einheit, mhm.spalte from rezept_has_artikel rha, menueplan_has_menues mhm, menue_has_rezept mhr where rezept_fk IN "+
	"(select rezept_id from menue_has_rezept where menue_id IN"+ 
		"(select menue from menueplan_has_menues where menueplan = "+
			"(select id from menueplan where week = {0} AND year={1,number,#}))) "+
"AND mhm.menue=mhr.menue_id AND mhr.rezept_id=rha.rezept_fk AND mhm.menueplan = (select id from menueplan where week = {0} AND year={1,number,#})";

	// private final String GET_MENUES_BY_MENUEPLAN = "";

	public MenueplanDAO() {
		super();
	}

	public static MenueplanDAO getInstance() {
		if (instance == null) {
			instance = new MenueplanDAO();
		}
		return instance;
	}
	
	public List<ArtikelBedarf> getArtikelBedarf(Week week) throws ConnectException, DAOException, SQLException{
		List<ArtikelBedarf> bedarf = new ArrayList<ArtikelBedarf>();
		
		ResultSet set = getManaged(MessageFormat.format(
				GET_MENUEPLAN_BY_WEEK, week.getWeek(), week.getYear()));
		
		while (set.next()) {
			
		}
		
		return bedarf;
	}

	public Menueplan getMenueplanByWeekWithItems(Week week)
			throws ConnectException, DAOException, SQLException {
		Menueplan menueplan = null;
		ResultSet setMpl = getManaged(MessageFormat.format(
				GET_MENUEPLAN_BY_WEEK, week.getWeek(), week.getYear()));

		while (setMpl.next()) {
			menueplan = new Menueplan(setMpl.getLong(ID), week);
		}

		if (menueplan != null) {
			// TODO: K�che laden

			List<MenueComponent> menues = new ArrayList<MenueComponent>();
			// TODO: Men�s laden
			ResultSet setMenues = getManaged(MessageFormat.format(
					GET_MENUES_BY_MENUEPLAN, menueplan.getId()));

			while (setMenues.next()) {
				Long id = setMenues.getLong("id");
				String name = setMenues.getString("name");
				Mitarbeiter koch = null;
				// TODO: = new Mitarbeiter(name, vorname);
				Menue menue = new Menue(id, name, koch);
				int row = setMenues.getInt("zeile");
				int col = setMenues.getInt("spalte");
				// Rezepte hinzuf�gen
				List<Rezept> rezepte = Rezeptverwaltung.getInstance()
						.getRezepteByMenue(menue);
				menue.setRezepte(rezepte);
				List<Fussnote> fussnoten = Fussnotenverwaltung.getInstance()
						.getFussnoteByMenue(id);
				menue.setFussnoten(fussnoten);
				Geschmack geschmack = Geschmackverwaltung.getInstance()
						.getGeschmackById(setMenues.getLong("geschmack_fk"));
				menue.setGeschmack(geschmack);
				Menueart menueart = Menueartverwaltung.getInstance()
						.getMenueartById(setMenues.getLong("menueart_fk"));
				menue.setMenueart(menueart);
				String angezName = setMenues.getString("angezName");
				Integer portion = setMenues.getInt("portion");
				MenueComponent menueComp = new MenueComponent(menue, angezName,
						null, null, row, col, false,portion);
				menues.add(menueComp);
			}
			menueplan.setMenues(menues);
		}
		return menueplan;
	}

	// public Menueplan getMenueplanByWeekWithMenues(Week week) throws
	// ConnectException, DAOException, SQLException{
	// Menueplan menueplan = null;
	// ResultSet setMpl = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK,
	// week.getWeek(),week.getYear()));
	//
	// while (setMpl.next()) {
	// menueplan = new Menueplan(setMpl.getLong(ID),week);
	// }
	//
	//
	//
	// if(menueplan!=null){
	// // TODO: K�che laden
	//
	// List<MenueComponent> menues = new ArrayList<>();
	// // TODO: Men�s laden
	// ResultSet setMenues = get(MessageFormat.format(GET_MENUES_BY_MENUEPLAN,
	// menueplan.getId()));
	//
	// while (setMenues.next()) {
	// Long id = setMenues.getLong("id");
	// String name = setMenues.getString("name");
	// Mitarbeiter koch=null;
	// // TODO: = new Mitarbeiter(name, vorname);
	// Menue menue = new Menue(id, name, koch);
	// int row = setMenues.getInt("zeile");
	// int col = setMenues.getInt("spalte");
	// MenueComponent menueComp = new MenueComponent(menue, null, null, row,
	// col, false);
	// menues.add(menueComp);
	// }
	// menueplan.setMenues(menues);
	// }
	// return menueplan;
	// }

	public List<MenueplanItem> getItemsForMenueplan(Long menuplanID)
			throws ConnectException, DAOException, SQLException {
		List<MenueplanItem> items = new ArrayList<MenueplanItem>();

		ResultSet set = getManaged(MessageFormat.format(
				GET_MENUES_BY_MENUEPLAN, menuplanID));

		while (set.next()) {
			Long id = set.getLong("id");
			String name = set.getString("name");
			Mitarbeiter koch = null;
			Menue men = new Menue(id, name, koch);
			MenueplanItem item = new MenueplanItem();
			item.setMenue(men);
			int spalte = set.getInt("spalte");
			item.setSpalte(spalte);
			int zeile = set.getInt("zeile");
			item.setZeile(zeile);
			items.add(item);
		}
		return items;
	}

	public List<Menue> getMenuesByMenueplan(Long menuplanID)
			throws ConnectException, DAOException, SQLException {
		List<Menue> menues = new ArrayList<Menue>();

		List<MenueplanItem> items = getItemsForMenueplan(menuplanID);

		for (MenueplanItem i : items) {
			menues.add(i.getMenue());
		}

		return menues;
	}

	public void createMenueForMenueplan(Menueplan mpl, Menue menue,
			String name, int col, int row, Integer portion) throws ConnectException,
			DAOException {
		String strName = "'" + name + "'";
		putManaged(MessageFormat.format(CREATE_MENUE_FOR_MENUEPLAN,
				mpl.getId(), menue.getId(), strName, col, row,portion));

	}

	public void createMenueplan(Menueplan menueplan) throws ConnectException,
			DAOException {
		Week week = menueplan.getWeek();
		putManaged(MessageFormat.format(CREATE_MENUEPLAN, week.getWeek(),
				week.getYear()));
	}

	public void deleteItemsByMenueplan(Menueplan menueplan)
			throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_MENUPLANITEMS_BY_MENUEPLAN,
				menueplan.getId()));
	}

	public void createKochForMenueplan(Menueplan menueplan, KochInMenueplan kim)
			throws ConnectException, DAOException {
		String sql = "INSERT INTO menueplan_has_koeche (menueplan, spalte,position, koch) VALUES ({0},{1},{2},{3})";
		putManaged(MessageFormat.format(sql, menueplan.getId(),
				kim.getSpalte(), kim.getPosition(), kim.getKoch().getId()));

	}
	
	public List<KochInMenueplan> getKoecheByMenueplan(Menueplan menueplan) throws ConnectException, DAOException, SQLException{
		List<KochInMenueplan> kim = new ArrayList<KochInMenueplan>();
		String sql = "SELECT * FROM menueplan_has_koeche WHERE " + menueplan.getId();
		
		ResultSet set = getManaged(sql);
		
		while(set.next()){
			Integer spalte = set.getInt("spalte");
			Integer position = set.getInt("position");
			Long kochId = set.getLong("koch");
			
			KochInMenueplan k = new KochInMenueplan();
			k.setKoch(Mitarbeiterverwaltung.getInstance().getMitarbeiterById(kochId));
			k.setPosition(position);
			k.setSpalte(spalte);
			
			kim.add(k);
		}
		return kim;
	}

	public void deleteKoecheByMenueplan(Menueplan menueplan)
			throws ConnectException, DAOException {
		String sql = "DELETE FROM menueplan_has_koeche WHERE menueplan = "
				+ menueplan.getId();
		putManaged(sql);
	}
}

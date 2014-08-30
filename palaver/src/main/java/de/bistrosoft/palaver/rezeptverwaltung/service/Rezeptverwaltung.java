/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.data.RezeptartDAO;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.DAO.ArtikelDAO;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.DAO.FussnoteDAO;
import de.palaver.management.menu.DAO.GeschmackDAO;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.RezeptHasArtikel;
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.DAO.RecipeDAO;
import de.palaver.management.recipe.DAO.ZubereitungDAO;

public class Rezeptverwaltung extends RecipeDAO {

	private static Rezeptverwaltung instance = null;

	private Rezeptverwaltung() {
		super();
	}

	public static Rezeptverwaltung getInstance() {
		if (instance == null) {
			instance = new Rezeptverwaltung();
		}
		return instance;
	}

	public void createRezept(Recipe Recipe) throws ConnectException,
			DAOException, SQLException {
		super.createRezept(Recipe);
	}

	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> result = null;

		result = ZubereitungDAO.getInstance().getAllZubereitungen();

		return result;
	}

	public List<Artikel> getAllArtikelByRezeptId() throws ConnectException,
			DAOException, SQLException {
		List<Artikel> result = null;
		result = ArtikelDAO.getInstance().getActiveArtikeln();
		return result;
	}

	public List<Fussnote> getAllFussnote() throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> result = null;
		result = FussnoteDAO.getInstance().getAllFussnote();
		return result;
	}

	public List<Recipetype> Recipetype() throws ConnectException, DAOException,
			SQLException {
		List<Recipetype> result = null;

		result = RezeptartDAO.getInstance().getAllRezeptart();

		return result;
	}

	public List<Geschmack> Geschmack() throws ConnectException, DAOException,
			SQLException {
		List<Geschmack> result = null;

		result = GeschmackDAO.getInstance().getAllGeschmack();

		return result;
	}

	public void updateRezept(Recipe recipe) throws ConnectException,
			DAOException, SQLException {
		super.updateRezept(recipe);
	}

	public List<Recipe> getRezepteByMenue(Menu menu) {
		try {
			return super.getRezepteByMenue(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RezeptHasArtikel> ladeArtikelFuerRezept(Recipe rez) {
		try {
			return super.ladeArtikelFuerRezept(rez);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<RezeptHasArtikel>();
	}

	public void deleteZutatenZuRezept(Recipe recipe) throws ConnectException,
			DAOException, SQLException {
		super.deleteZutatenZuRezept(recipe);
	}

	public void deleteZubereitungZuRezept(Recipe recipe)
			throws ConnectException, DAOException {
		super.deleteZubereitungZuRezept(recipe);
	}

	// public void addZutat(RezeptHasArtikel rezeptHasArtikel) throws
	// ConnectException,
	// DAOException, SQLException {
	//
	// super.addZutat(rezeptHasArtikel);
	// }

	public void setRezeptDisabled(Recipe rezeptAusTb) throws ConnectException,
			DAOException, SQLException {
		super.setRezeptDisabled(rezeptAusTb);
	}

	public void deaktiviereRezept(Long id, boolean action) throws ConnectException, DAOException {
		if (action) {
			super.deaktivierung(id, 1);
		} else {
			super.deaktivierung(id, 0);
		}
	}

}

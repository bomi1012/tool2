/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.palaver.management.menu.Menu;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.RezeptHasArtikel;
import de.palaver.management.recipe.DAO.RecipeDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

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
}
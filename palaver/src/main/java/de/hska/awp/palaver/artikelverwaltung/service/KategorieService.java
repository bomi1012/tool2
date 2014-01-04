package de.hska.awp.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.KategorieDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class KategorieService extends KategorieDAO {
	
	private static KategorieService instance = null;
	public KategorieService() {
		super();
	}
	
	public static KategorieService getInstance() {
		if (instance == null) {
			instance = new KategorieService();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Kategorien zurück.
	 * @return List<Kategorie>
	 */
	public List<Kategorie> getAllKategories() throws ConnectException,
			DAOException, SQLException {
		return super.getAllKategories();
	}

	/**
	 * Die Methode liefert eine Kategorie anhand des Parameter id zurück.
	 * @param id 
	 * @return Kategorie
	 */
	public Kategorie getKategorieById(Long id) throws ConnectException,
			DAOException, SQLException {
		return super.getKategorieById(id);
	}
	
	public void createKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {
		super.createKategorie(kategorie);
	}

	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		super.updateKategorie(kategorie);
	}
	
	public void deleteKategorie(Long id) throws ConnectException, DAOException {
		super.deleteKategorie(id); 		
	}
}

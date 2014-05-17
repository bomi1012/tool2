package de.palaver.management.artikel.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.DAO.KategorieDAO;

public class KategorieService {
	
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
		return KategorieDAO.getInstance().getAllKategories();
	}

	/**
	 * Die Methode liefert eine Kategorie anhand des Parameter id zurück.
	 * @param id 
	 * @return Kategorie
	 */
	public Kategorie getKategorieById(Long id) throws ConnectException,
			DAOException, SQLException {
		return KategorieDAO.getInstance().getKategorieById(id);
	}
	
	public void createKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {
		KategorieDAO.getInstance().createKategorie(kategorie);
	}

	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		KategorieDAO.getInstance().updateKategorie(kategorie);
	}
	
	public void deleteKategorie(Long id) throws ConnectException, DAOException {
		KategorieDAO.getInstance().deleteKategorie(id); 		
	}
}

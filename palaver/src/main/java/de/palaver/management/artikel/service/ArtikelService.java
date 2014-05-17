package de.palaver.management.artikel.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.DAO.ArtikelDAO;

public class ArtikelService {
	private static ArtikelService instance = null;
	private static final Logger LOG = LoggerFactory.getLogger(ArtikelService.class.getName());
	private ArtikelService() {
		super();
	}

	public static ArtikelService getInstance() {
		if (instance == null) {
			instance = new ArtikelService();
		}
		return instance;
	}

	public List<Artikel> getActiveArtikeln() {
		try {
			return  ArtikelDAO.getInstance().getActiveArtikeln();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.toString());
		} 
		return null;
	}

	public Artikel getArtikelById(Long id) throws ConnectException,
			DAOException, SQLException {
		return ArtikelDAO.getInstance().getArtikelById(id);
	}

	public List<Artikel> getArtikelByName(String name) throws ConnectException,
			DAOException, SQLException {
		return ArtikelDAO.getInstance().getArtikelByName(name);
	}


	public List<Artikel> getActiveArtikelByLieferantId(Long id) throws ConnectException,
	DAOException, SQLException {
		return ArtikelDAO.getInstance().getActiveArtikelByLieferantId(id);
	}
	
	public List<Artikel> getActiveArtikelnByKategorieId(Long id) throws ConnectException, DAOException, SQLException {
		return ArtikelDAO.getInstance().getActiveArtikelnByKategorieId(id);
	}

	public void createArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		ArtikelDAO.getInstance().createArtikel(artikel);
	}

	public void updateArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		ArtikelDAO.getInstance().updateArtikel(artikel);
	}

	public void deaktivireArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		ArtikelDAO.getInstance().deaktivirenArtikel(artikel.getId());
	}

	public List<Artikel> getGrundbedarfByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		return ArtikelDAO.getInstance().getGrundbedarfByLieferantId(id);
	}

	public List<Artikel> getArtikelByGrundbedarf() throws ConnectException, DAOException, SQLException {
		return ArtikelDAO.getInstance().getArtikelByGrundbedarf();
	}
}

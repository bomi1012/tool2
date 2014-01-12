/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.palaver.service.artikelverwaltung;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.artikelverwaltung.ArtikelDAO;
import de.palaver.domain.artikelverwaltung.Artikel;

public class ArtikelService extends ArtikelDAO {
	private static ArtikelService instance = null;
	
	private ArtikelService() {
		super();
	}

	public static ArtikelService getInstance() {
		if (instance == null) {
			instance = new ArtikelService();
		}
		return instance;
	}

	public List<Artikel> getActiveArtikeln() throws ConnectException, DAOException,
			SQLException {
		return  super.getActiveArtikeln();		
	}

	public Artikel getArtikelById(Long id) throws ConnectException,
			DAOException, SQLException {
		return super.getArtikelById(id);
	}

	public List<Artikel> getArtikelByName(String name) throws ConnectException,
			DAOException, SQLException {
		return super.getArtikelByName(name);
	}


	public List<Artikel> getActiveArtikelByLieferantId(Long id) throws ConnectException,
	DAOException, SQLException {
		return super.getActiveArtikelByLieferantId(id);
	}
	
	public List<Artikel> getActiveArtikelnByKategorieId(Long id) throws ConnectException, DAOException, SQLException {
		return super.getActiveArtikelnByKategorieId(id);
	}

	public void createArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		super.createArtikel(artikel);
	}

	public void updateArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		super.updateArtikel(artikel);
	}

	public void deaktivireArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		super.deaktivirenArtikel(artikel.getId());
	}

	public List<Artikel> getGrundbedarfByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		return super.getGrundbedarfByLieferantId(id);
	}
}

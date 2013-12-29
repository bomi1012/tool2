/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.hska.awp.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.ArtikelDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public class Artikelverwaltung extends ArtikelDAO {
	private static Artikelverwaltung instance = null;
	
	private Artikelverwaltung() {
		super();
	}

	public static Artikelverwaltung getInstance() {
		if (instance == null) {
			instance = new Artikelverwaltung();
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

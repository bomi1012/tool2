/**
 * 
 */
package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasFussnote;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.DAO.ArtikelDAO;

/**
 * @author Christine Hartkorn, Jasmin Baumgartner
 * 
 */
public class Kuchenrezeptverwaltung extends KuchenrezeptDAO {

	private static Kuchenrezeptverwaltung instance = null;

	// Konstruktor
	private Kuchenrezeptverwaltung() {
		super();
	}

	// Methode, die eine Instanz der Kuchenrezeptverwaltung zurückliefert
	public static Kuchenrezeptverwaltung getInstance() {
		if (instance == null) {
			instance = new Kuchenrezeptverwaltung();
		}
		return instance;
	}

	// Methode, die Kuchenrezept speichert
	public void createKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.createKuchenrezept(kuchenrezept);
	}

	// Methode, die Artikel zu Kuchenrezept speichert
	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		super.saveArtikel(kuchenrezept);
	}

	// Methode, die alle Kuchenrezepte zurückliefert
	public List<Kuchenrezept> getAllKuchenrezepte() throws ConnectException,
			DAOException, SQLException {
		List<Kuchenrezept> result = null;
		result = super.getAllKuchenrezepte(true);
		return result;
	}

	// Methode, die alle Artikel zu einem Kuchenrezept zurückliefert
	public List<Artikel> getAllArtikelByKuchenrezeptId()
			throws ConnectException, DAOException, SQLException {
		List<Artikel> result = null;

		result = ArtikelDAO.getInstance().getActiveArtikeln();

		return result;
	}

	// Methode, die Kuchenrezepte updatet
	public void updateRezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.updateKuchenrezept(kuchenrezept);
	}

	// Methode, die alle Artikel zu einem Kuchenrezept zurückliefert
	public List<KuchenrezeptHasArtikel> ladeArtikelFuerKuchenrezept(
			Kuchenrezept rez) {
		try {
			return super.ladeArtikelFuerKuchenrezept(rez);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<KuchenrezeptHasArtikel>();
	}

	// Methode, die Fußnoten zum Kuchenrezept hinzufügt
	public void FussnoteKuchenAdd(KuchenrezeptHasFussnote kuchenHasFussnote)
			throws ConnectException, DAOException, SQLException {
		super.FussnoteKuchenAdd(kuchenHasFussnote);
	}

	// Methode, die Artikel eines Kuchenrezeptes löscht
	public void deleteZutatenZuKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.deleteZutatenZuKuchenrezept(kuchenrezept);
	}

	// Methode, die Kuchenrezept inaktiv setzt
	public void setKuchenrezeptDisabled(Kuchenrezept kuchenrezeptAusTb)
			throws ConnectException, DAOException, SQLException {
		super.setKuchenrezeptDisabled(kuchenrezeptAusTb);
	}

}

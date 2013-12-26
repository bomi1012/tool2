/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver.artikelverwaltung.dao.ArtikelDAO;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;

/**
 * @author Jan Lauinger
 * 
 */
public class Rezeptverwaltung extends RezeptDAO {

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

	public void createRezept(Rezept Rezept) throws ConnectException,
			DAOException, SQLException {
		super.createRezept(Rezept);
	}

	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> result = null;

		result = ZubereitungDAO.getInstance().getAllZubereitung();

		return result;
	}

	public void saveArtikel(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		super.saveArtikel(rezept);
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

	public List<Rezeptart> Rezeptart() throws ConnectException, DAOException,
			SQLException {
		List<Rezeptart> result = null;

		result = RezeptartDAO.getInstance().getAllRezeptart();

		return result;
	}

	public List<Geschmack> Geschmack() throws ConnectException, DAOException,
			SQLException {
		List<Geschmack> result = null;

		result = GeschmackDAO.getInstance().getAllGeschmack();

		return result;
	}

	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
			throws ConnectException, DAOException, SQLException {
		super.ZubereitungAdd(rezeptHasZubereitung);

	}

	public void updateRezept(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		super.updateRezept(rezept);
	}

	public List<Rezept> getRezepteByMenue(Menue menue) {
		try {
			return super.getRezepteByMenue(menue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RezeptHasArtikel> ladeArtikelFuerRezept(Rezept rez) {
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

	public void deleteZutatenZuRezept(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		super.deleteZutatenZuRezept(rezept);
	}

	public void deleteZubereitungZuRezept(Rezept rezept)
			throws ConnectException, DAOException {
		super.deleteZubereitungZuRezept(rezept);
	}

	// public void addZutat(RezeptHasArtikel rezeptHasArtikel) throws
	// ConnectException,
	// DAOException, SQLException {
	//
	// super.addZutat(rezeptHasArtikel);
	// }

	public void setRezeptDisabled(Rezept rezeptAusTb) throws ConnectException,
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

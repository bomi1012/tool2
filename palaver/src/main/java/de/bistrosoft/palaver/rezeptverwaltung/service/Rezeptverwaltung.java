/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

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
	
//	public void createArtikelForRezept(Rezept rezept){
//		try {
//			super.createArtikelForRezept(rezept);
//		} catch (ConnectException | DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public List<RezeptHasArtikel> getArtikelByRezept(Rezept rezept){
//		List<RezeptHasArtikel> artikel=null;
//		
//		try {
//			super.getArtikelByRezept(rezept);
//		} catch (ConnectException | DAOException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return artikel;
//	}

	// public List<Rezept> getAllRezept() throws ConnectException, DAOException,
	// SQLException {
	// List<Rezept> result = null;
	//
	// result = super.getAllRezept();
	//
	// return result;
	// }
	//
	// public Rezept getRezeptById(Long id) throws ConnectException,
	// DAOException,
	// SQLException {
	// Rezept result = null;
	//
	// result = super.getRezeptById(id);
	//
	// return result;
	// }
	//
	// public List<Rezept> getRezeptByName(String name) throws ConnectException,
	// DAOException, SQLException {
	// List<Rezept> result = null;
	//
	// result = super.getRezeptByName(name);
	//
	// return result;
	// }

	public void createRezept(Rezept Rezept) throws ConnectException,
			DAOException, SQLException {
		super.createRezept(Rezept);
	}


//	public void addArtikel(Artikel a, BigDecimal m, Rezept r, Mengeneinheit me) throws ConnectException,
//	DAOException, SQLException {
//		this.Rezept.getId();
//	}
	
	// public void updateRezept(Rezept Rezept) throws ConnectException,
	// DAOException {
	// super.updateRezept(Rezept);
	// }

	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> result = null;

		result = ZubereitungDAO.getInstance().getAllZubereitung();

		return result;
	}

	public List<Artikel> getAllArtikelByRezeptId() throws ConnectException,
			DAOException, SQLException {
		List<Artikel> result = null;

		result = ArtikelDAO.getInstance().getAllArtikel();

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
	
//	public void addZutat(RezeptHasArtikel rezeptHasArtikel) throws ConnectException,
//	DAOException, SQLException {
//
//		super.addZutat(rezeptHasArtikel);
//	}

}
package de.palaver.service.person.lieferantenverwaltung;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.person.lieferantenverwaltung.LieferantDAO;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;


public class LieferantenService {

	private static LieferantenService instance = null;

	private LieferantenService() {
		super();
	}

	public static LieferantenService getInstance() {
		if (instance == null) {
			instance = new LieferantenService();
		}
		return instance;
	}

	public List<Lieferant> getAllLieferanten() throws ConnectException, DAOException, SQLException {
		return LieferantDAO.getInstance().getActiveLieferanten();
	}

	public List<Lieferant> getLieferantenByGrundbedarf(boolean b) throws SQLException, ConnectException, DAOException {
		return LieferantDAO.getInstance().getLieferantenByGrundbedarf(b);
	}

	public Long createLieferant(Lieferant lieferant) throws ConnectException, DAOException {
		return LieferantDAO.getInstance().createLieferant(lieferant);
	}

	public void updateLieferant(Lieferant lieferant) throws ConnectException, DAOException {
		LieferantDAO.getInstance().updateLieferant(lieferant);
		
	}

	public void deleteLieferant(Long id) throws ConnectException, DAOException {
		LieferantDAO.getInstance().deleteLieferant(id);		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	/**
//	 * Die Methode liefert alle Lieferanten zurück.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public List<Lieferant> getAllLieferanten() throws ConnectException, DAOException, SQLException {
//
//		List<Lieferant> result = null;
//
//		result = LieferantDAO.getInstance().getAllLieferanten();
//
//		return result;
//	}
//
//	/**
//	 * Die Methode liefert einen oder mehrere Lieferanten anhand des Parameter
//	 * name zurück.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public List<Lieferant> getLieferantenByName(String name) throws ConnectException, DAOException, SQLException {
//
//		List<Lieferant> result = null;
//
//		result = LieferantDAO.getInstance().getLieferantenByName(name);
//
//		return result;
//	}
//
//	/**
//	 * Die Methode liefert einen Lieferanten anhand des Parameter id zurück.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public Lieferant getLieferantById(Long id) throws ConnectException, DAOException, SQLException {
//		Lieferant lieferant = LieferantDAO.getInstance().getLieferantById(id);
//		return lieferant;
//	}

//	/**
//	 * Die Methode liefert den letzten Lieferant in der Datenbank zurück.
//	 * 
//	 * @return
//	 * @throws ConnectException
//	 * @throws DAOException
//	 * @throws SQLException
//	 */
//	public Lieferant getLastLieferant() throws ConnectException, DAOException, SQLException {
//		List<Lieferant> lieferanten = super.getAllLieferantenForShow();
//		return lieferanten.get(lieferanten.size()-1);
//	}
//
//	/**
//	 * Die Methode erzeugt einen Lieferant.
//	 * 
//	 * @return
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public void createLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
//
//		super.createLieferant(lieferant);
//	}
//
//	/**
//	 * Die Methode aktualisiert einen Lieferant.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public void updateLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
//
//		super.updateLieferant(lieferant);
//
//	}
//
//	
//	public void deaktiviereLieferant(Long id, boolean action) throws ConnectException, DAOException, SQLException{
//		if(action){
//			super.deaktivierung(id, 1);
//		} else {
//			super.deaktivierung(id, 0);
//		}
//	}
//
//	public List<Lieferant> getLieferantenByGrundbedarf(boolean hatGrundbedarf) throws SQLException, ConnectException, DAOException {
//		return super.getLieferantenByGrundbedarf(hatGrundbedarf);
//	}	
}

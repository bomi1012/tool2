package de.palaver.management.supplier.service;

import java.sql.SQLException;
import java.util.List;

import de.palaver.management.info.person.DAO.AdresseDAO;
import de.palaver.management.info.person.DAO.KontakteDAO;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.DAO.AnsprechpartnerDAO;
import de.palaver.management.supplier.DAO.SupplierDAO;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;


public class SupplierService {

	private static SupplierService instance = null;

	private SupplierService() {
		super();
	}

	public static SupplierService getInstance() {
		if (instance == null) {
			instance = new SupplierService();
		}
		return instance;
	}

	public List<Supplier> getAllLieferanten() throws ConnectException, DAOException, SQLException {
		return SupplierDAO.getInstance().getActiveLieferanten();
	}

	public List<Supplier> getLieferantenByGrundbedarf(boolean b) throws SQLException, ConnectException, DAOException {
		return SupplierDAO.getInstance().getLieferantenByGrundbedarf(b);
	}

	public Long createLieferant(Supplier supplier) throws ConnectException, DAOException {
		return SupplierDAO.getInstance().createLieferant(supplier);
	}

	public void updateLieferant(Supplier supplier) throws ConnectException, DAOException {
		SupplierDAO.getInstance().updateLieferant(supplier);
		
	}

	public void removeSupplier(Supplier supplier) throws ConnectException, DAOException {		
		AnsprechpartnerDAO.getInstance().deleteAnsprechpartnerByLieferantId(supplier.getId());
		SupplierDAO.getInstance().removeSupplier(supplier.getId());
		if(supplier.getAdresse() != null) {
			AdresseDAO.getInstance().deleteAdresse(supplier.getAdresse().getId());
		}
		if(supplier.getKontakte() != null) {
			KontakteDAO.getInstance().deleteKontakte(supplier.getKontakte().getId());
		}
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

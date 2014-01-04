/**
 * Created by Elena W
 */

package de.hska.awp.palaver.bestellverwaltung.service;

import de.hska.awp.palaver.bestellverwaltung.dao.BestellpositionDAO;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

/**
 * Die Klasse ermöglicht die Verwaltung der BEstellpositionen und stellt für die GUI
 * Methoden bereit.
 * 
 * @author Elena W
 */

public class BestellpositionService extends BestellpositionDAO {
	
	private static BestellpositionService instance = null;

	private BestellpositionService() {
		super();
	}

	public static BestellpositionService getInstance() {
		if (instance == null) {
			instance = new BestellpositionService();
		}
		return instance;
	}
	
		
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException, DAOException {
		super.createBestellposition(bestellposition);
	}
	
	
	
	
	
	
	
	
//	/**
//	 * Die Methode erzeugt eine Bestellposition.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public void createBestellposition(Bestellposition bestellposition) throws ConnectException,
//			DAOException, SQLException {
//
//		try {
//			super.createBestellposition(bestellposition);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Die Methode aktualisiert eine Bestellposition.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException,
//			DAOException, SQLException {
//
//		super.updateBestellposition(bestellposition);
//
//	}
//	
//	/**
//	 * Die Methode löscht eine Bestellposition.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	public void deleteBestellposition(Long id)
//			throws ConnectException, DAOException, SQLException {
//
//		super.deleteBestellposition(id);
//
//	}
//	
//	/**
//	 * Die Methode liefert eine Bestellposition anhand des Parameter id zurÃ¼ck.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
////	public Bestellposition getBestellpositionById(Long id) throws ConnectException,
////			DAOException, SQLException {
////		Bestellposition bestellposition = super.getBestellpositionById(id);
////		return bestellposition;
////	}
//	
//	/**
//	 * Die Methode wandelt eine Liste von BestellungData Objekten um in eine Liste mit Bestellpositionen. 
//	 * Verwendet wird die Methode beim erstellen einer Manuellen Bestellung.
//	 * @author Philipp Tunggul
//	 * @param bestData
//	 * @return
//	 */
//	public List<Bestellposition> getBestellpositionen(List<BestellungData> bestData) {
//		List<Bestellposition> bpen = new ArrayList<Bestellposition>();
//		 for( int i = 0; i < bestData.size(); ++i){
//			 Bestellposition bp = new Bestellposition();
//			 bp.setArtikel(bestData.get(i).getBestellungArtikel());
//			 bp.setDurchschnitt(BestellungData.getInt(bestData.get(i).getDurchschnitt().getValue()));
//			 bp.setKantine(BestellungData.getInt(bestData.get(i).getKantine().getValue()));
//			 bp.setGesamt(BestellungData.getInt(bestData.get(i).getGesamt().getValue()));
//			 bp.setFreitag(bestData.get(i).getFreitag().getValue());
//			 bp.setMontag(bestData.get(i).getMontag().getValue());
//			 bpen.add(bp);
//		 }
//		 return bpen;
//	}
//	
//	/**
//	 * Die Methode wandelt eine Liste von BestellungData Objekten um in eine Liste mit Bestellpositionen.
//	 * Verwendet wird die Methode um die Bestellung mit seinen Bestellpositionen 
//	 * an der Oberfläche darstellen zu können.
//	 * @author Christian Barth
//	 * @param bestData
//	 * @return
//	 */
//	public List<Bestellposition> getBestellpositionenMitId(List<BestellungData> bestData) {
//		List<Bestellposition> bpen = new ArrayList<Bestellposition>();
//		 for( int i = 0; i < bestData.size(); ++i){
//			 Bestellposition bp = new Bestellposition();
//			 bp.setId(bestData.get(i).getId());
//			 bp.setArtikel(bestData.get(i).getBestellungArtikel());
//			 bp.setDurchschnitt(BestellungData.getInt(bestData.get(i).getDurchschnitt().getValue()));
//			 bp.setKantine(BestellungData.getInt(bestData.get(i).getKantine().getValue()));
//			 bp.setGesamt(BestellungData.getInt(bestData.get(i).getGesamt().getValue()));
//			 bp.setFreitag(bestData.get(i).getFreitag().getValue());
//			 bp.setMontag(bestData.get(i).getMontag().getValue());
//			 bpen.add(bp);
//		 }
//		 return bpen;
//	}
	
}

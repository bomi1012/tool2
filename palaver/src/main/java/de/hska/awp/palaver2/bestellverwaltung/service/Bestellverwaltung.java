/**
 * Created by Christian Barth
 * 06.05.2013 - 10:43:40
 */
package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author Christian Barth
 * @author Mihail Boehm
 */
public class Bestellverwaltung extends BestellungDAO {

	private static Bestellverwaltung instance = null;

	public Bestellverwaltung() {
		super();
	}

	public static Bestellverwaltung getInstance() {
		if (instance == null) {
			instance = new Bestellverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode erzeugt eine Bestellung mit seinen Bestellposition.
	 * 
	 * @auhtor Christian Barth
	 */
	public void createBestellung(Bestellung bestellung) throws ConnectException, DAOException, SQLException, ParseException {
		super.createBestellung(bestellung);
	}

	/**
	 * Die Methode aktualisiert eine Bestellung mit seinen Bestellposition.
	 * 
	 * @auhtor Christian Barth
	 */
	public void updateBestellung(Bestellung bestellung) throws ConnectException, DAOException, SQLException, ParseException {
		super.updateBestellung(bestellung);
	}

	/**
	 * Die Methode aktualisiert eine Bestellung ohne die Bestellpositionen zu
	 * ber�cksichtigen.
	 * 
	 * @author Christian Barth
	 */
	public void updateBestellungOhneBP(Bestellung bestellung) throws ConnectException, DAOException, SQLException {
		super.updateBestellungOhneBP(bestellung);
	}

	/**
	 * Die Methode liefert alle Bestellungen zur�ck.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Bestellung>
	 */
	public List<Bestellung> getAllBestellungen() throws ConnectException, DAOException, SQLException {

		List<Bestellung> result = null;

		result = super.getAllBestellungen();

		return result;
	}

	/**
	 * Die Methode liefert alle Bestellungen, die noch nicht bestellt sind
	 * zur�ck.
	 * 
	 * @author Christian Barth
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Bestellungen
	 */
	public List<Bestellung> getAllBestellungenNotOrdered() throws ConnectException, DAOException, SQLException {

		List<Bestellung> result = null;

		result = super.getAllBestellungenNotOrdered();

		return result;
	}

	/**
	 * Die Methode liefert die Bestellungen der letzten 3 Wochen zur�ck.
	 * 
	 * @author Philipp Tunggul
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return
	 */
	public List<Bestellung> getBestellungenLTWeeks() throws ConnectException, DAOException, SQLException {

		List<Bestellung> result = null;

		result = super.getBestellungenLTWeeks();

		return result;
	}

	/**
	 * Die Methode liefert eine Bestellung anhand des Parameter id zur�ck.
	 * 
	 * @param id
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Bestellung
	 */
	public Bestellung getBestellungById(Long id) throws ConnectException, DAOException, SQLException {
		Bestellung bestellung = super.getBestellungById(id);
		return bestellung;
	}

	/**
	 * Die Methode liefert alle Artikel von einem Lieferanten zur�ck.
	 * 
	 * @param lieferant
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getAllArtikelByLieferant(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		List<Artikel> list = null;
		ArtikelDAO adao = ArtikelDAO.getInstance();
		adao.getAllArtikelByLieferantId(lieferant.getId());
		return list;
	}

	/**
	 * Die Methode gibt eine Liste mit Lieferanten zur�ck anhand einer
	 * Artikelliste, wobei jedoch nur jeder Lieferant nur einmal in der Liste
	 * erscheint.
	 * 
	 * @author Christian Barth
	 * @param artikellist
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getLieferantenWithArtikel() throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();
		list = LieferantDAO.getInstance().getLieferantenWithArtikel();

		return list;
	}

	/**
	 * Die Methode generiert alle Bestellungen anhand dem Men�plan und dem
	 * Grundbedarf und speichert anschlie�end diese in der Datenbank 
	 * 
	 * @author Christian Barth
	 * 
	 * @param week
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	// Week week als input
	public void generateAllBestellungenByMenueplanAndGrundbedarf(Week week) throws ConnectException, DAOException, SQLException, ParseException {
		List<Bestellposition> list = new ArrayList<Bestellposition>();
		// 1. Menueplan holen
		System.out.println("Woche: ");
		System.out.println(week);

		Menueplan mp = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(week);
		System.out.println("Men�plan: ");
		System.out.println(mp);

		List<RezeptHasArtikel> rhafreitag = new ArrayList<RezeptHasArtikel>();
		for (int i = 0; i < mp.getMenues().size(); i++) {
			if (mp.getMenues().get(i).col == 5) {
				for (int j = 0; j < mp.getMenues().get(i).getMenue().getRezepte().size(); j++) {

					for (int z = 0; z < mp.getMenues().get(i).getMenue().getRezepte().get(j).getArtikel().size(); z++) {
						rhafreitag.add(mp.getMenues().get(i).getMenue().getRezepte().get(j).getArtikel().get(z));
					}

				}
			}
		}
		System.out.println("RezeptHasArtikelliste: ");
		System.out.println(rhafreitag);

		// TODO
		// 2. Das kleinsten Datum auslesen und setzen
		// Date kleinstedatum = null;
		// 3. Get List<RezeptHasArtikel> von Menueplan mit gro�ten datum aus der
		// Woche (Freitag)
		// TODO
		// List<RezeptHasArtikel> rhafreitag = null;

		// TODO TESTDATEN
		// List<RezeptHasArtikel> rhafreitag = new
		// ArrayList<RezeptHasArtikel>();

		// for(long i = 1 ; i < 10 ; i++) {
		// RezeptHasArtikel rha = new RezeptHasArtikel();
		// rha.setArtike(Artikelverwaltung.getInstance().getArtikelById(i));
		// rha.setMenge(Double.valueOf("2000"));
		// rhafreitag.add(rha);
		// }
		// //einen Doppelten Artikel erzeugen und hinzuf�gen.
		// RezeptHasArtikel rha1 = new RezeptHasArtikel();
		// rha1.setArtike(Artikelverwaltung.getInstance().getArtikelById(Long.valueOf("1")));
		// rha1.setMenge(Double.valueOf("1000"));
		// rhafreitag.add(rha1);
		//
		// rha1.setArtike(Artikelverwaltung.getInstance().getArtikelById(Long.valueOf("369")));
		// rha1.setMenge(Double.valueOf("1000"));
		// rhafreitag.add(rha1);
		//
		// System.out.println(rhafreitag);

		// 3.1 Liste aufbereiten und doppelt Artikel zusammenf�gen und Menge
		// addieren um sp�ter Rundungsfehler zu minimieren
		List<RezeptHasArtikel> rha = mengeAddSameArtikel(rhafreitag);

		// 4. F�ge List<RezeptHasArtikel> von Menueplan mit kleinste datum in
		// List<Bestellposition>
		int leer = 0;

		Bestellposition bpo = new Bestellposition();
		bpo.setArtikel(rha.get(0).getArtikel());
		bpo.setDurchschnitt(rha.get(0).getArtikel().getDurchschnitt());
		bpo.setFreitag(convertMenge(rha.get(0)) + bpo.getDurchschnitt());
		bpo.setKantine(convertMenge(rha.get(0)));
		bpo.setGeliefert(false);
		bpo.setMontag(leer);
		bpo.setGesamt(bpo.getKantine() + bpo.getDurchschnitt());
		list.add(bpo);

		for (int i = 1; i < rha.size(); i++) {
			Bestellposition bp = new Bestellposition();
			bp.setArtikel(rha.get(i).getArtikel());
			// 5. Umrechen der Menge Menge/Bestellgr��e durch die Methode
			// convertBestellmenge
			bp.setDurchschnitt(rha.get(i).getArtikel().getDurchschnitt());
			// 6. Setzen der Menge auf Freitag und Durchschnitt hinzuaddieren
			bp.setFreitag(convertMenge(rha.get(i)) + bp.getDurchschnitt());
			bp.setKantine(convertMenge(rha.get(i)));
			bp.setGeliefert(false);
			bp.setMontag(leer);
			bp.setGesamt(bp.getKantine() + bp.getDurchschnitt());

			boolean vorhanden = false;
			for (int z = 0; z < list.size(); z++) {
				if (bp.getArtikel().equals(list.get(z).getArtikel())) {
					vorhanden = true;
					list.get(z).setFreitag(list.get(z).getFreitag() + bp.getFreitag());
					list.get(z).setGesamt(list.get(z).getGesamt() + bp.getFreitag() - bp.getDurchschnitt());
					list.get(z).setKantine(list.get(z).getKantine() + bp.getKantine());
				}
			}
			if (vorhanden == false) {
				list.add(bp);
			}
		}

		// 8. Get List<RezeptHasArtikel> von Menueplan f�r Mo,Di,Mi,Do aus der
		// Woche
		// TODO
		List<RezeptHasArtikel> rhamobisdo = new ArrayList<RezeptHasArtikel>();

		for (int i = 0; i < mp.getMenues().size(); i++) {
			if (mp.getMenues().get(i).col < 5) {
				for (int j = 0; j < mp.getMenues().get(i).getMenue().getRezepte().size(); j++) {

					for (int z = 0; z < mp.getMenues().get(i).getMenue().getRezepte().get(j).getArtikel().size(); z++) {
						rhamobisdo.add(mp.getMenues().get(i).getMenue().getRezepte().get(j).getArtikel().get(z));
					}

				}
			}
		}

		// TESTDATEN
		// for(long i = 133 ; i < 144 ; i++) {
		// RezeptHasArtikel rha2 = new RezeptHasArtikel();
		// rha2.setArtike(Artikelverwaltung.getInstance().getArtikelById(i));
		// rha2.setMenge(Double.valueOf("250"));
		// rhamobisdo.add(rha2);
		// }
		// RezeptHasArtikel rha3 = new RezeptHasArtikel();
		// rha3.setArtike(Artikelverwaltung.getInstance().getArtikelById(Long.valueOf("1")));
		// rha3.setMenge(Double.valueOf("250"));
		// rhamobisdo.add(rha3);

		// 8.1
		rha = mengeAddSameArtikel(rhamobisdo);
		// 9. Wenn Artikel vorhanden, dann vorhandenen ver�ndern(Menge
		// addieren), andersfalls
		// neue Bestellposition hinzuf�gen
		for (int i = 0; i < rha.size(); i++) {
			boolean vorhanden = false;
			for (int z = 0; z < list.size(); z++) {

				if (rha.get(i).getArtikel().equals(list.get(z).getArtikel())) {
					vorhanden = true;
					list.get(z).setMontag(list.get(z).getMontag() + convertMenge(rha.get(i)));
					list.get(z).setGesamt(list.get(z).getGesamt() + convertMenge(rha.get(i)));
					list.get(z).setKantine(list.get(z).getKantine() + convertMenge(rha.get(i)));

				}
			}
			if (vorhanden == false) {
				Bestellposition bp = new Bestellposition();
				bp.setArtikel(rha.get(i).getArtikel());
				// 9.1 Umrechen der Menge Menge/Bestellgr��e durch die Methode
				// convertBestellmenge
				bp.setDurchschnitt(rha.get(i).getArtikel().getDurchschnitt());
				// 9.2 Setzen der Menge auf Freitag und Durchschnitt
				// hinzuaddieren
				bp.setFreitag(leer);
				bp.setKantine(convertMenge(rha.get(i)));
				bp.setGeliefert(false);
				bp.setMontag(convertMenge(rha.get(i)) + bp.getDurchschnitt());
				bp.setGesamt(bp.getKantine() + bp.getDurchschnitt());

				list.add(bp);
			}
		}

		// 10. Artikel mit Grundbedarf auslesen
		List<Artikel> alist = Artikelverwaltung.getInstance().getArtikelByGrundbedarf();

		// 11. Artikel �berpr�fen ob schon vorhanden in List<Bestellposition>
		// list, wenn nein hinzuf�gen,
		// falls ja nichts machen, da ja der durchschnitt schon vorhanden ist
		for (int i = 0; i < alist.size(); i++) {
			boolean vorhanden = false;
			for (int z = 0; z < list.size(); z++) {
				if (alist.get(i).equals(list.get(z).getArtikel())) {
					vorhanden = true;
				}
			}
			if (vorhanden == false) {
				Bestellposition bp = new Bestellposition();
				bp.setArtikel(alist.get(i));
				bp.setDurchschnitt(alist.get(i).getDurchschnitt());
				bp.setFreitag(bp.getDurchschnitt());
				bp.setKantine(leer);
				bp.setGeliefert(false);
				bp.setMontag(leer);
				bp.setGesamt(bp.getDurchschnitt());
				list.add(bp);
			}

		}
		// 12. Bestellung erzeugen und in der Datenbank abspeichern
		List<Lieferant> lieferanten = getAllLieferantByListOfBestellposition(list);
		for (int i = 0; i < lieferanten.size(); i++) {

			Bestellverwaltung.getInstance().createBestellung(generateBestellungByListOfBestellpositionAndByLieferant(lieferanten.get(i), list));

		}

	}

	/**
	 * Die Methode berechnet die Menge der zu bestellenden Artikel und rundet
	 * diese auf. 
	 * 
	 * @author Christian Barth
	 * 
	 * @param rha
	 * @return
	 */
	private int convertMenge(RezeptHasArtikel rha) {
		int bm = 0;
		// Math.ceil wird immer auf ganze Zahl aufgerundet
		if (rha.getArtikel().getBestellgroesse() != 0 && rha.getArtikel().getBestellgroesse() != null) {
			bm = (int) Math.ceil(rha.getMenge() / rha.getArtikel().getBestellgroesse());
		}

		return bm;
	}

	/**
	 * Die Methode addiert die Mengen gleicher Artikel zusammen.
	 *  
	 * @author Christian Barth
	 * 
	 * @param rhalist
	 * @return
	 */
	private List<RezeptHasArtikel> mengeAddSameArtikel(List<RezeptHasArtikel> rhalist) {

		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();
		if (rhalist != null) {

			System.out.println("MENGEADDSAMEARTIKEL INPUTLISTE:");
			System.out.println("/n");
			System.out.println(rhalist);

			for (int i = 0; i < rhalist.size(); i++) {
				boolean vorhanden = false;
				if (i == 0) {
					list.add(rhalist.get(i));
					System.out.println("1 Element eintragen! Menge:");
					System.out.println(list.get(i).getMenge());
				} else {
					for (int z = 0; z < list.size(); z++) {
						if (list.get(z).getArtikel().equals(rhalist.get(i).getArtikel())) {
							System.out.println("Aktuelle Menge in vorhandener Liste");
							System.out.println(list.get(z).getMenge());
							list.get(z).setMenge(list.get(z).getMenge() + rhalist.get(i).getMenge());
							System.out.println("Menge in NEUER Liste");
							System.out.println(rhalist.get(i).getMenge());
							System.out.println("GESAMTMENGE in Liste");
							System.out.println(list.get(z).getMenge());
							vorhanden = true;
						}

					}
					if (vorhanden == false) {
						list.add(rhalist.get(i));
					}
				}
			}
		}

		return list;
	}

	/**
	 * Die Methode gibt alle Lieferanten zur�ck, die mit mindestens einem
	 * Artikel in der Bestellpositionliste vertreten sind. author Christian
	 * Barth
	 * 
	 * @param bplist
	 * @return
	 */
	private List<Lieferant> getAllLieferantByListOfBestellposition(List<Bestellposition> bplist) {
		List<Lieferant> list = new ArrayList<Lieferant>();
		list.add(bplist.get(0).getArtikel().getLieferant());
		for (int i = 1; i < bplist.size(); i++) {
			boolean vorhanden = false;
			for (int z = 0; z < list.size(); z++) {
				if (bplist.get(i).getArtikel().getLieferant().equals(list.get(z))) {
					vorhanden = true;
				}

			}
			if (vorhanden == false) {
				list.add(bplist.get(i).getArtikel().getLieferant());
			}
		}

		return list;
	}

	/**
	 * Die Methode generiert eine Bestellung f�r einen Lieferanten. author
	 * Christian Barth
	 * 
	 * @param lieferant
	 * @param bplist
	 * @return
	 */
	private Bestellung generateBestellungByListOfBestellpositionAndByLieferant(Lieferant lieferant, List<Bestellposition> bplist) {

		Bestellung b = new Bestellung();
		b.setBestellt(false);
		b.setLieferant(lieferant);
		java.util.Date date2 = new java.util.Date();
		Date date = new Date(date2.getTime());
		b.setDatum(date);
		// TODO richtiges Lieferdatum einsetzen.
		b.setLieferdatum(date);
		b.setLieferdatum2(date);

		List<Bestellposition> list = new ArrayList<Bestellposition>();

		for (int i = 0; i < bplist.size(); i++) {

			if (bplist.get(i).getArtikel().getLieferant().equals(lieferant)) {
				list.add(bplist.get(i));
			}
		}
		b.setBestellpositionen(list);
		return b;
	}

}

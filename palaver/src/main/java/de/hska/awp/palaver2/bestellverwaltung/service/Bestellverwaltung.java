/**
 * Created by Christian Barth
 * 06.05.2013 - 10:43:40
 */
package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
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
 * Die Klasse erm�glicht die Verwaltung der Bestellung und stellt f�r die GUI
 * Methoden bereit.
 * 
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
	public void createBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException, ParseException {
		super.createBestellung(bestellung);
	}

	/**
	 * Die Methode aktualisiert eine Bestellung mit seinen Bestellposition.
	 * 
	 * @auhtor Christian Barth
	 */
	public void updateBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException, ParseException {
		super.updateBestellung(bestellung);
	}

	/**
	 * Die Methode aktualisiert eine Bestellung ohne die Bestellpositionen zu
	 * ber�cksichtigen.
	 * 
	 * @author Christian Barth
	 */
	public void updateBestellungOhneBP(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException {
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
	public List<Bestellung> getAllBestellungen() throws ConnectException,
			DAOException, SQLException {

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
	public List<Bestellung> getAllBestellungenNotOrdered()
			throws ConnectException, DAOException, SQLException {

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
	public List<Bestellung> getBestellungenLTWeeks() throws ConnectException,
			DAOException, SQLException {

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
	public Bestellung getBestellungById(Long id) throws ConnectException,
			DAOException, SQLException {
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
	public List<Artikel> getAllArtikelByLieferant(Lieferant lieferant)
			throws ConnectException, DAOException, SQLException {
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
	public List<Lieferant> getLieferantenWithArtikel() throws ConnectException,
			DAOException, SQLException {
		List<Lieferant> list = null;
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
	public void generateAllBestellungenByMenueplanAndGrundbedarf(
			Menueplan menuplanAktuelleWoche,
			List<RezeptHasArtikel> kuchenplanAktuelleWoche, Week week,
			Menueplan menuplanvorWoche,
			List<RezeptHasArtikel> kuchenplanVorWoche) throws ConnectException,
			DAOException, SQLException, ParseException {
		List<Bestellposition> bestellPosition_list = new ArrayList<Bestellposition>();
		int leer = 0;

		System.out.println("-----------KUCHEN---------------");
		for (RezeptHasArtikel rrr : kuchenplanAktuelleWoche) {
			System.out.println("KUCHENplanAktuelleWoche "
					+ rrr.getArtikelname() + " " + rrr.getMenge() + " menge: "
					+ rrr.getMenge());
		}
		for (RezeptHasArtikel rrr : kuchenplanVorWoche) {
			System.out.println("KUCHENplanVorWoche " + rrr.getArtikelname()
					+ " " + rrr.getMenge());
		}
		System.out.println("----------------------------");
		System.out.println(" ");
		// 1. Mengen f�r den Freitag auslesen col = 5 (Freitag) von der Woche
		// davor
		System.out.println("----------MENU----------------");
		List<RezeptHasArtikel> rhaFreitagVorWocheMenu = new ArrayList<RezeptHasArtikel>();
		if (menuplanvorWoche != null) {
			for (int i = 0; i < menuplanvorWoche.getMenues().size(); i++) {
				if (menuplanvorWoche.getMenues().get(i).col == 5) {
					for (int j = 0; j < menuplanvorWoche.getMenues().get(i)
							.getMenue().getRezepte().size(); j++) {
						for (int z = 0; z < menuplanvorWoche.getMenues().get(i)
								.getMenue().getRezepte().get(j).getArtikel()
								.size(); z++) {
							rhaFreitagVorWocheMenu.add(menuplanvorWoche
									.getMenues().get(i).getMenue().getRezepte()
									.get(j).getArtikel().get(z));

							System.out
									.println("Mengen f�r den Freitag auslesen col = 5 (Freitag) von der Woche: "
											+ menuplanvorWoche.getMenues()
													.get(i).getMenue()
													.getRezepte().get(j)
													.getArtikel().get(z));
						}

					}
				}
			}
		}

		// 2. Mengen f�r Mo bis Do auslesen, col 1-4 (Mo - Do)
		List<RezeptHasArtikel> rhaAktuelleWocheMenuMonDon = new ArrayList<RezeptHasArtikel>();

		for (int i = 0; i < menuplanAktuelleWoche.getMenues().size(); i++) {
			if (menuplanAktuelleWoche.getMenues().get(i).col < 5) {
				for (int j = 0; j < menuplanAktuelleWoche.getMenues().get(i)
						.getMenue().getRezepte().size(); j++) {

					for (int z = 0; z < menuplanAktuelleWoche.getMenues()
							.get(i).getMenue().getRezepte().get(j).getArtikel()
							.size(); z++) {
						rhaAktuelleWocheMenuMonDon.add(menuplanAktuelleWoche
								.getMenues().get(i).getMenue().getRezepte()
								.get(j).getArtikel().get(z));

						System.out
								.println("Mengen f�r Mo bis Do auslesen, col 1-4 (Mo - Do): "
										+ menuplanAktuelleWoche.getMenues()
												.get(i).getMenue().getRezepte()
												.get(j).getArtikel().get(z));
					}

				}
			}
		}

		/*
		 * SELECT a.name, rha.menge FROM palaver.rezept r JOIN
		 * palaver.rezept_has_artikel rha on r.id = rha.rezept_fk JOIN
		 * palaver.artikel a on rha.artikel_fk = a.id Where r.name = 'Lasagne'
		 */

		// 3. Liste aufbereiten und doppelt Artikel zusammenf�gen und Menge
		// addieren um sp�ter Rundungsfehler zu minimieren
		List<RezeptHasArtikel> rhaDoppeltZusammen = null;
		if (kuchenplanVorWoche.isEmpty() == false) { // wenn KuchPlanVorWoche
														// nicht leer ist,
														// dann...
			for (int i = 0; i < kuchenplanVorWoche.size(); i++) {
				rhaFreitagVorWocheMenu.add(kuchenplanVorWoche.get(i)); // hinzuf�gen
																		// zu
																		// MenuVorWoche
			}
		}
		rhaDoppeltZusammen = mengeAddSameArtikel(rhaFreitagVorWocheMenu); // doppelte
																			// eintr�ge
																			// l�schen

		// 4. F�ge List<RezeptHasArtikel> von Menueplan (Freitag) in
		// List<Bestellposition>

		// TODO Doppelte pr�f�ng kann man weg lassen
		if (rhaDoppeltZusammen.isEmpty() == false) {
			for (int i = 0; i < rhaDoppeltZusammen.size(); i++) {
				Bestellposition bp = new Bestellposition();
				bp.setArtikel(rhaDoppeltZusammen.get(i).getArtikel());
				bp.setDurchschnitt(rhaDoppeltZusammen.get(i).getArtikel().getDurchschnitt());
				bp.setFreitag(convertMenge(rhaDoppeltZusammen.get(i))); // Ohne Durchschnitt!!!
				// + bp.getDurchschnitt());
				bp.setKantine(bp.getFreitag());
				bp.setGeliefert(false);
				bp.setMontag(leer);
				bp.setGesamt(bp.getKantine());
				bp.setSumme(rhaDoppeltZusammen.get(i).getMenge());
				//bp.setGesamt(bp.getKantine() + bp.getDurchschnitt());
				bestellPosition_list.add(bp);
			}
		}
		// 7. Liste leeren und neue Liste von Mo-Do einf�gen und vorher die
		// Mengen addieren
		rhaDoppeltZusammen = null;

		// Mengen aus dem Kuchenplan hinzuf�gen
		if (kuchenplanAktuelleWoche.isEmpty() == false) {
			for (int i = 0; i < kuchenplanAktuelleWoche.size(); i++) {
				rhaAktuelleWocheMenuMonDon.add(kuchenplanAktuelleWoche.get(i));
			}
		}

		rhaDoppeltZusammen = mengeAddSameArtikel(rhaAktuelleWocheMenuMonDon);
		// 8. Wenn Artikel vorhanden in List<Bestellposition> list, dann
		// vorhandenen ver�ndern(Menge
		// addieren), andersfalls neue Bestellposition hinzuf�gen
		if (rhaDoppeltZusammen.isEmpty() == false) {  // wenn nicht leer
			for (int i = 0; i < rhaDoppeltZusammen.size(); i++) { //aktuelle 
				boolean vorhanden = false;
				for (int z = 0; z < bestellPosition_list.size(); z++) {
					if (rhaDoppeltZusammen.get(i).getArtikel().equals(bestellPosition_list.get(z).getArtikel())) {
						vorhanden = true;
						bestellPosition_list.get(z).setMontag( bestellPosition_list.get(z).getMontag()
										+ convertMenge(rhaDoppeltZusammen.get(i)));
						bestellPosition_list.get(z).setKantine(bestellPosition_list.get(z).getKantine() + 
								bestellPosition_list.get(z).getMontag());
						bestellPosition_list.get(z).setGesamt( bestellPosition_list.get(z).getKantine());
						bestellPosition_list.get(z).setSumme(bestellPosition_list.get(z).getSumme() + 
								rhaDoppeltZusammen.get(i).getMenge());
						
//						bestellPosition_list.get(z).setKantine( bestellPosition_list.get(z).getKantine()
//								+ convertMenge(rhaDoppeltZusammen.get(i)));
//						bestellPosition_list.get(z).setGesamt( bestellPosition_list.get(z).getGesamt()
//										+ convertMenge(rhaDoppeltZusammen.get(i)));
					}
				}
				if (vorhanden == false) {
					Bestellposition bp = new Bestellposition();
					bp.setArtikel(rhaDoppeltZusammen.get(i).getArtikel());
					bp.setDurchschnitt(rhaDoppeltZusammen.get(i).getArtikel().getDurchschnitt());
					bp.setFreitag(leer);
					//bp.setKantine(convertMenge(rhaDoppeltZusammen.get(i)));
					bp.setGeliefert(false);
					bp.setMontag(convertMenge(rhaDoppeltZusammen.get(i)));
							//+ bp.getDurchschnitt());
					bp.setGesamt(bp.getKantine()); // + bp.getDurchschnitt()
					bp.setKantine(bp.getMontag());
					bestellPosition_list.add(bp);
					bp.setSumme(rhaDoppeltZusammen.get(i).getMenge());
				}
			}
		}
		
		
		
		// 10. Artikel mit Grundbedarf auslesen
		List<Artikel> listArtrtikelByGrundbedarf = Artikelverwaltung.getInstance().getArtikelByGrundbedarf();
		if (listArtrtikelByGrundbedarf.isEmpty() == false) {
			for (int i = 0; i < listArtrtikelByGrundbedarf.size(); i++) {
				boolean vorhanden = false;
				for (int z = 0; z < bestellPosition_list.size(); z++) {
					if (listArtrtikelByGrundbedarf.get(i).equals(bestellPosition_list.get(z).getArtikel())) {
						
						if(bestellPosition_list.get(z).getKantine() < listArtrtikelByGrundbedarf.get(i).getDurchschnitt()){
							bestellPosition_list.get(z).setDurchschnitt(
									listArtrtikelByGrundbedarf.get(i).getDurchschnitt() - 
									bestellPosition_list.get(z).getKantine()
									);	
							bestellPosition_list.get(z).setGesamt(bestellPosition_list.get(z).getKantine() + bestellPosition_list.get(z).getDurchschnitt());
							if(bestellPosition_list.get(z).getMontag() > bestellPosition_list.get(z).getFreitag())
								bestellPosition_list.get(z).setFreitag(bestellPosition_list.get(z).getDurchschnitt());
							else
								bestellPosition_list.get(z).setMontag(bestellPosition_list.get(z).getDurchschnitt());
						
						}
						else{
							bestellPosition_list.get(z).setDurchschnitt(leer);
							bestellPosition_list.get(z).setGesamt(bestellPosition_list.get(z).getKantine());
						}
						vorhanden = true;
					}
				}
				if (vorhanden == false) {
					Bestellposition bp = new Bestellposition();
					bp.setArtikel(listArtrtikelByGrundbedarf.get(i));
					bp.setDurchschnitt(listArtrtikelByGrundbedarf.get(i).getDurchschnitt());
					bp.setFreitag(bp.getDurchschnitt());
					bp.setKantine(leer);
					bp.setGeliefert(false);
					bp.setMontag(leer);
					bp.setGesamt(bp.getDurchschnitt());
					bp.setSumme(0);
					bestellPosition_list.add(bp);
				}
			}
		}
		// 12. Bestellung erzeugen und in der Datenbank abspeichern
		List<Lieferant> lieferanten = getAllLieferantByListOfBestellposition(bestellPosition_list);
		for (int i = 0; i < lieferanten.size(); i++) {

			Bestellverwaltung.getInstance().createBestellung(
					generateBestellungByListOfBestellpositionAndByLieferant(
							lieferanten.get(i), bestellPosition_list, week));

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
		if (rha.getArtikel().getBestellgroesse() != 0
				&& rha.getArtikel().getBestellgroesse() != null) {
			bm = (int) Math.ceil(rha.getMenge()
					/ rha.getArtikel().getBestellgroesse());
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
	private List<RezeptHasArtikel> mengeAddSameArtikel(
			List<RezeptHasArtikel> rhalist) {

		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();
		if (rhalist.isEmpty() == false) {
			for (int i = 0; i < rhalist.size(); i++) {
				boolean vorhanden = false;
				if (i == 0)
					list.add(rhalist.get(i));
				else {
					for (int z = 0; z < list.size(); z++) {
						if (list.get(z).getArtikel()
								.equals(rhalist.get(i).getArtikel())) {
							list.get(z).setMenge(
									list.get(z).getMenge()
											+ rhalist.get(i).getMenge());
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
	private List<Lieferant> getAllLieferantByListOfBestellposition(
			List<Bestellposition> bplist) {
		List<Lieferant> list = new ArrayList<Lieferant>();
		try {
			list.add(bplist.get(0).getArtikel().getLieferant());
			for (int i = 1; i < bplist.size(); i++) {
				boolean vorhanden = false;
				for (int z = 0; z < list.size(); z++) {
					if (bplist.get(i).getArtikel().getLieferant()
							.equals(list.get(z))) {
						vorhanden = true;
					}

				}
				if (vorhanden == false) {
					list.add(bplist.get(i).getArtikel().getLieferant());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	private Bestellung generateBestellungByListOfBestellpositionAndByLieferant(
			Lieferant lieferant, List<Bestellposition> bplist, Week week) {

		Bestellung b = new Bestellung();

		try {

			b.setBestellt(false);
			b.setLieferant(lieferant);
			java.util.Date date2 = new java.util.Date();
			Date date = new Date(date2.getTime());
			b.setDatum(date);

			java.util.Date datemontag;
			java.util.Date datefreitag;

			final GregorianCalendar calendar = new GregorianCalendar(
					Locale.GERMANY);
			calendar.clear();
			calendar.set(Calendar.YEAR, week.getYear());
			calendar.set(Calendar.WEEK_OF_YEAR, week.getWeek());

			datemontag = calendar.getTime();
			calendar.add(Calendar.DAY_OF_MONTH, 4);
			datefreitag = calendar.getTime();

			Date datemo = new Date(datemontag.getTime());
			Date datefr = new Date(datefreitag.getTime());

			b.setLieferdatum(datemo);
			b.setLieferdatum2(datefr);

			List<Bestellposition> list = new ArrayList<Bestellposition>();

			for (int i = 0; i < bplist.size(); i++) {

				if (bplist.get(i).getArtikel().getLieferant().equals(lieferant)) {
					list.add(bplist.get(i));
				}
			}
			b.setBestellpositionen(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

}

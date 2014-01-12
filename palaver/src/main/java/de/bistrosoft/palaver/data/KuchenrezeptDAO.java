package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasFussnote;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Fussnotekuchenverwaltung;
import de.hska.awp.palaver.dao.AbstractDAO;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.palaver.dao.artikelverwaltung.ArtikelDAO;
import de.palaver.dao.artikelverwaltung.MengeneinheitDAO;
import de.palaver.domain.artikelverwaltung.Artikel;
import de.palaver.domain.artikelverwaltung.Mengeneinheit;
import de.palaver.service.artikelverwaltung.ArtikelService;
import de.palaver.service.artikelverwaltung.MengeneinheitService;

/**
 * 
 * @author Christine Hartkorn, Jasmin Baumgartner
 * 
 */

public class KuchenrezeptDAO extends AbstractDAO {

	private final static String TABLE = "kuchenrezept";
	private final static String NAME = "name";
	private final static String KOMMENTAR = "kommentar";
	private final static String ERSTELLT = "erstellt";
	private final static String KUCHENREZEPTHASARTIKEL = "kuchenrezept_has_artikel";
	private final static String KUCHENREZEPTFK = "kuchenrezept_fk";
	private final static String AKTIV = "aktiv";

	private static KuchenrezeptDAO instance = null;

	// SQL-Statements
	private final static String GET_ALL_KUCHENREZEPTS = "SELECT * FROM kuchenrezept where aktiv = true";
	private final static String GET_KUCHENREZEPT_BY_ID = "SELECT * FROM kuchenrezept WHERE id = {0}";
	private final static String GET_KUCHENREZEPT_BY_NAME = "SELECT * FROM kuchenrezept WHERE kuchenrezept.name = {0}";
	private final static String GET_ARTIKEL_KUCHENREZEPT_BY_ID = "Select * From artikel Join kuchenrezept_has_artikel On artikel.id = kuchenrezept_has_artikel.artikel_fk Where kuchenrezept_has_artikel.kuchenrezept_fk = {0}";
	private final static String SAVE_ARTIKEL = "INSERT INTO kuchenrezept_has_artikel VALUES ({0},{1},{2},{3})";
	private final static String GET_KUCHENREZEPTHASARTIKEL_BY_KUCHENREZEPT_ID = "SELECT * FROM "
			+ KUCHENREZEPTHASARTIKEL + " WHERE " + KUCHENREZEPTFK + " = {0}";
	private static final String GET_ALL_KUCHENREZEPT_TABELLE = "SELECT * FROM "
			+ TABLE;
	private static final String GET_ALL_KUCHENREZEPT_TABELLE_AKTIV = "SELECT * FROM "
			+ TABLE + " WHERE " + AKTIV + "=true";

	Kuchenrezept kuchenrezept;

	// Konstruktor
	public KuchenrezeptDAO() {
		super();
	}

	// Instanz erzeugen
	public static KuchenrezeptDAO getInstance() {
		if (instance == null) {
			instance = new KuchenrezeptDAO();
		}
		return instance;
	}

	// Methode, die alle Kuchenrezepte in einer Liste zurückliefert
	public List<Kuchenrezept> getAllKuchenrezepte(Boolean ladeArtikel)
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPTS);
		while (set.next()) {
			Kuchenrezept kr = new Kuchenrezept(set.getLong("id"),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"), set.getBoolean("aktiv"));
			if (ladeArtikel) {
				kr.setArtikel(getAllArtikelByKuchenrezeptId1(kr));
			}
			List<FussnoteKuchen> fussnoten = Fussnotekuchenverwaltung
					.getInstance().getFussnoteKuchenByKuchen(kr.getId());
			String fn = "";
			for (FussnoteKuchen f : fussnoten) {
				fn = fn + " (" + f.getAbkuerzung().toString() + ")";
			}
			kr.setFussnoten(fn);
			list.add(kr);
		}
		return list;
	}

	// Methode, die alle Kuchenrezepte in einer Liste für Tabelle zurückliefert
	public List<Kuchenrezept> getAllKuchenrezepteTabelle()
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPT_TABELLE);

		while (set.next()) {
			Kuchenrezept kuchenrezept = new Kuchenrezept(set.getLong("id"),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));

			list.add(kuchenrezept);
		}
		return list;
	}

	// Methode, die alle aktiven Kuchenrezepte in einer Liste zurückliefert
	public List<Kuchenrezept> getAllKuchenrezepteTabelleAktiv()
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPT_TABELLE_AKTIV);

		while (set.next()) {
			Kuchenrezept kuchenrezept = new Kuchenrezept(set.getLong("id"),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"), set.getBoolean("aktiv"));
			list.add(kuchenrezept);

		}
		return list;
	}

	// Methode, die ID und Name aller Kuchenrezepte in einer Liste zurückliefert
	public List<Kuchenrezept> getAllKuchenrezepteM() throws ConnectException,
			DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPTS);
		;
		while (set.next()) {
			list.add(new Kuchenrezept(set.getLong("id"), set.getString("name"),
					null

			));
		}
		return list;
	}

	// Methode, die Kuchenrezept anhand einer ID mit Artikeln zurückliefert
	public Kuchenrezept getKuchenrezeptById(Long id, Boolean ladeArtikel)
			throws ConnectException, DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID,
				id));
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		while (set.next()) {
			Kuchenrezept kr = new Kuchenrezept(set.getLong("id"),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));
			if (ladeArtikel) {
				kr.setArtikel(getAllArtikelByKuchenrezeptId1(kr));
			}
			kr.setFussnoteKuchen(FussnoteKuchenDAO.getInstance()
					.getFussnoteKuchenByKuchen(id));
			list.add(kr);
		}
		return list.get(0);
	}

	// Methode, die ID eines Kuchenrezeptes zurückliefert
	public Kuchenrezept getKuchenrezept1(Long id) throws ConnectException,
			DAOException, SQLException {
		Kuchenrezept result = null;
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID,
				id));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"));
		}
		return result;
	}

	// Methode, die ID und Name eines Kuchenrezepts anhand eines Namens
	// zurückliefert
	public Kuchenrezept getKuchenrezeptByName(String namekuchenrezept)
			throws ConnectException, DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPT_BY_NAME, NAME));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"), set.getString("name"),
					null);
		}
		return result;
	}

	// Methode, die ID eines Kuchenrezepts anhand eines Namens zurückliefert
	public Kuchenrezept getKuchenrezeptByName1(String name)
			throws ConnectException, DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPT_BY_NAME, "name"));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"));
		}

		return result;
	}

	// Methode, die alle Artikel eines Kuchenrezepts zurückliefert
	public List<Kuchenrezept> getAllArtikelByKuchenrezeptId(int kuchenrezeptID)
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> kuchenrezept = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ARTIKEL_KUCHENREZEPT_BY_ID);
		while (set.next()) {
			kuchenrezept.add(new Kuchenrezept(set.getString("name"), null));
		}
		return kuchenrezept;
	}

	// Methode, die alle Artikel eines Kuchenrezepts anhand einer ID
	// zurückliefert
	public List<KuchenrezeptHasArtikel> getAllArtikelByKuchenrezeptId1(
			Kuchenrezept kr) throws ConnectException, DAOException,
			SQLException {
		List<KuchenrezeptHasArtikel> rha = new ArrayList<KuchenrezeptHasArtikel>();
		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPTHASARTIKEL_BY_KUCHENREZEPT_ID, kr.getId()));
		while (set.next()) {
			rha.add(new KuchenrezeptHasArtikel(kr, ArtikelDAO.getInstance()
					.getArtikelById(set.getLong("artikel_fk")),
					MengeneinheitDAO.getInstance().getMengeneinheitById(
							set.getLong("einheit")), set.getDouble("menge")));
		}
		return rha;
	}

	// Methode, die ein neues Kuchenrezept speichert
	public void createKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ KOMMENTAR + "," + ERSTELLT + "," + AKTIV + ")" + " VALUES"
				+ "('" + kuchenrezept.getName() + "','"
				+ kuchenrezept.getKommentar() + "','"
				+ kuchenrezept.getErstellt() + "', true)";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die Artikel zu einem Kuchenrezept speichert
	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		List<KuchenrezeptHasArtikel> rha = kuchenrezept.getArtikel();
		for (KuchenrezeptHasArtikel a : rha) {
			String rez = kuchenrezept.getId().toString();
			String artikel_fk = a.getArtikelId().toString();
			String menge = Double.toString(a.getMenge());
			String me = "1";
			putManaged(MessageFormat.format(SAVE_ARTIKEL, rez, artikel_fk,
					menge, me));
		}
	}

	// Methode, die Fußnoten zu einem Kuchenrezept speichert
	public void FussnoteKuchenAdd(KuchenrezeptHasFussnote kuchenHasFussnote)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO kuchenrezept_has_fussnote (kuchenrezept_fk, fussnotekuchen_fk) VALUES"
				+ "("
				+ kuchenHasFussnote.getKuchen().getId()
				+ ", "
				+ kuchenHasFussnote.getFussnoteKuchen().getId() + ")";
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die Fußnoten zu einem Kuchenrezept löscht
	public void FussnoteKuchenDelete(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from kuchenrezept_has_fussnote WHERE kuchenrezept_fk = "
				+ kuchenrezept.getId() + ";";

		this.putManaged(DELETE_QUERY);
	}

	// Methode, die Kuchenrezept updatet
	public void updateKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "UPDATE kuchenrezept SET name = '"
				+ kuchenrezept.getName() + "'," + "kommentar='"
				+ kuchenrezept.getKommentar() + "'," + "erstellt='"
				+ kuchenrezept.getErstellt() + "' WHERE id = "
				+ kuchenrezept.getId();
		this.putManaged(INSERT_QUERY);
	}

	// Methode, die Artikel zu einem Kuchenrezept lädt
	public List<KuchenrezeptHasArtikel> ladeArtikelFuerKuchenrezept(
			Kuchenrezept rez) throws ConnectException, DAOException,
			SQLException {
		List<KuchenrezeptHasArtikel> rha = new ArrayList<KuchenrezeptHasArtikel>();

		ResultSet set = getManaged("select * from kuchenrezept_has_artikel where kuchenrezept_fk="
				+ rez.getId());

		while (set.next()) {
			KuchenrezeptHasArtikel a = new KuchenrezeptHasArtikel();
			a.setKuchenrezept(rez);
			a.setMenge(set.getDouble("menge"));
			Artikel artikel = ArtikelService.getInstance().getArtikelById(
					set.getLong("artikel_fk"));
			a.setArtike(artikel);
			Mengeneinheit me = MengeneinheitService.getInstance()
					.getMengeneinheitById(set.getLong("einheit"));
			a.setMengeneinheit(me);
			rha.add(a);
		}
		return rha;
	}

	// Methode, die Artikel zu einem Kuchenrezept löscht
	public void deleteZutatenZuKuchenrezept(Kuchenrezept rez2)
			throws ConnectException, DAOException, SQLException {
		String DELETE_QUERY = "DELETE FROM kuchenrezept_has_artikel WHERE "
				+ KUCHENREZEPTFK + "=" + rez2.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}

	// Methode, die Kuchenrezept deaktiviert
	public void setKuchenrezeptDisabled(Kuchenrezept kuchenrezeptAusTb)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + AKTIV
				+ "=false WHERE id=" + kuchenrezeptAusTb.getId();
		this.putManaged(UPDATE_QUERY);
	}
}

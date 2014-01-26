package de.palaver.service.person.lieferantenverwaltung;

import java.sql.SQLException;
import java.util.List;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.dao.person.lieferantenverwaltung.AnsprechpartnerDAO;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;

public class AnsprechpartnerService extends AnsprechpartnerDAO {

	private static AnsprechpartnerService instance = null;

	private AnsprechpartnerService() {
		super();
	}

	public static AnsprechpartnerService getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerService();
		}
		return instance;
	}

	public Long createAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException {
		return AnsprechpartnerDAO.getInstance().createAnsprechpartner(ansprechpartner);
	}

	public List<Ansprechpartner> getAllAnsprechpartnersByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		return AnsprechpartnerDAO.getInstance().getAllAnsprechpartnersByLieferantId(id);
	}
	
	
}

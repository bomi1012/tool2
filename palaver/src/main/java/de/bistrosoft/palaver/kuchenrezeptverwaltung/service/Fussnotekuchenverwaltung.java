package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

/**
 * 
 * @author Jasmin Baumgartner
 * 
 */

import java.sql.SQLException;
import java.util.List;
import de.bistrosoft.palaver.data.FussnoteKuchenDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public class Fussnotekuchenverwaltung extends FussnoteKuchenDAO {

	private static Fussnotekuchenverwaltung instance = null;

	private Fussnotekuchenverwaltung() {
		super();
	}

	public static Fussnotekuchenverwaltung getInstance() {
		if (instance == null) {
			instance = new Fussnotekuchenverwaltung();
		}
		return instance;
	}

	public List<FussnoteKuchen> getAllFussnoteKuchen() throws ConnectException,
			DAOException, SQLException {
		List<FussnoteKuchen> result = null;
		result = super.getAllFussnoteKuchen();
		return result;
	}

	public FussnoteKuchen getFussnoteKuchenById(Long id)
			throws ConnectException, DAOException, SQLException {
		FussnoteKuchen result = null;
		result = super.getFussnoteKuchenById(id);
		return result;
	}

	public FussnoteKuchen getFussnoteKuchenByName(String name)
			throws ConnectException, DAOException, SQLException {
		FussnoteKuchen result = null;
		result = super.getFussnoteKuchenByName(name);
		return result;
	}

	public void createFussnoteKuchen(FussnoteKuchen fussnotekuchen)
			throws ConnectException, DAOException, SQLException {
		super.createFussnoteKuchen(fussnotekuchen);
	}

	public void updateFussnoteKuchen(FussnoteKuchen fussnotekuchen)
			throws ConnectException, DAOException, SQLException {
		super.updateFussnoteKuchen(fussnotekuchen);
	}
}

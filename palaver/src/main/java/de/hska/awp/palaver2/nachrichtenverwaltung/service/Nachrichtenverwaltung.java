package de.hska.awp.palaver2.nachrichtenverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.NachrichtDAO;
import de.palaver.management.emploee.InternMessage;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * @author PhilippT
 * 
 */
public class Nachrichtenverwaltung extends NachrichtDAO {

	private static Nachrichtenverwaltung instance = null;

	private Nachrichtenverwaltung() {
		super();
	}

	public static Nachrichtenverwaltung getInstance() {
		if (instance == null) {
			instance = new Nachrichtenverwaltung();
		}
		return instance;
	}

	public InternMessage getNachrichtById(Long id) throws ConnectException, DAOException, SQLException {

		final InternMessage internMessage = super.getNachrichtById(id);

		if (internMessage == null) {
			return null;
		}

		return internMessage;
	}

	public List<InternMessage> getNachrichtByRolle(Rolle rolle) throws ConnectException, DAOException, SQLException {
		if (rolle == null) {
			return null;
		}

		List<InternMessage> nachrichten = null;
		nachrichten = super.getNachrichtByRolle(rolle);

		return nachrichten;

	}

	public List<InternMessage> getNachrichtByRolleId(Long rid) throws ConnectException, DAOException, SQLException {
		if (rid == null) {
			return null;
		}

		List<InternMessage> nachrichten = null;
		nachrichten = super.getNachrichtByRolleId(rid);

		return nachrichten;

	}

	public List<InternMessage> getAllNachricht() throws ConnectException, DAOException, SQLException {

		final List<InternMessage> nachrichten = super.getAllNachricht();

		if (nachrichten == null) {
			return null;
		}

		return nachrichten;
	}

	public void createNachricht(InternMessage internMessage) throws ConnectException, DAOException, SQLException {

		if (internMessage == null) {
			return;
		}
		super.createNachricht(internMessage);
	}

	public void deleteNachricht(InternMessage internMessage) throws ConnectException, DAOException, SQLException {

		if (internMessage == null) {
			return;
		}
		internMessage = getNachrichtById(internMessage.getId());
		if (internMessage == null) {
			return;
		}
		super.deleteNachricht(internMessage.getId());

	}

}

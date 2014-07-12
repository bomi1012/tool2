package de.palaver.management.emploee;

import java.io.Serializable;

import de.palaver.domain.EntityId;

public class InternMessage extends EntityId implements Serializable {

	private static final long serialVersionUID = 1291141882464373163L;

	private Employee m_mitarbeiterSender;
	public Employee getMitarbeiterSender() { return m_mitarbeiterSender; }
	public void setMitarbeiterSender(Employee mitarbeiterBySenderFk) { m_mitarbeiterSender = mitarbeiterBySenderFk; }
	
	private Rolle m_empfaengerRolle;
	public Rolle getEmpfaengerRolle() { return m_empfaengerRolle; }
	public void setEmpfaengerRolle(Rolle empfaengerRolle) { m_empfaengerRolle = empfaengerRolle; }
	
	private String m_nachricht;
	public String getNachricht() { return m_nachricht; }
	public void setNachricht(String nachricht) { m_nachricht = nachricht; }

	public InternMessage() {
		super();
	}

	public InternMessage(Long id, String nachricht, Employee mitarbeiterSender, Rolle empfaengerRolle) {
		m_id = id;
		m_nachricht = nachricht;
		m_mitarbeiterSender = mitarbeiterSender;
		m_empfaengerRolle = empfaengerRolle;

	}

	@Override
	public String toString() {
		return "Nachricht [id=" + m_id + ", nachricht=" + m_nachricht + "]";
	}

}

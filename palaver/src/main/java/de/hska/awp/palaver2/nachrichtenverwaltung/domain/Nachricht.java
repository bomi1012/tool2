package de.hska.awp.palaver2.nachrichtenverwaltung.domain;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * Nachricht generated by PhilippT
 */

public class Nachricht implements java.io.Serializable {

	private static final long serialVersionUID = 1291141882464373163L;

	private Long id;
	private Mitarbeiter mitarbeiterBySenderFk;
	private Rollen empfaengerRolle;
	private String nachricht;

	public Nachricht() {
	}

	public Nachricht(Mitarbeiter mitarbeiterBySenderFk, Rollen empfaengerRolle) {
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
		this.empfaengerRolle = empfaengerRolle;
	}

	public Nachricht(Long id, String nachricht, Mitarbeiter mitarbeiterBySenderFk, Rollen empfaengerRolle) {
		this.id = id;
		this.nachricht = nachricht;
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
		this.empfaengerRolle = empfaengerRolle;

	}

	public Nachricht(Long id, String nachricht, Mitarbeiter mitarbeiterBySenderFk) {
		this.id = id;
		this.nachricht = nachricht;
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mitarbeiter getMitarbeiterBySenderFk() {
		return this.mitarbeiterBySenderFk;
	}

	public void setMitarbeiterBySenderFk(Mitarbeiter mitarbeiterBySenderFk) {
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
	}

	public Rollen getEmpfaengerRolle() {
		return this.empfaengerRolle;
	}

	public void setEmpfaengerRolle(Rollen empfaengerRolle) {
		this.empfaengerRolle = empfaengerRolle;
	}

	public String getNachricht() {
		return this.nachricht;
	}

	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	@Override
	public String toString() {
		return "Nachricht [id=" + id + ", nachricht=" + nachricht + "]";
	}

}

package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

import java.sql.Date;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
//
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

public class Kuchenrezept implements java.io.Serializable {

	private static final long serialVersionUID = 7984117576450240771L;

	private Long id;
	private Mitarbeiter mitarbeiter;
	private String name;
	private String kommentar;
	private Date erstellt;
	private List<KuchenrezeptHasArtikel> artikel;
	List<FussnoteKuchen> fussnotekuchen;
	public List<KuchenrezeptHasArtikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<KuchenrezeptHasArtikel> artikel) {
		this.artikel = artikel;
	}

	public Kuchenrezept() {
		super();
	}

	public Kuchenrezept(Mitarbeiter mitarbeiter, String name, String kommentar) {
		super();
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Kuchenrezept(Long id, Mitarbeiter mitarbeiter, String name,
			String kommentar) {
		super();
		this.id = id;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Kuchenrezept(Long id, Mitarbeiter mitarbeiter, String name,
			String kommentar, Date erstellt) {
		super();
		this.id = id;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
	}

	public Kuchenrezept(Long id) {
		super();
		this.id = id;

	}
	public List<FussnoteKuchen> getFussnoteKuchen() {
		return fussnotekuchen;
	}

	public void setFussnoteKuchen(List<FussnoteKuchen> fussnotekuchen) {
		this.fussnotekuchen = fussnotekuchen;
	}
	// public Rezept(Rezeptart rezeptart2, Geschmack geschmack2, Mitarbeiter
	// mitarbeiter2, String string) {
	// }
	//
	// public Rezept(Rezeptart rezeptart, Geschmack geschmack, String name,
	// Mitarbeiter mitarbeiter) {
	// this.rezeptart = rezeptart;
	// this.geschmack = geschmack;
	// this.name = name;
	// this.mitarbeiter = mitarbeiter;
	// }
	//
	// public Rezept(Geschmack geschmack, Rezeptart rezeptart,
	// Mitarbeiter mitarbeiter, String name, String kommentar,
	// int portion, Set<Menue> menues,
	// Set<RezeptHasFussnote> rezeptHasFussnotes,
	// Set<RezeptHasArtikel> rezeptHasArtikels,
	// RezeptHasZubereitung rezeptHasZubereitung) {
	// this.geschmack = geschmack;
	// this.rezeptart = rezeptart;
	// this.mitarbeiter = mitarbeiter;
	// this.name = name;
	// this.kommentar = kommentar;
	// this.portion = portion;
	// // this.menues = menues;
	// this.rezeptHasFussnotes = rezeptHasFussnotes;
	// this.rezeptHasArtikels = rezeptHasArtikels;
	// this.rezeptHasZubereitung = rezeptHasZubereitung;
	// }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mitarbeiter getMitarbeiter() {
		return this.mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	// @PrePersist
	// private void prePersist() {
	// erstellt = new Date();
	// }
	//
	// public Date getErzeugt() {
	// return erstellt == null ? null : (Date) erstellt.clone();
	// }
	//
	// public void setErzeugt() {
	// this.erstellt = erstellt == null ? null : (Date) erstellt.clone();
	// }

	// @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "menue_has_rezept", catalog = "palaver", joinColumns =
	// { @JoinColumn(name = "rezept_id", nullable = false, updatable = false) },
	// inverseJoinColumns = { @JoinColumn(name = "menue_id", nullable = false,
	// updatable = false) })
	// public Set<Menue> getMenues() {
	// return this.menues;
	// }
	//
	// public void setMenues(Set<Menue> menues) {
	// this.menues = menues;
	// }

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public Set<RezeptHasFussnote> getRezeptHasFussnotes() {
	// return this.rezeptHasFussnotes;
	// }
	//
	// public void setRezeptHasFussnotes(Set<RezeptHasFussnote>
	// rezeptHasFussnotes) {
	// this.rezeptHasFussnotes = rezeptHasFussnotes;
	// }
	//
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public Set<RezeptHasArtikel> getRezeptHasArtikels() {
	// return this.rezeptHasArtikels;
	// }
	//
	// public void setRezeptHasArtikels(Set<RezeptHasArtikel> rezeptHasArtikels)
	// {
	// this.rezeptHasArtikels = rezeptHasArtikels;
	// }
	//
	// @OneToOne(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public RezeptHasZubereitung getRezeptHasZubereitung() {
	// return this.rezeptHasZubereitung;
	// }
	//
	// public void setRezeptHasZubereitung(
	// RezeptHasZubereitung rezeptHasZubereitung) {
	// this.rezeptHasZubereitung = rezeptHasZubereitung;
	// }

	public Date getErstellt() {
		return erstellt;
	}

	public void setErstellt(Date erstellt) {
		this.erstellt = erstellt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((erstellt == null) ? 0 : erstellt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kommentar == null) ? 0 : kommentar.hashCode());
		result = prime * result
				+ ((mitarbeiter == null) ? 0 : mitarbeiter.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kuchenrezept other = (Kuchenrezept) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (erstellt == null) {
			if (other.erstellt != null)
				return false;
		} else if (!erstellt.equals(other.erstellt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kommentar == null) {
			if (other.kommentar != null)
				return false;
		} else if (!kommentar.equals(other.kommentar))
			return false;
		if (mitarbeiter == null) {
			if (other.mitarbeiter != null)
				return false;
		} else if (!mitarbeiter.equals(other.mitarbeiter))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "" + id + " " + mitarbeiter;
	}

}

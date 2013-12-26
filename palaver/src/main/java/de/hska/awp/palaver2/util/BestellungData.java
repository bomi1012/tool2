/**
 * Created by Sebastian Walz
 * 06.05.2013 12:11:49
 */
package de.hska.awp.palaver2.util;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;

public class BestellungData implements java.io.Serializable {
	
	private static final long serialVersionUID = 7961573736724011030L;
	
	private String name;
	private String gebinde;
	private Kategorie kategorie;
	private TextField durchschnitt = new TextField();
	private TextField kantine = new TextField();;
	private TextField gesamt = new TextField();;
	private IntStepper freitag = new IntStepper();
	private IntStepper montag = new IntStepper();
	private String notiz;
	private Artikel artikel;
	private Long id;
	private double summe;
	private boolean geliefert;

	/**
	 * @param name
	 * @param artnr
	 * @param menge
	 */
	public BestellungData(String name, String gebinde, Kategorie kategorie, Integer durchschnitt, Integer kantine, String notiz, double summe) {
		super();
		this.name = name;
		this.gebinde = gebinde;
		this.kategorie = kategorie;
		this.durchschnitt.setValue(durchschnitt.toString());
		this.kantine.setValue(kantine.toString());
		this.gesamt.setValue("" + (durchschnitt + kantine));
		this.gesamt.setEnabled(false);
		this.freitag.setStyleName("stepper-palaver");
		this.freitag.setValue(durchschnitt + kantine);
		this.montag.setValue(0);
		this.setNotiz(notiz);
		this.summe = summe;
	}

	@SuppressWarnings("serial")
	public BestellungData(Bestellposition bp) {
		super();
		this.id = bp.getId();
		this.artikel = bp.getArtikel();
		this.geliefert = bp.isGeliefert();
		this.name = bp.getArtikelName();
		this.gebinde = bp.getArtikel().getBestellgroesse() + " " + bp.getArtikel().getMengeneinheit().getKurz();
		this.kategorie = bp.getArtikel().getKategorie();
		this.durchschnitt.setValue(bp.getDurchschnitt().toString());
		this.kantine.setValue(bp.getKantine().toString());
		this.gesamt.setValue(bp.getGesamt().toString());
		this.gesamt.setEnabled(false);
		this.freitag.setStyleName("stepper-palaver");
		this.freitag.setValue(bp.getFreitag());
		this.montag.setValue(bp.getMontag());
		this.setNotiz(this.artikel.getNotiz() + " Summe: " + bp.getSumme());
		this.summe = bp.getSumme();

		this.durchschnitt.setWidth("50px");
		this.kantine.setWidth("50px");
		this.gesamt.setWidth("50px");
		this.freitag.setWidth("50px");
		this.montag.setWidth("50px");

		this.durchschnitt.setImmediate(true);
		this.kantine.setImmediate(true);

		this.freitag.setMinValue(0);
		this.montag.setMinValue(0);
		this.freitag.setManualInputAllowed(false);
		this.montag.setManualInputAllowed(false);

		this.gesamt.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				montag.setMaxValue(getInt(gesamt.getValue()));
				freitag.setMaxValue(getInt(gesamt.getValue()));
			}
		});

		this.durchschnitt.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				Integer gesammt = Integer.parseInt(durchschnitt.getValue()) + Integer.parseInt(kantine.getValue());
				gesamt.setValue(String.valueOf(gesammt));

				montag.setValue(Integer.parseInt(gesamt.getValue()) - freitag.getValue());

			}
		});

		this.kantine.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Integer gesammt = Integer.parseInt(durchschnitt.getValue()) + Integer.parseInt(kantine.getValue());
				gesamt.setValue(String.valueOf(gesammt));

				montag.setValue(Integer.parseInt(gesamt.getValue()) - freitag.getValue());
			}
		});

		this.freitag.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (freitag.getValue() >= getInt(gesamt.getValue())) {
					freitag.setValue(getInt(gesamt.getValue()));
					montag.setValue(0);
				}
				if (freitag.getValue() < getInt(gesamt.getValue())) {
					montag.setValue(getInt(gesamt.getValue()) - freitag.getValue());
				}
			}
		});

		this.montag.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				if (montag.getValue() < 0) {
					for (int i = montag.getValue(); i < 0; i++) {
						montag.setValue(montag.getValue() + 1);
					}
				}
				if (montag.getValue() < getInt(gesamt.getValue())) {
					freitag.setValue(getInt(gesamt.getValue()) - montag.getValue());
				}

			}
		});
	}

	@SuppressWarnings("serial")
	public BestellungData(Artikel artikel) {
		super();
		this.name = artikel.getName();
		this.gebinde = artikel.getBestellgroesse() + " " + artikel.getMengeneinheit().getKurz();
		this.kategorie = artikel.getKategorie();
		this.durchschnitt.setValue(artikel.getDurchschnittLT1() + "");
		this.kantine.setValue(0 + "");
		this.gesamt.setValue("" + artikel.getDurchschnittLT1());
		this.gesamt.setEnabled(false);
		this.freitag.setValue(artikel.getDurchschnittLT1());
		this.montag.setValue(0);
		this.artikel = artikel;

		this.durchschnitt.setWidth("50px");
		this.kantine.setWidth("50px");
		this.gesamt.setWidth("50px");
		this.freitag.setWidth("50px");
		this.montag.setWidth("50px");

		this.durchschnitt.setImmediate(true);
		this.kantine.setImmediate(true);

		this.freitag.setMinValue(0);
		this.montag.setMinValue(0);
		this.freitag.setManualInputAllowed(false);
		this.montag.setManualInputAllowed(false);

		this.gesamt.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				montag.setMaxValue(getInt(gesamt.getValue()));
				freitag.setMaxValue(getInt(gesamt.getValue()));
			}
		});

		this.durchschnitt.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					Integer gesammt = Integer.parseInt(durchschnitt.getValue()) + Integer.parseInt(kantine.getValue());
					gesamt.setValue(String.valueOf(gesammt));
				}
				catch (Exception e){
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_INPUT_WERT);
				}
				montag.setValue(Integer.parseInt(gesamt.getValue()) - freitag.getValue());
			}
		});

		this.kantine.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					Integer gesammt = Integer.parseInt(durchschnitt.getValue()) + Integer.parseInt(kantine.getValue());
					gesamt.setValue(String.valueOf(gesammt));
				}
				catch (Exception e){
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_INPUT_WERT);
				}
				montag.setValue(Integer.parseInt(gesamt.getValue()) - freitag.getValue());
			}
		});

		this.freitag.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (freitag.getValue() >= getInt(gesamt.getValue())) {
					freitag.setValue(getInt(gesamt.getValue()));
					montag.setValue(0);
				} else {
					montag.setValue(getInt(gesamt.getValue()) - freitag.getValue());
				}
			}
		});

		this.montag.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (montag.getValue() >= getInt(gesamt.getValue())) {
					montag.setValue(getInt(gesamt.getValue()));
					freitag.setValue(0);
				}
				if (montag.getValue() < 0) {
					for (int i = montag.getValue(); i < 0; i++) {
						montag.setValue(montag.getValue() + 1);
					}
				}
				if (montag.getValue() < getInt(gesamt.getValue())) {
					freitag.setValue(getInt(gesamt.getValue()) - montag.getValue());
				}
			}
		});
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the artnr
	 */
	public String getGebinde() {
		return gebinde;
	}

	/**
	 * @param artnr
	 *            the artnr to set
	 */
	public void setGebinde(String artnr) {
		this.gebinde = artnr;
	}

	/**
	 * @return the menge
	 */
	public IntStepper getFreitag() {
		return freitag;
	}

	/**
	 * @param menge
	 *            the menge to set
	 */
	public void setFreitag(IntStepper menge) {
		this.freitag = menge;
	}

	/**
	 * @return the kategorie
	 */
	public Kategorie getKategorie() {
		return kategorie;
	}

	/**
	 * @param kategorie
	 *            the kategorie to set
	 */
	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * @return the durchschnitt
	 */
	public TextField getDurchschnitt() {
		return durchschnitt;
	}

	/**
	 * @param durchschnitt
	 *            the durchschnitt to set
	 */
	public void setDurchschnitt(TextField durchschnitt) {
		durchschnitt.setConverter(Integer.class);
		this.durchschnitt = durchschnitt;
	}

	/**
	 * @return the kantine
	 */
	public TextField getKantine() {
		return kantine;
	}

	/**
	 * @param kantine
	 *            the kantine to set
	 */
	public void setKantine(TextField kantine) {
		this.kantine = kantine;
	}

	/**
	 * @return the gesamt
	 */
	public TextField getGesamt() {
		return gesamt;
	}

	/**
	 * @param gesamt
	 *            the gesamt to set
	 */
	public void setGesamt(TextField gesamt) {
		this.gesamt = gesamt;
	}

	/**
	 * @return the montag
	 */
	public IntStepper getMontag() {
		return montag;
	}

	/**
	 * @param montag
	 *            the montag to set
	 */
	public void setMontag(IntStepper montag) {
		this.montag = montag;
	}

	public Artikel getBestellungArtikel() {
		return this.artikel;
	}

	public static Integer getInt(String str) {
		Integer nummer = null;
		try {
			nummer = Integer.parseInt(str);
		} catch (NumberFormatException e) {

		}
		return nummer;
	}

	/**
	 * @return the geliefert
	 */
	public boolean isGeliefert() {
		return geliefert;
	}

	/**
	 * @param geliefert
	 *            the geliefert to set
	 */
	public void setGeliefert(boolean geliefert) {
		this.geliefert = geliefert;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	/**
	 * @return the summe
	 */
	public double getSumme() {
		return summe;
	}

	/**
	 * @param summe the summe to set
	 */
	public void setSumme(double summe) {
		this.summe = summe;
	}
	
	
}

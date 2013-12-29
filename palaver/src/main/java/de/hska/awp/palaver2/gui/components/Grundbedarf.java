package de.hska.awp.palaver2.gui.components;

import java.io.Serializable;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;

public class Grundbedarf implements Serializable{
	private static final long serialVersionUID = 3583052471410219992L;
	private long m_artikelId;
	private Label m_lieferantName = new Label();
	private Label m_artikelName = new Label();
	private IntStepper m_liefertermin1 = new IntStepper();
	private IntStepper m_liefertermin2 = new IntStepper();
	private Label m_mengeneinheit = new Label();
	private Label m_gebinde = new Label();
	private Label m_summe1 = new Label();
	private Label m_summe2 = new Label();
	private CheckBox m_remove = new CheckBox();

	public long getArtikelId() {
		return m_artikelId;
	}
	public void setArtikelId(long artikelId) {
		m_artikelId = artikelId;
	}
	
	public Label getLieferantName() {
		return m_lieferantName;
	}
	public void setLieferantName(Label lieferantName) {
		m_lieferantName = lieferantName;
	}
	public Label getArtikelName() {
		return m_artikelName;
	}
	public void setArtikelName(Label artikelName) {
		m_artikelName = artikelName;
	}
	public IntStepper getLiefertermin1() {
		return m_liefertermin1;
	}
	public void setLiefertermin1(IntStepper liefertermin1) {
		m_liefertermin1 = liefertermin1;
	}
	public IntStepper getLiefertermin2() {
		return m_liefertermin2;
	}
	public void setLiefertermin2(IntStepper liefertermin2) {
		m_liefertermin2 = liefertermin2;
	}
	public Label getMengeneinheit() {
		return m_mengeneinheit;
	}
	public void setMengeneinheit(Label mengeneinheit) {
		m_mengeneinheit = mengeneinheit;
	}
	public Label getGebinde() {
		return m_gebinde;
	}
	public void setGebinde(Label gebinde) {
		m_gebinde = gebinde;
	}
	public Label getSumme1() {
		return m_summe1;
	}
	public void setSumme1(Label summe) {
		m_summe1 = summe;
	}
	
	public Label getSumme2() {
		return m_summe2;
	}
	public void setSumme2(Label summe) {
		m_summe2 = summe;
	}
	
	public CheckBox getRemove() {
		return m_remove;
	}
	public void setRemove(CheckBox remove) {
		m_remove = remove;
	}
	
	public Grundbedarf() {
		super();
	}
	
	@SuppressWarnings({ "serial"})
	public Grundbedarf(final Artikel artikel) {
		super();
		m_artikelId = artikel.getId();
		
		m_lieferantName.setValue(artikel.getLieferant().getName());
		m_artikelName.setValue(artikel.getName());
		
		m_liefertermin1.setValue(artikel.getDurchschnittLT1());
		m_liefertermin2.setValue(artikel.getDurchschnittLT2());
		m_liefertermin1.setWidth("50");
		m_liefertermin2.setWidth("50");
		
		m_mengeneinheit.setValue(artikel.getMengeneinheit().getKurz());
		m_mengeneinheit.setDescription(artikel.getMengeneinheit().getName());
		m_mengeneinheit.setSizeUndefined();
		
		m_gebinde.setValue(String.valueOf(artikel.getBestellgroesse()));
		m_gebinde.setSizeUndefined();
		
		m_summe1.setValue(String.valueOf(artikel.getDurchschnittLT1() * artikel.getBestellgroesse()));
		m_summe2.setValue(String.valueOf(artikel.getDurchschnittLT2() * artikel.getBestellgroesse()));
		
		m_summe1.setSizeUndefined();
		m_summe2.setSizeUndefined();

		m_remove.setValue(false);
		
		m_liefertermin1.addValueChangeListener(new ValueChangeListener() {	
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_summe1.setValue(String.valueOf(sum(m_liefertermin1.getValue(), artikel.getBestellgroesse())));
				
			}
		});
		m_liefertermin2.addValueChangeListener(new ValueChangeListener() {	
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_summe2.setValue(String.valueOf(sum(m_liefertermin2.getValue(), artikel.getBestellgroesse())));
				
			}
		});
		m_remove.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean value = ((Boolean) m_remove.getValue()).booleanValue();
				m_artikelName.setEnabled(!value);
				m_lieferantName.setEnabled(!value);
				m_liefertermin1.setEnabled(!value);
				m_liefertermin2.setEnabled(!value);
				m_summe1.setEnabled(!value);
				m_summe2.setEnabled(!value);
				m_mengeneinheit.setEnabled(!value);
				m_gebinde.setEnabled(!value);
				
			}
		});
	}
	
	
	private double sum(int lt, double gebinde) {
		return lt * gebinde;
	}
	
}

package de.palaver.view.models;

import java.io.Serializable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.service.bestellverwaltung.BestellpositionService;

public class WarenannahmeModel implements Serializable{
	private static final long serialVersionUID = 6472077862405421086L;
	
	private Bestellposition m_bestellposition;
	private Label m_artikelName = new Label();
	private Label m_bestellgroesseLT1 = new Label();
	private Label m_bestellgroesseLT2 = new Label();
	private TextField m_komment = new TextField();
	private CheckBox m_geliefertLT1 = new CheckBox();
	private CheckBox m_geliefertLT2 = new CheckBox();
		
	public Bestellposition getBestellposition() {
		return m_bestellposition;
	}

	public void setBestellposition(Bestellposition bestellposition) {
		m_bestellposition = bestellposition;
	}

	public Label getArtikelName() {
		return m_artikelName;
	}

	public void setArtikelName(Label artikelName) {
		m_artikelName = artikelName;
	}

	public TextField getKomment() {
		return m_komment;
	}

	public void setKomment(TextField komment) {
		m_komment = komment;
	}
	
	public CheckBox getGeliefertLT1() {
		return m_geliefertLT1;
	}

	public void setGeliefertLT1(CheckBox geliefertLT1) {
		m_geliefertLT1 = geliefertLT1;
	}

	public CheckBox getGeliefertLT2() {
		return m_geliefertLT2;
	}

	public void setGeliefertLT2(CheckBox geliefertLT2) {
		m_geliefertLT2 = geliefertLT2;
	}

	public Label getBestellgroesseLT1() {
		return m_bestellgroesseLT1;
	}

	public void setBestellgroesseLT1(Label bestellgroesseLT1) {
		m_bestellgroesseLT1 = bestellgroesseLT1;
	}
	
	public Label getBestellgroesseLT2() {
		return m_bestellgroesseLT2;
	}

	public void setBestellgroesseLT2(Label bestellgroesseLT2) {
		m_bestellgroesseLT2 = bestellgroesseLT2;
	}

	public WarenannahmeModel() {
		
	}
	
	@SuppressWarnings("serial")
	public WarenannahmeModel(final Bestellposition bestellposition) {
		m_artikelName.setValue(bestellposition.getArtikel().getName());
		m_bestellposition = bestellposition;
		m_geliefertLT1.setValue(bestellposition.isStatusLT1());
		m_geliefertLT2.setValue(bestellposition.isStatusLT2());
		m_bestellgroesseLT1.setValue(bestellposition.getLiefermenge1().toString() + " " 
				+ bestellposition.getArtikel().getMengeneinheit().getKurz());
		m_bestellgroesseLT2.setValue(bestellposition.getLiefermenge2().toString() + " "  
				+ bestellposition.getArtikel().getMengeneinheit().getKurz());
		
		//m_komment.setValue(bestellposition.g)
		
		setEnable();
		
		m_geliefertLT1.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				setEnable();
				try {
					changeStatus(bestellposition.getId(), 1, ((Boolean) m_geliefertLT1.getValue()).booleanValue());
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		});
		
		m_geliefertLT2.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				setEnable();
				try {
					changeStatus(bestellposition.getId(), 2, ((Boolean) m_geliefertLT2.getValue()).booleanValue());
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setEnable() {
		if(!m_bestellposition.getBestellung().getLieferant().isMehrereliefertermine()) {
			boolean value = ((Boolean) m_geliefertLT1.getValue()).booleanValue();
			m_artikelName.setEnabled(!value);
			m_bestellgroesseLT1.setEnabled(!value);				
		} else {
			boolean value1 = ((Boolean) m_geliefertLT1.getValue()).booleanValue();
			m_bestellgroesseLT1.setEnabled(!value1);	
			boolean value2 = ((Boolean) m_geliefertLT2.getValue()).booleanValue();
			m_bestellgroesseLT2.setEnabled(!value2);
			if(value1 && value2) {
				m_artikelName.setEnabled(false);
			}
		}
	}
	
	private void changeStatus(Long id, int lt, boolean value) throws ConnectException, DAOException {
		BestellpositionService.getInstance().updateStatus(id, lt, value);
	}
}

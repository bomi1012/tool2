package de.hska.awp.palaver2.gui.view.bestellverwaltung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.service.ArtikelService;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.components.Grundbedarf;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.ArtikelErstellen;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.KategorienAnzeigen;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.OverArtikelverwaltungView;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class GrundbedarfGenerierenAnsicht extends OverBestellverwaltungView implements View,
ValueChangeListener {
	private static final Logger LOG = LoggerFactory.getLogger(KategorienAnzeigen.class.getName());
	private BeanItemContainer<Grundbedarf> container;
	private NativeSelect m_lieferantSelect;
	private OverArtikelverwaltungView m_overArtikelverwaltungView = new OverArtikelverwaltungView();
	private Button m_vorschauButton;
	private Button m_generierenButton;
	private DateField m_termin1;
	private DateField m_termin2;
	private java.sql.Date m_d1;
	private java.sql.Date m_d2;
	public GrundbedarfGenerierenAnsicht() throws SQLException, ConnectException, DAOException {
		super();
		template();
		listeners();
	}
	
	private void template() throws SQLException, ConnectException, DAOException {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, "Grundbedarf generieren", STYLE_HEADLINE);
		m_vorschauButton = buttonSetting(m_button, "Vorschau", IConstants.ICON_ZOOM, true, true);
		m_generierenButton = buttonSetting(m_button, "Grundbedarf generieren", IConstants.ICON_BASKET_ADD, true, true);

	
		
		m_lieferantSelect = nativeSelectSetting(m_lieferantSelect, "Lieferant",
				FULL, false, "Lieferant", this);
		m_lieferantSelect.setNullSelectionAllowed(false);
		m_lieferanten = Lieferantenverwaltung.getInstance().getLieferantenByGrundbedarf(true);
			
		
		m_termin1 = dateField("Liefertermin 1", "dd.MM.yyyy", FULL, Util.getDate(2, 7), true);
		m_termin2 = dateField("Liefertermin 2", "dd.MM.yyyy", FULL, Util.getDate(6, 7), true);
	
		m_vertikalLayout = new VerticalLayout();
		m_vertikalLayout.setWidth("95%");
		m_vertikalLayout.addComponent(m_lieferantSelect);
		m_vertikalLayout.addComponent(m_termin1);
		m_vertikalLayout.addComponent(m_termin2);
		m_vertikalLayout.setSpacing(true);
		
		m_filterTable = new FilterTable();
		m_filterTable.setWidth("95%");
		m_filterTable.setSelectable(true);
		m_filterTable.setFilterBarVisible(true);
		m_filterTable.setFilterGenerator(new customFilter());
		m_filterTable.setFilterDecorator(new customFilterDecorator());
		m_filterTable.setSelectable(true);
		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setWidth(FULL);
		m_horizontalLayout.setHeight(FULL);
		m_horizontalLayout.addComponent(m_vertikalLayout);
		m_horizontalLayout.addComponent(m_filterTable);
		m_horizontalLayout.setExpandRatio(m_vertikalLayout, 1);
		m_horizontalLayout.setExpandRatio(m_filterTable, 5);
		m_horizontalLayout.setComponentAlignment(m_filterTable, Alignment.TOP_RIGHT);
		
		/** ControlPanel */
		m_control = new HorizontalLayout();
		m_control.setSpacing(true);
		m_control.addComponent(m_generierenButton);
		m_control.addComponent(m_vorschauButton);
			
		allLieferanten(m_lieferanten);
		m_vertikalLayout = vLayout(m_vertikalLayout, FULL);
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);
		
	}




	private void listeners() {
		m_lieferantSelect.addValueChangeListener(new ValueChangeListener() {			
			private static final long serialVersionUID = -109892182781142316L;
			@Override
			public void valueChange(ValueChangeEvent event) {				
					try {
						beans((Lieferant) event.getProperty().getValue());
						mehrereliefertermine((Lieferant) event.getProperty().getValue());
					} catch (ConnectException e) {
						LOG.error(e.toString());
					} catch (DAOException e) {
						LOG.error(e.toString());
					} catch (SQLException e) {
						LOG.error(e.toString());
					}
			}
		});
		
		m_filterTable.addItemClickListener(new ItemClickListener() {			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()) {
					//TODO: save in Artikel
					try {
						windowModal(ArtikelService.getInstance().getArtikelById(((Grundbedarf) event.getItemId()).getArtikelId()));
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		m_vorschauButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: switch to vorschau				
			}
		});
		
		m_generierenButton.addClickListener(new ClickListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void buttonClick(ClickEvent event) {
				if(validiereEingabe()) {
					m_bestellung = new Bestellung(
							(Lieferant) m_lieferantSelect.getValue(), 
							(Mitarbeiter) ((Application) UI.getCurrent().getData()).getUser(), 
							m_d1, m_d2, false, 0);
					try {
						m_bestellung.setId(m_bestellungService.getInstance().createBestellung(m_bestellung));
						for (Grundbedarf gb : container.getItemIds()) {
							if(!(Boolean) gb.getRemove().getValue().booleanValue()) {
								m_bestellposition = new Bestellposition(
										gb.getArtikel(), m_bestellung, 
										Double.valueOf(gb.getSumme1().getValue()), 
										Double.valueOf(gb.getSumme2().getValue()), false);
								m_bestellpositionService.getInstance().createBestellposition(m_bestellposition);
							}
						}	
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					}
				}
			}
		});		
	}

	private void allLieferanten(List<Lieferant> lieferanten) throws ConnectException, DAOException, SQLException {
		m_lieferantSelect.removeAllItems();		
		for (Lieferant e : lieferanten) {
			m_lieferantSelect.addItem(e);
		}
		m_lieferantSelect.setValue(lieferanten.get(0));
		beans(lieferanten.get(0));
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	private void beans(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		m_filterTable.removeAllItems();	
		m_grundbedarfe = new ArrayList<Grundbedarf>();
		if(lieferant == null) {
			m_overArtikelverwaltungView.m_artikeln = ArtikelService.getInstance().getArtikelByGrundbedarf();
		} else {
			m_overArtikelverwaltungView.m_artikeln = ArtikelService.getInstance().getGrundbedarfByLieferantId(lieferant.getId());
		}
		for (Artikel artikel : m_overArtikelverwaltungView.m_artikeln) {		
			Grundbedarf g = new Grundbedarf(artikel);
			m_grundbedarfe.add(g);
		}
		
		try {
			container = new BeanItemContainer<Grundbedarf>(Grundbedarf.class, m_grundbedarfe);
			m_filterTable.setContainerDataSource(container);
			m_filterTable.setVisibleColumns(new Object[] { "artikelName", "gebinde", 
					"liefertermin1", "summe1", "liefertermin2", "summe2",
					"mengeneinheit", "remove"});
			m_filterTable.sort(new Object[] { "artikelName" }, new boolean[] { true });
			m_filterTable.setColumnWidth("remove", 70);
			m_filterTable.setColumnHeader("remove", "ignorieren");	
			m_filterTable.setColumnWidth("gebinde", 60);
			m_filterTable.setColumnWidth("summe1", 60);
			m_filterTable.setColumnWidth("summe2", 60);
			m_filterTable.setColumnWidth("mengeneinheit", 45);
			m_filterTable.setColumnWidth("liefertermin1", 80);
			m_filterTable.setColumnWidth("liefertermin2", 80);
			m_filterTable.setColumnAlignment("summe1", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("summe2", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("mengeneinheit", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("gebinde", m_filterTable.ALIGN_CENTER);
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	private void windowModal(Artikel artikel) {
		win = windowUI(win, OverArtikelverwaltungView.ARTIKEL, "90%", "95%");		
		if(artikel != null) {
			m_overArtikelverwaltungView.m_artikelErstellen = new ArtikelErstellen(artikel);
		} else {
			m_overArtikelverwaltungView.m_artikelErstellen = new ArtikelErstellen();
	}
		addComponent(m_overArtikelverwaltungView.m_artikelErstellen);
		win.setContent(m_overArtikelverwaltungView.m_artikelErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);		
	}
	
	private VerticalLayout vLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setMargin(true);
		box.setSpacing(true);

		box.addComponent(m_headlineLabel);
		box.setComponentAlignment(m_headlineLabel, Alignment.MIDDLE_LEFT);
		box.addComponent(m_horizontalLayout);
		box.setComponentAlignment(m_horizontalLayout, Alignment.MIDDLE_CENTER);

		box.addComponent(m_control);
		box.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		return box;
	}

	private boolean validiereEingabe() {
		boolean isValid = true;
		if(m_termin1.getValue() == null) {
			//TODO: meldung
			isValid = false;
		} else {
			m_d1 = new java.sql.Date(m_termin1.getValue().getTime());
		}
		if(m_termin2.getValue() == null) {
			m_d2 = null;
		} else {
			m_d2 = new java.sql.Date(m_termin2.getValue().getTime());
		}
		return isValid;
	}
	
	private void mehrereliefertermine(Lieferant lieferant) {
		if(!lieferant.getMehrereliefertermine()) {
			m_termin2.setVisible(false);
			m_termin2.setValue(null);
		}
	}

	@Override
	public void getViewParam(ViewData data) { }

	@Override
	public void valueChange(ValueChangeEvent event) { }
}

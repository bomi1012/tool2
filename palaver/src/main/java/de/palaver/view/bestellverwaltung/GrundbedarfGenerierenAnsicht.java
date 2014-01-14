package de.palaver.view.bestellverwaltung;

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

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.artikelverwaltung.Artikel;
import de.palaver.domain.bestellverwaltung.Bestellposition;
import de.palaver.domain.bestellverwaltung.Bestellung;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.artikelverwaltung.ArtikelService;
import de.palaver.service.person.lieferantenverwaltung.LieferantenService;
import de.palaver.view.artikelverwaltung.ArtikelErstellen;
import de.palaver.view.artikelverwaltung.KategorienAnzeigen;
import de.palaver.view.artikelverwaltung.OverArtikelverwaltungView;
import de.palaver.view.models.GrundbedarfModel;

@SuppressWarnings("serial")
public class GrundbedarfGenerierenAnsicht extends OverBestellverwaltungView implements View,
ValueChangeListener {
	private static final Logger LOG = LoggerFactory.getLogger(KategorienAnzeigen.class.getName());
	private static final String GRUNDBEDARF = "Grundbedarf";
	private static final String GRUNDBEDARF_GENERIEREN = "Grundbedarf generieren";
	private static final String BESTELLUNG_ERSTELLEN = "Bestellung erstellen";
	private static final String LIFERANT = "Lieferant";
	private static final String LIFERTERMIN = "Liefertermin ";
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	
	private BeanItemContainer<GrundbedarfModel> m_container;
	private NativeSelect m_lieferantSelect;
	private OverArtikelverwaltungView m_overArtikelverwaltungView = new OverArtikelverwaltungView();
	private Button m_erstellenButton;
	private DateField m_termin1;
	private DateField m_termin2;
	private java.sql.Date m_d1;
	private java.sql.Date m_d2;
	public GrundbedarfGenerierenAnsicht() throws SQLException, ConnectException, DAOException {
		super();
		layout();
		listeners();
	}
	
	private void layout() throws SQLException, ConnectException, DAOException {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, GRUNDBEDARF_GENERIEREN, STYLE_HEADLINE);
		m_erstellenButton = buttonSetting(m_button, BESTELLUNG_ERSTELLEN, IConstants.ICON_BASKET_ADD, true, true);
		
		m_lieferantSelect = nativeSelectSetting(m_lieferantSelect, LIFERANT,
				FULL, false, LIFERANT, this);
		m_lieferantSelect.setNullSelectionAllowed(false);
		m_lieferanten = LieferantenService.getInstance().getLieferantenByGrundbedarf(true);
			
		
		m_termin1 = dateField(LIFERTERMIN + 1, DATE_FORMAT, FULL, Util.getDate(2, 7), true);
		m_termin2 = dateField(LIFERTERMIN + 2, DATE_FORMAT, FULL, Util.getDate(6, 7), true);
	
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
		m_control.addComponent(m_erstellenButton);
			
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
					try {
						windowModal(ArtikelService.getInstance().getArtikelById(((GrundbedarfModel) event.getItemId()).getArtikelId()));
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
	
		m_erstellenButton.addClickListener(new ClickListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void buttonClick(ClickEvent event) {
				if(validiereEingabe()) {
					m_bestellung = new Bestellung(
							(Lieferant) m_lieferantSelect.getValue(), 
							(Mitarbeiter) ((Application) UI.getCurrent().getData()).getUser(), 
							m_d1, m_d2, false, GRUNDBEDARF);
					try {
						m_bestellung.setId(m_bestellungService.getInstance().createBestellung(m_bestellung));
						for (GrundbedarfModel gb : m_container.getItemIds()) {
							if(!(Boolean) gb.getIgnore().getValue().booleanValue()) {
								m_bestellposition = new Bestellposition(
										gb.getArtikel(), m_bestellung, 
										Double.valueOf(gb.getSumme1().getValue()), 
										Double.valueOf(gb.getSumme2().getValue()), false);
								m_bestellpositionService.getInstance().createBestellposition(m_bestellposition);
							}
						}	
						((Application) UI.getCurrent().getData()).showDialog(String.format(
								"Die Bestellung für den Lieferant <%s> wurde generiert!", m_bestellung.getLieferant().getName()));
						ViewHandler.getInstance().switchView(BestellungenAnzeigenTable.class,
						new ViewDataObject<Bestellung>(m_bestellung));
						
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
	
	private void beans(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		m_grundbedarfe = new ArrayList<GrundbedarfModel>();
		if(lieferant == null) {
			m_overArtikelverwaltungView.m_artikeln = ArtikelService.getInstance().getArtikelByGrundbedarf();
		} else {
			m_overArtikelverwaltungView.m_artikeln = ArtikelService.getInstance().getGrundbedarfByLieferantId(lieferant.getId());
		}
		for (Artikel artikel : m_overArtikelverwaltungView.m_artikeln) {		
			GrundbedarfModel g = new GrundbedarfModel(artikel);
			m_grundbedarfe.add(g);
		}
		
		try {
			m_container = new BeanItemContainer<GrundbedarfModel>(GrundbedarfModel.class, m_grundbedarfe);
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { FIELD_ARTIKEL_NAME, FIELD_BESTELLGROESSE, 
				FIELD_LIEFERDATUM_1, FIELD_SUMME_1, FIELD_LIEFERDATUM_2, FIELD_SUMME_2,
				FIELD_MENGENEINHEIT, FIELD_IGNORE});
		m_filterTable.sort(new Object[] { FIELD_ARTIKEL_NAME }, new boolean[] { true });
		m_filterTable.setColumnWidth(FIELD_IGNORE, 70);
		m_filterTable.setColumnHeader(FIELD_IGNORE, "ignorieren");	
		m_filterTable.setColumnWidth(FIELD_BESTELLGROESSE, 60);
		m_filterTable.setColumnWidth(FIELD_SUMME_1, 60);
		m_filterTable.setColumnWidth(FIELD_SUMME_2, 60);
		m_filterTable.setColumnWidth(FIELD_MENGENEINHEIT, 45);
		m_filterTable.setColumnWidth(FIELD_LIEFERDATUM_1, 80);
		m_filterTable.setColumnWidth(FIELD_LIEFERDATUM_2, 80);
		m_filterTable.setColumnAlignment(FIELD_SUMME_1, m_filterTable.ALIGN_CENTER);
		m_filterTable.setColumnAlignment(FIELD_SUMME_2, m_filterTable.ALIGN_CENTER);
		m_filterTable.setColumnAlignment(FIELD_MENGENEINHEIT, m_filterTable.ALIGN_CENTER);
		m_filterTable.setColumnAlignment(FIELD_BESTELLGROESSE, m_filterTable.ALIGN_CENTER);	
	}

	private void windowModal(final Artikel artikel) {
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
		m_overArtikelverwaltungView.m_artikelErstellen.m_speichernButton.addClickListener(
		new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {	
				
				for (GrundbedarfModel gb : m_grundbedarfe) {
					if(gb.getArtikelId() == artikel.getId()) {
						gb.init(artikel);
					}
				}
				setTable();				
			}
		});
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
		if(!lieferant.isMehrereliefertermine()) {
			m_termin2.setVisible(false);
			//m_termin2.setValue(null);
		} else {
			m_termin2.setVisible(true);
			//m_termin2 = dateField(LIFERTERMIN + 2, DATE_FORMAT, FULL, Util.getDate(6, 7), true);
		}
	}

	@Override
	public void getViewParam(ViewData data) { }

	@Override
	public void valueChange(ValueChangeEvent event) { }
}

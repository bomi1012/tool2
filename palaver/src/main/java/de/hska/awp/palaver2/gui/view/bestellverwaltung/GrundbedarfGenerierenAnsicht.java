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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.gui.components.Grundbedarf;
import de.hska.awp.palaver2.gui.view.artikelverwaltung.KategorienAnzeigen;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class GrundbedarfGenerierenAnsicht extends BestellverwaltungView implements View,
ValueChangeListener {
	private static final Logger log = LoggerFactory.getLogger(KategorienAnzeigen.class.getName());
	private BeanItemContainer<Grundbedarf> container;
	private NativeSelect m_lieferantSelect;
	public GrundbedarfGenerierenAnsicht() throws SQLException, ConnectException, DAOException {
		super();
		template();
		listeners();
		beans(null);
	}
	
	private void template() throws SQLException, ConnectException, DAOException {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, "Grundbedarf generieren", "ViewHeadline");
		m_auswaehlenButton = buttonSetting(m_auswaehlenButton, "Vorschau", IConstants.ICON_ZOOM, true, true);

		m_lieferantSelect = nativeSelectSetting(m_lieferantSelect, "Lieferant",
				"80%", false, "Lieferant", this);
		m_lieferantSelect.setNullSelectionAllowed(false);
		
		m_lieferanten = Lieferantenverwaltung.getInstance().getLieferantenByGrundbedarf(true);
		
		allLieferanten(m_lieferanten);
		
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
		m_horizontalLayout.addComponent(m_lieferantSelect);
		m_horizontalLayout.addComponent(m_filterTable);
		m_horizontalLayout.setExpandRatio(m_lieferantSelect, 1);
		m_horizontalLayout.setExpandRatio(m_filterTable, 5);
		m_horizontalLayout.setComponentAlignment(m_filterTable, Alignment.TOP_RIGHT);
		
		
		/** ControlPanel */
		m_control = new HorizontalLayout();
		m_control.setSpacing(true);
		m_control.addComponent(m_auswaehlenButton);
			
		vertikalLayout = vLayout(vertikalLayout, FULL);
		this.addComponent(vertikalLayout);
		this.setComponentAlignment(vertikalLayout, Alignment.MIDDLE_CENTER);
		
	}


	private void listeners() {
		m_lieferantSelect.addValueChangeListener(new ValueChangeListener() {			
			private static final long serialVersionUID = -109892182781142316L;
			@Override
			public void valueChange(ValueChangeEvent event) {				
					try {
						beans((Lieferant) event.getProperty().getValue());
					} catch (ConnectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

//					for (Grundbedarf ggg : container.getItemIds()) {
//						System.out.println(ggg.getArtikelName() + " " + ggg.getSumme());
//					}

			}
		});
		
		m_filterTable.addItemClickListener(new ItemClickListener() {			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()) {
					//TODO: save in Artikel
					System.out.println(((Grundbedarf) event.getItemId()).getArtikelId());
				}
			}
		});
		
		m_auswaehlenButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: switch to vorschau				
			}
		});
		
	}

	private void allLieferanten(List<Lieferant> lieferanten) {
		m_lieferantSelect.removeAllItems();
		for (Lieferant e : lieferanten) {
			m_lieferantSelect.addItem(e);
		}
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	private void beans(Lieferant lieferant) throws ConnectException, DAOException, SQLException {
		m_filterTable.removeAllItems();	
		List<Grundbedarf> gb = new ArrayList<Grundbedarf>();
		List<Artikel> m_arList = new ArrayList<Artikel>();
		if(lieferant == null) {
			m_arList = Artikelverwaltung.getInstance().getArtikelByGrundbedarf();
		} else {
			m_arList = Artikelverwaltung.getInstance().getGrundbedarfByLieferantId(lieferant.getId());
		}
		for (Artikel artikel : m_arList) {		
			Grundbedarf g = new Grundbedarf(artikel);
			gb.add(g);
		}
		
		try {
			container = new BeanItemContainer<Grundbedarf>(Grundbedarf.class, gb);
			m_filterTable.setContainerDataSource(container);
			m_filterTable.setVisibleColumns(new Object[] { "lieferantName", "artikelName", "gebinde", 
					"liefertermin1", "summe1", "liefertermin2", "summe2",
					"mengeneinheit", "remove"});
			m_filterTable.sort(new Object[] { "lieferantName", "artikelName" }, new boolean[] { true });
			m_filterTable.setColumnWidth("remove", 40);
			m_filterTable.setColumnHeader("remove", "ignorieren");	
			m_filterTable.setColumnWidth("gebinde", 60);
			m_filterTable.setColumnWidth("summe1", 60);
			m_filterTable.setColumnWidth("summe2", 60);
			m_filterTable.setColumnWidth("mengeneinheit", 45);
			m_filterTable.setColumnWidth("liefertermin1", 70);
			m_filterTable.setColumnWidth("liefertermin2", 70);
			m_filterTable.setColumnAlignment("summe1", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("summe2", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("mengeneinheit", m_filterTable.ALIGN_CENTER);
			m_filterTable.setColumnAlignment("gebinde", m_filterTable.ALIGN_CENTER);
		} catch (Exception e) {
			
			
			log.error(e.toString());
		}		
	}
	
	private VerticalLayout vLayout(VerticalLayout box, String width) {
		box = new VerticalLayout();
		box.setWidth(width);
		box.setSpacing(true);
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



	@Override
	public void getViewParam(ViewData data) { }

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}

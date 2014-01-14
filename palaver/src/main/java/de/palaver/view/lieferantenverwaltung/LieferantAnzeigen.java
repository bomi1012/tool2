package de.palaver.view.lieferantenverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.Application;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.person.lieferantenverwaltung.LieferantenService;

@SuppressWarnings("serial")
public class LieferantAnzeigen extends OverLieferantverwaltungView implements View,
ValueChangeListener {
	private static final Logger log = LoggerFactory.getLogger(LieferantAnzeigen.class.getName());

	private Button m_showFilterButton;
	private HorizontalLayout headLayout;
	private Lieferant lieferant;
	private BeanItemContainer<Lieferant> m_container;

	public LieferantAnzeigen() {
		super();
		layout();
		listeners();
		beans();
	}

	private void layout() {
		this.setSizeFull();
		this.setMargin(true);
		
		m_filterTable = filterTable();	
		m_showFilterButton = filterButton(true, true);			
		m_control = controlLieferantenPanel();		
		m_headlineLabel = headLine(m_headlineLabel, "Alle Lieferanten", STYLE_HEADLINE);
	
		headLayout = new HorizontalLayout();
		headLayout.setWidth(FULL);
		headLayout.addComponent(m_headlineLabel);
		headLayout.setComponentAlignment(m_headlineLabel, Alignment.MIDDLE_LEFT);
		headLayout.addComponent(m_showFilterButton);
		headLayout.setComponentAlignment(m_showFilterButton, Alignment.MIDDLE_RIGHT);
		headLayout.setExpandRatio(m_headlineLabel, 1);

		this.addComponent(headLayout);
		this.setSpacing(true);
		this.addComponent(m_filterTable);
		this.setExpandRatio(m_filterTable, 1);
		this.addComponent(m_control);
		this.setComponentAlignment(m_control, Alignment.BOTTOM_RIGHT);
		
	}
	
	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					lieferant = (Lieferant) event.getProperty().getValue();
					m_control.setEnabled(true);
				}
			}
		});

		m_filterTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_auswaehlenButton.click();
				}
			}
		});

		m_auswaehlenButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (lieferant != null) {
					//TODO: Window
					//ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
				}
				else {
					((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_LIEFERANT_AUSWAEHLEN);
				}
			}
		});
		m_showFilterButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_filterTable.resetFilters();
			}
		});
	}
	
	private void beans() {		
		try {
			m_container = new BeanItemContainer<Lieferant>(Lieferant.class, LieferantenService.getInstance().getAllLieferanten());
			setTable();
		} catch (Exception e) {
			log.error(e.toString());
		}
		
	}

	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "name", "lieferantnummer", "bezeichnung", "notiz" });
		m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });		
	}

	@Override
	public void getViewParam(ViewData data) { }

	@Override
	public void valueChange(ValueChangeEvent event) { }
}
package de.palaver.view.artikelverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.domain.artikelverwaltung.Lagerort;
import de.palaver.service.artikelverwaltung.LagerorService;

@SuppressWarnings("serial")
public class LagerorteAnzeigen extends OverAnzeigen implements View {
	private static final Logger LOG = LoggerFactory.getLogger(LagerorteAnzeigen.class.getName());
	private static final String LAGERORTE_ALL =  "Alle Lagerorte";
	private static final String LAYOUT = "50%";
	private static final String WIDTH = "500";
	private static final String HEIGHT = "250";
	
	private BeanItemContainer<Lagerort> m_container;
	public LagerorteAnzeigen() {
		super();
		template();
		listeners();
		beans();
	}


	private void template() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, LAGERORTE_ALL, STYLE_HEADLINE);
		m_table = table();
		m_control = controlPanelEditAndNew();		
		m_vertikalLayout = addToLayoutTableAndControl(m_vertikalLayout, LAYOUT);
			
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);
	}
	
	
	private void listeners() {
		m_table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_lagerort = (Lagerort) event.getProperty().getValue();
					m_editButton.setEnabled(true);
				}
			}
		});
		m_table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_editButton.click();
				}
			}
		});

		m_editButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_lagerort != null) {
					windowModal(m_lagerort);
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_LAGERORT_AUSWAEHLEN);
				}
			}
		});
		
		m_createButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModal(null);
			}
		});			
	}

	private void windowModal(Lagerort lagerort) {				
		win = windowUI(win, EDIT_LAGERORT, WIDTH, HEIGHT);
		if(lagerort != null) {
			m_lagerortErstellen = new LagerortErstellen(lagerort);
		} else {
			m_lagerortErstellen  = new LagerortErstellen();
		}
		addComponent(m_lagerortErstellen);
		win.setContent(m_lagerortErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		m_lagerortErstellen.m_speichernButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				m_container.addItem(m_lagerortErstellen.m_lagerort);
				setTable();
			}
		});
		m_lagerortErstellen.m_deaktivierenButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				if(m_lagerortErstellen.m_okRemove) {
					m_container.removeItem(m_lagerort);
					setTable();
					m_okRemove = false;
				}
			}
		});
	}
	
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { FIELD_NAME });
		m_table.sort(new Object[] { FIELD_NAME }, new boolean[] { true });
	}
	
	private void beans() {	
		try {
			m_container = new BeanItemContainer<Lagerort>(Lagerort.class,
					LagerorService.getInstance().getAllLagerorts());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}

	@Override
	public void getViewParam(ViewData data) { }
}

package de.hska.awp.palaver2.gui.view.artikelverwaltung;

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
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.service.KategorieService;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class KategorienAnzeigen extends OverAnzeigen implements View {
	private static final Logger LOG = LoggerFactory.getLogger(KategorienAnzeigen.class.getName());
	private static final String KATEGORIE_ALL = "Alle Kategorien";
	private static final String LAYOUT = "50%";
	private static final String WIDTH = "500";
	private static final String HEIGHT = "250";
	private BeanItemContainer<Kategorie> m_container;
	public KategorienAnzeigen() {
		super();
		template();
		listeners();
		beans();
	}


	private void template() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, KATEGORIE_ALL, STYLE_HEADLINE);
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
					m_kategorie = (Kategorie) event.getProperty().getValue();
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
				if (m_kategorie != null) {
					windowModal(m_kategorie);
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_KATEGORIE_AUSWAEHLEN);
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

	private void windowModal(Kategorie kategorie) {				
		win = windowUI(win, EDIT_KATEGORIE, WIDTH, HEIGHT);
		if(kategorie != null) {
			m_kategorieErstellen = new KategorieErstellen(kategorie);
		} else {
			m_kategorieErstellen = new KategorieErstellen();
		}
		addComponent(m_kategorieErstellen);
		win.setContent(m_kategorieErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		m_kategorieErstellen.m_speichernButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				m_container.addItem(m_kategorieErstellen.m_kategorie);
				setTable();
			}
		});
		m_kategorieErstellen.m_deaktivierenButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				if(m_kategorieErstellen.m_okRemove) {
					m_container.removeItem(m_kategorie);
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
			m_container = new BeanItemContainer<Kategorie>(Kategorie.class,
					KategorieService.getInstance().getAllKategories());
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}

	@Override
	public void getViewParam(ViewData data) { }
}

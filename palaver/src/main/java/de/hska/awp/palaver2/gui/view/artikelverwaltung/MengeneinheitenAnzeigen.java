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
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.artikelverwaltung.service.MengeneinheitService;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class MengeneinheitenAnzeigen extends OverAnzeigen implements View {
	private static final Logger LOG = LoggerFactory.getLogger(MengeneinheitenAnzeigen.class.getName());
	private static final String WIDTH = "500";
	private static final String HEIGHT = "250";
	private static final String LAYOUT = "50%";
	private static final String MENGENEINHEIT_EDIT = "Mengeneinheit bearbeiten";
	private static final String MENGENEINHEIT_ALL = "Alle Mengeneinheiten";
	private BeanItemContainer<Mengeneinheit> m_container;
	public MengeneinheitenAnzeigen() {
		super();
		template();
		listeners();
		beans();
	}
	
	private void template() {
		this.setSizeFull();
		this.setMargin(true);
		m_headlineLabel = headLine(m_headlineLabel, MENGENEINHEIT_ALL, STYLE_HEADLINE);
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
					m_mengeneinheit = (Mengeneinheit) event.getProperty().getValue();
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
				if (m_mengeneinheit != null) {
					windowModal(m_mengeneinheit);
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

	private void windowModal(Mengeneinheit mengeneinheit) {
		win = windowUI(win, MENGENEINHEIT_EDIT, WIDTH, HEIGHT);		
		if(mengeneinheit != null) {
			m_mengeneinheitErstellen = new MengeneinheitErstellen(mengeneinheit);
		} else {
			m_mengeneinheitErstellen = new MengeneinheitErstellen();
		}
		addComponent(m_mengeneinheitErstellen);
		win.setContent(m_mengeneinheitErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		m_mengeneinheitErstellen.m_speichernButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				m_container.addItem(m_mengeneinheitErstellen.m_mengeneinheit);
				setTable();
			}
		});
		m_mengeneinheitErstellen.m_deaktivierenButton.addClickListener(new ClickListener() {					
			@Override
			public void buttonClick(ClickEvent event) {	
				if(m_mengeneinheitErstellen.m_okRemove) {
					m_container.removeItem(m_mengeneinheit);
					setTable();
				}
			}
		});		
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { FIELD_NAME, FIELD_KURZ });
		m_table.sort(new Object[] { FIELD_NAME }, new boolean[] { true });
		m_table.setColumnWidth(FIELD_KURZ, 90);
		m_table.setColumnHeader(FIELD_KURZ, "abkürzung");
		m_table.setColumnAlignment(FIELD_KURZ, m_table.ALIGN_CENTER);
	}
	
	private void beans() {	
		try {
			m_container = new BeanItemContainer<Mengeneinheit>(Mengeneinheit.class,
					MengeneinheitService.getInstance().getAllMengeneinheiten());
			setTable();			
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	@Override
	public void getViewParam(ViewData data) { }
	
}

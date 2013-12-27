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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class MengeneinheitenAnzeigen extends ArtikelverwaltungView implements View {
	private static final Logger log = LoggerFactory.getLogger(MengeneinheitenAnzeigen.class.getName());
	BeanItemContainer<Mengeneinheit> container;
	public MengeneinheitenAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		
		m_headlineLabel = headLine(m_headlineLabel, "Alle Mengeneinheiten", "ViewHeadline");
		m_createNewButton = buttonSetting(m_createNewButton, IConstants.BUTTON_NEW,
				IConstants.BUTTON_NEW_ICON, true);
		m_auswaehlenButton = buttonSetting(m_auswaehlenButton, IConstants.BUTTON_EDIT,
				IConstants.BUTTON_EDIT_ICON, true);
		
		m_table = new Table();
		m_table.setSizeFull();
		m_table.setSelectable(true);

		/** ControlPanel */
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_createNewButton);
		m_horizontalLayout.addComponent(m_auswaehlenButton);
		
		vertikalLayout = vLayout(vertikalLayout, "50%");

		m_table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_mengeneinheit = (Mengeneinheit) event.getProperty().getValue();
				}
			}
		});
		m_table.addItemClickListener(new ItemClickListener() {
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
					if (m_mengeneinheit != null) {
						windowModal(m_mengeneinheit);
					} else {
						((Application) UI.getCurrent().getData())
								.showDialog(IConstants.INFO_KATEGORIE_AUSWAEHLEN);
					}
				}
			});
			
			m_createNewButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					windowModal(null);
				}
			});	
			
			beans();
			this.addComponent(vertikalLayout);
			this.setComponentAlignment(vertikalLayout, Alignment.MIDDLE_CENTER);
	}
	
	private void windowModal(Mengeneinheit mengeneinheit) {
		win = windowUI(win, "Mengeneinheit Bearbeiten", "500", "250");		
		if(mengeneinheit != null) {
			m_mengeneinheitErstellen = new MengeneinheitErstellen(mengeneinheit);
		} else {
			m_mengeneinheitErstellen = new MengeneinheitErstellen();
		}
		addComponent(m_mengeneinheitErstellen);
		win.setContent(m_mengeneinheitErstellen);
		win.setModal(true);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new CloseListener() {						
			@Override
			public void windowClose(CloseEvent e) {
				beans();
			}
		});	
		
	}
	
	private void beans() {
		m_table.removeAllItems();	
		try {
			container = new BeanItemContainer<Mengeneinheit>(Mengeneinheit.class,
					Mengeneinheitverwaltung.getInstance().getAllMengeneinheiten());
			m_table.setContainerDataSource(container);
			m_table.setVisibleColumns(new Object[] { "name", "kurz" });
			m_table.sort(new Object[] { "name" }, new boolean[] { true });
			m_table.setColumnWidth("kurz", 90);
			m_table.setColumnHeader("kurz", "abkürzung");
			m_table.setColumnAlignment("kurz", m_table.ALIGN_CENTER);
			
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

		box.addComponent(m_table);
		box.setComponentAlignment(m_table, Alignment.MIDDLE_CENTER);
		box.addComponent(m_horizontalLayout);
		box.setComponentAlignment(m_horizontalLayout, Alignment.MIDDLE_RIGHT);
		return box;
	}

	@Override
	public void getViewParam(ViewData data) { }
	
}

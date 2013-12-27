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
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class KategorienAnzeigen extends ArtikelverwaltungView implements View {
	private static final Logger log = LoggerFactory.getLogger(KategorienAnzeigen.class.getName());
	BeanItemContainer<Kategorie> container;
	public KategorienAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		m_headlineLabel = headLine(m_headlineLabel, "Alle Kategorien", "ViewHeadline");
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
					m_kategorie = (Kategorie) event.getProperty().getValue();
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
				if (m_kategorie != null) {
					windowModal(m_kategorie);
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

	private void windowModal(Kategorie kategorie) {				
		win = windowUI(win, "Kategorie Bearbeiten", "500", "250");
		if(kategorie != null) {
			m_kategorieErstellen = new KategorieErstellen(kategorie);
		} else {
			m_kategorieErstellen = new KategorieErstellen();
		}
		addComponent(m_kategorieErstellen);
		win.setContent(m_kategorieErstellen);
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
			container = new BeanItemContainer<Kategorie>(Kategorie.class,
					Kategorienverwaltung.getInstance().getAllKategories());
			m_table.setContainerDataSource(container);
			m_table.setVisibleColumns(new Object[] { "name" });
			m_table.sort(new Object[] { "name" }, new boolean[] { true });
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
	public void getViewParam(ViewData data) {

	}
}

package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

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
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnzeigenTabelle extends VerticalLayout implements View {

	private FilterTable table;

	private Button showFilter;
	private Rezept rezept;

	public RezeptAnzeigenTabelle() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		// showFilter.setIcon(new ThemeResource("img/filter.ico"));

		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(false);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					rezept = (Rezept) event.getProperty().getValue();
				}

			}
		});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(RezeptAnlegen.class,
							new ViewDataObject<Rezept>(rezept));
				}

			}
		});

		BeanItemContainer<Rezept> container;

		try {
			container = new BeanItemContainer<Rezept>(Rezept.class,
					Rezeptverwaltung.getInstance().getAllRezepte());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
					"geschmack", "mitarbeiter", "erstellt" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.addComponent(showFilter);
		this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.isFilterBarVisible()) {
					table.setFilterBarVisible(false);
					table.resetFilters();
					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					// showFilter.setIcon(new ThemeResource("img/filter.ico"));
				} else {
					table.setFilterBarVisible(true);
					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					// showFilter.setIcon(new
					// ThemeResource("img/disable_filter.ico"));
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util
	 * .ViewData)
	 */
	@Override
	public void getViewParam(ViewData data) {
	}

}
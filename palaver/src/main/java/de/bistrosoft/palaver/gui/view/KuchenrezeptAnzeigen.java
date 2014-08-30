package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.Application;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnzeigen extends VerticalLayout implements View {

	// Layout
	private HorizontalLayout hlControl = new HorizontalLayout();
	
	// Tabelle
	private FilterTable table;

	// Überschrift
	private Label ueberschrift = new Label("Kuchenrezepte");

	// Kuchenrezept
	private Kuchenrezept kuchenrezept;

	// Buttons
	private Button btFilterLeeren;
	private Button btAuswaehlen;
	private Button btLoeschen;
	private Button btNeuesRezept;

	// Konstruktor
	public KuchenrezeptAnzeigen() {
		super();

		// Komponenten
		
		ueberschrift.setStyleName("ViewHeadline");
		
		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);
		btLoeschen = new Button(IConstants.BUTTON_DELETE);
		btLoeschen.setIcon(new ThemeResource("img/cross.ico"));
		btNeuesRezept=new Button(IConstants.BUTTON_NEW);
		btNeuesRezept.setIcon(new ThemeResource("img/new.ico"));

		this.setSizeFull();
		this.setMargin(true);

		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);
		btFilterLeeren.setIcon(new ThemeResource("img/disable_filter.ico"));

		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);

		// ValueChangeListener
		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					kuchenrezept = (Kuchenrezept) event.getProperty()
							.getValue();
				}

			}
		});

		// ClickListener zum Auswählen eines Kuchenrezeptes per Doppelklick
		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(
							KuchenrezeptAnlegen.class,
							new ViewDataObject<Kuchenrezept>(kuchenrezept));
				}

			}
		});

		// BeanItemContainer befüllen
		BeanItemContainer<Kuchenrezept> container;

		try {
			container = new BeanItemContainer<Kuchenrezept>(Kuchenrezept.class,
					Kuchenrezeptverwaltung.getInstance().getAllKuchenrezepte(
							false));
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "erstellt" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.addComponent(ueberschrift);
		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(hlControl);
		this.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(btNeuesRezept);
		hlControl.addComponent(btLoeschen);
		hlControl.addComponent(btAuswaehlen);

		// ClickListener zum Leeren des Filters
		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});

		// ClickListener zum Auswählen eines Kuchenrezeptes per Button-Klick
		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Kuchenrezept) {

					Kuchenrezept kuchenrezeptAusTb = (Kuchenrezept) table
							.getValue();
					ViewHandler.getInstance()
							.switchView(
									KuchenrezeptAnlegen.class,
									new ViewDataObject<Kuchenrezept>(
											kuchenrezeptAusTb));
				} else {
					((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_KUCHENREZEPTANZEIGEN_SELECT);
				}

			}
		});
		
		// ClickListener zum Löschen eines Kuchenrezeptes
		btLoeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Kuchenrezept) {

					Kuchenrezept kuchenrezeptAusTb = (Kuchenrezept) table.getValue();
					try {
						Kuchenrezeptverwaltung.getInstance().setKuchenrezeptDisabled(
								kuchenrezeptAusTb);
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_KUCHENREZEPT_DELETE);

					ViewHandler.getInstance().switchView(
							KuchenrezeptAnzeigen.class);
				}
			}
		});
		
		// ClickListener zum Anlegen eines neuen Rezeptes
		btNeuesRezept.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(KuchenrezeptAnlegen.class);
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
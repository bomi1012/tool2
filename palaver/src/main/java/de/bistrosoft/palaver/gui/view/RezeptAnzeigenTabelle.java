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

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnzeigenTabelle extends VerticalLayout implements View {

	//Horizontales Layout
	private HorizontalLayout hlControl = new HorizontalLayout();

	//Gesamttabelle der Rezepte
	private FilterTable table;

	//Rezept zum Befüllen der Tabelle
	private Rezept rezept;
	
	//Buttons
	private Button btFilterLeeren;
	private Button btAuswaehlen;
	private Button btLoeschen;
	private Button btNeuesRezept;

	//Label
	private Label headline;

	public RezeptAnzeigenTabelle() {
		super();

		//Button beschriften über die IConstans
		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);
		btLoeschen = new Button(IConstants.BUTTON_DELETE);
		btNeuesRezept=new Button(IConstants.BUTTON_NEW);
		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);

		//Buttons belegen mit einem Bild/Image
		btNeuesRezept.setIcon(new ThemeResource("img/new.ico"));
		btLoeschen.setIcon(new ThemeResource("img/cross.ico"));
		btFilterLeeren.setIcon(new ThemeResource("img/cross.ico"));

		//Setze Seite auf volle Größe
		this.setSizeFull();
		this.setMargin(true);

		//Aufbau der Tabelle, welche die Rezepte beinhalten wird.
		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(true);
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

			//Durch doppelcklick wird das Rezept geöffnet in der View RezeptAnlegen
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(RezeptAnlegen.class,
							new ViewDataObject<Rezept>(rezept));
				}
			}
		});

		//Container, welcher mit den Spaten die filterbar sind gefüllt wird
		BeanItemContainer<Rezept> container;

		try {
			//Container mit den Spalten Name, Rezeptart, Mitarbeiter, Erstellt.
			container = new BeanItemContainer<Rezept>(Rezept.class,
					Rezeptverwaltung.getInstance().getAllRezepteTabelleAktiv());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "rezeptart",
					"mitarbeiter", "erstellt" });
			//Sortiert nach Rezept
			table.sort(new Object[] { "name" }, new boolean[] { true });
			//Setze den angemeldeten Mitarbeiter als default Filter
			table.setFilterFieldValue("mitarbeiter", ((Application) UI
					.getCurrent().getData()).getUser().getVorname());

		} catch (Exception e) {
			e.printStackTrace();
		}

		//Dieser Button wird benötigt, damit auf dem Tablet ein Rezept ausgewählt werden kann
		//da auf Touch kein Doppelklick möglich
		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Rezept) {

					Rezept rezeptAusTb = (Rezept) table.getValue();
					ViewHandler.getInstance().switchView(RezeptAnlegen.class,
							new ViewDataObject<Rezept>(rezeptAusTb));
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REZEPTANZEIGEN_SELECT);
				}
			}
		});

		//Ab hier wird die Seite aufgebaut
		//Überschrift
		headline = new Label("Alle Rezepte");
		headline.setStyleName("ViewHeadline");
		this.addComponent(headline);
		this.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);

		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(hlControl);
		this.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		
		//Aufbau der Buttons unter der Tabelle
		hlControl.addComponent(btNeuesRezept);
		hlControl.addComponent(btLoeschen);
		hlControl.addComponent(btAuswaehlen);

		//Button, der den Filter wieder leert
		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});

		//Button für Löschen eines Rezeptes
		btLoeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Rezept) {

					Rezept rezeptAusTb = (Rezept) table.getValue();
					try {
						Rezeptverwaltung.getInstance().setRezeptDisabled(
								rezeptAusTb);
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REZEPT_DELETE);

					ViewHandler.getInstance().switchView(
							RezeptAnzeigenTabelle.class);
				}
			}
		});
		
		//Bei klick auf diesen Button wird in die View RezeptAnlegen mit der Möglichkeit
		//ein neues Rezept anzulegen
		btNeuesRezept.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(RezeptAnlegen.class);
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
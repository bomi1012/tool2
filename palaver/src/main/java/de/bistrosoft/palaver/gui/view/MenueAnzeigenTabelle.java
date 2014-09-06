package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter.ConversionException;
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

import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.palaver.Application;
import de.palaver.management.menu.Menu;
import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

@SuppressWarnings("serial")
public class MenueAnzeigenTabelle extends VerticalLayout implements View {

	private HorizontalLayout hlControl = new HorizontalLayout();
	private FilterTable table;

	private Button btFilterLeeren;
	private Menu menu;

	private Button btAuswaehlen;
	private Button btLoeschen;
	private Button btNeuesMenue;

	private Label headline;

	public MenueAnzeigenTabelle() throws ConnectException, DAOException,
			SQLException {
		super();
		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);
		btLoeschen = new Button(IConstants.BUTTON_DELETE);
		btLoeschen.setIcon(new ThemeResource("img/delete.ico"));
		btNeuesMenue = new Button(IConstants.BUTTON_NEW);
		btNeuesMenue.setIcon(new ThemeResource("img/new.ico"));

		this.setSizeFull();
		this.setMargin(true);

		headline = new Label("Alle Menüs");
		headline.setStyleName("ViewHeadline");

		this.addComponent(headline);
		this.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);

		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);
		btFilterLeeren.setIcon(new ThemeResource("img/disable_filter.ico"));

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
					menu = (Menu) event.getProperty().getValue();
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(MenueAnlegen.class,
							new ViewDataObject<Menu>(menu));
				}
			}
		});

		BeanItemContainer<Menu> container;

		try {
			container = new BeanItemContainer<Menu>(Menu.class,
					Menueverwaltung.getInstance().getAllMenuesFast());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "kochname",
					"geschmack", "menueart" });
			table.setFilterBarVisible(true);
			table.sort(new Object[] { "name" }, new boolean[] { true });
			table.markAsDirty();
			table.setFilterFieldValue("kochname", ((Application) UI
					.getCurrent().getData()).getUser().getVorname());

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConversionException e) {
			e.printStackTrace();
		}

		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(hlControl);
		this.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(btNeuesMenue);
		hlControl.addComponent(btLoeschen);
		hlControl.addComponent(btAuswaehlen);
		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Menu) {

					Menu menueAusTb = (Menu) table.getValue();
					ViewHandler.getInstance().switchView(MenueAnlegen.class,
							new ViewDataObject<Menu>(menueAusTb));
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_MENUEANZEIGEN_SELECT);
				}
			}
		});

		btLoeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Menu) {

					Menu menueAusTb = (Menu) table.getValue();
					try {
						Menueverwaltung.getInstance().setMenueDisabled(
								menueAusTb);
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_MENUE_DELETE);

					ViewHandler.getInstance().switchView(
							MenueAnzeigenTabelle.class);
				}
			}
		});

		btNeuesMenue.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(MenueAnlegen.class);
			}
		});

	}

	@Override
	public void getViewParam(IViewData data) {
	}
}
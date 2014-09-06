package de.bistrosoft.palaver.gui.view;

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
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.RegelDAO;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class RegelnAnzeigen extends VerticalLayout implements View {

	HorizontalLayout hlControl = new HorizontalLayout();
	Button bearbeiten = new Button(IConstants.BUTTON_EDIT);
	Button neu = new Button(IConstants.BUTTON_NEW);
	Button btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);
	Button loeschen = new Button(IConstants.BUTTON_DELETE);
	FilterTable table = new FilterTable();
	private Label headline;

	BeanItemContainer<Regel> container;

	Regel regel;

	public RegelnAnzeigen() {

		this.setSizeFull();
		this.setMargin(true);

		try {
			container = new BeanItemContainer<Regel>(Regel.class, RegelDAO
					.getInstance().getAllRegeln());
		} catch (Exception e) {
			e.printStackTrace();
		}

		table.setContainerDataSource(container);
		table.setSizeFull();
		table.setStyleName("palaverTable");
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);
		
		table.setVisibleColumns(new Object[] { "zeile", "spalte", "regeltyp",
				"operator", "kriterien", "fehlermeldung", "aktiv" });
		
		table.setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(CustomTable source, Object itemId, Object propertyId) {
				Regel regel = (Regel) itemId;
				if ("aktiv".equals(propertyId)) {
					return regel.getAktiv() ? "check" : "cross";
				}
				return "";
			}
		});
		loeschen.setIcon(new ThemeResource(IConstants.BUTTON_DELETE_ICON));
		loeschen.setEnabled(false);
		bearbeiten.setEnabled(false);
		neu.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		bearbeiten.setIcon(new ThemeResource(IConstants.BUTTON_EDIT_ICON));

		neu.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(RegelAnlegen.class);

			}
		});

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					regel = (Regel) event.getProperty().getValue();
					bearbeiten.setEnabled(true);
					loeschen.setEnabled(true);
				} else {
					bearbeiten.setEnabled(false);
					loeschen.setEnabled(false);
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {

				if (event.isDoubleClick()) {
					bearbeiten.click();
				}

			}
		});

		bearbeiten.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (regel != null) {
					ViewHandler.getInstance().switchView(RegelAnlegen.class,
							new ViewDataObject<Regel>(regel));
				}

			}
		});

		loeschen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				Regel.loeschen(regel);
				ViewHandler.getInstance().switchView(RegelnAnzeigen.class);

			}
		});
		
		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});
		
		headline = new Label("Alle Regeln");
		headline.setStyleName("ViewHeadline");
		this.addComponent(headline);
		this.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);
		
		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		
		this.addComponent(hlControl);
		this.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(neu);
		hlControl.setComponentAlignment(neu, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(bearbeiten);
		hlControl.setComponentAlignment(bearbeiten, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(loeschen);
		hlControl.setComponentAlignment(loeschen, Alignment.MIDDLE_RIGHT);
		
	}

	@Override
	public void getViewParam(IViewData data) {
	}

}

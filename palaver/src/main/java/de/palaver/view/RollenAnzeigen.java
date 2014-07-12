package de.palaver.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.management.emploee.Rolle;
import de.palaver.management.employee.service.RolleService;

/**
 * 
 * @author Christian Barth
 * 
 */
@SuppressWarnings("serial")
public class RollenAnzeigen extends VerticalLayout implements View {
	
	private static final Logger	log	= LoggerFactory.getLogger(RollenAnzeigen.class.getName());

	private VerticalLayout layout = new VerticalLayout();
	private Table table;

	private TextField nameup = new TextField("Name");

	private Label headline;

	private Rolle rollenupdate;

	public RollenAnzeigen() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		layout.setWidth("40%");
		layout.setMargin(true);
		layout.setSpacing(true);
		table = new Table();
		table.setSizeFull();
		table.setSelectable(true);

		headline = new Label("Alle Rollen");
		headline.setStyleName("ViewHeadline");

		layout.addComponent(headline);
		layout.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					rollenupdate = (Rolle) event.getProperty().getValue();
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					final Window mengNeu = new Window();
					mengNeu.setClosable(false);
					mengNeu.setWidth("400px");
					mengNeu.setHeight("270px");
					mengNeu.setModal(true);
					mengNeu.center();
					mengNeu.setResizable(false);
					mengNeu.setCaption("Rolle hinzufügen");

					UI.getCurrent().addWindow(mengNeu);

					VerticalLayout layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button speichern = new Button(IConstants.BUTTON_SAVE);
					Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

					nameup.setWidth("100%");

					VerticalLayout feld = new VerticalLayout();

					feld.addComponent(nameup);

					HorizontalLayout control = new HorizontalLayout();
					control.setSpacing(true);
					control.addComponent(verwerfen);
					control.addComponent(speichern);
					speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
					verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

					layout.addComponent(feld);
					layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
					layout.addComponent(control);
					layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
					mengNeu.setContent(layout);

					nameup.setImmediate(true);
					nameup.setValue(rollenupdate.getName());
					nameup.setMaxLength(45);
					nameup.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 4, 45, false));

					verwerfen.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(RollenAnzeigen.class);
						}
					});

					speichern.addClickListener(new ClickListener() {
						public void buttonClick(ClickEvent event) {
							rollenupdate.setName(nameup.getValue());
							try {
								RolleService.getInstance().updateRolle(rollenupdate);
							} catch (Exception e) {
								log.error(e.toString());
							}
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(RollenAnzeigen.class);
						}
					});

				}

			}
		});

		layout.addComponent(table);
		layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);

		BeanItemContainer<Rolle> container;
		try {
			container = new BeanItemContainer<Rolle>(Rolle.class, RolleService.getInstance().getAllRolles());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub

	}

}

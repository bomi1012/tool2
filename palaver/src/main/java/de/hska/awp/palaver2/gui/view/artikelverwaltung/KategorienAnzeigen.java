package de.hska.awp.palaver2.gui.view.artikelverwaltung;

import java.util.List;

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

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author Mihail Boehm
 * 
 */
@SuppressWarnings("serial")
public class KategorienAnzeigen extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory
			.getLogger(KategorienAnzeigen.class.getName());

	private VerticalLayout layout = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	private Button hinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Button auswaehlen = new Button(IConstants.BUTTON_SELECT);
	private Button loeschen = new Button(IConstants.BUTTON_DELETE);
	private Table table;

	private TextField name = new TextField("Name");
	private TextField nameUp = new TextField("Name");
	private String nameText;

	private Label headline;

	private Kategorie kategorieUpdate;

	public KategorienAnzeigen() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		layout.setWidth("40%");
		layout.setMargin(true);
		layout.setSpacing(true);
		table = new Table();
		table.setSizeFull();
		table.setSelectable(true);

		headline = new Label("Alle Kategorien");
		headline.setStyleName("ViewHeadline");

		layout.addComponent(headline);
		layout.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					kategorieUpdate = (Kategorie) event.getProperty()
							.getValue();
				}
			}
		});

		loeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (kategorieUpdate != null) {
					try {
						List<Artikel> artikels= Artikelverwaltung.getInstance()
								.getActiveArtikelnByKategorieId(
										kategorieUpdate.getId());						
						if(artikels.size() == 0){
							Kategorienverwaltung.getInstance().deleteKategorie(kategorieUpdate.getId());
							((Application) UI.getCurrent().getData())
							.showDialog("Kategorie <" + kategorieUpdate.getName() + "> wurde gelöscht");
							ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
						} else {
							((Application) UI.getCurrent().getData())
							.showDialog("Die Kategorie <" + kategorieUpdate.getName() + "> darf nicht gelöscht werden, " +
									"weil sie an " + artikels.size() + " Artikeln " +
									"hängt.");							
						}						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		auswaehlen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (kategorieUpdate != null) {
					final Window kategNeu = new Window();
					kategNeu.setClosable(false);
					kategNeu.setWidth("400px");
					kategNeu.setHeight("270px");
					kategNeu.setModal(true);
					kategNeu.center();
					kategNeu.setResizable(false);
					kategNeu.setCaption("Kategorie bearbeiten");

					UI.getCurrent().addWindow(kategNeu);

					VerticalLayout layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button speichern = new Button(IConstants.BUTTON_SAVE);
					Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

					nameUp.setWidth("100%");

					VerticalLayout feld = new VerticalLayout();

					feld.addComponent(nameUp);

					HorizontalLayout control = new HorizontalLayout();
					control.setSpacing(true);
					control.addComponent(verwerfen);
					control.addComponent(speichern);
					speichern.setIcon(new ThemeResource(
							IConstants.BUTTON_SAVE_ICON));
					verwerfen.setIcon(new ThemeResource(
							IConstants.BUTTON_DISCARD_ICON));

					layout.addComponent(feld);
					layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
					layout.addComponent(control);
					layout.setComponentAlignment(control,
							Alignment.BOTTOM_RIGHT);
					kategNeu.setContent(layout);

					nameUp.setImmediate(true);
					nameUp.setValue(kategorieUpdate.getName());
					nameUp.setMaxLength(45);
					nameUp.addValidator(new StringLengthValidator(
							"Bitte gï¿½ltigen Namen eingeben", 4, 45, false));

					verwerfen.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(kategNeu);
							ViewHandler.getInstance().switchView(
									KategorienAnzeigen.class);
						}
					});

					speichern.addClickListener(new ClickListener() {
						public void buttonClick(ClickEvent event) {
							kategorieUpdate.setName(nameUp.getValue());
							try {
								Kategorienverwaltung.getInstance()
										.updateKategorie(kategorieUpdate);
							} catch (Exception e) {
								log.error(e.toString());
							}
							UI.getCurrent().removeWindow(kategNeu);
							ViewHandler.getInstance().switchView(
									KategorienAnzeigen.class);
						}
					});

					nameUp.addValueChangeListener(new ValueChangeListener() {

						public void valueChange(final ValueChangeEvent event) {
							final String valueString = String.valueOf(event
									.getProperty().getValue());

							nameText = valueString;
						}
					});
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_KATEGORIE_AUSWAEHLEN);
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					auswaehlen.click();
				}

			}
		});

		control.addComponent(auswaehlen);
		control.addComponent(hinzufuegen);
		control.addComponent(loeschen);
		layout.addComponent(table);
		layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		layout.addComponent(control);
		layout.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		hinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));

		BeanItemContainer<Kategorie> container;
		try {
			container = new BeanItemContainer<Kategorie>(Kategorie.class,
					Kategorienverwaltung.getInstance().getAllKategories());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

		hinzufuegen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Window kategNeu = new Window();
				kategNeu.setClosable(false);
				kategNeu.setWidth("400px");
				kategNeu.setHeight("270px");
				kategNeu.setModal(true);
				kategNeu.center();
				kategNeu.setResizable(false);
				kategNeu.setCaption("Kategorie hinzufÃ¼gen");

				UI.getCurrent().addWindow(kategNeu);

				VerticalLayout layout = new VerticalLayout();
				layout.setMargin(true);
				layout.setWidth("100%");
				layout.setSpacing(true);

				Button speichern = new Button(IConstants.BUTTON_SAVE);
				Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

				name.setWidth("100%");

				VerticalLayout feld = new VerticalLayout();

				feld.addComponent(name);

				HorizontalLayout control = new HorizontalLayout();
				control.setSpacing(true);
				control.addComponent(verwerfen);
				control.addComponent(speichern);
				speichern
						.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
				verwerfen.setIcon(new ThemeResource(
						IConstants.BUTTON_DISCARD_ICON));

				layout.addComponent(feld);
				layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
				layout.addComponent(control);
				layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
				kategNeu.setContent(layout);

				name.setImmediate(true);
				name.setMaxLength(45);
				name.addValidator(new StringLengthValidator(
						"Bitte gï¿½ltigen Namen eingeben", 4, 45, false));

				verwerfen.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().removeWindow(kategNeu);
						ViewHandler.getInstance().switchView(
								KategorienAnzeigen.class);
					}
				});

				speichern.addClickListener(new ClickListener() {
					public void buttonClick(ClickEvent event) {
						if (validiereKategorie()) {
							Kategorie me = new Kategorie();
							me.setName(name.getValue());
							String notification = "Kategorie gespeichert";
							try {
								Kategorienverwaltung.getInstance()
										.createKategorie(me);
								UI.getCurrent().removeWindow(kategNeu);
								((Application) UI.getCurrent().getData())
										.showDialog(notification);
								ViewHandler.getInstance().switchView(
										KategorienAnzeigen.class);
							} catch (Exception e) {
								if (e.toString().contains(
										"INSERT INTO kategorie"))
									notification = "Der Name ist bereits im System vorhanden!";
								else
									notification = e.toString();
								log.error(e.toString());
							}
						}
					}
				});
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {

	}

	public boolean validiereKategorie() {

		if (name.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIENAME);
			return false;
		} else {
			return true;
		}
	}
}

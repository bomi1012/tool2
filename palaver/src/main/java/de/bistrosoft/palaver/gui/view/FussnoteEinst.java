package de.bistrosoft.palaver.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;

/**
 * @author Michael Marschall
 * 
 */
@SuppressWarnings("serial")
public class FussnoteEinst extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory
			.getLogger(FussnoteEinst.class.getName());

	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vlDetails = new VerticalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btHinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btAuswaehlen = new Button(IConstants.BUTTON_EDIT);

	private FilterTable tblFussnote;

	private TextField tfBezeichnung = new TextField("Bezeichnung");
	private TextField tfAbkuerzung = new TextField("Abkuerzung");

	private Label lUeberschrift;

	private Fussnote fn = new Fussnote();
	private Window fnNeu;

	public FussnoteEinst() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(vl);
		this.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);

		vl.setWidth("25%");
		vl.setMargin(true);
		vl.setSpacing(true);
		tblFussnote = new FilterTable();
		tblFussnote.setSizeFull();
		tblFussnote.setSelectable(true);
		tblFussnote.setFilterBarVisible(true);

		lUeberschrift = new Label("Fussnote");
		lUeberschrift.setStyleName("ViewHeadline");

		vl.addComponent(lUeberschrift);
		vl.setComponentAlignment(lUeberschrift, Alignment.MIDDLE_LEFT);

		vl.addComponent(tblFussnote);
		vl.setComponentAlignment(tblFussnote, Alignment.MIDDLE_CENTER);
		vl.addComponent(hlControl);
		vl.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(btAuswaehlen);
		hlControl.addComponent(btHinzufuegen);
		btHinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));

		btHinzufuegen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				hinzufuegen();
			}

		});

		tblFussnote.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					fn = (Fussnote) event.getProperty().getValue();
				}
			}
		});
		
		tblFussnote.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					updateFussnote();
				}
			}
		});

		BeanItemContainer<Fussnote> ctFussnote;
		try {
			ctFussnote = new BeanItemContainer<Fussnote>(Fussnote.class,
					Fussnotenverwaltung.getInstance().getAllFussnote());
			tblFussnote.setContainerDataSource(ctFussnote);
			tblFussnote.setVisibleColumns(new Object[] { "bezeichnung",
					"abkuerzung" });
			tblFussnote.sort(new Object[] { "bezeichnung" },
					new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (tblFussnote.getValue() != null
						&& tblFussnote.getValue() instanceof Fussnote) {
					updateFussnote();
				} else
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_FUSSNOTE_SELECT);
			}
		});

	}

	private void hinzufuegen() {
		fnNeu = new Window();
		fnNeu.setClosable(false);
		fnNeu.setWidth("400px");
		fnNeu.setHeight("270px");
		fnNeu.setModal(true);
		fnNeu.center();
		fnNeu.setResizable(false);
		fnNeu.setCaption("Fussnote hinzufügen");

		UI.getCurrent().addWindow(fnNeu);

		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth("100%");
		vl.setSpacing(true);

		tfBezeichnung.setWidth("100%");
		tfAbkuerzung.setWidth("100%");

		tfBezeichnung.setRequired(true);
		tfAbkuerzung.setRequired(true);

		vlDetails = new VerticalLayout();
		vlDetails.addComponent(tfBezeichnung);
		vlDetails.addComponent(tfAbkuerzung);

		hlControl = new HorizontalLayout();
		hlControl.setSpacing(true);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btSpeichern);

		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		vl.addComponent(vlDetails);
		vl.setComponentAlignment(vlDetails, Alignment.MIDDLE_CENTER);
		vl.addComponent(hlControl);
		vl.setComponentAlignment(hlControl, Alignment.BOTTOM_RIGHT);
		fnNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 3, 20, false));

		tfAbkuerzung.setImmediate(true);
		tfAbkuerzung.addValidator(new StringLengthValidator(
				"Bitte gültige Abkürzung eingeben", 1, 5, false));

		btSpeichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
				}
			}
		});

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				abbrechen();
			}
		});
	}

	private void updateFussnote() {

		fnNeu = new Window();
		fnNeu.setClosable(false);
		fnNeu.setWidth("400px");
		fnNeu.setHeight("270px");
		fnNeu.setModal(true);
		fnNeu.center();
		fnNeu.setResizable(false);
		fnNeu.setCaption("Fussnote bearbeiten");

		UI.getCurrent().addWindow(fnNeu);

		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth("100%");
		vl.setSpacing(true);

		tfBezeichnung.setWidth("100%");
		tfAbkuerzung.setWidth("100%");

		vlDetails = new VerticalLayout();
		vlDetails.addComponent(tfBezeichnung);
		vlDetails.addComponent(tfAbkuerzung);

		hlControl = new HorizontalLayout();
		hlControl.setSpacing(true);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btUpdate);
		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		vl.addComponent(vlDetails);
		vl.setComponentAlignment(vlDetails, Alignment.MIDDLE_CENTER);
		vl.addComponent(hlControl);
		vl.setComponentAlignment(hlControl, Alignment.BOTTOM_RIGHT);
		fnNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.setValue(fn.getBezeichnung());
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 3, 50, false));

		tfAbkuerzung.setImmediate(true);
		tfAbkuerzung.setValue(fn.getAbkuerzung());
		tfAbkuerzung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 1, 5, false));

		btUpdate.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
				}
			}
		});

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				abbrechen();
			}
		});
	}

	private void speichern() {
		fn.setBezeichnung(tfBezeichnung.getValue());
		fn.setAbkuerzung(tfAbkuerzung.getValue());

		try {
			Fussnotenverwaltung.getInstance().createFussnote(fn);

		} catch (Exception e) {
			log.error(e.toString());
			if (e.toString().contains("INSERT INTO fussnote"))
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_VALID_BEZ_ABK_DOPPELT);
			return;

		}
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_FUSSNOTE_SAVE);
		zurueck();
	}

	private void update() {
		fn.setBezeichnung(tfBezeichnung.getValue());
		fn.setAbkuerzung(tfAbkuerzung.getValue());

		try {
			Fussnotenverwaltung.getInstance().updateFussnote(fn);
		} catch (Exception e) {
			log.error(e.toString());
			if (e.toString().contains("UPDATE fussnote SET"))
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_VALID_BEZ_ABK_DOPPELT);
			return;
		}

		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_FUSSNOTE_SAVE);
		zurueck();
	}

	private void zurueck() {
		UI.getCurrent().removeWindow(fnNeu);
		ViewHandler.getInstance().switchView(FussnoteEinst.class);
	}

	private void abbrechen() {
		UI.getCurrent().removeWindow(fnNeu);
		ViewHandler.getInstance().switchView(FussnoteEinst.class);
	}

	private Boolean validiereEingabe() {
		if (tfBezeichnung.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_VALID_BEZEICHNUNG);
			return false;
		}
		if (tfAbkuerzung.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_VALID_ABKUERZUNG);
			return false;
		}
		return true;
	}

	@Override
	public void getViewParam(ViewData data) {
	}
}
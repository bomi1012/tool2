package de.bistrosoft.palaver.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Michael Marschall Jan Lauinger - Geschmack hinzufügen
 * 
 */
@SuppressWarnings("serial")
public class GeschmackEinst extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory
			.getLogger(GeschmackEinst.class.getName());

	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vlDetails = new VerticalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btHinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btAuswaehlen = new Button(IConstants.BUTTON_SELECT);

	private FilterTable tblGeschmack;

	private TextField tfBezeichnung = new TextField("Bezeichnung");

	private Label lUeberschrift;

	private Geschmack geschmack = new Geschmack();
	private Window geschmackNeu;

	public GeschmackEinst() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(vl);

		vl.setWidth("60%");
		vl.setMargin(true);
		vl.setSpacing(true);
		tblGeschmack = new FilterTable();
		tblGeschmack.setSizeFull();
		tblGeschmack.setSelectable(true);
		tblGeschmack.setFilterBarVisible(true);

		lUeberschrift = new Label("Geschmack");
		lUeberschrift.setStyleName("ViewHeadline");

		vl.addComponent(lUeberschrift);
		vl.setComponentAlignment(lUeberschrift, Alignment.MIDDLE_LEFT);

		vl.addComponent(tblGeschmack);
		vl.setComponentAlignment(tblGeschmack, Alignment.MIDDLE_CENTER);
		vl.addComponent(btHinzufuegen);
		vl.setComponentAlignment(btHinzufuegen, Alignment.MIDDLE_RIGHT);
		btHinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		vl.addComponent(btAuswaehlen);
		vl.setComponentAlignment(btAuswaehlen, Alignment.MIDDLE_CENTER);

		btHinzufuegen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				hinzufuegen();
			}

		});

		tblGeschmack.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					geschmack = (Geschmack) event.getProperty().getValue();
				}
			}
		});

//		tblGeschmack.addItemClickListener(new ItemClickListener() {
//
//			@Override
//			public void itemClick(ItemClickEvent event) {
//				if (event.isDoubleClick()) {
//					updateGeschmack();
//				}
//			}
//		});

		BeanItemContainer<Geschmack> ctGeschmack;
		try {
			ctGeschmack = new BeanItemContainer<Geschmack>(Geschmack.class,
					Geschmackverwaltung.getInstance().getAllGeschmack());
			tblGeschmack.setContainerDataSource(ctGeschmack);
			tblGeschmack
					.setVisibleColumns(new Object[] { "id", "bezeichnung", });
			tblGeschmack.sort(new Object[] { "id" }, new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (tblGeschmack.getValue() != null
						&& tblGeschmack.getValue() instanceof Geschmack) {
					updateGeschmack();
				} else
					showNotification("Bitte Geschmack ausw�hlen!");
			}
		});

	}

	private void hinzufuegen() {
		geschmackNeu = new Window();
		geschmackNeu.setClosable(false);
		geschmackNeu.setWidth("400px");
		geschmackNeu.setHeight("270px");
		geschmackNeu.setModal(true);
		geschmackNeu.center();
		geschmackNeu.setResizable(false);
		geschmackNeu.setCaption("Geschmack hinzufügen");

		UI.getCurrent().addWindow(geschmackNeu);

		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth("100%");
		vl.setSpacing(true);

		tfBezeichnung.setWidth("100%");
		tfBezeichnung.setRequired(true);

		vlDetails = new VerticalLayout();
		vlDetails.addComponent(tfBezeichnung);

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
		geschmackNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 3, 25, false));

		btSpeichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
					zurueck();
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

	private void updateGeschmack() {
		geschmackNeu = new Window();
		geschmackNeu.setClosable(false);
		geschmackNeu.setWidth("400px");
		geschmackNeu.setHeight("270px");
		geschmackNeu.setModal(true);
		geschmackNeu.center();
		geschmackNeu.setResizable(false);
		geschmackNeu.setCaption("Geschmack bearbeiten");

		UI.getCurrent().addWindow(geschmackNeu);

		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth("100%");
		vl.setSpacing(true);

		tfBezeichnung.setWidth("100%");

		vlDetails = new VerticalLayout();
		vlDetails.addComponent(tfBezeichnung);

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
		geschmackNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.setValue(geschmack.getBezeichnung());
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte g�ltige Bezeichnung eingeben", 3, 50, false));

		btUpdate.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
					zurueck();
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
		geschmack.setBezeichnung(tfBezeichnung.getValue());

		try {
			Geschmackverwaltung.getInstance().createGeschmack(geschmack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		showNotification("Geschmack wurde gespeichert!");
	}

	private void update() {
		geschmack.setBezeichnung(tfBezeichnung.getValue());

		try {
			Geschmackverwaltung.getInstance().updateGeschmack(geschmack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		showNotification("Geschmack wurde ge�ndert!");
	}

	private void zurueck() {
		UI.getCurrent().removeWindow(geschmackNeu);
		ViewHandler.getInstance().switchView(GeschmackEinst.class);
	}

	private void abbrechen() {
		UI.getCurrent().removeWindow(geschmackNeu);
		ViewHandler.getInstance().switchView(GeschmackEinst.class);
	}

	private Boolean validiereEingabe() {
		if (tfBezeichnung.getValue().isEmpty()) {
			showNotification("Bitte Bezeichnung eingeben!");
			return false;
		}

		return true;
	}

	private void showNotification(String text) {
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}

	@Override
	public void getViewParam(ViewData data) {

	}
}
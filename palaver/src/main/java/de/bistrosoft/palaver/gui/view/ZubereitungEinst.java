package de.bistrosoft.palaver.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
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

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Michael Marschall
 * 
 */
@SuppressWarnings("serial")
public class ZubereitungEinst extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory
			.getLogger(ZubereitungEinst.class.getName());

	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vlDetails = new VerticalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btHinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btAuswaehlen = new Button(IConstants.BUTTON_EDIT);

	private FilterTable tblZubereitung;

	private TextField tfBezeichnung = new TextField("Bezeichnung");

	private Label lUeberschrift;

	private Zubereitung zub = new Zubereitung();
	private Window zubNeu;

	public ZubereitungEinst() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(vl);
		this.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);

		vl.setWidth("60%");
		vl.setMargin(true);
		vl.setSpacing(true);
		tblZubereitung = new FilterTable();
		tblZubereitung.setSizeFull();
		tblZubereitung.setSelectable(true);
		tblZubereitung.setFilterBarVisible(true);

		lUeberschrift = new Label("Zubereitung");
		lUeberschrift.setStyleName("ViewHeadline");

		vl.addComponent(lUeberschrift);
		vl.setComponentAlignment(lUeberschrift, Alignment.MIDDLE_LEFT);

		vl.addComponent(tblZubereitung);
		vl.setComponentAlignment(tblZubereitung, Alignment.MIDDLE_CENTER);
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

		tblZubereitung.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					zub = (Zubereitung) event.getProperty().getValue();
				}
			}
		});

		BeanItemContainer<Zubereitung> ctZubereitung;
		try {
			ctZubereitung = new BeanItemContainer<Zubereitung>(
					Zubereitung.class, Zubereitungverwaltung.getInstance()
							.getAllZubereitung());
			tblZubereitung.setContainerDataSource(ctZubereitung);
			tblZubereitung.setVisibleColumns(new Object[] { "bezeichnung", });
			tblZubereitung.sort(new Object[] { "bezeichnung" },
					new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (tblZubereitung.getValue() != null
						&& tblZubereitung.getValue() instanceof Zubereitung) {
					updateZubereitung();
				} else
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ZUBEREITUNG_SELECT);
			}
		});
	}

	private void hinzufuegen() {
		zubNeu = new Window();
		zubNeu.setClosable(false);
		zubNeu.setWidth("400px");
		zubNeu.setHeight("270px");
		zubNeu.setModal(true);
		zubNeu.center();
		zubNeu.setResizable(false);
		zubNeu.setCaption("Zubereitung hinzuf�gen");

		UI.getCurrent().addWindow(zubNeu);

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
		zubNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte g�ltige Bezeichnung eingeben", 3, 20, false));

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

	private void updateZubereitung() {

		zubNeu = new Window();
		zubNeu.setClosable(false);
		zubNeu.setWidth("400px");
		zubNeu.setHeight("270px");
		zubNeu.setModal(true);
		zubNeu.center();
		zubNeu.setResizable(false);
		zubNeu.setCaption("Zubereitung bearbeiten");

		UI.getCurrent().addWindow(zubNeu);

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
		zubNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.setValue(zub.getBezeichnung());
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
		zub.setBezeichnung(tfBezeichnung.getValue());

		try {
			Zubereitungverwaltung.getInstance().createZubereitung(zub);

		} catch (Exception e) {
			e.printStackTrace();
		}
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_ZUBEREITUNG_SAVE);
	}

	private void update() {
		zub.setBezeichnung(tfBezeichnung.getValue());

		try {
			Zubereitungverwaltung.getInstance().updateZubereitung(zub);
		} catch (Exception e) {
			e.printStackTrace();
		}
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_ZUBEREITUNG_EDIT);
	}

	private void zurueck() {
		UI.getCurrent().removeWindow(zubNeu);
		ViewHandler.getInstance().switchView(ZubereitungEinst.class);
	}

	private void abbrechen() {
		UI.getCurrent().removeWindow(zubNeu);
		ViewHandler.getInstance().switchView(ZubereitungEinst.class);
	}

	private Boolean validiereEingabe() {
		if (tfBezeichnung.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_VALID_BEZEICHNUNG);
			return false;
		}
		return true;
	}

	@Override
	public void getViewParam(ViewData data) {
	}
}
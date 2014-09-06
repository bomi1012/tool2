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

import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.menu.Menutype;

/**
 * @author Michael Marschall
 * 
 */
@SuppressWarnings("serial")
public class MenueartEinst extends VerticalLayout implements View {

	private static final Logger log = LoggerFactory
			.getLogger(MenueartEinst.class.getName());

	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vlDetails = new VerticalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btHinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btAuswaehlen = new Button(IConstants.BUTTON_EDIT);

	private FilterTable tblMenueart;

	private TextField tfBezeichnung = new TextField("Bezeichnung");

	private Label lUeberschrift;

	private Menutype ma = new Menutype();
	private Window maNeu;

	public MenueartEinst() {
		super();

		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(vl);
		this.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);

		vl.setWidth("25%");
		vl.setMargin(true);
		vl.setSpacing(true);
		tblMenueart = new FilterTable();
		tblMenueart.setSizeFull();
		tblMenueart.setSelectable(true);
		tblMenueart.setFilterBarVisible(true);

		lUeberschrift = new Label("Menüart");
		lUeberschrift.setStyleName("ViewHeadline");

		vl.addComponent(lUeberschrift);
		vl.setComponentAlignment(lUeberschrift, Alignment.MIDDLE_LEFT);

		vl.addComponent(tblMenueart);
		vl.setComponentAlignment(tblMenueart, Alignment.MIDDLE_CENTER);
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

		tblMenueart.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					ma = (Menutype) event.getProperty().getValue();
				}
			}
		});
		
		tblMenueart.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					updateMenueart();
				}
			}
		});

		BeanItemContainer<Menutype> ctMenueart;
		try {
			ctMenueart = new BeanItemContainer<Menutype>(Menutype.class,
					Menueartverwaltung.getInstance().getAllMenutypes());
			tblMenueart.setContainerDataSource(ctMenueart);
			tblMenueart.setVisibleColumns(new Object[] { "bezeichnung", });
			tblMenueart.sort(new Object[] { "bezeichnung" },
					new boolean[] { true });
		} catch (Exception e) {
			log.error(e.toString());
		}

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (tblMenueart.getValue() != null
						&& tblMenueart.getValue() instanceof Menutype) {
					updateMenueart();
				} else
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_MENUEART_SELECT);

			}
		});

	}

	private void hinzufuegen() {
		maNeu = new Window();
		maNeu.setClosable(false);
		maNeu.setWidth("400px");
		maNeu.setHeight("270px");
		maNeu.setModal(true);
		maNeu.center();
		maNeu.setResizable(false);
		maNeu.setCaption("Menüart hinzufügen");

		UI.getCurrent().addWindow(maNeu);

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
		maNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 3, 20, false));

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

	private void updateMenueart() {

		maNeu = new Window();
		maNeu.setClosable(false);
		maNeu.setWidth("400px");
		maNeu.setHeight("270px");
		maNeu.setModal(true);
		maNeu.center();
		maNeu.setResizable(false);
		maNeu.setCaption("Zubereitung bearbeiten");

		UI.getCurrent().addWindow(maNeu);

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
		maNeu.setContent(vl);

		tfBezeichnung.setImmediate(true);
		tfBezeichnung.setValue(ma.getName());
		tfBezeichnung.addValidator(new StringLengthValidator(
				"Bitte gültige Bezeichnung eingeben", 3, 50, false));

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
		ma.setName(tfBezeichnung.getValue());

		try {
			Menueartverwaltung.getInstance().createMenueart(ma);

		} catch (Exception e) {
			log.error(e.toString());
			if (e.toString().contains("INSERT INTO menueart"))
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_VALID_BEZ_DOPPELT);
			return;
		}
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_MENUEART_SAVE);
		zurueck();

	}

	private void update() {
		ma.setName(tfBezeichnung.getValue());

		try {
			Menueartverwaltung.getInstance().updateMenueart(ma);
		} catch (Exception e) {
			log.error(e.toString());
			if (e.toString().contains("UPDATE menueart SET"))
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_VALID_BEZ_DOPPELT);
			return;
		}
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_MENUEART_EDIT);
		zurueck();

	}

	private void zurueck() {
		UI.getCurrent().removeWindow(maNeu);
		ViewHandler.getInstance().switchView(MenueartEinst.class);
	}

	private void abbrechen() {
		UI.getCurrent().removeWindow(maNeu);
		ViewHandler.getInstance().switchView(MenueartEinst.class);
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
	public void getViewParam(IViewData data) {
	}
}
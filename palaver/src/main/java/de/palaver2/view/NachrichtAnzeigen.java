package de.palaver2.view;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Rollenverwaltung;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.nachrichtenverwaltung.service.Nachrichtenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class NachrichtAnzeigen extends VerticalLayout implements View, ValueChangeListener {

	private static final Logger log = LoggerFactory.getLogger(NachrichtAnzeigen.class.getName());

	private HorizontalLayout horizontallayout = new HorizontalLayout();
	private VerticalLayout nachrichtanzeigenlayout = new VerticalLayout();
	private VerticalLayout nachrichterstellenlayout = new VerticalLayout();

	private VerticalLayout nachrichtverticallayout = new VerticalLayout();
	HorizontalLayout nachrichterstellenlayoutbuttons = new HorizontalLayout();

	private Label von;
	private TextArea nachrichtentext;
	private TextArea neuernachrichtentext;
	private ComboBox combobox = new ComboBox();
	Button speichern = new Button(IConstants.BUTTON_SEND);

	private List<Nachricht> nl = new ArrayList<Nachricht>();
	private Nachricht nachricht = new Nachricht();

	private String neuernachrichtentextinput;

	Mitarbeiter m = null;

	private int NACHRICHT_MAXLENGTH = 300;

	public NachrichtAnzeigen() {

		super();

		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(horizontallayout);

		horizontallayout.setSizeFull();
		horizontallayout.setHeight("90%");
		horizontallayout.setSpacing(true);

		nachrichtanzeigenlayout.setWidth("300px");
		nachrichtanzeigenlayout.setSpacing(true);
		nachrichterstellenlayout.setWidth("300px");
		nachrichterstellenlayout.setSpacing(true);

		Panel panel = new Panel("Nachrichten");
		panel.setWidth("450px");
		panel.setHeight("100%");

		final VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setWidth(400, Unit.PIXELS);
		contentLayout.setMargin(true);

		horizontallayout.addComponent(panel);
		horizontallayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		horizontallayout.addComponent(nachrichterstellenlayout);
		horizontallayout.setComponentAlignment(nachrichterstellenlayout, Alignment.MIDDLE_LEFT);

		// Nachrichtlayout zusammenbauen
		try {

			Mitarbeiter m2 = ((Application) UI.getCurrent().getData()).getUser();
			m = Mitarbeiterverwaltung.getInstance().getMitarbeiterById(m2.getId());
			List<Rollen> rlist = m.getRollen();
			if (rlist != null) {
				for (int i = 0; i < rlist.size(); i++) {
					if (rlist.get(i).getNachrichten() != null) {
						for (int z = 0; z < rlist.get(i).getNachrichten().size(); z++) {
							nl.add(rlist.get(i).getNachrichten().get(z));
						}
					}
				}

			}

		} catch (Exception e) {
			log.error(e.toString());
		}

		if (nl != null) {

			// Sortieren der Nachrichten nach der größten ID
			final List<Nachricht> neu = new ArrayList<Nachricht>();
			if (nl != null) {
				for (int z = 0; z < nl.size(); z++) {
					long zahl = 0;
					int ind = 0;
					for (int i = 0; i < nl.size(); i++) {
						if (zahl < nl.get(i).getId()) {
							zahl = nl.get(i).getId();
							ind = i;
						}
					}

					neu.add(nl.get(ind));
					nl.remove(ind);
					z = z - 1;
				}
			}

			nl = neu;

			for (int i = 0; i < nl.size(); i++) {

				von = new Label("Von:");
				von.setWidth("100%");
				von.setValue("Von: " + nl.get(i).getMitarbeiterBySenderFk().getBenutzername());

				final Button loeschbutton = new Button();
				loeschbutton.setIcon(new ThemeResource(IConstants.ICON_DELETE));

				loeschbutton.setId(String.valueOf(nl.get(i).getId()));

				nachrichtentext = new TextArea("");
				nachrichtentext.setWidth("100%");
				nachrichtentext.setRows(4);
				nachrichtentext.setValue(nl.get(i).getNachricht());
				nachrichtentext.setReadOnly(true);

				nachrichtverticallayout = new VerticalLayout();
				nachrichtverticallayout.setStyleName("nachricht");
				nachrichtverticallayout.addComponent(von);
				nachrichtverticallayout.addComponent(nachrichtentext);
				nachrichtverticallayout.addComponent(loeschbutton);
				nachrichtverticallayout.setComponentAlignment(loeschbutton, Alignment.TOP_RIGHT);

				contentLayout.addComponent(nachrichtverticallayout);
				panel.setContent(contentLayout);

				loeschbutton.addClickListener(new ClickListener() {
					public void buttonClick(ClickEvent event) {
						try {
							Nachrichtenverwaltung.getInstance().deleteNachricht(Long.valueOf(loeschbutton.getId()));
						} catch (Exception e) {
							log.error(e.toString());
						}

						ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
					}
				});
			}
		}

		// RECHTE SEITE

		Label label = new Label("Neue Nachricht an:");

		nachrichterstellenlayout.addComponent(label);

		combobox.setWidth("60%");
		combobox.setImmediate(true);
		combobox.setNullSelectionAllowed(false);
		combobox.setRequired(true);
		try {
			List<Rollen> rollen = Rollenverwaltung.getInstance().getAllRollen();
			for (Rollen i : rollen) {
				combobox.addItem(i);
			}
		} catch (Exception e) {
			log.error(e.toString());
		}

		nachrichterstellenlayout.addComponent(combobox);

		neuernachrichtentext = new TextArea();
		neuernachrichtentext.setWidth("100%");
		neuernachrichtentext.setRows(4);
		neuernachrichtentext.setImmediate(true);
		neuernachrichtentext.setInputPrompt(neuernachrichtentextinput);
		neuernachrichtentext.setMaxLength(NACHRICHT_MAXLENGTH);
		neuernachrichtentext.setRequired(true);

		nachrichterstellenlayout.addComponent(neuernachrichtentext);

		neuernachrichtentext.addValueChangeListener(this);

		Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		nachrichterstellenlayoutbuttons.addComponent(verwerfen);
		nachrichterstellenlayoutbuttons.addComponent(speichern);

		nachrichterstellenlayout.addComponent(nachrichterstellenlayoutbuttons);

		speichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				if (validiereNachricht()) {
					
					nachricht.setNachricht(neuernachrichtentext.getValue());
	
					nachricht.setEmpfaengerRolle((Rollen) combobox.getValue());
	
					try {
						nachricht.setMitarbeiterBySenderFk(((Application) UI.getCurrent().getData()).getUser());
						Nachrichtenverwaltung.getInstance().createNachricht(nachricht);
						Notification notification = new Notification("Die Nachricht wurde gesendet!");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					} catch (Exception e) {
						log.error(e.toString());
					}
					ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
				}
			}
		});

		verwerfen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
			}
		});

	}

	private boolean validiereNachricht() {
		if (combobox.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_NACHRICHT_EMPF);
			return false;
		}
		if (neuernachrichtentext.getValue() == null || neuernachrichtentext.getValue() == "") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_NACHRICHT_TEXT);
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void getViewParam(ViewData data) {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}
}

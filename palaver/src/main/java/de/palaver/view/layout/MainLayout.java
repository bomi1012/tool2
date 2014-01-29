package de.palaver.view.layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.gui.view.FussnoteEinst;
import de.bistrosoft.palaver.gui.view.GeschmackEinst;
import de.bistrosoft.palaver.gui.view.KuchenplanAnzeigen;
import de.bistrosoft.palaver.gui.view.KuchenplanHistorie;
import de.bistrosoft.palaver.gui.view.KuchenrezeptAnlegen;
import de.bistrosoft.palaver.gui.view.KuchenrezeptAnzeigen;
import de.bistrosoft.palaver.gui.view.MenueAnlegen;
import de.bistrosoft.palaver.gui.view.MenueAnzeigenTabelle;
import de.bistrosoft.palaver.gui.view.MenueartEinst;
import de.bistrosoft.palaver.gui.view.MenueplanAnzeigen;
import de.bistrosoft.palaver.gui.view.RegelnAnzeigen;
import de.bistrosoft.palaver.gui.view.RezeptAnlegen;
import de.bistrosoft.palaver.gui.view.RezeptAnzeigenTabelle;
import de.bistrosoft.palaver.gui.view.ZubereitungEinst;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.view.EmailOhneBestellung;
import de.palaver.view.MitarbeiterAnzeigen;
import de.palaver.view.MitarbeiterErstellen;
import de.palaver.view.NachrichtAnzeigen;
import de.palaver.view.RollenAnzeigen;
import de.palaver.view.artikelverwaltung.ArtikelAnzeigen;
import de.palaver.view.artikelverwaltung.ArtikelErstellen;
import de.palaver.view.artikelverwaltung.KategorieErstellen;
import de.palaver.view.artikelverwaltung.KategorienAnzeigen;
import de.palaver.view.artikelverwaltung.LagerortErstellen;
import de.palaver.view.artikelverwaltung.LagerorteAnzeigen;
import de.palaver.view.artikelverwaltung.MengeneinheitErstellen;
import de.palaver.view.artikelverwaltung.MengeneinheitenAnzeigen;
import de.palaver.view.bestellverwaltung.BestellungenAnzeigenTable;
import de.palaver.view.bestellverwaltung.GrundbedarfGenerierenAnsicht;
import de.palaver.view.lieferantenverwaltung.LieferantAnzeigenTable;
import de.palaver.view.lieferantenverwaltung.LieferantErstellen;

@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements Command {
	private HorizontalLayout header = new HorizontalLayout();
	private static final Logger log = LoggerFactory.getLogger(MainLayout.class
			.getName());

	private MenuBar menu = new MenuBar();

	@SuppressWarnings({ "deprecation", "unused" })
	public MainLayout() {
		super();

		this.setSizeFull();

		header.setWidth("100%");
		header.setHeight("100px");
		header.setStyleName("palaver-header");

		// Image logo = new Image(null, new
		// ThemeResource("../img/cafe_palaver_Logo.png"));
		// header.addComponent(logo);
		// header.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
		// logo.addClickListener(new ClickListener()
		// {
		// @Override
		// public void click(ClickEvent event)
		// {
		// ViewHandler.getInstance().switchView(DefaultView.class);
		// }
		// });

		header.addListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				ViewHandler.getInstance().switchView(DefaultView.class);
			}
		});

		this.addComponent(header);

		menu.setWidth("100%");
		
		/** Artikel */
		MenuItem artikelItem = menu.addItem(IConstants.MENU_ARTIKEL_HEADLINE, null);
		/** 1 Level */
		MenuItem artikelAnzeigenItem = artikelItem.addItem(IConstants.MENU_ARTIKEL_ANZEIGEN, this);
		MenuItem artikelAnlegenItem = artikelItem.addItem(IConstants.MENU_ARTIKEL_NEU, this);
		artikelItem.addSeparator();
		MenuItem kategorieItem = artikelItem.addItem(IConstants.MENU_KATEGORIE_HEADLINE, null);
		MenuItem mengeneinheitItem = artikelItem.addItem(IConstants.MENU_MENGENEINHEIT_HEADLINE, null);
		MenuItem lagerortItem = artikelItem.addItem(IConstants.MENU_LAGERORT_HEADLINE, null);		
		/** 2 Level */		
		MenuItem kategorieAnzeigenItem = kategorieItem.addItem(IConstants.MENU_KATEGORIE_ANZEIGEN, this);
		MenuItem kategorieAnlegenItem = kategorieItem.addItem(IConstants.MENU_KATEGORIE_NEU, this);		
		MenuItem mengeneinheitAnzeigenItem = mengeneinheitItem.addItem(IConstants.MENU_MENGENEINHEIT_ANZEIGEN, this);
		MenuItem mengeneinheitAnlegenItem = mengeneinheitItem.addItem(IConstants.MENU_MENGENEINHEIT_NEU, this);		
		MenuItem lagerortAnzeigenItem = lagerortItem.addItem(IConstants.MENU_LAGERORT_ANZEIGEN, this);
		MenuItem lagerortAnlegenItem = lagerortItem.addItem(IConstants.MENU_LAGERORT_NEU, this);		
		/** Icons */
		artikelAnzeigenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
		artikelAnlegenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));		
		kategorieItem.setIcon(new ThemeResource(IConstants.ICON_FOLDER_PAGE_WHITE));
		kategorieAnzeigenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
		kategorieAnlegenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));		
		mengeneinheitItem.setIcon(new ThemeResource(IConstants.ICON_FOLDER_PAGE_WHITE));
		mengeneinheitAnzeigenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
		mengeneinheitAnlegenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));		
		lagerortItem.setIcon(new ThemeResource(IConstants.ICON_FOLDER_PAGE_WHITE));
		lagerortAnzeigenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
		lagerortAnlegenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));
		
		/** Bestellung */
		if (((Application) UI.getCurrent().getData()).userHasPersmission(Rollen.ADMINISTRATOR)
				|| ((Application) UI.getCurrent().getData()).userHasPersmission(Rollen.BESTELLER)) {
			/** Bestellung */
			MenuItem bestellungItem = menu.addItem(IConstants.MENU_BESTELLUNG_HEADLINE, null);
			/** 1 Level */
			MenuItem bestellungeAnzeigen = bestellungItem.addItem(IConstants.MENU_BESTELLUNG_ANZEIGEN, this);
			MenuItem bestellungWählenItem = bestellungItem.addItem(IConstants.MENU_BESTELLUNG_GENERIEREN, null);
			bestellungItem.addSeparator();
			MenuItem lieferantItem = bestellungItem.addItem(IConstants.MENU_LIEFERANT_HEADLINE, null);
			
			/** 2 Level */
			MenuItem grundbedarfGenerierenItem = bestellungWählenItem.addItem(IConstants.MENU_GRUNDBEDARF, this);
			MenuItem lieferantAnzeigen = lieferantItem.addItem(IConstants.MENU_LIEFERANT_ANZEIGEN, this);
			MenuItem lieferantAnlegen = lieferantItem.addItem(IConstants.MENU_LIEFERANT_NEW, this);
			
			/** Icons */
			bestellungWählenItem.setIcon(new ThemeResource(IConstants.ICON_FOLDER_PAGE_WHITE));
			bestellungeAnzeigen.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
			grundbedarfGenerierenItem.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));
			lieferantItem.setIcon(new ThemeResource(IConstants.ICON_FOLDER_PAGE_WHITE));
			lieferantAnzeigen.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_LUPE));
			lieferantAnlegen.setIcon(new ThemeResource(IConstants.ICON_PAGE_WHITE_ADD));
			
//			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_NEW_RANDOM, this);
//			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_GENERATE, this);
//		
//		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_BEARBEITEN, this);
//		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_ANZEIGEN, this);
		}
		
		
		MenuItem mitarbeiterItem = menu.addItem(
				IConstants.MENU_MITARBEITER_HEADLINE, null);
		if (((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_NEU, this);
		}
		mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_ANZEIGEN, this);

		MenuItem rezeptItem = menu.addItem(IConstants.MENU_REZEPT_HEADLINE,
				null);
		rezeptItem.addItem(IConstants.MENU_REZEPT_NEU, this);
		rezeptItem.addItem(IConstants.MENU_REZEPT_ANZEIGEN, this);
		MenuItem menue1Item = menu
				.addItem(IConstants.MENU_MENUE_HEADLINE, null);
		menue1Item.addItem(IConstants.MENU_MENUE_ANLEGEN, this);
		menue1Item.addItem(IConstants.MENU_MENUE_SUCHEN, this);

		MenuItem menuplanItem = menu.addItem(IConstants.MENU_MENUPLAN_HEADLINE,
				this);

		MenuItem kuchenverwaltungItem = menu.addItem(
				IConstants.MENU_KUCHENVERWALTUNG_HEADLINE, null);
		kuchenverwaltungItem
				.addItem(IConstants.MENU_KUCHENREZEPT_ANLEGEN, this);
		kuchenverwaltungItem.addItem(IConstants.MENU_KUCHENREZEPT_ANZEIGEN,
				this);
		kuchenverwaltungItem.addItem(IConstants.MENU_KUCHENPLAN_AKTUELL, this);

//		MenuItem bestellungItem = menu.addItem(
//				IConstants.MENU_BESTELLUNG_HEADLINE, null);
//		if (((Application) UI.getCurrent().getData())
//				.userHasPersmission(Rollen.ADMINISTRATOR)
//				|| ((Application) UI.getCurrent().getData())
//						.userHasPersmission(Rollen.BESTELLER)) {
//			
//			bestellungItem.addItem("Grundbedarf generieren", this);
//			
//			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_NEW_RANDOM, this);
//			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_GENERATE, this);
//		}
//		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_BEARBEITEN, this);
//		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_ANZEIGEN, this);

		MenuItem einstellungItem = menu.addItem(
				IConstants.MENU_EINSTELLUNGEN_HEADLINE, null);
		einstellungItem.addItem(IConstants.MENU_HEADER, this);
		if (((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			einstellungItem.addItem(IConstants.MENU_REGEL, this);
		}
		einstellungItem.addItem(IConstants.MENU_FUSSNOTE, this);
		einstellungItem.addItem(IConstants.MENU_GESCHMACK, this);
		einstellungItem.addItem(IConstants.MENU_MENUEART, this);
		einstellungItem.addItem(IConstants.MENU_ZUBEREITUNG, this);
		einstellungItem.addItem("Email", this);
		einstellungItem.addItem("Nachrichten", this);
		einstellungItem.addItem(IConstants.MENU_MENUPLAN_HISTORIE, this);
		einstellungItem.addItem(IConstants.MENU_KUCHENPLAN_HISTORIE, this);
		// einstellungItem.addItem(IConstants.MENU_INFO, this);
		this.addComponent(menu);

		MenuItem logout = menu.addItem(IConstants.MENU_LOGOUT, this);
		MenuItem username = menu.addItem(getUser(), null);
		username.setEnabled(false);

		// DefaultView content = new DefaultView();
		//TODO:
		NachrichtAnzeigen content = new NachrichtAnzeigen();
		this.addComponent(content);
		this.setExpandRatio(content, 1);

		// this.addComponent(new ArtikelErstellen());
		// this.setExpandRatio(this.getComponent(2), 1);

		if (UI.getCurrent().getSession().getBrowser().isTouchDevice()) {
			setHeaderVisible(false);
		}
	}

	@Override
	public void menuSelected(final MenuItem selectedItem) {
		if (((Application) UI.getCurrent().getData()).getCahnge() == false) {
			if (selectedItem.getText().equals(IConstants.MENU_ARTIKEL_NEU)) {
				ViewHandler.getInstance().switchView(ArtikelErstellen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_ARTIKEL_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_MENGENEINHEIT_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_KATEGORIE_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_LAGERORT_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(LagerorteAnzeigen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_MENGENEINHEIT_NEU)) {
				ViewHandler.getInstance().switchView(MengeneinheitErstellen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_KATEGORIE_NEU)) {
				ViewHandler.getInstance().switchView(KategorieErstellen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_LAGERORT_NEU)) {
				ViewHandler.getInstance().switchView(LagerortErstellen.class);
			} else if(selectedItem.getText().equals(IConstants.MENU_BESTELLUNG_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(BestellungenAnzeigenTable.class);
			} else if(selectedItem.getText().equals(IConstants.MENU_GRUNDBEDARF)) {
				ViewHandler.getInstance().switchView(GrundbedarfGenerierenAnsicht.class);
			} else if(selectedItem.getText().equals(IConstants.MENU_LIEFERANT_NEW)) {
				ViewHandler.getInstance().switchView(LieferantErstellen.class);
			}
			
			
			
			else if (selectedItem.getText().equals(
					IConstants.MENU_LIEFERANT_NEW)) {
				//ViewHandler.getInstance().switchView(LieferantErstellen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MITARBEITER_NEU)) {
				ViewHandler.getInstance()
						.switchView(MitarbeiterErstellen.class);
			}  else if (selectedItem.getText().equals(
					IConstants.MENU_LIEFERANT_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(LieferantAnzeigenTable.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MITARBEITER_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);
			}   else if (selectedItem.getText().equals(IConstants.MENU_LOGOUT)) {
				UI.getCurrent().setContent(new LoginForm());
				UI.getCurrent().getSession().close();
				UI.getCurrent().close();
				log.info("**************************************************************");
				log.info("LOGOUT");
				log.info("**************************************************************");
			} 
//			else if (selectedItem.getText().equals(
//					IConstants.MENU_BESTELLUNG_NEW_RANDOM)) {
//				ViewHandler.getInstance().switchView(
//						BestellungLieferantAuswaehlen.class);
//			} else if (selectedItem.getText().equals(
//					IConstants.MENU_BESTELLUNG_BEARBEITEN)) {
//				ViewHandler.getInstance().switchView(
//						BestellungBearbeitenAuswaehlen.class);
//			} else if (selectedItem.getText().equals(
//					IConstants.MENU_BESTELLUNG_ANZEIGEN)) {
//				ViewHandler.getInstance().switchView(BestellungAnzeigen.class);
//			} 
			else if (selectedItem.getText().equals(
					IConstants.MENU_REZEPT_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(
						RezeptAnzeigenTabelle.class);
			} else if (selectedItem.getText()
					.equals(IConstants.MENU_REZEPT_NEU)) {
				ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MENUPLAN_HEADLINE)) {
				ViewHandler.getInstance().switchView(MenueplanAnzeigen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MENUPLAN_HISTORIE)) {
				ViewHandler.getInstance().switchView(MenueplanAnzeigen.class);
				// ViewHandler.getInstance().switchView(MenueplanHistorie.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_KUCHENPLAN_HISTORIE)) {
				ViewHandler.getInstance().switchView(KuchenplanHistorie.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_KUCHENREZEPT_ANLEGEN)) {
				ViewHandler.getInstance().switchView(KuchenrezeptAnlegen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_KUCHENREZEPT_ANZEIGEN)) {
				ViewHandler.getInstance()
						.switchView(KuchenrezeptAnzeigen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_KUCHENPLAN_AKTUELL)) {
				ViewHandler.getInstance().switchView(KuchenplanAnzeigen.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_FUSSNOTE)) {
				ViewHandler.getInstance().switchView(FussnoteEinst.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_GESCHMACK)) {
				ViewHandler.getInstance().switchView(GeschmackEinst.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_MENUEART)) {
				ViewHandler.getInstance().switchView(MenueartEinst.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_ZUBEREITUNG)) {
				ViewHandler.getInstance().switchView(ZubereitungEinst.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MENUE_ANLEGEN)) {
				ViewHandler.getInstance().switchView(MenueAnlegen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_MENUE_SUCHEN)) {
				ViewHandler.getInstance()
						.switchView(MenueAnzeigenTabelle.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_REGEL)) {
				ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
			} else if (selectedItem.getText().equals("Email")) // Temp
			{
				ViewHandler.getInstance().switchView(EmailOhneBestellung.class);
			} else if (selectedItem.getText().equals("Nachrichten")) {
				ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_ROLLEN_ANZEIGEN)) {
				ViewHandler.getInstance().switchView(RollenAnzeigen.class);
			} else if (selectedItem.getText().equals(
					IConstants.MENU_BESTELLUNG_GENERATE)) {
//				ViewHandler.getInstance()
//						.switchView(BestellungGenerieren.class);
			} else if (selectedItem.getText().equals(IConstants.MENU_HEADER)) {
				setHeaderVisible(!this.header.isVisible());
			} 
			// else if (selectedItem.getText().equals(IConstants.MENU_INFO))
			// {
			// showInfo();
			// }
			else {
				ViewHandler.getInstance().returnToDefault();
			}
		}
		else {
			Label text = new Label("Speichern vergessen?");
			Button mpVerwerfen = new Button("Nein");
			Button mpSpeichern = new Button("Ja");
			
			this.setMargin(true);
			
			final Window win = new Window("Warnung");
			win.setModal(true);
			win.setWidth("350px");
			win.setHeight("150px");
			win.center();
			win.setResizable(false);
			
			
			VerticalLayout box = new VerticalLayout();
			VerticalLayout frame = new VerticalLayout();

			frame.setSizeFull();

			box.setSpacing(true);
			box.addComponent(text);
			box.setMargin(new MarginInfo(true, true, true, true));
			win.setContent(frame);
			UI.getCurrent().addWindow(win);
			frame.addComponent(box);
			frame.setComponentAlignment(box, Alignment.TOP_LEFT);

			HorizontalLayout control = new HorizontalLayout();
			control.setSpacing(true);
			box.setWidth("315px");
			box.addComponent(control);
			box.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
			control.addComponent(mpVerwerfen);
			control.addComponent(mpSpeichern);

			mpVerwerfen.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					((Application)UI.getCurrent().getData()).setChange(false);
					UI.getCurrent().removeWindow(win);
					menuSelected(selectedItem);
				}
			});
			mpSpeichern.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().removeWindow(win);
				}
			});
			
			//mpSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
			//meVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		}
	}

	private String getUser() {
		return "Benutzer : "
				+ ((Application) UI.getCurrent().getData()).getUser()
						.getBenutzername();
	}

	private void setHeaderVisible(Boolean arg0) {
		this.header.setVisible(arg0);
	}

	@SuppressWarnings("unused")
	private void showInfo() {
		final Window win = new Window();
		win.center();
		win.setModal(true);
		win.setWidth("410px");
		win.setHeight("400px");
		win.setClosable(false);
		win.setStyleName("dialog-window");
		win.setResizable(false);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		content.setSpacing(true);

		Embedded logo = new Embedded(null, new ThemeResource("img/hska.png"));
		content.addComponent(logo);

		Label text = new Label(
				"PalaverApp /n (c) 2013 Hochschule Karlsruhe /n Build: 07-06-2013-18-48");
		content.addComponent(text);

		Button close = new Button(IConstants.BUTTON_OK);
		close.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);
			}
		});
		content.addComponent(close);
		content.setComponentAlignment(close, Alignment.BOTTOM_RIGHT);

		win.setContent(content);
		UI.getCurrent().addWindow(win);
	}
}

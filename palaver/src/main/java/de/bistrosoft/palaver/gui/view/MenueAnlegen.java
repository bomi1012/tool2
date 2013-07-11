package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Jasmin Baumgartner, Eike Becher, Michael Marschall
 * 
 */
 
@SuppressWarnings("serial")
public class MenueAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	//Layouts
	private VerticalLayout vlBox = new VerticalLayout();
	private VerticalLayout vlDetailsLinks = new VerticalLayout();
	private VerticalLayout vlDetailsRechts = new VerticalLayout();
	private VerticalLayout vlDetailsLinksOben = new VerticalLayout();
	private VerticalLayout vlDetailsLinksUnten = new VerticalLayout();
	
	private HorizontalLayout hlRezepte = new HorizontalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();
	private HorizontalLayout hlDetails = new HorizontalLayout();
	
	private Label dummy = new Label(
			"<pre><b><font size='1' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"><br></font><b></pre>",
			ContentMode.HTML);
	
	
	private TextField tfMenuename = new TextField("Men�name");

	private TwinColTouch tcsFussnoten = new TwinColTouch("Fu�noten");

	private NativeSelect nsKoch = new NativeSelect("Koch");
	private NativeSelect nsMenueart = new NativeSelect("Men�art");
	private NativeSelect nsGeschmack = new NativeSelect("Geschmack");

	private CheckBox chbFavorit = new CheckBox("Men� ist ein Favorit");
	private CheckBox chbAufwand = new CheckBox("Men� hat hohen Aufwand");

	private Button btSpeichern = new Button("Speichern");
	private Button btVerwerfen = new Button("Verwerfen");
	private Button btUpdate = new Button("�ndern");
	private Button btNeuesRezept = new Button("Neues Rezept");

	private FilterTable tblMenueRezepte = new FilterTable();
	private BeanItemContainer<Rezept> ctMenue;
	private FilterTable tblRezepte = new FilterTable();
	private BeanItemContainer<Rezept> ctRezepte;

	private List<Rezept> tmpRezepte = new ArrayList<Rezept>();
	List<Rezept> listrezept = new ArrayList<Rezept>();
	List<Fussnote> listfussnote = new ArrayList<Fussnote>();
	Menue menue = new Menue();

	private Label headlineAnlegen;
	private Label headlineUpdate;
	
	public MenueAnlegen() {
		super();
		
		this.setSizeFull();
		this.setMargin(true);

		tblRezepte = new FilterTable();
		tblRezepte.setSizeFull();
		tblRezepte.setStyleName("palaverTable");
		tblRezepte.setImmediate(true);
		tblRezepte.setFilterBarVisible(true);
		tblRezepte.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		tblRezepte.setCaption("Rezepte");
		tblRezepte.setSelectable(true);

		tblMenueRezepte = new FilterTable();
		tblMenueRezepte.setSizeFull();
		tblMenueRezepte.setStyleName("palaverTable");
		tblMenueRezepte.setImmediate(true);
		tblMenueRezepte.setFilterBarVisible(true);
		tblMenueRezepte
				.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		tblMenueRezepte.setCaption("Rezepte in Men�");

		load();
		
		vlBox.setWidth("1000px");
		vlBox.setSpacing(true);

		this.addComponent(vlBox);
		this.setComponentAlignment(vlBox, Alignment.MIDDLE_CENTER);
		headlineAnlegen = new Label("Men� anlegen");
		headlineAnlegen.setStyleName("ViewHeadline");
		vlBox.addComponent(headlineAnlegen);
		
		vlBox.addComponent(hlDetails);
		vlBox.addComponent(hlRezepte);
		vlBox.addComponent(hlControl);
		
		hlDetails.addComponent(vlDetailsLinks);
		hlDetails.addComponent(vlDetailsRechts);
		hlDetails.setWidth("1000px");
		hlDetails.setHeight("250px");
		
		vlDetailsLinks.addComponent(vlDetailsLinksOben);
		vlDetailsLinks.addComponent(vlDetailsLinksUnten);vlDetailsLinks.addComponent(vlDetailsLinksOben);
		vlDetailsLinks.addComponent(vlDetailsLinksUnten);
		
		vlDetailsLinksOben.setHeight("160px");
		
		vlDetailsLinksUnten.setHeight("90px");
		
		vlBox.setComponentAlignment(headlineAnlegen, Alignment.MIDDLE_LEFT);
			
		vlDetailsLinksOben.addComponent(tfMenuename);
		vlDetailsLinksOben.addComponent(nsKoch);
		vlDetailsLinksOben.addComponent(nsMenueart);
		vlDetailsLinksOben.addComponent(nsGeschmack);
		vlDetailsLinksUnten.addComponent(dummy);
		vlDetailsLinksUnten.addComponent(chbFavorit);
		vlDetailsLinksUnten.addComponent(chbAufwand);
		vlDetailsLinks.setWidth("450px");
		
		vlDetailsRechts.addComponent(tcsFussnoten);
		vlDetailsRechts.setWidth("500px");
		vlDetailsRechts.setComponentAlignment(tcsFussnoten, Alignment.MIDDLE_RIGHT);
		
		hlRezepte.setWidth("1000px");
		hlRezepte.setHeight("393px");
		
//		hlControl.setSpacing(true);
		vlBox.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btNeuesRezept.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		btSpeichern.setEnabled(true);
		btNeuesRezept.setEnabled(true);
		
		hlRezepte.addComponent(tblMenueRezepte);
		hlRezepte.addComponent(tblRezepte);
		
		hlControl.addComponent(btNeuesRezept);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btSpeichern);

		// Komponenten formatieren
//		vlDetailsRechts.setWidth("450px");
//		hlDetails.setWidth("525px");
//		hlDetails.setComponentAlignment(vlDetailsLinks, Alignment.TOP_LEFT);
//		hlDetails.setComponentAlignment(vlDetailsRechts, Alignment.TOP_RIGHT);
//		hlRezepte.setWidth("900px");
//		hlRezepte.setComponentAlignment(tblMenueRezepte, Alignment.TOP_LEFT);
//		hlRezepte.setComponentAlignment(tblRezepte, Alignment.TOP_RIGHT);
		tblRezepte.setVisibleColumns(new Object[] { "name", "rezeptart",
				"mitarbeiter" });
		tblRezepte.setFilterFieldValue("mitarbeiter", ((Application) UI
				.getCurrent().getData()).getUser().getVorname());
		tblMenueRezepte.setVisibleColumns(new Object[] { "name", "rezeptart",
				"mitarbeiter" });

		tcsFussnoten.setWidth("100%");
		tcsFussnoten.setImmediate(true);
		tfMenuename.setWidth("100%");
		tfMenuename.setMaxLength(200);
		tfMenuename.addValidator(new StringLengthValidator(
				IConstants.INFO_MENUE_NAME, 5, 200, false));
		nsKoch.setWidth("100%");

		nsGeschmack.setWidth("100%");
		nsGeschmack.setImmediate(true);
		nsGeschmack.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (nsGeschmack.getValue() == null) {
					throw new InvalidValueException(
							IConstants.INFO_MENUE_GESCHMACK);
				}
			}
		});
		nsMenueart.setWidth("100%");
		nsMenueart.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (nsMenueart.getValue() == null) {
					throw new InvalidValueException(
							IConstants.INFO_MENUE_MENUEART);
				}

			}
		});
		//
		// nsGeschmack.setNullSelectionAllowed(false);
		// nsMenueart.setNullSelectionAllowed(false);
		tfMenuename.setImmediate(true);
		nsKoch.setImmediate(true);
		nsKoch.setNullSelectionAllowed(false);

		tcsFussnoten.setImmediate(true);

		btSpeichern.setIcon(new ThemeResource("img/save.ico"));
		btVerwerfen.setIcon(new ThemeResource("img/cross.ico"));
		btUpdate.setIcon(new ThemeResource("img/cross.ico"));

		// Drag&Drop
		tblMenueRezepte.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				Rezept selected = (Rezept) t.getData("itemId");
				ctRezepte.removeItem(selected);
				tmpRezepte.add(selected);
				ctMenue.addItem(selected);
				tblMenueRezepte.markAsDirty();
				tblRezepte.markAsDirty();
			}
		});

		tblRezepte.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				Rezept selected = (Rezept) t.getData("itemId");
				ctRezepte.addItem(selected);
				tmpRezepte.remove(selected);
				ctMenue.removeItem(selected);
				tblMenueRezepte.markAsDirty();
				tblRezepte.markAsDirty();
			}
		});

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (MenueAnlegen.this.getParent() instanceof Window) {
					Window win = (Window) MenueAnlegen.this.getParent();
					win.close();
				} else {
					ViewHandler.getInstance().switchView(MenueAnlegen.class);
				}
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
					if (MenueAnlegen.this.getParent() instanceof Window) {
						Window win = (Window) MenueAnlegen.this.getParent();
						win.close();
					} else {
						ViewHandler.getInstance().switchView(
								MenueAnzeigenTabelle.class);
					}
				}
			}
		});

		btNeuesRezept.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addRezept();
			}
		});

		nsKoch.select(((Application) UI.getCurrent().getData()).getUser());
	}

	private void addRezept() {
		final Window win = new Window("Neues Rezept");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("1200px");
		win.setHeight("850px");

		RezeptAnlegen ra = new RezeptAnlegen();
		addComponent(ra);

		win.setContent(ra);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				try {
					ctRezepte = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepteForMenue());
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ConnectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tblRezepte.setContainerDataSource(ctRezepte);
				tblRezepte.setVisibleColumns(new Object[] { "name", "rezeptart",
				"mitarbeiter" });
			}
		});
	}

	public void load() {

		// Inhalte laden
		try {
			ctRezepte = new BeanItemContainer<Rezept>(Rezept.class,
					Rezeptverwaltung.getInstance().getAllRezepteForMenue());
			tblRezepte.setContainerDataSource(ctRezepte);

			tblRezepte.setVisibleColumns(new Object[] { "name", "rezeptart",
					"mitarbeiter" });

			ctMenue = new BeanItemContainer<Rezept>(Rezept.class);
			tblMenueRezepte.setContainerDataSource(ctMenue);

			tblMenueRezepte.setVisibleColumns(new Object[] { "name",
					"rezeptart", "mitarbeiter" });

			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				nsKoch.addItem(e);
			}

			List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
					.getAllGeschmack();
			for (Geschmack e : geschmack) {
				nsGeschmack.addItem(e);

			}

			List<Menueart> menueart = Menueartverwaltung.getInstance()
					.getAllMenueart();
			for (Menueart e : menueart) {
				nsMenueart.addItem(e);

			}

			List<Fussnote> fussnote = Fussnotenverwaltung.getInstance()
					.getAllFussnote();
			for (Fussnote e : fussnote) {
				tcsFussnoten.addItem(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getViewParam(ViewData data) {

		Object dataParam = ((ViewDataObject<?>) data).getData();
		if (dataParam instanceof Menue) {
			menue = (Menue) dataParam;
			ladeMenue();
		} else if (dataParam instanceof Rezept) {
			Rezept rezept = (Rezept) dataParam;
			ladeRezept(rezept);
		}
	}

	public void ladeRezept(Rezept rezept) {
		if (rezept.getId() == null) {
			try {
				rezept = Rezeptverwaltung.getInstance().getRezeptByName(
						rezept.getName());
			} catch (ConnectException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		tfMenuename.setValue(rezept.getName());
		tblMenueRezepte.addItem(rezept);
		tmpRezepte.add(rezept);
		tblRezepte.removeItem(rezept);
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_MENUE_ALS_REZEPT);
	}

	private void ladeMenue() {

		Long id = menue.getId();
		menue = null;
		try {
			menue = Menueverwaltung.getInstance().getMenueById(id);
		} catch (ConnectException e1) {
			e1.printStackTrace();
		} catch (DAOException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			menue = MenueDAO.getInstance().getMenueById(menue.getId());
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// vorhandene Rezepte setzen
		if (menue.getRezepte() != null) {
			tmpRezepte = menue.getRezepte();
		} else {
			tmpRezepte = new ArrayList<Rezept>();
		}

		tfMenuename.setValue(menue.getName());

		nsKoch.select(menue.getKoch());

		nsMenueart.select(menue.getMenueart());

		nsGeschmack.select(menue.getGeschmack());

		chbAufwand.setValue(menue.getAufwand());

		chbFavorit.setValue(menue.getFavorit());

		for (int i = 0; i < menue.getFussnoten().size(); i++) {
			tcsFussnoten.select(menue.getFussnoten().get(i));
		}

		tblMenueRezepte = null;
		tblMenueRezepte = new FilterTable();
		tblMenueRezepte.setSizeFull();
		tblMenueRezepte.setStyleName("palaverTable2");
		tblMenueRezepte.setImmediate(true);

		tblMenueRezepte.setContainerDataSource(ctRezepte);
		tmpRezepte = menue.getRezepte();
		for (Rezept r : tmpRezepte) {
			ctRezepte.removeItem(r);
			ctMenue.addItem(r);

		}

		menue.setRezepte(tmpRezepte);

		hlControl.replaceComponent(btSpeichern, btUpdate);
		headlineUpdate = new Label("Men� bearbeiten");

		headlineUpdate.setStyleName("ViewHeadline");

		vlBox.replaceComponent(headlineAnlegen, headlineUpdate);

		vlBox.setComponentAlignment(headlineUpdate,
				Alignment.MIDDLE_LEFT);

		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
					ViewHandler.getInstance().switchView(
							MenueAnzeigenTabelle.class);
				}
			}
		});
	}

	private void speichern() {

		erstelleMenue();
		try {
			Menueverwaltung.getInstance().speicherMenue(menue);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void erstelleMenue() {

		if (menue == null) {
			menue = new Menue();
		}

		menue.setName(tfMenuename.getValue());
		menue.setKoch((Mitarbeiter) nsKoch.getValue());
		menue.setMenueart((Menueart) nsMenueart.getValue());
		menue.setGeschmack((Geschmack) nsGeschmack.getValue());

		if (chbFavorit.getValue()) {
			menue.setFavorit(true);
		} else
			menue.setFavorit(false);

		if (chbAufwand.getValue()) {
			menue.setAufwand(true);
		} else
			menue.setAufwand(false);

		menue.setFussnoten(ladeFussnotenAusTCS());

		menue.setRezepte(tmpRezepte);
	}

	private List<Fussnote> ladeFussnotenAusTCS() {
		List<Fussnote> fussnoten = new ArrayList<Fussnote>();

		if (tcsFussnoten.getValue().toString() != "[]") {
			List<String> sFussnoten = Arrays.asList(tcsFussnoten
					.getValue()
					.toString()
					.substring(1,
							tcsFussnoten.getValue().toString().length() - 1)
					.split("\\s*,\\s*"));

			for (String sFussnote : sFussnoten) {
				try {
					fussnoten.add(Fussnotenverwaltung.getInstance()
							.getFussnoteByName(sFussnote));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return fussnoten;
	}

	private void update() {

		erstelleMenue();

		try {
			Menueverwaltung.getInstance().updateMenue(menue);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Boolean validiereEingabe() {
		if (tfMenuename.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_NAME);
			return false;
		}
		if (nsKoch.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_KOCH);
			return false;
		}
		if (nsMenueart.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_MENUEART);
			return false;
		}
		if (nsGeschmack.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_GESCHMACK);
			return false;
		}
		if (tmpRezepte == null || tmpRezepte.size() == 0) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_REZEPT);
			return false;
		}
		int countHauptmenue = 0;
		for (Rezept rezept : tmpRezepte) {
			if (rezept.getRezeptart().getId() == 1L) {
				++countHauptmenue;
			}
		}
		if (countHauptmenue == 0) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_HAUPTGERICHT);
			return false;
		}
		if (countHauptmenue > 1) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_MENUE_NUR_HAUPTGERICHT);
			return false;
		}
		return true;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}

	// private void rezeptAlsHauptgerichtSpeichern() {
	// if (ausgArtikel.isEmpty()) {
	// return;
	// }
	// Menue menue = new Menue();
	//
	// menue.setName(nameInput);
	// try {
	// menue.setKoch(MitarbeiterDAO.getInstance().getMitarbeiterById(
	// Long.parseLong(mitarbeiterInput.toString())));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	//
	// try {
	// Menueverwaltung.getInstance().createRezeptAlsMenue(menue);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private void rezeptAlsMenuSpeichern() {
	// if (ausgArtikel.isEmpty()) {
	// return;
	// }
	// MenueHasRezept mhr = new MenueHasRezept();
	// try {
	// mhr.setMenue(MenueDAO.getInstance().getMenueIdByName(nameInput));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// try {
	// mhr.setRezept(RezeptDAO.getInstance().getRezeptByName1(nameInput));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// try {
	// Menueverwaltung.getInstance().RezepteAdd(mhr);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// mhr.setHauptgericht(true);
	// Notification notification = new Notification(
	// "Rezept wurde als Menü gespeichert!");
	// notification.setDelayMsec(500);
	// notification.show(Page.getCurrent());
	// }

}

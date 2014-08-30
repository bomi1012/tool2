package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
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

import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.DAO.MenueDAO;
import de.palaver.management.recipe.Recipe;
import de.palaver.view.bean.rezeptverwaltung.ChangeRecipeBean;

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
	
	
	private TextField tfMenuename = new TextField("Menüname");

	private TwinColTouch tcsFussnoten = new TwinColTouch("Fuünoten");

	private NativeSelect nsKoch = new NativeSelect("Koch");
	private NativeSelect nsMenueart = new NativeSelect("Menüart");
	private NativeSelect nsGeschmack = new NativeSelect("Geschmack");

	private CheckBox chbFavorit = new CheckBox("Menü ist ein Favorit");
	private CheckBox chbAufwand = new CheckBox("Menü hat hohen Aufwand");

	private Button btSpeichern = new Button("Speichern");
	private Button btVerwerfen = new Button("Verwerfen");
	private Button btUpdate = new Button("ündern");
	private Button btNeuesRezept = new Button("Neues Rezept");

	private FilterTable tblMenueRezepte = new FilterTable();
	private BeanItemContainer<Recipe> ctMenue;
	private FilterTable tblRezepte = new FilterTable();
	private BeanItemContainer<Recipe> ctRezepte;

	private List<Recipe> tmpRezepte = new ArrayList<Recipe>();
	List<Recipe> listrezept = new ArrayList<Recipe>();
	List<Fussnote> listfussnote = new ArrayList<Fussnote>();
	Menu menu = new Menu();

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
		tblMenueRezepte.setCaption("Rezepte in Menü");

		load();
		
		vlBox.setWidth("1000px");
		vlBox.setSpacing(true);

		this.addComponent(vlBox);
		this.setComponentAlignment(vlBox, Alignment.MIDDLE_CENTER);
		headlineAnlegen = new Label("Menü anlegen");
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
		tblRezepte.setVisibleColumns(new Object[] { "name", "recipetype",
				"employee" });
		tblRezepte.setFilterFieldValue("mitarbeiter", ((Application) UI
				.getCurrent().getData()).getUser().getVorname());
		tblMenueRezepte.setVisibleColumns(new Object[] { "name", "recipetype",
				"employee" });

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
				Recipe selected = (Recipe) t.getData("itemId");
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
				Recipe selected = (Recipe) t.getData("itemId");
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

		ChangeRecipeBean ra = new ChangeRecipeBean(); 
		addComponent(ra);

		win.setContent(ra);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				try {
					ctRezepte = new BeanItemContainer<Recipe>(Recipe.class,
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
				"employee" });
			}
		});
	}

	public void load() {

		// Inhalte laden
		try {
			ctRezepte = new BeanItemContainer<Recipe>(Recipe.class,
					Rezeptverwaltung.getInstance().getAllRezepteForMenue());
			tblRezepte.setContainerDataSource(ctRezepte);

			tblRezepte.setVisibleColumns(new Object[] { "name", "recipetype",
					"employee" });

			ctMenue = new BeanItemContainer<Recipe>(Recipe.class);
			tblMenueRezepte.setContainerDataSource(ctMenue);

			tblMenueRezepte.setVisibleColumns(new Object[] { "name",
					"recipetype", "employee" });

			List<Employee> employee = EmployeeService.getInstance()
					.getAllEmployees();
			for (Employee e : employee) {
				nsKoch.addItem(e);
			}

			List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
					.getAllGeschmack();
			for (Geschmack e : geschmack) {
				nsGeschmack.addItem(e);

			}

			List<Menutype> menutype = Menueartverwaltung.getInstance()
					.getAllMenueart();
			for (Menutype e : menutype) {
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
		if (dataParam instanceof Menu) {
			menu = (Menu) dataParam;
			ladeMenue();
		} else if (dataParam instanceof Recipe) {
			Recipe recipe = (Recipe) dataParam;
			ladeRezept(recipe);
		}
	}

	public void ladeRezept(Recipe recipe) {
		if (recipe.getId() == null) {
			try {
				recipe = Rezeptverwaltung.getInstance().getRezeptByName(
						recipe.getName());
			} catch (ConnectException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		tfMenuename.setValue(recipe.getName());
		tblMenueRezepte.addItem(recipe);
		tmpRezepte.add(recipe);
		tblRezepte.removeItem(recipe);
		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_MENUE_ALS_REZEPT);
	}

	private void ladeMenue() {

		Long id = menu.getId();
		menu = null;
		try {
			menu = Menueverwaltung.getInstance().getMenueById(id);
		} catch (ConnectException e1) {
			e1.printStackTrace();
		} catch (DAOException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			menu = MenueDAO.getInstance().getMenueById(menu.getId());
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// vorhandene Rezepte setzen
		if (menu.getRecipeList() != null) {
			tmpRezepte = menu.getRecipeList();
		} else {
			tmpRezepte = new ArrayList<Recipe>();
		}

		tfMenuename.setValue(menu.getName());

		nsKoch.select(menu.getEmployee());

		nsMenueart.select(menu.getMenutype());

		nsGeschmack.select(menu.getGeschmack());

		chbAufwand.setValue(menu.getAufwand());

		chbFavorit.setValue(menu.getFavorit());

		for (int i = 0; i < menu.getFussnotenList().size(); i++) {
			tcsFussnoten.select(menu.getFussnotenList().get(i));
		}

		tblMenueRezepte = null;
		tblMenueRezepte = new FilterTable();
		tblMenueRezepte.setSizeFull();
		tblMenueRezepte.setStyleName("palaverTable2");
		tblMenueRezepte.setImmediate(true);

		tblMenueRezepte.setContainerDataSource(ctRezepte);
		tmpRezepte = menu.getRecipeList();
		for (Recipe r : tmpRezepte) {
			ctRezepte.removeItem(r);
			ctMenue.addItem(r);

		}

		menu.setRecipeList(tmpRezepte);

		hlControl.replaceComponent(btSpeichern, btUpdate);
		headlineUpdate = new Label("Menü bearbeiten");

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
			Menueverwaltung.getInstance().speicherMenue(menu);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void erstelleMenue() {

		if (menu == null) {
			menu = new Menu();
		}

		menu.setName(tfMenuename.getValue());
		menu.setEmployee((Employee) nsKoch.getValue());
		menu.setMenutype((Menutype) nsMenueart.getValue());
		menu.setGeschmack((Geschmack) nsGeschmack.getValue());

		if (chbFavorit.getValue()) {
			menu.setFavorit(true);
		} else
			menu.setFavorit(false);

		if (chbAufwand.getValue()) {
			menu.setAufwand(true);
		} else
			menu.setAufwand(false);

		menu.setFussnotenList(ladeFussnotenAusTCS());

		menu.setRecipeList(tmpRezepte);
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
			Menueverwaltung.getInstance().updateMenue(menu);
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
		for (Recipe recipe : tmpRezepte) {
			if (recipe.getRecipetype().getId() == 1L) {
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

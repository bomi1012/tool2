package de.palaver.view.bean.menuverwaltung;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menu;
import de.palaver.management.menu.Menutype;
import de.palaver.management.menu.service.MenuService;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.wrappers.RecipeWrapper;

public class ChangeMenuBean extends TemplateBuilder implements View, ValueChangeListener {
	private static final long serialVersionUID = -4672352966956210418L;
	private static final int POSITION_TOP = 0;
	private static final int POSITION_BOTTOM = 1;
	private Menu m_menu;
	private ArrayList<RecipeWrapper> m_menuHasRecipeList;
	private boolean m_toCreate;
	private TextField m_nameField;
	private NativeSelect m_employeeSelect;
	private NativeSelect m_menutypeSelect;
	private NativeSelect m_geschmackSelect;
	private Button m_addMenutypeButton;
	private Button m_addGeschmackButton;	

	private CheckBox m_favoritCheckbox;
	private CheckBox m_aufwandCheckbox;
	private TwinColSelect m_fussnotenColSelect;
	private Button m_addFussnoteButton;
	private VerticalLayout m_innerBoxFussnoten;
	private VerticalLayout m_innerBoxMenu;
	private BeanItemContainer<RecipeWrapper> m_containerItem;
	private BeanItemContainer<RecipeWrapper> m_containerMenuHasRecipe;

	public ChangeMenuBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
        clickListener();
		changeListner();
        beans();
        loadContent();
        setDataToTwinColSelect();
        dragAndDrop();
        
		m_employeeSelect.setValue(((Application) UI.getCurrent().getData()).getUser());
		m_menu.setEmployee(((Application) UI.getCurrent().getData()).getUser());
		
		resetMarkAsChange();
	}

	private void init() {
		m_menu = new Menu();
		m_menuHasRecipeList = new ArrayList<RecipeWrapper>();
		m_toCreate = true;
	}
	
	private void componetsManager() {
		m_headLine = title("Menü anlegen", STYLE_HEADLINE_STANDART);
		m_nameField = textField("Menüname", WIDTH_FULL, true, "Menüname", 0);
		
		m_employeeSelect = nativeSelect("Mitarbeiter", WIDTH_FULL, true, "Mitarbeiter", this);
		m_menutypeSelect = nativeSelect("Menüart", WIDTH_FULL, true, "Menüart", this);
		m_geschmackSelect = nativeSelect("Geschmack", WIDTH_FULL, true, "Geschmack", this);
		
		m_addMenutypeButton= button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);
		m_addGeschmackButton= button(BUTTON_TEXT_CREATE, BUTTON_ICON_CREATE, true, true);
		
		m_favoritCheckbox = new CheckBox("Menü ist ein Favorit");
		m_aufwandCheckbox = new CheckBox("Menü hat hohen Aufwand");
		
		m_fussnotenColSelect = twinColSelect(null, "verfügbare Fußnoten", "ausgewählte Fußnoten", 8, 0, true, true);	
		m_addFussnoteButton = buttonAsIcon(" neue Fußnote", BaseTheme.BUTTON_LINK, 
				"cursor-hand lieferant", "icons/bricks.png", true);
				
		m_table = HTMLComponents.table(true, true, WIDTH_FULL, "300px");
		m_table.setStyleName("palaverTable");
		m_table.setCaption("Menükomponenten");
		m_table.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		
		m_filterTable = HTMLComponents.filterTable(true, true, WIDTH_FULL, "300px");
		m_filterTable.setStyleName("palaverTable");
		m_filterTable.setCaption("Rezeptenliste");
		m_filterTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		
		m_control = controlPanel(this);			
	}
	
	private void templateBuilder() {			
		m_innerBoxFussnoten = new VerticalLayout();
		m_innerBoxFussnoten.setWidth(WIDTH_FULL);
		m_innerBoxFussnoten.setSpacing(true);
		m_innerBoxFussnoten.setMargin(true);			
		m_innerBoxFussnoten.addComponent(m_fussnotenColSelect);
		m_innerBoxFussnoten.addComponent(m_addFussnoteButton);
		
		m_innerBoxMenu = new VerticalLayout();		
		m_innerBoxMenu.setWidth(WIDTH_FULL);
		m_innerBoxMenu.setSpacing(true);
		m_innerBoxMenu.setMargin(true);		
		
		m_innerBoxMenu.addComponent(m_headLine);
		m_innerBoxMenu.addComponent(new Hr());
		m_innerBoxMenu.addComponent(horizontalLayoutBuilder(POSITION_TOP));	
		m_innerBoxMenu.addComponent(horizontalLayoutBuilder(POSITION_BOTTOM));	
		
		m_innerBoxMenu.addComponent(new Hr());
		m_innerBoxMenu.addComponent(m_control);
		m_innerBoxMenu.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(m_innerBoxMenu);		
		setComponentAlignment(m_innerBoxMenu, Alignment.MIDDLE_CENTER);			
	}
	
	@SuppressWarnings("serial")
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					ViewHandler.getInstance().switchView(ShowMenusBean.class, new ViewDataObject<Menu>(m_menu));
				}							
			}
		});		
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ShowMenusBean.class);
			}
		});
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO:   windowModalYesNoRemove(m_menu);	
			}
		});
		
		m_addFussnoteButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO:   getWindowFactory(new ChangeFussnotenBean()); 
			}
		});
	}
	
	private boolean validiereEingabe() {
		boolean bool = true;
		if (isMarkAsChange()) {
			if (StringUtils.isBlank(m_nameField.getValue())) {
				((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENUE_NAME);
				bool = false;
			} else if (m_menutypeSelect.getValue() == null) {
				((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENUEART_SELECT);
				bool = false;
			} else if (m_employeeSelect.getValue() == null) {
				((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENUE_KOCH);
				bool = false;
			} else if (m_geschmackSelect.getValue() == null) {
				((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_GESCHMACK_SELECT);
				bool = false;
			}  
			else if (m_containerMenuHasRecipe.getItemIds().size() == 0) {
				((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_MENUE_REZEPT);					
				bool = false;
			} 
		} else {
			bool = false;
		} 			
		return bool;		
	}
	
	private void saveItem() {		
		try {
			if (m_toCreate) { //TODO: TESTEN
				Long menuId = MenuService.getInstance().createMenu(m_menu);
				MenuService.getInstance().createRelationFussnoten(menuId, setFussnoten());
				MenuService.getInstance().createRelationRecipe(menuId, m_containerMenuHasRecipe.getItemIds());
			} else {
				MenuService.getInstance().updateMenu(m_menu);
				MenuService.getInstance().updateRelationFussnoten(m_menu.getId(), setFussnoten());
				MenuService.getInstance().updateRelationItem(m_menu.getId(), m_containerMenuHasRecipe.getItemIds());
			}			
			message(MESSAGE_SUSSEFULL_ARG_1, "Menü");	
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}
	
	private List<Fussnote> setFussnoten() {
		List<Fussnote> fussnotenList = new ArrayList<Fussnote>();
		for (Object id : m_fussnotenColSelect.getItemIds()) {					
			if (m_fussnotenColSelect.isSelected((Long) id))	{				
				fussnotenList.add(new Fussnote((Long) id, null, null));
			} 							
		}
		return fussnotenList;
	}
	
	@SuppressWarnings("serial")
	private void changeListner() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setName(String.valueOf(event.getProperty().getValue()));		
				markAsChange();
			}
		});
		m_favoritCheckbox.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setFavorit((Boolean) event.getProperty().getValue());	
				markAsChange();
			}
		});
		m_aufwandCheckbox.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setAufwand((Boolean) event.getProperty().getValue());	
				markAsChange();
			}
		});
		m_employeeSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setEmployee((Employee) event.getProperty().getValue());	
				markAsChange();
			}
		});
		m_geschmackSelect.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setGeschmack((Geschmack) event.getProperty().getValue());	
				markAsChange();
			}
		});
		m_menutypeSelect.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_menu.setMenutype((Menutype) event.getProperty().getValue());		
				markAsChange();
			}
		});
		m_fussnotenColSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				markAsChange();
			}
		});
	}
	
	private void beans() {
		try {
			m_containerItem = new BeanItemContainer<RecipeWrapper>(RecipeWrapper.class, RecipeWrapper.getRecipeWrappers());
			m_filterTable.setContainerDataSource(m_containerItem);
			m_filterTable.setVisibleColumns(new Object[] { "name", "recipetype", "employee" });
			m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });			
			m_filterTable.setColumnHeader("name", "Rezept");		
			m_filterTable.setColumnHeader("recipetype", "Rezeptart");
			m_filterTable.setColumnHeader("employee", "Mitarbeiter");
			
			m_containerMenuHasRecipe = new BeanItemContainer<RecipeWrapper>(RecipeWrapper.class);
			m_table.setContainerDataSource(m_containerMenuHasRecipe);
			m_table.setVisibleColumns(new Object[] { "name", "recipetype", "kommentar"});	
			m_table.setColumnHeader("name", "Rezept");		
			m_table.setColumnHeader("recipetype", "Rezeptart");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadContent() {
		Object[] objects = {new Employee(), new Menutype(), new Geschmack()};		
		for (Object object : objects) {
			loadAllForSelectFactory(object);
		}
	} 
	
	private void loadAllForSelectFactory(Object object) {
		try {
			if(object instanceof Employee) { 
				m_employeeSelect.removeAllItems();			
				for (Employee employee : EmployeeService.getInstance().getAllEmployees()) {
					m_employeeSelect.addItem(employee);
				}				 
			}
			if(object instanceof Menutype) { 
				m_menutypeSelect.removeAllItems();			
				for (Menutype menutype : MenuService.getInstance().getAllMenutypes()) {
					m_menutypeSelect.addItem(menutype);
				}				 
			}
			if(object instanceof Geschmack) { 
				m_geschmackSelect.removeAllItems();			
				for (Geschmack geschmack : MenuService.getInstance().getAllGeschmacks()) {
					m_geschmackSelect.addItem(geschmack);
				}				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDataToTwinColSelect() {
		try {
			for (Fussnote fussnote : MenuService.getInstance().getAllFussnote()) {
				m_fussnotenColSelect.addItem(fussnote.getId());
				m_fussnotenColSelect.setItemCaption(fussnote.getId(), fussnote.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	private void dragAndDrop() {
		m_table.setDropHandler(new DropHandler() {
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable transferable = event.getTransferable();
				if (transferable.getData("itemId") instanceof RecipeWrapper) {
					RecipeWrapper selected = (RecipeWrapper) transferable.getData("itemId");
					m_containerItem.removeItem(selected);
					m_menuHasRecipeList.add(selected);
					m_containerMenuHasRecipe.addItem(selected);
				}
				mark();
			}
		});		
		
		m_filterTable.setDropHandler(new DropHandler() {
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable transferable = event.getTransferable();
				if (transferable.getData("itemId") instanceof RecipeWrapper) {
					RecipeWrapper selected = (RecipeWrapper) transferable.getData("itemId");
					m_containerMenuHasRecipe.removeItem(selected);
					m_menuHasRecipeList.remove(selected);
					m_containerItem.addItem(selected);
				}
				mark();
			}
		});
	}
	
	private void mark() {
		m_table.markAsDirty();
		m_filterTable.markAsDirty();
		markAsChange();
	}
	
	private HorizontalLayout horizontalLayoutBuilder(int position) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setMargin(true);	
		
		if (position == POSITION_TOP) {			
			Component[] array = {m_nameField, m_employeeSelect, 
					selectAndAddButtonPanel(m_menutypeSelect, m_addMenutypeButton, WIDTH_FULL),
					selectAndAddButtonPanel(m_geschmackSelect, m_addGeschmackButton, WIDTH_FULL),
					m_favoritCheckbox, m_aufwandCheckbox};	
			
			VerticalLayout verticalLayout = verticalLayout(array, "90%");
					
			horizontalLayout.addComponent(verticalLayout);
			horizontalLayout.addComponent(m_innerBoxFussnoten);
			horizontalLayout.setExpandRatio(verticalLayout, 1);
			horizontalLayout.setExpandRatio(m_innerBoxFussnoten, 1);
			horizontalLayout.setComponentAlignment(verticalLayout, Alignment.TOP_LEFT);
			horizontalLayout.setComponentAlignment(m_innerBoxFussnoten, Alignment.TOP_RIGHT);			
		} 
		if (position == POSITION_BOTTOM) {
			horizontalLayout.addComponent(m_table);
			horizontalLayout.addComponent(m_filterTable);
			horizontalLayout.setExpandRatio(m_table, 1);
			horizontalLayout.setExpandRatio(m_filterTable, 1);
			horizontalLayout.setComponentAlignment(m_table, Alignment.TOP_LEFT);
			horizontalLayout.setComponentAlignment(m_filterTable, Alignment.TOP_RIGHT);
		}		
		return horizontalLayout;
	}
	
	private VerticalLayout verticalLayout (Component[] array, String width) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setWidth(width);
		verticalLayout.setSpacing(true);
		
		for (Component component : array) {
			verticalLayout.addComponent(component);
		}
		
		return verticalLayout;		
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) { }

	@Override
	public void getViewParam(IViewData data) { 	}
}

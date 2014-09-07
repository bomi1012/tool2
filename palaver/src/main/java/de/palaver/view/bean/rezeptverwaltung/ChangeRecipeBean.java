package de.palaver.view.bean.rezeptverwaltung;

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
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.IViewData;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.management.emploee.Employee;
import de.palaver.management.employee.service.EmployeeService;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.Recipetype;
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.service.RecipeService;
import de.palaver.management.util.Helper;
import de.palaver.view.bean.artikelverwaltung.ChangeItemBean;
import de.palaver.view.bean.menuverwaltung.ChangeMenuBean;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.TemplateBuilder;
import de.palaver.view.bean.util.wrappers.RezeptHasArtikelWrapper;

public class ChangeRecipeBean extends TemplateBuilder implements View, ValueChangeListener {
	private static final long serialVersionUID = -4672352966956210418L;
	private static final int POSITION_TOP = 0;
	private static final int POSITION_BOTTOM = 1;
	
	private boolean m_toCreate;
	private NativeSelect m_employeeSelect;
	private NativeSelect m_recipetypeSelect;
	private TwinColSelect m_zubereitungColSelect = new TwinColSelect();
	private VerticalLayout m_innerBoxRecipe;
	private TextField m_nameField;
	private TextArea m_commentField;
	private BeanItemContainer<Artikel> m_containerItem;
	private BeanItemContainer<RezeptHasArtikelWrapper> m_containerRezeptHasArtikel;
	
	private List<RezeptHasArtikelWrapper> m_rezeptHasArtikelWrappers;
	private Recipe m_recipe;
	private Button m_addZubereitungButton;
	private VerticalLayout m_innerBoxZubereitung;

	public ChangeRecipeBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listener();
		beans();
		loadContent();
		setDataToTwinColSelect();
		dragAndDrop();
		
		m_employeeSelect.setValue(((Application) UI.getCurrent().getData()).getUser());
		m_recipe.setEmployee(((Application) UI.getCurrent().getData()).getUser());
	}

	private void init() {
		m_recipe = new Recipe();
		m_rezeptHasArtikelWrappers = new ArrayList<RezeptHasArtikelWrapper>();
		m_toCreate = true;
	}

	private void componetsManager() {
		m_headLine = title("Rezept anlegen", STYLE_HEADLINE_STANDART);
		m_nameField = textField("Rezeptname", WIDTH_FULL, true, "Rezeptname", 0);
		m_employeeSelect = nativeSelect("Mitarbeiter", WIDTH_FULL, true, "Mitarbeiter", this);
		m_recipetypeSelect = nativeSelect("Rezeptart", WIDTH_FULL, true, "Rezeptart", this);
		m_commentField = textArea("Kommentar", WIDTH_FULL, "60", false, "Kommentar", this);
		
		m_zubereitungColSelect = twinColSelect(null, "verfügbare Zubereitung", "ausgewählte Zubereitung", 8, 0, true, true);	
		m_addZubereitungButton = buttonAsIcon(" neue Zubereitung", BaseTheme.BUTTON_LINK, 
				"cursor-hand lieferant", "icons/bricks.png", true);
		
		//Konfiguriere Tabelle Reztepte
		m_table = HTMLComponents.table(true, true, WIDTH_FULL, "300px");
		m_table.setStyleName("palaverTable");
		m_table.setCaption("Rezeptkomponenten");
		m_table.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);

		//Konfiguriere Tabelle der Artikel
		m_filterTable = HTMLComponents.filterTable(true, true, WIDTH_FULL, "300px");
		m_filterTable.setStyleName("palaverTable");
		m_filterTable.setCaption("Zutatenliste");
		m_filterTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		
		m_control = controlPanel(this);			
	}

	private void templateBuilder() {
		m_nameField.addValueChangeListener(this);
		m_commentField.addValueChangeListener(this);
		m_employeeSelect.addValueChangeListener(this);
		m_recipetypeSelect.addValueChangeListener(this);
		
		m_innerBoxZubereitung = new VerticalLayout();
		m_innerBoxZubereitung.setWidth(WIDTH_FULL);
		m_innerBoxZubereitung.setSpacing(true);
		m_innerBoxZubereitung.setMargin(true);			
		m_innerBoxZubereitung.addComponent(m_zubereitungColSelect);
		m_innerBoxZubereitung.addComponent(m_addZubereitungButton);
		
		m_innerBoxRecipe = new VerticalLayout();		
		m_innerBoxRecipe.setWidth(WIDTH_FULL);
		m_innerBoxRecipe.setSpacing(true);
		m_innerBoxRecipe.setMargin(true);		
		
		m_innerBoxRecipe.addComponent(m_headLine);
		m_innerBoxRecipe.addComponent(new Hr());
		m_innerBoxRecipe.addComponent(horizontalLayoutBuilder(POSITION_TOP));	
		m_innerBoxRecipe.addComponent(horizontalLayoutBuilder(POSITION_BOTTOM));	
		
		m_innerBoxRecipe.addComponent(new Hr());
		m_innerBoxRecipe.addComponent(m_control);
		m_innerBoxRecipe.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		addComponent(m_innerBoxRecipe);		
		setComponentAlignment(m_innerBoxRecipe, Alignment.MIDDLE_CENTER);			
	}

	private HorizontalLayout horizontalLayoutBuilder(int position) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setMargin(true);	
		
		if (position == POSITION_TOP) {			
			Component[] array = {m_nameField, m_employeeSelect, m_recipetypeSelect, m_commentField};			
			VerticalLayout verticalLayout = verticalLayout(array, "90%");
					
			horizontalLayout.addComponent(verticalLayout);
			horizontalLayout.addComponent(m_innerBoxZubereitung);
			horizontalLayout.setExpandRatio(verticalLayout, 1);
			horizontalLayout.setExpandRatio(m_innerBoxZubereitung, 1);
			horizontalLayout.setComponentAlignment(verticalLayout, Alignment.TOP_LEFT);
			horizontalLayout.setComponentAlignment(m_innerBoxZubereitung, Alignment.TOP_RIGHT);
			
		} 
		if (position == POSITION_BOTTOM) {
			horizontalLayout.addComponent(m_table);
			horizontalLayout.addComponent(m_filterTable);
			horizontalLayout.setExpandRatio(m_table, 7);
			horizontalLayout.setExpandRatio(m_filterTable, 3);
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

	@SuppressWarnings("serial")
	private void listener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					ViewHandler.getInstance().switchView(ShowRecipesBean.class, new ViewDataObject<Recipe>(m_recipe));
				}							
			}
		});		
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ShowRecipesBean.class);
			}
		});
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				windowModalYesNoRemove(m_recipe);	
			}
		});
		
		m_buttonAddItem.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeItemBean());
			}
		});
		
		m_buttonAddToMenue.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();	
					ViewHandler.getInstance().switchView(ChangeMenuBean.class, new ViewDataObject<Recipe>(m_recipe));	
				}
			}
		});
		m_addZubereitungButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindowFactory(new ChangeZubereitungBean()); 
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void getWindowFactory(final Object object) {	
		if (object instanceof ChangeItemBean) {
			addComponent((ChangeItemBean) object);
			m_window = windowUI(CREATE_NEW_ARTIKEL, true, false, "950", "500");
			m_window.setContent((ChangeItemBean) object);			
		} 
		if(object instanceof ChangeZubereitungBean) {
			addComponent((ChangeZubereitungBean) object);
			m_window = windowUI(CREATE_NEW_ZUBEREITUNG, true, false, "500", "250");
			m_window.setContent((ChangeZubereitungBean) object);
		}
		
		UI.getCurrent().addWindow(m_window);
		m_window.addCloseListener(new CloseListener() {			
			@Override
			public void windowClose(CloseEvent e) {
				if(object instanceof ChangeItemBean && ((ChangeItemBean) object).getArtikel() != null) {
					m_containerItem.addItem(((ChangeItemBean) object).getArtikel());
				} 
				if(object instanceof ChangeZubereitungBean && ((ChangeZubereitungBean) object).getZubereitung() != null) {
					m_zubereitungColSelect.addItem(((ChangeZubereitungBean) object).getZubereitung());
				} 
			}
		});
	}

	private boolean validiereEingabe() {
		boolean bool = true;
		if (StringUtils.isBlank(m_nameField.getValue())) {
			((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPT_NAME);
			bool =  false;
		} else if (m_recipetypeSelect.getValue() == null) {
			((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPT_REZEPTART);
			bool =  false;
		} else if (m_employeeSelect.getValue() == null) {
			((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPT_KOCH);
			bool =  false;
		} else if (m_containerRezeptHasArtikel.getItemIds().size() == 0) {
			((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPT_ZUTATEN);					
			bool =  false;
		} else {
			for (RezeptHasArtikelWrapper eWrapper : m_containerRezeptHasArtikel.getItemIds()) {
				if(!Helper.isDouble(eWrapper.getMenge().getValue()) 
						|| Double.valueOf(eWrapper.getMenge().getValue()) >= 100000.0) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPT_MENGE);
					bool = false;
				}
			}	
		}			
		return bool;
	}
	
	private void saveItem() {		
		try {
			if (m_toCreate) {
				Long recipeId = RecipeService.getInstance().createRecipe(m_recipe);
				RecipeService.getInstance().createRelationZubereitung(recipeId, setZubereitungs());
				RecipeService.getInstance().createRelationItem(recipeId, m_containerRezeptHasArtikel.getItemIds());
			} else {
				RecipeService.getInstance().updateRecipe(m_recipe);
				RecipeService.getInstance().updateRelationZubereitung(m_recipe.getId(), setZubereitungs());
				RecipeService.getInstance().updateRelationItem(m_recipe.getId(), m_containerRezeptHasArtikel.getItemIds());
			}			
			message(MESSAGE_SUSSEFULL_ARG_1, "Rezept");	
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}
		
	private List<Zubereitung> setZubereitungs() {
		List<Zubereitung> zubereitungList = new ArrayList<Zubereitung>();
		for (Object id : m_zubereitungColSelect.getItemIds()) {					
			if (m_zubereitungColSelect.isSelected((Long) id))	{				
				zubereitungList.add(new Zubereitung((Long) id, null));
			} 							
		}
		return zubereitungList;
	}

	private void beans() {
		try {
			m_containerItem = new BeanItemContainer<Artikel>(Artikel.class, ArtikelService.getInstance().getActiveArtikeln());
			m_filterTable.setContainerDataSource(m_containerItem);
			m_filterTable.setVisibleColumns(new Object[] { "name" });
			m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });			
			m_filterTable.setColumnHeader("name", "Artikel");			
			
			m_containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikelWrapper>(RezeptHasArtikelWrapper.class);
			m_table.setContainerDataSource(m_containerRezeptHasArtikel);
			m_table.setVisibleColumns(new Object[] { "artikelname", "menge", "mengeneinheit", "comment" });		
			m_table.setColumnHeader("comment", "notiz");
		} catch (Exception e) {
			//FIXME Popup
			e.printStackTrace();
		}
	}
	
	private void loadContent() {
		Object[] objects = {new Employee(), new Recipetype()};		
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
			if(object instanceof Recipetype) { 
				m_recipetypeSelect.removeAllItems();			
				for (Recipetype recipetype : RecipeService.getInstance().getAllRecipetypes()) {
					m_recipetypeSelect.addItem(recipetype);
				}				 
			}
		} catch (Exception e) {
			//FIXME Popup
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
				if (transferable.getData("itemId") instanceof Artikel) {
					Artikel selected = (Artikel) transferable.getData("itemId");
					m_containerItem.removeItem(selected);
					RezeptHasArtikelWrapper tmp = new RezeptHasArtikelWrapper(selected);
					m_rezeptHasArtikelWrappers.add(tmp);
					m_containerRezeptHasArtikel.addItem(tmp);
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
				if (transferable.getData("itemId") instanceof RezeptHasArtikelWrapper) {
					RezeptHasArtikelWrapper selected = (RezeptHasArtikelWrapper) transferable.getData("itemId");
					m_containerRezeptHasArtikel.removeItem(selected);
					m_rezeptHasArtikelWrappers.remove(selected);
					m_containerItem.addItem(selected.getRezeptHasArtikel().getArtikel());
					mark();
				}				
			}
		});
	}
	
	private void mark() {
		m_table.markAsDirty();
		m_filterTable.markAsDirty();
	}

	private void setDataToTwinColSelect() {
		try {
			for (Zubereitung zubereitung : RecipeService.getInstance().getAllZubereitungen()) {
				m_zubereitungColSelect.addItem(zubereitung.getId());
				m_zubereitungColSelect.setItemCaption(zubereitung.getId(), zubereitung.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	@Override
	public void valueChange(ValueChangeEvent event) {			
		m_nameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_recipe.setName(String.valueOf(event.getProperty().getValue()));	
			}
		});
		m_commentField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_recipe.setKommentar(String.valueOf(event.getProperty().getValue()));	
			}
		});
		m_employeeSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_recipe.setEmployee((Employee) event.getProperty().getValue());
			}
		});
		m_recipetypeSelect.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_recipe.setRecipetype((Recipetype) event.getProperty().getValue());
			}
		});
	}

	@Override
	public void getViewParam(IViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Recipe) {
			try {
				m_recipe = (Recipe)((ViewDataObject<?>) data).getData(); 
				setNewInfo();
				setValueToComponent(getData());
				
				m_recipe.setZubereitungList(RecipeService.getInstance().getAllZubereitungsByRecipeId(m_recipe.getId()));				
				if (m_recipe.getZubereitungList() != null && m_recipe.getZubereitungList().size() > 0) {
					for (Zubereitung zubereitung : m_recipe.getZubereitungList()) {
						m_zubereitungColSelect.select(zubereitung.getId());
					}
				}
				
				m_rezeptHasArtikelWrappers = RezeptHasArtikelWrapper.getRezeptHasArtikelWrappersByRecipeID(m_recipe);
				
				for (RezeptHasArtikelWrapper rezeptHasArtikelWrapper : m_rezeptHasArtikelWrappers) {
					m_containerItem.removeItem(rezeptHasArtikelWrapper.getRezeptHasArtikel().getArtikel());
					m_containerRezeptHasArtikel.addItem(rezeptHasArtikelWrapper);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 			
		}
	}
	
	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_recipe.getName());
		getData().put(m_employeeSelect, m_recipe.getEmployee());
		getData().put(m_recipetypeSelect, m_recipe.getRecipetype());
		getData().put(m_commentField, m_recipe.getKommentar());
		
		m_toCreate = false;		
		m_headLine.setValue("Rezept bearbeiten");
		m_buttonDeaktiviren.setVisible(true);
	}
}

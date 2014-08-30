package de.palaver.view.bean.rezeptverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.recipe.Recipe;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.helpers.interfaces.IShowSingleTable;
import de.palaver.view.bean.helpers.wrappers.RecipeWrapper;

public class ShowRecipesBean  extends TemplateBuilder implements View, IShowSingleTable {
	private static final long serialVersionUID = 7835379225675541891L;
	private static final String TITLE = "Rezepten Anzeigen";
	
	private Recipe m_recipe;
	private BeanItemContainer<RecipeWrapper> m_container;
	
	public ShowRecipesBean() {
		super();
		m_container = null;
		componetsManager();
		templateBuilder();
		listeners();
		beans();
	}

	private void componetsManager() {
		m_filterControlPanel = filterHorisontalLayoutWithHeadTitle(TITLE, STYLE_HEADLINE_STANDART, WIDTH_FULL);
		m_filterTable = HTMLComponents.filterTable(true, true, WIDTH_FULL, WIDTH_FULL);
		m_control = controlPanel(this);			
	}

	private void templateBuilder() {
		defaultShowPageFilterTable(m_filterControlPanel, m_filterTable, m_control);
	}

	@SuppressWarnings("serial")
	private void listeners() {
		m_filterTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					m_buttonEdit.setEnabled(true);
					m_recipe = ((RecipeWrapper) event.getProperty().getValue()).getRecipe();
				}
			}
		});

		m_filterTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					m_buttonEdit.click();
				}
			}
		});
		
		m_buttonEdit.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (m_recipe != null) {
					ViewHandler.getInstance().switchView(ChangeRecipeBean.class, new ViewDataObject<Recipe>(m_recipe));
				}
				else {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REZEPTANZEIGEN_SELECT);
				}
			}
		});
		
		m_buttonCreate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(ChangeRecipeBean.class);
			}
		});	
		
		m_buttonFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_filterTable.resetFilters();
			}
		});
	}

	private void beans() {
		try {
			m_container = new BeanItemContainer<RecipeWrapper>(RecipeWrapper.class, RecipeWrapper.getRecipeWrappers());
			setTable();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void setTable() {
		m_filterTable.setContainerDataSource(m_container);
		m_filterTable.setVisibleColumns(new Object[] { "name",  "recipetype", "employee", "erstellt", "kommentar"});
		m_filterTable.sort(new Object[] { "name" }, new boolean[] { true });
		
		m_filterTable.setColumnHeader("employee", "Mitarbeiter");
		m_filterTable.setColumnHeader("recipetype", "Rezeptart");
		
		m_filterTable.setFilterFieldValue("employee", ((Application) UI.getCurrent().getData()).getUser().getBenutzername());
	}

	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Recipe) {
			m_recipe = (Recipe)((ViewDataObject<?>) data).getData(); 
			m_filterTable.resetFilters();
			m_filterTable.setFilterFieldValue("name", ((Recipe)((ViewDataObject<?>) data).getData()).getName());
		}
	}
}

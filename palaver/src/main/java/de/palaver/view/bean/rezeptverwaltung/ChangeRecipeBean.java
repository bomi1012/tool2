package de.palaver.view.bean.rezeptverwaltung;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.view.bean.helpers.HTMLComponents;
import de.palaver.view.bean.helpers.TemplateBuilder;
import de.palaver.view.bean.helpers.wrappers.RezeptHasArtikelWrapper;

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

	public ChangeRecipeBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listener();
		beans();
		dragAndDrop();
	}



	private void init() {
		m_rezeptHasArtikelWrappers = new ArrayList<RezeptHasArtikelWrapper>();
		m_toCreate = true;
		
	}

	private void componetsManager() {
		m_headLine = title("Rezept anlegen", STYLE_HEADLINE_STANDART);
		m_nameField = textField("Rezeptname", WIDTH_FULL, true, "Rezeptname", this);
		m_employeeSelect = nativeSelect("Mitarbeiter", WIDTH_FULL, true, "Mitarbeiter", this);
		m_recipetypeSelect = nativeSelect("Rezeptart", WIDTH_FULL, true, "Rezeptart", this);
		m_commentField = textArea("Kommentar", WIDTH_FULL, "60", false, "Kommentar", this);
		
		m_zubereitungColSelect = twinColSelect(null, "verfügbare Zubereitung", "ausgewählte Zubereitung", 8, 0, true, true);	
		
		//Konfiguriere Tabelle Reztepte
		m_table = HTMLComponents.table(true, true, WIDTH_FULL, WIDTH_FULL);
		m_table.setStyleName("palaverTable");
		m_table.setCaption("Zutatenliste");
		m_table.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);

		//Konfiguriere Tabelle der Artikel
		m_filterTable = HTMLComponents.filterTable(true, true, WIDTH_FULL, WIDTH_FULL);
		m_filterTable.setStyleName("palaverTable");
		m_filterTable.setCaption("Zutatenliste");
		m_filterTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		
		m_control = controlPanel(this);	
		
	}

	private void templateBuilder() {
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
			horizontalLayout.addComponent(m_zubereitungColSelect);
			horizontalLayout.setExpandRatio(verticalLayout, 1);
			horizontalLayout.setExpandRatio(m_zubereitungColSelect, 1);
			horizontalLayout.setComponentAlignment(verticalLayout, Alignment.TOP_LEFT);
			horizontalLayout.setComponentAlignment(m_zubereitungColSelect, Alignment.TOP_RIGHT);
			
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

	private void listener() {
		// TODO Auto-generated method stub
		
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
				m_table.markAsDirty();
				m_filterTable.markAsDirty();
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
					m_table.markAsDirty();
					m_filterTable.markAsDirty();
				}				
			}
		});
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

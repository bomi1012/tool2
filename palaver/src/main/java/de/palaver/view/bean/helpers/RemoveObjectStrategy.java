package de.palaver.view.bean.helpers;

import com.vaadin.ui.UI;

import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.artikel.Artikel;
import de.palaver.management.artikel.Kategorie;
import de.palaver.management.artikel.Lagerort;
import de.palaver.management.artikel.Mengeneinheit;
import de.palaver.management.artikel.service.ArtikelService;
import de.palaver.management.artikel.service.KategorieService;
import de.palaver.management.artikel.service.LagerortService;
import de.palaver.management.artikel.service.MengeneinheitService;
import de.palaver.management.recipe.Recipe;
import de.palaver.management.recipe.service.RecipeService;
import de.palaver.management.supplier.Ansprechpartner;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.service.AnsprechpartnerService;
import de.palaver.management.supplier.service.SupplierService;
import de.palaver.view.bean.artikelverwaltung.ShowItemsBean;
import de.palaver.view.bean.artikelverwaltung.ShowKategoriesBean;
import de.palaver.view.bean.artikelverwaltung.ShowQuantitiesUnitBean;
import de.palaver.view.bean.artikelverwaltung.ShowWarehousesBean;
import de.palaver.view.bean.lieferantenverwaltung.ChangeSupplierBean;
import de.palaver.view.bean.lieferantenverwaltung.ShowSuppliersBean;
import de.palaver.view.bean.rezeptverwaltung.ShowRecipesBean;

public class RemoveObjectStrategy {

	protected static final String MESSAGE_SUSSEFULL_ARG_REMOVE = "%s wurde erfolgreich gelöscht";
	protected static final String MESSAGE_FAIL_ARG_REMOVE= "Felehr beim Löschen: Element wurde nicht gelöscht";

	private Object m_object;

	public RemoveObjectStrategy(Object object) {
		m_object = object;
	}
	
	public void provider() {
		try {
			String element = null;
			if (m_object instanceof Recipe) {	
				element = "Rezept";
				RecipeService.getInstance().removeRecipeFromDB(((Recipe) m_object).getId());				
				ViewHandler.getInstance().switchView(ShowRecipesBean.class);
			} else if (m_object instanceof Lagerort) {
				element = "Lagerort";
				LagerortService.getInstance().removeLagerort(((Lagerort) m_object).getId());
				ViewHandler.getInstance().switchView(ShowWarehousesBean.class);
			} else if (m_object instanceof Mengeneinheit) {
				element = "Mengeneineit";
				MengeneinheitService.getInstance().removeMengeneinheit(((Mengeneinheit) m_object).getId());
				ViewHandler.getInstance().switchView(ShowQuantitiesUnitBean.class);
			} else if (m_object instanceof Kategorie) {
				element = "Kategorie";
				KategorieService.getInstance().removeKategorie(((Kategorie) m_object).getId());
				ViewHandler.getInstance().switchView(ShowKategoriesBean.class);
			} else if (m_object instanceof Artikel) {
				element = "Artikel";
				ArtikelService.getInstance().removeArtikel(((Artikel) m_object).getId());
				ViewHandler.getInstance().switchView(ShowItemsBean.class);
			} else if (m_object instanceof Supplier) {
				element = "Supplier";
				SupplierService.getInstance().removeSupplier((Supplier) m_object);
				ViewHandler.getInstance().switchView(ShowSuppliersBean.class);
			} else if (m_object instanceof Ansprechpartner) {
				element = "Ansprechpartner";
				AnsprechpartnerService.getInstance().removeAnsprechpartner(((Ansprechpartner) m_object).getId());
				ViewHandler.getInstance().switchView(ChangeSupplierBean.class, 
						new ViewDataObject<Supplier>(((Ansprechpartner) m_object).getLieferant()));
			}
			((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_REMOVE, 
					element));	
		} catch (Exception e) {
			((Application) UI.getCurrent().getData()).showDialog(MESSAGE_FAIL_ARG_REMOVE);	
			e.printStackTrace();
		} 
	}
}

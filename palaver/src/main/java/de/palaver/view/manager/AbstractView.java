package de.palaver.view.manager;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AbstractView extends VerticalLayout{
	private static final long serialVersionUID = -6995479031013214882L;
	
	protected static final String WIDTH_FULL = "100%";
	protected static final String STYLE_HEADLINE_STANDART = "ViewHeadline";
	protected static final String STYLE_HEADLINE_SUB = "subHeadline";
	
	protected static final String FIELD_NAME = "name";
	protected static final String FIELD_KURZ = "kurz";
	protected static final String FIELD_ARTIKEL_NR = "artikelnr";
	protected static final String FIELD_ARTIKEL_NAME = "artikelName";
	protected static final String FIELD_LIEFERANT = "lieferant";
	protected static final String FIELD_KATEGORIE = "kategorie";
	protected static final String FIELD_LAGERORT = "lagerort";
	protected static final String FIELD_MITARBEITER = "mitarbeiter";
	protected static final String FIELD_MENGENEINHEIT = "mengeneinheit";
	protected static final String FIELD_PREIS = "preis";
	protected static final String FIELD_STANDARD = "standard";
	protected static final String FIELD_GRUNDBEDARF = "grundbedarf";
	protected static final String FIELD_BESTELLGROESSE = "bestellgroesse";
	protected static final String FIELD_BESTELLGROESSE_LT2 = "bestellgroesseLT2";
	protected static final String FIELD_LIEFERDATUM_1 = "lieferdatum1";
	protected static final String FIELD_LIEFERDATUM_2 = "lieferdatum2";
	protected static final String FIELD_DATUM = "datum";
	protected static final String FIELD_NOTIZ = "notiz";
	protected static final String FIELD_STATUS = "status";
	protected static final String FIELD_SUMME_1 = "summe1";
	protected static final String FIELD_SUMME_2 = "summe2";
	protected static final String FIELD_IGNORE = "ignore";
	
	protected static final String CREATE_NEW_LIEFERANT = "Erstelen neuen Lieferant";
	protected static final String CREATE_NEW_MENGENEINHEIT = "Erstelen neue Mengeeinheit";
	protected static final String CREATE_NEW_KATEGORIE = "Erstelen neue Kategorie";
	protected static final String CREATE_NEW_LAGERORT = "Erstelen neuen Lagerort";
	
	protected static final String MESSAGE_LEER_ARG_1 = "Tragen Sie bitte im Feld %s den Wert ein";
	protected static final String MESSAGE_EXISTS_ARG_1 = "Der Name %s ist bereits im System vorhanden";	
	protected static final String MESSAGE_SUSSEFULL_ARG_1 = "%s wurde erfolgreich gespeichert";
	
	
	public class Hr extends Label {
		@SuppressWarnings("deprecation")
		public Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}
	
	protected Window windowUI(String title, boolean isModal, boolean isResizable, String width, String height) {
		Window win = new Window(title);
		win.setModal(isModal);
		win.setResizable(isResizable);
		win.setWidth(width);
		win.setHeight(height);
		
		return win;
	}
}

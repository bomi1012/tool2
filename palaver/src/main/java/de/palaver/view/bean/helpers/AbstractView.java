package de.palaver.view.bean.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.palaver.view.layout.popup.YesNoPopup;

public class AbstractView extends VerticalLayout {
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
	protected static final String FIELD_DATUM = "datum";
	protected static final String FIELD_NOTIZ = "notiz";

	protected static final String CREATE_NEW_LIEFERANT = "Erstelen neuen Lieferant";
	protected static final String CREATE_NEW_MENGENEINHEIT = "Erstelen neue Mengeeinheit";
	protected static final String CREATE_NEW_KATEGORIE = "Erstelen neue Kategorie";
	protected static final String CREATE_NEW_LAGERORT = "Erstelen neuen Lagerort";
	protected static final String CREATE_NEW_ARTIKEL = "Erstelen neuen Artikel";

	protected static final String CHANGE_OLD_PASSWORD = "Passwort ändern";

	protected static final String MESSAGE_LEER_ARG_1 = "Tragen Sie bitte im Feld %s den Wert ein";
	protected static final String MESSAGE_EXISTS_ARG_1 = "Der Name %s ist bereits im System vorhanden";
	protected static final String MESSAGE_SUSSEFULL_ARG_1 = "%s wurde erfolgreich gespeichert";


	private RemoveObjectStrategy m_strategy;
	private Map<Component, Object> m_data;
	protected YesNoPopup m_yesNoPopup;

	public Map<Component, Object> getData() {
		return m_data;
	}

	public AbstractView() {
		super();
		m_data = new HashMap<Component, Object>();
	}

	public class Hr extends Label {
		@SuppressWarnings("deprecation")
		public Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}

	protected Window windowUI(String title, boolean isModal,
			boolean isResizable, String width, String height) {
		Window win = new Window(title);
		win.setModal(isModal);
		win.setResizable(isResizable);
		win.setWidth(width);
		win.setHeight(height);

		return win;
	}

	protected void setValueToComponent(Map<Component, Object> hashMap) {
		for (Entry<Component, Object> element : hashMap.entrySet()) {
			if (element.getKey() instanceof TextField)
				((TextField) element.getKey()).setValue((String) element
						.getValue());
			if (element.getKey() instanceof NativeSelect)
				((NativeSelect) element.getKey()).setValue(element.getValue());
			if (element.getKey() instanceof TextArea)
				((TextArea) element.getKey()).setValue((String) element
						.getValue());
		}
	}

	@SuppressWarnings("serial")
	protected void windowModalYesNoRemove(final Object object) {
		final Window window = windowUI("Element löschen", true, false, "450",
				"180");
		m_yesNoPopup = new YesNoPopup("32x32/flag.png",
				"Möchten Sie wirklich den Element löschen?");
		addComponent(m_yesNoPopup);
		window.setContent(m_yesNoPopup);

		UI.getCurrent().addWindow(window);
		m_yesNoPopup.m_yesButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_strategy = new RemoveObjectStrategy(object);
				m_strategy.provider();
				window.close();
			}
		});
		m_yesNoPopup.m_noButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
			}
		});
	}
}

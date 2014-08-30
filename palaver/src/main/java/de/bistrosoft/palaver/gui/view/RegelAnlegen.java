package de.bistrosoft.palaver.gui.view;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.management.menu.Fussnote;
import de.palaver.management.menu.Geschmack;
import de.palaver.management.menu.Menutype;
import de.palaver.management.recipe.Zubereitung;
import de.palaver.management.recipe.service.RecipeService;

@SuppressWarnings({ "serial", "deprecation" })
public class RegelAnlegen extends VerticalLayout implements View,
		ValueChangeListener {
	// Variablen und Komponenten
	HorizontalLayout box = new HorizontalLayout();
	HorizontalLayout box2 = new HorizontalLayout();
	VerticalLayout links2 = new VerticalLayout();
	VerticalLayout mitte2 = new VerticalLayout();
	VerticalLayout rechts2 = new VerticalLayout();
	private VerticalLayout fenster = new VerticalLayout();
	HorizontalLayout control = new HorizontalLayout();
	VerticalLayout links = new VerticalLayout();
	VerticalLayout mitte = new VerticalLayout();
	VerticalLayout rechts = new VerticalLayout();

	private NativeSelect regeltyp = new NativeSelect("Regeltyp");
	private NativeSelect operator = new NativeSelect("Operator");
	private OptionGroup spalten = new OptionGroup("Spalten");
	private OptionGroup zeilen = new OptionGroup("Zeilen");
	private TwinColTouch kriterienFussnote = new TwinColTouch("Kriterien");
	private TwinColTouch kriterienGeschmack = new TwinColTouch("Kriterien");
	private TwinColTouch kriterienZubereitung = new TwinColTouch("Kriterien");
	private TwinColTouch kriterienMenueart = new TwinColTouch("Kriterien");
	private TextField kriterienText = new TextField("Kriterium");
	private TextArea fehlermeldung = new TextArea("Fehlermeldung");
	private CheckBox aktiv = new CheckBox("Aktiv");
	private CheckBox ignorierRegel = new CheckBox("Ignorierbar");
	private Button speichern = new Button(IConstants.BUTTON_SAVE);
	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button bearbeiten = new Button(IConstants.BUTTON_SAVE);
	private Label label;
	private Regel regel;

	String zeileninput;
	String spalteninput;
	String regeltypinput;
	String operatorinput;
	String kriterieninput;
	String fehlermeldunginput;

	BeanItemContainer<Zubereitung> zubereitungContainer;
	BeanItemContainer<Fussnote> fussnoteContainer;
	BeanItemContainer<Geschmack> geschmackContainer;
	BeanItemContainer<Menutype> menueartContainer;

	List<String> operatorinhalt = Arrays.asList(
			IConstants.INFO_REGEL_OPERATOR_ERLAUBT,
			IConstants.INFO_REGEL_OPERATOR_VERBOTEN,
			IConstants.INFO_REGEL_OPERATOR_MAXIMAL);
	List<String> regeltypinhalt = Arrays.asList(
			IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG,
			IConstants.INFO_REGEL_REGELTYP_FUSSNOTE,
			IConstants.INFO_REGEL_REGELTYP_GESCHMACK,
			IConstants.INFO_REGEL_REGELTYP_MENUEART,
			IConstants.INFO_REGEL_REGELTYP_AUFWAND,
			IConstants.INFO_REGEL_REGELTYP_MENUE);
	List<String> zeileninhalt = Arrays.asList(IConstants.INFO_REGEL_ZEILE_1,
			IConstants.INFO_REGEL_ZEILE_2, IConstants.INFO_REGEL_ZEILE_3,
			IConstants.INFO_REGEL_ZEILE_4, IConstants.INFO_REGEL_ZEILE_5);
	List<String> spalteninhalt = Arrays.asList(IConstants.INFO_REGEL_SPALTE_1,
			IConstants.INFO_REGEL_SPALTE_2, IConstants.INFO_REGEL_SPALTE_3,
			IConstants.INFO_REGEL_SPALTE_4, IConstants.INFO_REGEL_SPALTE_5);

	BeanItemContainer<String> operatorcontainer = new BeanItemContainer<String>(
			String.class, operatorinhalt);
	BeanItemContainer<String> regeltypcontainer = new BeanItemContainer<String>(
			String.class, regeltypinhalt);

	// Konstruktur der für das bearbeiten einer Regel verwendet wird
	public RegelAnlegen(Regel regel) {
		label = new Label("Regel bearbeiten");
		layout();
		check();
		ChangeListener();

	}

	// Konstruktor der für das Anlegen einer neuen Regel verwendet wird
	public RegelAnlegen() {
		label = new Label("Regel erstellen");
		layout();
		check();
		aktiv.setValue(true);
		ChangeListener();
		speichern();

	}

	// Methode um das Layout beim Regel anlegen und bearbeiten festzulegen
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);

		label = new Label("Neue Regel");
		label.setStyleName("ViewHeadline");

		fenster.setWidth("900px");

		links.setWidth("150px");
		links.setSpacing(true);

		mitte.setWidth("300px");
		mitte.setSpacing(true);

		rechts.setWidth("300px");
		rechts.setSpacing(true);

		box.setWidth("900px");
		box.addComponent(links);
		box.setComponentAlignment(links, Alignment.TOP_CENTER);
		box.addComponent(mitte);
		box.setComponentAlignment(mitte, Alignment.TOP_LEFT);

		box2.setWidth("900px");
		links2.setWidth("150px");
		mitte2.setWidth("300px");
		rechts2.setWidth("300px");
		box2.addComponent(links2);
		box2.addComponent(mitte2);
		box2.addComponent(rechts2);

		links.addComponent(zeilen);
		links.addComponent(spalten);
		mitte.addComponent(regeltyp);
		mitte.addComponent(operator);
		rechts.addComponent(fehlermeldung);
		rechts.addComponent(ignorierRegel);
		rechts.addComponent(aktiv);

		box.addComponent(rechts);
		box.setComponentAlignment(rechts, Alignment.TOP_LEFT);

		control.setSpacing(true);
		control.addComponents(verwerfen, speichern);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
			}
		});

		links2.addComponent(label);
		box2.setComponentAlignment(links2, Alignment.TOP_CENTER);
		fenster.addComponent(box2);
		fenster.addComponent(box);
		fenster.setComponentAlignment(box, Alignment.TOP_CENTER);
		fenster.addComponent(control);
		fenster.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);

		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);

		for (String z : zeileninhalt) {
			zeilen.addItem(z);
		}
		for (String z : spalteninhalt) {
			spalten.addItem(z);
		}

		zeilen.setImmediate(true);
		zeilen.setMultiSelect(true);
		zeilen.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (zeilen.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_ZEILEN);
				}

			}
		});

		spalten.setImmediate(true);
		spalten.setMultiSelect(true);
		spalten.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (spalten.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_SPALTEN);
				}

			}
		});

		regeltyp.setWidth("95%");
		regeltyp.setImmediate(true);
		regeltyp.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_REGELTYP, 3, 500, false));
		regeltyp.setContainerDataSource(regeltypcontainer);

		operator.setWidth("95%");
		operator.setImmediate(true);
		operator.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_OPERATOR, 3, 500, false));
		operator.setContainerDataSource(operatorcontainer);

		kriterienFussnote.setWidth("90%");
		kriterienFussnote.setImmediate(true);
		kriterienFussnote.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (kriterienFussnote.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_KRITERIEN_TWIN);
				}

			}
		});

		kriterienGeschmack.setWidth("90%");
		kriterienGeschmack.setImmediate(true);
		kriterienGeschmack.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (kriterienGeschmack.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_KRITERIEN_TWIN);
				}

			}
		});

		kriterienMenueart.setWidth("90%");
		kriterienMenueart.setImmediate(true);
		kriterienMenueart.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (kriterienMenueart.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_KRITERIEN_TWIN);
				}

			}
		});
		kriterienZubereitung.setWidth("90%");
		kriterienZubereitung.setImmediate(true);
		kriterienZubereitung.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (kriterienZubereitung.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_KRITERIEN_TWIN);
				}

			}
		});

		kriterienText.setWidth("90%");
		kriterienText.setImmediate(true);
		kriterienText.addValidator(new IntegerValidator(
				IConstants.INFO_REGEL_KRITERIEN_ZAHL));

		fehlermeldung.setWidth("100%");
		fehlermeldung.setImmediate(true);
		fehlermeldung.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_FEHLERMELDUNG, 3, 500, false));

	}

	// Clicklistener für das Speichern einer neuen Regel
	private void speichern() {
		speichern.addClickListener(new ClickListener() {

			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// Regel speichern

				if (validiereEingabe()) {
					Regel neueRegel = new Regel();
					neueRegel.setRegeltyp(regeltypinput);
					neueRegel.setZeile(zeileninput);
					neueRegel.setSpalte(spalteninput);
					neueRegel.setOperator(operatorinput);
					neueRegel.setKriterien(kriterieninput);
					neueRegel.setFehlermeldung(fehlermeldunginput);
					neueRegel.setAktiv(true);
					neueRegel.setIgnorierbar(ignorierRegel.getValue());
					Regel.speichern(neueRegel);

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REGEL_SAVE);

					ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
				}
			}
		});
	}

	// ChangeListener für die Felder
	private void ChangeListener() {

		zeilen.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				zeileninput = valueString;
			}
		});

		spalten.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				spalteninput = valueString;
			}
		});

		regeltyp.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				regeltypinput = valueString;

				// Falls Regeltyp Aufwand oder Menue ausgewühlt wird, wird ein
				// Textfeld eingefügt und aus dem Container für Operator die
				// Auswahlmüglichkeiten "verbotene Werte" und "muss enthalten"
				// entfernt
				if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_AUFWAND
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUE) {
					mitte.removeComponent(kriterienFussnote);
					mitte.removeComponent(kriterienGeschmack);
					mitte.removeComponent(kriterienMenueart);
					mitte.removeComponent(kriterienZubereitung);
					mitte.addComponent(kriterienText);
					operator.removeItem(IConstants.INFO_REGEL_OPERATOR_ERLAUBT);
					operator.removeItem(IConstants.INFO_REGEL_OPERATOR_VERBOTEN);

					// Falls Regeltyp Zubereitung ist
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG) {
					// falls Operator "maximale Anzahl"
					if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL) {
						// dann die TwinCols raus und ein Textfeld einfügen
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.addComponent(kriterienText);
					}
					// Ansonsten TwinCol für Zubereitung hinufügen und alle
					// anderen Komontenen entfernen
					else {
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienZubereitung);
					}
					// falls Container für Operator "verbotene Werte" oder
					// "muss enthalten" nicht enthült, werden diese hinzugefügt
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_ERLAUBT);
					}
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_VERBOTEN);
					}
					// Falls Regeltyp Menueart ist
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART) {
					// falls Operator "maximale Anzahl"
					if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL) {
						// dann die TwinCols raus und ein Textfeld einfügen
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.addComponent(kriterienText);
					}
					// Ansonsten TwinCol für Menueart hinufügen und alle
					// anderen Komontenen entfernen
					else {
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienZubereitung);
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienMenueart);
					}
					// falls Container für Operator "verbotene Werte" oder
					// "muss enthalten" nicht enthült, werden diese hinzugefügt
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_ERLAUBT);
					}
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_VERBOTEN);
					}
				}
				// Falls Regeltyp Geschmack ist
				else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK) {
					// falls Operator "maximale Anzahl"
					if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL) {
						// dann die TwinCols raus und ein Textfeld einfügen
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.addComponent(kriterienText);
					} 
					// Ansonsten TwinCol für Geschmack hinufügen und alle
					// anderen Komontenen entfernen
					else {
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienGeschmack);
					}
					// falls Container für Operator "verbotene Werte" oder
					// "muss enthalten" nicht enthült, werden diese hinzugefügt
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_ERLAUBT);
					}
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_VERBOTEN);
					}
				} 
				// Falls Regeltyp Fuünote ist
				else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE) {
					// falls Operator "maximale Anzahl"
					if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL) {
						// dann die TwinCols raus und ein Textfeld einfügen
						mitte.removeComponent(kriterienFussnote);
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.addComponent(kriterienText);
					}
					// Ansonsten TwinCol für Fuünote hinufügen und alle
					// anderen Komontenen entfernen
					else {
						mitte.removeComponent(kriterienGeschmack);
						mitte.removeComponent(kriterienMenueart);
						mitte.removeComponent(kriterienZubereitung);
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienFussnote);
					}
					// falls Container für Operator "verbotene Werte" oder
					// "muss enthalten" nicht enthült, werden diese hinzugefügt
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_ERLAUBT);
					}
					if (!operator
							.containsId(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
						operator.addItem(IConstants.INFO_REGEL_OPERATOR_VERBOTEN);
					}
				}
			}

		});

		operator.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				operatorinput = valueString;

				// Falls beim Feld Operator "maximale Anzahl" ausgewühlt wird, wird ein Textfeld
				// eingefügt
				if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL) {
					mitte.removeComponent(kriterienFussnote);
					mitte.removeComponent(kriterienGeschmack);
					mitte.removeComponent(kriterienMenueart);
					mitte.removeComponent(kriterienZubereitung);
					mitte.addComponent(kriterienText);

					// Falls beim Regeltyp Aufwand oder Menue ausgewühlt wird, so
					// wird ein Textfeld eingefügt
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_AUFWAND
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUE) {
					mitte.removeComponent(kriterienFussnote);
					mitte.removeComponent(kriterienGeschmack);
					mitte.removeComponent(kriterienMenueart);
					mitte.removeComponent(kriterienZubereitung);
					mitte.addComponent(kriterienText);
				}
				// Falls beim Operator "verbotene Werte" oder "muss enthalten" ausgewühlt wird
				else if (operatorinput == IConstants.INFO_REGEL_OPERATOR_ERLAUBT
						|| operatorinput == IConstants.INFO_REGEL_OPERATOR_VERBOTEN) {
					// Wenn Beim Regeltyp Menueart ausgewühlt wird, wird twincol für Menueart einfügt 
					if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART
							|| regeltyp
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_REGELTYP_MENUEART)) {
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienMenueart);
					}
					// Wenn beim Regeltyo Fuünote ausgewühlt wird, wird twincol für Fuünote eingefügt
					if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE
							|| regeltyp
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)) {
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienFussnote);
					}
					// Wenn beim Regeltyo Geschmack ausgewühlt wird, wird twincol für Geschmack eingefügt
					if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK
							|| regeltyp
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_REGELTYP_GESCHMACK)) {
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienGeschmack);
					}
					// Wenn beim Regeltyo Zubereitung ausgewühlt wird, wird twincol für Zubereitung eingefügt
					if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG
							|| regeltyp
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)) {
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienZubereitung);
					}
				}
			}
		});

		kriterienFussnote.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		kriterienGeschmack.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		kriterienMenueart.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		kriterienZubereitung.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		kriterienText.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				// valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		fehlermeldung.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				fehlermeldunginput = valueString;
			}
		});

	}

	// Diese Methode dient dazu, die Container je nach ausgewühltem Regeltyp zu
	// setzen
	public void check() {
		try {

			// Container
			List<Zubereitung> zb = RecipeService.getInstance()
					.getAllZubereitungen();
			List<Fussnote> fn = Fussnotenverwaltung.getInstance()
					.getAllFussnote();
			List<Menutype> ma = Menueartverwaltung.getInstance()
					.getAllMenueart();
			List<Geschmack> g = Geschmackverwaltung.getInstance()
					.getAllGeschmack();

			// Container setzen
			for (Zubereitung z : zb) {
				kriterienZubereitung.addItem(z.getName());
			}
			for (Fussnote f : fn) {
				kriterienFussnote.addItem(f.getName());
			}

			for (Geschmack ge : g) {
				kriterienGeschmack.addItem(ge.getName());
			}

			for (Menutype m : ma) {
				kriterienMenueart.addItem(m.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Diese Methode wird aufgerufen, sobald man eine Regel per Doppelklick
	// auswühlt
	public void getViewParam(ViewData data) {
		// Angeklickte Regel
		regel = (Regel) ((ViewDataObject<?>) data).getData();

		// Speichern Button mit Bearbeiten Button ersetzen
		control.replaceComponent(speichern, bearbeiten);

		// Werte setzen
		label.setValue("Regel bearbeiten");
		regeltyp.setValue(regel.getRegeltyp());
		operator.setValue(regel.getOperator());
		fehlermeldung.setValue(regel.getFehlermeldung());
		aktiv.setValue(regel.getAktiv());
		ignorierRegel.setValue(regel.getIgnorierbar());

		List<String> sp = Arrays.asList(regel.getSpalte().split("\\s*,\\s*"));
		for (String s : sp) {
			spalten.select(s);
		}
		List<String> zl = Arrays.asList(regel.getZeile().split("\\s*,\\s*"));
		for (String z : zl) {
			zeilen.select(z);
		}

		List<String> kr = Arrays
				.asList(regel.getKriterien().split("\\s*,\\s*"));
		try {

			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)) {
				if (regel.getOperator().equals(
						IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
						|| regel.getOperator().equals(
								IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
					mitte.addComponent(kriterienZubereitung);
					for (String z : kr) {
						kriterienZubereitung.select(z);
					}
				} else {
					mitte.addComponent(kriterienText);
					kriterienText.setValue(regel.getKriterien());
				}
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)) {
				if (regel.getOperator().equals(
						IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
						|| regel.getOperator().equals(
								IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
					mitte.addComponent(kriterienFussnote);
					for (String z : kr) {
						kriterienFussnote.select(z);
					}
				} else {
					mitte.addComponent(kriterienText);
					kriterienText.setValue(regel.getKriterien());
				}
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_GESCHMACK)) {
				if (regel.getOperator().equals(
						IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
						|| regel.getOperator().equals(
								IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
					mitte.addComponent(kriterienGeschmack);
					for (String z : kr) {
						kriterienGeschmack.select(z);
					}
				} else {
					mitte.addComponent(kriterienText);
					kriterienText.setValue(regel.getKriterien());
				}
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_MENUEART)) {
				if (regel.getOperator().equals(
						IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
						|| regel.getOperator().equals(
								IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
					mitte.addComponent(kriterienMenueart);
					for (String z : kr) {
						kriterienMenueart.select(z);
					}
				} else {
					mitte.addComponent(kriterienText);
					kriterienText.setValue(regel.getKriterien());
				}
			}

			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_AUFWAND)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_MENUE)) {
				mitte.removeComponent(kriterienFussnote);
				mitte.removeComponent(kriterienGeschmack);
				mitte.removeComponent(kriterienMenueart);
				mitte.removeComponent(kriterienZubereitung);
				mitte.addComponent(kriterienText);
				kriterienText.setValue(regel.getKriterien());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		bearbeiten.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					regel.setZeile(zeilen
							.getValue()
							.toString()
							.substring(1,
									zeilen.getValue().toString().length() - 1));
					regel.setSpalte(spalten
							.getValue()
							.toString()
							.substring(1,
									spalten.getValue().toString().length() - 1));
					regel.setRegeltyp(regeltyp.getValue().toString());
					regel.setOperator(operator.getValue().toString());
					if (operator.getValue().toString()
							.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
							|| operator
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						if (regeltyp
								.getValue()
								.toString()
								.equals(IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)) {
							regel.setKriterien(kriterienFussnote
									.getValue()
									.toString()
									.substring(
											1,
											kriterienFussnote.getValue()
													.toString().length() - 1));
						}
						if (regeltyp
								.getValue()
								.toString()
								.equals(IConstants.INFO_REGEL_REGELTYP_GESCHMACK)) {
							regel.setKriterien(kriterienGeschmack
									.getValue()
									.toString()
									.substring(
											1,
											kriterienGeschmack.getValue()
													.toString().length() - 1));
						}
						if (regeltyp
								.getValue()
								.toString()
								.equals(IConstants.INFO_REGEL_REGELTYP_MENUEART)) {
							regel.setKriterien(kriterienMenueart
									.getValue()
									.toString()
									.substring(
											1,
											kriterienMenueart.getValue()
													.toString().length() - 1));
						}
						if (regeltyp
								.getValue()
								.toString()
								.equals(IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)) {
							regel.setKriterien(kriterienZubereitung
									.getValue()
									.toString()
									.substring(
											1,
											kriterienZubereitung.getValue()
													.toString().length() - 1));
						}
					} else {
						regel.setKriterien(kriterienText.getValue());
					}
					regel.setFehlermeldung(fehlermeldung.getValue());
					regel.setAktiv(aktiv.getValue());
					regel.setIgnorierbar(ignorierRegel.getValue());

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REGEL_EDIT);

					try {
						Regelverwaltung.getInstance().updateRegel(regel);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
			}
		});
	}

	private Boolean validiereEingabe() {
		if (zeilen.getValue().toString() == "[]") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_ZEILEN);
			return false;
		}
		if (spalten.getValue().toString() == "[]") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_SPALTEN);
			return false;
		}
		if (regeltyp.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_REGELTYP);
			return false;
		}
		if (operator.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_OPERATOR);
			return false;
		}
		if (mitte.getComponent(2) == kriterienText) {
			if (kriterienText.getValue().isEmpty()) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_ZAHL);
				return false;
			}
		}
		if (mitte.getComponent(2) == kriterienFussnote) {
			if (kriterienFussnote.getValue().toString() == "[]") {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_TWIN);
				return false;
			}
		}
		if (mitte.getComponent(2) == kriterienGeschmack) {
			if (kriterienGeschmack.getValue().toString() == "[]") {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_TWIN);
				return false;
			}
		}
		if (mitte.getComponent(2) == kriterienMenueart) {
			if (kriterienMenueart.getValue().toString() == "[]") {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_TWIN);
				return false;
			}
		}
		if (mitte.getComponent(2) == kriterienZubereitung) {
			if (kriterienZubereitung.getValue().toString() == "[]") {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_TWIN);
				return false;
			}
		}
		return true;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}
}

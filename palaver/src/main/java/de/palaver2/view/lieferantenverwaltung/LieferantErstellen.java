/**
 * Created by Sebastian Walz
 * 18.04.2013 15:41:31
 */
package de.palaver2.view.lieferantenverwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class LieferantErstellen extends VerticalLayout implements View
{
        private static final Logger        log        = LoggerFactory.getLogger(LieferantErstellen.class.getName());
        
        private HorizontalLayout        box = new HorizontalLayout();
        private VerticalLayout                fenster = new VerticalLayout();
        
        private Label                                headline;
        
        private TextField                        name = new TextField("Name");
        private TextField                        bezeichnung = new TextField("Bezeichnung");
        private TextField                        kundennummer = new TextField("Kundennummer");
        private TextField                        strasse = new TextField("StrassŸe");
        private TextField                        plz = new TextField("PLZ");
        private TextField                        ort = new TextField("Ort");
        private TextField                        email = new TextField("E-Mail");
        private TextField                        telefon = new TextField("Telefon");
        private TextField                        fax = new TextField("Telefax");
        private TextArea                        notiz = new TextArea("Notiz");
        private CheckBox                        mehrereliefertermine = new CheckBox("mehrere Liefertermine");
        
        private Window dialog = new Window();
        
        private Lieferant lieferant = new Lieferant();

        
        private Button                        speichern = new Button(IConstants.BUTTON_SAVE);
        private Button                        verwerfen = new Button(IConstants.BUTTON_DISCARD);
        
        public LieferantErstellen()
        {
                super();
                this.setSizeFull();
                this.setMargin(true);
                
                headline = new Label("Neuer Lieferant");
                headline.setStyleName("ViewHeadline");
                
                name.setWidth("100%");
                bezeichnung.setWidth("100%");
                kundennummer.setWidth("100%");
                strasse.setWidth("100%");
                plz.setWidth("100%");
                ort.setWidth("100%");
                email.setWidth("100%");
                telefon.setWidth("100%");
                fax.setWidth("100%");
                notiz.setWidth("100%");
                notiz.setHeight("92px");
                notiz.setRows(3);
                mehrereliefertermine.setWidth("100%");
                
                fenster.setWidth("900px");
                fenster.setSpacing(true);
                
                VerticalLayout links = new VerticalLayout();
                links.setWidth("250px");
                links.setSpacing(true);
                
                VerticalLayout mitte = new VerticalLayout();
                mitte.setWidth("250px");
                mitte.setSpacing(true);
                
                VerticalLayout rechts = new VerticalLayout();
                rechts.setWidth("250px");
                rechts.setSpacing(true);
                
                box.setWidth("900px");
                box.setSpacing(true);
                box.addComponent(links);
                box.addComponent(mitte);
                
                
                links.addComponent(name);
                links.addComponent(bezeichnung);
                links.addComponent(kundennummer);
                links.addComponent(strasse);
                mitte.addComponent(plz);
                mitte.addComponent(ort);
                mitte.addComponent(email);
                mitte.addComponent(telefon);
                rechts.addComponent(fax);
                rechts.addComponent(notiz);
                rechts.addComponent(mehrereliefertermine);
                
                box.addComponent(rechts);
                
                HorizontalLayout control = new HorizontalLayout();
                control.setWidth("100%");
                control.setSpacing(true);
//                box.addComponent(control);
//                box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
//                
                control.addComponent(verwerfen);
                control.addComponent(speichern);
                
                mitte.addComponent(control);
                mitte.setComponentAlignment(control, Alignment.TOP_CENTER);
                
        
                speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
                verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
                
                
                fenster.addComponent(headline);
                fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
                fenster.addComponent(box);
                fenster.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
                
                this.addComponent(fenster);
                this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
                
                name.setImmediate(true);
                name.setRequired(true);
                name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 3,45, false));
                name.setMaxLength(45);
                
                bezeichnung.setImmediate(true);
                bezeichnung.setMaxLength(45);
                
                kundennummer.setImmediate(true);
                kundennummer.setMaxLength(45);
                
                strasse.setImmediate(true);
                strasse.setMaxLength(45);
                
//        Validator postalCodeValidator = new AbstractStringValidator(
//                "Bitte gÃ¼ltige PLZ eingeben.") {
//                        @Override
//                        protected boolean isValidValue(String value) {
//                return value.matches("[1-9][0-9]{4}");
//                        }
//        };
                
                plz.setImmediate(true);
//                plz.addValidator(postalCodeValidator);
                plz.setMaxLength(45);
                
                ort.setImmediate(true);
                ort.setMaxLength(45);
                
                email.setImmediate(true);
                email.addValidator(new EmailValidator("Bitte gültige E-Mailadresse angeben"));
                email.setMaxLength(45);
                
                telefon.setImmediate(true);
                telefon.setMaxLength(45);
                
                fax.setImmediate(true);
                fax.setMaxLength(45);
                
                notiz.setImmediate(true);
                notiz.setMaxLength(300);
        
    verwerfen.addClickListener(new ClickListener() {
                
                @Override
                public void buttonClick(ClickEvent event) {
                        
                        if (LieferantErstellen.this.getParent() instanceof Window) {
                                Window win = (Window) LieferantErstellen.this.getParent();
                                win.close();
                                UI.getCurrent().removeWindow(dialog);
                        }
                        else {
                                ViewHandler.getInstance().returnToDefault();
                        }
                }
        });
                
        speichern.addClickListener(new ClickListener()
        {                        
                @Override
                public void buttonClick(ClickEvent event)
                {
                        if (validiereEingabe()) {

                                lieferant.setName(name.getValue());
                                lieferant.setBezeichnung(bezeichnung.getValue());
                                lieferant.setKundennummer(kundennummer.getValue());
                                lieferant.setStrasse(strasse.getValue());
                                lieferant.setPlz(plz.getValue());
                                lieferant.setOrt(ort.getValue());
                                lieferant.setEmail(email.getValue());
                                lieferant.setTelefon(telefon.getValue());
                                lieferant.setFax(fax.getValue());
                                lieferant.setNotiz(notiz.getValue());
                                lieferant.setMehrereliefertermine(mehrereliefertermine.getValue());
                                
                                try {
                                        Lieferantenverwaltung.getInstance().createLieferant(lieferant);
                                        ((Application) UI.getCurrent().getData())
                                        .showDialog(IConstants.INFO_LIEFERANT_SAVE);
                                        ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
                                } catch (Exception e) {
                                        ((Application) UI.getCurrent().getData())
                                        .showDialog(IConstants.INFO_LIEFERANT_NOT_SAVE);
                                        log.error(e.toString());
                                }
                                
                                
                                
                        /*        if (LieferantErstellen.this.getParent() instanceof Window) {
                                        Window win = (Window) LieferantErstellen.this.getParent();
                                        win.close();
                                }
                                else {
                                        //ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
                                }*/
                        }
                }
        });
        }

        @Override
        public void getViewParam(ViewData data) {
                // TODO Auto-generated method stub
                
        }
        private Boolean validiereEingabe() {
                if (name.isValid() == false) {
                        ((Application) UI.getCurrent().getData())
                                        .showDialog(IConstants.INFO_Lieferant_NAME);
                        return false;
                }
                if (!email.getValue().isEmpty() && !email.getValue().contains("@")) {
                        ((Application) UI.getCurrent().getData())
                        .showDialog(IConstants.INFO_LIFERANT_EMAIL);
                        return false;
                }
                else {
                        return true;
                }
        }
}
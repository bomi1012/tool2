/**
 * Created by S.Walz
 */
package de.hska.awp.palaver2.gui.layout;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.util.Util;
import de.palaver.Application;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

/**
 * @author Sebastian
 * 
 */
@SuppressWarnings("serial")
public class LoginForm extends VerticalLayout {
	private static final Logger log = LoggerFactory.getLogger(LoginForm.class.getName());

	private HorizontalLayout form = new HorizontalLayout();

	private Embedded logo;
	private TextField username = new TextField("Benutzername");
	private PasswordField password = new PasswordField("Passwort");

	private Button loginButton = new Button("Login");

	private Label message = new Label("Login fehlgeschlagen");

	public LoginForm() {
		super();

		this.setSizeFull();
		form.setWidth("350px");
		form.setHeight("300px");
		form.setStyleName("palaver-login");
		form.setMargin(true);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		form.addComponent(content);
		form.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

		this.addComponent(form);
		this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);

		logo = new Embedded(null, new ThemeResource("img/cafe_palaver_Logo.png"));
		content.addComponent(logo);

		HorizontalLayout messageBar = new HorizontalLayout();
		messageBar.setWidth("100%");
		messageBar.addComponent(message);
		messageBar.addComponent(loginButton);
		messageBar.setExpandRatio(message, 1);
		messageBar.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);

		message.setStyleName("ErrorMessage");

		VerticalLayout fields = new VerticalLayout();
		fields.addComponent(username);
		fields.addComponent(password);
		message.setVisible(false);
		fields.addComponent(messageBar);
		fields.setSpacing(true);
		fields.setWidth("300px");
		username.setWidth("100%");
		password.setWidth("100%");
		// fields.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);
		username.focus();

		content.addComponent(fields);

		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					log.info("Password: " + Util.encryptPassword(password.getValue()));
				} catch (UnsupportedEncodingException e1) {
					log.error(e1.toString());
				} catch (NoSuchAlgorithmException e1) {
					log.error(e1.toString());
				}

				if (username.getValue().equals("demo") && password.getValue().equals("palaverapp")) {
					((Application) UI.getCurrent().getData()).showDialog("Der Benutzer demo ist nicht mehr aktiv.");
					username.focus();
				} else {
					try {
						Mitarbeiter current = MitarbeiterDAO.getInstance().getMitarbeiterByBenutzername(username.getValue());
						if (current.getPasswort() != null && current.getPasswort().equals(Util.encryptPassword(password.getValue()))) {
							((Application) UI.getCurrent().getData()).login(current);

							UI.getCurrent().setContent(((Application) UI.getCurrent().getData()).getLayout());
						} else {
							fail();
						}
					} catch (ConnectException e) {
						log.error(e.toString());
						fail();
					} catch (DAOException e) {
						log.error(e.toString());
						fail();
					} catch (SQLException e) {
						log.error(e.toString());
						fail();
					} catch (UnsupportedEncodingException e) {
						log.error(e.toString());
						fail();
					} catch (NoSuchAlgorithmException e) {
						log.error(e.toString());
						fail();
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.toString());
						fail();
					}
				}
			}
		});

		loginButton.setClickShortcut(KeyCode.ENTER);
	}

	private void fail() {
		// Application.getInstance().showDialog("Login fehlgeschlagen!");
		message.setVisible(true);
		username.focus();
	}
}
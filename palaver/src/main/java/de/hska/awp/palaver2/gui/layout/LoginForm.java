/**
 * 
 */
package de.hska.awp.palaver2.gui.layout;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.util.Util;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class LoginForm extends VerticalLayout
{
	private static final Logger	log	= LoggerFactory.getLogger(LoginForm.class.getName());
	
	private HorizontalLayout 	form = new HorizontalLayout();
	
	private Embedded 			logo;
	private TextField 			username = new TextField("Benutzername");
	private PasswordField 		password = new PasswordField("Password");
	
	private Button 				loginButton = new Button("Login");
	
	public LoginForm()
	{
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
		
		VerticalLayout fields = new VerticalLayout();
		fields.addComponent(username);
		fields.addComponent(password);
		fields.addComponent(loginButton);
		fields.setSpacing(true);
		fields.setWidth("300px");
		username.setWidth("100%");
		password.setWidth("100%");
		fields.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);
		username.focus();
		
		content.addComponent(fields);
		
		loginButton.addClickListener(new ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				try
				{
					log.info("Password: " + Util.encryptPassword(password.getValue()));
				} 
				catch (UnsupportedEncodingException e1)
				{
					log.error(e1.toString());
				} 
				catch (NoSuchAlgorithmException e1)
				{
					log.error(e1.toString());
				}
				
				if (username.getValue().equals("demo") && password.getValue().equals("palaverapp"))
				{
					Application.getInstance().login(username.getValue());
					UI.getCurrent().setContent(Application.getInstance().getLayout());
				}
				else 
				{
					try
					{
						Mitarbeiter current = MitarbeiterDAO.getInstance().getMitarbeiterByBenutzername(username.getValue());
						if (current.getPasswort() != null && current.getPasswort().equals(Util.encryptPassword(password.getValue())))
						{
							Application.getInstance().login(username.getValue());
							Application.getInstance().setUser(current);
							UI.getCurrent().setContent(Application.getInstance().getLayout());
						}
						else
						{
							fail();
						}
					} 
					catch (ConnectException e)
					{
						log.error(e.toString());
						fail();
					} 
					catch (DAOException e)
					{
						log.error(e.toString());
						fail();
					} 
					catch (SQLException e)
					{
						log.error(e.toString());
						fail();
					} 
					catch (UnsupportedEncodingException e)
					{
						log.error(e.toString());
						fail();
					} 
					catch (NoSuchAlgorithmException e)
					{
						log.error(e.toString());
						fail();
					}
					catch (Exception e)
					{
						log.error(e.toString());
						fail();
					}
				}	
			}
		});
		
		loginButton.setClickShortcut(KeyCode.ENTER);
	}
	
	private void fail()
	{
		new Notification("Login Failed").show(Page.getCurrent());
		username.focus();
	}
}
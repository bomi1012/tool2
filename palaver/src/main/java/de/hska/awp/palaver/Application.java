package de.hska.awp.palaver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.gui.layout.LoginForm;
import de.hska.awp.palaver2.gui.layout.MainLayout;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * The Application's "main" class
 */
@Theme("palaver")
@SuppressWarnings("serial")
public class Application extends UI
{
    private static final ThreadLocal<Application>	currentApplication	= new ThreadLocal<Application>();

    private static final Logger				log					= LoggerFactory.getLogger(Application.class.getName());
    
    private String 							username;
    private Mitarbeiter						user;
    
    private MainLayout						layout;
    
    /**
     * Zugriff auf "MAIN" Klasse und Session
     * @return Application
     */
    public static Application getInstance()
    {
    	log.info("Instance: " + currentApplication.get());
    	if (currentApplication.get() == null)
    	{
    		log.error("Instance is NULL");
    		return setInstance(new Application());
    	}
    	return currentApplication.get();
    }
    
    private static Application setInstance(Application application)
	{
		log.info("Set Instance: " + application);
    	currentApplication.set(application);
    	return application;
	}
    
    public Application()
    {
    	setInstance(this);
    }
	
    /**
     * 
     */
	@Override
    protected void init(VaadinRequest request) 
    {
        setInstance(this);
        log.info("**************************************************************");
        log.info("New Session with following data:");
        log.info("IP : " + request.getRemoteAddr());
        log.info("**************************************************************");
		Page.getCurrent().setTitle("PalaverApp");
		
        setContent(new LoginForm());
        
//        Button button = new Button("Datenbank Test");
//        button.addClickListener(new Button.ClickListener() 
//        {
//            public void buttonClick(ClickEvent event) 
//            {
//                SystemDAO dao = SystemDAO.getInstance();
//                try
//				{
//					layout.addComponent(new Label("Erfolg: " + dao.testConnection()));
//				} 
//                catch (ConnectException e)
//				{
//					layout.addComponent(new Label(e.toString()));
//				} 
//                catch (DAOException e)
//				{
//                	layout.addComponent(new Label(e.toString()));
//				} 
//                catch (SQLException e)
//				{
//                	layout.addComponent(new Label(e.toString()));
//				}
//            }
//        });
//        layout.addComponent(button);
//        layout.addComponent(MainLayout.getInstance());
    }
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		log.info("LOGIN USER: " + username);
		this.username = username;
	}
	
	public Mitarbeiter getUser()
	{
		return user;
	}
	
	public void setUser(Mitarbeiter mitarbeiter)
	{
		this.user = mitarbeiter;
	}
	
	public MainLayout getLayout()
	{
		return layout;
	}
	
	public void login(String username)
	{
		setUsername(username);
		layout = new MainLayout();
	}
	
	@Override
	public void close()
	{
		log.info("Closing Application");
		super.close();

		super.getSession().close();
		getPage().setLocation("/Logout");
		setInstance(null);
	}

	public void onRequestStart(HttpServletRequest request, HttpServletResponse response)
	{
		currentApplication.set(this);
		log.info("IP : " +request.getRemoteAddr());
	}


	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response)
	{
		currentApplication.set(null);
		currentApplication.remove();
	}
	
	public Boolean userHasPersmission(String role)
	{
		for (Rollen e : user.getRollen())
		{
			if (e.getName().equals(role))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �ffnet den Standartdialog mit einem OK Button
	 * @param message
	 */
	public void showDialog(String message)
	{
		final Window dialog = new Window();
		dialog.setClosable(false);
		dialog.setWidth("300px");
		dialog.setHeight("150px");
		dialog.setModal(true);
		dialog.center();
		dialog.setResizable(false);
		dialog.setStyleName("dialog-window");
		
		Label content = new Label(message);
		
		Button okButton = new Button("OK");
		
		VerticalLayout dialogContent = new VerticalLayout();
		dialogContent.setSizeFull();
		dialogContent.setMargin(true);
		dialog.setContent(dialogContent);
		
		dialogContent.addComponent(content);
		dialogContent.addComponent(okButton);
		dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
		
		UI.getCurrent().addWindow(dialog);
		
		okButton.addClickListener(new ClickListener()
		{	
			@Override
			public void buttonClick(ClickEvent event)
			{
				UI.getCurrent().removeWindow(dialog);
			}
		});
	}
}
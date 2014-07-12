/**
 * Created by S.Walz
 */
package de.palaver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.palaver.management.emploee.Employee;
import de.palaver.management.emploee.Rolle;
import de.palaver.view.layout.LoginForm;
import de.palaver.view.layout.MainLayout;

/**
 * The Application's "main" class
 */
@Theme("palaver")
@SuppressWarnings("serial")
public class Application extends UI
{
    private static final ThreadLocal<Application>	currentApplication	= new ThreadLocal<Application>();

    private static final Logger				log					= LoggerFactory.getLogger(Application.class.getName());
    
    private Employee						user;
    
    private de.palaver.view.layout.MainLayout						layout;
    
    /**
     * Zugriff auf "MAIN" Klasse und Session
     * @return Application
     */
    @Deprecated
    public static Application getInstance()
    {
    	log.info("OLD !! Instance: " + currentApplication.get());
    	if (currentApplication.get() == null)
    	{
    		log.error("OLD !! Instance is NULL");
    		return setInstance(new Application());
    	}
    	return currentApplication.get();
    }
    
    @Deprecated
    private static Application setInstance(Application application)
	{
		log.info("Set OLD !! Instance: " + application);
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
        super.setData(this);
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
	
	public Employee getUser()
	{
		return user;
	}
	
	public void setUser(Employee employee)
	{
		this.user = employee;
	}
	
	public MainLayout getLayout()
	{
		return layout;
	}
	
	public void login(Employee user)
	{
		setUser(user);
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
		super.setData(null);
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
	
	public Boolean userHasPersmission(String role) {
		for (Rolle e : user.getRollen()) {
			if (e.getName().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Öffnet den Standartdialog mit einem OK Button
	 * @param message
	 */
	public void showDialog(String message)
	{
		Notification dialog = new Notification(message);
		// Bei -1 muss man draufklicken
//		dialog.setDelayMsec(-1);
		dialog.setDelayMsec(2000);
		dialog.show(Page.getCurrent());
	}
	
	public void showDialogWindow(String message)
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
		okButton.setClickShortcut(KeyCode.ENTER);
	}
	
	private boolean m_change = false; 	
	public boolean getCahnge(){
		return m_change;
	}
	public void setChange(boolean isCahnge){
		m_change = isCahnge;		
	}
}
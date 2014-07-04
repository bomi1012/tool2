/**
 * Created by Sebastian
 * 08.06.2013 - 19:41:45
 */
package de.hska.awp.palaver2.util;

import java.io.Serializable;

import de.palaver.management.emploee.Employee;
import de.palaver.view.layout.MainLayout;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class SessionData implements Serializable
{
	private MainLayout 		layout;
	
	private Employee		user;
	
	public SessionData()
	{
		this(null, null);
	}
	
	public SessionData(MainLayout layout, Employee user)
	{
		super();
		this.setLayout(layout);
		this.setUser(user);
	}

	public MainLayout getLayout()
	{
		return layout;
	}

	private void setLayout(MainLayout layout)
	{
		this.layout = layout;
	}

	public Employee getUser()
	{
		return user;
	}

	public void setUser(Employee user)
	{
		this.user = user;
	}
}

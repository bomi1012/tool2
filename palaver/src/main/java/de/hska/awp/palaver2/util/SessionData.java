/**
 * Created by Sebastian
 * 08.06.2013 - 19:41:45
 */
package de.hska.awp.palaver2.util;

import java.io.Serializable;

import de.hska.awp.palaver2.gui.layout.MainLayout;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class SessionData implements Serializable
{
	private MainLayout 		layout;
	
	private Mitarbeiter		user;
	
	public SessionData()
	{
		this(null, null);
	}
	
	public SessionData(MainLayout layout, Mitarbeiter user)
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

	public Mitarbeiter getUser()
	{
		return user;
	}

	public void setUser(Mitarbeiter user)
	{
		this.user = user;
	}
}

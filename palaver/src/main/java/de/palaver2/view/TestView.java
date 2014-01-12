/**
 * Created by Sebastian Walz
 * 24.04.2013 20:12:03
 */
package de.palaver2.view;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class TestView extends VerticalLayout
{
	public TestView()
	{
		super();
		
		this.setSizeFull();
		this.addComponent(new Label("Test"));
		
		try
		{
			InitialContext cxt;
			cxt = new InitialContext();

			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/palaverDB" );
			
			Statement smt = ds.getConnection().createStatement();
			
			ResultSet set = smt.executeQuery("SELECT * FROM artikel WHERE id < 20");
			while (set.next())
			{
				this.addComponent(new Label("ID " + set.getString("id") + " NAME " + set.getString("name")));
			}
			if (ds != null)
			{
				this.addComponent(new Label("Success"));
			}
		} 
		catch (Exception e)
		{
			this.addComponent(new Label(e.toString()));
		}
		
	}
}

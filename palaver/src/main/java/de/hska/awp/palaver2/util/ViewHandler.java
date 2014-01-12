/**
 * Created by Sebastian Walz
 */
package de.hska.awp.palaver2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.UI;

import de.hska.awp.palaver2.gui.layout.DefaultView;
import de.palaver.Application;

/**
 * @author Sebastian
 * 
 */
public class ViewHandler {
	private static ViewHandler instance = null;
	private static final Logger log = LoggerFactory.getLogger(ViewHandler.class.getName());

	private ViewHandler() {
		super();
	}

	public static ViewHandler getInstance() {
		if (instance == null) {
			instance = new ViewHandler();
		}
		return instance;
	}

	public void switchView(Class<? extends View> view) {
		Application app = (Application) UI.getCurrent().getData();
		if (app != null)
		{
			try {
				
				if (view != null) {
					View current = view.newInstance();
					app.getLayout().removeComponent(
							app.getLayout().getComponent(app.getLayout().getComponentCount() - 1));
					app.getLayout().addComponent(current);
					app.getLayout().setExpandRatio(
							app.getLayout().getComponent(app.getLayout().getComponentCount() - 1), 1);
					log.info("Switch View to: " + current.getClass());
				}
			} catch (Exception e) {
				e.printStackTrace();
				app.getLayout().addComponent(new DefaultView());
				app.getLayout().setExpandRatio(
						app.getLayout().getComponent(app.getLayout().getComponentCount() - 1), 1);
			}
		}
		else
		{
			log.warn("Instane is NULL");
		}
	}

	public void switchView(Class<? extends View> view, ViewData data) {
		Application app = (Application) UI.getCurrent().getData();
		if (app != null)
		{
			try {
				if (view != null) {
					View current = view.newInstance();
					app.getLayout()
							.removeComponent(
									app.getLayout().getComponent(app.getLayout().getComponentCount() - 1));
					app.getLayout().addComponent(current);
					app.getLayout()
							.setExpandRatio(
									app.getLayout().getComponent(app.getLayout().getComponentCount() - 1), 1);
					current.getViewParam(data);
					log.info("Switch View to: " + current.getClass() + " with data: " + data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				app.getLayout().addComponent(new DefaultView());
				app.getLayout()
						.setExpandRatio(
								app.getLayout().getComponent(app.getLayout().getComponentCount() - 1), 1);
			}
		}
		else
		{
			log.warn("Instane is NULL");
		}
	}

	public void returnToDefault() {
		switchView(DefaultView.class);
	}
}

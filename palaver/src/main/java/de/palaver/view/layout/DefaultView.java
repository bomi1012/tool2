/**
 * Created by Sebastian
 * 17.04.2013 - 13:37:37
 */
package de.palaver.view.layout;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.IViewData;

/**
 * @author Sebastian
 * 
 */
@SuppressWarnings("serial")
public class DefaultView extends VerticalLayout implements View {
	public DefaultView() {
		super();
		this.setSizeFull();

		Image content = new Image(null, new ThemeResource("img/palaverschild.jpeg"));
		this.addComponent(content);
		this.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util
	 * .ViewData)
	 */
	@Override
	public void getViewParam(IViewData data) {
	}
}

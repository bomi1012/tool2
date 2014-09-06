/**
 * Created by Sebastian Walz
 * 02.05.2013 16:55:00
 */
package de.hska.awp.palaver2.util;

import com.vaadin.ui.ComponentContainer;

public interface View extends ComponentContainer {
	public void getViewParam(IViewData data);
}

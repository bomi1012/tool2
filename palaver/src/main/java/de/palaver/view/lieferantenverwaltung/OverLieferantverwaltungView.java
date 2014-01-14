package de.palaver.view.lieferantenverwaltung;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import de.hska.awp.palaver2.util.IConstants;
import de.palaver.view.ViewAbstract;

@SuppressWarnings("serial")
public class OverLieferantverwaltungView extends ViewAbstract {
	
	protected Button m_auswaehlenButton;
	protected Button m_editButton;
	protected HorizontalLayout m_control;
	
	protected OverLieferantverwaltungView() {
		super();
	}

	protected OverLieferantverwaltungView(Component... children) {
		super(children);
	}
	
	
	protected HorizontalLayout controlLieferantenPanel() {	
		m_auswaehlenButton =  buttonSetting(m_button, IConstants.BUTTON_SELECT,
				IConstants.ICON_PAGE_WHITE_LUPE, true, true);
		m_editButton = editButton(true, true);		
		m_horizontalLayout = new HorizontalLayout();
		m_horizontalLayout.setSpacing(true);
		m_horizontalLayout.addComponent(m_auswaehlenButton);
		m_horizontalLayout.addComponent(m_editButton);
		m_horizontalLayout.setEnabled(false);
		return m_horizontalLayout;
	}
}

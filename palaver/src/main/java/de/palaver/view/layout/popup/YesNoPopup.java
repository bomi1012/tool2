package de.palaver.view.layout.popup;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.view.ViewAbstract;

@SuppressWarnings("serial")
public class YesNoPopup extends ViewAbstract implements View, ValueChangeListener {

	private Image m_userImage;
	private Label m_textLabel;
	private HorizontalLayout m_messageLayout;
	private HorizontalLayout m_control;

	public YesNoPopup(String image, String text) {
		super();
		layout(image, text);
	}
	
	private void layout(String image, String text) {
		this.setSizeFull();
		this.setMargin(true);	
		
		m_userImage = new Image();
		m_userImage.setSource(new ThemeResource(image));		
		m_textLabel = new Label(text);
		m_control = controlYesNo();
		
		m_messageLayout = new HorizontalLayout();
		m_messageLayout.setWidth(FULL);		
		m_messageLayout.addComponent(m_userImage);
		m_messageLayout.addComponent(m_textLabel);
		m_messageLayout.setExpandRatio(m_userImage, 1);
		m_messageLayout.setExpandRatio(m_textLabel, 4);
		m_messageLayout.setComponentAlignment(m_userImage, Alignment.MIDDLE_CENTER);
		m_messageLayout.setComponentAlignment(m_textLabel, Alignment.MIDDLE_RIGHT);
				
		m_vertikalLayout = new VerticalLayout();
		m_vertikalLayout.setWidth(FULL);
		m_vertikalLayout.addComponent(m_messageLayout);
		m_vertikalLayout.addComponent(m_control);
		m_vertikalLayout.setComponentAlignment(m_control, Alignment.TOP_RIGHT);
		
		this.addComponent(m_vertikalLayout);
		this.setComponentAlignment(m_vertikalLayout, Alignment.MIDDLE_CENTER);		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

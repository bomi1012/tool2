package de.palaver.view.lieferantenverwaltung.popup;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.person.lieferantenverwaltung.AnsprechpartnerService;
import de.palaver.view.lieferantenverwaltung.OverLieferantverwaltungView;

@SuppressWarnings("serial")
public class LieferantAnzeigenInfo extends OverLieferantverwaltungView implements View,
ValueChangeListener {
	private static final Logger LOG = LoggerFactory.getLogger(LieferantAnzeigenInfo.class.getName());

	private VerticalLayout m_leftVLLayout;
	private VerticalLayout m_rightVLLayout;

	public LieferantAnzeigenInfo(Lieferant lieferant) {
		super();
		m_lieferant = lieferant;
		layout();
	}
	
	@SuppressWarnings("deprecation")
	private void layout() {
		try {
			this.setSizeFull();
			this.setMargin(true);
			String infoLieferant = getInfoLieferant();
			String infoAnsprechpartner = getInfoAnsprechpartner();
		 
			m_horizontalLayout = new HorizontalLayout();
			m_horizontalLayout.setWidth("90%");
			
			m_leftVLLayout = new VerticalLayout();
			m_leftVLLayout.setWidth("90%");
			m_leftVLLayout.addComponent(new Label(infoLieferant, Label.CONTENT_XHTML));
			
			m_rightVLLayout = new VerticalLayout();
			m_rightVLLayout.setWidth("90%");
			m_rightVLLayout.addComponent(new Label(infoAnsprechpartner, Label.CONTENT_XHTML));
			
			m_horizontalLayout.addComponent(m_leftVLLayout);
			m_horizontalLayout.addComponent(m_rightVLLayout);
			m_horizontalLayout.setExpandRatio(m_leftVLLayout, 1);
			m_horizontalLayout.setExpandRatio(m_rightVLLayout, 1);
			m_horizontalLayout.setComponentAlignment(m_leftVLLayout, Alignment.MIDDLE_LEFT);
			m_horizontalLayout.setComponentAlignment(m_rightVLLayout, Alignment.MIDDLE_RIGHT);
			
			this.addComponent(m_horizontalLayout);
			this.setComponentAlignment(m_horizontalLayout, Alignment.MIDDLE_CENTER);
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		
	}

	private String getInfoAnsprechpartner() throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> ansprechpartners 
				= AnsprechpartnerService.getInstance().getAllAnsprechpartnersByLieferantId(m_lieferant.getId());
		if (ansprechpartners.size() == 0) {
			System.out.println("NULL");
		} else {
			for (Ansprechpartner ansprechpartner : ansprechpartners) {
				System.out.println(ansprechpartner.getName());
			}
		}
		return null;
	}

	private String getInfoLieferant() {
		String message = "";
		String ki = "keine Information";

		message += "<br><b>Kontakte:</b><br>";
		if(m_lieferant.getKontakte() == null) {
			message += ki + "<br>";
		} else {
			message += "<b>Telefon: </b> " + m_lieferant.getKontakte().getTelefon() + "<br>";
			message += "<b>Handy: </b> " + m_lieferant.getKontakte().getHandy() + "<br>";
			message += "<b>Fax: </b> " + m_lieferant.getKontakte().getFax() + "<br>";
			message += "<b>Email: </b> " + m_lieferant.getKontakte().getEmail() + "<br>";
			message += "<b>Website: </b> " + m_lieferant.getKontakte().getWww() + "<br>";
		}
				
		message += "<br><b>Adresse:</b><br>";
		if(m_lieferant.getAdresse() == null) {
			message += ki + "<br>";
		} else {
			message += "<b>Straﬂe: </b> " + m_lieferant.getAdresse().getStrasse() + "<br>";
			message += "<b>Hausnummer: </b> " + m_lieferant.getAdresse().getHausnummer() + "<br>";
			message += "<b>Stadt: </b> " + m_lieferant.getAdresse().getStadt() + "<br>";
			message += "<b>PLZ: </b> " + m_lieferant.getAdresse().getPlz() + "<br>";
			message += "<b>Land: </b> " + m_lieferant.getAdresse().getLand() + "<br>";
		}
		if(m_lieferant.getNotiz() != null && !m_lieferant.getNotiz().equals("")) {
			message += "<br><b>Kommentar: </b>" + m_lieferant.getNotiz() + "<br>";
		}
		return message.replace("null", "");
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

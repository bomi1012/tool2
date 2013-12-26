package de.bistrosoft.palaver.menueplanverwaltung;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;

public class KoecheComponent extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723143101691071320L;

	VerticalLayout vl = new VerticalLayout();
	private NativeSelect nsKoch1 = new NativeSelect();
	private NativeSelect nsKoch2 = new NativeSelect();

	
	public KoecheComponent(List<Mitarbeiter> mitarbeiter) {

		setCompositionRoot(vl);
		vl.setSizeFull();
		vl.addComponent(nsKoch1);
		vl.addComponent(nsKoch2);

		this.setWidth("149px");
		this.setHeight("50px");

		nsKoch1.setWidth("140px");
		nsKoch2.setWidth("140px");

		vl.setComponentAlignment(nsKoch1, Alignment.TOP_CENTER);
		vl.setComponentAlignment(nsKoch2, Alignment.BOTTOM_CENTER);

		for (Mitarbeiter m : mitarbeiter) {
			nsKoch1.addItem(m);
			nsKoch2.addItem(m);
		}

	}

	public Mitarbeiter getKoch1() {
		return (Mitarbeiter) nsKoch1.getValue();
	}

	public void setKoch1(Mitarbeiter koch) {
		nsKoch1.select(koch);
	}

	public Mitarbeiter getKoch2() {
		return (Mitarbeiter) nsKoch2.getValue();
	}

	public void setKoch2(Mitarbeiter koch) {
		nsKoch2.select(koch);
	}

}

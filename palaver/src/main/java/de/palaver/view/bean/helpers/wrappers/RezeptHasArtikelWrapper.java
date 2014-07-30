package de.palaver.view.bean.helpers.wrappers;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.palaver.management.artikel.Artikel;
import de.palaver.management.recipe.RezeptHasArtikel;

public class RezeptHasArtikelWrapper {

	private RezeptHasArtikel m_rezeptHasArtikel;
	public RezeptHasArtikel getRezeptHasArtikel() { return  m_rezeptHasArtikel; }
	
	private TextField m_menge = new TextField();
	public TextField getMenge() { return m_menge; }
	
	private Label m_artikelname = new Label();
	public Label getArtikelname() { return m_artikelname; }
	
	private Label m_mengeneinheit = new Label();
	public Label getMengeneinheit() { return m_mengeneinheit; }
	
	private Label m_comment = new Label();
	public Label getComment() { return m_comment; }
	
	public RezeptHasArtikelWrapper(RezeptHasArtikel rezeptHasArtikel) {
		m_rezeptHasArtikel = rezeptHasArtikel;
		m_menge.setValue(String.valueOf(rezeptHasArtikel.getMenge()));
		m_artikelname.setValue(rezeptHasArtikel.getArtikel().getName());
		m_mengeneinheit.setValue(rezeptHasArtikel.getArtikel().getMengeneinheit().getName());
		m_comment.setValue(rezeptHasArtikel.getArtikel().getNotiz());
	}

	public RezeptHasArtikelWrapper(Artikel artikel) {
		this(new RezeptHasArtikel(artikel, 1.0, null));
	}

}

package de.palaver.view;

import java.sql.SQLException;

import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.dao.ConnectException;
import de.hska.awp.palaver.dao.DAOException;

public interface IErstellen {
	void sqlStatement(int i) throws ConnectException, DAOException, SQLException;
	boolean validiereEingabe();
	VerticalLayout addToLayout(VerticalLayout box, String width);
}

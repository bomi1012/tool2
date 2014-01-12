package de.palaver.view;

import java.sql.SQLException;

import com.vaadin.ui.VerticalLayout;

import de.palaver.dao.ConnectException;
import de.palaver.dao.DAOException;

public interface IErstellen {
	void sqlStatement(int i) throws ConnectException, DAOException, SQLException;
	boolean validiereEingabe();
	VerticalLayout addToLayout(VerticalLayout box, String width);
}

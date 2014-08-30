package de.palaver.view;

import java.sql.SQLException;

import com.vaadin.ui.VerticalLayout;

import de.palaver.management.util.dao.ConnectException;
import de.palaver.management.util.dao.DAOException;

public interface IErstellen {
	void sqlStatement(int i) throws ConnectException, DAOException, SQLException;
	boolean validiereEingabe();
	VerticalLayout addToLayout(VerticalLayout box, String width);
}

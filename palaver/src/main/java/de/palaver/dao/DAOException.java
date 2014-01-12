/*
 * DAOException.java
 *
 * created at 28.11.2012 09:21:24  by Sebastian Walz
 *
 * Copyright (c) 2012 SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.palaver.dao;

public class DAOException extends Exception {
	private static final long serialVersionUID = 6985496923909454404L;

	public DAOException(String msg) {
		super(msg);
	}
}
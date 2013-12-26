/*
 * DAOException.java
 *
 * created at 28.11.2012 09:21:24  by Sebastian Walz
 *
 * Copyright (c) 2012 SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.hska.awp.palaver.dao;

@SuppressWarnings("serial")
public class DAOException extends Exception
{
	public DAOException(String msg)
	{
		super(msg);
	}
}
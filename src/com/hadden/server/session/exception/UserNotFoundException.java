package com.hadden.server.session.exception;

public class UserNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5070200291687967999L;

	public UserNotFoundException(String message)
	{
		super(message);
	}
}

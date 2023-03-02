package com.hadden.server.session.exception;

public class UserNotAuthorizedException extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7198308405944861764L;

	public UserNotAuthorizedException(String message)
	{
		super(message);
	}
}

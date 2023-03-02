package com.hadden.server.session.user;

public interface SSOAppUser
{
	String getUserId();
	String getLastName();
	String getFirstName();
	String getAddress();
	String getCity();
	String getState();
	String getZipCode();
	String getEmailAddress();
	String getAdId();
	String getPersonnelNumber();
	String getLoginId();
	String getDisplayName();
	String getAttribute(String name);
}

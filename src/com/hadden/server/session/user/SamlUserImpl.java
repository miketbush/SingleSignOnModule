package com.hadden.server.session.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SamlUserImpl implements SamlAppUser, Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3741684065594711483L;

	private String userId;
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String emailAddress;
	private String adId;
	private String personnelNumber;
	private String loginId;
	private String displayName;
	
	Map<String,String> userAttrs = new HashMap<String,String>();
	
	public SamlUserImpl()
	{
		super();
	}

	
	public SamlUserImpl(String userId,String lastName,String firstName,String address,String city,String state,String zipCode,
			            String emailAddress,String adId,String personnelNumber,String loginId,String displayName)
	{
		super();
		this.userId = userId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.emailAddress = emailAddress;
		this.adId = adId;
		this.personnelNumber = personnelNumber;
		this.loginId = loginId;
		this.displayName = displayName;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getAdId()
	{
		return adId;
	}

	public void setAdId(String adId)
	{
		this.adId = adId;
	}

	public String getPersonnelNumber()
	{
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber)
	{
		this.personnelNumber = personnelNumber;
	}

	public String getLoginId()
	{
		return loginId;
	}

	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	@Override
	public String getAttribute(String name)
	{
		return userAttrs.get(name);
	}

	

	
}

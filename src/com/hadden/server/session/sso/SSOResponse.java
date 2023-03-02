package com.hadden.server.session.sso;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hadden.server.session.user.SSOAppUser;
import com.hadden.server.session.user.SamlAppUser;
import com.hadden.server.session.user.SamlProperties;
import com.hadden.server.session.xml.XMLHelper;
import com.hadden.server.session.xml.XMLHelperElement;

public class SSOResponse implements SamlAppUser, Serializable, SamlProperties
{
	XMLHelperElement el;

	Map<String,String> values = new HashMap<String,String>();
	
	public SSOResponse()
	{
		super();
	}
	
	public SSOResponse(Map<String,String> values)
	{
		try
		{
			this.values.putAll(values);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	public String getAttribute(String attrName)
	{
		return values.get(attrName);
	}

	@Override
	public String getDisplayName()
	{
		return getAttribute("givenName") + " " + getAttribute("sn");
	}

	@Override
	public String getUserId()
	{
		return getAttribute("userid");
	}

	@Override
	public String getLastName()
	{
		return getAttribute("sn");
	}

	@Override
	public String getFirstName()
	{
		return getAttribute("givenName");
	}

	@Override
	public String getAddress()
	{
		// TODO Auto-generated method stub
		return getAttribute("streetAddress"); 
	}

	@Override
	public String getCity()
	{
		return getAttribute("city");
	}

	@Override
	public String getState()
	{
		return getAttribute("state");
	}

	@Override
	public String getZipCode()
	{
		return getAttribute("postalCode");
	}


	@Override
	public String getEmailAddress()
	{
		return getAttribute("email");
	}

	@Override
	public String getAdId()
	{
		return getAttribute("userid");
	}

	@Override
	public String getPersonnelNumber()
	{
		return getAttribute("employeeID");
	}


	@Override
	public String getLoginId()
	{
		return getAttribute("userid");
	}

	@Override
	public Map<String, String> getProperties()
	{
		return values;
	}

	
}	
package com.hadden.server.session.saml;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hadden.server.session.user.SamlAppUser;
import com.hadden.server.session.user.SamlProperties;
import com.hadden.server.session.xml.XMLHelper;
import com.hadden.server.session.xml.XMLHelperElement;

public class SamlResponse implements SamlAppUser, Serializable, SamlProperties
{
	XMLHelperElement el;

	Map<String,String> values = new HashMap<String,String>();
	
	public SamlResponse()
	{
		super();
	}
	
	public SamlResponse(byte[] bytes)
	{
		try
		{
			el = XMLHelper.loadXML(new String(bytes,"utf-8"), "samlp:Response", XMLHelperElement.class);
			if(el!=null)
			{
				SamlAttribute[] attrs = el.getChildren("saml:Attribute", SamlAttribute.class);
				for(SamlAttribute attr : attrs)
				{
					SamlAttributeValue[] vals = attr.getChildren("saml:AttributeValue", SamlAttributeValue.class);
					for(SamlAttributeValue val : vals)
					{
						values.put(attr.getValue(), val.getValue());
					}
				}
			}
			
		}
		catch(UnsupportedEncodingException e)
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
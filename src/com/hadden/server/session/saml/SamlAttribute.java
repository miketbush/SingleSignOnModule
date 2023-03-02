package com.hadden.server.session.saml;

import org.w3c.dom.Element;

import com.hadden.server.session.xml.XMLHelperElement;



public class SamlAttribute extends XMLHelperElement
{
	private String value;
	
	public SamlAttribute(org.w3c.dom.Element el)
	{
		super(el);
		value = this.getAttributes().get("Name");
		System.out.println("SamlAttribute::value:" + value);
	}
	
	public String getValue()
	{
		return value;
	}
		
}	
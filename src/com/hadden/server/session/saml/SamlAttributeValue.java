package com.hadden.server.session.saml;

import org.w3c.dom.Element;

import com.hadden.server.session.xml.XMLHelperElement;

public class SamlAttributeValue extends XMLHelperElement
{
	public SamlAttributeValue(org.w3c.dom.Element el)
	{
		super(el);
		System.out.println("SamlAttributeValue::value:" + this.getValue());
	}

	
}
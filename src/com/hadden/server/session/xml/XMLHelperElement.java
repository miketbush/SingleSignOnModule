package com.hadden.server.session.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHelperElement
{
	private Element element = null;
	private Map<String,String> attrs = new HashMap<String,String>();
	
	public XMLHelperElement(Element element)
	{
		this.element = element;
		if(attrs!=null && element!=null)
		{
			NamedNodeMap map = element.getAttributes();
			if(map!=null)
			{
				for(int index=0;index<map.getLength();index++)
				{	
					Node attr = map.item(index);
					if(attr!=null)
					{
						attrs.put(attr.getNodeName(), attr.getNodeValue());
					}
				}
			}
		}
		
	}
	
	public Map<String,String> getAttributes()
	{
		return attrs;
	}

	public XMLHelperElement[] getChildren()
	{		
		return (XMLHelperElement[])XMLHelper.getChildArray(element, Document.ELEMENT_NODE, XMLHelperElement.class);
	}	
	
	public XMLHelperElement[] getChildren(String name)
	{		
		return (XMLHelperElement[])XMLHelper.getChildArray(element, name, Document.ELEMENT_NODE, XMLHelperElement.class);
	}
	
	public <T> T[] getChildren(String name,Class<T> clazz)
	{		
		return (T[])XMLHelper.getChildArray(element, name, Document.ELEMENT_NODE, clazz);
	}
	
	public String getName()
	{
		String elVal = "";
		
		if(element!=null)
		{
			elVal = element.getNodeName();
		}
		
		return elVal;
	}
	
	public String getValue()
	{
		String elVal = "";
			
		if(element!=null)
		{
			NodeList list = element.getChildNodes();
			if(list!=null)
			{
				int max = list.getLength();
				for(int index = 0;index < max; index++)
				{
					Node n = list.item(index);
					if(n.getNodeType() == Node.CDATA_SECTION_NODE)
					{
						elVal = n.getNodeValue();
						break;
					}
					else if(n.getNodeType() == Node.TEXT_NODE)
					{
						elVal = n.getNodeValue();
					}
				}
			}
		}
		
		return elVal;
	}
	
};

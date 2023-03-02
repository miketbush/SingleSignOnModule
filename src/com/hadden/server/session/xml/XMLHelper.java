package com.hadden.server.session.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class XMLHelper
{
	protected static Element[] getChildArray(Element parent,String name)
	{
		return getChildArray(parent,name,Document.ELEMENT_NODE);
	}
	
	protected static Element[] getChildArray(Element parent,String name,short type)
	{
		List<Element> set = new ArrayList<Element>();
		
		if(parent!=null)
		{
			try
			{
				NodeList nodes = parent.getElementsByTagName(name);
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == type)
							{
								if(node.getNodeName().equals(name))
								{
									set.add((Element) node);
									break;
								}
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				set = null;
				e.printStackTrace();
			}
		}
		
		return set.toArray(new Element[set.size()]);
	}
	
	public Element[] getChildArray(Element parent)
	{
		List<Element> set = new ArrayList<Element>();
		
		if(parent!=null)
		{
			try
			{
				NodeList nodes = parent.getChildNodes();
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == Document.ELEMENT_NODE)
							{
								set.add((Element) node);
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				set = null;
				e.printStackTrace();
			}
		}
		
		return set.toArray(new Element[set.size()]);
	}
	@SuppressWarnings("unchecked")
	protected static <T> T[] getChildArray(Element parent,String name,Class<T> clazz)
	{
		return getChildArray(parent,name,Document.ELEMENT_NODE,clazz);
	}
	
	@SuppressWarnings("unchecked")
	public
	static <T> T[] getChildArray(Element parent,String name,short type,Class<T> clazz)
	{
		List<T> set = new ArrayList<T>();
		
		if(parent!=null)
		{
			try
			{
				NodeList nodes = parent.getElementsByTagName(name);
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == type)
							{
								if(node.getNodeName().equals(name))
								{
									try
									{
										Constructor<T> ctor = clazz.getConstructor(Element.class);
										
										T object = ctor.newInstance((Element) node);
										
										set.add(object);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				set = null;
				e.printStackTrace();
			}
		}
		
		//return set.toArray(new Object[set.size()]);
		return (T[])set.toArray((T[])Array.newInstance(clazz, set.size()));
	}

	@SuppressWarnings("unchecked")
	public
	static <T> T[] getChildArray(Element parent,short type,Class<T> clazz)
	{
		List<T> set = new ArrayList<T>();
		
		if(parent!=null)
		{
			try
			{
				NodeList nodes = parent.getChildNodes();
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == type)
							{
								if(node.getNodeName()!=null)
								{
									try
									{
										Constructor<T> ctor = clazz.getConstructor(Element.class);
										
										T object = ctor.newInstance((Element) node);
										
										set.add(object);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				set = null;
				e.printStackTrace();
			}
		}
		
		//return set.toArray(new Object[set.size()]);
		return (T[])set.toArray((T[])Array.newInstance(clazz, set.size()));
	}	
	
	protected static <T>T getFirstElement(Document doc,String name,Class<T> clazz)
	{
		T obj = null;
		
		if(doc!=null)
		{
			try
			{
				NodeList nodes = doc.getElementsByTagName(name);
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == Document.ELEMENT_NODE)
							{
								if(node.getNodeName().equals(name))
								{
									try
									{
										Constructor<T> ctor = clazz.getConstructor(Element.class);
										
										obj = ctor.newInstance((Element) node);
										
										break;
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				obj = null;
				e.printStackTrace();
			}
		}
		
		return obj;
	}
	
	public static <T>T getFirstElement(Element doc,String name,Class<T> clazz)
	{
		T obj = null;
		
		if(doc!=null)
		{
			try
			{
				NodeList nodes = doc.getElementsByTagName(name);
				if(nodes != null)
				{
					int size = nodes.getLength();
					if(size > 0)
					{
						for(int index = 0; index < size; index++)
						{
							Node node = nodes.item(index);
							if(node != null && node.getNodeType() == Document.ELEMENT_NODE)
							{
								if(node.getNodeName().equals(name))
								{
									try
									{
										Constructor<T> ctor = clazz.getConstructor(Element.class);
										
										obj = ctor.newInstance((Element) node);
										
										break;
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			catch(DOMException e)
			{
				obj = null;
				e.printStackTrace();
			}
		}
		
		return obj;
	}

	public static <T> T load(String fileName,String rootName,Class<T> clazz)
	{
		T t = null;
		
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();		
		if(factory!=null)
		{
			DocumentBuilder builder;
			try
			{
				builder = factory.newDocumentBuilder();
				if(builder!=null)
				{
					File f = new File(fileName);
					if(f!=null && f.exists())
					{
						try
						{
							Document doc = builder.parse(f);							
							if(doc!=null)
							{
								t = XMLHelper.getFirstElement(doc,rootName,clazz); 
							}
						}
						catch(SAXException e)
						{
							e.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			catch(ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}
		
		return t;
	}

	protected static <T> T load(File f,String rootName,Class<T> clazz)
	{
		T t = null;
		
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();		
		if(factory!=null)
		{
			DocumentBuilder builder;
			try
			{
				builder = factory.newDocumentBuilder();
				if(builder!=null)
				{
					//File f = new File(fileName);
					if(f!=null && f.exists())
					{
						try
						{
							Document doc = builder.parse(f);							
							if(doc!=null)
							{
								t = XMLHelper.getFirstElement(doc,rootName,clazz); 
							}
						}
						catch(SAXException e)
						{
							e.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			catch(ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}
		
		return t;
	}

	public static <T> T loadXML(String strXML,String rootName,Class<T> clazz)
	{
		T t = null;
		
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();		
		if(factory!=null)
		{
			DocumentBuilder builder;
			try
			{
				builder = factory.newDocumentBuilder();
				if(builder!=null)
				{
					if(strXML!=null)
					{
						try
						{
							Document doc = builder.parse(new ByteArrayInputStream(strXML.getBytes()));							
							if(doc!=null)
							{
								t = XMLHelper.getFirstElement(doc,rootName,clazz); 
							}
						}
						catch(SAXException e)
						{
							e.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			catch(ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}
		
		return t;
	}
	
	public static String loadToXML(String fileName)
	{
		String xml = null;

		Document doc = load(fileName);
		if(doc != null)
		{
			xml = convertDocumentToStringEncoding(doc,"UTF-8");
		}

		return xml;
	}

	public static Document load(String fileName)
	{
		Document doc = null;

		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		if(factory != null)
		{
			DocumentBuilder builder;
			try
			{
				builder = factory.newDocumentBuilder();
				if(builder != null)
				{
					File f = new File(fileName);
					if(f != null && f.exists())
					{
						try
						{
							doc = builder.parse(f);
						}
						catch(SAXException e)
						{
							e.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			catch(ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}

		return doc;
	}

	public static String convertDocumentToStringEncoding(final Document doc, String encoding)
	{
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer lsSerializer = implLS.createLSSerializer();
		lsSerializer.getDomConfig().setParameter("format-pretty-print", true);
		LSOutput lsOutput = implLS.createLSOutput();
		lsOutput.setEncoding(encoding);
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		lsSerializer.write(doc, lsOutput);
		
		return stringWriter.toString();
	}

	public static String convertDocumentToString(final Document doc)
	{
		final DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		final LSSerializer lsSerializer = domImplementation.createLSSerializer();
		lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		final String xml = lsSerializer.writeToString(doc);

		return xml;
	}
		
}

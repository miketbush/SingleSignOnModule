package com.hadden.server.session.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.centurylink.messaging.util.BASE64Decoder;
import com.hadden.server.session.SessionHelper;
import com.hadden.server.session.saml.SamlResponse;
import com.hadden.server.session.user.SamlAppUser;

@SuppressWarnings("serial")
public abstract class SAMLProcessingServlet extends HttpServlet
{
	/**
	 * 
	 * @return
	 */
	protected abstract String getMainPage();
	/**
	 * 
	 * @return
	 */
	protected abstract String getLoginPage();
	/**
	 * 
	 * @param userId
	 * @return
	 */
	protected abstract String getLoginRestriction(SamlAppUser user);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	protected abstract SamlAppUser authenticatedUser(SamlAppUser user);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	protected abstract SamlAppUser findUser(String userId);
	
	protected abstract void processSession(HttpServletRequest req, HttpServletResponse resp,HttpSession session);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		
		System.out.println("SAMLProcessingServlet::GET:" + session);

		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		
		System.out.println("SAMLProcessingServlet::POST:" + session);
		
		Enumeration params = req.getParameterNames();
		while(params.hasMoreElements())
		{
			String p = (String) params.nextElement();
			System.out.println("SAMLProcessingServlet::POST:params:" + p + " = " + req.getParameter(p));
			
			if("SAMLResponse".equals(p))
			{
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] bytes =	decoder.decodeBuffer(req.getParameter(p));
				if(bytes!=null)
				{
					/*

					<saml:Attribute Name="email" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
					<saml:AttributeValue xsi:type="xs:string">Michael.Bush@centurylink.com</saml:AttributeValue>
					</saml:Attribute>
					<saml:Attribute Name="userid" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
					<saml:AttributeValue xsi:type="xs:string">AA42234</saml:AttributeValue>
					</saml:Attribute>
				
					
					 */
					System.out.println("SAMLProcessingServlet::POST:SAMLResponse:" + new String(bytes,"utf-8"));
					
					SamlResponse samlResponse = new SamlResponse(bytes);
					if(samlResponse!=null)
					{
						String redirect = getMainPage();
						
						System.out.println("SAMLProcessingServlet::POST:SamlResponse:userid:" + samlResponse.getAttribute("userid"));
						System.out.println("SAMLProcessingServlet::POST:SamlResponse:email:" + samlResponse.getAttribute("email"));
						
						SamlAppUser responseUser = samlResponse;
						
						processSession(req,resp,session);
						
						SamlAppUser user = authenticatedUser(responseUser);
						if(user == null)
						{
							user = responseUser;
						}

						if(user.getUserId() != null)
						{
							session = req.getSession(true);

							session.setAttribute(SessionHelper.SESSION_USER_ID, samlResponse.getAttribute("userid"));
							session.setAttribute(SessionHelper.SESSION_USER_NAME, samlResponse.getAttribute("givenName") + " " + samlResponse.getAttribute("sn"));
							session.setAttribute(SessionHelper.SESSION_USER_FIRST, samlResponse.getAttribute("givenName"));
							session.setAttribute(SessionHelper.SESSION_USER_LAST, samlResponse.getAttribute("sn"));
							session.setAttribute(SessionHelper.SESSION_SAML, samlResponse);
							session.setAttribute(SessionHelper.SESSION_USER, user);
						}						
						else
						{
							redirect = getLoginPage();
						}					
					}
				}
			}
			
		}
		
		
		resp.sendRedirect(req.getContextPath() + getMainPage());
	}
	
}

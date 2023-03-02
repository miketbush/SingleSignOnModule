package com.hadden.server.session.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.centurylink.messaging.util.BASE64Decoder;
import com.hadden.server.session.SessionHelper;
import com.hadden.server.session.exception.UserNotAuthorizedException;
import com.hadden.server.session.exception.UserNotFoundException;
import com.hadden.server.session.saml.SamlResponse;
import com.hadden.server.session.sso.SSOResponse;
import com.hadden.server.session.user.SSOAppUser;
import com.hadden.server.session.user.SamlAppUser;

@SuppressWarnings("serial")
public abstract class SSOProcessingServlet extends HttpServlet
{

	private static final Map<String,HttpSession> sessionCache = new HashMap<String,HttpSession>();
	
	public static boolean isLiveSession(HttpSession session)
	{
		return sessionCache.containsKey(session.getId());
	}
	
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
	 * @return
	 */
	protected abstract String getUserNotFoundPage();	
	/**
	 * 
	 * @return
	 */
	protected abstract String getUserNotAuthorizedPage();
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
	protected abstract SSOAppUser authenticatedUser() throws UserNotFoundException,UserNotAuthorizedException;
	/**
	 * 
	 * @param userId
	 * @return
	 */
	protected abstract SamlAppUser findUser(String userId);
	
	protected abstract void processSession(HttpServletRequest req, HttpServletResponse resp,HttpSession session);
	
	protected abstract void authenticatedSession(HttpServletRequest req, HttpServletResponse resp,HttpSession session,SSOAppUser user);
	
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
		
		System.out.println("SSOProcessingServlet::POST:" + session);
		
		String redirect = getLoginPage(); 
		
		if(req.getParameterMap().containsKey("userName") && req.getParameterMap().containsKey("password"))
		{
			Map<String,String[]> parms = req.getParameterMap();
			
			String[] userNames = parms.get("userName");
			String[] passwords = parms.get("password");
		
			if(userNames.length > 0 && passwords.length > 0)
			{
				processSession(req,resp,session);
				
				try
				{
					SSOAppUser user = authenticatedUser();
					if(user!=null && user.getUserId() != null)
					{
						session = req.getSession(true);
						session.setMaxInactiveInterval(1000 * 60 * 60);
						session.setAttribute(SessionHelper.SESSION_USER_ID, user.getUserId());
						session.setAttribute(SessionHelper.SESSION_USER_NAME, user.getFirstName() + " " + user.getLastName());
						session.setAttribute(SessionHelper.SESSION_USER_FIRST, user.getFirstName());
						session.setAttribute(SessionHelper.SESSION_USER_LAST, user.getLastName());
						session.setAttribute(SessionHelper.SESSION_USER, user);
						
						sessionCache.put(session.getId(),session);
						
						authenticatedSession(req,resp,session,user);
						
						redirect = getMainPage();
					}						
					else
					{
						redirect = getLoginPage();
					}
				}
				catch(UserNotFoundException e)
				{
					redirect = getUserNotFoundPage();
				}
				catch(UserNotAuthorizedException e)
				{
					redirect = getUserNotAuthorizedPage();
				}	
			}
		}
		
		if(redirect.toLowerCase().startsWith("http://") || redirect.toLowerCase().startsWith("https://"))
		{
			System.out.println("SSOProcessingServlet::redirect[long]:" + redirect);
			resp.sendRedirect(redirect);
		}
		else
		{
			System.out.println("SSOProcessingServlet::redirect[short]:" + redirect);
			resp.sendRedirect(req.getContextPath() + redirect);
		}
	}
	
}

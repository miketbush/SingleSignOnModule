package com.hadden.server.session.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hadden.server.session.SessionHelper;

public abstract class LoginFilter implements Filter 
{
	
	protected abstract String 	 getSAMLRedirectURL();
	
	protected abstract String[]  getResourceFilter();
	
	protected abstract boolean   filterResource(String servletPath);
	
	public LoginFilter()
	{
		super();
	}
	
	public void destroy() 
	{
		System.out.println("LoginFilter::destroy");		
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest  req  = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String servletPath = req.getServletPath();
		
		System.out.println("LoginFilter::doFilter[" + req.getContextPath() + "]:" + servletPath);	

		boolean bChain = false;
		
		String[] resourceFilter =  getResourceFilter();;
		if(resourceFilter!=null)
		{
			for(String rf : resourceFilter)
			{
				if(rf.equals(servletPath))
				{
					bChain = true;
					break;
				}
				else
				{
					// look for wildcard, otherwise reject
					if(rf.endsWith("*"))
					{
						if(servletPath.startsWith(rf.replace("*", "")))
						{
							bChain = true;
							break;	
						}
					}
				}
			}
			if(servletPath.equals(System.getProperty("SAMLRedirectURL")))
			{
				bChain = true;
			}
		}
		else
		{			
			bChain = filterResource(servletPath);
		}
				
		if(bChain)
		{
			chain.doFilter(req,resp);
			return;
		}
		
		HttpSession session = req.getSession(false);
		if(session!=null)
		{
			String username = (String) session.getAttribute(SessionHelper.SESSION_USER_ID);
			if(username!=null)
			{
				chain.doFilter(req,resp);
				return;
			}
		}
		
		System.out.println("LoginFilter::doFilter::rejected[" + req.getContextPath() + "]:" + servletPath  );
		String redirect = this.getSAMLRedirectURL();
		if(System.getProperty("SAMLRedirectURL")!=null)
		{
			redirect = System.getProperty("SAMLRedirectURL");
		}
		System.out.println("LoginFilter::doFilter::redirect:" + redirect );
		
		resp.sendRedirect(redirect);
	}

	public void init(FilterConfig finit) throws ServletException 
	{
		System.out.println("LoginFilter::init");
	}

}

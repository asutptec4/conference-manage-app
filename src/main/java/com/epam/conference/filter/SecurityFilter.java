package com.epam.conference.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.conference.entity.UserRole;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter("/jsp/*")
public class SecurityFilter implements Filter {

    /**
     * Default constructor.
     */
    public SecurityFilter() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	// TODO Auto-generated method stub
	// place your code here
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	HttpSession session = req.getSession();
	UserRole role = null;
	if (!(req.getRequestURI().contains(UriPathConstant.PATH_LOGIN)
		|| req.getRequestURI().contains(UriPathConstant.PATH_USER))) {
	    if (session != null
		    && session.getAttribute(SessionConstant.ROLE) != null) {
		role = (UserRole) session.getAttribute(SessionConstant.ROLE);
	    }
	    if (role == null) {
		resp.sendRedirect(
			req.getContextPath() + UriPathConstant.PATH_INDEX);
	    }
	}
	// pass the request along the filter chain
	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	// TODO Auto-generated method stub
    }

}

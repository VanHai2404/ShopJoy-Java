package com.edu.shop.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component //
public class AdminAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private HttpSession session;
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("pre handle of request :" +request.getRequestURI());
		if(session.getAttribute("username") != null) {
			return true;
		}
		session.setAttribute("redirect-uri", request.getRequestURI());
		
		response.sendRedirect("/alogin");
		return false;
	}
	

}

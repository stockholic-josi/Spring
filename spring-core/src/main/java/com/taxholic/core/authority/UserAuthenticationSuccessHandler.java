package com.taxholic.core.authority;


import java.io.IOException;



import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.taxholic.core.util.SysUtil;

public class UserAuthenticationSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler {
	

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {       
		
		//System.out.println("+++> ID : " + AuthUtil.getUser().getUserId() );
		
		if ("true".equals(request.getHeader("X-Ajax-call")) ) {           
			try {
				
				AuthUtil.getUser().setSessionId(SysUtil.getSessionID(request));
				AuthUtil.getUser().setLoginDate(new Date());
				
				response.getWriter().print("OK");
				response.getWriter().flush();   
			} catch (IOException e) {} 
		} else {        
			try {
				super.onAuthenticationSuccess(request, response, auth);
			} catch (Exception e) {}  
		}   
	}
	
	

}

package com.taxholic.core.authority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;


public class AuthUtil {
	
	public static String getPrincipal() {
		
		if(SecurityContextHolder.getContext().getAuthentication() == null){
			return "GUS";
		}
		
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (obj instanceof UserDetails) {
            return ((UserDetails) obj).getUsername();
        } else {
            return "GUS";
        }
    }
	
	public static AuthDto getUser(){
		
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(obj.equals("anonymousUser")){
			return null;
		}else{
			return ((AuthInfo)obj).getUser();
		}
		
	}


}

package com.taxholic.core.controller;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class BaseExceptionController implements HandlerExceptionResolver{
	
	private String view = null;
	
	public void setView(String view) {
		this.view = view;
	}

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse HttpServletResponse, Object object, Exception exception) {
		
		StringBuffer sb = new StringBuffer();
		request.setAttribute("Class",object.getClass());
		request.setAttribute("Exception",exception );
		request.setAttribute("Line",exception.getStackTrace()[0]);
		
		for(int i = 0; i <  exception.getStackTrace().length; i++){
			sb.append(exception.getStackTrace()[i]).append("<br>");			
		}		
		request.setAttribute("Detail",sb.toString());
		
		return new ModelAndView(view);

	}

}
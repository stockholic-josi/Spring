package com.taxholic.manage.system.dwr.controller;



import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller("com.taxholic.manage.system.dwr.controller.FetchController")
@RequestMapping("/manage/system/batch/*.do")
public class FetchController {
	
	/**
	 * Http 가져오기
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView httpFetch(HttpServletRequest request, HttpServletResponse respons) {
		return new ModelAndView("manage/system/dwr/httpFetch");
	}
	

}
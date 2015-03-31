package com.taxholic;


import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.cxf.dto.Rest;


@Controller
@RequestMapping("/test/*.do")
public class TestController{
	
	@Resource
	private MessageSourceAccessor message;
	
	
	@RequestMapping
	@ResponseBody
	public  String  logging() {
		
		Rest rest = new Rest();
		rest.setUserId("aaa");
		rest.setRoleNm("admin");
		
		return "";
		
	} 
		
		
}
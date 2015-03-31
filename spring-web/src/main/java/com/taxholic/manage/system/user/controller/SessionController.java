package com.taxholic.manage.system.user.controller;


import java.util.ArrayList;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthInfo;
import com.taxholic.core.util.StringUtil;

@Controller("com.taxholic.manage.system.user.controller.SessionController")
@RequestMapping("/manage/system/user/*.do")
public class SessionController  {
	
	@Resource
	private SessionRegistryImpl sessionReg;

	
	/**
	 * Session 유저 목록
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView userSessionList(HttpServletRequest request, HttpServletResponse respons) throws Exception{
		
		String p = ServletRequestUtils.getStringParameter(request, "p");
		if(StringUtil.chkNum(p) == false) p = "1";
		
		
		ModelAndView mv = new ModelAndView("manage/system/user/userSessionList");
		
		List<Object> principals = this.sessionReg.getAllPrincipals();
		
		List<AuthDto> userList = new ArrayList<AuthDto>();
		
		for (Object principal: principals) {
//		    	System.out.print( ">> " +  ((AuthInfo)principal).getUser().getUserId() + "\t\t");
//		    	System.out.println( ((AuthInfo)principal).getUser().getSessionId() + ":" + ((AuthInfo)principal).getUser().getLoginDate());
	    	userList.add(((AuthInfo)principal).getUser());
		}
		
		int page = Integer.parseInt(p);
		int row = 15;
		int total = userList.size();
		int totalPage  = (int) Math.ceil( total / (double)row);
		if(page > totalPage) page = totalPage;
		int start = (page - 1) * row;
		int end = (page ==totalPage) ? total :row * page;
		
		List subList = new ArrayList();
		if(total > 0) subList = userList.subList(start,end) ;
		
		mv.addObject("p", p);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		mv.addObject("start", start);
		mv.addObject("userList", subList);
		
		return mv;
	}
	
	/**
	 * Session 유저 강제로그아웃
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public void userSessionLogOut(HttpServletRequest request, HttpServletResponse respons) throws Exception{
		
		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId");
		
		if( this.sessionReg.getSessionInformation(sessionId) != null){
			this.sessionReg.getSessionInformation(sessionId).expireNow();
		}
		
	}

	
}
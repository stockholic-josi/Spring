package com.taxholic.manage.system.code.controller;


import java.io.IOException;
import java.util.List;







import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.manage.system.code.dto.AuthCode;
import com.taxholic.manage.system.code.dto.AuthCodeArr;
import com.taxholic.manage.system.code.service.AuthCodeService;

@Controller("com.taxholic.manage.system.code.controller.AuthCodeController")
@RequestMapping("/manage/system/code/auth/*.do")
public class AuthCodeController  {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private AuthCodeService authCodeService;
	
	
	/**
	 * 마스터 권한코드 목록
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView authCodeList(HttpServletRequest request, HttpServletResponse respons) {
		
		return new ModelAndView("manage/system/code/authCodeList");
	}
	
	/**
	 * 마스터 권한코드 목록(Json)
	 * @param respons
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void authCodeJson(HttpServletResponse response) throws IOException {
		response.getWriter().print(this.authCodeService.getAuthCodeList());
	}
	
	/**
	 * 마스터 권한코드 등록
	 * @param request
	 * @param respons
	 * @param dto
	 */
	@RequestMapping
	public void authCodeInsert(HttpServletResponse response, AuthCodeArr dto) {
		
		this.authCodeService.authCodeInsert(dto); 
		
	}
	
	/**
	 * 마스터 권한코드 삭제
	 * @param request
	 * @param respons
	 * @param dto
	 */
	@RequestMapping
	public void authCodeDelete(HttpServletResponse response, AuthCodeArr dto) {
		
		this.authCodeService.authCodeDelete(dto); 
		
	}
	
	/**
	 * 유저권한 목록(Json)
	 * @param request
	 * @param respons
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void authUserRoleJson(HttpServletResponse response) throws IOException {
		response.getWriter().print(this.authCodeService.getAuthUserList());
	}
	
	
	/**
	 * 유저권한 등록폼
	 * @param dto
	 */
	@RequestMapping
	public ModelAndView authCodeRegForm(AuthCode dto) {
		
		ModelAndView mv = new ModelAndView("manage/system/code/authCodeRegForm");
		mv.addObject("dto", dto);
		
		return mv;
		
	}
	
	/**
	 * 유저권한 등록 목록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView authCodeRegListAjax(AuthCode dto) {

		ModelAndView mv = new ModelAndView("manage/system/code/authCodeRegListAjax");
		
		List codeList = this.authCodeService.getAuthCodeRegList(dto);

		mv.addObject("codeList", codeList);
		
		
		return mv; 

	}
	
	/**
	 * 유저권한 등록 처리
	 * @param response
	 * @param dto
	 */
	@RequestMapping
	public void authCodeRegProc( HttpServletResponse response, AuthCodeArr dto) {
		 this.authCodeService.authCodeRegProc(dto);
		 
	}
	
	
}
package com.taxholic.front.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.core.util.SecuUtil;
import com.taxholic.core.util.SysUtil;
import com.taxholic.front.user.dto.User;
import com.taxholic.front.user.service.UserService;

@Controller("com.taxholic.front.user.controller.UserController")
@RequestMapping("/user/*.do")
public class UserController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 로그인 폼
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("user/loginForm");		
		
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect");
		
		mv.addObject("error", StringUtil.chkNull(request.getParameter("error")));
		mv.addObject("redirect", StringUtil.chkNull(redirect));
		
		String password = "1234";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		
		System.out.println("username				:	stock");
		System.out.println("passwd 				:	1234");
		System.out.println("encodedPassword		: 	" + encodedPassword);
		
		return mv;
		
	}
	
	/**
	 *  회원가입 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("user/joinForm");	
		
		return mv;
		
	}
	
	/**
	 * 아이디 검색 폼
	 * @param dto
	 */
	@RequestMapping
	public ModelAndView idSearchForm( User dto) {
		
		ModelAndView mv = new ModelAndView("user/idSearch");
		
		mv.addObject("dto", dto);
		
		return mv;
	}
	
	/**
	 * 아이디 검색
	 * @param response
	 * @param dto
	 * @throws IOException 
	 */
	@RequestMapping("/user/ajax/idSearchProc.do")
	public void idSearchProc( HttpServletResponse response, User dto) throws IOException {
		
		int count = 0;
		if( StringUtil.chkString("^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$", StringUtil.chkNull(dto.getUserId())) == true ) {
			count = this.userService.getIdSearch(dto.getUserId());
		}
		response.setContentType("text/html");
		response.getWriter().print(count);
	}
	
	/**
	 * 닉네임 검색
	 * @param dto
	 */
	@RequestMapping
	public ModelAndView nickSearchForm(User dto) {
		
		
		ModelAndView mv = new ModelAndView("user/nickSearch");

		mv.addObject("dto", dto);
		
		return mv;
		
	}
	
	/**
	 * 닉네임 검색
	 * @param response
	 * @param dto
	 * @throws IOException 
	 */
	@RequestMapping("/user/ajax/nickSearchProc.do")
	public void nickSearchProc(HttpServletResponse response, User dto) throws IOException {

		int count = 0;
		if( StringUtil.chkString("^[a-zA-Z0-9가-힝]{2,10}$", StringUtil.chkNull(dto.getUserNm())) == true ) {
			count = this.userService.getNickSearch(dto.getUserNm());
		}
		
		response.setContentType("text/html");
		response.getWriter().print(count);
		
	}
	
	/**
	 * 회원가입
	 * @param request
	 * @param response
	 * @param dto
	 * @throws Exception 
	 */
	@RequestMapping
	public void joinProc(HttpServletRequest request, HttpServletResponse response, User dto) throws Exception {
		
		
//		System.out.println("Captcha : " + SysUtil.getCookie(request, "captcha"));
//		System.out.println("id : " + dto.getUserId()  +">>"+ HtmlUtil.chkString("^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$", dto.getUserId()));
//		System.out.println("nick : " + dto.getUserNm()  +">>"+ HtmlUtil.chkString("^[a-zA-Z0-9가-힝]{2,10}$", dto.getUserNm()));
//		System.out.println("email : " + dto.getEmail()  +">>"+ HtmlUtil.chkString("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", dto.getEmail()));

		int formChk = 0;
		boolean isId = false;
		boolean isNick = false;
		boolean isEmail = false;
		Map map = new HashMap();  
		String captcha = SysUtil.getCookie(request, "captcha");
		
		//captcha 체크
		if(!captcha.equals(StringUtil.chkNull(dto.getCaptcha()))){
			formChk++;
		}
		
		//아이디 체크
		if( StringUtil.chkString("^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$", dto.getUserId()) == false) formChk++;
		
		//닉네임 체크
		if( StringUtil.chkString("^[a-zA-Z0-9가-힝]{2,10}$", dto.getUserNm()) == false) formChk++;
		
		//이메일 체크
		if( StringUtil.chkString("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", dto.getEmail()) == false)	formChk++;
		
		if(formChk == 0){
			
			//이메일 중복체크
			if( this.userService.getEmailSearch(dto.getEmail()) > 0 ) {
				isEmail = true;
				map.put( "reg", "isEmail" );
			
			//아이디 중복체크
			}else if( this.userService.getIdSearch(dto.getUserId()) > 0 ) {
				isId = true;
				map.put( "reg", "isId" );
			
			//닉네임 중복체크
			}else if( this.userService.getNickSearch(dto.getUserNm()) > 0 ) {
				isNick = true;
				map.put( "reg", "isNick" );
			}
		}else{
			map.put( "reg", "fail" );
		}
		
		
		//정보 등록 (이메일 인증처리 추후 추가 현재는 바로 승인)
		if(isEmail == false && isId == false && isNick == false && this.userService.joinProc(dto) == true){
			map.put( "reg", "success" );
		}
		
		response.setContentType("text/html");
		response.getWriter().print(JSONObject.fromObject(map));
		
	}
	
	/**
	 * 회원가입 결과
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView joinResult(HttpServletRequest request, HttpServletResponse response, User dto) {
		
		ModelAndView mv = new ModelAndView("user/joinResult");
		
		AuthDto user = new AuthDto();
		user.setUserId(dto.getUserId());
		user.setUseYn("N");
		AuthDto info = this.userService.getUserInfo(user);
		
		SecuUtil sec = new SecuUtil();
		String certificate = sec.enc( info.getUserId() + ":" + info.getEmail() );
		
		mv.addObject("info", info);
		mv.addObject("certificate", certificate);
		
		return mv;
	}
	
	/**
	 * 회원가입 이메일 인증
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws ServletRequestBindingException 
	 */
	@RequestMapping
	public ModelAndView joinCertificate(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
		
		ModelAndView mv = new ModelAndView("user/joinCertificate");
		
		String msg = "fail";
		String auth = StringUtil.chkNull(ServletRequestUtils.getStringParameter(request, "auth"));
		
		if(auth.length() < 8){
			msg = "fail";
		}else{
			
			SecuUtil sec = new SecuUtil();
			String authDec = sec.dec(auth);
			String[] authArr = authDec.split(":");
			
			if(authArr.length == 2){
				
				AuthDto user = new AuthDto();
				user.setUserId(authArr[0]);
				user.setUseYn("N");
				
				AuthDto info = this.userService.getUserInfo(user);
				if(this.userService.joinCertificate(info) > 0) {
					msg = "success";
					mv.addObject("info", info);
				}
			}
		}
		
		mv.addObject("msg", msg);
		
		return mv;
	}
	
	/**
	 *  회원 정보수정 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("user/userInfo");	

		AuthDto auth = AuthUtil.getUser();
		
		AuthDto user = new AuthDto();
		user.setUserId(auth.getUserId());
		user.setUseYn("Y");
		AuthDto info = this.userService.getUserInfo(user);
		
		mv.addObject("info", info);
		
		return mv;
		
	}
	
	/**
	 * 회원 정보수정
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userInfoModify(HttpServletRequest request, HttpServletResponse response, User dto) {
		
		ModelAndView mv = new ModelAndView("message");	

		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		Map map = new HashMap();  
		
		if(this.userService.userInfoUpdate(dto) > 0){
			map.put( "reg", "success" );
		}else{
			map.put( "reg", "fail" );
		}
		
		mv.addObject("msg",JSONObject.fromObject(map));
		
		return mv;
	}
	
	/**
	 *  회원 비밀번호 변경 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userPw(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("user/userPw");	
		
		return mv;
		
	}
	
	/**
	 *  회원 비밀번호 변경
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userPwModify(HttpServletRequest request, HttpServletResponse response, User dto) {
		
		ModelAndView mv = new ModelAndView("message");	

		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		Map map = new HashMap();  
		
		if(this.userService.userPwUpdate(dto) > 0){
			map.put( "reg", "success" );
		}else{
			map.put( "reg", "fail" );
		}
		
		mv.addObject("msg",JSONObject.fromObject(map));
		
		return mv;
		
	}
	
	/**
	 *  회원 탈퇴 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userOut(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("user/userOut");	
		
		return mv;
		
	}
	
	/**
	 *  회원 탈퇴
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView userOutProc(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("message");
		
		return mv;
		
	}
	
} 
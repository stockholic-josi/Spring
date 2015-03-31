package com.taxholic.front.main.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.front.board.dto.Board;
import com.taxholic.front.user.dto.User;

@Controller("com.taxholic.front.main.controlle.MainController")
@RequestMapping("/*.do")
public class MainController{
	
	@Resource
	private MessageSourceAccessor message;
	
	@Resource
	private ServletContext servletContext;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	/**
	 * 404 에러
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView error404(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:/error404.html");
	}
	
	
	/**
	 * 메인페이지
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("main");
		
		AuthDto auth = AuthUtil.getUser();
		
		boolean admMenu = false; 
		List  roleList = AuthUtil.getUser().getRoleList();
		for(int i = 0; i < roleList.size();i++   ){
			if( roleList.get(i).equals("ADM") || roleList.get(i).equals("SYS") ){
				admMenu = true;
				break;
			}
		}
		
		mv.addObject("admMenu",admMenu);
		mv.addObject("auth",auth);
		
		return mv;
		
	}
	
	
	/**
	 * 다운로드
	 * @param request
	 * @param response
	 */
	@RequestMapping
	public void downLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String target = ServletRequestUtils.getStringParameter(request, "target");
		String fileNm = ServletRequestUtils.getStringParameter(request, "fileNm");
		String fileRnm = ServletRequestUtils.getStringParameter(request, "fileRnm");
		
		String filePath = message.getMessage("DocBase") + message.getMessage(target + ".filePath");
		
		try{ 
			java.io.File file =  new java.io.File(filePath,fileNm); 
			
			String mime = servletContext.getMimeType(file.toString());
	        if (mime == null) mime = "application/octet-stream";
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=" +   new String(fileRnm.getBytes("euc-kr"), "iso-8859-1"));
	        response.setHeader("Content-Length", "" + file.length() );
	        
	        InputStream in = new FileInputStream(file);
	        OutputStream out =  response.getOutputStream();        
	        
	        BufferedInputStream bin = null;
	        BufferedOutputStream bos = null;
	        
	        try {
	            bin = new BufferedInputStream( in );
	            bos = new BufferedOutputStream( out ); 
	        
	            byte[] buf = new byte[2048]; //buffer size 2K.
	            int read = 0;
	            while ((read = bin.read(buf)) != -1) {
	                bos.write(buf,0,read);
	            }
	        } finally {
	            bos.close();
	            bin.close();
	        }        

		}catch(Exception e){}
		
	}
	
	
	@RequestMapping
	public String ddddd(@RequestBody String body, String aaa,Model model) {
		
		System.out.println("----------->" + aaa);
		System.out.println("----------->" + body);
		
		HashMap hm = new HashMap();
		hm.put("result1", "test1");
		hm.put("result2", "test222");
		
	    model.addAttribute("boardList",new Board());
	 
	    return "jsonView";
		
	}
	
	@RequestMapping
	@ResponseBody
	 public String valid(@ModelAttribute("User") @Valid User user, BindingResult result){
		
		
		List<ObjectError> list = result.getAllErrors();
        for (ObjectError e : list) {
           System.out.println(" ObjectError : " + e);
        }
		
		
		return "";
		
	}
		
}
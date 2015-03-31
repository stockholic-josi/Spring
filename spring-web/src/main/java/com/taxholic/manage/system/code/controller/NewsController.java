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

import com.taxholic.manage.system.code.dto.News;
import com.taxholic.manage.system.code.dto.NewsArr;
import com.taxholic.manage.system.code.service.NewsService;

@Controller("com.taxholic.manage.system.code.controller.NewsController")
@RequestMapping("/manage/system/code/news/*.do")
public class NewsController  {
	
	@Resource
	private NewsService newsService;
	
	
	/**
	 * 뉴스 목록
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsList() {
		
		return new ModelAndView("manage/system/code/newsList");
	}
	
	/**
	 * 뉴스 목록(Json)
	 * @param request
	 * @param respons
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void newsJson(HttpServletResponse response, News dto) throws IOException {
		

		ModelAndView mv = new ModelAndView("message");
		
		JSONObject jsonObject = this.newsService.getNewsList(dto);
		
		response.getWriter().print(jsonObject);

	}
	
	/**
	 * 뉴스 등록
	 * @param request
	 * @param respons
	 * @param dto
	 */
	@RequestMapping
	public void newsInsert(HttpServletResponse respons, NewsArr dto) {
		this.newsService.newsInsert(dto); 
	}
	
	/**
	 * 뉴스 삭제
	 * @param request
	 * @param respons
	 * @param dto
	 */
	@RequestMapping
	public void newsDelete(HttpServletResponse respons, NewsArr dto) {
		this.newsService.newsDelete(dto); 
	}
	
	
}
package com.taxholic.front.news.controller;

import java.io.IOException;
import java.util.List;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.util.StringUtil;
import com.taxholic.front.news.service.RssNewsService;
import com.taxholic.manage.system.code.dto.News;
import com.taxholic.manage.system.code.dto.NewsRss;
import com.taxholic.manage.system.code.service.NewsService;

@Controller("com.taxholic.front.news.controller.NewsController")
@RequestMapping("/front/news/*.do")
public class NewsController  {
	
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private RssNewsService rssNewsService;
	
	
	/**
	 * 뉴스 대상 목록
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsList(HttpServletRequest request, HttpServletResponse respons, News dto) {
		
		ModelAndView mv = new ModelAndView("front/news/newsList");
		
		if(dto.getFlag() == null || (
				!dto.getFlag().equals("stock") 
				&& !dto.getFlag().equals("economy") 
				&& !dto.getFlag().equals("society") 
				&& !dto.getFlag().equals("culture") 
				&& !dto.getFlag().equals("nation") 
				&& !dto.getFlag().equals("sports") 
				&& !dto.getFlag().equals("entertain") 
		)) dto.setFlag("stock");
		
		dto.setUseYn("Y");
		JSONObject jsonObject = this.newsService.getNewsList(dto);
		mv.addObject("newsData",jsonObject.toString());
		mv.addObject("flag",dto.getFlag());
		
		return mv;
	}
	
	/**
	 * 뉴스 RSS 데이터
	 * @param respons
	 * @param dto
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void rssNewsListJson(HttpServletResponse respons, NewsRss  dto) throws IOException {

		respons.getWriter().print(this.rssNewsService.getRssNewsList(dto));

	}
	
	/**
	 * 뉴스 RSS 전체
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsAllList(NewsRss  dto) {
		
		ModelAndView mv = new ModelAndView("front/news/newsAllList");
		
		int rowCount = 30;
		dto.setStart(0);
		dto.setLimit(rowCount);
		
		if(dto.getFlag() == null || (
				!dto.getFlag().equals("stock") 
				&& !dto.getFlag().equals("economy") 
				&& !dto.getFlag().equals("society") 
				&& !dto.getFlag().equals("culture") 
				&& !dto.getFlag().equals("nation") 
				&& !dto.getFlag().equals("sports") 
				&& !dto.getFlag().equals("entertain") 
		)) dto.setFlag("stock");
		
		
		
		if( !StringUtil.chkNull(dto.getSearchString()).equals("") ){
			mv.addObject("searchString",dto.getSearchString());
			dto.setSearchString("'*" + dto.getSearchString() + "*'");
		}
		
		int searchCnt = this.rssNewsService.getRssNewsAllCount(dto);
		List newsList = this.rssNewsService.getRssNewsAllList(dto);
		
		mv.addObject("searchCnt",searchCnt);
		mv.addObject("newsList",newsList);
		mv.addObject("rowCount",rowCount);
		mv.addObject("flag",dto.getFlag());
		
		return mv; 

	}
	
	/**
	 * 뉴스 RSS 전체(Data)
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsAllAjax(NewsRss  dto) {
		
		ModelAndView mv = new ModelAndView("front/news/newsAllAjax");
		
		if( !StringUtil.chkNull(dto.getSearchString()).equals("") ){
			dto.setSearchString("'*" + dto.getSearchString() + "*'");
		}
		
		List newsList = this.rssNewsService.getRssNewsAllList(dto);

		mv.addObject("newsList",newsList);
		
		return mv; 

	}
	
	
	/**
	 * 뉴스 RSS 종목별
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsItemList(HttpServletRequest request, HttpServletResponse respons, NewsRss  dto) {
		
		ModelAndView mv = new ModelAndView("front/news/newsItemList");
		
		int rowCount = 30;
		dto.setStart(0);
		dto.setLimit(rowCount);
		
		if(dto.getFlag() == null || (
				!dto.getFlag().equals("stock") 
				&& !dto.getFlag().equals("economy") 
				&& !dto.getFlag().equals("society") 
				&& !dto.getFlag().equals("culture") 
				&& !dto.getFlag().equals("nation") 
				&& !dto.getFlag().equals("sports") 
				&& !dto.getFlag().equals("entertain") 
		)) dto.setFlag("stock");
		
		if( !StringUtil.chkNull(dto.getSearchString()).equals("") ){
			mv.addObject("searchString",dto.getSearchString());
			dto.setSearchString("'*" + dto.getSearchString() + "*'");
		}
		
		int searchCnt = this.rssNewsService.getRssNewsAllCount(dto);
		List newsList = this.rssNewsService.getRssNewsAllList(dto);
		
		mv.addObject("searchCnt",searchCnt);
		mv.addObject("newsList",newsList);
		mv.addObject("rowCount",rowCount);
		mv.addObject("flag",dto.getFlag());
		
		return mv; 

	}
	
	
}
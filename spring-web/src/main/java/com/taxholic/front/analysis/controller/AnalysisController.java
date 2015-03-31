package com.taxholic.front.analysis.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.taxholic.manage.system.code.service.KospiService;



public class AnalysisController extends MultiActionController  {
	
	private KospiService kospiService;
	
	public void setKospiService(KospiService kospiService) {
		this.kospiService = kospiService;
	}
	
	/**
	 * 주식 매수 분석
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView stockBuy( HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("front/analysis/stockBuy");
		
		return mv;
	
	}
	
	/**
	 * 주식 매도 분석
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView stockSell( HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("front/analysis/stockSell");
		
		return mv;
	
	}
	
	/**
	 * 기업정보
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException 
	 */
	public ModelAndView kospiInfo( HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
		
		ModelAndView mv = new ModelAndView("front/analysis/kospiInfo");
		
		String searchString = ServletRequestUtils.getStringParameter(request, "searchString");
		
//		List infoList = this.kospiService.getKospiInfo(searchString);
		List infoList = new ArrayList();
		mv.addObject("infoList", infoList);
		mv.addObject("searchString", searchString);
		
		return mv;
	
	}
	
	
	/**
	 * 선물 매도 분석
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView futureBuy( HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("front/analysis/stockBuy");
		
		return mv;
	
	}
	
	/**
	 * 선물 매도 분석
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView futureSell( HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("front/analysis/stockBuy");
		
		return mv;
	
	}
	
}
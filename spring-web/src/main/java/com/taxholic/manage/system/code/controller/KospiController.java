package com.taxholic.manage.system.code.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.util.ExcelUtil;
import com.taxholic.manage.system.code.dto.Kospi;
import com.taxholic.manage.system.code.dto.KospiArr;
import com.taxholic.manage.system.code.service.KospiService;

@Controller("com.taxholic.manage.system.code.controller.KospiController")
@RequestMapping("/manage/system/code/kospi/*.do")
public class KospiController  {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private KospiService kospiService;
	
	
	/**
	 * 종목 목록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView kospiList() {
		return new ModelAndView("manage/system/code/kospiList");
	}
	
	/**
	 * 종목 목록(Json)
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void kospiJson(HttpServletResponse response, Kospi dto) throws IOException {
		
		if(dto.getFlag() == null) dto.setFlag("01");
		
		response.getWriter().print( this.kospiService.getKospiList(dto) );
	}
	
	/**
	 * 종목 삭제
	 * @param response
	 * @return
	 */
	@RequestMapping
	public void kospiDelete(HttpServletResponse response, KospiArr dto) {
		this.kospiService.kospiDelete(dto);
	}
	
	/**
	 * 종목 목록(Excel)
	 * @param response
	 * @param dto
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public void kospiExcel(HttpServletResponse response, Kospi dto) throws UnsupportedEncodingException {
		
		String excelFile = "KOSPI_종목.xls";
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" +   new String(excelFile.getBytes("euc-kr"), "iso-8859-1"));
		
		List<Kospi> list = this.kospiService.getKospiExcel(dto);

		List rowList =  new ArrayList();
		String [] header = {"코드","종목","가격","구분"};
		
		for(int i = 0; i < list.size(); i++){
			int k = 0;
			String [] rowData = new String[header.length];
			
			rowData[k++] = list.get(i).getCodeCd();
			rowData[k++] = list.get(i).getCodeNm();
			rowData[k++] = list.get(i).getPrice();
			rowData[k++] = list.get(i).getFlag();
			
			rowList.add(rowData);
		}

		ExcelUtil.createExcel(header, rowList, response);
		
	}
	
	/**
	 * 종목 엑셀 전송폼
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView kospiExcelForm() {
		return new ModelAndView("manage/system/code/kospiExcelForm");
	}
	
	/**
	 * 종목 엑셀 전송처리
	 * @param request
	 * @param respons
	 */
	@RequestMapping
	public ModelAndView kospiExcelUpload(Kospi dto) {
		
		ModelAndView mv = new ModelAndView("manage/system/code/kospiExcelForm");
		
		int result = 0;
		InputStream stream = null;

		try {
			Iterator fileIterator = dto.getFile().iterator();
	    	
	    	MultipartFile multiFile = (MultipartFile)fileIterator.next();

			if (multiFile.getSize() > 0) {
				try {
					stream = multiFile.getInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
			if(stream != null){
				POIFSFileSystem fs = new POIFSFileSystem(stream);
				
				String [] header = {"codeCd","codeNm","price","flag"};
				List<HashMap> list = ExcelUtil.getExcel(header, fs);
				
				for(int i = 0; i < list.size(); i++){
					int k = 0;
				}
				
				result = this.kospiService.kospiInsert(list);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {	stream.close();} catch (IOException e) {}
		}
		
		mv.addObject("result",result);
		
		return mv;
		
	}
	
	
}
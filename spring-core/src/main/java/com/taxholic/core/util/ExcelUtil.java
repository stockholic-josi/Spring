package com.taxholic.core.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class ExcelUtil {
	
	/**
	 * 엑셀 생성		
	 * @param header
	 * @param list
	 * @param respons
	 */
	public static void createExcel(String [] header, List list, HttpServletResponse respons) {
		
		HSSFWorkbook wb = new HSSFWorkbook();						//워크북을 생성
		HSSFSheet sheet = wb.createSheet("Sheet1");						//시트 생성
		
		//스타일
		HSSFCellStyle style = wb.createCellStyle();							
		style.setFillPattern ((short) 1);												
		style.setFillForegroundColor (HSSFColor.GREY_25_PERCENT.index);	// 배경색 지정 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);							//정렬
		
		//헤더 생성
		HSSFRow initRow = sheet.createRow(0);			
		
		
		int headerCount = header.length;
		for(int i = 0; header !=null && i < headerCount; i++){
			
			//셀 너비
			 sheet.setColumnWidth((short)i, (short)4000);
			
			HSSFCell cell = initRow.createCell((short)i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		//데이터 생성
		for(int i = 0; i < list.size(); i++){
			String [] arrData = (String[]) list.get(i);
			for(int j =0; arrData != null && j <arrData.length; j++){
				HSSFRow row = sheet.createRow( i+1 );
				row.createCell((short)j).setCellValue(arrData[j]);
			}
		}
		
		OutputStream fileOut = null;
		
		try{	
			fileOut = respons.getOutputStream();
			wb.write(fileOut);
		}catch (Exception e){
			e.getStackTrace();
		}finally{
			if(fileOut != null)	try {fileOut.close();} catch (IOException e) {}
		}
		
	}
	
	/**
	 * 엑셀데이터 로드
	 * @param header
	 * @param pfs
	 * @return List
	 */
	public static List getExcel(String [] header, POIFSFileSystem pfs) {
		
		List list = new ArrayList();
		
		try{
			//워크북을 생성
			HSSFWorkbook wb = new HSSFWorkbook(pfs);
			
			//생성된 워크북을 이용하여 시트 수만큼 돌면서 엑셀 시트 하나씩을 생성
			int sheetNum = wb.getNumberOfSheets();
			for (int k = 0; k < sheetNum; k++) {
				HSSFSheet sheet = wb.getSheetAt(k);
				
				//생성된 시트를 이용하여 그 행의 수만큼 돌면서 행을 하나씩 생성
				int rows = sheet.getPhysicalNumberOfRows();
				for (int r = 1; r < rows; r++) {
					HSSFRow row   = sheet.getRow(r);
					
					//생성된 행을 이용하여 그 셀의 수만큼 돌면서 셀을 하나씩 생성
//					int cells = row.getPhysicalNumberOfCells();
					int cells = header.length;
					HashMap hm = new HashMap();
					for (short c = 0; c < cells; c++) { 
				    	HSSFCell cell  = row.getCell(c); 
				   		if (cell != null) {
				   			hm.put(header[c], cell.toString());
				   		}
					}
					list.add(hm);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	
}
package com.taxholic.core.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	 
	/***
	 * null && 공백 체크 <p>
	 * @param str
	 * @return
	 */
	public static String chkNull(String str){
		if(str == null){
			return "";
		}
		return str.trim();
	}
	
	/**
	 * 크로스사이트 스크립트 막기
	 * @param str
	 * @return String
	 */
	public static String getScriptChange(String str){
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	/***
	 * 숫자체크 <p>
	 * @param num
	 * @return
	 */
	public static boolean chkNum(String num){	
		
		boolean chk = true;
		
		if(num == null) num = "";
		
		Pattern patt = Pattern.compile("^[0-9]*[0-9]$");		
		Matcher match = patt.matcher(num);
					
		if(num == null || !match.find()){
			chk = false;
		}else{
			chk = true;
		}

		return chk;
	}
	
	/***
	 * 정규식 문자열 체크<p>
	 * @param pt : 패턴 <br>
	 * @param str : 문자열
	 * @return
	 */
	public static boolean chkString(String pt, String str){

		boolean chk = true;
		
		Pattern patt = Pattern.compile(pt);		
		Matcher match = patt.matcher(str);
					
		if(str == null || !match.find()){
			chk = false;
		}else{
			chk = true;
		}

		return chk;
	}
	
	/***
	 * 뒤로가기 <p>
	 * @return String
	 */
	public static String gotoBack(){
		return "<script>history.back()</script>";
	}
	
	/***
	 * html 처리 <p>
	 * @param str
	 * @return
	 */
	  public static String htmlTag(String str) {	   
		  if(str == null)	return "";
		  return rplc( rplc(str, "<", "&lt;"), "\n", "<br>");
	  }	  
	  
	  public static String rplc(String mainString,String oldString,String newString) {
		  
		if (mainString == null) {
			return null;
		}
		if (oldString == null || oldString.length() == 0) {
			return mainString;
		}
		if (newString == null) {
			newString = "";
		}
	
		int i = mainString.lastIndexOf(oldString);
		if (i < 0) return mainString;
	
		StringBuffer mainSb = new StringBuffer(mainString);
	
		while (i >= 0) {
			mainSb.replace(i, (i + oldString.length()), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}
	  
	/**
	 * <br> 처리 <p>
	 * @param src
	 * @return
	 */
	public static String lineBreak(String src){
		
		if(src== null ) return "";
		
		int len = src.length();
		
		int linenum = 0, i = 0;

		for(i = 0; i < len; i++){
			if(src.charAt(i) == '\n') linenum ++;
		}
		StringBuffer dest = new StringBuffer(len + linenum * 3);

		for(i = 0; i < len; i++){
			if(src.charAt(i) == '\n'){
				dest.append("<br>");
			}else{
				dest.append(src.charAt(i));
			}
		}

		return dest.toString();
	}
	
	/**
	 * 셀렉트박스 생성
	 * @param key
	 * @param text
	 * @param flag
	 * @return
	 */
	public static String makeSelect(List key, List text, String flag){
		StringBuffer dataList = new StringBuffer();

		for(int i = 0; i < key.size(); i++){

			if(key.get(i).equals(flag)){
				dataList.append("<option value='" + key.get(i) + "' selected>" + text.get(i) + "</option>");
			}else{
				dataList.append("<option value='" + key.get(i) + "'>" + text.get(i) + "</option>");
			}
		}

		return dataList.toString();
	}
	
	/**
	 * 홑 따옴표(') 처리 <p>
	 * @param src
	 * @return
	 */
	public static String quotationMark(String src){

		if(src == null || (src.trim()).equals("")){
			return "";
		}else{
			int len = src.length();
			int linenum = 0, i = 0;

			for(i = 0; i < len; i++){
				if(src.charAt(i) == '\''){
					linenum++;
				}
			}

			StringBuffer dest = new StringBuffer(len + linenum);

			for(i = 0; i < len; i++){
				if(src.charAt(i) == '\''){
					dest.append("''");	
				}else{
					dest.append(src.charAt(i));
				}
			}

			return dest.toString();
		}
	}	
	
	 /***
	  * reply 처리 <p>
	  * @param src <br>
	  * @param Method : 1,일반폼전송, 2, multiPart 폼 전
	  * @return
	  */
	  public static String reply(String src, int Method) {
		int len = src.length();
		int linenum = 0, i = 0;
		
		String mt = (Method == 2) ? ">" : "\\>"; 
		
		for (i = 0; i < len; i++){
			if (src.charAt(i) == '\n')  linenum++;
		}
		
		StringBuffer dest = new StringBuffer(len + linenum + 1);
		
		dest.append(">");
		
		for (i = 0; i < len; i++) {
		  if (src.charAt(i) == '\n'){
		    dest.append(mt);
		  } else{
		    dest.append(src.charAt(i));
		  }	
		}	
		return dest.toString();
	  }
	  
	
	 /***
	  * 문자열 반복 <p>
	  * @param str <br>
	  * @param num :  반복 수
	  * @return
	  */
	public static String strRepeat(String str, int num){
		String result = "";
		for(int i = 0; i < num; i++){
			result += str; 
		}
		return result;
	}	

	
	/***
	 * tab 처리 <p>
	 * @param src 
	 * @return
	 */
	public static String tabkey(String src) {
	    int len = src.length();
	    int linenum = 0, i = 0;
	
	    for (i = 0; i < len; i++)
	      if (src.charAt(i) == '\t')
	        linenum++;
	
	    StringBuffer dest = new StringBuffer(len + linenum * 7);
	
	    for (i = 0; i < len; i++) {
	      if (src.charAt(i) == '\t')
	        dest.append("        ");
	      else
	        dest.append(src.charAt(i));
	    }
	
	    return dest.toString();
	}
	
	
	/***
	 * 한글 처리 <p>
	 * @param str
	 * @return String
	 */
	public static String toKorean(String str) {
		try {
		  return new String(str.getBytes("Cp1252"), "EUC_KR");
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (NullPointerException npe){
			return "";
		}
	}
	
	public static String korMark(String str){
		return quotationMark(toKorean(str));
	}

	
	/***
	 * 태그제거 <p>
	 * @param str <br>
	 * @return String
	 */
	public static String removeTag(String str){
		
		if(str == null) return "";
		
		int lt = str.indexOf("<");

		if ( lt != -1 ) {
			int gt = str.indexOf(">", lt);
			if ( (gt != -1) ) {
				str = str.substring( 0, lt ) + str.substring( gt + 1 );				
				str = removeTag(str);		// 재귀 호출
			}
		}
		return str;
	}
	
	/***
	 * replace 처리 <p>
	 * @param str : 문자열 <br>
	 * @param pattern : 치환문자 <br>
	 * @param replace : 대상문자
	 * @return
	 */
   public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}   
	
   /***
    * 문자열 자르기 
    * @param str : 문자 <br>
    * @param i : 바이트 <br>
    * @param trail : 생략 문자열. 예) "..."
    * @return
    */
	public static String strlen(String str, int i, String trail) {
    	if (str==null) return "";
    	String tmp = str;
    	int slen = 0, blen = 0;
    	char c;
    	try {
        	if(tmp.getBytes("MS949").length>i) {
        		while (blen+1 < i) {
        			c = tmp.charAt(slen);
        			blen++;
        			slen++;
        			if ( c  > 127 ) blen++;  //2-byte character..
        		}
        		tmp=tmp.substring(0,slen)+trail;
        	}
        } catch(java.io.UnsupportedEncodingException e) {}
    	return tmp;
    }
	
	 /***
    * 제로필 숫자 처리 ex)zeroFill("0000","5") ==> 0005
    * @param format
    * @param input
    * @return
    */
	public static String zeroFill(String format, String input){	
		DecimalFormat DF = new DecimalFormat(format);
		String outPut = DF.format(Integer.parseInt(input)); 
		return outPut;
	}
	
	/**
	 * 통화 표시
	 * @param str
	 * @return
	 */
	public static String getPrice(String str) {
	  DecimalFormat df = new DecimalFormat("###,###");

	  return df.format(Long.parseLong(str));
	}
	
	
	public static int getNumber(String str) {
	  return Integer.parseInt(str);
	}
	
	public static double getRound(double val1, double val2) {
		 return  100 * Math.round( val1 / val2 * 10000) / 10000.0;
	}	
	
	public static int getRoundInt(double val1, double val2) {
		 return (int)( Math.round( val1/val2 ) );
	}

	
	/***
	 * 파일 사이즈 <p>
	 * @param file
	 * @return String
	 */
	public static String getFileSize(double file){	

		String fileValue = "";

		if(file < 1024){
			fileValue = Double.toString(file) + " byte";
		}else if(file >= 1024 && file < 1024 * 1024){
			file = file / (1024);
			file = (double)(int)(file * 100 + 0.5)/100.0; //소수 2 재자리 반올림
			fileValue = Double.toString(file) + " KB";
		}else{
			file = file / (1024 * 1024);
			file = (double)(int)(file * 100 + 0.5)/100.0;
			fileValue = Double.toString(file) + " MB";
		}

		return fileValue;
	}
	
	/**
	 * 파일 존재 유무
	 * @param fileName
	 * @return boolean
	 */
	public static boolean getFileExist(String fileName){	

		boolean isCheck = false;
		
		File file = new File(fileName);
		if(file.exists()) isCheck = true;
		
		return isCheck;
	}
	
	/**
	 * 페이징
	 * @param endRow : 로우 수
	 * @param total : 총레코드 수
	 * @param p : 현재 페이지
	 * @param param : 파라미터
	 * @return String
	 */
	public static String paging (int totalPage, String p_, String param,String url)  {

		int x = 10;
		int p = Integer.parseInt(p_);
		int s = 0;
		int e = 0;
		int d = p % x;											// 현재 페이지 위치
	
		switch(d){											// 루프의 처음 s 과 마지막 e	
			case 1:		// 현재 페이지가 네비게이션의 처음에 있을때..
				s = p;
				e = p + (x - 1);
			break;
	
			case 0:		// 현재 페이지가 마지막에 있을때.
				s = p - (x - 1);
				e = p; 
			break;
	
			default:	// 중간에 꼈을때...
				s = p - (d - 1);
				e = p + (x - d);
		}	
		
		StringBuffer dataList = new StringBuffer();
		
		if(e > totalPage) e = totalPage;		// 루프의 마지막이 총페이지를 넘는지 체크
	
		if(x < p){									//$x만큼 이전 찍기.
			int prev = s - 1;
			dataList.append("<a href='" + url + "?p=" + Integer.toString(prev) + param + "'><img src='/images/board/first.jpg' border='0' align='absmiddle'></a>&nbsp;");	
		}else{
			dataList.append("<img src='/images/board/first.jpg' border='0' align='absmiddle'>&nbsp;");
		}
	
		if(p != 1 ){									//이전
			int step1 = p - 1;
			dataList.append("<a href='" + url + "?p=" + step1 + param + "'><img src='/images/board/prev.jpg' border='0' align='absmiddle'></a>&nbsp;&nbsp;&nbsp;");
		}else{
			dataList.append("<img src='/images/board/prev.jpg' border='0' align='absmiddle'>&nbsp;&nbsp;&nbsp;");
		}	
		dataList.append(" | ");
	
		for(int i = s; i <= e ;i++){
			 if(i == p){
				dataList.append("<font color=#00276F><b>" + i + "</b></font> | ");
			 } else{ 	
				dataList.append("<a href='" + url + "?p=" + i + param + "'>" + i + "</a> | ");
			 } 	
		}
	
		if(p != totalPage){					//다음
			int step2 = p + 1;
			dataList.append("&nbsp;&nbsp;&nbsp;<a href='" + url + "?p=" + step2 + param + "'><img src='/images/board/next.jpg' border='0' align='absmiddle'></a>");
		}else{
			dataList.append("&nbsp;&nbsp;&nbsp;<img src='/images/board/next.jpg' border='0' align='absmiddle'>&nbsp");
		}
	
		if(totalPage > e){					//$e 만큼 다음찍기.
			int next = e + 1;
			dataList.append("&nbsp;<a href='" + url + "?p=" + next + param + "'><img src='/images/board/end.jpg' border='0' align='absmiddle'></a>");
		}else{
			dataList.append("&nbsp;<img src='/images/board/end.jpg' border='0' align='absmiddle'>");
		}
		
		return dataList.toString();
	}
}

package com.taxholic.core.util;


public class PagingUtil {
	
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
		int d = p % x;										// 현재 페이지 위치
	
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
	
//		if(x < p){									//$x만큼 이전 찍기.
//			int prev = s - 1;
//			dataList.append("<a href='" + url + "?p=" + Integer.toString(prev) + param + "'><img src='/images/board/first.jpg' border='0' align='absmiddle'></a>&nbsp;");	
//		}else{
//			dataList.append("<img src='/images/board/first.jpg' border='0' align='absmiddle'>&nbsp;");
//		}
	
		if(p != 1 ){									//이전
			int step1 = p - 1;
			dataList.append("<a href='" + url + "?p=" + step1 + param + "' class='prev'>《 이전</a>\n");
		}	
	
		for(int i = s; i <= e ;i++){
			 if(i == p){
				 dataList.append("<span class='current'>" + i + "</span>\n");
			 } else{ 	
				dataList.append("<a href='" + url + "?p=" + i + param + "' class='page'>" + i + "</a>\n");
			 } 	
		}
	
		if(p != totalPage){					//다음
			int step2 = p + 1;
			dataList.append("<a href='" + url + "?p=" + step2 + param + "' class='next'>다음 》</a>\n");
		}
	
//		if(totalPage > e){					//$e 만큼 다음찍기.
//			int next = e + 1;
//			dataList.append("&nbsp;<a href='" + url + "?p=" + next + param + "'><img src='/images/board/end.jpg' border='0' align='absmiddle'></a>");
//		}else{
//			dataList.append("&nbsp;<img src='/images/board/end.jpg' border='0' align='absmiddle'>");
//		}
		
		return dataList.toString();
	}
	
	/**
	 * 페이징
	 * @param endRow : 로우 수
	 * @param total : 총레코드 수
	 * @param p : 현재 페이지
	 * @param param : 파라미터
	 * @return String
	 */
	public static String pagingUrl (int totalPage, String p_, String param,String url)  {
		
		int x = 10;
		int p = Integer.parseInt(p_);
		int s = 0;
		int e = 0;
		int d = p % x;										// 현재 페이지 위치
	
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
	
		if(p != 1 ){									//이전
			int step1 = p - 1;
			dataList.append("<a href='" + url + param + "/" + step1 + "' class='prev' target='_top'>《 이전</a>\n");
		}	
	
		for(int i = s; i <= e ;i++){
			 if(i == p){
				 dataList.append("<span class='current'>" + i + "</span>\n");
			 } else{ 	
				dataList.append("<a href='" +  url  +  param + "/" + i + "' class='page' target='_top'>" + i + "</a>\n");
			 } 	
		}
	
		if(p != totalPage){					//다음
			int step2 = p + 1;
			dataList.append("<a href='" +  url  +  param + "/" + step2 + "' class='next' target='_top'>다음 》</a>\n");
		}
	
		return dataList.toString();
	}
}

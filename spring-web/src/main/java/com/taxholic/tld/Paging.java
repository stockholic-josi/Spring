package com.taxholic.tld;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Paging extends TagSupport{

	private int totalPage;
	private int page;
	private int pageCount = 0;
	private String param;
	private String url;

	public int doStartTag(){
		
		JspWriter out = pageContext.getOut();
		
		int x = (this.pageCount == 0) ? 10 : this.pageCount;
		int p = this.page;
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
			dataList.append("<a href='" + url + "?page=" + step1 + param + "' class='prev'>《 이전</a>\n");
		}	
	
		for(int i = s; i <= e ;i++){
			 if(i == p){
				 dataList.append("<span class='current'>" + i + "</span>\n");
			 } else{ 	
				 dataList.append("<a href='" + url + "?page=" + i + param + "' class='page'>" + i + "</a>\n");
			 } 	
		}
	
		if(p != totalPage){					//다음
			int step2 = p + 1;
			dataList.append("<a href='" + url + "?page=" + step2 + param + "' class='next'>다음 》</a>\n");
		}
		
		try {
			out.println(dataList);
		} catch(IOException ioe){          
			ioe.printStackTrace(); 
		}
		return SKIP_BODY; 
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
		 
}

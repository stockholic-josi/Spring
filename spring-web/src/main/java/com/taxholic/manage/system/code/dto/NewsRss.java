package com.taxholic.manage.system.code.dto;

public class NewsRss  implements java.io.Serializable{
	
	private String refNo;				//참조번호
	private String newsNm;				//뉴스사
	private String title;					//제목
	private String link;					//링크 
	private String description;			//요약
	private String pubDate;			//날짜
	private int timeDiff;					//날짜지난시간
	private String flag;					//구분
	private String searchString;		//검색어
	private int start;
	private int limit;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public int getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}
	public void setNewsNm(String newsNm) {
		this.newsNm = newsNm;
	}
	public String getNewsNm() {
		return newsNm;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchString() {
		return searchString;
	}
	
	
}

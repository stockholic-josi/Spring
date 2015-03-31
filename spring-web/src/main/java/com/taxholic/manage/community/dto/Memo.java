package com.taxholic.manage.community.dto;

import java.util.Date;

public class Memo implements java.io.Serializable{
	
	private String seqNo;			//번호
	private String userId;			//아이디
	private String content;			//내용
	private String regDate;			//등록일

	private String p;									//페이지
	private int totalPage; 							//총페이지
	private int total;									//총 로우수
	private String searchString;
	
	private int start;
	private int limit;
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchString() {
		return searchString;
	}
	
}

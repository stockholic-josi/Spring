package com.taxholic.front.board.dto;


public class Search implements java.io.Serializable{
	
	private int start = 0;							//시작 로우
	private int limit;									//마지막 로우
	private String searchKey ;						//검색구분
	private String searchValue;					//검색어


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
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}

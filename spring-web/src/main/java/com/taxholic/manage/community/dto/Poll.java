package com.taxholic.manage.community.dto;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.taxholic.front.board.dto.Search;

public class Poll {
	private String seqNo;				//일련번호
	private int num;				
	private String no;					//설문번호
	private String idx;					//항목번호
	private String title;					//제목
	private String content;				//내용
	private String startDate;			//시작 일
	private String endDate;			//종료 일
	private Date regDate;				//등록 일
	private String isNew;				//새글 구분
	private String searchString;		
	
	private int hit;						//투표수
	private int graphWidth = 20;	//그래프 사이즈
	private String hitRate = "0";		//투표율
	private String pollStatus;			//투표진행현황
	
	private List arrIdx = new ArrayList();					//항목번호
	private List question = new ArrayList();				//항목타이틀
	private List arrHit = new ArrayList();				//항목타이틀
	
	private String page;								//페이지
	private int totalPage; 							//총페이지
	private int total;									//총 로우수
	
	private int start = 0;							//시작 로우
	private int limit;									//마지막 로우
	
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getContent() {
		return content;
	}
	public List getQuestion() {
		return question;
	}
	public void setQuestion(List question) {
		this.question = question;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGraphWidth() {
		return graphWidth;
	}
	public void setGraphWidth(int graphWidth) {
		this.graphWidth = graphWidth;
	}
	public String getHitRate() {
		return hitRate;
	}
	public void setHitRate(String hitRate) {
		this.hitRate = hitRate;
	}
	public String getPollStatus() {
		return pollStatus;
	}
	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}
	public List getArrIdx() {
		return arrIdx;
	}
	public void setArrIdx(List arrIdx) {
		this.arrIdx = arrIdx;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
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
	public List getArrHit() {
		return arrHit;
	}
	public void setArrHit(List arrHit) {
		this.arrHit = arrHit;
	}

}

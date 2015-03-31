package com.taxholic.manage.system.code.dto;


public class News  implements java.io.Serializable{
	
	private String seqNo;				//일련번호
	private String itemGroup;			//아이템그룹
	private String itemNm;			//아이템
	private String newsNm;			//뉴스명
	private String newsLink;			//뉴스링크
	private int stp;						//순서
	/**
	 * 구분 
	 * stock		증권 
	 * economy	경제
	 * society	사회
	 * culture  	문화
	 * nation		국제
	 * sports		스포츠
	 * entertain	연예
	 */
	private String flag;					
	private String userId;				//유저아이디
	private String charset;				//charset
	private String useYn;				//사용여부

	private String editCd;
	
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public String getNewsNm() {
		return newsNm;
	}
	public void setNewsNm(String newsNm) {
		this.newsNm = newsNm;
	}
	public String getNewsLink() {
		return newsLink;
	}
	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}
	public int getStp() {
		return stp;
	}
	public void setStp(int stp) {
		this.stp = stp;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setEditCd(String editCd) {
		this.editCd = editCd;
	}
	public String getEditCd() {
		return editCd;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getCharset() {
		return charset;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUseYn() {
		return useYn;
	}
	
}

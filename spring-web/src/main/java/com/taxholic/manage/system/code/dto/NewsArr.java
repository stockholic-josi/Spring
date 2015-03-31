package com.taxholic.manage.system.code.dto;

import java.util.List;

public class NewsArr  implements java.io.Serializable{
	
	private List lstSeqNo;					//권한코드
	private List lstItemGroup;				//아이템그룹
	private List lstItemNm;					//아이템
	private List lstNewsNm;				//뉴스명
	private List lstNewsLink;				//뉴스링크
	private List lstStp;						//순서
	private List lstCharset;					//인코딩
	private List lstUseYn;					//사용여부
	private List lstEditCd;					//등록 : I,수정 : U 상태
	
	private String flag;
	
	public List getLstSeqNo() {
		return lstSeqNo;
	}
	public void setLstSeqNo(List lstSeqNo) {
		this.lstSeqNo = lstSeqNo;
	}
	public List getLstItemGroup() {
		return lstItemGroup;
	}
	public void setLstItemGroup(List lstItemGroup) {
		this.lstItemGroup = lstItemGroup;
	}
	public List getLstItemNm() {
		return lstItemNm;
	}
	public void setLstItemNm(List lstItemNm) {
		this.lstItemNm = lstItemNm;
	}
	public List getLstNewsNm() {
		return lstNewsNm;
	}
	public void setLstNewsNm(List lstNewsNm) {
		this.lstNewsNm = lstNewsNm;
	}
	public List getLstNewsLink() {
		return lstNewsLink;
	}
	public void setLstNewsLink(List lstNewsLink) {
		this.lstNewsLink = lstNewsLink;
	}
	public List getLstStp() {
		return lstStp;
	}
	public void setLstStp(List lstStp) {
		this.lstStp = lstStp;
	}
	public List getLstEditCd() {
		return lstEditCd;
	}
	public void setLstEditCd(List lstEditCd) {
		this.lstEditCd = lstEditCd;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public List getLstCharset() {
		return lstCharset;
	}
	public void setLstCharset(List lstCharset) {
		this.lstCharset = lstCharset;
	}
	public List getLstUseYn() {
		return lstUseYn;
	}
	public void setLstUseYn(List lstUseYn) {
		this.lstUseYn = lstUseYn;
	}

}

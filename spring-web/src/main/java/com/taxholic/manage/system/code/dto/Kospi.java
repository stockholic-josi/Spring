package com.taxholic.manage.system.code.dto;

import java.util.ArrayList;
import java.util.List;

import com.taxholic.front.board.dto.Search;

public class Kospi extends Search{
	
	private int num;					//일련번호
	private String codeCd;			//코드
	private String codeNm	;		//코드명
	private String price;				//가격
	private String fluctuate;		//등락
	private String rate;				//등락율
	private String info;				//기업정보
	private String flag;				//구분(01:코스피, 02:코스닥)
	private String regDate;			//수정일
	private List file = new ArrayList();					//업로드 파일
	
	private String sort;				//remote sort 컬럼명
	private String dir;				//remote sort 방향(desc, ase)
	
	private List lstCodeCd;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCodeCd() {
		return codeCd;
	}
	public void setCodeCd(String codeCd) {
		this.codeCd = codeCd;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List getLstCodeCd() {
		return lstCodeCd;
	}
	public void setLstCodeCd(List lstCodeCd) {
		this.lstCodeCd = lstCodeCd;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice() {
		return price;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public void setFile(List file) {
		this.file = file;
	}
	public List getFile() {
		return file;
	}
	public String getFluctuate() {
		return fluctuate;
	}
	public void setFluctuate(String fluctuate) {
		this.fluctuate = fluctuate;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getInfo() {
		return info;
	}
	
}

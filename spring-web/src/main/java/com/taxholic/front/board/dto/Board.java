package com.taxholic.front.board.dto;


import java.util.List;
import java.util.ArrayList;




import com.taxholic.front.board.dto.Search;

public class Board extends Search {
	
	private int num;					//일련번호
	private String no;					//번호
	private int total;					
	private int fid;						//글 그룹
	private int lev;						//글 깊이
	private int stp;						//글 순서
	private String userId;				//아이디
	private String userNm;			//이름
	private String title;					//제목
	private String content;			//내용
	private String html;				//HTML 사용
	private String ip;					//아이피
	private String readCnt;			//조회수
	private String regDate;			//등록일
	private String flag;					//게시판구분	
	private String isNew;				//새글 구분
	
	private int fileCount;									//파일 수
	private String fileName;								//파일명
	private String fileRealName;							//파일명
	private String fileExt;									//파일확장자
	private String fileSize;									//파일크기
	private String delFileIdx;								//삭제파일 번호
	private String delFileName;							//삭제파일
	
	private String width;									//이미지 폭
	private String imgFile;								//이미지 파일
	private String delImgFileIdx;						//삭제이미지 번호
	private String delImgFileName;					//삭제이미지 파일

	private String page;					
	private String sortKey;					
	private String sortValue;					
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public int getStp() {
		return stp;
	}
	public void setStp(int stp) {
		this.stp = stp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(String readCnt) {
		this.readCnt = readCnt;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	public String getDelFileIdx() {
		return delFileIdx;
	}
	public void setDelFileIdx(String delFileIdx) {
		this.delFileIdx = delFileIdx;
	}
	public String getDelFileName() {
		return delFileName;
	}
	public void setDelFileName(String delFileName) {
		this.delFileName = delFileName;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getImgFile() {
		return imgFile;
	}
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	public String getDelImgFileIdx() {
		return delImgFileIdx;
	}
	public void setDelImgFileIdx(String delImgFileIdx) {
		this.delImgFileIdx = delImgFileIdx;
	}
	public String getDelImgFileName() {
		return delImgFileName;
	}
	public void setDelImgFileName(String delImgFileName) {
		this.delImgFileName = delImgFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}

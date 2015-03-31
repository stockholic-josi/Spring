package com.taxholic.manage.system.code.dto;


import java.util.Date;

public class AuthCode  implements java.io.Serializable{
	
	private Integer seqNo;					//일련번호
	private String roleCd;					//권한코드
	private String roleNm;					//권한명
	private String userId;					//유저아이디
	private String userNm;					//유저명
	private Integer roleStp;					//순서
	private Date modifyDt;				//수정일
	private String editCd;					//등록,수정,삭제 상태
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public String getRoleCd() {
		return roleCd;
	}
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getRoleNm() {
		return roleNm;
	}
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
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
	public Integer getRoleStp() {
		return roleStp;
	}
	public void setRoleStp(Integer roleStp) {
		this.roleStp = roleStp;
	}
	public Date getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getEditCd() {
		return editCd;
	}
	public void setEditCd(String editCd) {
		this.editCd = editCd;
	}
	
	
}

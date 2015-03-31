package com.taxholic.manage.system.code.dto;

import java.util.List;

public class AuthCodeArr  implements java.io.Serializable{
	
	private List lstRoleCd;					//권한코드
	private List lstRoleNm;					//권한명
	private List lstRoleStp;					//순서
	private List lstEditCd;						//등록 : I,수정 : U 상태
	
	private String userId;
	
	public List getLstRoleCd() {
		return lstRoleCd;
	}
	public void setLstRoleCd(List lstRoleCd) {
		this.lstRoleCd = lstRoleCd;
	}
	public List getLstRoleNm() {
		return lstRoleNm;
	}
	public void setLstRoleNm(List lstRoleNm) {
		this.lstRoleNm = lstRoleNm;
	}
	public List getLstRoleStp() {
		return lstRoleStp;
	}
	public void setLstRoleStp(List lstRoleStp) {
		this.lstRoleStp = lstRoleStp;
	}
	public List getLstEditCd() {
		return lstEditCd;
	}
	public void setLstEditCd(List lstEditCd) {
		this.lstEditCd = lstEditCd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
		
}

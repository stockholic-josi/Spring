package com.taxholic.core.authority;

import java.util.Date;
import java.util.List;

public class AuthDto implements java.io.Serializable{
	
	private static final long serialVersionUID = -4294694251587167808L;
	
	private String userId;
	private String userNm;
	private String password;
	private String email;
	private String role;
	private String receive;
	private String useYn;
	private String sessionId;
	private Date loginDate;
	
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	private List roleList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	public String getUserNm() {
		return userNm;
	}
	public void setUserName(String userNm) {
		this.userNm = userNm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List getRoleList() {
		return roleList;
	}
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getReceive() {
		return receive;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUseYn() {
		return useYn;
	}

}

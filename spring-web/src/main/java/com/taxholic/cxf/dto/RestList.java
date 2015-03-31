package com.taxholic.cxf.dto;

import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RoleList")
public class RestList {
	
	private List<Rest> roleList;

	public List<Rest> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Rest> roleList) {
		this.roleList = roleList;
	}

	

	
}

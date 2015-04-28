package com.taxholic;

import com.taxholic.core.annotation.Encrypt;


public class EncryptTest  {
	
	private String name;
	@Encrypt
	private String passwd;
	private String num;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	
}

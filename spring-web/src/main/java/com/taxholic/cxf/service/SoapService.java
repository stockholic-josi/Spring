package com.taxholic.cxf.service;

import javax.jws.WebService;

import com.taxholic.cxf.dto.Soap;
import com.taxholic.cxf.dto.SoapList;

@WebService
public interface SoapService {
	
	public SoapList getRoleList(Soap dto);
	
	
}
package com.taxholic.cxf.service;

import java.util.List;



import javax.annotation.Resource;
import javax.jws.WebService;


import com.taxholic.core.dao.CommonDao;
import com.taxholic.cxf.dto.Soap;
import com.taxholic.cxf.dto.SoapList;

@WebService(endpointInterface = "com.taxholic.cxf.service.SoapService", serviceName = "soapService")
public class SoapServiceImpl implements SoapService {
	
	@Resource
	private CommonDao commonDao;
	
	public SoapList getRoleList(Soap dto) {
		
		List<Soap> roleList = this.commonDao.getList("front.Cxf.getSoapRoleList");
		SoapList soapList = new SoapList();
		soapList.setRoleList(roleList);
		
		return soapList;
	}
}

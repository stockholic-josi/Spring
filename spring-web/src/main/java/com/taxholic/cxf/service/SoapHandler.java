package com.taxholic.cxf.service;


import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taxholic.cxf.dto.Soap;
import com.taxholic.cxf.dto.SoapList;

public class SoapHandler {
	
	/**
	 * 직접연결
	 * @return
	 */
	public static List<Soap> getRoleList(String userId, String password){
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	    factory.setServiceClass(SoapService.class);
	    factory.setAddress("http://localhost:8888/cxf-service/soapService?wsdl");
	    SoapService soapService = (SoapService) factory.create();
	    
	    Soap dto = new Soap();
	    dto.setUserId(userId);
	    dto.setPassword(password);
	    SoapList soapList = soapService.getRoleList(dto);
		
		return soapList.getRoleList();
	}
	
	/**
	 * 스프링 컨텍스트 연결
	 * @return
	 */
	public static List<Soap> getContextRoleList(String userId, String password){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/taxholic/cxf/application-cxf-client.xml");
		SoapService soapService = (SoapService) context.getBean("client");
		
		Soap dto = new Soap();
	    dto.setUserId(userId);
	    dto.setPassword(password);
	    
	    SoapList soapList = soapService.getRoleList(dto);
		
		return soapList.getRoleList();
	}
	
}

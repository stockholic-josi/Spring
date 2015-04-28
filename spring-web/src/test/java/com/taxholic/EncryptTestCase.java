package com.taxholic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EncryptTestCase extends BaseTestCase {
	
	@Autowired
	private TestService testServce;
	
	EncryptTest et  = new EncryptTest();
	
	@Test
	public void encryptTest() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		et.setName("메렁");;
		et.setPasswd("1234");
		et.setNum("1");
		
		testServce.insert(et);
		
		logger.debug("---> password : " + testServce.select().getPasswd());
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
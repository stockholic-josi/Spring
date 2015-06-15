package com.taxholic;



import java.util.Map;




import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;

import com.taxholic.core.util.EnumUtils;
import com.taxholic.enums.TestEnum;

public class EnumTest extends BaseTestCase {
	
	@Autowired
	private MessageSourceAccessor message;
	

	@Test
	public void enumTest(){
		
		System.out.println("--->" + TestEnum.ABC.getValue());
		System.out.println("--->" + TestEnum.ABC.getLabel());
		
		 Map<String, String> map = EnumUtils.getOptions(TestEnum.values());
		  System.out.println("--->" + map.get("ABC"));
		 
		 System.out.println("getValueByLabel --->" +EnumUtils.getValueByLabel(TestEnum.class, "Test1"));
		 System.out.println("getLabelByValue --->" +EnumUtils.getLabelByValue(TestEnum.class, "DEF"));
		 System.out.println("getLabelByValue --->" + message.getMessage(EnumUtils.getLabelByValue(TestEnum.class, "MSG")));
		 
		
	}
	
	
}

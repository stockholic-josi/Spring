/*
 * @(#)AnnotationConfiguration.java $version 2014. 10. 31.
 *
 * Copyright 2014 NHN Ent. All rights Reserved.
 * NHN Ent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.taxholic.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.taxholic.TestService;

@Configuration
//@ComponentScan(basePackages = {"com.taxholic.configuration.beans"},  excludeFilters = {@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
@ComponentScan(
	    basePackages = {"com.taxholic.configuration.beans","com.taxholic.core.dao"}, 
	   // useDefaultFilters = false,
	    includeFilters = {
	      //  @ComponentScan.Filter(type = FilterType.ANNOTATION,   value = org.springframework.stereotype.Repository.class),
	        @ComponentScan.Filter(type = FilterType.ANNOTATION,   value = org.springframework.stereotype.Repository.class)
	    })
public class AnnotationConfiguration {
	
	@Bean
	public TestService testService() {
		TestService testService = new TestService();
		return testService;
	}
	
	
}

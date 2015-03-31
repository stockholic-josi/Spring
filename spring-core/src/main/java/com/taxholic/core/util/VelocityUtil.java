package com.taxholic.core.util;

import org.apache.velocity.app.VelocityEngine;


public class VelocityUtil {
	
	public static VelocityEngine initVelocity(String path) {
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path);
		ve.setProperty(ve.INPUT_ENCODING, "UTF-8");
		ve.init();
		
//		VelocityContext context = new VelocityContext();
//		context.put("date", new DateTool()); 
//		context.put("number", new NumberTool()); 
//		context.put("math", new MathTool());
		
		return  ve;
		
	} 	

    
}


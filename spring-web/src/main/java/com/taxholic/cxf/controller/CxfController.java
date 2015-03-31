package com.taxholic.cxf.controller;



import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;


import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.taxholic.cxf.dto.Soap;
import com.taxholic.cxf.service.SoapHandler;


@Controller("com.taxholic.cxf.controller.CxfController")
@RequestMapping("/front/cxf/*.do")
public class CxfController  {
	
	
	@RequestMapping
	public void getSoapRoleList( HttpServletResponse response) throws IOException {
		
		
	    List<Soap> list = SoapHandler.getRoleList("merong","whsskdjfudnjf@#$%#@");
	    
		StringBuffer sb = new StringBuffer(); 
		sb.append("<pre>");
		for( Soap role : list ){
			sb.append("<p>	" + role.getRoleCd() + "	:	" + role.getRoleNm() + "</p>");
		}
		sb.append("</pre>");
	      
	    response.setContentType("text/html");
	    response.getWriter().print( sb );
	
	}
	
	@RequestMapping
	public void getRestXmlRoleList( HttpServletResponse response) throws IOException {
		
		PostMethod mPost = null;
		Element element = null;
		String output = "";
		
		try{
			
	        String url = "http://localhost:8888/cxf-service/restService/xml/roleList";
	        
	        HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
	        HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
	        httpConnMgrParams.setConnectionTimeout(5000);
	        httpConnMgrParams.setSoTimeout(20000);
	        connMgr.setParams(httpConnMgrParams);
	
	        HttpClient client = new HttpClient();
	        mPost = new PostMethod(url);
	        mPost.addParameter("userId", "메렁");
	        mPost.addParameter("password", "password");
	        mPost.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
	        client.executeMethod( mPost );
	        
	        output = mPost.getResponseBodyAsString();
	        
	        SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(mPost.getResponseBodyAsStream());
			element = doc.getRootElement();
			
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			if(mPost !=null) mPost.releaseConnection();
		}
		
		StringBuffer sb = new StringBuffer();
		Iterator iter = element.getChildren("roleList").iterator();
		while (iter.hasNext()) {
			Object child = iter.next();
			Element ele = (Element) child;
			sb.append( ele.getChildText("roleCd") + "	:	" +  ele.getChildText("roleNm") + "\n" );
		}
		
	        
		response.setContentType("text/html");
	    response.getWriter().print("<div style='font-size:10pt;width:800px'>" + output.replaceAll("<","&lt;").replaceAll(">","&gt;") +  "</div>\n\n<pre>"+ sb  + "</pre>");
		
		
	
	}
	
	@RequestMapping
	@ResponseBody
	public String getRestRoleJsonList( HttpServletResponse response) throws IOException {
		
		PostMethod mPost = null;
		String output = "";
		
		try{
			
	        String url = "http://localhost:8888/cxf-service/restService/json/roleList";
	        
	        HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
	        HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
	        httpConnMgrParams.setConnectionTimeout(5000);
	        httpConnMgrParams.setSoTimeout(20000);
	        connMgr.setParams(httpConnMgrParams);
	
	        HttpClient client = new HttpClient();
	        mPost = new PostMethod(url);
	        mPost.addParameter("userId", "메렁");
	        mPost.addParameter("password", "password");
	        mPost.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
	        client.executeMethod( mPost );
	        
	        System.out.println("-------------->" + mPost.getResponseBodyAsString());
	        
	        output = mPost.getResponseBodyAsString();
	        
	        
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			if(mPost !=null) mPost.releaseConnection();
		}
		
		
		return output;
	
	}
	
}
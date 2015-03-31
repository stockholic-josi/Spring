package com.taxholic.manage.system.scheduler.service;

import java.io.File;



import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.HashMap;


import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import com.taxholic.core.util.DateParser;
import com.taxholic.core.util.StringUtil;
import com.taxholic.core.util.XMLParser;
import com.taxholic.manage.system.code.dto.Kospi;
import com.taxholic.manage.system.code.dto.NewsRss;

public class HttpUtil {
	
	public  Log log = LogFactory.getLog(getClass());
	
	public HttpClient makeHttpClient(int connTimeout, int soTimeout) {
		HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
		httpConnMgrParams.setConnectionTimeout(connTimeout);
		httpConnMgrParams.setSoTimeout(soTimeout);
		connMgr.setParams(httpConnMgrParams);
		
		return new HttpClient(connMgr);
	}
	
	/**
	 * Stock 차트 가져오기
	 * @throws IOException 
	 */
	public String httpGetStockChart(HashMap hm){
		
		String msg = "";
		
		HttpClient hc = makeHttpClient(15000, 20000);
		
		//hc.getHostConfiguration().setProxy("10.235.236.21", 8080);		//Proxy
		
		hc.getParams().setParameter("http.useragent", "HttpClient");
		GetMethod getMethod = new GetMethod(hm.get("link").toString());
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		
		FileOutputStream fos = null;
		InputStream stream = null;
		try {
			int statusCode = hc.executeMethod(getMethod);
			
			if(statusCode != HttpStatus.SC_OK) {
				msg = "Error : Unable to fetch default page, status code :" + statusCode;
			}else{
				stream = getMethod.getResponseBodyAsStream();
				fos = new FileOutputStream(new File(hm.get("fileNm").toString()));

				int r;
	            byte[] buffer = new byte[1024];
	            while ((r = stream.read(buffer)) != -1) {
	                fos.write(buffer, 0, r);
	            }
			}			
			msg = hm.get("link").toString();
		} catch(Exception e) {
			log.error(e);
			msg = "Error : " + e.toString();
		 } finally {
	  		if( getMethod != null ) getMethod.releaseConnection();
	  		try {fos.close();} catch (IOException e) {}
	  		try {stream.close();} catch (IOException e) {}
	    }
		
		return msg;
		
	}
	
	
	/**
	 * 주가시세 가져오기
	 * @param link
	 * @param flag
	 * @return
	 */
	public List httpGetStockPrice(String link, String flag){
		
		HttpClient hc = makeHttpClient(5000, 20000);
		hc.getParams().setParameter("http.useragent", "HttpClient");
	    hc.getParams().setParameter("http.protocol.content-charset", "euc-kr");
		GetMethod getMethod = new GetMethod(link);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		Charset ch = Charset.forName("euc-kr");
		
		List list = new ArrayList();
		InputStream stream = null;
		BufferedReader reader = null;
		try {
			int statusCode = hc.executeMethod(getMethod);
			
			if(statusCode != HttpStatus.SC_OK) {
				log.error("Unable to fetch default page, status code :" + statusCode);
			}else{
				
				String line;
				stream = getMethod.getResponseBodyAsStream();
				reader = new BufferedReader(new InputStreamReader(stream,ch));
			    StringBuffer sb = new StringBuffer();
			    while ((line = reader.readLine()) != null) {
			        sb.append(line);
			    }
				
				Source source = new Source(sb.toString()); 
				
				int tableCount = 1; 
				List<HashMap>  codeList= new ArrayList();
				List priceList = new ArrayList();
				List fluctuateList = new ArrayList();
				List rateList = new ArrayList();
				
				//-------------------------------------- 지수
				int div = 0;
				for (Iterator i = source.getAllElements(HTMLElementName.DIV).iterator(); i.hasNext(); div++) { 
					Segment segment = (Segment) i.next(); 
					if( StringUtil.chkNull(source.getAllElements(HTMLElementName.DIV).get(div).getAttributeValue("id")).equals("moduleIndex") ){
//						System.out.println("--------->" + segment.getAllElements(HTMLElementName.DT).get(0).getTextExtractor().toString());
//						System.out.println("--------->" + segment.getAllElements(HTMLElementName.DD).get(0).getTextExtractor().toString().replace("▲","").replace("▼", "-"));
//						System.out.println("--------->" + segment.getAllElements(HTMLElementName.DD).get(1).getTextExtractor().toString());
						
						HashMap hm = new HashMap();
						if(flag.equals("01")){
							hm.put("codeNm", "KOSPI");
							hm.put("codeCd", "000000");
						}else{
							hm.put("codeNm", "KOSDAQ");
							hm.put("codeCd", "000001");
						}
						codeList.add(hm);
						String price = segment.getAllElements(HTMLElementName.DT).get(0).getTextExtractor().toString() ;
						price = price.replaceAll(",", "");
						
						priceList.add( price );
						fluctuateList.add( segment.getAllElements(HTMLElementName.DD).get(0).getTextExtractor().toString().replace("▲","").replace("▼", "-") );
						rateList.add( segment.getAllElements(HTMLElementName.DD).get(1).getTextExtractor().toString() );
						
					}
				}
				
				//-------------------------------------- 종목
				for (Iterator i = source.getAllElements(HTMLElementName.TABLE).iterator(); i.hasNext(); tableCount++) { 
//					System.out.println("=============================== TABLE : " +tableCount );
		            Segment segment = (Segment) i.next(); 
		            
		            	//----------------- 코드
		            	int thCount = 1;
		                for (Iterator j = segment.getAllElements(HTMLElementName.TH).iterator(); j.hasNext(); thCount++) { 
		                    Segment childsegment = (Segment) j.next(); 

		                    if(thCount < 5) continue;
		                    
		                    HashMap hm = new HashMap();
		                    String codeNm = childsegment.getAllElements(HTMLElementName.A).get(0).getTextExtractor().toString();
		                    String codeCd = childsegment.getAllElements(HTMLElementName.A).get(0).getAttributeValue("href");
		                    hm.put("codeNm", codeNm);
		                    hm.put("codeCd", codeCd.substring( codeCd.lastIndexOf("=")  + 1));
		                    
//		                    System.out.println("===========>" + code.substring( code.lastIndexOf("=")  + 1) +":"+ codeNm);
		                    codeList.add(hm);
		                } 
		            	
		              //----------------- 가격
		                int tdCount = 1;
		                int valCount = 0;
		                for (Iterator j = segment.getAllElements(HTMLElementName.TD).iterator(); j.hasNext(); tdCount++) { 
		                    Segment childsegment = (Segment) j.next(); 
		                   
		                    valCount = tdCount % 3;
		                    
//		                   if( tdCount % 3 != 1) continue;
//		                   System.out.println("===========>" + tdCount % 3 +" : " + childsegment.getTextExtractor() );
//		                    priceList.add(childsegment.getTextExtractor());
		                    
		                    if(valCount == 1){
		                    	 priceList.add(childsegment.getTextExtractor());
		                    }else if(valCount == 2){
		                    	fluctuateList.add(childsegment.getTextExtractor().toString().replace("▲","").replace("▼", "-"));
		                    }if(valCount == 0){
		                    	rateList.add(childsegment.getTextExtractor());
		                    }
		                    
		                } 
		                
		         } 
				
				for(int i = 0; i < codeList.size(); i++ ){
					Kospi kp = new Kospi();
					
					kp.setCodeCd( codeList.get(i).get("codeCd").toString());
					kp.setCodeNm( codeList.get(i).get("codeNm").toString());
					kp.setPrice( priceList.get(i).toString());
					kp.setFluctuate( fluctuateList.get(i).toString());
					kp.setRate( rateList.get(i).toString());
					kp.setFlag(flag);
					
					list.add(kp);
					
				}
			}	
		} catch(Exception e) {
			log.error(e);
		 } finally {
	  		if( getMethod != null ) getMethod.releaseConnection();
	  		try {stream.close();} catch (IOException e) {};
	  		try {reader.close();} catch (IOException e) {};
	    }
		 
		return list;
		
	}
	
	/**
	 * Rss 뉴스 가져오기
	 * @param link
	 * @param charset
	 * @param seqNo
	 * @param flag
	 */
	public List httpGetRss(HashMap hm) {
		
		Log log = LogFactory.getLog("scheduling.quartz");

		HttpClient hc = makeHttpClient(5000, 20000);
		
//		hc.getHostConfiguration().setProxy("10.235.236.21", 8080);		//Proxy
		
		GetMethod getMethod = new GetMethod(hm.get("link").toString());
		
		List list = new ArrayList();
		InputStream stream = null;
		BufferedReader reader = null;
		try {
			int statusCode = hc.executeMethod(getMethod);
		
			if(statusCode != HttpStatus.SC_OK) {
				log.info(hm.get("link") + " : Unable to fetch default page, status code :" + statusCode);
			}else{
				
				
				Namespace ns = Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/");
				SimpleDateFormat df = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
				
			    SAXBuilder builder = new SAXBuilder();
			    Document doc = builder.build(getMethod.getResponseBodyAsStream());
			    Element element = doc.getRootElement();
				
				 	
				Iterator iter = element.getChild("channel").getChildren("item").iterator();
				
				while (iter.hasNext()) {
					Object child = iter.next();
					Element eleChild = (Element) child;
				    NewsRss rss = new NewsRss();
				    rss.setRefNo( hm.get("seqNo").toString() );
				    rss.setTitle( eleChild.getChildText("title") );
				    rss.setLink( eleChild.getChildText("link") );
				    rss.setDescription( StringUtil.strlen(StringUtil.removeTag(eleChild.getChildText("description")),400,"...") );
				    
				    if(eleChild.getChildText("pubDate") == null){
			    		rss.setPubDate(  df.format ( DateParser.parseDate(eleChild.getChildText("date",ns)) ) );
			    	}else{
			    		rss.setPubDate(  df.format ( DateParser.parseDate(eleChild.getChildText("pubDate")) ) );
			    	}
			    	
				    rss.setFlag( hm.get("flag").toString() );
				    list.add(rss);
					   
				}
			}
			
		} catch(Exception e) {
			log.info("Error : " + hm.get("itemNm") + " : " + e);
		 } finally {
	  		if( getMethod != null ) getMethod.releaseConnection();
	    }  
		
		 return list;
		
	}
	
	
	/**
	 * 기업정보 가져오기
	 * @param link
	 * @param flag
	 * @return
	 */
	public String httpGetCompanyInfo(String link, String codeNm){
		
		HttpClient hc = makeHttpClient(5000, 20000);
		hc.getParams().setParameter("http.useragent", "HttpClient");
	    hc.getParams().setParameter("http.protocol.content-charset", "euc-kr");
		GetMethod getMethod = new GetMethod(link);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		Charset ch = Charset.forName("euc-kr");
		
		String info = null;
		InputStream stream = null;
		BufferedReader reader = null;
		try {
			int statusCode = hc.executeMethod(getMethod);
			
			if(statusCode != HttpStatus.SC_OK) {
				log.error("Unable to fetch default page, status code :" + statusCode);
			}else{
				
				String line;
				stream = getMethod.getResponseBodyAsStream();
				reader = new BufferedReader(new InputStreamReader(stream,ch));
			    StringBuffer sb = new StringBuffer();
			    while ((line = reader.readLine()) != null) {
			        sb.append(line);
			    }
				
				Source source = new Source(sb.toString()); 
				StringBuffer httpVal = new StringBuffer();
				

				int k = 0;
				for (Iterator i = source.getAllElements(HTMLElementName.TD).iterator(); i.hasNext(); k++) { 
					Segment segment = (Segment) i.next(); 
					
					if( "td_comment".equals(source.getAllElements( HTMLElementName.TD).get(k).getAttributeValue("class")) ){

						httpVal.append("출처 : <a href='http://www.fnguide.com' target='_blank'>FnGuide</a> >  <a href='http:/"+ link + "' target='_blank'>" + link + "</a>").append("<BR><BR>");
						httpVal.append( segment.getAllElements( HTMLElementName.TABLE).get(0).getAllElements(HTMLElementName.TD ).get(1).getContent()).append("<BR><BR>");
						httpVal.append( segment.getAllElements( HTMLElementName.TABLE).get(0).getAllElements(HTMLElementName.TD ).get(3).getContent());
						
							info = httpVal.toString();
					}
				}
			}
				
		} catch(Exception e) {
			log.error(e);
		 } finally {
	  		if( getMethod != null ) getMethod.releaseConnection();
	  		try {stream.close();} catch (IOException e) {};
	  		try {reader.close();} catch (IOException e) {};
	    }
		 
		return info;
		
	}
	

}

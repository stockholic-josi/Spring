package com.taxholic.core.util;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;


/**
 * @author J.S Park <br>
<pre>
 예제 URL Parsing<br>
Element element =  XMLParser.getElement("http://rss.allblog.net/TodayBestPosts.xml");

String title = element.getChild("channel").getChildText("title");
out.println(title);

List item = element.getChild("channel").getChildren("item");
Iterator iter = item.iterator();

while (iter.hasNext()) {
	Object child = iter.next();
	Element ele = (Element) child;
   	out.println("item/title : "+ele.getChildText("title") + " ");
   	out.println("item/source : "+ele.getChildText("source"));
}
</pre>

<br><br>

<pre>
예제 File Parsing<br>
&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;itemlist&gt;
	&lt;title style="font:14pt 돋움;font-weight:bold "&gt;File xml Sample&lt;/title&gt;
	&lt;item&gt;
		&lt;itemCode style="font:9pt;color: #FF6600"&gt;1&lt;/itemCode&gt;
		&lt;itemName style="font:9pt;color: #0066CC"&gt;현대증권&lt;/itemName&gt;
	&lt;/item&gt;
	&lt;item&gt;
		&lt;itemCode style="font:9pt;color: #FF6600"&gt;1&lt;/itemCode&gt;
		&lt;itemName style="font:9pt;color: #0066CC"&gt;현대중공업&lt;/itemName&gt;
	&lt;/item&gt;
	&lt;item&gt;
		&lt;itemCode style="font:9pt;color: #FF6600"&gt;1&lt;/itemCode&gt;
		&lt;itemName style="font:9pt;color: #0066CC"&gt;삼성중공업&lt;/itemName&gt;
	&lt;/item&gt;
	&lt;item&gt;
		&lt;itemCode style="font:9pt;color: #FF6600"&gt;1&lt;/itemCode&gt;
		&lt;itemName style="font:9pt;color: #0066CC"&gt;삼성전자&lt;/itemName&gt;
	&lt;/item&gt;
	
	&lt;item01&gt;
		&lt;item02&gt;
			&lt;itemCode style="font:9pt;"&gt;2&lt;/itemCode&gt;
			&lt;itemName style="font:9pt;"&gt;삼성1&lt;/itemName&gt;
		&lt;/item02&gt;
		&lt;item02&gt;
			&lt;itemCode style="font:9pt;"&gt;2&lt;/itemCode&gt;
			&lt;itemName style="font:9pt;"&gt;삼성2&lt;/itemName&gt;
		&lt;/item02&gt;
	&lt;/item01&gt;
&lt;/itemlist&gt;	

<br>	

//=================== File 1 Dept ================== <br>

Element element =  XMLParser.getElement("G:/private/www/stock/word.xml");

out.println("<font style='" + element.getChild("title").getAttributeValue("style") +  "'>" + element.getChildText("title") + "</font>");

List item = element.getChildren("item");
Iterator iter = item.iterator();
while (iter.hasNext()) {
	Object child = iter.next();
	Element ele = (Element) child;
    out.println("<font style='" + ele.getChild("itemCode").getAttributeValue("style") + "'>" + ele.getChildText("itemCode") + "</font>");
    out.println("<font style='" + ele.getChild("itemName").getAttributeValue("style") + "'>" + ele.getChildText("itemName") + "</font>");
}

<br>

//=================== File 2 Dept ================== <br>

Element element =  XMLParser.getElement("G:/private/www/stock/word.xml");
out.println("<font style='" + element.getChild("title").getAttributeValue("style") +  "'>" + element.getChildText("title") + "</font>");

List item = element.getChild("item01").getChildren("item02");
Iterator iter = item.iterator();
while (iter.hasNext()) {
	Object child = iter.next();
	Element ele = (Element) child;
    out.println("<font style='" + ele.getChild("itemCode").getAttributeValue("style") + "'>" + ele.getChildText("itemCode") + "</font>");
    out.println("<font style='" + ele.getChild("itemName").getAttributeValue("style") + "'>" + ele.getChildText("itemName") + "</font>");
} 
</pre>
*
*/
public class XMLParser {

	/** 
	 * URL / 파일 을 읽어 파싱한다.<br>
	 * @param arg URL 경로 OR 파일경로
	 * @return element
	 */
	public static Element getElement(String str){
		
		Element  element= null;
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(str);
			element = doc.getRootElement();   				//문서의 root를 읽어온다.
		}catch (Exception e){
			e.getStackTrace();
		}
		
		return element;
	}
	
	/**
	 * String XML 을 읽어 파싱한다.
	 * @param arg
	 * @return element
	 */
	public static Element getStringElement(String str, String charset){
		
		Element  element= null;
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new ByteArrayInputStream(str.getBytes( charset )));		//charset 지정
			element = doc.getRootElement();
		}catch (Exception e){
			e.getStackTrace();
		}
		
		return element;
	}
		
	
}
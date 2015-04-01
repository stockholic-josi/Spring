<%@ page import="org.apache.commons.httpclient.HttpClient"%>
<%@ page import="org.apache.commons.httpclient.HttpConnectionManager"%>
<%@ page import=" org.apache.commons.httpclient.SimpleHttpConnectionManager"%>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod"%>
<%@ page import="org.apache.commons.httpclient.params.HttpConnectionManagerParams"%>
<%@ page import="org.jdom.Document"%>
<%@ page import="org.jdom.Element"%>
<%@ page import="org.jdom.input.SAXBuilder"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.taxholic.core.util.DateParser"%>
<%@ page import="net.htmlparser.jericho.*"%>




<%

GetMethod mGet = null;
String output = ""; 

try{
	
  String url = "http://stock.koscom.co.kr/kse_sise/kse_totalsise.html";
    
    HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
    HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
    httpConnMgrParams.setConnectionTimeout(5000);
    httpConnMgrParams.setSoTimeout(20000);
    connMgr.setParams(httpConnMgrParams);

    
    
    HttpClient client = new HttpClient();
    client.getParams().setParameter("http.protocol.content-charset", "euc-kr");
    
    mGet = new GetMethod(url);
    client.executeMethod( mGet );
    
    
    Source source = new Source(mGet.getResponseBodyAsString()); 
    
    //out.println( source.getAllElements(HTMLElementName.TABLE).size() + "<br>");
   // out.println( source.getAllElements(HTMLElementName.TABLE).get(0).getAttributeValue("class").toString() + "<br>");
    
    List tb = source.getAllElements(HTMLElementName.TABLE);
    
    for (int i = 0; i < tb.size(); i++) { 
    	 Segment segment = (Segment)tb.get(i);
    	
    	 List th = segment.getAllElements();
    	 for(int j = 0; j < th.size(); j++ ){
    		 Segment childsegment = (Segment)th.get(j); 

    		 out.println(childsegment.getTextExtractor().toString() + "<br>");
    	}
    	
    	 out.println("--------------------------------<br>");
    }

    
/*    
	Iterator iter =element.getChild("channel").getChildren("item").iterator();
	while (iter.hasNext()) {
		Object child = iter.next();
		Element ele = (Element) child;
	    sb.append(ele.getChildText("pubDate") + " > " + df.format(DateParser.parseDate(ele.getChildText("pubDate"))) + "<br>");
	}
*/	
//	out.println(mGet.getResponseBodyAsStream());
//	out.println(mGet.getResponseBodyAsString());
    
} catch (Exception e) {
    e.printStackTrace();
} finally {
	if(mGet !=null) mGet.releaseConnection();
}


%>
 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.taxholic.core.util.SysUtil" %>
<%@ page import="org.apache.commons.httpclient.*" %>
<%@ page import="org.apache.commons.httpclient.methods.*" %>
<%@ page import="org.apache.commons.httpclient.cookie.*" %>
<%@ page import="org.apache.commons.httpclient.params.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.nio.charset.Charset" %>

<%

HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
httpConnMgrParams.setConnectionTimeout(5000);
httpConnMgrParams.setSoTimeout(20000);
connMgr.setParams(httpConnMgrParams);
HttpClient client = new HttpClient(connMgr);

//client.getHostConfiguration().setProxy("10.235.236.21", 8080);		//Proxy

String loginUrl = "http://www.anmajoy.com/bbs/login_check.php";     										//로그인 폼이 존재하는 메인 페이지
String fetchUrl = "http://www.anmajoy.com/bbs/board.php?bo_table=oepilogue&sca=&sfl=wr_subject%7C%7Cwr_content&stx=%EC%A0%9C%EB%AA%A8+%EC%99%81%EC%8B%B1&sop=or";		 //fetch 할 URL

String inputUserName = "mb_id";         									//ID FORM name
String inputPassword = "mb_password";   								//PW FORM	name
String userName = "parkjjss ";  												//ID
String password = "1528";  													//PW


PostMethod postMethod = new PostMethod(loginUrl);
postMethod.addParameter(inputUserName, userName);
postMethod.addParameter(inputPassword, password);
postMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

int loginStatus = client.executeMethod(postMethod);

GetMethod getMethod = new GetMethod(fetchUrl);


InputStream stream = null;
BufferedReader reader = null;

Charset ch = Charset.forName("utf-8");

try {
	
	if( loginStatus == 200 ){
		
		int fetchStatus = client.executeMethod(getMethod);
		
		if( fetchStatus == 200 ){
    
			/*
		   	String line;
			stream = getMethod.getResponseBodyAsStream();
		    reader = new BufferedReader(new InputStreamReader(stream,ch));
		    StringBuffer sb = new StringBuffer();
		    
		  	 while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		  	 */
			out.print(getMethod.getResponseBodyAsString());
		}

	}
	
    
} catch (Exception e) {
    e.printStackTrace();
} finally {
	if( getMethod != null ) getMethod.releaseConnection();
	if( postMethod != null ) postMethod.releaseConnection();
	try {if(stream != null) stream.close();} catch (IOException e) {};
	try {if(reader != null) reader.close();} catch (IOException e) {};
}

%>
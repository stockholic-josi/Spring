 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.taxholic.core.util.StringUtil" %>

<%
boolean result = true;
String msg = "";

String fromMail = "merong@merong.net";
String fromName = "메렁";

String msgSubj = StringUtil.chkNull(request.getParameter("msgSubj"));
String msgText  = StringUtil.chkNull(request.getParameter("msgText"));
String attachName  = StringUtil.chkNull(request.getParameter("attachName"));
int flag = (request.getParameter("html") ==  null) ? 0 : 1;

SendMail sm = new SendMail();
if(attachName.equals("")){
	result = sm.sendText("parkjjss@gmail.com", "박종식", fromMail, fromName, msgSubj, msgText, flag);
}else{
	java.io.InputStream attachStream =FileUploadUtil.uploadFileStream( dto.getFile() );
	result = sm.sendAttach("parkjjss@gmail.com", "박종식", fromMail, fromName, msgSubj, msgText, attachName,attachStream,flag);
}

if(result == true){
	System.out.println("성공");
}else{
	System.out.println("실패");
}
%>
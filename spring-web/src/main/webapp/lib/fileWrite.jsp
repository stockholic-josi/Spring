<%@page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.taxholic.core.util.StringUtil"%>
<%@ page import="com.taxholic.core.util.FileUploadUtil"%>
<%@ page import="com.taxholic.core.util.SysUtil"%>

<%

String docBase = "/";
String fileNm = StringUtil.chkNull(request.getParameter("fileNm"));
String content =request.getParameter("content");
String uploadDir = config.getServletContext().getRealPath(docBase + fileNm);


File dir = new File(uploadDir);
try {
	if(!dir.getParentFile().exists()) dir.getParentFile().mkdirs();
} catch (Exception e) {
    e.printStackTrace();
}

//System.out.println(fileNm);
//System.out.println(content);

//백업파일
FileUploadUtil.fileCopy(uploadDir, uploadDir + ".bak");

//파일생성
SysUtil.setFileWrite(content,uploadDir,"UTF-8");

%>


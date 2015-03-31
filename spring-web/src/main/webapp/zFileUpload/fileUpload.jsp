<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File,java.io.IOException,java.io.PrintWriter"%>
<%@ page import="java.util.Iterator,java.util.List" %>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%!
public String chkNull(String str){
	if(str == null){
		return "";
	}
	return str.trim();
}
%>
<%

String uploadPath = chkNull(request.getParameter("uploadPath"));
File dir = new File(uploadPath);

try {
	if(!dir.exists())	dir.mkdirs();
} catch (Exception e) {
    e.printStackTrace();
}

//form type이 multipart/form-data 면 true 그렇지 않으면 false를 반환
boolean isMultipart = ServletFileUpload.isMultipartContent(request);

if (isMultipart) {

	try{

		// threshold  값 설정(한번에 메모리에 저장할 사이즈 설정)
		int maxMemorySize = 1024 * 512;
	
		//업로드 최대 사이즈 설정 (100M)
		long maxRequestSize = 1024 * 1024 * 700;   
	
		File fileDirectory = new File(uploadPath);
	
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemorySize);
		factory.setRepository(fileDirectory); 
	
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxRequestSize);				// 업로드 디렉토리 설정
		upload.setHeaderEncoding("UTF-8");				// 인코딩 설정
		
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		
		while (iter.hasNext()) {
		
			FileItem item = (FileItem) iter.next();
	
			if(!item.isFormField()){
			
				String tmpFile = Long.toString(System.currentTimeMillis());
	
				File uploadedFile = new File(uploadPath + File.separator + item.getName());
				item.write(uploadedFile);
				item.delete();
				
				
			}
		}
		
	}catch(Exception e){
		e.getStackTrace();
	}
		
}
%>

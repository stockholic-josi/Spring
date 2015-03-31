package com.taxholic.core.servlet;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.taxholic.core.util.StringUtil;

public class FileUpload extends HttpServlet {

	public void doPost (HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
		
		String path = StringUtil.chkNull(request.getParameter("path"));
		if(path.equals("")) return;
		
		String uploadDir = getServletContext().getRealPath(path);
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		PrintWriter out = response.getWriter();
		
		if (isMultipart) {

			try{

				// threshold  값 설정(한번에 메모리에 저장할 사이즈 설정)
				int maxMemorySize = 1024 * 512;
			
				//업로드 최대 사이즈 설정 (100M)
				long maxRequestSize = 1024 * 1024 * 700;   
			
				File fileDirectory = new File(uploadDir);
			
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemorySize);
				factory.setRepository(fileDirectory);
			
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxRequestSize);				// 업로드 디렉토리 설정
				upload.setHeaderEncoding("UTF-8");				// 인코딩 설정
				
				
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				
				String fileName = "";
				String originalFileName = "";
				String fileExt = "";
				long fileSize = 0;
				
				
				while (iter.hasNext()) {
					
					FileItem item = (FileItem) iter.next();
			
					if(!item.isFormField()){
					
						String tmpFile = Long.toString(System.currentTimeMillis());
					//	String fieldName = item.getFieldName();
						fileName = item.getName();
						originalFileName = fileName;
					//	String contentType = item.getContentType();
						fileSize = item.getSize();
			
						fileExt = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()).toLowerCase();
						if(fileExt.equals("jsp") || fileExt.equals("php")) fileExt = "txt";
						fileName = tmpFile + "." + fileExt;
						
						//System.out.println("File.separator : " + File.separator);
						//System.out.println("uploadDir : " + uploadDir);
						//System.out.println("tmpFile : " + fileName);
						//System.out.println("originalFileName : " + originalFileName);
						//System.out.println("fileExt : " + fileExt);
						
						File uploadedFile = new File(uploadDir + "/" + fileName);
						item.write(uploadedFile);
						
						out.print("{" + "\"fileName\":\"" + fileName + "\",\"fileRealName\":\"" + originalFileName + "\",\"fileExt\":\"" + fileExt + "\",\"fileSize\":\"" + fileSize + "\"}");
						out.close();
						
					}
				}
				
			}catch(Exception e){
				e.getStackTrace();
			}
		  
		  }
	}
}
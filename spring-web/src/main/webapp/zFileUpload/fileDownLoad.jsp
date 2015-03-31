<%@ page contentType="text/html; charset=UTF-8" %><%@
page import="java.io.*"%><%!
public String chkNull(String str){
	if(str == null){
		return "";
	}
	return str.trim();
}
%><%

String filePath = chkNull(request.getParameter("filePath")).replaceAll("\\.\\.","");
String fileNm = chkNull(request.getParameter("fileNm"));

if( "".equals(filePath) || "".equals(fileNm) ) return;


try{ 
	File file =  new File(filePath,fileNm);
	String mime = application.getMimeType(file.toString());
	
	 if (mime == null) mime = "application/octet-stream";
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename="+ java.net.URLEncoder.encode(fileNm, "UTF-8") + ";");
	    response.setHeader("Content-Length", "" + file.length() );
    
    InputStream in = new FileInputStream(file);
    OutputStream os =  response.getOutputStream();        
    
    BufferedInputStream bin = null;
    BufferedOutputStream bos = null;
    
    try {
        bin = new BufferedInputStream( in );
        bos = new BufferedOutputStream( os );
    
        byte[] buf = new byte[2048]; //buffer size 2K.
        int read = 0;
        while ((read = bin.read(buf)) != -1) {
            bos.write(buf,0,read);
        }
    } finally {
        bos.close();
        bin.close();
    }        

}catch(Exception e){}
%>
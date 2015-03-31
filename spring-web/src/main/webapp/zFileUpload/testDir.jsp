 <%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%!
public String chkNull(String str){
	if(str == null){
		return "";
	}
	return str.trim();
}

public String getFileSize(Long file){	

	String size = "0";

	if(file < 1024){
	//	size = file.toString();
		size = file + " B";
	}else if(file >= 1024 && file < 1024 * 1024){
		size = String.format("%.2f", (double)file / 1024 ) + " KB";
		
	}else{
		size = String.format("%.2f", (double)file / 1024 ) + " MB";
	}

	return size;
}
%>
<%
String subPath = chkNull(request.getParameter("subPath"));

SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
ArrayList dirList = new ArrayList();
ArrayList fileList = new ArrayList();

String filePath = application.getRealPath("/"); 
File file = new File(filePath + subPath );
File[] files = file.listFiles();

if(files == null) return;

/*
Arrays.sort(files, new Comparator<File>(){
    public int compare(File f1, File f2) {
        return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
    } 
});
*/
    
 for (int i = 0; i < files.length; i++) {
     if(files[i].isDirectory()){
    	 dirList.add(files[i]);
     }else{
    	 fileList.add(files[i]);
     }
 }
 
//최신파일순 쇼팅
Collections.sort(fileList, new Comparator<File>(){
  public int compare(File f1, File f2) {
      return Long.valueOf(  f2.lastModified()).compareTo(f1.lastModified()  );
  } 
});
      
%>

<!DOCTYPE HTML>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>

* {
	font-family: 맑은 고딕,굴림,돋움, Dotum, Gulim, Arial, sans-serif, Verdana, Helvetica, geneva;
	font-size:9pt;
}
</style>

<script> 
	var subPath = "<%=subPath%>";
	function goSub(dir){
		var sub = (subPath=="")?"":"/"
		location.href = "./test.jsp?subPath=" + subPath + sub + dir;
	}
	
	function upStep(){
		location.href = "./test.jsp?subPath=" + subPath.substring(0,subPath.lastIndexOf("/"));
	}
	
	function upNav(dir){
		location.href  = "test.jsp?subPath=" + dir;
	}
</script>

<body>
<div style="float:left">
	Path :<a href="test.jsp"><%=filePath.replaceAll("\\\\","/") %></a> 
	
</div>
<%
String[] arrSub = subPath.split("/");
String subDir = "";

for(int i = 0; i < arrSub.length; i++ ){
	subDir += ((i ==0)?"" : "/") + arrSub[i];
%>
 <div style="float:left"><a href="javascript:upNav('<%=subDir %>')"><%=arrSub[i] %></a><%=subPath.equals("")?"":"/" %></div>
<%} %>


  <table  cellpadding="4" cellspacing="0" border="0"  style="clear: both">
  <%if(!"".equals(subPath)){ %>
   <tr>
  	<td colspan="3"><b><a href="javascript:upStep();">..</a></b></td>
  </tr>
  <%} %>
  
  <%
  for( int i = 0; i < dirList.size(); i++ ){
	  File dirFile = (File)dirList.get(i);
  %>
  <tr>
  	<td width="200"><b><a href="javascript:goSub('<%=dirFile.getName() %>');"><%=dirFile.getName() %></a></b></td>
  	<td width="80" align="right">DIR</td>
  	<td width="130" align="center"><%= sdf.format( new java.sql.Timestamp( dirFile.lastModified() ) ) %></td>
  </tr>
  <%} %>
  
  <%
  for( int i = 0; i < fileList.size(); i++ ){
	  File nFile = (File)fileList.get(i);
  %>
  <tr>
  	<td width="200"><%=nFile.getName() %></td>
  	<td width="80" align="right"><%=getFileSize( nFile.length() )%></td>
  	<td width="130" align="center"><%= sdf.format( new java.sql.Timestamp( nFile.lastModified() ) ) %></td>
  </tr>
  <%} %>
  
 </table>
 
 </body>
 </html>

 
 <%@ page contentType="text/html; charset=UTF-8" %>
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
String rootPath = chkNull(request.getParameter("rootPath"));
String subPath = chkNull(request.getParameter("subPath"));

SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
ArrayList dirList = new ArrayList();
ArrayList fileList = new ArrayList();

String filePath = (!"".equals(rootPath)) ? rootPath : application.getRealPath("/"); 
File file = new File(filePath + subPath );
File[] files = file.listFiles();

if(files == null) {
	out.println("<font color='#FF6600'>목록 가져오기 실패</font>");
	return;
}

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
/*
Collections.sort(fileList, new Comparator<File>(){
  public int compare(File f1, File f2) {
      return Long.valueOf(  f2.lastModified()).compareTo(f1.lastModified()  );
  } 
});
*/
      
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zFileUpload :::</title>

<script type="text/javascript" src="./js/jquery-1.4.3.min.js"></script>
<link rel="stylesheet" href="./css/progressbar.css" type="text/css" />

<script>

var rootPath = "<%=rootPath%>";
var subPath = "<%=subPath%>";

function goSub(dir){
	var sub = (subPath=="")?"":"/";
	subPath = (dir == "") ? "" : subPath + sub + dir;
	getFileList();
}

function upStep(){
	subPath = subPath.substring(0,subPath.lastIndexOf("/"));
	getFileList();
}


function upNav(dir){
	subPath= dir;
	getFileList();
}

function getFileList(){
	location.href = "fileSingleList.jsp?rootPath=" + rootPath + "&subPath=" + subPath;
}

function downLoad(fileNm){
	location.href = "fileDownLoad.jsp?filePath=" + $("#filePath").val() + "&fileNm=" + fileNm;		
}


</script>
	
	
</head>


<body>

 <table  cellpadding="5" cellspacing="0" border="0" style="border:1px solid #8E8E8E;background-color:#EEEEEE;width:420px">
 <tr><td>
<ul style="list-style:none; padding:0; margin:0;">
	<li style="float: left">PATH : <a href="javascript:goSub('')"><%=filePath.replaceAll("\\\\","/") %></a></li>
	<%
	String[] arrSub = subPath.split("/");
	String subDir = "";
	
	for(int i = 0; i < arrSub.length; i++ ){
		subDir += ((i ==0)?"" : "/") + arrSub[i];
	%>
	 <li style="float: left"><a href="javascript:upNav('<%=subDir %>')"><%=arrSub[i] %></a><%=subPath.equals("")?"":"/" %></li>
	<%} %>
</ul>
</td></tr>
</table>

  <input type="hidden" id="filePath" value="<%=filePath.replaceAll("\\\\","/") + subDir%>">

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
  	<td width="200">
  		<div style="font-weight:bold; width:200px;text-overflow: ellipsis;overflow:hidden;white-space : nowrap;">
  			<a href="javascript:goSub('<%=dirFile.getName() %>');"><%=dirFile.getName() %></a>
  		</div>
  	</td>
  	<td width="80" align="right">DIR</td>
  	<td width="130" align="center"><%= sdf.format( new java.sql.Timestamp( dirFile.lastModified() ) ) %></td>
  </tr>
  <%} %>
  
  <%
  for( int i = 0; i < fileList.size(); i++ ){
	  File nFile = (File)fileList.get(i);
  %>
  <tr>
  	<td width="200"  class="fileNm">
  		<div style="width:200px;text-overflow: ellipsis;overflow:hidden;white-space : nowrap;">
  		<a href="javascript:downLoad('<%=nFile.getName() %>')"><%=nFile.getName() %></a>
  		</div>
  	</td>
  	<td width="80" align="right" ><%=getFileSize( nFile.length() )%></td>
  	<td width="130" align="center"><%= sdf.format( new java.sql.Timestamp( nFile.lastModified() ) ) %></td>
  </tr>
  <%} %>
  
 </table>
 
 </body>
 </html>

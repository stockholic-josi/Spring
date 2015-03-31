<%@page import="java.io.*"%><%@ 
page import="java.util.*"%><%!

public String chkNull(String str){
	if(str == null){
		return "";
	}
	return str.trim();
}

//directory filter
public FileFilter getDirectoryFilter(){
	FileFilter fileFilter = new FileFilter() {
		public boolean accept(File file) {
		  return file.isDirectory();
		}
	};
	return fileFilter;
}
 
 //file filter
 public FilenameFilter getFileFilter(){
  FilenameFilter filter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
          return !name.startsWith(".");
      }
  };
  return filter;
 }
 

%><%

String docBase = config.getServletContext().getRealPath("/");
String subDir = chkNull(request.getParameter("subDir"));
File dir = new File(docBase + subDir);
File[] dirlist = dir.listFiles(getDirectoryFilter());

int k = 0;
StringBuffer sb = new StringBuffer();
sb.append("[");
for (File flist : dirlist){
	if(k == 0){
		sb.append( "{'dir':'" + flist.getName()  + "'}" ); 
	}else{
		sb.append( ",{'dir':'" + flist.getName() + "'}" ); 
	}
	k++;
}
sb.append("]");

out.println(sb.toString());
%>


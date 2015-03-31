<%@ page contentType="text/html; charset=UTF-8" %><%@ 
 page import="java.sql.*" %><%

 String type = request.getParameter("dbType");
 String host = request.getParameter("hostNm");
 String db = request.getParameter("dbNm");
 String id = request.getParameter("id");
 String pw = request.getParameter("pw");
 
Connection conn = null;

try{
	if(type.equals("mysql")){
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "","" + id + "","" + pw + "");
	}else if(type.equals("oracle")){
		Class.forName("oracle.jdbc.OracleDriver");
		conn = DriverManager.getConnection("java:oracle:thin:@" + host + ":" + db + "","" + id + "","" + pw + ""); 
	}
	
	if( conn.isClosed() == false ){
		out.print("success");
	}
	
} catch ( SQLException e ) {
 	e.getStackTrace();
 	out.print(e);
} finally {
	try {
		if(conn != null) conn.close();
	} catch (SQLException e) {
        e.printStackTrace(); 
    }
}%>
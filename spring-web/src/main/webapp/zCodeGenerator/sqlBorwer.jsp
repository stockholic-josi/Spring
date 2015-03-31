 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include  file="zdb.inc.jsp" %>


<%
String type = "mysql";
String host = "127.0.0.1:3306";
String db = "stock";
String id = "shkr";
String pw = "Wkwkdaus";
String tb = "sk_news_stock";

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zCodeGenerator :::</title>

<style>
*{
	font-style: nomal;
	font-size:9pt;
    font-family: 맑은 고딕,굴림,돋움, Dotum, Gulim, Arial, sans-serif, Verdana, Helvetica, geneva;
}
</style>


</head>
<body>


<table cellpadding="2" cellspacing="0" border="1" align="center"  style="border-collapse:collapse; border:1px gray solid;">
<%
Connection conn = null;
PreparedStatement  pstmt = null;
ResultSet rs = null;
try {
	
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT *  \n");
	sql.append("FROM " + tb + " \n");

	if( type.equals("mysql")){
		sql.append("LIMIT 0,10");
	}else if( type.equals("oracle")){
		sql.append("where rownum <= 10");
	}

	conn = getConnect(type, host, db, id, pw);
	pstmt = conn.prepareStatement( sql.toString()  );
	rs = pstmt.executeQuery();
	ResultSetMetaData rsmd = rs.getMetaData();
	int cnt = rsmd.getColumnCount();
	
	out.println("<tr style='background-color:#efefef'>");
	for(int i =1; i < cnt + 1; i ++){
		out.println( "<td>" + rsmd.getColumnName(i)+ "</td>");
	}
	out.println("</tr>");
	
	int k = 1;
 	while(rs.next()) {
 		out.println("<tr>");
 		for(int i = 1; i < cnt + 1; i ++){
 			out.println("<td>" +  rs.getString(i) + "</td>" );
 		}
 		out.println("</tr>");
	}
	
} catch ( Exception e ) {
 	e.getStackTrace();
} finally {
	try {
		if(conn != null) conn.close();
		if(pstmt != null) pstmt.close();
		if(rs != null) rs.close();
	} catch (SQLException e) {
        e.printStackTrace();
    }
}

%>

</table>

</body>
</html>



<%//= "Connection Closeed : " + conn.isClosed() + "<br>" %>



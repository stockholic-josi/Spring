 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%!
public Connection getConnect(String type) {
	Connection conn = null;
	try{
		if(type.equals("mysql")){
			//Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock","shkr","Wkwkdaus");
		}else if(type.equals("oracle")){
			//Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("java:oracle:thin:@165.213.246.186:1527:GCD","EDUS","tjdrhd03"); 
		}
	} catch ( Exception e ) {
	 	e.getStackTrace();
	}
	
	return conn;
}

public String getColumnSql(String type, String db, String tb){
	
	StringBuffer sql = new StringBuffer();
	
	if(type.equals("mysql")){
		sql.append("SELECT \n");
		sql.append("	COLUMN_NAME AS columnName, \n");
		sql.append("	DATA_TYPE AS dataType \n");
		sql.append("FROM information_schema.COLUMNS \n");
		sql.append("WHERE TABLE_SCHEMA = '" + db + "' AND TABLE_NAME = '" + tb + "'");
	}else if(type.equals("oracle")){
		sql.append(" SELECT  \n");
		sql.append("	cols.column_name AS columnName, \n");
		sql.append("	data_type AS dataType \n");
		sql.append("FROM sys.all_updatable_columns auc, \n");
		sql.append("         sys.all_col_comments coms, \n");
		sql.append("         sys.all_tab_columns cols \n");
		sql.append("WHERE   coms.table_name = cols.table_name \n");
		sql.append("	AND coms.column_name = cols.column_name \n");
		sql.append("	AND coms.owner = cols.owner \n");
		sql.append("	AND auc.table_name = cols.table_name \n");
		sql.append("	AND auc.column_name = cols.column_name \n");
		sql.append("	AND auc.owner = cols.owner \n");
		sql.append("	AND cols.table_name = '" + tb.toUpperCase() + "' \n");
		sql.append("ORDER BY column_id \n");
	}
	
	return sql.toString();
}

public List getColumn(String type, String db, String tb) {
	
	Connection conn = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;
	List list = new ArrayList();

	try {

		conn = getConnect(type);
		pstmt = conn.prepareStatement( getColumnSql(type,db,tb)  );
		rs = pstmt.executeQuery();
	
	 	while(rs.next()) {
	 		HashMap hm = new HashMap();
	 		hm.put("columnName",rs.getString("columnName") );
	 		hm.put("dataType",rs.getString("dataType") );
	 		
	 		list.add(hm);
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
	return list;
}
%>

<%

Connection conn = null;
PreparedStatement  pstmt = null;
ResultSet rs = null;

String type = "mysql";
String db = "stock";
String tb = "sk_news_stock";
/*

String type = "oracle";
String db = "EDUS";
String tb = "TB_crs";
*/

StringBuffer sql = new StringBuffer();
sql.append("SELECT *  \n");
sql.append("FROM " + tb + " \n");

if( type.equals("mysql")){
	sql.append("LIMIT 0,10");
}else if( type.equals("oracle")){
	sql.append("where rownum <= 10");
}


List  cList = getColumn(type, db, tb);

int cnt = cList.size();
%>
<table border=1>
<tr>
<%
for(int i =0; i < cnt; i ++){
	out.println( "<td>" + ((HashMap)cList.get(i)).get("columnName") + "</td>");
}
%>
</tr>	
<%

try {

	conn = getConnect(type);
	pstmt = conn.prepareStatement( sql.toString()  );
	rs = pstmt.executeQuery();
	
	ResultSetMetaData rsmd = rs.getMetaData();
	
	System.out.println( rsmd.getColumnCount() );
	System.out.println( rsmd.getColumnName(1) );
	
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


<%//= "Connection Closeed : " + conn.isClosed() + "<br>" %>



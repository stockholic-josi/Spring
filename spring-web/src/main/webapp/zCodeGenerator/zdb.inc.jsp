 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.velocity.app.VelocityEngine" %>
<%@ page import="org.apache.velocity.Template" %>
<%@ page import="org.apache.velocity.VelocityContext" %>
<%@ page import="org.apache.velocity.app.VelocityEngine" %>

<%!
public Connection getConnect(String type, String host, String db, String id, String pw) {
	Connection conn = null;
	try{
		if(type.equals("mysql")){
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "","" + id + "","" + pw + "");
		}else if(type.equals("oracle")){
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("java:oracle:thin:@" + host + ":" + db + "","" + id + "","" + pw + ""); 
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
		sql.append("	DATA_TYPE AS dataType, \n");
		sql.append("	COLUMN_TYPE AS columnType, \n");
		sql.append("	COLUMN_KEY AS columnKey, \n");
		sql.append("	IFNULL(COLUMN_COMMENT,'N/A') AS columnComment \n");
		sql.append("FROM information_schema.COLUMNS \n");
		sql.append("WHERE TABLE_SCHEMA = '" + db + "' AND TABLE_NAME = '" + tb + "'");
	}else if(type.equals("oracle")){
		sql.append(" SELECT  \n");
		sql.append("	lower(cols.column_name) AS columnName, \n");
		sql.append("	data_type AS dataType, \n");
		sql.append("    data_type  || '(' || DECODE (data_type, \n");
		sql.append("         'NUMBER', data_precision + data_scale, \n");
		sql.append("         'CHAR', char_length, \n");
		sql.append("         'VARCHAR', char_length, \n");
		sql.append("         'VARCHAR2', char_length, \n");
		sql.append("         'NCHAR', char_length, \n");
		sql.append("         'NVARCHAR', char_length, \n");
		sql.append("         'NVARCHAR2', char_length, \n");
		sql.append("         data_length)  || ')'  AS  columnType,  \n");
		sql.append("	'' AS columnKey, \n");
		sql.append("	NVL(comments,'N/A') AS columnComment \n");
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

public List getColumn(String type, String host, String db, String tb, String id, String pw) {
	
	Connection conn = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;
	List list = new ArrayList();

	try {
		
		conn = getConnect(type,host, db, id, pw);
		pstmt = conn.prepareStatement( getColumnSql(type,db,tb)  );
		rs = pstmt.executeQuery();
	
	 	while(rs.next()) {
	 		HashMap hm = new HashMap();
	 		hm.put("columnName",rs.getString("columnName") );
	 		hm.put("dataType",rs.getString("dataType") );
	 		hm.put("columnType",rs.getString("columnType") );
	 		hm.put("columnKey",rs.getString("columnKey") );
	 		hm.put("columnComment",rs.getString("columnComment") );
	 		
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

public static String chkNull(String str){
	if(str == null){
		return "";
	}
	return str.trim();
}

public static VelocityEngine initVelocity(String path) {
	
	VelocityEngine ve = new VelocityEngine();
	ve.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path);
	ve.setProperty(ve.INPUT_ENCODING, "UTF-8");
	ve.init();
	
	return  ve;
} 	

public static String getCamelCase(String str) {
	
	StringBuilder sb = new StringBuilder();
	String[]  input = str.toLowerCase().split("_");
	
	for(int i = 0; i< input.length; i++){
		if( i > 0 ){
			 sb.append( input[i].substring(0,1).toUpperCase() );
		}else{
			 sb.append( input[i].substring(0,1) );
		}
		
		 sb.append( input[i].substring(1));
	}
	
	return sb.toString();
}

public static String getType(String str){
	String type = str.toLowerCase();
	return ( type.equals("number") || type.equals("int") ) ? "int" : "String";
}

public static void setFileWrite(String str, String filename, String charset) { 
	FileOutputStream fos = null;
	Writer out = null;
	
	try  {
		fos = new FileOutputStream(filename); 
		out = new OutputStreamWriter(fos, charset); 
		out.write(str); 
		
	} catch (IOException e) {
		
	}finally{
		if(out != null)	try{out.close();} catch (IOException e) {}
		if(fos != null)	try{fos.close();} catch (IOException e) {}
	}
}

public static void mkDir(String filePath){
	
	File dir = new File(filePath);
	try {
		if(!dir.exists()) dir.mkdirs();
	} catch (Exception e) {
	    e.printStackTrace();
	}
}


%>
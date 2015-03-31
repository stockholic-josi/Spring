<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<%@ page import="java.util.*" %>
<%@ page import="com.taxholic.core.util.MD5" %>

<%

String str = request.getParameter("md");
if(str == null || "".equals(str)) str = "MD5";


byte bytes[] = str.getBytes();
MD5 md5 = new MD5();
md5.update(bytes, 0, str.length() );

%>

<!DOCTYPE html>
<HTML >
<HEAD>
<TITLE> Java </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="/css/global.css" type="text/css">
	<link type="text/css" href="/css/redmond/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" />	
	<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
</HEAD>

<script>
$(document).ready(function () {

	$( "button" ).button({
		icons: {
			primary: "ui-icon-document"
		}
	});

});
</script>

</head>


<body>

<form action='./md5.jsp'>
	<input type="text" name="md" value="<%=str %>">
	<button>전송</button>
</form>

<br><br>

MD5 : <%=md5.getHashString() %>
 
 
<br><br>

<%

%>

<%//=isLogin %>

</body>

</html>
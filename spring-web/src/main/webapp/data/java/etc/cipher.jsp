 <%@ page contentType="text/html; charset=UTF-8" %>
 <%@ page import="java.util.*" %>
<%@ page import="com.taxholic.core.util.SecuUtil" %>
<%

String str = request.getParameter("md");
if(str == null || "".equals(str)) str = "love";


SecuUtil sec = new SecuUtil();
String encString = sec.enc(str);
String decString = sec.dec(encString);

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

<form action='./cipher.jsp'>
	<input type="text" name="md" value="<%=str %>">
	<button>전송</button>
</form>

<br><br>

<p>Encode : <%=encString %></p>
<p>Decode : <%=decString %></p>
 




</body>

</html>
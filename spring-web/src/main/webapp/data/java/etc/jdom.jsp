 <%@ page contentType="text/html; charset=UTF-8" %>
 <%@ page import="java.util.*" %>
 <%@ page import="org.jdom.*" %>
<%@ page import="com.taxholic.core.util.XMLParser" %>
<%
StringBuffer sb = new StringBuffer();
StringBuffer sb2 = new StringBuffer();
Element element =  XMLParser.getElement(application.getRealPath("/") + "/data/java/jdom.xml");

List item = element.getChildren("item");
Iterator iter = item.iterator();
while (iter.hasNext()) {
	Object child = iter.next();
	Element ele = (Element) child;
    sb.append("<font style='" + ele.getChild("itemCode").getAttributeValue("style") + "'>" + ele.getChildText("itemCode") + "</font> ");
    sb.append("<font style='" + ele.getChild("itemName").getAttributeValue("style") + "'>" + ele.getChildText("itemName") + "</font><br>");
}


List item2 = element.getChild("item01").getChildren("item02");
Iterator iter2 = item2.iterator();
while (iter2.hasNext()) {
	Object child = iter2.next();
	Element ele = (Element) child;
	 sb2.append("<font style='" + ele.getChild("itemCode").getAttributeValue("style") + "'>" + ele.getChildText("itemCode") + "</font> ");
	 sb2.append("<font style='" + ele.getChild("itemName").getAttributeValue("style") + "'>" + ele.getChildText("itemName") + "</font><br>");
} 
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

</script>

</head>




<body>

<textarea style="width:600px; height:300px">
<?xml version="1.0" encoding="utf-8" ?>
<itemlist>
	<title style="font:14pt 돋움;font-weight:bold ">File xml Sample</title>
	<item>
		<itemCode style="font:9pt;color: #FF6600">1</itemCode>
		<itemName style="font:9pt;color: #0066CC">현대증권</itemName>
	</item>
	<item>
		<itemCode style="font:9pt;color: #FF6600">1</itemCode>
		<itemName style="font:9pt;color: #0066CC">현대중공업</itemName>
	</item>
	<item>
		<itemCode style="font:9pt;color: #FF6600">1</itemCode>
		<itemName style="font:9pt;color: #0066CC">삼성중공업</itemName>
	</item>
	<item>
		<itemCode style="font:9pt;color: #FF6600">1</itemCode>
		<itemName style="font:9pt;color: #0066CC">삼성전자</itemName>
	</item>
	
	<item01>
		<item02>
			<itemCode style="font:9pt;">2</itemCode>
			<itemName style="font:9pt;">삼성1</itemName>
		</item02>
		<item02>
			<itemCode style="font:9pt;">2</itemCode>
			<itemName style="font:9pt;">삼성2</itemName>
		</item02>
	</item01>
</itemlist>	
</textarea>

	<div style="margin: 20px 0 20px 0"><%=sb %></div>
	
	<div><%=sb2 %></div>
	
	
	


</body>

</html>
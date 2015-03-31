 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ include  file="zdb.inc.jsp" %>
<%
String type = chkNull(request.getParameter("type"));
String host = chkNull(request.getParameter("host"));
String db = chkNull(request.getParameter("db"));
String id = chkNull(request.getParameter("id"));
String pw = chkNull(request.getParameter("pw"));
String tb = chkNull(request.getParameter("tb"));
String flag = chkNull(request.getParameter("flag"));

Connection conn = null;
PreparedStatement  pstmt = null;
ResultSet rs = null;

List <HashMap> list = getColumn(type, host, db, tb,id,pw);			//테이블 컬럼 목록

if(list.size() ==0) {
	out.println("<p style='color:#FF6633;font-size:10pt;font-weight:bold;text-align:center;margin-top:100px'>입력정보가 일치하지 않음 </p>");
	out.println("<p style='color:#0000FF;font-size:10pt;font-weight:bold;text-align:center;'><a href='javascript:window.close()'>닫기</a></p>");
	 return;
}

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zCodeGenerator :::</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<style type="text/css"> 
*{
	font-style: nomal;
	font-size:9pt;
    font-family: 맑은 고딕,굴림,돋움, Dotum, Gulim, Arial, sans-serif, Verdana, Helvetica, geneva;
}
.btn{ height:30px;font-size: 12px;font-weight:bold;} 

</style>

<script>

function chkAll(ts){
	
	//1.7 attr ==> prop
	
	if( ts.checked == true ){
		 $("input:checkbox[name=key]:not(checked)").prop("checked", "checked");
	}else{
		$("input:checkbox[name=key]:checked").prop("checked", "");
	}
	
}

function setKey(){
	
	if($("input[name=key]:checkbox:checked").length == 0) return;

	var data = {
		key : []
	}
	
	$("input[name=key]:checkbox:checked").each(function (i) {
		data.key.push( $(this).val() );
	});
	<% if(flag.equals("key")){ %>
	$("#keyColumn", opener.document).html("<input type='text' name='keyColumn' value='" + data.key + "' readOnly class='inputBox' onClick='getColumn(\"key\")'>");
	<% }else if(flag.equals("view")){ %>
	$("#viewColumn", opener.document).html("<input type='text' name='viewColumn' value='" + data.key + "' readOnly class='inputBox' onClick='getColumn(\"view\")'>");
	<% }else if(flag.equals("form")){ %>
	$("#formColumn", opener.document).html("<input type='text' name='formColumn' value='" + data.key + "' readOnly class='inputBox' onClick='getColumn(\"form\")'>");
	<%}%>
	window.close();
}
</script> 
</head>

<body leftmargin="0" topmargin="10" marginwidth="0" marginheight="0">

<table  border="1" cellspacing="0" cellpadding="3" align='center' style="border-collapse:collapse; border:1px gray solid;width:600px">
<tr style="background-color:#efefef;text-align:center">
	<td width="10"><input type="checkbox"  id="chk" onClick="chkAll(this)"></td>
	<td width="120">Column</td>
	<td width="120">Data Type</td>
	<td>Comment</td>
</tr>
<%for( HashMap hm : list){%>
	<tr>
		<td><input type="checkbox" name="key" value="<%=hm.get("columnName") %>"></td>
		<td><%=hm.get("columnName") %> <%=hm.get("columnKey") == null || hm.get("columnKey").equals("")?"":"(" + hm.get("columnKey") + ")" %></td>
		<td><%=hm.get("columnType") %></td>
		<td><%=hm.get("columnComment") %></td>
	</tr>
<%} %>
</table>

<p align="center">
<input type="button"  value="Key Setting" class="btn" style="width:100px" onClick="setKey()"> 
<input type="button"  value="Close" class="btn" style="width:100px" onClick="window.close()"> 
</p>

</body>
</html>






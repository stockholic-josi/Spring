 <%@ page contentType="text/html; charset=UTF-8" %>
<%

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

function send(){
	var attach = document.getElementById("file[0]").value;
	var attachName = attach.substring( attach.lastIndexOf("\\") + 1 );
	
	if(attachName != null) document.Wform.attachName.value = attachName;
	
	document.Wform.submit(); 
}

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

<form action='./sendMailProc.jsp' method=post name="Wform" enctype="multipart/form-data">
<table cellspacing=0 cellpadding=0 border=0 width=600>
<tr>
	<td colspan=2 height=3></td>
</tr>

<tr><td colspan=2 height=2 bgcolor=#E3E3E3></td></tr>
<tr>	
	<td width=100 bgcolor=#F9F9F9 height=28 align=center><font color=#616161><b>수신주소</b></font></td>
	<td bgcolor=#FFFFFF>&nbsp;&nbsp;
		<input type=text name='toMail' value='parkjjss@gmail.com' style="width: 150px;border:1px solid #AFAFAF; font-size:9pt;">
	</td>
</tr>
<tr><td colspan=2 height=1 bgcolor=#E3E3E3></td></tr>

<tr>	
	<td width=100 bgcolor=#F9F9F9 height=28 align=center><font color=#616161><b>수 신 인</b></font></td>
	<td bgcolor=#FFFFFF>&nbsp;&nbsp;
		<input type=text name='toName' value='메렁' style="width: 100px;border:1px solid #AFAFAF; font-size:9pt;">
	</td>
</tr>
<tr><td colspan=2 height=1 bgcolor=#E3E3E3></td></tr>

<tr>	
	<td width=100 bgcolor=#F9F9F9 height=28 align=center><font color=#616161><b>제 목</b></font></td>
	<td bgcolor=#FFFFFF>&nbsp;&nbsp;
		<input type=text name='msgSubj' value='테스트 메일' style="width: 300px;border:1px solid #AFAFAF; font-size:9pt;">
		&nbsp;&nbsp;<input type="checkbox" name="html" value="1">HTML
	</td>
</tr>
<tr><td colspan=2 height=1 bgcolor=#E3E3E3></td></tr>

<tr>	
	<td width=100 bgcolor=#F9F9F9 height=150 align=center><font color=#616161><b>내 용</b></font></td>
	<td bgcolor=#FFFFFF>&nbsp;&nbsp;
		<textarea name="msgText" style="width: 97%;height:150px;border:1px solid #AFAFAF; font-size:9pt;"><b>테스트 내용</b></textarea>
	</td>
</tr>
<tr><td colspan=2 height=1 bgcolor=#E3E3E3></td></tr>

<tr>	
	<td width=100 bgcolor=#F9F9F9 height=40 align=center><font color=#616161><b>파 일</b></font></td>
	<td bgcolor=#FFFFFF>&nbsp;&nbsp;
		<input type='file' size='60' name='file[0]' class='border'>
	</td>
</tr>
<tr><td colspan=2 height=1 bgcolor=#E3E3E3></td></tr>

<input type="hidden"name="attachName">
</table>
</form>

<br><br>

<button onClick="send()">전송</button>




</body>

</html>
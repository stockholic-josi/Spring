<html>

<title>아이디 검색</title>
</head>
<script language="JavaScript" src='/js/comm.js'></script>
<link rel="stylesheet" href="/css/host.css" type="text/css">	

<SCRIPT LANGUAGE="JavaScript">
<!--

function zipSearch(){

	if(!trim(document.idForm.userId.value)){
		alert("검색어를 입력해주세요");
		document.idForm.userId.select();
		return;
	}	
	
	document.idForm.submit();
}

function add_id(id){
	opener.reg_form.userId.value = id;
	window.close();
}

//-->
</SCRIPT>	

<body onLoad="document.idForm.userId.select()">

<table cellspacing=0 cellpadding=0 border=0 width=100% align=center>
<tr height=30>
	<form method="post" name="idForm"action="./searchId.do" onSubmit="zipSearch();return false">
		<td align="center" colspan="2">
		<input type="text" name="userId" value="$!userId" class="border">
		<a href="javascript:zipSearch()">검색</a>
	</td>
	</form>
</tr>

<tr>
	<td colspan=2 height=90 align=center style="line-height:20px">
		#if($status == 'exist')
			<font color='#6D6D6D'><b>'$!userId'</b></font> 은 존재하는 아이디입니다.
		#elseif($status == 'disable')
			<font color='#6D6D6D'><b>'$!userId'</b></font> 은 아이디 형식이 올바르지 않습니다.<br>
			<font color="#3333FF">6 ~12자 이내의 영문/숫자 '_' 만 가능하고 <br> 첫 글자는 '_' 나 숫자가 올 수 없습니다.</font>
		#elseif($status == 'enable')	
			<font color='#6D6D6D'><b>'$!userId'</b></font> 은 사용할 수 있는 아이디입니다.<br><br>
			<b><a href="javascript:add_id('$!userId')"><u>아이디 사용</u></a></b>	
		#else
			<font color="#3333FF">6 ~12자 이내의 영문/숫자 '_' 만 가능하고 <br> 첫 글자는 '_' 나 숫자가 올 수 없습니다.</font>
		#end
		
		<br>
	</td>
</tr>	

<tr>
	<td colspan=2 height=30 align=center><a href="javascript:window.close()">닫기</a></td>
</tr>	

</table>
	
</body>	

</html>

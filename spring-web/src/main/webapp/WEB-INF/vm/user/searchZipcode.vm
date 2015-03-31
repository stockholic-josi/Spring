<html>

<title>우편번호 검색</title>
</head>
<script language="JavaScript" src='/js/comm.js'></script>
<link rel="stylesheet" href="/css/host.css" type="text/css">	

<SCRIPT LANGUAGE="JavaScript">
<!--

function zipSearch(){

	if(!trim(document.zipForm.dong.value)){
		alert("검색어를 입력해주세요");
		document.zipForm.dong.select();
		return;
	}else if(document.zipForm.dong.value.length < 2){
		alert("검색어는 2자이상 입력하여 주십시요");
		document.zipForm.dong.select();
		return;
	}	
	
	document.zipForm.submit();
}

function add_addr(zip1,zip2,addr){
	opener.reg_form.zip1.value = zip1;
	opener.reg_form.zip2.value = zip2;
	opener.reg_form.addr1.value = addr;
	opener.reg_form.addr2.focus();
	window.close();
}

//-->
</SCRIPT>	

<body onLoad="document.zipForm.dong.select()">

<table cellspacing=0 cellpadding=0 border=0 width=100% align=center>
<tr height=30>
	<form method="post" name="zipForm"action="./searchZipcode.do" onSubmit="zipSearch();return false">
		<td align="center" colspan="2">
		<input type="text" name="dong" value="$!dong" style="ime-mode:active" class="border">
		<a href="javascript:zipSearch()">검색</a>
	</td>
	</form>
</tr>

#set($count = 0)
#foreach($list in $zipcodeList)
<tr height="22" onClick="add_addr('$!list.zipcode.substring(0,3)','$!list.zipcode.substring(4,7)','$!list.city $!list.gu $!list.dong')" style="cursor:hand">
	<td width="60" align="center">$!list.zipcode</td>	
	<td>$!list.city $!list.gu $!list.dong $!list.addr</td>	
</tr>
#set($count = $velocityCount)
#end

#if($count == 0)
<tr>
	<td colspan=2 height=100 align=center>자료가 없습니다.</td>
</tr>	
#end

</table>
	
</body>	

</html>

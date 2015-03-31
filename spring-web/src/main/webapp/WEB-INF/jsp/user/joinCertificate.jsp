<html>
<head>

<link rel="stylesheet" href="/css/user.css" type="text/css">

<script>

</script>

</head>

<body>
	
<div id="headerInfo" style="display:none">
   <span class="headerNav"> 》 Join </span>
</div>

<div class="contents-box">

<table border="0" cellspacing="0" cellpadding="0" width="500" style="margin:0 auto;">
<tr><td height="100" colspan="2"class="regTitle"><img src="/images/icon/icon_10.png" align="absbottom"> 인증 완료</td></tr>	
<tr><td height="2" colspan="2" bgcolor="#5980D6"></td></tr>

<tr height="25">
	<td width="200" class="regEle" align="center">아이디 : </td>
	<td width="300" class="regInfo">$!info.userId</td>
</tr>
<tr height="25">
	<td width="200" class="regEle" align="center">닉네임 : </td>
	<td width="300" class="regInfo">$!info.userNm</td>
</tr>
<tr height="25">
	<td width="200" class="regEle" align="center">이메일 : </td>
	<td width="300" class="regInfo">$!info.email</td>
</tr>

<tr height="100">
	<td colspan="2" class="regMail">
		
	#if($msg == 'success')
	본인인증이 완료되었습다.
	#else
	<font color="#FF6600">잘못된 정보입니다.</font>
	#end
	
	</td>
</tr>

<tr><td height="2"  colspan="2" bgcolor="#D0D0D0"></td></tr>
</table>

	<div class="clear"></div>
</div>
	
</body>
</html>
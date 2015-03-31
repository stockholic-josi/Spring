<html>
<head>

<script type="text/javascript" src="/js/join.js"></script>
<script type="text/javascript" src="/js/sortable/jquery.cookies.js"></script>
<link rel="stylesheet" href="/css/user.css" type="text/css">
	
<script>
function sendForm(){

	var frm = document.regForm;
	if(!trim(frm.passwd.value)){
		modalAlert("패스워드를 입력하여 주십시요",function(){
			frm.passwd.focus();
		});
		return;
	}else if(!isValidPasswd(frm.passwd.value)){
		frm.passwd.focus();
		return;
	}else if(frm.passwd.value != frm.passwd2.value){
		modalAlert("패스워드가 일치하지 않습니다.",function(){
			frm.passwd2.focus();
		});
		return;
	}
	
	jQuery.ajax({
		url: 'userPwModify.do',
		type: 'POST',
		data: $("#regForm").serialize(),
		timeout : 3000,
		error: function(){
			modalAlert("비밀번호 변경에 실패하였습니다.");
		},
		success: function(msg){
			var result = eval('(' + msg + ')'); 
			if( result.reg == "success"){
				modalAlert("비밀번호가 변경되었습니다.")
				$("#passwd").val("");
				$("#passwd2").val("");
			}
		}
	});
	
}
</script>

</head>



<body>
	
<div id="headerInfo" style="display:none">
   <span class="headerNav"> 》 Member Info 》 비밀번호변경</span>
</div>


<div class="contents-box">
	<div class="leftBar">&nbsp;</div>
	<div class="leftMenu">
    <ul>
		<li><a href="./userInfo.do">ㆍ회원정보</a></li>
		<li><a href="#" style="background:#EBF4FC">ㆍ비밀번호변경</a></li>
		<li><a href="./userOut.do">ㆍ회원탈퇴</a></li>
    </ul>
	</div>

	<div class="contents">
   	 <table border="0" cellspacing="0" cellpadding="0">
    <tr><td colspan="2" height="50" class="joinTitle"><img src="/images/icon/icon_10.png" align="absbottom"> 비밀번호변경</td></tr>	
    <tr><td height="2" colspan="2" bgcolor="#5980D6"></td></tr>
    
    <form name="regForm" id="regForm" method="post" action="joinProc.do" onSubmit="return false">
   <tr height="50">
    	<td width="100" class="joinEle">패스워드</td>
    	<td width="500">
    		<input type="password" maxlength="18" name="passwd" style="width:150px" class="inputBox">
    		<font class="joinCheck">공백없이 6 ~18자 이하</font>
    	</td>
    </tr>
    <tr><td colspan="2" height="1" bgcolor="EEEEEE"></td></tr>
    
     <tr height="50">
    	<td width="100" class="joinEle">패스워드확인</td>
    	<td width="500">
    		<input type="password"  maxlength="18" name="passwd2" style="width:150px" class="inputBox">
    	</td>
    </tr>
    <tr><td colspan="2" height="1" bgcolor="EEEEEE"></td></tr>
    
    <tr>
    	<td colspan="2" height="50" align="center">
           <button class="word2" onClick="sendForm()" onFocus="blur()">수정</button>
    	</td>
    </tr>
    
    </form>
     <tr><td colspan="2" height="280"></td></tr>
    </table>
	</div>

	<div class="clear"></div>
</div>


	
</body>
</html>
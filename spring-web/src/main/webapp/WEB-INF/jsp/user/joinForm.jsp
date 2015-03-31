<%@ page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript" src="/js/join.js"></script>
<script>


var isCheckId = false;
function setSearchId(id){
	document.regForm.userId.value = id;
	isCheckId = true;
}

function searchId(){

	zpop.iframe({
		title : "아이디찾기",
		width:350,
		height:200,
		url : "idSearchForm.do?userId=" + $('#userId').val(),
		modal : true,
		header: true
	},function(){
		$("#zpopIframe")[0].contentWindow.setUserId();
		zpop.close();
	})

}

var isCheckNick = false;
function setSearchNick(nm){
	document.regForm.userNm.value = nm;
	isCheckNick = true;
}
function searchNick(){
	
	zpop.iframe({
		title : "닉네임찾기",
		width:350,
		height:200,
		url : "nickSearchForm.do?userNm=" + trim($('#userNm').val()),
		modal : true,
		header: true
	},function(){
		$("#zpopIframe")[0].contentWindow.setNick();
		zpop.close();
	})
}


function sendForm(){
	var frm = document.regForm;
	
	if(!isValidUserid(frm.userId.value)){
		
		zpop.alert('아이디 형식이 올바르지 않습니다',function(){
			frm.userId.focus();
		});
		
		return;
	}
	
	if(!trim(frm.passwd.value)){
		zpop.alert('패스워드를 입력하여 주십시요',function(){
			frm.passwd.focus();
		});
		return;
	}else if(!isValidPasswd(frm.passwd.value)){
		frm.passwd.focus();
		return;
	}else if(frm.passwd.value != frm.passwd2.value){
		zpop.alert('패스워드가 일치하지 않습니다',function(){
			frm.passwd2.focus();
		});
		return;
	}
	
	if(!isValidNick(frm.userNm.value)){
		zpop.alert('닉네임 형식이 올바르지 않습니다',function(){
			frm.passwd2.focus();
		});
	}

	var email = frm.email1.value + "@"  + frm.email2.value;
	
	if( isValidEmail(email) == false ){	
		zpop.alert('메일 형식이 올바르지 않습니다',function(){
			frm.email1.focus();
		});
		
		return;
	}
	
	if(isCheckId == false){
		alertDialog("알림","아이디를 검색을 해주세요.",function(){
			$("#dialog").dialog("close"); 
		});
		return;
	}
	
	if(isCheckNick == false){
		zpop.alert('닉네임을 검색을 해주세요',function(){
			frm.email1.focus();
		});
		return;
	}
	
	if($.cookie("captcha") != frm.captcha.value){
		
		zpop.alert('인증 문자가 일치하지 않습니다',function(){
			frm.captcha.select();
		});
		return;
	}

	frm.email.value = email;
	
	$.ajax({
		url: 'joinProc.do',
		type: 'POST',
		data: $("#regForm").serialize(),
		timeout : 3000,
		error: function(){
			zpop.alert("저장에 실패하였습니다.");
		},
		success: function(msg){
			var result = eval('(' + msg + ')'); 
			if( result.reg == "fail"){
				zpop.alert("등록 정보에 올바르지 않은 정보가 있습니다. 등록할 수 없습니다.");
			}else if( result.reg == "isId"){
				zpop.alert("동일한 아이디가 존재합니다.");
			}else if( result.reg == "isNick"){
				zpop.alert("동일한 닉네임이 존재합니다.");
			}else if( result.reg == "isEmail"){
				zpop.alert("동일한 이메일이 존재합니다.");
			}else if( result.reg == "success"){
				$.ajax({    
                	url: "/login",    
                	type: "POST",    
                	data: "userNm=" + $('#userId').val() + "&passwd=" + $('#passwd').val(),
                	beforeSend: function (xhr) {        
                		xhr.setRequestHeader("X-Ajax-call", "true");    
                	},    
                	success: function(result) {  
                		if (result == "OK") {
            				location.href = "/index.html"
                		}    
                	}
                });
			}
		}
	});
	
	
}

</script>

	
<div id="headerInfo" style="display:none">
   <span class="headerNav"> 》 Join  》 회원가입 1단계</span>
</div>


<div class="contents-box">

<form name="regForm" id="regForm" method="post" action="joinProc.do" onSubmit="return false">
<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr><td colspan="3" height="50" class="joinTitle"><img src="/images/icon/icon_10.png" align="absbottom"> 회원가입</td></tr>	
<tr><td height="2" colspan="3" bgcolor="#5980D6"></td></tr>

<tr height="50">
	<td width="100" class="joinEle">아이디</td>
	<td width="500">
		<input type="text" name="userId" id="userId" maxlength="12" style="width:100px;ime-mode:inactive" class="inputBox">
        <button  onfocus="blur()" onClick="searchId()" >아이디 검색</button>
		<font class="joinCheck">공백없이 4 ~ 12자 이하의 " _"ㆍ영문ㆍ숫자</font>
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

 <tr height="50">
	<td width="100" class="joinEle">패스워드</td>
	<td width="500">
		<input type="password" maxlength="18" name="passwd" id="passwd" style="width:150px" class="inputBox">
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

 <tr height="50">
	<td width="100" class="joinEle">패스워드확인</td>
	<td width="500">
		<input type="password"  maxlength="18" name="passwd2" style="width:150px" class="inputBox">
		<font class="joinCheck">공백없이 6 ~18자 이하</font>
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

<tr height="50">
	<td width="100" class="joinEle">닉네임</td>
	<td width="500">
		<input type="text" name="userNm" id="userNm" maxlength="10" style="width:150px" class="inputBox">
        <button class="word6" onClick="searchNick()" onFocus="blur()">닉네임 검색</button>
		<font class="joinCheck">공백없이 2 ~ 10자 이하 한글ㆍ영문ㆍ숫자</font>
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>


<tr height="60">
	<td width="100" class="joinEle">이메일</td>
	<td width="500">
		<div style="float:left;margin-right:5px">
			<input type=text name="email1" style="width:100px;ime-mode:inactive" class="inputBox">@<input type=text name="email2" style="width:100px" class="inputBox">
		</div>
		<div>		
		<select name="email3" id="email3" onChange="setEmail(this.options[this.selectedIndex].value)">
    		<option value="">이메일선택</option>
    		<option value="gmail.com">gmail.com</option>
    		<option value="naver.com">naver.com</option>
    		<option value="yahoo.co.kr">yahoo.co.kr</option>
    		<option value="nate.com">nate.com</option>
    		<option value="hotmail.com">hotmail.com</option>
    		<option value="empal.com">empal.com</option>
    		<option value="hanmail.net">hanmail.net</option>
    		<option value="dreamwiz.com">dreamwiz.com</option>
    		<option value="freechal.com">freechal.com</option>
    		<option value="netian.com">netian.com</option>
    		<option value="lycos.co.kr">lycos.co.kr</option>
    		<option value="korea.com">korea.com</option>
    		<option value="chollian.net">chollian.net</option>
    		<option value="intizen.com">intizen.com</option>
    		<option value="chollian.net">chollian.net</option>
    		<option value="hanafos.com">hanafos.com</option>
    		<option value="kebi.com">kebi.com</option>
    		<option value="korea.com">korea.com</option>
    		<option value="netsgo.com">netsgo.com</option>
    		<option value="sayclub.com">sayclub.com</option>
		</select>
        </div>
		
		<div style="clear:both;margin-top:25px">
		<!-- <font color="#FF6600">※ 등록 후 이메일 인증을 해야 정상 가입됩니다.</font> -->
		</div>
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

<tr height="50">
	<td width="100" class="joinEle">이메일수신여부</td>
	<td width="500">
		<input type="radio" name="receive" id="receive" value="Y">수신 &nbsp;
		<input type="radio" name="receive" id="receive" value="N" checked>비수신
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

<tr height="70">
	<td colspan="3">
		<div style="float:left;margin-right:10px"><img src="/Captcha" class="hand" onClick="changeCaptcha(this)"></div>
		<div style="float:left;margin-right:20px"><input type="text" name="captcha" id="captcha" style="width:50px;margin-top:5px;ime-mode:inactive" maxlength="5" class="inputBox"></div>
        <div>
			<font class="joinCheck">ㆍ좌측 이미지 문자 5자리를 입력해 주세요<br>ㆍ좌측 이미지가 잘 보이지 않으면 이미지를 클릭해주세요</font>
		</div>
	</td>
</tr>
<tr><td colspan="3" height="1" bgcolor="EEEEEE"></td></tr>

<tr>
	<td colspan="3" height="50" align="center">
       <button class="word2" onClick="sendForm()" onFocus="blur()">전송</button>&nbsp;
       <button class="word2" onClick="history.back()" onFocus="blur()">취소</button>
	</td>
</tr>


<tr><td height="2" colspan="3" bgcolor="#D0D0D0"></td></tr>
</table>
<input type="hidden" name="email">
	
</form>


	<div class="clear"></div>
</div>

<div id='idSearch' style="display: none">
<iframe  src="" id="idFrame" scrolling="no" frameborder="0" style="width:100%;height:100px"></iframe>
</div>


<div id='nickSearch' style="display: none">
<iframe  src="" id="nickFrame" scrolling="no" frameborder="0" style="width:100%;height:100px"></iframe>
</div>
	

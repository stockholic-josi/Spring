<%@ page contentType="text/html; charset=UTF-8" %>

<script>


//닉네임 체크
function isValiduserNm(el) {
	var pattern = /^[a-zA-Z0-9가-힝]{2,10}$/;
	return (pattern.test(el)) ? true : false;
}

function nickSearch(){

	if( !trim($("#userNm").val()) ){
		$("#result").html("<font color='#0066CF'>닉네임을 검색해주세요.</font>");
		return ;
	}
	
	if(!isValiduserNm($("#userNm").val())){
		$("#result").html("<font color='#FF6600'>닉네임 형식이 올바르지 않습니다.</font>");
		return ;
	}
	
	$.ajax({    
    	url: "/user/ajax/nickSearchProc.do",    
    	type: "POST",    
    	data: $("#frm").serialize(),    
		error: function (e) {        
    	},    
    	success: function(data) {  
			
			if(data == 0){
				$("#result").html('<font color=" #0066CF">사용할 수 있는 닉네임입니다.</font>');
			}else{
    			$("#result").html('<font color="#FF6600">닉네임이 존재합니다.</font>');
			}
    	}
    });

}

function setNick(){
	if(isValiduserNm($("#userNm").val())){
		parent.setSearchNick($("#userNm").val());
	}
}


$(document).ready(function() {
	$("#userNm").select();
});


</script>


<table cellspacing="0" cellpadding="0" align="center" border="0">
<tr>
	<td align="center" height="30">
		<form name="frm" id="frm" onSubmit="nickSearch(); return false">
		닉네임 : <input type="text" name="userNm" id="userNm" value="${dto.userNm}" maxlength="10" style="ime-mode:active" class="inputBox">
		</form>
	</td>
	<td>&nbsp;<button class="word2" onClick="nickSearch()">검색</button></td>
</tr>
</table>

<div id="result" style="text-align:center;margin-top:20px"></div>


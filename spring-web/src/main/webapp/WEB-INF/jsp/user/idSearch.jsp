<%@ page contentType="text/html; charset=UTF-8" %>

<script>


//아이디 체크
function isValidUserid(el) {
	var pattern = /^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$/;
	return (pattern.test(el)) ? true : false;
}

function idSearch(){

	if( !trim($("#userId").val()) ){
		$("#result").html("<font color='#0066CF'>아이디를 검색해주세요.</font>");
		return ;
	}
	
	if(!isValidUserid($("#userId").val())){
		$("#result").html("<font color='#FF6600'>아이디 형식이 올바르지 않습니다.</font>");
		return ;
	}
	
	$.ajax({    
    	url: "/user/ajax/idSearchProc.do",    
    	type: "POST",    
    	data: $("#frm").serialize(),    
		error: function (e) {        
			alert();
    	},    
    	success: function(data) {  
			
			if(data == 0){
				$("#result").html('<font color=" #0066CF">사용할 수 있는 아이디입니다.</font>');
			}else{
    			$("#result").html('<font color="#FF6600">아이디가 존재합니다.</font>');
			}
    	}
    });

}

function setUserId(){
	if(isValidUserid($("#userId").val())){
		parent.setSearchId($("#userId").val());
	}
}

$(document).ready(function() {

	$("#userId").select();
});


</script>


<table cellspacing="0" cellpadding="0" align="center" border="0">
<tr>
	<td align="center" height="30">
		<form name="frm" id="frm" onSubmit="idSearch();return false">
		아이디 : <input type="text" name="userId" id="userId" value="${dto.userId}" maxlength="12" style="ime-mode:inactive" class="inputBox">
		</form>
	</td>
	<td>&nbsp;<button class="word2" onClick="idSearch()">검색</button></td>
</tr>
</table>

<div id="result" style="text-align:center;margin-top:20px"></div>


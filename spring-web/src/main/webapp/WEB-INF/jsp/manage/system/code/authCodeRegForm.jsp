<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>


function userSearch(){
	
	if(trim( $("#userId").val() )){

		$.ajax({
			url: 'authCodeRegListAjax.do',
			type : 'POST',
			data : $("#userForm").serialize(),
			timeout : 3000,

			error: function(){
				modalAlert('검색에 실패하였습니다.');
			},
			success: function(msg){
				$("#userList").html(msg);
			}
		});
	}	

	$("#userId").select();
}


<c:if test="${not empty dto.userId}">userSearch()</c:if>

$("#userId").select();

$( "#searchBtn" ).button({
	icons: {
		primary: "ui-icon-document"
	}
});


</script>

<table cellspacing="0" cellpadding="0" align="center" border="0">
<tr>
	
	<td align="center" height="30">
		<form name="userForm" id="userForm" onSubmit="userSearch(); return false">
		아이디 : <input type="text" name="userId" id="userId" value="${dto.userId}" class="inputBox">
		</form>
	</td>
	<td>&nbsp;<button class="word2" onClick="userSearch()" id="searchBtn">검색</button></td>
</tr>
</table>

<div id="userList" align="center"></div>
	

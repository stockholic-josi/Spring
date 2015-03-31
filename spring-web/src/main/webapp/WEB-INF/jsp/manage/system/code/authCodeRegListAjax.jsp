<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
function setRoleCd(){

	//if( $("input[name=lstRoleCd]:checkbox:checked").length ==0  )	return;

	document.codeForm.userId.value = document.userForm.userId.value;
	
	$.ajax({
		url: 'authCodeRegProc.do',
		type : 'POST',
		data : $("#codeForm").serialize(),
		timeout : 5000,

		error: function(){
			zpop.alert("저장에 실패하였습니다");
		},
		success: function(msg){
			parent.store2.load();
			parent.zpop.close();
		}
	});
	
	
}


function allCheck(th){
	if(th.checked == true){
		 $("input:checkbox[name=lstRoleCd]:not(checked)").prop("checked", "checked");
	}else{
		 $("input:checkbox[name=lstRoleCd]:checked").prop("checked", "");
	}
}
</script>

<table cellspacing="0" cellpadding="0">
<tr><td colspan="3" height="2" bgcolor="#ACBDEA"></td></tr>
<tr height="25" bgcolor="#F2F2F2" align="center">
	<td width="90"><font color="#5D5D5D""><b>코드</b></font></td>
	<td align="left" width="170"><font color="#5D5D5D"><b>코드명</b></font></td>
	<td width="30"><input type="checkbox" onClick="allCheck(this)"></td>
</tr>
<form name="codeForm" id="codeForm">
	<c:forEach var="list" items="${codeList }" >
	<tr align="center" height="24">
		<td>${list.roleCd}</td>
		<td align="left">${list.roleNm}</td>
		<td><input type="checkbox" name="lstRoleCd" value="${list.roleCd}"  <c:if test="${list.editCd eq 'I'}">checked </c:if>></td>
	</tr>
	</c:forEach>
	<input type="hidden" name="userId" id="userId">
</form>	

<c:if test="${empty codeList }">
<tr><td colspan="3" height="100" align="center">사용자가 존재하지 않습니다.</td></tr>
</c:if>


</table>
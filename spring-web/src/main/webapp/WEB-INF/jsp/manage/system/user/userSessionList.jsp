<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shkr" uri="/WEB-INF/tld/Shkr.tld" %>
<script>
function removeSession(ts,sessionId){

	zpop.confirm("로그아웃을 하겠습니까?.",function(){
		
		$.ajax({
        	url: 'userSessionLogOut.do',
        	type : 'POST',
    		data: 'sessionId=' + sessionId,
        	timeout : 3000,
    		error: function(){
            	zpop.alert("로그아웃 실패");
        	},
    		success: function(data){
    		  $(ts).parent().html("<font color='red'>로그아웃</font>");
    		}
        });
	});

}

</script>
	
<div id="wrapper" style="width:900px">
	
    <div>총 ${total} 명 [ ${p} / ${totalPage}  ]</div>
    <table cellpadding="3" cellspacing="0" border="1" style="border-collapse:collapse; border:1px gray solid;margin-top:5px">
    <tr align="center" bgcolor="#E1E1E1">
    	<td width="40">번호</td>
    	<td width="110">아이디</td>
    	<td width="80">이름</td>
    	<td width="140">권한</td>
    	<td width="220">세션아이디</td>
    	<td width="110">로그인시간</td>
    	<td width="80">상태</td>
    </tr>
	
	<c:forEach var="list" items="${userList }" varStatus="status">
    <tr>
    	<td align="center">${status.count}</td>
    	<td>${list.userId}</td>
    	<td>${list.userNm}</td>
    	<td>${list.roleList}</td>
    	<td>${list.sessionId}</td>
    	<td align="center"> <fmt:formatDate value="${list.loginDate}" pattern="yyyy.MM.dd hh:mm" /></td>
    	<td align="center">로그인 <img src="/images/icon/close.gif" align="absmiddle" class="hand" onClick="removeSession(this,'${list.sessionId}');"></td>
    </tr>
	</c:forEach>

    </table>
	
	<c:if test="${total !=0 }">
	<div id='paging'>
		<shkr:nav totalPage="${totalPage }" page="${p}" param="" url="./userSessionList.do" /> 
	</div>
    </c:if>
	
</div>

</html>
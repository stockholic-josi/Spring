<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shkr" uri="/WEB-INF/tld/Shkr.tld" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<script>


$(document).ready(function () {

});

</script>

	
<div id="wrapper">
	

	<div id="subMenu" style="height:22px"> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
    	<tr>
			<td width="300">
    			<form name="frm" id="frm" action="./pollList.do">
    			<input type="text" name="searchString" id="searchString" value="${search.searchString}" class="inputBox" style="ime-mode:active;width:300px">
				</form>
			</td>
			<td width="100"><a href="javascript:search()"><img src="/images/common/search_02.gif"></a></td>
			<td>Total : ${search.total} [  ${search.page} / ${search.totalPage} ]</td>
    	</tr>
		</table>
	</div>	

	<table cellspacing="5" cellpadding="0" border="0" width="100%" id="pollTb">
    <tr><td colspan="2" height="2" bgcolor='#ACBDEA'></td></tr>
    
    <c:forEach var="list" items="${pollList }">
		
	<tr height="20">	
		<td width="30" rowspan="2" style="color:#888888;font-weight:bold;text-align:center">${list.num}</td>
		<td align="right" style="color:#888888;font-weight:bold">
			<c:choose>
				<c:when test="${list.pollStatus eq '0'}">)대기중	</c:when>
				<c:when test="${list.pollStatus eq '1'}"><span style="color:#2B6AB9;font-weight:bold">진행중</span></c:when>
				<c:when test="${list.pollStatus eq '2'}">종 료</c:when>
			</c:choose>&nbsp;
			
			기간 : ${list.startDate} ~ ${list.endDate} &nbsp;
			
			참여인원 : <fmt:formatNumber value="${list.hit}" type="number" />
		</td>	
	</tr>	
		
    <tr  height="40" valign="top" class="lineHeight">	
    	<td>
            <a href="./pollRead.do?seqNo=${list.seqNo}&page=${search.page}" style="color:#2B6AB9;font-weight:bold">${list.title}</a><br>
			${fn:replace(list.content, newLineChar,"<br>")}
    	</td>
    
    </tr>
	 <tr><td colspan="2" height="1" bgcolor="#D2D2D2"></td></tr>
	
    </c:forEach>
    
    <c:if test="${search.total == 0}">
    <tr align="center" height="150">	
    	<td>자료가 없습니다.</td>
    </tr>
	<tr><td colspan="2" height="1" bgcolor="#D2D2D2"></td></tr>
    </c:if>
    
	 <tr>
		<td colspan="2" height="30">
            <button class="word2" onClick="document.location.href='./pollForm.do'">등록</button>
		</td>
	 </tr>
    </table>
    
	<c:if test="${search.total != 0 }">
	<div id='paging'>
		<c:set var="searchParam" value="&searchString=${search.searchString}" />
		<shkr:nav totalPage="${search.totalPage }" page="${search.page}" param="${searchParam}" url="./pollList.do" pageCount="5"/> 
	</div>
	</c:if>
</div>
	



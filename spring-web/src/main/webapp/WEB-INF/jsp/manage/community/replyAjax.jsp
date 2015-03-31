<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
	
	<!-- 댓글 목록 -->
	<table cellspacing="0" cellpadding="0" width="100%" border="0" id="board" style="margin-top:10px">
	<tr><td colspan="3" height="1" bgcolor="#D2D2D2"></td></tr>
	 <c:forEach var="list" items="${replyList}" varStatus="status">
	<tr>
        <td width="10%" class="listRow"> 
            <span class="userNm">${list.userNm}</span><br>
			<span class="remark">${list.regDate}</span> 
		</td>
        <td class="listRow" id="cnt${list.idx}">${fn:replace(list.content, newLineChar,"<br>")}</td>
		<td width="5%" class="listRow">
			<c:if test="${auth.userId eq list.userId}">
    		<img src="/images/board/memo_mo.gif" class="wordTip hand" id="replyMo" title="수정" tipWidth="25" onClick="reply.modify('${list.idx}')">&nbsp;
    		<img src="/images/board/memo_del.gif" class="wordTip hand" id="replyDel" title="삭제" tipWidth="25" onClick="reply.delete('${list.idx}')">
			</c:if>
		</td>
	</tr>
	</c:forEach>
    </table>
	
	<!-- 댓글 쓰기 폼 -->
	<table cellspacing="0" cellpadding="0" width="100%" border="0" id="board" style="margin-top:10px">
	<tr>
		<form name="frm" id="frm">
        <td width="85%"><textarea style="padding:5px;height:50px;width:100%" style="border" id="replyBox" name="content"></textarea></td>
		<input type="hidden" name="flag" value="${dto.flag}">
		<input type="hidden" name="seqNo" value="${dto.seqNo}">
		</form>
		<td width="15%" style="padding-left:20px" valign="top">
			<button class="word4" onClick="reply.write('')">댓글쓰기</button><br><br>
			<img src="/images/icon/icon_14.png " class="wordTip hand" id="replyBox1" title="늘리기" tipWidth="40" onClick="reply.setBox(50)">
			<img src="/images/icon/icon_15.png" class="wordTip hand" id="replyBox2" title="줄이기" tipWidth="40" onClick="reply.setBox(-50)">
		</td>
	</tr>
    </table>
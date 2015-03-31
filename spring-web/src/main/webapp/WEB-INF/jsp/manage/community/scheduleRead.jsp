<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<jsp:useBean id="now" class="java.util.Date" />

<script language="JavaScript">
function check(){
	if(!trim(Wform.content.value)){
		
		zpop.alert("내용을 입력해 주십시요",function(){
			Wform.content.focus();
		});
	
		return;
	}

	Wform.submit();
}

function delAll(Y,M,D){
	
	var data = {
			year : "${dto.year}",
			month : "<fmt:formatNumber minIntegerDigits="2" value="${dto.month}" />",
			day :  "<fmt:formatNumber minIntegerDigits="2" value="${dto.day}" />"
		}
	
	zpop.confirm("모두 삭제하시겠습니까",function(){
		location.href = "./scheduleDelete.do?" + $.param(data,true)
	});
}

function del(seqNo){
	
	var data = {
		year  :"${dto.year}",
		month : "<fmt:formatNumber minIntegerDigits="2" value="${dto.month}" />",
		day :  "<fmt:formatNumber minIntegerDigits="2" value="${dto.day}" />",
		sqeNo : seqNo
	}
	
	zpop.confirm("삭제하시겠습니까?",function(){
		location.href = "./scheduleDelete.do?seqNo="+ seqNo + "&" + $.param(data,true);
	})
	
}

function modify(seqNo,hh,mm){
	
	 $("#hour > option[value=" + hh + "]").prop("selected", "true");
	 $("#min > option[value=" + mm + "]").prop("selected", "true");
	
	$("#content").text($("#cnt_" + seqNo).text());
	$("#seqNo").val(seqNo)
	
	
	
}

$(document).ready(function () {
	getCalendar("regDate");
});
</script>


	
<div id="wrapper">

<table cellspacing=0 cellpadding=0 border=0 width="100%">
<tr><td colspan="4" height="25"><font color="#808080"><b>${dto.year}년 ${dto.month}월 ${dto.day}일<b></font></td></tr> 
<tr><td colspan="4" height=2 bgcolor='#ACBDEA'></td></tr>

<c:forEach var="list" items="${scheduleList }" varStatus="status">
<tr style="background-color:$color" height="30">	
	<td width='40' align='center'>${status.count }</td>
	<td width='40' align='center'>
		<a href="javascript:del('${list.seqNo}')"><img src="/images/board/memo_del.gif" border="0" align="absmiddle" alt="삭제"></a>&nbsp;
		<a href="javascript:modify('${list.seqNo}','<fmt:formatDate value="${list.schDate}" pattern="hh" />','<fmt:formatDate value="${list.schDate}" pattern="mm" />')"><img src="/images/board/memo_mo.gif" border="0" align="absmiddle" alt="수정"></a>
	</td>
	<td width='80' align='center'><fmt:formatDate value="${list.schDate}" pattern="hh시 mm분" /></td>
	<td style="padding:10;line-height:20px" id="cnt_${list.seqNo}">${fn:replace(list.content, newLineChar,"<br>")}</td>
</tr>
<tr><td colspan="7" background="/images/board/dot2.jpg" height="1"></td></tr>
</c:forEach>

<c:if test="${empty scheduleList}">
<tr align=center height="50">
	<td colspan="4">자료가 없습니다.</td>
</tr>
<tr><td colspan="4" background="/images/board/dot2.jpg" height="1"></td></tr>
</c:if>
<tr>
	<td colspan="4" height="30" align="right">
		<button class="word2" onClick="delAll()">삭제</button>	
	</td>
</tr>
</table>

<form name="Wform" id="Wform" action="./scheduleInsert.do" method="post" onSubmit="return false">
<table cellspacing=0 cellpadding=5 border="0" width="100%">
<tr><td colspan=2 height="20"></td></tr>
<tr>
	<td>
        <div style="float:left;margin-right:5px">
			<img src="/images/common/calendar.gif" align="absmiddle">
			 <c:choose>
				<c:when test="${not empty dto.year }">
					<input TYPE="text" id="regDate" name="regDate"  value="${dto.year}/${dto.month}/${dto.day}" style="width:75px" class="inputBox" readOnly>
				</c:when>
				<c:otherwise>
					<input TYPE="text" id="regDate" name="regDate"  value="<fmt:formatDate value="${now}" pattern="yyyy/MM/dd" />" style="width:75px" class="inputBox" readOnly>
				</c:otherwise>
			</c:choose>
		</div>

		
		<div style="float:left;margin-right:5px">
		<select name="hour" id="hour">
		
		<c:forEach  begin="1" end="24"  varStatus="status">
		<c:choose>
			<c:when test="${status.count == 12 }">
				<option value="<fmt:formatNumber minIntegerDigits="2" value="${status.count}" />"  selected><fmt:formatNumber minIntegerDigits="2" value="${status.count}" /></option>
			</c:when>
			<c:otherwise>
				<option value="<fmt:formatNumber minIntegerDigits="2" value="${status.count}" />"><fmt:formatNumber minIntegerDigits="2" value="${status.count}" /></option>
			</c:otherwise>
		</c:choose>
		</c:forEach>
		</select>시
		</div>
		
		<div style="float:left">
		<select name="min" id="min">
		<c:forEach  begin="0" end="59"  varStatus="status">
		<c:choose>
			<c:when test="${status.index == 30 }">
			<option value="<fmt:formatNumber minIntegerDigits="2" value="${status.index}" />" selected><fmt:formatNumber minIntegerDigits="2" value="${status.index}" /></option>
			</c:when>
			<c:otherwise>
				<option value="<fmt:formatNumber minIntegerDigits="2" value="${status.index}" />"><fmt:formatNumber minIntegerDigits="2" value="${status.index}" /></option>
			</c:otherwise>
		</c:choose>
		</c:forEach>
		</select>분
		</div>
	</td>
</tr>	
<tr>
	<td>
		<textarea name=content id="content" rows="5"  class="boarder" style='width:99%'></textarea>
	</td>
</tr>	
<tr>
	<td  height="30">
		<button class="word2" onClick="check()">등록</button>
		<button class="word2" onClick="location.href='./calendar.do?year=${dto.year}&month=${dto.month}'">목록</button>
	</td>
</tr>	
<input type="hidden" name="seqNo" id="seqNo">
</table>
</form>
	

</div>


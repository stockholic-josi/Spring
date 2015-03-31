<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>

<script type="text/javascript" src="/js/reply.js"></script>		
<script language="javascript" type="text/javascript" src="/js/flot/jquery.flot.js"></script>
<script language="javascript" type="text/javascript" src="/js/flot/jquery.flot.pie.js"></script>
	
<script>
function del(seqNo){

	zpop.confirm("삭제하시겠습니까.",function(){
		document.location.href="./pollDelete.do?seqNo=" + seqNo;
	});
	
}


function poll(){
	if( $("input:radio[name=idx]:checked").length  == 0) {
		
		zpop.alert("항목을 선택해 주십시요",function(){
			$("#dialog").dialog("close"); 
		});
		
		return;
	}

	$.ajax({
		url: 'pollAjax.do',
		type: 'POST',
		data: $("#pForm").serialize(),
		timeout : 3000,
		error: function(){
			zpop.alert("저장에 실패하였습니다.");
		},
		success: function(msg){
			
			if( msg.poll == "isPolled"){
				zpop.alert("이미 설문에 참여하셨습니다.");
			}else{
				zpop.alert("설문에 참여하셨습니다.",function(){
					document.location.reload();
    			});
				
			}
		}
	});

}

var pieData = [];
function setPieData(label,data){
	pieData.push( { label : label,  data: data} );
}

function pieHover(event, pos, obj) {
	if (!obj) return;
	percent = parseFloat(obj.series.percent).toFixed(2);
	$("#pieHover").html('<span style="font-weight: bold; color: '+obj.series.color+'">'+obj.series.label+' ('+percent+'%)</span>');
}

$(document).ready(function() {
	
	$.plot($("#pieChart"), pieData, {
		series: {
             pie: { 
                show: true,
                 radius: 1,
                 label: {
                     show: true,
                     radius: 1,
                     formatter: function(label, series){
                         return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';
                     },
                     background: { opacity: 0.8 }
                 }
             }
         },
         legend: {
             show: false
         },
		  grid: {
             hoverable: true,
         }
	});
	
	$("#pieChart").bind("plothover", pieHover);
	
	//댓글
	reply.init('poll','${poll.seqNo}');
	
});


</script>

	
<div id="wrapper">
	

		<table cellspacing="0" cellpadding="0" border="0" width="100%">
    	<tr height="20">
    		<td width="60%" style="color:#888888;font-weight:bold">
    			설문기간 : ${poll.startDate} ~ ${poll.endDate}
    		</td>
    		<td width="40%" align="right" style="color:#888888;font-weight:bold">
    			등록일  : <fmt:formatDate value="${poll.regDate}" pattern="yyyy.MM.dd" />&nbsp;&nbsp;&nbsp;
    		</td>
    	</tr>
    
    	<tr><td height="2" colspan="2" bgcolor='#ACBDEA'></td></tr>
    	<tr height="28">
    		<td colspan="2" style="padding:0 0 0 5"><font color="#2B6AB9"><b>${poll.title} </b></font></td>
    	</tr>
    	<tr><td colspan="2" height="1" bgcolor='#D2D2D2'></td></tr>
    	<tr>
    		<td colspan="2" height=50 style="line-height:20px;padding:5" valign=top>
    			${fn:replace(poll.content, newLineChar,"<br>")}
    		</td>
    	</tr>
    	
    	<tr>
    		<td height="200" style="line-height:20px;padding:5" valign="top">
				
    			<table cellspacing="5" cellpadding="0" border="0">
    			<tr><td colspan="2"><font color=#616161><b>총 투표인원 : ${poll.total}l 명</b></font></td></tr>
    			<tr><td colspan="2" height="1" bgcolor='#D2D2D2'></td></tr>
    			
    			<form name='pForm' id="pForm"  onSubmit="return false">
    			<c:forEach var="list" items="${questionList}" varStatus="status">
    			<tr height="2">
    				<td width="300">${status.count}. <input type="radio" name='idx' value="${list.idx}"> ${list.title}</td> 
    				<td width="300" bgcolor='#EFEFEF'>
    					<div style="width:${list.graphWidth}px;height:12px;background-color:#ACBDEA;float:left;margin-top:5px;margin-right:5px"></div>
						<div>${list.hit} 명(${list.hitRate}%)</div>
					</td>
    			</tr>
    			<tr><td colspan="2" height="1" bgcolor='#D2D2D2'></td></tr>
				
				<script>setPieData("${list.title}",${list.hitRate})</script>
    			</c:forEach>
    			
    			<input type="hidden" name="seqNo" value="${poll.seqNo}">
    			<input type="hidden" name="p" value="${poll.page}">
    			</form>
    			
    			</table>
				
				<div>
        			<div id="pieChart" style="width:350px;height:200px;float:left"></div>
        			<div id="pieHover" style="padding:50px; 0px 0px 0px"></div>
        		</div>
				
			</td>
    	</tr>
    	<tr>
    		<td colspan="2" height="50"  style="color:#2B6AB9;font-weight:bold">
    		<c:choose>
				<c:when test="${poll.pollStatus eq '0'}">)설문대기</c:when>
				<c:when test="${poll.pollStatus eq '1'}"><button class="word4" onClick="poll()">투표하기</button></c:when>
				<c:when test="${poll.pollStatus eq '2'}">설문종료</c:when>
				<c:when test="${poll.pollStatus eq '4'}">설문에 참여 하셨습니다.</c:when>
			</c:choose>
    		</td>
    	</tr>
    	
    	<tr><td colspan="2" height="3" bgcolor="#D2D2D2"></td></tr>
    
    	<tr>
    		<td height="40" colspan="2">
				<button class="word2" onClick="location.href='./pollList.do?page=${dto.page}'">목록</button>
				<button class="word2" onClick="location.href='./pollUpdateForm.do?seqNo=${poll.seqNo}&page=${dto.page}'">수정</button>
				<button class="word2" onClick="del('${poll.seqNo}')">삭제</button>
    		</td>
    	</tr>
    </table>
	
	<!-- 댓글 -->
    <div id="reply"></div>
	

</div>


	
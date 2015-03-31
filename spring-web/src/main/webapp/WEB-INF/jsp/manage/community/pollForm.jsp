<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
	
<script language="javascript" type="text/javascript" src="/js/jquery.tablednd_0_5.js"></script>

<style>

td.showDragHandle {
	background-image: url(/images/common/updown.gif);
	background-repeat: no-repeat;
	background-position: center center;
	cursor: move;
}

</style>
	
<script language="JavaScript">

var num = 0;

function initDnd(){

	$("#tableDnd").tableDnD({
	    onDrop: function(table, row) {
		/*
            var rows = table.tBodies[0].rows;
			var debugStr = "Text Box 값 : ";
            for (var i=0; i<rows.length; i++) {
                debugStr += $(rows[i]).find("input[type=text]").val() + " ";
            }
			alert(debugStr)
		
		  */
		  	sortNum();
	    },
		onDragStart: function(table, row) {
			
		}
	});

	 $("#tableDnd tr").hover(function() {
          $(this.cells[0]).addClass('showDragHandle');
    }, function() {
          $(this.cells[0]).removeClass('showDragHandle');
    });


}

function sortNum(){
	$("#tableDnd").find(".num").each(function (i){
		$(this).text( (i + 1) );
	});
}

function delItem(obj){
	num = $(".question").length ;
	$(obj).parent().parent().remove();
	sortNum();
}

function addItem(){

	if(num == 10) return;

    var template = "<tr align='center'>"
    				+ "	<td width='15'></td>"
    				+ "	<td width='15' class='num'></td>"
    				+ "	<td><input type='text' name='question' class='question inputBox' style='width:450px'  /><input type='hidden' name='arrHit' value='0'></td>"
    				+ "	<td width='80'><button class='word2' onClick='delItem(this)'>삭제</button></td>"
    				+ "	<td width='100'></td>"
    				+ "</tr>";

	$("#tableDnd tbody").append(template);
	
		$( "#tableDnd tbody button" ).button({
		icons: {
			primary: "ui-icon-document"
		}
	});
	
	
	initDnd();
	
	num = $(".question").length ;
	sortNum();
    
}

function check(){
	var frm = document.Wform;
	
	if(!trim(frm.title.value)){
		zpop.alert("제목을 입력해 주십시요",function(){
			frm.title.focus();
		});
		return;
	}
	
	if(!trim(frm.startDate.value)){
		zpop.alert("설문시직일을 입력해 주십시요",function(){
			frm.startDate.focus();
		});
		return;
	}
	
	if(!trim(frm.endDate.value)){
		zpop.alert("설문종료일을 입력해 주십시요",function(){
			frm.endDate.focus();
		});
		return;
	}
	

	if(!trim(frm.title.value)){
		zpop.alert("제목을 입력해 주십시요",function(){
			frm.title.focus();
		});
		return;
	}
	
	
	var poll = 0;
	for(i = 0; i < frm.question.length; i++){
		if(trim(frm.question[i].value)) poll++;	
	}

	if(poll < 2){
		zpop.alert("두개 이상의  설문항목을 <br>입력하여 주십시요");
		return;
	}
	
	
	for(i = 0; i < frm.question.length; i++){
		if(  !trim(frm.question[i].value) ){
			zpop.alert((i + 1) + "번째 항목을 입력해주세요",function(){
				frm.question[i].focus();
			});
			return;	
		}
	}
	
	$("#startDate").val( $("#startDate").val().replace(/\//g,"-") );
	$("#endDate").val( $("#endDate").val().replace(/\//g,"-") );

	frm.submit();
}

$(document).ready(function() {

	getCalendar("startDate");
	getCalendar("endDate");

	<c:if test="${action eq 'pollWrite'}">
	for(i = 0; i < 4; i++){
		addItem();	
	}
	</c:if>

	initDnd();
})

</script>

	
<div id="wrapper">
	
    <form name="Wform"action="./${action}.do" method="post" onSubmit="return false">
    
    <table cellspacing="0" cellpadding="0" border="0" >

    <c:if test="${action eq 'pollUpdate'}">
    <tr height="35">
    	<td colspan="2">
    		상태 :
    		<c:choose>
				<c:when test="${poll.pollStatus eq '0'}">)대기중</c:when>
				<c:when test="${poll.pollStatus eq '1'}">진행중</c:when>
				<c:when test="${poll.pollStatus eq '2'}">종료</c:when>
			</c:choose> &nbsp;&nbsp;
    		총 투표인원 : ${poll.total} 명
    	</td>
    </tr>	
    </c:if>
    
   <tr><td height="2" colspan="2" bgcolor='#ACBDEA'></td></tr>
    
    <tr>
    	<td width="120" bgcolor="#F9F9F9" height="35" align="center"><font color=#616161><b>설문명</b></font></td>
    	<td width="700" bgcolor="#FFFFFF">
    		<input type="text" name='title' id="title" value='${poll.title}' class="inputBox" style='margin-left:5px;width:95%'>
    	</td>
    </tr>	
    <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr>
    
    <tr>
    	<td width="120" bgcolor="#F9F9F9" height="60" align="center"><font color=#616161><b>설문내용</b></font></td>
    	<td width="700" bgcolor="#FFFFFF">
    		<textarea name="content" rows="3" class="border" style='margin-left:5px;width:95%'>${poll.content}</textarea>
    	</td>
    </tr>	
    <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr>
    
    <tr>
    	<td width="120" bgcolor="#F9F9F9" height="35" align="center"><font color=#616161><b>설문기간</b></font></td>
    	<td width="700" bgcolor="FFFFFF" style="padding-left:5px">
			<img src="/images/common/calendar.gif" align="absmiddle">
			<input TYPE="text" id="startDate" name="startDate"  value="${poll.startDate}" style="width:75px" class="inputBox" readOnly> ~
			<input TYPE="text" id="endDate" name="endDate"  value="${poll.endDate}" style="width:75px" class="inputBox" readOnly>
    	</td>
    </tr>
    <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr> 
    
    <tr>	
    	<td width="120" bgcolor="#F9F9F9" height="35" align="center"><font color=#616161><b>설문항목</b></font></td>
    	<td width="700" bgcolor="#FFFFFF" style='padding:10px'>
            <button class="word2" onClick="addItem()">추가</button>
			
    		<table cellspacing="5" cellpadding="0" border="0" id="tableDnd" width="98%">
            <tbody>
			<c:if test="${action eq 'pollUpdate'}">	
   			<c:forEach var="list" items="${questionList }" varStatus="stataus">
			<tr align='center'>
            	<td width='15'></td>
            	<td width='15' class='num'>${status.count }</td>
            	<td>
					<input type='text' name='question' class='question inputBox' value="${list.title}" style='width:450px'  />
					<input type='hidden' name='arrHit' value="${list.hit}" />
				</td>
            	<td width='80'><button class='word2' onClick='delItem(this)'>삭제</button></td>
            	<td width='100' align='left'>${list.hit}명(${list.hitRate}%)</td>
            </tr>
			</c:forEach>
    		</c:if>
			</tbody>
    		</table>
    	</td>
    </tr>
    <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr>
    </table>
    <input type="hidden" name="seqNo" value="${poll.seqNo}">
    <input type="hidden" name="page" value="${poll.page}">
    </form>
    
    <table cellspacing=0 cellpadding=0 border=0 width=690>
    <tr>	
    	<td bgcolor="#FFFFFF" height=40>
            <button class="word2" onClick="check()">등록</button>	
            <button class="word2" onClick="document.location.href='./pollList.do?page=${dto.page}'">목록</button>	
    	</td>
    </tr>
    </table>
	
</div>
	

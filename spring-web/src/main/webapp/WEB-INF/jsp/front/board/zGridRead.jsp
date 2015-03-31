<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>


<script type="text/javascript" src="/js/reply.js"></script>
<script type="text/javascript" src="/js/jquery.flash.js"></script>
<script type="text/javascript" src="/js/jquery.externalinterface.js"></script>

<link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css"/>
	
<script>

//--------------------------------------------------------------------- fileDownLoad Start
//------------------------------------ Air / App 설치 확인
function setUpApp(msg){
	
	if(msg == "Air"){
		document.location.href = "/zFileDownLoad/fileDownLoadInstallAir.html";
	}else if(msg == "App"){
		document.location.href = "/zFileDownLoad/fileDownLoadInstallApp.html";
	}

}

var fileData = {};

//------------------------------------ Flex 에서 호출
function checkFileList(){

	if( $(".fileList").find("input:checkbox:checked").length == 0){
		alertDialog("알림","파일을 선택해 주세요",function(){
			$("#dialog").dialog("close"); 
		});
	}else{
		callFlexSetFileList();
	}
}

//------------------------------------ Flex setFileList 호출
function callFlexSetFileList(){
	
	if(fileData.fileNm.length > 0){
		
		$("#zFileDownLoad").externalInterface({
			method : 'setFileList',
			args : {
				fileData : fileData
			}
		});
	}
}

function sendFileList(){

	 fileData = {
	 	filePath : "",
		fileNm : [],
		fileRealNm : [],
		fileSize : []
	};

	if( $(".fileList").find("input:checkbox:checked").length == 0) return;

	fileData.filePath = "<spring:message  code='URL.host'/><spring:message  code='board.filePath'/>/$!{dto.flag}";
	

	
	 $(".fileList").find("input:checkbox").each(function() {
		if( $(this).prop("checked") ){
			var file = $(this).val().split("<@>");
			
			fileData.fileNm.push(file[0]);
			fileData.fileRealNm.push(file[1]);
			fileData.fileSize.push(file[2]);
		}
    });
	
}


$(document).ready(function(){

	$('#downFile').flash({
		src : '/zFileDownLoad/zFileLauncher.swf',
		id : 'zFileDownLoad',
		width : '75',
		height : '22',
		bgcolor : '#ffffff',
		allowScriptAccess : 'always'
	});
	
	 $("input:checkbox").click(function() {
		sendFileList();
    });
	
	$("#btnAllCheck").click(function() {
		
		$(".fileList").find("input:checkbox").prop("checked", $(this).prop("checked"));

		sendFileList();
    });


});


//--------------------------------------------------------------------- fileDownLoad End

var params = {
		flag : '${dto.flag}',
		page : '${dto.page}',
		searchKey : '${dto.searchKey}',
		searchValue : '${dto.searchValue}'
}
function boardList(){
	location.href = "./boardList.do?" + jQuery.param(params,true);
}
function boardDelete(no){
	
	zpop.confirm("삭제하시겠습니까 ?",function(){
		location.href = "./boardDelete.do?no=" + no + "&" + $.param(params,true);
	});
	
}
function boardUpdate(no){
	location.href = "./boardUpdateForm.do?no=" + no + "&" + $.param(params,true);
}
function boardReply(no){
	location.href = "./boardReplyForm.do?no=" + no + "&" + $.param(params,true);
}

function downLoad(T,FN,FRN){
	location.href = "/downLoad.do?target=" + T + "&fileNm=" + FN + "&fileRnm=" + encodeURIComponent(FRN);
}


//댓글
$(document).ready(function() {
	reply.init('board','${board.no}');
});
</script>

</head>

<body>
	
<div id="wrapper">
	
	<table cellspacing=0 cellpadding=0 border=0 width="100%">
	<tr height=20>
		<td width="50%">이름 : ${board.userNm}</td>
		<td width="50%" align=right>
			날짜  : ${board.regDate}&nbsp;&nbsp;&nbsp;
			조회 : ${board.readCnt}
		</td>
	</tr>

	<tr><td height=2 colspan="2" bgcolor='#ACBDEA'></td></tr>
	<tr height=28>
		<td colspan="2" style="padding:0 0 0 5"><font color=#8C8C8C><b>${board.title} </b></font></td>
	</tr>
	<tr><td colspan="2" height=1 bgcolor='#D2D2D2'></td></tr>
	<tr>
		<td colspan="2" height=200 style="line-height:20px;padding:5" valign="top">
			${board.content}
		</td>
	</tr>
	<tr><td colspan="2" height=2 bgcolor=#D2D2D2></td></tr>
	
	<tr>
		<td colspan="2" height='50' style="line-height:20px;padding:10">
			<div style="margin:10px 0px 10px 0px">
				<div style="font-size:10pt;float:left"><input type="checkbox" id="btnAllCheck"> 전체선택</div> &nbsp;
				<div id="downFile"></div>
			</div>
			
			 <c:forEach var="list" items="${fileList}" varStatus="status">
			<div style="height:25px" class="fileList">
				<input type="checkbox" value="${list.fileName}<@>${list.fileRealName}<@>${list.fileSize}">
				<img src="/images/board/ext_img/${list.fileExt}.gif"  onerror="this.src='/images/board/ext_img/etc.gif'">
				<a href="javascript:downLoad('board','${dto.flag}/${list.fileName}','${list.fileRealName}')">${list.fileRealName})</a>
			 </div>
			</c:forEach>
			 
		</td>
	</tr>
	<tr><td colspan="2" height=2 bgcolor=#D2D2D2></td></tr>
	
	<tr>
		<td height=40 colspan="2">
            <button onClick="boardList()">목록</button>
            <button onClick="boardReply('${board.no}')">답변</button>
            <button onClick="boardUpdate('${board.no}')">수정</button>
            <button onClick="boardDelete('${board.no}')">삭제</button>
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			
		<table cellspacing=0 cellpadding=0 border=0 width='100%'>
		
		<tr><td colspan="2" height=2 bgcolor=#E3E3E3></td></tr>
		
		 <c:forEach var="list" items="${readList}" varStatus="status">

		<c:if test ="${status.count == 1}">
		<tr>	
			<td width=100 bgcolor=#F9F9F9 height='28' align='center'><font color=#616161><b>이전글</b></font></td>
			<td bgcolor=#FFFFFF>&nbsp;&nbsp;
				<a href="./boardRead.do?no=${list.no}&flag=${dto.flag}">${list.title}</a>
			</td>
		</tr>	
		</c:if>
		
		<c:if test ="${status.count == 2}">
		<tr>	
			<td width=100 bgcolor=#F9F9F9 height=28 align=center><font color=#616161><b>다음글</b></font></td>
			<td bgcolor=#FFFFFF>&nbsp;&nbsp;
				<a href="./boardRead.do?no=${list.no}&flag=${dto.flag}">${list.title}</a>
			</td>
		</tr>	
		</c:if>

		<tr><td colspan="2" height=1 bgcolor=#E3E3E3></td></tr>
		</c:forEach>
		</table>
		
		</td>
		
	</tr>
	</table>
	
	<!-- 댓글 -->
    <div id="reply"></div>

</div>
	

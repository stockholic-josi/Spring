<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>

<script type="text/javascript" src="/js/modal/jqModal.js"></script>
<script type="text/javascript" src="/js/modal/jqDnR.js"></script>
	
<script type="text/javascript" src="/js/swfupload/progressbar.js"></script>
<script type="text/javascript" src="/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/js/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="/js/swfupload/swfupload.speed.js"></script>
<script type="text/javascript" src="/js/swfupload/handlers.js"></script>
<script type="text/javascript" src="/js/swfupload/handlersForm.js"></script>

<script type="text/javascript" src="/js/cleditor/jquery.cleditor.js"></script>
<script type="text/javascript" src="/js/cleditor/jquery.imgUpload.js"></script>

<link rel="stylesheet" type="text/css" href="/js/cleditor/jquery.cleditor.css" />
<link rel="stylesheet" href="/js/swfupload/progressbar.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css"/>

<script>

//-------------------------- 이미지 저장 경로
var imagePath = "<spring:message  code='URL.tmpFilePath'/>" ; 							//임시 URL 경로
var upBoardFilePath = "<spring:message  code='URL.upBoardFilePath'/>";					//실제 URL 경로
var tmpPath = "<spring:message  code='tmp.filePath'/>";										//임시 저장 경로


<c:if test="${action eq 'boardUpdate'}">

    var imgUploadIdx = new Array();
    var imgUploadName = new Array();
    
    <c:forEach var="list" items="${imgList}" varStatus="status">
        imgUploadIdx[${status.index}] = "${list.idx}";
        imgUploadName[${status.index}] = "${list.fileName}";
    </c:forEach>
    
</c:if>

//---------------------------------------------- 파일 업로드
var maxFileCount = 5;
var fileCount = maxFileCount - ${fileCount};

var fileList = ${empty fileList ? 'null' : fileList};
 

function check(){

	var frm = document.Wform;

	if(!trim(frm.title.value)){ 
		alert("제목을 적어주세요");
		frm.title.focus();
		return false;
	}
	
	/*
	if(!$("#content").val()){ 
		alert("내용을 적어주세요");
		return false;
	}
	*/
		
	//이미지
	if(chkImg() != ""){
		$("#content").val( $("#content").val().replace(/\/data\/tmp/g,"/data/board/${dto.flag}") );
		frm.imgFile.value = chkImg();
	}
	
	//alert(chkImg())
	<c:if test="${action eq 'boardUpdate'}">
	chkLoadedImg();
	</c:if>

	return true;
	

}

function boardList(){
	var params = {
		flag : '${dto.flag}',
		page : '${dto.page}',
		searchKey : '${dto.searchKey}',
		searchValue : '${dto.searchValue}'
	}
	location.href = "./boardList.do?" + $.param(params,true);
}

function send(){
	var frm = document.Wform;
	
	if(check() == false) return;

	if( eval($("#fileQuenFormCount").text()) > 0){
		swfForm.startUpload()
	}else{
		frm.submit();
	}
}

function uploadFormComplete(file) {
	var frm = document.Wform;
	
	if( this.getStats().files_queued == 0){
		
		$("#progressbarFormTotal").reportprogress('100');
		$("#fileName").val(fileFormName)
		$("#fileRealName").val(fileFormRealName)
		$("#fileExt").val(fileExt)
		$("#fileSize").val(fileSize)
		
		frm.submit();
	}
	
}


var swfForm;
$(document).ready(function() {
	$("#content").cleditor({width:"100%", height:"100%"}).focus();
	
	$('#fileUploadBox').html(fileTemplateForm);
	
	var settings = {
		flash_url : "/js/swfupload/swfupload.swf",
		upload_url: "/fileUpload?path=<spring:message  code='board.filePath'/>/${dto.flag}",
		file_size_limit : "700 MB",
		file_types : "*.*",
		file_types_description : "ALL Files",
		file_queue_limit : 0,
		debug: false,

		// Button settings
		button_image_url: "/images/button/swf_file_upload.gif",
		button_width: "80",
		button_height: "19",
		button_placeholder_id: "fileUploadFormBtn",
		
		moving_average_history_size: 40,
		
		// The event handler functions are defined in handlersForm.js
		file_dialog_start_handler : fileDialogFormStart,
		file_queued_handler : fileFormQueued,
		file_dialog_complete_handler: fileDialogFormComplete,
		upload_start_handler : uploadFormStart,
		upload_progress_handler : uploadFormProgress,
		upload_success_handler : uploadFormSuccess,
		upload_complete_handler : uploadFormComplete

	};

	swfForm = new SWFUpload(settings);
	$("#progressbarFormTotal").reportprogress('0');
	
	if(fileCount == 0) setTimeout("swfForm.setButtonDisabled(true)",500);
	if(fileList !=null) setOldFile(fileList);
	
 });


</script>
	
	
<div id="wrapper">
	
	
 <form action='./${action}.do' method="post" name="Wform" onSubmit="return false">
 <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr><td colspan="2" height="3"></td></tr>
    <tr><td colspan="2" height="2" bgcolor="#E3E3E3"></td></tr>
    
    <tr>	
    	<td width="100"  bgcolor="#F9F9F9" height="28" align="center"><font color=#616161><b>제 목}</b></font></td>
    	<td bgcolor="#FFFFFF">&nbsp;&nbsp;
    		<input type=text name='title' value='${board.title}' style="width:90%" class="inputBox">
    	</td>
    </tr>
    <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr>
 </table>
 
	
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>	
    	<td height="280">
			<textarea name="content" id="content">
			
			<c:if test="${action eq 'boardReply'}">
				<p>------------------------------------  ${board.userId} 님 원본 -----------------------------------</p>
			</c:if>
			${board.content}
			
			</textarea>
    	</td>
    </tr>
</table> 
 
	
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr> 
      <td width="100" rowspan="2"  bgcolor="#F9F9F9" height="28" align="center">
			<font color=#616161><b>첨부파일</b></font>
	 </td>
    </tr>
	<tr><td id="fileUploadBox" style="padding:10px"></td></tr>
    
   <tr><td colspan="2" height="1" bgcolor="#E3E3E3"></td></tr>
    
    <input type="hidden" name="no" value="${dto.no}">
    <input type="hidden" name="flag" value="${dto.flag}">
    <input type="hidden" name="page" value="${dto.page}">
		
    <input type="hidden" name="fileName" id="fileName">
    <input type="hidden" name="fileRealName" id="fileRealName">
    <input type="hidden" name="fileExt" id="fileExt">
    <input type="hidden" name="fileSize" id="fileSize">
    <input type="hidden" name="delFileIdx" id="delFileIdx">
    <input type="hidden" name="delFileName" id="delFileName">
		
    <input type="hidden" name="imgFile" id="imgFile">
    <input type="hidden" name="delImgFileIdx" id="delImgFileIdx">
    <input type="hidden" name="delImgFileName" id="delImgFileName">
    </table>

    </form>
    
    
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>	
    	<td bgcolor="#FFFFFF" height="40">
               <button onClick="boardList()">목록</button>
               <button onClick="send()">등록</button>
    	</td>
    </tr>
</table>

</div>

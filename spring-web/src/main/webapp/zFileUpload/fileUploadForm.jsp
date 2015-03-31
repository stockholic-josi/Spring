<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*"%>
<%!
public String chkNull(String str){
	if(str == null){
		return  "";
	}
	return str.trim();
}

%>

<%
String rootPath = config.getServletContext().getRealPath("/").replaceAll("\\\\","/");
String subPath = chkNull(request.getParameter("subPath"));
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zFileUpload :::</title>

<script type="text/javascript" src="./js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="./js/progressbar.js"></script>
<script type="text/javascript" src="./js/swfupload.js"></script>
<script type="text/javascript" src="./js/swfupload.queue.js"></script>
<script type="text/javascript" src="./js/swfupload.speed.js"></script>
<script type="text/javascript" src="./js/handlers.js"></script>
<script type="text/javascript" src="./js/handlersForm.js"></script>

<link rel="stylesheet" href="./css/progressbar.css" type="text/css" />

<script>

var rootPath = "<%=rootPath%>";
var subPath = "";
function goSub(dir){
	var sub = (subPath=="")?"":"/";
	subPath = (dir == "") ? "" : subPath + sub + dir;
	getFileList();
}

function upStep(){
	subPath = subPath.substring(0,subPath.lastIndexOf("/"));
	getFileList();
}


function upNav(dir){
	subPath= dir;
	getFileList();
}

function changeRoot(){
	
	if( $("#rootPath").val().substring( trim( $("#rootPath").val() ).length - 1 ) != "/" ){
		$("#rootPath").val( $("#rootPath").val() + "/" )
	}
	
	$("#rootPath").val( $("#rootPath").val().replace(/\\/g,"/") )
	
	rootPath = $("#rootPath").val();
	subPath = "";
	getFileList();
}

function getFileList(){
	
	$("#fileList").html("<img src='./images/loading.gif'>")
	
	var params = {
		rootPath : rootPath,
		subPath : subPath
	}
	
	$.ajax({
    	url: 'fileList.jsp',
    	type : 'POST',
    	dataType: 'text', 
    	data :  $.param(params,true),
		error: function(){
        	$("#fileList").html("<font color='#FF6600'>목록 가져오기 실패</font>")
    	},
		success: function(data){
			$("#fileList").html(data);
		}
    });
	
}

function downLoad(fileNm){
	location.href = "fileDownLoad.jsp?filePath=" + $("#filePath").val() + "&fileNm=" + fileNm;		
}

function initBox(){
	location.href = "fileUploadForm.jsp?rootPath=" + rootPath + "&subPath=" + subPath;
}


//---------------------------------------------- 파일 업로드
var maxFileCount = 100;
var fileCount = maxFileCount - 0;

function trim (strSource) {
	re = /^\s+|\s+$/g;
	return strSource.replace(re, '');
}

function send(){
	
	if( eval($("#fileQuenFormCount").text()) > 0){
		swfForm.startUpload()
	}
}

function uploadFormComplete(file) {
	
	if( this.getStats().files_queued == 0){
		getFileList();
	}
	
}


var swfForm;
$(document).ready(function() {
	
	$('#fileUploadBox').html(fileTemplateForm);
	
	var settings = {
		flash_url : "./images/swfupload.swf",
		//upload_url: "/zFileUpload/fileUpload.jsp",
		file_size_limit : "700 MB",
		file_types : "*.*",
		file_types_description : "ALL Files",
		file_queue_limit : 0,
		debug: false,

		// Button settings
		button_image_url: "./images/swf_file_upload.gif",
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
	
	getFileList();
	
 });


</script>
	
	
</head>


<body>

<div style="width:1070px;">

	<input type="hidden" class="inputBox" style="width:300px" id="rootPath" value="<%=rootPath%>" onKeypress="if(event.keyCode == 13){changeRoot()}" > 


	<div id="fileUploadBox" style="padding:10px;float:left"></div>

	<div id="fileList" style="padding:10px;float:left;height:600px;width:430px;overflow:auto;"></div>
</div>

</body>
	
</html>


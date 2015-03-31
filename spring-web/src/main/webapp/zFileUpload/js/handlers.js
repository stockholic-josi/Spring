/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */

//------------------------------------- 파일폼 템플릿
var fileTemplate = ""
+ "<div style='float:left;margin-bottom:5px'><button  class='word2' onClick='closeUploadModal()' onfocus='blur()'>닫기</button></div>"
+ "<div style='float:left;margin-left:5px;margin-bottom:5px' id='fileDelete'><button  class='word2' onClick='removeFile()' onfocus='blur()'>삭제</button></div>"
+ "<div style='float:left;margin-left:5px;margin-bottom:5px' id='fileSend'><button  class='word2' onClick='swfu.startUpload()' onfocus='blur()'>전송</button></div>"
+ "<table cellspacing='0' cellpadding='0' class='fileUploadStatus' >"
+ "<colgroup>"
+ "	<col width='70'>"
+ "	<col width='220'>"
+ "	<col width='70'>"
+ "	<col width='220'>"
+ "</colgroup>"
+ "<tr>"
+ "	<th>남은시간 : </th>"
+ "	<td id='timeRemaining'>0</td>"
+ "	<th>경과시간 : </th>"
+ "	<td id='timeElapsed'>0</td>"
+ "</tr>"
+ "<tr>"
+ "	<th>전송속도 : </th>"
+ "	<td id='averageSpeed' >0</td>"
+ "	<th>파일개수 : </th>"
+ "	<td><span id='fileUploadCount'>0</span> / <span id='fileQuenCount'>0</span></td>"
+ "</tr>"
+ "<tr>"
+ "	<th>전송상태 : </th>"
+ "	<td><span id='totalUploadFileSize'>0</span> / <span id='totalFileSize'>0</span></td>"
+ "	<td colspan='2'>"
+ "		<div class='progressbarTotal' id='progressbarTotal'></div>"
+ "	</td>"
+ "</tr>"
+ "<tr>"
+ "	<td colspan='4'>"
+ "		<div id='fileContainer'></div>"
+ "	</td>"
+ "</tr>"
+ "</table>";


var uploadCount = 0;
var totalFileSize = 0;
var totalUploadFileSize = 0;

function fileQueued(file) {
	
	//파일 Quen 생성
	var fileDiv = "<div id='" + file.id + "'>"
					+ "<div class='chkbox'><input type='hidden' name='fileSize' value='" + file.size + "'><input type='checkbox' name='fileId' value='" + file.id + "'></div>"
					+"<div class='fileList'>" + file.name + "</div>"
					+"<div class='progressbar' id='progressbar" + file.id + "'></div>"
					+"<div class='fileStatus'>(<span id='uploadSize" + file.id + "'></span> / <span>" + SWFUpload.speed.formatBytes(file.size) + "</span>)</div>"
					+ "</div>";

	
	$("#fileContainer").append(fileDiv);								//파일 Quen 생성
	$("#progressbar" + file.id).reportprogress('0');				//파일 progressbar 초기화
	$("#uploadSize" + file.id).text(0);								//파일 progressbar text 초기화
	
	totalFileSize = totalFileSize + file.size;							//파일 사이즈 총합
	
}

function fileDialogComplete(file) {
	
	if(file == 0) return;
	
	if(file > imgMaxCount){
		alert("이미지는 " + imgMaxCount + " 개만 가능합니다.");
		return;
	}
		
	$("#totalFileSize").text(SWFUpload.speed.formatBytes(totalFileSize));			//파일 사이즈 총합 표시
	$("#totalUploadFileSize").text(0);															//파일 총 업로드사이즈 초기화
	$("#fileUploadCount").text("0");															//파일 업로드 개수 초기화
	//$("#progressbarTotal").reportprogress('0');											//파일 총 progressbar text 초기화 
	
	try {
		$("#fileQuenCount").text(this.getStats().files_queued);						//파일 Quen 총 개수 생성
	} catch (ex) {
		this.debug(ex);
	}
	
	fileUploadModal();
	
	//this.startUpload();
}

function uploadStart(file) {
	
	try {
		updateDisplay.call(this, file);
	}
	catch (ex) {
		this.debug(ex);
	}
	
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		updateDisplay.call(this, file);
	} catch (ex) {
		this.debug(ex);
	}
	
}


var fileName = [];
var fileName = [];

function uploadSuccess(file, serverData) {
	
	try {
		updateDisplay.call(this, file);
		totalUploadFileSize = totalUploadFileSize + file.size;
		
		//업로드 완료 시 progressbar 100%표시
		$("#progressbar" + file.id).reportprogress('100');
		
		//서버에 업로드된 파일 보기
		var obj = eval("(" + serverData + ')'); 
		fileName.push( obj.fileName );
		fileRealName.push( obj.fileRealName );
		
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	
	//$("#fileUploadCount").text(this.getStats().successful_uploads);
	
	if( this.getStats().files_queued == 0){
		
		addFile(fileName)
		$("#progressbarTotal").reportprogress('100');
		
		$("#fileDelete").hide();
		$("#fileSend").hide();
		
	}
	//alert ("Error : " + this.getStats().upload_errors);
	
}

function updateDisplay(file) {
	
	$("#timeRemaining").text( SWFUpload.speed.formatTime(file.timeRemaining) );
	$("#timeElapsed").text( SWFUpload.speed.formatTime(file.timeElapsed) );
	
	$("#uploadSize" + file.id).text(SWFUpload.speed.formatBytes(file.sizeUploaded));
	$("#totalUploadFileSize").text( SWFUpload.speed.formatBytes(totalUploadFileSize + file.sizeUploaded) );

	$("#progressbar" + file.id).reportprogress(SWFUpload.speed.formatPercent(file.percentUploaded)); 
	$("#progressbarTotal").reportprogress(SWFUpload.speed.formatPercent( ((totalUploadFileSize +  file.sizeUploaded) / totalFileSize) * 100 )); 
	
	$("#averageSpeed").text( SWFUpload.speed.formatBPS(file.averageSpeed) );

}


//파일 Diallog 선택시 폼 초기화
function fileDialogStart(){
	$('#fileDialog').html(fileTemplate);    
	$("#progressbarTotal").reportprogress('0');
	
}


//파일제거
function removeFile(){
	
	$("input:checkbox[name=fileId]:checked").each(function(i){
		swfu.cancelUpload( $(this).val() );
	   	$("#fileContainer").find("#" + $(this).val()).remove();
	   	
	   	//파일 queue 
	   	$("#fileQuenCount").text( $("#fileQuenCount").text() - 1 );
	   	
	   	//파일 사이즈 총합
	   	totalFileSize = totalFileSize - eval($(this).parent().find("input:hidden[name=fileSize]").val());
	   	$("#totalFileSize").text(SWFUpload.speed.formatBytes(totalFileSize));			
	   	
	   	
	}); 
}

function fileUploadModal(){
	$('#fileDialog').jqm({
		overlay: 20, 
		modal: true
	});
	$('#fileDialog').jqmShow();
 }

function closeUploadModal(){
	
	$("input:checkbox[name=fileId]").each(function(i){
		swfu.cancelUpload( $(this).val() );
	}); 
	
	$("#content").cleditor()[0].focus();
	
	$('#fileDialog').jqmHide();
	
	
}


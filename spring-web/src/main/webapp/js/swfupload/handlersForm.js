/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */

//------------------------------------- 파일폼 템플릿
var fileTemplateForm = ""
+ "<div style='width:585px;'>"
+ "	<span id='fileUploadFormBtn' style='float:left'></span>"
+ "	<button  class='word2' onClick='removeFileForm()' onfocus='blur()' style='float:right'>삭제</button>"
+" </div>"
+ "<table cellspacing='0' cellpadding='5' class='fileFormUploadStatus' >"
+ "<colgroup>"
+ "	<col width='70'>"
+ "	<col width='220'>"
+ "	<col width='70'>"
+ "	<col width='220'>"
+ "</colgroup>"
+ "<tr>"
+ "	<th>남은시간 : </th>"
+ "	<td id='timeFormRemaining'>0</td>"
+ "	<th>경과시간 : </th>"
+ "	<td id='timeFormElapsed'>0</td>"
+ "</tr>"
+ "<tr>"
+ "	<th>전송속도 : </th>"
+ "	<td id='averageFormSpeed' >0</td>"
+ "	<th>파일개수 : </th>"
+ "	<td><span id='fileUploadFormCount'>0</span> / <span id='fileQuenFormCount'>0</span></td>"
+ "</tr>"
+ "<tr>"
+ "	<th>전송상태 : </th>"
+ "	<td><span id='totalFormUploadFileSize'>0</span> / <span id='totalFormFileSize'>0</span></td>"
+ "	<td colspan='2'>"
+ "		<div class='progressbarTotal' id='progressbarFormTotal'></div>"
+ "	</td>"
+ "</tr>"
+ "<tr>"
+ "	<td colspan='4'>"
+ "		<div id='fileFormContainer'></div>"
+ "	</td>"
+ "</tr>"
+ "</table>";



var uploadCount = 0;
var totalFileSize = 0;
var totalUploadFileSize = 0;

var fileFormName = [];
var fileFormRealName = [];
var fileExt = [];
var fileSize = [];

var delFileIdx = [];
var delFileName = [];



function fileDialogFormStart(){
	swfForm.setFileUploadLimit(fileCount);
}

function fileFormQueued(file) {
	if(fileCount == 0) return;
	
	var ext = "<img src='/images/board/ext_img/" + file.name.substring(file.name.lastIndexOf(".") + 1).toLowerCase() + ".gif' onerror=\"this.src='/images/board/ext_img/etc.gif'\"  align='absmiddle'> ";
	
	//파일 Quen 생성
	var fileDiv = "<div id='" + file.id + "'>"
					+ "<div class='chkbox'><input type='hidden' name='fileSize' value='" + file.size + "'><input type='checkbox' name='fileFormId' value='" + file.id + "'></div>"
					+"<div class='fileList'>"+ ext + file.name + "</div>"
					+"<div class='progressbar' id='progressbarForm" + file.id + "'></div>"
					+"<div class='fileStatus'>(<span id='uploadFormSize" + file.id + "'></span> / <span>" + SWFUpload.speed.formatBytes(file.size) + "</span>)</div>"
					+ "</div>";
	
	$("#fileFormContainer").append(fileDiv);							//파일 Quen 생성
	$("#progressbarForm" + file.id).reportprogress('0');				//파일 progressbar 초기화
	$("#uploadFormSize" + file.id).text(0);								//파일 progressbar text 초기화
	
	totalFileSize = totalFileSize + file.size;								//파일 사이즈 총합
	
}

function fileDialogFormComplete(file) {
	
	if(file == 0) return ;
	
	if(file > maxFileCount || ($('.oldFile').length + file > maxFileCount)){
		alert("파일은 " + maxFileCount + " 개만 가능합니다.");
		return;
	}else{
		$("#totalFormFileSize").text(SWFUpload.speed.formatBytes(totalFileSize));			//파일 사이즈 총합 표시
		$("#totalFormUploadFileSize").text(0);															//파일 총 업로드사이즈 초기화
		$("#fileUploadFormCount").text("0");															//파일 업로드 개수 초기화
		
		try {
			$("#fileQuenFormCount").text(this.getStats().files_queued);							//파일 Quen 총 개수 생성
		} catch (ex) {
			this.debug(ex);
		}
	}
	
	//this.startUpload();
}

function uploadFormStart(file) {
	
	try {
		updateFormDisplay.call(this, file);
	}
	catch (ex) {
		this.debug(ex);
	}
	
}

function uploadFormProgress(file, bytesLoaded, bytesTotal) {
	try {
		updateFormDisplay.call(this, file);
	} catch (ex) {
		this.debug(ex);
	}
	
}


function uploadFormSuccess(file, serverData) {
	
	try {
		updateFormDisplay.call(this, file);
		totalUploadFileSize = totalUploadFileSize + file.size;
		
		//업로드 완료 시 progressbar 100%표시
		$("#progressbarForm" + file.id).reportprogress('100');
		
		//서버에 업로드된 파일 보기
		var obj = eval("(" + serverData + ')'); 
		fileFormName.push( obj.fileName );
		fileFormRealName.push( obj.fileRealName );
		fileExt.push( obj.fileExt );
		fileSize.push( obj.fileSize );
		
	} catch (ex) {
		this.debug(ex);
	}
}

function updateFormDisplay(file) {
	
	$("#timeFormRemaining").text( SWFUpload.speed.formatTime(file.timeRemaining) );
	$("#timeFormElapsed").text( SWFUpload.speed.formatTime(file.timeElapsed) );
	
	$("#uploadFormSize" + file.id).text(SWFUpload.speed.formatBytes(file.sizeUploaded));
	$("#totalFormUploadFileSize").text( SWFUpload.speed.formatBytes(totalUploadFileSize + file.sizeUploaded) );

	$("#progressbarForm" + file.id).reportprogress(SWFUpload.speed.formatPercent(file.percentUploaded)); 
	$("#progressbarFormTotal").reportprogress(SWFUpload.speed.formatPercent( ((totalUploadFileSize +  file.sizeUploaded) / totalFileSize) * 100 )); 
	
	$("#averageFormSpeed").text( SWFUpload.speed.formatBPS(file.averageSpeed) );

}


//파일제거
function removeFileForm(){
	
	$("input:checkbox[name=fileFormId]:checked").each(function(i){
		
		if( $(this).attr("class") == 'oldFile' ) {
			
			var delImgIdxVal = "";
			var delFileNameVal = "";
			var file = $(this).val().split(":");
			if(confirm($(this).parent().parent().find('.fileList').text() + ' 은 업로드된 파일입니다. 삭제 하겠습니까?')){
				delFileIdx.push(file[0]);
				delFileName.push(file[1]);
				for(i = 0; i < delFileIdx.length; i++){
					delImgIdxVal += (i == 0) ? delFileIdx[i] : ":" + delFileIdx[i];
					delFileNameVal += (i == 0) ? delFileName[i] : ":" + delFileName[i];
				}
				$("#delFileIdx").val(delImgIdxVal);
				$("#delFileName").val(delFileNameVal);
				
				fileCount++;
				$(this).parent().parent().remove();
				swfForm.setFileUploadLimit(fileCount);
				swfForm.setButtonDisabled(false);
			}
		}else{
			swfForm.cancelUpload( $(this).val() );
		   	$("#fileFormContainer").find("#" + $(this).val()).remove();
		   	
		   	//파일 queue 
		   	$("#fileQuenFormCount").text( $("#fileQuenFormCount").text() - 1 );
		   	
		   	//파일 사이즈 총합
		   	totalFileSize = totalFileSize - eval($(this).parent().find("input:hidden[name=fileSize]").val());
		   	$("#totalFormFileSize").text(SWFUpload.speed.formatBytes(totalFileSize));		
		}
	   	
	}); 
}

function setOldFile(fileList){
	
	for(i = 0; i < fileList !=null && i < fileList.length; i++){
		
		var ext = "<img src='/images/board/ext_img/" + fileList[i].fileExt.toLowerCase() + ".gif' onerror=\"this.src='/images/board/ext_img/etc.gif'\" align='absmiddle'> ";
		var oldFile = ""
			+ "<div>"
			+ "	<div class='chkbox'><input type='checkbox' name='fileFormId' class='oldFile' value='" + fileList[i].idx + ":" + fileList[i].fileName + "'></div>"
			+"		<div class='fileList'>" + ext + fileList[i].fileRealName + "</div>"
			+"		<div class='progressbar' style='text-align:center;background-color:#F3F3F3;padding-bottom:2px'>업로드된 파일</div>"
			+"		<div class='fileStatus'>" + SWFUpload.speed.formatBytes(fileList[i].fileSize )+ "</div>"
			+ "</div>";
		
		$("#fileFormContainer").append(oldFile);
	}
	
}




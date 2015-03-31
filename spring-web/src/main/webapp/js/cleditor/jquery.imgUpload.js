(function($) {

	$.cleditor.buttons.imgUp = {    
		name: "imgUp",
		image: "btn01.gif",
		title: "이미지업로드",
		popupName: "imgUp",
		buttonClick: btnClick,
		popupClick: function(e, data) {
			 return false;
		}
	};

 
	$.cleditor.defaultOptions.controls = $.cleditor.defaultOptions.controls
    .replace("link", "imgUp link");

	  function btnClick(e, data) {
		 $(data.popup).width(0);
		 $(data.popup).height(0);
		 $(data.popup).css("border","1px solid #fff");
		 $(data.popup).css("margin-top","-50px");
		 $(data.popup).css("margin-left","-15px");
		 
		 if(swfu == null){
			 $("body").append("<div class='fileUploadModal' id='fileDialog'></div>");
			 $(data.popup).html("<span id='fileUploadBtn'></span>");
			 settings.upload_url = "/fileUpload?path=" + tmpPath;
			 swfu = new SWFUpload(settings);
		 }
		 
	  }
 
})(jQuery);

var swfu;
var imgMaxCount = 5;
var settings = {
		flash_url : "/js/swfupload/swfupload.swf",
		upload_url: "",
		file_size_limit : "2 MB",
		file_types : "*.jpg;*.gif;*.png;*.bmp",
		file_types_description : "Image Files",
		file_upload_limit : imgMaxCount,
		file_queue_limit : 0,

		debug: false,

		// Button settings
		button_image_url: "/images/button/swf_file_upload.gif",
		button_width: "80",
		button_height: "19",
		button_placeholder_id: "fileUploadBtn",
		
		moving_average_history_size: 40,
		
		// The event handler functions are defined in handlers.js
		file_dialog_start_handler : fileDialogStart,
		file_queued_handler : fileQueued,
		file_dialog_complete_handler: fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete
	};



var imgUpload = [];
//--------------------------------------------------------- 이미지 삽입
function addFile(file){
	
	var img = "";
	 $.each(file,function(i,val){
		img += "<p><img src='" + imagePath + "/" + val + "'><p>";
	});
	
	$("#content").cleditor()[0].execCommand("inserthtml", img, null,null);
}
  

//--------------------------------------------------------- 등록시 이미지 처리

var uploadImg;

function chkImg(){
	var editor = $("#content").cleditor()[0].$frame[0].contentWindow.document;
	var imgPath = "";
	var imgName = "";
	uploadImg = "";
	
	var k = 0;
	for(i = 0; i < editor.images.length;i++){
		imgPath = editor.images[i].src;
		if(imgPath.indexOf(imagePath) != -1){
		
			imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
			
			for(j = 0; j < fileName.length; j++){
				if(imgName == fileName[j]){
					uploadImg += (k == 0) ? imgName : ":" + imgName;
					k++;
					break;
				}	
			}
		}
		
	}
	return uploadImg;
}

//--------------------------------------------------------- 수정시 이미지 처리
var delImgIdx;
var delImgName;
function chkLoadedImg(){
	var editor = $("#content").cleditor()[0].$frame[0].contentWindow.document;
	var imgPath = "";
	var imgName = "";
	delImgIdx = "";
	delImgName = "";
	var k = 0
	
	for(j = 0; j < imgUploadName.length; j++){
		var chk = false;
		for(i = 0; i <editor.images.length;i++){
			imgPath = editor.images[i].src;
			if(imgPath.indexOf(upBoardFilePath) != -1){
				imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
				if(imgUploadName[j] == imgName){
					chk = true;
				}
			}
		}
		
		if(chk == false){
			delImgIdx += (k == 0) ? imgUploadIdx[j] : ":" + imgUploadIdx[j];
			delImgName += (k == 0) ? imgUploadName[j] : ":" + imgUploadName[j];
			k++;
		}
	}
	//alert(delImgIdx + ":" + delImgName)
	$("#delImgFileIdx").val(delImgIdx);
	$("#delImgFileName").val(delImgName);
	
}


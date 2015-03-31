//--------------------------------- trim() -----------------------------------------
function trim (str) {
	re = /^\s+|\s+$/g;

	return str.replace(re, '');
}

//--------------------------------- 숫자체크-----------------------------------------
function numChk(n){
	var reg = /^[-0-9]/g ;
	var num = n.replace(/,/g,'');
	return reg.test(n); 
}

//--------------------------------- 한글체크-----------------------------------------
function hanChk(Str) {
  var pattern = new RegExp('[^가-힣\x20]', 'i');
  if (pattern.exec(Str) != null) {
      return false ;
  } else {
      return true ;
  }
}

//--------------------------------- Object to String -----------------------------------
function JSONtoString(object) {
 	var results = [];
 	for (var property in object) {
		var value = object[property];
		if (value)
			results.push(property.toString() + ': ' + value);
	}
	return '{' + results.join(', ') + '}';
}


//--------------------------------- 통화-----------------------------------------
function currency(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n += '';                          				// 숫자를 문자열로 변환

	while (reg.test(n))
	n = n.replace(reg, '$1' + ',' + '$2');
	return n;
}

//--------------------------------- 문자치환-----------------------------------------
function replaceAll(inputStr, targetStr, replaceStr){
  var ret = null;
  var regExp = new RegExp(targetStr, "g");
  ret = inputStr.replace(regExp, replaceStr);
  
  return ret;
 }

//--------------------------------- 파일사이지-----------------------------------------
function getReadableFileSizeString(fileSizeInBytes) {

    var i = -1;
    var byteUnits = [' kB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
    do {
        fileSizeInBytes = fileSizeInBytes / 1024;
        i++;
    } while (fileSizeInBytes > 1024);

    return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
}


//--------------------------------- Copy To Clipboard-----------------------------------------
function copyToClipboard(src){
	if(window.clipboardData){
		clipboardData.setData("text",src);
	}else if(window.netscape){
		netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if(!clip) return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if(!trans) return;
		trans.addDataFlavor('text/unicode');
		var str = new Object(),len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		str.data=src;
		trans.setTransferData("text/unicode",str,src.length*2);
		var clipid=Components.interfaces.nsIClipboard;
		if(!clipid) return false;
		clip.setData(trans,null,clipid.kGlobalClipboard);
	}

}

//--------------------------------- TextArea Tab Use-----------------------------------------
function interceptTabs(evt, control)  {
	   key = evt.keyCode ? evt.keyCode : evt.which ? evt.which : evt.charCode;
	    if (key==9)  {
	        insertAtCursor(control, '\t');
	        return false;
	    } else {
	        return key;
	    }
 }

	function insertAtCursor(myField, myValue)  {
	    //IE support
	    if (document.selection){
	        myField.focus();
	        sel = document.selection.createRange();
	        sel.text = myValue;

		//MOZILLA/NETSCAPE support
	     } else if (myField.selectionStart || myField.selectionStart == '0') {
	        var startPos = myField.selectionStart;
	        var endPos = myField.selectionEnd;
	        restoreTop = myField.scrollTop;
	       
	        myField.value = myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length);
	       
	        myField.selectionStart = startPos + myValue.length;
	        myField.selectionEnd = startPos + myValue.length;
	       
	        if (restoreTop>0) {
	            myField.scrollTop = restoreTop;
	        }
	     } else {
	        myField.value += myValue;
	    }
 }

//--------------------------------- changeCaptcha-----------------------------------------
function changeCaptcha(ts){
	ts.src = "/Captcha?C" + Math.floor(Math.random() * 10000000) + 1;
}


//--------------------------------- ToolTip-----------------------------------------
function getToolTip(id,cnt, w, pos){
	$("#" + id).bt(cnt,{ 
		width : w,
		fill: '#FFFFCC', 
		strokeStyle: '#B7B7B7',
		spikeLength: 10,
		spikeGirth: 10,
		padding: 8,
		cornerRadius: 5, 
		cssStyles: {  fontSize: '9pt' },
		positions: pos
	});
}

//--------------------------------- Calendar-----------------------------------------
function getCalendar(id){
	
	/*
	new DatePicker(id, {
		format : 'Y/m/d',
		pickerClass: 'datepicker_dashboard',
		allowEmpty: true,
		positionOffset: {x: -5, y: -5},
		useFadeInOut: !Browser.ie
	});
	*/
	
	new Picker.Date(id, {
		pickerClass: 'datepicker_dashboard'
	});
	
}

//--------------------------------- 모달팝업(Jqm) Start -----------------------------------------
var modalCallback;
function closeModal(){
	$('#modalView').jqmHide();
}
function makeModal(data){

	var modalTemplate = ""
	    + "<div class=\"winModal\" id=\"modalView\"><div class='shadow1'><div class='shadow2'>	<div class='shadow3'><div class='container'>"
	    + "	  <div class=\"modalHeader\">"
	    + "		<div class=\"modalTitle\"></div>"
	    + "		<div class=\"modalClose\"><a href='javascript:closeModal()''><img src=\"/images/common/close.gif\" title=\"닫기\"></a></div>"
	    + "	   </div>"
	    + "	  <div class=\"modalTarget\"><iframe id=\"modalFrame\" scrolling=\"auto\" frameborder=\"0\" style=\"width:100%;\"></iframe></div>"
	    + "</div></div></div></div></div>";
	

	if ( $("#modalView").length > 0 ) {
		$("#modalView").remove();
	}
	$("body").append( modalTemplate );

	//html
	 if(data.type == 'html'){
		 $('#modalView')
		 		.jqDrag('.modalHeader')
		 		.jqm({
		 		overlay: 20, 
		 		modal: data.modal
		 	});
		 $('#modalView .modalTarget').html(data.html);
		 $('#modalView .modalTarget').css("line-height", "18px");
	 }
	
	//ajax
    if(data.type == 'ajax'){
    	$('#modalView')
    		.jqDrag('.modalHeader')
    		.jqm({
    		overlay: 20, 
    		target : $('#modalView .modalTarget'),
    		ajax: data.url,
    		modal: data.modal
    	});
	}
  //iframe
	if(data.type == 'iframe'){
    	$('#modalView')
    		.jqDrag('.modalHeader')
    		.jqm({
    		overlay: 20, 
    		modal: data.modal
    	});
		
		$('#modalView .modalTarget iframe').attr("src",data.url);
		$('#modalView .modalTarget iframe').css("height",data.height - 40);
	}
	
	//callback
	modalCallback = (typeof(data.callback) != undefined)?  data.callback : "";

	//header
	if(data.header == false){
		$("#modalView .modalHeader").remove()
	}
	
    var left  =($(window).width() - data.width)/2; 
    var top =($(window).height()- data.height)/2; 
	if(top < 0) top = 0;
	if(left < 0) left = 0;

	$("#modalView").css("width", data.width);
	$("#modalView").css("height",data.height);
	$("#modalView").css("top",top);
	$("#modalView").css("left",left);
	$("#modalView .container").css("height",data.height - 12);
	$("#modalView .modalTitle").html(data.title);
	
	$('#modalView').jqmShow();
	
}
//--------------------------------- 모달팝업(Jqm) End -----------------------------------------

//--------------------------------- Modal (jQuery UI ) Start -----------------------------------------
function alertDialog(title,msg,callback){

	if( $("#dialogMsg").length  == 0){
		$("body").append("<div id='dialog' title='' style='display:none;'><div id='dialogMsg'></div></div>");
	}

	$('#dialogMsg').html(msg);

	$('#dialog').dialog({
		resizable : false,
		autoOpen: false,
		width: 250,
		height:150,
		title : "<span class='ui-icon ui-icon-alert' style='float:left;margin-right:5px'></span><span>" + title + "</span>",
		buttons: {
			"확인": callback
		},
		modal : true
	});

	$('#dialog').dialog('open');
	$('#dialogMsg').css({'text-align':'center','padding-top':'20px'});


	$("body").focus();

}

function cfDialog(title,msg,callback){

	if( $("#dialogMsg").length  == 0){
		$("body").append("<div id='dialog' title='' style='display:none;'><div id='dialogMsg'></div></div>");
	}

	$('#dialogMsg').html(msg);

	$('#dialog').dialog({
		resizable : false,
		autoOpen: false,
		width: 250,
		height:150,
		title : "<span class='ui-icon ui-icon-circle-check' style='float:left;margin-right:5px'></span><span>" + title + "</span>",
		buttons: {

			"확인": callback,
		
			"닫기": function() { 
				$(this).dialog("close"); 
			} 

			
		},
		modal : true
	});

	$('#dialog').dialog('open');
	$('#dialogMsg').css({'text-align':'center','padding-top':'20px'});
	$("body").focus();

}


function regDialog(id,title,width,height,callback){

	$('#' + id).dialog({
		autoOpen: false,
		width: width,
		height:height,
		title : "<span class='ui-icon ui-icon-circle-plus' style='float:left;margin-right:5px'></span><span>" + title + "</span>",
		buttons: {

			"확인": callback,
		
			"닫기": function() { 
				$(this).dialog("close"); 
			} 
		},
		modal : true
	});

	$('#' + id).dialog('open');
	$("body").focus();

}
//--------------------------------- Modal (jQuery UI ) End -----------------------------------------


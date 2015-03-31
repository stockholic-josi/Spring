<html>
<head>	
		
<script type="text/javascript" src="/js/jquery.filestyle.js"></script>

<script>

$(function() {
    $("input[type=file]").filestyle({ 
         image: "/images/button/btn_sub_borwser.gif",
         imageheight : 19,
         imagewidth : 79,
         width : 220
     });
});


function send(){
	//parent.$('#modalView').jqmHide();
	
	if( !$("input[type=file]").val() ){
		
		alertDialog("알림","파일을 선택해 주십시요",function(){
			$("#dialog").dialog("close"); 
		});
		
		return;
	}
	
	document.frm.submit();
}


function sendResult(msg){

	if(msg == null) return;
	
	if(msg > 0){
		alertDialog("알림","정상적으로 등록되었습니다",function(){
			$("#dialog").dialog("close"); 
		});
		
	}else{
		alertDialog("알림","등록에 실패하였습니다",function(){
			$("#dialog").dialog("close"); 
		});
	}
	
}

$(document).ready(function () {
	sendResult($!result)
});

</script>


</head>

<body>
<div style="margin-top:20px;margin-left:20px">
	※ 파일형식 : 종목코드 | 종목명 | 구분 (01:코스피, 02:코스닥)
	
</div>
 
<form  action='./kospiExcelUpload.do' method="post" name="frm" id="frm" enctype="multipart/form-data">
<div style="margin-top:20px;margin-left:20px;float:left">
	<input type='file' size='25' name='file' class="inputBox" >
</div>
</form>

<div style="margin-top:20px;margin-left:330px;"></div>
</body>

</html>
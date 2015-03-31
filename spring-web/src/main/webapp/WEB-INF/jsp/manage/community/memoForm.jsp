<html>
<head>

<script type="text/javascript" src="/js/cleditor/jquery.cleditor.js"></script>
<link rel="stylesheet" type="text/css" href="/js/cleditor/jquery.cleditor.css" />
<link rel="stylesheet" href="/css/memo.css" type="text/css">

<script>

function check(){

	if( !trim( $("#content").val() ) ){
		
		alertDialog("알림","내용을 입력해주세요",function(){
			$("#dialog").dialog("close"); 
		});
		
		return;
	}
	
	jQuery.ajax({
		url: 'memoInsert.do',
		type: 'POST',
		data: $("#frm").serialize(),
		timeout : 3000,
		error: function(){
			alertDialog("알림","저장에 실패하였습니다.",function(){
			$("#dialog").dialog("close"); 
		});
		},
		success: function(msg){
		
			if( !$("#seqNo").val() ){
				parent.location.href="/manage/community/memo/memoList.do";
			}else{
				parent.locaton.reload();
			}
			
			parent.closeModal();
		}
	});
	
}


$(document).ready(function() {
	$("#content").cleditor({width:'100%', height:"100%"}).focus();
 });

</script>


</head>


<body>
	
<div id="wrapper">
	<form name="frm" id="frm">
	<div id="contentWrap"><textarea name="content" id="content">$!dto.content</textarea></div>
	<input type="hidden" name="seqNo" value="$!dto.seqNo">
	</form>
	
	<div style="margin-top:10px;float:left">
		<button class="word2" onClick="check()">등록</button>
		<button class="word2" onClick="parent.closeModal()">닫기</button> 
	</div>
	
	
</div>


</body>
</html>
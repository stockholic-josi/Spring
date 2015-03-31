<html>
<head>

<script language="javascript" type="text/javascript" src="/js/modal/jqModal.js"></script>
<script language="javascript" type="text/javascript" src="/js/modal/jqDnR.js"></script> 
<link rel="stylesheet" href="/css/memo.css" type="text/css">
<link rel="stylesheet" href="/css/modal.css" type="text/css">

<script>

function memoWrite(){
	makeModal({
		title : "메모등록",
		type : "iframe",
		url : "./memoForm.do",
		width : 820,
		height : 600,
		modal : true
	})
}

function memoModify(seqNo){
	makeModal({
		title : "메모등록",
		type : "iframe",
		url : "./memoForm.do?seqNo=" + seqNo ,
		width : 820,
		height : 600,
		modal : true
	})
	
	

}

function memoDelete(seqNo){
	
	cfDialog("확인","삭제하시겠습니까.",function(){
		$("#dialog").dialog("close"); 
		location.href = "./memoDelete.do?seqNo=" + seqNo;
	});
}


function search(){
	if(trim(jQuery('#searchString').val()).length >= 2 ){
		document.frm.submit();
	}
}
</script>


</head>


<body>
	
<div id="wrapper">
	<div class="title">Community > Memo</div>
	
	<div style="width:802px;">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr>
			<td width="300">
    			<form name="frm" id="frm" action="./memoList.do" method="get" onSubmit="search();return false">
    			<input type="text" name="searchString" id="searchString" value="$!searchString" class="inputBox" style="ime-mode:active;width:300px">
				</form>
			</td>
			<td width="100"><a href="javascript:search()"><img src="/images/common/search_02.gif" align="absmidle"></a></td>
			<td>Total : $!dto.total [  $!dto.p / $!dto.totalPage ]</td>
			<td align="right">
				<button class="word2" onClick="location.href='memoList.do'">전체</button>
				<button class="word2" onClick="memoWrite()">등록</button>
			</td>
		</tr>
		</table>
		
	</div>
	
	<div id="memoList">
		
		#foreach($list in $memoList)
		<div class="header">
			<div class="date">$!list.regDate</div>
			<div class="action">
				<a href="javascript:memoModify('$!list.seqNo')"><img src="/images/board/memo_mo.gif"></a>
				<a href="javascript:memoDelete('$!list.seqNo')"><img src="/images/board/memo_del.gif"></a>
			</div>
		</div>
		<div class="content">
			$!list.content
		</div>
		#end	
		
		#if($dto.total == 0)
		<div class="content" style="text-align:center;height:40px;padding-top:20px">자료가 없습니다.</div>	
		#end
	</div>
	
	 <div id="memoPage">
    #if($dto.total != 0)
    #set($searchParam = "&searchString=$!dto.searchString")
    $!StringUtil.paging($dto.totalPage,$dto.p,$searchParam,'memoList.do')
    #end
    </div>

</div>

 <div id='memoForm' style="display: none">
<iframe  src="./memoForm.do" id="memoFrame" scrolling="no" frameborder="0" style="width:100%;height:100"></iframe>
</div>


</body>
</html>
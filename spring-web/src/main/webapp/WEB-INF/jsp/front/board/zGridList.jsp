<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shkr" uri="/WEB-INF/tld/Shkr.tld" %>
	
<script type="text/javascript" src="/js/zGrid/zGrid-core.js"></script>
<script type="text/javascript" src="/js/zGrid/zGrid-util.js"></script>
<script type="text/javascript" src="/js/zGrid/jquery.mousewheel.js"></script>
<link rel="stylesheet" type="text/css" href="/js/zGrid/zGrid.css"/>

<script>

$(window).load(function(){

	$("#grid").zGrid({
		columns : [
			{columnId : "num", columnNm: "번호", width: 50, align:"center"},
			{columnId : "title", columnNm: "제목", width: 500, align:"left",sortable : true, renderer : titleRender},
			{columnId : "userNm", columnNm: "이름", width: 100, align:"center"},
			{columnId : "fileCount", columnNm: "파일", width: 50, align:"center",renderer : fileImg},
			{columnId : "regDate", columnNm: "등록일", width: 120, align:"center"}
		],
		gridHeight : 435,						//그리드 높이
		rowHeight :28,						//row 높이
		autoColumn :true,					//컬럼 너비 자동 조정
		stripeRows : true,						//로우구분 컬로 표시
		url : "/front/sqlite/boardJson.do",		//URL
		params :$("#frm").serializeObject(),
		paging : { limit : 15 ,pageSize : 10},	//paging
		page : ${dto.page}							//첫페이지
	}) ;
	
	
});

function strRepeat(str, num){
	var result = "";
	for(i = 0; i < num; i++){
		result += str; 
	}
	return result;
}	

function fileImg(value,record){
	return (value >  0) ? "<img src='/images/board/file.gif'>" : "";
}

function titleRender(value,record){
	var newImg = ( record.isNew == 1) ? "<img src='/images/board/new.gif' align='absmiddle'>" : ""; 	
	if(record.lev > 0) newImg += " " + strRepeat('&nbsp;&nbsp;&nbsp;',record.lev) + "<img src='/images/board/re.jpg' align='absmiddle'>";
	return newImg += " <a href=\"javascript:viewPost('" + record.no + "')\">" + value + "</a> ";
}


function viewPost(no){
	var params = {
		no : no,
		page : zGrid.util.getPage()
	}
	
	location.href = "./boardRead.do?" + $.param(params,true) + "&" + $("#frm").serialize();
}

function search() {
	 zGrid.setParam($("#frm").serializeObject());
	 zGrid.getGridData();
}

function addBoard(){
	
	var params = {
			page : zGrid.util.getPage()
		}
	
	location.href = "./boardForm.do?" + $.param(params,true) + "&" + $("#frm").serialize();
}

</script>


<div id="wrapper">
<div class="searchBox">
	<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<form name="frm" id="frm" onSubmit="search();return false">
		<td width="50%">
			<c:set var="key" value="title,content" />
	    	<c:set var="value" value="제목,내용" />
	    	<shkr:selectArray key="${fn:split(key, ',')}" value="${fn:split(value, ',')}" id="searchKey" selectKey="${dto.searchKey}" style="width:80px" />
			<input type="text" name="searchValue" value="${dto.searchValue}" class="inputBox" style="width:200px">
			&nbsp;<button class="word2">검색</button>
			<input type="hidden" name="flag" value="${dto.flag}">
		</td>
		</form>
		<td width="50%" align="right">
			<button class="word2" onClick="addBoard()">등록</button>
		</td>
	</tr>
	</table>
	
</div>

<div id="grid"></div>

</div>


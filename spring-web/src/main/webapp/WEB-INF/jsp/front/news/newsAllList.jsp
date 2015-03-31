<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/js/news/sortable.css" type="text/css">

<script>

var start = 0;
var rowCount = ${rowCount};
var searchCount = ${searchCnt};

function setMenu(itemId){
	if($("#itemMenu").find("." + itemId).attr("checked") == true){
		 $("#itemMenu").find("." + itemId).parent().css("color","#2B6AB9")
	}else{
		$("#itemMenu").find("." + itemId).parent().css("color","#B3B3B3")
	}
}


function lastPost(ts,start, limit, searchString,flag) { 

	var params = (searchString != "") ?  'start=' + start + '&limit=' + limit + '&searchString=' + searchString : 'start=' + start + '&limit=' + limit;
		 params = params + "&flag=" + flag;
	
	 $('#newsContent div:last').remove(); 
	 $("#newsContent").append("<div id='loading'><img src='/images/common/loading.gif'></div>");
	 
	 $.ajax({
		url: '/front/news/newsAllAjax.do',
		type : 'POST',
		data : params,
		timeout : 5000,

		error: function(){
			zpop.alert("데이터를 가져오지 못했습니다.");
			var lastCount = searchCount - start;
			$('#newsContent div:last').remove(); 
			$("#newsContent").append("<div id='loading'><a onClick='addPost(this)' class='hand'>기사 더 보기</a> ... " + currency(lastCount) + "</div>");
		},
		success: function(data){
			var dataVal;
			if(searchString != "") {
				dataVal = data;
				var arrSearch = trim($("#searchString").val()).split(" ");
				for(i = 0; i < arrSearch.length; i++ ){
				
					dataVal = replaceAll(dataVal,arrSearch[i],"<span style='background-color: #FFFF99'>" + arrSearch[i] + "</span>");
					if(i == 4) break;
				}
				
			}else{
				dataVal = data;
			}
			
			$('#newsContent div:last').remove(); 

			var lastCount = searchCount - start - rowCount;
			
			$("#newsContent").append(dataVal);
			if(lastCount > 0){
				$("#newsContent").append("<div id='loading'><a onClick='addPost(this)' class='hand'>기사 더 보기</a> ... " + currency(lastCount) + "</div>");
			}
			
		}
	});
	 
  
}; 

function addPost(ts){
	start = start +  rowCount;
	lastPost(ts,start, rowCount, $('#searchString').val(),'${flag}');
}


function search() {
	if(trim($('#searchString').val()).length >= 2 ){
		document.frm.submit();
	}
}

$(document).ready(function () {

	if( $("#searchString").val() != "") {
		var arrSearch = trim($("#searchString").val()).split(" ");
		var newsContent = $("#newsContent").html();
		for(i = 0; i < arrSearch.length; i++ ){
			newsContent = replaceAll(newsContent,arrSearch[i],"<span style='background-color: #FFFF99'>" + arrSearch[i] + "</span>");
			if(i == 4) break;
		}
		
		$("#newsContent").empty();
		$("#newsContent").append(newsContent);
	}
	
	$("#bottomNav").css("margin-left", $("#newsContent").width() + 5 );
	
	$('#bodyTop').click(function(){
		$('html, body').animate({scrollTop:0}, 'slow');
	});
	
});
</script>
<div id="wrapper">

	
	<div class="grayBox" style="width:875px;"> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr>
			<td width="300">
    			<form name="frm" id="frm" action="./newsAllList.do" method="get" onSubmit="search();return false">
    			<input type="text" name="searchString" id="searchString" value="${searchString}" class="inputBox" style="ime-mode:active;width:300px">
    			<input type="hidden" name="flag" value="${flag}">
				</form>
			</td>
			<td width="100"><a href="javascript:search()"><img src="/images/common/search_02.gif"></a></td>
			<td><c:if test="${searchString }">최근 1주일</c:if> 기사 수 : <span id="searchCnt"><fmt:formatNumber value="${searchCnt }" type="number"/> 개</span></td>
			<td align="right">
				<button class="word2" onClick="location.href='./newsAllList.do?flag=${flag}'">전체</button>
				<button class="word2" onClick="location.href='./newsList.do?flag=${flag}'">메인</button>
			</td>
		</tr>
		</table>
	</div>
	
	<div id="newsContent">
    	<div>
    	<c:forEach var="list" items="${newsList}" varStatus="status">
            <div class="newstitle"><a href="${list.link}" target="_blank">[${list.newsNm}]  ${list.title}</a>
        		<span class="newsPubdate">
        			${list.pubDate}
        			<c:choose>
				       <c:when test="${timeDiff == 0}">
				        - <b>1 분전</b>
				       </c:when>
				     <c:when test="${timeDiff > 0 &&  timeDiff < 60}">
				        - <b>${timeDiff} 분전</b>
				       </c:when>
				      <c:when test="${timeDiff > 60 &&  timeDiff < 1440}">
				        - <b><fmt:formatNumber  value="${timeDiff  / 60 }"  maxFractionDigits="0" /> 시간전</b>
				       </c:when>
   					</c:choose>
        		</span>
        	</div>
        	<div class="newsDescription">${fn:replace(list.description, newLineChar, "; ")}</div>
    	</c:forEach>
    	</div>
    	
		<c:choose>
	       <c:when test="${searchCnt == 0}">
	       	<div style="margin-bottom:50px;text-align:center;border">데이터가 없습니다.</div>
	       </c:when>
	      <c:when test="${searchCnt >  rowCount}">
	       	<div id="loading"><a onClick="addPost(this)" class="hand">기사 더 보기</a> ... <fmt:formatNumber value="${searchCnt - rowCount }" type="number"/></div>
	       </c:when>
		</c:choose>
	</div>
	
	<div id="bottomNav">
		<img src="/images/icon/icon_09.png" id="bodyTop" class="hand">
    </div>
		
</div>

	

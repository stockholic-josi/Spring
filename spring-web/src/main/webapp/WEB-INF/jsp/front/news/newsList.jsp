<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="/js/jquery.cookies.js"></script>
<script type="text/javascript" src="/js/news/sortable.js"></script>
<link rel="stylesheet" href="/js/news/sortable.css" type="text/css">


<script>

var newsData = ${newsData};

$(document).ready(function () {

	sortable.initItemList('${flag}');

	
});

</script>

	
<div id="wrapper">
	
	<div id="menuInit">
		 <a href="#" onClick="sortable.reSetIem();location.reload(); return false;" >초기화</a> / 
		 <a href="#" onClick="location.href='./newsAllList.do?flag=${flag}'" >전체보기</a>
	</div>
	
	<div class="grayBox" style="height:40px;line-height:20px;margin-bottom:10px;width:876px">
		<div id="itemMenu"></div>
	</div>
	
	<div style="width:900px">
        <div id="sort1" class="column"></div>
        <div id="sort2" class="column"></div>
        <div id="sort3" class="column"></div>
	</div>

</div>
	

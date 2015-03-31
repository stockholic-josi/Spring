<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<!DOCTYPE html>
<html>

<head>
	<title>::: shkr :::</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="/css/global.css" type="text/css">
	<link rel="stylesheet" href="/css/main.css" type="text/css">
	<link rel="stylesheet" href="/css/ui-darkness/jquery-ui-1.10.3.custom.min.css" type="text/css" />	
	<link rel="stylesheet" href="/js/zPop/zpop.css" type="text/css"/>	
	
	<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/js/jquery.layout-latest.min.js "></script>
	<script type="text/javascript" src="/js/common.js"></script>
	
	<script type="text/javascript" src="/js/tree/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="/js/tree/jquery.cookie.js"></script>
	<script type="text/javascript" src="/js/tree/jquery.jstree.js"></script>
	<script type="text/javascript" src="/js/tree/treeHandler.js"></script>
	<script type="text/javascript" src="/js/JSON.js"></script>
	<script type="text/javascript" src="/js/clock.js"></script>
	
	<script type="text/javascript" src="/js/zPop/zpop.js"></script>
	
	<!-- codemirror Start-->
    <link rel="stylesheet" href="/js/codemirror/lib/codemirror.css">
    <link rel="stylesheet" href="/js/codemirror/theme/mbo.css">
    <link rel="stylesheet" href="/js/codemirror/addon/dialog/dialog.css">
    	
    <script src="/js/codemirror/lib/codemirror.js"></script>
    <script src="/js/codemirror/mode/htmlmixed/htmlmixed.js"></script>
    
    <script src="/js/codemirror/addon/selection/active-line.js"></script>
    <script src="/js/codemirror/addon/edit/matchbrackets.js"></script>
    <script src="/js/codemirror/addon/dialog/dialog.js"></script>
    <script src="/js/codemirror/addon/search/searchcursor.js"></script>
    <script src="/js/codemirror/addon/search/search.js"></script>
    
    <script src="/js/codemirror/mode/xml/xml.js"></script>
    <script src="/js/codemirror/mode/css/css.js"></script>
    <script src="/js/codemirror/mode/clike/clike.js"></script>
    <script src="/js/codemirror/mode/javascript/javascript.js"></script>
	<!-- codemirror End-->

	<script type="text/javascript">

	var myLayout; // http://layout.jquery-dev.net/

	$(document).ready(function () {
		myLayout = $('body').layout({
				north__size:  25, 
				north__spacing_open : 0,
				west__size:      230,
				west__spacing_open : 5,

				south__size:  21,
				south__spacing_open : 0
		});
		
		realtimeClock();
		
		$( ".editorBtn" ).button({
			icons: {
				primary: "ui-icon-disk"
			}
		});
		
		$("#treeContent").treeHandler();
		
		
		
 	});
	</script>
</head>

<body>

<div class="ui-layout-north">
	<div id="header">
	
	<div class="user">${auth.userNm}</div>
	<div><a href="/logout" class="logOut"><img src="/images/icon/pop_close.gif" align="absmiddle"> 로그아웃</a></div>
	<div><button class='editorBtn'>저장</button></div>
	<div id="clock" class="clock"></div>
	</div>
</div>

<div class="ui-layout-west">
	<div id="treeContent"></div>
</div>


<div class="ui-layout-center">
</div>

<div class="ui-layout-south">
	taxHolic.com
</div>


</body>

</html>



 <%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zCodeGenerator :::</title>
<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookies.js"></script>

<link rel="stylesheet" href="/js/zPop/zpop.css" type="text/css"/>	
<script type="text/javascript" src="/js/zPop/zpop.js"></script>


<style type="text/css"> 
*{
	font-style: nomal;
	font-size:9pt;
    font-family: 맑은 고딕,굴림,돋움, Dotum, Gulim, Arial, sans-serif, Verdana, Helvetica, geneva;
}

#login { 
    width: 600px; /* 폭이나 높이가 일정해야 합니다. */ 
    height: 200px; /* 폭이나 높이가 일정해야 합니다. */ 
    position: absolute; 
    top: 20%; /* 화면의 중앙에 위치 */ 
    left: 50%; /* 화면의 중앙에 위치 */ 
    margin: -100px 0 0 -300px; /* 높이의 절반과 너비의 절반 만큼 margin 을 이용하여 조절 해 줍니다. */ 
} 

 .colsTitle 	{
	width:100px;
	text-align:right;
	background-color:#F9F9F9;
	font-weight: bold;
	color:#646464;
}
.btn{ height:30px;font-size: 12px;font-weight:bold;} 
.inputBox{border:1px solid #AFAFAF;height:18px;width:100%}
</style> 

<script type="text/javascript">

function chk(){
	var k = 0;
	
	if( $("input[name=dbType]:radio:checked").length == 0) {
		$("#error").html("<font color='#FF6633'><b>DB 선택 !!</b></font>");
		return false; 
	}
	
	$("input[type=text]").each(function (i) {

		if( $(this).val() == ''){
			$(this).css({border:"1px solid #FF9900"});
			$(this).focus();
			$("#error").html("<font color='#FF6633'><b>정보 입력 !!</b></font>");
			k++;
			return false;
		}else{
			$(this).css({border:"1px solid #AFAFAF"});
		}
	});
	
	return k == 0 ? true : false;
}

function changeDb(val){
	$("#schma").text( val == "oracle"? "SID" : "DB Name" )	
}

function testConnect(){
	
	if( chk() == false ) return;	
	
	var params = {
		dbType : $("input[name=dbType]:checked").val(),
		hostNm : $("#hostNm").val(),
		dbNm : $("#dbNm").val(),
		id : $("#id").val(),
		pw : $("#pw").val()
	}

	$.ajax({    
		url: "codeConTest.jsp",  
		type: "POST",
		data: $.param(params,true),
		error: function (e) {        
			$("#error").html("<font color='#FF6633'><b>Ajax error</b></font>");
		},    
		success: function(data) {  
			if(data == "success"){
				$("#error").html("<font color='#0000FF'><b>Connection Success</b></font>");
			}else{
				$("#error").html("<font color='#FF6633'><b>" + data + "</b></font>");
			}
		}
	});
	
}

function getColumn(flag){
	
	if( chk() == false ) return;
	
	var frm = document.vFrm;
	var loc1 = screen.availWidth;
	var loc2 = screen.availHeight;
	var h = 500;
	var w = 630;
	
	window.open("", "viewColumn", "status=0,menubar=0,scrollbars=yes,width="+ w +",height=" + h + ",align=left,left="+(loc1-w)/2+",top="+(loc2-h)/2);
	
	vFrm.type.value = $("input[name=dbType]:checked").val();
	vFrm.host.value = $("#hostNm").val();
	vFrm.db.value = $("#dbNm").val();
	vFrm.id.value = $("#id").val();
	vFrm.pw.value = $("#pw").val();
	vFrm.tb.value = $("#tbNm").val();
	vFrm.flag.value = flag;
	
	vFrm.submit();
	$("#error").empty();
}


function codeZen(){
	var frm = document.frm;
	
	if( chk() == true ){
		
		if( $("input[name=keyColumn]").val() == undefined){
			$("#error").html("<font color='#FF6633'><b>Key Setting 입력 !!</b></font>");
			return;
		}
		
		frm.submit();
	}	
	
}
</script> 
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="login">

<form name="frm" method="post" action="codeZenPrc.jsp">
<table  border="1" cellspacing="0" cellpadding="6" align='center' style="border-collapse:collapse; border:1px gray solid;width:500px">
<tr>
	<td class="colsTitle">DB Type</td>
	<td>
		<input type="radio" name="dbType" value="mysql"  onClick="changeDb(this.value)" checked> MySql
		<input type="radio" name="dbType" value="oracle" onClick="changeDb(this.value)"> Oracle
	</td>
</tr>
<tr>
	<td class="colsTitle">Host:Port</td>
	<td><input type="text" name="hostNm"  id="hostNm" class="inputBox" value="127.0.0.1:3306"></td>
</tr>
<tr>
	<td class="colsTitle" id="schma">DB Name</td>
	<td><input type="text" name="dbNm"  id="dbNm" class="inputBox" value="stock"></td>
</tr>
<tr>
	<td class="colsTitle">ID / PW</td>
	<td>
		<input type="text" name="id"  id="id" class="inputBox" style="width:120px" value="shkr"> / 
		<input type="text" name="pw"  id="pw" class="inputBox" style="width:120px" value="Wkwkdaus">
	</td>
</tr>
<tr>
	<td class="colsTitle">Table Name</td>
	<td><input type="text" name="tbNm"  id="tbNm" class="inputBox" value="sk_news_stock"></td>
</tr>
<tr>
	<td class="colsTitle">Package Name</td>
	<td><input type="text" name="pkgNm"  id="pkgNm" class="inputBox" value="com.taxkholic.front.test"></td>	
</tr>
<tr>
	<td class="colsTitle">Class Name</td>
	<td><input type="text" name="classNm"  id="classNm" class="inputBox" value="Test"></td>
</tr>
<tr>
	<td class="colsTitle">Velocity Path</td>
	<td><input type="text" name="vmNmPath"  id="vmNmPath" class="inputBox" value="/front/test"></td>
</tr>
<tr>
	<td class="colsTitle" colspan="2"  style="text-align:center">Option</td>
</tr>
<tr>
	<td class="colsTitle">Key Setting</td>
	<td id="keyColumn"><a href="javascript:getColumn('key')">View Column</a></td>
</tr>
<tr>
	<td class="colsTitle">View Setting</td>
	<td id="viewColumn"><a href="javascript:getColumn('view')">View Column</a></td>
</tr>
<tr>
	<td class="colsTitle">Form Setting</td>
	<td id="formColumn"><a href="javascript:getColumn('form')">View Column</a></td>
</tr>
</table>


</form>

<p align="center">
<input type="button"  value="Test Connect" class="btn" style="width:150px" onClick="testConnect()"> 
<input type="button"  value="Code Generator" class="btn" style="width:200px" onClick="codeZen()">
</p>

<table align='center'>
<tr><td id="error"></td></tr>
</table>
  
	
</div>

<form name="vFrm" action="codeViewColumn.jsp"  method="post" target="viewColumn" >
<input type="hidden" name="type">
<input type="hidden" name="host">
<input type="hidden" name="db">
<input type="hidden" name="id">
<input type="hidden" name="pw">
<input type="hidden" name="tb">
<input type="hidden" name="flag">
</form>


</body>
</html>
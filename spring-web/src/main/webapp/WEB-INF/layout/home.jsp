<%@page import="org.tuckey.web.filters.urlrewrite.SetAttribute"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.taxholic.core.authority.AuthUtil" %>
<%@ page import="com.taxholic.core.authority.AuthDto" %>
<%
String isLogin = AuthUtil.getPrincipal();
request.setAttribute("isLogin",isLogin);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><decorator:title /></title>
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon"/>
<link rel="icon" href="/images/favicon.ico" type="image/x-icon" />
<meta name="keywords" content="tax,holic,TtockHolic,rss,news,rss news,뉴스,증권,주식,선물,차트,주식 뉴스,주식 매매,팁과노하우,주식 차트,주식 관리,주식 포트,주식 포트 관리,모의 투자" /> 
<meta name='description' content='주식 선물 매매 RSS 뉴스 정보 사이트 입니다.' />

<script type="text/javascript" src="/js/comm.js"></script>
<script type="text/javascript" src="/js/jquery-1.4.3.min.js"></script>

<script language="javascript" type="text/javascript" src="/js/modal/jqModal.js"></script>
<script language="javascript" type="text/javascript" src="/js/modal/jqDnR.js"></script>

<script language="javascript" type="text/javascript" src="/js/msdropdown/jquery.dd.js"></script>

<script language="javascript" type="text/javascript" src="/js/jquery.vticker.1.4.js"></script>

<!-- ToolTip -->
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="/js/flot/excanvas.min.js"></script><![endif]-->
<script src="/js/tooltip/jquery.bt.min.js" type="text/javascript" charset="utf-8"></script>

<decorator:head/>

<link rel="stylesheet" href="/css/home.css" type="text/css">
<link rel="stylesheet" href="/js/msdropdown/dd.css" type="text/css">

<script>
$(document).ready(function() {
	
	$(".headerInfo").html($("#headerInfo").html());
	
	<!-- Ticker -->
	/*
	$('.stockTicker').vTicker({
		speed: 500,
		pause: 3000,
		showItems: 1,
		animation: 'fade'
		,mousePause: false
		,height: 0,
		direction: 'up'
	});
	*/
	
	$(".mtp").each(function (i){
		getToolTip( $(this).attr("id"), $(this).attr("title") , $(this).attr("tipWidth"),'bottom' )
	});
	
	try{
		  $("body select").msDropDown();
		}catch(e){}
})
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

	<div id="wrap">
		<!-- Head Start-->
		<div id="header">
			<div class="headerCnt">
				<div class="logo">
					taxHolic
					<h1 style="display:none">stock,holic,taxHolic,rss,news,rss news,뉴스,증권,주식,선물,차트,주식 뉴스,주식 매매,팁과노하우,주식 차트,주식 관리,주식 포트,주식 포트 관리,모의 투자</h1>
				</div>
				<div class="headerInfo"></div>
				<div class="menuBox">
					<span><a href="/" class="mtp" id="mtoolTip1" title="홈이동" tipWidth="40" target="_top">Home</a></span>
					<span ><a href="/rssNews/stock" class="mtp" id="mtoolTip2" title="RSS 뉴스" tipWidth="50" target="_top">Rss News</a></span>
					<span ><a href="/stockChart/INDU" class="mtp" id="mtoolTip9" title="주식차트" tipWidth="50" target="_top">Chart</a></span>
					<span><a href="/tip/list/03" class="mtp" id="mtoolTip3" title="커뮤니티" tipWidth="50" target="_top">Community</a></span>
					<!-- 
					<span>
						<c:if test="${isLogin eq 'GUS' }"><a href="/user/login.do?redirect=/main.do" class="mtp" id="mtoolTip4" title="주식관리" tipWidth="50" target="_top">My Stock</a></c:if>
						<c:if test="${isLogin ne 'GUS' }"><a href="/main.do" target="_blank" class="mtp" id="mtoolTip4" title="주식관리" tipWidth="50" target="_top">My Stock</a></c:if> 
					</span>
					 -->
					 <span>
						<a href="#"  class="mtp" id="mtoolTip4" title="주식관리" tipWidth="50">My Stock</a>
					</span>
					<c:if test="${isLogin eq 'GUS' }">
					<span><a href="/home/user/join.do" class="mtp" id="mtoolTip5" title="회원가입" tipWidth="50" target="_top">Join</a></span>
					<span><a href="/user/login.do" class="mtp" id="mtoolTip6" title="로그인" tipWidth="40" target="_top">Login</a></span>
					</c:if>
					<c:if test="${isLogin ne 'GUS' }">
					<span><a href="/home/user/userInfo.do" class="mtp" id="mtoolTi7" title="회원정보" tipWidth="50" target="_top">Member Info</a></span>
					<span><a href="/logout" class="mtp" id="mtoolTi8" title="로그아웃" tipWidth="50" target="_top">LogOut</a></span>
					</c:if> 
				</div>
			</div>
		</div>
		<!-- Head End-->

		<!-- Body Start-->
		<div id="container">
			<decorator:body />
		</div>
		<!-- Body End-->

		<!-- Footer Start-->
		<div id="footer">
			<div class="footerWrap">
				<div class="copyRights">
					
				</div>
				
				<div class="Privacy">
					© 2011 TaxHolic.com All rights reserved.&nbsp;&nbsp;&nbsp;&nbsp;
					이용약관 | 개인정보취급방침 | 이메일주소무단수집거부
				</div>
			</div>
		</div>
		<!-- Footer End-->
	</div>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>:: Spring ::</title>

<link rel="stylesheet" href="/css/global.css" type="text/css" />
<link type="text/css" href="/css/redmond/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" />	
<link rel="stylesheet" href="/js/zPop/zpop.css" type="text/css"/>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/jquery.cookies.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="/js/zPop/zpop.js"></script>

<!-- Calendar -->
<script src="/js/datepicker/mootools-core.js" type="text/javascript"></script>
<script src="/js/datepicker/mootools-more.js" type="text/javascript"></script>
<script src="/js/datepicker/Picker.js" type="text/javascript"></script>
<script src="/js/datepicker/Picker.Attach.js" type="text/javascript"></script>
<script src="/js/datepicker/Picker.Date.js" type="text/javascript"></script>
<link href="/js/datepicker/datepicker_dashboard.css" rel="stylesheet">



<script>
$(document).ready(function () {

	$( "button" ).button({
		icons: {
			primary: "ui-icon-document"
		}
	});
	
});
</script>
</head>

<body>
	<tiles:insertAttribute name="body" />
</body>

</html>
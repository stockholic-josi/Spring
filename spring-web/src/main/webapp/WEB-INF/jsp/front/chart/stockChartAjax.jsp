<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<div style="margin-bottom:50px">
	<a href="http://stockcharts.com/h-sc/ui?s=%24${flag}" target="_blank"><img src="/data/chart/<fmt:formatDate  value="${now}" pattern="yyyyMMdd" />/${flag}1.png" onerror="this.src='/images/icon/icon_13.png'"></a>
</div>
<div><img src="/data/chart/<fmt:formatDate  value="${now}" pattern="yyyyMMdd" />/${flag}2.png" onerror="this.src='/images/icon/icon_13.png'"></div>
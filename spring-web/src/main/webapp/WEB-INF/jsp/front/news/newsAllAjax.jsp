<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<%@ page contentType="text/html; charset=UTF-8" %>
	
<script language="JavaScript">


	$(function() {
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"파일을 찾을 수 없습니다." );
				}
			}
		});
	});
</script>


<div style="margin-bottom:20px">출처 : <a href="http://stockcharts.com" target="_blank">http://stockcharts.com</a></div>

<div id="tabs">
	
	<ul>
		<li><a href="/front/chart/stockChartAjax.do?flag=INDU&chartNm=다우지수">다우지수(INDU)</a></li>
		<li><a href="/front/chart/stockChartAjax.do?flag=DAX&chartNm=독일지수">독일지수(DAX)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=NIKK&chartNm=일본지수">일본지수(NIKK)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=SSEC&chartNm=상해종합지수">상해종합지수(SSEC)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=HSI&chartNm=홍콩항생지수">홍콩항생지수(HSI)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=TWII&chartNm=대만지수">대만지수(TWII)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=USD&chartNm=달러인덱스">달러인덱스(USD)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=SOX&chartNm=필아델피아반도체">필아델피아반도체(SOX)</a></li> 
		<li><a href="/front/chart/stockChartAjax.do?flag=WWK&chartNm=MSCI Korea Index">MSCI Korea Index (WWK)</a></li> 
		
		<li><a href="/front/chart/stockChartAjax.do?flag=WTIC&chartNm=원유" target="main">원유(WTIC)</a></li> 
    	<li><a href="/front/chart/stockChartAjax.do?flag=GOLD&chartNm=금" target="main">금(GOLD)</a></li> 
    	<li><a href="/front/chart/stockChartAjax.do?flag=BDI&chartNm=BDI" target="main">BDI</a></li> 
	</ul>

</div>


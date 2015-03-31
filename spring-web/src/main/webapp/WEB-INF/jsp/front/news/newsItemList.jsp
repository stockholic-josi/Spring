<%@ page contentType="text/html; charset=UTF-8" %>

<link rel="stylesheet" href="/js/sortable/sortable.css" type="text/css">


<script>

var start = 0;
var rowCount = $!rowCount;
var searchCount = $!searchCnt;

function lastPost(ts,start, limit, searchString,flag) { 

	var params = (searchString != "") ?  'start=' + start + '&limit=' + limit + '&searchString=' + searchString : 'start=' + start + '&limit=' + limit;
		 params = params + "&flag=" + flag;
	
	 $('#newsContent div:last').remove(); 
	 $("#newsContent").append("<div id='loading'><img src='/images/common/loading.gif'></div>");
	 
	 $.ajax({
		url: '/news/newsAllAjax.do',
		type : 'POST',
		data : params,
		timeout : 5000,

		error: function(){
			Ext.MessageBox.alert('알림', '데이터를 가져오지 못했습니다.');
			var lastCount = searchCount - start;
			$('#newsContent div:last').remove(); 
			$("#newsContent").append("<div id='loading'><a onClick='addPost(this)' class='hand'>기사 더 보기</a> ... " + currency(lastCount) + "</div>");
		},
		success: function(data){
			var dataVal;
			if(searchString != "") {
				if( trim(searchString) < 2 ) return;
			
				dataVal = data;
				dataVal = replaceAll(dataVal,searchString,"<span style='background-color: #FFFF99'>" + searchString + "</span>");
				
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
	lastPost(ts,start, rowCount, jQuery('#searchString').val(),'$!flag');
}


$(document).ready(function () {
	var searchString = $("#searchString").val();
	
	if( trim(searchString) < 2 ) return;
	
	var newsContent = $("#newsContent").html();
	newsContent = replaceAll(newsContent,'$!searchString',"<span style='background-color: #FFFF99'>$!searchString</span>");
	
	$("#newsContent").empty();
	$("#newsContent").append(newsContent);
	
	$("#bottomNav").css("margin-left", $("#newsContent").width() + 5 );
	
	$('#bodyTop').click(function(){
		$('html, body').animate({scrollTop:0}, 'slow');
	});
	
});
</script>

<style>
	#newsContent{width:98%}
	#loading{width:98%}
</style>

</head>



<div id="wrapper">
	
	<input type="hidden" name="searchStrng" id="searchString" value="$!searchString">
	<div style="font-weight:bold">기사 수 : <span id="searchCnt">$number.format('integer',$searchCnt) 개</span></div>
	<div id="newsContent">
    	<div>
    	#foreach($list in $newsList)
            <div class="newstitle"><a href="$!list.link" target="_blank">[$!list.newsNm]  $!list.title</a>
        		<span class="newsPubdate">
        			$!list.pubDate
        			
        			#set ($timeDiff = $list.timeDiff)
        			#if($timeDiff == 0)
        				- <b>1 분전</b>
        			#elseif($timeDiff > 0 &&  $timeDiff < 60)
        				- <b>$timeDiff 분전</b>
        			#elseif($timeDiff > 60 &&  $timeDiff < 1440)
        				- <b>$StringUtil.getRoundInt($timeDiff,60)  시간전</b>
        			#end
        		</span>
        	</div>
        	<div class="newsDescription">$StringUtil.lineBreak($!list.description)</div>
    	#end
    		
    	</div>
		
   	#if($searchCnt == 0)
    	<div style="margin-bottom:50px;text-align:center;border">데이터가 없습니다.</div>
   	#elseif($searchCnt > $rowCount)
		#set($lastCount = $searchCnt - $rowCount )
    	<div id="loading"><a onClick="addPost(this)" class="hand">기사 더 보기</a> ... $number.format('integer',$lastCount)</div>
    #end	
    		
	</div>
	
	<div id="bottomNav">
		<img src="/images/icon/icon_09.png" id="bodyTop" class="hand" title="위로">
    </div>
	

		
</div>
	

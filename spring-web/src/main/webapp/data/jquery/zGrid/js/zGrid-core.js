(function( $ ){
	/*
	* Grid init
	*/
	
	$.fn.zGrid = function(data) {
		zGrid.$grid = this;

		$.each(data,function(key, value){
			zGrid.initData[key] = value;
		})

		zGrid.initGrid();

	};

	/*
	* form to Json
	*/
	$.fn.serializeObject = function(){
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	/*
	* 스크롤바 존재여부 체크 있으면 true
	*/
	$.fn.hasScrollBar = function() {
		return this.get(0).scrollHeight > this.innerHeight();
	}


})( jQuery );


var zGrid = {

	$grid : null,
	gridWidth : 0,
	$gridWrap : null,
	dragging : false,
	rowCount : 0,
	sort : {},

	initData : {
		store : {},								//그리드 데이터
		params : {},								//파라미터
		gridHeight : 400,						//그리드 높이
		rowHeight : 22,						//row 높이
		autoColumn : true,					//컬럼 너비 자동 조정
		stripeRows : true,						//로우구분 컬로 표시
		paging : {limit:0,pageSize:10},	//페이징
		page : 1									//첫 페이지
	},


	//그리드 초기화
	initGrid : function(){

		zGrid.drawGrid();
		
		$('#zGridWrap .dragbar').each(function(i){

			$(this).mousedown(function(e){
			   e.preventDefault();
			   zGrid.dragging = true;

			   zGrid.$gridWrap = $(this).parent().parent().parent();		//선택된 그리드  header , body

			   $headerColumn1 = $(this).parent();
			   $headerColumn2 =  $(this).parent().next();
			   if($headerColumn2.length == 0) return;

			   var ghostbar = $("<div>",
									{id:'ghostbar',
										css: {
											height: $('#headerWrap').height() + $('#dataWrap').height(),
											top: $(this).offset().top,
											left: $(this).offset().left
										}
									}).appendTo('body');
			   
			 $(document).mousemove(function(e){
					ghostbar.css("left",e.pageX);
			   });
			});
		});

		$(document).mouseup(function(e){


			$('#ghostbar').remove();

			   if (zGrid.dragging)  {

				   if($headerColumn2.length == 0) return;

					var minWidth = 20;

					var colWidth1 = (e.pageX  - $headerColumn1.offset().left < minWidth ) ? minWidth : e.pageX - $headerColumn1.offset().left ;
					var colWidth2 = 0;
					if( $headerColumn2.offset().left + $headerColumn2.width() - e.pageX < minWidth){
						colWidth2 = minWidth;
						colWidth1 = $headerColumn2.width() + $headerColumn1.width() - colWidth2
					}else{
						colWidth2 = $headerColumn2.width() + $headerColumn1.width() - colWidth1;
					}

					var headerWidth = [];


					zGrid.$gridWrap.children().eq(0).find(".column").each(function(i){

						if( $(this).get(0) == $headerColumn1.get(0) ){
							headerWidth.push( colWidth1 )
						}else  if( $(this).get(0) == $headerColumn2.get(0)){
							headerWidth.push( colWidth2 )
						}else{
							headerWidth.push( $(this).width() )
						}
					 });

					zGrid.setColumnWidth(headerWidth,zGrid.$gridWrap.children().eq(0),zGrid.$gridWrap.children().eq(1));
					
				   $(document).unbind('mousemove');
				   zGrid.dragging = false;
			   }
		  });
		   	
	},

	//그리드 생성
	drawGrid : function(){

		zGrid.$grid.append("<div id='zGridFixWrap' style='float:left'><div id='headerFixWrap''></div><div id='dataFixWrap' class='zData'></div></div>");
		zGrid.$grid.append("<div id='zGridWrap'></div>");
		zGrid.$grid.append("<div id='paging'><div class='total'><span></span></div><div class='nav'></div></div>");
		zGrid.$grid.find("#zGridWrap").append("<div id='headerWrap'></div>");
		zGrid.$grid.find("#zGridWrap").append("<div id='dataWrap' class='zData'></div>");
		zGrid.$grid.find("#zGridWrap").append("<div id='zGridFin'></div>");
		zGrid.$grid.find("#zGridWrap").append("<div id='zGridFinFix'></div>");

		//그리드 높이
		zGrid.$grid.find("#dataWrap").height(zGrid.initData.gridHeight);

		//checkbox
		if( zGrid.initData.checkbox ) zGrid.initData.columns.unshift(zGrid.initData.checkbox);
		
		var gridWidth = zGrid.$grid.width();
		zGrid.gridWidth = gridWidth;
		

		// Body 생성
		var coulmmCount = zGrid.initData.columns.length;
		$.each(zGrid.initData.columns,function(i){
			// Header 생성
			var bar = ( i <  coulmmCount - 1) ?  "<div class='dragbar'></div>" : "";
			var columnNm = ($(this)[0].columnNm == undefined) ? "" :  $(this)[0].columnNm ;
			zGrid.$grid.find("#headerWrap").append("<div cols='" + i + "' class='column'>" + bar +  "<div class='header'>" + columnNm + " <span class='sort'></span></div></div>");

			// Data body 생성
			zGrid.$grid.find("#dataWrap").append("<div cols='" + i + "' class='column'></div>");
		});

		//컬럼 너비 조정
		var colWidth = [];
		$.each(zGrid.initData.columns,function(i){
			colWidth.push($(this)[0].width);
		});
		zGrid.setColumnWidth(colWidth,$("#headerWrap"),$("#dataWrap"));

		//checkbox
		if( zGrid.initData.checkbox )  $(zGrid.$grid.find("#headerWrap div .header")[0]).html("<input type='checkbox'>");
		zGrid.$grid.find("#headerWrap div .header input").bind("click",function(){
			var checked = $(this).prop("checked");
			zGrid.$grid.find(".zData").children().eq(0).find("input[type=checkbox]").prop("checked", checked);
			$.each(zGrid.initData.store.dataList,function(i){
				$(this)[0].checked  = checked
			});

			zGrid.$grid.find(".zData .cell").css("background-color", checked==true ? "#dfe8f6" : "")
		});

		//Header 속성 셋팅
		var $columns = zGrid.$grid.find("#headerWrap .column");
		$columns.find(".header").each(function(i){
			if( zGrid.initData.columns[i].sortable  == true){
				//css
				$(this).css({"cursor":"pointer"})

				//sort
				$(this).bind("click",function(){
					zGrid.setSort($(this), $columns, zGrid.initData.columns[$(this).parent().index()].columnId, i);
				});
			}

			//컬럼 픽스 바인딩
			if(zGrid.initData.autoColumn != true) {

				$(this).parent().mouseover(function(e){

						var obj = $(this);
						var left = this.offsetLeft + $(this).width() -26 - ($(this).parent().parent().attr("id")=="zGridWrap" ? $(this).parent().parent().scrollLeft() : 0);

						zGrid.$grid.find('#zGridWrap #zGridFin').css({ "top": this.offsetTop, "left":  left }).show();
						zGrid.$grid.find('#zGridWrap #zGridFin').mouseover(function(){$(this).show()});
						zGrid.$grid.find('#zGridWrap #zGridFin').mouseout(function(){$(this).hide()});
						zGrid.$grid.find('#zGridWrap #zGridFin').unbind('click');
						zGrid.$grid.find('#zGridWrap #zGridFin').bind("click",function(){
							zGrid.setFixColumn(obj)
						})
				});

				$(this).parent().mouseout(function(e){
					$(this).parent().unbind('mouseover');
					zGrid.$grid.find('#zGridWrap #zGridFin').hide();
				});
			}

		});


		//store 생성
		zGrid.getGridData();
		
	},


	fixColumnWidth : 0,
	columnWidth : 0,
	setColumnWidth : function(w,headerWrap,dataWrap){

		var alterRate = 0;
		var totalColumnWidth = 0;
		var alterWidth = [];

		for(i = 0; i < w.length; i++){
			totalColumnWidth += w[i]
		}

		if(headerWrap.attr("id") == "headerWrap"){
			zGrid.columnWidth = totalColumnWidth;
		}else{
			zGrid.fixColumnWidth = totalColumnWidth;
		}

		if(zGrid.initData.autoColumn == true){

			for(i = 0; i < w.length; i++){
				alterRate += Math.floor(w[i]  / totalColumnWidth * 100);
				alterWidth.push(Math.floor(w[i] / totalColumnWidth * 100));
			}

			var cnt = alterWidth.length; ;
			var remainRate = 100 - alterRate;
			for(i  = 0; i < cnt; i++){
				if( cnt - remainRate <= i) alterWidth[i] = alterWidth[i] + 1;
			}

			for(i = 0; i < cnt; i++){
				 zGrid.$grid.find(headerWrap).find(".column").eq(i).css("width",alterWidth[i] + "%");
				 zGrid.$grid.find(dataWrap).find(".column").eq(i).css("width",alterWidth[i] + "%");
			}
		}else{

			if(headerWrap.attr("id") == "headerWrap"){
				zGrid.$grid.find(headerWrap).width(totalColumnWidth);
				zGrid.$grid.find(dataWrap).width(totalColumnWidth + 18);
				zGrid.$grid.find("#zGridWrap").css({"overflow":"auto"})
			}
			for(i = 0; i < w.length; i++){
				zGrid.$grid.find(headerWrap).find(".column").eq(i).width(w[i]);
				zGrid.$grid.find(dataWrap).find(".column").eq(i).width(w[i]);
			}
		}
	},

	//그리드 쇼팅
	setSort : function(obj,columns, columnId, i){
		columns.each(function(){
			if($(this).index() != i) $(this).find(".sort").text("");
		});

		zGrid.$grid.find(".zData .cell").remove();

		if( obj.find(".sort").text() == "▼"){
			obj.find(".sort").text("▲") 
			
			if(zGrid.initData.paging.limit > 0){
				zGrid.sort.key = columnId;
				zGrid.sort.value = "asc";
				zGrid.getGridData();
			}else{
				zGrid.setGridDataSort(columnId,"asc");
			}
		}else{
			obj.find(".sort").text("▼") ;
			 
			 if(zGrid.initData.paging.limit > 0){
				 zGrid.sort.key = columnId;
				 zGrid.sort.value = "desc";
				 zGrid.getGridData();
			 }else{
				 zGrid.setGridDataSort(columnId,"desc");
			 }
		}

		zGrid.rowCount = 0;
		if(zGrid.initData.paging.limit == 0) zGrid.setGridData();
	},

	 setGridDataSort : function(prop, order) {

		zGrid.initData.store.dataList = zGrid.initData.store.dataList.sort(function(a, b) {
			if (order =="asc") return (a[prop] > b[prop]) ? 1 : ((a[prop] < b[prop]) ? -1 : 0);
			else return (b[prop] > a[prop]) ? 1 : ((b[prop] < a[prop]) ? -1 : 0);
		});

	},

	/*
	 * 그리드 데이터 생성
	 */
	getGridData : function(urlInfo){
		
		if( urlInfo == undefined ){
			if(zGrid.initData.paging.limit > 0) zGrid.util.setParam(zGrid.initData.page);
			zGrid.util.setUrlInfo();
		}else{
			zGrid.util.setUrlInfo();
		}
		
		var info = zGrid.util.getUrlInfo();


		zGrid.setGridData();
		//paging
		/*
		if(zGrid.initData.paging.limit > 0){
			zGrid.util.paging(zGrid.$grid.find("#zGridWrap #paging"), zGrid.initData.store.totalCount,zGrid.util.getPage());
		}else{
			$("#zGridWrap #topWrap .total span").text(zGrid.initData.store.totalCount);
		}
		*/
		if(zGrid.initData.paging.limit > 0){
			zGrid.util.paging(zGrid.$grid.find("#paging"), zGrid.initData.store.totalCount,zGrid.util.getPage());
		}else{
			zGrid.$grid.find("#paging").hide();
		}
		zGrid.setSrolbarWidth();

/*
		$.ajax({
			url: info.url,
			data : $.param(info.params, true),
			type : 'POST',
			dataType: 'JSON',
			timeout : 3000,
			error: function(e) {
				alert('Error loading JSON');
			},
			success: function(data){
				zGrid.initData.store = data; 
				
				zGrid.setGridData();
				//paging
				zGrid.util.paging(zGrid.$grid.find("#zGridWrap #paging"), zGrid.initData.store.totalCount,info.page);
			}
		});
		
*/

	},

	/*
	* scrollbar 생성에 따른 header width 조정
	*/
	setSrolbarWidth : function(){
		
		if( zGrid.$grid.find("#dataWrap").hasScrollBar() ){
			zGrid.$grid.find("#headerWrap").css("padding-right","18px");
			if(zGrid.initData.autoColumn != true) zGrid.$grid.find("#dataWrap").width(zGrid.columnWidth + 18);
		}else{
			zGrid.$grid.find("#headerWrap").css("padding-right","");
			if(zGrid.initData.autoColumn != true) zGrid.$grid.find("#dataWrap").width(zGrid.totalColumnWidth);
		}

	},

	/*
	* 그리드 데이터  바인딩
	*/
	setGridData : function(){
		
		zGrid.removeAll();
		
		$("#dataStatus").hide();
		
		if( zGrid.initData.store.dataList.length == 0 ){
			  zGrid.setDataStatus("자료가 없습니다.");
		}else{
			$.each(zGrid.initData.store.dataList,function(i){
				zGrid.addRow($(this)[0]);
			});
		}

	},
	
	setDataStatus :function(msg){
		
		if( $("#dataStatus").length == 0 ){
			$("<div>",	{
	  			id:'dataStatus',
				css: {
					height: zGrid.$grid.find('#zGridWrap').height() - zGrid.$grid.find('#zGridWrap #headerWrap').height(),
					width : zGrid.$grid.find('#zGridWrap').width(),
					top: zGrid.$grid.find('#zGridWrap').offset().top + zGrid.$grid.find('#zGridWrap #headerWrap').height(),
					left: zGrid.$grid.find('#zGridWrap').offset().left,
					position:"absolute",
					"text-align":"center",
					"line-height":zGrid.$grid.find('#zGridWrap').height() + "px" 
				}
			}).appendTo(zGrid.$grid.find('#zGridWrap'));	
		}
		
		 $("#dataStatus").html(msg);
		 $("#dataStatus").show();
		
	},

	/*
	* 로우 추가
	* Object
	*/
	addRow : function(obj){
		zGrid.rowCount++;

		zGrid.$grid.find(".zData .column").each(function(j){

			var bgColor = ( zGrid.initData.stripeRows == true &&  zGrid.rowCount % 2 != 0) ? ";background-color:#fafafa;" : "";
			if(obj.checked == true) bgColor = ";background-color:#dfe8f6";

			var value = (obj[zGrid.initData.columns[j].columnId] == undefined) ? "" : obj[zGrid.initData.columns[j].columnId];

			//checkbox
			if( zGrid.initData.checkbox !=undefined &&  j == 0 ){
				value="<input type='checkbox' " + ((obj.checked == true) ? 'checked' : '') + " onClick=\"zGrid.setColumVaue( this,'checked')\"'>";
			}

			//String --> Int			
			if( zGrid.initData.columns[j].type == "int" ) {
				obj[zGrid.initData.columns[j].columnId] = zGrid.util.StringToInt(obj[zGrid.initData.columns[j].columnId]);
			}

			//렌더러 
			if( typeof(zGrid.initData.columns[j].renderer) == "function" ) {
				var func = zGrid.initData.columns[j].renderer;
				value = func(value,obj);
			}

			$(this).append("<div class='cell' rowId='"+ zGrid.rowCount + "' col='" + j + "' style='height:" + zGrid.initData.rowHeight + "px;text-align:" + zGrid.initData.columns[j].align + bgColor + "'>" + value+ "</div>")

		});


	},

	/*
	* 컬럼 데이터 셋팅
	* 순서, key, value
	*/
	setColumVaue : function(ts,key){
		var index = zGrid.$grid.find(".zData .column").eq(0).find("input[type=checkbox]").index(ts);
		zGrid.$grid.find(".zData .column").each(function(){
			$(this).find(".cell").eq(index).css("background-color", ts.checked==true ? "#dfe8f6" : "");
		})
		zGrid.initData.store.dataList[index][key] = ts.checked;
	},
	
	/*
	* 로우 삭제
	* key
	*/
	removeRow : function(key){

		var obj = {};
		obj[key] = [];
		var gridRows = [];
		var cnt = 0;
		zGrid.$grid.find(".zData .column").eq(0).find("input[type=checkbox").each(function(i){
			if( $(this).prop("checked") == true){
				
				//store key 값 추출/제거
				if(zGrid.initData.store.dataList[i-cnt][key] !=undefined) obj[key].push(zGrid.initData.store.dataList[i-cnt][key]);
				zGrid.initData.store.dataList.splice(i-cnt, 1);

				//삭제대상 grid rowId
				gridRows.push($(this).parent().attr("rowId") )

				cnt++;
				zGrid.rowCount--;
			}
		});

		//그리드 row 삭제
		zGrid.$grid.find(".zData .column").each(function(i){
			for(var  r in gridRows){
				$(this).find("div[rowId=" + gridRows[r] + "]").remove();
			}
		});

		zGrid.refresh();
		zGrid.setSrolbarWidth();

		return $.param(obj, true);

	},

	/*
	* store 추가
	* Object
	*/
	addStore : function(obj){
		zGrid.addRow(obj);
		zGrid.initData.store.dataList.push(obj);
		zGrid.setSrolbarWidth();
		zGrid.rowCount++;
	},
	
	/*
	 * 파리미터 셋팅
	 */
	setParam : function(obj){
		 zGrid.initData.params = obj;
	},

	/*
	* Grid refresh
	*/
	refresh : function(){
		zGrid.$grid.find(".zData .cell").remove();
		zGrid.rowCount = 0;
		zGrid.setGridData();
	},
	
	/*
	* Grid remove All
	*/
	removeAll : function(){
		zGrid.$grid.find("#zData .cell").remove();
		zGrid.rowCount = 0;
	},

	/*
	* 컬럼 픽스
	*/
	setFixColumn : function(obj){

		var col =  obj.index();
		if(obj.parent().attr("id") == "headerWrap"){

			for(i = 0; i <= col; i++){
				zGrid.columnWidth -= zGrid.$grid.find("#headerWrap .column:first").width();
				zGrid.fixColumnWidth += zGrid.$grid.find("#headerWrap .column:first").width();
				
				zGrid.$grid.find("#headerWrap").width(zGrid.columnWidth);
				zGrid.$grid.find("#dataWrap").width(zGrid.columnWidth);

				zGrid.$grid.find("#headerFixWrap").width(zGrid.fixColumnWidth);
				zGrid.$grid.find("#dataFixWrap").width(zGrid.fixColumnWidth);

				zGrid.$grid.find("#headerFixWrap").append( zGrid.$grid.find("#headerWrap .column:first") );
				zGrid.$grid.find("#dataFixWrap").append( zGrid.$grid.find("#dataWrap .column:first") );

			}

			if( zGrid.$grid.find("#dataWrap").hasScrollBar() == true){
				zGrid.$grid.find("#dataWrap").width(zGrid.columnWidth + 18);
			}

			//그리드 높이
			zGrid.$grid.find("#dataFixWrap").height(zGrid.initData.gridHeight);


		//	zGrid.$grid.find("#headerFixWrap .column:eq("+ col +") .dragbar").css({"background-color":"#f2f2f2","width":"0px"});
			zGrid.$grid.find("#headerFixWrap").css({"border-right": "1px solid #ccacac"}) 
			zGrid.$grid.find("#dataFixWrap").css({"border-right": "1px solid #ccacac"}) 

			zGrid.setScrollbar();
		}else if(obj.parent().attr("id") == "headerFixWrap"){

			for(i = obj.parent().find(".column").length  -1; i > col; i--){


				zGrid.columnWidth += zGrid.$grid.find("#headerFixWrap .column:last").width();
				zGrid.fixColumnWidth -= zGrid.$grid.find("#headerFixWrap .column:last").width();

				zGrid.$grid.find("#headerWrap").width(zGrid.columnWidth);
				zGrid.$grid.find("#dataWrap").width(zGrid.columnWidth);

				zGrid.$grid.find("#headerFixWrap").width(zGrid.fixColumnWidth);
				zGrid.$grid.find("#dataFixWrap").width(zGrid.fixColumnWidth);

				$("#headerWrap").prepend( zGrid.$grid.find("#headerFixWrap .column:last") );
				$("#dataWrap").prepend( zGrid.$grid.find("#dataFixWrap .column:last") );

			}

			if( zGrid.$grid.find("#dataWrap").hasScrollBar() == true){
				zGrid.$grid.find("#dataWrap").width(zGrid.columnWidth + 18);
			}

		}

		//dataWrap 스크롤 조정
		if( zGrid.$grid.width() - zGrid.fixColumnWidth  < 100) zGrid.$grid.width(zGrid.fixColumnWidth + 200) 
		if( zGrid.$grid.width()  > zGrid.fixColumnWidth + zGrid.columnWidth)	zGrid.$grid.width(zGrid.fixColumnWidth + zGrid.columnWidth + 30) 
		
		//pin 표시
		var pin = zGrid.$grid.find("#headerFixWrap .column:last");
		zGrid.$grid.find('#zGridWrap #zGridFinFix').css({ "top": pin.offset().top, "left":  pin.offset().left + pin.width() - 26 }).show();

	},

	/*
	* 컬럼 픽스시 스크롤 제어
	*/
	setScrollbar : function(){
		var top = 0;
		var h = 30;
		var end = true;
		var scrollingScreen = false;
		zGrid.$grid.find("#dataFixWrap").mousewheel(function(event, delta) {
			if ( !scrollingScreen ) {
				if ( delta < 0 && end == true ){
					top += h
				} else if(delta > 0 && top > 0 ){
					top -= h
				}

				zGrid.$grid.find("#dataFixWrap").animate({ scrollTop:top }, 0, "", function() {
					end = this.scrollHeight -  $(this).scrollTop() > $(this).innerHeight();
					 scrollingScreen = false;
				});

				zGrid.$grid.find("#dataWrap").animate({ scrollTop:top }, 0, "", function() {});

			}
			 return false; // Prevents default scrolling

		});

		zGrid.$grid.find("#dataWrap").mousewheel(function(event, delta) {

			 if ( !scrollingScreen ) {
				if ( delta < 0 && end == true ){
					top += h
				} else if(delta > 0 && top > 0 ){
					top -= h
				}

				zGrid.$grid.find("#dataWrap").animate({ scrollTop:top }, 0, "", function() {
					end = this.scrollHeight -  $(this).scrollTop() > $(this).innerHeight();
					 scrollingScreen = false;
				});

			 }

			 return false; // Prevents default scrolling
		});

		//scroll bar 제어
		zGrid.$grid.find("#dataWrap").scroll(function() {
			zGrid.$grid.find("#dataFixWrap").scrollTop($(this).scrollTop() )
			//console.log( $(this).scrollTop() );
		});
	}

};



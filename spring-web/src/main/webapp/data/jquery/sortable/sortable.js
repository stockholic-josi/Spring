var loading = '<p style="margin-top:50px;color:#717171" align="center"><img src="loading.gif" align="absmiddle"> 로딩 ...</p>';


var sortable = {

	initItemData : {},
	itemData : {},

	//-------------------------- URL Json Data
	initItemList : function(){

		var data = {list : [

				  {group : "sort1",item :"item1",name :"한국경제 | 증권",link : "http://rss.hankyung.com/stock.xml"},
				  {group : "sort1",item :"item2",name :"매일경제 | 증권",link : "http://news.mk.co.kr/rss/stock.xml"},
				  {group : "sort1",item :"item3",name :"이데일리 | 증권",link : "http://rss.edaily.co.kr/stock_news.xml"},
				  {group : "sort1",item :"item4",name :"서울경제 | 증권",link : "http://rss.hankooki.com/economy/sk_stock.xml"},

				  {group : "sort2",item :"item5",name :"연합뉴스 | 증권",link : "http://www.yonhapnews.co.kr/RSS/stock.xml"},
				  {group : "sort2",item :"item6",name :"헤럴드경제 | 증권",link : "http://biz.heraldm.com/rss/010106000000.xml"},
				  {group : "sort2",item :"item7",name :"파이낸셜뉴스 | 증권",link : "http://www.fnnews.com/rss/fn_realnews_stock.xml"},

				  {group : "sort3",item :"item8",name :"조선일보 | 경제",link : "http://www.chosun.com/site/data/rss/economy.xml"},
				  {group : "sort3",item :"item9",name :"중앙일보 | 경제",link : "http://rss.joinsmsn.com/joins_money_list.xml"},
				  {group : "sort3",item :"item10",name :"동아일보 | 경제",link : "http://rss.donga.com/economy.xml"}

			]};
		
		sortable.initItemData = data;

		if($.cookie("cookie_item") == null){
			sortable.itemData = data;
		}else{
			var itemList = {list : []};
			
			 $.each(sortable.getItemPosition().list ,function(){
				
				for(i = 0; i < data.list.length; i++){
					var itemObj = {group : "", item : "",name : "", link : ""};

					if(data.list[i].item == this.item){

						itemObj.group = this.group;
						itemObj.item = this.item;
						itemObj.name = data.list[i].name;
						itemObj.link = data.list[i].link;

						itemList.list.push(itemObj);
						break
					}
				}
			 });

			sortable.itemData = itemList;
		}

		this.setMenu(data);								//Menu

		this.makeItem(sortable.itemData);			//Box 내용
		this.initSortable(".column");					//Box UI 
		
	},


	initSortable : function(column){

		$( column ).sortable({
			connectWith: ".column",
			cursor: 'move',
			update: function(event, ui) { 
				if(ui.sender  == null ){
					sortable.setItemPosition( sortable.getItemList() );
				}
			}
			
		});


		$( column ).disableSelection();
	},

	//-------------------------- Set Item Menu
	setMenu : function(data){
		 $.each(data.list,function(i){
			 $("#itemMenu").append("<div class='menuName'><input type='checkBox' class='" + this.item + "'>" + this.name + "</div>");
			 if( (i + 1) % 5 == 0) $("#itemMenu").append("<br>");
		 });

		 $.each(data.list,function(i){
			for(j = 0; j < sortable.itemData.list.length; j++){
				if(sortable.itemData.list[j].item == this.item){
					$("#itemMenu").find("." + sortable.itemData.list[j].item).attr("checked", "checked");
					break;
				}
			}
		 });

		 $("#itemMenu").find("input").click(function() {
			sortable.setItem($(this).attr("class"))
		});
	},

	//-------------------------- make Item Box
	makeItem : function(data){
		
		var itemBox;
		 $.each(data.list,function(){

			itemBox = "<div class='portlet' id='" + this.item + "'>"
						+ "<div class='portlet-header'>" + this.name + "</div>"
						+ "<div class='portlet-content'> "+ loading + "</div>"
						+ "</div>";

			$("#" + this.group).append(itemBox);

			
			$( "#" + this.item ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
			.find( ".portlet-header" )
				.addClass( "ui-widget-header ui-corner-all" )
				.prepend( "<span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>")
				.prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
				.prepend( "<span class='ui-icon ui-icon-close'></span>")
				.end()
			.find( ".portlet-content" );

			$( "#" + this.item +  " .portlet-header .ui-icon" ).click(function() {

				//접기 - 펼치기
				if( $(this).attr("class") == "ui-icon ui-icon-minusthick" || $(this).attr("class") == "ui-icon ui-icon-plusthick"){
					$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
					$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
				}

				//refresh
				if( $(this).attr("class") == "ui-icon ui-icon-arrowreturnthick-1-w" ){

					$( this ).parents( ".portlet:first" ).find( ".portlet-content").html(loading);
					sortable.refresh( $(this) );
				}

				//close
				if( $(this).attr("class") == "ui-icon ui-icon-close" ){
					$( this ).parents(".portlet:first").remove();
					$("#itemMenu").find("." + $(this).parent().parent().attr("id")).attr("checked", "");
					sortable.setItemPosition( sortable.getItemList() );
				}

			});

			sortable.getNews(this.group,this.item,this.link);
		}); 
	},

	//-------------------------- Item List Data
	getNews : function(group,item,link){

/*
		listCount = 6;
		var list = "";
		$.ajax({
			url: link,
			type : 'GET',
			dataType: 'xml',
			error: function() {},
			success: function(xml){

				if ($(xml).find("channel").find("item").length > 0) {

					$(xml).find("channel").find("item").each(function(i) {		

						var title = $(this).find("title").text();
						var link = $(this).find("link").text();

						var dspDate = "";
						var cDate = new Date();
						var nDate = new Date($(this).find("pubDate").text());

						var cMin = cDate.getHours() * 60 + cDate.getMinutes();
						var nMin = nDate.getHours() * 60 + nDate.getMinutes();

						if(nDate == "NaN") {
							dspDate = (cDate.getMonth() + 1) + " / " +  cDate.getDate() ;
						}else if(cMin - nMin > 60){
							dspDate = parseInt( (cMin - nMin) / 60 ) + " 시간전";
						}else if(cMin - nMin < 60){
							dspDate = cMin - nMin + " 분전";
						}else{
							dspDate = " 1 분전";
						}

						list += "<li><div class='list'>" + "<a href='" + link + "' target='_blank'>" + title + "</a></div><div class='date'>" +  dspDate  + "</div></li>" ;

						if(i == listCount) return false;
					});
				
					$("#" + group + " #" + item + " .portlet-content").html("<ul>" +  list +  "</ul>");

				}else{
					$("#" + group + " #" + item + " .portlet-content").html("<ul style='text-align:center;padding-top:40px'>No Data</ul>");
				}
			},

			 error: function(){
				alert('Error loading XML document')
			 }
		});

*/

		list = "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" 
		 + "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" 
		 + "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" 
		 + "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" 
		 + "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" 
		 + "<li><div class='list'><a href='#' target='_blank'>" + item + "가가가가 가가 가가가각 가가가가가가가 가가가가가 가가가가가가가</a></div><div class='date'>20분전</div></li>" ;

		$("#" + group + " #" + item + " .portlet-content").html("<ul>" +  list +  "</ul>");

	},

	//-------------------------- Set Item
	setItem : function(itemId){

		var itemObj = {group : "", item : "",name : "", link : ""};
		 $.each(sortable.initItemData.list,function(i){
			if(itemId == this.item){
				itemObj.item = this.item;
				itemObj.name = this.name;
				itemObj.link = this.link;
				return false;
			}
		 });		

		var arrSort = [];
		if($("#itemMenu").find("." + itemId).attr("checked") == true){
			
			$.each($(".column"),function(i){
				arrSort.push( $("#" + $(this).attr("id") + " .portlet").length );
			});

			$.each($(".column"),function(i){
				if( arrSort.sort()[0] == $("#" + $(this).attr("id") + " .portlet").length ){
					itemObj.group = $(this).attr("id");
					return false;
				}
			 });
			 sortable.makeItem( {list : [itemObj]} );

		}else{
			$("div #" + itemId).remove();
		}
		sortable.setItemPosition( sortable.getItemList() );
		
	},

	//-------------------------- Current Get Item (Json)
	getItemList : function(){
		var group;
		var itemList = {list : []};
		
		 $.each($("body .column"),function(){
			group = $(this).attr("id");
			
			$.each( $("#" + group + " .portlet") ,function(){
				var itemObj = {group : "", item : ""};
				itemObj.group = group;
				itemObj.item = $(this).attr("id");

				itemList.list.push(itemObj);

			});

		 });
		 return itemList;
	},

	//-------------------------- Item Refresh
	refresh :function(ts){
		 $.each(sortable.initItemData.list,function(){
			if(this.item == ts.parent().parent().attr("id") ){
				sortable.getNews( ts.parent().parent().parent().attr("id"), ts.parent().parent().attr("id"), this.link  )
				return false;
			}
		});
	},

	//-------------------------- Set Item Cookie
	setItemPosition : function(obj){
		var cookieItem = [];
		$.each(obj.list,function(){
			cookieItem.push("{group:\"" + this.group + "\"^item:\"" + this.item + "\"}");
		}); 
		$.cookie("cookie_item", cookieItem, { path: '/', expires: 90 });
	},

	//-------------------------- Get Item Cookie
	getItemPosition : function(){
		var itemList = {list : []};
		var cookieItem = $.cookie("cookie_item").split(",");

		$.each(cookieItem,function(i){
			itemList.list.push( eval("(" + this.replace("^",",") + ')') );
		}); 
		return itemList;
	},

	reSetIem : function(){
		$.cookie("cookie_item", null, { path: '/'});
	}


}


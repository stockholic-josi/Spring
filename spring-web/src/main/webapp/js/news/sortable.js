var loading = '<div style="padding-top:50px;color:#717171" align="center"><img src="/js/news/loading.gif" align="absmiddle"> 로딩 ...</p>';


var sortable = {

	itemData : {},
	flag : "",

	//-------------------------- URL Json Data
	initItemList : function(flag){

		this.flag = flag;
		
		if($.cookie(flag + "_item") == null){
			this.itemData = newsData;
		}else{
			var itemList = {dataList : []};
			
			 $.each(sortable.getItemPosition().dataList ,function(){
				
				for(i = 0; i < newsData.dataList.length; i++){
					var itemObj = {itemGroup : "", itemNm : "",newsNm : "", newsLink : "", seqNo : ""};

					if(newsData.dataList[i].itemNm == this.itemNm){

						itemObj.itemGroup = this.itemGroup;
						itemObj.itemNm = this.itemNm;
						itemObj.newsNm = newsData.dataList[i].newsNm;
						itemObj.newsLink = newsData.dataList[i].newsLink;
						itemObj.seqNo = newsData.dataList[i].seqNo;

						itemList.dataList.push(itemObj);
						break
					}
				}
			 });

			 this.itemData = itemList;
		}

		this.setMenu(newsData);						//Menu
		this.makeItem(this.itemData);				//Box
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
		
		 $.each(newsData.dataList,function(i){
			 $("#itemMenu").append("<div class='menuName'><input type='checkBox' class='" + this.itemNm + "'> " + this.newsNm + "</div>");
		//	 if( (i + 1) % 5 == 0) $("#itemMenu").append("<br>");
		 });

		 $.each(newsData.dataList,function(i){
			for(j = 0; j < sortable.itemData.dataList.length; j++){
				if(sortable.itemData.dataList[j].itemNm == this.itemNm){
					$("#itemMenu").find("." + sortable.itemData.dataList[j].itemNm).prop("checked", "checked");
					$("#itemMenu").find("." + sortable.itemData.dataList[j].itemNm).parent().css("color", "#2B6AB9");
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
		
		 $.each(data.dataList,function(){
			 
			var itemBox = "<div class='portlet' id='" + this.itemNm + "'>"
						+ "<div class='portlet-header'>" + this.newsNm + "</div>"
						+ "<div class='portlet-content'> "+ loading + "</div>"
						+ "</div>";

			
			$("#" + this.itemGroup).append(itemBox);

			$( "#" + this.itemNm ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
			.find( ".portlet-header" )
				.addClass( "ui-widget-header ui-corner-all" )
				.prepend( "<span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>")
				.prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
				.prepend( "<span class='ui-icon ui-icon-close'></span>")
				.end()
			.find( ".portlet-content" );

			$( "#" + this.itemNm +  " .portlet-header .ui-icon" ).click(function() {

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
					$("#itemMenu").find("." + $(this).parent().parent().attr("id")).prop("checked", "");
					sortable.setItemPosition( sortable.getItemList() );
				}

			});

			sortable.getNews(this.itemGroup,this.itemNm,this.newsLink,this.seqNo);
		}); 
	},

	//-------------------------- Item List Data
	getNews : function(itemGroup,itemNm,newsLink,seqNo){
		
		$("#" + itemGroup + " #" + itemNm + " .itemContent").html(loading);

		listCount = 6;
		var list = "";
		$.ajax({
			url: '/front/news/rssNewsListJson.do?flag=' + this.flag + '&refNo=' + seqNo,
			type : 'GET',
			dataType: 'json',
			error: function() {},
			success: function(data){
				for(i = 0; i < data.dataList.length; i++){
					
					var title = data.dataList[i].title;
					var newsLink = data.dataList[i].link;
					var timeDiff = eval(data.dataList[i].timeDiff);
					
					var dspDate = "";
					
					if(timeDiff >0 &&  timeDiff < 60){
						dspDate = timeDiff + "분전";
					}else if(timeDiff > 60 &&  timeDiff < 1440){
						dspDate = Math.round(timeDiff / 60) + "시간전";
					}else{
						var nDate = new Date(data.dataList[i].pubDate);
						dspDate =(nDate.getMonth() + 1) + " / " +  nDate.getDate() ;
					}
					
					
					list += "<li><div class='list'>" + "<a href='" + newsLink + "' target='_blank'>" + title + "</a></div><div class='date'>" +  dspDate  + "</div></li>" ;
					if(i == listCount) return false;
					
				}
				
				$("#" + itemGroup + " #" + itemNm + " .portlet-content").html("<ul>" +  list +  "</ul>");
				
			}
		});
		
	},

	//-------------------------- Set Item
	setItem : function(itemId){
		
		var itemObj = {itemGroup : "", itemNm : "",newsNm : "", newsLink : "",seqNo : ""};
		
		 $.each(newsData.dataList,function(i){
			if(itemId == this.itemNm){
				
				itemObj.itemNm = this.itemNm;
				itemObj.newsNm = this.newsNm;
				itemObj.newsLink = this.newsLink;
				itemObj.seqNo = this.seqNo;
				return false;
			}
		 });		
		 
		var arrSort = [];
		if($("#itemMenu").find("." + itemId).prop("checked") == true){
			
			$.each($(".column"),function(i){
				arrSort.push( $("#" + $(this).attr("id") + " .portlet").length );
			});

			$.each($(".column"),function(i){
				if( arrSort.sort()[0] == $("#" + $(this).attr("id") + " .portlet").length ){
					itemObj.itemGroup = $(this).attr("id");
					return false;
				}
			 });
			
			sortable.makeItem( {dataList : [itemObj]} );
			$("#itemMenu").find("." + itemId).parent().css("color","#2B6AB9")

		}else{
			$("div #" + itemId).remove();
			$("#itemMenu").find("." + itemId).parent().css("color","#B3B3B3")
		}
		
		sortable.setItemPosition( sortable.getItemList() );
		
	},

	//-------------------------- Current Get Item (Json)
	getItemList : function(){
		var group;
		var itemList = {dataList : []};
		
		 $.each($("body .column"),function(){
			group = $(this).attr("id");
			
			$.each( $("#" + group + " .portlet") ,function(){
				var itemObj = {itemGroup : "", itemNm : ""};
				
				itemObj.itemGroup = group;
				itemObj.itemNm = $(this).attr("id");

				itemList.dataList.push(itemObj);

			});

		 });
		 return itemList;
	},

	//-------------------------- Item Refresh
	refresh :function(ts){
		 $.each(sortable.itemData.dataList,function(){
			if(this.itemNm == ts.parent().parent().attr("id") ){
				sortable.getNews( ts.parent().parent().parent().attr("id"), ts.parent().parent().attr("id"), this.newsLink,this.seqNo  )
				return false;
			}
		});
	},

	//-------------------------- Set Item Cookie
	setItemPosition : function(obj){
		var cookieItem = [];
		$.each(obj.dataList,function(){
			cookieItem.push("{itemGroup:\"" + this.itemGroup + "\"^itemNm:\"" + this.itemNm + "\"}");
		}); 
		$.cookie(this.flag + "_item", cookieItem, { path: '/', expires: 90 });
	},

	//-------------------------- Get Item Cookie
	getItemPosition : function(){
		var itemList = {dataList : []};
		var cookieItem = $.cookie(this.flag + "_item").split(",");

		$.each(cookieItem,function(i){
			itemList.dataList.push( eval("(" + this.replace("^",",") + ')') );
		}); 
		return itemList;
	},

	reSetIem : function(){
		$.cookie(this.flag + "_item", null, { path: '/'});
	}


}


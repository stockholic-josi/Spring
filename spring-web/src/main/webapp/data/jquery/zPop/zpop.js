(function($){
	
	$.fn.zpop = function(data) {

		if ( typeof(data) === 'object') {

			$.each(data,function(key, value){
				zpop.setting[key] = value;
			})

			zpop.setting.msg = this.html();
			zpop.draw();
		}else{
			$.error('파리미터 값이 옳바르지 않습니다.');
		}

	};

	$.fn.center = function() {
		this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
		this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
		return this;
	}

})( jQuery );


var zpop = {
	setting : {
		width : 0,
		height : 0,
		title : "",
		msg : "",
		header : true,
		modal : false,
		callback : null
	},

	draw : function(){

		if(zpop.setting.modal == true) $("body").prepend("<div id='zModalWrap'></div>");
		$("body").append("<div id='zpopWrap'></div>");
		
		if(zpop.setting.header == true){
			$("#zpopWrap").append("<div class='header'><span class='title'>" + zpop.setting.title + "</span><span class='close'><img src='/images/icon/pop_close.gif'></span></div>");
		}
		
		$("#zpopWrap").append("<div class='body'>" + zpop.setting.msg + "</div>");
		$("#zpopWrap").append("<div class='foot'></div>");

		$("#zpopWrap").width(zpop.setting.width);
		$("#zpopWrap .header .title").width(zpop.setting.width - 30);
		$("#zpopWrap").height(zpop.setting.height);
		$("#zpopWrap .body").css("overflow","auto");

		$("#zpopWrap .header .close img").bind("click",function(){
			zpop.close();
		});
		
		//center 위치 조정
		$("#zpopWrap").center();

		//DIV 이동
		var X = 0;
		var Y = 0;
		$("#zpopWrap .header").mousedown(function(e){
			e.preventDefault();
			X =  e.pageX - $(this).offset().left;
			Y =  e.pageY - $(this).offset().top;
			$(document).mousemove( function(e) {
			   $('#zpopWrap').css({"left":e.pageX - X, "top": e.pageY - Y});
			});
		});

		$(document).mouseup(function(e){

		   $(document).unbind('mousemove');

			//Pop DIV 외부 클릭시 제거
		   if(zpop.setting.modal == false &&  !$("#zpopWrap").is(e.target) && $("#zpopWrap").has(e.target).length === 0) {
				zpop.close();
			}
		});

		zpop.makeBody();
		
		
		if( typeof(zpop.setting.callback) == "function"){
			$("#zpopWrap .foot").append("<button class='btn2'>확인</button>&nbsp;")
			$("#zpopWrap .foot .btn2").bind("click",zpop.setting.callback).bind("click",function(){zpop.close()});
		}
		
		$("#zpopWrap .foot").append("<button class='btn1'>닫기</button>");
		$("#zpopWrap .foot .btn1").bind("click",function(){
			zpop.close();
		});

	},

	makeBody : function(){
		var bodyHeight = zpop.setting.height - $("#zpopWrap .header").height() - $("#zpopWrap .foot").height() - 16;
		$("#zpopWrap .body").height(bodyHeight);
		return bodyHeight;
	},

	alert : function(msg,func){

		if($("#zpopWrap").length > 0) return;
		
		zpop.setting = {
			width : 250,
			height :130,
			title  : "알림",
			msg : msg == undefined ? "" : "<p>" + msg + "</p>",
			header : true,
			modal : false
		}
		zpop.draw();
		$("#zpopWrap .body").css({"line-height":"18px","text-align":"center","overflow":""});
		
		if(func !=undefined) $("#zpopWrap .foot .btn1").bind("click",func);

	},

	confirm : function(msg,func){

		if($("#zpopWrap").length > 0) return;
			
		zpop.setting = {
			width : 250,
			height :130,
			title  : "확인",
			msg : msg == undefined ? "" : "<p>" + msg + "</p>",
			header : true,
			modal : true
		}
		zpop.draw();
		$("#zpopWrap .body").css({"line-height":"18px","text-align":"center","overflow":""});

		$("#zpopWrap .foot").prepend("<button class='btn2'>확인</button>&nbsp;")
		$("#zpopWrap .foot .btn2").bind("click",func).bind("click",function(){zpop.close()});

	},

	iframe : function(data,func){

		if($("#zpopWrap").length > 0) return;
			
		zpop.setting = {
			width : data.width,
			height :data.height,
			title  : data.title,
			msg : data.url == undefined ? "" : "<iframe id='zpopIframe' frameborder='0' style='width:100%;height:100%' src='" + data.url + "'></iframe>",
			header : data.header,
			modal : data.modal
		} 
		zpop.draw();

		$("#zpopWrap .body").css("overflow","");
		$("#zpopWrap .foot").prepend("<button class='btn2'>확인</button>&nbsp;")
		$("#zpopWrap .foot .btn2").bind("click",func);

	},

	close : function(){
		$("#zModalWrap").remove();
		$("#zpopWrap").remove();
	}
}
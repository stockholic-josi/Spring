var reply = {
		
	flag : "",
	seqNo : "",

	//--------------------------------- 초기화
	init : function(flag,seqNo){
		this.flag = flag;
		this.seqNo = seqNo;
		this.getReply(this.flag, this.seqNo);
	},
	
	getFlag : function(){
		return this.flag;
	},
	
	getSeqNo : function(){
		return this.seqNo;
	},
	
	getTemplete : function(msg,idx){
		
		var templete =  "<div id='replyUPdateBox' style=''>"
			+ "<form id='replyFrm' style='height:100%'>"
			+ "<textarea style='padding:5px;width:98%;height:90%' id='replyContent' name='content'>" + msg + "</textarea>"
			+ "<input type='hidden' name='idx' value='" + idx + "'></form>"
			+ "</div>";
			
		return templete;
	},
	
	//--------------------------------- 입력 박스크기 조절
	setBox : function(n){
		var min = 50;
		var max = 200;
		var h =  $("#replyBox").height();
		
		if(n > 0){
			if( h >=  max ) return;
			$("#replyBox").css("height", h+ n)
		}else if(n < 0){
			if( h <=  min ) return;
			$("#replyBox").css("height", h + n)
		}
		
	},
	
	//--------------------------------- 목록
	getReply : function(flag,seqNo){
		$.ajax({
    		url: '/manage/reply/replyAjax.do',
    		type: 'POST',
    		data: 'flag=' + this.flag + '&seqNo=' + this.seqNo,
    		timeout : 5000,
    		error: function(){
    		},
    		success: function(msg){
    			$("#reply").html(msg);
    			$( "button" ).button({
    				icons: {
    					primary: "ui-icon-document"
    				}
    			});
    		}
    	});
	},
	
	//--------------------------------- 등록 
	write : function (isLogin){

		if(!isLogin){
	      	if(!trim($("#replyBox").val()) ){
	    		
	    		alertDialog("알림","내용을 입력해주세요",function(){
	    			$("#dialog").dialog("close"); 
	    			$("#replyBox").focus();
	    		});
	    		
	    		return;
	    	}
	    
	    	$.ajax({
	    		url: '/manage/reply/replyInsert.do',
	    		type: 'POST',
	    		data: $("#frm").serialize(),
	    		timeout : 3000,
	    		error: function(){
	    			alertDialog("알림","저장에 실패하였습니다.",function(){
		    			$("#dialog").dialog("close"); 
		    		});
	    		},
	    		success: function(msg){
					$("#reply").empty();
					reply.getReply(reply.getFlag(), reply.getSeqNo());
	    		}
	    	});
	    }else{
	    	alertDialog("알림",isLogin,function(){
    			$("#dialog").dialog("close"); 
    		});
	    }

	},
	
	//--------------------------------- 수정 폼

	modify : function (idx){
		
		if($("#replyUPdateBox").length == 0 ){
			$("body").append( this.getTemplete($("#cnt" + idx).html().replace(/<br>/g,"\n"),idx) );
		}
		
		$("#replyUPdateBox").zpop({
			title : "댓글수정",
			width:750,
			height:230,
			modal : true,
			callback : function(){
				reply.modifyProc()
			}
		});
		
		$("#replyUPdateBox").remove();
		
	},
	
	//--------------------------------- 수정
	modifyProc : function(){
		if(!trim($("#zpopWrap textarea").val()) ) return;

		$.ajax({
			url: '/manage/reply/replyUpdate.do',
			type: 'POST',
			data: $("#replyFrm").serialize(),
			timeout : 3000,
			error: function(){
				
			},
			success: function(msg){
				reply.getReply(reply.getFlag(), reply.getSeqNo());
			}
		});
	},

	//--------------------------------- 삭제
	delete : function (idx){
		
		zpop.confirm("삭제하시겠습니까 ? ",function(){
			zpop.close();
			
			$.ajax({
	    		url: '/manage/reply/replyDelete.do',
	    		type: 'POST',
	    		data: 'idx=' + idx,
	    		timeout : 3000,
	    		error: function(){
	    			zpop.alert("삭제에 실패하였습니다.");
	    		},
	    		success: function(msg){
	    			reply.getReply(reply.getFlag(), reply.getSeqNo());
	    		}
	    	});
			
		});
		
	}

}


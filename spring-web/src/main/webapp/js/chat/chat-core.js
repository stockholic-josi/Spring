//------------------------------------------------ 채팅 초기화
function initChat(){
	$('#chatFlash').flash({
		src : 'ChatClient.swf',
		id : 'ChatClient',
		width : '0',
		height : '0',
		allowScriptAccess : 'always'
	});
}

//-------------------------------------------------  서버해제
function closeServerMsg(){
	$("#ChatClient").externalInterface({
		method : 'closeServer'
	});
}

//------------------------------------------------  서버에 메세지 송신
function sendServerMsg(cmd,msg){

	$("#ChatClient").externalInterface({
		method : 'sendMsg',
		args : {
			cmd : cmd,
			msg : msg
		}
	});
}

//------------------------------------------------  서버에 귓속말 송신
function sendWhisper(cmd,msg,userId){

	$("#ChatClient").externalInterface({
		method : 'sendMsg',
		args : {
			cmd : cmd,
			msg : msg,
			userId : userId
		}
	});
}

//------------------------------------------------  채팅방 입장
function enterRoom(roomNm){
	alert();
	sendMsg(CMD_ENTERROOM,roomNm)
}

//------------------------------------------------  채팅방 퇴장
function exitRoom(){
	sendMsg(CMD_EXITROOM,"채팅방 퇴장");
	$("#chatFrm").attr("src","waitRoom.html");

}


//------------------------------------------------  서버수신 클라이언트 메세지 처리

function recvMsg(msg){

	var roomList = $("#chatFrm").contents().find("#roomList");
	var clientList = $("#chatFrm").contents().find("#clientList");
	var chatMsg = $("#chatFrm").contents().find("#chatMsg");

	var data = eval('(' + msg + ')');
		
	switch (data.cmd){


		case CMD_GENERALMSG :								//일반 메세지
			chatMsg.append("<p><img src='/images/chat/" + data.msg.sex  + ".gif' align='absmiddle'>" + data.msg.userNm + " : " + data.msg.msg + "</p>");		
			chatMsg.attr("scrollTop",chatMsg.attr("scrollHeight"));
			break;
		case CMD_NOTICE_GENERALMSG :					//입장, 퇴장 메시지
			$("#chatFrm").contents().find("#loading").remove();
			chatMsg.append("<p>" + data.msg + "</p>");			
			chatMsg.attr("scrollTop",chatMsg.attr("scrollHeight"));																						
			break;
		case CMD_ENTERROOM_PASS_MSG :	 			//패스워드 조회 결과
			if( data.msg  =="fail"){
				 $("#chatFrm").contents().find("#errPass").text("비밀번호가 일치하지 않습니다.");
			}else{
				enterRoom(privateRoom);
				$("#chatFrm").contents().find("#passFrm").dialog("close"); 
			}

			break;		
		case CMD_ENTERROOM_OK :	 					//입장
			$("#chatFrm").attr("src","chatRoom.html")
			break;		
		case CMD_ENTERROOM_FAIL :					//입장 실패
			if(data.msg == "isUserId"){
				alertDialog("'확인","이미 채팅방에 참여중입니다",function(){
					$(this).dialog("close"); 
				});
			}else if(data.msg == "maxCount"){
				alertDialog("'확인","정원을 초과했습니다.",function(){
					$(this).dialog("close"); 
				});
			}else if(data.msg == "noRoom"){
				alertDialog("'확인","채팅방이 존재하지 않습니다.",function(){
					$(this).dialog("close"); 
				});
			}
			break;			
		case CMD_CLIENTLIST_START :				//클라이언트 리스트 시작
			clientList.empty();																						
			break;				
		case CMD_CLIENTLIST :							//채팅방 클라이언트 리스트 표시
			var data = data.msg;
			for(i = 0; i < data.length; i++){
				clientList.append("<p id='" + data[i].userId + "' class='cList' onClick='userMenu(\"" +data[i].userId+ "\",\"" +data[i].userNm+ "\",this)'><img src='/images/chat/" + data[i].sex  + ".gif' align='absmiddle'>" + data[i].userNm + "</p>");
			}
			
			clientList.attr("scrollTop",clientList.attr("scrollHeight"));
			break;	
		case CMD_CLIENTLIST_ADD :					//채팅방 입장 전달
			clientList.prepend("<p id='" + data.msg.userId + "' class='cList' onClick='userMenu(\"" +data.msg.userId+ "\",\"" +data.msg.userNm+ "\",this)'><img src='/images/chat/" + data.msg.sex  + ".gif' align='absmiddle'>" + data.msg.userNm + "</p>");
			break;	
		case CMD_CLIENTLIST_REMOVE :			 //채팅방 유저 목록 제거
			clientList.find("#" + data.msg.userId ).remove();
			break;	
		case CMD_ROOMLIST :							//채팅방 리스트  표시
			var data = data.msg;
			for(i = 0; i < data.length; i++){
				var date = data[i].date.split(" ");
				var num = " (" + ( (data[i].num == "0")?"무제한": "<span>" + data[i].apply + "</span>/" +data[i].num) + ")" + (data[i].roomType == 'Y'?"":" - 비공개");
				roomList.append("<ul id='" + data[i].seqNo + "' onClick='enterRoom(\"" + data[i].seqNo + "\",\"" + data[i].roomType + "\")' class='hand'><div class='r1'>" + data[i].name + num + "</div><div class='r2'>" + date[1].split(":")[0] + ":" + date[1].split(":")[1] + "</div></ul>");
			}
			break;	
		case CMD_CREATE_ROOM_OK :				//채팅방 생성 확인
			//alertDialog("확인",data.msg);
			$("#createFrm").dialog("close"); 
			enterRoom(data.msg,"Y");
			break;	
		case CMD_ROOMLIST_ADD :					//채팅방 생성 표시
			var date = data.msg.date.split(" ");
			var num = " (" + ( (data.msg.num == "0")?"무제한": "<span>"+ data.msg.apply + "</span>/" + data.msg.num) + ")" + (data.msg.roomType == 'Y'?"":" - 비공개");
			roomList.prepend("<ul id='" + data.msg.seqNo + "' onClick='enterRoom(\"" + data.msg.seqNo + "\",\"" + data.msg.roomType + "\")' class='hand'><div class='r1'>" + data.msg.name + num + "</div><div class='r2'>"  +   date[1].split(":")[0] + ":" + date[1].split(":")[1] + "</div></ul>");
			break;	
		case CMD_WHISPER :							//귓속말
			chatMsg.append("<p>" + data.msg + "</p>");				
			chatMsg.attr("scrollTop",chatMsg.attr("scrollHeight"));						
			break;
		case CMD_KICK_OK :							//추방
			closeServer();
			break;
	}

}


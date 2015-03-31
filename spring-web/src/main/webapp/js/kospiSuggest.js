var itemNodeCount;							//서제스트 수
var keyUpDown;								//서제스트 방향키 위치 계산
var cursorFieldID;							//커서가 위치한 서제스트 ID
var scrollMax = 10;							//스크롤 데이터 건수
var scrollHT = 0;								//스크롤 높이
var inputName;								//현재 INPUT 이름
var fieldValue;								//서제스트 리스트 값
var sgtTop;										//서제스트 레이어 위치(Top)
var sgtLeft;										//서제스트 레이어 위치(Left)
var sgtWidth;									//서제스트 레이어 폭

function strLen(T) {
	var i;
	var len = 0;
	for (i=0; i<T.length; i++) {
		if (T.charCodeAt(i) > 255) {
			len += 2;
		} else{
			len ++;
		}
	}
	return len;
}


//------------------- 오브젝트 위치
function getRealOffsetTop(o) {
	return o ? o.offsetTop + getRealOffsetTop(o.offsetParent) : 0; 
}
function getRealOffsetLeft(o) {
	return o ? o.offsetLeft + getRealOffsetLeft(o.offsetParent) : 0; 
} 


//------------------- 이벤트 리스너 설정
function commAddListener(parmObject, parmType, parmFunction, parmFalse){
	if(parmObject.attachEvent){
		parmObject.attachEvent("on" + parmType,parmFunction);						//IE
	}else{
		parmObject.addEventListener(parmType,parmFunction,parmFalse);		//IE 이외
	}
}

//-------------------  이벤트 리스너 해제
function  commRemoveListener(parmObject, parmType, parmFunction, parmFalse) {

    if (parmObject.detachEvent) {
        parmObject.detachEvent("on" + parmType, parmFunction);
    } else {
        parmObject.removeEventListener(parmType, parmFunction, parmFalse);
    }
}

//-------------------  차일드 엘리먼트 삭제
function commRemoveChild(parmParentNode) {

    if (document.getElementById(parmParentNode) ) {
        var parmParent = document.getElementById(parmParentNode);
        parmParent.parentNode.removeChild(parmParent);
    }
}

//-------------------  선택한 객체 인식 
function commChoiceObject(evt) {

    var returnChoiceObject;
    if (evt.srcElement) {
        returnChoiceObject = evt.srcElement;	 // IE
    } else {
        returnChoiceObject = evt.target;			 // IE 이외
    }
    return returnChoiceObject;
}

//-------------------  버블 방지
function commStopBubble(evt) {
    if (window.event) {
        window.event.cancelBubble = true;   // IE
    } else {
        evt.stopPropagation();						// IE 이외
    }
}

//----------------------------------------------------------------- 서제스트 리스트에 마우스가 위치
function mouseOverSuggest(evt) {
    var suggestLine = commChoiceObject(evt);
    
   if($(suggestLine).attr("id") == '' ){
	   $(suggestLine).parent().css("background-color","F4F4F4")
   }else{
	   $(suggestLine).css("background-color","F4F4F4")
   }
    
	fieldValue = suggestLine.innerText;
}

//----------------------------------------------------------------- 서제스트 리스트 밖으로 마우스 이동
function mouseOutSuggest(evt) {
    var suggestLine = commChoiceObject(evt);
    
    if($(suggestLine).attr("id") == '' ){
 	   $(suggestLine).parent().css("background-color","FFFFFF")
    }else{
 	   $(suggestLine).css("background-color","FFFFFF")
    }
}


//-----------------------------------------------------------------  서제스트 리스트의 데이터 선택

function mouseUpSuggest(evt) {

    var suggestLine = commChoiceObject(evt);
	var itemValue = "";
    
    if($(suggestLine).attr("id") == '' ){
    	itemValue = $(suggestLine).parent().text().split(" ");
    }else{
    	itemValue = $(suggestLine).text().split(" ");
    }
	
	document.getElementById("codeCd").value = itemValue[0];
	document.getElementById(inputName).value = itemValue[1];
	
    commRemoveChild("suggestList");  //서제스트 엘리먼트 삭제
}


//----------------------------------------------------------- 아래 방향키  
var onePlus;
function  nextFocus(evt) {

	if(!document.all["suggestList"]) return;

   onePlus = keyUpDown + 1;

	if(onePlus > scrollMax / 2 && onePlus < itemNodeCount){
		scrollHT = scrollHT + 18;
		document.getElementById("suggestList").scrollTop = scrollHT;
		
	}

   //방향키를 누른 서제스트 라인의 바탕색을 원래의 색으로 변경
    if (onePlus > 0 && onePlus < itemNodeCount) {
        var currentSgtID = document.getElementById(cursorFieldID);
        currentSgtID.style.backgroundColor = "#FFFFFF";
    }
   
   //최대값과 이동할 위치 값 비교
  if (onePlus < itemNodeCount) {    //서제스트 건수
        keyUpDown++;
        cursorFieldID = "sgt" + keyUpDown;
        var nextCodeDesc = document.getElementById(cursorFieldID);
        nextCodeDesc.style.backgroundColor = "#F4F4F4";

		fieldValue = nextCodeDesc.innerText;

		var itemValue = nextCodeDesc.innerText.split(" ");
		document.getElementById("codeCd").value = itemValue[0];
		document.getElementById(inputName).value = itemValue[1];

  }


}
//----------------------------------------------------------- 위 방향키
function  prevFocus(evt) {

	if(!document.all["suggestList"]) return;

	if(onePlus > 5 && scrollHT >= 18) scrollHT = scrollHT - 18;	
	document.getElementById("suggestList").scrollTop = scrollHT;

     //방향키를 누른 서제스트 라인의 바탕색을 원래의 색으로 변경
    if ((keyUpDown > 0) && (keyUpDown < itemNodeCount)) {
        var currentSgtID = document.getElementById(cursorFieldID);
        currentSgtID.style.backgroundColor = "#FFFFFF";
    }
    //최대값과 이동할 위치 값 비교
    if (keyUpDown > 0) {
        keyUpDown--;
        cursorFieldID = "sgt" + keyUpDown;
        var nextCodeDesc = document.getElementById(cursorFieldID);
        nextCodeDesc.style.backgroundColor = "#F4F4F4";

		var itemValue = nextCodeDesc.innerText.split(" ");
		document.getElementById("codeCd").value = itemValue[0];
		document.getElementById(inputName).value = itemValue[1];

    }

}

//-----------------------------------------------------------  "KeyDown" 이벤트 발생  
function  fieldKeyDown(evt) {
	//방향키 입력에 따라 커서 이동
	if (evt.keyCode == 40 || evt.keyCode == 38) { 
		
		if (evt.keyCode == 40) {         //아래 방향키
			nextFocus(evt);
		} else {                               //위 방향키
			prevFocus(evt);
		} 
		return;
	}	

}

//-----------------------------------------------------------  "keyUp" 이벤트 발생  
function  fieldKeyUp(evt) {
	
	if (strLen(commChoiceObject(evt).value) < 2)  return;
	
	if (evt.keyCode >= 37 && evt.keyCode <= 40) {		//커서 키
		return;
	}else if(evt.keyCode == 13){									//엔터 키
		if(fieldValue == null)	{
			return;
		}
		
		var itemValue = fieldValue.split(" ")
		commChoiceObject(evt).value = itemValue[1];
		commRemoveChild("suggestList");
		return;
	}

	commRemoveChild("suggestList");
	keyUpDown = -1;

	if(strLen(commChoiceObject(evt).value) >= 2){
		getData();
	}
}

function	getData(){
	
	var createDivTag;												//첫 div 태그 생성여부, "CRT"-> div 태그 생성  
	var suggestDiv;
	var suggestScroll = 0;
	var searchKey = document.getElementById(inputName).value.toUpperCase();

	if (createDivTag != "CRT") {
		suggestDiv = document.createElement("div");
		suggestDiv.id = "suggestList";  
		createDivTag = "CRT";										 //div 태그 두번 생성 방지
	}
	
	suggestDiv.style.pixelLeft = sgtLeft;
	suggestDiv.style.pixelTop = sgtTop;
	suggestDiv.style.pixelWidth = sgtWidth -2;
	
	var listCount = 0;
	
	for(i = 0; i < kospiXmlData.length; i++){
	
		if(kospiXmlData[i].itemName.match(searchKey) || kospiXmlData[i].itemCode.match(searchKey)){
						
			var suggestData = document.createElement("div");
			suggestData.id = "sgt" + listCount;						// "sgt" + 일련번호로 id 부여
			suggestScroll++;												//서제스트 1라인 추가
	
			suggestData.innerHTML = kospiXmlData[i].itemCode.replace(searchKey,"<font color='#FF9900'>" + searchKey + "</font>") + " " + kospiXmlData[i].itemName.replace(searchKey,"<font color='#FF9900'>" + searchKey + "</font>");
			suggestDiv.appendChild(suggestData);
		

			//서제스트 데이터 단위로 마우스 이벤트 리스너 설정
			commAddListener(suggestData, "mouseup", mouseUpSuggest, false);
			commAddListener(suggestData, "mouseover", mouseOverSuggest, false);
			commAddListener(suggestData, "mouseout", mouseOutSuggest, false);
			
			listCount++;
		}
	}
	itemNodeCount = listCount;
	if(listCount == 0) {return;}

	//서제스트 리스트 출력
	if (createDivTag != "") {    //서제스트 데이터가 존재할 때
		
		// 서제스트 데이터가 scrollMax 건이 넘으면, 상하 스크롤 바 표시
		if (suggestScroll > scrollMax) { 
			suggestDiv.style.overflow = "auto";
			suggestDiv.style.pixelHeight   = scrollMax * 18 ;
		}
		//서제스트 데이터 출력
		document.body.appendChild(suggestDiv);
	} 

}


function kospiInitField(th){
	
	if(kospiXmlData == null) return;
	
	inputName = th.name
	var field = document.getElementById(inputName);				//필드명

	//리스너 해제
	commRemoveListener(field, "keyup", fieldKeyUp, false);			
	commRemoveListener(field, "keydown", fieldKeyDown, false);

	//리스너 설정
	commAddListener(field, "keyup", fieldKeyUp, false);			
	commAddListener(field, "keydown", fieldKeyDown, false);

	sgtLeft = getRealOffsetLeft(th)							//레이어 좌측 위치
	sgtTop = getRealOffsetTop(th) + th.offsetHeight;	//레이어 상단 위치
	sgtWidth = $(th).width();									//레이어 폭
	
	fieldValue = null;

}

function outField(v){
	
	if(inputName == undefined ) return;
	
	if(fieldValue == null) fieldValue = v;

	if(document.all["suggestList"] || v){
		
		if(document.getElementById(inputName).value == fieldValue){
			document.getElementById(inputName).value = fieldValue;
		}
		
		commRemoveChild("suggestList");
	}

}

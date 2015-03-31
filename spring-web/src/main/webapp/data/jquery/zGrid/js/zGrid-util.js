zGrid.util = {

	/*
	* trim
	*/
	trim : function(str){
		re = /^\s+|\s+$/g;
		return str.replace(re, '');
	},

	/*
	* 숫자체크
	*/
	numChk : function(str){
		var reg = /^[-0-9]/g ;
		return reg.test(str); 
	},

	/*
	* 한글체크
	*/
	hanChk : function(str){
		var pattern = new RegExp('[^가-힣\x20]', 'i');
		if (pattern.exec(Str) != null) {
		  return false ;
		} else {
		  return true ;
		}
	},

	/*
	* 통화
	*/
	currency : function(str){
		var reg = /(^[+-]?\d+)(\d{3})/; 
		str += '';
		while (reg.test(str))
		str = str.replace(reg, '$1' + ',' + '$2');
		return str;
	},

	/*
	* 문자치환
	*/
	replaceAll : function (inputStr, targetStr, replaceStr){
		var ret = null;
		var regExp = new RegExp(targetStr, "g");
		ret = inputStr.replace(regExp, replaceStr);

		return ret;
	},

	/*
	* 파일사이즈
	*/
	getReadableFileSizeString : function (fileSizeInBytes) {
		var i = -1;
		var byteUnits = [' KB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
		do {
			fileSizeInBytes = fileSizeInBytes / 1024;
			i++;
		} while (fileSizeInBytes > 1024);

		return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
	},

	/*
	* String to int
	*/
	StringToInt : function (str) {
		return (this.numChk(str) == true) ? parseInt(str) : str;
	},
	
	/**
	 *paging
	 */
	urlInfo : {},
	param : {},
	page : 1,
	
	getPage : function(){
		return this.page;
	},
	getParam: function(){
		 return this.param;
	},
	setParam : function(page){
		this.page = page;
		if(zGrid.initData.paging.limit > 0){
			this.param = {
				start : (page - 1) * zGrid.initData.paging.limit,
				limit :  zGrid.initData.paging.limit,
				sortKey : zGrid.sort.key==undefined ? "" : zGrid.sort.key,
				sortValue :zGrid.sort.value==undefined ? "" : zGrid.sort.value
			};
		}

	},
	getUrlInfo : function(){
		return this.urlInfo;
	},
	
	goPage : function(i){
		this.setParam(i);
		zGrid.getGridData(this.urlInfo);
	},
	setUrlInfo : function(){
		$.extend(zGrid.initData.params, this.getParam());
		this.urlInfo= {
			url : zGrid.initData.url,
			params :  zGrid.initData.params
		};
	},
	
	/**
	 * Object,  전체로우수 , 현재페이지
	 */
	paging : function (obj, rowCount, page) {
		
		var str = "";
		var x = zGrid.initData.paging.pageSize;									//보여질 페이지 수
		var tp =Math.ceil( rowCount  / limit );										//전체페이지 수
		var p = page;																		//현재 페이지
		var s = 0;
		var e = 0;
		var d = p % x;									// 현재 페이지 위치

		switch(d){						// 루프의 처음 s 과 마지막 e	
			case 1:						// 현재 페이지가 네비게이션의 처음에 있을때
				s = p;
				e = p + (x - 1);
			break;
	
			case 0:				// 현재 페이지가 마지막에 있을때.
				s = p - (x - 1);
				e = p; 
			break;
	
			default:				// 중간에 꼈을때.
				s = p - (d - 1);
				e = p + (x - d);
		}	

		if(e > tp) e = tp;		// 루프의 마지막이 총페이지를 넘는지 체크

		if(p != 1 ){									//이전
			var step1 = p - 1;
			str +="<a href='#' onClick=\"zGrid.util.goPage(" + step1 + ")\" class='prev'>《 이전</a>";
		}else{
			str +="<span class='prev'>《 이전</span>";
		}


		for(var i = s; i <= e ;i++){
			 if(i == p){
				 str +=" <span class='current'>" + i + "</span>";
			 } else{ 	
				str +=" <a href='#' onClick=\"zGrid.util.goPage(" + i + ")\" class='page'>" + i + "</a>";
			 } 	
		}

		if(p != tp){					//다음
			var step2 = p + 1;
			str +=" <a href='#' onClick=\"zGrid.util.goPage(" + step2 + ")\" class='next'>다음 》</a>";
		}else{
			str +=" <span class='next'>다음 》</span>";
		}

		obj.find(".total span").text("Total : " + rowCount + " [" + p + "/" + tp + "]");
	 	obj.find(".nav").html(str);
		
	},

	/**
	 *console
	 */
	console : function (str) {
	 	$("#console").text(str)
	}
};


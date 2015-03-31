<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<script type="text/javascript" src="/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="/extjs/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css"/>	

<script>

var store = null; 													//data store
var grid = null; 													//grid
var fields = null;
var storeUrl = "./authCodeJson.do";						//검색페이지 URL

var store2 = null; 												
var grid2 = null; 													
var fields2 = null;
var storeUrl2 = "./authUserRoleJson.do";						

Ext.onReady(function(){

	//=============================================================================== 마스터 권한코드

    // Data Store 설정
	
	store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: storeUrl,
   	     	method: 'POST'
		}),

		reader: new Ext.data.JsonReader(
			{ root: 'dataList'  },
			fields = Ext.data.Record.create([
				'roleCd','roleNm',{name: 'roleStp', type: 'int'},'editCd'
			])
		)
	});
     

    // Grid 패널/컬럼 정의
	var sm = new Ext.grid.CheckboxSelectionModel( { id: "check", width:30 } );		//체그박스
	var fm = Ext.form;
	grid = new Ext.grid.EditorGridPanel({
        el:'GridDataList',
		height:500,
        width:344,
        title:'마스터 권한코드',
        store: store,
        loadMask: {msg:'로딩 ...'},
        stripeRows: true,			//로우구분 컬로 표시
		sm: sm,

		columns: [
			sm,
            {header: "코드", width: 100, align: 'center', sortable: true, dataIndex: 'roleCd', editor: new fm.TextField({allowBlank: true}) },
            {header: "코드명", width: 160, align: 'left', sortable: true, dataIndex: 'roleNm',editor: new fm.TextField({allowBlank: true}) },
            {header: "순서", width: 50, align: 'center', sortable: true, dataIndex: 'roleStp', editor: new fm.NumberField( { allowBlank : false, allowNegative : false }) }
        ],

    }); 

	grid.addListener("rowmousedown", function() { return false; } );
	
    // render it
    grid.render();

	 // trigger the data store load
	store.load();



	//=============================================================================== 권한목록

    // Data Store 설정
	
	store2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: storeUrl2,
   	     	method: 'POST'
		}),

		reader: new Ext.data.JsonReader(
			{ root: 'dataList'  },
			fields2 = Ext.data.Record.create([
				'seqNo','userId','userNm','roleNm'
			])
		)
	});
     

    // Grid 패널/컬럼 정의
	grid2 = new Ext.grid.GridPanel({
        el:'GridDataList2',
		height:500,
        width:500,
        title:'유저권한목록',
        store: store2,
        loadMask: {msg:'로딩 ...'},
        stripeRows: true,

		viewConfig: {
			forceFit: true 				// column width 자동조정 
		},

		columns: [
            {header: "아이디", width: 90, align: 'center', sortable: true, dataIndex: 'userId',renderer: renderId },
            {header: "이름", width: 90, align: 'center', sortable: true, dataIndex: 'userNm' },
            {header: "권한", width: 338, align: 'left', sortable: true, dataIndex: 'roleNm' }
        ],

    }); 

    grid2.render();

	store2.load();

	//------------------------------------------------------  사용자 함수
    function renderId(value,p,record){
	    return String.format('<a href="javascript:addRole(\'' + record.data.userId + '\')">{0}</a>', value,record.data.userId);
    }

});




//-------------------------------------------  권한코드 수정
function saveRecod(){
	
	zpop.confirm("저장하겠습니까 ?",function(){
		modifyRecordProc();
	});
	
}
function modifyRecordProc(){

	var params = {lstRoleCd : [],lstRoleNm : [],lstRoleStp : [],lstEditCd : []};

	var modifyRecords = store.getModifiedRecords();	//수정된 레코드 모두 찾기
	var len = modifyRecords.length;

	if(len > 0){
		for(var i = 0; i < len ; i++){
			params.lstRoleCd.push( modifyRecords[i].get('roleCd') );
			params.lstRoleNm.push( modifyRecords[i].get('roleNm') );
			params.lstRoleStp.push( modifyRecords[i].get('roleStp') );
			params.lstEditCd.push( modifyRecords[i].get('editCd') );
		}

		$.ajax({
			url: 'authCodeInsert.do',
			type: 'POST',
			data: jQuery.param(params,true),
			timeout : 3000,
			error: function(){
				zpop.alert("저장에 실패하였습니다.");
			},
			success: function(msg){
				store.load();
			}
		});

		store.commitChanges();					//isDirty Flag 없애기
	
	}else{
		zpop.alert("변경된 자료가 없습니다.");
		
	}
}

//----------------------------------------------- 권한코드 등록
function addRecord(){
	var r = new fields( {
			roleCd : 'AAA',
			roleNm : '새권한',
			roleStp : 99,
			editCd : 'I',					// 등록 상태
		});
		grid.stopEditing();
		store.insert(0, r);
		grid.startEditing(0, 0);		// 등록할 rowIndex, colIndex 위치
}

//------------------------------------------------ 권한코드 삭제
function deleteRecord() {
	
	if(grid.getSelectionModel().getSelections().length == 0) return;

	zpop.confirm("삭제하시겠습니까 ?",function(){
		deleteRecordProc();
	});
	
}
function deleteRecordProc(){

	var params ={lstRoleCd: []};

	var chk = grid.getSelectionModel().getSelections();
	 for( var i = 0; i < chk.length; i++) {
		params.lstRoleCd.push( chk[i].data.roleCd);   
	} 
 	
	if(chk.length > 0){
		$.ajax({
			url: 'authCodeDelete.do',
			type : 'POST',
			data : jQuery.param(params,true),
			timeout : 3000,

			error: function(){
				zpop.alert("삭제에 실패하였습니다.");
				
			},
			success: function(msg){
				store.load();
			}
		});

	}else{
		zpop.alert("대상을 선택해주세요.");
	}
}


function addRole(userId){
	
	zpop.iframe({
		title : "유저권한등록",
		width:350,
		height:320,
		url: './authCodeRegForm.do?userId=' + userId,
		modal : true,
		header: true
	},function(){
		$("#zpopIframe")[0].contentWindow.setRoleCd();
	})
}


</script>
	
<div id="wrapper" style="width:900px">
	
	<!-- 권한코드 -->
	<div style="float:left">
		<div id="GridDataList"></div>	

		<div style="margin-top:10px;">
			<div style="float:left">
			<button class="word2" onClick="deleteRecord()">삭제</button>
			</div>

			<div style="float:right">
			<button class="word2" onClick="addRecord()">등록</button>
			<button class="word2" onClick="saveRecod()">저장</button>
			<button class="word4" onClick="store.load()">새로고침</button>	
			</div>
		</div>
	</div>

	<!-- 유저권한 -->
	<div style="float:left;margin-left:50px">
		<div id="GridDataList2"></div>
		
		<div style="margin-top:10px;">
			<div style="float:right">
			<button class="word2" onClick="addRole('')">등록</button>
			<button class="word4" onClick="store2.load()">새로고침</button>	
		</div>
		</div>

	</div>

	
</div>

<div id='authReg' style="display: none">
</div>



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
var storeUrl = "./kospiJson.do";								//검색페이지 URL
var rowCount = 20;												//페이지 로우수

Ext.onReady(function(){

    // Data Store 설정
	
	store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: storeUrl,
   	     	method: 'POST',
			timeout : 2000
		}),

		reader: new Ext.data.JsonReader(
			{totalProperty : 'totalCount', root : 'dataList'  },
			fields = Ext.data.Record.create([
				{name: 'num', type: 'int'},'codeCd','codeNm','price','regDate'
			])
		),
			remoteSort: true,
			sortInfo: {field:'codeNm', direction:'ASC'}
	});

	// 페이징
    var pagingBar = new Ext.PagingToolbar({
        pageSize: rowCount,
        store: store,
        displayInfo: true,
        beforePageText : "페이지", 
        afterPageText  : "/ {0}",
        displayMsg: '총 {2} [{0} - {1}]',
        emptyMsg: "자료가 없습니다." 
    });
     

    // Grid 패널/컬럼 정의
	var sm = new Ext.grid.CheckboxSelectionModel( { id: "check", width:30 } );		//체그박스
	var fm = Ext.form;
	grid = new Ext.grid.EditorGridPanel({
        el:'GridDataList',
		height:620,
        width:600,
        store: store,
        loadMask: {msg:'로딩 ...'},
        stripeRows: true,			//로우구분 컬로 표시
		sm: sm,

		columns: [
			//new Ext.grid.RowNumberer({width: 30}),
			sm,
			{id: 'no',header: "번호", dataIndex: 'num', width: 55, align: 'center'},
            {header: "코드", width: 120, align: 'center', sortable: true, dataIndex: 'codeCd' },
            {header: "코드명", width: 190, align: 'left', sortable: true, dataIndex: 'codeNm' },
            {header: "현재가", width: 80, align: 'right', sortable: true, dataIndex: 'price',renderer : renderPrice },
            {header: "수정일", width: 120, align: 'center', dataIndex: 'regDate' }
        ],

		bbar: pagingBar

    }); 

	grid.addListener("rowmousedown", function() { return false; } );
	
    // render it
    grid.render();

	 // trigger the data store load
	store.load({params:{start:0, limit:rowCount}});
	
	
	//------------------------------------------------------  사용자 함수
	function renderPrice(value,p,record){
		return currency(parseInt(record.data.price))
	}

});

//------------------------------------------------ 종목코드 삭제
function deleteRecord() {
	
	if(grid.getSelectionModel().getSelections().length == 0) return;

	Ext.MessageBox.confirm('확인', '삭제하겠습니까?',deleteRecordProc);
}

function deleteRecordProc(btn){
	if(btn == 'no') return;

	var params ={arrCodeCd : []};

	var chk = grid.getSelectionModel().getSelections();
	 for( var i = 0; i < chk.length; i++) {
		params.arrCodeCd.push( chk[i].data.codeCd);   
	} 
	
	if(chk.length > 0){
		jQuery.ajax({
			url: 'kospiDelete.do',
			type : 'POST',
			data : jQuery.param(params,true),
			timeout : 5000,

			error: function(){
				Ext.MessageBox.alert('알림', '삭제에 실패하였습니다.');
			},
			success: function(msg){
				store.load({params:{start:0, limit:rowCount}});
			}
		});

	}else{
		Ext.MessageBox.alert('알림', '대상을 선택해주세요');
	}
}

function excelExport(){
	document.location.href = "./kospiExcel.do";
}

function search(){
	store.proxy.conn.url = storeUrl + "?" + $("#frm").serialize();
	store.load({params:{start:0, limit:rowCount}});
}

function excelImport(){

	$("#fileFrame").attr("src","kospiExcelForm.do");;

	regDialog("fileUpload","파일업로드",480,200,function(){
		$('#fileFrame')[0].contentWindow.send();
	})

}

</script>
	
<div id="wrapper">
	
	<div class="searchBox" style="width:589px;">
		<form name="frm" id="frm" onSubmit="search();return false">
		
		<select name="flag" id="flag" onChange="search()" style="width:70px">
			<option value="01">코스피</option>
			<option value="02">코스닥</option>
		</select>

		<select name="searchKey" id="searchKey" style="width:70px">
			<option value="codeNm">코드명</option>
			<option value="codeCd">코드</option>
		</select>

		<input type="text" name="searchValue" class="inputBox" style="ime-mode:active">
		<button class="word2">검색</button>
		</form>
	</div>

	<div id="GridDataList"></div>	

	<div style="margin-top:10px;width:602px">
		<div style="float:left">
		<button class="word2" onClick="deleteRecord()">삭제</button>
		</div>

		<div style="float:right">
		<button class="excel_export" onClick="excelExport()">엑셀다운로드</button>	
		<button class="excel_import" onClick="excelImport()">엑셀업로드</button>
		</div>
	</div>

	
</div>


<div id='fileUpload' style="display: none">
<iframe  src="./kospiExcelForm.do" id="fileFrame" scrolling="no" frameborder="0" style="width:100%;height:100px"></iframe>
</div>


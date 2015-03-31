 <%@ page contentType="text/html; charset=UTF-8" %>
 <%@ page import="java.io.*" %>
 <%@ page import="java.text.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="org.apache.poi.poifs.filesystem.POIFSFileSystem" %>
<%

String filePath = application.getRealPath("/") + "data/utility/excel";
StringBuffer sb = new StringBuffer();

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
DecimalFormat df = new DecimalFormat("###.##");
File file  = new File( filePath, "sample.xls" ) ;

try {
	
	POIFSFileSystem fs  =  new POIFSFileSystem(new FileInputStream( file ));
	
	//워크북을 생성
	HSSFWorkbook wb = new HSSFWorkbook(fs);
	
	//생성된 워크북을 이용하여 시트 수만큼 돌면서 엑셀 시트 하나씩을 생성
	int sheetNum = wb.getNumberOfSheets();
	for (int k = 0; k < sheetNum; k++) {
		HSSFSheet sheet = wb.getSheetAt(k);
		
		//생성된 시트를 이용하여 그 행의 수만큼 돌면서 행을 하나씩 생성
		int rows = sheet.getPhysicalNumberOfRows();
		for (int r = 0; r < rows; r++) {
			HSSFRow row   = sheet.getRow(r);
			
			//생성된 행을 이용하여 그 셀의 수만큼 돌면서 셀을 하나씩 생성
			int cells = row.getPhysicalNumberOfCells();
			for (short c = 0; c < cells; c++) { 
		    	HSSFCell cell  = row.getCell(c); 
		   		if (cell != null) {
		   			
		   			switch(cell.getCellType()){
		   	       
			   	       case 0:
			   	    	   //날자형
				   	    	if( cell.getCellStyle().getDataFormat() > 13 ){
					   	    	sb.append( sdf.format(cell.getDateCellValue()) + " : " );
					   	    //숫자형
				   	    	}else{
					   	    	sb.append( df.format(cell.getNumericCellValue()) + " : " );
				   	    	}
			   	       break;
			   	       
			   	       case 1:		//문자형
			   	    	sb.append(cell.getStringCellValue() + " : ");
			   	       break;
		   	         
		   	      }
		   			
		   		}
		   		 
			}
			
			sb.append("<br>");
								
		}

	}
	
	
}catch(Exception e){
	e.printStackTrace();
	sb.setLength(0);
	sb.append("형식이 올바르지 않습니다.");			
}
%>

<!DOCTYPE html>
<HTML >
<HEAD>
<TITLE> Java </TITLE>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="/css/global.css" type="text/css">
	<link type="text/css" href="/css/redmond/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" />	
	<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
</HEAD>

<script>
$(document).ready(function () {

	$( "button" ).button({
		icons: {
			primary: "ui-icon-document"
		}
	});

});
</script>

</head>


<body>

<div style="border:1px solid #8E8E8E;background-color:#EEEEEE;width:500px;padding:5px">
Path : <%=file.getAbsolutePath() %>
</div>

<hr>
<%=sb.toString() %>
<hr>

<button onClick="location.href='./excelWrite.jsp'">엑셀생성</button>


</body>

</html>
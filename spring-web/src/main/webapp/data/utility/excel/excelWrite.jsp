 <%@ page contentType="text/html; charset=UTF-8" %> <%@
  page import="java.io.*" %> <%@
  page import="java.text.*" %> <%@
  page import="java.util.*" %><%@
  page import="org.apache.poi.hssf.usermodel.*" %><%@
  page import="org.apache.poi.hssf.util.*" %><%@
  page import="org.apache.poi.poifs.filesystem.POIFSFileSystem" %><%

Date currentDate = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
String date = sdf.format(currentDate);

String excelFile = "sample_" + date + ".xls";
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=" + excelFile );

String [] header = {"이름","가격","날짜"};
int [] hw = {3000,3000,5000};

HashMap hm = new HashMap();
hm.put("name","알리");
hm.put("price",90000);
hm.put("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate));

ArrayList list = new ArrayList();
for(int i = 0; i < 10; i++){
	list.add(hm);
}

//-------------------------------------------------------------------


HSSFWorkbook wb = new HSSFWorkbook();						//워크북을 생성
HSSFSheet sheet = wb.createSheet("sheet1");						//시트 생성


//헤더 스타일
HSSFCellStyle style = wb.createCellStyle();							
style.setFillPattern ((short) 1);												
style.setFillForegroundColor (HSSFColor.GREY_25_PERCENT.index);	// 배경색 지정 
style.setAlignment(HSSFCellStyle.ALIGN_CENTER);							//정렬

//헤더 생성
HSSFRow initRow = sheet.createRow(0);			
int headerCount = header.length;
for(int i = 0; header !=null && i < headerCount; i++){
	
	//셀 너비
	sheet.setColumnWidth((short)i, (short)hw[i]);
	
	HSSFCell cell = initRow.createCell((short)i);
	cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	cell.setCellStyle(style);
	cell.setCellValue(header[i]);
}

//데이터 생성
for(int i = 0; i < list.size(); i++){
	
	int k = 0;
	HashMap ehm = (HashMap)(list.get(i));
	
	HSSFRow row = sheet.createRow( i+1 );
	
	HSSFCell col1 = row.createCell((short)k++);
	col1.setEncoding(HSSFCell.ENCODING_UTF_16);
	col1.setCellValue( (String)ehm.get("name") + (i+1));

	HSSFCell col2 = row.createCell((short)k++);
	col2.setCellValue( (Integer)ehm.get("price") + (i+1));
	
	HSSFCell col3 = row.createCell((short)k++);
	col3.setCellValue( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate) );
	
}

OutputStream fileOut = null;

try{	
	out.clear();		//jsp 만해당
	out = pageContext.pushBody(); //jsp 만해당
	
	fileOut = response.getOutputStream();
	wb.write(fileOut);
}catch (Exception e){
	e.getStackTrace();
}finally{
	if(fileOut != null)	try {fileOut.close();} catch (IOException e) {}
}


%>


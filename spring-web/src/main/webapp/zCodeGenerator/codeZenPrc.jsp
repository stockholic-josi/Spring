 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ include  file="zdb.inc.jsp" %>

<%

String dbType = chkNull(request.getParameter("dbType"));
String hostNm = chkNull(request.getParameter("hostNm"));
String dbNm = chkNull(request.getParameter("dbNm"));
String tbNm = chkNull(request.getParameter("tbNm"));
String id = chkNull(request.getParameter("id"));
String pw = chkNull(request.getParameter("pw"));
String classNm = chkNull(request.getParameter("classNm"));
String classAlias = classNm.substring(0,1).toLowerCase() + classNm.substring(1);
String pkgNm = chkNull(request.getParameter("pkgNm"));
String vmNmPath = chkNull(request.getParameter("vmNmPath"));
String keyColumn = chkNull(request.getParameter("keyColumn"));				//Option key
String viewColumn = chkNull(request.getParameter("viewColumn"));			//Option view
String formColumn = chkNull(request.getParameter("formColumn"));			//Option	 form

Connection conn = null;
PreparedStatement  pstmt = null;
ResultSet rs = null;


List list = getColumn(dbType, hostNm, dbNm, tbNm,id,pw);			//테이블 컬럼 목록


if(list.size() ==0) {
	out.println("<p style='color:#FF6633;font-size:10pt;font-weight:bold;text-align:center;margin-top:100px'>입력정보가 일치하지 않음 </p>");
	out.println("<p style='color:#0000FF;font-size:10pt;font-weight:bold;text-align:center;'><a href='javascript:history.back()'>뒤로가기</a></p>");
	 return;
}

List cList = new ArrayList();					//Camel 변형 

for( int i =0; i < list.size(); i++ ){
	
	HashMap hm = (HashMap)list.get(i);
	HashMap cm = new HashMap();
	
	cm.put("columnName",getCamelCase(hm.get("columnName").toString()));
	cm.put("dataType",getType(hm.get("dataType").toString()));
	cm.put("columnComment",hm.get("columnComment").toString());
	cList.add(cm);
}

File fileTemplete = new File(application.getRealPath("/") + "/zCodeGenerator/templete");		//fiie templete
//File fileOutput = new File(application.getRealPath("/") + "/zCodeGenerator/output");			//file out put

VelocityEngine ve = initVelocity(fileTemplete.getPath());


//---------------------------------------------------------------DTO
String dtoFileNm = "dto";
Template templateDto = ve.getTemplate("java/" + dtoFileNm + ".vm");
VelocityContext contextDto = new VelocityContext();
contextDto.put("pkgNm", pkgNm);
contextDto.put("tb", tbNm.toUpperCase());
contextDto.put("classNm", classNm);
contextDto.put("classAlias", classAlias);
contextDto.put("cList", cList);
StringWriter writerDto = new StringWriter();
templateDto.merge(contextDto, writerDto);

//String dtoOutPath = fileOutput + "/" + pkgNm.replaceAll("\\.","/") + "/" + dtoFileNm + "/";
//mkDir(dtoOutPath);
//setFileWrite(writerDto.toString(),dtoOutPath +classNm + ".java","utf-8"); 

//--------------------------------------------------------------- Controller
String controllerFileNm = "controller";
StringBuffer params = new StringBuffer();
int k = 0;
for( String kc : keyColumn.split(",") ){			//링크 파라미터 생성
	if(!kc.equals("")){
		String camelCase = getCamelCase(kc);
		params.append( (k==0?"\"?":" + \"&") + getCamelCase(kc) + "=\" + dto.get" + camelCase.substring(0,1).toUpperCase() + camelCase.substring(1) + "()");
		k++;
	}
}
Template templateController= ve.getTemplate("java/" + controllerFileNm + ".vm");
VelocityContext contextController= new VelocityContext();
contextController.put("classNm", classNm);
contextController.put("classAlias", classAlias);
contextController.put("pkgNm", pkgNm);
contextController.put("vmNmPath", vmNmPath);
contextController.put("params", params.toString());
StringWriter writerController= new StringWriter();
templateController.merge(contextController, writerController);

//String controllerOutPath = fileOutput + "/" + pkgNm.replaceAll("\\.","/") + "/" + controllerFileNm + "/";
//mkDir(controllerOutPath);
//setFileWrite(writerController.toString(),controllerOutPath +classNm + "Controller.java","utf-8");


//---------------------------------------------------------------Service
String serviceImplFileNm = "service";
Template templateServiceImpl= ve.getTemplate("java/" + serviceImplFileNm + ".vm");
VelocityContext contextService= new VelocityContext();
contextService.put("classNm", classNm);
contextService.put("classAlias", classAlias);
contextService.put("pkgNm", pkgNm);
StringWriter writerService= new StringWriter();
templateServiceImpl.merge(contextService, writerService);

//setFileWrite(writerServiceImpl.toString(),serviceOutPath +classNm + "ServiceImpl.java","utf-8");


//---------------------------------------------------------------MyBatis
String xmlFileNm = "mapper";

StringBuffer where = new StringBuffer();
int w = 0;
for( String kc : keyColumn.split(",") ){			//조건 생성
	if(!kc.equals("")){
		String camelCase = getCamelCase(kc);
		where.append( (w==0?"WHERE ":" AND " ) + kc + " = #{" + getCamelCase(kc) + "}" );
		w++;
	}
}


Template templateMyBatis = ve.getTemplate("java/" + xmlFileNm + ".vm");
VelocityContext contextMyBatis = new VelocityContext();
contextMyBatis.put("type", dbType);
contextMyBatis.put("classNm", classNm);
contextMyBatis.put("classAlias", classAlias);
contextMyBatis.put("tb", tbNm);
contextMyBatis.put("pkgNm", pkgNm);
contextMyBatis.put("tList", list);
contextMyBatis.put("cList", cList);
contextMyBatis.put("where", where.toString());
StringWriter writerMyBatis = new StringWriter();
templateMyBatis.merge(contextMyBatis, writerMyBatis);

//String xmlOutPath = fileOutput + "/" + pkgNm.replaceAll("\\.","/") + "/" + daoFileNm + "/mapper/";
//mkDir(xmlOutPath);
//setFileWrite(writerMyBatis.toString(),xmlOutPath +classNm + ".xml","utf-8");


//--------------------------------------------------------------- List.vm
//----------- 링크 파라미터 생성
StringBuffer link = new StringBuffer();
int vk = 0;
for( String kc : keyColumn.split(",") ){			
	if(!kc.equals("")){
		String camelCase = getCamelCase(kc);
		link.append( (vk==0?"":"&") + getCamelCase(kc) + "=$!list." + getCamelCase(kc));
		vk++;
	}
}

//----------- 페이징 생성
StringBuffer paging = new StringBuffer();
paging.append("#if($search.total != 0)\r\n");
paging.append("#set($searchParam = '&searchKey=&searchValue=')\r\n");
paging.append("<div id='paging'>\r\n");
paging.append("	$PagingUtil.paging($search.totalPage,$search.p,$searchParam,'" + classAlias + "List.do')\r\n");
paging.append("</div>\r\n");
paging.append("#end");

Template templateList= ve.getTemplate("vm/list.vm");
VelocityContext contextList= new VelocityContext();
contextList.put("cList", cList);
contextList.put("classNm", classNm);
contextList.put("classAlias", classAlias);
contextList.put("link", link);
contextList.put("paging", paging.toString());
StringWriter writerList= new StringWriter();
templateList.merge(contextList, writerList);

//String listOutPath = fileOutput + "/vm" + vmNmPath;
//mkDir(listOutPath);
//setFileWrite(writerList.toString(),listOutPath + "/" + classAlias + "List.vm","utf-8");

//--------------------------------------------------------------- Read.vm
Template templateRead= ve.getTemplate("vm/read.vm");
VelocityContext contextRead= new VelocityContext();
contextRead.put("cList", cList);
contextRead.put("classNm", classNm);
contextRead.put("classAlias", classAlias);
contextRead.put("link", link.toString().replaceAll("list","params"));
StringWriter writerRead= new StringWriter();
templateRead.merge(contextRead, writerRead);

//setFileWrite(writerRead.toString(),listOutPath + "/" + classAlias + "Read.vm","utf-8");


//--------------------------------------------------------------- Form.vm
//----------- key input 생성
StringBuffer input = new StringBuffer();
for( String kc : keyColumn.split(",") ){			
	if(!kc.equals("")){
		String camelCase = getCamelCase(kc);
		input.append("<input type=\"hidden\" name=\""+ getCamelCase(kc) + "\" value=\"$!params." + getCamelCase(kc) + "\">");
	}
}

Template templateForm= ve.getTemplate("vm/form.vm");
VelocityContext contextForm= new VelocityContext();
contextForm.put("cList", cList);
contextForm.put("kList", keyColumn.split(","));
contextForm.put("classNm", classNm);
contextForm.put("classAlias", classAlias);
contextForm.put("input", input);
StringWriter writerForm= new StringWriter();
templateForm.merge(contextForm, writerForm);

//setFileWrite(writerForm.toString(),listOutPath + "/" + classAlias + "Form.vm","utf-8");

%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: zBorwser :::</title>

<link type="text/css" rel="stylesheet" href="css/SyntaxHighlighter.css"></link>
<script language="javascript" src="js/shCore.js"></script>
<script language="javascript" src="js/shBrushJava.js"></script>
<script language="javascript" src="js/shBrushXml.js"></script>
<script language="javascript" src="js/shBrushSql.js"></script>

<script language="javascript">
onload = function(){
	dp.SyntaxHighlighter.ClipboardSwf = 'js/clipboard.swf';
	dp.SyntaxHighlighter.HighlightAll('code');
}
</script>

<style>
.title{font-size: 15px;font-weight: bold;margin-top:20px;font-family: "Consolas", "Courier New", Courier, mono, serif;}
.sourceWrapper{height:280px;overflow: auto;}
</style>

</head>
<body>
 
<div class="title">■ Dto - <%=pkgNm %>.dto.<%=classNm %>.java</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="java" ><%=writerDto %></textarea></div>

<div class="title">■ Controller - <%=pkgNm %>.controller.<%=classNm %>Controller.java</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="java" ><%=writerController %></textarea></div>  

<div class="title">■ Service - <%=pkgNm %>.service.<%=classNm %>Service.java</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="java" ><%=writerService %></textarea></div>

<div class="title">■ Mybatis XML - com.taxholic.dao.mapper.*.<%=classNm %>.xml</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="xml" ><%=writerMyBatis %></textarea></div>

<div class="title">■ <%=classNm %>List.vm</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="xml" ><%=writerList %></textarea></div>

<div class="title">■ <%=classNm %>Read.vm</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="xml" ><%=writerRead %></textarea></div>

<div class="title">■ <%=classNm %>Form.vm</div>
<div class="sourceWrapper"><textarea style='width:100%;height:150px' name="code" class="xml" ><%=writerForm %></textarea></div>



</body>
</html>




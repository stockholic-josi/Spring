 <%@ page contentType="text/html; charset=UTF-8" %>
 <%
 HttpSession ses = request.getSession(true);
 
 String CAPCHA_SESSION = (String) ses.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY);
 
 
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

</script>

</head>


<body>


<table cellspacing=5>
<tr>
	<td>
		<img src="/Captcha.jpg"> 
	</td>
	<td>
		CAPCHA_SESSION : <%=CAPCHA_SESSION%>
	</td>
</tr>
</table>

출처 : <a href="http://simplecaptcha.sourceforge.net/index.html" target="_blank">http://simplecaptcha.sourceforge.net/index.html</a>


<pre>
	
		
&lt;servlet&gt;
	&lt;servlet-name&gt;Captcha&lt;/servlet-name&gt;
	&lt;display-name&gt;Captcha&lt;/display-name&gt;
	&lt;servlet-class&gt;nl.captcha.servlet.CaptchaServlet&lt;/servlet-class&gt;
&lt;/servlet&gt;
&lt;servlet-mapping&gt;
	&lt;servlet-name&gt;Captcha&lt;/servlet-name&gt;
	&lt;url-pattern&gt;/Captcha.jpg&lt;/url-pattern&gt;
&lt;/servlet-mapping&gt;





&lt;!--border around captcha. Legal values are yes or no. Defaults to yes Other values will be ignored--&gt;

&lt;init-param&gt;
&lt;param-name&gt;cap.border&lt;/param-name&gt;
&lt;param-value&gt;yes&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!--color of the border. Legal values are r,g,b(and optional alpaha) or white,black,etc. Other values will be ignored--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.border.c&lt;/param-name&gt;
&lt;param-value&gt;black&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!--thickness of the border around captcha. Legal values are &gt; 0 other values will be ignored. Defaults to one--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.border.th&lt;/param-name&gt;
&lt;param-value&gt;1&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!-- the image producer. Currently only one--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.cap.producer&lt;/param-name&gt;
&lt;param-value&gt;nl.captcha.obscurity.imp.DefaultCaptchaIml&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!-- the text producer. defaults to a random character thing. But there is a first name generator aswell--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.text.producer&lt;/param-name&gt;
&lt;param-value&gt;Default&lt;/param-value&gt;
&lt;!--&lt;param-value&gt;nl.captcha.text.FiveLetterFirstNameTextCreator&lt;/param-value&gt;--&gt;
&lt;/init-param&gt;

&lt;!-- incase the default generator is chosen. The characters that will create the string can be configured aswell please--&gt;
&lt;!-- notice that if japanese or chinese characters are chosen the fonts chosen below will have to support these--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.char.arr&lt;/param-name&gt;
&lt;param-value&gt;2,3,4,6,7,8,a,b,c,d,e,f,g,h,k,o,r,s,t,x,y&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!--if default captcha producer is slelected this value represents the amount of chars that are supplied in the captcha. values below 2 are not exepted when omitted defaults to 5--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.char.arr.l&lt;/param-name&gt;
&lt;param-value&gt;7&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!-optional font array for the captcha--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.font.arr&lt;/param-name&gt;
&lt;param-value&gt;Arial,Helvetica,Courier,TimesRoman&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!-- image size--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.font.size&lt;/param-name&gt;
&lt;param-value&gt;40&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;!-- font color. values can be either red,yellow,blue,cya etc or r,g,b (optional alpha value)--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.font.color&lt;/param-name&gt;
&lt;param-value&gt;166,31,255&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;init-param&gt;
&lt;param-name&gt;cap.distortionImp&lt;/param-name&gt;
&lt;param-value/&gt;
&lt;/init-param&gt;

&lt;init-param&gt;
&lt;param-name&gt;cap.backgroundImp&lt;/param-name&gt;
&lt;param-value/&gt;
&lt;/init-param&gt;

&lt;!--values can be either red,yellow,blue,cya etc or r,g,b,alpha value--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.background.c.to&lt;/param-name&gt;
&lt;param-value&gt;255,255,255&lt;/param-value&gt;
&lt;/init-param

&lt;!--values can be either red,yellow,blue,cya etc or r,g,b,alpha value--&gt;
&lt;init-param&gt;
&lt;param-name&gt;cap.background.c.from&lt;/param-name&gt;
&lt;param-value&gt;75,75,255&lt;/param-value&gt;
&lt;/init-param&gt;

</pre>

	


</body>

</html>
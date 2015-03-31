package com.taxholic.core.util;

import java.io.*;




import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

public class SysUtil {
	
	/***
	 * base64Encoder <p>
	 * @param sOriginStr
	 * @return
	 */
	public static String  base64Encoder(String sOriginStr){
		String sEncoderStr = "";
		try{
			Base64 encoder = new Base64();
	        byte[] byStr=sOriginStr.getBytes();;
	        sEncoderStr = encoder.encodeToString(byStr);
		}catch(Exception e){}
		
		return sEncoderStr;
	}

	/***
	 * base64Decoder <p>
	 * @param sEncoderStr
	 * @return
	 */
	public static String  base64Decoder(String sEncoderStr){
		String sDecoderStr = "";
		try{
			Base64 decoder = new Base64();
			byte[] byStr = decoder.decode(sEncoderStr);;
			sDecoderStr = new String(byStr);
		}catch(Exception e){}
		
		return sDecoderStr ;
	}
	
	/***
	 * 시스템 환경정보 <p>
	 * @param env  (ex ALL:모든정보, OS:OS 정보)
	 * @return String
	 */
	public static String getEnv(String env){
		String sCommend = "";
        String sVersion = getOS();
        BufferedReader bufferedreader = null;
        
        if(sVersion.equals("Windows NT") || sVersion.equals("Windows 2000") || sVersion.equals("Windows XP")){
            sCommend = "cmd.exe /C set";
        }else if(sVersion.equals("Windows 95") || sVersion.equals("Windows 98")){
            sCommend = "command.exe /C set";
	 	}else if(sVersion.equals("FreeBSD") || sVersion.equals("Solaris") || sVersion.equals("Linux") || sVersion.equals("UNIX") || sVersion.equals("SunOS")){
            sCommend = "env";
	 	}
        		
        try{
            Process process = Runtime.getRuntime().exec(sCommend);
            bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }catch(IOException ioexception){
            System.out.println(ioexception);
        }
                
        String envKey = "";
        String envValue = "";
        boolean flag = false;
         
        if(env.equals("ALL")){
        	StringBuffer dataList = new StringBuffer();
        	try{
	            while((envKey = bufferedreader.readLine()) != null) {
	            	dataList.append(envKey + "\n");
	            }
	        }catch(IOException ioexception1){}
	        
	        return dataList.toString();
        }else{
        	try{
	            while((envKey = bufferedreader.readLine()) != null  && !flag) {
	
	                StringTokenizer stringtokenizer = new StringTokenizer(envKey, "=");
	                while(stringtokenizer.hasMoreElements()) {
	                    if(stringtokenizer.nextToken().equals(env)){
	                        envValue = stringtokenizer.nextToken();
	                        flag = true;
	                    }
	                }
	            }
	        }catch(IOException ioexception1){}
	        
	        return envValue;
        }          
	}
	
	/**
	 * 콘솔 명령 결과 출력
	 * @param cmd
	 * @return String
	 */
	public static String getStream(String cmd){
		
		StringBuffer dataList = new StringBuffer();

		Runtime rt = Runtime.getRuntime();
		Process ps = null;
		
		try{
			ps = rt.exec(cmd);

			BufferedReader br = new BufferedReader(
			new InputStreamReader(
			new SequenceInputStream(ps.getInputStream(), ps.getErrorStream())));

			while((br.readLine()) != null){
				dataList.append(br.readLine() + "\n");
			}
			br.close();

		}catch(IOException e){}
		
		return dataList.toString();
	}
	

	/**
	 * 이미지 리사이징
	 * @param originalFile
	 * @param thumbnailFile
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param quality
	 * @throws Exception
	 */
	public static void makeThumbNail(String originalFile, String thumbnailFile, int thumbWidth, int thumbHeight) throws Exception{
		
		Image image = javax.imageio.ImageIO.read(new File(originalFile));

		double thumbRatio = (double)thumbWidth / (double)thumbHeight;
		int imageWidth    = image.getWidth(null);
		int imageHeight   = image.getHeight(null);
		
		double imageRatio = (double)imageWidth / (double)imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int)(thumbWidth / imageRatio);
		} else {
			thumbWidth = (int)(thumbHeight * imageRatio);
		}
		   
		if(imageWidth < thumbWidth && imageHeight < thumbHeight) {
			thumbWidth = imageWidth;
			thumbHeight = imageHeight;
		} else if(imageWidth < thumbWidth){
			thumbWidth = imageWidth;
		}else if(imageHeight < thumbHeight){
			thumbHeight = imageHeight;
		}
		
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setBackground(Color.WHITE);
		graphics2D.setPaint(Color.WHITE); 
		graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
   
		 javax.imageio.ImageIO.write(thumbImage, "JPG", new File(thumbnailFile));
		       
	}
	
	
	/***
	 * 파일 사이즈 <p>
	 * @param file
	 * @return String
	 */
	public static  String getFileSize(Long file){	

		String size = "0";

		if(file < 1024){
		//	size = file.toString();
			size = file + " B";
		}else if(file >= 1024 && file < 1024 * 1024){
			size = String.format("%.2f", (double)file / 1024 ) + " KB";
		}else{
			size = String.format("%.2f", (double)file / 1024 ) + " MB";
		}

		return size;
	}
	
	/****
	 * OS 종류 <p>
	 * @return String
	 */
	 public static String getOS(){
		 return System.getProperty("os.name");
	 }
	
	/***
	 * 프로퍼티 <p>
	 * @param pptKey : 프로퍼티 키값<br>
	 * @param pptFile : 프로퍼티 파일명  ex) "G:/project/soft/WEB-INF/classes/lib/config.properties"<br>
	 * @return String
	 */
	public static String getProperty(String sValue){
	
		Properties properties = new Properties();
	
		try {
			properties.load(new FileInputStream("E:/private/jsp/board/WEB-INF/config.properties"));
		} catch (IOException e) {}
		
		return  properties.getProperty(sValue);
	}
	
	
	/***
	 * 파일읽기
	 * @param path : 파일패스<br>
	 * @param filename : 파일명
	 * @return
	 */
	public static String getFileRead(String path, String filename) {
		
		StringBuffer dataList = new StringBuffer();
		BufferedReader in = null;
		try{
			File oTmpFile = new File(path, filename);
			if (!oTmpFile.exists()) 	dataList.append("");

			in = new java.io.BufferedReader(new FileReader(oTmpFile));
			while(in.ready()) {
				dataList.append(in.readLine()+ "\n");
			}

		}catch (Exception e){
			
		}finally{
			if(in != null)	try{in.close();} catch (IOException e) {}
		}

		return dataList.toString();
	}
		
	/***
	 * 파일쓰기
	 * @param str : 파일내용
	 * @param filename : 파일명(경로포함)
	 */
	public static void setFileWrite(String str, String filename, String charset) { 
		FileOutputStream fos = null;
		Writer out = null;
		
		try  {
			fos = new FileOutputStream(filename); 
			out = new OutputStreamWriter(fos, charset); 
			out.write(str); 
			
		} catch (IOException e) {
			
		}finally{
			if(out != null)	try{out.close();} catch (IOException e) {}
			if(fos != null)	try{fos.close();} catch (IOException e) {}
		}
	}
	
	/**
	 * 내부 URL 연결
	 * @param url
	 * @param params
	 * @return
	 */
	public static String  getUrl(String url, String params) {
	     
		 OutputStream outStream = null;
		 InputStream inStream = null;
		 Writer writer = null;
		 Reader reader = null;
		 BufferedReader buf = null;
		 String output = null;
		 StringBuffer sb = new StringBuffer();
		
		 try{
		
			  URLConnection connection = new URL(url).openConnection();
			
			  connection.setDoOutput(true);  //post방식:true
			  connection.setDoInput(true);  	//데이타 첨부되는 경우
			
			  outStream = connection.getOutputStream();
			  writer = new OutputStreamWriter(outStream);
			  writer.write(params);    
			  writer.flush();       					//내부버퍼에 있는 모든 데이타를 전송하도록 한다.
			  writer.close();
			  outStream.close();
			
			  //보낸데이타를 받는다.
			 inStream = connection.getInputStream();
			 
			 reader = new InputStreamReader(inStream, "cp949");
			 buf = new BufferedReader(reader);
			 
			 while((output = buf.readLine()) != null){
				 sb.append(output + "\r\n");
			  }
			 inStream.close();
		 }catch(Exception e){
			 e.getStackTrace();
		 }finally{
			if(writer != null) try{writer.close();} catch (IOException e) {}
			if(outStream != null) try{outStream.close();} catch (IOException e) {}
			if(reader != null) try{reader.close();} catch (IOException e) {}
			if(buf != null) try{buf.close();} catch (IOException e) {}
			if(inStream != null) try{outStream.close();} catch (IOException e) {}
		 }
		 
		 return sb.toString();
	}

	
	
	/***
	 * 로그파일 생성 <p>
	 * @param logName : 로그파일명 <br>
	 * @param msg : 로그에러 내용 <br>
	 * @throws IOException
	 */
	public static void setLog(String logName,String msg) throws IOException{

		java.text.SimpleDateFormat now = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String current = now.format(new java.util.Date());
		String sLog = "[" + current + "]\t" + msg;

		RandomAccessFile raf = new RandomAccessFile(logName,"rw");
		raf.seek(raf.length());		
		raf.writeBytes( new String( sLog.getBytes( "EUC-KR" ), "8859_1" ) ); 		
		raf.close();
	}	
	
	/***
	 * 쿠키값 가져오기 <p>
	 * @param request <br>
	 * @param sName : 쿠키명 <br>
	 * @return String
	 * @throws Exception
	 */
	public static String getCookie(HttpServletRequest request, String sName) throws Exception {
		Cookie [] cookies = request.getCookies();
		if (cookies==null) return "";
		String value = "";
		for(int i=0;i<cookies.length;i++) {
			if(sName.equals(cookies[i].getName())) {
				value = cookies[i].getValue();				
				break;
			}
		}
		return value;
	}
	
	/***
	 * 쿠키값 생성 <p>
	 * @param response <br>
	 * @param sName : 쿠키 명 <br>
	 * @param cValue : 쿠키 값 <br>
	 * @param nTime : 쿠키 우효시간 ( -1 브라우저 생성시만 존재)<br>
	 * @param sDomain : 유효 도메인 ex) www.merong.net , .merong.net
	 */
	public static void setCookie(HttpServletResponse response, String sName, String cValue, int nTime, String sDomain) {
		
		Cookie cookie = new Cookie(sName, cValue.trim());
		cookie.setPath("/");
		if(nTime > 0) nTime = 	3600 * nTime;
		cookie.setMaxAge(nTime);		
		
		if(sDomain != null && !sDomain.equals("")) cookie.setDomain(sDomain);
		
		response.addCookie(cookie);		
	}
	
	/*** 세션 ID 가져오기<p>
	 * @param request <br>
	 * @param name
	 * @return
	 */
	public static String getSessionID(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
	    return session.getId();
	} 
	
	public static int getSessionTime(HttpServletRequest request) {
		HttpSession session   = request.getSession(true);
	    return session.getMaxInactiveInterval();
	} 
	
	/***
	 * 세션 가져오기<p>
	 * @param request <br>
	 * @param name
	 * @return
	 */
	
/*	public static LoginDto getSession(HttpServletRequest request, String sName) {
		HttpSession session = request.getSession(true);
		return (LoginDto)session.getAttribute(sName);
	} */ 
	
	 /***
     * 세션 생성<p>
     * @param request <br>
     * @param sName <br>
     * @param sValue
     */
/*    public static void setSession(HttpServletRequest request, String sName, LoginDto sValue) {
      HttpSession session   = request.getSession(true);
      session.setAttribute(sName, sValue);
    } */
    
    /***
     * 세션 종료<p>
     * @param request <br>
     * @param nMaxTime : 분단위
     */
    public static void setSessionOut(HttpServletRequest request) {
		HttpSession session   = request.getSession(true);
	    session.invalidate();
	} 
    
    /***
     * 세션 시간 설정<p>
     * @param request <br>
     * @param nMaxTime : 분단위
     */
    public static void setSessionTime(HttpServletRequest request, int nMinute) {
		HttpSession session   = request.getSession(true);
		int  nMaxTime = 60 * nMinute; 
	    session.setMaxInactiveInterval(nMaxTime);
	} 
	

	
}
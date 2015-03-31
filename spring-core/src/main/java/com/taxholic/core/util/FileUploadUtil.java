package com.taxholic.core.util;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.taxholic.core.dto.FileDto;;

public class FileUploadUtil{

	/**
	 * 파일업로드
	 * @param fileList : 파일목록
	 * @param filePath : 업로드 파일 패스
	 * @return List
	 */
    public static List uploadFile(List fileList, String filePath) {
    	
    	List uploadList = new ArrayList(); 
    	Iterator fileIterator = fileList.iterator();
		
    	String fileName = "";
		String originalFilename = "";
		String fileExt = "";
		String tmpFile = Long.toString(System.currentTimeMillis());		
		
    	File dir = new File(filePath);
    	try {
    		if(!dir.exists())	dir.mkdirs();
    	} catch (Exception e) {
            e.printStackTrace();
        }
		
		int k = 1;
		while (fileIterator.hasNext()) {
			
			MultipartFile multiFile = (MultipartFile)fileIterator.next();
			long fileSize = multiFile.getSize();
			if (fileSize > 0) {
				
				originalFilename = multiFile.getOriginalFilename();
				fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1,originalFilename.length()).toLowerCase();
				if(fileExt.equals("jsp")) fileExt = "txt";
				fileName = tmpFile + "_" + k + "." + fileExt;
				
				FileDto FileDto = new FileDto();
				FileDto.setFileRealName(originalFilename);
				FileDto.setFileName(fileName);
				FileDto.setFileExt(fileExt);
				FileDto.setFileSize(fileSize);		
				uploadList.add(FileDto);
				
				InputStream stream;

		        try {
		            stream = multiFile.getInputStream(); 
		            String file = filePath + "/" + fileName;
		            OutputStream bos = new FileOutputStream(file);
		            int bytesRead = 0;
		            byte[] buffer = new byte[4096];
		            while ((bytesRead = stream.read(buffer, 0, 4096)) != -1) {
		                bos.write(buffer, 0, bytesRead);
		            }
		            bos.close();
		            stream.close();

		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        k++;
			}
		}   

		return uploadList;

    }
    
    /**
     * 파일업로드 스트림
     * @param fileList
     * @return
     */
    public static InputStream uploadFileStream(List fileList) {
    	
    	Iterator fileIterator = fileList.iterator();
    	InputStream stream = null;
		
//		while (fileIterator.hasNext()) {
			
			MultipartFile multiFile = (MultipartFile)fileIterator.next();

			if (multiFile.getSize() > 0) {
				try {
					stream = multiFile.getInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
//		}   

		return stream;

    }
    
    /**
     * 파일용량체크
     * @param fileList
     * @param filePath
     * @param fileMax
     * @return
     */
    public static boolean maxFile(List fileList, String filePath, String fileMax) {
    	
    	long TotalFileSize = 0;
    	boolean check = false;
    	
    	for(int i = 0; i < fileList.size(); i++){
			TotalFileSize = TotalFileSize + ((FileDto)fileList.get(i)).getFileSize();
		}
    	
    	if(Long.parseLong(fileMax) < TotalFileSize){
			for(int i = 0; i < fileList.size(); i++){
				File file = new File(filePath + "/" + ((FileDto)fileList.get(i)).getFileName());
				if(file.exists()) file.delete();
			}
			
			check = true;
    	}
    	
    	return check;
    }
    
    /**
     * 파일카피 && 파일 삭제
     * @param Source
     * @param Target
     * @throws IOException
     */
    public static void fileCopy(String source, String target) throws IOException{
    	
    	//복사 대상이 되는 파일 생성  
    	File sourceFile = new File( source );
    	
    	if(sourceFile.exists()){
	    	//스트림, 채널 선언  
	    	FileInputStream inputStream = null;  
	    	FileOutputStream outputStream = null; 
	    	FileChannel fcin = null;  FileChannel fcout = null;
	    	
	    	try {
	    		//스트림 생성  
	    		inputStream = new FileInputStream(sourceFile);   
	    		outputStream = new FileOutputStream(target);
	    		
	    		 //채널 생성   
	    		fcin = inputStream.getChannel();   
	    		fcout = outputStream.getChannel();   
	    		
	    		//채널을 통한 스트림 전송   
	    		long size = fcin.size();   
	    		fcin.transferTo(0, size, fcout);
	    		
	    	} catch (Exception  e) {
	    		 e.printStackTrace();
	    	}finally{
	    		 try{fcout.close(); }catch(IOException ioe){}
	    		 try{fcin.close();}catch(IOException ioe){}
	    		 try{outputStream.close(); }catch(IOException ioe){}   
	    		 try{inputStream.close();}catch(IOException ioe){}
	    	}
	    	
	    	sourceFile.delete();
    	}
    	
    }
    
}

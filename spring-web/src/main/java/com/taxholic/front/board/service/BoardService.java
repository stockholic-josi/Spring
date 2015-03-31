package com.taxholic.front.board.service;


import java.io.File;




import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;






import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.FileUploadUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.front.board.dto.Board;
import com.taxholic.front.board.dto.BoardFile;

@Service("com.taxholic.front.board.service.BoardService")
public class BoardService{
	
	@Resource
	private MessageSourceAccessor message;
	
	@Resource
	private CommonDao commonDao;
	
	public int getBoardCount(Board dto) {
		return this.commonDao.getInt("front.board.getBoardCount",dto);
	}
	
	public List getBoardList(Board dto) {
		
		int total = getBoardCount(dto);
		dto.setTotal(total);
		int num = total - dto.getStart();

		List list = this.commonDao.getList("front.board.getBoardList",dto);

		for(int i = 0; i < list.size(); i++) {				
			
			Board c = (Board)list.get(i);
			c.setNum(num);
			list.set(i, c);
			
			num--;
		}
		
		return list;
	}
	
	public Board getBoard(Board dto){
		return (Board) this.commonDao.getObject("front.board.getBoard", dto);
	}
	
	public void readCntUpdate(String no){
		this.commonDao.update("front.board.readCntUpdate",no);
	}
	
	public List getBoardReadList(Board dto) {
		return this.commonDao.getList("front.board.getBoardReadList", dto);
	}
	
	public void boardInsert(Board dto) {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		String  filePath = message.getMessage("DocBase") + message.getMessage("board.filePath") + "/" + dto.getFlag();
		String  tmpPath = message.getMessage("DocBase") + message.getMessage("tmp.filePath");
		
		File dir = new File(filePath);
    	try {
    		if(!dir.exists()) dir.mkdirs();
    	} catch (Exception e) {
            e.printStackTrace();
        }
		
		//이미지 처리(임시디렉토리 ==> 사용디렉토리)
    	String[] imgList = dto.getImgFile().split(":");
		for(int i =0;  i < imgList.length; i++){
			if(!imgList[i].equals(""))
				try {
					FileUploadUtil.fileCopy(tmpPath + "/"+  imgList[i], filePath + "/" + imgList[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		int fid = this.commonDao.getInt("front.board.getBoardFidMax",dto.getFlag());

		dto.setFid(fid);
		dto.setTitle(StringUtil.getScriptChange(dto.getTitle()));
		this.commonDao.insert("front.board.boardInsert", dto);

		//파일 등록
		String[] fileName = dto.getFileName().split(",");
		String[] fileRealName = dto.getFileRealName().split(",");
		String[] fileExt = dto.getFileExt().split(",");
		String[] fileSize = dto.getFileSize().split(",");
		for(int i =0; fileName != null && i < fileName.length; i++){
			if(!fileName[i].equals("")){
				BoardFile fileDto = new BoardFile();
				fileDto.setNo(dto.getNo());	
				fileDto.setFileName(fileName[i]);
				fileDto.setFileRealName(fileRealName[i]);
				fileDto.setFileExt(fileExt[i] );
				fileDto.setFileSize(Long.parseLong(fileSize[i]) );
				fileDto.setFlag("F");
				
				this.commonDao.insert("front.board.boardFileInsert",fileDto);
			}
		}
		
		//이미지 등록
		BoardFile imgDto = new BoardFile();
		for(int i = 0; i < imgList.length; i++){
			if(!imgList[i].equals("")){
				imgDto.setNo(dto.getNo());	
				imgDto.setFileName(imgList[i]);
				imgDto.setFileRealName(imgList[i]);		
				imgDto.setFlag("M");
				this.commonDao.insert("front.board.boardFileInsert", imgDto);
			}				
		}
			
	}

	public void boardDelete( Board dto){
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		String  filePath = message.getMessage("DocBase") + message.getMessage("board.filePath") + "/" + dto.getFlag();
		
		BoardFile fileDto = new BoardFile();
		fileDto.setNo(dto.getNo());
		List fileList = getFileList(fileDto);
						
		if(this.commonDao.delete("front.board.boardDelete",dto) > 0) {
			
			for(int i = 0; i < fileList.size(); i++){
				BoardFile c = (BoardFile)fileList.get(i);
				File file = new File(filePath + "/" + c.getFileName());
				if(file.exists()) file.delete();
			}
		}
	}
	
	public List getFileList(BoardFile dto) {		
		return this.commonDao.getList("front.board.getFileList",dto);	
	}
	
	public void boardUpdate(Board dto) {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());

		String  filePath = message.getMessage("DocBase") + message.getMessage("board.filePath") + "/" + dto.getFlag();
		String  tmpPath = message.getMessage("DocBase") + message.getMessage("tmp.filePath");
		
    	File dir = new File(filePath);
    	try {
    		if(!dir.exists())	dir.mkdirs();
    	} catch (Exception e) {
            e.printStackTrace();
        }
			
		//이미지 처리(임시디렉토리 ==> 사용디렉토리)
    	String[] imgList = dto.getImgFile().split(":");
		for(int i =0; i < imgList.length; i++){
			if(!imgList[i].equals(""))
				try {
					FileUploadUtil.fileCopy(tmpPath + "/"+  imgList[i], filePath + "/" + imgList[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		dto.setTitle(StringUtil.getScriptChange(dto.getTitle()));
		
		this.commonDao.update("front.board.boardUpdate",dto);
			
		//파일 등록
		String[] fileName = dto.getFileName().split(",");
		String[] fileRealName = dto.getFileRealName().split(",");
		String[] fileExt = dto.getFileExt().split(",");
		String[] fileSize = dto.getFileSize().split(",");
		for(int i =0; fileName != null && i < fileName.length; i++){
			if(!fileName[i].equals("")){
				BoardFile fileDto = new BoardFile();
				fileDto.setNo(dto.getNo());	
				fileDto.setFileName(fileName[i]);
				fileDto.setFileRealName(fileRealName[i]);
				fileDto.setFileExt(fileExt[i] );
				fileDto.setFileSize(Long.parseLong(fileSize[i]) );
				fileDto.setFlag("F");
				
				this.commonDao.insert("front.board.boardFileInsert",fileDto);
			}
		}
		
		//파일 삭제
		if( !StringUtil.chkNull(dto.getDelFileIdx()).equals("")  ){
			String [] delFileIdx = dto.getDelFileIdx().split(":");		
			String [] delFileName = dto.getDelFileName().split(":");
			
			if(this.commonDao.delete("front.board.fileDelete",delFileIdx) > 0){
				for(int i = 0; i < delFileName.length; i++){
					if(!delFileName[i].equals("")){
						File file = new File(filePath + "/" + delFileName[i]);
						if(file.exists()) file.delete();
					}
				}
			}
		}
		
		
		//이미지 등록
		BoardFile imgDto = new BoardFile();
		for(int i = 0; i < imgList.length; i++){
			if(!imgList[i].equals("")){
				imgDto.setNo(dto.getNo());	
				imgDto.setFileName(imgList[i]);
				imgDto.setFileRealName(imgList[i]);		
				imgDto.setFlag("M");
				this.commonDao.insert("front.board.boardFileInsert",imgDto);
			}				
		}

		//이미지 삭제
		if( !StringUtil.chkNull(dto.getDelImgFileIdx()).equals("")  ){
			
			String [] delImgFileIdx = dto.getDelImgFileIdx().split(":");		
			String [] delImgFileName = dto.getDelImgFileName().split(":");		
		
			if(this.commonDao.delete("front.board.fileDelete",delImgFileIdx) > 0){
				for(int i = 0; i < delImgFileName.length; i++){
					if(!delImgFileName[i].equals("")){
						File file = new File(filePath + "/" + delImgFileName[i]);
						if(file.exists()) file.delete();
					}
				}
			}
		}
	}
	
	public void boardReply(Board dto){
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		String  filePath = message.getMessage("DocBase") + message.getMessage("board.filePath") + "/" + dto.getFlag();
		String  tmpPath = message.getMessage("DocBase") + message.getMessage("tmp.filePath");
		
		//이미지 처리(임시디렉토리 ==> 사용디렉토리)
		String[] imgList = dto.getImgFile().split(":");
		for(int i =0; i < imgList.length; i++){
			if(!imgList[i].equals(""))
				try {
					FileUploadUtil.fileCopy(tmpPath + "/"+  imgList[i], filePath + "/" + imgList[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		dto.setTitle(StringUtil.getScriptChange(dto.getTitle()));
		
		Board board = (Board)this.commonDao.getObject("front.board.getBoardReply",dto.getNo());
		dto.setFid(board.getFid());
		dto.setLev(board.getLev());
		dto.setStp(board.getStp());

		this.commonDao.update("front.board.boardReplyStp",dto);

		int no = this.commonDao.insert("front.board.boardReply",dto);			
		
		//파일 등록
		String[] fileName = dto.getFileName().split(",");
		String[] fileRealName = dto.getFileRealName().split(",");
		String[] fileExt = dto.getFileExt().split(",");
		String[] fileSize = dto.getFileSize().split(",");
		for(int i =0; fileName != null && i < fileName.length; i++){
			if(!fileName[i].equals("")){
				BoardFile fileDto = new BoardFile();
				fileDto.setNo(dto.getNo());	
				fileDto.setFileName(fileName[i]);
				fileDto.setFileRealName(fileRealName[i]);
				fileDto.setFileExt(fileExt[i] );
				fileDto.setFileSize(Long.parseLong(fileSize[i]) );
				fileDto.setFlag("F");
				
				this.commonDao.insert("front.board.boardFileInsert", fileDto);
			}
		}
		
		//이미지 등록
		BoardFile imgDto = new BoardFile();
		for(int i = 0; i < imgList.length; i++){
			if(!imgList[i].equals("")){
				imgDto.setNo(dto.getNo());	
				imgDto.setFileName(imgList[i]);
				imgDto.setFileRealName(imgList[i]);		
				imgDto.setFlag("M");
				this.commonDao.insert("front.board.boardFileInsert",imgDto);
			}				
		}
	}
	
}

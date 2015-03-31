package com.taxholic.front.board.controller;

import java.util.HashMap;

import java.util.List;




import java.util.Map;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.util.StringUtil;
import com.taxholic.front.board.dto.Board;
import com.taxholic.front.board.dto.BoardFile;
import com.taxholic.front.board.service.BoardService;

@Controller("com.taxholic.front.board.controller.BoardController")
@RequestMapping("/front/board/*.do")
public class BoardController {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private MessageSourceAccessor message;
	
	@Resource
	private BoardService boardService;
	
	
	/**
	 * 게시판 목록
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardList(Board dto) {
		ModelAndView mv = new ModelAndView("front/board/boardList");
		
		mv.addObject("dto",dto);
		
		return mv;
	}
	
	/**
	 * 게시판 목록(Json)
	 * @param dto
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public void boardJson(HttpServletResponse response, Board dto) throws IOException {
		
		List list = this.boardService.getBoardList(dto);
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		Map map = new HashMap();  
		map.put( "totalCount", dto.getTotal() );
		map.put("dataList", jsonArray);
		
		response.setContentType("text/json");
		response.getWriter().print(JSONObject.fromObject(map));

	}
	
	/**
	 * 게시판 조회
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardRead(Board dto) {
		
		ModelAndView mv = new ModelAndView("front/board/boardRead");
		
		Board board =  this.boardService.getBoard(dto);
		if(board == null) {return null;}
		this.boardService.readCntUpdate(dto.getNo());
		
		List readList = boardService.getBoardReadList(dto); 
		
		BoardFile fileDto = new BoardFile();
		fileDto.setNo(dto.getNo());
		fileDto.setFlag("F");
		List fileList = this.boardService.getFileList(fileDto);		
		
		mv.addObject("board",board);
		mv.addObject("fileList", fileList);
		mv.addObject("readList", readList);
		mv.addObject("dto",dto);
		
		return mv; 

	}

	/**
	 * 게시판 쓰기폼 
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardForm(Board dto) {
		
		ModelAndView mv = new ModelAndView("front/board/boardForm");
		
		mv.addObject("action", "boardInsert");
		mv.addObject("fileCount", 0);		
		mv.addObject("dto",dto);
		
		return mv;
	}
	
	/**
	 * 게시판 쓰기
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardInsert(HttpServletRequest request, Board dto) {
		
		if(dto.getFlag() == null) dto.setFlag("01");
		dto.setIp(request.getRemoteAddr());		
		
		this.boardService.boardInsert(dto);	
		
		return new ModelAndView("redirect:./boardRead.do?no=" + dto.getNo() + "&flag=" + dto.getFlag());

	}
	
	/**
	 * 게시판 수정 폼
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardUpdateForm(Board dto) {
		
		if(StringUtil.chkNum(dto.getNo()) == false) {return null;}

		ModelAndView mv = new ModelAndView("front/board/boardForm");
		
		Board board = this.boardService.getBoard(dto);		
		if(board == null){return null;}
		
		//파일 목록
		BoardFile FileDto = new BoardFile();
		FileDto.setNo(dto.getNo());
		FileDto.setFlag("F");
		List fileList = this.boardService.getFileList(FileDto);
		
		JSONArray jsonArray = JSONArray.fromObject(fileList);
		
		
		//이미지 목록
		BoardFile imgDto = new BoardFile();
		imgDto.setNo(dto.getNo());
		imgDto.setFlag("M");
		List imgList = this.boardService.getFileList(imgDto);
		
		mv.addObject("action", "boardUpdate");
		mv.addObject("board",board);
		mv.addObject("fileCount", Integer.toString(fileList.size()));		
		mv.addObject("fileList", jsonArray.toString());		
		mv.addObject("imgList", imgList);		
		mv.addObject("dto", dto);		
		
		return mv;

	}	
	
	/**
	 * 게시판 수정
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardUpdate(HttpServletRequest request, Board dto) {
		
		if(StringUtil.chkNum( dto.getNo() ) == false) {return null;}
		
		if(dto.getFlag() == null) dto.setFlag("01");
		if(dto.getHtml() == null) dto.setHtml("N");
		dto.setIp(request.getRemoteAddr());
		
		this.boardService.boardUpdate(dto);
		
		return new ModelAndView("redirect:./boardRead.do?no=" + dto.getNo() + "&flag=" + dto.getFlag());

	}
	
	/**
	 * 게시판 댓글 폼
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardReplyForm(HttpServletRequest request, HttpServletResponse respons,  Board dto) {
		
		if(StringUtil.chkNum( dto.getNo()) == false) {return null;}
		ModelAndView mv = new ModelAndView("front/board/boardForm");
		
		Board board = this.boardService.getBoard(dto);		
		if(board == null){return null;}
		
		mv.addObject("action", "boardReply");
		mv.addObject("board",board);
		mv.addObject("dto",dto);
		mv.addObject("fileCount", 0);	
		
		return mv;

	}
	
	/**
	 * 게시판 댓글
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardReply(HttpServletRequest request,  Board dto) {

		if(dto.getFlag() == null) dto.setFlag("01");
		if(dto.getHtml() == null) dto.setHtml("N");
		dto.setIp(request.getRemoteAddr());
		
		this.boardService.boardReply(dto);			
		
		return new ModelAndView("redirect:./boardRead.do?no=" + dto.getNo() + "&flag=" + dto.getFlag());

	}
	
	
	/**
	 * 게시판삭제
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView boardDelete(HttpServletRequest request, HttpServletResponse respons, Board dto) {
		
		this.boardService.boardDelete(dto);
		
		return new ModelAndView("redirect:./boardList.do?flag=" + dto.getFlag());

	}
	
}
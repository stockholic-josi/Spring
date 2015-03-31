package com.taxholic.manage.community.Controller;

import java.util.List;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.community.dto.Reply;
import com.taxholic.manage.community.service.ReplyService;

@Controller("com.taxholic.manage.community.Controller.ReplyController")
@RequestMapping("/manage/reply/*.do")
public class ReplyController  {
	
	@Resource
	private ReplyService replyService;
	
	/**
	 * 댓글 목록
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView replyAjax( HttpServletResponse response,Reply dto)  {
		
		if(StringUtil.chkNum(dto.getSeqNo()) == false) {return null;}
		ModelAndView mv = new ModelAndView("manage/community/replyAjax");
	
		AuthDto auth = AuthUtil.getUser();
		List replyList = this.replyService.getReplyList(dto);
		
		mv.addObject("replyList", replyList);
		mv.addObject("dto", dto);
		mv.addObject("auth", auth);
		
		return mv;
	}
	

	/**
	 * 댓글 쓰기
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public void replyInsert(HttpServletRequest request, HttpServletResponse response, Reply dto) {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		dto.setIp(request.getRemoteAddr());
		
		this.replyService.replyInsert(dto);
		
	}

	/**
	 * 댓글 수정
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public void replyUpdate(HttpServletRequest request, HttpServletResponse response, Reply dto) {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		dto.setIp(request.getRemoteAddr());
		
		this.replyService.replyUpdate(dto);
		
	}

	/**
	 * 댓글 삭제
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public void replyDelete(HttpServletResponse response,Reply dto) {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		this.replyService.replyDelete(dto);
		
	}
	
}
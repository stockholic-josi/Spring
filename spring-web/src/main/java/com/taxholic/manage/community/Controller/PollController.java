package com.taxholic.manage.community.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.util.StringUtil;
import com.taxholic.core.util.SysUtil;
import com.taxholic.manage.community.dto.Poll;
import com.taxholic.manage.community.service.PollService;

@Controller("com.taxholic.manage.community.Controller.PollController")
@RequestMapping("/manage/community/poll/*.do")
public class PollController {
	
	@Resource
	private PollService pollService;
	
	/**
	 * 목록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollList(Poll dto) {
		
		
		if(StringUtil.chkNum(dto.getPage()) == false) dto.setPage("1");
		
		ModelAndView mv = new ModelAndView("manage/community/pollList");

		List pollList = this.pollService.getPollList(dto);		
		
		mv.addObject("search",dto);
		mv.addObject("pollList", pollList);

		
		return mv;

	}
	
	/**
	 * 조회
	 * @param request
	 * @param response
	 * @param dto : Poll
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView pollRead(HttpServletRequest request, Poll dto) {
		
		
		if(StringUtil.chkNum(dto.getSeqNo()) == false) {return null;}

		ModelAndView mv = new ModelAndView("manage/community/pollRead");
	
		Poll poll = this.pollService.getPoll(dto) ;		
		if(poll == null) return null;
		
		//설문 참여 여부 체크
		try {
			if(SysUtil.getCookie(request, "poll_" + dto.getSeqNo()).equals("poll_" + dto.getSeqNo())){
				poll.setPollStatus("4");
			}
		} catch (Exception e) {}
		
		mv.addObject("poll", poll);
		mv.addObject("dto", dto);
		
		List questionList = this.pollService.getPolQuestionlList(dto,poll.getTotal());
		
		mv.addObject("questionList", questionList);
		
		
		return mv;

	}
	
	/**
	 * 등록폼
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollForm(HttpServletRequest request, HttpServletResponse response, Poll dto) {

		ModelAndView mv = new ModelAndView("manage/community/pollForm");
		
		mv.addObject("action", "pollWrite");
		mv.addObject("poll",dto);
		
		return mv;

	}
	
	/**
	 * 등록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollWrite(Poll dto) {

		this.pollService.pollWrite(dto);			
		
		return new ModelAndView("redirect:./pollList.do");

	}
	
	/**
	 * 투표하기
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public void pollAjax(HttpServletRequest request, HttpServletResponse response, Poll dto) throws Exception {
		
		if(StringUtil.chkNum(dto.getSeqNo()) == false) return;
		
		Map map = new HashMap();  
		
		if(SysUtil.getCookie(request, "poll_" + dto.getSeqNo()).equals("poll_" + dto.getSeqNo())){
			map.put( "poll", "isPolled" );
		}else{
			this.pollService.pollProc(dto); 
			SysUtil.setCookie(response, "poll_" + dto.getSeqNo(), "poll_" + dto.getSeqNo(), 24*60, "");
			map.put( "poll", "polled" );
		}
			
		response.setContentType("text/json");
		response.getWriter().print(JSONObject.fromObject(map));
	}
	
	/**
	 * 수정폼
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollUpdateForm(Poll dto) {
		

		if(StringUtil.chkNum(dto.getSeqNo()) == false) {return null;}

		ModelAndView mv = new ModelAndView("manage/community/pollForm");		
		
		Poll poll = this.pollService.getPoll(dto);		
		if(poll == null){return null;}
		
		mv.addObject("action", "pollUpdate");
		mv.addObject("poll",poll);
		mv.addObject("dto",dto);
		
		List questionList = this.pollService.getPolQuestionlList(dto,poll.getTotal());
		mv.addObject("questionList", questionList);
		
		
		return mv;

	}	
	
	/**
	 * 수정
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollUpdate(Poll dto) {
		
		this.pollService.pollUpdate(dto);			
		
		return new ModelAndView("redirect:./pollRead.do?seqNo=" + dto.getSeqNo());

	}
	
	/**
	 * 삭제
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView pollDelete(Poll dto) {
		
		this.pollService.pollDelete(dto.getSeqNo());
		
		return new ModelAndView("redirect:./pollList.do");

	}

}
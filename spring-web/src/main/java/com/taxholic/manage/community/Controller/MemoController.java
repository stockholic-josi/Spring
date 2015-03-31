package com.taxholic.manage.community.Controller;

import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.community.dto.Memo;
import com.taxholic.manage.community.service.MemoService;

@Controller("com.taxholic.manage.community.Controller.MemoController")
@RequestMapping("/manage/community/memo/*.do")
public class MemoController {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private MemoService memoService;
	
	/**
	 * 메모목록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView memoList(Memo dto) {

		ModelAndView mv = new ModelAndView("manage/community/memoList");
				
		
		if(StringUtil.chkNum(dto.getP()) == false) dto.setP("1");
		if( !StringUtil.chkNull(dto.getSearchString()).equals("") ){
			mv.addObject("searchString",dto.getSearchString());
			dto.setSearchString("'*" + dto.getSearchString() + "*'");
		}
		
		List memoList = this.memoService.getMemoList(dto);
		
		mv.addObject("dto", dto);
		mv.addObject("memoList", memoList);
		
		return mv; 
	}
	
	/**
	 * 메모등록 폼
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView memoForm( Memo dto) {
		
		ModelAndView mv =  new ModelAndView("manage/community/memoForm");
		
		if(!StringUtil.chkNull(dto.getSeqNo()).equals("")){
			String content  = this.memoService.getMemo(dto);
			dto.setContent(content);
		}
		
		mv.addObject("dto",dto);
		
		return mv;
	}

	/**
	 * 메모등록
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public void memoInsert(HttpServletResponse response,Memo dto) {
		
		if(StringUtil.chkNull(dto.getSeqNo()).equals("")){
			this.memoService.memoInsert(dto);
		}else{
			this.memoService.memoUpdate(dto);
		}
	}

	/**
	 * 메모삭제
	 * @param dto
	 * @return
	 */
	@RequestMapping
	public ModelAndView memoDelete(Memo dto) {
		
		this.memoService.memoDelete(dto);
		
		return new ModelAndView("redirect:/manage/community/memo/memoList.do");
	}
	
		
}
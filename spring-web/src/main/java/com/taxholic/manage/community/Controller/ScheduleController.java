package com.taxholic.manage.community.Controller;

import java.util.List;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.util.DateUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.community.dto.ScheduleDto;
import com.taxholic.manage.community.service.ScheduleService;

@Controller("com.taxholic.manage.community.Controller.ScheduleController")
@RequestMapping("/manage/community/schedule/*.do")
public class ScheduleController {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private ScheduleService scheduleService;
	
	/**
	 * 월간 스케즐
	 * @param request
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping
	public ModelAndView calendar(HttpServletRequest request, ScheduleDto dto) throws Exception {

		String sYear = ServletRequestUtils.getStringParameter(request, "year");
		String month = ServletRequestUtils.getStringParameter(request, "month");
		int nYear = DateUtil.getYear();
		int nMonth = DateUtil.getMonth();
		
		if(!StringUtil.chkNull(sYear).equals("") && StringUtil.chkNum(sYear) == true && sYear.length() < 5){
			nYear = Integer.parseInt(sYear);
		}
		if(!StringUtil.chkNull(month).equals("") && StringUtil.chkNum(month) == true && month.length() < 3){
			nMonth = Integer.parseInt(month);
		}
		
		ModelAndView mv = new ModelAndView("manage/community/calendar");
		mv.addObject("calendar", this.scheduleService.getCalendar(nYear, nMonth, 100, 90));
		mv.addObject("year", nYear);
		mv.addObject("month", nMonth);
		
		return mv; 
	}
	
	/**
	 * 날짜별 스케즐 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView scheduleRead(ScheduleDto dto) throws Exception {
		
		String sYear = dto.getYear();
		String month = dto.getMonth();
		String sDay =  dto.getDay();
		int nYear = DateUtil.getYear();
		int nMonth = DateUtil.getMonth();
		int nDay = DateUtil.getDay();
		
		if(!StringUtil.chkNull(sYear).equals("") && StringUtil.chkNum(sYear) == true && sYear.length() < 5){
			nYear = Integer.parseInt(sYear);
		}
		if(!StringUtil.chkNull(month).equals("") && StringUtil.chkNum(month) == true && month.length() < 3){
			nMonth = Integer.parseInt(month);
		}
		if(!StringUtil.chkNull(sDay).equals("") && StringUtil.chkNum(sDay) == true && sDay.length() < 3){
			nDay = Integer.parseInt(sDay);
		}
		
		ModelAndView mv = new ModelAndView("manage/community/scheduleRead");
		
		List scheduleList = this.scheduleService.getScheduleList(nYear,nMonth,nDay);
		mv.addObject("scheduleList", scheduleList);
		mv.addObject("dto", dto);
		
		return mv; 
	}
	
	/**
	 * 날짜별 스케즐 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView scheduleInsert(ScheduleDto dto) throws Exception {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		if(!dto.getSeqNo().equals("")){
			this.scheduleService.scheduleUpdate(dto);
		}else{
			this.scheduleService.scheduleInsert(dto);
		}
		
		String [] regDate = dto.getRegDate().split("/");
		
		String url = "/manage/community/schedule/scheduleRead.do?year=" + regDate[0]+ "&month=" + Integer.parseInt(regDate[1]) + "&day=" + regDate[2] + "&flag=Y";
		
		return new ModelAndView("redirect:" + url);
		

	}
	
	/**
	 * 날짜별 스케즐 삭제 
	 * @param request
	 * @param respons
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView scheduleDelete(ScheduleDto dto) throws Exception {
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		this.scheduleService.scheduleDelete(dto);	

		String url = "/manage/community/schedule/scheduleRead.do?year=" + dto.getYear() + "&month=" + Integer.parseInt(dto.getMonth()) + "&day=" + Integer.parseInt(dto.getDay()) + "&flag=Y";
		
		return new ModelAndView("redirect:" + url);

	}
		
}
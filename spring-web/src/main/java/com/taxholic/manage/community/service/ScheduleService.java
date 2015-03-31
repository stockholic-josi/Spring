package com.taxholic.manage.community.service;

import java.util.List;




import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.DateUtil;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.community.dto.ScheduleDto;

@RemoteProxy(name="Schedule")
@Service("com.taxholic.manage.community.service.ScheduleService")
public class ScheduleService {
	
	@Resource
	private CommonDao commonDao;
	
	/**
	 * 스케즐 달력
	 * @param nYear : 년
	 * @param nMonth : 월
	 * @param width : 달력 폭
	 * @param height : 달력 높이
	 * @param page : 이동 페이지
	 * @return String
	 */
	@RemoteMethod
	public String getCalendar(int nYear, int nMonth, int width, int height){
		
		AuthDto auth = AuthUtil.getUser();
		
		StringBuffer dataList = new StringBuffer();
		
		int fwcm = DateUtil.getFirstWeekday(nYear, nMonth);	//월의 첫번째주일
		int ldcm = DateUtil.getLastDay(nYear, nMonth);				//월의 마지막 날짜
		int cw = DateUtil.getWeekCount(fwcm,ldcm);				//월의 주 개수
		String bgcolor = "";
		
		int tw = width * 7 + 15;

		dataList.append("<table cellspacing='0' cellpadding='0' id='schWraper'>");
		dataList.append("<tr height='30'  align='center' bgcolor='#DFE8F6'>");
		dataList.append("	<td class='scFirst' width='" + width + "'>일</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>월</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>화</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>수</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>목</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>금</td>");
		dataList.append("	<td class='scHeader' width='" + width + "'>토</td>");
		dataList.append("</tr>");
		dataList.append("<tr height='" + height +  "'>");
		
		ScheduleDto dto = new ScheduleDto();
		dto.setUserId(auth.getUserId());
		dto.setYear(Integer.toString(nYear));
		dto.setMonth(StringUtil.zeroFill("00",Integer.toString(nMonth)));
		List list = this.commonDao.getList("manage.community.schedule.getCalendarList",dto);
		
		int k = 1;
		for(int i = 1; i <= cw * 7; i++){
			if(i < fwcm){
				dataList.append("<td bgcolor='#F9F9F9' width='" + width + "' height='" + height + "' class='schDay'>&nbsp;</td>");
			}else{
				if(k <= ldcm){
					bgcolor = (DateUtil.getYear() == nYear && DateUtil.getMonth() == nMonth && DateUtil.getDay() == k) ? "#EEEEFF" : "#FFFFFF";
					dataList.append("<td valign='top' bgcolor='" + bgcolor + "' class='schDay'>");
					dataList.append("<table cellspacing='0' cellpadding='0' width='100%' height=" + height + " onClick=\"viewSchedule('" + nYear + "','" + nMonth + "','" + k + "')\">");
					dataList.append("<tr><td height='18' align='right'><font color='#717171' class='schBody'><b>" + k + "</b></font></td></tr>");
					dataList.append("<tr><td valign='top' style='line-height:17px' class='schBody'>");
					
					int s = 0;
					for(int j = 0; j < list.size(); j++){
						ScheduleDto c = (ScheduleDto)list.get(j);
						if(Integer.toString(k).equals(c.getKeyValue())){
							dataList.append(StringUtil.strlen(c.getContent(),12,"...") + "<br>");
							s++;
							if(s == 4) break;
						}
					}
			
					dataList.append("</td></tr>");
					dataList.append("</table>");
					dataList.append("</td>");
				}else{
					dataList.append("<td width='" + width + "' height='" + height + "' bgcolor='#F9F9F9' class='schDay'>&nbsp;</td>");
				}
				k++;
			}

			if(i % 7 == 0){
				dataList.append("</tr><tr>");
			}
		}
		
		dataList.append("</table>");
		
		
		return dataList.toString();
	}
	
	
	/**
	 * 날짜별 스케즐 조회
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public List getScheduleList(int nYear, int nMonth, int nDay){
		
		AuthDto auth = AuthUtil.getUser();
		
		ScheduleDto dto = new ScheduleDto();
		dto.setUserId(auth.getUserId());
		dto.setYear(Integer.toString(nYear));
		dto.setMonth(StringUtil.zeroFill("00",Integer.toString(nMonth)));
		dto.setDay(StringUtil.zeroFill("00",Integer.toString(nDay)));
		
		List scheduleList =  this.commonDao.getList("manage.community.schedule.getScheduleList",dto);
		
		return scheduleList;
		
	}
	
	/**
	 * 날짜별 스케즐 등록
	 * @param dto
	 */
	public void scheduleInsert(ScheduleDto dto) {
		this.commonDao.insert("manage.community.schedule.scheduleInsert",dto);
	}
	
	/**
	 * 날짜별 스케즐 수정
	 * @param dto
	 */
	public void scheduleUpdate(ScheduleDto dto) {
		this.commonDao.update("manage.community.schedule.scheduleUpdate",dto);
	}
	
	/**
	 * 날짜별 스케즐 삭제
	 * @param dto
	 * @return
	 */
	public void scheduleDelete(ScheduleDto dto) {
		 this.commonDao.delete("manage.community.schedule.scheduleDelete",dto);
	}
		
}

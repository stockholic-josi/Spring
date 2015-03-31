package com.taxholic.manage.community.service;


import java.text.DecimalFormat;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.manage.community.dto.Poll;
	
@Service("com.taxholic.manage.community.service.PollService")
public class PollService{
	
	@Resource
	private CommonDao commonDao;
	
	/**
	 * 목록 개수
	 * @param dto
	 * @return int
	 */
	public int getPollCount(Poll dto) {		
		return this.commonDao.getInt("manage.community.poll.getPollCount",dto);		
	}
	
	/**
	 * 목록
	 * @param dto : SearchDto
	 * @return List
	 */
	public List getPollList(Poll dto) {
		
		int  total = getPollCount(dto);				
		dto.setTotal(total);		
	
		List list = new ArrayList();
		
		int idx = 1;
		if(total> 0 ){
			dto.setLimit(10);
			dto.setTotalPage((int) Math.ceil( total / (double)dto.getLimit()));
			dto.setStart((Integer.parseInt(dto.getPage()) - 1) * dto.getLimit());		
			
			list = this.commonDao.getList("manage.community.poll.getPollList",dto);
			idx = total - dto.getStart();
			for(int i = 0; i < list.size(); i++) {				
				
				Poll c = (Poll)list.get(i);
				c.setNum(idx);
				list.set(i, c);
				
				idx--;
			}
		}
		
		return list;
	}
	
	/**
	 * 조회
	 */
	public Poll getPoll(Poll dto) {
		
		Poll poll = (Poll)this.commonDao.getObject("manage.community.poll.getPoll",dto);
		
		//설문 참여 수
		if(poll != null){
			poll.setTotal(getPollQuestionCount(dto));
		}
		
		return poll;
	}

//	public Poll getPollMain() {
//		
//		Poll poll = this.commonDao.getPollMain();
//		
//		//설문 참여 수
//		if(poll != null){
//			poll.setTotal(getPollQuestionCount(poll));
//		}
//		
//		return poll;
//	}
	
	/**
	 * 설문 참여 수
	 * @param dto : Poll
	 * @return int
	 */
	public int getPollQuestionCount(Poll dto) {		
		return this.commonDao.getInt("manage.community.poll.getPollQuestionCount",dto);
	}
	
	/**
	 * 설문 항목 목록
	 * @param dto
	 * @return
	 */
	public List getPolQuestionlList(Poll dto, int total) {
	
		double rate = 0;
		int graphWidth = 1;
		String hitRate = "";
		DecimalFormat df = new DecimalFormat("0");
		
		List list = this.commonDao.getList("manage.community.poll.getPollQuestionList",dto);
		
		for(int i = 0; i < list.size(); i++) {			
			
			Poll c = (Poll)list.get(i);
			if(total > 0){
				rate = Math.round(((double)c.getHit() / total) * 100);
				hitRate = df.format(rate);
				graphWidth = Integer.parseInt(hitRate) * 2;
				
				if(graphWidth == 0) graphWidth = 20;
				
				c.setHitRate(hitRate);
				c.setGraphWidth(graphWidth);
			}
		}

		return list;
	}
	
	/**
	 * 설문 항목 목록
	 * @param dto
	 * @return
	 */
	public List getPollChartlList(String no) {
		return this.commonDao.getList("manage.community.poll.getPollChartList",no);
	}
	
	/**
	 * 등록
	 * @param dto : Poll
	 * @return String
	 */
	public void pollWrite(Poll dto) {
		
		this.commonDao.insert("manage.community.poll.pollInsert",dto);
		
		//설문 항목 등록
		String question= "";
		for(int i = 0; i < dto.getQuestion().size(); i++){
			question = dto.getQuestion().get(i).toString().trim();
			
			if(!question.equals("")){
				dto.setSeqNo(dto.getSeqNo());
				dto.setTitle(question);
				this.commonDao.insert("manage.community.poll.QuestionInsert",dto);
			}
		}
		

	}
	
	/**
	 * 투표하기
	 * @param dto : Poll
	 * @return boolean
	 * @throws Exception 
	 */
	public int pollProc(Poll dto) {
		
		return this.commonDao.update("manage.community.poll.pollProc",dto);

	}
	
	/**
	 * 수정
	 * @param dto : Poll
	 * @return boolean
	 */
	public void pollUpdate(Poll dto) {
		
		this.commonDao.update("manage.community.poll.pollUpdate",dto);
		
		//설문 항목 삭제
		this.commonDao.delete("manage.community.poll.pollQuestionDelete",dto.getSeqNo());
		
		//설문 항목 등록
		String question= "";
		int  hit = 0;
		for(int i = 0; i < dto.getQuestion().size(); i++){
			question = dto.getQuestion().get(i).toString().trim();
			hit = Integer.parseInt(dto.getArrHit().get(i).toString());
			
			if(!question.equals("")){
				dto.setSeqNo(dto.getSeqNo());
				dto.setTitle(question);
				dto.setHit(hit);
				this.commonDao.insert("manage.community.poll.QuestionInsert",dto);
			}
		}
			
	}
	
	
	/**
	 * 삭제
	 * @param dto : Poll
	 * @return boolean
	 */
	public void pollDelete(String seqNo) {
		
		this.commonDao.delete ("manage.community.poll.pollDelete",seqNo);
		
	}
	
}

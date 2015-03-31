package com.taxholic.manage.community.service;

import java.util.List;





import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.community.dto.Reply;

@Service("com.taxholic.manage.community.service.ReplyService")
public class ReplyService {
	
	@Resource
	private CommonDao commonDao;
	
	public List getReplyList(Reply dto) {		
		return this.commonDao.getList("manage.community.reply.getReplyList",dto);	
	}
	
	public void replyInsert( Reply dto){
		dto.setContent(StringUtil.getScriptChange(dto.getContent()));
		this.commonDao.insert("manage.community.reply.replyInsert",dto);
	}
	
	public void replyUpdate( Reply dto){
		dto.setContent(StringUtil.getScriptChange(dto.getContent()));
		this.commonDao.update("manage.community.reply.replyUpdate",dto);
	}
	
	public void replyDelete( Reply dto){
		this.commonDao.delete("manage.community.reply.replyDelete",dto);
	}
	

}

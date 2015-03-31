package com.taxholic.front.user.service;

import java.io.StringWriter;








import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.dao.CommonSqliteDao;
import com.taxholic.core.util.MD5;
import com.taxholic.core.util.VelocityUtil;
import com.taxholic.front.user.dto.User;

@Service("com.taxholic.front.user.servic.UserService")
public class UserService {
	
	@Resource
	private MessageSourceAccessor message;
	
	@Resource
	private CommonSqliteDao commonDao;
	

	public boolean joinProc(User dto){
		
		boolean result = false;	
		
		//패스워드 암호화
		dto.setPasswd(MD5.getHashString(dto.getPasswd()));
		
		result = this.commonDao.insert("front.User.joinInsert",dto) > 0 ? true:  false;
		
		//인증메일 발송
		/*
		if(result == true){
			VelocityEngine ve = VelocityUtil.initVelocity(this.messageSourceAccessor.getMessage("DocBase") + "/WEB-INF/vm/");
			
			VelocityContext context = new VelocityContext();
			context.put("userId", dto.getUserId()); 
			context.put("userNm", dto.getUserNm()); 
			
			StringWriter writer = new StringWriter();
			Template template = ve.getTemplate("user/mailJoinTpl.vm" );
			template.merge(context, writer);
			//System.out.println(writer);
		}
		*/
		
		return result;
	}
	
	public int getIdSearch(String userId){
		return this.commonDao.getInt("front.User.getIdSearch",userId);
	}

	public int getNickSearch(String userNm){
		return this.commonDao.getInt("front.User.getNickSearch",userNm);
	}

	public int getEmailSearch(String email){
		return this.commonDao.getInt("front.User.getEmailSearch",email);
	}
	
	public AuthDto getUserInfo(AuthDto dto){
		return (AuthDto) this.commonDao.getObject("front.User.getUserInfo",dto);
	}
	
	public int joinCertificate(AuthDto dto){
		return this.commonDao.update("front.User.joinCertificate",dto);
	}

	public int userInfoUpdate(User dto){
		return this.commonDao.update("front.User.userInfoUpdate",dto);
	}

	public int userPwUpdate(User dto){
		
		//패스워드 암호화
		dto.setPasswd(MD5.getHashString(dto.getPasswd()));
		
		return this.commonDao.update("front.User.userPwUpdate",dto);
	}
	
	public int userOutProc(String userId){
		return this.commonDao.update("front.User.userOutPro",userId);
	}
	
}

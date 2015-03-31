package com.taxholic.manage.system.code.service;

import java.util.ArrayList;
import java.util.HashMap;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.manage.system.code.dto.AuthCode;
import com.taxholic.manage.system.code.dto.AuthCodeArr;

@Service("com.taxholic.manage.system.code.service.AuthCodeService")
public class AuthCodeService {
	
	@Resource
	private CommonDao commonDao;
	
	public JSONObject getAuthCodeList() {
		
		List<AuthCode> list = this.commonDao.getList("manage.community.authCode.getAuthCodeList");

		JSONArray jsonArray = JSONArray.fromObject(list);

		HashMap map = new HashMap();
		map.put("dataList", jsonArray);
		
		return JSONObject.fromObject(map);
		
	}
	
	public void authCodeInsert(AuthCodeArr dto){
		
		String editCd = "";
		for(int i = 0; i <dto.getLstEditCd().size(); i++){
			
			editCd = dto.getLstEditCd().get(i).toString();
			
			AuthCode authCode = new AuthCode();
			
			authCode.setRoleCd( dto.getLstRoleCd().get(i).toString() );
			authCode.setRoleNm( dto.getLstRoleNm().get(i).toString() );
			authCode.setRoleStp( Integer.parseInt( dto.getLstRoleStp().get(i).toString()) );
			
			if(editCd.equals("I")){
				this.commonDao.insert("manage.community.authCode.authCodeInsert",authCode);
			}
			
			if(editCd.equals("U")){
				this.commonDao.update("manage.community.authCode.authCodeUpdate",authCode);
			}
			
		}
		
	}
	
	
	public void authCodeDelete(AuthCodeArr dto){
		
		for(int i = 0; i < dto.getLstRoleCd().size(); i++){
			this.commonDao.delete("manage.community.authCode.authCodeDelete", dto.getLstRoleCd().get(i).toString() );
		}
			
	}
	
	public JSONObject getAuthUserList() {
		
		List<AuthCode> list = this.commonDao.getList("manage.community.authCode.getAuthUserList");
		
		for(int i = 0; i < list.size(); i++) {				
			
			StringBuffer roleNm = new StringBuffer();
			AuthCode c = (AuthCode)list.get(i);
			List<AuthCode> roleList  = this.commonDao.getList("manage.community.authCode.getAuthUserRoleList",c.getUserId());
			
			for(int j = 0; j < roleList.size(); j++){
				if(j == 0){
					roleNm.append(roleList.get(j).getRoleNm());
				}else{
					roleNm.append(" / " + roleList.get(j).getRoleNm());
				}
			}
			
			c.setRoleNm(roleNm.toString());
			list.set(i, c);
			
		}
		

		JSONArray jsonArray = JSONArray.fromObject(list);

		HashMap map = new HashMap();
		map.put("dataList", jsonArray);
		
		return JSONObject.fromObject(map);
		
	}
	
	public List getAuthCodeRegList(AuthCode dto){
		
		List<AuthCode> list = null;
		
		//유저체크
		int uCnt = 	this.commonDao.getInt("manage.community.authCode.getUserCount",dto.getUserId());
			
		if(uCnt > 0){
			
			list = this.commonDao.getList("manage.community.authCode.getAuthCodeList");
			
			List<AuthCode> roleList = this.commonDao.getList("manage.community.authCode.getAuthUserRoleList",dto.getUserId());
			
			for(int i = 0; i < list.size(); i++) {	
				AuthCode c = (AuthCode)list.get(i);
				
				for(int j = 0; j < roleList.size(); j++){
					if( c.getRoleCd().equals( roleList.get(j).getRoleCd() ) ){
						c.setEditCd("I");
						break;
					}
				}
				
				list.set(i, c);
			}
		}
		return list;
				
	}
	
	public void authCodeRegProc(AuthCodeArr dto){
		
		//권한삭제
		this.commonDao.delete("manage.community.authCode.authCodeRegDelete",dto.getUserId());
		
		
		if(dto.getLstRoleCd() != null){

			List list = new ArrayList();
		
			for(int i = 0; i < dto.getLstRoleCd().size(); i++){
				AuthCode authCode = new AuthCode();
				
				authCode.setRoleCd( dto.getLstRoleCd().get(i).toString() );
				authCode.setUserId(dto.getUserId());
				
				list.add(authCode);
				
			}
			
			//권한등록
			this.commonDao.insert("manage.community.authCode.authCodeRegProc",list);
		}
		
		
	}

}

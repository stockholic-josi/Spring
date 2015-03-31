package com.taxholic.core.authority;


import java.util.ArrayList;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.dao.CommonSqliteDao;

public class AuthService  implements UserDetailsService {
	
	@Resource
//	private CommonDao commonDao;
	private CommonSqliteDao commonDao;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException, DataAccessException {
    	
		if (userId == null || "".equals(userId.trim()) || userId == null) {
			return null;
		}
    	
		/*
		List role = new ArrayList();
		role.add("ADMIN");
		role.add("USER");
		dto.setRole(role);
		*/
    	
		//로그인 정보
		AuthDto user = new AuthDto();
		user.setUserId(userId);
		user.setUseYn("Y");
		AuthDto dto = (AuthDto)this.commonDao.getObject("front.User.getUserInfo",user);
		
		//회원 권한
		if(dto != null){
			
			List roleList = new ArrayList();
			List<AuthDto> authList = this.commonDao.getList("front.User.getUserAuthList",userId);
			int authCount = authList.size();
			if(authCount > 0){
				for(int i = 0; i < authCount; i++){
					roleList.add( authList.get(i).getRole());
				}
			}else{
				roleList.add( "USR");
			}
			
			dto.setRoleList(roleList);
		}

		return new AuthInfo(dto);
    }
    
}
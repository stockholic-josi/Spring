package com.taxholic.manage.community.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;
import com.taxholic.core.dao.CommonDao;
import com.taxholic.manage.community.dto.Memo;

@Service("com.taxholic.manage.community.service.MemoService")
public class MemoService {
	
	@Resource
	private CommonDao commonDao;
	
	public List getMemoList(Memo dto){
		
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		List list = new ArrayList();
		
		int total = this.commonDao.getInt("manage.community.memo.getMemoCount",dto);
		dto.setTotal(total);
		if(total > 0){
			dto.setLimit(10);
			dto.setTotalPage((int) Math.ceil( total / (double)dto.getLimit()));
			dto.setStart((Integer.parseInt(dto.getP()) - 1) * dto.getLimit());		
			
			list = this.commonDao.getList("manage.community.memo.getMemoList",dto);
		}
		
		return list;
	}
	
	public String getMemo(Memo dto){
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		return this.commonDao.getString("manage.community.memo.getMemo",dto);
	}

	public int memoInsert(Memo dto){
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		return this.commonDao.insert("manage.community.memo.memoInsert",dto);
	}
	
	public int memoUpdate(Memo dto){
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		return this.commonDao.update("manage.community.memo.memoUpdate",dto);
	}
	
	public int memoDelete(Memo dto){
		AuthDto auth = AuthUtil.getUser();
		dto.setUserId(auth.getUserId());
		
		return this.commonDao.delete("manage.community.memo.memoDelete",dto);
	}

}

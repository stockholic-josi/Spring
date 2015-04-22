package com.taxholic;




import java.util.Date;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.dao.CommonSqliteDao;
import com.taxholic.core.dto.FileDto;
import com.taxholic.front.board.dto.BoardFile;

@Service("com.taxholic.TestService")
public class TestService{
	
	@Resource
	private CommonSqliteDao sqliteDao;
	
	@Resource
	private CommonDao commonDao;

	public void schedule(){
		
		System.out.println(".........................." + new Date());
	}
	
	public int insert(){
		
		int gg = this.commonDao.insert("front.board.roomInsert");
		
		String[] aa = null;
		int bb = aa.length;
		
		
		return gg;
	}
	
	public List select(){
		return this.commonDao.getList("front.board.getChatList");
	}
	
	public String noCashe(){
		return this.sqliteDao.getString("front.sqlite.getTime");
		
	}
	
	@Cacheable(value="getTime")
	public String cashe(){
		return this.sqliteDao.getString("front.sqlite.getTime");
	}
	
}

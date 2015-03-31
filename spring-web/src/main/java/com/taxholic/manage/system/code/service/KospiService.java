package com.taxholic.manage.system.code.service;


import java.util.ArrayList;



import java.util.HashMap;
import java.util.Map;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.StringUtil;
import com.taxholic.core.util.SysUtil;
import com.taxholic.manage.system.code.dto.Kospi;
import com.taxholic.manage.system.code.dto.KospiArr;
import com.taxholic.manage.system.scheduler.service.HttpUtil;

@Service("com.taxholic.manage.system.code.service.KospiService")
public class KospiService {
	
	public  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private CommonDao commonDao;
	
	public JSONObject getKospiList(Kospi dto) {
		
		int count = this.commonDao.getInt("manage.system.kospi.getKospiCount",dto);
		int num = count - dto.getStart();

		List<Kospi> list = this.commonDao.getList("manage.system.kospi.getKospiList",dto);

		for(int i = 0; i < list.size(); i++) {
			
			Kospi c = (Kospi)list.get(i);
			c.setNum(num);
			list.set(i, c);
			
			num--;
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		Map map = new HashMap();  
		map.put( "totalCount", count );
		map.put("dataList", jsonArray);
		
		return JSONObject.fromObject(map);
		
	}
	
	public List getKospiExcel(Kospi dto) {
		return  this.commonDao.getList("manage.system.kospi.getKospiExcel",dto);
	}
	
	public void kospiDelete(KospiArr dto) {
		this.commonDao.delete("manage.system.kospi.kospiDelete",dto);
	}

	public int kospiInsert(List list) {
		
		int cnt = 0;
		for(int i = 0; i < list.size(); i++){
			cnt = cnt + this.commonDao.insert("manage.system.kospi.kospiInsert",list);
		}
		
		return cnt;
		
	}
	
	public List getKospiInfo(String searchString) {
		
		String[] arrStr = StringUtil.chkNull(searchString).trim().split(" ");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < arrStr.length; i++){
			if(i == 0 && !"".equals(arrStr[i]) ){
				sb.append("and info like '%" + arrStr[i] + "%'");
			}else if(!"".equals(arrStr[i])){
				sb.append(" and info like '%" + arrStr[i] + "%'");
			}
		}
		List list = ( StringUtil.chkNull(sb.toString()).equals("") ) ? new ArrayList() : this.commonDao.getList("manage.system.kospi.getKospiInfo",sb.toString());
		
		return list;
	}
	
}

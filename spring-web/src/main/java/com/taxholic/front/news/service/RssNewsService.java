package com.taxholic.front.news.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.manage.system.code.dto.NewsRss;

@Service("com.taxholic.front.news.service.RssNewsService")
public class RssNewsService{
	
	@Resource
	private CommonDao commonDao;
	
	public JSONObject getRssNewsList(NewsRss dto) {
		
		List list = this.commonDao.getList("front.rssNews.getRssNewsList",dto);

		JSONArray jsonArray = JSONArray.fromObject(list);
		
		Map map = new HashMap();  
		map.put("dataList", jsonArray);
		
		return JSONObject.fromObject(map);
	}
	
	public int getRssNewsAllCount(NewsRss dto) {
		return this.commonDao.getInt("front.rssNews.getRssNewsAllCount",dto);
	}

	public List getRssNewsAllList(NewsRss dto) {
		return this.commonDao.getList("front.rssNews.getRssNewsAllList",dto);
	}
		
	
	
}

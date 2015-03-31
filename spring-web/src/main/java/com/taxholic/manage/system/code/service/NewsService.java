package com.taxholic.manage.system.code.service;



import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.manage.system.code.dto.News;
import com.taxholic.manage.system.code.dto.NewsArr;

@Service("com.taxholic.manage.system.code.service.NewsService")
public class NewsService {
	
	@Resource
	private CommonDao commonDao;
	
	public JSONObject getNewsList(News dto) {
		
		List<News> list = this.commonDao.getList("manage.system.news.getNewsList",dto);

		JSONArray jsonArray = JSONArray.fromObject(list);

		HashMap map = new HashMap();
		map.put("dataList", jsonArray);
		
		return JSONObject.fromObject(map);
	}
	
	
	public void newsInsert(NewsArr dto){
		
		String editCd = "";
		for(int i = 0; i <dto.getLstEditCd().size(); i++){
			
			editCd = dto.getLstEditCd().get(i).toString();
			News news = new News();
			
			news.setItemGroup( dto.getLstItemGroup().get(i).toString());
			news.setNewsNm( dto.getLstNewsNm().get(i).toString());
			news.setNewsLink( dto.getLstNewsLink().get(i).toString());
			news.setCharset( dto.getLstCharset().get(i).toString());
			news.setUseYn(dto.getLstUseYn().get(i).toString());
			news.setFlag( dto.getFlag());
			news.setStp( Integer.parseInt( dto.getLstStp().get(i).toString()) );
			
			if(editCd.equals("I")){
				this.commonDao.insert("manage.system.news.newsInsert",news);
			}
			
			if(editCd.equals("U")){
				news.setSeqNo( dto.getLstSeqNo().get(i).toString() );
				this.commonDao.update("manage.system.news.newsUpdate",news);
			}
		}
	}
	
	public void newsDelete(NewsArr dto){
		
		for(int i = 0; i < dto.getLstSeqNo().size(); i++){
			this.commonDao.delete("manage.system.news.newsDelete", dto.getLstSeqNo().get(i).toString() );
		}
			
	}
	

}

package com.taxholic.manage.system.dwr.service;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.ServletContextAware;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.StringUtil;
import com.taxholic.core.util.SysUtil;
import com.taxholic.manage.system.code.dto.Kospi;
import com.taxholic.manage.system.scheduler.service.HttpUtil;


@RemoteProxy(name="httpStockSync")
public class FetchService implements  ServletContextAware{
	
	SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy/MM/dd HH:mm:ss", Locale.KOREA );
	
	@Resource
	private MessageSourceAccessor message;
	
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
	
	@Resource
	private CommonDao commonDao;
	
	
	 /**
	  * 종목가져오기
	  */
	 @RemoteMethod
	 public void fetchStockPrice(){
		 
		ServerContext sctx = ServerContextFactory.get(servletContext);
        Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(ServerContextFactory.get().getContextPath() + "/manage/system/batch/httpFetch.do");
        Util util = new Util(sessions);
		 
     	int result = 0;
     	String fileName = servletContext.getRealPath("/lib/itemList.json");
		String [] link = {"http://finance.joinsmsn.com/stock/alljuga1.html","http://finance.joinsmsn.com/stock/alljuga3.html"};
		String [] flag = {"01","02"};
		
		
		
		util.addFunctionCall("setStockPriceSyncMsg");
		
		HttpUtil httpUtil = new HttpUtil();
		List<Kospi> list = new ArrayList();
		for(int i = 0; i < link.length; i++){
			list.addAll( httpUtil.httpGetStockPrice(link[i],flag[i]) );
		}
		
		List jsonList = new ArrayList();
		for(int i =0; i < list.size(); i++){
			HashMap hm = new HashMap();
			hm.put("itemCode", list.get(i).getCodeCd() );
			hm.put("itemName", list.get(i).getCodeNm() );
			jsonList.add(hm);
		}
		
		JSONArray jsonArray = JSONArray.fromObject(jsonList);
		Map map = new HashMap();  
		map.put("dataList", jsonArray);
		
		
		if(list.size() > 0){
			//JSON 저장
			SysUtil.setFileWrite(JSONObject.fromObject(map).toString(), fileName, "utf-8");
	
			//데이터저장
			this.commonDao.delete("manage.system.kospi.kospiDelete");
			result = this.commonDao.insert("manage.system.kospi.kospiInsert",list);
			
		}
		
		util.addFunctionCall("stockPriceSyncFinsh", "<font color='#990000'> [" + sdf.format (new Date()) + "]<br><br>동기화 완료 " + StringUtil.getPrice(Integer.toString(result)) + " 개</font>");
		 
	 }
	 
	 /**
	  * 기업정보 가져오기
	  */
	 @RemoteMethod
	 public void fetchCompanyInfo(){
		 
		 ServerContext sctx = ServerContextFactory.get(servletContext);
	     Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(ServerContextFactory.get().getContextPath() + "/manage/system/batch/httpFetch.do");
	     Util util = new Util(sessions);
		 
		 HttpUtil httpUtil = new HttpUtil();
		 List<Kospi> list = this.commonDao.getList("manage.system.kospi.getKospiList",new Kospi());
		 
		 if(util != null) util.addFunctionCall("setCompanyInfoMsg","<font color='#990000'>----------------- [시작 - " + sdf.format (new Date()) + "] -----------------</font>");
		 
		 if(util != null){
			 int k = 1;
			 String info = null;
			 for( Kospi kp : list ){
				 	try{
					 info = httpUtil.httpGetCompanyInfo("http://asp01.fnguide.com/SVO/Handbook_New/xml/SVD_Main.asp?gicode=A" + kp.getCodeCd(),kp.getCodeNm()) ;
				 	}catch(Exception e){}
				 if(info == null){
					 util.addFunctionCall("setCompanyInfoMsg",k + ". "+ kp.getCodeCd() + "  " + kp.getCodeNm() + " ... 정보없음");
				 }else{
					 HashMap  hm = new HashMap();
					 hm.put("codeCd", kp.getCodeCd());
					 hm.put("info", info);
					 this.commonDao.insert("manage.system.kospi.kospiInfoInsert",hm);
					 util.addFunctionCall("setCompanyInfoMsg",k + ". "+ kp.getCodeCd() + "  " + kp.getCodeNm() + " ... <font color='#990000'>ok</font>");
				 }
					
								
				k++;
			 }
	 	}
		 
		 if(util != null) util.addFunctionCall("setCompanyInfoMsg","<font color='#990000'>----------------- [완료 - " + sdf.format (new Date()) + "] -----------------</font>");
		 if(util != null) util.addFunctionCall("companyInfoFinsh","finished");
		 
	 }
		 
}

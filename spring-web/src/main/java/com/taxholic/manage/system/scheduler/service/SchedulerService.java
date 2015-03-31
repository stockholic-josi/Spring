package com.taxholic.manage.system.scheduler.service;


import java.io.File;
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

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.core.util.StringUtil;
import com.taxholic.manage.system.code.dto.Kospi;
import com.taxholic.manage.system.code.dto.News;
import com.taxholic.manage.system.code.dto.NewsRss;
import com.taxholic.manage.system.scheduler.service.HttpUtil;

@RemoteProxy(name="httpFetch")
@Service("com.taxholic.manage.system.scheduler.service.SchedulerService")
public class SchedulerService{
	
	Log log = LogFactory.getLog("scheduling.quartz");
	SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy/MM/dd HH:mm:ss", Locale.KOREA );
	
	@Resource
	private MessageSourceAccessor message;
	
	@Resource
	private ServletContext servletContext;
	
	@Resource
	private CommonDao commonDao;
	
	public static String getFileSize(double file){	
		file = file / (1024 * 1024);
		file = (double)(int)(file * 100 + 0.5)/100.0;
		return  Double.toString(file);
	}
	
	public static Util getSessionUtil(ServerContext sctx, String page){
		 Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(ServerContextFactory.get().getContextPath() + page);
		 Util util = new Util(sessions);
		 return util;
	}
	
	
	/**
	 * 서버정보(Memory)
	 */
	 @RemoteMethod
	 public void quartzServerInfo(){
		 Util util = null;
		 ServerContext sctx = ServerContextFactory.get(servletContext);
		 if(sctx != null) {
		 
	         //Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(ServerContextFactory.get().getContextPath() + "/manage/system/batch/httpFetch.do");
	        // Util util = new Util(getRemoetSession(sctx,"/manage/system/batch/httpFetch.do"));
			 util = getSessionUtil(sctx,"/manage/system/batch/httpFetch.do");
			 
			 
	         StringBuffer sb = new StringBuffer();
	         
	         MemoryMXBean membean = (MemoryMXBean)ManagementFactory.getMemoryMXBean();
		        	 
	    	 MemoryUsage heap = membean.getHeapMemoryUsage();
	    	 
	    	 sb.append("{");
	    	 sb.append("appRate:'" + (int)(100 * Math.round( (double)heap.getUsed() / (double)heap.getMax() * 100) / 100.0) + "',");
	    	 sb.append("appMax:'" + getFileSize(heap.getMax()) + "',");
	    	 sb.append("appUsed:'" + getFileSize(heap.getUsed()) + "',");
	    	 sb.append("appAvail:'" + getFileSize(heap.getMax() - heap.getUsed()) + "',");
	    	 sb.append("}");
	    	 
	    	 if(util != null) util.addFunctionCall("setServerInfo", sb.toString());
		 }

	 }
	
	
	/**
	 * Stock Chart 가져오기
	 */
	@RemoteMethod
	public void quartzStockChart(){
		
		Util util = null;
		ServerContext sctx = ServerContextFactory.get(servletContext);
        if(sctx != null) {
        	util = getSessionUtil(sctx,"/manage/system/batch/httpFetch.do");
        }
	 
		SimpleDateFormat df = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		String uploadPath = message.getMessage("DocBase") + message.getMessage("stockChart.filePath") + "/" + df.format (new Date());
		File dir = new File(uploadPath);
		if(!dir.exists())	dir.mkdirs();
		
		List chartList = new ArrayList();
		String [] index = {
					"WWK"
					,"USD"
					,"BDI"		
					,"INDU"
					,"FTSE"
					,"DAX"
					,"NIKK"
					,"SSEC"
					,"HSI"
					,"TWII"
					,"WTIC"
					,"SOX"
					,"GOLD"
		};
		
		 if(util != null) util.addFunctionCall("setChartMsg","<font color='#990000'>----------------- [시작 - " + sdf.format (new Date()) + "] -----------------</font>");
		HttpUtil httpUtil = new HttpUtil();
		//일봉
		int k = 1;
		for(int i = 0; i < index.length; i++){
			HashMap hm = new HashMap();
			hm.put("fileNm", uploadPath + "/" + index[i] + "1.png" );
			hm.put("link", "http://stockcharts.com/c-sc/sc?s=$" + index[i] + "&p=D&i=0");
			
			 if(util != null) util.addFunctionCall("setChartMsg",k + ". "+ httpUtil.httpGetStockChart(hm) + " ... <font color='#990000'>ok</font>");
			 log.info("StockChart Fetch : " +  httpUtil.httpGetStockChart(hm));
			 k++;
			
		}
		//주봉
		for(int i = 0; i < index.length; i++){
			HashMap hm = new HashMap();
			hm.put("fileNm", uploadPath + "/" + index[i] + "2.png" );
			hm.put("link", "http://stockcharts.com/c-sc/sc?s=$" + index[i] + "&p=W&i=0");
			
			 if(util != null) util.addFunctionCall("setChartMsg",k + ". "+ httpUtil.httpGetStockChart(hm) + " ... <font color='#990000'>ok</font>");
			 log.info("StockChart Fetch : " +  httpUtil.httpGetStockChart(hm));
			 k++;
			
		}
		 if(util != null) util.addFunctionCall("setChartMsg","<font color='#990000'>----------------- [완료 - " + sdf.format (new Date()) + "] -----------------</font>");
		 if(util != null) util.addFunctionCall("chartFetchFinsh","finished");
	}	
	

	/**
	 * Rss 뉴스 가져오기
	 */
	@RemoteMethod
	public void quartzNewsRss(){
		
		Util util = null;
		ServerContext sctx = ServerContextFactory.get(servletContext);
        if(sctx != null) {
        	util = getSessionUtil(sctx,"/manage/system/batch/httpFetch.do");
        }
		
		if(util != null) util.addFunctionCall("setRssNewsMsg","<font color='#990000'>----------------- [시작 - " + sdf.format (new Date()) + "] -----------------</font>");

        News dto = new News();
		dto.setUseYn("Y");
		List<News> list = this.commonDao.getList("manage.system.news.getNewsList",dto);
		
		HttpUtil httpUtil = new HttpUtil();
				
		 int k = 1;
		 for(int i =0; i < list.size(); i++){
		 int result = 0;
			
			 HashMap hm = new HashMap();
			 hm.put("link", list.get(i).getNewsLink());
			 hm.put("charset", list.get(i).getCharset());
			 hm.put("seqNo", list.get(i).getSeqNo());
			 hm.put("itemNm", list.get(i).getItemNm());
			 hm.put("flag", list.get(i).getFlag());
			 
			 List  rssList =  null;
			 try{
				 rssList = httpUtil.httpGetRss(hm); 
				 System.out.println(">>>>>>>>>>>>>" + rssList.size());
			 }catch(Exception e){}

			 if(rssList != null){
				
				for(int j = 0; j < rssList.size(); j++){
					this.commonDao.insert("mansge.system.scheduler.rssNewsInsert", (NewsRss)rssList.get(j) );
					result++;
				}
				
			 }
			 
			 if(util != null) util.addFunctionCall("setRssNewsMsg",k + ". "+ list.get(i).getNewsNm() + " : "  + list.get(i).getNewsLink() + " ... <font color='#990000'>" + result + "개 ok</font>");
			 
			 log.info("News Fetch : " + list.get(i).getNewsNm() + " : "  + list.get(i).getNewsLink() + " ... " + result);
			 k++;
		}
		
		 if(util != null) util.addFunctionCall("setRssNewsMsg","<font color='#990000'>----------------- [완료 - " + sdf.format (new Date()) + "] -----------------</font>");
		 if(util != null) util.addFunctionCall("newsFetchFinsh","finished");
		 
	}
	
	/**
	 * 종목시세 가져오기
	 */
	@RemoteMethod
	public void quartzStockPrice(){
		
		Util util = null;
		ServerContext sctx = ServerContextFactory.get(servletContext);
        if(sctx != null) {
        	util = getSessionUtil(sctx,"/manage/system/batch/httpFetch.do");
        }
		
        if(util != null)  util.addFunctionCall("setStockPriceUpdateMsg");
		
        int result = 0;
		HttpUtil httpUtil = new HttpUtil();
		String [] link = {"http://finance.joinsmsn.com/stock/alljuga1.html","http://finance.joinsmsn.com/stock/alljuga3.html"};
		String [] flag = {"01","02"};
		
		List<Kospi> list = new ArrayList();
		for(int i = 0; i < link.length; i++){
			List kpList = httpUtil.httpGetStockPrice(link[i],flag[i]);
			
			for(int j = 0; j < kpList.size(); j++){
				this.commonDao.update("mansge.system.scheduler.kospiUpdate", (Kospi)kpList.get(j) );
				result++;
			}
			
		}
		
		 if(util != null) util.addFunctionCall("stockPriceUpdateFinsh","<font color='#990000'>[" + sdf.format (new Date()) + "]<br><br>업데이트 완료 " + StringUtil.getPrice(Integer.toString(result)) + " 개</font>");
		
		log.info("StockPrice Fetch : " + result);
		
	}
	
}

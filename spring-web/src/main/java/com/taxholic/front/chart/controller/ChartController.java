package com.taxholic.front.chart.controller;



import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.Rotation;
import org.jfree.util.TableOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller("com.taxholic.front.chart.controller.ChartController")
@RequestMapping("/front/chart/*.do")
public class ChartController  {
	
	public  Log log = LogFactory.getLog(getClass());
	
	/**
	 * 주식차트
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView stockChart() {
		
		ModelAndView mv = new ModelAndView("front/chart/stockChart");

		return mv;
	
	}
	
	/**
	 * 주식차트 Ajax
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping
	public ModelAndView stockChartAjax( HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("front/chart/stockChartAjax");
		
		String flag = "";
		String chartNm = "";
		try {
			flag = ServletRequestUtils.getStringParameter(request, "flag");
			chartNm = ServletRequestUtils.getStringParameter(request, "chartNm");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		
		mv.addObject("flag",flag);
		mv.addObject("chartNm",chartNm);

		return mv;
	
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------ Bar Chart
	@RequestMapping
	public void barChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(10,"S1","C1");
			dataset.addValue(8,"S1","C2");
			dataset.addValue(8,"S1","C3");
			dataset.addValue(4,"S1","C4");
			dataset.addValue(12,"S1","C5");
			dataset.addValue(11,"S1","C6");
			dataset.addValue(2,"S1","C7");
			dataset.addValue(3,"S1","C8");
			
			//create a chart
			JFreeChart chart = ChartFactory.createBarChart(
			"BarChart",
			"Category",
			"Values",
			dataset,
			PlotOrientation.VERTICAL,
			false,	//legend
			true,		//tooltips
			false	//URLs
			);
		
			//Bar color
			CategoryPlot plot = chart.getCategoryPlot();
			BarRenderer renderer = (BarRenderer)plot.getRenderer();
			renderer.setSeriesPaint(0,Color.orange);
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
		
	}
	
	@RequestMapping
	public void barChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(10,"S1","C1");
			dataset.addValue(8,"S1","C2");
			dataset.addValue(8,"S1","C3");
			dataset.addValue(4,"S1","C4");
			dataset.addValue(12,"S1","C5");
			dataset.addValue(11,"S1","C6");
			dataset.addValue(2,"S1","C7");
			dataset.addValue(3,"S1","C8");
			
			//create a chart
			JFreeChart chart=ChartFactory.createBarChart(
			"BarChart",
			"Category",
			"Values",
			dataset,
			PlotOrientation.HORIZONTAL,
			false,	//legend
			true,		//tooltips
			false	//URLs
			);
		
			//Bar color
			CategoryPlot plot = chart.getCategoryPlot();
			BarRenderer renderer = (BarRenderer)plot.getRenderer();
			renderer.setSeriesPaint(0,new Color(88,174,226));
	        renderer.setMaximumBarWidth(0.05);		//Bar 두께					
		
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void barChart3( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(10.0,"S1","C1");
			dataset.addValue(4.0,"S1","C2");
			dataset.addValue(15.0,"S1","C3");
			dataset.addValue(14.0,"S1","C4");
			dataset.addValue(-5.0,"S2","C1");
			dataset.addValue(-7.0,"S2","C2");
			dataset.addValue(14.0,"S2","C3");
			dataset.addValue(-3.0,"S2","C4");
			dataset.addValue(6.0,"S3","C1");
			dataset.addValue(17.0,"S3","C2");
			dataset.addValue(-12.0,"S3","C3");
			dataset.addValue(7.0,"S3","C4");
			dataset.addValue(7.0,"S4","C1");
			dataset.addValue(15.0,"S4","C2");
			dataset.addValue(11.0,"S4","C3");
			dataset.addValue(0.0,"S4","C4");
			dataset.addValue(-8.0,"S5","C1");
			dataset.addValue(-6.0,"S5","C2");
			dataset.addValue(10.0,"S5","C3");
			dataset.addValue(-9.0,"S5","C4");
			dataset.addValue(9.0,"S6","C1");
			dataset.addValue(8.0,"S6","C2");
			dataset.addValue(null,"S6","C3");
			dataset.addValue(6.0,"S6","C4");
			dataset.addValue(-10.0,"S7","C1");
			dataset.addValue(9.0,"S7","C2");
			dataset.addValue(7.0,"S7","C3");
			dataset.addValue(7.0,"S7","C4");
			dataset.addValue(11.0,"S8","C1");
			dataset.addValue(13.0,"S8","C2");
			dataset.addValue(9.0,"S8","C3");
			dataset.addValue(9.0,"S8","C4");
			dataset.addValue(-3.0,"S9","C1");
			dataset.addValue(7.0,"S9","C2");
			dataset.addValue(11.0,"S9","C3");
			dataset.addValue(-10.0,"S9","C4");
			
			//create a chart
			JFreeChart chart=ChartFactory.createBarChart(
				"BarChart",
				"Category",
				"Values",
				dataset,
				PlotOrientation.VERTICAL,
				true,		//legend
				true,		//tooltips
				false	//URLs
			);
		
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void barChart4( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        dataset.addValue(25.0, "Series 1", "Category 1");   
	        dataset.addValue(34.0, "Series 1", "Category 2");   
	        dataset.addValue(19.0, "Series 2", "Category 1");   
	        dataset.addValue(29.0, "Series 2", "Category 2");   
	        dataset.addValue(41.0, "Series 3", "Category 1");   
	        dataset.addValue(33.0, "Series 3", "Category 2");   

			
			//create a chart
	        JFreeChart chart = ChartFactory.createBarChart3D(
                "3D Bar Chart",    	  // chart title
                "Category",               // domain axis label
                "Value",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
            );

		
	        CategoryPlot plot = chart.getCategoryPlot();
	        CategoryAxis axis = plot.getDomainAxis();
	        axis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
	        );
	        
	        CategoryItemRenderer renderer = plot.getRenderer();
	        BarRenderer r = (BarRenderer) renderer;
	        r.setMaximumBarWidth(0.05);

		
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void barChart5( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
			dataset.add(32.5, 17.9, "Series 1", "Type 1");
	        dataset.add(27.8, 11.4, "Series 1", "Type 2");
	        dataset.add(29.3, 14.4, "Series 1", "Type 3");
	        dataset.add(37.9, 10.3, "Series 1", "Type 4");

	        dataset.add(22.9,  7.9, "Series 2", "Type 1");
	        dataset.add(21.8, 18.4, "Series 2", "Type 2");
	        dataset.add(19.3, 12.4, "Series 2", "Type 3");
	        dataset.add(30.3, 20.7, "Series 2", "Type 4");

	        dataset.add(12.5, 10.9, "Series 3", "Type 1");
	        dataset.add(24.8,  7.4, "Series 3", "Type 2");
	        dataset.add(19.3, 13.4, "Series 3", "Type 3");
	        dataset.add(17.1, 10.6, "Series 3", "Type 4");

			
			//create a chart
	        CategoryAxis xAxis = new CategoryAxis("Type");
	        xAxis.setLowerMargin(0.01d); // percentage of space before first bar
	        xAxis.setUpperMargin(0.01d); // percentage of space after last bar
	        xAxis.setCategoryMargin(0.05d); // percentage of space between categories
	        ValueAxis yAxis = new NumberAxis("Value");

	        // define the plot
	        CategoryItemRenderer renderer = new StatisticalBarRenderer();
	        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
	        JFreeChart chart = new JFreeChart("Statistical Bar Chart Demo", new Font("Helvetica", Font.BOLD, 14), plot, true);
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void barChart6( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        dataset.addValue(25.0, "Series 1", "Category 1");   
	        dataset.addValue(34.0, "Series 1", "Category 2");   
	        dataset.addValue(19.0, "Series 2", "Category 1");   
	        dataset.addValue(29.0, "Series 2", "Category 2");   
	        dataset.addValue(41.0, "Series 3", "Category 1");   
	        dataset.addValue(33.0, "Series 3", "Category 2");   

			
			//create a chart
	        JFreeChart chart = ChartFactory.createBarChart(
                "바차트 꾸미기",    			// chart title
                "Category",               			// domain axis label
                "Value",                				// range axis label
                dataset,                  			// data
                PlotOrientation.VERTICAL, 	// orientation
                true,                     			// include legend
                true,                     			// tooltips
                false                     			// urls
            );
	        
	        //챠트 배경색 바꾸기
	        chart.setBackgroundPaint(Color.WHITE);
	        
	        //범례(Legend) 위치조정
	        LegendTitle legend = (LegendTitle)chart.getSubtitle(0); 
	        legend.setPosition(RectangleEdge.RIGHT); 
	        legend.setVerticalAlignment(VerticalAlignment.TOP); 

	  	        
	        //그래프 전체의 경계선 설정하기
	        chart.setBorderVisible(true);                		 // 챠트전체의 경계선이 나타난다.
	        chart.setBorderPaint(Color.BLUE);          		 // 챠트전체의 경계선의 색을 파란색으로 정한다.
	        chart.setBorderStroke(new BasicStroke(5));   // 챠트전체의 경계선의 두께를 정한다
	        
	        // Plot 영역 조정하기
	        CategoryPlot plot = chart.getCategoryPlot();  	// 챠트의 Plot 객체를 구한다.
	        plot.setBackgroundPaint(Color.lightGray);     // 챠트의 Plot 배경색을 lightGray로 바꾼다.
	        plot.setRangeGridlinePaint(Color.BLUE);       // 수평 그리드라인의 색을 BLUE로 바꾼다.
	        plot.setDomainGridlinesVisible(false);        	// 수직 그리드라인을 안보이게 한다.
	        
	        //횡축 설정하기
	        CategoryAxis axis = plot.getDomainAxis();     // 횡축 객체 구하기
	        axis.setLowerMargin(0.03);  						// 횡축의 가장 왼쪽과 가장 왼쪽 봉과의 여백
	        axis.setUpperMargin(0.03);  						// 횡축의 가장 오른쪽과 가장 오른쪽 봉과의 여백
	        axis.setCategoryLabelPositions
	        (CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
	        																// 카테고리명을 위로 향하게 비스듬히 나타나도록 한다.

	        //종축 설정하기
	        // "getRangeAxis()"로 종축 설정을 구한다.
	        // "setStandardTickUnits()"로 정수값만을 표시하도록 설정한다.
	        // (여기선 종축이 수치이기 때문에 NumberAxis로 캐스트했지만
	        // 종축이 일자인 경우에는 DateAxis,
	        // 시각인 경우에는 PeriodAxis로 캐스트한다.
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        
	        
	        // BAR 커스터마이즈하기
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();  // BarRenderer를 구한다.
	        renderer.setItemMargin(0.05);              							  	// BAR 와 BAR 여백을 정한다.
	        renderer.setDrawBarOutline(false);            						// BAR 경계선 표시를 설정
	        renderer.setMaximumBarWidth(0.05);									// BAR 두깨 설정
	        
	        //BAR 색깔 바꾸기
	        renderer.setSeriesPaint(0, new Color(0,0,255));
	        renderer.setSeriesPaint(1, new Color(255,0,0));
	        renderer.setSeriesPaint(2, new Color(255,255,0));

	        
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------ Bar Chart
	@RequestMapping
	public void lineChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(212,"Classes","JDK1.0");
			dataset.addValue(504,"Classes","JDK1.1");
			dataset.addValue(1520,"Classes","SDK1.2");
			dataset.addValue(1842,"Classes","SDK1.3");
			
			//createthechart...
			JFreeChart chart = ChartFactory.createLineChart(
				"JavaStandardClassLibrary",	//charttitle
				"Release",								//domainaxislabel
				"ClassCount",						//rangeaxislabel
				dataset,									//data
				PlotOrientation.VERTICAL,		//orientation
				false,									//legend
				true,										//tooltips
				false									//urls
			);
			
	
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void lineChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(212,"Classes","JDK1.0");
			dataset.addValue(504,"Classes","JDK1.1");
			dataset.addValue(1520,"Classes","SDK1.2");
			dataset.addValue(1842,"Classes","SDK1.3");
			
			//createthechart...
			JFreeChart chart = ChartFactory.createLineChart(
				"JavaStandardClassLibrary",	//char ttitle
				"Release",								//domainaxis label
				"ClassCount",						//rangeaxis label
				dataset,									//data
				PlotOrientation.VERTICAL,		//orientation
				false,									//legend
				true,										//tooltips
				false									//urls
			);
			
			
		//서브 타이틀
		chart.addSubtitle(new TextTitle("Number of Classes By Release"));
		TextTitle source = new TextTitle("By JS.Park");
		source.setFont(new Font("gulim",Font.PLAIN,12));
		source.setPosition(RectangleEdge.BOTTOM);
		source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		chart.addSubtitle(source);
		
		//배경색
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = (CategoryPlot)chart.getPlot();
		plot.setBackgroundPaint(new Color(245, 245, 245));
		plot.setRangeGridlinePaint(new Color(136, 136, 136));
		
		//customise the rangeaxis...
		//NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		//customise the renderer...
		LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setFillPaint(Color.white);
		renderer.setSeriesPaint(0,Color.blue);
		
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void lineChart3( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			XYSeries series1 = new XYSeries("First");
			series1.add(1.0,1.0);
			series1.add(2.0,4.0);
			series1.add(3.0,3.0);
			series1.add(4.0,5.0);
			series1.add(5.0,5.0);
			series1.add(6.0,7.0);
			series1.add(7.0,7.0);
			series1.add(8.0,8.0);

			XYSeries series2 = new XYSeries("Second");
			series2.add(1.0,5.0);
			series2.add(2.0,7.0);
			series2.add(3.0,6.0);
			series2.add(4.0,8.0);
			series2.add(5.0,4.0);
			series2.add(6.0,4.0);
			series2.add(7.0,2.0);
			series2.add(8.0,1.0);

			XYSeries series3 = new XYSeries("Third");
			series3.add(3.0,4.0);
			series3.add(4.0,3.0);
			series3.add(5.0,2.0);
			series3.add(6.0,3.0);
			series3.add(7.0,6.0);
			series3.add(8.0,3.0);
			series3.add(9.0,4.0);
			series3.add(10.0,3.0);

			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);			
			
			//createthechart...
			JFreeChart chart = ChartFactory.createXYLineChart(
				"Line Chart Based On An XYDataset",		//chart title
				"X",							//xaxis label
				"Y",							//yaxis label
				dataset,						//data
				PlotOrientation.VERTICAL,
				true,							//include legend
				true,							//tooltips
				false						//urls
			);
			
		XYPlot plot = (XYPlot)chart.getPlot();
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setShapesFilled(true);
		renderer.setSeriesPaint(0,Color.blue);
		renderer.setSeriesPaint(1,Color.red);
		renderer.setSeriesPaint(2,Color.orange);
		
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void lineChart4( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{

	         String category1 = "Category 1";
	         String category2 = "Category 2";
	         String category3 = "Category 3";
	         String category4 = "Category 4";
	         String category5 = "Category 5";
	         String category6 = "Category 6";
	         String category7 = "Category 7";
	         String category8 = "Category 8";

	        // create the dataset1...
	         DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
	        dataset1.addValue(1.0, "First", category1);
	        dataset1.addValue(4.0, "First", category2);
	        dataset1.addValue(3.0, "First", category3);
	        dataset1.addValue(5.0, "First", category4);
	        dataset1.addValue(5.0, "First", category5);
	        dataset1.addValue(7.0, "First", category6);
	        dataset1.addValue(7.0, "First", category7);
	        dataset1.addValue(8.0, "First", category8);
	        dataset1.addValue(5.0, "Second", category1);
	        dataset1.addValue(7.0, "Second", category2);
	        dataset1.addValue(6.0, "Second", category3);
	        dataset1.addValue(8.0, "Second", category4);
	        dataset1.addValue(4.0, "Second", category5);
	        dataset1.addValue(4.0, "Second", category6);
	        dataset1.addValue(2.0, "Second", category7);
	        dataset1.addValue(1.0, "Second", category8);
	        dataset1.addValue(4.0, "Third", category1);
	        dataset1.addValue(3.0, "Third", category2);
	        dataset1.addValue(2.0, "Third", category3);
	        dataset1.addValue(3.0, "Third", category4);
	        dataset1.addValue(6.0, "Third", category5);
	        dataset1.addValue(3.0, "Third", category6);
	        dataset1.addValue(4.0, "Third", category7);
	        dataset1.addValue(3.0, "Third", category8);

	        // create the dataset2...
	        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
	        dataset2.addValue(15.0, "Fourth", category1);
	        dataset2.addValue(24.0, "Fourth", category2);
	        dataset2.addValue(31.0, "Fourth", category3);
	        dataset2.addValue(25.0, "Fourth", category4);
	        dataset2.addValue(56.0, "Fourth", category5);
	        dataset2.addValue(37.0, "Fourth", category6);
	        dataset2.addValue(77.0, "Fourth", category7);
	        dataset2.addValue(18.0, "Fourth", category8);
	        
	        JFreeChart chart = ChartFactory.createBarChart(
                "Dual Axis Chart",        // chart title
                "Category",               // domain axis label
                "Value",                  // range axis label
                dataset1,                 // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips?
                false                     // URL generator?  Not required...
            );

	        CategoryPlot plot = chart.getCategoryPlot();
	        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));

	        plot.setDataset(1, dataset2);
	        plot.mapDatasetToRangeAxis(1, 1);
	        
	        //Category 설정
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
	        
	        //Y축 생성
	        ValueAxis axis2 = new NumberAxis("Secondary");
	        plot.setRangeAxis(1, axis2);
	        
	        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
	        renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	        renderer2.setSeriesPaint(0,new Color(0,128,255));
	        plot.setRenderer(1, renderer2);
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
	        

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------ Pie Chart
	@RequestMapping
	public void pieChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			//create a data set
			DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("One", 43);
	        dataset.setValue("Two", 10);
	        dataset.setValue("Three", 27);
	        dataset.setValue("Four", 17);
	        dataset.setValue("Five", 11.0);
	        dataset.setValue("Six", 19.4);
			
			//create a chart
	        JFreeChart chart = ChartFactory.createPieChart(
	            "Pie Chart",  // chart title
	            dataset,             // data
	            false,               // include legend
	            true,
	            false
	        );
	        chart.setBackgroundPaint(Color.WHITE);
	        
	        PiePlot plot = (PiePlot) chart.getPlot();
	        plot.setBackgroundPaint(null);
	        plot.setOutlineStroke(null);
	        
	        //plot.setLabelGenerator(null);
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void pieChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			//create a data set
			DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("One", 43);
	        dataset.setValue("Two", 10);
	        dataset.setValue("Three", 27);
	        dataset.setValue("Four", 17);
	        dataset.setValue("Five", 11.0);
	        dataset.setValue("Six", 19.4);
			
			//create a chart
	        JFreeChart chart = ChartFactory.createPieChart(
	            "Pie Chart",  		// chart title
	            dataset,             // data
	            true,               // include legend
	            true,
	            false
	        );
	        

	        PiePlot plot = (PiePlot) chart.getPlot();
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	            "{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
	        ));
	        plot.setNoDataMessage("No data available");
	        plot.setExplodePercent(1, 0.30);	
     
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
			
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void pieChart3( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			//create a data set
			DefaultPieDataset dataset = new DefaultPieDataset();
			dataset.setValue("Java", new Double(43.2));
	        dataset.setValue("Visual Basic", new Double(10.0));
	        dataset.setValue("C/C++", new Double(17.5));
	        dataset.setValue("PHP", new Double(32.5));
	        dataset.setValue("Perl", new Double(1.0));
			
			//create a chart
	        JFreeChart chart = ChartFactory.createPieChart3D(
	            "Pie Chart",  		// chart title
	            dataset,             // data
	            false,               // include legend
	            true,
	            false
	        );
	        

	        PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.7f);
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	            "{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
	        ));
	        plot.setNoDataMessage("No data to display");
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
			
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void pieChart4( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			double[][] data = new double[][] {
	            {3.0, 4.0, 3.0, 5.0},
	            {5.0, 7.0, 6.0, 8.0},
	            {5.0, 7.0, 3.0, 8.0},
	            {1.0, 2.0, 3.0, 4.0},
	            {2.0, 3.0, 2.0, 3.0}
	        };
	        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
	            "Region ",
	            "Sales/Q",
	            data
	        );

	        
	        JFreeChart chart = ChartFactory.createMultiplePieChart(
                "Multiple Pie Chart",
                dataset,
                TableOrder.BY_ROW,
                //TableOrder.BY_COLUMN,
                true,
                true,
                false
	        );
	        
	        MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
	        plot.setBackgroundPaint(Color.white);
	        plot.setOutlineStroke(new BasicStroke(1.0f));
	        JFreeChart subchart = plot.getPieChart();
	        PiePlot p = (PiePlot) subchart.getPlot();
	        p.setBackgroundPaint(null);
	        p.setOutlineStroke(null);
	        p.setLabelGenerator(new StandardPieSectionLabelGenerator(
	        		"{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		    ));
	
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,800,500);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------ Area Chart
	@RequestMapping
	public void areaChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			double[][] data = new double[][] {
	            {1.0, 4.0, 3.0, 5.0, 5.0, 7.0, 7.0, 8.0},
	            {5.0, 7.0, 6.0, 8.0, 4.0, 4.0, 2.0, 1.0},
	            {4.0, 3.0, 2.0, 3.0, 6.0, 3.0, 4.0, 3.0}
	        };

	        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
	            "Series ", "Type ", data
	        );

	        //create a chart
	        JFreeChart chart = ChartFactory.createAreaChart(
                "Area Chart",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
	        );
	     

	        chart.setBackgroundPaint(Color.white);
            TextTitle subtitle = new TextTitle("An area chart demonstration.  We use this "
                + "subtitle as an example of what happens when you get a really long title or "
                + "subtitle.");
            subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
            subtitle.setPosition(RectangleEdge.TOP);
            subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            chart.addSubtitle(subtitle);

            CategoryPlot plot = chart.getCategoryPlot();
            plot.setForegroundAlpha(0.5f);
            
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.white);
           
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            domainAxis.setLowerMargin(0.0);
            domainAxis.setUpperMargin(0.0);
            domainAxis.addCategoryLabelToolTip("Type 1", "The first type.");
            domainAxis.addCategoryLabelToolTip("Type 2", "The second type.");
            domainAxis.addCategoryLabelToolTip("Type 3", "The third type.");
            
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
	

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}

	@RequestMapping
	public void areaChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			//create a data set
			double[][] data = new double[][] {
	            {1.0, 4.0, 3.0, 5.0, 5.0, 7.0, 7.0, 8.0 },
	            {5.0, 7.0, 6.0, 8.0, 4.0, 4.0, 2.0, 1.0 },
	            {4.0, 3.0, 2.0, 3.0, 6.0, 3.0, 4.0, 3.0 }
	        };


	        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
	            "Series ", "Type ", data
	        );

	        //create a chart
	        JFreeChart chart = ChartFactory.createAreaChart(
                "Stacked Area Chart",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
	        );
	     

	        chart.setBackgroundPaint(Color.white);

	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setForegroundAlpha(0.5f);
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setLowerMargin(0.0);
	        domainAxis.setUpperMargin(0.0);

	        // change the auto tick unit selection to integer units only...
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        CategoryItemRenderer renderer = plot.getRenderer();
	        renderer.setItemLabelsVisible(true);

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------ Dual Chart
	
	private CategoryDataset createDataset1() {

        // row keys...
        final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";
        final String category6 = "Category 6";
        final String category7 = "Category 7";
        final String category8 = "Category 8";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);
        dataset.addValue(7.0, series1, category6);
        dataset.addValue(7.0, series1, category7);
        dataset.addValue(8.0, series1, category8);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);
        dataset.addValue(4.0, series2, category6);
        dataset.addValue(2.0, series2, category7);
        dataset.addValue(1.0, series2, category8);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);
        dataset.addValue(3.0, series3, category6);
        dataset.addValue(4.0, series3, category7);
        dataset.addValue(3.0, series3, category8);

        return dataset;

    }

    private CategoryDataset createDataset2() {

        // row keys...
        final String series1 = "Fourth";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";
        final String category6 = "Category 6";
        final String category7 = "Category 7";
        final String category8 = "Category 8";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(15.0, series1, category1);
        dataset.addValue(24.0, series1, category2);
        dataset.addValue(31.0, series1, category3);
        dataset.addValue(25.0, series1, category4);
        dataset.addValue(56.0, series1, category5);
        dataset.addValue(37.0, series1, category6);
        dataset.addValue(77.0, series1, category7);
        dataset.addValue(18.0, series1, category8);

        return dataset;

    }
    
    @RequestMapping
    public XYDataset createDataset3() {

        TimePeriodValues s1 = new TimePeriodValues("Supply");
        TimePeriodValues s2 = new TimePeriodValues("Demand");
        Day today = new Day();
        for (int i = 0; i < 24; i++) {
            Minute m0 = new Minute(0, new Hour(i, today));
            Minute m1 = new Minute(15, new Hour(i, today));
            Minute m2 = new Minute(30, new Hour(i, today));
            Minute m3 = new Minute(45, new Hour(i, today));
            Minute m4 = new Minute(0, new Hour(i + 1, today));
            s1.add(new SimpleTimePeriod(m0.getStart(), m1.getStart()), Math.random());
            s2.add(new SimpleTimePeriod(m1.getStart(), m2.getStart()), Math.random());
            s1.add(new SimpleTimePeriod(m2.getStart(), m3.getStart()), Math.random());
            s2.add(new SimpleTimePeriod(m3.getStart(), m4.getStart()), Math.random());
        }

        TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;

    }

    @RequestMapping
	public XYDataset createDataset4() {

        TimePeriodValues s1 = new TimePeriodValues("WebCOINS");
        Day today = new Day();
        for (int i = 0; i < 24; i++) {
            Minute m0 = new Minute(0, new Hour(i, today));
            Minute m1 = new Minute(30, new Hour(i, today));
            Minute m2 = new Minute(0, new Hour(i + 1, today));
            s1.add(new SimpleTimePeriod(m0.getStart(), m1.getStart()), Math.random() * 2.0);
            s1.add(new SimpleTimePeriod(m1.getStart(), m2.getStart()), Math.random() * 2.0);
        }

        TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
        dataset.addSeries(s1);

        return dataset;

    }
	
    @RequestMapping
	public void dualChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			CategoryDataset dataset1 = createDataset1();
			JFreeChart chart = ChartFactory.createBarChart(
	            "Dual Axis Chart",
	            "Category",
	            "Value",
	            dataset1,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );

			CategoryPlot plot = chart.getCategoryPlot();
			CategoryDataset dataset2 = createDataset2();
	        plot.setDataset(1, dataset2);
	        
	        //Category 설정
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
	        plot.mapDatasetToRangeAxis(1, 1);
	        
	        //Y축 생성
	        ValueAxis axis2 = new NumberAxis("Secondary");
	        plot.setRangeAxis(1, axis2);

	        //라인생성
	        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
	        renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	        renderer2.setSeriesPaint(0,new Color(0,128,255));
	        plot.setRenderer(1, renderer2);
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
	        
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
    @RequestMapping
	public void dualChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			CategoryDataset dataset1 = createDataset1();
			JFreeChart chart = ChartFactory.createBarChart(
	            "Dual Axis Chart",
	            "Category",
	            "Value",
	            dataset1,
	            PlotOrientation.HORIZONTAL,
	            true,
	            true,
	            false
	        );

			chart.setBackgroundPaint(new Color(0xCC, 0xFF, 0xCC));
	        
			CategoryPlot plot = chart.getCategoryPlot();
	        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	        CategoryDataset dataset2 = createDataset2();
	        
	        ValueAxis axis2 = new NumberAxis("Secondary");
	        plot.setRangeAxis(1, axis2);
	        
	        plot.setDataset(1, dataset2);
	        plot.mapDatasetToRangeAxis(1, 1);
	        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
	        
	        CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
	        renderer2.setSeriesPaint(0,new Color(0,128,255));
	        plot.setRenderer(1, renderer2);
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
    @RequestMapping
	public void dualChart3( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			CategoryDataset dataset1 = createDataset1();
			
			JFreeChart chart = ChartFactory.createBarChart3D(
	            "Dual Axis Chart",
	            "Category",
	            "Value",
	            dataset1,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );

			CategoryPlot plot = chart.getCategoryPlot();
	        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
	        plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
	        
	        CategoryItemRenderer renderer1 = plot.getRenderer();
	        renderer1.setSeriesPaint(0, Color.red);
	        renderer1.setSeriesPaint(1, Color.yellow);
	        renderer1.setSeriesPaint(2, Color.green);
	        
	        CategoryDataset dataset2 = createDataset2();
	        ValueAxis axis2 = new NumberAxis3D("Secondary");
	        plot.setRangeAxis(1, axis2);
	        plot.setDataset(1, dataset2);
	        plot.mapDatasetToRangeAxis(1, 1);
	        
	        CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
	        renderer2.setSeriesPaint(0, Color.blue);
	        plot.setRenderer(1, renderer2);

	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
    @RequestMapping
	public void dualChart4( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
			
			XYDataset data1 = createDataset3();
	        XYItemRenderer renderer1 = new XYBarRenderer();
	        
	        DateAxis domainAxis = new DateAxis("Date");
	        domainAxis.setVerticalTickLabels(true);
	        domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 1));
	        domainAxis.setDateFormatOverride(new SimpleDateFormat("hh:mm"));
	        domainAxis.setLowerMargin(0.01);
	        domainAxis.setUpperMargin(0.01);
	        ValueAxis rangeAxis = new NumberAxis("Value");
	        
	        XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);

	        XYDataset data2 = createDataset4();
	        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
	        renderer2.setShapesFilled(true);
	        
	        plot.setDataset(1, data2);
	        plot.setRenderer(1, renderer2);

	        
	        JFreeChart chart = new JFreeChart("Time Period Values", plot);

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------ Combinded Chart
	
	private XYDataset combindedDataset1() {
        // create dataset 1...
        XYSeries series1 = new XYSeries("Series 1");
        series1.add(10.0, 12353.3);
        series1.add(20.0, 13734.4);
        series1.add(30.0, 14525.3);
        series1.add(40.0, 13984.3);
        series1.add(50.0, 12999.4);
        series1.add(60.0, 14274.3);
        series1.add(70.0, 15943.5);
        series1.add(80.0, 14845.3);
        series1.add(90.0, 14645.4);
        series1.add(100.0, 16234.6);
        series1.add(110.0, 17232.3);
        series1.add(120.0, 14232.2);
        series1.add(130.0, 13102.2);
        series1.add(140.0, 14230.2);
        series1.add(150.0, 11235.2);

        XYSeries series2 = new XYSeries("Series 2");
        series2.add(10.0, 15000.3);
        series2.add(20.0, 11000.4);
        series2.add(30.0, 17000.3);
        series2.add(40.0, 15000.3);
        series2.add(50.0, 14000.4);
        series2.add(60.0, 12000.3);
        series2.add(70.0, 11000.5);
        series2.add(80.0, 12000.3);
        series2.add(90.0, 13000.4);
        series2.add(100.0, 12000.6);
        series2.add(110.0, 13000.3);
        series2.add(120.0, 17000.2);
        series2.add(130.0, 18000.2);
        series2.add(140.0, 16000.2);
        series2.add(150.0, 17000.2);

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
        collection.addSeries(series2);
        return collection;
    }
	
	private XYDataset combindedDataset2() {

        // create dataset 2...
        XYSeries series2 = new XYSeries("Series 3");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

    }
	
	private XYDataset combindedDataset3() {
        // create dataset 1...
        XYSeries series1 = new XYSeries("Series 1");
        series1.add(10.0, 12353.3);
        series1.add(20.0, 13734.4);
        series1.add(30.0, 14525.3);
        series1.add(40.0, 13984.3);
        series1.add(50.0, 12999.4);
        series1.add(60.0, 14274.3);
        series1.add(70.0, 15943.5);
        series1.add(80.0, 14845.3);
        series1.add(90.0, 14645.4);
        series1.add(100.0, 16234.6);
        series1.add(110.0, 17232.3);
        series1.add(120.0, 14232.2);
        series1.add(130.0, 13102.2);
        series1.add(140.0, 14230.2);
        series1.add(150.0, 11235.2);

        XYSeries series2 = new XYSeries("Series 2");
        series2.add(10.0, 15000.3);
        series2.add(20.0, 11000.4);
        series2.add(30.0, 17000.3);
        series2.add(40.0, 15000.3);
        series2.add(50.0, 14000.4);
        series2.add(60.0, 12000.3);
        series2.add(70.0, 11000.5);
        series2.add(80.0, 12000.3);
        series2.add(90.0, 13000.4);
        series2.add(100.0, 12000.6);
        series2.add(110.0, 13000.3);
        series2.add(120.0, 17000.2);
        series2.add(130.0, 18000.2);
        series2.add(140.0, 16000.2);
        series2.add(150.0, 17000.2);

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
        collection.addSeries(series2);
        return collection;
    }
	
	private XYDataset combindedDataset4() {

        // create dataset 2...
        XYSeries series2 = new XYSeries("Series 3");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

    }
	
	@RequestMapping
	public void combindedChart1( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
		
			//create a chart
			 // create subplot 1...
	        XYDataset data1 = combindedDataset1();
	        XYItemRenderer renderer1 = new StandardXYItemRenderer();
	        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
	        XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
	        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
	        
	        XYTextAnnotation annotation = new XYTextAnnotation("Hello!", 50.0, 10000.0);
	        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
	        annotation.setRotationAngle(Math.PI / 4.0);
	        subplot1.addAnnotation(annotation);
	        
	        // create subplot 2...
	        XYDataset data2 = combindedDataset2();
	        XYItemRenderer renderer2 = new StandardXYItemRenderer();
	        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
	        rangeAxis2.setAutoRangeIncludesZero(false);
	        XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
	        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

	        // parent plot...
	        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
	        plot.setGap(10.0);
	        
	        // add the subplots...
	        plot.add(subplot1, 1);
	        plot.add(subplot2, 1);
	        plot.setOrientation(PlotOrientation.VERTICAL);
	        
	        JFreeChart chart =  new JFreeChart("CombinedDomainXYPlot Demo", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
	@RequestMapping
	public void combindedChart2( HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try{
		
			//create a chart
			// create subplot 1...
	        XYDataset data1 = combindedDataset3();
	        XYItemRenderer renderer1 = new StandardXYItemRenderer();
	        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
	        XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
	        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

	        // add secondary axis
	        subplot1.setDataset(1, combindedDataset4());
	        NumberAxis axis2 = new NumberAxis("Range Axis 2");
	        axis2.setAutoRangeIncludesZero(false);
	        subplot1.setRangeAxis(1, axis2);
	        subplot1.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
	        subplot1.setRenderer(1, new StandardXYItemRenderer());       
	        subplot1.mapDatasetToRangeAxis(1, 1);

	        XYTextAnnotation annotation = new XYTextAnnotation("Hello!", 50.0, 10000.0);
	        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
	        annotation.setRotationAngle(Math.PI / 4.0);
	        subplot1.addAnnotation(annotation);
	        
	        // create subplot 2...
	        XYDataset data2 = combindedDataset4();
	        XYItemRenderer renderer2 = new StandardXYItemRenderer();
	        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
	        rangeAxis2.setAutoRangeIncludesZero(false);
	        XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
	        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

	        // parent plot...
	        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
	        plot.setGap(10.0);
	        
	        // add the subplots...
	        plot.add(subplot1, 1);
	        plot.add(subplot2, 1);
	        plot.setOrientation(PlotOrientation.VERTICAL);

	        
	        JFreeChart chart =  new JFreeChart("CombinedDomainXYPlot Demo", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,chart,400,300);
		
		}catch(Exception e){
			System.err.println(e.toString());
		}finally{
			out.close();
		}
	}
	
}
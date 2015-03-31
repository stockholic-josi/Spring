package com.taxholic.core.util;

import java.util.*;

public class DateUtil {
	
	public static Calendar cal = Calendar.getInstance();
	
	/***
	 * 현재 년도 <p>
	 * @return int
	 */
	public static int getYear()	 {
		return cal.get(Calendar.YEAR);
	 }

	/***
	 * 현재 월 <p>
	 * @return int
	 */
	public static int getMonth() {
		return cal.get(Calendar.MONTH)+1;
	}

	/***
	 * 현재 일 <p>
	 * @return int
	 */
	public static int getDay() {
		return cal.get(Calendar.DATE);
	}
	
	/***
	 * 현재 요일 <p>
	 * @return String
	 */
	public static String getWeek() {
		String [] week = {"","일","월","화","수","목","금","토"};
		return week[cal.get(Calendar.DAY_OF_WEEK)]; 
	}	
	
	/***
	 * 날짜 가져오기 ex) 오늘부 터 7일후 getDate(0,0,7) <p>
	 * @param Y <br>
	 * @param M <br>
	 * @param D
	 * @return int
	 */
	public static int[] getDate(int Y, int M, int D) {
		
		int [] arrDate = new int[3];	
		
		if(Y != 0) cal.add(Calendar.YEAR,Y); 
		if(M != 0) cal.add(Calendar.MONTH,M); 
		if(D != 0) cal.add(Calendar.DATE,D); 
		
		arrDate[0] = cal.get(Calendar.YEAR);
		arrDate[1] = cal.get(Calendar.MONTH)+1;
		arrDate[2] = cal.get(Calendar.DATE);
		
		return arrDate;
	}
	
	/***
	 * 현재달 주 (1주, 2주 ...)
	 * @return int
	 */
	public static int getCurrentWeek(){
		int result = cal.get(Calendar.WEEK_OF_MONTH) ;
		return result;
	} 
	
	/***
	 * 해당년월의 마지막 날짜
	 * @param nYear : 년도
	 * @param nMonth : 월
	 * @return int
	 */
	public static int getLastDay(int nYear, int nMonth){
		GregorianCalendar cld = new GregorianCalendar (nYear, nMonth - 1, 1);
		int result = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
		return result;
	}
	
	/***
	 * 해당년월의 첫번째 날짜의 요일(1:SUNDAY, 2:MONDAY...)
	 * @param nYear
	 * @param nMonth
	 * @return
	 */
	public static int getFirstWeekday(int nYear, int nMonth){
		GregorianCalendar cld = new GregorianCalendar (nYear, nMonth - 1, 1);
		int result = cld.get(Calendar.DAY_OF_WEEK);
		return result;
	}
	
	/***
	 * 해당년월의 주의 개수
	 * @param nFristWeekday : 그 달의 첫째날의 요일
	 * @param nToDay : 그 달의 날짜 수
	 * @return
	 */
	public static int getWeekCount(int nFristWeekday, int nToDay){
		int nCountDay = nFristWeekday + nToDay - 1;
		int result = (nCountDay / 7);
		if ((nCountDay % 7) > 0) {
			result++;
		}
		return result;
	}

	
	/**
	 * 셀렉트 년도 날짜 생성 ex) getYear(2007)
	 * @param sY : 년도
	 * @return 
	 */
	public static String getSelYear(int sY){

		StringBuffer dataList = new StringBuffer();
		
		for(int i = sY - 4; i <= sY + 4; i++){
			if(i == sY){
				dataList.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				dataList.append("<option value='" + i + "'>" + i + "</option>");
			}
		}

		return dataList.toString();
	}

	/**
	 *  셀렉트 월  날짜 생성 ex) getMonth(3);
	 * @param m : 현재 월
	 * @return
	 */
	public static String getSelMonth(int sM){

		StringBuffer dataList = new StringBuffer();
	
		for(int i = 1; i < 13; i++){
	
			if(i == sM){
				dataList.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				dataList.append("<option value='" + i + "'>" + i + "</option>");
			}
		}

		return dataList.toString();
	}

	/***
	 * 셀렉트 일 날짜 생성 ex(getDay(25))
	 * @param d : 현재 일
	 * @return
	 */
	public static String getSelDay(int sY, int sM, int sD){
		StringBuffer dataList = new StringBuffer();

		for(int i = 1; i <= DateUtil.getLastDay(sY,sM); i++){

			if(i == sD){
				dataList.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				dataList.append("<option value='" + i + "'>" + i + "</option>");
			}
		}

		return dataList.toString();
	}

}

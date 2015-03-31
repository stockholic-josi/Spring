package com.taxholic.core.util;

import java.math.BigInteger;
import java.text.NumberFormat;

public class MonUtil {
	
	/***
	 * 통화 콤마 표시 <p>
	 * @param price : 금액
	 * @return
	 */
	 public static String getPrice(long LngPrice){ 
		NumberFormat numFormat = NumberFormat.getIntegerInstance(); 
		
		if(LngPrice >= 100) numFormat.setMinimumIntegerDigits(3) ; 
		
		return numFormat.format(LngPrice); 
	} 
	 
	 /***
	  * 통화 한글로 표시
	  * @param sNumValue : 금액
	  * @return
	  */
	 public static String convertAmount(String sPrice)	{
		String sNumResult = "";
		String sTmpStr;
		int nTmpNum1;				// 몫
		BigInteger nTmpNum2;				// 나머지
		BigInteger nTmpNum3 = new BigInteger(sPrice);	// 버퍼
		BigInteger nTmpJisu = new BigInteger("1");
		int nTmpFlag = 0;
		int nTmpFlag2 = 0;			// 단위 플래그 (만,억,조)
		int nNumLen = sPrice.length() - 1;
		
		for (int i=nNumLen ; i > 0; i--)	{
			for (int j=0; j < i; j++)
				nTmpJisu = nTmpJisu.multiply(new BigInteger("10"));

			nTmpNum1 = nTmpNum3.divideAndRemainder(nTmpJisu)[0].intValue();	// 몫
			nTmpNum2 = nTmpNum3.divideAndRemainder(nTmpJisu)[1];	// 나머지

			// 몫 기록
			sTmpStr = SingleNum(nTmpNum1);
			if (sTmpStr == "-1")
				return "숫자아님1" + "|" + i + "|" + sPrice + "|" + nTmpNum1 + "|" + nTmpNum2;
			else if (sTmpStr != "0")
			{
				sNumResult = sNumResult + sTmpStr;
				nTmpFlag = 1;
				nTmpFlag2 = nTmpFlag2 + 1;
			}

			// 자리 기록
			sTmpStr = SingleGisu(i+1);
			if (sTmpStr == "-1")
				return "숫자아님2" + "|" + i + "|" + sPrice + "|" + nTmpNum1 + "|" + nTmpNum2;
			else if (nTmpFlag > 0 || (nTmpFlag2 > 0 && i%4 == 0))
			{
				sNumResult = sNumResult + sTmpStr;
			}

			// 단위마다 플래그 초기화
			nTmpFlag = 0;
			if ( i%4 == 0 )
				nTmpFlag2 = 0;

			nTmpNum3 = nTmpNum2;		// Next Step
			nTmpJisu = new BigInteger("1");
		}

		sTmpStr = SingleNum(nTmpNum3.intValue());
		if (sTmpStr == "-1")
			return "숫자아님";
		else if (sTmpStr != "0")
			sNumResult = sNumResult + sTmpStr;

		if (sNumResult == "")
			sNumResult = "영";

		return sNumResult;
	}
	
	public static  String SingleGisu(int num_gisu)	{
		String sNumResult;

		if (num_gisu == 1)
			sNumResult = "원";
		else if (num_gisu == 2)
			sNumResult = "십";
		else if (num_gisu == 3)
			sNumResult = "백";
		else if (num_gisu == 4)
			sNumResult = "천";
		else if (num_gisu == 5)
			sNumResult = "만";
		else if (num_gisu == 6)
			sNumResult = "십";
		else if (num_gisu == 7)
			sNumResult = "백";
		else if (num_gisu == 8)
			sNumResult = "천";
		else if (num_gisu == 9)
			sNumResult = "억";
		else if (num_gisu == 10)
			sNumResult = "십";
		else if (num_gisu == 11)
			sNumResult = "백";
		else if (num_gisu == 12)
			sNumResult = "천";
		else if (num_gisu == 13)
			sNumResult = "조";
		else if (num_gisu == 14)
			sNumResult = "십";
		else if (num_gisu == 15)
			sNumResult = "백";
		else if (num_gisu == 16)
			sNumResult = "천";
        else
			sNumResult = "-1";

		return sNumResult;
	}

	public static 	String SingleNum(int sPrice)	{
		String sNumResult;
		if (sPrice == 1)
			sNumResult = "일";
		else if(sPrice == 2)
            sNumResult = "이";
		else if(sPrice == 3)
			sNumResult = "삼";
		else if(sPrice == 4)
			sNumResult = "사";
		else if(sPrice == 5)
			sNumResult = "오";
		else if(sPrice == 6)
			sNumResult = "육";
		else if(sPrice == 7)
			sNumResult = "칠";
		else if(sPrice == 8)
			sNumResult = "팔";
		else if(sPrice == 9)
			sNumResult = "구";
		else if(sPrice == 0)
			sNumResult = "0";
		else
			sNumResult = "-1";
		return sNumResult;
	}	
}

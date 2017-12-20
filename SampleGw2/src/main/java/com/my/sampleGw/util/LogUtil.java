package com.my.sampleGw.util;

public class LogUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(LogUtil.formattedResultLog("strPName", "strMid", "strResultCode", "strResultMsg"));
		System.out.println(LogUtil.formattedResultLog("strPName", "strMid", "0000", "strResultMsg"));

	}
	
	public static String formattedResultLog(String strPName, String strMid, String strResultCode, String strResultMsg){
		
		String strLogRT = "";
		
		if("0000".equals(strResultCode)) {
			/*success */
			strLogRT = "PGWASRT:[" +  strPName + "][" +  strMid +"]["  + strResultCode + "][" + strResultMsg + "]";
		} else { 
			/*fail */				
			//strLogRT = "[1;41m" + "PGWASRT:[" + strPName + "][" +  strMid +"]["  + strResultCode + "][" + strResultMsg + "]" + " [0m";
			strLogRT = "[1;41mPGWASRT:[" 
					+ strPName + "][" 
					+ strMid +"]["  
					+ strResultCode + "][" 
					+ strResultMsg + "] [0m";
		}
		
		return strLogRT;
	}

}

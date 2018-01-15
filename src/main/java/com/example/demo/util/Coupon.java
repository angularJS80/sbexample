package com.example.demo.util;

import java.util.Random;

/*
 useCase
 	public Map<String, Object> getRefreshBarcode(Map<String, Object> reqBodyMap) {
				
		
		boolean inwhile = true;
		int currentIndex = 1;
		while (inwhile) {
			String[] coponArryStr = coupon.couponnum();
			reqBodyMap.put("mobile_barcd", coponArryStr[0]);
			currentIndex = centerDao.selectCenterBarcodeCnt(reqBodyMap);
			if(currentIndex==0){
				inwhile = false;
				centerDao.updateRefreshBarcode(reqBodyMap);
			}
		}		
		
		return reqBodyMap;
	}
*/

public class Coupon {
	public static String[] couponnum(){
		 int couponSize = 1;
		 final char[] possibleCharacters =
		   {'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F',
		    'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V',
		    'W','X','Y','Z'};
		 
		 final int possibleCharacterCount = possibleCharacters.length;
		 String[] arr = new String[couponSize];
		 Random rnd = new Random();
		 int currentIndex = 0;
		 int i = 0;
		 while (currentIndex < couponSize) {
		  StringBuffer buf = new StringBuffer(16);
		  //i는 8자리의 랜덤값을 의미
		  for (i= 16; i > 0; i--) {
		   buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		  }
		  String couponnum = buf.toString();
		  System.out.println("couponnum==>"+couponnum);   
		  arr[currentIndex] = couponnum;
		  currentIndex++;
		 }
		 return arr;
		}	
}

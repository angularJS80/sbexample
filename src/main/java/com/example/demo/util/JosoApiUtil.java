package com.example.demo.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.XML;




public class JosoApiUtil{
	public String getJusoCombo( Map<String, Object> reqBodyMap)throws IOException {       	
        String apiUrl = "http://openapi.epost.go.kr/postal/retrieveLotNumberAdressAreaCdService/retrieveLotNumberAdressAreaCdService/";
        String brtcCd = (String) reqBodyMap.get("brtcCd");
        String signguCd = (String) reqBodyMap.get("signguCd");
//        brtcCd = "서울";
//        signguCd = "종로";
        String serviceKey = "GE5uac178uN0nihkVWiWkPLQDlbAdFDUekAzbkzbkbL9nHYjHj7X%2B0RskFNpG0bScFbjtYO%2FrRPR1TPnh6ub3w%3D%3D";
        String appendUrl =  "getBorodCityList";
        String appendParam =  "";
       
        if(!brtcCd.equals("")) {
        	appendUrl = "getSiGunGuList";
        	appendParam = "&brtcCd="+URLEncoder.encode(brtcCd, "UTF-8");
        }        
        
        if(!brtcCd.equals("") && !signguCd.equals("")) {
        	appendUrl = "getEupMyunDongList";
        	appendParam = "&brtcCd="+URLEncoder.encode(brtcCd, "UTF-8")+"&signguCd="+URLEncoder.encode(signguCd, "UTF-8");
        }
        
        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/        
        urlBuilder.append(appendUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey); /*Service Key*/        
        urlBuilder.append(appendParam); 
        
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        
        org.json.JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(sb.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlJSONObj.toString();

    }
}
package com.example.demo.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.security.jwt.JwtAuthResponse;
public class LoadTest {
	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(101);
		RestTemplate rt = new RestTemplate();
		String getUrl = "http://52.193.154.247:28080/sbexample/api/authenticateAsyncGet?username=testuser&password=testuser&rememberMe=false";
		String postUserRegUrl = "http://52.193.154.247:28080/sbexample/api/registerAsync";
		String postloginUrl = "http://52.193.154.247:28080/sbexample/api/authenticateAsync";
		
		
		RestTemplate restTemplate = new RestTemplate();

		
		String testuser = "";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		
		
		
		int loadCnt = 101;
		CyclicBarrier barrier = new CyclicBarrier(loadCnt);
		
		for(int i=0;i<loadCnt;i++) {
				//rt.getForEntity(getUrl, ResponseEntity.class);
			
			String regRequestJson = createRegUserJson(i);
			HttpEntity<String> regEntity = new HttpEntity<String>(regRequestJson,headers);
			ResponseEntity<String> reganswer = restTemplate.postForEntity(postUserRegUrl, regEntity, String.class);
			System.out.println("reganswer: "+reganswer);
			
			/*
			String logRequestJson = createLoginUserJson(i);
			HttpEntity<String> logEntity = new HttpEntity<String>(logRequestJson,headers);
			ResponseEntity<String> loganswer = restTemplate.postForEntity(postloginUrl, logEntity, String.class);
			System.out.println(loganswer);
			*/
				
				
		}
		
	}

	private static String createRegUserJson(int i) {
		String regUserJson = "{\n" + 
				"  \"activated\": true,\n" + 
				"  \"authorities\": [\n" + 
				"    {\n" + 
				"      \"name\": \"user\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"createdBy\": \"testuser"+i+"\",\n" +
				"  \"createdDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"email\": \"testuser"+i+"@test.com\",\n" + 
				"  \"id\": 0,\n" + 
				"  \"lastModifiedBy\": \"testuser\",\n" + 
				"  \"lastModifiedDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"login\": \"testuser"+i+"\",\n" + 
				"  \"name\": \"testuser"+i+"\",\n" + 
				"  \"password\": \"testuser\"\n" + 
				"}";
		return regUserJson;
	}
	
	private static String createLoginUserJson(int i) {
		String logingUserJson = "{\"username\":\"testuser"+i+"\",\"password\":\"testuser\"}";
		return logingUserJson;
	}
	
		
}

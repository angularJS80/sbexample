package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.dto.LoadTestUser;

public class LoadTestUtil {	

	public static List<ResponseEntity> run(LoadTestUser loadTestUser) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		List<HttpEntity<String>> entitys = new ArrayList<>();
		int startNum = loadTestUser.getStartNum();
		int loadCnt = loadTestUser.getLoadCnt();
		for (int i=startNum-1; i<=loadCnt+startNum; i++){
			String requestJson = "";
					
			if(loadTestUser.getActionFlag().equals("reg")) {
				loadTestUser.setPostUrl(loadTestUser.getRootUrl()+LoadTestConst.postRegUrl);
				requestJson = createRegUserJson(i,loadTestUser.getStartNum());		
			}
			
			if(loadTestUser.getActionFlag().equals("auth")) {
				loadTestUser.setPostUrl(loadTestUser.getRootUrl()+LoadTestConst.postloginUrl);
				requestJson = createLoginUserJson(i,loadTestUser.getStartNum());	
			}
			
			HttpEntity<String> regEntity = new HttpEntity<String>(requestJson,headers);
			entitys.add(regEntity);
		}
		return restRequest(loadTestUser ,entitys);
	}

	public static List<ResponseEntity> restRequest(LoadTestUser loadTestUser,List<HttpEntity<String>> entitys){
		
		RestTemplate restTemplate = new RestTemplate();
		String postUrl = loadTestUser.getPostUrl();
		int startNum = loadTestUser.getStartNum();
		AtomicInteger counter = new AtomicInteger(startNum-1);
		int loadCnt = loadTestUser.getLoadCnt();
		List<ResponseEntity> rtnList = new ArrayList();
		
		ExecutorService es = Executors.newFixedThreadPool(loadCnt);
		CyclicBarrier barrier = new CyclicBarrier(loadCnt+1);	
		StopWatch main = new StopWatch();
		main.start();
	
		for (int i=0; i<loadCnt; i++){
			Future<ResponseEntity> asyncResponse = es.submit(()->{
				int idx = counter.addAndGet(1);
				System.out.println("idx : "+idx);
				barrier.await();							
				ResponseEntity<String> reganswer = restTemplate.postForEntity(postUrl, entitys.get(idx), String.class);
				System.out.println("reganswer" + reganswer);
				rtnList.add(reganswer);
				return reganswer;
			});
		}
		
		try {
			barrier.await();
			es.shutdown();
			es.awaitTermination(loadCnt, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.stop();
		return rtnList;

	}
	private static String createRegUserJson(int i,int startNum) {
		int userIndex = startNum+i-1;
		String regUserJson = "{\n" + 
				"  \"activated\": true,\n" + 
				"  \"authorities\": [\n" + 
				"    {\n" + 
				"      \"name\": \"user\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"createdBy\": \"testuser"+userIndex+"\",\n" +
				
				"  \"email\": \"testuser"+userIndex+"@test.com\",\n" + 
				"  \"id\": 0,\n" + 
				"  \"lastModifiedBy\": \"testuser\",\n" + 
				
				"  \"login\": \"testuser"+userIndex+"\",\n" + 
				"  \"name\": \"testuser"+userIndex+"\",\n" + 
				"  \"password\": \"testuser\"\n" + 
				"}";
		return regUserJson;
	}
	
	private static String createLoginUserJson(int i,int startNum) {
		int userIndex = startNum+i-1;
		String logingUserJson = "{\"username\":\"testuser"+userIndex+"\",\"password\":\"testuser\"}";
		return logingUserJson;
	}

}

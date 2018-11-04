package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.dto.LoadTestUser;

public class LoadTestUtil {	

	public static void run(LoadTestUser loadTestUser,String actionFlag) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		List<HttpEntity<String>> entitys = new ArrayList<>();
		int startNum = loadTestUser.getStartNum();
		int loadCnt = loadTestUser.getLoadCnt();
		for (int i=startNum; i<loadCnt+startNum; i++){
			String requestJson = "";
					
			if(actionFlag.equals("reg")) {
				loadTestUser.setPostUrl(LoadTestUser.postRegUrl);
				requestJson = createRegUserJson(i,loadTestUser.getStartNum());		
			}
			
			if(actionFlag.equals("auth")) {
				loadTestUser.setPostUrl(LoadTestUser.postRegUrl);
				requestJson = createLoginUserJson(i,loadTestUser.getStartNum());	
			}
			
			HttpEntity<String> regEntity = new HttpEntity<String>(requestJson,headers);
			entitys.add(regEntity);
		}
		restRequest(loadTestUser ,entitys);
	}

	public static void restRequest(LoadTestUser loadTestUser,List<HttpEntity<String>> entitys){
		AtomicInteger counter = new AtomicInteger(0);
		RestTemplate restTemplate = new RestTemplate();
		String postUrl = loadTestUser.getPostUrl();
		int startNum = loadTestUser.getStartNum();
		int loadCnt = loadTestUser.getLoadCnt();
		
		
		ExecutorService es = Executors.newFixedThreadPool(loadCnt);
		CyclicBarrier barrier = new CyclicBarrier(loadCnt+1);	
		StopWatch main = new StopWatch();
		main.start();
	
		for (int i=startNum; i<loadCnt+startNum; i++){
			es.submit(()->{
				int idx = counter.addAndGet(1);
				barrier.await();			
				StopWatch sw = new StopWatch();
				sw.start();
				
				ResponseEntity<String> reganswer = restTemplate.postForEntity(postUrl, entitys.get(idx), String.class);
				System.out.println("reganswer: "+reganswer);				
				sw.stop();
				return null;
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
	

	}
	private static String createRegUserJson(int i,int startNum) {
		int userIndex = startNum+i;
		String regUserJson = "{\n" + 
				"  \"activated\": true,\n" + 
				"  \"authorities\": [\n" + 
				"    {\n" + 
				"      \"name\": \"user\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"createdBy\": \"testuser"+userIndex+"\",\n" +
				"  \"createdDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"email\": \"testuser"+startNum+i+"@test.com\",\n" + 
				"  \"id\": 0,\n" + 
				"  \"lastModifiedBy\": \"testuser\",\n" + 
				"  \"lastModifiedDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"login\": \"testuser"+userIndex+"\",\n" + 
				"  \"name\": \"testuser"+userIndex+"\",\n" + 
				"  \"password\": \"testuser\"\n" + 
				"}";
		return regUserJson;
	}
	
	private static String createLoginUserJson(int i,int startNum) {
		int userIndex = startNum+i;
		String logingUserJson = "{\"username\":\"testuser"+userIndex+"\",\"password\":\"testuser\"}";
		return logingUserJson;
	}

}

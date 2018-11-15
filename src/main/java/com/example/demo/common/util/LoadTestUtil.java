package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.dto.LoadTestUser;
import com.example.demo.security.jwt.JwtAuthRequest;
import com.example.demo.security.model.UserDto;
import com.google.gson.Gson;
public class LoadTestUtil {	
	static Gson gson = new Gson();
	public static List<HttpEntity<String>> createRequests(LoadTestUser loadTestUser) {
		
		
		
		int startNum = loadTestUser.getStartNum();
		int loadCnt = loadTestUser.getLoadCnt();
		int startindex = startNum;
		int endindex = loadCnt+startNum-1;
		
		System.out.println("startindex : "+startindex);
		System.out.println("endindex : "+endindex);
		List<HttpEntity<String>> requestEntitys = null;
		if(loadTestUser.getActionFlag().equals("reg")) {
			requestEntitys = createRegUserEntitiys(loadTestUser,startindex,endindex);
			
		}
		
		if(loadTestUser.getActionFlag().equals("auth")) {
			requestEntitys = createLoginUserEntitiys(loadTestUser,startindex,endindex);
			
		}
		
		return requestEntitys;
	}

	private static List<HttpEntity<String>> createLoginUserEntitiys(LoadTestUser loadTestUser, int startindex,
			int endindex) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<HttpEntity<String>> requestEntitys = new ArrayList<>();
		for (int i=startindex; i<=endindex; i++){
			String requestJson = "";
			//loadTestUser.setPostUrl(loadTestUser.getRootUrl()+LoadTestConst.postloginUrl);
			//requestJson = createLoginUserJson(i,loadTestUser.getStartNum());	
			requestJson = createLoginUser(i,loadTestUser.getStartNum());	
			HttpEntity<String> regEntity = new HttpEntity<String>(requestJson,headers);
			requestEntitys.add(regEntity);
		}
		return requestEntitys;
	}

	private static List<HttpEntity<String>> createRegUserEntitiys(LoadTestUser loadTestUser,int startindex, int endindex) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<HttpEntity<String>> requestEntitys = new ArrayList<>();
		for (int i=startindex; i<=endindex; i++){
			String requestJson = "";
			//loadTestUser.setPostUrl(loadTestUser.getRootUrl()+LoadTestConst.postRegUrl);
			//requestJson = createRegUserJson(i,loadTestUser.getStartNum());		
			requestJson = createRegUser(i,loadTestUser.getStartNum());		
			HttpEntity<String> regEntity = new HttpEntity<String>(requestJson,headers);
			requestEntitys.add(regEntity);
		}
		return requestEntitys;
	}

	public static List<String> restRequest(LoadTestUser loadTestUser,List<HttpEntity<String>> requestEntitys,String postUrl){
		
		RestTemplate restTemplate = new RestTemplate();
		
		
		
		int startNum = loadTestUser.getStartNum();
		AtomicInteger counter = new AtomicInteger(0);
		int loadCnt = loadTestUser.getLoadCnt();
		List<String> rtnList = new ArrayList();
		
		ExecutorService es = Executors.newFixedThreadPool(loadCnt);
		CyclicBarrier barrier = new CyclicBarrier(loadCnt+1);	
		StopWatch main = new StopWatch();
		main.start();
	
		for (int i=0; i<loadCnt; i++){
			Future<ResponseEntity> asyncResponse = es.submit(()->{
				int idx = counter.addAndGet(1);
				System.out.println("idx : "+idx);
				barrier.await();							
				ResponseEntity<String> reganswer = restTemplate.postForEntity(postUrl, requestEntitys.get(idx-1), String.class);
				System.out.println("reganswer" + reganswer);

				rtnList.add(reganswer.getBody());
				return reganswer;
			});
		}
		
		try {
			barrier.await();
			es.shutdown();
			es.awaitTermination(loadCnt, TimeUnit.SECONDS);
			//runSchTask(es,loadCnt);
			} catch (Exception e) {
			e.printStackTrace();
		}
		main.stop();
		return rtnList;
	}
	
	
	private static void runSchTask (ExecutorService es, int loadCnt) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());	
		c.add(Calendar.MILLISECOND, 200); // 동시 발송시 1초식 딜레이를 준다 교착상태 방지용
		
		FutureTask<String> task = new FutureTask<String>(
	    new Callable<String>() {
			@Override
			public String call() throws Exception {
				es.shutdown();
				es.awaitTermination(loadCnt, TimeUnit.SECONDS);
				return null;
			}
	    });
				
		ThreadPoolTaskScheduler asyncTaskScheduler = new ThreadPoolTaskScheduler();
		asyncTaskScheduler.initialize();
		asyncTaskScheduler.schedule(task, c.getTime());
	}
	


	private static  String createRegUser(int userIndex,int startNum) {
		
		UserDto.Create userDtoCreate = new UserDto.Create();
		userDtoCreate.setEmail("testuser"+userIndex+"@test.com");
		userDtoCreate.setLogin("testuser"+userIndex);
		userDtoCreate.setName("testuser"+userIndex);
		userDtoCreate.setPassword("testuser"+userIndex);
		return gson.toJson(userDtoCreate);
	}

	
	
	
	private static String createLoginUser(int userIndex,int startNum) {
		JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
		jwtAuthRequest.setPassword("testuser"+userIndex);
		jwtAuthRequest.setUsername("testuser"+userIndex);
		return gson.toJson(jwtAuthRequest); 
	}
	
	/* 제거 예
	private static String createRegUserJson(int userIndex,int startNum) {
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

	private static String createLoginUserJson(int userIndex,int startNum) {
		
		String logingUserJson = "{\"username\":\"testuser"+userIndex+"\",\"password\":\"testuser\"}";
		return logingUserJson;
	}
	*/
	

}

package com.example.demo.util;

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

public class LoadTest2 {
	static AtomicInteger counter = new AtomicInteger(0);
	static String postUserRegUrl = "http://52.193.154.247:28080/sbexample/api/registerAsync";
	//static String postUserRegUrl = "http://localhost:38080/sbexample2/api/registerAsync";
	
	//static String postloginUrl = "http://52.193.154.247:28080/sbexample/api/authenticateAsync";
	static String postloginUrl = "http://localhost:38080/sbexample2/api/authenticateAsync";
	static int startNum = 0;
	static int loadCnt = 100;
	static RestTemplate restTemplate = new RestTemplate();

	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
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
		
		String regRequestJson = createRegUserJson(idx);
		HttpEntity<String> regEntity = new HttpEntity<String>(regRequestJson,headers);
		ResponseEntity<String> reganswer = restTemplate.postForEntity(postUserRegUrl, regEntity, String.class);
		System.out.println("reganswer: "+reganswer);
		/*
		String logRequestJson = createLoginUserJson(idx);
		HttpEntity<String> logEntity = new HttpEntity<String>(logRequestJson,headers);
		ResponseEntity<String> loganswer = restTemplate.postForEntity(postloginUrl, logEntity, String.class);
		System.out.println(loganswer);
		*/
		sw.stop();
		
	
		return null;
	});
	}

	barrier.await();
	es.shutdown();
	es.awaitTermination(loadCnt, TimeUnit.SECONDS);

	main.stop();
	

	}
	private static String createRegUserJson(int i) {
		String regUserJson = "{\n" + 
				"  \"activated\": true,\n" + 
				"  \"authorities\": [\n" + 
				"    {\n" + 
				"      \"name\": \"user\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"createdBy\": \"testuser"+startNum+i+"\",\n" +
				"  \"createdDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"email\": \"testuser"+startNum+i+"@test.com\",\n" + 
				"  \"id\": 0,\n" + 
				"  \"lastModifiedBy\": \"testuser\",\n" + 
				"  \"lastModifiedDate\": \"2018-11-03T03:33:12.998Z\",\n" + 
				"  \"login\": \"testuser"+startNum+i+"\",\n" + 
				"  \"name\": \"testuser"+startNum+i+"\",\n" + 
				"  \"password\": \"testuser\"\n" + 
				"}";
		return regUserJson;
	}
	
	private static String createLoginUserJson(int i) {
		String logingUserJson = "{\"username\":\"testuser"+startNum+i+"\",\"password\":\"testuser\"}";
		return logingUserJson;
	}
}


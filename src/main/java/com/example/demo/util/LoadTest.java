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
public class LoadTest {
	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(101);
		RestTemplate rt = new RestTemplate();
		String getUrl = "http://localhost:38080/sbexample2/api/authenticateAsyncGet?username=testuser&password=testuser&rememberMe=false";
		String postUrl = "http://localhost:38080/sbexample2/api/authenticateAsync";
		
		
		RestTemplate restTemplate = new RestTemplate();

		String requestJson = "{\"username\":\"testuser\",\"password\":\"testuser\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		
		
		
		
		
		int loadCnt = 500;
		CyclicBarrier barrier = new CyclicBarrier(loadCnt);
		
		for(int i=0;i<loadCnt;i++) {
			es.submit(()->{
				
				Thread.sleep(100);
				headers.setContentType(MediaType.APPLICATION_JSON);
				//rt.getForEntity(getUrl, ResponseEntity.class);
				ResponseEntity<ResponseEntity> answer = restTemplate.postForEntity(postUrl, entity, ResponseEntity.class);
				System.out.println(answer);
				
				return null;
				}
			);
		}
		es.shutdown();
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
		
}

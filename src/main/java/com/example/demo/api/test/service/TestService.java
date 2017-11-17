package com.example.demo.api.test.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.TestMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class TestService {
	
	private static final Logger logger = LoggerFactory.getLogger(TestService.class);
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	
	@Autowired
	private  TestMapper testMapper;
	
	public  Map<String, Object> regUser(Map<String, Object> map) {
			
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("succuess", "true");
		
		try {
			testMapper.insertUser(map);	
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("succuess", "false");
		}
		
		//return map;
		
		return	rtnMap;
	}
	
	public  Map<String, Object> loginUser(Map<String, Object> map) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<Map<String, Object>> rtnList = null;
		rtnMap.put("succuess", "true");
		
		try {
			rtnList = testMapper.selectUserByLogin(map);	
			
			if(rtnList.size()==0) {
				rtnMap.put("succuess", "false");
			}else {
				rtnMap.put("USER_ID", rtnList.get(0).get("USER_ID"));
				rtnMap.put("USER_PASSWORD", rtnList.get(0).get("USER_PASSWORD"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("succuess", "false");
		}
		
		//return map;
		
		return	rtnMap;
	}
	
	
	
	
	
	public  Map<String, Object> getUserList(Map<String, Object> map) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("succuess", "true");
		List<Map<String, Object>> rtnList = null;
		try {
			rtnList = testMapper.selectUser(map);	
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succuess", "false");
		}
		rtnMap.put("rtnList", rtnList);
		//return map;
		
		return	rtnMap;
	}
	
	
	
	

	@Scheduled(fixedRateString = "10000")
	public void scjTest()  {
		System.out.println("Test");
	}
	
	
}

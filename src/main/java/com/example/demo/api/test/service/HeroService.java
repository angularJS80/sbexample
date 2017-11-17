package com.example.demo.api.test.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.HeroMapper;
import com.example.demo.vo.Heros;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class HeroService {
	
	private static final Logger logger = LoggerFactory.getLogger(HeroService.class);
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	
	@Autowired
	private  HeroMapper heroMapper;
	
	public  Map<String, Object> regHero(Map<String, Object> map) {
			
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("succuess", "true");
		
		try {
			heroMapper.insertHero(map);	
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("succuess", "false");
		}
		
		//return map;
		
		return	rtnMap;
	}
	
	
	
	
	public  Map<String, Object> getHeroList(Map<String, Object> map) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("succuess", "true");
		List<Heros> rtnList = null;
		try {
			rtnList = heroMapper.selectHeros(map);	
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succuess", "false");
		}
		rtnMap.put("rtnList", rtnList);
		//return map;
		
		return	rtnMap;
	}
	

	
	
}

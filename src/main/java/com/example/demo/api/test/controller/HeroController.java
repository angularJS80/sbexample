package com.example.demo.api.test.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.test.service.HeroService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/hero")
public class HeroController {
	private static final Logger logger = LoggerFactory.getLogger(HeroController.class);
    @Autowired 
    private HeroService heroService; 

	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	

	 /*안드로이드 테스트 회원가입*/
   @RequestMapping(value="/reg", method=RequestMethod.POST)
   public  String regUserJson(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {    	
   	return gson.toJson(heroService.regHero(paramMap));
	}
    
    /*안드로이드 테스트 회원가입*/
    @RequestMapping(value="/getList", method=RequestMethod.POST)
    public  String getUser( @RequestBody Map<String, Object> paramMap, HttpServletRequest request) {    	    	
    	logger.debug(paramMap.toString());
    	return gson.toJson(heroService.getHeroList(paramMap));
	}
    
}

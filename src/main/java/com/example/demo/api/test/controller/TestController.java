package com.example.demo.api.test.controller;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.test.service.TestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired 
    private TestService testService; 

	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	
    /*안드로이드 테스트 회원가입*/
    @RequestMapping(value="/regUser", method=RequestMethod.POST)
    public  String regUser( HttpServletRequest request) {    	
    	Map parameterMap = new HashMap();
    	Enumeration enums = request.getParameterNames();
    	while(enums.hasMoreElements()){
	    	String paramName = (String)enums.nextElement();
	    	String[] parameters = request.getParameterValues(paramName);
	
	    	// Parameter가 배열일 경우
	    	if(parameters.length > 1){
	    		parameterMap.put(paramName, parameters);
	    	// Parameter가 배열이 아닌 경우
	    	}else{
	    		parameterMap.put(paramName, parameters[0]);
	    	}
    	}
    	logger.debug(parameterMap.toString());
    	return gson.toJson(testService.regUser(parameterMap));
	}
    
    /*안드로이드 테스트 회원가입*/
    @RequestMapping(value="/getUserList", method=RequestMethod.GET)
    public  String getUser( HttpServletRequest request) {    	
    	Map parameterMap = new HashMap();
    	Enumeration enums = request.getParameterNames();
    	while(enums.hasMoreElements()){
	    	String paramName = (String)enums.nextElement();
	    	String[] parameters = request.getParameterValues(paramName);
	
	    	// Parameter가 배열일 경우
	    	if(parameters.length > 1){
	    		parameterMap.put(paramName, parameters);
	    	// Parameter가 배열이 아닌 경우
	    	}else{
	    		parameterMap.put(paramName, parameters[0]);
	    	}
    	}
    	logger.debug(parameterMap.toString());
    	return gson.toJson(testService.getUserList(parameterMap));
	}
    
}

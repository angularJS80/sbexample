
package com.example.demo.common.controller;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.test.service.HeroService;
import com.example.demo.common.dto.LoadTestUser;
import com.example.demo.common.service.CommonService;
import com.example.demo.security.model.User;
import com.example.demo.security.model.UserDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiParam;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

@RestController
@RequestMapping("/api")
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired 
    private CommonService commonService; 

	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	
    /*서버 로드테스트*/
	@PostMapping(path = "/loadTestingUserReg",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    
    public ResponseEntity registerAccountAsync(@Valid @RequestBody LoadTestUser loadTestUser) {
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        commonService.requestLoadTest(loadTestUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    		
    }
    
}

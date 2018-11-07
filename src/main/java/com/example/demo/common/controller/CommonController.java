
package com.example.demo.common.controller;
import static com.example.demo.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.test.service.HeroService;
import com.example.demo.common.dto.LoadTestUser;
import com.example.demo.common.service.CommonService;
import com.example.demo.security.jwt.JwtAuthRequest;
import com.example.demo.security.jwt.JwtAuthResponse;
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

    @RequestMapping(value = "/loadTesting", method = RequestMethod.POST)
    public ResponseEntity<?>  registerAccountAsync(@Valid @RequestBody LoadTestUser loadTestUser) {
        
    	
    	List<String> rtnList = commonService.requestLoadTest(loadTestUser);
    	
    	
    	//ArrayList rtnList = new ArrayList();
    	
        try {
            return ResponseEntity.ok(rtnList);
        } catch (AuthenticationException ae) {
            logger.debug("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }    		
    }    
}

package com.example.demo.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.security.jwt.JwtAuthRequest;
import com.example.demo.security.jwt.JwtAuthResponse;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.service.AuthService;

import javax.servlet.http.HttpServletResponse;

import static com.example.demo.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

import java.util.Collections;
import java.util.concurrent.Future;

/**
 * @modify Yongbeom Cho
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest request
            , @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe
            , HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = "Bearer " + jwtUtil.createToken(authentication, rememberMe);
            response.addHeader(AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok(new JwtAuthResponse(jwt));
        } catch (AuthenticationException ae) {
            logger.debug("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/authenticateAsync", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateAsync(@RequestBody JwtAuthRequest request
            , @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe
            , HttpServletResponse response) throws AuthenticationException {
    			
    		Future<String> futureJwt = authService.authenticate(request.getUsername(),request.getPassword(),rememberMe);
    		String jwtToken = "";
	    		
    	    try {
	    	    	while (true) {
		        if (futureJwt.isDone()) {
		        		jwtToken= futureJwt.get();
		        		 response.addHeader(AUTHORIZATION_HEADER, jwtToken);
		        		 System.out.println("jwtToken:"+jwtToken);
		                 return ResponseEntity.ok(new JwtAuthResponse(jwtToken));
		        }
		        
		        Thread.sleep(1000);
	    		}
           
        } catch (Exception ae) {
        		ae.printStackTrace();
            logger.debug("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/authenticatAsyncGet", method = RequestMethod.GET)
    public ResponseEntity<?> authenticatAsyncGet(@RequestParam  String username,@RequestParam String password
            , @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe
            , HttpServletResponse response) throws AuthenticationException {
    			
    		Future<String> futureJwt = authService.authenticate(username,password,rememberMe);
    		String jwtToken = "";
	    		
    	    try {
	    	    	while (true) {
		        if (futureJwt.isDone()) {
		        		jwtToken= futureJwt.get();
		        		 response.addHeader(AUTHORIZATION_HEADER, jwtToken);
		                 return ResponseEntity.ok(new JwtAuthResponse(jwtToken));
		        }
		        System.out.println("Continue doing something else. ");
		        Thread.sleep(1000);
	    		}
           
        } catch (Exception ae) {
        		ae.printStackTrace();
            logger.debug("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
}
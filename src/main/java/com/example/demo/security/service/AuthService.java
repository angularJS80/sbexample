package com.example.demo.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.security.SecurityUtil;
import com.example.demo.security.comn.ApiException;
import com.example.demo.security.jwt.JwtAuthResponse;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.model.Authority;
import com.example.demo.security.model.User;
import com.example.demo.security.model.UserDto;
import com.example.demo.security.repository.AuthorityRepository;
import com.example.demo.security.repository.UserRepository;

import static com.example.demo.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @modify Yongbeom Cho
 */
@Service
@Transactional
public class AuthService {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Async("threadPoolTaskExecutor")
	public Future<String> authenticate(String username, String password,boolean rememberMe) {
		UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
		 Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwtToken = "Bearer " + jwtUtil.createToken(authentication, rememberMe);
         return new AsyncResult<String>(jwtToken);

		
	}

}
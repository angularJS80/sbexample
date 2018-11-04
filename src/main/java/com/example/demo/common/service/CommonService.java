package com.example.demo.common.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.common.dto.LoadTestUser;
import com.example.demo.common.util.LoadTestUtil;
import com.example.demo.mapper.TestMapper;
import com.example.demo.security.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class CommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	public AsyncResult<String> requestLoadTest(LoadTestUser loadTestUser) {
		LoadTestUtil.run(loadTestUser);
         return new AsyncResult<String>("reuqested");
	}	
}

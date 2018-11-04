package com.example.demo.config;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
	// TODO Auto-generated method stub
	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	taskExecutor.setCorePoolSize(10);
	taskExecutor.setMaxPoolSize(20);
	taskExecutor.setQueueCapacity(50);
	configurer.setTaskExecutor(taskExecutor);
	}
}

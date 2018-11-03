package com.example.demo.config;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    
   @Bean(name = "threadPoolTaskExecutor")
   public Executor threadPoolTaskExecutor() {
	   ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(1);
       executor.setMaxPoolSize(4);
       executor.setQueueCapacity(1000);
       return executor;
   }
}

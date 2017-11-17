package com.example.demo;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

import com.example.demo.config.CORSFilter;

@Configuration
@ComponentScan(basePackages="com.example.demo")
//@EnableEurekaClient
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })

@SpringBootApplication
//@EnableScheduling



public class DemoApplication  extends SpringBootServletInitializer implements WebApplicationInitializer{

	@SuppressWarnings("restriction")
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CORSFilter.class);
		corsFilter.addMappingForUrlPatterns(null, false, "/*");

		System.out.println("#########################################servletContext#############################");		
		System.out.println(servletContext);
		//super.onStartup(servletContext);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

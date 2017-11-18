package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.security.jwt.JwtUtil;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @modify Yongbeom Cho
 * @modify yongbeom.cho 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null,
                "Bearer " + jwtUtil.createAdminToken(), ApiKeyVehicle.HEADER, "Authorization", "," /* scope separator */);
    }
}
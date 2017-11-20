package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.Http401ErrorEntryPoint;
import com.example.demo.security.SimpleCorsFilter;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.security.jwt.JwtUtil;

/**
 * @modify Yongbeom Cho
 * @modify yongbeom.cho
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Http401ErrorEntryPoint http401ErrorEntryPoint() {
        return new Http401ErrorEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new SimpleCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(http401ErrorEntryPoint())
                .and()
                    .csrf()
                    .disable()
                    .headers()
                    .frameOptions()
                    .disable()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/hero/**").authenticated()
                .antMatchers("/api/**").authenticated();
        		
        
        
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/content/**")                
         //     .antMatchers("/h2-console/**");
                .antMatchers("/swagger-ui.html");
    }


}

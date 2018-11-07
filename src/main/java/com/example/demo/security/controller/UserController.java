package com.example.demo.security.controller;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.security.comn.PaginationUtil;
import com.example.demo.security.jwt.JwtAuthRequest;
import com.example.demo.security.jwt.JwtAuthResponse;
import com.example.demo.security.model.User;
import com.example.demo.security.model.UserDto;
import com.example.demo.security.repository.UserRepository;
import com.example.demo.security.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.demo.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @modify Yongbeom Cho
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    @PostMapping(path = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity registerAccount(@Valid @RequestBody UserDto.Create userDto) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        if (!!StringUtils.isEmpty(userDto.getPassword()) &&
                userDto.getPassword().length() >= 4 && userDto.getPassword().length() <= 100) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        userService.registerAccount(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping(path = "/registerAsync",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity registerAccountAsync(@Valid @RequestBody UserDto.Create userDto) {
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
       
    		Future<User> futureUserReg =  userService.registerAccountAsync(userDto);
    		
    	    try {
	    	    while (true) {
			        if (futureUserReg.isDone()) {
			        		 userRepository.save(futureUserReg.get());
			        	     return new ResponseEntity<>(HttpStatus.CREATED);
			        }
			        Thread.sleep(700); // 쓰레드를 슬립을 주어 와일문을 쉬게 한다. 
	    		}
           
        } catch (Exception ae) {
        		ae.printStackTrace(); 
            logger.debug("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("RegistException",
                    ae.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }    
    

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDto.Response> getUser(@PathVariable String login) {
        return userRepository.findOneByLogin(login)
                .map(user -> modelMapper.map(user, UserDto.Response.class))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto.Response>> getAllUsers(Pageable pageable) {
        Page<User> page = userService.findAllUser(pageable);
        Page<UserDto.Response> pageResult = page.map(user -> modelMapper.map(user, UserDto.Response.class));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(pageResult.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping(path = "/user/update-password",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity updatePassword(@Valid @RequestBody UserDto.Login userDto) {
        userService.updatePassword(userDto.getLogin(), userDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
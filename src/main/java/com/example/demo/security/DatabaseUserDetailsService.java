package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.security.model.User;
import com.example.demo.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @modify Yongbeom Cho
 */
@Component
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        String lowercaseLogin = login.toLowerCase();
        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin, user.getPassword(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("사용자 " + lowercaseLogin + "DB에 존재하지 않습니다."));
    }
}
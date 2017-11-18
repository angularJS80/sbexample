package com.example.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.security.model.User;

import java.util.Optional;

/**
 * @modify Yongbeom Cho
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(Long id);
    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByLoginOrEmail(String login, String email);
    Optional<User> findOneWithAuthoritiesByLogin(String login);
    Optional<User> findOneByEmail(String email);
}

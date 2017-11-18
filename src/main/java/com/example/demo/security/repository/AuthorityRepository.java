package com.example.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.security.model.Authority;

/**
 * @modify Yongbeom Cho
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

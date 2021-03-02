package com.fafunda.authms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fafunda.authms.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

	Page<User> findAllByUsername(String username, PageRequest pageRequest);

	User findOneByUsername(String username);
}

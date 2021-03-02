package com.fafunda.authms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fafunda.authms.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	public Authority findOneByRole(@Param("role") String role);
	
}

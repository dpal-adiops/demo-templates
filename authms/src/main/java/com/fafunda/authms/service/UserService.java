package com.fafunda.authms.service;

import org.springframework.data.domain.PageRequest;

import com.fafunda.authms.mapper.UserPagedList;

public interface UserService {

	UserPagedList listUsers(String username, PageRequest of);

}

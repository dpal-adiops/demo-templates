package com.fafunda.authms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fafunda.authms.mapper.UserPagedList;
import com.fafunda.authms.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/")
@RestController
public class UserController {

	 private static final Integer DEFAULT_PAGE_NUMBER = 0;
	 private static final Integer DEFAULT_PAGE_SIZE = 25;
	    
	@Autowired
    private  UserService userService;
	
	@GetMapping(produces = { "application/json" }, path = "users")
	public ResponseEntity<UserPagedList> listUsers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "username", required = false) String username) {
		 log.debug("Listing users");
		 if (pageNumber == null || pageNumber < 0){
	            pageNumber = DEFAULT_PAGE_NUMBER;
	        }

	        if (pageSize == null || pageSize < 1) {
	            pageSize = DEFAULT_PAGE_SIZE;
	        }
	        UserPagedList userList = userService.listUsers(username, PageRequest.of(pageNumber, pageSize));
	        
	        return new ResponseEntity<>(userList, HttpStatus.OK);
		 
	}
}

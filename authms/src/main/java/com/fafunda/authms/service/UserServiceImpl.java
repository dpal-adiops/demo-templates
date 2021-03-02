package com.fafunda.authms.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fafunda.authms.domain.User;
import com.fafunda.authms.mapper.UserMapper;
import com.fafunda.authms.mapper.UserPagedList;
import com.fafunda.authms.model.UserDto;
import com.fafunda.authms.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private UserMapper<User, UserDto> userMapper = (user -> {
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setId(user.getId());
		return userDto;
	});

	@Override
	public UserPagedList listUsers(String username, PageRequest pageRequest) {
		log.debug("fetching user list");
		 UserPagedList userPagedList;
	        Page<User> userPage;

	        if (!ObjectUtils.isEmpty(username)) {
	            //search both
	        	userPage = userRepository.findAllByUsername(username, pageRequest);
	       
	        } else {
	        	userPage = userRepository.findAll(pageRequest);
	        }
	        
	        userPagedList = new UserPagedList(userPage
                    .getContent()
                    .stream()
                    .map(userMapper::map)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(userPage.getPageable().getPageNumber(),
                            		userPage.getPageable().getPageSize()),
                            userPage.getTotalElements());
	        
	        return userPagedList;
	}
}

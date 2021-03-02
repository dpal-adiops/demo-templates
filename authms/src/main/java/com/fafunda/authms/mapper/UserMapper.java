package com.fafunda.authms.mapper;



@FunctionalInterface
public interface UserMapper<K,T> {

	T map(K user);
}

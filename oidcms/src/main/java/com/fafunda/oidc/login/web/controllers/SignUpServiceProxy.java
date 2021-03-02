package com.fafunda.oidc.login.web.controllers;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="netflix-zuul-api-gateway-server")
public interface SignUpServiceProxy {
	@GetMapping("/authms/v1/signup")
	public String submitForm(@RequestBody SignUpRequest reqBody);
}

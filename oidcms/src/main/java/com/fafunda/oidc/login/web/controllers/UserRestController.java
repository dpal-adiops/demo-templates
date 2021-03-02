package com.fafunda.oidc.login.web.controllers;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fafunda.oidc.security.jwt.util.JwtTokenUtil;

@EnableFeignClients
@RestController
@RequestMapping("/oauth")
public class UserRestController {

    
	@Autowired
	private SignUpServiceProxy proxy;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    public UserRestController() {
    }

    @GetMapping("/token")
    public ResponseEntity<?> getOidcUserPrincipal(@AuthenticationPrincipal OidcUser principal) { 
    	String paswd=RandomStringUtils.random(6, true, true);
    	String message = proxy.submitForm(new SignUpRequest(principal.getEmail(),paswd,"GOOGLE"));
    	System.out.println(message);
    	UserDetails tUserDetails=new org.springframework.security.core.userdetails.User(principal.getEmail(),paswd,
    			true, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER")));
    	final String token = jwtTokenUtil.generateToken(tUserDetails);
		return ResponseEntity.ok(new AuthResponse(token));
    }


}

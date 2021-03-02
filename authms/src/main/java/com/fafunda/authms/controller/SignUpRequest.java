package com.fafunda.authms.controller;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
	 @NotBlank(message = "username is mandatory")
	 	private String username;
	
	    private String password;
	    
	    private String oidcProvider;
}

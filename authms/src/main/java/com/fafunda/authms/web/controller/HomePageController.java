package com.fafunda.authms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
	@RequestMapping("/home")
	public String index() {
		return "Application home Page!";
	}
	
	
	
	
}

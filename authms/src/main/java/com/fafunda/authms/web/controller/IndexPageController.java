package com.fafunda.authms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexPageController {

	
	@RequestMapping("/")
	public String index() {
		return "Index Page!";
	}
}

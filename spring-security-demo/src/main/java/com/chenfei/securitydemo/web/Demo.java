package com.chenfei.securitydemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Demo {


	@GetMapping("/sayHi")
	public String sayHi(){

		return "Hi ChanFi";
	}

	@GetMapping("/admin/sayHi")
	public String sayAdminHi(){

		return "Hi amdin ChanFi";
	}

	@GetMapping("/user/sayHi")
	public String sayUserHi(){

		return "Hi user ChanFi";
	}
}

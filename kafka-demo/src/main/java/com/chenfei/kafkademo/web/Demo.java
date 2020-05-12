package com.chenfei.kafkademo.web;


import com.chenfei.kafkademo.model.DemoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/demo")
@Api(value = "demo-API")
public class Demo {

	@GetMapping("/sayHello")
	@ApiOperation(value = "demo-sayHello")
	public String sayHello(@RequestParam("str") String str){
		return str+"12321";
	}

	@PostMapping("/postSayHello")
	@ApiOperation(value = "demo-postSayHello")
	public String sayHello(@RequestBody DemoVO demoVO){
		return demoVO.getName();
	}
}

package com.chenfei.kafkademo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "test-demo-model")
public class DemoVO {

	@ApiModelProperty(value = "名字",required = true,example = "ChanFi")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

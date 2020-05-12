package com.chenfei.kafkademo.web;


import com.alibaba.fastjson.JSON;
import com.chenfei.kafkademo.constant.BizConstant;
import com.chenfei.kafkademo.producer.KafkaProducer;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/kafka")
@Api(value = "kafa-发送API")
public class KafkaController {

	@Resource
	private KafkaProducer kafkaProducer;

	@Resource
	private AdminClient adminClient;

	@GetMapping("/send")
	@ApiOperation(value = "发送消息")
	public String sendMsg(){
		for (int i = 0; i <1000 ; i++) {
			kafkaProducer.send("this is kafka producer demo【"+i+"】!",String.valueOf(i));
		}
		return "success";
	}

	@GetMapping("/create")
	@ApiOperation(value = "创建topic")
	public void createTopic(){
		// 这种是手动创建 //10个分区，一个副本
		// 分区多的好处是能快速的处理并发量，但是也要根据机器的配置
		NewTopic topic = new NewTopic(BizConstant.TOPIC_TEST, 10, (short) 1);
		CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(topic));
		topics.values().values().stream().forEach(voidKafkaFuture -> {
			try {
				System.out.println("创建topic结果：" + JSON.toJSONString(voidKafkaFuture.get()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

	}
}

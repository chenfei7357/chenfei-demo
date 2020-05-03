package com.chenfei.kafkademo.web;


import com.chenfei.kafkademo.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

	@Resource
	private KafkaProducer kafkaProducer;

	@Resource
	private AdminClient adminClient;

	@GetMapping("/send")
	public String sendMsg(){
		for (int i = 0; i <1000 ; i++) {
			kafkaProducer.send("this is kafka producer demo【"+i+"】!");
		}
		return "success";
	}

	@GetMapping("/create")
	public void createTopic(){
		// 这种是手动创建 //10个分区，一个副本
		// 分区多的好处是能快速的处理并发量，但是也要根据机器的配置
		NewTopic topic = new NewTopic(KafkaProducer.TOPIC_TEST, 10, (short) 1);
		adminClient.createTopics(Arrays.asList(topic));
	}
}

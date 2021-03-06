package com.chenfei.kafkademo.consumer;


import com.alibaba.fastjson.JSON;
import com.chenfei.kafkademo.constant.BizConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumerDemo {
//
//	@KafkaListener(topics = BizConstant.YC,groupId = BizConstant.TOPIC_GROUP)
//	public void topic_test(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
//		log.info("线程名：{}, 内容：{}", Thread.currentThread().getName());
//		for (ConsumerRecord<String, String> record : records) {
//			Optional message = Optional.ofNullable(record.value());
//			if (message.isPresent()) {
//				Object msg = message.get();
//				log.info("topic_test 消费了： Topic:" + record.topic() + ",key:" + record.key()+ ",Message:" + msg);
//			}
//		}
//		ack.acknowledge();
//	}

//	@KafkaListener(topics = BizConstant.TOPIC_TEST_U, groupId = BizConstant.TOPIC_GROUP)
//	public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack) {
//
//		Optional message = Optional.ofNullable(record.value());
//		Map value = JSON.parseObject(String.valueOf(record.value()),Map.class);
//		Object data = value.get("data");
//		System.out.println("data:"+data);
//		if (message.isPresent()) {
//			Object msg = message.get();
//			log.info("topic_test1 消费了： Topic:" + record.topic() + ",Message:" + msg);
//			ack.acknowledge();
//		}
//	}
}

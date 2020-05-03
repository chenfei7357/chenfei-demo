package com.chenfei.kafkademo.consumer;


import com.alibaba.fastjson.JSON;
import com.chenfei.kafkademo.constant.BizConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumer {

	@KafkaListener(concurrency = "5",topics = BizConstant.TOPIC_TEST,groupId = BizConstant.TOPIC_GROUP)
	public void topic_test(ConsumerRecord record, Acknowledgment ack) {
		log.info("线程名：{}, 内容：{}", Thread.currentThread().getName(),JSON.toJSONString(record.value()));
		Optional message = Optional.ofNullable(record.value());
		if (message.isPresent()) {
			Object msg = message.get();
			log.info("topic_test 消费了： Topic:" + record.topic() + ",key:" + record.key()+ ",Message:" + msg);
		}
		ack.acknowledge();
	}

//	@KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP2)
//	public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//
//		Optional message = Optional.ofNullable(record.value());
//		if (message.isPresent()) {
//			Object msg = message.get();
//			log.info("topic_test1 消费了： Topic:" + topic + ",Message:" + msg);
//			ack.acknowledge();
//		}
//	}
}

package com.chenfei.kafkademo.producer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenfei.kafkademo.constant.BizConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

@Slf4j
@Component
public class KafkaProducer {

	@Resource
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void send(Object obj,String key) {

		String obj2String = JSONObject.toJSONString(obj);
		log.info("准备发送消息为：{}", obj2String);
		//发送消息
		ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(BizConstant.TOPIC_TEST, key,obj);
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(record);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void onFailure(Throwable throwable) {
				//发送失败的处理
				log.info(BizConstant.TOPIC_TEST + " - 生产者 发送消息失败：" + throwable.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
				//成功的处理
				log.info(BizConstant.TOPIC_TEST + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
			}
		});
	}


}

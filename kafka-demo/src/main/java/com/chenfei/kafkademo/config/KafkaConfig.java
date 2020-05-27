package com.chenfei.kafkademo.config;


import com.chenfei.kafkademo.constant.BizConstant;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

	@Resource
	private KafkaProperties properties;

	@Bean
	KafkaListenerContainerFactory<?> batchFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new
				ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(true); // 开启批量监听
		factory.setConcurrency(5);
		factory.setMissingTopicsFatal(false);
		/**
		 * RECORD :当listener一读到消息，就提交offset
		 *
		 * BATCH : poll() 函数读取到的所有消息,就提交offset
		 *
		 * TIME : 当超过设置的ackTime ，即提交Offset
		 *
		 * COUNT ：当超过设置的COUNT，即提交Offset
		 *
		 * COUNT_TIME ：TIME和COUNT两个条件都满足，提交offset
		 *
		 * MANUAL ： Acknowledgment.acknowledge()即提交Offset，和Batch类似
		 *
		 * MANUAL_IMMEDIATE： Acknowledgment.acknowledge()被调用即提交Offset
		 */
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}

	@Bean
	public ConsumerFactory consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.GROUP_ID_CONFIG, BizConstant.TOPIC_GROUP);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getConsumer().getAutoOffsetReset());
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, properties.getConsumer().getMaxPollRecords()); //设置每次接收Message的数量
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100000);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
		props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,properties.getConsumer().getEnableAutoCommit());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}




	@Bean //创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> props = new HashMap<>();
		//配置Kafka实例的连接地址
		//kafka的地址，不是zookeeper
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		KafkaAdmin admin = new KafkaAdmin(props);
		return admin;
	}

	@Bean  //kafka客户端，在spring中创建这个bean之后可以注入并且创建topic
	public AdminClient adminClient() {
		return AdminClient.create(kafkaAdmin().getConfig());
	}
}

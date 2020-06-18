package com.chenfei.zuuldemo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.statistic.cache.CacheMap;
import com.alibaba.csp.sentinel.slots.statistic.cache.ConcurrentLinkedHashMapWrapper;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.google.common.collect.Maps;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Demo {

	static ExecutorService executorService = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws Exception {

		setLogLevel();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		int num=0;
		while (true){
			if(num%2==0){
				TimeUnit.MILLISECONDS.sleep(10);
			}
			executorService.execute(() -> testGet("http://localhost:8889/api/myApi/demo/sayHello?str=212313",httpclient));
//			testGet("http://localhost:8889/kafka-demo/demo/sayHello?str=212313");
//			testGet("http://localhost:8889/api/myApi/demo/sayHello?str=212313");
			num++;
		}
//		for (int i = 0; i <20000 ; i++) {
//			testGet("http://localhost:8889/openApi/demo/sayHello?str=212313");
//		}
//		System.out.println("结束");
//		TimeUnit.SECONDS.sleep(100);

//		for (int i = 0; i < 1000; i++) {
//			testGet("http://localhost:8888/my_api/demo/sayHello?str=212313");
//			testGet("http://localhost:8888/kafka-demo/demo/sayHello?str=212313");
//		}

//		System.out.println(Math.round(1.0 * 1000 * 1 * 2 / 1));
//		AtomicLong lastPastTimeRef = new AtomicLong();
//		lastPastTimeRef.set(10000);
//		if (lastPastTimeRef.compareAndSet(10000, 100003)) {
//			System.out.println("交换成功，最新值为："+lastPastTimeRef.get());
//		}else{
//			System.out.println("交换失败，最新值为："+lastPastTimeRef.get());
//		}
//		for (int i = 0; i <10 ; i++) {
//			if(test(1,1,5)){
//				System.out.println("成功");
//			}else{
//				System.out.println("失败");
//			};
//		}
	}



	private static void testGet(String reqUrl,CloseableHttpClient httpclient)  {
		try {
			HttpGet httpget = new HttpGet(reqUrl);
			httpget.setHeader("token", "1");
			httpget.setHeader("Content-Type","application/json");
			CloseableHttpResponse execute =null;
			execute = httpclient.execute(httpget);
			//请求体内容
			String content = EntityUtils.toString(execute.getEntity(), "UTF-8");
			//内容
			System.out.println("返回结果：" + content);
		}catch (Exception e){
			throw new RuntimeException(e);
		}

	}

	private static final Map<String, CacheMap<Object, AtomicLong>> ruleTimeCounters = Maps.newHashMap();
	static {
		ruleTimeCounters.put("$D",new ConcurrentLinkedHashMapWrapper<Object, AtomicLong>(100));
	}


	public static boolean test(int acquireCount,int durationInSec,int tokenCount){
		String value= "$D";
		CacheMap<Object, AtomicLong> timeRecorderMap = ruleTimeCounters.get(value);

		long costTime = Math.round(1.0 * 1000 * acquireCount * durationInSec / tokenCount);
		while (true) {
			long currentTime = TimeUtil.currentTimeMillis();
			AtomicLong timeRecorder = timeRecorderMap.putIfAbsent(value, new AtomicLong(currentTime));
			if (timeRecorder == null) {
				return true;
			}
			//AtomicLong timeRecorder = timeRecorderMap.get(value);
			long lastPassTime = timeRecorder.get();
			long expectedTime = lastPassTime + costTime;

			if (expectedTime <= currentTime) {
				AtomicLong lastPastTimeRef = timeRecorderMap.get(value);
				if (lastPastTimeRef.compareAndSet(lastPassTime, currentTime)) {
					long waitTime = expectedTime - currentTime;
					if (waitTime > 0) {
						lastPastTimeRef.set(expectedTime);
						try {
							TimeUnit.MILLISECONDS.sleep(waitTime);
						} catch (InterruptedException e) {
							RecordLog.warn("passThrottleLocalCheck: wait interrupted", e);
						}
					}
					return true;
				} else {
					Thread.yield();
				}
			} else {
				return false;
			}
		}
	}

	private static void setLogLevel() {
		Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http"));
		for (String log : loggers) {
			Logger logger = (Logger) LoggerFactory.getLogger(log);
			logger.setLevel(Level.INFO);
			logger.setAdditive(false);
		}
	}
}

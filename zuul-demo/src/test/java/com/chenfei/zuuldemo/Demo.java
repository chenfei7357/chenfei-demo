package com.chenfei.zuuldemo;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.statistic.cache.CacheMap;
import com.alibaba.csp.sentinel.slots.statistic.cache.ConcurrentLinkedHashMapWrapper;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Demo {

	static ExecutorService executorService = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws Exception {

//		for (int i = 0; i <2 ; i++) {
//			try {
////				executorService.execute(() -> testGet("http://localhost:8888/my_api/demo/sayHello?str=212313"));
//				executorService.execute(() -> testGet("http://localhost:8888/kafka-demo/demo/sayHello?str=212313"));
//			}catch (Exception e){
//				System.out.println("错误："+e);
//			}
//		}
		int num=0;
		while (true){
			if(num%2==0){
				TimeUnit.MILLISECONDS.sleep(10);
			}
//			executorService.execute(() -> testGet("http://localhost:8889/kafka-demo/demo/sayHello?str=212313"));
//			testGet("http://localhost:8889/kafka-demo/demo/sayHello?str=212313");
			testGet("http://localhost:8889/openApi/demo/sayHello?str=212313");
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

	private static void testGet(String reqUrl)  {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "close");
			conn.setRequestProperty("token", "1");
			conn.connect();


			// 获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {// 循环读取流
				sb.append(line);
			}
			br.close();// 关闭流
			conn.disconnect();// 断开连接
			System.out.println("返回结果：" +sb.toString());
		}catch (Exception e){
			throw new RuntimeException(e);
//			System.out.println("错误："+e);
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
}

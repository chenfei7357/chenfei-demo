package com.chenfei.zuuldemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

	static ExecutorService executorService = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws Exception {

//		for (int i = 0; i <10 ; i++) {
//			executorService.execute(() -> testGet());
//		}

		for (int i = 0; i < 1000; i++) {
			testGet("http://localhost:8888/my_api/demo/sayHello?str=212313");
			testGet("http://localhost:8888/kafka-demo/demo/sayHello?str=212313");
		}

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
			System.out.println("错误："+e);
		}

	}
}

package com.chenfei.kafkademo.web;


import com.alibaba.fastjson.JSON;
import com.chenfei.kafkademo.model.DemoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/demo")
@Api(value = "demo-API")
public class Demo {

	@Value("${server.port}")
	private String port;

	@GetMapping("/sayHello")
	@ApiOperation(value = "demo-sayHello")
	public String sayHello(@RequestParam("str") String str){
		try {
			TimeUnit.MILLISECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return str+",port:"+port;
	}

	@PostMapping("/postSayHello")
	@ApiOperation(value = "demo-postSayHello")
	public String sayHello(@RequestBody DemoVO demoVO){
		return demoVO.getName();
	}

	/**
	 * 授权回调接口地址
	 * @param code
	 * @param state
	 * @return
	 */
	@GetMapping("/callback")
	public String callback(@RequestParam("code") String code,
						@RequestParam("state") String state) {


		//todo 通过授权码code 获取access_token
		String accessToken=this.getAccessToken(code,state);

		//todo 通过access_token获取用户的相关信息
		Map<String,Object> userInfo=getGitHubUserInfo(accessToken);

		return "login:"+userInfo.get("login")+"    id:"+userInfo.get("id");
	}

	private Map<String, Object> getGitHubUserInfo(String accessToken) {
		System.out.println("accessToken:"+accessToken);
		String url="https://api.github.com/user?access_token="+accessToken;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		try {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			CloseableHttpResponse execute = httpclient.execute(get);
			String res = EntityUtils.toString(execute.getEntity(),"utf-8");
			Map<String,Object> result =JSON.parseObject(res, Map.class);
			System.out.println("获取用户信息返回结果："+JSON.toJSONString(result));
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getAccessToken(String code, String state) {

		String url="https://github.com/login/oauth/access_token";
		Map<String, String> map = new HashMap<>();
		map.put("client_id", "73f4f09327a729c6ac61");
		map.put("client_secret", "8702186404ff9c383fdfafb660ba5cd9c1e1e340");
		map.put("state",state);
		map.put("code", code);
		map.put("redirect_uri", "http://localhost:8081/demo/callback");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type","application/json");
		StringEntity stringEntity = new StringEntity(JSON.toJSONString(map), ContentType.create("text/json", "UTF-8"));
		post.setEntity(stringEntity);
		try {
			CloseableHttpResponse execute = httpclient.execute(post);
			String res = EntityUtils.toString(execute.getEntity(),"utf-8");
			System.out.println("获取token返回结果："+res);
			String[] strs=res.split("&");
			String access_token=strs[0].split("=")[1];//解析access_token
			return access_token;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}

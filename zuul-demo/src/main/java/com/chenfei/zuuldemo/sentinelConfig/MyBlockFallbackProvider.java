package com.chenfei.zuuldemo.sentinelConfig;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBlockFallbackProvider implements ZuulBlockFallbackProvider {
	@Override
	public String getRoute() {
		return null;
	}

	@Override
	public BlockResponse fallbackResponse(String route, Throwable cause) {
		log.info("请求错误信息：{}",cause);
		if (cause instanceof BlockException) {
			return new BlockResponse(200, "请求频繁,请稍后重试", route);
		} else {
			return new BlockResponse(500, "系统异常", route);
		}
	}
}

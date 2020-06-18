package com.chenfei.zuuldemo.sentinelConfig;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
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
		if(cause instanceof ParamFlowException){
			return new BlockResponse(200, "请求频繁ParamFlowException,请稍后重试", route);
		} else if(cause instanceof DegradeException){
			return new BlockResponse(200, "请求被降级,请稍后重试", route);
		}else if(cause instanceof BlockException) {
			return new BlockResponse(200, "请求频繁BlockException,请稍后重试", route);
		} else {
			return new BlockResponse(500, "系统异常", route);
		}
	}
}

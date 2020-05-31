package com.chenfei.zuuldemo.sentinelConfig;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Set;

//@Configuration
@Slf4j
public class ZuulConfig implements InitializingBean {

	@Resource
	private GatewayFlowRuleProperties gatewayFlowRuleProperties;


	@Override
	public void afterPropertiesSet() throws Exception {

		// 注册 FallbackProvider
//		ZuulBlockFallbackManager.registerProvider(new MyBlockFallbackProvider());
		//静态加载限流规则
		//initGatewayRules();
		//动态加载限流规则
//		this.initApolloFlowRule();

	}

	@Bean
	public ZuulFilter sentinelZuulPreFilter() {
		// We can also provider the filter order in the constructor.
		return new SentinelZuulPreFilter();
	}

	@Bean
	public ZuulFilter sentinelZuulPostFilter() {
		return new SentinelZuulPostFilter();
	}

	@Bean
	public ZuulFilter sentinelZuulErrorFilter() {
		return new SentinelZuulErrorFilter();
	}


	/**
	 * 配置限流规则
	 */
	private void initGatewayRules() {
//		Set<GatewayFlowRule> rules = new HashSet<>();
//		rules.add(new GatewayFlowRule("kafka-demo")
//				.setCount(1) // 限流阈值
//				.setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
				//应对突发请求时额外允许的请求数目。
//				.setBurst(2)
//				.setParamItem(new GatewayParamFlowItem()
//						.setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
//				)
//		);
		log.info("gatewayFlowRuleProperties:{}", JSON.toJSONString(gatewayFlowRuleProperties));
		GatewayRuleManager.loadRules(gatewayFlowRuleProperties.getGatewayFlowRules());
	}

	//动态监听加载限流规则
	private void initApolloFlowRule() {
		String defaultFlowRules = "[]";
		ReadableDataSource<String, Set<GatewayFlowRule>> flowRuleDataSource = new ApolloDataSource<>("application", "flowRules",
				defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {}));
		log.info("flowRules:{}",JSON.toJSONString(flowRuleDataSource.getProperty()));
		GatewayRuleManager.register2Property(flowRuleDataSource.getProperty());
	}
}

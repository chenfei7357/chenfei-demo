package com.chenfei.zuuldemo.sentinelConfig;


import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "flow.rule")
public class GatewayFlowRuleProperties {

	private Set<GatewayFlowRule> gatewayFlowRules;
}

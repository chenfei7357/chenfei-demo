package com.chenfei.zuuldemo.customFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenfei.zuuldemo.sentinelConfig.GatewayFlowRuleProperties;
import com.google.gson.JsonObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class TokenFilter extends ZuulFilter {

	@Value("#{'${url.prefix}'.split(',')}")
	private List<String> urlPrefix;

	/**
	 * PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
	 * ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
	 * POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
	 * ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。
	 * @return
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {

		RequestContext context= RequestContext.getCurrentContext();
		HttpServletRequest request=context.getRequest();
		String requestURI = request.getRequestURI();
		log.info("requestURI：{}",requestURI);
		if(CollectionUtils.isNotEmpty(urlPrefix)){
			for (String prefix : urlPrefix) {
				if(requestURI.contains(prefix)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		Object accessToken = request.getHeader("token");
		if (Objects.isNull(accessToken)) {
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(404);
			try {
				JSONObject object = new JSONObject();
				object.put("code",400);
				object.put("message","token is empty");
				context.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
				context.getResponse().getWriter().write(object.toJSONString());
			} catch (Exception e) {
				log.error("errorMsg:{}", e);
			}
			return null;
		}
		return null;
	}
}

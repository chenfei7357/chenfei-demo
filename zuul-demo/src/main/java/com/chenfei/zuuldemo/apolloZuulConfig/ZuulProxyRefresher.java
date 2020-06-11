package com.chenfei.zuuldemo.apolloZuulConfig;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZuulProxyRefresher implements ApplicationContextAware {


	private ApplicationContext applicationContext;

	@Autowired
	private RouteLocator routeLocator;

	@ApolloConfigChangeListener(interestedKeyPrefixes = "zuul.")
	public void onChange(ConfigChangeEvent changeEvent) {
		refreshZuulProxy(changeEvent);
	}

	private void refreshZuulProxy(ConfigChangeEvent changeEvent) {

		log.info("Refreshing zuul properties!");
		this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
		this.applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));
		log.info("Zuul properties refreshed!");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
